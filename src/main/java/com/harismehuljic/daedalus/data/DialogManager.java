package com.harismehuljic.daedalus.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.packet.c2s.common.CustomClickActionC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.Consumer;

public class DialogManager {
    private static final DialogCallbackRegistry CALLBACK_REGISTRY = new DialogCallbackRegistry();

    public static void executePlayerAction(CustomClickActionC2SPacket packet, ServerPlayerEntity spe) {
        MinecraftServer server = spe.getServer();
        UUID playerID = spe.getUuid();

        assert server != null;
        if (!server.isOnThread()) {
            server.execute(() -> executePlayerAction(packet, spe));
            return;
        }

        Consumer<NbtCompound> callback = CALLBACK_REGISTRY.get(packet.id(), playerID);
        if (callback == null) return;

        Optional<NbtElement> playerPayload = packet.payload();
        if (playerPayload.isEmpty() || playerPayload.get().asCompound().isEmpty()) return;
        callback.accept(playerPayload.get().asCompound().get());
    }

    public static void registerCallback(Identifier action, ServerPlayerEntity recipient, Consumer<NbtCompound> callback) {
        CALLBACK_REGISTRY.register(action, recipient.getUuid(), callback);
    }
}
