package me.BadBones69.wallgen.api.controlers;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.BadBones69.wallgen.Main;
import me.BadBones69.wallgen.Methods;

public class WallController implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onWallGen(PlayerInteractEvent e){
		if(!e.isCancelled()){
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
				if(e.hasItem()){
					ItemStack item = e.getItem();
					if(isWallGenItem(item)){
						e.setCancelled(true);
						if(e.getBlockFace() != BlockFace.UP){
							ItemStack block = getWallType(item);
							for(Location loc : getWallLocations(e.getClickedBlock().getLocation(), e.getBlockFace())){
								loc.getBlock().setType(block.getType());
				 				loc.getBlock().setData(block.getData().getData());
							}
						}
					}
				}
			}
		}
	}
	
	private ItemStack getWallType(ItemStack item){
		String[] D = Methods.color(Main.settings.getConfig().getString("Settings.WallGen-Item-Options.Name")).toLowerCase().split("%block%");
		String ID = item.getItemMeta().getDisplayName().toLowerCase().replace(D[0], "");
		if(D.length > 1){
			ID = ID.replace(D[1], "");
		}
		Material m = Material.matchMaterial(ID.split(":")[0]);
		Short md = Short.parseShort(ID.split(":")[1]);
		return new ItemStack(m, 1, md);
	}
	
	private Boolean isWallGenItem(ItemStack item){
		Boolean isWallGenItem = false;
		String[] D = Methods.color(Main.settings.getConfig().getString("Settings.WallGen-Item-Options.Name")).toLowerCase().split("%block%");
		if(item.hasItemMeta()){
			if(item.getItemMeta().hasDisplayName()){
				if(item.getItemMeta().getDisplayName().toLowerCase().startsWith(D[0])){
					if(D.length > 1){
						if(item.getItemMeta().getDisplayName().toLowerCase().endsWith(D[1])){
							isWallGenItem = true;
						}
					}else{
						isWallGenItem = true;
					}
				}
			}
		}
		return isWallGenItem;
	}
	
	private ArrayList<Location> getWallLocations(Location block, BlockFace blockFace){
		switch(blockFace){
		case SOUTH:
			block.add(0, 0, 1);
			break;
		case WEST:
			block.add(-1, 0, 0);
			break;
		case EAST:
			block.add(1, 0, 0);
			break;
		case NORTH:
			block.add(0, 0, -1);
			break;
		case UP:
			block.add(0, 1, 0);
			break;
		case DOWN:
			block.add(0, -1, 0);
			break;
		default:
			break;
		}
		ArrayList<Location> locations = new ArrayList<Location>();
		locations.add(block.clone());
		for(; block.subtract(0, 1, 0).getBlock().getType() == Material.AIR;){
			if(block.getY() >= 0){
				locations.add(block.clone());
			}else{
				break;
			}
		}
		return locations;
	}
	
}