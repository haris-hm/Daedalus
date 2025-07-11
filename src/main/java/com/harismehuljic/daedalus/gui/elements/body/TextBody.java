package com.harismehuljic.daedalus.gui.elements.body;

import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.dialog.body.DialogBody;
import net.minecraft.dialog.body.PlainMessageDialogBody;
import net.minecraft.text.Text;

public class TextBody implements BodyElement {
    private final Text text;
    private final int width;

    public TextBody(StylableText text) {
        this.text = text.getText();
        this.width = PlainMessageDialogBody.DEFAULT_WIDTH;
    }

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
