
import org.json.JSONObject;

public class PrinterVisitor implements PrintVisitor {
  @Override
  public void print(Task task) {
    System.out.println("activity:    "+ task.name +"   "+ task.getStartTime() +"  "+ task.getProgressTime()+ "  "+ task.getDuration().getSeconds());
  }

  @Override
  public void print(TaskManager taskManager) {
    //System.out.println("activity:    "+ taskManager.name +"   "+ taskManager.getStartTime() +"  "+ taskManager.getProgressTime()+ "  "+ taskManager.getDuration().getSeconds());
  }

  @Override
  public void print(Interval interval) {
    System.out.println("interval:      "+ interval.getStartTime() +"  "+ interval.getProgressTime()+ "  "+ interval.getDuration().getSeconds());
  }
}
