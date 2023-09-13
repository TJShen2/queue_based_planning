package com.example.queuebasedplanning.Panels;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
//import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JList;

import com.example.queuebasedplanning.QueueItem;
import com.example.queuebasedplanning.Windows.MainWindow;

public class QueuePanel extends JPanel {

	//From parent window
	private MainWindow parent;
	private JFrame frame;
	private JPanel contentPane;
	private LinkedHashMap<String, QueueItem> queueItems;
	private LinkedHashMap<String, QueueItem> archivedItems;
	private CardLayout contentPaneLayout;
	private EditItemPanel editItemPanel;

	private JPanel queueItemsPanel;
	private JButton addItemButton;

	private JScrollPane queueLabelScrollPane;

	private JButton removeSelectedItemButton;
	private JButton archiveSelectedItemButton;
	private JButton editSelectedItemButton;
	private JPanel saveAndExitPanel;
	private JButton saveAndExitButton;
	private JButton moveSelectedItemToBackOfQueueButton;
	private JList<String> queueItemsList;

	public QueuePanel(MainWindow parent) {
		this.parent = parent;
		frame = parent.getFrame();
		contentPane = parent.getContentPane();
		contentPaneLayout = parent.getContentPaneLayout();
		editItemPanel = parent.getEditItemPanel();
		queueItems = parent.getQueueItems();
		archivedItems = parent.getArchivedItems();

		String[] queueItemNames = new String[queueItems.size()];
		int i = 0;
		for (Map.Entry<String, QueueItem> item : queueItems.entrySet()) {
			queueItemNames[i] = item.getKey();
			i++;
		}

		//Set up the panel
		setBackground(new Color(255, 246, 187));
		setBounds(100, 100, 960, 540);
		setBorder(new EmptyBorder(5, 5, 5, 5));

		//Set up components
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0};
		setLayout(gbl_contentPane);
		
		JPanel titleLabelPanel = new JPanel();
		GridBagConstraints gbc_titleLabelPanel = new GridBagConstraints();
		gbc_titleLabelPanel.gridwidth = 3;
		gbc_titleLabelPanel.gridy = 1;
		gbc_titleLabelPanel.insets = new Insets(0, 0, 5, 5);
		gbc_titleLabelPanel.gridx = 1;
		add(titleLabelPanel, gbc_titleLabelPanel);
		titleLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
		
		JLabel titleLabel = new JLabel("Main Queue");
		titleLabelPanel.add(titleLabel);
		
		queueLabelScrollPane = new JScrollPane();
		GridBagConstraints gbc_queueLabelScrollPane = new GridBagConstraints();
		gbc_queueLabelScrollPane.fill = GridBagConstraints.BOTH;
		gbc_queueLabelScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_queueLabelScrollPane.gridx = 1;
		gbc_queueLabelScrollPane.gridy = 3;
		add(queueLabelScrollPane, gbc_queueLabelScrollPane);
		
		queueItemsPanel = new JPanel();
		queueItemsPanel.setLayout(new BorderLayout(0, 0));
		queueLabelScrollPane.setViewportView(queueItemsPanel);
		
		JPanel actionButtonPanel = new JPanel();
		GridBagConstraints gbc_actionButtonPanel = new GridBagConstraints();
		gbc_actionButtonPanel.insets = new Insets(0, 0, 5, 5);
		gbc_actionButtonPanel.gridx = 3;
		gbc_actionButtonPanel.gridy = 3;
		add(actionButtonPanel, gbc_actionButtonPanel);
		actionButtonPanel.setLayout(new BoxLayout(actionButtonPanel, BoxLayout.Y_AXIS));
		
		addItemButton = new JButton("New Item");
		addItemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		actionButtonPanel.add(addItemButton);
		
