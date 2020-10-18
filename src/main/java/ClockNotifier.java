import java.util.Observer;

public class ClockNotifier {
  private final Clock clockObservable;

  public ClockNotifier(Clock clock){
    this.clockObservable = clock;
  }

  public void addListener(Observer interval){
    clockObservable.addObserver(interval);
  }

  public void removeListener(Observer interval){
    clockObservable.deleteObserver(interval);
  }
}