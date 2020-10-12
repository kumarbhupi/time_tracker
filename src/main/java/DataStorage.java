import org.json.JSONArray;

import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Thread.sleep;


public class DataStorage {
  public static final String JSON_SAVED_FILE_NAME = "json_data.txt";

  public void saveData(Tracker toSave){

    JSONArray trackerListJson = new JSONArray();
    trackerListJson.put(toSave.getJSON());
    try(FileWriter file = new FileWriter(JSON_SAVED_FILE_NAME)){
      file.write(trackerListJson.toString());
      file.flush();
    }catch (IOException e){
      e.printStackTrace();
    }
  }



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










  }

}
