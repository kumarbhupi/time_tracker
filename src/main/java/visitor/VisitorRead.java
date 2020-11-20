package visitor;

import persistence.FileManager;
import core.TaskManager;

public interface VisitorRead {
  TaskManager visit(FileManager fileManager);

}
