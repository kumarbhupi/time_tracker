import java.time.Duration;
import java.util.List;

public class TaskManager extends Tracker{

  List<Tracker> trackers;


  public TaskManager(String name) {
    super(name);
  }

  @Override
  public Duration getDuration() {
    return duration;
  }

  @Override
  public Tracker getTracker() {
    return null;
  }

  public void createTrackers(){

  }
}
