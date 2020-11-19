package visitor;

import core.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Observable;
import java.util.Observer;


public class PrinterVisitor implements VisitorPrint, Observer {
  private final TaskManager taskManager;
  private final String STRING_ACTIVITY = "Activity:";
  static Logger logger= LoggerFactory.getLogger("visitor.PrinterVisitor");


  public PrinterVisitor(TaskManager taskManager) {
    Clock clock = Clock.getInstance();
    this.taskManager = taskManager;
    clock.addObserver(this);
  }

  @Override
  public void print(Tracker tracker) {
    tracker.print(this);
  }

  @Override
  public void print(TaskManager taskManager) {
    if (taskManager.isActive()) {
      for (Tracker tracker : taskManager.getTrackers()) {
        print(tracker);
      }
      logger.info("%s %30s %30s %30s %30s\n", STRING_ACTIVITY, taskManager.getName(), taskManager.getStartTimeToString(), taskManager.getEndTimeToString(), taskManager.getDuration().getSeconds());
      //System.out.printf("%s %30s %30s %30s %30s\n", STRING_ACTIVITY, taskManager.getName(), taskManager.getStartTimeToString(), taskManager.getEndTimeToString(), taskManager.getDuration().getSeconds());
      //LOGGER.warning("Hola");
    }
  }


  @Override
  public void print(Task task) {
    if (task.isActive()) {
      for (Interval interval : task.getListIntervals()) {
        print(interval);
      }
      logger.info("%s %30s %30s %30s %30s\n", STRING_ACTIVITY, task.getName(), task.getStartTimeToString(), task.getEndTimeToString(), task.getDuration().getSeconds());
      //System.out.printf("%s %30s %30s %30s %30s\n", STRING_ACTIVITY, task.getName(), task.getStartTimeToString(), task.getEndTimeToString(), task.getDuration().getSeconds());
      //LOGGER.warning("Hola");
    }
  }

  @Override
  public void print(Interval interval) {
    if (interval.isInProgress()) {
      String STRING_INTERVAL = "Interval:";
      logger.info("%s  %60s %30s %30s\n", STRING_INTERVAL, interval.getStartTimeToString(), interval.getEndTimeToString(), interval.getDuration().getSeconds());
      //System.out.printf("%s  %60s %30s %30s\n", STRING_INTERVAL, interval.getStartTimeToString(), interval.getEndTimeToString(), interval.getDuration().getSeconds());
      //LOGGER.warning("Hola");
    }
  }

  public void stopPrinting() {
    Clock clock = Clock.getInstance();
    clock.deleteObserver(this);
  }

  //As an observer, prints every time clock ticks.
  @Override
  public void update(Observable observable, Object o) {
    print(taskManager);
  }
}
