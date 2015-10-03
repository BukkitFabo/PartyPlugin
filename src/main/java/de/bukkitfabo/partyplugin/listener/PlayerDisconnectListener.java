package de.bukkitfabo.partyplugin.listener;


import de.bukkitfabo.partyplugin.Main;
import de.bukkitfabo.partyplugin.party.PartyManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnectListener implements Listener {

	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent e) {
		ProxiedPlayer p = e.getPlayer();
		
		if(PartyManager.playerparty.containsKey(p)) {
			Main.getPartyManager().removePlayerFromParty(p, PartyManager.playerparty.get(p));
		}
		
	}
	
}
