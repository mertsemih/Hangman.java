import javax.swing.*; // GUI bileşenlerini kullanmak için gerekli kütüphane
import java.awt.event.ActionEvent; // ActionEvent kullanımı için gerekli kütüphane
import java.awt.event.ActionListener; // ActionListener kullanımı için gerekli kütüphane
import java.io.*; // Dosya işlemleri için gerekli kütüphane
import java.util.HashSet; // Set veri yapısını kullanmak için gerekli kütüphane
import java.util.Set; // Set koleksiyonları için gerekli kütüphane
import java.util.List; // List veri yapısını kullanmak için gerekli kütüphane
import java.util.ArrayList; // ArrayList veri yapısını kullanmak için gerekli kütüphane


public class Oyun extends JFrame {
    private JPanel oyunPanel;
    private JLabel oyuncuIsmi;
    private JButton tahminEtButton;
    private JTextField tahminGirisi;
    private JLabel Uyari;
    private JLabel kullanilanHarfler;
    private JLabel Sure;
    private JLabel Kelime;
    private JLabel KalanHak;
    private JLabel Adam;
    private JLabel skorTablosu;
    private String hedefKelime;
    private int kalanHak;
    private final String[] asciiArt = {
            // Adam asmaca oyunundaki aşamaları temsil eden ASCII sanatları
            "<html><pre>\n\n\n\n\n\n</pre></html>",
            "<html><pre>__________</pre></html>",
            "<html><pre>   |\n   |\n   |\n   |\n   |\n___|______</pre></html>",
            "<html><pre>   _______\n   |/   |\n   |    O\n   |\n   |\n   |\n___|______</pre></html>",
            "<html><pre>   _______\n   |/   |\n   |    O\n   |    |\n   |\n   |\n___|______</pre></html>",
            "<html><pre>   _______\n   |/   |\n   |    O\n   |   /|\\\n   |\n   |\n___|______</pre></html>",
            "<html><pre>   _______\n   |/   |\n   |    O\n   |   /|\\\n   |   / \\\n   |\n___|______</pre></html>"
    };

    private StringBuilder gorunenKelime; // StringBuilder yeni nesne oluşturmaz,mevcut nesne üzerinden işlem yapar
    private StringBuilder kullanilanHarflerStr; // Kullanılan harflerin tutulduğu StringBuilder

    private Set<String> tahminEdilenHarfler = new HashSet<>(); // Kullanılan harflerin tutulduğu Set (tekrarlamayı engeller)
    private Timer timer;
    private int saniye;
    private boolean tekrarOynama;

    static class Oyuncu { // Oyuncu sınıfı
        String isim;
        int sure;
        int puan;

