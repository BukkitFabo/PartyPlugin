package de.BukkitFabo.Party;

import java.util.ArrayList;

import net.md_5.bungee.api.connection.ProxiedPlayer;


public class Party {

	public ProxiedPlayer partyOwner;
	public ArrayList<ProxiedPlayer> partyPlayers = new ArrayList<ProxiedPlayer>();
	public ArrayList<ProxiedPlayer> partyRequests = new ArrayList<ProxiedPlayer>();
	
	
	public Party(ProxiedPlayer partyOwner) {
		this.partyOwner = partyOwner;
	}
	
	public ProxiedPlayer getPartyOwner() {
		return partyOwner;
	}
	
	public ArrayList<ProxiedPlayer> getPartyPlayers() {
		return partyPlayers;
	}
	
	public ArrayList<ProxiedPlayer> getPartyRequests() {
		return partyRequests;
	}
	
}
