package course.oop.saving;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Тестирует класс course.oop.saving.FrameSaver
 */
public class FrameSaverTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * Проверяет, что корректно сохраняется внутренний map
     */
    @Test
    public void testCorrectSaving() {
        FrameSaver fs = new FrameSaver();
        fs.setConfilename("tmp.conf");
        try {
            fs.setSaveLocation(tempFolder.newFolder("Config"));
        } catch (IOException e) {
            Assert.assertTrue(false);
        }

        fs.addSaveableFrame(new Saveable() {
            @Override
            public FrameConfig getWindowConfig() {
                return new FrameConfig(
                        new Pair(1, 2),
                        new Pair(3, 4),
                        false);
            }

            @Override
            public String getFrameId() {
                return "tmp_w";
            }

            @Override
            public void loadConfig(FrameConfig frameConfig) {

            }
        });
        try {
            fs.save();
        } catch (SaveException e) {
            Assert.assertTrue(false);
        }

        try {
            InputStream is = new FileInputStream(new File(fs.getSaveLocation(), fs.getConfilename()));
            ObjectInputStream ois = new ObjectInputStream(is);
            try {
                Map<String, FrameConfig> actual = (HashMap<String, FrameConfig>) ois.readObject();
                FrameConfig fc = new FrameConfig(new Pair(1, 2),
                        new Pair(3, 4),
                        false);
                Assert.assertEquals(fc, actual.get("tmp_w"));
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
}