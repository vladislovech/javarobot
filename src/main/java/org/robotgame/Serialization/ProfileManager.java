package org.robotgame.Serialization;

import java.io.IOException;
import java.util.List;

public interface ProfileManager {
    void saveProfiles(List<Object> profiles) throws IOException;
    List<Object> loadProfiles() throws IOException, ClassNotFoundException;
}
