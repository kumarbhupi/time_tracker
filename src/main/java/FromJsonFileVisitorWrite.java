import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FromJsonFileVisitorWrite implements VisitorRead {

  @Override
  public TaskManager visit(JSONObject object) {
    TaskManager rootManager = new TaskManager(object.getString("name"));
    rootManager.setDuration(durationConverter(object.getLong("seconds"),object.getLong("nano")));

    JSONArray jsonTrackers = object.getJSONArray("trackers");

    for (int i = 0; i < jsonTrackers.length(); i++) {
      JSONObject jsonObject = jsonTrackers.getJSONObject(i);
      if (jsonObject.has("listIntervals")){
        Task task = new Task(rootManager, jsonObject.getString("name"));
        task.setStatus(jsonObject.getBoolean("inProgress"));
        task.setDuration(durationConverter(object.getLong("seconds"),object.getLong("nano")));

        JSONArray jsonIntervarls = jsonObject.getJSONArray("listIntervals");

        for (int j = 0; j < jsonIntervarls.length() ; j++) {
          JSONObject jsonInterval = jsonIntervarls.getJSONObject(i);
          String startTimeString = jsonInterval.getString("startTime");
          String[] stringSplit = startTimeString.split("T");
          String dateSource = stringSplit[0];


          LocalDate date = LocalDate.parse(dateSource, DateTimeFormatter.ofPattern(""));

          //LocalDateTime startTime = new LocalDateTime();
          //Interval interval = new Interval(task,)

        }
      }


    }



    return null;
  }

  private Duration durationConverter(long seconds, long nano){
    Duration duration = Duration.ofSeconds(seconds);
    duration = duration.plus(Duration.ofNanos(nano));
    return duration;
  }
}
