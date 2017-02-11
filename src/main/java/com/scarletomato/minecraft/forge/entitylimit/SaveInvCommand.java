package com.scarletomato.minecraft.forge.entitylimit;

import java.io.File;
import java.util.LinkedList;
import java.util.Random;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.config.Property.Type;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class SaveInvCommand extends ACommand {

	@SubscribeEvent
	public void onNewPlayer(PlayerLoggedInEvent event) {
		// check the main world folder for the players dat file
		if(!new File(mod.server.worldServerForDimension(0).getSaveHandler().getWorldDirectory(), "/playerdata/" + event.player.getUniqueID() + ".dat").exists()) {
			// if it's not there yet add the new inventory stuff
			execLoad(event.player);
		}
	}

	public SaveInvCommand(EntityLimit mod) {
		super(mod);
	}

	@Override
	public String getCommandName() {
		return "si";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length > 0){
			if("save".equalsIgnoreCase(args[0]) && sender instanceof EntityPlayer) {
				execSave((EntityPlayer)sender);
			} else if("load".equalsIgnoreCase(args[0]) && sender instanceof EntityPlayer) {
				execLoad((EntityPlayer)sender);
			} else if("reload".equals(args[0])) {
				mod.config.load();
			}
		}
	}

	private void execSave(EntityPlayer player) {
		LinkedList<String> inventoryStrings = new LinkedList<>();
		for(ItemStack is : player.inventory.mainInventory) {
			if(null != is) {
				inventoryStrings.add(String.valueOf(is.writeToNBT(new NBTTagCompound())));
			}
		}
		mod.config.getCategory("inv").put("firstInventory", new Property("firstInventory", inventoryStrings.toArray(new String[]{}), Type.STRING));
		mod.config.save();
	}

	private void execLoad(EntityPlayer player) {
		String[] invStrs = mod.config.getStringList("firstInventory", "inv", new String[]{}, "someComment");
		for(String s : invStrs) {
			s = parseRandom(s);
			try {
				player.inventory.addItemStackToInventory(ItemStack.loadItemStackFromNBT(JsonToNBT.getTagFromJson(s)));
			} catch (NBTException e) {
				player.addChatMessage(new TextComponentString("Couldn't finish loading the inventory, see server log for details"));
				throw new RuntimeException(e);
			}
		}
	}

	private static String parseRandom(String s) {
		while (s.indexOf("%isr%") > 0) {
			int start = s.indexOf("%isr%");
			int end = s.indexOf("%ise%");
			String[] ar = s.substring(start, end).split("\\|");
			s = s.substring(0, start) + ar[new Random().nextInt(ar.length)] + s.substring(end + 5);
		}
		return s;
	}

}
