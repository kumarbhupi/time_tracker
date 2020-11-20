package core;

import org.json.JSONObject;
import visitor.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


//This class is the composite in the Composite pattern.
public class TaskManager extends Tracker implements Element {

  private TaskManager parentProject;
  private List<Tracker> trackers;
  private boolean active;

  public TaskManager(String name) {
    super(name);
    //Precondition
    assert (invariant()) : "Invariant has detected wrong value.";
    trackers = new ArrayList<>();
    assert (invariant()) : "Invariant has detected wrong value.";
  }

  public TaskManager(TaskManager parentProject, String name) {
    super(name);
    //Precondition
    assert (invariant()) : "Invariant has detected wrong value.";
    trackers = new ArrayList<>();
    this.parentProject = parentProject;
    assert (invariant()) : "Invariant has detected wrong value.";
  }

  //Implementation of invariant method. Checking variables are valid before and after a method's execution.
  private boolean invariant(){
    boolean isInvariant = false;

    if((this.name)!=null){
      isInvariant=true;
    }

    if ((this.parentProject!=null)){
      isInvariant=true;
    }

    if ((this.isActive())){
      isInvariant=true;
    }

    if ((this.endTime!=null)){
      isInvariant=true;
    }

    if ((this.startTime!=null)){
      isInvariant=true;
    }

    if(trackers.size()>0){
      isInvariant=true;
    }

    return isInvariant;
  }

  public boolean isActive() {
    return active;
  }

  //First method using invariant.
  public void setActive(boolean active) {
    //Precondition
    assert (invariant()) : "Invariant has detected wrong value."; //Runs method to check all different possible parameter values are correct.
    assert (!active) : "TaskManager not active"; //Specifying contract with variable passed by parameter.

    if (parentProject != null) {
      parentProject.setActive(active);
    }
    this.active = active;
    //Postcondition
    assert (invariant()) : "Invariant has detected wrong value."; //Checking all variables have valid values by the end of the method.
  }

  public TaskManager getParentProject() {
    return parentProject;
  }

  @Override
  protected void updateParentEndTime(LocalDateTime endTime) {
    //Precondition
    assert (invariant()) : "Invariant has detected wrong value.";
    assert(endTime!=null) : "Endtime has wrong value";

    if (parentProject == null) {
      this.endTime = endTime;
    } else {
      this.endTime = endTime;
      parentProject.updateParentEndTime(endTime);
    }
    assert (invariant()) : "Invariant has detected wrong value.";
  }


  public void setStartTime(LocalDateTime startTime) {
    //Precondition
    assert (invariant()) : "Invariant has detected wrong value.";
    assert(endTime!=null) : "Starttime has wrong value";

    this.startTime = startTime;
    if (parentProject != null) {
      if (parentProject.getStartTime() == null) {
        parentProject.setStartTime(startTime);
      }
    }
    //Postcondition
    assert (invariant()) : "Invariant has detected wrong value.";
  }

  public List<Tracker> getTrackers() {
    return trackers;
  }

  public void setTrackers(List<Tracker> trackers) {
    //Precondition
    assert (invariant()) : "Invariant has detected wrong value.";
    assert(trackers.size()>0) : "List of trackers is empty";
    this.trackers = trackers;
    //Postcondition
    assert (invariant()) : "Invariant has detected wrong value.";
  }


  @Override
  public Duration getDuration() {

    Duration duration = Duration.ZERO;
    for (Tracker tracker : trackers) {
      duration = duration.plus(tracker.getDuration());
    }
    return duration;
  }

  public void addChild(Tracker child) {
    //Precondition
    assert (invariant()) : "Invariant has detected wrong value.";
    assert(child!=null) : "Tracker is empty";
    trackers.add(child);
    //Postcondition
    assert (invariant()) : "Invariant has detected wrong value.";
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }


  @Override
  public String getStartTimeToString() {
    if (startTime != null) {
      return startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    return "null";
  }

  @Override
  public String getEndTimeToString() {
    if (endTime != null) {
      return endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    return "null";
  }


  public void setEndTime(LocalDateTime endTime) {
    //Precondition
    assert (invariant()) : "Invariant has detected wrong value.";
    assert(endTime!=null) : "Endtime has wrong value";
    if (parentProject != null) {
      parentProject.setEndTime(endTime);
    }
    this.endTime = endTime;
    //Postcondition
    assert (invariant()) : "Invariant has detected wrong value.";
  }

  public LocalDateTime getEndTime() {
    return this.endTime;
  }


  @Override
  public TaskManager accept(VisitorRead v) {
    return null;
  }

  @Override
  public JSONObject accept(Visitor v) {
    return v.visit(this);
  }

  @Override
  public void print(VisitorPrint visitorPrint) {
    //Precondition
    assert (invariant()) : "Invariant has detected wrong value.";
    assert(visitorPrint!=null): "visitorPrint is empty";

    visitorPrint.print(this);

    assert (invariant()) : "Invariant has detected wrong value.";
  }

  @Override
  public long calculateTotalTime(VisitorTotalTime visitorTotalTime) {
    return visitorTotalTime.calculateTime(this);
  }
}
