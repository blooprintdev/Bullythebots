import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
    private static final long serialVersionUID = 1L;
    private static final int SCREEN_WIDTH = 1920;
    private static final int SCREEN_HEIGHT = 1080;
    private static final int DELAY = 16;

    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<WeaponPickup> pickups = new ArrayList<>();
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private WaveManager waveManager;
    private Timer timer;
    private Random random = new Random();
    private boolean running = true;
    private int score = 0;
    private Point mousePos = new Point(0, 0);
    private boolean scoping = false;

    private boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false;
    private String playerClass;

    public GamePanel(String selectedClass) {
        playerClass = selectedClass;
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        resetGame();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void resetGame() {
        enemies.clear(); pickups.clear(); projectiles.clear();
        score = 0; running = true;
        waveManager = new WaveManager(this);
        player = createPlayer();
    }

    private Player createPlayer() {
        int x = SCREEN_WIDTH / 2, y = SCREEN_HEIGHT / 2;
        if ("Warrior".equals(playerClass)) return new Warrior(x, y);
        if ("Mage".equals(playerClass)) return new Mage(x, y);
        return new Sniper(x, y);
    }

    public ArrayList<Enemy> getEnemies() { return enemies; }

    public void spawnEnemy() {  // jetzt public – WaveManager findet es
        enemies.add(new Enemy(random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_HEIGHT), waveManager.getCurrentWave()));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (running) {
            player.draw(g);
            for (Enemy e : enemies) e.draw(g);
            for (WeaponPickup p : pickups) p.draw(g);
            for (Projectile p : projectiles) p.draw(g);
            if (scoping && player instanceof Sniper) {
                g.setColor(Color.WHITE);
                g.drawLine(player.getX() + 10, player.getY() + 10, mousePos.x, mousePos.y);
            }
            g.setColor(Color.WHITE);
            g.drawString("Wave: " + waveManager.getCurrentWave() + " | Score: " + score, 10, 20);
            g.drawString("Health: " + player.getHealth(), 10, SCREEN_HEIGHT - 110);
            g.drawString("Weapon: " + player.getWeaponName(), 10, SCREEN_HEIGHT - 90);
            g.drawString("Damage: " + player.getDamage(), 10, SCREEN_HEIGHT - 70);
        } else {
            g.setColor(new Color(0,0,0,180));
            g.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", SCREEN_WIDTH/2 - 150, SCREEN_HEIGHT/2 - 50);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.drawString("Final Wave: " + waveManager.getCurrentWave(), SCREEN_WIDTH/2 - 120, SCREEN_HEIGHT/2);
            g.drawString("Drücke R zum Neustarten", SCREEN_WIDTH/2 - 150, SCREEN_HEIGHT/2 + 50);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!running) return;
        player.updateMovement(upPressed, downPressed, leftPressed, rightPressed);
        player.update();
        waveManager.update();

        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            enemy.update(player.getX(), player.getY());
            if (player.collidesWith(enemy.getBounds())) player.takeDamage(enemy.getDamage());
            if (player.getHealth() <= 0) { running = false; timer.stop(); }
            if (enemy.getHealth() <= 0) { enemies.remove(i); score += 10; }
        }

        for (int j = projectiles.size() - 1; j >= 0; j--) {
            Projectile p = projectiles.get(j);
            p.update();
            if (p.x < 0 || p.x > SCREEN_WIDTH || p.y < 0 || p.y > SCREEN_HEIGHT) { projectiles.remove(j); continue; }
            for (int i = enemies.size() - 1; i >= 0; i--) {
                if (p.getBounds().intersects(enemies.get(i).getBounds())) {
                    enemies.get(i).takeDamage(p.damage);
                    projectiles.remove(j);
                    break;
                }
            }
        }

        for (int i = pickups.size() - 1; i >= 0; i--) {
            if (player.collidesWith(pickups.get(i).getBounds())) {
                player.equipWeapon(pickups.get(i).getWeapon());
                pickups.remove(i);
            }
        }

        if (random.nextInt(100) < 2) spawnPickup();
        repaint();
    }

    private void spawnPickup() {
        int x = random.nextInt(SCREEN_WIDTH);
        int y = random.nextInt(SCREEN_HEIGHT);
        Weapon w = random.nextBoolean() ? new Sword() : new Staff();
        pickups.add(new WeaponPickup(x, y, w));
    }

    // ==================== ALLE LISTENER (keine "must implement" mehr) ====================
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) upPressed = true;
        if (key == KeyEvent.VK_S) downPressed = true;
        if (key == KeyEvent.VK_A) leftPressed = true;
        if (key == KeyEvent.VK_D) rightPressed = true;
        if (key == KeyEvent.VK_R && !running) resetGame();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) upPressed = false;
        if (key == KeyEvent.VK_S) downPressed = false;
        if (key == KeyEvent.VK_A) leftPressed = false;
        if (key == KeyEvent.VK_D) rightPressed = false;
    }

    @Override public void keyTyped(KeyEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (!running) return;
        if (e.getButton() == MouseEvent.BUTTON1) {
            Projectile proj = player.mouseAttack(mousePos.x, mousePos.y, enemies);
            if (proj != null) projectiles.add(proj);
        } else if (e.getButton() == MouseEvent.BUTTON3 && player instanceof Sniper) {
            scoping = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) scoping = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) { mousePos = e.getPoint(); }
    @Override
    public void mouseDragged(MouseEvent e) { mousePos = e.getPoint(); }
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}