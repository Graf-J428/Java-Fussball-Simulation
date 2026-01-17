package Datenmodell;

import GUI.IReporter;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
/**
 * Diese Klasse stellt die für eine Fussballtabelle nötigen funktionen bereit.
 * Ausserdem stellt sie methoden zum schreiben in eine Datei und zum auslesen des Inhalts der Datei.
 * Die Variable  CSTR_TRENN_ZEICHEN wird benutzt um bei der auslese des Inhalts der Datei die strings voneinander zu trennen und dann auszulesen**/
public class Tabelle implements IReporter {

    private final String CSTR_TRENN_ZEICHEN =";";
    Fussballspiel spiel;
    protected Verein[] _vereine;
    protected int _size;
    private final IReporter _reporter;
    public Tabelle(IReporter reporter){
        _vereine=new Verein[18];
         _size=0;
         _reporter=reporter;
    }

    public Verein getVerein(int i){
        return _vereine[i];
    }
    /**gibt die größe des Arrays zurück
     * @return _size OUT: größe des Arrays **/
    public int size(){
        return _size;
    }

    @Override
    public String toString (){
        return Arrays.toString(_vereine);
    }
    /**sortiert die Tabelle nach denen in der comparto methode festgelegten Kriterien**/
    public void sort(){
        for (int i =1; i< _size;i++){

            for (int j= _size-1; j>=i;j--){

                if (_vereine[j].compareTo(_vereine[j-1])==1){
                        Verein temp = _vereine[j];
                        _vereine[j]= _vereine[j-1];
                        _vereine[j-1]=temp;
                }
            }
        }
    }

    /**Vergleicht den übergebeneden Verein mit denen in der tabelle und gibt den Index zurück
     * @param ver IN: zu findende Verein
     * @return i OUT: index des Vereins**/
    public int find (Verein ver){
        for (int i =0; i< _size;i++){

            if (_vereine[i].equals(ver) ){
                return i;
            }
        }
        return -1;
    }

    private void ensureTabelleHasSpace() {
        if (size() == 18) {
            throw new RuntimeException("Die Tabelle ist voll");
        }
    }
    /**Fügt einen neuen Verein der Tabelle hinzu und vergrößert sie **/
    public void add(Verein ver) {
        ensureTabelleHasSpace();
        _vereine[_size] = ver;
        ++_size;
    }
    public boolean remove (Verein ver){
        int indexVer=find(ver);
        if(indexVer!=-1){
            _vereine[indexVer]=null;
            for(int i = indexVer+1; i<_size;i++){
                int neueIndex= i-1;
                _vereine[neueIndex]=_vereine[i];
            }
            --_size;
            _vereine[_size]=null;
            return true;
        }
        return false;
    }
    /**Entfernt einen Verein aus der tabelle und verringert die größe
     * @param index IN: Verein index
     * **/
    public void remove(int index) {
        for (int i = index; i < _size - 1; i++) {
            _vereine[i] = _vereine[i + 1];
        }
        _size = _size - 1;
        _vereine[_size] = null;
    }

    public void removeAll() {
        _vereine = new Verein[18];
        _size = 0;
    }

     public Verein[] vereineBekommen(){
        sort();
        return _vereine;
     }

     public void reset (){
        for(int i =0;i< _size; i++){
            _vereine[i].setGemachteSpiele(0);
            _vereine[i].setEigeneTore(0);
            _vereine[i].setGegentore(0);
            _vereine[i].setTordifferenz(0);
            _vereine[i].setPunkte(0);
        }
     }


