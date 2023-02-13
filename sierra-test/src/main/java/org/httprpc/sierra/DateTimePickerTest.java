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

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;

import static org.httprpc.sierra.UIBuilder.cell;
import static org.httprpc.sierra.UIBuilder.column;
import static org.httprpc.sierra.UIBuilder.glue;
import static org.httprpc.sierra.UIBuilder.row;

public class DateTimePickerTest extends JFrame implements Runnable {
    private JLabel selectionLabel;

    private DateTimePickerTest() {
        super("Date Picker Test");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void run() {
        var dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        var timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

        setContentPane(column(
            cell(new JLabel()).weightBy(1).with(label -> {
                label.setHorizontalAlignment(SwingConstants.CENTER);

                selectionLabel = label;
            }),
            row(4,
                glue(),
                cell(new DatePicker()).with(datePicker -> {
                    var now = LocalDate.now();

                    datePicker.setDate(now);
                    datePicker.setMinimumDate(now.minus(2, ChronoUnit.DAYS));
                    datePicker.setMaximumDate(now.plus(2, ChronoUnit.DAYS));
                    datePicker.setPopupVerticalAlignment(VerticalAlignment.TOP);
                    datePicker.addActionListener(event -> showSelection(dateFormatter, datePicker.getDate()));
                }),
                cell(new TimePicker()).with(timePicker -> {
                    var now = LocalTime.now();

                    timePicker.setTime(now);
                    timePicker.setMinimumTime(now.minus(2, ChronoUnit.HOURS));
                    timePicker.setMaximumTime(now.plus(2, ChronoUnit.HOURS));
                    timePicker.setPopupVerticalAlignment(VerticalAlignment.TOP);
                    timePicker.addActionListener(event -> showSelection(timeFormatter, timePicker.getTime()));
                }),
                cell(new JSeparator(SwingConstants.VERTICAL)),
                cell(new TimePicker(30)).with(timePicker -> {
                    timePicker.setTime(LocalTime.of(14, 30));
                    timePicker.setMinimumTime(LocalTime.of(6, 0));
                    timePicker.setMaximumTime(LocalTime.of(18, 0));
                    timePicker.setPopupVerticalAlignment(VerticalAlignment.TOP);
                    timePicker.addActionListener(event -> showSelection(timeFormatter, timePicker.getTime()));
                }),
                glue()
            )
        ).with(contentPane -> contentPane.setBorder(new EmptyBorder(8, 8, 8, 8))).getComponent());

        setSize(480, 320);
        setVisible(true);
    }

    private void showSelection(DateTimeFormatter formatter, TemporalAccessor value) {
        var message = String.format("You selected %s.", formatter.format(value));

        selectionLabel.setText(message);
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();

        SwingUtilities.invokeLater(new DateTimePickerTest());
    }
}