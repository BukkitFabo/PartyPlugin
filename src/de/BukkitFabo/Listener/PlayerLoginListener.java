package de.BukkitFabo.Listener;

import de.BukkitFabo.PartyPlugin.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerLoginListener implements Listener {

	@EventHandler
	public void onPlayerLogin(ServerConnectEvent e) {
		ProxiedPlayer p = e.getPlayer();
		if(Main.sql.getToggleTP(p.getName()) == null) {
			Main.sql.queryUpdate("INSERT INTO `PartyPlugin` (Player, ToggleTP) VALUES ('" + p.getName() + "', 0)");
		}
	}
	
}
