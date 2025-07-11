package com.harismehuljic.daedalus.gui;

import com.harismehuljic.daedalus.Daedalus;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.packet.c2s.common.CustomClickActionC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.Consumer;

public class DialogManager {
    private static final HashMap<Identifier, HashMap<UUID, Consumer<NbtCompound>>> CALLBACKS = new HashMap<>();

    public static void executePlayerAction(CustomClickActionC2SPacket packet, ServerPlayerEntity spe) {
        MinecraftServer server = spe.getServer();

        assert server != null;
        if (!server.isOnThread()) {
            server.execute(() -> executePlayerAction(packet, spe));
            return;
        }

        HashMap<UUID, Consumer<NbtCompound>> playerCallbacks = CALLBACKS.get(packet.id());
        if (playerCallbacks == null) return;

        UUID playerId = spe.getUuid();
        Consumer<NbtCompound> callback = playerCallbacks.get(playerId);
        if (callback == null) return;

        Optional<NbtElement> playerPayload = packet.payload();
        if (playerPayload.isEmpty() || playerPayload.get().asCompound().isEmpty()) return;
        callback.accept(playerPayload.get().asCompound().get());
        Daedalus.LOGGER.info("CALLBACKS: {}", CALLBACKS);
        playerCallbacks.remove(playerId);
    }

    public static void registerCallback(ServerPlayerEntity recipient, Identifier action, Consumer<NbtCompound> callback) {
        CALLBACKS.computeIfAbsent(action, (i) -> new HashMap<>())
                 .put(recipient.getUuid(), callback);
    }
}
