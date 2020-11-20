package client;

import core.Interval;
import core.Task;
import core.TaskManager;
import core.Tracker;
import persistence.FileManager;
import searcher.TagManager;
import searcher.Tags;
import visitor.FromJsonVisitor;
import visitor.PrinterVisitor;
import visitor.ToJsonVisitor;
import visitor.TotalTimeCalculator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.lang.Thread.sleep;

public class Main {

  public static void main(String[] args) throws InterruptedException {

    System.out.println("C++".equalsIgnoreCase("c++"));
    Main client = new Main();
    client.testTotalTimeFunctionalities();


    client.testTags();

  }


  private void testTags(){
    core.TaskManager root = new core.TaskManager(null, "root");

    core.TaskManager softwareDesign = new core.TaskManager(root, "Software Design");
    root.addChild(softwareDesign);

    core.TaskManager softwareTesting = new core.TaskManager(root, "Software Testing");
    root.addChild(softwareTesting);

    core.TaskManager database = new core.TaskManager(root, "Database");
    root.addChild(database);

    core.TaskManager taskTrasnportation = new core.TaskManager(root, "Task Transportation");
    root.addChild(taskTrasnportation);

    core.TaskManager problems = new core.TaskManager(softwareDesign, "Problems");
    softwareDesign.addChild(problems);

    core.TaskManager projectTime = new core.TaskManager(softwareDesign, "Project Time");
    softwareDesign.addChild(projectTime);

    core.Task firstList = new core.Task(problems, "First list");
    problems.addChild(firstList);

    core.Task secondList = new core.Task(problems, "Second list");
    problems.addChild(secondList);

    core.Task readHeadout = new core.Task(projectTime, "Read Headout");
    projectTime.addChild(readHeadout);

    core.Task firstMilestone = new core.Task(projectTime, "First Milestone");
    projectTime.addChild(firstMilestone);

    TagManager tagManager = new TagManager();

    tagManager.createTag("java");
    tagManager.addTracker("java",softwareDesign);
    tagManager.addTracker("java",firstList);

    tagManager.createTag("flutter");
    tagManager.addTracker("flutter",softwareDesign);

    tagManager.createTag("c++");
    tagManager.addTracker("c++",softwareTesting);

    tagManager.createTag("Java");
    tagManager.addTracker("Java",softwareTesting);
    tagManager.addTracker("Java",firstMilestone);

    tagManager.createTag("python");
    tagManager.addTracker("python",softwareTesting);
    tagManager.addTracker("python",database);

    tagManager.createTag("C++");
    tagManager.addTracker("C++",database);

    tagManager.createTag("SQL");
    tagManager.addTracker("SQL",database);

    tagManager.createTag("Dart");
    tagManager.addTracker("Dart",secondList);

    tagManager.createTag("IntelliJ");
    tagManager.addTracker("IntelliJ",firstList);

    //Test.removeTracker("java",softwareDesign);

    List<Tracker> trackersFound = tagManager.searchTag("java");

    for (Tracker tracker : trackersFound){
      System.out.println(tracker.getName());
    }

  }




  private void testTotalTimeFunctionalities(){

    core.TaskManager jsonRoot = readFromJsonToTaskManager(); //Para evitar ejecutar testGetTotalTime()

    LocalDate today = LocalDate.of(2020, 11, 20);
    LocalDateTime periodStartTime = LocalDateTime.of(today, LocalTime.of(15, 43, 30,734228));//Temps correspondent a 60seg
    LocalDateTime periodEndTime = LocalDateTime.of(today, LocalTime.of(15, 44, 30,739900));////Temps correspondent a 120seg
    visitor.TotalTimeCalculator totalTimeCalculator = new TotalTimeCalculator(periodStartTime, periodEndTime);
    long totalTime = totalTimeCalculator.calculateTime(jsonRoot);
    System.out.println(totalTime);
  }


  private core.TaskManager readFromJsonToTaskManager(){
    persistence.FileManager fileManager = new persistence.FileManager();
    visitor.FromJsonVisitor fromJsonVisitor = new visitor.FromJsonVisitor();
    fileManager.readFromJsonFile();
     return fileManager.accept(fromJsonVisitor);
  }

