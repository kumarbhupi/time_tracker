package visitor;

import core.Interval;
import core.Task;
import core.TaskManager;
import core.Tracker;

import java.time.Duration;
import java.time.LocalDateTime;

//This class adds the total time worked between two time intervals.
public class TotalTimeCalculator implements VisitorTotalTime {
  LocalDateTime startInterval;
  LocalDateTime endInterval;

  public TotalTimeCalculator(LocalDateTime startInterval, LocalDateTime endInterval) {
    this.startInterval = startInterval;
    this.endInterval = endInterval;
  }
  @Override
  public long calculateTime(Tracker tracker) {
    return tracker.calculateTotalTime(this);
  }

  @Override
  public long calculateTime(TaskManager taskManager) {
    assert startInterval != null && endInterval != null;
    long spendTime = 0;
    if (taskManager.getStartTime() != null && taskManager.getStartTime()!= null ){
      if (!checkIfIsOutArea(taskManager.getStartTime(), taskManager.getEndTime())) {
        for (Tracker tracker : taskManager.getTrackers()) {
          spendTime += calculateTime(tracker);
        }
      }
      return spendTime;
    }
    return spendTime;
  }

  @Override
  public long calculateTime(Task task) {
    assert startInterval != null && endInterval != null;
    long spendTime = 0;
    for (Interval interval: task.getListIntervals()) {
      spendTime += calculateTime(interval);
    }
    return spendTime;
  }

  @Override
  public long calculateTime(Interval interval) {
    assert startInterval != null && endInterval != null;
    if (checkIfIsOutArea(interval.getStartTime(), interval.getEndTime())) return 0;
    return getSpentTime(interval.getStartTime(), interval.getEndTime());
  }


  //Checks if in interval range (time).
  private boolean checkIfIsOutArea(LocalDateTime startInterval, LocalDateTime endInterval) {
    if (this.startInterval.isAfter(endInterval)) {
      return true;
    }
    return this.endInterval.isBefore(startInterval);
  }

  //Checks state of interval in time range. If entirely, como esta dentro del rango.
  private long getSpentTime(LocalDateTime startInterval, LocalDateTime endInterval) {
    if (this.startInterval.isAfter(startInterval)) {
      if (this.endInterval.isAfter(endInterval)) {
        return Duration.between(this.startInterval, endInterval).getSeconds();
      }
      if (this.endInterval.isBefore(endInterval)) {
        return Duration.between(this.startInterval, this.endInterval).getSeconds();
      }
    }
    if (this.startInterval.isBefore(startInterval)) {
      if (this.endInterval.isBefore(endInterval)) {
        return Duration.between(startInterval, this.endInterval).getSeconds();
      }
      if (this.endInterval.isAfter(endInterval)) {
        return Duration.between(startInterval, endInterval).getSeconds();
      }
    }
    return 0;
  }

}
