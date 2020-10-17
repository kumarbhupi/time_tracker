import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer, Element{
  private final Task parentTask;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Duration duration;
  private boolean inProgress;
  private LocalDateTime progressTime; // Captura

  public Interval(Task task, LocalDateTime startTime){
    this.parentTask=task;
    this.startTime=startTime;
    this.progressTime=startTime;
    this.inProgress=true;
    this.duration = Duration.ZERO;
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
  public String getProgressTime(){return progressTime.toString();}
  private Duration updateDuration() {
    return Duration.between(progressTime, endTime);
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }
  public void setEndTime(LocalDateTime endTime){
    this.endTime=endTime;
  }

  public Task getParentTask() {
    return parentTask;
  }

  public String getStartTime() {
    return startTime.toString();
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
    }else{

      progressTime=(LocalDateTime) time;
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

  public JSONObject getJSON(){
    JSONObject object = new JSONObject();
    object.put("parentTask", parentTask.getName());
    object.put("startTime", startTime.toString());
    object.put("endTime", endTime.toString());
    object.put("duration", duration.getSeconds());
    object.put("inProgress", inProgress);
    return object;
  }

  public void setDuration(Duration duration) {
    this.duration=duration;
  }

  public void setInProgress(boolean inProgress) {
    this.inProgress=inProgress;
  }

  public void setParentTask(Task task) {
  }


  @Override
  public JSONObject accept(VisitorRead v) {
    return null;
  }

  @Override
  public JSONObject accept(Visitor v) {
    return null;
  }

  @Override
  public void acceptPrinter(PrintVisitor v) {
    v.print(this);
  }
}
