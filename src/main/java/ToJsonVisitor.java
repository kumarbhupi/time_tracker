import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ToJsonVisitor implements Visitor {     //passa els objectes que hi han a JSON per guardar
  //TODO: S'ha canviat el instanceof per un accept.

  @Override
  public JSONObject visit(Tracker tracker) {
    return tracker.accept(this);
  }

  @Override
  public JSONObject visit(TaskManager taskManager) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("name", taskManager.getName());
    jsonObject.put("seconds", taskManager.getDuration().getSeconds());
    jsonObject.put("nano", taskManager.getDuration().getNano());
    List<Tracker> trackerList = taskManager.getTrackers();

    JSONArray jsonArray = new JSONArray();
    for (Tracker tracker : trackerList) {
      jsonArray.put(visit(tracker));
    }
    jsonObject.put("trackers", jsonArray);
    return jsonObject;
  }

  @Override
  public JSONObject visit(Task task) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("name", task.name);
    jsonObject.put("seconds", task.getDuration().getSeconds());
    jsonObject.put("nano", task.getDuration().getNano());
    jsonObject.put("active", task.isActive());
    List<Interval> intervalList = task.getListIntervals();
    JSONArray jsonArray = new JSONArray();

    for (Interval interval : intervalList) {
      jsonArray.put(visit(interval));
    }
    jsonObject.put("listIntervals", jsonArray);
    return jsonObject;
  }

  @Override
  public JSONObject visit(Interval interval) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("startTime", interval.getStartTime());
    jsonObject.put("endTime", interval.getEndTime());
    jsonObject.put("seconds", interval.getDuration().getSeconds());
    jsonObject.put("nano", interval.getDuration().getNano());
    jsonObject.put("inProgress", interval.isInProgress());
    return jsonObject;
  }

}
