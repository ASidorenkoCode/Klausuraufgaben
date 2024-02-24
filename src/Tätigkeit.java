import java.util.ArrayList;
import java.util.Collections;

public class Tätigkeit {
    private String kunde;
    private double stundensatz;
    private ArrayList<Zeiteintrag> zeiteintraege;

    public Tätigkeit(String kunde, double stundensatz) {
        this.kunde = kunde;
        this.stundensatz = stundensatz;
        this.zeiteintraege = new ArrayList<>();
    }

    public String getKunde() {
        return kunde;
    }

    public void setKunde(String kunde) {
        this.kunde = kunde;
    }

    public double getStundensatz() {
        return stundensatz;
    }

    public void setStundensatz(double stundensatz) {
        this.stundensatz = stundensatz;
    }

    public ArrayList<Zeiteintrag> getZeiteintraege() {
        return zeiteintraege;
    }

    public void setZeiteintraege(ArrayList<Zeiteintrag> zeiteintraege) {
        this.zeiteintraege = zeiteintraege;
    }

    public boolean addZeiteintrag(Zeiteintrag zeiteintrag) {

        for (Zeiteintrag eintrag : zeiteintraege) {
            // Laufende Nummer-Prüfung
            if (eintrag.getLaufendeNummer() == zeiteintrag.getLaufendeNummer()) return false;
            // Offenes Ende-Prüfung
            if (eintrag.getEnde() < 0) return false;
            // Keine Überschneidungs-Prüfung
            if (zeiteintrag.getBeginn() <= eintrag.getEnde()) return false;
        }
        zeiteintraege.add(zeiteintrag);
        Collections.sort(zeiteintraege);
        return true;
    }

    public static double aufwandImMonat(ArrayList<Tätigkeit> tätigkeiten, String kunde, int jahr, int monat) {
        double gesamtkosten = 0.0;
        for (Tätigkeit tätigkeit : tätigkeiten) {
            for(Zeiteintrag zeiteintrag: tätigkeit.zeiteintraege) {
                int tage = zeiteintrag.konvertiereSekundenInTage();
                int zJahr = zeiteintrag.getJahr(tage);
                int zMonat = zeiteintrag.getMonat(zJahr);
                if(zeiteintrag.isAbrechenbar() &&
                        tätigkeit.kunde.equals(kunde) &&
                        zJahr == jahr &&
                        zMonat == monat) gesamtkosten += zeiteintrag.getDauer();
            }
        }
        return gesamtkosten;
    }
}
