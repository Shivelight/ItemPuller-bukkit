package io.github.cwkfr.ItemPuller;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.cwkfr.ItemPuller.command.Pull;
import io.github.cwkfr.ItemPuller.command.Puller;

public class ItemPuller extends JavaPlugin {

  
  public void onEnable() {
    Settings.setup(this);
    
    getCommand("puller").setExecutor(new Puller());
    getCommand("pull").setExecutor(new Pull());
    
    getLogger().info("ItemPuller has been enabled!");
  }
  
}
