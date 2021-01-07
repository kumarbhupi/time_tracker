package core;

import org.json.JSONObject;
import visitor.*;

import java.time.Duration;
import java.time.LocalDateTime;

//This class is the component of the Composite pattern.
public abstract class Tracker implements Element {
  protected int id;
  protected String name;
  protected LocalDateTime startTime;
  protected LocalDateTime endTime;

  public Tracker(String name) {
    IdentifierGenerator identifierGenerator = IdentifierGenerator.getIdentifier();
    this.id = identifierGenerator.getId();
    this.name = name;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
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

  public abstract Tracker findActivityById(int id);


  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Tracker){
      return this.id == ((Tracker) obj).id;
    }else{
      return false;
    }
  }

  @Override
  public JSONObject accept(Visitor v) {
    return v.visit(this);
  }

  @Override
  public void print(VisitorPrint visitorPrint) {
    visitorPrint.print(this);
  }

  @Override
  public long calculateTotalTime(VisitorTotalTime visitorTotalTime) {
    return visitorTotalTime.calculateTime(this);
  }
}
