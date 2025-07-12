package com.harismehuljic.daedalus.gui.elements.input;

import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.dialog.input.InputControl;
import net.minecraft.dialog.input.SingleOptionInputControl;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Represents a button selector input element in a dialog, which allows the user to cycle through multiple options
 * and select one of them by clicking a button.
 * <p>
 * This class is used to create a button selector input field with a label and multiple options, which can be displayed
 * in a dialog for user interaction.
 * </p>
 */
public class ButtonSelectorInput extends InputElement {
    private final StylableText label;
    private final ArrayList<SingleOptionInputControl.Entry> options = new ArrayList<>();

    private boolean labelVisible = true;

    /**
     * Constructs a ButtonSelectorInput with the specified key, width, and label.
     *
     * @param key The unique key for this input element, used to identify it in the dialog.
     * @param width The width of the input element as displayed in the dialog.
     * @param label The label for the input element, which will be displayed to the user.
     *
     * @apiNote Options should be added through {@link ButtonSelectorInput#addOption(String, StylableText)} after
     * creating an instance of this class. Also, the {@code key} will be used to access the value of this input element
     * in the {@link NbtCompound} that is passed to the dialog's callback function. It should be a unique identifier for
     * this input element.
     */
    public ButtonSelectorInput(String key, int width, StylableText label) {
        super(key, width);
        this.label = label;
    }

    /**
     * Constructs a ButtonSelectorInput with the specified key and width, using a default label.
     *
     * @param key The unique key for this input element, used to identify it in the dialog.
     * @param width The width of the input element as displayed in the dialog.
     *
     * @apiNote Options should be added through {@link ButtonSelectorInput#addOption(String, StylableText)} after
     * creating an instance of this class. Also, the {@code key} will be used to access the value of this input element
     * in the {@link NbtCompound} that is passed to the dialog's callback function. It should be a unique identifier for
     * this input element.
     */
    public ButtonSelectorInput(String key, int width) {
        this(key, width, new StylableText());
        this.labelVisible = false;
    }

    /**
     * Adds an option to the button selector input.
     *
     * @param id The unique identifier for the option.
     * @param displayText The display text for the option, which will be shown to the user.
     * @return The current instance of ButtonSelectorInput, allowing for method chaining.
     *
     * @apiNote The {@code id} should be a unique identifier for this option, and will be the value present in the
     * {@link NbtCompound} when it is passed to the callback. The {@code displayText} will be displayed in the dialog.
     * If no options are added before retrieving the input control, an exception will be thrown.
     */
    public ButtonSelectorInput addOption(String id, StylableText displayText) {
        boolean initial = this.options.isEmpty();
        this.options.add(new SingleOptionInputControl.Entry(
                id,
                Optional.ofNullable(displayText.getText()),
                initial
        ));

        return this;
    }

    /**
     * Returns the {@link InputControl} for this button selector input.
     *
     * @throws IllegalStateException If no options have been added through {@link ButtonSelectorInput#addOption}.
     */
    @Override
    protected InputControl getInputControl() throws IllegalStateException {
        if (this.options.isEmpty()) {
            throw new IllegalStateException("ButtonSelectorInput must have at least one option.");
        }

        return new SingleOptionInputControl(this.width, this.options, this.label.getText(), this.labelVisible);
    }
}
