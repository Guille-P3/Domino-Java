package Domino;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Domino {
    private ArrayList<Token> tokens;

    Domino() {
        this.tokens = new ArrayList<>(28);
        fuller();
    }

    public void fuller() {
        int x = 0;
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                tokens.add(new Token(i, j, new ImageIcon("C://Source//Fichas//" + x + ".png")));
                x++;
            }
        }
    }

    public Token popToken(int index) {
        if (index > -1 && index < 28) {
            return tokens.get(index);
        }
        return null;
    }

    public void shuffle() {
        int j = 0;

        Random random = new Random();

        for (int i = tokens.size() - 1; i > 0; i--) {
            j = random.nextInt(i + 1);

            Token temp = tokens.get(i);
            tokens.set(i, tokens.get(j));
            tokens.set(j, temp);
        }
    }

}
