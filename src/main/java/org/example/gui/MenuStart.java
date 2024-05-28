package gui;

import javax.swing.*;
import java.util.Objects;

public class MenuStart {
    public static int whatProfile(){
        Object[] options = {"save",
                "create"};
        return JOptionPane.showOptionDialog(null,
                "select",
                "confirmation",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }
    public static String createProfile(){
        JOptionPane newProfile = new JOptionPane("Ok", JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION);

        newProfile.setWantsInput(true);
        JDialog dialog = newProfile.createDialog(null);

        newProfile.selectInitialValue();
        dialog.show();
        dialog.dispose();

        Object result2 = newProfile.getInputValue();

        if (result2 == JOptionPane.UNINITIALIZED_VALUE) {
            return null;
        }
        else if (Objects.equals(result2.toString(), "")){
            return createProfile();
        }
        else{
            return result2.toString();
        }
    }
}
