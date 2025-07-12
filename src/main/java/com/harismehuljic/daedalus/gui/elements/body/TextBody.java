package com.harismehuljic.daedalus.gui.elements.body;

import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.dialog.body.DialogBody;
import net.minecraft.dialog.body.PlainMessageDialogBody;
import net.minecraft.text.Text;

/**
 * Represents a body element that contains text, which can be displayed in a dialog.
 */
public class TextBody implements BodyElement {
    private final Text text;
    private final int width;

    /**
     * Constructs a TextBody with the specified text and the default width.
     * <p>
     * This constructor creates a TextBody with the given text, using the default width of 250 pixels, as defined
     * by {@link PlainMessageDialogBody#DEFAULT_WIDTH}. If the width needs to be customized, use
     * {@link #TextBody(StylableText, int)} instead.
     * </p>
     *
     * @param text The text to be displayed in the dialog body.
     */
    public TextBody(StylableText text) {
        this.text = text.getText();
        this.width = PlainMessageDialogBody.DEFAULT_WIDTH;
    }

    /**
     * Constructs a TextBody with the specified text and width.
     *
     * @param text  The text to be displayed in the dialog body.
     * @param width The width of the text display in pixels. Must be a positive integer greater than 0.
     * @throws IllegalArgumentException If the width is not a positive integer greater than 0.
     */
    public TextBody(StylableText text, int width) throws IllegalArgumentException {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be a non-negative integer greater than 0.");
        }

        this.text = text.getText();
        this.width = width;
    }

    @Override
    public DialogBody getBody() {
        return new PlainMessageDialogBody(this.text, this.width);
    }
}
