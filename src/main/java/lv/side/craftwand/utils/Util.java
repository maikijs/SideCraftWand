package lv.side.craftwand.utils;


import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Util {
    public static String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static ItemStack glowify(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        meta.addEnchant(Enchantment.DURABILITY, 0, true);
        item.setItemMeta(meta);
        return item;
    }
}