        Oyuncu(String isim, int sure, int puan) { // Oyuncu sınıfı yapıcı metodu
            this.isim = isim;
            this.sure = sure;
            this.puan = puan;
        }
    }
    public Oyun(String isim, String secilenZorluk, String kelime) { // Oyun sınıfının yapıcı metodu
        add(oyunPanel); // Oyun panelini ekliyoruz
        setTitle("Adam Asmaca - " + secilenZorluk); // Pencere başlığını, zorluk seviyesine göre ayarlıyoruz

        // Zorluk seviyesine göre kalan hak sayısını ayarlıyoruz
        kalanHak = switch (secilenZorluk) {
            case "Kolay" -> 6;
            case "Orta" -> 4;
            case "Zor" -> 2;
            default -> 6; // Varsayılan zorluk "Kolay" olarak belirlenmiştir
        };

        skorTablosunuGuncelle(); // Skor tablosunu güncelliyoruz

        hedefKelime = kelime.toLowerCase(); // Kelimeyi küçük harfe çeviriyoruz (case-sensitive olmaması için)
        gorunenKelime = new StringBuilder("_ ".repeat(hedefKelime.length()).toUpperCase()); // Kullanıcıya görünen kelimeyi boşluklarla oluşturuyoruz

        kullanilanHarflerStr = new StringBuilder(); // Kullanılan harflerin tutulacağı StringBuilder'ı oluşturuyoruz

        oyuncuIsmi.setText("Oyuncu: " + isim); // Oyuncu ismini mevcut etikete ekliyoruz
        KalanHak.setText("Kalan Hak: " + kalanHak); // Kalan hakları güncelliyoruz
        Kelime.setText(gorunenKelime.toString()); // Görünen kelimeyi mevcut etikete ekliyoruz
        Adam.setText(asciiArt[asciiArt.length - kalanHak - 1]); // Adam asmaca ASCII sanatını zorluk seviyesine göre ayarlıyoruz


        saniye = 0; // Süreyi 0'dan başlatıyoruz
        timer = new Timer(1000, new ActionListener() { // 1 saniyede bir tetiklenecek zamanlayıcıyı başlatıyoruz
            @Override
            public void actionPerformed(ActionEvent e) { // Her saniye tetiklenecek aksiyon
                saniye++; // Süreyi artırıyoruz
                Sure.setText("Süre: " + saniye + " saniye"); // Süreyi ekranda gösteriyoruz
            }
        });
        timer.start(); // Zamanlayıcıyı başlatıyoruz

        tahminGirisi.addActionListener(new ActionListener() { // Kullanıcının tahmin girişi alanı için dinleyici
            @Override
            public void actionPerformed(ActionEvent e) {
                tahminEtButton.doClick(); // Tahmin et butonuna tıklanmış gibi davranıyoruz
            }
        });

        // Tahmin butonu aksiyonu
        tahminEtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tahmin = tahminGirisi.getText().toLowerCase(); // Tahmin metnini küçük harfe çeviriyoruz
                tahminGirisi.setText(""); // Tahmin giriş alanını temizliyoruz

                if (tahmin.isEmpty()) {
                    Uyari.setText("Lütfen bir harf veya kelime giriniz."); // Uyarı mesajı gösteriyoruz
                    return;
                }


                if (tahminEdilenHarfler.contains(tahmin)) { // Eğer tahmin edilen harfler arasında varsa
                    Uyari.setText("Bu harfi zaten kullandınız!"); // Uyarı mesajı gösteriyoruz
                    return;
                }

                tahminEdilenHarfler.add(tahmin); // Yeni tahmin edilen harfi listeye ekliyoruz
                kullanilanHarflerStr.append(tahmin).append(" "); // Kullanılan harfler alanına harfi ekliyoruz
                kullanilanHarfler.setText(" " + kullanilanHarflerStr); // Kullanılan harfler etiketini güncelliyoruz

                // Kelime tamamlandı mı kontrolü
                if (tahmin.length() == 1) { // Eğer tahmin tek harfse
                    char harf = tahmin.charAt(0); // Tahmin edilen harfi alıyoruz
                    if (hedefKelime.contains(String.valueOf(harf))) { // Eğer hedef kelime harfi içeriyorsa
                        Uyari.setText("Doğru harf!"); // Doğru harf mesajı gösteriyoruz
                        doldurGorunenKelime(harf,secilenZorluk); // Görünen kelimeyi dolduruyoruz
                    } else { // Eğer harf yanlışsa
                        kalanHak--; // Kalan hakları bir azaltıyoruz
                        Adam.setText(asciiArt[asciiArt.length - kalanHak - 1]); // ASCII sanatını güncelliyoruz
                        KalanHak.setText("Kalan Hak: " + kalanHak); // Kalan haklar etiketini güncelliyoruz
                        Uyari.setText("Yanlış harf!"); // Yanlış harf mesajı gösteriyoruz
                    }
                } else { // Eğer tahmin tüm kelimeyi içeriyorsa
                    if (tahmin.equalsIgnoreCase(hedefKelime)) { // Eğer kelime doğruysa
                        gorunenKelime = new StringBuilder(hedefKelime.toUpperCase().replace("", " ").trim()); // Kelimeyi ekranda doğru olarak gösteriyoruz
                        Kelime.setText(gorunenKelime.toString()); // Görünen kelimeyi güncelliyoruz
                        JOptionPane.showMessageDialog(null, "Tebrikler! Kelimeyi doğru bildiniz!");
                        kaydet(saniye, secilenZorluk); // Skoru kaydediyoruz
                        oyunBitis(); // Oyun bitiş fonksiyonunu çağırıyoruz
                    } else { // Eğer kelime yanlışsa
                        kalanHak--; // Kalan hakları bir azaltıyoruz
                        Adam.setText(asciiArt[asciiArt.length - kalanHak - 1]); // ASCII sanatını güncelliyoruz
                        KalanHak.setText("Kalan Hak: " + kalanHak); // Kalan haklar etiketini güncelliyoruz
                        Uyari.setText("Yanlış kelime!"); // Yanlış kelime mesajı gösteriyoruz
                    }
                }

                // Kalan hak 0 olduysa oyunu bitirme
                if (kalanHak == 0) { // Eğer kalan hak 0 ise
                    JOptionPane.showMessageDialog(null, "Maalesef, kaybettiniz! Kelime: " + hedefKelime.toUpperCase()); // Kaybetme mesajı gösteriyoruz
                    try {
                        kaydet(saniye); // Skoru kaydediyoruz
                    } catch (DosyaOkumaHatasi ex) { // Dosya okuma hatası durumunda
                        throw new RuntimeException(ex); // Hata fırlatıyoruz
                    }
                    oyunBitis(); // Oyun bitiş fonksiyonunu çağırıyoruz
                }
            }
        });

        setExtendedState(JFrame.MAXIMIZED_BOTH); // Pencereyi tam ekran yapıyoruz
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Pencereyi kapattığınızda uygulamanın sonlanmaması için ayar
        setVisible(true); // Pencereyi görünür yapıyoruz
    }


    // Bu fonksiyon doğru tahmin edilen harflerin "gorunenKelime" içine eklenmesini sağlar.
    private void doldurGorunenKelime(char harf, String secilenZorluk) {
        harf = Character.toUpperCase(harf); // Harfi büyük yap, çünkü 'hedefKelime' küçük harflerle tutuluyor.
        for (int i = 0; i < hedefKelime.length(); i++) {
            // 'hedefKelime' içinde belirtilen harfi bulduğumuzda
            if (hedefKelime.charAt(i) == Character.toLowerCase(harf)) { // Küçük harfi kontrol et
                gorunenKelime.setCharAt(i * 2, harf); // Bu harfi ekranda gösterilen kelimede yerleştir
            }
        }
        Kelime.setText(gorunenKelime.toString()); // Güncellenmiş kelimeyi ekranda göster

        // Eğer kullanıcı kelimeyi doğru tahmin ettiyse
        if (gorunenKelime.toString().replace(" ", "").equalsIgnoreCase(hedefKelime)) { // Büyük/küçük harf duyarlılığını kaldırarak kontrol ediyoruz
            JOptionPane.showMessageDialog(null, "Tebrikler! Kelimeyi doğru bildiniz!"); // Kullanıcıya başarı mesajı gösteriyoruz
            kaydet( saniye, secilenZorluk); // Sonuçları kaydetme
            oyunBitis(); // Oyunu bitir
        }
    }

    // Bu fonksiyon oyuncu bilgilerini okuyarak sıralama yapar ve dosyaya yazar.
    private void siralaOyuncular() throws DosyaOkumaHatasi {
        List<Oyuncu> oyuncular = new ArrayList<>(); // Oyuncu bilgilerini tutacak liste

        // Dosyadaki oyuncu bilgilerini okuma
        try (BufferedReader reader = new BufferedReader(new FileReader("oyuncular.txt"))) {
            String satir;
            while ((satir = reader.readLine()) != null) {
                String[] parcalar = satir.split(" - "); // Satırı bölerek isim, süre, puan bilgilerini ayırdık
                if (parcalar.length == 3) {
                    String isim = parcalar[0]; // Oyuncu ismini al
                    int sure = Integer.parseInt(parcalar[1].replaceAll("\\D+", "")); // Süreyi al, "Süre: 45 saniye" gibi metni sadece sayıya dönüştürdük
                    int puan = Integer.parseInt(parcalar[2].replaceAll("\\D+", "")); // Puanı al, "Puan: 20" gibi metni sayıya dönüştürdük
                    oyuncular.add(new Oyuncu(isim, sure, puan)); // Oyuncuyu listeye ekle
                }
            }
        } catch (IOException e) {
            throw new DosyaOkumaHatasi("Dosya okunurken hata oluştu: " + e.getMessage());
        }

        // Sıralama işlemi: Önce puanları azalan sıraya göre, sonra süreyi artan sıraya göre sıralıyoruz
        oyuncular.sort((o1, o2) -> {
            if (o1.puan != o2.puan) {
                return Integer.compare(o2.puan, o1.puan); // Puanları büyükten küçüğe sıraladık
            }
            return Integer.compare(o1.sure, o2.sure); // Süreleri küçükten büyüğe sıraladık
        });

        // Sıralanmış oyuncu bilgilerini dosyaya yazdık
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("oyuncular.txt"))) {
            for (Oyuncu oyuncu : oyuncular) {
                writer.write(oyuncu.isim + " - Süre: " + oyuncu.sure + " saniye - Puan: " + oyuncu.puan); // Oyuncu bilgilerini dosyaya yazdık
                writer.newLine(); // Yeni satıra geçme
            }
        } catch (IOException e) {
            System.err.println("Dosya yazılırken hata oluştu: " + e.getMessage());
        }
    }

    // Skor tablosunu günceller ve ekranda gösterir.
    private void skorTablosunuGuncelle() {
        List<Oyuncu> oyuncular = new ArrayList<>();

        // Dosyadan oyuncu bilgilerini oku
        try (BufferedReader reader = new BufferedReader(new FileReader("oyuncular.txt"))) {
            String satir;
            while ((satir = reader.readLine()) != null) {
                String[] parcalar = satir.split(" - "); // Satırı parçalarına ayır
                if (parcalar.length == 3) {
                    String isim = parcalar[0];
                    int sure = Integer.parseInt(parcalar[1].replaceAll("\\D+", "")); // Süreyi sayıya çevirdik
                    int puan = Integer.parseInt(parcalar[2].replaceAll("\\D+", "")); // Puanı sayıya çevirdik
                    oyuncular.add(new Oyuncu(isim, sure, puan)); // Oyuncu bilgilerini listeye ekledik
                }
            }
        } catch (IOException e) {
            System.err.println("Dosya okunurken hata oluştu: " + e.getMessage());
            skorTablosu.setText("Skor tablosu yüklenemedi."); // Eğer dosya okunamazsa kullanıcıya hata mesajı gösteriyoruz
            return;
        }

        // Oyuncuları sıralama (puan ve süreye göre)
        oyuncular.sort((o1, o2) -> {
            if (o1.puan != o2.puan) {
                return Integer.compare(o2.puan, o1.puan); // Puanları büyükten küçüğe sıraladık
            }
            return Integer.compare(o1.sure, o2.sure); // Süreleri küçükten büyüğe sıraladık
        });

        // Skor tablosunu HTML formatında oluşturduk
        StringBuilder tablo = new StringBuilder("<html><body>");
        tablo.append("<div style='font-size:30px; text-align:center;'>Skor Tablosu</div><br>");

        // İlk 5 oyuncuyu göster
        for (int i = 0; i < Math.min(oyuncular.size(), 5); i++) { // Maksimum 5 oyuncuyu gösteriyoruz
            Oyuncu oyuncu = oyuncular.get(i);
            tablo.append((i + 1)).append(". ")
                    .append(oyuncu.isim).append(" - Süre: ")
                    .append(oyuncu.sure).append(" saniye - Puan: ")
                    .append(oyuncu.puan).append("<br>");
        }
        tablo.append("</body></html>");

        // JLabel'a verileri aktardık
        skorTablosu.setText(tablo.toString());
    }

    // Oyuncunun oyunu kaydetmesini sağlar (süre ve puan ile)
    private void kaydet(int saniye) throws DosyaOkumaHatasi {

        int puan = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("oyuncular.txt", true))) {
            writer.write(Giris.getIsim() + " - Süre: " + saniye + " saniye - Puan: " + puan ); // Oyuncu ismi, süre ve puanı dosyaya yazdık
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Sonuç kaydedilirken hata oluştu: " + e.getMessage());
        }

        try {
            siralaOyuncular(); // Oyuncuları sıralama fonksiyonu
        } catch (DosyaOkumaHatasi e) {
            throw new RuntimeException(e);
        }
    }

    // Oyuncunun skorunu, süresini ve zorluk seviyesini kaydeder.
    private void kaydet(int saniye, String secilenZorluk)  {
        int puan = 0;

        // Zorluk seviyesine göre puan hesaplaması yaptık
        switch (secilenZorluk) {
            case "Kolay":
                puan = 10 + (kalanHak * 15);
                break;
            case "Orta":
                puan = 20 + (kalanHak * 20);
                break;
            case "Zor":
                puan = 30 + (kalanHak * 35);
                break;
            default:
                break;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("oyuncular.txt", true))) {
            writer.write(Giris.getIsim() + " - Süre: " + saniye + " saniye - Puan: " + puan ); // Oyuncunun bilgilerini dosyaya yazdık
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Sonuç kaydedilirken hata oluştu: " + e.getMessage());
        }

        try {
            siralaOyuncular(); // Oyuncuları sıralama işlemi
        } catch (DosyaOkumaHatasi e) {
            throw new RuntimeException(e);
        }
    }

    // Oyunun bitişini yönetir ve kullanıcıya tekrar oynamak isteyip istemediğini sorar.
    private void oyunBitis() {
        if (!tekrarOynama) {
            // JOptionPane kullanarak kullanıcıya oyunun bitip bitmediğini soran bir onay penceresi açıyoruz
            int cevap = JOptionPane.showConfirmDialog(null, "Tekrar oynamak ister misiniz?", "Oyun Bitti", JOptionPane.YES_NO_OPTION);
            if (cevap == JOptionPane.YES_OPTION) {
                tekrarOynama = true;
                Giris girisPenceresi = new Giris(); // Yeni bir giriş penceresi oluşturarak başa dönüyoruz
                girisPenceresi.setVisible(true);
                dispose(); // Oyun penceresini kapatıyoruz
            } else {
                System.exit(0); // Uygulamayı kapat
            }
        }
    }
}


