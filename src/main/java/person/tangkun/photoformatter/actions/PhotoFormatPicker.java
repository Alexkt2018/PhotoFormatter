/*
 * Copyright (c) 2025 by Tang Kun. All rights reserved.
 */

package person.tangkun.photoformatter.actions;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.ColorPicker;
import com.intellij.ui.ColorPickerListener;
import com.intellij.ui.CommonActionsPanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PhotoFormatPicker extends JPanel {
    private final List<? extends CommonActionsPanel.Listener> myExternalListeners;

    public PhotoFormatPicker(List<? extends CommonActionsPanel.Listener> listeners) {
        super();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
//        myColorWheelPanel = new ColorPicker.ColorWheelPanel(this, enableOpacity, opacityInPercent);
        setSize(300, 350);
        myExternalListeners = listeners;
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
    }
}
