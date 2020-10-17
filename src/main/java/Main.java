import org.json.JSONObject;

import static java.lang.Thread.setDefaultUncaughtExceptionHandler;
import static java.lang.Thread.sleep;

public class Main {

  public static void main(String[] args) throws InterruptedException {

    //Clock clock = Clock.getInstance();

    //TaskManager root = new TaskManager("main");

    //Primer Task
    // Task task0 = new Task(root, "first task");
    //root.addChild(task0);
    //Tracker task0 = root.createTrackers("first task", TrackerType.TASK);

    //Interval interval0 = task0.createInterval();
    //sleep(5000);
    //Interval interval01 = task0.createInterval();

    //notify.addListener(interval0);
    //notify.addListener(interval01);


    //Segon Task1

    /*Task task1 = new Task(root, "second task");
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

    System.out.println(object.toString());*/



    /*****************************************TEST*****************************************/
    Clock clock = Clock.getInstance();
    PrintVisitor printer=new PrinterVisitor();
    TaskManager root1 = new TaskManager("root");
    /********* TASCA: Transportation **********/
    Task tasca = new Task(root1, "transportation");
    root1.addChild(tasca);
    Interval intervaltrans =tasca.createInterval();
    sleep(4000);
    intervaltrans.setInProgress(false);
    sleep(2000);
    /********* TASCA: First List **********/
    Task tasca2=new Task(root1,"first list");
    root1.addChild(tasca2);
    Interval intervalfirst=tasca2.createInterval();
    sleep(6000);
    /********* TASCA: Second List **********/
    Task tasca3=new Task(root1,"second list");
    root1.addChild(tasca3);
    Interval intervalsecond=tasca3.createInterval();
    sleep(4000);
    intervalfirst.stopInterval();
    sleep(2000);
    intervalsecond.stopInterval();
    sleep(2000);
    intervaltrans.setInProgress(true);
    sleep(4000);
    intervaltrans.stopInterval();


    System.out.println("              Initial date   "+"     Final date"+"     Duration");
    System.out.println("Start test");
    //Tracker elements
    Element elements[]={tasca, tasca2,  tasca3,  intervaltrans,  intervalfirst,  intervalsecond,root1};

    for (Element element : elements) {
        element.acceptPrinter(printer);

    }




  }


}
