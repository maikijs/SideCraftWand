package lv.side.craftwand.objects;

import java.util.ArrayList;
import java.util.List;

import lv.side.craftwand.Main;
import org.bukkit.Material;

public class ItemManager {
    List<CraftableItem> list = new ArrayList<>();

    public ItemManager() {
        setup();
    }

    private void setup() {
        for (String string : Main.inst.getConfig().getStringList("Craftables")) {
            String[] split = string.split(":");
            this.list.add(new CraftableItem(Material.valueOf(split[0]), Short.parseShort(split[1]), Integer.parseInt(split[2]), Material.valueOf(split[3])));
        }
    }

    public List<CraftableItem> getList() {
        return this.list;
    }

    public CraftableItem getFromType(Material type) {
        for (CraftableItem item : this.list) {
            if (item.getType() == type)
                return item;
        }
        return null;
    }
}