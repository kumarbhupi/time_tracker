import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

//TODO:Arreglar y mirar que todo este correcto /Alberto

public class Clock extends Observable {
  Observable observable;
  Timer clock;
  LocalDateTime time;

  public Clock() {
    observable = new Observable();
    clock = new Timer();
  }

  public void startTick() {
    clock.schedule(new TimerTask() {
      @Override
      public void run() {
        setTime(LocalDateTime.now());
      }
    }, 0, 1000);
  }

  public void addObserver(Observer obs) {
    observable.addObserver(obs);
  }

  public void deleteObserver(Observer obs) {
    observable.deleteObserver(obs);
  }

  private void setTime(LocalDateTime time) {
    //System.out.println("me enviaron esto wey! --> " + time.toString());
    this.time = time;
    setChanged();
    notifyObservers();
  }

  public void stopClock() {
    clock.cancel();
  }

  public static void main(String[] args) throws InterruptedException {
    Clock clock = new Clock();
    clock.startTick();
    LocalDateTime firstTime = LocalDateTime.now();

    Thread.sleep(1000);

    LocalDateTime secondTime = LocalDateTime.now();
    Duration pta = Duration.between(firstTime, secondTime);
  }



}
