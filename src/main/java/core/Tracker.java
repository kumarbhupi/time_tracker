package core;

import org.json.JSONObject;
import visitor_utils.*;

import java.time.Duration;
import java.time.LocalDateTime;

//This class is the component of the Composite pattern.
public abstract class Tracker implements Element {
  protected String name;
  protected LocalDateTime startTime;
  protected LocalDateTime endTime;

  public Tracker(String name) {
    this.name = name;
  }

  protected abstract void updateParentEndTime(LocalDateTime endTime);

  public String getName() {
    return this.name;
  }

  //Provides the duration of all children, doesn't distinct between core.TaskManager or Tasks
  public abstract Duration getDuration();

  //Provides the time in following format: YYYY-MM-DD HH:mm:ss
  public abstract String getStartTimeToString();

  public abstract String getEndTimeToString();

  public abstract LocalDateTime getStartTime();

  public abstract LocalDateTime getEndTime();

  @Override
  public JSONObject accept(Visitor v) {
    return v.visit(this);
  }

  @Override
  public void print(VisitorPrint visitorPrint) {
    visitorPrint.print(this);
  }

  @Override
  public void calculateTotalTime(VisitorTotalTime visitorTotalTime) {
    visitorTotalTime.visit(this);
  }
}
