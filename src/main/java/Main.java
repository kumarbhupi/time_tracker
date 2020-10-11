import static java.lang.Thread.sleep;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    //TODO: Al eliminar/terminar un intervalo hay que quitarlo de escuchador==OBSERVER del clock
    Clock clock = new Clock();
    clock.startTick();
    ClockNotifier notify = new ClockNotifier(clock);
    TaskManager root= new TaskManager("main");

    //Primer Task
    Tracker task0 = root.createTrackers("name", TrackerType.TASK);
    //TODO assegurar que al castejar no afecta res
    Interval interval0 = ((Task) task0).createInterval();
    Interval interval01 = ((Task) task0).createInterval();
    notify.addListener(interval0);
    notify.addListener(interval01);

    //Segon Task1
    Tracker task1 = root.createTrackers("name", TrackerType.TASK);
    Interval interval1 = ((Task) task1).createInterval();
    notify.addListener(interval1);

    sleep(5000);
    interval0.stopInterval();
    interval01.stopInterval();
    interval1.stopInterval();

    //TODO notify.removeListener
    notify.removeListener(interval0);
    notify.removeListener(interval01);
    notify.removeListener(interval1);

    clock.stopClock();
    System.out.println(root.getDuration().getSeconds());




  }
}
