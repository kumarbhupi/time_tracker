import org.json.JSONObject;

public interface VisitorRead {
  TaskManager visit(JSONObject object);

}
