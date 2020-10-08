public class TaskManager extends Tracker{


  public TaskManager(String name) {
    super(name);
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
