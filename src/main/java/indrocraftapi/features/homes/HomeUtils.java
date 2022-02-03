package indrocraftapi.features.homes;

import indrocraftapi.IndrocraftAPI;
import indrocraftapi.datamanager.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HomeUtils {

    private static final IndrocraftAPI api = IndrocraftAPI.getPlugin(IndrocraftAPI.class);

    /**
     * @param homeName Name of the home you want to get
     * @param owner owner of the home you want to get
     * @return returns a home object containing all the relevant data
     * @apiNote This method should be used for getting a Home object and is NOT interchange able with getHomeByLocation
     */
    public static Home getHome(String homeName, Player owner) {
        String id = owner.getUniqueId() + homeName;
        World world = Bukkit.getWorld(api.sqlUtils.getString("world", "homeID", id, "homeTable"));
        Double x = api.sqlUtils.getDouble("x", "homeID", id, "homeTable");
        Double y = api.sqlUtils.getDouble("y", "homeID", id, "homeTable");
        Double z = api.sqlUtils.getDouble("z", "homeID", id, "homeTable");
        Float yaw = api.sqlUtils.getFloat("yaw", "homeID", id, "homeTable");
        Float pitch = api.sqlUtils.getFloat("pitch", "homeID", id, "homeTable");

        return new Home(owner, "test", world, x, y, z, yaw, pitch);
    }


    /**
     * @param owner owner of the home you are checking for
     * @param location location being checked for a home
     * @return returns a home if one is set at that location and returns null if the home does not exist
     *
     * @apiNote this should only be used to check for home by using their location and not be relied upon to get Home
     * object as it is imprecise and can return null
     */
    public static Home getHomeByLocation(Player owner, Location location) {
        return new Home(owner, "test", location);
    }

    /**
     * @param homeName name of the home that you want to check
     * @param owner    Player objects for the owner of this home
     * @return returns true if the home exist and false if it does not
     */
    public static boolean homeExist(Player owner, String homeName) {
        return api.sqlUtils.rowExists("homeID", owner.getUniqueId() + homeName, "homeTable");
    }

    /**
     * @param name name of the new home
     * @param location Location object of the
     * @param owner who is the owner of the home
     * @apiNote this method create a new home in the database and returns it as an object
     */
    public static Home createHome(String name, Location location, Player owner) {
        if (!(homeExist(owner, name))) {
            String id = owner.getUniqueId() + name;
            api.sqlUtils.createRow("homeID", id, "homeTable");
            api.sqlUtils.setData(String.valueOf(location.getX()), "homeID", id, "x", "homeTable");
            api.sqlUtils.setData(String.valueOf(location.getY()), "homeID", id, "y", "homeTable");
            api.sqlUtils.setData(String.valueOf(location.getZ()), "homeID", id, "z", "homeTable");

            api.sqlUtils.setData(location.getWorld().getName(), "homeID", id, "world", "homeTable");
            api.sqlUtils.setData(owner.getUniqueId().toString(), "homeID", id, "owner", "homeTable");
            return new Home(owner, name, location);
        }
        return null;
    }

    /**
     * @param homeName Name of target home
     * @param owner Owner of home for removal
     * @apiNote This method does not use the recommended Home object
     */
    public static void deleteHome(String homeName, Player owner) {
        if (homeExist(owner, homeName)) {
            api.sqlUtils.removeRow("homeID", owner.getUniqueId() + homeName, "homeTable");
        }
    }

    /**
     * @param home Home object containing all relevant data
     */
    public static void deleteHome(Home home) {
        if (homeExist(home.getOwner(), home.getName())) {
            api.sqlUtils.removeRow("homeID", home.getId(), "homeTable");
        }
    }

    /**
     * @param sqlUtils this is the mysql connection used to create the database
     * @apiNote This must be run at least once to set up the table in the database for saving home data
     */
    public static void createHomeTable(SQLUtils sqlUtils) {
        sqlUtils.createTable("homeTable", "homeID");
        sqlUtils.createColumn("owner", "VARCHAR(100)", "homeTable");
        sqlUtils.createColumn("world", "VARCHAR(100)", "homeTable");
        sqlUtils.createColumn("x", "DOUBLE", "homeTable");
        sqlUtils.createColumn("y", "DOUBLE", "homeTable");
        sqlUtils.createColumn("z", "DOUBLE", "homeTable");
    }

    /**
     * @param homeName Name of the home being teleported to
     * @param owner Owner of the home and target being teleported
     * @apiNote This method doesn't use the recommended Home object
     */
    public static void teleportHome(String homeName, Player owner) {
        Home home = getHome(homeName, owner);
        if (home.getWorld() == owner.getWorld()) {
            owner.teleport(home.getLocation());
        } else {
            owner.sendMessage(ChatColor.RED + "You try with all your might to teleport! " +
                    "But you aren't quite strong enough to go between worlds!");
        }
    }

    /**
     * @param home home object that contains location and owner data
     * @apiNote This method uses the functionality of the home object to teleport a player
     */
    public static void teleportHome(Home home) {
        Player owner = home.getOwner();
        if (home.getWorld() == owner.getWorld()) {
            owner.teleport(home.getLocation());
        } else {
            owner.sendMessage(ChatColor.RED + "You try with all your might to teleport! " +
                    "But you aren't quite strong enough to go between worlds!");
        }
    }
}
