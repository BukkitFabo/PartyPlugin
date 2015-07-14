package de.BukkitFabo.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import de.BukkitFabo.Party.Party;
import de.BukkitFabo.Party.PartyManager;
import de.BukkitFabo.PartyPlugin.Main;

public class PartyCommand extends Command {

	public PartyCommand() {
		super("party");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage("Du musst ein Spieler sein.");
		}
		ProxiedPlayer p = (ProxiedPlayer) sender;
		
		if(args.length == 0) {
			
			p.sendMessage(Main.prefix + "§6/party invite <Spieler>");
			p.sendMessage(Main.prefix + "§6/party accept <Spieler>");
			p.sendMessage(Main.prefix + "§6/party deny <Spieler>");
			p.sendMessage(Main.prefix + "§6/party kick <Spieler>");
			p.sendMessage(Main.prefix + "§6/party msg <Nachricht>");
			p.sendMessage(Main.prefix + "§6/party promote <Spieler>");
			p.sendMessage(Main.prefix + "§6/party delete");
			p.sendMessage(Main.prefix + "§6/party leave");
			p.sendMessage(Main.prefix + "§6/party list");
			
		} else if(args.length == 1) {
			
			if(args[0].equalsIgnoreCase("delete")) {
				
			}
			if(args[0].equalsIgnoreCase("leave")) {
				
			}
			if(args[0].equalsIgnoreCase("list")) {
				if(PartyManager.playerparty.containsKey(p)) {
					Party party = PartyManager.playerparty.get(p);
					p.sendMessage(Main.prefix + "§7Hier sind alle Partymitglieder:");
					p.sendMessage(Main.prefix + "§7PartyOwner: §5" + party.getPartyOwner().getName());
					p.sendMessage(Main.prefix + "§7Partymitglieder: §5" + party.getPartyPlayers().toString());
				} else {
					p.sendMessage(Main.prefix + " §cDu bist in keiner Party. Erstelle eine indem du einem Spieler in deine Party einlädst.");
				}
			}
			
		} else if(args.length == 2) {
			
			if(args[0].equalsIgnoreCase("accept")) {
				if(PartyManager.partyrequest.containsKey(p)) {
					if(args[1].equalsIgnoreCase(PartyManager.partyrequest.get(p).getName())) {
						p.sendMessage(Main.prefix + "§aDu bist der Party von §5" + PartyManager.partyrequest.get(p).getName() + " §abeigetreten.");
						PartyManager.addPlayerToParty(p, PartyManager.playerparty.get(PartyManager.partyrequest.get(p)));
						PartyManager.partyrequest.remove(p);
					} else {
						p.sendMessage(Main.prefix + "§cDu hast von dem Spieler §5" + args[1] + " §ckeine Partyanfrage bekommen.");
					}
				}
			}
			if(args[0].equalsIgnoreCase("deny")) {
				if(PartyManager.partyrequest.containsKey(p)) {
					if(args[1].equalsIgnoreCase(PartyManager.partyrequest.get(p).getName())) {
						p.sendMessage(Main.prefix + "§7Du hast die Partyanfrage von §5" + args[1] + " §7abgelehnt.");
						PartyManager.partyrequest.get(p).sendMessage(Main.prefix + "§7Der Spieler §5" + p.getName() + " §7hat deine Partyanfrage abgelehnt.");
						PartyManager.partyrequest.remove(p);
					} else {
						p.sendMessage(Main.prefix + "§cDu hast von dem Spieler §5" + args[1] + " §ckeine Partyanfrage bekommen.");
					}
				}
			}
			if(args[0].equalsIgnoreCase("invite")) {
				if(PartyManager.playerparty.containsKey(p) && PartyManager.playerparty.get(p).getPartyOwner() != p) {
					p.sendMessage(Main.prefix + "§cDu bist nicht der PartyOwner deiner Party. Nur der PartyOwner kann Spieler in seine Party einladen.");
				} else {
					if(ProxyServer.getInstance().getPlayer(args[1]) == null) {
						p.sendMessage(Main.prefix + "§cDer Spieler §5" + args[1] + " §cist nicht online.");
						return;
					} else {
						if(!PartyManager.playerparty.containsKey(p)) {
							PartyManager.playerparty.put(p, new Party(p));
							PartyManager.playerparty.get(p).partyPlayers.add(p);
						}
						PartyManager.sendPartyRequest(ProxyServer.getInstance().getPlayer(args[1]), PartyManager.playerparty.get(p));
						p.sendMessage(Main.prefix + "§7Du hast dem Spieler §5" + args[1] + " §7eine Einladung in deine Party gesendet.");
					}
				}
				
			}
			if(args[0].equalsIgnoreCase("kick")) {
				
			}
			if(args[0].equalsIgnoreCase("promote")) {
				
			}
			
		} else if(args.length >= 2) {
			if(args[0].equalsIgnoreCase("msg")) {
				if(PartyManager.playerparty.containsKey(p)) {
					String message = "";
					for(int i = 1; i < args.length; i++) {
						message = message + args[i] + " ";
					}
					String msg = "§5" + p.getName() + "§7: §f" + message;
					PartyManager.sendPartyMessage(PartyManager.playerparty.get(p), msg);
				} else {
					p.sendMessage(Main.prefix + " §cDu bist in keiner Party. Erstelle eine indem du einem Spieler in deine Party einlädst.");
				}
			}
		}
		
	}

}
