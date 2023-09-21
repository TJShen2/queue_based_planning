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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
//import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JList;

import com.example.queuebasedplanning.QueueItem;
import com.example.queuebasedplanning.Windows.MainWindow;
import javax.swing.JCheckBox;

public class QueuePanel extends JPanel {

	private Boolean archiveMode; //Whether the displayed items are the archived items
	public Boolean getArchiveMode() { return archiveMode; }
	public void setArchivedMode(Boolean value) { archiveMode = value; }

	private LinkedHashMap<String, QueueItem> displayedItems; //The items (current or archive) that are currently being displayed

	//Swing/AWT components
	//From parent window
	private MainWindow parent;
	private JFrame frame;
	private JPanel contentPane;
	private LinkedHashMap<String, QueueItem> currentQueueItems;
	private LinkedHashMap<String, QueueItem> archivedItems;
	private CardLayout contentPaneLayout;
	private EditItemPanel editItemPanel;

	//Window components
	//Panels
	private JPanel queueItemsPanel;
	private JPanel savePanel;
	
	//Scroll panes
	private JScrollPane queueLabelScrollPane;

	//Buttons
	private JButton addItemButton;
	private JButton removeSelectedItemButton;
	private JButton archiveSelectedItemButton;
	private JButton editSelectedItemButton;
	private JButton exitButton;
	private JButton moveSelectedItemToBackOfQueueButton;
	private JButton saveButton;

	//Lists
	private JList<String> queueItemsList;
	private ListSelectionModel queueItemsListSelectionModel;

	//Check boxes
	private JCheckBox archiveModeCheckBox;

