package Datenmodell;

import GUI.IReporter;

public class Spielereignis {
    private final IReporter _reporter;

    enum Ereignistyp {
        Ecke, Freistoss, Gelbekarte, RoteKarte, Elfmeter, Fehlent
    }

    Fussballspiel _spiel;
    Wuerfel w6 = new Wuerfel(6);

    Spielereignis(Fussballspiel s,IReporter reporter) {
        _reporter=reporter;
        _spiel = s;
    }

    public Mannschaft ereignisMannschaft() {
        int wert = w6.wuerfeln();
        if (wert <= 3) {
            return _spiel.get_mann1();
        } else return _spiel.get_mann2();
    }

    private void macheSpielEreignis(Ereignistyp ereignistyp) {       //ereignistyp ist variable von Enum Ereignistyp

        Mannschaft mannschaft = ereignisMannschaft();

        switch (ereignistyp) {
            case Ecke -> _reporter.giveNewMessage("Ecke für " + mannschaft);
            case Freistoss -> _reporter.giveNewMessage("Gefährlicher Freistoß für " + mannschaft);
            case Gelbekarte -> _reporter.giveNewMessage("Gelbe Karte für " + mannschaft);
            case RoteKarte -> {
                int roteKarte = w6.wuerfeln();
                if (roteKarte == 1) {
                    _reporter.giveNewMessage("Rote Karte für den Torwart von " + mannschaft);

                } else if (roteKarte == 2 || roteKarte == 3) {
                    _reporter.giveNewMessage("Rote Karte für den Verteidiger von " + mannschaft);
                } else if (roteKarte == 4 || roteKarte == 5) {
                    _reporter.giveNewMessage("Rote Karte für den Mittelfeld von " + mannschaft);
                } else if (roteKarte == 6) {
                    _reporter.giveNewMessage("Rote Karte für den Stürmer von " + mannschaft);

                }
            }
            case Elfmeter -> {
                _reporter.giveNewMessage("Elfmeter für "+mannschaft);

               if(mannschaft.kassiereTor( mannschaft.schiesseTor())) {

                   if(mannschaft==_spiel.get_mann1()){
                       _spiel.set_torMann1(_spiel.get_torMann1()+1);
                   }
                   if(mannschaft==_spiel.get_mann2()){
                       _spiel.set_torMann2(_spiel.get_torMann2()+1);
                   }

               }



            }



        }
    }

    public void berechneSpielEreignis() {

        if (w6.wuerfeln() == 6) {
            macheSpielEreignis(Ereignistyp.values()[w6.wuerfeln() - 1]);
        }
    }
}
