/*
 * Copyright (c) 2025 by Tang Kun. All rights reserved.
 */

package person.tangkun.photoformatter.actions;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.CommonActionsPanel;
import org.jetbrains.annotations.NotNull;
import person.tangkun.photoformatter.utils.LogUtil;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

public class PhotoFormatPicker extends JPanel {
    private static final String TAG = PhotoFormatPicker.class.getSimpleName();
    private final List<? extends CommonActionsPanel.Listener> myExternalListeners;
    private int selectedIndex = 0;
    private int jpegSliderPercent = 0;
    private int webpSliderPercent = 0;

    public PhotoFormatPicker(List<? extends CommonActionsPanel.Listener> listeners) {
        super();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        try {
            add(buildPanel(), BorderLayout.NORTH);
        } catch (ParseException e) {
            LogUtil.e(TAG, "ParseException" + e);
        }

        setSize(300, 350);
        myExternalListeners = listeners;
    }

    private JComponent buildPanel() throws ParseException {
        final JPanel result = new JPanel(new BorderLayout());
        final JPanel left = new JPanel(new BorderLayout());
        final JPanel right = new JPanel(new BorderLayout());
        result.add(left, BorderLayout.WEST);
        result.add(right, BorderLayout.EAST);

        final JRadioButton jpegRadioButton =  new JRadioButton();
        final JRadioButton webpRadioButton =  new JRadioButton();
        jpegRadioButton.setSelected(true);
        jpegRadioButton.setText("jpeg");
        addRadioButtonListener(jpegRadioButton, webpRadioButton, jpegRadioButton, 0);
        webpRadioButton.setText("webp");
        addRadioButtonListener(webpRadioButton, webpRadioButton, jpegRadioButton, 1);

        left.add(jpegRadioButton, BorderLayout.NORTH);
        left.add(webpRadioButton, BorderLayout.SOUTH);

        JSlider jpegSlider = createJSlider(0);
        JSlider webpSlider = createJSlider(1);

        right.add(jpegSlider, BorderLayout.NORTH);
        right.add(webpSlider, BorderLayout.SOUTH);

        return result;
    }

    private void addRadioButtonListener(JRadioButton webpRadioButton, JRadioButton webpRadioButton1, JRadioButton jpegRadioButton, int index) {
        webpRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelected(jpegRadioButton, webpRadioButton1, index);
            }
        });
    }

    private @NotNull JSlider createJSlider(int index) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 200, 50);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(5);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof JSlider source) {
                    LogUtil.i(TAG, "jpegSlider value: " + source.getValue());
                    switch (index) {
                        case 0:
                            jpegSliderPercent = source.getValue();
                            break;
                        case 1:
                            webpSliderPercent = source.getValue();
                            break;
                    }
                    System.out.println(source.getValue());
                }
            }
        });
        return slider;
    }

    private void setSelected(JRadioButton button1, JRadioButton button2, int index) {
        LogUtil.i(TAG, "setSelected: " + index);
        selectedIndex = index;
        switch (index) {
            case 0:
                button1.setSelected(true);
                button2.setSelected(false);
                break;
            case 1:
                button1.setSelected(false);
                button2.setSelected(true);
                break;
            default:
        }
    }

    private void fireClosed() {
        if (myExternalListeners == null) return;
        for (CommonActionsPanel.Listener listener : myExternalListeners) {
            listener.doRemove();
        }
    }
    static final class PhotoFormatPickerDialog extends DialogWrapper {

        private PhotoFormatPicker myPhotoFormatPicker;

        PhotoFormatPickerDialog(@NotNull Component parent,
                                @NlsContexts.DialogTitle String caption) {
            super(parent, true);
            setTitle(caption);
            setResizable(false);
            super.init();
        }

        @Override
        public void show() {
            super.show();
            myPhotoFormatPicker.fireClosed();
        }

        @Override
        protected @NotNull JComponent createCenterPanel() {
            if (myPhotoFormatPicker == null) {
                myPhotoFormatPicker = new PhotoFormatPicker(null);
            }
            return myPhotoFormatPicker;
        }

        @Override
        protected void doOKAction() {
            super.doOKAction();
        }

        @Override
        public void doCancelAction() {
            super.doCancelAction();
        }
    }
}
