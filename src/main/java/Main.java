import static java.lang.Thread.sleep;

public class Main {

  public static void main(String[] args) throws InterruptedException {

    TaskManager root = new TaskManager(null,"root");


    Task transportations = new Task(root, "Transportation");
    root.addChild(transportations);

    PrinterVisitor printerVisitor = new PrinterVisitor(root);
    Interval transportationsInterval = transportations.createInterval();
    sleep(6000);
    transportationsInterval.stopInterval();

    TaskManager softwareDesign = new TaskManager(root, "Software Design");
    root.addChild(softwareDesign);

    TaskManager problems = new TaskManager(softwareDesign, "Problems");
    softwareDesign.addChild(problems);

    Task firstList = new Task(problems, "First List");
    problems.addChild(firstList);

    Task secondList = new Task(problems, "Second List");
    problems.addChild(secondList);

    sleep(2000);
    Interval firstListInterval = firstList.createInterval();
    sleep(6000);
    Interval secondListInterval = secondList.createInterval();

    sleep(4000);
    firstListInterval.stopInterval();
    sleep(2000);
    secondListInterval.stopInterval();
    sleep(2000);
    Interval transportationsSecondInterval = transportations.createInterval();
    sleep(4000);
    transportationsSecondInterval.stopInterval();

    printerVisitor.stopPrinting();
    Clock.getInstance().stopClock();
    ToJsonVisitor toJsonVisitor = new ToJsonVisitor();

    FileManager fileManager = new FileManager();
    fileManager.saveToJsonFile(toJsonVisitor.visit(root));
  }
  //TODO starttime y endtime no tiene que ser null
}
