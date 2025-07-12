package com.harismehuljic.daedalus.gui;

import com.harismehuljic.daedalus.gui.elements.actions.ActionButton;
import com.harismehuljic.daedalus.gui.elements.body.BodyElement;
import com.harismehuljic.daedalus.gui.elements.body.ItemBody;
import com.harismehuljic.daedalus.gui.elements.body.TextBody;
import com.harismehuljic.daedalus.gui.elements.input.*;
import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a custom dialog that can be defined and opened in the game.
 * <p>
 * This class provides methods to define the dialog's title, body elements, input elements,
 * and action buttons. It also allows for configuration of dialog behavior such as closing on
 * escape key press and pausing the game when the dialog is open.
 * </p>
 */
public abstract class CustomDialog {
    private final ArrayList<BodyElement> bodyElements = new ArrayList<>();
    private final ArrayList<InputElement> inputElements = new ArrayList<>();
    private final List<ActionButton> actionButtons = new ArrayList<>();

    private StylableText dialogTitle;
    private boolean closeOnEscape = true;
    private boolean pauseGame = false;
    private int actionButtonColumns = 2;

    /**
     * Constructs a CustomDialog instance.
     * <p>
     * This constructor is protected to ensure that only subclasses can instantiate it.
     * The dialog must be defined in the subclass by overriding the {@link #defineDialog()} method.
     * </p>
     */
    protected CustomDialog() {
        defineDialog();
    }

    /**
     * Defines the dialog's structure, including title, body elements, input elements, and action buttons.
     * <p>
     * This method must be implemented by subclasses to specify the dialog's content and behavior.
     * </p>
     */
    protected abstract void defineDialog();

    /**
     * Sets the title of the dialog.
     *
     * @param title The title to set for the dialog, represented as a {@link StylableText}.
     */
    protected void setDialogTitle(StylableText title) {
        this.dialogTitle = title;
    }

    /**
     * Sets the title of the dialog using a plain string.
     *
     * @param title The title to set for the dialog.
     */
    protected void setDialogTitle(String title) {
        this.dialogTitle = new StylableText(title);
    }

    /**
     * Configures whether the dialog should close when the escape key is pressed.
     *
     * @param closeOnEscape If true, the dialog will close on escape key press; otherwise, it will not.
     *
     * @implNote By default, this is true.
     */
    protected void setCloseOnEscape(boolean closeOnEscape) {
        this.closeOnEscape = closeOnEscape;
    }

    /**
     * Configures whether the game should be paused when the dialog is open.
     *
     * @param pauseGame If true, the game will be paused when the dialog is open; otherwise, it will not.
     *
     * @implNote By default, this is false. Also, this is only effective if the dialog is opened in a single-player
     * world.
     */
    protected void setPauseGame(boolean pauseGame) {
        this.pauseGame = pauseGame;
    }

    /**
     * Sets the number of columns for action buttons in the dialog.
     *
     * @param actionButtonColumns The number of columns for action buttons, must be a positive integer.
     * @throws IllegalArgumentException if the provided value is not a positive integer.
     */
    protected void setActionButtonColumns(int actionButtonColumns) throws IllegalArgumentException {
        if (actionButtonColumns <= 0) {
            throw new IllegalArgumentException("Action button columns must be a positive integer.");
        }
        this.actionButtonColumns = actionButtonColumns;
    }

    /**
     * Adds a body element to the dialog.
     *
     * @param bodyElement The body element to add, which can be any subclass of {@link BodyElement}.
     *
     * @see TextBody
     * @see ItemBody
     */
    protected void addBodyElement(BodyElement bodyElement) {
        this.bodyElements.add(bodyElement);
    }

    /**
     * Adds an input element to the dialog.
     *
     * @param inputElement The input element to add, which can be any subclass of {@link InputElement}.
     *
     * @see TextInput
     * @see ButtonSelectorInput
     * @see BooleanInput
     * @see NumberInput
     */
    protected void addInputElement(InputElement inputElement) {
        this.inputElements.add(inputElement);
    }

    /**
     * Adds an action button to the dialog.
     *
     * @param actionButton The action button to add, which should be an instance of {@link ActionButton}.
     */
    protected void addActionButton(ActionButton actionButton) {
        this.actionButtons.add(actionButton);
    }

    /**
     * Opens the dialog for the specified player.
     * <p>
     * This method constructs the dialog using the defined title, body elements, input elements, and action buttons,
     * and then opens it for the given player. It will throw an {@link IllegalStateException} if the dialog is not
     * properly defined (e.g., missing title or action buttons).
     * </p>
     *
     * @param spe The server player entity for whom the dialog should be opened.
     * @throws IllegalStateException If the dialog title is not set or if there are no action buttons defined.
     */
    public void openDialog(@NotNull ServerPlayerEntity spe) throws IllegalStateException {
        String className = this.getClass().getSimpleName();
        if (this.dialogTitle == null) {
            throw new IllegalStateException(String.format("Dialog title must be set in %s" +
                    ".defineDialog() before opening the dialog.", className));
        }
        else if (this.actionButtons.isEmpty()) {
            throw new IllegalStateException(String.format("There must be at least one action button defined in %s" +
                    ".defineDialog() for the dialog to open.", className));
        }

        DialogBuilder dialogBuilder = new DialogBuilder()
                .setTitle(this.dialogTitle)
                .setCloseOnEscape(this.closeOnEscape)
                .setPauseGame(this.pauseGame)
                .setActionButtonColumns(this.actionButtonColumns);

        this.bodyElements.forEach(dialogBuilder::addBodyElement);
        this.inputElements.forEach(dialogBuilder::addInputElement);
        this.actionButtons.forEach(dialogBuilder::addActionButton);

        dialogBuilder.openDialog(spe);
    }
}
