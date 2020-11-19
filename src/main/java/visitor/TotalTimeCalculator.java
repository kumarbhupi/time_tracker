package visitor;

import core.Task;
import core.TaskManager;
import core.Tracker;

import java.time.LocalDateTime;

//TODO: Finish this class to calculate time spend between two periods.
public class TotalTimeCalculator implements VisitorTotalTime{
    LocalDateTime startInterval;
    LocalDateTime endInterval;

    public TotalTimeCalculator(LocalDateTime startInterval, LocalDateTime endInterval){
        this.startInterval = startInterval;
        this.endInterval = endInterval;
    }


    @Override
    public void visit(Tracker tracker) {
        assert startInterval != null && endInterval != null;
        tracker.calculateTotalTime(this);
    }

    @Override
    public void visit(TaskManager taskManager) {
        assert startInterval != null && endInterval != null;
        for (Tracker tracker:
             taskManager.getTrackers()) {
            visit(tracker);
        }
    }

    @Override
    public void visit(Task task) {
        assert startInterval != null && endInterval != null;
        LocalDateTime taskStartTime = task.getStartTime();
        LocalDateTime taskEndTime = task.getEndTime();

        if (startInterval.isAfter(taskStartTime) || startInterval.isEqual(taskStartTime) ){
            if (endInterval.isBefore(taskEndTime) || endInterval.isEqual(taskEndTime)){
                //LocalDateTime timeSpent = taskEndTime.minus(/);


            }

        }

    }
}