	public QueuePanel(MainWindow parent) {
		this.parent = parent;
		frame = parent.getFrame();
		contentPane = parent.getContentPane();
		contentPaneLayout = parent.getContentPaneLayout();
		editItemPanel = parent.getEditItemPanel();
		currentQueueItems = parent.getCurrentQueueItems();
		archivedItems = parent.getArchivedItems();

		archiveMode = false;

		String[] queueItemNames = new String[currentQueueItems.size()];
		int i = 0;
		for (Map.Entry<String, QueueItem> item : currentQueueItems.entrySet()) {
			queueItemNames[i] = item.getKey();
			i++;
		}

		//Set up the main panel
		setBackground(new Color(255, 246, 187));
		setBounds(100, 100, 960, 540);
		setBorder(new EmptyBorder(5, 5, 5, 5));

		//Set up layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0};
		setLayout(gbl_contentPane);
		
		//Set up scroll panes
		queueLabelScrollPane = new JScrollPane();
		GridBagConstraints gbc_queueLabelScrollPane = new GridBagConstraints();
		gbc_queueLabelScrollPane.fill = GridBagConstraints.BOTH;
		gbc_queueLabelScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_queueLabelScrollPane.gridx = 1;
		gbc_queueLabelScrollPane.gridy = 3;
		
		//Set up panels
		JPanel titleLabelPanel = new JPanel();
		GridBagConstraints gbc_titleLabelPanel = new GridBagConstraints();
		gbc_titleLabelPanel.gridwidth = 3;
		gbc_titleLabelPanel.gridy = 1;
		gbc_titleLabelPanel.insets = new Insets(0, 0, 5, 5);
		gbc_titleLabelPanel.gridx = 1;

		titleLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
		
		queueItemsPanel = new JPanel();
		queueItemsPanel.setLayout(new BorderLayout(0, 0));
		queueLabelScrollPane.setViewportView(queueItemsPanel);
		
		JPanel actionButtonPanel = new JPanel();
		GridBagConstraints gbc_actionButtonPanel = new GridBagConstraints();
		gbc_actionButtonPanel.insets = new Insets(0, 0, 5, 5);
		gbc_actionButtonPanel.gridx = 3;
		gbc_actionButtonPanel.gridy = 3;
		
		actionButtonPanel.setLayout(new BoxLayout(actionButtonPanel, BoxLayout.Y_AXIS));

		savePanel = new JPanel();
		GridBagConstraints gbc_saveAndExitPanel = new GridBagConstraints();
		gbc_saveAndExitPanel.anchor = GridBagConstraints.SOUTHEAST;
		gbc_saveAndExitPanel.gridx = 3;
		gbc_saveAndExitPanel.gridy = 5;
		
		//Set up labels
		JLabel titleLabel = new JLabel("Main Queue");
		titleLabelPanel.add(titleLabel);

		//Set up buttons
		addItemButton = new JButton("New Item");
		addItemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPaneLayout.show(parent.getContentPane(), "Add Item Panel");
				parent.setContentPaneLayout(contentPaneLayout);
			}
		});
		
		archiveSelectedItemButton = new JButton();
		archiveSelectedItemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		archiveSelectedItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)  {
				currentQueueItems = parent.getCurrentQueueItems();
				performItemOperation("archive");
				parent.setCurrentQueueItems(currentQueueItems);

				updateButtonStates();
			}
		});
		
		moveSelectedItemToBackOfQueueButton = new JButton("Move Selected Item to Back of Queue");
		moveSelectedItemToBackOfQueueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		moveSelectedItemToBackOfQueueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentQueueItems = parent.getCurrentQueueItems();
				performItemOperation("move");
				parent.setCurrentQueueItems(currentQueueItems);

				updateButtonStates();
			}
		});
		
		editSelectedItemButton = new JButton("Edit Selected Item");
		editSelectedItemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		editSelectedItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String firstSelectedItem = queueItemsList.getSelectedValue();
				int colonIndex = firstSelectedItem.indexOf(":\n");
				firstSelectedItem = firstSelectedItem.substring(0, colonIndex);

				QueueItem selectedValue;

				if (archiveMode) {
					archivedItems = parent.getArchivedItems();
					selectedValue = archivedItems.get(firstSelectedItem);
				} else {
					currentQueueItems = parent.getCurrentQueueItems();
					selectedValue = currentQueueItems.get(firstSelectedItem);
				}

				try {
					editItemPanel = parent.getEditItemPanel();
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
		
		removeSelectedItemButton = new JButton("Remove Selected Item");
		removeSelectedItemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		removeSelectedItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentQueueItems = parent.getCurrentQueueItems();
				performItemOperation("remove");
				parent.setCurrentQueueItems(currentQueueItems);

				updateButtonStates();
			}
		});
			
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.saveChanges();
			}
		});
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		//Set up check boxes
		archiveModeCheckBox = new JCheckBox("Archive Mode");
		archiveModeCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		archiveModeCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				archiveMode = archiveModeCheckBox.isSelected();
				updateQueueList();
				updateButtonStates();
			}
		});

		//Add buttons to panels
		actionButtonPanel.add(archiveModeCheckBox);
		actionButtonPanel.add(addItemButton);
		actionButtonPanel.add(archiveSelectedItemButton);
		actionButtonPanel.add(moveSelectedItemToBackOfQueueButton);
		actionButtonPanel.add(editSelectedItemButton);
		actionButtonPanel.add(removeSelectedItemButton);

		savePanel.add(saveButton);
		savePanel.add(exitButton);

		//Add top-level containers
		add(queueLabelScrollPane, gbc_queueLabelScrollPane);
		add(titleLabelPanel, gbc_titleLabelPanel);
		add(actionButtonPanel, gbc_actionButtonPanel);
		add(savePanel, gbc_saveAndExitPanel);

		updateQueueList();
		updateButtonStates();
	}
	public void updateQueueList() {
		queueItemsPanel.removeAll();

		currentQueueItems = parent.getCurrentQueueItems();
		archivedItems = parent.getArchivedItems();
		
		if (archiveMode) {
			displayedItems = archivedItems;
		} else {
			displayedItems = currentQueueItems;
		}

		DefaultListModel<String> queueItemsListModel = new DefaultListModel<String>();

		for (Map.Entry<String,QueueItem> itemEntry : displayedItems.entrySet()) {
			//JEditorPane queueLabel = new JEditorPane("text.rtf.RTFEditorKit", item.name + ":\n" + item.details);
			String queueLabelText = "";
			QueueItem item = itemEntry.getValue();
			queueLabelText += item.name + ":\n" + item.details;
			queueItemsListModel.addElement(queueLabelText);
		}
		queueItemsList = new JList<String>(queueItemsListModel);
		queueItemsListSelectionModel = queueItemsList.getSelectionModel();
		queueItemsListSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				updateButtonStates();
			}
		});

		queueItemsPanel.add(queueItemsList);
		queueItemsPanel.validate();
	}
	private void performItemOperation(String mode) {
		//String itemToRemove = (String) parent.getQueuePanel().itemSelectionComboBox.getSelectedItem();
		List<String> selectedItems = queueItemsList.getSelectedValuesList();

		for (String item : selectedItems) {
			int colonIndex = item.indexOf(":\n");
			item = item.substring(0, colonIndex);

			//If mode is "move", add the removed item back to the bottom of the queue
			//If mode is "archive", add the removed item to the archivedItems hashmap
			//If mode is "remove", do not add the item back to any hashmaps
			
			if (archiveMode) {
				QueueItem removedItem = archivedItems.remove(item);
				if (mode == "move") {
					archivedItems.put(item, removedItem);
				} else if (mode == "archive") {
					currentQueueItems.put(item, removedItem);
				}
			} else {
				QueueItem removedItem = currentQueueItems.remove(item);
				if (mode == "move") {
					currentQueueItems.put(item, removedItem);
				} else if (mode == "archive") {
					archivedItems.put(item, removedItem);
				}
			}
		}
		updateQueueList();
	}
	public void updateButtonStates() {
		//Modify which buttons are enabled based on the number of items selected and whether archive mode is activated
		int itemsSelectedCount = queueItemsList.getSelectedValuesList().size();

		if (itemsSelectedCount == 1) {
			archiveSelectedItemButton.setEnabled(true);
			moveSelectedItemToBackOfQueueButton.setEnabled(true);
			removeSelectedItemButton.setEnabled(true);
			editSelectedItemButton.setEnabled(true);
		} else if (itemsSelectedCount > 1) {
			archiveSelectedItemButton.setEnabled(true);
			moveSelectedItemToBackOfQueueButton.setEnabled(true);
			removeSelectedItemButton.setEnabled(true);
			editSelectedItemButton.setEnabled(false);
		} else {
			archiveSelectedItemButton.setEnabled(false);
			moveSelectedItemToBackOfQueueButton.setEnabled(false);
			removeSelectedItemButton.setEnabled(false);
			editSelectedItemButton.setEnabled(false);
		}

		if (archiveMode) {
			archiveSelectedItemButton.setText("Restore Selected Item");
			addItemButton.setEnabled(false);
		} else {
			archiveSelectedItemButton.setText("Archive Selected Item");
			addItemButton.setEnabled(true);
		}
	}
}