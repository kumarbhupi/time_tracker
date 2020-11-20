package client;

import persistence_utils.FileManager;
import core.Interval;
import core.Task;
import core.TaskManager;
import visitor_utils.FromJsonVisitor;
import visitor_utils.PrinterVisitor;

import static java.lang.Thread.sleep;

public class Main {

  public static void main(String[] args) throws InterruptedException {

    core.TaskManager root = new core.TaskManager(null, "root");

    core.Task transportations = new core.Task(root, "Transportation");
    root.addChild(transportations);

    visitor_utils.PrinterVisitor printerVisitor = new visitor_utils.PrinterVisitor(root);
    core.Interval transportationsInterval = transportations.createInterval();
    sleep(6000);
    transportationsInterval.stopInterval();

    core.TaskManager softwareDesign = new core.TaskManager(root, "Software Design");
    root.addChild(softwareDesign);

    core.TaskManager problems = new core.TaskManager(softwareDesign, "Problems");
    softwareDesign.addChild(problems);

    core.Task firstList = new core.Task(problems, "First List");
    problems.addChild(firstList);

    core.Task secondList = new core.Task(problems, "Second List");
    problems.addChild(secondList);

    sleep(2000);
    core.Interval firstListInterval = firstList.createInterval();
    sleep(6000);
    core.Interval secondListInterval = secondList.createInterval();

    sleep(4000);
    firstListInterval.stopInterval();
    sleep(2000);
    secondListInterval.stopInterval();
    sleep(2000);
    core.Interval transportationsSecondInterval = transportations.createInterval();
    sleep(4000);
    transportationsSecondInterval.stopInterval();

    printerVisitor.stopPrinting();
    core.Clock.getInstance().stopClock();


    //Write Root Object to JsonFile
    visitor_utils.ToJsonVisitor toJsonVisitor = new visitor_utils.ToJsonVisitor();
    persistence_utils.FileManager fileManager = new persistence_utils.FileManager();
    fileManager.saveToJsonFile(toJsonVisitor.visit(root));

    /**/
    //Read from Root JsonFile to core.TaskManager
   /* FileManager fileManager = new FileManager();
    FromJsonVisitor fromJsonVisitor = new FromJsonVisitor();
    fileManager.readFromJsonFile();
    TaskManager rootFromJson = fileManager.accept(fromJsonVisitor);
    PrinterVisitor printerVisitor = new PrinterVisitor(rootFromJson);
    Task afterReadingTask = new Task(rootFromJson, "Task After Reading");
    rootFromJson.addChild(afterReadingTask);

    Interval interval = afterReadingTask.createInterval();
    sleep(7000);
    interval.stopInterval();*/



  }

}
