package de.BukkitFabo.Party;

import java.util.HashMap;
import java.util.Random;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import de.BukkitFabo.PartyPlugin.Main;

public class PartyManager {

	public static HashMap<ProxiedPlayer, Party> playerparty = new HashMap<ProxiedPlayer, Party>();
	public static HashMap<ProxiedPlayer, ProxiedPlayer> partyrequest = new HashMap<ProxiedPlayer, ProxiedPlayer>();
	
	@SuppressWarnings("deprecation")
	public static void sendPartyRequest(ProxiedPlayer player, Party party) {
		player.sendMessage(Main.prefix + "§7Du wurderst von §5" + party.getPartyOwner().getName() + " §7in die Party eingeladen.");
		player.sendMessage(Main.prefix + "§7Bestätige mit §a/party accept " + party.getPartyOwner().getName() + " §7oder lehne die Anfrage mit §c/party deny " + party.getPartyOwner().getName() + " §7ab.");
		partyrequest.put(player, party.getPartyOwner());
	}
	
	public static void addPlayerToParty(ProxiedPlayer player, Party party) {
		
		playerparty.put(player, party);
		party.partyPlayers.add(player);
		sendPartyMessage(party, "§aDer Spieler §5" + player.getName() + " §aist der Party beigetreten.");
		
	}
	
	public static void removePlayerFromParty(ProxiedPlayer player, Party party) {
		
		party.partyPlayers.remove(player);
		playerparty.remove(player);
		sendPartyMessage(party, "§cDer Spieler §5" + player.getName() + " §chat die Party verlassen.");
		if(party.getPartyOwner() == player) {
			if(party.getPartyPlayers().size() <= 0) {
				sendPartyMessage(party, "§cDie Party wurde aufgelöst, da zu viele Spieler die Party verlassen haben.");
			}
			int i = new Random().nextInt(party.getPartyPlayers().size());
			setNewPartyOwner(party.partyPlayers.get(i), party);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void sendPartyMessage(Party party, String message) {
		
		for(ProxiedPlayer player : party.getPartyPlayers()) {
			player.sendMessage(Main.prefix + " " + message);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void sendPartyToServer(Party party, Server server) {
		
		for(ProxiedPlayer player : party.getPartyPlayers()) {
			player.connect(server.getInfo());
			player.sendMessage(Main.prefix + "§7Die Party betritt den Server §6" + server.getInfo().getName());
		}
		
	}
	
	
	public static void setNewPartyOwner(ProxiedPlayer player, Party party) {
		party.partyOwner = player;
		sendPartyMessage(party, "§7Der Spieler §5" + player.getName() + " §7ist der neue PartyOwner.");
	}
}
