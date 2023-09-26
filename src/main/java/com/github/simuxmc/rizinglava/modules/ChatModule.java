package com.github.simuxmc.rizinglava.modules;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class ChatModule implements Listener {

	private final Chat chat;
	private final MiniMessage miniMessage;

	public ChatModule(@NotNull Chat chat, @NotNull MiniMessage miniMessage) {
		this.chat = chat;
		this.miniMessage = miniMessage;
	}

	@EventHandler
	private void onChat(AsyncChatEvent event) {
		event.renderer(new ChatRenderer() {
			@Override
			public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName,
											 @NotNull Component message, @NotNull Audience viewer) {
				TagResolver prefix = Placeholder.parsed("prefix", chat.getPlayerPrefix(source));
				TagResolver displayName = Placeholder.component("displayname", sourceDisplayName);
				TagResolver suffix = Placeholder.parsed("suffix", chat.getPlayerSuffix(source));
				TagResolver msg = Placeholder.component("message", message);
				return miniMessage.deserialize(
						"<prefix><displayname><reset><suffix><reset> <dark_grey>» <white><message>",
						prefix, displayName, suffix, msg);
			}
		});
	}

	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		TagResolver prefix = Placeholder.parsed("prefix", chat.getPlayerPrefix(player));
		TagResolver displayName = Placeholder.component("displayname", player.displayName());
		Component joinMessage = miniMessage.deserialize("<green>→ <prefix><displayname>", prefix, displayName);
		event.joinMessage(joinMessage);
	}

	@EventHandler
	private void onQuit(PlayerQuitEvent event) {
		/*Player player = event.getPlayer();
		TagResolver prefix = Placeholder.parsed("prefix", chat.getPlayerPrefix(player));
		TagResolver displayName = Placeholder.component("displayname", player.displayName());
		Component quitMessage = miniMessage.deserialize("<red>← <prefix><displayname>", prefix, displayName);*/
		Component quitMessage = Component.empty(); // disabled for player retention
		event.quitMessage(quitMessage);
	}

	public Chat getChat() {
		return chat;
	}

}
