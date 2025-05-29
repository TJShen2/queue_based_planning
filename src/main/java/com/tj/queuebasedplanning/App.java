package com.tj.queuebasedplanning;

import java.awt.EventQueue;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.tj.queuebasedplanning.windows.MainWindow;

public class App {
	// TODO: Keep copies of previous versions of user data in case of incorrect manipulation.
	public static final String APP_NAME = "queue-based-planning";
	public static final Path DATA_DIR = getDataDir();

	/**
	 * Returns the path to the data directory based on the operating system.
	 *
	 * @return Path to the data directory.
	 */
	private static Path getDataDir() {
		String os = System.getProperty("os.name");
		String dir = null;

		if (os.contains("Windows")) {
			dir = System.getenv("APPDATA");
		} else if (os.contains("Mac OS")) {
			dir = System.getProperty("user.home") + "/Library/Application Support";
		} else if (os.contains("Linux")) {
			dir = System.getenv("XDG_DATA_HOME");
		}
		return Path.of((dir == null ? System.getProperty("user.home") : dir) + "/" + App.APP_NAME + "/data");
	}

    /**
	 * Launch the application.
	 */
    public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				if (!Files.exists(DATA_DIR)) {
					Files.createDirectories(DATA_DIR);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Failed to create data directory: " + DATA_DIR);
			}
			MainWindow window = new MainWindow();
			window.setVisible(true);
		});
	}
}
