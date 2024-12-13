
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AdamAsmacaSwingGame {
    private JFrame frame;
    private JLabel kelimeLabel, kalanHakLabel, mesajLabel, sureLabel, cizimLabel;
    private JTextField tahminField;
    private JButton tahminButton;
    private KelimeOyunu oyun;
    private SkorTahtasi skorTahtasi;
    private int kalanHak;
    private Instant baslangicZamani;
    private int puan;
    private final String[] asciiArt = {
            "<html><pre>\n\n\n\n\n\n</pre></html>",
            "<html><pre>\n\n\n\n\n__________</pre></html>",
            "<html><pre>   |\n   |\n   |\n   |\n   |\n___|______</pre></html>",
            "<html><pre>   _______\n   |/   |\n   |    O\n   |\n   |\n   |\n___|______</pre></html>",
            "<html><pre>   _______\n   |/   |\n   |    O\n   |    |\n   |\n   |\n___|______</pre></html>",
            "<html><pre>   _______\n   |/   |\n   |    O\n   |   /|\\\n   |\n   |\n___|______</pre></html>",
            "<html><pre>   _______\n   |/   |\n   |    O\n   |   /|\\\n   |   / \\\n   |\n___|______</pre></html>"
    };

    public void startGame() throws IOException {
        ensureKelimeDosyasi();
        String oyuncuIsmi = JOptionPane.showInputDialog(null,"Lütfen isminizi girin:","Giriş",JOptionPane.QUESTION_MESSAGE);
        if (oyuncuIsmi == null || oyuncuIsmi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Oyuncu ismi gerekli. Oyun kapatılıyor.","Bilgilendirme",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        String[] zorluklar = {"Kolay", "Orta", "Zor"};
        String zorluk = (String) JOptionPane.showInputDialog(null, "Zorluk seviyesini seçin:",
                "Zorluk Seçimi", JOptionPane.QUESTION_MESSAGE, null, zorluklar, zorluklar[0]);
        if (zorluk == null) {
            JOptionPane.showMessageDialog(null, "Zorluk seçimi yapılmadı. Oyun kapatılıyor.","Bilgilendirme",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        int maxHak = switch (zorluk) {
            case "Kolay" -> 6;
            case "Orta" -> 4;
            default -> 2;
        };
        puan = switch (zorluk) {
            case "Kolay" -> 10;
            case "Orta" -> 20;
            default -> 30;
        };

        String kelime = rastgeleKelimeSec();
        oyun = new KelimeOyunu(kelime, maxHak);
        skorTahtasi = new SkorTahtasi();
        kalanHak = oyun.getMaxTahminSayisi();

        frame = new JFrame("Adam Asmaca");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(7, 1));

        kelimeLabel = new JLabel("Tahmin Edilecek Kelime: " + oyun.getTahminEdilen(), JLabel.CENTER);
        kalanHakLabel = new JLabel("Kalan Hak: " + kalanHak, JLabel.CENTER);
        mesajLabel = new JLabel("", JLabel.CENTER);
        sureLabel = new JLabel("", JLabel.CENTER);
        cizimLabel = new JLabel(asciiArt[0], JLabel.CENTER);
        tahminField = new JTextField();
        tahminField.setFont(new Font("Arial", Font.PLAIN, 18));
        tahminButton = new JButton("Tahmin Yap");

        tahminButton.addActionListener(new TahminDinleyici(oyuncuIsmi));

        frame.add(kelimeLabel);
        frame.add(kalanHakLabel);
        frame.add(cizimLabel);
        frame.add(tahminField);
        frame.add(tahminButton);
        frame.add(mesajLabel);
        frame.add(sureLabel);

        frame.setSize(1920, 1080);
        frame.setVisible(true);

        baslangicZamani = Instant.now();
    }

    private void ensureKelimeDosyasi() throws IOException {
        Path dosyaYolu = Path.of("kelimeler.txt");
        if (!Files.exists(dosyaYolu)) {
            try (BufferedWriter writer = Files.newBufferedWriter(dosyaYolu)) {
                writer.write("java\nprogramlama\nbilgisayar\noyun\nkapsülleme\n");
            }
        }
    }

    private String rastgeleKelimeSec() throws IOException {
        List<String> kelimeler = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Path.of("kelimeler.txt"))) {
            kelimeler = reader.lines().collect(Collectors.toList());
        }
        if (kelimeler.isEmpty()) {
            throw new IOException("Kelime dosyası boş. Lütfen kelimeler ekleyin.");
        }
        return kelimeler.get(new Random().nextInt(kelimeler.size()));
    }

    private void skorKaydet(String oyuncuIsmi, int skor, Duration sure) throws IOException {
        Path dosyaYolu = Path.of("liste.txt");
        List<String> mevcutSkorlar = new ArrayList<>();

        if (Files.exists(dosyaYolu)) {
            try (BufferedReader reader = Files.newBufferedReader(dosyaYolu)) {
                mevcutSkorlar = reader.lines().collect(Collectors.toList());
            }
        }

        mevcutSkorlar.add(String.format("%s,%d,%d:%02d", oyuncuIsmi, skor, sure.toMinutes(), sure.getSeconds() % 60));
        mevcutSkorlar.sort((s1, s2) -> Integer.compare(Integer.parseInt(s2.split(",")[1]), Integer.parseInt(s1.split(",")[1])));

        try (BufferedWriter writer = Files.newBufferedWriter(dosyaYolu)) {
            for (String satir : mevcutSkorlar) {
                writer.write(satir + "\n");
            }
        }
    }

    private class TahminDinleyici implements ActionListener {
        private final String oyuncuIsmi;

        public TahminDinleyici(String oyuncuIsmi) {
            this.oyuncuIsmi = oyuncuIsmi;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String tahmin = tahminField.getText().trim();
                if (tahmin.isEmpty() || !tahmin.matches("[a-zA-ZığüşöçİĞÜŞÖÇ]+")) {
                    mesajLabel.setText("Geçersiz tahmin. Harf veya kelime giriniz.");
                    return;
                }

                if (tahmin.length() == 1) {
                    char harf = tahmin.charAt(0);

                    if (oyun.tahminYap(harf)) {
                        kelimeLabel.setText("Tahmin Edilecek Kelime: " + oyun.getTahminEdilen());
                        mesajLabel.setText("Doğru tahmin!");
                    } else {
                        kalanHak--;
                        kalanHakLabel.setText("Kalan Hak: " + kalanHak);
                        mesajLabel.setText("Yanlış tahmin!");
                        cizimLabel.setText(asciiArt[Math.min(asciiArt.length - 1, oyun.getMaxTahminSayisi() - kalanHak)]);
                    }
                } else {
                    if (tahmin.equalsIgnoreCase(oyun.getGizliKelime())) {
                        kelimeLabel.setText("Tahmin Edilecek Kelime: " + oyun.getGizliKelime());
                        bitirOyun(true);
                        return;
                    } else {
                        kalanHak--;
                        kalanHakLabel.setText("Kalan Hak: " + kalanHak);
                        mesajLabel.setText("Yanlış tahmin! Kelime yanlış.");
                        cizimLabel.setText(asciiArt[Math.min(asciiArt.length - 1, oyun.getMaxTahminSayisi() - kalanHak)]);
                    }
                }

                if (oyun.getTahminEdilen().equals(oyun.getGizliKelime())) {
                    bitirOyun(true);
                } else if (kalanHak <= 0) {
                    bitirOyun(false);
                }
            } catch (Exception ex) {
                mesajLabel.setText("Hata: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        private void bitirOyun(boolean kazandi) {
            tahminField.setEnabled(false);
            tahminButton.setEnabled(false);
            Instant bitisZamani = Instant.now();
            Duration sure = Duration.between(baslangicZamani, bitisZamani);

            try {
                if (kazandi) {
                    mesajLabel.setText("Tebrikler! Kelimeyi bildiniz.");
                    skorTahtasi.skorArtir(puan);
                } else {
                    mesajLabel.setText("Üzgünüm, kelime: " + oyun.getGizliKelime());
                    cizimLabel.setText(asciiArt[asciiArt.length - 1]);
                }
                skorKaydet(oyuncuIsmi, skorTahtasi.getSkor(), sure);
                JOptionPane.showMessageDialog(frame, "Oyun bitti! Skorunuz kaydedildi.","Bilgilendirme",JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                mesajLabel.setText("Skor kaydı sırasında hata oluştu.");
                ex.printStackTrace();
            }
        }
    }
}
