import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

import static java.lang.Thread.sleep;

//TODO NO UTILIZAR PropertyChangeListener
public class Interval implements Observer {
  private Task parentTask;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Duration duration;
  private boolean inProgress;

  public Interval(LocalDateTime startTime){
    this.startTime=startTime;
    this.inProgress=false;
  }
//TODO Cridar al pare per que actualitzi
  public boolean isInProgress() {
    return inProgress;
  }

  public Duration getDuration(){
    return duration;
  }
  public LocalDateTime getEndTime(){
    return endTime;
  }

  private Duration updateDuration() {
    //System.out.println("Yoo he actualizado xd");
    return Duration.between(startTime, endTime);
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }
  public void setEndTime(LocalDateTime endTime){
    this.endTime=endTime;
  }

  public void stopInterval(){
    inProgress = false;
    parentTask.endInterval(this);
  }


  @Override
  public void update(Observable observable, Object time) {
    System.out.println("Funciona?");
    setEndTime((LocalDateTime) time);
    duration = updateDuration();

  }

  @Override
  public String toString() {
    return "Interval{" +
        "parentTask=" + parentTask +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", duration=" + duration +
        ", inProgress=" + inProgress +
        '}';
  }

  //TODO : USANDO observable , el updateduration() ira aqui
  public static void main(String[] args) throws InterruptedException {
    Clock clock = new Clock();
    clock.startTick();

    Interval interval=new Interval(LocalDateTime.now());
    clock.addObserver(interval);
    System.out.println("Clock count observers -->"+clock.countObservers());

  }


}
