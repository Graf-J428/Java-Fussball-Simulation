package GUI;

import Datenmodell.Fussballspiel;
import Datenmodell.Tabelle;
import Datenmodell.Verein;
import turban.utils.ErrorHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class FussballTabelleGUI extends JFrame implements IReporter{

    private Tabelle _fussballTabelle;
    private final JTextArea _spielBericht;
    private VereinStatsJTable vereinStatsJTable;
    public FussballTabelleGUI(){
        super("Fussballspiel");

        _fussballTabelle= new Tabelle(this);
        vereinStatsJTable = new VereinStatsJTable(_fussballTabelle);

        getContentPane().setLayout(new BorderLayout());

        JButton neueTabButton = new JButton("Neue Tab");
        neueTabButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(FussballTabelleGUI.this,
                        "Gesamte Tabelle löschen?", "Neue Tab",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    _fussballTabelle.removeAll();
                    vereinStatsJTable.updateJTable();
                }
            }
        });

        JButton resetTabButton = new JButton("Reset Tab");
        resetTabButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(FussballTabelleGUI.this,
                        "Statistiken zurücksetzen?", "Reset Tab",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    try{
                        _fussballTabelle.reset();
                        vereinStatsJTable.updateJTable();
                    }catch (RuntimeException ex){
                        ErrorHandler.logException(ex,true, FussballTabelleGUI.class,"Reseten der Tabelle ist fehlgeschlagen!");
                    }

                }
            }
        });

        JButton ladeTabButton = new JButton("Lade Tab");
        ladeTabButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    LadeTabelle ladeTabelle = new LadeTabelle();
                    ladeTabelle.ladeTabelleVonDatei();
                }catch (Throwable th ){
                    ErrorHandler.logException(th,true, FussballTabelleGUI.class,"Fehler beim Laden der Tabelle");
                }

            }
        });

        JButton sichereTabButton = new JButton("Sichere Tab");
        sichereTabButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(new File("."));
                fileChooser.setDialogTitle("Sichere Tab");
                fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Datei", "csv"));
                if (fileChooser.showSaveDialog(FussballTabelleGUI.this) == JFileChooser.APPROVE_OPTION) {
                    String fileName = fileChooser.getSelectedFile().toString();
                    if (!fileName.endsWith(".csv")) {
                        fileName += ".csv";
                    }

                    File file = new File(fileName);
                    if (file.exists()) {
                        if (JOptionPane.showConfirmDialog(FussballTabelleGUI.this,
                                "Bestehende Datei überschreiben?", "Sichere Tab",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
                            return;
                        }
                    }
                    BufferedWriter writer = null;
                    try {
                       _fussballTabelle.schreibeInDatei(file);
                        }

                     catch (Exception e2) {
                        JOptionPane.showMessageDialog(FussballTabelleGUI.this,
                                "Fehler beim Speichern der Datei:\n" + e2.getMessage(),
                                "Sichere Tab", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        try {
                            if (writer != null) {
                                writer.close();
                            }
                        } catch (IOException e3) {
                        }
                    }
                }
            }
        });
        JButton neuerVereinButton = new JButton("Neuer Verein");
        neuerVereinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name;
                do {
                    name = (String) JOptionPane.showInputDialog(FussballTabelleGUI.this,
                            "Bitte Vereinsnamen eingeben",
                            getTitle(), JOptionPane.PLAIN_MESSAGE, null, null, "");
                } while (name != null && name.trim().length() == 0);
                if (name != null) {
                    _fussballTabelle.add(new Verein(name,0,0,0,0));

                    vereinStatsJTable.updateJTable();
                }
            }
        });

        JButton loescheVereinButton = new JButton("Lösche Verein");
        loescheVereinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = vereinStatsJTable.getSelectedRow();
                try{
                    if (row >= 0) {
                        _fussballTabelle.remove(row);
                        vereinStatsJTable.updateJTable();
                    }
                }catch(RuntimeException ex) {
                    ErrorHandler.logException(ex,true, FussballTabelleGUI.class,"Verein löschen fehlgeschlagen");
                }

            }
        });

        JButton neuesSpiel = new JButton("Neues Spiel");
        neuesSpiel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (_fussballTabelle.size() >= 2) {
                    NeuesSpielDialog dialog = new NeuesSpielDialog(FussballTabelleGUI.this, _fussballTabelle.vereineBekommen());
                    dialog.setLocationRelativeTo(FussballTabelleGUI.this);
                    dialog.setVisible(true);
                    if (dialog.isStartButtonClicked()) {
                        _spielBericht.setText("");
                        _fussballTabelle.spielergebnis(
                                dialog.getVerein1(), dialog.getVerein2());

                        vereinStatsJTable.updateJTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(FussballTabelleGUI.this, "Nicht genug Vereine.", "Neues Spiel", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(neueTabButton);
        buttonPanel.add(resetTabButton);
        buttonPanel.add(ladeTabButton);
        buttonPanel.add(sichereTabButton);
        buttonPanel.add(neuerVereinButton);
        buttonPanel.add(loescheVereinButton);
        buttonPanel.add(neuesSpiel);
        getContentPane().add(buttonPanel, BorderLayout.NORTH);

        getContentPane().add(new JScrollPane(vereinStatsJTable), BorderLayout.CENTER);

        _spielBericht = new JTextArea();
        _spielBericht.setRows(20);
        _spielBericht.setEditable(false);
        JPanel spielBerichtPanel = new JPanel();
        spielBerichtPanel.setLayout(new BoxLayout(spielBerichtPanel, BoxLayout.PAGE_AXIS));
        spielBerichtPanel.add(new JLabel("Spielbericht"));
        spielBerichtPanel.add(new JScrollPane(_spielBericht));
        this.add(spielBerichtPanel, BorderLayout.SOUTH);

        pack();
        setSize(1000, 800);
        setLocation(100, 100);
        setVisible(true);

    }

    private class LadeTabelle {

        private void ladeTabelleVonDatei() {
            JFileChooser fileChooser = createFileChooser();
            if (fileChooser.showOpenDialog(FussballTabelleGUI.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File file = prepareFile(fileChooser.getSelectedFile());
            if (!file.exists()) {
                showErrorMessage("Datei nicht vorhanden");
                return;
            }

            loadTableFromFile(file);
        }

        private JFileChooser createFileChooser() {
            JFileChooser chooser = new JFileChooser(new File("."));
            chooser.setDialogTitle("Lade Tab");
            chooser.setFileFilter(new FileNameExtensionFilter("CSV Datei", "csv"));
            return chooser;
        }

        private File prepareFile(File selectedFile) {
            String fileName = selectedFile.toString();
            if (!fileName.endsWith(".csv")) {
                fileName += ".csv";
            }
            return new File(fileName);
        }

        private void loadTableFromFile(File file) {
            _fussballTabelle.removeAll();
            BufferedReader reader = null;

            try {
                _fussballTabelle.liesListeAusDatei(file);
            } catch (Exception e) {
                showErrorMessage("Fehler beim Laden der Datei:\n" + e.getMessage());
            } finally {
                vereinStatsJTable.updateJTable();
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e3) {
                    // Ignoriert
                }
            }
        }
    }


    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(FussballTabelleGUI.this, message, "Lade Tab", JOptionPane.ERROR_MESSAGE);
    }


    public void giveNewMessage(String str) {
        _spielBericht.setText(_spielBericht.getText() + "\n" + str);
    }
    public static void main(String[] args) {
        try{
            new FussballTabelleGUI();
        }catch (Throwable thr){
            ErrorHandler.logException(thr,true, FussballTabelleGUI.class,"Start ist fehlgeschlagen ");
        }

    }
}



