package indrocraftapi.features.ranks;

import indrocraftapi.datamanager.SQLUtils;
import org.bukkit.ChatColor;
import org.bukkit.advancement.Advancement;

public class RankEditor {
    /**
     * @param rankID unique name for new rank
     * @param displayName name displayed in game of rank
     * @apiNote These are the only 2 required parameters the rest can be set individual
     */
    public RankEditor(String rankID, String displayName) {
        this.id = rankID;
        this.display = displayName;
    }

    /**
     * @param sqlUtils connection to database
     */
    public RankEditor save(SQLUtils sqlUtils) {
        if (!sqlUtils.rowExists("rankID", this.id, "rankPresets"))
            sqlUtils.createRow("rankID", this.id, "rankPresets");
        sqlUtils.setData(this.display, "rankID", this.id, "display", "rankPresets");
        if (this.lBrace != null)
            sqlUtils.setData(this.lBrace, "rankID", this.id, "lBrace", "rankPresets");
        if (this.rBrace != null)
            sqlUtils.setData(this.rBrace, "rankID", this.id, "rBrace", "rankPresets");
        if (this.primary != null)
            sqlUtils.setData(this.primary, "rankID", this.id, "primaryColour", "rankPresets");
        if (this.secondary != null)
            sqlUtils.setData(this.secondary, "rankID", this.id, "secondaryColour", "rankPresets");
        if (this.nextRankId != null)
            sqlUtils.setData(this.nextRankId, "rankID", this.id, "nextRankId", "rankPresets");
        if (this.nextAdvancement != null)
            sqlUtils.setData(this.nextAdvancement, "rankID", this.id, "nextAdvancement", "rankPresets");
        if (this.level != null)
            sqlUtils.setData(this.level, "rankID", this.id, "level", "rankPresets");
        return this;
    }

    private String id;
    private String display;
    private String lBrace, rBrace;
    private String primary;
    private String secondary;
    private String nextRankId;
    private String nextAdvancement;
    private String level;

    public RankEditor setId(String id) {
        this.id = id;
        return this;
    }
    public RankEditor setDisplay(String display) {
        this.display = display;
        return this;
    }
    public RankEditor setBraces(String lBrace, String rBrace) {
        this.lBrace = lBrace;
        this.rBrace = rBrace;
        return this;
    }
    public RankEditor setPrimary(ChatColor primary) {
        this.primary = primary.name();
        return this;
    }
    public RankEditor setSecondary(ChatColor secondary) {
        this.secondary = secondary.name();
        return this;
    }
    public RankEditor setNextRankId(String nextRankId) {
        this.nextRankId = nextRankId;
        return this;
    }
    public RankEditor setNextAdvancement(Advancement nextAdvancement) {
        this.nextAdvancement = nextAdvancement.getKey().getKey();
        return this;
    }
    public RankEditor setLevel(Integer level) {
        this.level = level.toString();
        return this;
    }
}