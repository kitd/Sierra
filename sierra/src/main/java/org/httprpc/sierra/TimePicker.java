/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.httprpc.sierra;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

/**
 * Text field that supports local time entry.
 */
public class TimePicker extends JTextField {
    private LocalTime time = null;

    private LocalTime minimumTime = null;
    private LocalTime maximumTime = null;

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

    private final InputVerifier inputVerifier = new InputVerifier() {
        @Override
        public boolean verify(JComponent input) {
            try {
                var time = LocalTime.parse(getText(), timeFormatter);

                if (!validate(time)) {
                    return false;
                }

                TimePicker.this.time = time;

                TimePicker.super.fireActionPerformed();

                return true;
            } catch (DateTimeParseException exception) {
                return false;
            }
        }
    };

    /**
     * Constructs a new time picker.
     */
    public TimePicker() {
        super(6);

        setInputVerifier(inputVerifier);
    }

    /**
     * Throws {@link UnsupportedOperationException}.
     * {@inheritDoc}
     */
    @Override
    public void setText(String text) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the selected time.
     *
     * @return
     * The selected time.
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Sets the selected time.
     *
     * @param time
     * The selected time.
     */
    public void setTime(LocalTime time) {
        if (time == null) {
            super.setText(null);
        } else {
            if (!validate(time)) {
                throw new IllegalArgumentException();
            }

            super.setText(timeFormatter.format(time));
        }

        this.time = truncate(time);
    }

    private boolean validate(LocalTime time) {
        return (minimumTime == null || !time.isBefore(minimumTime))
            && (maximumTime == null || !time.isAfter(maximumTime));
    }

    /**
     * Returns the minimum value allowed by this time picker.
     *
     * @return
     * The minimum time, or {@code null} if no minimum time is set.
     */
    public LocalTime getMinimumTime() {
        return minimumTime;
    }

    /**
     * Sets the minimum value allowed by this time picker.
     *
     * @param minimumTime
     * The minimum time, or {@code null} for no minimum time.
     */
    public void setMinimumTime(LocalTime minimumTime) {
        if (minimumTime != null) {
            if (maximumTime != null && minimumTime.isAfter(maximumTime)) {
                throw new IllegalStateException();
            }

            if (time != null && time.isBefore(minimumTime)) {
                setTime(minimumTime);
            }
        }

        this.minimumTime = truncate(minimumTime);
    }

    /**
     * Returns the maximum value allowed by this time picker.
     *
     * @return
     * The maximum time, or {@code null} if no maximum time is set.
     */
    public LocalTime getMaximumTime() {
        return maximumTime;
    }

    /**
     * Sets the maximum value allowed by this time picker.
     *
     * @param maximumTime
     * The maximum time, or {@code null} for no maximum time.
     */
    public void setMaximumTime(LocalTime maximumTime) {
        if (maximumTime != null) {
            if (minimumTime != null && maximumTime.isBefore(minimumTime)) {
                throw new IllegalStateException();
            }

            if (time != null && time.isAfter(maximumTime)) {
                setTime(maximumTime);
            }
        }

        this.maximumTime = truncate(maximumTime);
    }

    /**
     * Verifies the contents of the text field.
     * {@inheritDoc}
     */
    @Override
    protected void fireActionPerformed() {
        inputVerifier.verify(this);
    }

    private static LocalTime truncate(LocalTime time) {
        return (time == null) ? null : time.withSecond(0).withNano(0);
    }
}
