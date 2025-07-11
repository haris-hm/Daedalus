package com.harismehuljic.daedalus.gui.elements.body;

import net.minecraft.dialog.body.DialogBody;
import net.minecraft.dialog.body.ItemDialogBody;
import net.minecraft.dialog.body.PlainMessageDialogBody;
import net.minecraft.item.ItemStack;

import java.util.Optional;

public class ItemBody implements BodyElement {
    private ItemStack itemStack;
    private boolean showDecorations = true;
    private boolean showTooltip = true;
    private Optional<PlainMessageDialogBody> description = Optional.empty();
    private int width = 100;
    private int height = 100;

    public ItemBody(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBody(ItemStack itemStack, boolean showDecorations, boolean showTooltip, int width, int height) {
        this.itemStack = itemStack;
        this.showDecorations = showDecorations;
        this.showTooltip = showTooltip;
        this.width = width;
        this.height = height;
    }

    public ItemBody setDescription(TextBody description) {
        this.description = Optional.of((PlainMessageDialogBody) description.getBody());
        return this;
    }

    @Override
    public DialogBody getBody() {
        return new ItemDialogBody(this.itemStack, this.description, this.showDecorations, this.showTooltip, this.width, this.height);
    }
}
