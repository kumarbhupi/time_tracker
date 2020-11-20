package core;

import org.json.JSONObject;
import visitor.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import core.Tag;

//This class is the component of the Composite pattern.
public abstract class Tracker implements Element {
  protected String name;
  protected LocalDateTime startTime;
  protected LocalDateTime endTime;
  protected ArrayList<Tag> tags = new ArrayList<Tag>();

  public Tracker(String name) {
    this.name = name;
  }

  protected abstract void updateParentEndTime(LocalDateTime endTime);

  public String getName() {
    return this.name;
  }

  //Provides the duration of all children, doesn't distinct between core.TaskManager or Tasks
  public abstract Duration getDuration();

  //Provides the time in following format: YYYY-MM-DD HH:mm:ss
  public abstract String getStartTimeToString();

  public abstract String getEndTimeToString();

  public abstract LocalDateTime getStartTime();

  public abstract LocalDateTime getEndTime();

  @Override
  public JSONObject accept(Visitor v) {
    return v.visit(this);
  }

  @Override
  public void print(VisitorPrint visitorPrint) {
    visitorPrint.print(this);
  }

  @Override
  public void calculateTotalTime(VisitorTotalTime visitorTotalTime) {
    visitorTotalTime.visit(this);
  }

  public void addTag(Tag newTag){
    tags.add(newTag);
  }

  public void removeTag(Tag tag){
    tags.remove(tag);
  }

  public ArrayList<Tag> getTags(){
    return this.tags;
  }


  public void searchTag(VisitorTag visitorTag, String tag){
    visitorTag.searchTag(this,tag);
  }
}
