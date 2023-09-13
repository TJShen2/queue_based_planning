package com.example.queue_based_planning.Panels;

import java.util.LinkedHashMap;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.example.queue_based_planning.LinkedHashMapEditor;
import com.example.queue_based_planning.QueueItem;
import com.example.queue_based_planning.Windows.MainWindow;

public class EditItemPanel extends JPanel {

    //From parent window
	private JPanel contentPane;
    private QueuePanel queuePanel;
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

    public EditItemPanel(MainWindow parent) {
		contentPane = parent.getContentPane();
		contentPaneLayout = parent.getContentPaneLayout();
		queueItems = parent.getQueueItems();

        //Set up the panel
        setBounds(100, 100, 960, 540);
        setBackground(new Color(255, 246, 187));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
        //Set up components
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_contentPane.columnWeights = new double[]{0.0};
        setLayout(gbl_contentPane);
         
        JLabel titleLabel = new JLabel("Edit Item");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_titleLabel = new GridBagConstraints();
        gbc_titleLabel.insets = new Insets(0, 0, 5, 5);
        gbc_titleLabel.gridx = 0;
        gbc_titleLabel.gridy = 1;
        add(titleLabel, gbc_titleLabel);
        
        infoEntryPanel = new JPanel();
        GridBagConstraints gbc_infoEntryPanel = new GridBagConstraints();
        gbc_infoEntryPanel.weighty = 1.0;
        gbc_infoEntryPanel.weightx = 1.0;
        gbc_infoEntryPanel.insets = new Insets(0, 0, 5, 5);
        gbc_infoEntryPanel.fill = GridBagConstraints.BOTH;
        gbc_infoEntryPanel.gridx = 0;
        gbc_infoEntryPanel.gridy = 3;
        add(infoEntryPanel, gbc_infoEntryPanel);
        infoEntryPanel.setLayout(new GridLayout(2, 2, 0, 25));
        
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
        gbc_addButton.gridx = 0;
        gbc_addButton.gridy = 5;
        add(editButton, gbc_addButton);

        editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                queueItems = parent.getQueueItems();
				QueueItem editedItem = new QueueItem(nameTextPane.getText(), detailsTextPane.getText());
				queueItems = LinkedHashMapEditor.replaceItem(queueItems, uneditedItem, nameTextPane.getText(), editedItem);
                parent.setQueueItems(queueItems);
                queuePanel = parent.getQueuePanel();
				queuePanel.updateQueueList();
                parent.setQueuePanel(queuePanel);

				contentPaneLayout.show(contentPane, "Queue Panel");
                parent.setContentPaneLayout(contentPaneLayout);
			}
		});
        
        JPanel backToQueuePanel = new JPanel();
        GridBagConstraints gbc_backToQueuePanel = new GridBagConstraints();
        gbc_backToQueuePanel.anchor = GridBagConstraints.SOUTHEAST;
        gbc_backToQueuePanel.gridx = 0;
        gbc_backToQueuePanel.gridy = 7;
        add(backToQueuePanel, gbc_backToQueuePanel);
        
        backToQueueButton = new JButton("Back to Queue");

        backToQueueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contentPaneLayout.show(parent.getContentPane(), "Queue Panel");
                parent.setContentPaneLayout(contentPaneLayout);
            }
        });
        backToQueuePanel.add(backToQueueButton);
	}
    public void setupEditItemPanel(QueueItem queueItem) {
        nameTextPane.setText(queueItem.name);
        detailsTextPane.setText(queueItem.details);
    }
}