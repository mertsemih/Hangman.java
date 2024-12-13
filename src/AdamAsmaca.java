import javax.swing.*;

import java.io.*;


public class AdamAsmaca{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new AdamAsmacaSwingGame().startGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
