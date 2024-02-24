import java.time.LocalDateTime;
import java.util.Objects;

public class Zeiteintrag implements Comparable<Zeiteintrag>{
    private int laufendeNummer;
    private long beginn;
    private long ende; // if negative -> zeiterfassung läuft noch
    private String bemerkung;
    private boolean abrechenbar;

    public Zeiteintrag(int laufendeNummer, int beginn) {
        if (laufendeNummer < 0) throw new IllegalArgumentException("Die laufende Nummer darf nicht negativ sein!");
        this.laufendeNummer = laufendeNummer;
        this.beginn = beginn;
        this.ende = -1;
        this.bemerkung = "";
        this.abrechenbar = true;
    }

    public int getLaufendeNummer() {
        return laufendeNummer;
    }

    public void setLaufendeNummer(int laufendeNummer) {
        this.laufendeNummer = laufendeNummer;
    }

    public long getBeginn() {
        return beginn;
    }

    public void setBeginn(int beginn) {
        this.beginn = beginn;
    }

    public long getEnde() {
        return ende;
    }

    public void setEnde(int ende) {
        if (this.ende < this.beginn) throw new IllegalArgumentException("Das Ende liegt vor dem Beginn");
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
        if (this.ende < 0) return this.ende;
        return (double) (this.ende - this.beginn) / 3600;
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
        return "Start: " + this.beginn + "Ende: " +
                (this.ende > 0 ? this.ende : "--") +
                "Bemerkung: " + this.bemerkung;
    }

    @Override
    public int compareTo(Zeiteintrag o) {
        return Long.compare(o.getBeginn(), this.getBeginn());
    }

    public int konvertiereSekundenInTage() {
        return (int) (getDauer() / 24);
    }

    public int getJahr(int days) {
        int year = 2000;
        for (int i = 0; i < days; i++) {
            if (days >= 365 && istSchaltjahr(year)) {
                days -= 365;
            } else if (days >= 364) {
                days -= 364;
            } else {
                break;
            }
            year++;
        }
        return year;
    }

    public int getMonat( int year) {
        int days = getJahr(konvertiereSekundenInTage());
        int monat = 0;
        int[] monatSchaltJahr = new int[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] monatKeinSchaltJahr = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (istSchaltjahr(year)) {
            for (int monatNum : monatSchaltJahr) {
                if (days >= monatNum) {
                    days -= monatNum;
                } else {
                    monat = monatNum + 1;
                }
            }
        } else {
            for (int monatNum : monatKeinSchaltJahr) {
                if (days >= monatNum) {
                    days -= monatNum;
                } else {
                    monat = monatNum + 1;
                }
            }
        }
        return monat;
    }

    public boolean istSchaltjahr(long year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    // Usage of a library that is allowed :P
    public long convertSecondsToYear() {
        LocalDateTime referenzDatum = LocalDateTime.of(2000, 1, 1, 0, 0);
        LocalDateTime neuesDatum = referenzDatum.plusSeconds(this.beginn);
        return neuesDatum.getYear();
    }

    public long convertSecondsToMonth() {
        LocalDateTime referenzDatum = LocalDateTime.of(2000, 1, 1, 0, 0);
        LocalDateTime neuesDatum = referenzDatum.plusSeconds(this.beginn);
        return neuesDatum.getMonthValue();
    }
}
