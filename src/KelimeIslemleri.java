import java.io.*;
import java.util.ArrayList;
import java.util.List;

// 'KelimeIslemleri' sınıfı, kelime ekleme ve okuma işlemlerini gerçekleştiren bir soyut sınıftır.
// Bu sınıf, 'IkelimeIslemleri' arayüzünü implement eder.
public abstract class KelimeIslemleri implements IkelimeIslemleri {
    protected final String dosyaAdi = "kelimeler.txt";

    // Kelime ekleme metodu (soyut)
    // Kelime eklemek için kullanılır fakat nasıl ekleneceği bu sınıfta belirlenmez.
    @Override
    public abstract void kelimeEkle(String kelime);

    // Tüm kelimeleri dosyadan okuma metodu
    // Bu metod kelimeler.txt dosyasındaki tüm kelimeleri okur ve bir liste olarak döndürür.
    @Override
    public List<String> kelimeleriOku() {
        // Kelimeleri tutacak bir liste oluşturuyoruz
        List<String> kelimeListesi = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dosyaAdi))) {
            String satir;
            // Dosyanın her bir satırını okuyoruz ve listeye ekliyoruz
            while ((satir = reader.readLine()) != null) {
                kelimeListesi.add(satir.trim()); // Satırdaki boşlukları temizliyoruz ve listeye ekliyoruz
            }
        } catch (IOException e) {
            // Dosya okunurken herhangi bir hata oluşursa, hata mesajı yazdırılır
            System.err.println("Dosya okunurken hata oluştu: " + e.getMessage());
        }
        // Okunan kelimeleri liste olarak döndür
        return kelimeListesi;
    }
}
