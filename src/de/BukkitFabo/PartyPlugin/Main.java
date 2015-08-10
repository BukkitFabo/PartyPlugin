package de.BukkitFabo.PartyPlugin;

import java.io.File;
import java.io.IOException;

import de.BukkitFabo.Commands.PartyCommand;
import de.BukkitFabo.Data.MySQL;
import de.BukkitFabo.Listener.PlayerDisconnectListener;
import de.BukkitFabo.Listener.PlayerLoginListener;
import de.BukkitFabo.Listener.ServerSwitchListner;
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
	public static MySQL sql;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		getProxy().getPluginManager().registerCommand(this, new PartyCommand());
		getProxy().getPluginManager().registerListener(this, new ServerSwitchListner());
		getProxy().getPluginManager().registerListener(this, new PlayerDisconnectListener());
		getProxy().getPluginManager().registerListener(this, new PlayerLoginListener());
		
		try{
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
		
		sql = new MySQL(mysql.getString("Host"), mysql.getString("Database"), mysql.getString("User"), mysql.getString("Password"));
		try {
			sql.openConnection();
			sql.queryUpdate("CREATE TABLE IF NOT EXISTS `PartyPlugin` (Player VARCHAR(50) NOT NULL, ToggleTP TINYINT(1) NOT NULL DEFAULT 0)");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onDisable() {
		plugin = null;
	}
	
}
