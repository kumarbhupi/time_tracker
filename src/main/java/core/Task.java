package core;

import org.json.JSONObject;
import visitor_utils.Element;
import visitor_utils.Visitor;
import visitor_utils.VisitorPrint;
import visitor_utils.VisitorRead;

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

  public Task(TaskManager parent, String name) {
    super(name);
    //Precondition
    assert (invariant()) : "Invariant has detected wrong value.";
    parentProject = parent;
    listIntervals = new ArrayList<>();
    active = false;
    assert (invariant()) : "Invariant has detected wrong value.";
  }

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

    /*if ((this.endTime.isAfter(this.startTime))){
      isInvariant=true;
    }*/

    if ((this.startTime!=null)){
      isInvariant=true;
    }

    if(listIntervals.size()>0){
      isInvariant=true;
    }

    return isInvariant;
  }

  public void setActive(boolean active) {
    //Precondition
    assert (invariant()) : "Invariant has detected wrong value.";
    assert (!active): "Is not active";
    this.active = active;

    //Post condition
    assert (invariant()) : "Invariant has detected wrong value.";
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
    return listIntervals.get(0).getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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
    assert(parentProject.getDuration() != null);
  }


  @Override
  public Duration getDuration() {
    Duration duration = Duration.ZERO;
    for (Interval interval : listIntervals) {
      duration = duration.plus(Duration.between(interval.getStartTime(), interval.getEndTime()));
    }
    float millis = duration.toMillis();
    int rounded = Math.round(millis / 1000);
    return Duration.ofSeconds(rounded);
  }

  public void endInterval(Interval interval) {
    //PreCondition
    assert (invariant()) : "Invariant has detected wrong value.";
    assert(interval!=null);
    assert(interval.isInProgress());

    System.out.println(name + " stops");
    Clock clock = Clock.getInstance();
    clock.deleteObserver(interval);
    active = false;
    parentProject.setActive(false);

    //PostCondition
    assert (invariant()) : "Invariant has detected wrong value.";
    assert(!interval.isInProgress());
  }

  public boolean isActive() {
    return active;
  }


  public Interval createInterval() {
    System.out.println(name + " started");
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
    assert(visitorPrint!=null);

    visitorPrint.print(this);

    //PostCondition
    assert (invariant()) : "Invariant has detected wrong value.";
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
}

