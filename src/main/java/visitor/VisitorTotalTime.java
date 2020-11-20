package visitor;

import core.Interval;
import core.Task;
import core.TaskManager;
import core.Tracker;

public interface VisitorTotalTime {
  long calculateTime(Tracker tracker);

  long calculateTime(TaskManager taskManager);

  long calculateTime(Task task);

  long calculateTime(Interval interval);
}
