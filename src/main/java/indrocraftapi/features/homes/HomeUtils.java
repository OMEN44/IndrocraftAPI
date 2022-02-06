package indrocraftapi.features.homes;

import indrocraftapi.IndrocraftAPI;
import indrocraftapi.datamanager.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class HomeUtils {

    private static final SQLUtils sqlUtils = new SQLUtils(IndrocraftAPI.getPlugin(IndrocraftAPI.class).sqlConnector);

    /**
     * @param homeName Name of the home you want to get
     * @param owner owner of the home you want to get
     * @return returns a home object containing all the relevant data
     * @apiNote This method should be used for getting a Home object and is NOT interchange able with getHomeByLocation
     */
    public static Home getHome(String homeName, Player owner) {
        String id = owner.getUniqueId() + homeName;
        World world = Bukkit.getWorld(sqlUtils.getString("world", "homeID", id, "homeTable"));
        Double x = (Double) sqlUtils.getData("x", "homeID", id, "homeTable");
        Double y = (Double) sqlUtils.getData("y", "homeID", id, "homeTable");
        Double z = (Double) sqlUtils.getData("z", "homeID", id, "homeTable");
        Float yaw = sqlUtils.getFloat("yaw", "homeID", id, "homeTable");
        Float pitch = sqlUtils.getFloat("pitch", "homeID", id, "homeTable");

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
        return sqlUtils.rowExists("homeID", owner.getUniqueId() + homeName, "homeTable");
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
            sqlUtils.createRow("homeID", "90cec013-c3da-4bfa-a6e2-5c4d1e666ea4test", "homeTable");
            sqlUtils.setData(String.valueOf(location.getX()), "homeID", id, "x", "homeTable");
            sqlUtils.setData(String.valueOf(location.getY()), "homeID", id, "y", "homeTable");
            sqlUtils.setData(String.valueOf(location.getZ()), "homeID", id, "z", "homeTable");
            sqlUtils.setData(String.valueOf(location.getYaw()), "homeID", id, "yaw", "homeTable");
            sqlUtils.setData(String.valueOf(location.getPitch()), "homeID", id, "pitch", "homeTable");

            sqlUtils.setData(location.getWorld().getName(), "homeID", id, "world", "homeTable");
            sqlUtils.setData(owner.getUniqueId().toString(), "homeID", id, "owner", "homeTable");
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
            sqlUtils.deleteRow("homeID", owner.getUniqueId() + homeName, "homeTable");
        }
    }

    /**
     * @param home Home object containing all relevant data
     */
    public static void deleteHome(Home home) {
        if (homeExist(home.getOwner(), home.getName())) {
            sqlUtils.deleteRow("homeID", home.getId(), "homeTable");
        }
    }

    /**
     * @apiNote This must be run at least once to set up the table in the database for saving home data
     */
    public static void createHomeTable() {
        sqlUtils.createTable("homeTable", "homeID");
        sqlUtils.createColumn("owner", "VARCHAR(100)", "homeTable");
        sqlUtils.createColumn("world", "VARCHAR(100)", "homeTable");
        sqlUtils.createColumn("x", "DOUBLE", "homeTable");
        sqlUtils.createColumn("y", "DOUBLE", "homeTable");
        sqlUtils.createColumn("z", "DOUBLE", "homeTable");
        sqlUtils.createColumn("yaw", "FLOAT", "homeTable");
        sqlUtils.createColumn("pitch", "FLOAT", "homeTable");
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

    /**
     * @param target Target player
     * @return returns the amount of homes that a player has set
     */
    public static int getPlayerHomeAmount(Player target) {
        int x = 0;
        try {
            List<String> data = new ArrayList<>();
            PreparedStatement ps = sqlUtils.getCorrectConn().prepareStatement(
                    "SELECT `owner` FROM `homeTable` WHERE `owner`=?");
            ps.setString(1, target.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                x++;
            }
            return x;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return x;
    }

    /**
     * @param target Target player
     * @return Returns a list of all the homes as Home objects that the target owns
     */
    public static List<Home> getPlayersHomes(Player target) {
        List<Home> homeList = new ArrayList<>();
        try {
            PreparedStatement ps = sqlUtils.getCorrectConn().prepareStatement(
                    "SELECT * FROM homeTable WHERE `owner`=?");
            ps.setString(1, target.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                homeList.add(new Home(
                        target,
                        rs.getString("homeID").replace(target.getUniqueId().toString(), ""),
                        Bukkit.getWorld(rs.getString("world")),
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        rs.getDouble("z"),
                        rs.getFloat("yaw"),
                        rs.getFloat("pitch")));
            }
            return homeList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return homeList;
    }
}
