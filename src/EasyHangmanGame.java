import java.util.Scanner;

class EasyHangmanGame extends HangmanGame {
    public EasyHangmanGame(String word, String[] hangmanStages) {
        super(word, 10, hangmanStages); // Kolay zorluk: 10 hak
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
