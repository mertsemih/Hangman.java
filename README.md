# Adam Asmaca

Bu proje, klasik Adam Asmaca oyununu Java dilinde geliştirilmiş bir sürümüdür. Oyun, kullanıcıların kelimeleri tahmin etmeye çalıştığı ve yanlış tahminler sonucunda "adam"ın asılmasını engellemeye çalıştığı eğlenceli bir deneyim sunar.

## Özellikler

- Farklı zorluk seviyeleri (Kolay, Orta, Zor).
- Kullanıcı giriş sistemi.
- Kelime veritabanından rastgele kelime seçimi.
- Oyuncu performans kaydı.
- Grafik arayüz.

## Gereksinimler

- **Java 8** veya daha üstü bir sürüm
- IDE (IntelliJ IDEA veya Eclipse önerilir) ya da terminal

## Kurulum ve Çalıştırma

1. Bu projeyi indirin veya klonlayın:
   ```bash
   git clone <repository-url>
   ```
2. Proje dosyalarını IDE'nize veya terminalinize yükleyin.

3. Gereken bağımlılıkların doğru şekilde yüklendiğinden emin olun.

4. Ana sınıf olan `Main.java` dosyasını çalıştırarak oyunu başlatın.

## Proje Yapısı

- **src/**: Kaynak kodlar
  - `Main.java`: Oyunun başlangıç noktası.
  - `Giris.java`: Kullanıcı giriş işlemleri.
  - `Oyun.java`: Oyun mantığının uygulandığı sınıf.
  - `Zorluk.java`: Zorluk seviyeleriyle ilgili işlemler.
  - `KelimeIslemleri.java`: Kelime yönetimi.
- **kelimeler.txt**: Oyun sırasında kullanılan kelimelerin saklandığı dosya.
- **oyuncular.txt**: Oyuncu performanslarının kaydedildiği dosya.

## Oyun Görselleri

Aşağıda oyundan birkaç ekran görüntüsü bulunmaktadır:

![Oyun Başlangıç Ekranı](images/start_screen.png)

![Kelime Tahmin Ekranı](images/guess_screen.png)

![Oyun Sonu Ekranı](images/game_over_screen.png)
