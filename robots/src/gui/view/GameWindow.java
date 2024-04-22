package gui.view;

import gui.viewmodel.ViewModel;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JInternalFrame {
        private final int gameWindowWidth; // размеры окна игры
        private final int gameWindowHeight;
    public GameWindow(View view, ViewModel viewModel, Dimension screenSize)
    {
        super("Игровое поле", false, true, true, true);
        this.gameWindowWidth = viewModel.getGameWindowWidth();
        this.gameWindowHeight = viewModel.getGameWindowHeight();
        setLocation((screenSize.width-gameWindowWidth)/2,(screenSize.height-gameWindowHeight)/2);
        //gameWindow.setSize(gw_width, gw_height);
        //gameWindow.setMinimumSize(new Dimension(gw_width, gw_height));
        setPreferredSize(new Dimension(gameWindowWidth, gameWindowHeight));

        JPanel panelView = new JPanel(new BorderLayout());
        panelView.setPreferredSize(new Dimension(gameWindowWidth, gameWindowHeight));
        panelView.add(view, BorderLayout.CENTER);
        panelView.setVisible(true);
        getContentPane().add(panelView);

        JPanel panelViewModel = new JPanel(new BorderLayout());
        //panelView.setPreferredSize(new Dimension(gameWindowWidth, gameWindowHeight));
        panelViewModel.add(viewModel, BorderLayout.EAST);
        //panelViewModel.setVisible(true);
        getContentPane().add(panelViewModel);

        pack();
    }
}
