package indrocraftapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class IndrocraftAPI extends JavaPlugin {

    public void nonStatic(String arg) {
        Bukkit.getLogger().severe("this is not static " + arg);
    }

    public static void test() {
        Bukkit.getLogger().severe("static");
    }
}
