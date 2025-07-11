package com.harismehuljic.daedalus.gui.elements.actions;

import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.dialog.DialogActionButtonData;
import net.minecraft.dialog.DialogButtonData;
import net.minecraft.dialog.Dialogs;
import net.minecraft.dialog.action.DialogAction;
import net.minecraft.dialog.action.DynamicCustomDialogAction;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

public class ActionButton {
    private final Identifier buttonID;
    private final int width;
    private final StylableText label;
    private final Consumer<NbtCompound> callback;
    private final NbtCompound extraData;

    public ActionButton(Identifier buttonID, int width, StylableText label, Consumer<NbtCompound> callback, @Nullable NbtCompound extraData) {
        this.buttonID = buttonID;
        this.width = width;
        this.label = label;
        this.callback = callback;
        this.extraData = extraData;
    }

    public ActionButton(Identifier buttonID, StylableText text, Consumer<NbtCompound> callback) {
        this(buttonID, Dialogs.BUTTON_WIDTH, text, callback, null);
    }

    public DialogAction getAction() {
        return new DynamicCustomDialogAction(this.buttonID, Optional.ofNullable(this.extraData));
    }

    public DialogActionButtonData getButton() {
        return new DialogActionButtonData(
            new DialogButtonData(this.label.getText(), this.width),
            Optional.of(this.getAction())
        );
    }

    public Consumer<NbtCompound> getCallback() {
        return this.callback;
    }

    public Identifier getButtonID() {
        return this.buttonID;
    }
}
