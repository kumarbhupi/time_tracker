import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class DataStorage {
  JSONObject jsonObject;


  public static void main(String[] args) throws InterruptedException {
    Clock clock = new Clock();
    clock.startTick();
    ClockNotifier clockNotifier = new ClockNotifier(clock);

    TaskManager tracker = new TaskManager("Lorem");

    tracker.createTrackers("Lorem task", TrackerType.TASK);
    Task loremTask = (Task) tracker.createTrackers("Lorem task2", TrackerType.TASK);
    Interval someInterval = loremTask.createInterval();
    clockNotifier.addListener(someInterval);

    sleep(5000);

    someInterval.stopInterval();
    clockNotifier.removeListener(someInterval);

    tracker.createTrackers("Ipsum Project", TrackerType.PROJECT);



    JSONArray trackerListJson = new JSONArray();
    trackerListJson.put(tracker.getJSON());


    try(FileWriter file = new FileWriter("test.json")){
      file.write(trackerListJson.toString());
      file.flush();
    }catch (IOException e){
      e.printStackTrace();
    }


  }

}
