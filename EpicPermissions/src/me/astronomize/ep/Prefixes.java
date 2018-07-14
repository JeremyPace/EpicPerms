package me.astronomize.ep;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class Prefixes extends JavaPlugin {
	public void onEnable() {
		this.saveDefaultConfig();
	}
	
	public void onDisable() {
		
	}
	public static void givePrefix(Player p, String prefix) {
		p.setDisplayName(prefix + p.getDisplayName());
		p.setPlayerListName(prefix + p.getDisplayName());
		
		
	}
}
