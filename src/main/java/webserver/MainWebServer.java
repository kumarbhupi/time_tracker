package webserver;

import core.Interval;
import core.Task;
import core.TaskManager;
import core.Tracker;

import static java.lang.Thread.sleep;

public class MainWebServer {
  public static void main(String[] args) {
    try {
      webServer();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void webServer() throws InterruptedException {
    final Tracker root = makeTreeCourses();
    // implement this method that returns the tree of
    // appendix A in the practicum handout
    // start your clock
    new WebServer(root);
  }

  private static Tracker makeTreeCourses() throws InterruptedException {
    TaskManager root = new TaskManager(null, "root");

    Task transportations = new Task(root, "Transportation");
    root.addChild(transportations);

    Interval transportationsInterval = transportations.createInterval();
    sleep(2000);

    transportationsInterval.stopInterval();

    TaskManager softwareDesign = new TaskManager(root, "Software Design");
    root.addChild(softwareDesign);

    TaskManager problems = new TaskManager(softwareDesign, "Problems");
    softwareDesign.addChild(problems);

    Task firstList = new Task(problems, "First List");
    problems.addChild(firstList);

    Task secondList = new Task(problems, "Second List");
    problems.addChild(secondList);

    return root;

  }


}