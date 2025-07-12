package com.harismehuljic.daedalus.gui.elements.input;

import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.dialog.input.BooleanInputControl;
import net.minecraft.dialog.input.InputControl;
import net.minecraft.nbt.NbtCompound;

/**
 * Represents a boolean input element in a dialog, which allows the user to select between two options: true or false.
 * <p>
 * This class is used to create a boolean input field with a label and an initial value, which can be displayed
 * in a dialog for user interaction.
 * </p>
 */
public class BooleanInput extends InputElement {
    private final StylableText label;
    private final boolean initialValue;

    /**
     * Constructs a BooleanInput with the specified key, label, and initial value.
     *
     * @param key The unique key for this input element, used to identify it in the dialog.
     * @param label The label for the input element, which will be displayed to the user.
     * @param initialValue The initial value of the boolean input, either true or false.
     *
     * @apiNote The {@code key} will be used to access the value of this input element in the {@link NbtCompound} that
     * is passed to the dialog's callback function. It should be a unique identifier for this input element.
     */
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
