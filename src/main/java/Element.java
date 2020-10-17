import org.json.JSONObject;

public interface Element {
  JSONObject accept(VisitorRead v);

  JSONObject accept(Visitor v);

  void acceptPrinter(PrintVisitor v);
}
