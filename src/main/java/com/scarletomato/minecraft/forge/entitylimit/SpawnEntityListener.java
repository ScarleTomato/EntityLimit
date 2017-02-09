package com.scarletomato.minecraft.forge.entitylimit;

import jline.internal.Log;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpawnEntityListener {
	
	@SubscribeEvent
	public void onEvent(CheckSpawn event) {
//		if(event.isCancelable() && event.getEntity().worldObj.loadedEntityList.size() > 20) {
			event.setResult(Result.DENY);
//		}
	}
	
	@SubscribeEvent
	public void onEvent(EntityInteractSpecific event) {
		EntityPlayer p = event.getEntityPlayer();
		Entity l = event.getTarget();
		p.addChatMessage(new TextComponentString(l.getName()));
		p.addChatMessage(new TextComponentString(l.getClass().toString()));
	}
	
//	@SubscribeEvent
//	public void onEvent(LivingEvent event) {
//		if(event.getEntity() instanceof EntityLiving){
//			Log.info(event.getClass());
//		}
//	}

	public void register() {
    	//Most events get posted to this bus
    	MinecraftForge.EVENT_BUS.register(this);
    	//Most world generation events happen here, such as Populate, Decorate, etc., with the strange exception that Pre and Post events are on the regular EVENT_BUS
    	MinecraftForge.TERRAIN_GEN_BUS.register(this);
    	//Ore generation, obviously
    	MinecraftForge.ORE_GEN_BUS.register(this);
	}
}
