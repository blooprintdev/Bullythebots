import java.util.ArrayList;

public abstract class Weapon {
    protected int damageBonus;

    public Weapon(int damageBonus) {
        this.damageBonus = damageBonus;
    }

    public abstract void use(Player player, ArrayList<Enemy> enemies);
    public abstract String getName();

    public int getDamageBonus() { return damageBonus; }
}