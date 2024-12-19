import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KelimeYonetimi extends KelimeIslemleri {
    private String dosyaAdi = "kelimeler.txt"; // Dosya adı

    public KelimeYonetimi() {
        // Sınıf oluşturulurken dosya kontrol edilir ve yoksa oluşturulur.
        dosyayiKontrolEtVeOlustur();
    }

    @Override
    public void kelimeEkle(String kelime) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaAdi, true))) {
            writer.write(kelime);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Kelime eklenirken hata oluştu: " + e.getMessage());
        }
    }

    public void kelimeleriListeIleEkle(List<String> kelimeListesi) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaAdi, true))) {
            for (String kelime : kelimeListesi) {
                writer.write(kelime);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Liste dosyaya yazılırken hata oluştu: " + e.getMessage());
        }
    }

    public void dosyayiKontrolEtVeOlustur() {
        File dosya = new File(dosyaAdi);
        if (!dosya.exists()) {
            try {
                if (dosya.createNewFile()) {
                    System.out.println("Kelime dosyası oluşturuldu: " + dosyaAdi);
                    // Başlangıç kelimelerini dosyaya yaz
                    kelimeleriListeIleEkle(List.of("merhaba", "dünya", "java", "kodlama", "adam asmaca", "ağaç"));
                }
            } catch (IOException e) {
                System.err.println("Dosya oluşturulurken hata oluştu: " + e.getMessage());
            }
        } else {
            System.out.println("Kelime dosyası zaten mevcut: " + dosyaAdi);
        }
    }

    public List<String> kelimeleriOku() {
        List<String> kelimeler = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dosyaAdi))) {
            String line;
            while ((line = reader.readLine()) != null) {
                kelimeler.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Dosyadan kelime okunurken hata oluştu: " + e.getMessage());
        }
        return kelimeler;
    }

    public String rastgeleKelimeSec() {
        List<String> kelimeListesi = kelimeleriOku(); // Tüm kelimeleri oku
        if (kelimeListesi.isEmpty()) {
            return null; // Eğer kelime yoksa null döner
        }
        Random random = new Random();
        return kelimeListesi.get(random.nextInt(kelimeListesi.size())); // Rastgele kelime seç
    }
}
