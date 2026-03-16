import java.util.ArrayList;

public abstract class RangedPlayer extends Player {
    public RangedPlayer(int x, int y, int health, int damage, Weapon startingWeapon) {
        super(x, y, health, damage, startingWeapon);
    }

    @Override
    public Projectile mouseAttack(int mx, int my, ArrayList<Enemy> enemies) {
        // Schuss: Projectile Richtung Maus
        return new Projectile(getX() + 10, getY() + 10, mx, my, damage);
    }
}