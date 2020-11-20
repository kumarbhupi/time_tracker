package visitor;

import core.TaskManager;
import org.json.JSONObject;

public interface Element { //Accepts visitor. It can access the code executed by visitor.
  TaskManager accept(VisitorRead v);

  JSONObject accept(Visitor v);

  void print(VisitorPrint visitorPrint);

  long calculateTotalTime(VisitorTotalTime visitorTotalTime);
}
