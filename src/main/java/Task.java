import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Task extends Tracker {

  private TaskManager parentProject;
  private List<Interval> listIntervals;
  private boolean status;

  public Task(TaskManager parent, String name) {
    super(name);
    parentProject=parent;
    listIntervals = new ArrayList<Interval>();
    status = true;
  }

  public void endTask(){
    status = false;
    for (Interval interval : listIntervals){
      if (interval.isInProgress()){
        interval.stopInterval();
      }
    }
  }

  @Override
  public Duration getDuration() {
    return duration;
  }

  @Override
  public Tracker getTracker() {
    return parentProject;
  }

  @Override
  protected void updateDuration(Duration durationToAdd) {
    parentProject.updateDuration(durationToAdd);
  }

  public Interval createInterval() {

    Interval interval = new Interval(this, LocalDateTime.now());
    listIntervals.add(interval);
    return interval;
  }

  public void endInterval(Interval interval){
    duration = duration.plus(interval.getDuration());
    updateDuration(duration);
  }
}

