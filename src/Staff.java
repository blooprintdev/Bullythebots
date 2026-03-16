import java.util.ArrayList;

public class Staff extends Weapon {
    public Staff() { super(5); }

    @Override
    public void use(Player player, ArrayList<Enemy> enemies) {
        // Legacy
    }

    @Override
    public String getName() { return "Staff"; }
}