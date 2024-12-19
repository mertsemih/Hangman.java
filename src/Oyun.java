import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

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

    private String hedefKelime; // Seçilen rastgele kelime
    private int kalanHak;
    private final String[] asciiArt = {
            "<html><pre>\n\n\n\n\n\n</pre></html>",
            "<html><pre>__________</pre></html>",
            "<html><pre>   |\n   |\n   |\n   |\n   |\n___|______</pre></html>",
            "<html><pre>   _______\n   |/   |\n   |    O\n   |\n   |\n   |\n___|______</pre></html>",
            "<html><pre>   _______\n   |/   |\n   |    O\n   |    |\n   |\n   |\n___|______</pre></html>",
            "<html><pre>   _______\n   |/   |\n   |    O\n   |   /|\\\n   |\n   |\n___|______</pre></html>",
            "<html><pre>   _______\n   |/   |\n   |    O\n   |   /|\\\n   |   / \\\n   |\n___|______</pre></html>"
    };

    private StringBuilder gorunenKelime; // Kullanıcının gördüğü _ _ _ _
    private StringBuilder kullanilanHarflerStr;

    private Set<String> tahminEdilenHarfler = new HashSet<>(); // Kullanılan harfleri tutar
    private Timer timer;
    private int saniye;
    private boolean tekrarOynama; // Tekrar oynamak isteyip istemediğini kontrol eden flag

    public Oyun(String isim, String secilenZorluk, String kelime) {
        add(oyunPanel);
        setTitle("Adam Asmaca - " + secilenZorluk);

        // Kalan hakları zorluk seviyesine göre belirle
        kalanHak = switch (secilenZorluk) {
            case "Kolay" -> 6;
            case "Orta" -> 4;
            case "Zor" -> 2;
            default -> 6;
        };

        hedefKelime = kelime.toLowerCase(); // Büyük/küçük harf uyumu için
        gorunenKelime = new StringBuilder("_ ".repeat(hedefKelime.length()));
        kullanilanHarflerStr = new StringBuilder();

        oyuncuIsmi.setText("Oyuncu: " + isim);
        KalanHak.setText("Kalan Hak: " + kalanHak);
        Kelime.setText(gorunenKelime.toString());
        Adam.setText(asciiArt[asciiArt.length - kalanHak - 1]);

        // Süreyi başlat
        saniye = 0;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saniye++;
                Sure.setText("Süre: " + saniye + " saniye");
            }
        });
        timer.start();


        tahminGirisi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tahminEtButton.doClick(); // Tahmin et butonunun işlevini çalıştır
            }
        });

        // Tahmin butonu aksiyonu
        tahminEtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tahmin = tahminGirisi.getText().toLowerCase();
                tahminGirisi.setText("");

                if (tahmin.isEmpty()) {
                    Uyari.setText("Lütfen bir harf veya kelime giriniz.");
                    return;
                }



                // Aynı harf kullanımı kontrolü
                if (tahminEdilenHarfler.contains(tahmin)) {
                    Uyari.setText("Bu harfi zaten kullandınız!");
                    return;
                }

                tahminEdilenHarfler.add(tahmin); // Yeni harfi listeye ekle
                kullanilanHarflerStr.append(tahmin).append(" ");
                kullanilanHarfler.setText(" " + kullanilanHarflerStr);

                // Kelime tamamlandı mı kontrolü
                if (tahmin.length() == 1) {
                    char harf = tahmin.charAt(0);
                    if (hedefKelime.contains(String.valueOf(harf))) {
                        Uyari.setText("Doğru harf!");
                        doldurGorunenKelime(harf,secilenZorluk);
                    } else {
                        kalanHak--;
                        Adam.setText(asciiArt[asciiArt.length - kalanHak - 1]);
                        KalanHak.setText("Kalan Hak: " + kalanHak);
                        Uyari.setText("Yanlış harf!");
                    }
                } else {
                    // Tüm kelimeyi tahmin ederse
                    if (tahmin.equals(hedefKelime)) {
                        gorunenKelime = new StringBuilder(hedefKelime.replace("", " ").trim());
                        Kelime.setText(gorunenKelime.toString());
                        JOptionPane.showMessageDialog(null, "Tebrikler! Kelimeyi doğru bildiniz!");
                        kaydet(isim, saniye, secilenZorluk); // Doğru tahmin olduğu için kaydet
                        oyunBitis(isim);
                    } else {
                        kalanHak--;
                        Adam.setText(asciiArt[asciiArt.length - kalanHak - 1]);
                        KalanHak.setText("Kalan Hak: " + kalanHak);
                        Uyari.setText("Yanlış kelime!");
                    }
                }

                // Kalan hak 0 olduysa oyunu bitir
                if (kalanHak == 0) {
                    JOptionPane.showMessageDialog(null, "Maalesef, kaybettiniz! Kelime: " + hedefKelime);
                    kaydet(isim, saniye, " ");
                    oyunBitis(isim);
                }
            }
        });

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    static class Oyuncu {
        String isim;
        int sure;
        int puan;

        Oyuncu(String isim, int sure, int puan) {
            this.isim = isim;
            this.sure = sure;
            this.puan = puan;
        }
    }

    private void doldurGorunenKelime(char harf,String secilenZorluk) {
        for (int i = 0; i < hedefKelime.length(); i++) {
            if (hedefKelime.charAt(i) == harf) {
                gorunenKelime.setCharAt(i * 2, harf);
            }
        }
        Kelime.setText(gorunenKelime.toString());
        if (gorunenKelime.toString().replace(" ", "").equals(hedefKelime)) {
            JOptionPane.showMessageDialog(null, "Tebrikler! Kelimeyi doğru bildiniz!");
            kaydet(oyuncuIsmi.getText(), saniye,secilenZorluk); // İsim, süre ve zorluk seviyesini kaydet
            oyunBitis(oyuncuIsmi.getText());
        }
    }

    private void siralaOyuncular() {
        List<Oyuncu> oyuncular = new ArrayList<>();

        // Dosyadan oyuncu bilgilerini oku
        try (BufferedReader reader = new BufferedReader(new FileReader("oyuncular.txt"))) {
            String satir;
            while ((satir = reader.readLine()) != null) {
                String[] parcalar = satir.split(" - ");
                if (parcalar.length == 3) {
                    String isim = parcalar[0];
                    int sure = Integer.parseInt(parcalar[1].replaceAll("\\D+", "")); // "Süre: 45 saniye" -> 45
                    int puan = Integer.parseInt(parcalar[2].replaceAll("\\D+", "")); // "Puan: 20" -> 20
                    oyuncular.add(new Oyuncu(isim, sure, puan));
                }
            }
        } catch (IOException e) {
            System.err.println("Dosya okunurken hata oluştu: " + e.getMessage());
            return;
        }

        // Sıralama: önce puana göre azalan, sonra süreye göre artan
        oyuncular.sort((o1, o2) -> {
            if (o1.puan != o2.puan) {
                return Integer.compare(o2.puan, o1.puan); // Puan büyükten küçüğe
            }
            return Integer.compare(o1.sure, o2.sure); // Süre küçükten büyüğe
        });

        // Sıralanmış oyuncuları dosyaya yaz
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("oyuncular.txt"))) {
            for (Oyuncu oyuncu : oyuncular) {
                writer.write(oyuncu.isim + " - Süre: " + oyuncu.sure + " saniye - Puan: " + oyuncu.puan);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Dosya yazılırken hata oluştu: " + e.getMessage());
        }
    }




    private void kaydet(String isim, int saniye,String secilenZorluk) {

        int puan = 0;

        switch (secilenZorluk) {
            case "Kolay":
                puan=10+(kalanHak*15);
                break;
            case "Orta":
                puan=20+(kalanHak*20);
                break;
            case "Zor":
                puan=30+(kalanHak*35);
                break;
            default:
        };


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("oyuncular.txt", true))) {
            writer.write(isim + " - Süre: " + saniye + " saniye - Puan: " + puan );
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Sonuç kaydedilirken hata oluştu: " + e.getMessage());
        }
        siralaOyuncular();

    }

    private void oyunBitis(String isim) {

        if (!tekrarOynama) { // Bir kere sadece "Tekrar oynamak ister misiniz?" sorusu sorsun
            int cevap = JOptionPane.showConfirmDialog(null, "Tekrar oynamak ister misiniz?", "Oyun Bitti", JOptionPane.YES_NO_OPTION);
            if (cevap == JOptionPane.YES_OPTION) {
                tekrarOynama = true; // Artık tekrar oynamak isteyip istemediğini sormayacak
                Giris girisPenceresi = new Giris();
                girisPenceresi.setVisible(true);
                dispose();
            } else {
                System.exit(0); // Uygulamayı kapat
            }
        }
    }
}