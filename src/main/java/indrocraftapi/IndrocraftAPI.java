package indrocraftapi;

import org.bukkit.plugin.java.JavaPlugin;

public final class IndrocraftAPI extends JavaPlugin {

    public Utils utils;

    public void onEnable() {
        utils = new Utils();
    }
}
