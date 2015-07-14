package de.BukkitFabo.PartyPlugin;

import de.BukkitFabo.Commands.PartyCommand;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {

	public static String prefix = "§7[§5§lParty§r§7] ";
	
	public static Plugin plugin;
	public static Plugin getPlugin() {
		return plugin;
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		
		getProxy().getPluginManager().registerCommand(this, new PartyCommand());
	}
	
	@Override
	public void onDisable() {
		plugin = null;
	}
	
}
