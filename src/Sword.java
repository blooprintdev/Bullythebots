import java.util.ArrayList;

public class Sword extends Weapon {
    public Sword() { super(10); }

    @Override
    public void use(Player player, ArrayList<Enemy> enemies) {
        // Legacy (nicht mehr genutzt)
    }

    @Override
    public String getName() { return "Sword"; }
}