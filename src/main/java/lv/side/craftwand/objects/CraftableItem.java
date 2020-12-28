package lv.side.craftwand.objects;

import org.bukkit.Material;

public class CraftableItem {
    private Material type;

    private short data;

    private int amount;

    private Material result;

    public CraftableItem(Material type, short data, int amount, Material result) {
        this.type = type;
        this.data = data;
        this.amount = amount;
        this.result = result;
    }

    public Material getType() {
        return this.type;
    }

    public int getAmount() {
        return this.amount;
    }

    public Material getResult() {
        return this.result;
    }

    public short getData() {
        return this.data;
    }
}

