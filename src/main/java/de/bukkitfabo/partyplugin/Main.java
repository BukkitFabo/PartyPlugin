package de.bukkitfabo.partyplugin;


import java.io.File;
import java.io.IOException;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import de.bukkitfabo.partyplugin.commands.PartyCommand;
import de.bukkitfabo.partyplugin.listener.PlayerDisconnectListener;
import de.bukkitfabo.partyplugin.listener.PlayerLoginListener;
import de.bukkitfabo.partyplugin.listener.ServerSwitchListner;
import de.bukkitfabo.partyplugin.party.PartyManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin {

	public static String prefix = "§7[§5§lParty§r§7] ";
	
	public static Plugin plugin;
	public static Plugin getPlugin() {
		return plugin;
	}
	
	private Configuration mysql;
	private File mysqlfile;
	
	private DBI dbi;
	private static Handle sql;
	public static Handle getSQL() {
		return sql;
	}
	
	private static PartyManager partyManager;
	public static PartyManager getPartyManager() {
		return partyManager;
	}
	
	
	@Override
	public void onEnable() {
		plugin = this;
		
		getProxy().getPluginManager().registerCommand(this, new PartyCommand());
		getProxy().getPluginManager().registerListener(this, new ServerSwitchListner());
		getProxy().getPluginManager().registerListener(this, new PlayerDisconnectListener());
		getProxy().getPluginManager().registerListener(this, new PlayerLoginListener());
		
		try {
			if(!getDataFolder().exists()) {
				getDataFolder().mkdir();
			}
			mysqlfile = new File("./plugins/PartyPlugin", "MySQL.yml");
			if(!mysqlfile.exists()) {
				mysqlfile.createNewFile();
			}
			mysql = ConfigurationProvider.getProvider(YamlConfiguration.class).load(mysqlfile);
			mysql.set("Host", "HostIP");
			mysql.set("Database", "Databasename");
			mysql.set("User", "Username");
			mysql.set("Password", "Userpassword");
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(mysql, mysqlfile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		dbi = new DBI("jdbc:mysql://" + mysql.getString("MySQL_Host") + "/" + mysql.getString("MySQL_Database")
		+ "?user=" + mysql.getString("MySQL_User") + "&password=" + mysql.getString("MySQL_Password")
		+ "&autoReconnect=true");
		sql = dbi.open();
		sql.execute("CREATE TABLE IF NOT EXISTS `PartyPlugin` (`PlayerUUID` VARCHAR(64) NOT NULL, `PartyToggle` TINYINT(1) NOT NULL DEFAULT 1)");
		
	}
	
	@Override
	public void onDisable() {
		sql.close();
		plugin = null;
	}
	
}
