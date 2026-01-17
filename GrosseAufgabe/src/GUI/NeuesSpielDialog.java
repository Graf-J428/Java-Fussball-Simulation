package GUI;

import Datenmodell.Verein;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NeuesSpielDialog extends JDialog {
    private boolean _startButtonClicked = false;
    private final JComboBox _verein1Box;
    private final JComboBox _verein2Box;
    private Verein _verein1;
    private Verein _verein2;

    public NeuesSpielDialog(Frame owner, Verein ver[]) {
        super(owner, "Neues Spiel", true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JPanel verein1Panel = new JPanel(new FlowLayout());
        verein1Panel.add(new JLabel("Verein 1: "));
        _verein1Box = new JComboBox(ver);
        verein1Panel.add(_verein1Box);
        add(verein1Panel);

        JPanel verein2Panel = new JPanel(new FlowLayout());
        verein2Panel.add(new JLabel("Verein 2: "));
        _verein2Box = new JComboBox(ver);
        verein2Panel.add(_verein2Box);
        add(verein2Panel);

        JPanel buttonBox = new JPanel(new FlowLayout());
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (_verein1Box.getSelectedItem() == _verein2Box.getSelectedItem()) {
                    JOptionPane.showMessageDialog(NeuesSpielDialog.this, "Bitte zwei verschiedene Vereine wählen.", "Starte Spiel", JOptionPane.ERROR_MESSAGE);
                } else {
                    _verein1 = (Verein) _verein1Box.getSelectedItem();
                    _verein2 = (Verein) _verein2Box.getSelectedItem();

                    _startButtonClicked = true;
                    dispose();
                }
            }
        });
        buttonBox.add(startButton);
        JButton abbruchButton = new JButton("Abbruch");
        abbruchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _startButtonClicked = false;
                dispose();
            }
        });
        buttonBox.add(abbruchButton);
        add(buttonBox);
        pack();
    }

    public Verein getVerein1() {
        return _verein1;
    }

    public Verein getVerein2() {
        return _verein2;
    }

    public boolean isStartButtonClicked() {
        return _startButtonClicked;
    }
}
