package com.scarletomato.minecraft.forge.entitylimit;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import jline.internal.Log;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry.EntityRegistration;

@Mod(modid = EntityLimit.MODID
   , name = "EntityLimit"
   , version = EntityLimit.VERSION
   , acceptableRemoteVersions="*")
public class EntityLimit
{
    public static final String MODID = "entitylimit";
    public static final String VERSION = "1.0";
    Configuration config;
    public double avgTps;
	
	public List<World> loadedWorlds = new LinkedList<>();
	public int entityLimit = 100;
	MinecraftServer server;
	SaveInvCommand sic = new SaveInvCommand(this);
    
    @EventHandler
    public void init(FMLPreInitializationEvent event)
    {
    	config = new Configuration(event.getSuggestedConfigurationFile());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	new EventLogger().register();
    	registerListener(sic);
    	new SpawnEntityListener(this).register();
        // some example code
        System.out.println("DIRT BLOCK >> "+Blocks.DIRT.getUnlocalizedName());
    }
    
    @EventHandler
    public void init(FMLServerStartingEvent event)
    {
    	server = event.getServer();
    	event.registerServerCommand(new TpsCommand(this));
    	event.registerServerCommand(new EntDumpCommand(this));
    	event.registerServerCommand(new LimitCommand(this));
    	event.registerServerCommand(sic);
    }
    
    public void entityDump() {
		try (FileWriter out = new FileWriter(System.currentTimeMillis() + "-entityDump.txt")) {
	    	for(World w : loadedWorlds){
	    		out.write(w.getWorldInfo().getWorldName() + "\r\n");
				for(Entity e : w.loadedEntityList) {
					if(e instanceof EntityItem) {
						out.write(String.valueOf(((EntityItem)e).writeToNBT(new NBTTagCompound())) + "\r\n");
					} else {
						out.write(String.valueOf(e.getClass()) + "\r\n");
					}
				}
	    	}
		} catch (IOException e) {
			System.out.println("Couldn't dump entity list");
			e.printStackTrace();
		}
    }

	public void registerListener(Object listener) {
    	//Most events get posted to this bus
    	MinecraftForge.EVENT_BUS.register(listener);
    	//Most world generation events happen here, such as Populate, Decorate, etc., with the strange exception that Pre and Post events are on the regular EVENT_BUS
    	MinecraftForge.TERRAIN_GEN_BUS.register(listener);
    	//Ore generation, obviously
    	MinecraftForge.ORE_GEN_BUS.register(listener);
    	//FML Events: these become very important in 1.7.2, as this is where TickEvents and KeyInputEvents are posted, with TickHandler and KeyHandler no longer existing.
    	FMLCommonHandler.instance().bus().register(listener);
	}

//    @EventHandler
//    public void init(FMLPostInitializationEvent event)
//    {
//    	String rnt = "\r\n\t";
//    	StringBuilder sb = new StringBuilder("registered entities:").append(rnt);
//    	for(Entry<String, Class<? extends Entity>> e : EntityList.NAME_TO_CLASS.entrySet()){
//    		sb.append(e.getKey())
//    		.append(" = ")
//    		.append(e.getValue())
//    		.append(rnt);
//    	}
//
//		Log.info(sb.toString());
//    }
}
