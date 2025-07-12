package com.harismehuljic.daedalus.testmod;

import com.harismehuljic.daedalus.testmod.commands.TestDialogCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMod implements ModInitializer {
    public static final String MOD_ID = "testmod";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion().getFriendlyString();

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(TestDialogCommand::register);
    }
}
