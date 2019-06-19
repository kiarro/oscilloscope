package ActualComport;

import java.util.EventListener;

public interface InstrumentListener extends EventListener {
    public void instrumentEventOccurred(InstrumentEvent e);
}
