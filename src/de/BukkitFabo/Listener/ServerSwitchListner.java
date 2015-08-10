package de.BukkitFabo.Listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import de.BukkitFabo.Party.PartyManager;

public class ServerSwitchListner implements Listener {

	@EventHandler
	public void onServerConnect(ServerConnectEvent e) {
		ProxiedPlayer p = e.getPlayer();
		
		if(PartyManager.playerparty.containsKey(p) && PartyManager.playerparty.get(p).getPartyOwner() == p) {
			PartyManager.sendPartyToServer(PartyManager.playerparty.get(p), e.getTarget());
		}
		
	}
	
}
