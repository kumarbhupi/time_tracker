import org.json.JSONObject;

public interface Element { //Accepta el visitor. Podra acceder al codigo que ejecute el visitor.
  TaskManager accept(VisitorRead v); //accept = indica que elementos tiene que devolver.

  JSONObject accept(Visitor v);

  void print(VisitorPrint visitorPrint);
}
