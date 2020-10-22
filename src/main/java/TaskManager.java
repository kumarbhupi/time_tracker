import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/*Estamos implementando Composite pattern, esta classe serà el composite*/

/*Classe que permite gestionar proyectos y tascas*/
public class TaskManager extends Tracker implements Element {

  private TaskManager parentProject;
  private List<Tracker> trackers;
  private boolean active;

  public TaskManager(String name) {
    super(name);                          //super calls Tracker's constructor (bc of the extend).
    trackers = new ArrayList<Tracker>();
  }
  public TaskManager(TaskManager parentProject, String name) {
    super(name);
    trackers = new ArrayList<Tracker>();
    this.parentProject = parentProject;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    if (parentProject!= null){
      parentProject.setActive(active);
    }
    this.active = active;
  }

  public TaskManager getParentProject() {
    return parentProject;
  }

  /*A la hora de actualizar el tiempo, lo haremos de leaf a root (ascendiente), es un metodo sobreescrito
  * por lo tanto, las siguientes classes en la jerarquia tambien usarán este metodo*/
  @Override
  protected void updateParentEndTime(LocalDateTime endTime) {
    if (parentProject == null) {
      this.endTime = endTime;
    } else {
      this.endTime = endTime;
      parentProject.updateParentEndTime(endTime);
    }
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
    if (parentProject!= null ){
      if (parentProject.getStartTime() == null){
        parentProject.setStartTime(startTime);
      }
    }
  }

  public List<Tracker> getTrackers() {
    return trackers;
  } //List of projects and tasks (including intervals).


  public void setTrackers(List<Tracker> trackers) {
    this.trackers = trackers;
  }

  /*Asks from parent to leaf the duration of the current project*/
  @Override
  public Duration getDuration() {
    Duration duration = Duration.ZERO;
    for (Tracker tracker : trackers) {
      duration = duration.plus(tracker.getDuration());
    }
    return duration;
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


  public void setEndTime(LocalDateTime endTime) {
    if (parentProject != null) {
      parentProject.setEndTime(endTime);
    }
    this.endTime = endTime;
  }

  public LocalDateTime getEndTime() {
    return this.endTime;
  }

  @Override
  public String getEndTimeToString() {
    if (endTime != null) {
      return endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    return "null";
  }

  public void addChild(Tracker child) {
    trackers.add(child);
  } //Child is Tracker Type. (Projects can have projects or tasks).

  //Accepts to use visitor class.
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
    visitorPrint.print(this); //The this is what differenciates what type to print. (Task Manager-Task-Interval..)

  }


}
