package com.harismehuljic.daedalus.gui.elements.input;

import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.dialog.input.InputControl;
import net.minecraft.dialog.input.TextInputControl;
import net.minecraft.dialog.input.TextInputControl.Multiline;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

import java.util.Optional;

/**
 * Represents a text input element in a dialog, which allows the user to enter text with optional multiline support.
 * <p>
 * This class is used to create a text input field with a label, placeholder text, maximum length, and optional multiline
 * input, which can be displayed in a dialog for user interaction.
 * </p>
 */
public class TextInput extends InputElement {
    private final Text label;
    private String placeholder = "";

    private boolean labelVisible = true;
    private int maxLength = 100;
    private Optional<Multiline> multiline = Optional.empty();

    /**
     * Constructs a TextInput with the specified key, width, label, and no placeholder or multiline support by default.
     *
     * @param key The unique key for this input element, used to identify it in the dialog.
     * @param width The width of the input element as displayed in the dialog.
     * @param label The label for the input element, which will be displayed to the user.
     *
     * @apiNote The {@code key} will be used to access the value of this input element in the {@link NbtCompound} that
     * is passed to the dialog's callback function. It should be a unique identifier for this input element. If
     * multiline support is needed, it can be set using {@link TextInput#setMultiline(int, int)}. If a placeholder is needed,
     * it can be set using {@link TextInput#setPlaceholder(String)}.
     */
    public TextInput(String key, int width, StylableText label) {
        super(key, width);
        this.label = label.getText();
    }

    /**
     * Constructs a TextInput with the specified key and width. This TextInput will have no label
     *
     * @param key The unique key for this input element, used to identify it in the dialog.
     * @param width The width of the input element as displayed in the dialog.
     *
     * @apiNote The {@code key} will be used to access the value of this input element in the {@link NbtCompound} that
     * is passed to the dialog's callback function. It should be a unique identifier for this input element. If
     * multiline support is needed, it can be set using {@link TextInput#setMultiline(int, int)}. If a placeholder is needed,
     * it can be set using {@link TextInput#setPlaceholder(String)}.
     */
    public TextInput(String key, int width) {
        this(key, width, new StylableText());
        this.labelVisible = false;
    }

    /**
     * Sets the value of the placeholder text for this TextInput.
     *
     * @param placeholder The placeholder text to be displayed when the input field is empty.
     * @return The current instance of TextInput for method chaining.
     */
    public TextInput setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    /**
     * Sets the maximum length of the text that can be entered in this TextInput.
     *
     * @param maxLength The maximum length of the text input. Must be a positive integer.
     * @return The current instance of TextInput for method chaining.
     * @throws IllegalArgumentException If the provided maxLength is not a positive integer.
     */
    public TextInput setMaxLength(int maxLength) throws IllegalArgumentException {
        if (maxLength <= 0) {
            throw new IllegalArgumentException("Max length must be a positive integer.");
        }
        this.maxLength = maxLength;
        return this;
    }

    /**
     * Sets the multiline properties for this TextInput.
     *
     * @param maxLines The maximum number of lines allowed in the input field.
     * @param maxHeight The maximum height of the input field in pixels.
     * @return The current instance of TextInput for method chaining.
     */
    public TextInput setMultiline(int maxLines, int maxHeight) {
        this.multiline = Optional.of(new TextInputControl.Multiline(Optional.of(maxLines), Optional.of(maxHeight)));
        return this;
    }

    @Override
    public InputControl getInputControl() {
        return new TextInputControl(this.width, this.label, this.labelVisible, this.placeholder, this.maxLength, this.multiline);
    }
}
