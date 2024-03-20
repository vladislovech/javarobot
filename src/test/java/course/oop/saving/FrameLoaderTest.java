package course.oop.saving;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Тестирует класс course.oop.saving.FrameLoader
 */
public class FrameLoaderTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * Проверяет, что корректно загружается состояние окна, если оно
     * было сохранено до.
     */
    @Test
    public void testCorrectLoading() {
        FrameSaver fs = new FrameSaver();
        fs.setConfilename("tmp.conf");
        try {
            fs.setSaveLocation(tempFolder.newFolder("Config"));
        } catch (IOException e) {
            Assert.assertTrue(false);
        }

        SaveableStub stub = new SaveableStub(new FrameConfig(
            new Pair(1, 2),
            new Pair(3, 4),
            false), null, "test");
        fs.addSaveableFrame(stub);

        try {
            fs.save();
        } catch (SaveException e) {
            Assert.assertTrue(false);
        }

        FrameLoader fl = new FrameLoader();
        fl.setSaveLocation(fs.getSaveLocation());
        fl.setConfilename(fs.getConfilename());

        try {
            fl.loadStates();
        } catch (LoadException e) {
            Assert.assertTrue(false);
        }

        try {
            fl.loadFrame(stub);
        } catch (LoadException e) {
            Assert.assertTrue(false);
        }

        Assert.assertFalse(stub.saved == stub.loaded);
        Assert.assertEquals(stub.saved, stub.loaded);
    }
}
