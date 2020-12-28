package core;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitor.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//This class is the leaf in the Composite pattern.
public class Task extends Tracker implements Element {

  private final TaskManager parentProject;
  private List<Interval> listIntervals;
  private boolean active;

  //Implementing Logger class to print messages and saving them in register mode.
  static Logger logger= LoggerFactory.getLogger(Task.class);
  public Task(TaskManager parent, String name) {
    super(name);
    //Precondition
    assert (invariant()) : "Invariant has detected wrong value.";
    parentProject = parent;
    listIntervals = new ArrayList<>();
    active = false;
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

    if(listIntervals.size()>0){
      isInvariant=true;
    }

    return isInvariant;
  }

  //First method using invariant.
  public void setActive(boolean active) {
    //Precondition
    assert (invariant()) : "Invariant has detected wrong value."; //Runs method to check all different possible parameter values are correct.
    assert (!active): "Is not active"; //Specifying contract with variable passed by parameter.
    this.active = active;

    //Post condition
    assert (invariant()) : "Invariant has detected wrong value."; //Checking all variables have valid values by the end of the method.
  }

  public List<Interval> getListIntervals() {
    return listIntervals;
  }


  public void setListIntervals(List<Interval> listIntervals) {
    //Precondition
    assert (invariant()) : "Invariant has detected wrong value.";

    this.listIntervals = listIntervals;

    //post Condition
    assert (invariant()) : "Invariant has detected wrong value.";
  }

  @Override
  public String getStartTimeToString() {
    if (!listIntervals.isEmpty()){
      return listIntervals.get(0).getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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

  @Override
  public LocalDateTime getStartTime() {
    return this.startTime;
  }

  @Override
  protected void updateParentEndTime(LocalDateTime endTime) {
    //PreCondition
    assert (invariant()) : "Invariant has detected wrong value.";

    parentProject.updateParentEndTime(endTime);
    parentProject.setActive(true);

    //PostCondition
    assert (invariant()) : "Invariant has detected wrong value.";
    assert(parentProject.getDuration() != null) : "Parent Project has wrong value of duration";
  }


  @Override
  public Duration getDuration() {
    Duration duration = Duration.ZERO;
    for (Interval interval : listIntervals) {
      if (interval.getStartTime() != null && interval.getEndTime() != null){
        duration = duration.plus(Duration.between(interval.getStartTime(), interval.getEndTime()));
      }else{
        duration = duration.plus(Duration.ZERO);
      }
    }
    float millis = duration.toMillis();
    int rounded = Math.round(millis / 1000);
    return Duration.ofSeconds(rounded);
  }

  public void endInterval(){
    logger.info(name + " stops");
    assert (invariant()) : "Invariant has detected wrong value.";
    for (Interval interval: this.listIntervals) {
      if (interval.isInProgress())
        interval.stopInterval();
    }

  }

  public void endInterval(Interval interval) {
    //Changed system out for a Logger type information.
    logger.info(name + " stops");
    //PreCondition
    assert (invariant()) : "Invariant has detected wrong value.";
    assert(interval!=null) : "Interval has wrong value.";
    assert(interval.isInProgress()) :"Interval is not in progress.";
    Clock clock = Clock.getInstance();
    clock.deleteObserver(interval);
    active = false;
    parentProject.setActive(false);

    //PostCondition
    assert (invariant()) : "Invariant has detected wrong value.";
    assert(!interval.isInProgress()) : "Interval is not in progress.";
  }

  public boolean isActive() {
    return active;
  }


  public Interval createInterval() {

    logger.info(name + " started");
    LocalDateTime now = LocalDateTime.now();
    Clock clock = Clock.getInstance();
    Interval interval = new Interval(this, now);
    listIntervals.add(interval);
    clock.addObserver(interval);
    if (parentProject.getStartTime() == null) {
      parentProject.setStartTime(now);
    }
    if (startTime == null) {
      startTime = now;
    }
    active = true;
    return interval;
  }



  public void intervalUpdated(LocalDateTime endTime) {
    //precondition
    assert (invariant()) : "Invariant has detected wrong value.";

    this.endTime = endTime;
    updateParentEndTime(endTime);

    //postCondition
    assert (invariant()) : "Invariant has detected wrong value.";
  }

  @Override
  public String toString() {
    return "{" +
        "parentProject=" + parentProject.getName() +
        ", listIntervals=" + listIntervals.toString() +
        ", name='" + name + '\'' +
        ", duration=" + getDuration() +
        '}';
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  @Override
  public Tracker findActivityById(int id) {
    if (this.id == id){
      return this;
    }
    return null;
  }

  public void setEndTime(LocalDateTime endTime) {
    //Precondition
    assert (invariant()) : "Invariant has detected wrong value.";

    parentProject.setEndTime(endTime);
    this.endTime = endTime;

    //Postcondition
    assert (invariant()) : "Invariant has detected wrong value.";
  }

  public void setStartTime(LocalDateTime startTime) {
    assert (invariant()) : "Invariant has detected wrong value.";
    this.startTime = startTime;
    assert (invariant()) : "Invariant has detected wrong value.";
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
    assert(visitorPrint!=null) :"Visitor print is empty.";

    visitorPrint.print(this);

    //PostCondition
    assert (invariant()) : "Invariant has detected wrong value.";
  }

  @Override
  public long calculateTotalTime(VisitorTotalTime visitorTotalTime) {
    return visitorTotalTime.calculateTime(this);
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
}

