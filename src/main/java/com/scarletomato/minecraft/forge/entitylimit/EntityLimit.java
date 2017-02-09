package com.scarletomato.minecraft.forge.entitylimit;

import java.util.Map.Entry;

import jline.internal.Log;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
    public Configuration config;
    
    @EventHandler
    public void init(FMLPreInitializationEvent event)
    {
    	config = new Configuration(event.getSuggestedConfigurationFile());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	new EventLogger().register();
    	new SpawnEntityListener().register();
        // some example code
        System.out.println("DIRT BLOCK >> "+Blocks.DIRT.getUnlocalizedName());
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
