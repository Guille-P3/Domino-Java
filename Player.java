package Domino;

import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Token> tokens;
    private int counter = 0;

    Player(String name) {
        this.name = name;
        this.tokens = new ArrayList<Token>(7);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void getToken(Token token) {
        if (counter < 7) {
            tokens.add(token);
            counter++;
        }
    }

    public void useToken(int index) {
        if (index <= tokens.size()) {
            tokens.remove(index);
            counter--;
        }
    }

    public Token giveToken(int index) {
        return tokens.get(index);
    }

    public ArrayList<Token> giveTokens(){
        return this.tokens;
    }

    public String toString() {
        String tokens = "";

        for (int i = 0; i < this.tokens.size(); i++) {
            tokens += this.tokens.get(i).toString() + ", ";
        }

        tokens = tokens.substring(0, tokens.length() - 2);

        return "Player : " + this.name;
    }

}
