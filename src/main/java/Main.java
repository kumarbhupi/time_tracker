import org.json.JSONObject;

import org.json.JSONObject;

import java.util.Arrays;

import static java.lang.Thread.sleep;

public class Main {

  public static void main(String[] args) throws InterruptedException {

    /*TaskManager root = new TaskManager("root");
    PrinterVisitor printerVisitor = new PrinterVisitor(root);
    //printerVisitor.print(root);
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

    ToJsonVisitor toJsonVisitor = new ToJsonVisitor();
    JSONObject jsonObject = toJsonVisitor.visit(root);*/

    FileManager fileManager = new FileManager();
    //fileManager.saveToJsonFile(jsonObject);
    fileManager.readFromJsonFile();
    FromJsonVisitor fromJsonVisitor = new FromJsonVisitor();
    TaskManager root = fromJsonVisitor.visit(fileManager);
    int lol = 1;

    //TODO starttime y endtime no tiene que ser null
  }

}
