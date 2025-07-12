package com.harismehuljic.daedalus.gui;

import com.harismehuljic.daedalus.data.DialogManager;
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

/**
 * A builder class for creating and managing custom dialogs in the game.
 * <p>
 * This class allows you to define the dialog's title, body elements, input elements,
 * and action buttons, as well as configure dialog behavior such as closing on escape
 * and pausing the game.
 * </p>
 */
public class DialogBuilder {
    private StylableText dialogTitle;
    private boolean closeOnEscape = true;
    private boolean pauseGame = false;
    private int actionButtonColumns = 2;

    private final ArrayList<DialogBody> bodyElements   = new ArrayList<>();
    private final ArrayList<DialogInput> dialogInputs = new ArrayList<>();
    private final ArrayList<DialogActionButtonData> actionButtons = new ArrayList<>();

    private final HashMap<Identifier, Consumer<NbtCompound>> actions = new HashMap<>();

    /**
     * Constructs a DialogBuilder instance.
     * <p>
     * This constructor initializes the dialog builder with default settings.
     * </p>
     *
     * @apiNote At a minimum, the title should be set using {@link DialogBuilder#setTitle(StylableText)} or
     * {@link DialogBuilder#setTitle(String)}, and at least one action button should be added using
     * {@link DialogBuilder#addActionButton(ActionButton)} before building the dialog.
     */
    public DialogBuilder() {}

    /**
     * Sets the title of the dialog.
     *
     * @param title The title to set for the dialog, represented as a {@link StylableText}.
     * @return The current instance of DialogBuilder for method chaining.
     */
    public DialogBuilder setTitle(StylableText title) {
        this.dialogTitle = title;
        return this;
    }

    /**
     * Sets the title of the dialog using a plain string.
     *
     * @param title The title to set for the dialog.
     * @return The current instance of DialogBuilder for method chaining.
     */
    public DialogBuilder setTitle(String title) {
        this.dialogTitle = new StylableText(title);
        return this;
    }

    /**
     * Sets whether the dialog should close when the escape key is pressed.
     *
     * @param closeOnEscape True if the dialog should close on escape, false otherwise.
     * @return The current instance of DialogBuilder for method chaining.
     *
     * @implNote By default, this is true, meaning the dialog will close when the escape key is pressed.
     */
    public DialogBuilder setCloseOnEscape(boolean closeOnEscape) {
        this.closeOnEscape = closeOnEscape;
        return this;
    }

    /**
     * Sets whether the game should be paused when the dialog is opened.
     *
     * @param pauseGame True if the game should be paused, false otherwise.
     * @return The current instance of DialogBuilder for method chaining.
     *
     * @implNote By default, this is false, meaning the game will not be paused when the dialog is open. This will also
     * only work if the dialog is opened in a single-player world.
     */
    public DialogBuilder setPauseGame(boolean pauseGame) {
        this.pauseGame = pauseGame;
        return this;
    }

    /**
     * Sets the number of columns for action buttons in the dialog.
     *
     * @param actionButtonColumns The number of columns for action buttons.
     * @return The current instance of DialogBuilder for method chaining.
     *
     * @implNote By default, this is set to 2, meaning if there is more than 1 action button,
     * they will be arranged in two columns.
     */
    public DialogBuilder setActionButtonColumns(int actionButtonColumns) {
        this.actionButtonColumns = actionButtonColumns;
        return this;
    }

    /**
     * Adds a body element to the dialog.
     *
     * @param bodyElement The body element to add, represented as a {@link BodyElement}.
     * @return The current instance of DialogBuilder for method chaining.
     */
    public DialogBuilder addBodyElement(BodyElement bodyElement) {
        this.bodyElements.add(bodyElement.getBody());
        return this;
    }

    /**
     * Adds an input element to the dialog.
     *
     * @param inputElement The input element to add, represented as an {@link InputElement}.
     * @return The current instance of DialogBuilder for method chaining.
     */
    public DialogBuilder addInputElement(InputElement inputElement) {
        this.dialogInputs.add(inputElement.getInput());
        return this;
    }

    /**
     * Adds an action button to the dialog.
     *
     * @param actionButton The action button to add, represented as an {@link ActionButton}.
     * @return The current instance of DialogBuilder for method chaining.
     */
    public DialogBuilder addActionButton(ActionButton actionButton) {
        this.actionButtons.add(actionButton.getButton());
        this.actions.put(actionButton.getButtonID(), actionButton.getCallback());
        return this;
    }

    /**
     * Builds the dialog with the current settings and elements.
     *
     * @param spe The server player entity for whom the dialog is being built.
     * @return A new instance of {@link Dialog} configured with the specified settings and elements.
     */
    public Dialog build(ServerPlayerEntity spe) {
        for (Map.Entry<Identifier, Consumer<NbtCompound>> entry : this.actions.entrySet()) {
            DialogManager.registerCallback(entry.getKey(), spe, entry.getValue());
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

    /**
     * Opens the dialog for the specified player.
     *
     * @param spe The server player entity for whom the dialog should be opened.
     */
    public void openDialog(ServerPlayerEntity spe) {
        Dialog dialog = this.build(spe);
        spe.openDialog(new RegistryEntry.Direct<>(dialog));
    }
}
