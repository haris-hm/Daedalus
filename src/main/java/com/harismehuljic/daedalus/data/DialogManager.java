package com.harismehuljic.daedalus.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.packet.c2s.common.CustomClickActionC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.Consumer;

/**
 * Manages dialog actions and callbacks for player interactions in the game.
 * <p>
 * This class provides methods to execute player actions based on custom click packets,
 * register callbacks for specific actions, and handle the execution of these actions
 * by the server.
 * </p>
 */
public class DialogManager {
    private static final DialogCallbackRegistry CALLBACK_REGISTRY = new DialogCallbackRegistry();

    /**
     * Executes a player action based on the provided packet and player entity.
     * <p>
     * This method checks if the server is on the correct thread and executes the action
     * if a callback is registered for the specified action ID and player UUID.
     * </p>
     *
     * @param packet The custom click action packet containing the action ID and payload.
     * @param spe    The server player entity that triggered the action.
     */
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

    /**
     * Registers a callback for a specific action and player.
     * <p>
     * This method allows you to register a callback that will be executed when the specified
     * action is triggered by the player.
     * </p>
     *
     * @param action    The identifier for the action to register the callback for.
     * @param recipient The server player entity that will receive the callback.
     * @param callback  The callback function to execute when the action is triggered.
     */
    public static void registerCallback(Identifier action, ServerPlayerEntity recipient, Consumer<NbtCompound> callback) {
        CALLBACK_REGISTRY.register(action, recipient.getUuid(), callback);
    }
}
