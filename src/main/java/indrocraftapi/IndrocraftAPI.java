package indrocraftapi;

import indrocraftapi.datamanager.SQLUtils;
import indrocraftapi.features.homes.HomeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class IndrocraftAPI extends JavaPlugin {

    public SQLUtils sqlUtils = new SQLUtils("test", "localhost", "3306", "root", "");

    @Override
    public void onEnable() {

        HomeUtils.createHomeTable(sqlUtils);

        Player player = Bukkit.getPlayer("OMEN44");
        Location location = new Location(player.getWorld(), 194.54, 171.00, -140.62);
        HomeUtils.createHome("test", location, player);
        //HomeUtils.deleteHome("test", player);

        HomeUtils.teleportHome("test", player);
    }
}
