package org.robotgame.Serialization;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultProfileManager implements ProfileManager {
    private static final String FILE_PATH = "profiles/my_profile.dat";
    private static final Object lock = new Object();

    @Override
    public void saveProfiles(List<Object> profiles) throws IOException {
        synchronized (lock) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(
                    new FileOutputStream(FILE_PATH))) {
                for (Object profile : profiles) {
                    outputStream.writeObject(profile);
                }
            }
        }
    }

    @Override
    public List<Object> loadProfiles() throws IOException, ClassNotFoundException {
        synchronized (lock) {
            List<Object> profiles = new ArrayList<>();
            try (ObjectInputStream inputStream = new ObjectInputStream(
                    new FileInputStream(FILE_PATH))) {
                while (true) {
                    try {
                        Object profile = inputStream.readObject();
                        profiles.add(profile);
                    } catch (EOFException e) {
                        break;
                    }
                }
            }
            return profiles;
        }
    }
}
