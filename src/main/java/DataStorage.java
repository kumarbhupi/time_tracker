import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class DataStorage {
  JSONObject jsonObject;


  public static void main(String[] args) {
    TaskManager tracker = new TaskManager("Lorem");
    tracker.createTrackers("Lorem task", TrackerType.TASK);

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
