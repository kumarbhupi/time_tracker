package webserver;

import core.Interval;
import core.Task;
import core.TaskManager;
import core.Tracker;
import visitor.PrinterVisitor;

public class MainWebServer {
  public static void main(String[] args) {
    webServer();
  }

  public static void webServer() {
    final Tracker root = makeTreeCourses();
    // implement this method that returns the tree of
    // appendix A in the practicum handout
    // start your clock
    new WebServer(root);
  }

  private static Tracker makeTreeCourses(){
    TaskManager root = new TaskManager(null, "root");

    Task transportations = new Task(root, "Transportation");
    root.addChild(transportations);

    PrinterVisitor printerVisitor = new PrinterVisitor(root);
    Interval transportationsInterval = transportations.createInterval();
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