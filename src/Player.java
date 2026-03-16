import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class Player {
    protected int x, y, health, damage;
    protected Weapon weapon;
    protected int speed = 5;

    public Player(int x, int y, int health, int damage, Weapon startingWeapon) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.damage = damage;
        this.weapon = startingWeapon;
    }

    public void updateMovement(boolean up, boolean down, boolean left, boolean right) {
        if (up) y -= speed;
        if (down) y += speed;
        if (left) x -= speed;
        if (right) x += speed;
        update();
    }

    public void update() {
        if (x < 0) x = 0; if (x > 800) x = 800;
        if (y < 0) y = 0; if (y > 600) y = 600;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 20, 20);
    }

    // Abstract Mouse Attack (M1)
    public abstract Projectile mouseAttack(int mx, int my, ArrayList<Enemy> enemies);

    public void equipWeapon(Weapon newWeapon) {
        this.weapon = newWeapon;
        this.damage += newWeapon.getDamageBonus();
    }

    public String getWeaponName() {
        return weapon.getName();
    }

    public boolean collidesWith(Rectangle other) {
        return new Rectangle(x, y, 20, 20).intersects(other);
    }

    public void takeDamage(int dmg) { health -= dmg; }
    public int getHealth() { return health; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getDamage() { return damage; } // Für UI
}