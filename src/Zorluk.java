import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Zorluk extends JFrame {
    private JPanel zorlukPanel;
    private JComboBox<String> secim;
    private JButton devamButton;

    private String secilenZorluk;

    public Zorluk(String isim) {
        add(zorlukPanel);
        setTitle("Zorluk Seçimi");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        secim.addItem("Kolay");
        secim.addItem("Orta");
        secim.addItem("Zor");

        zorlukPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "devam");
        zorlukPanel.getActionMap().put("devam", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                devamButton.doClick(); // Buton işlevini çalıştır
            }
        });

        devamButton.addActionListener(e -> {
            secilenZorluk = (String) secim.getSelectedItem();
            if (secilenZorluk != null) {
                KelimeYonetimi kelimeYonetimi = new KelimeYonetimi();
                String rastgeleKelime = kelimeYonetimi.rastgeleKelimeSec();
                if (rastgeleKelime != null) {
                    // Oyun penceresini aç
                    Oyun oyun = new Oyun(isim, secilenZorluk, rastgeleKelime);
                    oyun.setVisible(true);
                    dispose(); // Zorluk penceresini kapat
                } else {
                    JOptionPane.showMessageDialog(null, "Kelime dosyası boş!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Lütfen bir zorluk seviyesi seçin.");
            }
        });
    }
}

