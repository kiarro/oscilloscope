package ActualComport;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstrumentTab extends JPanel implements ActionListener {

    private JLabel modeLabel = new JLabel("Режим приема");

    private JButton runButton = new JButton("Пуск");
    private JButton exportButton = new JButton("Экспортировать");

    private InstrumentListener instrumentListener;
    InstrumentEvent ev = new InstrumentEvent(this);

    private JComboBox modeBox = new JComboBox();

    private JFileChooser fileChooser = new JFileChooser();

    private boolean auxState = false;

////////////////////////// Outer functions ////////////////////////////////////////////////

    public void setInstrumentListener (InstrumentListener listener) {
        this.instrumentListener = listener;
    }

    public void setBtnText(String bool) {
        runButton.setText(bool);
        modeBox.setEnabled(true);
    }

////////////////////// Inner functions ////////////////////////////////////////////////

    private void modeFill(){
        modeBox.addItem("Дата логгер");
        modeBox.addItem("Осциллограф");
    }

    private void boxFill() {
        modeFill();
    }

///////////////////////////////////////////////////////////////////////////////////////

    private void layoutSetUp(){

        setLayout(new GridBagLayout());
        // Starting settings
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 0.3f;
        gc.gridwidth = 2;
        gc.insets = new Insets(0,5,0,5);
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;

        // First line
        gc.gridx = 0;
        gc.gridy = 0;
        add(modeLabel, gc);

        // Second line
        gc.insets = new Insets(0,5,5,5);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridx = 0;
        gc.gridy = 1;
        add(modeBox, gc);

        // Third line
        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 2;
        add(runButton, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        add(exportButton, gc);

    }

    private void componentInit(){

        boxFill();

        exportButton.setEnabled(false);
        runButton.addActionListener(this);
        exportButton.addActionListener(this);
        modeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String item = cb.getSelectedItem().toString();
                ev.setFilePath(null);
                ev.setExport(false);
                ev.setReceiveMode(item);
                ev.setAuxState(true);
                switch (item){
                    case ("Осциллограф"):
                        if (instrumentListener != null)
                            instrumentListener.instrumentEventOccurred(ev);
                        break;
                    case ("Дата логгер"):
                        if (instrumentListener != null)
                            instrumentListener.instrumentEventOccurred(ev);
                        break;
                }
            }
        });
        runButton.setFont(new Font(runButton.getFont().getFontName(), Font.PLAIN, 10));
        exportButton.setFont(new Font(exportButton.getFont().getFontName(), Font.PLAIN, 10));

        modeLabel.setFont(new Font(modeLabel.getFont().getFontName(), Font.PLAIN, 11));

    }

    private void setSize(){
        Dimension dim = getPreferredSize();
        dim.width = 200;
        dim.height = 400;
        setPreferredSize(dim);
    }

///////////////////////////////MAIN////////////////////////////////////////////////////
    public InstrumentTab(){

        componentInit();
        setSize();

        Border innerBorder = BorderFactory.createTitledBorder("Инструменты");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        layoutSetUp();
    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (clicked == runButton) {
            String temp = modeBox.getSelectedItem().toString();
            ev.setReceiveMode(temp);
            ev.setAuxState(false);
            switch (temp) {
                case ("Дата логгер"):
                    ev.setFilePath(null);
                    ev.setExport(false);
                    if (runButton.getText().equals("Пуск")) {
                        ev.setActive(true);
                        modeBox.setEnabled(false);
                        exportButton.setEnabled(false);
                        runButton.setText("Стоп");
                    } else {
                        ev.setActive(false);
                        exportButton.setEnabled(true);
                        modeBox.setEnabled(true);
                        runButton.setText("Пуск");
                    }
                    break;
                case ("Осциллограф"):
                    ev.setFilePath(null);
                    ev.setExport(false);
                    if (runButton.getText().equals("Пуск")) {
                        ev.setActive(true);
                        modeBox.setEnabled(false);
                        exportButton.setEnabled(false);
                        runButton.setText("Стоп");
                    } else {
                        ev.setActive(false);
                        modeBox.setEnabled(true);
                        exportButton.setEnabled(true);
                        runButton.setText("Пуск");
                    }
                    break;
            }
            if (instrumentListener != null)
                instrumentListener.instrumentEventOccurred(ev);
        } else {
            if (fileChooser.showSaveDialog(InstrumentTab.this) == JFileChooser.APPROVE_OPTION){
                ev.setExport(true);
                ev.setFilePath(fileChooser.getSelectedFile().toString());
            }
            if (instrumentListener != null)
                instrumentListener.instrumentEventOccurred(ev);
        }
    }


}
