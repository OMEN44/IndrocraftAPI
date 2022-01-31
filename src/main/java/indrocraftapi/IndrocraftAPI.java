package indrocraftapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class IndrocraftAPI extends JavaPlugin {

    public Utils utils = new Utils();

    public static void test() {
        Bukkit.getLogger().severe("static");
    }
}
