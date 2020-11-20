package core;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.Level;

public class LevelFilter extends AbstractMatcherFilter<ILoggingEvent> {

  Level level;
  @Override
  public FilterReply decide(ILoggingEvent event) {
    if (!isStarted()) {
                    return FilterReply.NEUTRAL;
    }

    if (event.getLevel().equals(level)) {
      return onMatch;
    } else {
      return onMismatch;
    }
  }
  public void setLevel(Level level) {
    this.level = level;
  }

  public void start() {
    if (this.level != null) {
      super.start();
    }
  }

}
