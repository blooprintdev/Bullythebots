import java.util.ArrayList;

public class Warrior extends Player {
    public Warrior(int x, int y) {
        super(x, y, 200, 20, new Sword());
    }

    @Override
    public Projectile mouseAttack(int mx, int my, ArrayList<Enemy> enemies) {
        // Slash: Damage in Konus Richtung Maus (80px Reichweite, 60° Winkel)
        double px = getX() + 10, py = getY() + 10;
        double dx = mx - px, dy = my - py;
        double angle = Math.atan2(dy, dx);
        for (Enemy enemy : enemies) {
            double ex = enemy.getX() + 7.5, ey = enemy.getY() + 7.5;
            double edx = ex - px, edy = ey - py;
            double edist = Math.hypot(edx, edy);
            double eangle = Math.atan2(edy, edx);
            if (edist < 80 && Math.abs(eangle - angle) < Math.toRadians(30)) {
                enemy.takeDamage(damage);
            }
        }
        return null; // Kein Projectile
    }
}