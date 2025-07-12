package com.harismehuljic.daedalus.gui;

import com.harismehuljic.daedalus.gui.elements.actions.ActionButton;
import com.harismehuljic.daedalus.gui.elements.body.BodyElement;
import com.harismehuljic.daedalus.gui.elements.input.InputElement;
import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomDialog {
    private StylableText dialogTitle = new StylableText(String.format("%s Dialog", this.getClass().getSimpleName()));
    private boolean closeOnEscape = true;
    private boolean pauseGame = false;
    private int actionButtonColumns = 2;

    private final ArrayList<BodyElement> bodyElements = new ArrayList<>();
    private final ArrayList<InputElement> inputElements = new ArrayList<>();
    private final List<ActionButton> actionButtons = new ArrayList<>();

    protected CustomDialog() {
        defineDialog();
    }

    protected abstract void defineDialog();

    protected void setDialogTitle(StylableText title) {
        this.dialogTitle = title;
    }

    protected void setDialogTitle(String title) {
        this.dialogTitle = new StylableText(title);
    }

    protected void setCloseOnEscape(boolean closeOnEscape) {
        this.closeOnEscape = closeOnEscape;
    }

    protected void setPauseGame(boolean pauseGame) {
        this.pauseGame = pauseGame;
    }

    protected void setActionButtonColumns(int actionButtonColumns) throws IllegalArgumentException {
        if (actionButtonColumns <= 0) {
            throw new IllegalArgumentException("Action button columns must be a positive integer.");
        }
        this.actionButtonColumns = actionButtonColumns;
    }

    protected void addBodyElement(BodyElement bodyElement) {
        this.bodyElements.add(bodyElement);
    }

    protected void addInputElement(InputElement inputElement) {
        this.inputElements.add(inputElement);
    }

    protected void addActionButton(ActionButton actionButton) {
        this.actionButtons.add(actionButton);
    }

    public void openDialog(ServerPlayerEntity spe) {
        DialogBuilder dialogBuilder = new DialogBuilder()
            .setTitle(this.dialogTitle)
            .setCloseOnEscape(this.closeOnEscape)
            .setPauseGame(this.pauseGame)
            .setActionButtonColumns(this.actionButtonColumns);

        for (BodyElement bodyElement : this.bodyElements)
            dialogBuilder.addBodyElement(bodyElement);

        for (InputElement inputElement : this.inputElements)
            dialogBuilder.addInput(inputElement);

        for (ActionButton actionButton : this.actionButtons)
            dialogBuilder.addActionButton(actionButton);

        dialogBuilder.openDialog(spe);
    }
}
