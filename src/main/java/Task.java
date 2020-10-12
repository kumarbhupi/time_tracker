import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Task extends Tracker {

  private final TaskManager parentProject;
  private List<Interval> listIntervals;
  private boolean status;

  public Task(TaskManager parent, String name) {
    super(name);
    parentProject=parent;
    listIntervals = new ArrayList<Interval>();
    status = true;
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

    Interval interval = new Interval(this, LocalDateTime.now());
    listIntervals.add(interval);
    return interval;
  }

  public void endInterval(Interval interval){
    duration = duration.plus(interval.getDuration());
    updateDuration(duration);
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

  public void fromJSON(JSONObject jsonObject) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    LocalDateTime optionalDateTime;
    this.duration=Duration.ofSeconds(jsonObject.getInt("duration"));
    this.parentProject = (TaskManager) jsonObject.get("parentProject");
    JSONArray jsonArray =new JSONArray(jsonObject.getJSONArray("listIntervals"));
    for (int i = 0;i < jsonArray.length(); i++) {
      Interval currentInterval=new Interval();
      JSONObject interval=jsonArray.getJSONObject(i);
      currentInterval.setDuration(Duration.ofSeconds(interval.getLong("duration")));
      currentInterval.setInProgress(interval.getBoolean("inProgress"));
      currentInterval.setParentTask(this);
      currentInterval.setStartTime(interval.getString("startTime"));
      interval.getString((Format) jsonObject.getString("startTime"));

      this.listIntervals.add(interval);
    }
    this.listIntervals(interval);
  }
}

