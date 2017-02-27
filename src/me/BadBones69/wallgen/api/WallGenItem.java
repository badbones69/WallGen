package me.BadBones69.wallgen.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.BadBones69.wallgen.Main;
import me.BadBones69.wallgen.Methods;

public class WallGenItem {
	
	private Material wallType;
	private Material itemType;
	private String name;
	private List<String> lore;
	private short wallMetaData;
	private short itemMetaData;
	private Integer amount;
	
	/**
	 * Create a new wall generator item.
	 */
	public WallGenItem(){
		FileConfiguration config = Main.settings.getConfig();
		String type = config.getString("Settings.WallGen-Item-Options.Item");
		short ty = 0;
		if(type.contains(":")){
			String[] b = type.split(":");
			type = b[0];
			ty = Short.parseShort(b[1]);
		}
		wallType = Material.COBBLESTONE;
		itemType = Material.matchMaterial(type);
		name = config.getString("Settings.WallGen-Item-Options.Name");
		lore = config.getStringList("Settings.WallGen-Item-Options.Lore");
		wallMetaData = 0;
		itemMetaData = ty;
		amount = 1;
	}
	
	/**
	 * Set the name of the item.
	 * @param name The item's new name.
	 */
	public WallGenItem setName(String name){
		this.name = name;
		return this;
	}
	
	/**
	 * Set the lore of the item.
	 * @param lore The item's new lore.
	 */
	public WallGenItem setLore(List<String> lore){
		this.lore = lore;
		return this;
	}
	
	/**
	 * Set the amount of the item.
	 * @param amount The amount you want.
	 */
	public WallGenItem setAmount(int amount){
		this.amount = amount;
		return this;
	}
	
	/**
	 * Set the type of wall it will generate.
	 * @param material Type of wall you want.
	 */
	public WallGenItem setWallType(Material material){
		wallType = material;
		return this;
	}
	
	/**
	 * Set the item's type.
	 * @param material Type of the item.
	 */
	public WallGenItem setItemType(Material material){
		itemType = material;
		return this;
	}
	
	/**
	 * Set the wall generator's meta data.
	 * @param metaData MetaData number for the wall block type.
	 */
	public WallGenItem setWallMetaData(short metaData){
		this.wallMetaData = metaData;
		return this;
	}
	
	/**
	 * Set the items meta data.
	 * @param metaData MetaData number for the item block type.
	 */
	public WallGenItem setItemMetaData(short metaData){
		this.itemMetaData = metaData;
		return this;
	}
	
	public WallGenItem clone(){
		return new WallGenItem()
				.setAmount(amount)
				.setItemMetaData(itemMetaData)
				.setItemType(itemType)
				.setLore(lore)
				.setName(name)
				.setWallMetaData(wallMetaData)
				.setWallType(wallType);
	}
	
	/**
	 * Turn the resault into an ItemStack.
	 * @return The finished product as an ItemStack.
	 */
	public ItemStack build(){
		ItemStack item = new ItemStack(itemType, amount, itemMetaData);
		ItemMeta m = item.getItemMeta();
		m.setDisplayName(Methods.color(name
				.replaceAll("%Block%", wallType.name() + ":" + wallMetaData).replaceAll("%block%", wallType.name() + ":" + wallMetaData)));
		ArrayList<String> l = new ArrayList<String>();
		for(String L : lore){
			l.add(Methods.color(L));
		}
		m.setLore(l);
		item.setItemMeta(m);
		return item;
	}
	
}