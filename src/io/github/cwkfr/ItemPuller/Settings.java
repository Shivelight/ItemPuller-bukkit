package io.github.cwkfr.ItemPuller;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Settings {
  static FileConfiguration configFile;
  static File file;

  public static void setup(Plugin plugin) {
    plugin.saveDefaultConfig();
    Settings.file = new File(plugin.getDataFolder(), "config.yml");
    Settings.configFile = (FileConfiguration) YamlConfiguration.loadConfiguration(Settings.file);
  }

  public static FileConfiguration get() {
    return Settings.configFile;
  }

  public static void save() {
    try {
      Settings.configFile.save(Settings.file);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(Utils.colorize("&cCould not save config.yml!"));
    }
  }

  public static void reload() {
    Settings.configFile = (FileConfiguration) YamlConfiguration.loadConfiguration(Settings.file);
  }

}