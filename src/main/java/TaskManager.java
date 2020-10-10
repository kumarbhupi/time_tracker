import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

enum TrackerType {
  TASK,
  PROJECT
}
public class TaskManager extends Tracker{

  List<Tracker> trackers;
  private TaskManager parentProject;

  //TODO LListat de TaskManagers+Tasks
  //TODO Variable per diferenciar TaskManager de Task
  //TODO Recuperar la duració TOTAL del conjunt de Tasks+TaskManagers


  public TaskManager(String name) {
    super(name);
    trackers = new ArrayList<Tracker>();
  }

  @Override
  public Duration getDuration() {
    return duration;
  }

  @Override
  public Tracker getTracker() {
    return this;
  }

  @Override
  protected void updateDuration(Duration durationToAdd) {
    if (parentProject == null){
      this.duration = this.duration.plus(durationToAdd);
    }else {
      parentProject.updateDuration(duration);
    }
  }

  public Tracker createTrackers(String name, TrackerType type){
    switch (type){
      case TASK:
        Tracker task = new Task(this, name);
        trackers.add(task);
        return task;
      case PROJECT:
        Tracker project = new TaskManager(name);
        trackers.add(project);
        return project;
      default:
        break;
    }
    return null;
  }
}
