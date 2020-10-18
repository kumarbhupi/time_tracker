import java.util.Timer;
import java.util.TimerTask;

public class PrinterVisitor implements VisitorPrint {
    final Timer timer = new Timer();

    @Override
    public void print(Tracker tracker) {
        if (tracker instanceof Task) {
            print((Task) tracker);
        } else if (tracker instanceof TaskManager) {
            print((TaskManager) tracker);
        }
    }

    @Override
    public void print(TaskManager taskManager) {

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Tracker tracker:taskManager.getTrackers()) {
                    print(tracker);
                }
                System.out.println("activity:    " + taskManager.getName() + "    " + taskManager.getStartTimeToString() + "    " + taskManager.getEndTimeToString() + "    " + taskManager.getDuration().getSeconds());
            }
        }, 0, 2000);
    }


    @Override
    public void print(Task task) {
        if (task.isStatus()){
            for (Interval interval: task.getListIntervals()) {
                print(interval);
            }
            System.out.println("activity:    " + task.getName() + "    " + task.getStartTimeToString() + "    " + task.getEndTimeToString() + "    " + task.getDuration().getSeconds());
        }
    }

    @Override
    public void print(Interval interval) {
        if (interval.isInProgress()){
            System.out.println("interval:            " + interval.getStartTimeToString() + "    " + interval.getEndTimeToString() + "    " + interval.getDuration().getSeconds());
        }
    }

    public void stopPrinting(){
        timer.cancel();

    }

}
