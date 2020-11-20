package client;

import core.TaskManager;
import persistence_utils.FileManager;
import visitor_utils.FromJsonVisitor;
import visitor_utils.TotalTimeCalculator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {

  public static void main(String[] args) throws InterruptedException {

    /*
    TaskManager root = new TaskManager(null, "root");
    PrinterVisitor printerVisitor = new PrinterVisitor(root);

    TaskManager p0 = new TaskManager(root, "P0");
    root.addChild(p0);
    Task t0 = new Task(p0,"T0");
    p0.addChild(t0);

    sleep(10000); //10
    Interval t0_i = t0.createInterval();

    sleep(10000);//20

    Task t4 = new Task(root, "T4");
    root.addChild(t4);
    Interval t4_i = t4.createInterval();


    sleep(10000);//30
    t0_i.stopInterval();
    t4_i.stopInterval();

    Task t1 = new Task(p0, "T1");
    p0.addChild(t1);
    Interval t1_i = t1.createInterval();

    Task t2 = new Task(p0, "T2");
    p0.addChild(t2);
    Interval t2_i = t2.createInterval();

    sleep(10000);//40
    t0_i =t0.createInterval();

    Task t5 = new Task(root, "T5");
    root.addChild(t5);
    Interval t5_i = t5.createInterval();

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

    TaskManager p1 = new TaskManager(root, "P1");
    root.addChild(p1);
    Task t3 = new Task(p1, "T3");
    p1.addChild(t3);
    Interval t3_i = t3.createInterval();

    t4_i.stopInterval();


    sleep(10000);//140
    t0_i = t0.createInterval();

    t3_i.stopInterval();

    t4_i = t4.createInterval();

    sleep(10000);//150
    t0_i.stopInterval();

    sleep(10000);//160
    t4_i.stopInterval();


    TaskManager p3 = new TaskManager(root, "P3");
    root.addChild(p3);

    printerVisitor.stopPrinting();

    visitor_utils.ToJsonVisitor toJsonVisitor = new visitor_utils.ToJsonVisitor();
    persistence_utils.FileManager fileManager = new persistence_utils.FileManager();
    fileManager.saveToJsonFile(toJsonVisitor.visit(root));
    System.out.println(START_TIME.toString() + "START-TIME");
    System.out.println(END_TIME.toString() + "END-TIME");







    /*core.TaskManager root = new core.TaskManager(null, "root");

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
    FileManager fileManager = new FileManager();
    FromJsonVisitor fromJsonVisitor = new FromJsonVisitor();
    fileManager.readFromJsonFile();
    TaskManager rootFromJson = fileManager.accept(fromJsonVisitor);
    LocalDate today = LocalDate.of(2020, 11, 20);
    LocalDateTime periodeStartTime = LocalDateTime.of(today, LocalTime.of(10, 29, 50,613710));
    LocalDateTime periodeEndTime = LocalDateTime.of(today, LocalTime.of(10, 30, 30,620238));

    TotalTimeCalculator totalTimeCalculator = new TotalTimeCalculator(periodeStartTime, periodeEndTime);
    long time = totalTimeCalculator.calculateTime(rootFromJson);
    System.out.println(time);

  }

}
