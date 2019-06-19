package ActualComport;

import java.util.EventObject;

public class InstrumentEvent extends EventObject {

    private String receiveMode;
    private String filePath;

    private boolean export;
    private boolean Active;
    private boolean auxState;

    public boolean isAuxState() {
        return auxState;
    }

    public void setAuxState(boolean auxState) {
        this.auxState = auxState;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isExport() {
        return export;
    }

    public void setExport(boolean export) {
        this.export = export;
    }

    public String getReceiveMode() {
        return receiveMode;
    }

    public void setReceiveMode(String receiveMode) {
        this.receiveMode = receiveMode;
    }

    public InstrumentEvent (Object source) {
        super(source);
    }
}
