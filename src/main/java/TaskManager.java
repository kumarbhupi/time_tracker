import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class TaskManager extends Tracker implements Element {


    private TaskManager parentProject;

    private List<Tracker> trackers;

    private LocalDateTime startTime;//TODO: This two fields should be named batter.

    private LocalDateTime endTime; //TODO: <-- As well

    public TaskManager(String name) {
        super(name);
        trackers = new ArrayList<Tracker>();
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }


    public List<Tracker> getTrackers() {
        return trackers;
    }

    public TaskManager(TaskManager parentProject, String name) {
        super(name);
        trackers = new ArrayList<Tracker>();
        this.parentProject = parentProject;
    }

    public void setTrackers(List<Tracker> trackers) {
        this.trackers = trackers;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }


    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public String getStartTimeToString() {
        if (startTime != null){
            return startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return "null";
    }


    public void setEndTime(LocalDateTime endTime) {
        if (parentProject!= null){
            parentProject.setEndTime(endTime);
        }
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime(){
        return this.endTime;
    }

    @Override
    public String getEndTimeToString() {
        if (endTime != null){
            return endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return "null";
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
    public TaskManager accept(VisitorRead v) {
        //Not implemented
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
