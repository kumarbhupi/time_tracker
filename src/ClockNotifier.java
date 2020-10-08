import java.beans.PropertyChangeListener;

public class ClockNotifier {
  private final Clock clockObservable;

  public ClockNotifier(Clock clock){
    this.clockObservable = clock;
  }


  public void addListener(PropertyChangeListener interval){
    clockObservable.addPropertyChangeListener(interval);
  }

  public void removeListener(PropertyChangeListener interval){
    clockObservable.removePropertyChangeListener(interval);
  }
}