import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // ASCII Art aşamaları
        String[] hangmanStages = {
                """
              +---+
                  |
                  |
                  |
                 ===
            """,
                """
              +---+
              O   |
                  |
                  |
                 ===
            """,
                """
              +---+
              O   |
              |   |
                  |
                 ===
            """,
                """
              +---+
              O   |
             /|   |
                  |
                 ===
            """,
                """
              +---+
              O   |
             /|\\  |
                  |
                 ===
            """,
                """
              +---+
              O   |
             /|\\  |
             /    |
                 ===
            """,
                """
              +---+
              O   |
             /|\\  |
             / \\  |
                 ===
            """
        };

        // Dosyadan kelime seç
        String dosyaAdi = "C:\\Users\\semih\\IdeaProjects\\adamAsmaca\\src\\kelimeler.txt";
        String rastgeleKelime = KelimeSecici.rastgeleKelimeSec(dosyaAdi);

        // Oyuncuya zorluk seviyesi sor
        Scanner scanner = new Scanner(System.in);
        System.out.println("Zorluk seviyesini seçin:");
        System.out.println("1 - Kolay (10 hak)");
        System.out.println("2 - Zor (2 hak)");
        System.out.println("3 - Orta (6 hak)");

        int choice = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Lütfen 1 , 2  veya 3arasında bir seçim yapın.");
                choice = scanner.nextInt();
                scanner.nextLine(); // Giriş temizleme
                if (choice == 1 || choice == 2 || choice == 3) {
                    validInput = true;
                } else {
                    System.out.println("Lütfen 1 , 2  veya 3arasında bir seçim yapın.");
                }
            } catch (Exception e) {
                System.out.println("Geçersiz giriş. Lütfen bir sayı girin.");
                scanner.nextLine(); // Giriş temizleme
            }
        }

        HangmanGame game = null;
        if (choice == 1) {
            game = new EasyHangmanGame(rastgeleKelime, hangmanStages);
        }else if(choice == 2){
            game = new HardHangmanGame(rastgeleKelime, hangmanStages);
        }else if(choice == 3){
            game = new NormalHangmanGame(rastgeleKelime, hangmanStages);
        }

        // Oyunu başlat
        game.play();
    }
}
