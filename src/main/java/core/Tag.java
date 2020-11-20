package core;

import java.util.List;

public class Tag {
    private String tag;

    public Tag(String tag){
        this.tag = tag;
    }

    void setTag(String tag){
        this.tag = tag;
    }

    public String getTag(){return this.tag;}

    public void searchTag(String tag, TaskManager taskManager){
        for (int i = 0; i < taskManager.getTrackers().size(); i++){
            Tracker currentTracker = taskManager.getTrackers().get(i);
            for (int j = 0; j < currentTracker.getTags().size(); j++){
                Tag currentTag = currentTracker.getTags().get(j);
                String currentStringTag = currentTag.getTag();
                boolean find = currentStringTag.equalsIgnoreCase(tag);
                if(find){
                    System.out.println(currentTracker.getName());
                }
            }
            if(currentTracker instanceof TaskManager){
                TaskManager nextTaskManager = ((TaskManager) currentTracker);
                searchTag(tag, nextTaskManager);
            }
        }
    }



}
