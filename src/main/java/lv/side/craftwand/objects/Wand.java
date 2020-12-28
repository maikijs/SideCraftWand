package lv.side.craftwand.objects;

import de.tr7zw.nbtapi.NBTItem;
import lv.side.craftwand.Main;
import lv.side.craftwand.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Wand {
    public ItemStack getItem(int uses) {
        ItemStack itemStack = new ItemStack(Material.valueOf(Main.inst.getConfig().getString("Item.type")));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Util.format(Main.inst.getConfig().getString("Item.name")));
        List<String> lore = new ArrayList<>();
        for (String loreLine : Main.inst.getConfig().getStringList("Item.lore")) {
            if (uses == -1) {
                lore.add(Util.format(loreLine.replace("{uses}", "Infinite")));
                continue;
            }
            lore.add(Util.format(loreLine.replace("{uses}", uses + "")));
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("CraftWand", Boolean.valueOf(true));
        nbtItem.setInteger("Durability", Integer.valueOf(uses));
        return nbtItem.getItem();
    }

    public void run(Player player, Inventory inventory) {
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)Main.inst, () -> {
            int total = craft(player, inventory);
            if (total > 0)
                takeDurability(player, total);
        });
    }

    public int craft(Player player, Inventory inventory) {
        int total = 0;
        ItemStack[] contents = inventory.getContents();
        for (int length = contents.length, i = 0; i < length; i++) {
            ItemStack item = contents[i];
            if (item != null && item.getType() != Material.AIR) {
                CraftableItem craftable = Main.inst.itemManager.getFromType(item.getType());
                if (craftable != null) {
                    Material result = craftable.getResult();
                    int amount = craftable.getAmount();
                    int crafted = item.getAmount() / amount;
                    int overflow = item.getAmount() % amount;
                    if (crafted > 0) {
                        total += crafted;
                        inventory.setItem(i, null);
                        inventory.addItem(new ItemStack[] { new ItemStack(result, crafted, craftable.getData()) });
                        if (overflow > 0) {
                            item.setAmount(overflow);
                            inventory.addItem(new ItemStack[] { item });
                        }
                    }
                }
            }
        }
        return total;
    }

    public void takeDurability(Player player, int total) {
        player.sendMessage(Util.format(Main.inst.getConfig().getString("Messages.succesfully-crafted").replace("{items}", total + "")));
        ItemStack item = player.getInventory().getItemInHand();
        NBTItem nbtItem = new NBTItem(item);
        if (nbtItem.getInteger("Durability").intValue() != -1) {
            nbtItem.setInteger("Durability", Integer.valueOf(nbtItem.getInteger("Durability").intValue() - 1));
            if (nbtItem.getInteger("Durability").intValue() <= 0) {
                player.getInventory().setItem(player.getInventory().getHeldItemSlot(), null);
                player.sendMessage(Util.format(Main.inst.getConfig().getString("Messages.wand-destroyed")));
            } else {
                ItemStack itemStack = nbtItem.getItem();
                int durabilityLeft = nbtItem.getInteger("Durability").intValue();
                itemStack.setItemMeta(getItem(durabilityLeft).getItemMeta());
                player.getInventory().setItemInHand(itemStack);
                player.getInventory().getItemInHand().setAmount(1);
            }
        }
    }
}

