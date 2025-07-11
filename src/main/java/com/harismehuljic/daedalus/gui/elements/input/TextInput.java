package com.harismehuljic.daedalus.gui.elements.input;

import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.dialog.input.InputControl;
import net.minecraft.dialog.input.TextInputControl;
import net.minecraft.dialog.input.TextInputControl.Multiline;
import net.minecraft.text.Text;

import java.util.Optional;

public class TextInput extends InputElement {
    private final Text label;
    private String placeholder = "";

    private boolean labelVisible = true;
    private int maxLength = 100;
    private Optional<Multiline> multiline = Optional.empty();

    public TextInput(String key, int width, StylableText label) {
        super(key, width);
        this.label = label.getText();
    }

    public TextInput(String key, int width) {
        this(key, width, new StylableText());
        this.labelVisible = false;
    }

    public TextInput setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public TextInput setMaxLength(int maxLength) {
        if (maxLength <= 0) {
            throw new IllegalArgumentException("Max length must be a positive integer.");
        }
        this.maxLength = maxLength;
        return this;
    }

    public TextInput setMultiline(int maxLines, int maxHeight) {
        this.multiline = Optional.of(new TextInputControl.Multiline(Optional.of(maxLines), Optional.of(maxHeight)));
        return this;
    }

    @Override
    public InputControl getInputControl() {
        return new TextInputControl(this.width, this.label, this.labelVisible, this.placeholder, this.maxLength, this.multiline);
    }
}
