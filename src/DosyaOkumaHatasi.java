// Dosya okuma hataları için özel bir exception sınıfı
// Bu sınıf, dosya okuma işlemi sırasında oluşan hataları yakalamak ve yönetmek amacıyla kullanılır.
public class DosyaOkumaHatasi extends Exception {

    // Constructor ile hata mesajını alıyoruz ve süper sınıfa ileterek exception nesnesi oluşturuyoruz
    public DosyaOkumaHatasi(String message) {
        super(message);  // Exception sınıfının constructor'ına hata mesajını iletiyoruz
    }
}
