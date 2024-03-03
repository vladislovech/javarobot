package course.oop.saving;

import org.junit.Test;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;

public class WindowConfigsIOTest {

    /**
     * Чистит директорию с данными приложения
     */
    @BeforeClass
    public static void clearAppFolder() {
        String appDirPath = System.getProperty("user.home")
                + File.separator + "Robots";
        File appDir = new File(appDirPath);
        try {
            FileUtils.deleteDirectory(appDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверяет, что объект класса WindowConfigsIO - singleton.
     */
    @Test
    public void testIsSingleton() {
        try {
            WindowConfigsIO o1 = WindowConfigsIO.getInstance();
            WindowConfigsIO o2 = WindowConfigsIO.getInstance();
            Assert.assertTrue(o1 == o2);
        } catch (LoadException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверяет, что создается правильная структура директорий, когда
     * их не было.
     */
    @Test
    public void testStructureCreating() {
        String confFilePath = System.getProperty("user.home")
                + File.separator + "Robots"
                + File.separator + "config"
                + File.separator + "windowStates.conf";
        File conFile = new File(confFilePath);
        try {
            System.out.println(conFile.getParentFile().getParentFile());
            FileUtils.deleteDirectory(conFile.getParentFile().getParentFile());
            WindowConfigsIO.getInstance();
            Assert.assertTrue(conFile.exists());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LoadException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверяет, что корректно работает {@link WindowConfigIO#save()} и
     * {@link WindowConfigIO#load()}
     */
    @Test
    public void testSaveAndLoad() {
        WindowConfig wc = new WindowConfig(new Dimension(300, 200), new Point(10, 20), false);
        try {
            WindowConfigsIO wio = WindowConfigsIO.getInstance();
            wio.save("my window", wc);
            WindowConfig loaded = wio.load("my window");
            System.out.println(loaded.toStringFormatted());
            Assert.assertTrue(wc != loaded && wc.equals(loaded));
        } catch (LoadException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверяет корректность работы метода {@link WindowConfigIO#flush()}
     */
    @Test
    public void testFlush() {
        String confFilePath = System.getProperty("user.home")
                + File.separator + "Robots"
                + File.separator + "config"
                + File.separator + "windowStates.conf";
        File conFile = new File(confFilePath);
        try {
            FileUtils.delete(conFile);
            WindowConfigsIO wio = WindowConfigsIO.getInstance();
            wio.save("test window", new WindowConfig(new Dimension(300, 200), new Point(10, 20), false));
            wio.flush();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(conFile));
            try {
                Map<String, WindowConfig> act = (HashMap<String, WindowConfig>) ois.readObject();
                Assert.assertEquals(new WindowConfig(new Dimension(300, 200), new Point(10, 20), false),
                        act.get("test window"));
            } finally {
                ois.close();
            }
        } catch (LoadException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
