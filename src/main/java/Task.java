import org.json.JSONArray;
import org.json.JSONObject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Task extends Tracker implements Element {

  private final TaskManager parentProject;
  private List<Interval> listIntervals;
  private boolean status;
  private LocalDateTime endTime; //TODO: Think better name

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
  public String getStartTimeToString(){
    if (parentProject.getStartTime() == null){
      parentProject.setStartTime(listIntervals.get(0).getStartTime());
    }
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
    LocalDateTime now = LocalDateTime.now();
    Clock clock = Clock.getInstance();
    Interval interval = new Interval(this, now);
    listIntervals.add(interval);
    clock.addObserver(interval);
    if (parentProject.getStartTime() == null){
      parentProject.setStartTime(now);
    }
    return interval;
  }

  public void endInterval(Interval interval){
    Clock clock = Clock.getInstance();
    duration = duration.plus(interval.getDuration());
    updateDuration(interval.getDuration());
    clock.deleteObserver(interval);
  }

  public void updateInterval(Interval interval){
    updateDuration(durationDifference(interval.getDuration(), duration));
    duration = duration.plus(durationDifference(interval.getDuration(), duration));
  }

  public Duration durationDifference(Duration firstDuration, Duration secondDuration){
    return firstDuration.minus(secondDuration);
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
    return null;
  }

  @Override
  public void print(VisitorPrint visitorPrint) {
    visitorPrint.print(this);
  }
}

