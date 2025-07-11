package com.harismehuljic.daedalus.gui;

import com.harismehuljic.daedalus.gui.elements.actions.ActionButton;
import com.harismehuljic.daedalus.gui.elements.body.BodyElement;
import com.harismehuljic.daedalus.gui.elements.input.InputElement;
import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.dialog.AfterAction;
import net.minecraft.dialog.DialogActionButtonData;
import net.minecraft.dialog.DialogCommonData;
import net.minecraft.dialog.body.DialogBody;
import net.minecraft.dialog.type.Dialog;
import net.minecraft.dialog.type.DialogInput;
import net.minecraft.dialog.type.MultiActionDialog;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class DialogBuilder {
    private final Identifier dialogID;
    private StylableText dialogTitle;
    private boolean closeOnEscape = true;
    private boolean pauseGame = false;
    private int actionButtonColumns = 2;

    private final ArrayList<DialogBody> bodyElements   = new ArrayList<>();
    private final ArrayList<DialogInput> dialogInputs = new ArrayList<>();
    private final ArrayList<DialogActionButtonData> actionButtons = new ArrayList<>();

    private final HashMap<Identifier, Consumer<NbtCompound>> actions = new HashMap<>();

    public DialogBuilder(Identifier dialogID) {
        this.dialogID = dialogID;
    }

    public DialogBuilder setTitle(StylableText title) {
        this.dialogTitle = title;
        return this;
    }

    public DialogBuilder setTitle(String title) {
        this.dialogTitle = new StylableText(title);
        return this;
    }

    public DialogBuilder setCloseOnEscape(boolean closeOnEscape) {
        this.closeOnEscape = closeOnEscape;
        return this;
    }

    public DialogBuilder setPauseGame(boolean pauseGame) {
        this.pauseGame = pauseGame;
        return this;
    }

    public DialogBuilder setActionButtonColumns(int actionButtonColumns) {
        this.actionButtonColumns = actionButtonColumns;
        return this;
    }

    public DialogBuilder addBodyElement(BodyElement bodyElement) {
        this.bodyElements.add(bodyElement.getBody());
        return this;
    }

    public DialogBuilder addInput(InputElement inputElement) {
        this.dialogInputs.add(inputElement.getInput());
        return this;
    }

    public DialogBuilder addActionButton(ActionButton actionButton) {
        this.actionButtons.add(actionButton.getButton());
        this.actions.put(actionButton.getButtonID(), actionButton.getCallback());
        return this;
    }

    public Dialog build(ServerPlayerEntity spe) {
        for (Map.Entry<Identifier, Consumer<NbtCompound>> entry : this.actions.entrySet()) {
            DialogManager.registerCallback(spe, entry.getKey(), entry.getValue());
        }

        DialogCommonData data = new DialogCommonData(
                this.dialogTitle.getText(),
                Optional.empty(),
                this.closeOnEscape,
                this.pauseGame,
                AfterAction.CLOSE,
                this.bodyElements,
                this.dialogInputs
        );

        return new MultiActionDialog(
                data,
                this.actionButtons,
                Optional.empty(),
                this.actionButtonColumns
        );
    }

    public void openDialog(ServerPlayerEntity spe) {
        Dialog dialog = this.build(spe);
        spe.openDialog(new RegistryEntry.Direct<>(dialog));
    }
}