  private void testGetTotalTimeAndSaveItInJson() throws InterruptedException{
    core.TaskManager root = new core.TaskManager(null, "root");
    visitor.PrinterVisitor printerVisitor = new visitor.PrinterVisitor(root);
    //0
    core.TaskManager p0 = new core.TaskManager(root, "P0");
    root.addChild(p0);
    core.Task t0 = new core.Task(p0,"T0");
    p0.addChild(t0);
    core.Task t1 = new core.Task(p0, "T1");
    p0.addChild(t1);
    core.Task t2 = new core.Task(p0, "T2");
    p0.addChild(t2);

    core.TaskManager p1 = new core.TaskManager(root, "P1");
    root.addChild(p1);

    core.Task t3 = new core.Task(p1, "T3");
    p1.addChild(t3);
    core.Task t4 = new core.Task(root, "T4");
    root.addChild(t4);
    core.Task t5 = new core.Task(root, "T5");
    root.addChild(t5);

    core.TaskManager p3 = new core.TaskManager(root, "P3");
    root.addChild(p3);



    Thread.sleep(10000); //10
    core.Interval t0_i = t0.createInterval();

    sleep(10000);//20

    core.Interval t4_i = t4.createInterval();

    sleep(10000);//30
    t0_i.stopInterval();
    t4_i.stopInterval();

    core.Interval t1_i = t1.createInterval();

    core.Interval t2_i = t2.createInterval();

    sleep(10000);//40
    t0_i =t0.createInterval();


    core.Interval t5_i = t5.createInterval();

    sleep(10000);//50
    t0_i.stopInterval();

    t1_i.stopInterval();

    t4_i = t4.createInterval();

    sleep(10000);//60
    LocalDateTime START_TIME = LocalDateTime.now();


    sleep(10000);//70
    t1_i = t1.createInterval();

    t5_i.stopInterval();

    sleep(10000);//80
    t5_i = t5.createInterval();

    sleep(10000);//90
    t2_i.stopInterval();

    t5_i.stopInterval();

    sleep(10000);//100
    t5_i = t5.createInterval();

    sleep(10000);//110
    t2_i = t2.createInterval();

    t5_i.stopInterval();

    sleep(10000); //120
    LocalDateTime END_TIME = LocalDateTime.now();


    sleep(10000);//130

    t1_i.stopInterval();

    t2_i.stopInterval();


    core.Interval t3_i = t3.createInterval();

    t4_i.stopInterval();


    sleep(10000);//140
    t0_i = t0.createInterval();

    t3_i.stopInterval();

    t4_i = t4.createInterval();

    sleep(10000);//150
    t0_i.stopInterval();


    sleep(10000);//160
    t4_i.stopInterval();


    printerVisitor.stopPrinting();

    visitor.ToJsonVisitor toJsonVisitor = new visitor.ToJsonVisitor();
    persistence.FileManager fileManager = new persistence.FileManager();
    fileManager.saveToJsonFile(toJsonVisitor.visit(root));
    System.out.println(START_TIME.toString() + "START-TIME");
    System.out.println(END_TIME.toString() + "END-TIME");

  }

  private void testMiletone1() throws InterruptedException{
    core.TaskManager root = new core.TaskManager(null, "root");

    core.Task transportations = new core.Task(root, "Transportation");
    root.addChild(transportations);

    PrinterVisitor printerVisitor = new PrinterVisitor(root);
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
    ToJsonVisitor toJsonVisitor = new ToJsonVisitor();
    FileManager fileManager = new FileManager();
    fileManager.saveToJsonFile(toJsonVisitor.visit(root));
    FromJsonVisitor fromJsonVisitor = new FromJsonVisitor();
    fileManager.readFromJsonFile();
    TaskManager rootFromJson = fileManager.accept(fromJsonVisitor);
    LocalDate today = LocalDate.of(2020, 11, 20);
    LocalDateTime periodeStartTime = LocalDateTime.of(today, LocalTime.of(10, 29, 50,613710));
    LocalDateTime periodeEndTime = LocalDateTime.of(today, LocalTime.of(10, 30, 30,620238));
    printerVisitor = new PrinterVisitor(rootFromJson);
    Task afterReadingTask = new Task(rootFromJson, "Task After Reading");
    rootFromJson.addChild(afterReadingTask);

    Interval interval = afterReadingTask.createInterval();
    sleep(7000);
    interval.stopInterval();
  }

}
