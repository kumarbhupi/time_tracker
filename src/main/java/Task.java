import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends Tracker{

  private final TaskManager parentProject;
  private List<Interval> listIntervals;
  private boolean status;

  public Task(TaskManager parent, String name) {
    super(name);
    parentProject=parent;
    listIntervals = new ArrayList<Interval>();
    status = true;
  }

  public void setDuration(Duration duration){
    this.duration = duration;
  }

  public List<Interval> getListIntervals() {
    return listIntervals;
  }

  public void setListIntervals(List<Interval> listIntervals) {
    this.listIntervals = listIntervals;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public void endTask(){
    status = false;
    for (Interval interval : listIntervals){
      if (interval.isInProgress()){
        interval.stopInterval();
      }
    }
  }

  public boolean isStatus() {
    return status;
  }

  @Override
  public Duration getDuration() {
    return duration;
  }

  @Override
  public Tracker getTracker() {
    return parentProject;
  }

  @Override
  protected void updateDuration(Duration durationToAdd) {
    parentProject.updateDuration(durationToAdd);
  }

  public Interval createInterval() {
    Clock clock = Clock.getInstance();
    Interval interval = new Interval(this, LocalDateTime.now());
    listIntervals.add(interval);
    clock.addObserver(interval);
    return interval;
  }

  public void endInterval(Interval interval){
    Clock clock = Clock.getInstance();
    duration = duration.plus(interval.getDuration());
    updateDuration(interval.getDuration());
    clock.deleteObserver(interval);
  }

  @Override
  public String toString() {
    return "{" +
        "parentProject=" + parentProject.getName() +
        ", listIntervals=" + listIntervals.toString() +
        ", status=" + status +
        ", name='" + name + '\'' +
        ", duration=" + duration +
        '}';
  }
  @Override
  public JSONObject getJSON(){
    JSONObject object = new JSONObject();
    object.put("name", name);
    object.put("duration", duration.getSeconds());
    object.put("parentProject", parentProject.getName());
    object.put("status", status);

    JSONArray intervals = new JSONArray();
    for (Interval inter : listIntervals) {
      intervals.put(inter.getJSON());
    }
    object.put("listIntervals", intervals);
    return object;
  }

  @Override
  public void fromJSON(JSONObject jsonObject) {

  }
}

