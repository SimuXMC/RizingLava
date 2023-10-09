package com.github.simuxmc.rizinglava.util.items;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

public class SkullItemBuilder extends ItemBuilder {

	/**
	 * Creates a player head {@link ItemBuilder} from the given skin url.
	 *
	 * @param skinURL the url of the skin for the player head of this {@link ItemBuilder}
	 */
	@SuppressWarnings("CallToPrintStackTrace")
	public SkullItemBuilder(@NotNull String skinURL) {
		super(Material.PLAYER_HEAD);
		Objects.requireNonNull(skinURL);
		try {
			URL url = new URL(skinURL);
			PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
			PlayerTextures textures = profile.getTextures();
			textures.setSkin(url);
			profile.setTextures(textures);
			((SkullMeta) itemMeta).setPlayerProfile(profile);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a player head {@link ItemBuilder} of the specified player.
	 *
	 * @param player the player who will be represented on the player head of this {@link ItemBuilder}
	 */
	public SkullItemBuilder(@NotNull OfflinePlayer player) {
		super(Material.PLAYER_HEAD);
		Objects.requireNonNull(player);
		((SkullMeta) itemMeta).setPlayerProfile(player.getPlayerProfile());
	}

}
