import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

//TODO:Arreglar y mirar que todo este correcto /Alberto

public class Clock extends Observable {
  private Observable observable;
  private Timer clock;
  private LocalDateTime time;
  private static Clock uniqueInstance;

  private Clock() {
    observable = new Observable();
    clock = new Timer();
  }

  public static Clock getInstance(){
    if (uniqueInstance == null){
      uniqueInstance = new Clock();
    }
    return uniqueInstance;
  }

  public void startTick() {
    System.out.println("Clock is ticking");
    clock.schedule(new TimerTask() {
      @Override
      public void run() {
        setTime(LocalDateTime.now());
      }
    }, 0, 1000);
  }

  private void setTime(LocalDateTime time) {
    //System.out.println("Se ha enviado esto --> " + time.toString());
    this.time = time;
    setChanged();
    notifyObservers(time);
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
