package com.harismehuljic.daedalus.data;

import net.minecraft.util.Identifier;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.nbt.NbtCompound;

/**
 * Registry for dialog callbacks, allowing actions to be registered and retrieved based on action identifiers and player UUIDs.
 * <p>
 * This class provides methods to register callbacks for specific actions and retrieve them when needed.
 * </p>
 */
public class DialogCallbackRegistry {
    private final HashMap<Identifier, HashMap<UUID, Consumer<NbtCompound>>> callbacks = new HashMap<>();

    /**
     * Registers a callback for a specific action and player.
     * <p>
     * This method allows registration of a callback that will be executed when the specified
     * action is triggered by the player.
     * </p>
     *
     * @param action    The identifier for the action to register the callback for.
     * @param playerId  The UUID of the player who will receive the callback.
     * @param callback  The callback function to be executed when the action is triggered.
     */
    public void register(Identifier action, UUID playerId, Consumer<NbtCompound> callback) {
        callbacks.computeIfAbsent(action, k -> new HashMap<>())
                 .put(playerId, callback);
    }

    /**
     * Retrieves the callback for a specific action and player.
     * <p>
     * This method returns the callback function associated with the specified action and player UUID,
     * or null if no such callback exists.
     * </p>
     *
     * @param action   The identifier for the action to retrieve the callback for.
     * @param playerId The UUID of the player whose callback is being requested.
     * @return The callback function, or null if not found.
     */
    public Consumer<NbtCompound> get(Identifier action, UUID playerId) {
        HashMap<UUID, Consumer<NbtCompound>> playerMap = callbacks.get(action);
        return playerMap != null ? playerMap.get(playerId) : null;
    }
}
