package indrocraftapi.datamanager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class ConfigUtils {

    private final String fileName;
    private final JavaPlugin plugin;

    private File configFile;
    private FileConfiguration fileConfiguration;

    public ConfigUtils(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
    }

    // gets all of the
    public void reloadConfig() {
        if (configFile == null) {
            File dataFolder = plugin.getDataFolder();
            if (dataFolder == null)
                throw new IllegalStateException();
            configFile = new File(dataFolder, fileName);
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

        // Look for defaults in the jar
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new File(this.getClass()
                .getClassLoader().getResource(fileName).getPath()));
        fileConfiguration.setDefaults(defConfig);
    }

    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            this.reloadConfig();
        }
        return fileConfiguration;
    }

    // Saves all the data to the selected yml file
    public void saveConfig() {
        if (fileConfiguration != null && configFile != null) {
            try {
                getConfig().save(configFile);
            } catch (IOException ex) {
                plugin.getLogger().severe("Could not save config to " + configFile);
                ex.printStackTrace();
            }
        }
    }

    // Saves

    /**
     * public void saveDefaultConfig() {
     * if (!configFile.exists()) {
     * this.plugin.saveResource(fileName, false);
     * }
     * }
     */

    // New save default config saving code, it checks if the needed config is in the jar file before
    // overriding it.
    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), fileName);
        }
        if (!configFile.exists()) {
            try {
                plugin.saveResource(fileName, false);
            } catch (IllegalArgumentException e) {
                Bukkit.getLogger().severe("Config file not loaded incorrect file name" + e.getCause());
            }
        }
    }
}
