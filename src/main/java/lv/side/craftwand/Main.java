package lv.side.craftwand;

import lv.side.craftwand.cmd.CraftWandCmd;
import lv.side.craftwand.event.ChestClickEvent;
import lv.side.craftwand.objects.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main inst;

    public ItemManager itemManager;

    public void onEnable() {
        inst = this;
        saveDefaultConfig();
        this.itemManager = new ItemManager();
        getCommand("craftwand").setExecutor((CommandExecutor)new CraftWandCmd());
        Bukkit.getPluginManager().registerEvents((Listener)new ChestClickEvent(), (Plugin)this);
    }

    public void onDisable() {
        inst = null;
    }
}

