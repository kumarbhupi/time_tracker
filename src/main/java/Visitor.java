import org.json.JSONObject;



public interface Visitor {
  JSONObject visit(Tracker tracker);

  JSONObject visit(TaskManager taskManager);

  JSONObject visit(Task task);

  JSONObject visit(Interval interval);
}