		archiveSelectedItemButton = new JButton("Archive Selected Item");
		archiveSelectedItemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		actionButtonPanel.add(archiveSelectedItemButton);
		archiveSelectedItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)  {
				queueItems = parent.getQueueItems();
				performItemOperation("archive");
				parent.setQueueItems(queueItems);
			}
		});
		
		moveSelectedItemToBackOfQueueButton = new JButton("Move Selected Item to Back of Queue");
		moveSelectedItemToBackOfQueueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		actionButtonPanel.add(moveSelectedItemToBackOfQueueButton);
		moveSelectedItemToBackOfQueueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				queueItems = parent.getQueueItems();
				performItemOperation("move");
				parent.setQueueItems(queueItems);
			}
		});
		
		editSelectedItemButton = new JButton("Edit Selected Item");
		editSelectedItemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		actionButtonPanel.add(editSelectedItemButton);
		
		removeSelectedItemButton = new JButton("Remove Selected Item");
		removeSelectedItemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		actionButtonPanel.add(removeSelectedItemButton);
		
		saveAndExitPanel = new JPanel();
		GridBagConstraints gbc_saveAndExitPanel = new GridBagConstraints();
		gbc_saveAndExitPanel.anchor = GridBagConstraints.SOUTHEAST;
		gbc_saveAndExitPanel.gridx = 3;
		gbc_saveAndExitPanel.gridy = 5;
		add(saveAndExitPanel, gbc_saveAndExitPanel);
		
		saveAndExitButton = new JButton("Save & Exit");
		saveAndExitPanel.add(saveAndExitButton);

		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPaneLayout.show(parent.getContentPane(), "Add Item Panel");
				parent.setContentPaneLayout(contentPaneLayout);
			}
		});
		removeSelectedItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				queueItems = parent.getQueueItems();
				performItemOperation("remove");
				parent.setQueueItems(queueItems);
			}
		});
		editSelectedItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				queueItems = parent.getQueueItems();
				editItemPanel = parent.getEditItemPanel();

				String firstSelectedItem = queueItemsList.getSelectedValue();
				int colonIndex = firstSelectedItem.indexOf(':');
				firstSelectedItem = firstSelectedItem.substring(0, colonIndex);
				QueueItem selectedValue = queueItems.get(firstSelectedItem);

				try {
					editItemPanel.setupEditItemPanel(selectedValue);
					String editItemPanelUneditedItem = editItemPanel.getUneditedItem();
					editItemPanelUneditedItem = firstSelectedItem;
					editItemPanel.setUneditedItem(editItemPanelUneditedItem);
					contentPaneLayout.show(contentPane, "Edit Item Panel");
				} catch (NullPointerException e) {
					System.out.println("No item is selected");
				}

				parent.setEditItemPanel(editItemPanel);
				parent.setContentPaneLayout(contentPaneLayout);
			}
		});
		saveAndExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});

		updateQueueList();
	}
	public void updateQueueList() {
		queueItemsPanel.removeAll();
		queueItems = parent.getQueueItems();
		DefaultListModel<String> queueItemsListModel = new DefaultListModel<String>();

		for (Map.Entry<String,QueueItem> itemEntry : queueItems.entrySet()) {
			//JEditorPane queueLabel = new JEditorPane("text.rtf.RTFEditorKit", item.name + ":\n" + item.details);
			String queueLabelText = "";
			QueueItem item = itemEntry.getValue();
			queueLabelText += item.name + ":\n" + item.details;
			queueItemsListModel.addElement(queueLabelText);
		}
		queueItemsList = new JList<String>(queueItemsListModel);
		queueItemsPanel.add(queueItemsList);
		queueItemsPanel.validate();
	}
	private void performItemOperation(String mode) {
		//String itemToRemove = (String) parent.getQueuePanel().itemSelectionComboBox.getSelectedItem();
		List<String> selectedItems = queueItemsList.getSelectedValuesList();

		for (String item : selectedItems) {
			int colonIndex = item.indexOf(':');
			item = item.substring(0, colonIndex);
			QueueItem removedItem = queueItems.remove(item);

			if (mode == "move") {
				queueItems.put(item, removedItem);
			} else if (mode == "archive") {
				archivedItems.put(item, removedItem);
			} else if (mode == "remove") {
				assert true;
			}
		}
		updateQueueList();
	}
}