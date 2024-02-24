public class Aufgabe_1 {
    public static int quersumme(long zahl) {
        int quersum = 0;
        if (zahl <= 0) return 0;
        while (zahl > 0) {
            quersum += (int) zahl % 10;
            zahl /= 10;
        }
        return quersum;
    }
}
