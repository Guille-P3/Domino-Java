package Domino;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.BorderLayout;

public class PlayerPanel extends JPanel implements ActionListener {
    private String nickName;
    private Color background;
    private JPanel header, center, footer;
    private ArrayList<JButton> buttons;
    private ArrayList<Token> tokens;
    private JButton next, leftButton, rightButton;
    private int indexButton;
    private ImageIcon imageIcon;
    private static int site;
    private static Token currentToken;
    private static Token selectedToken;
    private int restantes = 7;

    PlayerPanel(String nickName, Color background, int height, int width, ArrayList<Token> tokens) {
        super();
        this.nickName = nickName;
        this.background = background;
        this.tokens = tokens != null ? new ArrayList<>(tokens) : new ArrayList<>();
        this.buttons = new ArrayList<>(7);
        this.next = new JButton("Next");
        this.leftButton = new JButton("Left");
        this.rightButton = new JButton("Right");
        setupListeners();

        setLayout(new BorderLayout());
        setupPanels(height, width);
        setupHeader();

        if (tokens != null) {
            for (Token token : tokens) {
                addToken(new JButton(token.toString()));
            }
        }
    }

    private void setupListeners() {
        next.addActionListener(this);
        leftButton.addActionListener(this);
        rightButton.addActionListener(this);
    }

    private void setupPanels(int height, int width) {
        header = new JPanel();
        center = new JPanel();
        center.setLayout(null);
        footer = new JPanel();

        header.setPreferredSize(new Dimension(width, (height / 10) * 2));
        center.setPreferredSize(new Dimension(width, (height / 10) * 7));
        footer.setPreferredSize(new Dimension(width, (height / 10) * 1));

        header.setBackground(background);
        center.setBackground(background);
        footer.setBackground(background);

        add(header, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        setSize(width, height);
        setBackground(background);
    }

    private void setupHeader() {
        JLabel name = new JLabel(this.nickName);
        name.setVerticalAlignment(JLabel.CENTER);
        name.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(name);
    }

    public String getNickName() {
        return this.nickName;
    }

    public Color getColor() {
        return this.background;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setColor(Color background) {
        this.background = background;
        setBackground(background);
    }

    public JButton getToken(int index) {
        if (index >= 0 && index < buttons.size()) {
            return buttons.get(index);
        }
        return null;
    }

    public void addToken(JButton token) {
        buttons.add(token);
        center.add(token);

        token.addActionListener(e -> {
            int index = buttons.indexOf(token);

            selectedToken = tokens.get(index);

            if (selectedToken.getLeft() == 6 && selectedToken.getRight() == 6) {
                painter(index);
            } else {
                indexButton = index;
                updateFooter();

            }
        });

        center.revalidate();
        center.repaint();
    }

    public void updateFooter() {
        footer.removeAll();

        if (currentToken != null) {
            boolean matchLeft = currentToken.getLeft() == selectedToken.getLeft()
                    || currentToken.getLeft() == selectedToken.getRight();
            boolean matchRight = currentToken.getRight() == selectedToken.getLeft()
                    || currentToken.getRight() == selectedToken.getRight();

            if (matchLeft && matchRight) {
                footer.setLayout(new GridLayout(1, 2));
                footer.add(leftButton);
                footer.add(rightButton);
            } else {
                footer.setLayout(new GridLayout(1, 1));
                if (currentToken.getLeft() == selectedToken.getLeft()
                        || currentToken.getLeft() == selectedToken.getRight()) {
                    footer.add(leftButton);
                } else if (currentToken.getRight() == selectedToken.getLeft()
                        || currentToken.getRight() == selectedToken.getRight()) {
                    footer.add(rightButton);
                }
            }

            footer.repaint();
            footer.revalidate();
        }
    }

    public void painter(int index) {
        restantes--;
        imageIcon = tokens.get(index).getImageIcon();
        currentToken = View.next(imageIcon, tokens.get(index), site);
        deleteToken(index);
        footer.removeAll();
    }

    public void deleteToken(int index) {
        buttons.remove(index);
        tokens.remove(index);
        center.remove(index);
        center.revalidate();
        center.repaint();
    }

    public void disableJPanel(int left, int right) {
        setEnabledPanelComponents(center, false, left, right);
    }

    public void disableJPanel2(int left, int right) {
        setEnabledPanelComponents2(center, false, left, right);
    }

    private void setEnabledPanelComponents(JPanel panel, boolean status, int left, int right) {
        Token temporal = new Token(left, right, null);

        for (int i = 0; i < center.getComponentCount(); i++) {
            if (temporal.compareTo(tokens.get(i)) != 0) {
                center.getComponent(i).setEnabled(status);
            }
        }
    }

    public void disableComponents() {
        for (Component jButton : center.getComponents()) {
            jButton.setEnabled(false);
        }
    }

    private void setEnabledPanelComponents2(JPanel panel, boolean status, int left, int right) {
        int le = 0, ri = 0;

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            JButton button = buttons.get(i);

            if (token.getLeft() == left || token.getRight() == left ||
                    token.getLeft() == right || token.getRight() == right) {
                button.setEnabled(!status);
                if (token.getLeft() == left || token.getRight() == left) {
                    le++;
                }
                if (token.getLeft() == right || token.getRight() == right) {
                    ri++;
                }
            } else {
                button.setEnabled(status);
            }
        }

        if (le == 0 && ri == 0) {
            footer.setLayout(new GridLayout(1,1));
            footer.add(next);
        }

    }

    public void quitNext(){
        footer.removeAll();
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public int getTokensButtons(){
        return restantes;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next) {
            View.next(null, null, 2);
        } else if (e.getSource() == leftButton) {
            site = 0;
            painter(indexButton);
        } else if (e.getSource() == rightButton) {
            site = 1;
            painter(indexButton);
        }
    }
}
