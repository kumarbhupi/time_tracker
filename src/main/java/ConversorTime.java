import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ConversorTime {
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private LocalDateTime tempDateTime;
  private Duration duration;
  public LocalDateTime getStartTime(){
    return startTime;
  }
  public LocalDateTime getEndTime(){
    return endTime;
  }
  private Duration calculateDuration(LocalDateTime start,LocalDateTime end) {
    this.startTime=start;
    this.endTime=end;
    duration = Duration.between(start,end);
    //TODO: Acabar esto YA /Alberto
    /*tempDateTime = LocalDateTime.from( startTime );
    //extreiem anys,mesos,dias,horas,minuts,segons del tempDateTime
    long years= tempDateTime.until( endTime, ChronoUnit.YEARS );
    long months = tempDateTime.until( endTime, ChronoUnit.MONTHS );
    long days= tempDateTime.until( endTime, ChronoUnit.DAYS );
    long hours = tempDateTime.until( endTime, ChronoUnit.HOURS );
    long minutes = tempDateTime.until( endTime, ChronoUnit.MINUTES );
    long seconds = tempDateTime.until( endTime, ChronoUnit.SECONDS );*/

    return duration;
  }

}