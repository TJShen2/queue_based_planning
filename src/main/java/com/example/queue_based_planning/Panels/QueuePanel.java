package com.example.queue_based_planning.Panels;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

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

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
//import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import com.example.queue_based_planning.LinkedHashMapEditor;
import com.example.queue_based_planning.QueueItem;
import com.example.queue_based_planning.Windows.MainWindow;
import javax.swing.JList;
import javax.swing.SwingConstants;

public class QueuePanel extends JPanel {

	//From parent window
	private JFrame frame;
	private JPanel contentPane;
	private LinkedHashMap<String, QueueItem> queueItems;
	private LinkedHashMap<String, QueueItem> archivedItems;
	private CardLayout contentPaneLayout;

	private JPanel queueLabelPanel;
	private JButton addItemButton;

	private JScrollPane queueLabelScrollPane;
	private JComboBox<String> itemSelectionComboBox;
	public JComboBox<String> getItemSelectionComboBox() { return itemSelectionComboBox; }
	public void setItemSelectionComboBox(JComboBox<String> value) { itemSelectionComboBox = value; }

	private JButton removeSelectedItemButton;
	private JButton archiveItemButton;
	private JLabel selectedItemLabel;
	private JButton editSelectedItemButton;
	private JPanel saveAndExitPanel;
	private JButton saveAndExitButton;
	private JButton moveFirstItemToBackOfQueueButton;
	private JList<String> list;

	public QueuePanel(MainWindow parentWindow) {
		frame = parentWindow.getFrame();
		contentPane = parentWindow.getContentPane();
		contentPaneLayout = parentWindow.getContentPaneLayout();
		queueItems = parentWindow.getQueueItems();
		archivedItems = parentWindow.getArchivedItems();

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
		
		queueLabelPanel = new JPanel();
		//queueLabelPanel.setPreferredSize(new Dimension(250, 400));
		queueLabelScrollPane.setViewportView(queueLabelPanel);
		queueLabelPanel.setLayout(new BoxLayout(queueLabelPanel, BoxLayout.Y_AXIS));
		
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
		
		selectedItemLabel = new JLabel("Selected Item:");
		selectedItemLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectedItemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		actionButtonPanel.add(selectedItemLabel);
		
		itemSelectionComboBox = new JComboBox<String>(queueItemNames);
		itemSelectionComboBox.setEditable(false);
		actionButtonPanel.add(itemSelectionComboBox);
		
		archiveItemButton = new JButton("Archive Selected Item");
		archiveItemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		actionButtonPanel.add(archiveItemButton);
		archiveItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)  {
				queueItems = parentWindow.getQueueItems();
				String keyOfFirstItem = (String) queueItems.keySet().toArray()[0];
				QueueItem removedValue = queueItems.remove(keyOfFirstItem);
				parentWindow.setQueueItems(queueItems);
				archivedItems.put(keyOfFirstItem, removedValue);
				parentWindow.setArchivedItems(archivedItems);
				updateItemSelectionComboBox();
				updateQueueList(parentWindow.getQueueItems());
			}
		});
		
		moveFirstItemToBackOfQueueButton = new JButton("Move Selected Item to Back of Queue");
		moveFirstItemToBackOfQueueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		actionButtonPanel.add(moveFirstItemToBackOfQueueButton);
		moveFirstItemToBackOfQueueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				queueItems = parentWindow.getQueueItems();
				String firstKeyInQueueItems = (String) queueItems.keySet().toArray()[0];
				QueueItem firstValueInQueueItems = (QueueItem) queueItems.values().toArray()[0];
				queueItems = LinkedHashMapEditor.removeItemAtIndex(queueItems, 0);
				queueItems.put(firstKeyInQueueItems, firstValueInQueueItems);
				parentWindow.setQueueItems(queueItems);
				updateQueueList(queueItems);
				updateItemSelectionComboBox();
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
				contentPaneLayout.show(parentWindow.getContentPane(), "Add Item Panel");
				parentWindow.setContentPaneLayout(contentPaneLayout);
			}
		});
		removeSelectedItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				queueItems = parentWindow.getQueueItems();
				String itemToRemove = (String) parentWindow.getQueuePanel().itemSelectionComboBox.getSelectedItem();
				queueItems.remove(itemToRemove);
				parentWindow.setQueueItems(queueItems);
				updateItemSelectionComboBox();
				updateQueueList(parentWindow.getQueueItems());
			}
		});
		editSelectedItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				queueItems = parentWindow.getQueueItems();
				String selectedItemName = (String) itemSelectionComboBox.getSelectedItem();
				QueueItem selectedItem = queueItems.get(selectedItemName);
				EditItemPanel editItemPanel = parentWindow.getEditItemPanel();
				try {
					editItemPanel.setupEditItemPanel(selectedItem);
					String editItemPanelUneditedItem = editItemPanel.getUneditedItem();
					editItemPanelUneditedItem = selectedItemName;
					editItemPanel.setUneditedItem(editItemPanelUneditedItem);
					parentWindow.setEditItemPanel(editItemPanel);
					contentPaneLayout.show(contentPane, "Edit Item Panel");
					parentWindow.setContentPaneLayout(contentPaneLayout);
				} catch (NullPointerException e) {
					System.out.println("No item is selected");
				}
			}
		});
		saveAndExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});

		updateQueueList(queueItems);
	}
	public void updateQueueList(LinkedHashMap<String,QueueItem> queueItems) {
		queueLabelPanel.removeAll();

		list = new JList<String>();
		queueLabelPanel.add(list);

		for (Map.Entry<String,QueueItem> itemEntry : queueItems.entrySet()) {
			//JEditorPane queueLabel = new JEditorPane("text.rtf.RTFEditorKit", item.name + ":\n" + item.details);
			String queueLabelText = "";
			QueueItem item = itemEntry.getValue();
			JTextPane queueTextPane = new JTextPane();

			queueLabelText += item.name + ":\n" + item.details;
			queueTextPane.setEditable(false);
			queueTextPane.setText(queueLabelText);
			list.add(queueTextPane);
		}
		queueLabelPanel.validate();
	}
	public void updateItemSelectionComboBox() {
		Object[] keys = queueItems.keySet().toArray();
		Arrays.sort(keys);
		itemSelectionComboBox.removeAllItems();
		for (Object key : keys) {
			itemSelectionComboBox.addItem((String) key);
		}
	}
}