package com.harismehuljic.daedalus.gui.elements.body;

import net.minecraft.dialog.body.DialogBody;
import net.minecraft.dialog.body.ItemDialogBody;
import net.minecraft.dialog.body.PlainMessageDialogBody;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Optional;

/**
 * Represents a body element that contains an item stack, which can be displayed in a dialog.
 * <p>
 * This class allows for customization of how the item is displayed, including whether to show
 * decorations or tooltips, and changing dimensions of the item display.
 * </p>
 */
public class ItemBody implements BodyElement {
    private final ItemStack itemStack;
    private boolean showDecorations = true;
    private boolean showTooltip = true;
    private Optional<PlainMessageDialogBody> description = Optional.empty();
    private int width = 25;
    private int height = 25;

    /**
     * Constructs an ItemBody with the specified item stack.
     * <p>
     * This constructor constructs an ItemBody with the given item stack, using default display options. The item will be
     * displayed with decorations and tooltips, and the default dimensions of 25x25 pixels. If these options need to be
     * customised, use {@link #ItemBody(ItemStack, boolean, boolean, int, int)} instead.
     * </p>
     *
     * @param itemStack The item stack to be displayed in the dialog body.
     *
     * @apiNote A valid ItemStack can be obtained using the {@link Items} class, which provides a static reference
     * to all vanilla items. For example, to create an ItemBody with a diamond, you can use:
     * {@code new ItemStack(Items.DIAMOND)}.
     *
     * @see ItemStack
     * @see Items
     */
    public ItemBody(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Constructs an ItemBody with the specified item stack and display options.
     *
     * @param itemStack        The item stack to be displayed in the dialog body.
     * @param showDecorations  Whether to show decorations around the item.
     * @param showTooltip      Whether to show tooltips for the item.
     * @param width            The width of the item display.
     * @param height           The height of the item display.
     *
     * @param itemStack The item stack to be displayed in the dialog body.
     *
     * @apiNote A valid ItemStack can be obtained using the {@link Items} class, which provides a static reference
     * to all vanilla items. For example, to create an ItemBody with a diamond, you can use:
     * {@code new ItemStack(Items.DIAMOND)}.
     *
     * @see ItemStack
     * @see Items
     */
    public ItemBody(ItemStack itemStack, boolean showDecorations, boolean showTooltip, int width, int height) {
        this.itemStack = itemStack;
        this.showDecorations = showDecorations;
        this.showTooltip = showTooltip;
        this.width = width;
        this.height = height;
    }

    /**
     * Sets the description for the item body.
     *
     * @param description The description to be set, as a {@link TextBody}.
     * @return This ItemBody instance for method chaining.
     *
     * @see TextBody
     */
    public ItemBody setDescription(TextBody description) {
        this.description = Optional.of((PlainMessageDialogBody) description.getBody());
        return this;
    }

    @Override
    public DialogBody getBody() {
        return new ItemDialogBody(this.itemStack, this.description, this.showDecorations, this.showTooltip, this.width, this.height);
    }
}
