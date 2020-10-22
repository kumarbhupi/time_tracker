import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {
  private Observable observable;
  private final Timer clock;
  private static Clock uniqueInstance;


  private Clock() {
    observable = new Observable();
    clock = new Timer();
    startTick();
  }

  /* getInstance is used to make sure we only instance the clock once. -> Singleton */
  public static Clock getInstance() {
    if (uniqueInstance == null) {
        uniqueInstance = new Clock();
    }
    return uniqueInstance; //uniqueInstance is clock type.
  }

  private void startTick() {
    System.out.println("Clock is ticking");
    clock.schedule(new TimerTask() {
      @Override
      public void run() {
        setTime(LocalDateTime.now());
      }
    }, 0, 2000);
  }


  /* This method keeps the time updated for all Tracker-Task-Interval type objects. */
  private void setTime(LocalDateTime time) {
    //System.out.println("Se ha enviado esto --> " + time.toString());

    setChanged();
    notifyObservers(time); //Notifies all of the observers (PrinterVisitor, Interval-Task-TaskManager) the actual time.
  }

  public void stopClock() {
    clock.cancel();
  }

}
