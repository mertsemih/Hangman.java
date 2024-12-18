import javax.swing.*;

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
    private JTextArea textArea1;

    public Oyun(String isim,String secilenZorluk) {
        // JPanel ve JLabel tanımları
        add(oyunPanel);
        setTitle("Adam Asmaca - " + secilenZorluk);

        // Mevcut yazı ve kullanıcıdan gelen veriyi birleştir
        String mevcutYazi = oyuncuIsmi.getText(); // Swing Designer'dan hazır eklenen metin
        oyuncuIsmi.setText(mevcutYazi + "\n" + isim); // Mevcut yazının yanına kullanıcı adını ekle

        // Tam ekran yapmak için
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true); // Pencereyi görünür yap
    }

}
