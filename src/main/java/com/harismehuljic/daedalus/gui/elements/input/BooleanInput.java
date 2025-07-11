package com.harismehuljic.daedalus.gui.elements.input;

import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.dialog.input.BooleanInputControl;
import net.minecraft.dialog.input.InputControl;

public class BooleanInput extends InputElement {
    private final StylableText label;
    private final boolean initialValue;

    public BooleanInput(String key, StylableText label, boolean initialValue) {
        super(key, 1);
        this.label = label;
        this.initialValue = initialValue;
    }

    @Override
    protected InputControl getInputControl() {
        return new BooleanInputControl(this.label.getText(), this.initialValue, "true", "false");
    }
}
