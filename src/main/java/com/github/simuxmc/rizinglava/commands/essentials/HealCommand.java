package com.github.simuxmc.rizinglava.commands.essentials;

import com.github.simuxmc.rizinglava.commands.Command;
import com.github.simuxmc.rizinglava.commands.CommandName;
import com.github.simuxmc.rizinglava.commands.CommandUtils;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.EntitySelectorArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

@CommandName("heal")
public class HealCommand extends Command {

	public HealCommand(String commandName) {
		super(commandName);
	}

	@Override
	protected void executes(CommandSender sender, CommandArguments args) {
		MiniMessage miniMessage = MiniMessage.miniMessage();
		Collection<Player> targets = CommandUtils.getTargets(miniMessage, sender, args);
		if (targets == null) return;
		for (Player p : targets) {
			AttributeInstance maxHealthAttribute = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			double maxHealth = maxHealthAttribute == null ? 20 : maxHealthAttribute.getValue();
			p.setHealth(maxHealth);
			Component feedback = miniMessage.deserialize(
					"<gold>Essentials> <green>" + p.getName() + " <yellow>has been healed.");
			sender.sendMessage(feedback);
		}
	}

	@Override
	public Argument<?>[] optionalArguments() {
		Argument<Collection> playerArgument =
				new EntitySelectorArgument.ManyPlayers(CommandUtils.PLAYER_ARGUMENT_NAME).instance();
		return new Argument[]{playerArgument};
	}

	@Override
	public String permission() {
		return "rizinglava.heal";
	}

}
