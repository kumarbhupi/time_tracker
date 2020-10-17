import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ToJsonFileVisitor implements Visitor {
  @Override
  public JSONObject visit(Tracker tracker) {
    if (tracker instanceof Task){
      return visit((Task) tracker);
    }else if (tracker instanceof TaskManager){
      return visit((TaskManager) tracker);
    }
    return null;
  }

  @Override
  public JSONObject visit(TaskManager taskManager) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("name", taskManager.getName());
    jsonObject.put("seconds", taskManager.getDuration().getSeconds());
    jsonObject.put("nano", taskManager.getDuration().getNano());
    List<Tracker> trackerList = taskManager.getTrackers();

    JSONArray jsonArray = new JSONArray();
    for (Tracker tracker: trackerList) {
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
    jsonObject.put("status", task.isStatus());
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
