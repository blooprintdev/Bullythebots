import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame("Wave Survival Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // 👉 FESTE 1920x1080 AUFLÖSUNG
        window.setSize(1920, 1080);
        window.setLocationRelativeTo(null); // zentriert

        MenuPanel menu = new MenuPanel(window);
        window.add(menu);

        window.setVisible(true);
    }
}