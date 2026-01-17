package Datenmodell;

import GUI.IReporter;

public class Fussballspiel {


    private final IReporter _reporter;

  private Mannschaft _mann1;
  private Mannschaft _mann2;
  private  int _torMann1;
   private int _torMann2;

    public void giveMessageToRep(String msg){
        _reporter.giveNewMessage(msg);
    }

    public Fussballspiel(Mannschaft mann1, Mannschaft mann2,IReporter reporter ) {
        _reporter = reporter;
        _mann1= mann1;
        _mann2 = mann2;

    }

    public Mannschaft get_mann1(){
        return _mann1;
    }

    public Mannschaft get_mann2(){
    return _mann2;
    }



    public int get_torMann1(){
        return _torMann1;
    }

    public void set_torMann1(int v){
        _torMann1=v;

    }
    public void set_torMann2(int v){
        _torMann2=v;

    }

    public int get_torMann2(){
        return _torMann2;
    }

    /**
     * Simuliert eine einzelne Spielminute.
     * Es wird geprüft, ob Mannschaft 1 einen Angriff startet und ob Mannschaft 2 verteidigt (und umgekehrt).
     * Falls ein Angriff durchkommt, wird geprüft, ob ein Tor fällt.
     * Alle Ereignisse werden an den Reporter zur Ausgabe weitergeleitet.
     *
     * @param spielMin Die aktuelle Minute des Spiels.
     */
   public void starteSpielMin(int spielMin){
        Mannschaft mann1=get_mann1();
        Mannschaft mann2=get_mann2();

       if (mann1.angriff()) {
           _reporter.giveNewMessage("AV                     ");
            if (!mann2.verteidigt()) {
                _reporter.giveNewMessage("!!!               ");
                if (mann2.kassiereTor(mann1.schiesseTor())) {
                    _reporter.giveNewMessage("TOOOOOOR für " + mann1 + " in der " + spielMin + " Spielminute");
                    _torMann1++;
                } else _reporter.giveNewMessage("Gute Parade durch den Keeper von Mannschaft " + mann2);
            } else _reporter.giveNewMessage("               VT");
        }

       if (mann2.angriff()){
           _reporter.giveNewMessage("               AV");
           if(!mann1.verteidigt()){
               _reporter.giveNewMessage("               !!!");
               mann2.schiesseTor();
               if (mann2.kassiereTor( mann2.schiesseTor())){
                   _reporter.giveNewMessage("TOOOOOOR für "+mann2+ "in der "+spielMin+". Spielminute");
                   _torMann2++;
               }else _reporter.giveNewMessage("Gute Parade durch den Keeper von Mannschaft "+mann1);
           }else _reporter.giveNewMessage("VT                 ");


       }
    }
    /**
     * Startet die komplette Spielsimulation über 90 Minuten.
     * - Initialisiert den Reporter.
     * - Führt die Schleife für 90 Minuten aus.
     * - Ruft Ereignisse (Karten, Ecken etc.) ab.
     * - Behandelt die Halbzeitpause.
     * - Ermittelt am Ende den Sieger und gibt das Resultat aus.
     */
     public void starteSpiel(){

        Spielereignis ereignis = new Spielereignis(this,_reporter);
         _reporter.giveNewMessage("------- Spiel beginnt -------");
         _reporter.giveNewMessage("Linke Seite: "+ _mann1+"\n");
         _reporter.giveNewMessage("Rechte Seite: " +_mann2+"\n");
         _reporter.giveNewMessage("-----------------------------");

        for(int i=1;i<=90;i++){
            starteSpielMin(i);
            ereignis.berechneSpielEreignis();

            if (i == 45) {
                _reporter.giveNewMessage("------- Halbzeit --------");
                _reporter.giveNewMessage("Linke Seite: "+_mann2+"\n" );
               _reporter.giveNewMessage("Rechte Seite: "+_mann1+"\n");
               _reporter.giveNewMessage("--------------------------");
            }
        }
        if (_torMann2>_torMann1){
            _reporter.giveNewMessage("Sieg für "+_mann2+" "+_torMann2+" : "+_torMann1);
        }
        else if (_torMann1>_torMann2){
           _reporter.giveNewMessage("Sieg für "+_mann1+" "+_torMann1+" : "+_torMann2);
        }else _reporter.giveNewMessage("Beide Mannschaften Spielen unentschieden " +_mann1+" : "+_mann2);

    }






}