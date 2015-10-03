package de.bukkitfabo.partyplugin.commands;


import de.bukkitfabo.partyplugin.Main;
import de.bukkitfabo.partyplugin.party.Party;
import de.bukkitfabo.partyplugin.party.PartyManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PartyCommand extends Command {

	public PartyCommand() {
		super("party");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(new TextComponent("Du musst ein Spieler sein."));
		}
		ProxiedPlayer p = (ProxiedPlayer) sender;
		
		if(args.length == 0) {
			
			p.sendMessage(new TextComponent(Main.prefix + "§6/party invite <Spieler> §f- Lädt einen Spieler in deine Party ein."));
			p.sendMessage(new TextComponent(Main.prefix + "§6/party accept <Spieler> §f- Akzeptiert die Partyeinladung von einem Spieler."));
			p.sendMessage(new TextComponent(Main.prefix + "§6/party deny <Spieler> §f- Lehnt die Partyeinladung von einem Spieler ab."));
			p.sendMessage(new TextComponent(Main.prefix + "§6/party kick <Spieler> §f- Kick einen Spieler aus deiner Party."));
			p.sendMessage(new TextComponent(Main.prefix + "§6/party msg <Nachricht> §f- Sendet an alle Spieler in der Party eine Nachricht."));
			p.sendMessage(new TextComponent(Main.prefix + "§6/party promote <Spieler> §f- Setzt einen neuen PartyOwner."));
			p.sendMessage(new TextComponent(Main.prefix + "§6/party delete §f- Löst die existierende Party auf."));
			p.sendMessage(new TextComponent(Main.prefix + "§6/party leave §f- Verlässt die Party, in der du bist."));
			p.sendMessage(new TextComponent(Main.prefix + "§6/party list §f- Zeigt dir alle Spieler die in der Party sind."));
			p.sendMessage(new TextComponent(Main.prefix + "§6/party toggle §f- Aktiviert oder Deaktiviert deine Partyanfragen."));
			
		} else if(args.length == 1) {
			
			if(args[0].equalsIgnoreCase("delete")) {
				if(PartyManager.playerparty.containsKey(p) && PartyManager.playerparty.get(p).getPartyOwner() != p) {
					p.sendMessage(new TextComponent(Main.prefix + "§cDu bist nicht der PartyOwner deiner Party. Nur der PartyOwner kann seine Party auflösen."));
				} else {
					Main.getPartyManager().sendPartyMessage(PartyManager.playerparty.get(p), "§cDer PartyOwner hat die Party aufgelöst.");
					for(ProxiedPlayer players : PartyManager.playerparty.get(p).getPartyPlayers()) {
						PartyManager.playerparty.remove(players);
					}
				}
			}
			
			if(args[0].equalsIgnoreCase("leave")) {
				if(PartyManager.playerparty.containsKey(p)) {
					p.sendMessage(new TextComponent(Main.prefix + "§7Du hast die Party von §5" + PartyManager.playerparty.get(p).getPartyOwner().getName() + " §7verlassen."));
					Main.getPartyManager().removePlayerFromParty(p, PartyManager.playerparty.get(p));
				}
			}
			
			if(args[0].equalsIgnoreCase("list")) {
				if(PartyManager.playerparty.containsKey(p)) {
					Party party = PartyManager.playerparty.get(p);
					p.sendMessage(new TextComponent(Main.prefix + "§7Hier sind alle Partymitglieder:"));
					p.sendMessage(new TextComponent(Main.prefix + "§7PartyOwner: §5" + party.getPartyOwner().getName()));
					p.sendMessage(new TextComponent(Main.prefix + "§7Partymitglieder: §5" + party.getPartyPlayers().toString().replace('[', ' ').replace(']', ' ')));
				} else {
					p.sendMessage(new TextComponent(Main.prefix + "§cDu bist in keiner Party. Erstelle eine indem du einem Spieler in deine Party einlädst."));
				}
			}
			
			if(args[0].equalsIgnoreCase("toggle")) {
				boolean toggle = Main.getPartyManager().getPartyToggle(p);
				if(toggle) {
					Main.getSQL().createStatement("UPDATE `PartyPlugin` SET `PartyToggle` = :PartyToggle WHERE `PlayerUUID` = :PlayerUUID")
					.bind("PartyToggle", 0).bind("PlayerUUID", p.getUniqueId().toString()).execute();
					p.sendMessage(new TextComponent(Main.prefix + "§7Du bekommst nun §ckeine §7Partyanfragen mehr."));
				} else {
					Main.getSQL().createStatement("UPDATE `PartyPlugin` SET `PartyToggle` = :PartyToggle WHERE `PlayerUUID` = :PlayerUUID")
					.bind("PartyToggle", 1).bind("PlayerUUID", p.getUniqueId().toString()).execute();
					p.sendMessage(new TextComponent(Main.prefix + "§7Du bekommst nun §awieder §7Partyanfragen."));
				}
			}
			
		} else if(args.length >= 2) {
			
			if(args[0].equalsIgnoreCase("msg")) {
				if(PartyManager.playerparty.containsKey(p)) {
					String message = "";
					for(int i = 1; i < args.length; i++) {
						message = message + args[i] + " ";
					}
					String msg = "§5" + p.getName() + "§7:§f " + message;
					Main.getPartyManager().sendPartyMessage(PartyManager.playerparty.get(p), msg);
				} else {
					p.sendMessage(new TextComponent(Main.prefix + "§cDu bist in keiner Party. Erstelle eine indem du einem Spieler in deine Party einlädst."));
				}
			}
			
			if(args[0].equalsIgnoreCase("accept")) {
				if(PartyManager.partyrequest.containsKey(p)) {
					if(args[1].equalsIgnoreCase(PartyManager.partyrequest.get(p).getName())) {
						p.sendMessage(new TextComponent(Main.prefix + "§aDu bist der Party von §5" + PartyManager.partyrequest.get(p).getName() + " §abeigetreten."));
						Main.getPartyManager().addPlayerToParty(p, PartyManager.playerparty.get(PartyManager.partyrequest.get(p)));
						PartyManager.partyrequest.remove(p);
					} else {
						p.sendMessage(new TextComponent(Main.prefix + "§cDu hast von dem Spieler §5" + args[1] + " §ckeine Partyanfrage bekommen."));
					}
				}
			}
			
			if(args[0].equalsIgnoreCase("deny")) {
				if(PartyManager.partyrequest.containsKey(p)) {
					if(args[1].equalsIgnoreCase(PartyManager.partyrequest.get(p).getName())) {
						p.sendMessage(new TextComponent(Main.prefix + "§7Du hast die Partyanfrage von §5" + args[1] + " §7abgelehnt."));
						PartyManager.partyrequest.get(p).sendMessage(new TextComponent(Main.prefix + "§7Der Spieler §5" + p.getName() + " §7hat deine Partyanfrage abgelehnt."));
						PartyManager.partyrequest.remove(p);
					} else {
						p.sendMessage(new TextComponent(Main.prefix + "§cDu hast von dem Spieler §5" + args[1] + " §ckeine Partyanfrage bekommen."));
					}
				}
			}
			
			if(args[0].equalsIgnoreCase("invite")) {
				
				if(PartyManager.playerparty.containsKey(p) && PartyManager.playerparty.get(p).getPartyOwner() != p) {
					p.sendMessage(new TextComponent(Main.prefix + "§cDu bist nicht der PartyOwner deiner Party. Nur der PartyOwner kann Spieler in seine Party einladen."));
				} else {
					
					if(ProxyServer.getInstance().getPlayer(args[1]) == null) {
						p.sendMessage(new TextComponent(Main.prefix + "§cDer Spieler §5" + args[1] + " §cist nicht online."));
						return;
					}
					
					if(ProxyServer.getInstance().getPlayer(args[1]) == p) {
						p.sendMessage(new TextComponent(Main.prefix + "§cDu kannst dich nicht selber in eine Party einladen."));
						return;
					}
					
					if(!Main.getPartyManager().getPartyToggle(ProxyServer.getInstance().getPlayer(args[1]))) {
						p.sendMessage(new TextComponent(Main.prefix + "§cDer Spieler §5" + args[1] + " §cnimmt keine Partyanfragen an."));
						return;
					}
					
					if(!PartyManager.playerparty.containsKey(p)) {
						PartyManager.playerparty.put(p, new Party(p));
						PartyManager.playerparty.get(p).partyPlayers.add(p);
					}
					
					Main.getPartyManager().sendPartyRequest(ProxyServer.getInstance().getPlayer(args[1]), PartyManager.playerparty.get(p));
					p.sendMessage(new TextComponent(Main.prefix + "§7Du hast dem Spieler §5" + args[1] + " §7eine Einladung in deine Party gesendet."));
				}
				
			}
			
			if(args[0].equalsIgnoreCase("kick")) {
				if(PartyManager.playerparty.containsKey(p) && PartyManager.playerparty.get(p).getPartyOwner() != p) {
					p.sendMessage(new TextComponent(Main.prefix + "§cDu bist nicht der PartyOwner deiner Party. Nur der PartyOwner kann Spieler in seine Party kicken."));
				} else {
					if(ProxyServer.getInstance().getPlayer(args[1]) != null && PartyManager.playerparty.containsKey(ProxyServer.getInstance().getPlayer(args[1]))) {
						p.sendMessage(new TextComponent(Main.prefix + "§7Du hast den Spieler §5" + args[1] + " §7aus deiner Party gekickt."));
						ProxyServer.getInstance().getPlayer(args[1]).sendMessage(new TextComponent(Main.prefix + "§cDu wurdest aus der Party von §5" 
						+ PartyManager.playerparty.get(p).getPartyOwner().getName() + " §cgekickt."));
						Main.getPartyManager().removePlayerFromParty(ProxyServer.getInstance().getPlayer(args[1]), PartyManager.playerparty.get(p));
					} else {
						p.sendMessage(new TextComponent(Main.prefix + "§cDer Spieler §5" + args[1] + " §cist nicht in deiner Party."));
					}
					
				}
			}
			
			if(args[0].equalsIgnoreCase("promote")) {
				if(PartyManager.playerparty.containsKey(p) && PartyManager.playerparty.get(p).getPartyOwner() != p) {
					p.sendMessage(new TextComponent(Main.prefix + "§cDu bist nicht der PartyOwner deiner Party. Nur der PartyOwner kann Spieler aus seiner Party promoten."));
				} else {
					if(ProxyServer.getInstance().getPlayer(args[1]) != null && PartyManager.playerparty.containsKey(ProxyServer.getInstance().getPlayer(args[1]))) {
						Main.getPartyManager().setNewPartyOwner(ProxyServer.getInstance().getPlayer(args[1]), PartyManager.playerparty.get(p));
					} else {
						p.sendMessage(new TextComponent(Main.prefix + "§cDer Spieler §5" + args[1] + " §cist nicht in deiner Party."));
					}
					
				}
			}
			
		}
		
	}

}
