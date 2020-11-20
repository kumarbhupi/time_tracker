package visitor;

import persistence_utils.FileManager;
import core.TaskManager;

public interface VisitorRead {
  TaskManager visit(FileManager fileManager);

}
