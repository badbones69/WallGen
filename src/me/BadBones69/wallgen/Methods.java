package me.BadBones69.wallgen;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Methods {
	
	public static String color(String msg){
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		return msg;
	}
	
	public static String getPrefix(String msg){
		return color(Main.settings.getConfig().getString("Settings.Prefix") + msg);
	}
	
	public static boolean isOnline(String name){
		for(Player player : Bukkit.getServer().getOnlinePlayers()){
			if(player.getName().equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isInt(String s) {
	    try {
	        Integer.parseInt(s);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public static Player getPlayer(String name){
		return Bukkit.getServer().getPlayer(name);
	}
	
	public static void removeItem(ItemStack item, Player player){
		if(item.getAmount() <= 1){
			player.getInventory().removeItem(item);
		}
		if(item.getAmount() > 1){
			ItemStack i = item;
			i.setAmount(item.getAmount() - 1);
		}
	}
	
}