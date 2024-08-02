package Domino;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import java.awt.Point;

public class View extends JFrame implements ActionListener {

    private static int currentTurn = 0;
    private static Token currentToken;
    private int counter = 1;
    private Domino domino = new Domino();
    private Player[] players = new Player[4];
    private JButton view, distribute, mix;
    private JPanel centerPanel;
    private static JPanel tokenPanel;
    private JPanel buttonPanel;
    private static PlayerPanel[] playerPanel = new PlayerPanel[4];
    private Color[] colors = { new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255),
            new Color(255, 255, 0) };
    private Image backgroundImage;
    private ImageIcon tokenIcon;
    private static int turn;
    private static int x1, x2, leftToken, rightToken;
    private static JScrollPane jscrollPane;
    private static int def = 0;

    View() {
        setTitle("Domino");
        hazInterfaz();
        hazEscuchas();
        currentToken = new Token(6, 6, null);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustComponents();
            }
        });
    }

    public void hazInterfaz() {
        setSize(1400, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        setResizable(false);
        backgroundImage = new ImageIcon("C://Source//Fichas//TableGame.jpg").getImage();

        for (int i = 0; i < 4; i++) {
            players[i] = new Player("Player " + (i + 1));
        }

        view = new JButton("View");
        distribute = new JButton("Distribute");
        mix = new JButton("Mix");

        centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        centerPanel.setLayout(null);
        centerPanel.setBackground(Color.GREEN);
        centerPanel.setBounds(((int) this.getWidth() / 100) * 25, ((int) this.getHeight() / 100) * 6,
                getWidth() / 10 * 5, getHeight() / 100 * 70);

        JLabel title = new JLabel("Tablero");
        title.setFont(new Font("Arial", Font.BOLD, 18));

        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBounds(0, (centerPanel.getHeight() / 100) * 2, centerPanel.getWidth(),
                (centerPanel.getHeight() / 10) * 1);
        centerPanel.add(title);
        add(centerPanel);

        tokenPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        tokenPanel.setBounds(0, (centerPanel.getHeight() / 100) * 20, centerPanel.getWidth(),
                (centerPanel.getHeight() / 100) * 80);
        tokenPanel.setLayout(new GridLayout(7, 4, 10, 10));

        tokenIcon = new ImageIcon("C://Source//Fichas//Trasera.jpg");

        tokenPanel.removeAll();
        for (int i = 0; i < 28; i++) {
            addImageToken(tokenIcon);
        }

        centerPanel.add(tokenPanel);

        buttonPanel = new JPanel() {

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, centerPanel.getWidth(), (centerPanel.getHeight() / 100) * 8, this);
            }
        };

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBounds(0, (centerPanel.getHeight() / 100) * 95, centerPanel.getWidth(),
                (centerPanel.getHeight() / 100) * 8);
        buttonPanel.add(view);
        buttonPanel.add(distribute);
        buttonPanel.add(mix);
        centerPanel.add(buttonPanel);

        centerPanel.repaint();
        centerPanel.revalidate();

        for (int i = 0; i < playerPanel.length; i++) {
            playerPanel[i] = new PlayerPanel("Player " + (i + 1), colors[i], 300, 200, null);
        }

        playerPanel[0].setBounds(centerPanel.getX() - 350, 0, getWidth() / 100 * 20, getHeight() / 100 * 30);
        playerPanel[1].setBounds(getWidth() - 350, 0, getWidth() / 100 * 20, getHeight() / 100 * 30);
        playerPanel[2].setBounds(centerPanel.getX() - 300, getHeight() / 100 * 66, getWidth() / 100 * 20,
                getHeight() / 100 * 30);
        playerPanel[3].setBounds(getWidth() - 300, getHeight() / 100 * 66, getWidth() / 100 * 20,
                getHeight() / 100 * 30);
        add(playerPanel[0]);
        add(playerPanel[1]);
        add(playerPanel[2]);
        add(playerPanel[3]);

        x1 = (tokenPanel.getWidth() / 2);
        x2 = x1;

        setVisible(true);

    }

    public void hazEscuchas() {
        view.addActionListener(this);
        distribute.addActionListener(this);
        mix.addActionListener(this);
    }

    public void addImageToken(ImageIcon tokenIcon) {
        int tokenWidth = (tokenPanel.getWidth() - 30) / 16;
        int tokenHeight = (tokenPanel.getHeight() - 120) / 7;

        Image image = tokenIcon.getImage().getScaledInstance(tokenWidth, tokenHeight, Image.SCALE_SMOOTH);
        JLabel token = new JLabel(new ImageIcon(image));
        tokenPanel.add(token);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view) {
            tokenPanel.removeAll();

            if (counter % 2 != 0) {
                for (int i = 0; i < 28; i++) {
                    tokenIcon = domino.popToken(i).getImageIcon();
                    addImageToken(tokenIcon);
                }
                view.setText("Hide");

            } else {
                for (int i = 0; i < 28; i++) {
                    tokenIcon = new ImageIcon("C://Source//Fichas//Trasera.jpg");
                    addImageToken(tokenIcon);
                }
                view.setText("View");
            }

            tokenPanel.repaint();
            tokenPanel.revalidate();

            counter++;

        } else if (e.getSource() == distribute) {
            try {
                view.setEnabled(false);
                mix.setEnabled(false);
                distribute.setEnabled(false);
                distribute();

            } catch (Exception ex) {
            }

        } else if (e.getSource() == mix) {
            domino.shuffle();

            if (counter % 2 == 0) {
                tokenPanel.removeAll();

                for (int i = 0; i < 28; i++) {
                    tokenIcon = domino.popToken(i).getImageIcon();
                    addImageToken(tokenIcon);
                }

                tokenPanel.repaint();
                tokenPanel.revalidate();
            }

        }
    }

    public void refresh() {
        tokenPanel.repaint();
        tokenPanel.revalidate();
    }

    public void distribute() {
        Thread removerTokens = new Thread(new Runnable() {
            @Override
            public void run() {
                int x = 0, counter = 0, positionX = 0;
                Token mula = new Token(6, 6, null);

                while (tokenPanel.getComponentCount() > 0) {

                    int buttonWidth = playerPanel[0].getWidth() / 7;
                    int buttonHeight = playerPanel[0].getHeight() / 2;

                    Token temporal = domino.popToken(counter);

                    Image tokenImage = temporal.getImageIcon().getImage().getScaledInstance(buttonWidth, buttonHeight,
                            Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(tokenImage);

                    JButton temporalToken = new JButton(scaledIcon);
                    temporalToken.setBounds(positionX, 0, buttonWidth, buttonHeight);

                    playerPanel[x].addToken(temporalToken);
                    players[x].getToken(temporal);
                    playerPanel[x].revalidate();
                    playerPanel[x].repaint();

                    if (temporal.compareTo(mula) == 0) {
                        turn = x;
                    }

                    x++;
                    if (x > 3) {
                        positionX += buttonWidth;
                        x = 0;
                    }

                    counter++;
                    tokenPanel.remove(0);
                    refresh();

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }

                    buttonPanel.removeAll();
                    buttonPanel.revalidate();
                    buttonPanel.repaint();
                }

                for (int i = 0; i < playerPanel.length; i++) {
                    playerPanel[i].setTokens(players[i].giveTokens());
                }

                for (int i = 0; i < playerPanel.length; i++) {
                    playerPanel[i].disableJPanel(currentToken.getLeft(), currentToken.getRight());
                }

                centerPanel.remove(tokenPanel);
                jscrollPane = new JScrollPane(tokenPanel);
                jscrollPane.setBounds(tokenPanel.getX(), tokenPanel.getY(), centerPanel.getWidth(),
                        (centerPanel.getHeight() / 100) * 80);
                jscrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                jscrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

                tokenPanel.setPreferredSize(new Dimension(2000, tokenPanel.getHeight()));
                tokenPanel.setLayout(null);
                centerPanel.add(jscrollPane);

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JViewport viewport = jscrollPane.getViewport();
                        Dimension viewSize = viewport.getExtentSize();
                        Dimension contentSize = tokenPanel.getPreferredSize();

                        int x = Math.max(0, (contentSize.width - viewSize.width) / 2);
                        int y = 0;
                        viewport.setViewPosition(new Point(x, y));
                    }
                });
            }
        });

        removerTokens.start();
    }

    public static Token next(ImageIcon imageIcon, Token tokenx, int site) {

        if (site != 2) {
            redimentionator(tokenx, site);
            def = 0;
        } else if (site == 2) {

            def++;
            if (def == 4) {
                JOptionPane.showMessageDialog(null, "Ningún jugador puede seguir", "Juego terminado",
                        JOptionPane.INFORMATION_MESSAGE);
                for (int i = 0; i < playerPanel.length; i++) {
                    playerPanel[i].disableComponents();
                }

                if (turn + 1 > 3) {
                    playerPanel[0].quitNext();
                } else {
                    playerPanel[turn].quitNext();
                }
                System.exit(0);
            }

        }

        if (playerPanel[turn].getTokensButtons() != 0) {

            currentTurn++;
            turn++;

            if (turn > 3) {
                turn = 0;
            }

            for (int i = 0; i < playerPanel.length; i++) {

                if (i == turn) {
                    playerPanel[i].disableJPanel2(currentToken.getLeft(), currentToken.getRight());
                } else {
                    playerPanel[i].disableComponents();
                    playerPanel[i].quitNext();
                }

                playerPanel[i].repaint();
                playerPanel[i].revalidate();

            }

            return currentToken;
        }

        for (int i = 0; i < playerPanel.length; i++) {
            playerPanel[i].disableComponents();
        }

        JOptionPane.showMessageDialog(null, "El jugador " + turn + " es el ganador", "Juego terminado",
                JOptionPane.INFORMATION_MESSAGE);

        return null;

    }

    public void adjustComponents() {
        centerPanel.setBounds(((int) this.getWidth() / 100) * 25, ((int) this.getHeight() / 100) * 5,
                getWidth() / 10 * 5, getHeight() / 100 * 70);
        tokenPanel.setBounds(0, (centerPanel.getHeight() / 100) * 10, centerPanel.getWidth(),
                (centerPanel.getHeight() / 100) * 80);
        buttonPanel.setBounds(0, (centerPanel.getHeight() / 100) * 95, centerPanel.getWidth(),
                (centerPanel.getHeight() / 100) * 8);

        playerPanel[0].setBounds(centerPanel.getX() - 350, 0, getWidth() / 100 * 20, getHeight() / 100 * 30);
        playerPanel[1].setBounds(getWidth() - 300, 0, getWidth() / 100 * 20, getHeight() / 100 * 30);
        playerPanel[2].setBounds(centerPanel.getX() - 350, getHeight() / 100 * 66, getWidth() / 100 * 20,
                getHeight() / 100 * 30);
        playerPanel[3].setBounds(getWidth() - 300, getHeight() / 100 * 66, getWidth() / 100 * 20,
                getHeight() / 100 * 30);

        centerPanel.repaint();
        centerPanel.revalidate();
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public static void redimentionator(Token token, int site) {
        int width = jscrollPane.getWidth() / 42;
        int height = jscrollPane.getHeight() / 6;
        int y = jscrollPane.getHeight() / 2;
        int value = 0;
        JLabel label = new JLabel();

        if (token.getLeft() == token.getRight()) {
            Image image = token.getImageIcon().getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(image));
            label.setBounds(tokenPanel.getWidth() / 2 - width / 2, y, width, height);
            if (currentTurn == 0) {
                leftToken = 6;
                rightToken = 6;
                x1 = tokenPanel.getWidth() / 2 - width / 2 - height;
                x2 = tokenPanel.getWidth() / 2 + width / 2;
            } else {

                boolean matchLeft = token.getLeft() == leftToken || token.getRight() == leftToken;
                boolean matchRight = token.getLeft() == rightToken || token.getRight() == rightToken;

                if (matchLeft && site == 0) {
                    label.setBounds(x1 + (width * 3), y, width, height);
                    currentToken.setLeft(token.getLeft());
                    x1 -= width;
                } else if (matchRight && site == 1) {
                    label.setBounds(x2, y, width, height);
                    currentToken.setRight(token.getRight());
                    x2 += width;
                }
            }

        } else {

            String route = token.getImageIcon().toString();
            RotatorImage rotatorImage = new RotatorImage(new File(route), label);
            rotatorImage.loadImage();

            boolean matchLeft = token.getLeft() == leftToken || token.getRight() == leftToken;
            boolean matchRight = token.getLeft() == rightToken || token.getRight() == rightToken;

            rotatorImage.loadImage();
            if (matchLeft && site == 0) {
                value = condition(site, token, currentToken, rotatorImage);
                leftToken = token.getLeft() == leftToken ? token.getRight() : token.getLeft();
                currentToken.setLeft(value);
            } else if (matchRight && site == 1) {
                value = condition(site, token, currentToken, rotatorImage);
                rightToken = token.getLeft() == rightToken ? token.getRight() : token.getLeft();
                currentToken.setRight(value);
            }

            BufferedImage rotatedBufferedImage = rotatorImage.getCurrentImage();
            Image rotatedImage = rotatedBufferedImage.getScaledInstance(height, width, Image.SCALE_SMOOTH);

            label.setIcon(new ImageIcon(rotatedImage));

            if (site == 0) {
                label.setBounds(x1, (jscrollPane.getHeight() / 100) * 55, height, width);
                x1 -= height;
            } else if (site == 1) {
                label.setBounds(x2, (jscrollPane.getHeight() / 100) * 55, height, width);
                x2 += height;
            }
        }

        tokenPanel.add(label);
        tokenPanel.repaint();
        tokenPanel.revalidate();
    }

    public static int condition(int site, Token token, Token currentToken, RotatorImage rotatorImage) {
        int value = 0;
        if (site == 0) {
            if (token.getLeft() == currentToken.getLeft()) {
                rotatorImage.rotateImage(90);
                value = token.getRight();
            } else {
                rotatorImage.rotateImage(-90);
                value = token.getLeft();
            }
        } else {
            if (token.getRight() == currentToken.getRight()) {
                rotatorImage.rotateImage(90);
                value = token.getLeft();
            } else {
                rotatorImage.rotateImage(-90);
                value = token.getRight();
            }
        }
        return value;
    }

}
