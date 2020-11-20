package searcher;

import core.Tracker;

import java.util.ArrayList;
import java.util.List;

public class Tag {
  private String tag;
  private ArrayList<Tracker> trackersTag = new ArrayList<>();

  public Tag(String tag){
    this.tag = tag;
  }

  public void removeTracker(Tracker tracker){
    trackersTag.remove(tracker);
  }

  public void addTracker(Tracker tracker){
    trackersTag.add(tracker);
  }

  public Tag getTag(){return this;}

  public String getNameTag(){return this.tag;}

  public List<Tracker> getTrackers(){return this.trackersTag;}
}