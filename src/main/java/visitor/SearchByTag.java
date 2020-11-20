package visitor;

import core.Tag;
import core.Task;
import core.TaskManager;
import core.Tracker;

public class SearchByTag implements VisitorTag{
    @Override
    public void searchTag(Tracker tracker, String tag) {
        tracker.searchTag(this,tag);
    }

    @Override
    public void searchTag(TaskManager taskManager, String tag) {
        for (Tracker tracker : taskManager.getTrackers()) {
            for (int j = 0; j < tracker.getTags().size(); j++){
                Tag currentTag = tracker.getTags().get(j);
                String currentStringTag = currentTag.getTag();
                boolean find = currentStringTag.equalsIgnoreCase(tag);
                if(find){
                    System.out.println(tracker.getName());
                }
            }
            searchTag(tracker, tag);
        }

    }

}
