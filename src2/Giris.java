import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Giris extends JFrame {
    private JTextField yazi1;
    private JButton girisButton;
    private JPanel GirisPanel;
    private String isim;

    public Giris() {
        add(GirisPanel);
        setTitle("Giris");
        setSize(400, 200);
        setLocationRelativeTo(null); // Ekranı ortada başlat
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        girisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isim = yazi1.getText(); // Kullanıcı ismini al
                if (isim != null && !isim.trim().isEmpty()) {
                    // Zorluk penceresini aç
                    Zorluk zorluk = new Zorluk(isim); // İsim ile Zorluk penceresine geç
                    zorluk.setVisible(true);
                    dispose(); // Giris penceresini kapat
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen isim giriniz.");
                }
            }
        });
    }

    public String getIsim() {
        return isim;
    }
}
