import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Duration;
import java.time.LocalDateTime;

public class Interval extends Thread implements PropertyChangeListener {
  private Task parentTask;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Duration duration;
  private boolean inProgress;

  public Interval(LocalDateTime startTime){
    this.startTime=startTime;
    this.inProgress=false;
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

  private Duration updateDuration() {
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
    currentThread().interrupt();
    parentTask.endInterval(this);
  }


  @Override
  public void run() {
    super.run();
    System.out.println("bernat us informa de l'hora d'inici: "+startTime.toString());

    while (true){
      if(inProgress) {
        System.out.println("bernat us informa de l'hora: " + endTime.toString());
        System.out.println("Duració de la tasca: " + updateDuration().toSeconds());
        duration = updateDuration();
      }
      try {
        sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    this.setEndTime((LocalDateTime) evt.getNewValue());
  }


  public static void main(String[] args) {
    Clock clock = new Clock();
    clock.startTick();
    Interval interval=new Interval(LocalDateTime.now());
    clock.addPropertyChangeListener(interval);
    interval.run();
  }
}
