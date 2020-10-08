public abstract class Tracker {
  protected String name;
  protected long duration;
  public abstract long getDuration();

  public abstract Tracker getTracker();

}
