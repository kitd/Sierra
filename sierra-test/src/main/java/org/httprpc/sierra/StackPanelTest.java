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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import static org.httprpc.sierra.UIBuilder.cell;
import static org.httprpc.sierra.UIBuilder.stack;

public class StackPanelTest extends JFrame implements Runnable {
    private StackPanelTest() {
        super("Stack Panel Test");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void run() {
        var contentPane = stack(
            cell(new JLabel("TOP", null, SwingConstants.CENTER))
                .with(label -> label.setVerticalAlignment(SwingConstants.TOP)),
            cell(new JLabel("CENTER", null, SwingConstants.CENTER))
                .with(label -> label.setVerticalAlignment(SwingConstants.CENTER)),
            cell(new JLabel("BOTTOM", null, SwingConstants.CENTER))
                .with(label -> label.setVerticalAlignment(SwingConstants.BOTTOM))
        );

        contentPane.setBorder(new EmptyBorder(16, 16, 16, 16));

        setContentPane(contentPane);

        setSize(320, 240);
        setVisible(true);
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();

        SwingUtilities.invokeLater(new StackPanelTest());
    }
}