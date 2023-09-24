package com.example.queuebasedplanning.Panels;

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
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class QueuePanel extends JPanel {

	private Boolean archiveMode; //Whether the displayed items are the archived items
	public Boolean getArchiveMode() { return archiveMode; }
	public void setArchivedMode(Boolean value) { archiveMode = value; }

	private List<QueueItem> displayedItems; //The items (current or archive) that are currently being displayed

	//Swing/AWT components
	//From parent window
	private MainWindow parent;
	private JFrame frame;
	private JPanel contentPane;
	private List<QueueItem> currentQueueItems;
	private List<QueueItem> archivedItems;
	private CardLayout contentPaneLayout;
	private EditItemPanel editItemPanel;

	//Window components
	//Panels
	private JPanel queueItemsPanel;
	private JPanel savePanel;
	private JPanel detailsDatePanel;
	
	//Scroll panes
	private JScrollPane queueLabelScrollPane;
	private JScrollPane detailsDateScrollPane;

	//Text panes
	private JTextPane detailsTextPane;
	private JTextPane lastEditedTextPane;

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

	//Labels
	private JLabel detailsLabel;
	private JLabel lastEditedLabel;

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
		for (QueueItem item : currentQueueItems) {
			queueItemNames[i] = item.getName();
			i++;
		}

		//Set up the main panel
		setBackground(new Color(255, 246, 187));
		setBounds(100, 100, 960, 540);
		setBorder(new EmptyBorder(5, 5, 5, 5));

		//Set up layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		setLayout(gbl_contentPane);
		
		//Set up scroll panes
		queueLabelScrollPane = new JScrollPane();
		GridBagConstraints gbc_queueLabelScrollPane = new GridBagConstraints();
		gbc_queueLabelScrollPane.gridheight = 2;
		gbc_queueLabelScrollPane.fill = GridBagConstraints.BOTH;
		gbc_queueLabelScrollPane.weighty = 1.0;
		gbc_queueLabelScrollPane.weightx = 1.0;
		gbc_queueLabelScrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_queueLabelScrollPane.gridx = 1;
		gbc_queueLabelScrollPane.gridy = 3;
		
		//Set up panels
		JPanel titleLabelPanel = new JPanel();
		GridBagConstraints gbc_titleLabelPanel = new GridBagConstraints();
		gbc_titleLabelPanel.gridwidth = 4;
		gbc_titleLabelPanel.gridy = 1;
		gbc_titleLabelPanel.insets = new Insets(5, 5, 5, 5);
		gbc_titleLabelPanel.gridx = 0;
		titleLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
		
		queueItemsPanel = new JPanel();
		queueItemsPanel.setLayout(new BorderLayout(0, 0));

		savePanel = new JPanel();
		GridBagConstraints gbc_saveAndExitPanel = new GridBagConstraints();
		gbc_saveAndExitPanel.insets = new Insets(5, 5, 0, 0);
		gbc_saveAndExitPanel.anchor = GridBagConstraints.SOUTHEAST;
		gbc_saveAndExitPanel.gridx = 3;
		gbc_saveAndExitPanel.gridy = 6;
		
		//Set up labels
		JLabel titleLabel = new JLabel("Main Queue");
			
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
		
		detailsDateScrollPane = new JScrollPane();
		GridBagConstraints gbc_detailsDateScrollPane = new GridBagConstraints();
		gbc_detailsDateScrollPane.weighty = 1.0;
		gbc_detailsDateScrollPane.fill = GridBagConstraints.BOTH;
		gbc_detailsDateScrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_detailsDateScrollPane.gridx = 3;
		gbc_detailsDateScrollPane.gridy = 3;
		
		JPanel actionButtonPanel = new JPanel();
		GridBagConstraints gbc_actionButtonPanel = new GridBagConstraints();
		gbc_actionButtonPanel.insets = new Insets(5, 5, 5, 0);
		gbc_actionButtonPanel.gridx = 3;
		gbc_actionButtonPanel.gridy = 4;
		
		actionButtonPanel.setLayout(new BoxLayout(actionButtonPanel, BoxLayout.Y_AXIS));

		detailsDatePanel = new JPanel();
		detailsDatePanel.setLayout(new BoxLayout(detailsDatePanel, BoxLayout.Y_AXIS));
		
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
				parent.saveChanges();

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
				parent.saveChanges();

				updateButtonStates();
			}
		});

		editSelectedItemButton = new JButton("Edit Selected Item");
		editSelectedItemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		editSelectedItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedIndex = queueItemsList.getSelectedIndex();

				QueueItem selectedValue;

				if (archiveMode) {
					archivedItems = parent.getArchivedItems();
					selectedValue = archivedItems.get(selectedIndex);
				} else {
					currentQueueItems = parent.getCurrentQueueItems();
					selectedValue = currentQueueItems.get(selectedIndex);
				}

				editItemPanel = parent.getEditItemPanel();
				editItemPanel.setupEditItemPanel(selectedIndex, selectedValue);
				contentPaneLayout.show(contentPane, "Edit Item Panel");

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
				parent.saveChanges();

				updateButtonStates();
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
		
		
		
		detailsLabel = new JLabel("Details:");
		detailsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		detailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		detailsTextPane = new JTextPane();
		
		lastEditedLabel = new JLabel("Last Edited:");
		lastEditedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lastEditedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		lastEditedTextPane = new JTextPane();

		//Add buttons to panels
		titleLabelPanel.add(titleLabel);

		savePanel.add(saveButton);
		savePanel.add(exitButton);

		actionButtonPanel.add(archiveModeCheckBox);
		actionButtonPanel.add(addItemButton);
		actionButtonPanel.add(archiveSelectedItemButton);
		actionButtonPanel.add(moveSelectedItemToBackOfQueueButton);
		actionButtonPanel.add(editSelectedItemButton);
		actionButtonPanel.add(removeSelectedItemButton);
		
		detailsDatePanel.add(detailsLabel);
		detailsDatePanel.add(detailsTextPane);
		detailsDatePanel.add(lastEditedLabel);
		detailsDatePanel.add(lastEditedTextPane);

		//Add panels to scroll panes
		queueLabelScrollPane.setViewportView(queueItemsPanel);
		detailsDateScrollPane.setViewportView(detailsDatePanel);
		
		//Add top-level containers (panels and scroll panes) to main panel
		add(detailsDateScrollPane, gbc_detailsDateScrollPane);
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

		for (QueueItem item : displayedItems) {
			//JEditorPane queueLabel = new JEditorPane("text.rtf.RTFEditorKit", item.name + ":\n" + item.details);
			String queueLabelText = item.getName();
			queueItemsListModel.addElement(queueLabelText);
		}
		queueItemsList = new JList<String>(queueItemsListModel);
		queueItemsList.setSelectedIndex(0);
		queueItemsListSelectionModel = queueItemsList.getSelectionModel();
		queueItemsListSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				updateButtonStates();
				updateDetailsDatePanel();
			}
		});

		queueItemsPanel.add(queueItemsList);
		queueItemsPanel.validate();
	}
	private void performItemOperation(String mode) {
		int[] selectedIndices = queueItemsList.getSelectedIndices();

		for (int index : selectedIndices) {
			//If mode is "move", add the removed item back to the bottom of the queue
			//If mode is "archive", add the removed item to the archivedItems hashmap
			//If mode is "remove", do not add the item back to any hashmaps
			
			if (archiveMode) {
				QueueItem removedItem = archivedItems.remove(index);
				if (mode == "move") {
					archivedItems.add(removedItem);
				} else if (mode == "archive") {
					currentQueueItems.add(removedItem);
				}
			} else {
				QueueItem removedItem = currentQueueItems.remove(index);
				if (mode == "move") {
					currentQueueItems.add(removedItem);
				} else if (mode == "archive") {
					archivedItems.add(removedItem);
				}
			}
		}
		updateQueueList();
		updateDetailsDatePanel();
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
	private void updateDetailsDatePanel() {
		int[] selectedIndices = queueItemsList.getSelectedIndices();

		if (selectedIndices.length == 1) {
			QueueItem selectedQueueItem = displayedItems.get(selectedIndices[0]);
			detailsTextPane.setText(selectedQueueItem.getDetails());
			lastEditedTextPane.setText(selectedQueueItem.getLastChanged().toString());
		} else if (selectedIndices.length > 1) {
			detailsTextPane.setText("Multiple");
			lastEditedTextPane.setText("Multiple");
		}
	}
}