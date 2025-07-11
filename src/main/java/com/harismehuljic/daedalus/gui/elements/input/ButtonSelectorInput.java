package com.harismehuljic.daedalus.gui.elements.input;

import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.dialog.input.InputControl;
import net.minecraft.dialog.input.SingleOptionInputControl;

import java.util.ArrayList;
import java.util.Optional;

public class ButtonSelectorInput extends InputElement {
    private final StylableText label;
    private final ArrayList<SingleOptionInputControl.Entry> options = new ArrayList<>();

    private boolean labelVisible = true;

    public ButtonSelectorInput(String key, int width, StylableText label) {
        super(key, width);
        this.label = label;
    }

    public ButtonSelectorInput(String key, int width) {
        this(key, width, new StylableText());
        this.labelVisible = false;
    }

    public ButtonSelectorInput addOption(String id, StylableText displayText) {
        boolean initial = this.options.isEmpty();
        this.options.add(new SingleOptionInputControl.Entry(
                id,
                Optional.ofNullable(displayText.getText()),
                initial
        ));

        return this;
    }

    @Override
    protected InputControl getInputControl() throws IllegalStateException {
        if (this.options.isEmpty()) {
            throw new IllegalStateException("ButtonSelectorInput must have at least one option.");
        }

        return new SingleOptionInputControl(this.width, this.options, this.label.getText(), this.labelVisible);
    }
}
