import javafx.print.Printer;

import static java.lang.Thread.sleep;

public class Main {

  public static void main(String[] args) throws InterruptedException {

    /*
    Clock clock = Clock.getInstance();

    TaskManager root = new TaskManager("main");

    //Primer Task
    Task task0 = new Task(root, "first task");
    root.addChild(task0);
    //Tracker task0 = root.createTrackers("first task", TrackerType.TASK);

    Interval interval0 = task0.createInterval();
    sleep(5000);
    Interval interval01 = task0.createInterval();

    //notify.addListener(interval0);
    //notify.addListener(interval01);


    //Segon Task1

    Task task1 = new Task(root, "second task");
    root.addChild(task1);
    Interval interval1 =  task1.createInterval();
    //notify.addListener(interval1);

    sleep(5000);
    interval0.stopInterval();
    System.out.println("interval0 " + interval0.getDuration().getSeconds());
    System.out.println(task0.name + " " + task0.getDuration().getSeconds());
    System.out.println(root.name + "-->" + root.getDuration().getSeconds());
    interval01.stopInterval();
    System.out.println("interval01 " + interval01.getDuration().getSeconds());
    System.out.println(task0.name + " " + task0.getDuration().getSeconds());
    System.out.println(root.name + "-->" + root.getDuration().getSeconds());
    interval1.stopInterval();
    System.out.println("interval1 " + interval1.getDuration().getSeconds());
    System.out.println(task1.name + " " + task1.getDuration().getSeconds());
    System.out.println(root.name + "-->" + root.getDuration().getSeconds());

    clock.stopClock();

    System.out.println(root.name + "-->" + root.getDuration().getSeconds());

    ToJsonFileVisitor jsonFileVisitor = new ToJsonFileVisitor();
    JSONObject object = root.accept(jsonFileVisitor);

    FileManager fileManager = new FileManager();
    fileManager.saveToJsonFile(object);


    FileManager fileManager = new FileManager();
    fileManager.readFromJsonFile();
    FromJsonVisitor fromJsonVisitor = new FromJsonVisitor();
    TaskManager taskManager = fromJsonVisitor.visit(fileManager);
     */




    PrinterVisitor printerVisitor = new PrinterVisitor();
    TaskManager root = new TaskManager("root");
    printerVisitor.print(root);
    Task transportation = new Task(root, "transportation");
    Task firstList = new Task(root,"first list");
    Task secondList = new Task(root, "second list");


    root.addChild(transportation);



    Interval transportationInterval = transportation.createInterval();
    sleep(4000);
    transportationInterval.stopInterval();
    sleep(2000);
    root.addChild(firstList);
    Interval firstListInterval =  firstList.createInterval();
    sleep(6000);
    root.addChild(secondList);
    Interval secondListInterval =  secondList.createInterval();
    sleep(4000);
    firstListInterval.stopInterval();
    sleep(2000);
    secondListInterval.stopInterval();
    sleep(2000);
    transportationInterval = transportation.createInterval();
    sleep(4000);
    transportationInterval.stopInterval();

    Clock.getInstance().stopClock();
    printerVisitor.stopPrinting();











  }


}
