import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class HangmanGUI extends JFrame {
    private final String word = "İYİYİM"; // Tahmin edilmesi gereken kelime
    private final char[] guessedWord = new char[word.length()];
    private final Set<Character> guessedLetters = new HashSet<>();
    private int attemptsLeft = 7; // Başlangıçtaki tahmin hakkı
    private final String[] hangmanStages = {
            "  +---+\n      |\n      |\n      |\n     ===",
            "  +---+\n  O   |\n      |\n      |\n     ===",
            "  +---+\n  O   |\n  |   |\n      |\n     ===",
            "  +---+\n  O   |\n /|   |\n      |\n     ===",
            "  +---+\n  O   |\n /|\\  |\n      |\n     ===",
            "  +---+\n  O   |\n /|\\  |\n /    |\n     ===",
            "  +---+\n  O   |\n /|\\  |\n / \\  |\n     ==="
    };

    // GUI Bileşenleri
    private final JLabel asciiArtLabel = new JLabel("<html>" + hangmanStages[0].replace("\n", "<br>") + "</html>");
    private final JLabel wordLabel = new JLabel("Kelime: " + String.valueOf(guessedWord).replace('\0', '_'));
    private final JLabel attemptsLabel = new JLabel("Kalan tahmin hakkı: " + attemptsLeft);
    private final JTextField inputField = new JTextField(5);
    private final JButton guessButton = new JButton("Tahmin Et");
    private final JLabel messageLabel = new JLabel("Bir harf giriniz.");

    public HangmanGUI() {
        // Arayüzün Başlangıç Yapılandırması
        setTitle("Adam Asmaca");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        // Başlangıç Kelimesini Hazırlama
        for (int i = 0; i < guessedWord.length; i++) {
            guessedWord[i] = '_';
        }

        // Bileşenleri Ekle
        add(asciiArtLabel);
        add(wordLabel);
        add(attemptsLabel);
        add(inputField);
        add(guessButton);
        add(messageLabel);

        // Buton Aksiyonları
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGuess(inputField.getText());
                inputField.setText(""); // Tahmin sonrası alanı temizle
            }
        });
    }

    private void processGuess(String input) {
        if (input.length() != 1) {
            messageLabel.setText("Lütfen sadece bir harf giriniz.");
            return;
        }

        char guess = input.toUpperCase(new Locale("tr")).charAt(0);

        // Daha önce tahmin edilmiş mi kontrol et
        if (guessedLetters.contains(guess)) {
            messageLabel.setText("Bu harfi zaten tahmin ettiniz!");
            return;
        }

        guessedLetters.add(guess);
        boolean correct = false;

        // Harfi kontrol et
        for (int i = 0; i < word.length(); i++) {
            if (word.toUpperCase(new Locale("tr")).charAt(i) == guess) {
                guessedWord[i] = word.charAt(i);
                correct = true;
            }
        }

        if (correct) {
            messageLabel.setText("Doğru tahmin!");
        } else {
            attemptsLeft--;
            messageLabel.setText("Yanlış tahmin.");
        }

        // Oyunun durumunu güncelle
        updateGameStatus();
    }

    private void updateGameStatus() {
        // ASCII Art ve Kelime Durumunu Güncelle
        int stageIndex = Math.max(0, hangmanStages.length - attemptsLeft);
        asciiArtLabel.setText("<html>" + hangmanStages[stageIndex].replace("\n", "<br>") + "</html>");
        wordLabel.setText("Kelime: " + String.valueOf(guessedWord));
        attemptsLabel.setText("Kalan tahmin hakkı: " + attemptsLeft);

        // Oyunu Kontrol Et
        if (String.valueOf(guessedWord).equals(word)) {
            messageLabel.setText("Tebrikler! Kelimeyi buldunuz.");
            inputField.setEnabled(false);
            guessButton.setEnabled(false);
        } else if (attemptsLeft <= 0) {
            messageLabel.setText("Oyun bitti! Kelime: " + word);
            inputField.setEnabled(false);
            guessButton.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HangmanGUI game = new HangmanGUI();
            game.setVisible(true);
        });
    }
}
