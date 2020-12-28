package lv.side.craftwand.cmd;

import lv.side.craftwand.Main;
import lv.side.craftwand.objects.Wand;
import lv.side.craftwand.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CraftWandCmd implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("craftwand.admin")) {
            if (args.length == 0) {
                sender.sendMessage("");
                sender.sendMessage(Util.format("&6&lCraft Wands"));
                sender.sendMessage("");
                sender.sendMessage(Util.format("&e/craftwand give [player] [amount] [uses]"));
                sender.sendMessage("");
                return false;
            }
            if (args.length == 4 && args[0].equalsIgnoreCase("give")) {
                Player target = Bukkit.getPlayer(args[1]);
                int amount = Integer.parseInt(args[2]);
                int uses = Integer.parseInt(args[3]);
                if (uses == 0) {
                    sender.sendMessage(Util.format(Main.inst.getConfig().getString("Messages.invalid-uses")));
                    return false;
                }
                if (target == null) {
                    sender.sendMessage(Util.format(Main.inst.getConfig().getString("Messages.not-a-player")));
                    return false;
                }
                if (amount == 0) {
                    sender.sendMessage(Util.format(Main.inst.getConfig().getString("Messages.invalid-amount")));
                    return false;
                }
                Wand wand = new Wand();
                ItemStack item = wand.getItem(uses);
                item.setAmount(amount);
                if (Main.inst.getConfig().getBoolean("Item.glowing"))
                    item = Util.glowify(item);
                target.getInventory().addItem(new ItemStack[] { item });
                target.sendMessage(Util.format(Main.inst.getConfig().getString("Messages.wand-given")));
                return true;
            }
        }
        return false;
    }
}

