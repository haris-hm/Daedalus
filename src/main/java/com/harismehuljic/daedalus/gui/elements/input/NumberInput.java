package com.harismehuljic.daedalus.gui.elements.input;

import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.dialog.input.InputControl;
import net.minecraft.dialog.input.NumberRangeInputControl;

import java.util.Optional;

/**
 * Represents a number input element in a dialog, which allows the user to input a numeric value within a specified range
 * using a slider.
 * <p>
 * This class is used to create a number input field with a label, minimum and maximum values, an initial value, and
 * a step size, which can be displayed in a dialog for user interaction.
 * </p>
 */
public class NumberInput extends InputElement {
    private final StylableText label;

    private NumberRangeInputControl.RangeInfo rangeInfo;

    /**
     * Constructs a NumberInput with the specified key, width, label, minimum and maximum values,
     *
     * @param key The unique key for this input element, used to identify it in the dialog.
     * @param width The width of the input element as displayed in the dialog.
     * @param label The label for the input element, which will be displayed to the user.
     * @param min The minimum value for the input.
     * @param max The maximum value for the input.
     * @param initialValue The initial value of the input, which must be within the range [min, max].
     * @param step The step size for the input, which must be a positive number and less than or equal to the range size.
     * @throws IllegalArgumentException If the initial value is not within the range [min, max] or if the step size is
     * not a positive number
     */
    public NumberInput(String key, int width, StylableText label, float min, float max, float initialValue, float step)
            throws IllegalArgumentException {
        super(key, width);
        this.label = label;
        this.rangeInfo = new NumberRangeInputControl.RangeInfo(
                min,
                max,
                Optional.empty(),
                Optional.empty()
        );
        this.setInitialValue(initialValue);
        this.setStep(step);
    }

    /**
     * Constructs a NumberInput with the specified key, width, label, minimum and maximum values,
     * and an initial value that is the midpoint of the range.
     *
     * @param key The unique key for this input element, used to identify it in the dialog.
     * @param width The width of the input element as displayed in the dialog.
     * @param label The label for the input element, which will be displayed to the user.
     * @param min The minimum value for the input.
     * @param max The maximum value for the input.
     */
    public NumberInput(String key, int width, StylableText label, float min, float max) {
        this(key, width, label, min, max, (float) Math.floor((min + max) / 2), 1.0f);
    }

    /**
     * Sets the initial value for the number input.
     *
     * @param initialValue The initial value to set for the number input.
     * @return The current instance of NumberInput for method chaining.
     * @throws IllegalArgumentException If the initial value is not within the range defined by the minimum and
     * maximum values.
     */
    public NumberInput setInitialValue(float initialValue) throws IllegalArgumentException {
        float min = this.rangeInfo.start();
        float max = this.rangeInfo.end();
        Optional<Float> step = this.rangeInfo.step();

        if (initialValue < min || initialValue > max) {
            throw new IllegalArgumentException("Initial value must be within the range [" + min + ", " + max + "]");
        }

        this.rangeInfo = new NumberRangeInputControl.RangeInfo(
            min,
            max,
            Optional.of(initialValue),
            step
        );
        return this;
    }

    /**
     * Sets the step size for the number input.
     *
     * @param step The step size to set for the number input.
     * @return The current instance of NumberInput for method chaining.
     * @throws IllegalArgumentException If the step size is not a positive number or if it exceeds the range size.
     */
    public NumberInput setStep(float step) throws IllegalArgumentException {
        float min = this.rangeInfo.start();
        float max = this.rangeInfo.end();
        Optional<Float> initialValue = this.rangeInfo.step();
        float range = max - min;

        if (step <= 0) {
            throw new IllegalArgumentException("Step must be a positive number.");
        }
        else if (step > range) {
            throw new IllegalArgumentException(String.format("Step must be less than or equal to the range size: %.2f.", range));
        }

        this.rangeInfo = new NumberRangeInputControl.RangeInfo(
            min,
            max,
            initialValue,
            Optional.of(step)
        );
        return this;
    }

    @Override
    protected InputControl getInputControl() {
        String format = "options.generic_value";
        return new NumberRangeInputControl(this.width, this.label.getText(), format, this.rangeInfo);
    }
}
