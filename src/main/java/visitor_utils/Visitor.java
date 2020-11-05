package visitor_utils;

import core.Interval;
import core.Task;
import core.TaskManager;
import core.Tracker;
import org.json.JSONObject;



public interface Visitor {
  JSONObject visit(Tracker tracker);

  JSONObject visit(TaskManager taskManager);

  JSONObject visit(Task task);

  JSONObject visit(Interval interval);
}


