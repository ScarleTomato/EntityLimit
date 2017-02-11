package com.scarletomato.minecraft.forge.entitylimit;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class LimitCommand extends ACommand {

	public LimitCommand(EntityLimit mod) {
		super(mod);
	}

	@Override
	public String getCommandName() {
		return "limit";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		try {
			mod.entityLimit = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

}
