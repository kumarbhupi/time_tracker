import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Task extends Tracker implements Element {

  private final TaskManager parentProject;
  private List<Interval> listIntervals;
  private boolean active;

  public Task(TaskManager parent, String name) {
    super(name);
    parentProject=parent;
    listIntervals = new ArrayList<Interval>();
    active = false;

  }


  public void setActive(boolean active) {
    this.active = active;
  }

  public List<Interval> getListIntervals() {
    return listIntervals;
  }

  public void setListIntervals(List<Interval> listIntervals) {
    this.listIntervals = listIntervals;
  }

  @Override
  public String getStartTimeToString(){
    return listIntervals.get(0).getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }

  @Override
  public String getEndTimeToString() {
    if (endTime!= null){
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
    parentProject.updateParentEndTime(endTime);
    parentProject.setActive(true);

  }

  @Override
  public Duration getDuration() {
    Duration duration = Duration.ZERO;
    for (Interval interval: listIntervals) {
      duration = duration.plus(Duration.between(interval.getStartTime(),interval.getEndTime()));
    }
    float millis = duration.toMillis();
    int rounded = Math.round(millis/1000);
    return Duration.ofSeconds(rounded);
    //return duration;
  }


  public void endInterval(Interval interval){
    System.out.println(name+ " stops");
    Clock clock = Clock.getInstance();
    clock.deleteObserver(interval);
    active = false;
    parentProject.setActive(false);
  }

  public boolean isActive() {
    return active;
  }

  public Interval createInterval() {
    System.out.println(name+" started");
    LocalDateTime now = LocalDateTime.now();
    Clock clock = Clock.getInstance();
    Interval interval = new Interval(this, now);
    listIntervals.add(interval);
    clock.addObserver(interval);
    if (parentProject.getStartTime() == null){
      parentProject.setStartTime(now);
    }
    if(startTime==null){
      startTime = now;
    }
    active = true;
    return interval;
  }


  public void intervalUpdated(LocalDateTime endTime){
    this.endTime = endTime;
    updateParentEndTime(endTime);
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
    parentProject.setEndTime(endTime);
    this.endTime = endTime;
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
    visitorPrint.print(this);
  }

  public void setActive(Boolean active) {
    this.active=active;
  }
}

