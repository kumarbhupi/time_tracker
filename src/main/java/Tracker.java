import org.json.JSONObject;

import java.time.Duration;

public abstract class Tracker {
  protected String name;
  protected Duration duration;

  public Tracker(String name){
    this.name = name;
    this.duration = Duration.ZERO;
  }


  public abstract Tracker getTracker();
  protected abstract void updateDuration(Duration durationToAdd);
  public String getName() {
    return this.name;
  }
  public abstract Duration getDuration();
  public abstract String getStartTimeToString();
  public abstract String getEndTimeToString();



}
