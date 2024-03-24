package org.robotgame.gui.buildingInternalFrame;

import org.robotgame.gui.LocalizationManager;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public abstract class AbstractWindow extends JInternalFrame {

    public AbstractWindow(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(title, resizable, closable, maximizable, iconifiable);
        setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        AbstractWindow.this,
                        LocalizationManager.getString("window.closing.message"),
                        LocalizationManager.getString("confirmation"),
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }

    protected abstract void closeWindow();

    @Override
    public void dispose() {
        closeWindow();
        super.dispose();
    }
}
