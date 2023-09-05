package com.example.queue_based_planning.Panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import com.example.queue_based_planning.QueueItem;
import com.example.queue_based_planning.Windows.MainWindow;

public class EditItemPanel extends JPanel {

    //From parent window
	private JPanel contentPane;
	private LinkedHashMap<String, QueueItem> queueItems;
	private CardLayout contentPaneLayout;
    
    private JPanel infoEntryPanel;

    private JButton editButton;
    private JButton backToQueueButton;

    private JTextPane nameTextPane;
    private JTextPane detailsTextPane;

    private String uneditedItem;
    public String getUneditedItem() { return uneditedItem; }
    public void setUneditedItem(String value) { uneditedItem = value; }

    public EditItemPanel(MainWindow parentWindow) {
		contentPane = parentWindow.getContentPane();
		contentPaneLayout = parentWindow.getContentPaneLayout();
		queueItems = parentWindow.getQueueItems();

        //Set up the panel
        setMinimumSize(new Dimension(960, 540));
        //pack();
        setBounds(100, 100, 960, 540);
        setBackground(new Color(255, 246, 187));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
        //Set up components
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0};
		gbl_contentPane.rowHeights = new int[] {20, 20, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWidths = new int[] {150, 660, 150};
        setLayout(gbl_contentPane);
         
        JLabel titleLabel = new JLabel("Edit Item");
        titleLabel.setPreferredSize(new Dimension(600, 50));
        GridBagConstraints gbc_titleLabel = new GridBagConstraints();
        gbc_titleLabel.insets = new Insets(0, 0, 5, 5);
        gbc_titleLabel.gridx = 1;
        gbc_titleLabel.gridy = 2;
        add(titleLabel, gbc_titleLabel);
        
        infoEntryPanel = new JPanel();
        GridBagConstraints gbc_infoEntryPanel = new GridBagConstraints();
        gbc_infoEntryPanel.weighty = 1.0;
        gbc_infoEntryPanel.weightx = 1.0;
        gbc_infoEntryPanel.insets = new Insets(0, 0, 5, 5);
        gbc_infoEntryPanel.fill = GridBagConstraints.BOTH;
        gbc_infoEntryPanel.gridx = 1;
        gbc_infoEntryPanel.gridy = 5;
        add(infoEntryPanel, gbc_infoEntryPanel);
        infoEntryPanel.setLayout(new GridLayout(2, 2, 0, 25));
        infoEntryPanel.setPreferredSize(new Dimension(400, 100));
        
        JLabel nameLabel = new JLabel("Name of Item:");
        infoEntryPanel.add(nameLabel);
        
        nameTextPane = new JTextPane();
        nameTextPane.setPreferredSize(new Dimension(200, 50));
        infoEntryPanel.add(nameTextPane);
      
        JLabel detailsLabel = new JLabel("Details:");
        infoEntryPanel.add(detailsLabel);
        
        detailsTextPane = new JTextPane();
        detailsTextPane.setPreferredSize(new Dimension(200, 50));
        infoEntryPanel.add(detailsTextPane);
  
        editButton = new JButton("Edit");
        GridBagConstraints gbc_addButton = new GridBagConstraints();
        gbc_addButton.insets = new Insets(0, 0, 5, 5);
        gbc_addButton.gridx = 1;
        gbc_addButton.gridy = 7;
        add(editButton, gbc_addButton);

        editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                queueItems = parentWindow.getQueueItems();
				QueueItem editedItem = new QueueItem(nameTextPane.getText(), detailsTextPane.getText());
				queueItems = replaceItemInLinkedHashMap(queueItems, uneditedItem, nameTextPane.getText(), editedItem);
                parentWindow.setQueueItems(queueItems);
                QueuePanel queuePanel = parentWindow.getQueuePanel();
                queuePanel.getItemSelectionComboBox();
				queuePanel.updateQueueList(queueItems);
                parentWindow.setQueuePanel(queuePanel);

				contentPaneLayout.show(contentPane, "Queue Panel");
                parentWindow.setContentPaneLayout(contentPaneLayout);
			}
		});
        
        JPanel backToQueuePanel = new JPanel();
        GridBagConstraints gbc_backToQueuePanel = new GridBagConstraints();
        gbc_backToQueuePanel.anchor = GridBagConstraints.SOUTH;
        gbc_backToQueuePanel.gridx = 2;
        gbc_backToQueuePanel.gridy = 11;
        add(backToQueuePanel, gbc_backToQueuePanel);
        
        backToQueueButton = new JButton("Back to Queue");
        backToQueuePanel.add(backToQueueButton);
	}
    public void setupEditItemPanel(QueueItem queueItem) {
        nameTextPane.setText(queueItem.name);
        detailsTextPane.setText(queueItem.details);
    }
    private LinkedHashMap<String, QueueItem> replaceItemInLinkedHashMap(LinkedHashMap<String, QueueItem> originalHashMap, String keyOfItemToRemove, String keyToInsert, QueueItem valueToInsert) {
        LinkedHashMap<String, QueueItem> newHashMap = new LinkedHashMap<String, QueueItem>();

        for (Map.Entry<String,QueueItem> entry : originalHashMap.entrySet()) { 
            if (entry.getKey().equals(keyOfItemToRemove)) {
                newHashMap.put(keyToInsert, valueToInsert);
            } else {
                newHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return newHashMap;
    }
}