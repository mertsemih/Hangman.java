import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// KelimeYonetimi sınıfı, KelimeIslemleri sınıfını extend eder ve kelime işlemleriyle ilgili işlemleri içerir
// Kelimeleri dosyadan okuma, ekleme ve rastgele seçme gibi işlevler sunar
public class KelimeYonetimi extends KelimeIslemleri {

    private String dosyaAdi = "kelimeler.txt";

    // Sınıf oluşturulurken dosya kontrol ediyoruz ve yoksa oluşturuyoruz
    public KelimeYonetimi() {
        dosyayiKontrolEtVeOlustur();
    }

    // Kelime ekleme metodu
    @Override
    public void kelimeEkle(String kelime) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaAdi, true))) {
            writer.write(kelime);
            writer.newLine();
        } catch (IOException e) {
            // Hata durumunda mesaj yazdırıyoruz
            System.err.println("Kelime eklenirken hata oluştu: " + e.getMessage());
        }
    }

    // Bir listeyi dosyaya ekleme metodu
    public void kelimeleriListeIleEkle(List<String> kelimeListesi) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaAdi, true))) {
            // Listedeki her kelimeyi dosyaya yazar
            for (String kelime : kelimeListesi) {
                writer.write(kelime);
                writer.newLine();
            }
        } catch (IOException e) {
            // Hata durumunda mesaj yazdırılır
            System.err.println("Liste dosyaya yazılırken hata oluştu: " + e.getMessage());
        }
    }

    // Dosyanın var olup olmadığını kontrol eder ve yoksa oluşturur
    public void dosyayiKontrolEtVeOlustur() {
        File dosya = new File(dosyaAdi);
        if (!dosya.exists()) {
            try {

                if (dosya.createNewFile()) {
                    // Başlangıç kelimeleri dosyaya eklenir
                    kelimeleriListeIleEkle(List.of(
                            "ADANA", "JAVA", "ANKARA", "BARDAK", "TRAKTÖR",
                            "FUTBOL", "NEHİR", "TÜRKİYE", "KADER", "GALATASARAY",
                            "FENERBAHÇE", "MUĞLA", "ALGORİTMA", "NESNE", "BİSİKLET",
                            "GAZİ", "RAPOR", "BİLGİSAYAR", "ATATÜRK", "YAKAMOZ",
                            "CEVİZ"
                    ));
                }
            } catch (IOException e) {
                // Dosya oluşturulurken hata oluşursa, hata mesajı yazdırılır
                System.err.println("Dosya oluşturulurken hata oluştu: " + e.getMessage());
            }
        }
    }

    // Dosyadan kelimeleri okuma metodu
    public List<String> kelimeleriOku() {
        List<String> kelimeler = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dosyaAdi))) {
            String line;
            // Dosyanın her satırını okuma ve listeye ekleme
            while ((line = reader.readLine()) != null) {
                kelimeler.add(line.trim()); // Satırdaki boşlukları temizle ve listeye ekle
            }
        } catch (IOException e) {
            // Dosya okunurken hata oluşursa, hata mesajı yazdırılır
            System.err.println("Dosyadan kelime okunurken hata oluştu: " + e.getMessage());
        }
        // Okunan kelimeleri döndürüyoruz
        return kelimeler;
    }

    // Rastgele bir kelime seçme metodu
    public String rastgeleKelimeSec() {
        List<String> kelimeListesi = kelimeleriOku(); // Tüm kelimeleri oku
        if (kelimeListesi.isEmpty()) {
            return null; // Eğer kelime yoksa null döndürüyoruz
        }
        Random random = new Random();
        // Random sınıfı sayesinde rastgele kelime döndürüyoruz
        return kelimeListesi.get(random.nextInt(kelimeListesi.size()));
    }
}
