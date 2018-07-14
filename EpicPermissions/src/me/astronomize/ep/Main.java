package me.astronomize.ep;

import java.lang.reflect.Array;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("unused")
public class Main extends JavaPlugin {
	
	PluginDescriptionFile pdf = this.getDescription();
	public void onEnable() {
		Bukkit.getLogger().log(Level.INFO, "[EpicPerms]The Plugin has been enabled.");
		this.saveDefaultConfig();
		

			for (Player pl  :  Bukkit.getOnlinePlayers()) {
				this.getConfig().addDefault("players." + pl.getUniqueId().toString(), "");
				this.getConfig().addDefault("players." + pl.getUniqueId().toString() + ".group", "default");
				this.getConfig().addDefault("players." + pl.getUniqueId().toString() + ".prefix", "");
				String uuid = pl.getUniqueId().toString();
				pl.setPlayerListName(this.getConfig().getString("players." + uuid + ".prefix" + pl.getDisplayName()));
				pl.setDisplayName(this.getConfig().getString("players." + uuid + ".prefix" + pl.getDisplayName()));

		}


		
	}
	
	
	public void onDisable() {
		Bukkit.getLogger().log(Level.INFO, "[EpicPermissions]The Plugin has been disabled.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String name = sender.getName();
		
		if (cmd.getName().equalsIgnoreCase("epicperms")) {
			String version = pdf.getVersion();
		
			//version
			if (args.length == 0) {
				sender.sendMessage(ChatColor.AQUA + "[EpicPerms] Version: " + version);
				Bukkit.getLogger().log(Level.INFO, "[EpicPerms] " + name + "ran the command /ultraperms.");
			}
			
			if (args.length == 1  || args.length > 1) {
				
				//reloada
				if (args[0].equalsIgnoreCase("reload")) {
					Bukkit.getPluginManager().getPlugin("EpicPermissions").reloadConfig();
					sender.sendMessage(ChatColor.AQUA + "[EpicPerms] Reloaded configuration.");
					Bukkit.getLogger().log(Level.INFO, "[EpicPerms] " + name + "ran the command /ultraperms reload.");
				}
				
				//add perm
				if (args[0].equalsIgnoreCase("add")) {
					String un = args[1];
					String perm = args[2];
					
					Player p = Bukkit.getPlayer(un);
					Bukkit.getPluginManager().getPermission(perm);
					Bukkit.getPluginManager().subscribeToPermission(perm, p);
				}
				
				//remove perm
				if (args[0].equalsIgnoreCase("remove")) {
					String un = args[1];
					String perm = args[2];
					
					Player p = Bukkit.getPlayer(un);
					if (p.hasPermission(perm)) {
						Bukkit.getPluginManager().getPermission(perm);
						Bukkit.getPluginManager().unsubscribeFromPermission(perm, p);
					} else {
						sender.sendMessage(ChatColor.RED + "This player doesn't have this permission.");
					}
				}
				
				//groups
				if (args[0].equalsIgnoreCase("group")) {
					String un = args[1];
					String group = args[2];
					
					Player p = Bukkit.getPlayer(un);
					String uuid = p.getUniqueId().toString();
					
					if (this.getConfig().contains(group)) {
						this.getConfig().set("players." + uuid + ".group", group);
						String prefix = this.getConfig().get("groups" + group + ".prefix").toString();
						Prefixes.givePrefix(p, prefix);
						for (String perms  :  this.getConfig().getStringList("groups." + group + ".permissions")) {
							perms = perms.toString();
							Bukkit.getPluginManager().getPermission(perms);
							Bukkit.getPluginManager().subscribeToPermission(perms, p);
						}
					}else {
						sender.sendMessage(ChatColor.RED + "This group doesnt exist.");
			        }
					
				}
				
			   if (args[0].equalsIgnoreCase("prefix")) {
				   String player = args[1];
				   String prefix = args[2];
				   
				   Player pl = Bukkit.getPlayer(player);
				   String uuid = pl.getUniqueId().toString();
				   Prefixes.givePrefix(pl, prefix);
				   this.getConfig().set("players." + uuid + "prefix", prefix);
			   }
			} 

		}
		//command style: /epicperms add {player} {permission}
		//group command: /epicperms group {player} {group}
		//prefix command /epicperms prefix {player} {prefix}
		return false;
	}
}























