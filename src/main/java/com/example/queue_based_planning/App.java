package com.example.queue_based_planning;

import java.awt.EventQueue;

import com.example.queue_based_planning.Windows.MainWindow;

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