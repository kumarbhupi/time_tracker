package webserver;

import core.Interval;
import core.Task;
import core.TaskManager;
import core.Tracker;
import org.json.JSONArray;
import org.json.JSONObject;
import searcher.Tag;
import searcher.TagManager;
import visitor.ToJsonVisitor;
import visitor.TotalTimeCalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

// Based on
// https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java
// http://www.jcgonzalez.com/java-socket-mini-server-http-example

public class WebServer {
  private static final int PORT = 8080; // port to listen to

  private Tracker currentActivity;
  private final Tracker root;

  public WebServer(Tracker root) {
    this.root = root;
    System.out.println(root);
    currentActivity = root;
    try {
      ServerSocket serverConnect = new ServerSocket(PORT);
      System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");
      // we listen until user halts server execution
      while (true) {
        // each client connection will be managed in a dedicated Thread
        new SocketThread(serverConnect.accept());
        // create dedicated thread to manage the client connection
      }
    } catch (IOException e) {
      System.err.println("Server Connection error : " + e.getMessage());
    }
  }

  private Tracker findActivityById(int id) {
    return root.findActivityById(id);
  }

  private class SocketThread extends Thread {
    // SocketThread sees WebServer attributes
    private final Socket insocked;
    // Client Connection via Socket Class

    SocketThread(Socket insocket) {
      this.insocked = insocket;
      this.start();
    }

    @Override
    public void run() {
      // we manage our particular client connection
      BufferedReader in;
      PrintWriter out;
      String resource;

      try {
        // we read characters from the client via input stream on the socket
        in = new BufferedReader(new InputStreamReader(insocked.getInputStream()));
        // we get character output stream to client
        out = new PrintWriter(insocked.getOutputStream());
        // get first line of the request from the client
        String input = in.readLine();
        // we parse the request with a string tokenizer

        System.out.println("sockedthread : " + input);

        StringTokenizer parse = new StringTokenizer(input);
        String method = parse.nextToken().toUpperCase();
        // we get the HTTP method of the client
        if (!method.equals("GET")) {
          System.out.println("501 Not Implemented : " + method + " method.");
        } else {
          // what comes after "localhost:8080"
          resource = parse.nextToken();
          System.out.println("input " + input);
          System.out.println("method " + method);
          System.out.println("resource " + resource);

          parse = new StringTokenizer(resource, "/[?]=&");
          int i = 0;
          String[] tokens = new String[20];
          // more than the actual number of parameters
          while (parse.hasMoreTokens()) {
            tokens[i] = parse.nextToken();
            System.out.println("token " + i + "=" + tokens[i]);
            i++;
          }

          // Make the answer as a JSON string, to be sent to the Javascript client
          String answer = makeHeaderAnswer() + makeBodyAnswer(tokens);
          System.out.println("answer\n" + answer);
          // Here we send the response to the client
          out.println(answer);
          out.flush(); // flush character output stream buffer
        }

        in.close();
        out.close();
        insocked.close(); // we close socket connection
      } catch (Exception e) {
        System.err.println("Exception : " + e + " " + Arrays.toString(e.getStackTrace()));
      }
    }


