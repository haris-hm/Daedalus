package com.harismehuljic.daedalus.gui.elements.body;

import net.minecraft.dialog.body.DialogBody;

/**
 * Represents an element that contains a dialog body.
 * <p>
 * This interface is used to define elements that can provide a {@link DialogBody} for rendering
 * within a dialog context.
 * </p>
 */
public interface BodyElement {
    /**
     * Retrieves the dialog body associated with this element.
     *
     * @return The {@link DialogBody} that this element provides.
     */
    DialogBody getBody();
}
