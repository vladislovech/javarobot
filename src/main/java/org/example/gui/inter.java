package gui;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.util.Locale;

import static gui.MenuBar.getLocaleString;

public abstract class inter extends JInternalFrame{
    public abstract void updateWindow();
    public inter(String str, boolean b, boolean b1, boolean b2, boolean b3){
        super(str, b, b1, b2, b3);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameListener() {

            @Override
            public void internalFrameOpened(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameClosing(InternalFrameEvent event) {
                Object[] options = {getLocaleString("yes", Locale.getDefault()), getLocaleString( "no", Locale.getDefault())};
                int n = JOptionPane
                        .showOptionDialog(event.getInternalFrame(), getLocaleString("closeWindow", Locale.getDefault()),
                                getLocaleString("accept", Locale.getDefault()), JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n == 0) {
                    event.getInternalFrame().setVisible(false);
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
            }

            @Override
            public void internalFrameClosed(InternalFrameEvent e) {

            }

            public void internalFrameIconified(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameDeiconified(InternalFrameEvent e) {

            }


            @Override
            public void internalFrameActivated(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {

            }

        });
    }


}
