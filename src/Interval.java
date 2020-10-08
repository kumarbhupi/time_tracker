import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Duration;
import java.time.LocalDateTime;

public class Interval extends Thread implements PropertyChangeListener {
  private Task parentTask;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Duration duration;
  private boolean status;
  public Interval(LocalDateTime startTime){
    this.startTime=startTime;
    this.status=false;
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
    this.status=true;
  }



  public void stopInterval(){
    currentThread().interrupt();
    parentTask.endInterval(this);
  }


  @Override
  public void run() {
    super.run();
    System.out.println("bernat us informa de l'hora d'inici: "+startTime.toString());

    while (true){
      if(status) {
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
