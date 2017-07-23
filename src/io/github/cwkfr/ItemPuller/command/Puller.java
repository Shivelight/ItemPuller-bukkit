package io.github.cwkfr.ItemPuller.command;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.cwkfr.ItemPuller.Settings;
import io.github.cwkfr.ItemPuller.Utils;

public class Puller implements CommandExecutor {

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("puller")) {
      
      if (!sender.hasPermission("itempuller.puller")) {
        sender.sendMessage(Utils.colorize("&6[ItemPuller] &cYou don't have permission!"));
        return true;
      }
      
      if (args.length == 0) {
        sender.sendMessage(Utils.colorize("&6--- [ItemPuller] ---"));
        sender.sendMessage(Utils.colorize("&6/puller - ItemPuller help"));
        sender.sendMessage(Utils.colorize("&6/puller type - List item type"));
        sender.sendMessage(Utils.colorize("&6/puller setlore - Set lore to item"));
        sender.sendMessage(Utils.colorize("&6/puller reload - Reload ItemPuller"));
        sender.sendMessage(Utils.colorize("&6/pull - Pull item from player"));

        return true;
      }

      if (args[0].equalsIgnoreCase("type")) {
        sender.sendMessage(Utils.colorize("&6[ItemPuller] Type list:"));
        ConfigurationSection itemType = Settings.get().getConfigurationSection("ItemPuller");
        
        for (String type : itemType.getKeys(false)) {
          sender.sendMessage(Utils.colorize("&6- " + type));
        }
        
        return true;
      }

      if (args[0].equalsIgnoreCase("setlore")) {

        if (!(sender instanceof Player)) {
          sender.sendMessage(Utils.colorize("&6[ItemPuller] &cYou are not a plyer!"));
          return true;
        }

        Player p = (Player) sender;

        if (args.length == 1) {
          p.sendMessage(
              Utils.colorize("&6[ItemPuller] &cExpecting 1 parameters: <type>"));
          return true;
        }
        
        
        if (Settings.get().getString("ItemPuller." + args[1]) != null) {
            
            ItemStack hand = p.getInventory().getItemInMainHand();
            
            if (hand.getType() == Material.AIR){
              p.sendMessage(Utils.colorize("&6[ItemPuller] &cYou must hold an item!"));
              return true;
            }
            
            ItemMeta meta = hand.getItemMeta();
            ArrayList<String> lore = new ArrayList<String>();
            lore.add(Settings.get().getString("ItemPuller." + args[1]));
            meta.setLore(lore);
            hand.setItemMeta(meta);
            p.sendMessage(Utils.colorize("&6[ItemPuller] &aLore has been set!"));
            p.updateInventory();
            return true;
        }
        

        sender.sendMessage(Utils.colorize("&6[ItemPuller] &cType not found. List: /puller type"));
        return true;
      }

      if (args[0].equalsIgnoreCase("reload")){
        Settings.reload();
        sender.sendMessage(Utils.colorize("&6[ItemPuller] &aConfig reloaded!"));
      }
    }

    return true;
  }
}
