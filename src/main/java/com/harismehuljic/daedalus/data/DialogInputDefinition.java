package com.harismehuljic.daedalus.data;

import com.harismehuljic.daedalus.gui.elements.input.InputElement;
import net.minecraft.nbt.NbtCompound;

import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class DialogInputDefinition {
    private final ArrayList<InputElement> inputs = new ArrayList<>();

    protected DialogInputDefinition() {
        this.defineInputs();
    }

    protected abstract void defineInputs();

    public ArrayList<InputElement> getInputElements() {
        return inputs;
    }

    public void unpackNbt(NbtCompound nbt) {
        for (String key : nbt.getKeys()) {
            try {
                Field field = this.getClass().getDeclaredField(key);
                field.setAccessible(true);
                Object value = nbt.get(key);
                if (value != null) {
                    field.set(this, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
