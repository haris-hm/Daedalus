package com.harismehuljic.daedalus.gui.elements.text;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class StylableText {
    private final MutableText text;

    public StylableText(String contents) {
        this.text = Text.literal(contents);
    }

    public StylableText() {
        this.text = Text.empty();
    }

    public StylableText append(String contents) {
        this.text.append(Text.literal(contents));
        return this;
    }

    public StylableText setColor(int color) {
        this.text.setStyle(this.text.getStyle().withColor(color));
        return this;
    }

    public StylableText setBold(boolean bold) {
        this.text.setStyle(this.text.getStyle().withBold(bold));
        return this;
    }

    public StylableText setItalic(boolean italic) {
        this.text.setStyle(this.text.getStyle().withItalic(italic));
        return this;
    }

    public StylableText setUnderline(boolean underline) {
        this.text.setStyle(this.text.getStyle().withUnderline(underline));
        return this;
    }

    public StylableText setStrikethrough(boolean strikethrough) {
        this.text.setStyle(this.text.getStyle().withStrikethrough(strikethrough));
        return this;
    }

    public StylableText setObfuscated(boolean obfuscated) {
        this.text.setStyle(this.text.getStyle().withObfuscated(obfuscated));
        return this;
    }

    public Text getText() {
        return this.text;
    }
}
