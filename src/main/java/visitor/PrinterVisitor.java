package visitor;

import core.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Observable;
import java.util.Observer;



public class PrinterVisitor implements VisitorPrint, Observer {
  private final TaskManager taskManager;
  private final String STRING_ACTIVITY = "Activity:";
  static Logger logger= LoggerFactory.getLogger(PrinterVisitor.class);
  private String data;

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
      data=String.format("%s %30s %30s %30s %30s",STRING_ACTIVITY, taskManager.getName(), taskManager.getStartTimeToString(), taskManager.getEndTimeToString(), taskManager.getDuration().getSeconds());
      logger.info("{}",data);
    }
  }


  @Override
  public void print(Task task) {
    if (task.isActive()) {
      for (Interval interval : task.getListIntervals()) {
        print(interval);
      }
      data=String.format("%s %30s %30s %30s %30s",STRING_ACTIVITY, task.getName(), task.getStartTimeToString(), task.getEndTimeToString(), task.getDuration().getSeconds());
      logger.info("{}",data);

    }
  }

  @Override
  public void print(Interval interval) {
    if (interval.isInProgress()) {
      String STRING_INTERVAL = "Interval:";
      data=String.format("%s %60s %30s %30s",STRING_ACTIVITY, interval.getStartTimeToString(), interval.getEndTimeToString(), interval.getDuration().getSeconds());
      logger.info("{}",data);
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
