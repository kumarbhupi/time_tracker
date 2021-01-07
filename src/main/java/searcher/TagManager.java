package searcher;
import core.Tracker;

import java.util.ArrayList;
import java.util.List;

//This class manages all tags in the project.
public class TagManager {
  private static TagManager tagManager;
  public ArrayList<Tag> tags;

  public static TagManager getInstance(){
    if (tagManager == null){
      tagManager = new TagManager();
    }
    return tagManager;
  }
  private TagManager(){
    this.tags = new ArrayList<>();
  }

  public void createTag(String tag){
    Tag newTag = new Tag(tag);
    if (!this.tags.contains(newTag)){
      tags.add(newTag);
    }
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

  public List<String> searchTag(Tracker tracker){
    List<String> tagsList = new ArrayList<>();
    for(Tag tag : tags){
      if (tag.getTrackers().contains(tracker)){
        tagsList.add(tag.getTag().getNameTag());
      }
    }
    return tagsList;
  }

  //Looks for tag passed through the parameter and returns all associated trackers.
  public List<Tracker> searchTag(String tag) {
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