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


//This class is the composite in the Composite pattern.
public class TaskManager extends Tracker implements Element {

  private TaskManager parentProject;
  private List<Tracker> trackers;
  private boolean active;

  public TaskManager(String name) {
    super(name);
    trackers = new ArrayList<>();
  }

  public TaskManager(TaskManager parentProject, String name) {
    super(name);
    trackers = new ArrayList<>();
    this.parentProject = parentProject;
  }

  public boolean isActive() {
    return active;
  }

  //
  public void setActive(boolean active) {
    if (parentProject != null) {
      parentProject.setActive(active);
    }
    this.active = active;
  }

  public TaskManager getParentProject() {
    return parentProject;
  }

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
    if (parentProject != null) {
      if (parentProject.getStartTime() == null) {
        parentProject.setStartTime(startTime);
      }
    }
  }

  public List<Tracker> getTrackers() {
    return trackers;
  }

  public void setTrackers(List<Tracker> trackers) {
    this.trackers = trackers;
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
    trackers.add(child);
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
    if (parentProject != null) {
      parentProject.setEndTime(endTime);
    }
    this.endTime = endTime;
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
    visitorPrint.print(this);
  }
}
