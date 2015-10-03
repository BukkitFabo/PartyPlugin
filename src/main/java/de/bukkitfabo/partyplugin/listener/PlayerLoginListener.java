package de.bukkitfabo.partyplugin.listener;


import de.bukkitfabo.partyplugin.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerLoginListener implements Listener {

	@EventHandler
	public void onPlayerLogin(ServerConnectEvent e) {
		ProxiedPlayer p = e.getPlayer();
		Main.getSQL().createStatement("INSERT INTO IGNORE `PartyPlugin` (PlayerUUID) VALUES (:PlayerUUID)")
		.bind("PlayerUUID", p.getUniqueId().toString()).execute();
	}
	
}
