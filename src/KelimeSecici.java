import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KelimeSecici {
    public static String rastgeleKelimeSec(String dosyaAdi) {
        List<String> kelimeler = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dosyaAdi))) {
            String line;
            while ((line = reader.readLine()) != null) {
                kelimeler.add(line.trim()); // Kelimeleri listeye ekle
            }
        } catch (IOException e) {
            System.err.println("Dosya okuma hatası: " + e.getMessage());
        }

        if (kelimeler.isEmpty()) {
            throw new IllegalStateException("Kelime listesi boş!");
        }

        // Rastgele bir kelime seç
        Random random = new Random();
        return kelimeler.get(random.nextInt(kelimeler.size()));
    }
}
