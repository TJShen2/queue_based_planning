package com.tj.queuebasedplanning;

import java.awt.EventQueue;

import com.tj.queuebasedplanning.windows.MainWindow;

public class App {
    /**
	 * Launch the application.
	 */
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
