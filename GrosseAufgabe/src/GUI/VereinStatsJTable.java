package GUI;
import Datenmodell.Tabelle;
import Datenmodell.Verein;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VereinStatsJTable extends JTable {
    public static final String[] column_names = {"Verein", "Spiele", "eigene Tore", "Gegentore", "Tordifferenz", "Punkte"};
    private final Tabelle spielTabelle;

    public VereinStatsJTable(Tabelle spielTabelle) {
        super(new DefaultTableModel(column_names, 50));
        this.spielTabelle = spielTabelle;
    }

    public void updateJTable() {
        DefaultTableModel model = (DefaultTableModel) getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        for (int i = 0; i < spielTabelle.size(); i++) {
            Verein ver = spielTabelle.getVerein(i);
            model.addRow(new Object[]{ver.getName(), ver.getGemachteSpiele(), ver.getEigeneTore(),
                    ver.getGegenTore(), ver.getTorDifferenz(), ver.getPunkte()});
        }
    }
}