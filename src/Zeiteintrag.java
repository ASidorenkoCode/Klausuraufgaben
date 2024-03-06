import java.util.Objects;

public class Zeiteintrag implements Comparable<Zeiteintrag> {
    private int laufendeNummer;
    private long beginn;
    private long ende; // if negative -> zeiterfassung läuft noch
    private String bemerkung;
    private boolean abrechenbar;
    private final int REFERENZMONAT = 1;
    private final int REFERENZJAHR = 2000;
    private final int[] MONATSCHALTJAHR = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private final int[] MONATJAHR = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private final int SCHALTJAHRINTAGEN = 366;
    private final int JAHRINTAGEN = 365;


    public Zeiteintrag(int laufendeNummer, int beginn) {
        setLaufendeNummer(laufendeNummer);
        setBeginn(beginn);
        this.ende = -1;
        // setze es auf leeren String, damit Output bei leerer Bemerkung nicht null ist
        this.bemerkung = "";
        // setze es standardmäßig false
        this.abrechenbar = false;
    }

    public int getLaufendeNummer() {
        return laufendeNummer;
    }

    public void setLaufendeNummer(int laufendeNummer) {
        if (laufendeNummer < 0) throw new IllegalArgumentException("Die laufende Nummer darf nicht negativ sein!");
        this.laufendeNummer = laufendeNummer;
    }

    public long getBeginn() {
        return beginn;
    }

    public void setBeginn(int beginn) {
        if (this.beginn < 0) throw new IllegalArgumentException("Der Beginn darf nicht kleiner 0 sein");
        this.beginn = beginn;
    }

    public long getEnde() {
        return ende;
    }

    public void setEnde(int ende) {
        if (this.ende < this.beginn) throw new IllegalArgumentException("Das Ende liegt vor dem Beginn");
        if (this.ende < 0) throw new IllegalArgumentException("Das Ende soll nicht negativ sein.");
        this.ende = ende;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    public boolean isAbrechenbar() {
        return abrechenbar;
    }

    public void setAbrechenbar(boolean abrechenbar) {
        this.abrechenbar = abrechenbar;
    }

    public double getDauer() {
        return (this.ende < 0 ? this.ende : (double) (this.ende - this.beginn) / 3600);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zeiteintrag that = (Zeiteintrag) o;
        return laufendeNummer == that.laufendeNummer && beginn == that.beginn && ende == that.ende && abrechenbar == that.abrechenbar && Objects.equals(bemerkung, that.bemerkung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(laufendeNummer, beginn, ende, bemerkung, abrechenbar);
    }


    @Override
    public String toString() {
        return "Start: " + this.beginn + " Ende: " + (this.ende > 0 ? this.ende : "--") + " Bemerkung: " + this.bemerkung;
    }

    @Override
    public int compareTo(Zeiteintrag o) {
        return Long.compare(o.getBeginn(), this.getBeginn());
    }

    public int[] getTagMonatJahr() {
        /* Annahme:
           - Beginn und Ende liegt am gleichen Tag
           - Wenn Ende im Folgemonat liegt, wird es nicht im aktuellen Monat betrachtet
         */
        int tag = (int) (ende / 86400); // ende / (3600*24)
        int monat = REFERENZMONAT;
        int jahr = REFERENZJAHR;

        while (tag >= JAHRINTAGEN) {
            tag -= istSchaltjahr(jahr) && tag >= SCHALTJAHRINTAGEN ? SCHALTJAHRINTAGEN : JAHRINTAGEN;
            jahr++;
        }

        for (int elm : (istSchaltjahr(jahr) ? MONATSCHALTJAHR : MONATJAHR)) {
            if (tag > elm) {
                tag -= elm;
                monat++;
            }
        }
        return new int[]{tag, monat, jahr};
    }

    public int getTag() {
        return getTagMonatJahr()[0];
    }

    public int getMonat() {
        return getTagMonatJahr()[1];
    }

    public int getJahr() {
        return getTagMonatJahr()[2];
    }

    public boolean istSchaltjahr(long jahr) {
        return jahr % 4 == 0 && jahr % 100 != 0 || jahr % 400 == 0;
    }


}
