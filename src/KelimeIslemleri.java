import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class KelimeIslemleri {
    protected final String dosyaAdi = "kelimeler.txt";

    // Kelime ekleme metodu (abstract)
    public abstract void kelimeEkle(String kelime);

    // Tüm kelimeleri listeleyen metot
    public List<String> kelimeleriOku() {
        List<String> kelimeListesi = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dosyaAdi))) {
            String satir;
            while ((satir = reader.readLine()) != null) {
                kelimeListesi.add(satir.trim());
            }
        } catch (IOException e) {
            System.err.println("Dosya okunurken hata oluştu: " + e.getMessage());
        }
        return kelimeListesi;
    }
}
