package com.tj.queuebasedplanning.panels;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import com.tj.queuebasedplanning.windows.MainWindow;

public class SuccessfullyAddedItemPanel extends JPanel {

	private CardLayout parentContentPaneLayout;

	public SuccessfullyAddedItemPanel(String item, MainWindow parent) {
		//From parent window
		parentContentPaneLayout = parent.getContentPaneLayout();

		//Set up the panel
		setBounds(100, 100, 960, 540);
		setBorder(new EmptyBorder(5, 5, 5, 5));

		//Set up layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0};
		setLayout(gbl_contentPane);

		//Set up components
		JTextPane messageTextPane = new JTextPane();
		messageTextPane.setText("The item \"" + item + "\" has been successfully added!");

		GridBagConstraints gbc_messageTextPane = new GridBagConstraints();
		gbc_messageTextPane.gridwidth = 2;
		gbc_messageTextPane.insets = new Insets(0, 0, 5, 5);
		gbc_messageTextPane.gridx = 0;
		gbc_messageTextPane.gridy = 0;
		add(messageTextPane, gbc_messageTextPane);

		JButton addButton = new JButton("Add another");
		GridBagConstraints gbc_addButton = new GridBagConstraints();
		gbc_addButton.weighty = 1.0;
		gbc_addButton.weightx = 1.0;
		gbc_addButton.gridx = 0;
		gbc_addButton.gridy = 1;
		add(addButton, gbc_addButton);

		addButton.addActionListener((ActionEvent e) -> {
			parentContentPaneLayout.show(parent.getContentPane(), "Add Item Panel");
			parent.setContentPaneLayout(parentContentPaneLayout);
		});

		JButton backToQueueButton = new JButton("Back to Queue");
		GridBagConstraints gbc_backToQueueButton = new GridBagConstraints();
		gbc_backToQueueButton.weighty = 1.0;
		gbc_backToQueueButton.weightx = 1.0;
		gbc_backToQueueButton.gridx = 1;
		gbc_backToQueueButton.gridy = 1;
		add(backToQueueButton, gbc_backToQueueButton);

		backToQueueButton.addActionListener((ActionEvent e) -> {
			parentContentPaneLayout.show(parent.getContentPane(), "Queue Panel");
			parent.setContentPaneLayout(parentContentPaneLayout);
		});
	}
}