    private String makeBodyAnswer(String[] tokens) {
      String body = "";
      ToJsonVisitor jsonVisitor = new ToJsonVisitor();
      Interval interval;
      switch (tokens[0]) {
        case "get_tree": {
          TagManager tagManager = TagManager.getInstance();

          int id = Integer.parseInt(tokens[1]);
          Tracker activity = findActivityById(id);

          assert (activity != null);
          body = jsonVisitor.visit(activity).toString();
          //body = activity.toJson(1).toString();
          break;
        }
        case "start": {
          int id = Integer.parseInt(tokens[1]);
          Tracker activity = findActivityById(id);
          assert (activity != null);
          Task task = (Task) activity;
          task.createInterval();
          //task.start();
          body = "{}";
          break;
        }
        case "stop": {
          int id = Integer.parseInt(tokens[1]);
          Tracker activity = findActivityById(id);
          assert (activity != null);
          Task task = (Task) activity;
          task.endInterval();
          //task.stop();
          body = "{}";
          break;
        }
        // TODO: add new task, project
        // TODO: edit task, project properties
        case "add": {//add?0&task&new_test
          TagManager tagManager = TagManager.getInstance();
          int id = Integer.parseInt(tokens[1]);
          assert (tokens[2] != null && tokens[3] != null);
          Tracker activity = findActivityById(id);
          assert (activity != null);
          if (tokens[2].equalsIgnoreCase("task")) {
            Task task = new Task((TaskManager) activity, tokens[3].replace("%20", " "));
            ((TaskManager) activity).addChild(task);

            for (int i = 4; i < tokens.length; i++) {
              if (tokens[i] != null) {
                tagManager.createTag(tokens[i]);
                tagManager.addTracker(tokens[i], task);
              }
            }
          } else if (tokens[2].equalsIgnoreCase("project")) {
            TaskManager taskManager = new TaskManager((TaskManager) activity, tokens[3].replace("%20", " "));
            ((TaskManager) activity).addChild((taskManager));

            for (int i = 4; i < tokens.length; i++) {
              if (tokens[i] != null) {
                tagManager.createTag(tokens[i]);
                tagManager.addTracker(tokens[i], taskManager);
              }
            }
          }
          break;
        }
        case "find_by_tags": {//localhost/find_by_tags?tag_name
          TagManager tagManager = TagManager.getInstance();
          System.out.println(tokens[1]);
          List<Tracker> trackersFound = tagManager.searchTag(tokens[1]);
          JSONArray jsonArray = new JSONArray();
          for (Tracker tracker : trackersFound) {
            System.out.println(tracker.getName());
            jsonArray.put(jsonVisitor.visit(tracker));
          }

          body = jsonArray.toString();
          break;
        }

        case "get_duration": {
          int id = Integer.parseInt(tokens[1]);
          Tracker activity = findActivityById(id);
          LocalDateTime periodStartTime = LocalDateTime.of(
              LocalDate.of(
                  Integer.parseInt(tokens[2]),
                  Integer.parseInt(tokens[3]),
                  Integer.parseInt(tokens[4])),
              LocalTime.of(
                  Integer.parseInt(tokens[5]),
                  Integer.parseInt(tokens[6])));

          LocalDateTime periodEndTime = LocalDateTime.of(
              LocalDate.of(
                  Integer.parseInt(tokens[7]),
                  Integer.parseInt(tokens[8]),
                  Integer.parseInt(tokens[9])),
              LocalTime.of(
                  Integer.parseInt(tokens[10]),
                  Integer.parseInt(tokens[11])));

          TotalTimeCalculator totalTimeCalculator = new TotalTimeCalculator(periodStartTime, periodEndTime);
          long totalTime = totalTimeCalculator.calculateTime(activity);

          JSONObject bodyJson = new JSONObject();
          bodyJson.put("duration", totalTime);
          body = bodyJson.toString();

          break;
        }
        case "add_tags": {//Not used
          TagManager tagManager = TagManager.getInstance();
          int id = Integer.parseInt(tokens[1]);
          Tracker activity = findActivityById(id);
          for (int i = 2; i < tokens.length; i++) {
            if (tokens[i] != null) {
              tagManager.createTag(tokens[i]);
              tagManager.addTracker(tokens[i], activity);
            }
          }
          for (Tag tag : tagManager.tags) {
            System.out.println(tag.getNameTag());
          }
          break;
        }
        case "edit": {

        }
        break;
        default:
          assert false;
      }
      System.out.println(body);
      return body;
    }

    private String makeHeaderAnswer() {
      String answer = "";
      answer += "HTTP/1.0 200 OK\r\n";
      answer += "Content-type: application/json\r\n";
      answer += "\r\n";
      // blank line between headers and content, very important !
      return answer;
    }
  } // SocketThread

} // WebServer