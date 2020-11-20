package searcher;

import core.Tracker;

import java.util.ArrayList;
import java.util.List;

public class Tags {
  private String tag;
  private ArrayList<Tracker> trackersTag = new ArrayList<Tracker>();

  public Tags(String tag){
    this.tag = tag;
  }

  public void removeTracker(Tracker tracker){
    this.tag = tag;
    trackersTag.remove(tracker);
  }

  public void addTracker(Tracker tracker){
    this.tag = tag;
    trackersTag.add(tracker);
  }

  public Tags getTag(){return this;}

  public String getNameTag(){return this.tag;}

  public List<Tracker> getTrackers(){return this.trackersTag;}
}