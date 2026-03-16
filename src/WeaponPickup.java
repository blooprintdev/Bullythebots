import java.awt.*;

public class WeaponPickup {
    private int x, y;
    private Weapon weapon;

    public WeaponPickup(int x, int y, Weapon weapon) {
        this.x = x;
        this.y = y;
        this.weapon = weapon;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, 10, 10);
    }

    public Weapon getWeapon() { return weapon; }
    public Rectangle getBounds() { return new Rectangle(x, y, 10, 10); }
}