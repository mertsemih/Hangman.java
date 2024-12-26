import javax.swing.*;  // javax.swing paketini içeri aktar, Swing bileşenleri için gerekli.

public class Main {
    public static void main(String[] args) {

        // Sistem görünümünü ayarladık
        try {
            // UIManager sınıfı, Java uygulamalarının görünümünü ayarlamak için kullanılır
            // Burada, uygulamanın görünümünü sistemin varsayılan görünümüne ayarladık
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();  // Hata izlerini yazdırır, böylece hata nedenini bulmak kolaylaşır.
        }

        // Swing bileşenlerinin düzgün bir şekilde çalışması için uygulama arka planda başlatılır.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                //Giris sınıfından bir nesne oluşturduk
                Giris giris = new Giris();

                // setVisible(true) çağrısı ile giriş penceresini ekranda görünür hale getirdik
                giris.setVisible(true);
            }
        });
    }
}
