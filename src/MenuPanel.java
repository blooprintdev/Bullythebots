import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel {
    private JFrame frame;

    public MenuPanel(JFrame f) {
        this.frame = f;
        setLayout(new GridLayout(5, 1, 20, 20));
        setBackground(Color.BLACK);

        JLabel title = new JLabel("WAVE SURVIVAL", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        add(title);

        add(createButton("1 – Warrior (Tank)", "Warrior"));
        add(createButton("2 – Mage (Zauberer)", "Mage"));
        add(createButton("3 – Sniper (Killer)", "Sniper"));

        JLabel hint = new JLabel("Klicke oder drücke 1 / 2 / 3", SwingConstants.CENTER);
        hint.setFont(new Font("Arial", Font.PLAIN, 22));
        hint.setForeground(Color.GRAY);
        add(hint);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_1) startGame("Warrior");
                if (e.getKeyCode() == KeyEvent.VK_2) startGame("Mage");
                if (e.getKeyCode() == KeyEvent.VK_3) startGame("Sniper");
            }
        });
    }

    private JButton createButton(String text, String clazz) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 24));
        btn.setBackground(new Color(60, 60, 60));
        btn.setForeground(Color.WHITE);
        btn.addActionListener(e -> startGame(clazz));
        return btn;
    }

    private void startGame(String clazz) {
        frame.getContentPane().removeAll();
        GamePanel game = new GamePanel(clazz);
        frame.getContentPane().add(game);
        frame.revalidate();
        frame.repaint();
        game.requestFocus();
    }
}