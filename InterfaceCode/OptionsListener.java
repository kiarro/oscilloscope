package ActualComport;
import java.util.EventListener;

public interface OptionsListener extends EventListener {
    public void optionsEventOccurred(OptionsEvent e);
}
