package indrocraftapi;

import indrocraftapi.datamanager.SQLConnector;
import indrocraftapi.datamanager.SQLUtils;
import indrocraftapi.features.homes.Home;
import indrocraftapi.features.homes.HomeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class IndrocraftAPI extends JavaPlugin {

    public SQLConnector sqlConnector = new SQLConnector(
            "test",
            "localhost",
            "3306",
            "root",
            "",
            false,
            this);

    @Override
    public void onEnable() {
        HomeUtils.createHomeTable();
        Player player = Bukkit.getPlayer("OMEN44");
        Location loc = new Location(player.getWorld(), 188.68, 171.00, -139.60, -0.15f, 28.50f);
        HomeUtils.createHome("test", loc, player);
        Home home = HomeUtils.getHome("test", player);
        HomeUtils.teleportHome(home);
    }
}
