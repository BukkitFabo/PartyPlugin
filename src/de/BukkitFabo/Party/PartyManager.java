package de.BukkitFabo.Party;

import java.util.HashMap;
import java.util.Random;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import de.BukkitFabo.PartyPlugin.Main;

public class PartyManager {

	public static HashMap<ProxiedPlayer, Party> playerparty = new HashMap<ProxiedPlayer, Party>();
	public static HashMap<ProxiedPlayer, ProxiedPlayer> partyrequest = new HashMap<ProxiedPlayer, ProxiedPlayer>();
	
	@SuppressWarnings("deprecation")
	public static void sendPartyRequest(ProxiedPlayer player, Party party) {
		player.sendMessage(Main.prefix + "§7Du wurderst von §5" + party.getPartyOwner().getName() + " §7in die Party eingeladen.");		
		
		TextComponent message1 = new TextComponent("§7Bestätige §7mit ");
		TextComponent message2 = new TextComponent("§a/party §aaccept " + party.getPartyOwner().getName());
		message2.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/party accept " + party.getPartyOwner().getName()));
		TextComponent meesage3 = new TextComponent(" §7oder §7lehne §7die §7Anfrage §7mit ");
		TextComponent message4 = new TextComponent("§c/party §cdeny " + party.getPartyOwner().getName());
		message4.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/party deny " + party.getPartyOwner().getName()));
		TextComponent message5 = new TextComponent(" §7ab.");
		message1.addExtra(message2);
		message1.addExtra(meesage3);
		message1.addExtra(message4);
		message1.addExtra(message5);
		player.sendMessage(message1);
		
		
		//IChatBaseComponent baseComponent = ChatSerializer.a("[{\"text\":\""+"§6"+   (i+1)+". §5" + songs.get(i)+   "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/radio vote " + (i+1) + "\"}}]");
        //PacketPlayOutChat packet = new PacketPlayOutChat(baseComponent);
        //((CraftPlayer) c).getHandle().playerConnection.sendPacket(packet);
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
			} else {
				int i = new Random().nextInt(party.getPartyPlayers().size());
				setNewPartyOwner(party.partyPlayers.get(i), party);
			}
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void sendPartyMessage(Party party, String message) {
		
		for(ProxiedPlayer player : party.getPartyPlayers()) {
			player.sendMessage(Main.prefix + "" + message);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void sendPartyToServer(Party party, ServerInfo server) {
		
		for(ProxiedPlayer player : party.getPartyPlayers()) {
			if(player == party.getPartyOwner()) continue;
			if(hasToggledTP(player)) continue;
			player.connect(server);
			player.sendMessage(Main.prefix + "§7Die Party betritt den Server §6" + server.getName());
		}
		
	}
	
	public static void setNewPartyOwner(ProxiedPlayer player, Party party) {
		party.partyOwner = player;
		sendPartyMessage(party, "§7Der Spieler §5" + player.getName() + " §7ist der neue PartyOwner.");
	}
	
	public static boolean hasToggledTP(ProxiedPlayer player) {
		return (Main.sql.getToggleTP(player.getName()) == 1) ? true : false;
	}
	
	public static void setToggleTP(ProxiedPlayer player, boolean toggleTP) {
		Main.sql.setToggleTP(player.getName(), toggleTP);
	}
}
