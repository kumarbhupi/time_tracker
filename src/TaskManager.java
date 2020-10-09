import java.time.Duration;
import java.util.List;
import java.lang.String;

public class TaskManager extends Tracker{

  public TaskManager(String name) { super(name);}

  @Override
  public Duration getDuration() { return Duration.ZERO; }

  @Override
  public Tracker getTracker() {
    return null;
  }


  }
