import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Task extends Tracker {


  private TaskManager project;
  private List<Interval> listIntervals;

  public void endTask(){

  }

  @Override
  public long getDuration() {
    return 0;
  }

  @Override
  public Tracker getTracker() {
    return null;
  }
}
