public interface PrintVisitor {
  void print(Task task);
  void print(TaskManager taskManager);
  void print(Interval interval);
}
