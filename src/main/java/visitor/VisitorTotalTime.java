package visitor;

import core.Task;
import core.TaskManager;
import core.Tracker;

public interface VisitorTotalTime {
    void  visit(Tracker tracker);
    void  visit(TaskManager taskManager);
    void  visit(Task task);
}
