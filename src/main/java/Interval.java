import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer{
  private final Task parentTask;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Duration duration;
  private LocalDateTime progressTime;
  private boolean inProgress;

  public Interval(Task task, LocalDateTime startTime){
    this.parentTask=task;
    this.startTime=startTime;
    this.inProgress=true;
    this.duration = Duration.ZERO;
    this.progressTime = startTime;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public void setInProgress(boolean inProgress) {
    this.inProgress = inProgress;
  }

  public boolean isInProgress() {
    return inProgress;
  }

  public Duration getDuration(){
    return duration;
  }
  public LocalDateTime getEndTime(){
    return endTime;
  }

  public String getEndTimeToString(){
    return endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
  private Duration updateDuration() {
    return Duration.between(progressTime, endTime);
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(LocalDateTime endTime){
    parentTask.setEndTime(endTime);
    this.endTime=endTime;
  }

  public Task getParentTask() {
    return parentTask;
  }

  public String getStartTimeToString() {
    return startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void stopInterval(){
    inProgress = false;
    parentTask.endInterval(this);

  }

  @Override
  public void update(Observable observable, Object time) {
    setEndTime((LocalDateTime) time);
    if (inProgress){
      duration = updateDuration();
      parentTask.updateInterval(this);
    }else{
      progressTime = (LocalDateTime) time;
    }

  }

  @Override
  public String toString() {
    return "{" +
        "parentTask :" + parentTask.getName() +
        ", startTime :" + startTime +
        ", endTime :" + endTime +
        ", duration :" + duration.getSeconds() +
        ", inProgress :" + inProgress +
        '}';
  }
}
