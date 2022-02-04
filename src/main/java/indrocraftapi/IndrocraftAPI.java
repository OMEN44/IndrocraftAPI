package indrocraftapi;

import indrocraftapi.datamanager.SQLUtils;
import indrocraftapi.features.ranks.Rank;
import indrocraftapi.features.ranks.RankEditor;
import indrocraftapi.features.ranks.RankUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class IndrocraftAPI extends JavaPlugin {

    public SQLUtils sqlUtils = new SQLUtils("test", "localhost", "3306", "root", "");

    @Override
    public void onEnable() {
        RankEditor rb = new RankEditor("toot", "name").setPrimary(ChatColor.LIGHT_PURPLE)
                .setSecondary(ChatColor.DARK_AQUA).save(sqlUtils);

        Rank rank = RankUtils.getRank("toot");
        if (rank.getrBrace().equals(""))
            Bukkit.getLogger().severe(rank.getlBrace() + " ''");
        if (rank.getrBrace() == null)
            Bukkit.getLogger().severe(rank.getlBrace() + "null");
    }
}
