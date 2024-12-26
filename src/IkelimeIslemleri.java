import java.util.List;

// Bu arayüz, kelime işlemleri için gerekli temel metotları tanımlar.
// Kelime ekleme, kelimeleri okuma gibi işlemler için uygulanması gereken metodları içerir.
public interface IkelimeIslemleri {

    // Kelime ekleme işlemi için metod.
    void kelimeEkle(String kelime);

    // Kelimeleri okuma işlemi için metod.
    List<String> kelimeleriOku();
}
