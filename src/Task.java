import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Task extends Tracker {


  private TaskManager project;
  private List<Interval> listIntervals;
  private boolean status;

  public Task(String name) {
    super(name);
  }

  public void endTask(){
    status = false;
    for (Interval interval : listIntervals){
      interval.stopInterval();
    }
  }

  @Override
  public Duration getDuration() {
    return duration;
  }

  @Override
  public Tracker getTracker() {
    return project;
  }


  public Interval createInterval() {
    Interval interval = new Interval(LocalDateTime.now());
    listIntervals.add(interval);
    return interval;
  }


  public void endInterval(Interval interval){
    duration = duration.plus(interval.getDuration());
  }
}
