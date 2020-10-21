import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FromJsonVisitor implements VisitorRead {


  @Override
  public TaskManager visit(FileManager fileManager) {
    JSONObject object = fileManager.getJsonObjectRead();
    TaskManager rootManager = new TaskManager(object.getString("name"));
    //rootManager.setDuration(durationConverter(object.getLong("seconds"), object.getLong("nano")));
    JSONArray jsonTrackers = object.getJSONArray("trackers");
    List<Tracker> trackerList = new ArrayList<>();

    for (int i = 0; i < jsonTrackers.length(); i++) {
      JSONObject jsonObject = jsonTrackers.getJSONObject(i);
      createTreeFromJsonObject(jsonObject, rootManager, trackerList);
    }
    rootManager.setTrackers(trackerList);
    return rootManager;
  }


  private void createTreeFromJsonObject(JSONObject jsonObject, TaskManager rootManager, List<Tracker> trackers ) {
    if (jsonObject.has("listIntervals")) {
      Task task = new Task(rootManager, jsonObject.getString("name"));
      task.setActive(jsonObject.getBoolean("active"));
      //task.setDuration(durationConverter(jsonObject.getLong("seconds"), jsonObject.getLong("nano")));

      JSONArray jsonArrayInterval = jsonObject.getJSONArray("listIntervals");
      List<Interval> intervalList = new ArrayList<>();

      for (int j = 0; j < jsonArrayInterval.length(); j++) {
        JSONObject jsonInterval = jsonArrayInterval.getJSONObject(j);
        LocalDateTime startTime = stringToLocalDateTime(jsonInterval.getString("startTime"));
        Interval interval = new Interval(task, startTime);
        interval.setEndTime(stringToLocalDateTime(jsonInterval.getString("endTime")));
        //interval.setDuration(durationConverter(jsonInterval.getLong("seconds"), jsonInterval.getLong("nano")));
        interval.setInProgress(jsonInterval.getBoolean("inProgress"));
        intervalList.add(interval);
      }
      task.setListIntervals(intervalList);
      trackers.add(task);
    } else if(jsonObject.has("trackers")){
      TaskManager taskManager = new TaskManager(rootManager, jsonObject.getString("name"));
      //taskManager.setDuration(durationConverter(jsonObject.getLong("seconds"), jsonObject.getLong("nano")));
      JSONArray jsonTrackers = jsonObject.getJSONArray("trackers");
      List<Tracker> trackerList = new ArrayList<>();
      for (int i = 0; i < jsonTrackers.length(); i++) {
        createTreeFromJsonObject(jsonTrackers.getJSONObject(i), taskManager, trackerList);
      }
      taskManager.setTrackers(trackerList);
      trackers.add(taskManager);
    }
  }

  private LocalDateTime stringToLocalDateTime(String stringLocalDateTime) {
    String[] stringSplit = stringLocalDateTime.split("T");
    String[] timeSplit = stringSplit[1].split(":");
    int seconds = Integer.parseInt(timeSplit[2].substring(0,2));
    int nano = (int) (Float.parseFloat(timeSplit[2].substring(2))*10e8);
    LocalDate localDate = LocalDate.parse(stringSplit[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    LocalTime localTime = LocalTime.of(Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]),seconds,nano);
    return LocalDateTime.of(localDate, localTime);
  }

  /*private Duration durationConverter(long seconds, long nano) {
    Duration duration = Duration.ofSeconds(seconds);
    duration = duration.plus(Duration.ofNanos(nano));
    return duration;
  }*/
}