    /**
     * Führt eine Spielsimulation zwischen zwei Vereinen durch und aktualisiert die Tabelle.
     * 1. Startet das Fussballspiel (Simulation aller 90 Min).
     * 2. Addiert geschossene Tore und Gegentore auf die Vereinsstatistiken.
     * 3. Berechnet die Punkte neu (3 für Sieg, 1 für Unentschieden).
     * 4. Erhöht den Zähler für gemachte Spiele.
     * 5. Sortiert die Tabelle basierend auf den neuen Werten neu.
     *
     * @param ver1 Der erste Verein (Heim).
     * @param ver2 Der zweite Verein (Gast).
     * @throws RuntimeException wenn ver1 und ver2 identisch sind.
     */
     public void spielergebnis (Verein ver1, Verein ver2){  //, int toreVer1, int toreVer2


        if (ver1.equals(ver2)){
            throw new RuntimeException("Der Verein kann nicht gegen sich selbst Spielen!");
        }

         Mannschaft mann1 = ver1.getMannschaft() == null ? ver1.setMannschaft() : ver1.getMannschaft();
         Mannschaft mann2 = ver2.getMannschaft() == null ? ver2.setMannschaft() : ver2.getMannschaft();

         spiel = new Fussballspiel(mann1, mann2, _reporter);
         spiel.starteSpiel();

         // Statistiken aktualisieren
         ver1.setEigeneTore(ver1.getEigeneTore() + spiel.get_torMann1());
         ver1.setGegentore(ver1.getGegenTore() + spiel.get_torMann2());
         ver1.updateTorDifferenz();
         ver2.setEigeneTore(ver2.getEigeneTore() + spiel.get_torMann2());
         ver2.setGegentore(ver2.getGegenTore() + spiel.get_torMann1());
         ver2.updateTorDifferenz();

         // Punkte aktualisieren
         if (spiel.get_torMann1() > spiel.get_torMann2()) {
             ver1.setPunkte(ver1.getPunkte() + 3);
         } else if (spiel.get_torMann2() > spiel.get_torMann1()) {
             ver2.setPunkte(ver2.getPunkte() + 3);
         } else {
             ver1.setPunkte(ver1.getPunkte() + 1);
             ver2.setPunkte(ver2.getPunkte() + 1);
         }


         ver1.setGemachteSpiele(ver1.getGemachteSpiele() + 1);
         ver2.setGemachteSpiele(ver2.getGemachteSpiele() + 1);

         sort();

     }


    /**Die Methode schreibt den inhalt der Tabelle in eine CSV-Datei
     * @param file IN: Die Datei **/
    public void schreibeInDatei(File file){

        if(file.exists() == true){
            System.out.println("Ueberschreibe existierende Datei " + file.toString());
        }

        try ( BufferedWriter bw = new BufferedWriter(new FileWriter(file)) ){

            for(int i = 0; i < _vereine.length; i++){
                Verein ver = _vereine[i];
                if(ver != null){
                    bw.write(ver.toCSVString() );

                    bw.newLine();
                }


            }
            bw.flush(); // Schreibt alles sicher weg
        }
        catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    /**
     * Parst eine Zeile aus der CSV-Datei und erstellt/aktualisiert ein Vereinsobjekt.
     * Erwartet 6 Werte getrennt durch Semikolon.
     * Wenn der Verein schon existiert, wird er aktualisiert, sonst hinzugefügt.
     *
     * @param strZeile Die rohe Textzeile aus der CSV-Datei.
     * @throws RuntimeException Wenn das Format ungültig ist oder Zahlen nicht geparst werden können.
     */

    private void verarbeiteZeile(String strZeile){

        String [] strs = strZeile.split(CSTR_TRENN_ZEICHEN);	  //String splitten mittels vereinbartem Trennzeichen
        if(strs.length != 6){
            throw new RuntimeException("Folgende Zeile kann nicht geparst werden, weil nicht vier Strings entstehen:" + strZeile);
        }

        try {
            String name = strs[0].trim();
            int gemachteSpiele = Integer.parseInt(strs[1].trim());
            int eigeneTore = Integer.parseInt(strs[2].trim());
            int gegentore = Integer.parseInt(strs[3].trim());

            int punkte = Integer.parseInt(strs[5].trim());

            Verein ver = new Verein(name, gemachteSpiele, punkte, eigeneTore, gegentore);
            //Duplikatsprüfung
            int index = find(ver);
            if (index != -1) {
                _vereine[index] = ver;
            } else {
                add(ver);
            }

        }catch(Exception ex){
            throw new RuntimeException("Folgende Zeile kann nicht geparst werden, weil wohl ein String kein Zahlwert ist wie verlangt:" + strZeile);
        }


    }
    /**Hier werden die Inhalte in der zuvor erstellten Datei ausgelesen
     * @param file IN: Die Datei**/
    public void liesListeAusDatei(File file) throws IOException{

        removeAll();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String strZeile = br.readLine();
            while(strZeile != null){
                verarbeiteZeile(strZeile);
                strZeile = br.readLine();
            }
        }
        catch (Exception ex) {

            ex.printStackTrace();
        }
    }


    @Override
    public void giveNewMessage(String str) {

    }
}
