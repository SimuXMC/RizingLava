package com.github.simuxmc.rizinglava;

import com.esotericsoftware.kryo.Kryo;
import com.github.simuxmc.rizinglava.commands.Command;
import com.github.simuxmc.rizinglava.commands.forcefield.ForceFieldHandler;
import com.github.simuxmc.rizinglava.modules.ChatModule;
import com.github.simuxmc.rizinglava.modules.SpawnModule;
import com.github.simuxmc.rizinglava.modules.SpeedModule;
import com.github.simuxmc.rizinglava.serialization.DataSerializer;
import com.github.simuxmc.rizinglava.serialization.kryo.KryoSerializer;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class RizingLava extends JavaPlugin {

	private DataSerializer dataSerializer;
	private static RizingLava instance;

	private ForceFieldHandler forceFieldHandler;
	private ChatModule chatModule;
	private GlobalPersistentData globalPersistentData;

	@Override
	public void onLoad() {
		CommandAPI.onLoad(new CommandAPIBukkitConfig(this));
		Command.registerAllCommands(getFile(), getLogger());
	}

	@Override
	public void onEnable() {
		instance = this;
		dataSerializer = new KryoSerializer(new Kryo(), this);
		CommandAPI.onEnable();
		PluginManager pluginManager = Bukkit.getPluginManager();
		initGlobalPersistentData();
		pluginManager.registerEvents(new SpawnModule(), this);
		pluginManager.registerEvents(new SpeedModule(), this);
		forceFieldHandler = new ForceFieldHandler(pluginManager, this);
		MiniMessage miniMessage = MiniMessage.miniMessage();
		if (!setupChat(miniMessage)) {
			getLogger().severe("Vault Chat was unable to be setup.");
		}
		pluginManager.registerEvents(chatModule, this);
	}

	@Override
	public void onDisable() {
		CommandAPI.onDisable();
	}

	private boolean setupChat(MiniMessage miniMessage) {
		RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
		if (rsp == null) return false;
		chatModule = new ChatModule(rsp.getProvider(), miniMessage);
		return true;
	}

	private void initGlobalPersistentData() {
		File file = new File(getDataFolder(), "global.rl");
		globalPersistentData = dataSerializer.deserialize(file, GlobalPersistentData.class);
		if (globalPersistentData == null) {
			globalPersistentData = new GlobalPersistentData(Bukkit.getWorlds().get(0).getSpawnLocation());
		}
	}

	public DataSerializer getDataSerializer() {
		return dataSerializer;
	}

	public static RizingLava getInstance() {
		return instance;
	}

	public ForceFieldHandler getForceFieldHandler() {
		return forceFieldHandler;
	}

	public ChatModule getChat() {
		return chatModule;
	}

	public GlobalPersistentData getGlobalPersistentData() {
		return globalPersistentData;
	}

}
