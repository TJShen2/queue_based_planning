package com.tj.queuebasedplanning.panels;

import java.util.List;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZonedDateTime;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.tj.queuebasedplanning.QueueItem;
import com.tj.queuebasedplanning.windows.MainWindow;

public class EditItemPanel extends JPanel {

    //From parent window
    private MainWindow parent;
	private JPanel contentPane;
    private QueuePanel queuePanel;
	private List<QueueItem> queueItems;
	private CardLayout contentPaneLayout;

    //Application settings
    private Boolean archiveMode;

    //Window components
    //Panels
    private JPanel infoEntryPanel;

    //Buttons
    private JButton editButton;
    private JButton cancelButton;

    //Text panes
    private JTextPane nameTextPane;
    private JTextPane detailsTextPane;

    //Labels
    private JLabel lastEditedLabel;
    private JLabel dateTimeLabel;

    //Item number before edit
    private int itemLocation;

    private ZonedDateTime lastEditedDateTime;

    public EditItemPanel(MainWindow parent) {
        this.parent = parent;
		contentPane = parent.getContentPane();
		contentPaneLayout = parent.getContentPaneLayout();
		queueItems = parent.getCurrentQueueItems();

        //Set up the main panel
        setBounds(100, 100, 960, 540);
        setBackground(new Color(255, 246, 187));
		setBorder(new EmptyBorder(5, 5, 5, 5));

        //Set up layout manager
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_contentPane.columnWeights = new double[]{0.0};
        setLayout(gbl_contentPane);

        //Set up panels
        infoEntryPanel = new JPanel();
        GridBagConstraints gbc_infoEntryPanel = new GridBagConstraints();
        gbc_infoEntryPanel.weighty = 1.0;
        gbc_infoEntryPanel.weightx = 1.0;
        gbc_infoEntryPanel.insets = new Insets(0, 0, 5, 5);
        gbc_infoEntryPanel.fill = GridBagConstraints.BOTH;
        gbc_infoEntryPanel.gridx = 0;
        gbc_infoEntryPanel.gridy = 3;
        infoEntryPanel.setLayout(new GridLayout(3, 2, 0, 25));

        JPanel cancelPanel = new JPanel();
        GridBagConstraints gbc_cancelPanel = new GridBagConstraints();
        gbc_cancelPanel.anchor = GridBagConstraints.SOUTHEAST;
        gbc_cancelPanel.gridx = 0;
        gbc_cancelPanel.gridy = 7;

        //Set up buttons and text panes in the infoEntryPanel
        JLabel titleLabel = new JLabel("Edit Item");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_titleLabel = new GridBagConstraints();
        gbc_titleLabel.insets = new Insets(0, 0, 5, 5);
        gbc_titleLabel.gridx = 0;
        gbc_titleLabel.gridy = 1;

        JLabel nameLabel = new JLabel("Name of Item:");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel detailsLabel = new JLabel("Details:");
        detailsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        nameTextPane = new JTextPane();
        nameTextPane.setPreferredSize(new Dimension(200, 50));
        nameTextPane.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateDateTime();
            }
            public void removeUpdate(DocumentEvent e) {
                updateDateTime();
            }
            public void changedUpdate(DocumentEvent e) {
                updateDateTime();
            }
        });

        detailsTextPane = new JTextPane();
        detailsTextPane.setPreferredSize(new Dimension(200, 50));
        detailsTextPane.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateDateTime();
            }
            public void removeUpdate(DocumentEvent e) {
                updateDateTime();
            }
            public void changedUpdate(DocumentEvent e) {
                updateDateTime();
            }
        });

        lastEditedLabel = new JLabel("Last Edited:");
        lastEditedLabel.setHorizontalAlignment(SwingConstants.CENTER);

        dateTimeLabel = new JLabel("New label");
        dateTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Set up buttons
        editButton = new JButton("Edit");
        GridBagConstraints gbc_addButton = new GridBagConstraints();
        gbc_addButton.insets = new Insets(0, 0, 5, 5);
        gbc_addButton.gridx = 0;
        gbc_addButton.gridy = 5;

        editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                getUpdatedSettings();
                queuePanel = parent.getQueuePanel();

                QueueItem editedItem = new QueueItem(nameTextPane.getText(), detailsTextPane.getText(), lastEditedDateTime);

                if (archiveMode) {
                    queueItems = parent.getArchivedItems();
                    queueItems.remove(itemLocation);
                    queueItems.add(itemLocation, editedItem);
                    parent.setArchivedItems(queueItems);
                } else {
                    queueItems = parent.getCurrentQueueItems();
                    queueItems.remove(itemLocation);
                    queueItems.add(itemLocation, editedItem);
                    parent.setCurrentQueueItems(queueItems);
                }

                parent.saveChanges();

				queuePanel.updateQueueList();
                queuePanel.updateButtonStates();
                parent.setQueuePanel(queuePanel);

				contentPaneLayout.show(contentPane, "Queue Panel");
                parent.setContentPaneLayout(contentPaneLayout);
			}
		});

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contentPaneLayout.show(parent.getContentPane(), "Queue Panel");
                parent.setContentPaneLayout(contentPaneLayout);
            }
        });

        //Add widgets
        add(titleLabel, gbc_titleLabel);
        add(editButton, gbc_addButton);

        cancelPanel.add(cancelButton);
        infoEntryPanel.add(nameLabel);
        infoEntryPanel.add(nameTextPane);
        infoEntryPanel.add(detailsLabel);
        infoEntryPanel.add(detailsTextPane);
        infoEntryPanel.add(lastEditedLabel);
        infoEntryPanel.add(dateTimeLabel);

        //Add top-level containers
        add(cancelPanel, gbc_cancelPanel);
        add(infoEntryPanel, gbc_infoEntryPanel);
	}

    public void setupEditItemPanel(int index, QueueItem queueItem) {
        itemLocation = index;

        nameTextPane.setText(queueItem.getName());
        detailsTextPane.setText(queueItem.getDetails());
        lastEditedDateTime = queueItem.getLastChanged();
        dateTimeLabel.setText(lastEditedDateTime.toString());
    }

    private void updateDateTime() {
        lastEditedDateTime = ZonedDateTime.now();
        dateTimeLabel.setText(lastEditedDateTime.toString());
    }
    private void getUpdatedSettings() {
        parent.loadSettings();
        archiveMode = parent.getArchiveMode();
    }
}
