/*
 * Copyright (c) 2025 by Tang Kun. All rights reserved.
 */

package person.tangkun.photoformatter;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;

public class HelloWorldAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Notifications.Bus.notify(
                new Notification("Print", "", "Hello world!", NotificationType.INFORMATION),
                e.getProject());
        Project project = e.getData(PlatformDataKeys.PROJECT);
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        String classPath = null;
        if (psiFile != null) {
            classPath = psiFile.getVirtualFile().getPath();
        }
        String title = "Hello World!";
        Messages.showMessageDialog(project, classPath, title, Messages.getInformationIcon());

        if (classPath != null && classPath.endsWith(".jpg")) {
            FormatConvertUtil.jpg2webp(classPath, classPath.replace(".jpg", ".webp"));
        }
    }
}