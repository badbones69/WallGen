package me.BadBones69.wallgen;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.BadBones69.wallgen.api.WallGenItem;
import me.BadBones69.wallgen.api.controlers.WallController;

public class Main extends JavaPlugin{
	
	public static SettingsManager settings = SettingsManager.getInstance();
	
	@Override
	public void onEnable(){
		settings.setup(this);
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new WallController(), this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLable, String[] args){
		if(commandLable.equalsIgnoreCase("wallgenerator") || commandLable.equalsIgnoreCase("wallgen") ||
				commandLable.equalsIgnoreCase("wg")){
			if(sender instanceof Player){
				if(!((Player)sender).hasPermission("worldgen.access")){
					sender.sendMessage(Methods.getPrefix("&cYou do not have permission to use this command."));
					return true;
				}
			}
			if(args.length <= 0){
				if(sender instanceof Player){
					Player player = (Player) sender;
					WallGenItem wg = new WallGenItem();
					player.getInventory().addItem(wg.build());
				}
			}else{
				if(args[0].equalsIgnoreCase("help")){// /Wg Help
					sender.sendMessage(Methods.color("&7List of all WallGen's commands."));
					sender.sendMessage(Methods.color("&a/Wg &7- Gives you a basic wallgen block.."));
					sender.sendMessage(Methods.color("&a/Wg Help &7- Shows you all the commands."));
					sender.sendMessage(Methods.color("&a/Wg Reload &7- Reloads the config.yml."));
					sender.sendMessage(Methods.color("&a/Wg Give <Block ID> [Amount] [Player] &7- Give a player a wallgen block."));
					sender.sendMessage(Methods.color("&7Permission: &cworldgen.access"));
					return true;
				}
				if(args[0].equalsIgnoreCase("reload")){// /Wg Reload
					settings.reloadConfig();
					settings.setup(this);
					sender.sendMessage(Methods.getPrefix("&7You have just reloaded the config.yml"));
					return true;
				}
				if(args[0].equalsIgnoreCase("give")){// /Wg Give <Block ID> [Amount] [Player]
					if(args.length >= 2){
						String id = args[1];
						short md = 0;
						int amount = 1;
						Player player = null;
						if(id.contains(":")){
							String[] b = id.split(":");
							id = b[0];
							md = Short.parseShort(b[1]);
						}
						if(Material.matchMaterial(id) == null){
							sender.sendMessage(Methods.getPrefix("&cThat is not an item ID in the game."));
							return true;
						}
						if(args.length >= 3){
							if(Methods.isInt(args[2])){
								amount = Integer.parseInt(args[2]);
							}else{
								sender.sendMessage(Methods.getPrefix("&cThat is not a number."));
								return true;
							}
						}
						if(args.length >= 4){
							if(Methods.isOnline(args[3])){
								player = Methods.getPlayer(args[3]);
							}else{
								sender.sendMessage(Methods.getPrefix("&cThat player is not online at this time."));
								return true;
							}
						}else{
							if(!(sender instanceof Player)){
								sender.sendMessage(Methods.getPrefix("&cYou must be a player to use the command like that."));
								return true;
							}else{
								player = (Player) sender;
							}
						}
						player.getInventory().addItem(new WallGenItem()
								.setAmount(amount)
								.setWallMetaData(md)
								.setWallType(Material.matchMaterial(id))
								.build());
						sender.sendMessage(Methods.getPrefix("&7You have given " + player.getName() + " a WallGenerator."));
						return true;
					}
					sender.sendMessage(Methods.getPrefix("&c/WallGen Give <Block ID> [Amount] [Player]"));
					return true;
				}
			}
		}
		return false;
	}
	
}