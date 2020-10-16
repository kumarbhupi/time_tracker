import static java.lang.Thread.sleep;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    //TODO: Al eliminar/terminar un intervalo hay que quitarlo de escuchador==OBSERVER del clock
    Clock clock = new Clock();
    clock.startTick();
    ClockNotifier notify = new ClockNotifier(clock);

    TaskManager root= new TaskManager("main");

    //Primer Task
    Tracker task0 = root.createTrackers("first task", TrackerType.TASK);
    //TODO assegurar que al castejar no afecta res
    Interval interval0 = ((Task) task0).createInterval();
    sleep(1000);
    Interval interval01 = ((Task) task0).createInterval();

    notify.addListener(interval0);
    notify.addListener(interval01);


    //Segon Task1
    Tracker task1 = root.createTrackers("second task", TrackerType.TASK);
    Interval interval1 = ((Task) task1).createInterval();
    notify.addListener(interval1);

    sleep(5000);
    interval0.stopInterval();
    interval01.stopInterval();
    interval1.stopInterval();
    System.out.println("interval0 "+interval0.getDuration().getSeconds());
    System.out.println("interval01 "+interval01.getDuration().getSeconds());
    System.out.println(task0.name+" "+task0.getDuration().getSeconds());
    //System.out.println("interval1 "+interval1.getDuration().getSeconds());
    System.out.println(task1.name+" "+task1.getDuration().getSeconds());



    //TODO notify.removeListener
    notify.removeListener(interval0);
    notify.removeListener(interval01);
    notify.removeListener(interval1);

    clock.stopClock();
    System.out.println(root.name+"-->"+root.getDuration().getSeconds());

  }
}
