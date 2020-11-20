package searcher;
import core.Tracker;

import java.util.ArrayList;
import java.util.List;

public class TagManager {
  protected ArrayList<Tag> tags;

  public TagManager(){
    this.tags = new ArrayList<>();
  }

  public void createTag(String tag){
    Tag newTag = new Tag(tag);
    tags.add(newTag);
  }

  public void addTracker(String tag, Tracker tracker){
    for (Tag value : tags) {
      Tag tagToAdd = value.getTag();
      String tagName = tagToAdd.getNameTag();
      if (tagName.equals(tag)) {
        tagToAdd.addTracker(tracker);
      }
    }
  }

  public void removeTracker(String tag, Tracker tracker){
    for (Tag value : tags) {
      Tag tagToRemove = value.getTag();
      String tagName = tagToRemove.getNameTag();
      if (tagName.equals(tag)) {
        tagToRemove.removeTracker(tracker);
      }
    }
  }


  public List<Tracker> searchTag(String tag) {
    //List<Tracker> TrackersFound = null;
    List<Tracker> trackersFound = new ArrayList<>();
    for (Tag value : tags) {
      Tag tagToSearch = value.getTag();
      String tagName = tagToSearch.getNameTag();
      if (tagName.equalsIgnoreCase(tag)) {
        trackersFound.addAll(tagToSearch.getTrackers());
      }
    }
    return trackersFound;
  }
}