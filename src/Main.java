import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Sistem görünümünü ayarla
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Look and Feel ayarlanırken hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Giriş penceresini oluştur ve göster
                Giris giris = new Giris();
                giris.setVisible(true);
            }
        });
    }
}
