package visitor_utils;

import core.Interval;
import core.Task;
import core.TaskManager;
import core.Tracker;

public interface VisitorPrint {
    void print(Tracker tracker);
    void print(Task task);
    void print(TaskManager taskManager);
    void print(Interval interval);

}
