/*
 * Copyright (c) 2025 by Tang Kun. All rights reserved.
 */

package person.tangkun.photoformatter;

import javax.swing.*;

public class SettingUI {
    private JPanel mainPanel;
    private JPanel settingPanel;
    private JRadioButton jpegRadioButton;
    private JSlider slider1;
    private JRadioButton webpRadioButton;
    private JSlider slider2;

    public SettingUI() {
    }

    public JComponent getComponent() {
        return mainPanel;
    }
}
