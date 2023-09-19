package com.github.simuxmc.rizinglava;

import com.esotericsoftware.kryo.Kryo;
import com.github.simuxmc.rizinglava.commands.Command;
import com.github.simuxmc.rizinglava.commands.CommandName;
import com.github.simuxmc.rizinglava.commands.forcefield.ForceFieldHandler;
import com.github.simuxmc.rizinglava.serialization.DataSerializer;
import com.github.simuxmc.rizinglava.serialization.kryo.KryoSerializer;
import com.github.simuxmc.rizinglava.util.FileUtils;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;

public final class RizingLava extends JavaPlugin {

    private DataSerializer dataSerializer;
    private static RizingLava instance;

    private ForceFieldHandler forceFieldHandler;

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
        forceFieldHandler = new ForceFieldHandler(Bukkit.getPluginManager(), this);
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
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

}
