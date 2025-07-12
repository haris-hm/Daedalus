package com.harismehuljic.daedalus.data;

import net.minecraft.util.Identifier;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.nbt.NbtCompound;

public class DialogCallbackRegistry {
    private final HashMap<Identifier, HashMap<UUID, Consumer<NbtCompound>>> callbacks = new HashMap<>();

    public void register(Identifier action, UUID playerId, Consumer<NbtCompound> callback) {
        callbacks.computeIfAbsent(action, k -> new HashMap<>())
                 .put(playerId, callback);
    }

    public Consumer<NbtCompound> get(Identifier action, UUID playerId) {
        HashMap<UUID, Consumer<NbtCompound>> playerMap = callbacks.get(action);
        return playerMap != null ? playerMap.get(playerId) : null;
    }
}
