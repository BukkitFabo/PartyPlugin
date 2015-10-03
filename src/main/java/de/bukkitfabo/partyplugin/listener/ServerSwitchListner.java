package de.bukkitfabo.partyplugin.listener;


import de.bukkitfabo.partyplugin.Main;
import de.bukkitfabo.partyplugin.party.PartyManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerSwitchListner implements Listener {

	@EventHandler
	public void onServerConnect(ServerConnectEvent e) {
		ProxiedPlayer p = e.getPlayer();
		
		if(PartyManager.playerparty.containsKey(p) && PartyManager.playerparty.get(p).getPartyOwner() == p) {
			Main.getPartyManager().sendPartyToServer(PartyManager.playerparty.get(p), e.getTarget());
		}
		
	}
	
}
