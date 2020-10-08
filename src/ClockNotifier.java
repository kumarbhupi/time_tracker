public class ClockNotifier {
  private Clock clockObservable;

  public ClockNotifier(Clock clock){
    this.clockObservable = clock;
  }


  public void addListener(Interval interval){
    clockObservable.addPropertyChangeListener(interval);
  }

  public void removeListener(Interval interval){
    clockObservable.removePropertyChangeListener(interval);
  }
}