package com.harismehuljic.daedalus.gui.elements.input;

import net.minecraft.dialog.input.InputControl;
import net.minecraft.dialog.type.DialogInput;

public abstract class InputElement {
    protected final String key;
    protected int width = 100;

    protected InputElement(String key, int width) {
        this.key = key;
        this.width = width;
    }

    protected abstract InputControl getInputControl();

    public DialogInput getInput() {
        return new DialogInput(this.key, this.getInputControl());
    };
}
