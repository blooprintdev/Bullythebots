import java.awt.*;

public class Projectile {
    double x, y, dx, dy;
    int damage;

    public Projectile(int startX, int startY, int targetX, int targetY, int dmg) {
        damage = dmg;
        double angle = Math.atan2(targetY - startY, targetX - startX);
        double speed = 10.0;
        dx = Math.cos(angle) * speed;
        dy = Math.sin(angle) * speed;
        x = startX;
        y = startY;
    }

    public void update() {
        x += dx;
        y += dy;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval((int)x - 2, (int)y - 2, 4, 4);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x - 2, (int)y - 2, 4, 4);
    }
}