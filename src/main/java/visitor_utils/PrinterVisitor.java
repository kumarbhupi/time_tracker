package visitor_utils;

import core.*;

import java.util.ConcurrentModificationException;
import java.util.Observable;
import java.util.Observer;


public class PrinterVisitor implements VisitorPrint, Observer {
  private final TaskManager taskManager;
  private final String STRING_ACTIVITY = "Activity:";

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
        try {
          print(tracker);
        }catch (ConcurrentModificationException e){
          System.out.println(e.getMessage());
        }

      }
      try {
        System.out.printf("%s %30s %30s %30s %30s\n", STRING_ACTIVITY, taskManager.getName(), taskManager.getStartTimeToString(), taskManager.getEndTimeToString(), taskManager.getDuration().getSeconds());
      }catch (NullPointerException e){
        System.out.println(e.getMessage());
      }


    }
  }


  @Override
  public void print(Task task) {
    if (task.isActive()) {
      for (Interval interval : task.getListIntervals()) {
        print(interval);
      }
      try {
        System.out.printf("%s %30s %30s %30s %30s\n", STRING_ACTIVITY, task.getName(), task.getStartTimeToString(), task.getEndTimeToString(), task.getDuration().getSeconds());
      }catch (Exception e){
        System.out.println(e.getMessage());
      }

    }
  }

  @Override
  public void print(Interval interval) {
    if (interval.isInProgress()) {
      String STRING_INTERVAL = "Interval:";
      try {
        System.out.printf("%s  %60s %30s %30s\n", STRING_INTERVAL, interval.getStartTimeToString(), interval.getEndTimeToString(), interval.getDuration().getSeconds());
      }catch (NullPointerException e){
        System.out.println(e.getMessage());
      }


    }
  }

  public void stopPrinting() {
    Clock clock = Clock.getInstance();
    clock.deleteObserver(this);
  }

  //As an observer, prints every time clock ticks.
  @Override
  public void update(Observable observable, Object o) {
    try {
      print(taskManager);
    }catch (ConcurrentModificationException exception){
      System.out.println(exception.getMessage());
    }

  }
}
