package indrocraftapi.features.ranks;

import indrocraftapi.IndrocraftAPI;
import indrocraftapi.datamanager.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class RankUtils {

    private static final IndrocraftAPI api = IndrocraftAPI.getPlugin(IndrocraftAPI.class);

    /**
     * @param sqlUtils connection to database
     * @apiNote this must be run to create table in database
     */
    public static void createRankTable(SQLUtils sqlUtils) {
        sqlUtils.makeQuery("create table if not exists rankPresets(" +
                "rankId VARCHAR(100) NOT NULL," +
                "display VARCHAR(100) NOT NULL," +
                "lBrace VARCHAR(100) NOT NULL," +
                "rBrace VARCHAR(100) NOT NULL," +
                "primaryColour VARCHAR(100) NOT NULL," +
                "secondaryColour VARCHAR(100) NOT NULL," +
                "nextRankId VARCHAR(100) NOT NULL," +
                "nextAdvancement VARCHAR(100) NOT NULL," +
                "level INT NOT NULL," +
                "PRIMARY KEY ( rankId )" +
                ");");
    }

    /**
     * @param rankId unique id for rank being deleted
     */
    public static void deleteRank(String rankId) {
        api.sqlUtils.removeRow("rankId", rankId, "rankPresets");
    }

    /**
     * @param rankId What is unique id of the rank you want to get
     * @return Rank object
     * @apiNote this method streamlines getting a rank object be doing all the information from the database for you
     */
    public static Rank getRank(String rankId) {
        return new Rank(
                rankId,
                api.sqlUtils.getString("display", "rankID", rankId, "rankPresets"),
                api.sqlUtils.getString("lBrace", "rankID", rankId, "rankPresets"),
                api.sqlUtils.getString("rBrace", "rankID", rankId, "rankPresets"),
                readColour(
                        api.sqlUtils.getString("primaryColour", "rankID", rankId, "rankPresets")
                ),
                readColour(
                        api.sqlUtils.getString("secondaryColour", "rankID", rankId, "rankPresets")
                ),
                api.sqlUtils.getString("nextRankId", "rankID", rankId, "rankPresets"),
                getAdvancement(
                        api.sqlUtils.getString("nextAdvancement", "rankID", rankId, "rankPresets")
                ),
                api.sqlUtils.getInt("level", "rankID", rankId, "rankPresets")
        );
    }

    /**
     * @param player Target player
     * @param rank Rank object being set
     */
    public static void setPlayerRank(Player player, Rank rank) {
        api.sqlUtils.setData(rank.getId(), "UUID", player.getUniqueId().toString(), "rank", "players");
    }

    /**
     * @param player Target player
     * @param rankId id of rank being set
     */
    public static void setPlayerRank(Player player, String rankId) {
        api.sqlUtils.setData(rankId, "UUID", player.getUniqueId().toString(), "rank", "players");
    }

    public static void setPlayerNameColour(Player player, String colour) {
        api.sqlUtils.setData(colour, "UUID", player.getUniqueId().toString(), "nameColour", "players");
    }

    /**
     * @param player Target player
     * @apiNote This method updates the players rank and nameColour thus must be called after setting either
     */
    public static void loadPlayerRank(Player player) {
        String rankId = api.sqlUtils.getString(
                "rank", "UUID", player.getUniqueId().toString(), "players"
        );
        Rank rank = getRank(rankId);

        //get colours and names
        ChatColor n = readColour(api.sqlUtils.getString(
                "nameColour", "UUID", player.getUniqueId().toString(), "players"
        ));
        ChatColor p = rank.getPrimary();
        ChatColor s = rank.getSecondary();
        String lb = rank.getlBrace();
        String rb = rank.getrBrace();
        String d = rank.getDisplay();
        String name = player.getName();

        player.setCustomName(s + lb + p + d + s + rb + n + name + ChatColor.WHITE + "");
    }

    /**
     * @param color Converts string of a ChatColour name to a ChatColour
     */
    public static ChatColor readColour(String color) {
        switch (color.toLowerCase()) {
            case "dark_red": return ChatColor.DARK_RED;
            case "red": return ChatColor.RED;
            case "gold": return ChatColor.GOLD;
            case "yellow": return ChatColor.YELLOW;
            case "dark_green": return ChatColor.DARK_GREEN;
            case "green": return ChatColor.GREEN;
            case "aqua": return ChatColor.AQUA;
            case "dark_aqua": return ChatColor.DARK_AQUA;
            case "dark_blue": return ChatColor.DARK_BLUE;
            case "blue": return ChatColor.BLUE;
            case "light_purple": return ChatColor.LIGHT_PURPLE;
            case "dark_purple": return ChatColor.DARK_PURPLE;
            case "white": return ChatColor.WHITE;
            case "gray": return ChatColor.GRAY;
            case "dark_gray": return ChatColor.DARK_GRAY;
            case "black": return ChatColor.BLACK;
            default:
                Bukkit.getLogger().warning("'" + color + "' is an invalid colour! try reloading the plugin.");
                Bukkit.getLogger().warning("Defaulting to white!");
                return ChatColor.WHITE;
        }
    }

    /**
     * @param player target player
     * @param name name of advancement
     * @return true if the target has the advancement and false if they don't
     */
    public static boolean hasAdvancement(Player player, String name) {
        // name should be something like minecraft:husbandry/break_diamond_hoe
        Advancement a = getAdvancement(name);
        if(a == null){
            // advancement does not exists.
            return false;
        }
        AdvancementProgress progress = player.getAdvancementProgress(a);
        // getting the progress of this advancement.
        return progress.isDone();
        //returns true or false.
    }

    /**
     * @param name String name of advancement must be in 'minecraft:story/mine_diamond' format
     * @return advancement if it exists and null if it doesn't
     */
    public static Advancement getAdvancement(String name) {
        Iterator<Advancement> it = Bukkit.getServer().advancementIterator();
        // gets all 'registered' advancements on the server.
        while (it.hasNext()) {
            // loops through these.
            Advancement a = it.next();
            if (a.getKey().toString().equalsIgnoreCase(name)) {
                //checks if one of these has the same name as the one you asked for. If so, this is the one it will return.
                return a;
            }
        }
        return null;
    }
}
