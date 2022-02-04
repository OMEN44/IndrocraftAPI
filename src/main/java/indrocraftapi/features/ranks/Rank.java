package indrocraftapi.features.ranks;

import org.bukkit.ChatColor;
import org.bukkit.advancement.Advancement;

public class Rank {
    private String id;
    private String display;
    private String lBrace, rBrace;
    private ChatColor primary;
    private ChatColor secondary;
    private String nextRankId;
    private Advancement nextAdvancement;
    private Integer level;

    /**
     * @param id Unique id of rank
     * @param display This is the name displayed in game
     * @param lBrace This is added to the start of a rank default is '[' (optional)
     * @param rBrace This is added to the end of a rank default is ']' (optional)
     * @param primary This is the colour that the name of the rank is set to (optional)
     * @param secondary This is the colour that the braces are set to (optional)
     * @param nextRankId ID of the rank that players get next (optional)
     * @param nextAdvancement Advancement required for the next rank to be achieved (optional)
     * @param level This level is used as a multiplier for rank abilities higher better (optional)
     * @apiNote If getting data from an optional param you need to check for "" and do not check for null
     */
    public Rank(String id, String display, String lBrace, String rBrace, ChatColor primary, ChatColor secondary, String nextRankId, Advancement nextAdvancement, Integer level) {
        this.id = id;
        this.display = display;
        this.lBrace = lBrace;
        this.rBrace = rBrace;
        this.primary = primary;
        this.secondary = secondary;
        this.nextRankId = nextRankId;
        this.nextAdvancement = nextAdvancement;
        this.level = level;
    }

    public String getId() {
        return id;
    }
    public String getDisplay() {
        return display;
    }
    public String getlBrace() {
        return lBrace;
    }
    public String getrBrace() {
        return rBrace;
    }
    public ChatColor getPrimary() {
        return primary;
    }
    public ChatColor getSecondary() {
        return secondary;
    }
    public String getNextRankId() {
        return nextRankId;
    }
    public Advancement getNextAdvancement() {
        return nextAdvancement;
    }
    public Integer getLevel() {
        return level;
    }
}
