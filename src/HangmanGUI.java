import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class HangmanGUI extends JFrame {
    private final String word;
    private final char[] guessedWord;
    private final Set<Character> guessedLetters = new HashSet<>();
    private int attemptsLeft;
    private final String[] hangmanStages = {
            "  +---+\n      |\n      |\n      |\n     ===",
            "  +---+\n      O   |\n         |\n         |\n     ===",
            "  +---+\n      O   |\n     |   |\n         |\n     ===",
            "  +---+\n      O   |\n    /|      |\n         |\n     ===",
            "  +---+\n      O   |\n    /|\\     |\n         |\n     ===",
            "  +---+\n      O   |\n    /|\\     |\n /       |\n     ===",
            "  +---+\n      O   |\n    /|\\     |\n /    \\  |\n     ==="
    };

    // GUI Bileşenleri
    private final JLabel asciiArtLabel = new JLabel("<html>" + hangmanStages[0].replace("\n", "<br>") + "</html>");
    private final JLabel wordLabel = new JLabel();
    private final JLabel attemptsLabel = new JLabel();
    private final JTextField inputField = new JTextField(5);
    private final JButton guessButton = new JButton("Tahmin Et");
    private final JLabel messageLabel = new JLabel("Bir harf giriniz.");

    public HangmanGUI(int difficulty) {
        // Zorluk seviyesine göre haklar
        switch (difficulty) {
            case 1 -> attemptsLeft = 10; // Kolay
            case 2 -> attemptsLeft = 7;  // Orta
            case 3 -> attemptsLeft = 5;  // Zor
        }

        // Rastgele bir kelime seç
        this.word = chooseRandomWord("C:\\Users\\semih\\IdeaProjects\\denemehang\\src\\kelimeler.txt").toUpperCase(new Locale("tr"));
        this.guessedWord = new char[word.length()];

        // Kelimeyi "_" ile başlat
        for (int i = 0; i < guessedWord.length; i++) {
            guessedWord[i] = '_';
        }

        // Arayüzün Başlangıç Yapılandırması
        setTitle("Adam Asmaca");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        // Başlangıç Kelimesini Hazırlama
        wordLabel.setText("Kelime: " + String.valueOf(guessedWord));
        attemptsLabel.setText("Kalan tahmin hakkı: " + attemptsLeft);

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
            if (word.charAt(i) == guess) {
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

    private String chooseRandomWord(String filePath) {
        try {
            List<String> words = Files.readAllLines(Path.of(filePath));
            Random random = new Random();
            return words.get(random.nextInt(words.size()));
        } catch (Exception e) {
            throw new RuntimeException("Kelime dosyası okunamadı: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Kullanıcıdan zorluk seviyesini al
        String[] options = {"Kolay", "Orta", "Zor"};
        int difficulty = JOptionPane.showOptionDialog(
                null,
                "Zorluk seviyesini seçin:",
                "Zorluk Seçimi",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (difficulty == -1) {
            JOptionPane.showMessageDialog(null, "Oyun iptal edildi.");
            System.exit(0);
        }

        SwingUtilities.invokeLater(() -> {
            HangmanGUI game = new HangmanGUI(difficulty + 1); // Kolay: 1, Orta: 2, Zor: 3
            game.setVisible(true);
        });
    }
}
