package visitor;

import core.Task;
import core.TaskManager;
import core.Tracker;

public interface VisitorTag {
    void searchTag(Tracker tracker, String tag);
    void searchTag(TaskManager taskManager, String tag);
}
