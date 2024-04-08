package robots.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CashWriter {
	private String originalName;
	private String path;

	public CashWriter(String filename) {
		originalName = filename;
		setPath();
		System.out.println("\u001B[32m[WRITING]\u001B[0m " + filename);
	}

	private void setPath() throws IllegalArgumentException {
		path = CashReader.getPath();
	}

	private File getPath() throws IOException {
		File dir = new File(path);
		if (!dir.exists()) {
			boolean created = dir.mkdirs();
			if (!created) {
				throw new IOException("Could not create directory " + path);
			}
		}
		return dir;
	}

	private void createAllDirs() throws IOException {
		getPath();
		String[] dirs = originalName.split("[/\\\\]");
		StringBuilder createdPath = new StringBuilder(path);
		for (int i = 0; i < dirs.length - 1; i++) {
			createdPath.append("/").append(dirs[i]);
			File currentDir = new File(createdPath.toString());
			if (!currentDir.exists()) {
				if (!currentDir.mkdir()) {
					throw new IOException("Can't create this dir: " + createdPath);
				}
			}
		}
	}

	private BufferedWriter createWriter(boolean override) throws IllegalArgumentException, IOException {
		File file = new File(String.format("%s/%s", path, originalName));
		return new BufferedWriter(new FileWriter(file, override));
	}

	public void write(String massage, boolean override) throws IOException {
		BufferedWriter pw = createWriter(override);
		pw.write(massage);
		pw.flush();
		pw.close();
	}

	public void writeObject(Object object) throws IOException {
		createAllDirs();
		String filename = String.format("%s/%s", path, originalName);
		File file = new File(filename);
		if (!file.exists()) {
			boolean created = file.createNewFile();
			if (!created) {
				throw new IOException("Can't create " + file + " in " + path);
			}
		}
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filename);
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static private void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) { // some JVMs return null for empty dirs
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}

	static public boolean deleteFile(String name) {
		File file = new File(String.format("%s/%s", CashReader.getPath(), name));
		return file.delete();
	}

	static public void deleteSaveFolder() {
		deleteFolder(new File(String.format("%s/saves", CashReader.getPath())));
	}
}
