package net.erv123.shadertoymc;

import net.erv123.shadertoymc.commands.ServerAdminCommands;
import net.erv123.shadertoymc.commands.ServerCommands;
import net.erv123.shadertoymc.networking.RegisterPackets;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShadertoyMC implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Shadertoy");
    public static final String VERSION = "1.0.0";
    public static MinecraftServer SERVER;

    @Override
    public void onInitialize() {
        RegisterPackets.registerC2SPackets();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess,environment) -> {
            ServerCommands.register(dispatcher);
            ServerAdminCommands.register(dispatcher);
        });
    }
}

