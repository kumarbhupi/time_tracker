import org.json.JSONObject;
import java.time.Duration;
import java.time.LocalDateTime;

//TODO TRADUCIR
/*Estamos implementando Composite pattern, esta classe serà el component*/

/*Utilizamos la interfaz Element para poder utilizar los metodos del visitor.*/
public abstract class Tracker implements Element {
  protected String name;
  protected LocalDateTime startTime;
  protected LocalDateTime endTime;

  public Tracker(String name){
    this.name = name;
  }


  protected abstract void updateParentEndTime(LocalDateTime endTime);
  public String getName() {
    return this.name;
  }
  public abstract Duration getDuration();
  public abstract String getStartTimeToString();
  public abstract String getEndTimeToString();
  public abstract LocalDateTime getStartTime();
  public abstract LocalDateTime getEndTime();


  @Override
  public JSONObject accept(Visitor v) {
    return v.visit(this);
  }

  @Override
  public void print(VisitorPrint visitorPrint) {
    visitorPrint.print(this);
  }
}
