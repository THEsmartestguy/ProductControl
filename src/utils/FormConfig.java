package utils;

import javax.swing.*;
import java.awt.*;

public class FormConfig {

    public static int setParams(JFrame frame, String title, int width, int height, int closeOperation){

        frame.setTitle(title);
        frame.setMinimumSize(new Dimension(width,height));
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("src/school_logo.png").getImage());
        frame.setDefaultCloseOperation(closeOperation);

        return 0;
    }

}
