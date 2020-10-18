import org.json.JSONObject;

public interface Element {
  TaskManager accept(VisitorRead v);

  JSONObject accept(Visitor v);

  void print(VisitorPrint visitorPrint);
}
