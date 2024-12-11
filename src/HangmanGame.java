import java.util.Locale;
abstract class HangmanGame {
    protected String word; // Gizli kelime
    protected int attemptsLeft; // Kalan tahmin hakkı
    protected String[] hangmanStages; // ASCII Art aşamaları
    protected char[] guessedWord; // Oyuncunun doğru tahminleri
    protected boolean[] revealed; // Hangi harflerin açıldığı

    public HangmanGame(String word, int attemptsLeft, String[] hangmanStages) {
        this.word = word;
        this.attemptsLeft = attemptsLeft;
        this.hangmanStages = hangmanStages;
        this.guessedWord = new char[word.length()];
        this.revealed = new boolean[word.length()];

        // Kelimeyi başlangıçta boşluklarla doldur
        for (int i = 0; i < guessedWord.length; i++) {
            guessedWord[i] = '_';
        }
    }

    public void displayGameStatus() {
        // ASCII Art'ı tahmin haklarına göre göster
        int stageIndex = Math.max(0, hangmanStages.length - 1 - attemptsLeft);
        System.out.println(hangmanStages[stageIndex]);

        // Tahmin edilen kelimeyi göster
        System.out.print("Kelime: ");
        for (char c : guessedWord) {
            System.out.print(c + " ");
        }
        System.out.println();
        System.out.println("Kalan tahmin hakkı: " + attemptsLeft);
    }

    public boolean processGuess(char guess) {
        // Türkçe diline uygun büyük harf dönüşümü
        guess = String.valueOf(guess).toUpperCase(new Locale("tr")).charAt(0);
        boolean correct = false;
        for (int i = 0; i < word.length(); i++) {
            char wordChar = String.valueOf(word.charAt(i)).toUpperCase(new Locale("tr")).charAt(0);
            if (wordChar == guess && !revealed[i]) { // Türkçe duyarlı karşılaştırma
                guessedWord[i] = word.charAt(i); // Orijinal harfi göster
                revealed[i] = true;
                correct = true;
            }
        }
        return correct;
    }

    public boolean checkWin() {
        for (boolean b : revealed) {
            if (!b) return false;
        }
        return true;
    }

    public abstract void play();
}
