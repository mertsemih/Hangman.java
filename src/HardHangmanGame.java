import java.util.Scanner;

class HardHangmanGame extends HangmanGame {
    public HardHangmanGame(String word, String[] hangmanStages) {
        super(word, 2, hangmanStages); // Zor zorluk: 2 hak
    }

    @Override
    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (attemptsLeft > 0) {
            displayGameStatus();
            System.out.print("Bir harf tahmin edin: ");
            char guess = scanner.nextLine().toUpperCase().charAt(0);

            if (processGuess(guess)) {
                System.out.println("Doğru tahmin!");
            } else {
                attemptsLeft--;
                System.out.println("Yanlış tahmin!");
            }

            if (checkWin()) {
                System.out.println("Tebrikler, kelimeyi buldunuz: " + word);
                return;
            }
        }
        System.out.println("Kaybettiniz! Kelime: " + word);
    }
}