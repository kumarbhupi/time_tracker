import org.json.JSONObject;

public interface Element { //Accepts visitor. It can access the code executed by visitor.
  TaskManager accept(VisitorRead v); //Accept = indicates which elements it has to return. From JSONObject to TaskManager.

  JSONObject accept(Visitor v); //Passes from any element to JSONObject.

  void print(VisitorPrint visitorPrint);

}
