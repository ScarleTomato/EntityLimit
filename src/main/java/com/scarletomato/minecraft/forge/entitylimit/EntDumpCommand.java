package com.scarletomato.minecraft.forge.entitylimit;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class EntDumpCommand implements ICommand {
	private final EntityLimit mod;
	
	public EntDumpCommand(EntityLimit mod) {
		this.mod = mod;
	}

	@Override
	public int compareTo(ICommand o) {
		return o.getCommandName().compareTo(getCommandName());
	}

	@Override
	public String getCommandName() {
		return "dumpEntities";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "dumpEntities";
	}

	@Override
	public List<String> getCommandAliases() {
		return Arrays.asList(new String[]{"de"});
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		mod.entityDump();
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}

}
