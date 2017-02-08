package com.scarletomato.minecraft.forge.entitylimit;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = EntityLimit.MODID, version = EntityLimit.VERSION
, acceptableRemoteVersions="*")
public class EntityLimit
{
    public static final String MODID = "EntityLimit";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	new EventLogger().register();
    	new SpawnEntityListener().register();
        // some example code
        System.out.println("DIRT BLOCK >> "+Blocks.DIRT.getUnlocalizedName());
    }
}
