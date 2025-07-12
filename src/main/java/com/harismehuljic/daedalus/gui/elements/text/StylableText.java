package com.harismehuljic.daedalus.gui.elements.text;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * A class that allows for creating and styling {@link Text} objects in a more convenient way.
 * <p>
 * Provides several builder style methods for setting the color of text and applying various
 * styles such as bold, italic, underline, strikethrough, and obfuscated.
 * </p>
 */
public class StylableText {
    private final MutableText text;

    /**
     * Constructs a StylableText with the given contents.
     *
     * @param contents The initial text content.
     */
    public StylableText(String contents) {
        this.text = Text.literal(contents);
    }

    /**
     * Constructs an empty StylableText.
     */
    public StylableText() {
        this.text = Text.empty();
    }

    /**
     * Appends the given contents to the current text.
     *
     * @param contents The text to append.
     * @return This StylableText instance for method chaining.
     */
    public StylableText append(String contents) {
        this.text.append(Text.literal(contents));
        return this;
    }

    /**
     * Appends the given {@link Text} object to the current text.
     *
     * @param text The {@link Text} object to append.
     * @return This StylableText instance for method chaining.
     */
    public StylableText append(Text text) {
        this.text.append(text);
        return this;
    }

    /**
     * Appends another StylableText to the current text.
     *
     * @param stylableText The StylableText to append.
     * @return This StylableText instance for method chaining.
     */
    public StylableText append(StylableText stylableText) {
        this.text.append(stylableText.getText());
        return this;
    }

    /**
     * Sets the color of the text.
     *
     * @param color The color to set, represented as an integer. Can be a hex color code (e.g., 0xFF0000 for red).
     * @return This StylableText instance for method chaining.
     */
    public StylableText setColor(int color) {
        this.text.setStyle(this.text.getStyle().withColor(color));
        return this;
    }

    /**
     * Sets the color of the text using a {@link Formatting} enum.
     *
     * @param color The {@link Formatting} color to set.
     * @return This StylableText instance for method chaining.
     */
    public StylableText setColor(Formatting color) {
        this.text.setStyle(this.text.getStyle().withColor(color));
        return this;
    }

    /**
     * Sets whether the text should be bold.
     *
     * @param bold true to make the text bold, false to remove bold styling.
     * @return This StylableText instance for method chaining.
     */
    public StylableText setBold(boolean bold) {
        this.text.setStyle(this.text.getStyle().withBold(bold));
        return this;
    }

    /**
     * Sets whether the text should be italic.
     *
     * @param italic true to make the text italic, false to remove italic styling.
     * @return This StylableText instance for method chaining.
     */
    public StylableText setItalic(boolean italic) {
        this.text.setStyle(this.text.getStyle().withItalic(italic));
        return this;
    }

    /**
     * Sets whether the text should be underlined.
     *
     * @param underline true to underline the text, false to remove underline styling.
     * @return This StylableText instance for method chaining.
     */
    public StylableText setUnderline(boolean underline) {
        this.text.setStyle(this.text.getStyle().withUnderline(underline));
        return this;
    }

    /**
     * Sets whether the text should have a strikethrough effect.
     *
     * @param strikethrough true to apply strikethrough, false to remove it.
     * @return This StylableText instance for method chaining.
     */
    public StylableText setStrikethrough(boolean strikethrough) {
        this.text.setStyle(this.text.getStyle().withStrikethrough(strikethrough));
        return this;
    }

    /**
     * Sets whether the text should be obfuscated (glitchy Minecraft text style).
     *
     * @param obfuscated true to apply obfuscation, false to remove it.
     * @return This StylableText instance for method chaining.
     */
    public StylableText setObfuscated(boolean obfuscated) {
        this.text.setStyle(this.text.getStyle().withObfuscated(obfuscated));
        return this;
    }

    /**
     * Returns the underlying {@link Text} object.
     *
     * @return The Text object representing this StylableText.
     */
    public Text getText() {
        return this.text;
    }
}
