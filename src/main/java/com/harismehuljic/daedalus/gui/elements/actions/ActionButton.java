package com.harismehuljic.daedalus.gui.elements.actions;

import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.dialog.DialogActionButtonData;
import net.minecraft.dialog.DialogButtonData;
import net.minecraft.dialog.Dialogs;
import net.minecraft.dialog.action.DialogAction;
import net.minecraft.dialog.action.DynamicCustomDialogAction;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Represents an action button in a dialog, which are the buttons at the bottom of a dialog which can be clicked
 * to perform actions or close the dialog.
 *
 * <p>
 * Action buttons are used to trigger specific actions when clicked, triggering a {@link Consumer} callback function.
 * which takes in an {@link NbtCompound} as a parameter.
 * </p>
 */
public class ActionButton {
    private final Identifier buttonID;
    private final int width;
    private final StylableText label;
    private final Consumer<NbtCompound> callback;
    private final Optional<NbtCompound> extraData;

    /**
     * Constructs an ActionButton with the specified parameters.
     *
     * @param buttonID  The unique identifier for the button.
     * @param width     The width of the button.
     * @param label     The label text for the button.
     * @param callback  The callback function to be executed when the button is clicked.
     * @param extraData Optional extra data to be passed to the callback.
     *
     * @apiNote The {@code buttonID} {@link Identifier} should be of the form "modid:button_name", where "modid" is your mod's
     * ID and button_name is a custom name for this action button.
     *
     * @see Consumer
     * @see Optional
     * @see NbtCompound
     * @see Identifier
     */
    public ActionButton(Identifier buttonID, int width, StylableText label, Consumer<NbtCompound> callback, Optional<NbtCompound> extraData) {
        this.buttonID = buttonID;
        this.width = width;
        this.label = label;
        this.callback = callback;
        this.extraData = extraData;
    }

    /**
     * Constructs an ActionButton with the specified parameters, using a default width.
     *
     * @param buttonID  The unique identifier for the button.
     * @param label     The label text for the button.
     * @param callback  The callback function to be executed when the button is clicked.
     *
     * @implNote The default width is 310, as defined in {@link Dialogs#BUTTON_WIDTH}.
     *
     * @apiNote The {@code buttonID} {@link Identifier} should be of the form "modid:button_name", where "modid" is your mod's
     * ID and button_name is a custom name for this action button.
     */
    public ActionButton(Identifier buttonID, StylableText label, Consumer<NbtCompound> callback) {
        this(buttonID, Dialogs.BUTTON_WIDTH, label, callback, Optional.empty());
    }

    /**
     * Returns the action associated with this button.
     *
     * @return A {@link DialogAction} that represents the action to be performed when the button is clicked.
     */
    public DialogAction getAction() {
        return new DynamicCustomDialogAction(this.buttonID, this.extraData);
    }

    /**
     * Returns the data associated with this button, which includes the label and width.
     *
     * @return A {@link DialogActionButtonData} containing the button's label and width.
     */
    public DialogActionButtonData getButton() {
        return new DialogActionButtonData(
            new DialogButtonData(this.label.getText(), this.width),
            Optional.of(this.getAction())
        );
    }

    /**
     * Returns the callback function that will be executed when the button is clicked.
     *
     * @return A {@link Consumer} that takes an {@link NbtCompound} as input.
     */
    public Consumer<NbtCompound> getCallback() {
        return this.callback;
    }

    /**
     * Returns the unique identifier for this button.
     *
     * @return An {@link Identifier} representing the button's ID.
     */
    public Identifier getButtonID() {
        return this.buttonID;
    }
}
