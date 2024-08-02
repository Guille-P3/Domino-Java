package Domino;

import javax.swing.ImageIcon;

public class Token implements Comparable<Object> {
    private int left, right;
    private ImageIcon imageIcon;

    Token(int left, int right, ImageIcon imageIcon) {
        this.left = left;
        this.right = right;
        this.imageIcon = imageIcon;
    }

    public int getLeft() {
        return this.left;
    }

    public int getRight() {
        return this.right;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    public ImageIcon getImageIcon() {
        return this.imageIcon;
    }

    public String toString() {
        return "Left: " + left + " right: " + right;
    }

    public int compareTo(Object other) {
        if (other instanceof Token) {
            Token otherToken = (Token) other;
            if (this.left != otherToken.left) {
                return Integer.compare(this.left, otherToken.left);
            } else {
                return Integer.compare(this.right, otherToken.right);
            }
        } else {
            throw new IllegalArgumentException("El objeto recibido no es una ficha");
        }
    }
}
