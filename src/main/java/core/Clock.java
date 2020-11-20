package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;



//This class implements Singleton pattern.
public class Clock extends Observable {
  private Observable observable;
  private final Timer clock;
  private static Clock uniqueInstance;
  private String data;
  static Logger logger = LoggerFactory.getLogger(Clock.class);

  private Clock() {
    observable = new Observable();
    clock = new Timer();
    startTick();
  }

  public static Clock getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new Clock();
    }
    return uniqueInstance;
  }

  //Creates a thread that executes every 2 seconds notifying to the observers.
  private void startTick() {
    data = "Clock is ticking";
    logger.info("{}", data);
    clock.schedule(new TimerTask() {
      @Override
      public void run() {
        setTime(LocalDateTime.now());
      }
    }, 0, 2000);
  }


  //As an observable alerts to each observers the current time.
  private void setTime(LocalDateTime time) {
    setChanged();
    notifyObservers(time);
  }

  public void stopClock() {
    clock.cancel();

  }

}
