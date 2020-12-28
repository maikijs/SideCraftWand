package lv.side.craftwand.event;

import de.tr7zw.nbtapi.NBTItem;
import lv.side.craftwand.Main;
import lv.side.craftwand.objects.Wand;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class ChestClickEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChestClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.isCancelled())
            return;
        if (!player.hasPermission("sidecraftwand.use"))
            return;
        if (player.getInventory().getItemInHand().getType() != Material.valueOf(Main.inst.getConfig().getString("Item.type")))
            return;
        if (e.getClickedBlock() == null || (e.getClickedBlock().getType() != Material.CHEST && e.getClickedBlock().getType() != Material.TRAPPED_CHEST))
            return;
        NBTItem nbtItem = new NBTItem(player.getInventory().getItemInHand());
        if (nbtItem.hasKey("CraftWand").booleanValue()) {
            e.setCancelled(true);
            Wand wand = new Wand();
            Chest chest = (Chest)e.getClickedBlock().getState();
            Inventory inventory = chest.getInventory();
            wand.run(player, inventory);
        }
    }
}

