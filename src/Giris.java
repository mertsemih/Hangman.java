import javax.swing.*;  // javax.swing paketini içeri aktar, GUI bileşenlerini kullanabilmek için.
import java.awt.event.ActionEvent;  // Aksiyon olayları (örneğin, buton tıklamaları) için gerekli sınıf.
import java.awt.event.ActionListener;  // Aksiyon olaylarını dinlemek için gerekli arayüz.

public class Giris extends JFrame {  // Giris sınıfı, JFrame (ana pencere) sınıfından türetiliyor.
    private JTextField yazi1;
    private JButton girisButton;
    private JPanel GirisPanel;
    private static String isim;

    public Giris() {
        add(GirisPanel);  // GirisPanel bileşenini pencereye ekledik
        setTitle("Giris");  // Pencerenin başlığını "Giris" olarak ayarladık
        setSize(400, 200);  // Pencerenin boyutlarını 400x200 piksel olarak ayarladık
        setLocationRelativeTo(null);  // Pencereyi ekranın ortasında başlattık
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Pencere kapatıldığında programı sonlandırma

        // Enter tuşuna basıldığında buton işlevini çalıştırma
        yazi1.addActionListener(new ActionListener() {  // yazi1 üzerinde bir aksiyon dinleyicisi ekledik
            @Override
            public void actionPerformed(ActionEvent e) {
                girisButton.doClick();  // Enter tuşuna basıldığında girisButton butonunun tıklanmasını sağladık
            }
        });

        // Giriş butonuna tıklama olayını dinleme
        girisButton.addActionListener(new ActionListener() {  // girisButton üzerinde bir aksiyon dinleyicisi ekledik
            @Override
            public void actionPerformed(ActionEvent e) {
                isim = yazi1.getText();  // yazi1 içine yazılan metni isim değişkenine atadık
                if (isim != null && !isim.trim().isEmpty()) {

                    Zorluk zorluk = new Zorluk(isim);  // Zorluk sınıfına ismi göndererek yeni bir nesne oluşturduk
                    zorluk.setVisible(true);
                    dispose();  // Giris penceresini kapatma
                } else {
                    // Eğer isim girilmezse kullanıcıya hata mesajı gösterme
                    JOptionPane.showMessageDialog(null, "Lütfen isim giriniz.");
                }
            }
        });
    }

    // İsim bilgisini diğer sınıflarda kullanmak için getter metodunu ekledik
    public static String getIsim() {
        return isim;  // Girilen ismi geri döndür.
    }
}
