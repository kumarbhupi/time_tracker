package core;

import org.json.JSONObject;
import visitor.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;


public class Interval implements Observer, Element {
  private final Task parentTask;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private boolean inProgress;

  public Interval(Task task, LocalDateTime startTime) {
    this.parentTask = task;
    this.startTime = startTime;
    this.inProgress = true;
  }

  public void setInProgress(boolean inProgress) {
    this.inProgress = inProgress;
  }

  public boolean isInProgress() {
    return inProgress;
  }


  //This duration is calculated by extracting endTime to startTime.
  float millis;
  public Duration getDuration() {
    if (startTime != null && endTime != null){
      millis= Duration.between(startTime, endTime).toMillis();
    }else {
      millis = 0;
    }
    int rounded = Math.round(millis / 1000);
    return Duration.ofSeconds(rounded);
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  //Provides the time in following format: YYYY-MM-DD HH:mm:ss
  public String getEndTimeToString() {
    if (endTime != null){
      return endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    return "null";
  }

  public String getStartTimeToString() {
    if (startTime != null){
      return startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    return "null";
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public Task getParentTask() {
    return parentTask;
  }

  public void stopInterval() {
    inProgress = false;
    parentTask.endInterval(this);
  }

  //As an observer, each time the clock ticks, this method updates the parents endTimes.
  @Override
  public void update(Observable observable, Object time) {
    setEndTime((LocalDateTime) time);
    if (inProgress) {
      parentTask.intervalUpdated(this.endTime);
    }
  }

  @Override
  public String toString() {
    return "{" +
        "parentTask :" + parentTask.getName() +
        ", startTime :" + startTime +
        ", endTime :" + endTime +
        ", duration :" + getDuration() +
        ", inProgress :" + inProgress +
        '}';
  }

  @Override
  public TaskManager accept(VisitorRead v) {
    return null;
  }

  @Override
  public JSONObject accept(Visitor v) {
    return v.visit(this);
  }

  @Override
  public void print(VisitorPrint visitorPrint) {
    visitorPrint.print(this);
  }

  @Override
  public long calculateTotalTime(VisitorTotalTime visitorTotalTime) {return visitorTotalTime.calculateTime(this);}
}
