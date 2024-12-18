import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Zorluk extends JFrame {
    private JPanel zorlukPanel;    // Ana panel
    private JComboBox<String> secim; // Seçim kutusu (JComboBox)
    private JButton devamButton;    // Devam butonu

    private String secilenZorluk;  // Seçilen zorluk seviyesini saklayacak değişke

    public Zorluk(String isim) {
        // GUI designer tarafından eklenmiş olan bileşenlerinizi burada kullanıyorsunuz.
        // secim JComboBox'ı GUI designer tarafından eklenmiş olacak.

        add(zorlukPanel);
        setTitle("Zorluk Seçimi");
        setSize(400, 200);
        setLocationRelativeTo(null); // Ekranı ortada başlat
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Burada secim JComboBox'a seçenekleri ekliyoruz:
        secim.addItem("Kolay");
        secim.addItem("Orta");
        secim.addItem("Zor");

        // Devam butonunun aksiyonu
        devamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Seçilen zorluk seviyesini al
                secilenZorluk = (String) secim.getSelectedItem();
                if (secilenZorluk != null) {
                    // Zorluk penceresini aç
                    Oyun oyun = new Oyun(isim,secilenZorluk); // İsim ile Zorluk penceresine geç
                    oyun.setVisible(true);
                    dispose(); // Giris penceresini kapat
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen bir zorluk seviyesi seçin.");
                }
            }
        });
    }
}
