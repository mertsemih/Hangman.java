import javax.swing.*;  // javax.swing paketini içeri aktar, GUI bileşenlerini kullanabilmek için.
import java.awt.event.ActionEvent;  // Aksiyon olayları (örneğin, buton tıklamaları) için gerekli sınıf.
import java.awt.event.ActionListener;  // Aksiyon olaylarını dinlemek için gerekli arayüz.

public class Zorluk extends JFrame {
    private JPanel zorlukPanel;
    private JComboBox<String> secim;
    private JButton devamButton;

    private String secilenZorluk;

    public Zorluk(String isim) {  // Zorluk sınıfının yapıcı metodu
        add(zorlukPanel);  // zorlukPanel'i pencereye ekledik
        setTitle("Zorluk Seçimi");  // Pencerenin başlığını "Zorluk Seçimi" olarak ayarladık
        setSize(400, 200);  // Pencerenin boyutlarını 400x200 piksel olarak ayarladık
        setLocationRelativeTo(null);  // Pencereyi ekranın ortasında başlattık
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Pencere kapatıldığında programı sonlandırma fonksiyonu

        // ComboBox'a zorluk seviyelerini ekledik
        secim.addItem("Kolay");
        secim.addItem("Orta");
        secim.addItem("Zor");

        // Kullanıcı "Enter" tuşuna bastığında butonun işlevini tetiklemesini sağladık
        zorlukPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "devam");
        // ActionMap ile "devam" adlı bir aksiyon tanımlıyoruz.
        zorlukPanel.getActionMap().put("devam", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                devamButton.doClick();  // "Enter" tuşuna basıldığında devam butonunun işlevini çalıştırır
            }
        });

        // Devam butonuna tıklanma olayını dinleme
        devamButton.addActionListener(e -> {
            secilenZorluk = (String) secim.getSelectedItem();  // ComboBox'dan seçilen zorluk seviyesini aldık
            if (secilenZorluk != null) {
                KelimeYonetimi kelimeYonetimi = new KelimeYonetimi();  // Kelimeleri yönetecek yeni bir nesne oluşturduk
                String rastgeleKelime = kelimeYonetimi.rastgeleKelimeSec();  // Kelime yöneticisi ile rastgele bir kelime seçtik
                if (rastgeleKelime != null) {
                    Oyun oyun = new Oyun(isim, secilenZorluk, rastgeleKelime);  // Oyun sınıfından kullanıcı ismi, zorluk ve kelimeyi alan bie nesne oluşturduk
                    oyun.setVisible(true);
                    dispose();  // Zorluk penceresini kapatma
                } else {
                    // Eğer kelime dosyasından kelime alınamazsa hata mesajı gösterme
                    JOptionPane.showMessageDialog(null, "Kelime dosyası boş!");
                }
            } else {
                // Eğer bir zorluk seviyesi seçilmemişse  kullanıcıya hata mesajı gösterme
                JOptionPane.showMessageDialog(null, "Lütfen bir zorluk seviyesi seçin.");
            }
        });
    }

}
