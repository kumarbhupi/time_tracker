package searcher;
import core.Tracker;

import java.util.ArrayList;
import java.util.List;

public class TagManager {
  protected ArrayList<Tags> tags;

  public TagManager(){
    this.tags = new ArrayList<>();
  }

  public void createTag(String tag){
    Tags newTag = new Tags(tag);
    tags.add(newTag);
  }

  public void addTracker(String tag, Tracker tracker){
    for (Tags value : tags) {
      Tags tagToAdd = value.getTag();
      String tagName = tagToAdd.getNameTag();
      if (tagName.equals(tag)) {
        tagToAdd.addTracker(tracker);
      }
    }
  }

  public void removeTracker(String tag, Tracker tracker){
    for (Tags value : tags) {
      Tags tagToRemove = value.getTag();
      String tagName = tagToRemove.getNameTag();
      if (tagName.equals(tag)) {
        tagToRemove.removeTracker(tracker);
      }
    }
  }


  public void searchTag(String tag){
    for (Tags value : tags) {
      Tags tagToSearch = value.getTag();
      String tagName = tagToSearch.getNameTag();
      if (tagName.equalsIgnoreCase(tag)) {
        List<Tracker> trackersToSearch = tagToSearch.getTrackers();
        for (Tracker toSearch : trackersToSearch) {
          System.out.println(toSearch.getName());
        }
      }
    }
  }
}