import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.Thread.sleep;


public class DataStorage {
  public static final String JSON_SAVED_FILE_NAME = "json_data.json";
  public List<Tracker> ListTrackers=null;
  public void saveData(Tracker toSave){

    JSONArray trackerListJson = new JSONArray();
    trackerListJson.put(toSave.getJSON());
    try(FileWriter file = new FileWriter(JSON_SAVED_FILE_NAME)){
      file.write(toSave.getJSON().toString());
      file.flush();
    }catch (IOException e){
      e.printStackTrace();
    }
  }

  public void readData() throws IOException {
    Tracker tracker=null;
    String json = new String(Files.readAllBytes(Paths.get(JSON_SAVED_FILE_NAME)));
    JSONObject jsonObject=new JSONObject(json);
    System.out.println(jsonObject.get("trackers"));
    for (int i = 0; i < jsonObject.length(); i++) {

    }
    //System.out.println(jsonObject.length());
    //jsonObject.getJSONObject("Task1");

  }


  public static void main(String[] args) throws InterruptedException {
    Clock clock = new Clock();
    clock.startTick();
    ClockNotifier clockNotifier = new ClockNotifier(clock);

    TaskManager tracker = new TaskManager("Lorem");

    tracker.createTrackers("Lorem task", TrackerType.TASK);
    Task loremTask = (Task) tracker.createTrackers("Lorem task2", TrackerType.TASK);
    Interval someInterval = loremTask.createInterval();
    clockNotifier.addListener(someInterval);

    sleep(5000);

    someInterval.stopInterval();
    clockNotifier.removeListener(someInterval);

    tracker.createTrackers("Ipsum Project", TrackerType.PROJECT);



  }

}
