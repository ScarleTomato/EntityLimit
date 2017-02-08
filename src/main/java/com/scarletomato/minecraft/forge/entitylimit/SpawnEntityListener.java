package com.scarletomato.minecraft.forge.entitylimit;

import jline.internal.Log;
import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpawnEntityListener {
	private static final String RNT = "\r\n\t";

	@SubscribeEvent
	public void onEvent(EntityConstructing event) {
		Entity e = event.getEntity();
		StringBuilder sb= new StringBuilder("Spawning entity").append(RNT)
		.append("Entity = ").append(e.toString()).append(RNT)
		.append("Name = ").append(e.getName()).append(RNT)
		.append("DisplayName = ").append(e.getDisplayName()).append(RNT)
		.append("CachedUniqueIdString = ").append(e.getCachedUniqueIdString()).append(RNT)
		.append("EntityData = ").append(e.getEntityData()).append(RNT)
		.append("EntityId = ").append(e.getEntityId()).append(RNT)
		.append("PersistentID = ").append(e.getPersistentID()).append(RNT)
		.append("CustomNameTag = ").append(e.getCustomNameTag()).append(RNT);
		
//		appendDataManager(sb, e.getDataManager());
		
		Log.info(sb.toString());
	}
	
	void appendDataManager(StringBuilder sb, EntityDataManager edm) {
		for(DataEntry<?> de : edm.getAll()){
			sb.append(de.getKey()).append(" = ").append(de.getValue()).append(RNT);
		}
	}

	public void register() {
    	//Most events get posted to this bus
    	MinecraftForge.EVENT_BUS.register(this);
    	//Most world generation events happen here, such as Populate, Decorate, etc., with the strange exception that Pre and Post events are on the regular EVENT_BUS
    	MinecraftForge.TERRAIN_GEN_BUS.register(this);
    	//Ore generation, obviously
    	MinecraftForge.ORE_GEN_BUS.register(this);
	}
}
