package visitor;

import core.Interval;
import core.Task;
import core.TaskManager;
import core.Tracker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

//This visitor class converts core.Tracker Objects to JsonObjects
public class ToJsonVisitor implements Visitor {

  @Override
  public JSONObject visit(Tracker tracker) {
    return tracker.accept(this);
  }

  @Override
  public JSONObject visit(TaskManager taskManager) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("id", taskManager.getId());
    jsonObject.put("name", taskManager.getName());
    jsonObject.put("startTime", taskManager.getStartTime());
    jsonObject.put("endTime", taskManager.getEndTime());
    jsonObject.put("class", "project");
    jsonObject.put("duration", taskManager.getDuration());
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
    jsonObject.put("id", task.getId());
    jsonObject.put("name", task.getName());
    jsonObject.put("startTime", task.getStartTime());
    jsonObject.put("endTime", task.getEndTime());
    jsonObject.put("active", task.isActive());
    jsonObject.put("class", "task");
    jsonObject.put("duration", task.getDuration());
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
    jsonObject.put("inProgress", interval.isInProgress());
    jsonObject.put("duration", interval.getDuration());
    return jsonObject;
  }

}
