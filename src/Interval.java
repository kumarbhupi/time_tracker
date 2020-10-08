import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;

public class Interval extends Thread implements PropertyChangeListener {
  private Task parentTask;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private long duration;


  public long getDuration(){
    return duration;
  }


  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  @Override
  public void run() {
    super.run();
    while (true){
      if(startTime!= null)
        System.out.println("bernat us informa de l'hora: "+startTime.toString());
      try {
        sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    this.setStartTime((LocalDateTime) evt.getNewValue());
  }

  public void startInterval(){

  }

  public static void main(String[] args) {
    Clock clock = new Clock();
    clock.startTick();
    Interval interval=new Interval();
    clock.addPropertyChangeListener(interval);
    interval.run();
  }
}
