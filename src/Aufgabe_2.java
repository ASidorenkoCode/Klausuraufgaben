import java.util.ArrayList;

public class Aufgabe_2 {
    public static ArrayList<Double> extrahiereIntervall(double untereGrenze, double obereGrenze, ArrayList<Double> werte) {
        ArrayList<Double> finalList = new ArrayList<>();
        for (Double wert : werte) {
            if (wert >= untereGrenze && wert <= obereGrenze) finalList.add(wert);
        }
        return finalList;
    }
}
