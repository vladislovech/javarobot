package course.oop.saving;

import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import course.oop.gui.GameWindow;
import course.oop.gui.MainApplicationFrame;

/**
 * Тестирует класс course.oop.saving.FrameStatesManager
 */
public class FrameStatesManagerTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * Проверяет, что корректно сохраняется внутренний map JFrame
     * и JInternalFrame с реализованными Saveable
     */
    @Test
    public void testCorrectSaving() {
        FrameStatesManager frameStatesManager = new FrameStatesManager();
        try {
            frameStatesManager.setSaveLocation(tempFolder.newFile());
        } catch (IOException e) {
            Assert.assertTrue(false);
        }

        MainApplicationFrame mainApplicationFrame = new MainApplicationFrame();
        mainApplicationFrame.setSize(new Dimension(1, 2));
        mainApplicationFrame.setLocation(new Point(3, 4));

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(new Dimension(5, 6));
        gameWindow.setLocation(new Point(7, 8));
        try {
            gameWindow.setIcon(true);
        } catch (PropertyVetoException e) {
            Assert.assertTrue(false);
        }

        frameStatesManager.addSaveableFrame((Saveable) mainApplicationFrame);
        frameStatesManager.addSaveableFrame((Saveable) gameWindow);
        try {
            frameStatesManager.save();
        } catch (SaveException e) {
            Assert.assertTrue(false);
        }

        try {
            InputStream is = new FileInputStream(frameStatesManager.getSaveLocation());
            ObjectInputStream ois = new ObjectInputStream(is);
            try {
                HashMap<String, FrameConfig> actual = (HashMap<String, FrameConfig>) ois.readObject();
                Assert.assertEquals(
                        new FrameConfig(
                                new Pair(1, 2),
                                new Pair(3, 4),
                                false),
                        actual.get("main"));
                Assert.assertEquals(
                        new FrameConfig(
                                new Pair(5, 6),
                                new Pair(7, 8),
                                true),
                        actual.get("game"));
            } catch (Exception e) {
                Assert.assertTrue(false);
            } finally {
                ois.close();
                is.close();
            }
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    /**
     * Проверяет, что корректно загружаются сохраненные ранее состояния окон
     */
    @Test
    public void testCorrectLoading() {
        FrameStatesManager frameStatesManager = new FrameStatesManager();
        try {
            frameStatesManager.setSaveLocation(tempFolder.newFile());
        } catch (IOException e) {
            Assert.assertTrue(false);
        }

        File oldLocation = frameStatesManager.getSaveLocation();

        MainApplicationFrame mainApplicationFrame = new MainApplicationFrame();
        mainApplicationFrame.setSize(new Dimension(1, 2));
        mainApplicationFrame.setLocation(new Point(3, 4));

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(new Dimension(5, 6));
        gameWindow.setLocation(new Point(7, 8));
        try {
            gameWindow.setIcon(true);
        } catch (PropertyVetoException e) {
            Assert.assertTrue(false);
        }

        frameStatesManager.addSaveableFrame((Saveable) mainApplicationFrame);
        frameStatesManager.addSaveableFrame((Saveable) gameWindow);

        try {
            frameStatesManager.save();
        } catch (SaveException e) {
            Assert.assertTrue(false);
        }

        frameStatesManager = new FrameStatesManager();
        frameStatesManager.setSaveLocation(oldLocation);

        MainApplicationFrame mainApplicationFrameLoaded = new MainApplicationFrame();
        GameWindow gameWindowLoaded = new GameWindow();

        try {
            frameStatesManager.loadStates();
        } catch (LoadException e) {
            Assert.assertTrue(false);
        }

        try {
            frameStatesManager.loadFrame(mainApplicationFrameLoaded);
            frameStatesManager.loadFrame(gameWindowLoaded);
        } catch (LoadException e) {
            Assert.assertTrue(false);
        }

        Assert.assertEquals(mainApplicationFrame.getSize(), mainApplicationFrameLoaded.getSize());
        Assert.assertEquals(mainApplicationFrame.getLocation(), mainApplicationFrameLoaded.getLocation());
        Assert.assertEquals(gameWindow.getLocation(), gameWindowLoaded.getLocation());
        Assert.assertEquals(gameWindow.getLocation(), gameWindowLoaded.getLocation());
        Assert.assertEquals(gameWindow.isIcon(), gameWindow.isIcon());
    }
}
