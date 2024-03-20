package course.oop.saving;

/**
 * Объект-заглушка для тестирования
 */
public class SaveableStub implements Saveable{
    public FrameConfig saved;
    public FrameConfig loaded;
    public String id;

    public SaveableStub(FrameConfig saved, FrameConfig loaded, String id) {
        this.saved = saved;
        this.loaded = loaded;
        this.id = id;
    }

    @Override
    public FrameConfig getWindowConfig() {
        return saved;
    }

    @Override
    public String getFrameId() {
        return id;
    }

    @Override
    public void loadConfig(FrameConfig config) {
        loaded = config;
    }
}