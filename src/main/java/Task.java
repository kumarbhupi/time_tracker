import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends Tracker {

  private final TaskManager parentProject;
  private List<Interval> listIntervals;
  private boolean status;
  private Duration taskDuration;

  public Task(TaskManager parent, String name) {
    super(name);
    parentProject=parent;
    listIntervals = new ArrayList<Interval>();
    status = true;
    taskDuration = Duration.ZERO;
  }

  public void endTask(){
    status = false;
    for (Interval interval : listIntervals){
      if (interval.isInProgress()){
        interval.stopInterval();
      }
    }
  }

  @Override
  public Duration getDuration() {
    return taskDuration;
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

    Interval interval = new Interval(this, LocalDateTime.now());
    listIntervals.add(interval);
    return interval;
  }

  public void endInterval(Interval interval){
    taskDuration = taskDuration.plus(interval.getDuration());
    updateDuration(taskDuration);
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

