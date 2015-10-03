package de.bukkitfabo.partyplugin.party;


import java.util.ArrayList;

import net.md_5.bungee.api.connection.ProxiedPlayer;


public class Party {

	public ProxiedPlayer partyOwner;
	public ArrayList<ProxiedPlayer> partyPlayers = new ArrayList<ProxiedPlayer>();
	
	
	public Party(ProxiedPlayer partyOwner) {
		this.partyOwner = partyOwner;
	}
	
	public void setPartyOwner(ProxiedPlayer partyOwner) {
		this.partyOwner = partyOwner;
	}
	public ProxiedPlayer getPartyOwner() {
		return partyOwner;
	}
	
	public void setPartyPlayers(ArrayList<ProxiedPlayer> partyPlayers) {
		this.partyPlayers = partyPlayers;
	}
	public ArrayList<ProxiedPlayer> getPartyPlayers() {
		return partyPlayers;
	}
	
}
