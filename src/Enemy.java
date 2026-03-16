import java.awt.*;

public class Enemy {
    private int x, y, health, speed;
    private int damage = 10;

    public Enemy(int x, int y, int wave) {
        this.x = x;
        this.y = y;
        this.health = 50 + (wave * 2);
        this.speed = 1 + (wave / 10);
    }

    public void update(int playerX, int playerY) {
        if (x < playerX) x += speed;
        if (x > playerX) x -= speed;
        if (y < playerY) y += speed;
        if (y > playerY) y -= speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 15, 15);
    }

    public void takeDamage(int dmg) { health -= dmg; }
    public int getHealth() { return health; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Rectangle getBounds() { return new Rectangle(x, y, 15, 15); }
    public int getDamage() { return damage; } // Added getter for damage
}