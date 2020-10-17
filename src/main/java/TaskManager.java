import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

enum TrackerType {
  TASK,
  PROJECT
}

public class TaskManager extends Tracker implements Element {


  private TaskManager parentProject;

  private List<Tracker> trackers;


  //TODO LListat de TaskManagers+Tasks
  //TODO Variable per diferenciar TaskManager de Task
  //TODO Recuperar la duració TOTAL del conjunt de Tasks+TaskManagers


  public TaskManager(String name) {
    super(name);
    trackers = new ArrayList<Tracker>();

  }

  public List<Tracker> getTrackers() {
    return trackers;
  }

  public TaskManager(TaskManager parentProject, String name) {
    super(name);
    trackers = new ArrayList<Tracker>();
    this.parentProject = parentProject;
  }

  @Override
  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  @Override
  public Tracker getTracker() {
    return this;
  }

  @Override
  protected void updateDuration(Duration durationToAdd) {
    if (parentProject == null) {
      duration = duration.plus(durationToAdd);

    } else {
      parentProject.updateDuration(durationToAdd);
    }
  }

  public void addChild(Tracker child) {
    trackers.add(child);
  }

  @Override
  public JSONObject getJSON() {
    JSONObject object = new JSONObject();
    object.put("name", name);
    object.put("duration", duration.getSeconds());
    if (parentProject != null) {
      object.put("parentProject", parentProject.getName());
    }
    JSONArray trackersArray = new JSONArray();
    for (Tracker tracker : trackers) {
      trackersArray.put(tracker.getJSON());
    }
    object.put("trackers", trackersArray);
    return object;
  }

  @Override
  public void fromJSON(JSONObject jsonObject) {
    this.name = jsonObject.getString("name");
    this.duration = Duration.ofSeconds(jsonObject.getLong("duration"));
    JSONArray jsonArray = jsonObject.getJSONArray("trackers");
    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject eachTrack = jsonArray.getJSONObject(i);

    }
  }

  @Override
  public JSONObject accept(VisitorRead v) {
    return null;
  }

  @Override
  public JSONObject accept(Visitor v) {
    return v.visit(this);
  }
}
