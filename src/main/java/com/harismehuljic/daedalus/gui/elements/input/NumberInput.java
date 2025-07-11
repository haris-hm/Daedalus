package com.harismehuljic.daedalus.gui.elements.input;

import com.harismehuljic.daedalus.gui.elements.text.StylableText;
import net.minecraft.dialog.input.InputControl;
import net.minecraft.dialog.input.NumberRangeInputControl;

import java.util.Optional;

public class NumberInput extends InputElement {
    private final StylableText label;

    private NumberRangeInputControl.RangeInfo rangeInfo;

    public NumberInput(String key, int width, StylableText label, float min, float max, float initialValue, float step) throws IllegalArgumentException {
        super(key, width);
        this.label = label;
        this.rangeInfo = new NumberRangeInputControl.RangeInfo(min, max, Optional.empty(), Optional.empty());
        this.setInitialValue(initialValue);
        this.setStep(step);
    }

    public NumberInput(String key, int width, StylableText label, float min, float max) {
        this(key, width, label, min, max, (float) Math.floor((min + max) / 2), 1.0f);
    }

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
