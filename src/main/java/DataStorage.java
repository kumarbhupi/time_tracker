import org.json.JSONArray;

import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Thread.sleep;


public class DataStorage {
  public static final String JSON_SAVED_FILE_NAME = "json_data.json";

  public void saveData(Tracker toSave){

    JSONArray trackerListJson = new JSONArray();
    trackerListJson.put(toSave.getJSON());
    try(FileWriter file = new FileWriter(JSON_SAVED_FILE_NAME)){
      file.write(toSave.getJSON().toString());
      file.flush();
    }catch (IOException e){
      e.printStackTrace();
    }
  }



  public static void main(String[] args) throws InterruptedException {
    Clock clock = new Clock();
    clock.startTick();
    ClockNotifier clockNotifier = new ClockNotifier(clock);

    TaskManager tracker = new TaskManager("root");

    Task loremTask= (Task) tracker.createTrackers("Lorem task", TrackerType.TASK);
    Task loremTask1 = (Task) tracker.createTrackers("Lorem task2", TrackerType.TASK);

    Interval someInterval = loremTask.createInterval();
    Interval someInterval1 = loremTask.createInterval();

    clockNotifier.addListener(someInterval);
    clockNotifier.addListener(someInterval1);
    sleep(3000);
    someInterval.stopInterval();
    clockNotifier.removeListener(someInterval);

    sleep(5000);
    someInterval1.stopInterval();
    clockNotifier.removeListener(someInterval1);

    TaskManager otherProject =(TaskManager)tracker.createTrackers("Ipsum Project", TrackerType.PROJECT);




    clock.stopClock();
    DataStorage dataStorage = new DataStorage();
    dataStorage.saveData(tracker);

    JSONArray jsonArray = new JSONArray();

    /*try(FileReader reader = new FileReader(DataStorage.JSON_SAVED_FILE_NAME)){
      JSONObject object = (JSONObject) new JSONTokener(reader).nextValue();




      Map map = object.toMap();
      String query = object.getString("name");
      System.out.println(query);




    } catch (IOException e) {
      e.printStackTrace();
    }*/

  }

}
