import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Clock {
  PropertyChangeSupport support;
  Timer clock;
  LocalDateTime time;

  public Clock() {
    support = new PropertyChangeSupport(this);
    clock = new Timer();
  }

  private void startTick() {
    clock.schedule(new TimerTask() {
      @Override
      public void run() {
        setTime(LocalDateTime.now());
      }
    }, 0, 1000);
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    support.removePropertyChangeListener(listener);
  }

  private void setTime(LocalDateTime time) {
    System.out.println("me enviaron esto wey! --> " + time.toString());
    support.firePropertyChange("now", this.time, time);
    this.time = time;
  }

  public void stopClock() {
    clock.cancel();
  }

  public static void main(String[] args) {
    Clock clock = new Clock();
    clock.startTick();
  }



}
