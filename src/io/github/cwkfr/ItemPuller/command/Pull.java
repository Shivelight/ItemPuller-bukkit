package io.github.cwkfr.ItemPuller.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.cwkfr.ItemPuller.Settings;
import io.github.cwkfr.ItemPuller.Utils;

public class Pull implements CommandExecutor {

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("pull")) {

      if (!sender.hasPermission("itempuller.puller")) {
        sender.sendMessage(Utils.colorize("&6[ItemPuller] &cYou don't have permission!"));
        return true;
      }

      if (args.length == 2) {

        Player target = Bukkit.getServer().getPlayer(args[0]);

        if (Bukkit.getServer().getPlayer(args[0]) == null) {
          sender.sendMessage(Utils.colorize("&6[ItemPuller] &cPlayer is not online!"));
          return true;
        }

        ConfigurationSection itemType = Settings.get().getConfigurationSection("ItemPuller");

        for (String type : itemType.getKeys(false)) {
          if (args[1].equalsIgnoreCase(type)) {
            removeItem(target, type);
            sender.sendMessage(Utils.colorize("&6[ItemPuller] &aPulled " + type + " item from " + target.getDisplayName()));
            return true;
          }
        }

      } else {
        sender.sendMessage(Utils.colorize("&6[ItemPuller] &cExpecting 2 parameters: <player> <type>"));
        return true;
      }
    }
    
    return true;
  }

  private void removeItem(Player player, String string) {
    ItemStack[] content = player.getInventory().getContents();
    
    for (int i = 0; i <= content.length - 1; i++) {
      ItemStack is = content[i];
      
      if (is == null){
        continue;
      }
      
      
      if (is.hasItemMeta()) {
        System.out.println(Settings.get().getString("ItemPuller." + string));
        System.out.println("lore:");
        System.out.println(is.getItemMeta().getLore().get(0));
        if (is.getItemMeta().getLore().get(0).equals(Settings.get().getString("ItemPuller." + string))) {
          content[i] = new ItemStack(Material.AIR);
        }
      }

    }
    player.getInventory().setContents(content);
    player.updateInventory();
  }
}
