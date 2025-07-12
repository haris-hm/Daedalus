package com.harismehuljic.daedalus.gui.elements.input;

import net.minecraft.dialog.input.InputControl;
import net.minecraft.dialog.type.DialogInput;
import net.minecraft.nbt.NbtCompound;

/**
 * Represents an input element in a dialog, which can be used to gather user input.
 * <p>
 * This abstract class serves as a base for various types of input elements, such as text inputs,
 * boolean inputs, and others. It provides a method to retrieve the input control associated with
 * the element and a method to get the dialog input representation.
 * </p>
 */
public abstract class InputElement {
    protected final String key;
    protected int width;

    /**
     * Constructs an InputElement with the specified key and width.
     *
     * @param key The unique key for this input element, used to identify it in the dialog.
     * @param width The width of the element as displayed in the dialog.
     *
     * @apiNote The {@code key} will be used to access the value of this input element in the {@link NbtCompound} that
     * is passed to the dialog's callback function. It should be a unique identifier for this input element.
     */
    protected InputElement(String key, int width) {
        this.key = key;
        this.width = width;
    }

    /**
     * Retrieves the input control associated with this input element.
     * <p>
     * This method must be implemented by subclasses to provide the specific input control
     * that corresponds to the type of input element (e.g., text input, boolean input).
     * </p>
     *
     * @return The {@link InputControl} for this input element.
     */
    protected abstract InputControl getInputControl();

    /**
     * Retrieves the dialog input representation of this input element.
     * <p>
     * This method creates a {@link DialogInput} that encapsulates the key and the input control,
     * allowing it to be used in a dialog context.
     * </p>
     *
     * @return A {@link DialogInput} representing this input element.
     */
    public DialogInput getInput() {
        return new DialogInput(this.key, this.getInputControl());
    }
}
