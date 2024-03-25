package gui.view;

import gui.viewmodel.ViewModel;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JInternalFrame
{
    //private final GameVisualizer m_visualizer;
    private final ViewModel viewModel;
    public GameWindow()
    {
        super("Игровое поле", true, true, true, true);
        //m_visualizer = new GameVisualizer();
        viewModel = new ViewModel();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(viewModel, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
