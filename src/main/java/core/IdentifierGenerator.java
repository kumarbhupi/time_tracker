package core;

public class IdentifierGenerator {
  private static IdentifierGenerator identifier;
  private int id;

  private IdentifierGenerator() {
    id = 0;
  }

  public static IdentifierGenerator getIdentifier() {
    if (identifier == null) {
      identifier = new IdentifierGenerator();
    }
    return identifier;
  }

  public int getId() {
    return this.id++;
  }
}
