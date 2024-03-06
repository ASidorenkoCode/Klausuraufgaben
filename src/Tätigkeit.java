import java.util.ArrayList;
import java.util.Collections;

public class Tätigkeit {
    private String kunde;
    private double stundensatz;
    private ArrayList<Zeiteintrag> zeiteintraege;

    public Tätigkeit(String kunde, double stundensatz) {
        setKunde(kunde);
        setStundensatz(stundensatz);
        this.zeiteintraege = new ArrayList<>();
    }

    public String getKunde() {
        return kunde;
    }

    public void setKunde(String kunde) {
        // Nehme an, dass der Kunde einen Namen haben muss
        if (kunde.isEmpty()) throw new IllegalArgumentException("Der Kunde muss einen Namen besitzen");
        this.kunde = kunde;
    }

    public double getStundensatz() {
        return stundensatz;
    }

    public void setStundensatz(double stundensatz) {
        // nehme an, dass der Stundensatz auch 0 sein kann
        if (stundensatz < 0) throw new IllegalArgumentException("Der Stundensatz darf nicht negativ sein");
        this.stundensatz = stundensatz;
    }

    public void setZeiteintraege(ArrayList<Zeiteintrag> zeiteintraege) {
        if (zeiteintraege.isEmpty()) throw new IllegalArgumentException("Die übergebene Liste darf nicht leer sein");
        this.zeiteintraege = zeiteintraege;
    }

    public boolean addZeiteintrag(Zeiteintrag zeiteintrag) {

        for (Zeiteintrag eintrag : zeiteintraege) {
            // Laufende Nummer-Prüfung
            if (eintrag.getLaufendeNummer() == zeiteintrag.getLaufendeNummer()) return false;
            // Offenes Ende-Prüfung
            if (eintrag.getEnde() < 0) return false;
            // Keine Überschneidungs-Prüfung
            if (zeiteintrag.getEnde() < eintrag.getBeginn()) return false;
        }
        zeiteintraege.add(zeiteintrag);
        Collections.sort(zeiteintraege);
        return true;
    }

    public static double aufwandImMonat(ArrayList<Tätigkeit> tätigkeiten, String kunde, int jahr, int monat) {
        double aufwand = 0.0;
        for (Tätigkeit tätigkeit : tätigkeiten) {
            if (tätigkeit.kunde.equals(kunde)) for (Zeiteintrag zeiteintrag : tätigkeit.zeiteintraege) {
                if (zeiteintrag.isAbrechenbar() && zeiteintrag.getJahr() == jahr && zeiteintrag.getMonat() == monat)
                    aufwand += zeiteintrag.getDauer() * tätigkeit.getStundensatz();
            }
        }
        return aufwand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tätigkeit tätigkeit)) return false;

        if (Double.compare(getStundensatz(), tätigkeit.getStundensatz()) != 0) return false;
        return getKunde() != null ? getKunde().equals(tätigkeit.getKunde()) : tätigkeit.getKunde() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getKunde() != null ? getKunde().hashCode() : 0;
        temp = Double.doubleToLongBits(getStundensatz());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
