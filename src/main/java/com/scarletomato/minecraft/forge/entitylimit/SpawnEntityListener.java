package com.scarletomato.minecraft.forge.entitylimit;

import jline.internal.Log;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class SpawnEntityListener {
	/**
	 * How many old averages to remember
	 */
	private static final int WEIGHT = 10;
	/**
	 * How many ticks to count before recalculating the TPS
	 */
	private static final int COUNT = 50;
	
	long startTime = System.currentTimeMillis();
	int ticks = 0;
	double avgTps;
	int avgWeight = 0;
	
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
	
	@SubscribeEvent
	public void onEvent(ServerTickEvent event) {
		ticks++;
		if(ticks >= COUNT) {
			//running avg = (nowCalc + oldAvg*weight)/(weight+1)
			avgTps = ((ticks*1000.0)/(System.currentTimeMillis() - startTime) + avgTps*avgWeight)/(avgWeight+1);
			Log.info("current avg = " + avgTps);
			
			// restart count
			startTime = System.currentTimeMillis();
			ticks = 0;
			if(0==avgWeight) {
				avgWeight = WEIGHT;
			}
		}
	}
	
	public static void main(String[] args) {
		
		int simulatedTPS = 40;
		SpawnEntityListener s = new SpawnEntityListener();
		while(true) {
			s.onEvent((ServerTickEvent)null);
			try {
				// 1000/TPS = milliseconds per tick
				Thread.sleep(1000/simulatedTPS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
