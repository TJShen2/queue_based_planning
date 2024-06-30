package com.tj.queuebasedplanning.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.tj.queuebasedplanning.windows.MainWindow;
import javax.swing.BoxLayout;
import java.awt.GridBagConstraints;

public class SettingsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

    //From parent
    private MainWindow parent;

    //Application settings
    private HashMap<String,String> settings;
	private int displayedArchivedItemsCount; //The number of archived items that are displayed
	private int displayedCurrentQueueItemsCount; //The number of current queue items that are displayed
	private Boolean archiveMode; //Whether the displayed items are the archived items
	private Boolean displayPicture; //Whether a photo is displayed after adding an item
	private Boolean autosave; //Whether autosave is enabled
	private int autosaveInterval; //Frequency of autosaves

	private JButton applyChangesButton;
	private JCheckBox archiveModeCheckBox;
	private JCheckBox displayPictureCheckBox;

	private JPanel displayedItemsCountSelectionPanel;
	private JLabel displayedArchivedItemsCountLabel;
	private JTextPane displayedArchivedItemsCountTextPane;
	private JLabel displayedCurrentQueueItemsCountLabel;
	private JTextPane displayedCurrentQueueItemsCountTextPane;
	private JPanel displayedArchivedItemsCountPanel;
	private JPanel displayedCurrentQueueItemsCountPanel;
	private JCheckBox autosaveCheckBox;
	private JPanel autosaveIntervalPanel;
	private JLabel autosaveIntervalLabel;
	private JTextPane autosaveIntervalTextPane;
	private JLabel settingsLabel;
	private JButton OKButton;

	/**
	 * Create the panel.
	 */
	public SettingsPanel(MainWindow parent) {
        this.parent = parent;
		getUpdatedSettings();

		//Set up the main panel
		setBackground(new Color(255, 246, 187));
		setBounds(100, 100, 960, 540);
		setBorder(new EmptyBorder(5, 5, 5, 5));

		//Set up layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		setLayout(gbl_contentPane);

		displayedItemsCountSelectionPanel = new JPanel();
		displayedItemsCountSelectionPanel.setLayout(new BoxLayout(displayedItemsCountSelectionPanel, BoxLayout.Y_AXIS));

		settingsLabel = new JLabel("Settings");
		settingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		displayedItemsCountSelectionPanel.add(settingsLabel);

		//Set up check boxes
		archiveModeCheckBox = new JCheckBox("Archive Mode");
		displayedItemsCountSelectionPanel.add(archiveModeCheckBox);
		archiveModeCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		archiveModeCheckBox.setSelected(archiveMode);

		displayPictureCheckBox = new JCheckBox("Display Picture");
		displayedItemsCountSelectionPanel.add(displayPictureCheckBox);
		displayPictureCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		displayPictureCheckBox.setSelected(displayPicture);

		GridBagConstraints gbc_displayedItemsCountSelectionPanel = new GridBagConstraints();
		gbc_displayedItemsCountSelectionPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_displayedItemsCountSelectionPanel.weighty = 1.0;
		gbc_displayedItemsCountSelectionPanel.weightx = 1.0;
		add(displayedItemsCountSelectionPanel, gbc_displayedItemsCountSelectionPanel);

		autosaveCheckBox = new JCheckBox("Autosave");
		autosaveCheckBox.setSelected(autosave);
		autosaveCheckBox.setAlignmentX(0.5f);
		displayedItemsCountSelectionPanel.add(autosaveCheckBox);

		displayedArchivedItemsCountPanel = new JPanel();
		displayedItemsCountSelectionPanel.add(displayedArchivedItemsCountPanel);
        displayedArchivedItemsCountPanel.setLayout(new BoxLayout(displayedArchivedItemsCountPanel, BoxLayout.X_AXIS));

        displayedArchivedItemsCountLabel = new JLabel("displayedArchivedItemsCount");
        displayedArchivedItemsCountPanel.add(displayedArchivedItemsCountLabel);
        displayedArchivedItemsCountLabel.setHorizontalAlignment(SwingConstants.CENTER);

        displayedArchivedItemsCountTextPane = new JTextPane();
        displayedArchivedItemsCountTextPane.setText(String.valueOf(displayedArchivedItemsCount));
        displayedArchivedItemsCountPanel.add(displayedArchivedItemsCountTextPane);

		displayedCurrentQueueItemsCountPanel = new JPanel();
		displayedItemsCountSelectionPanel.add(displayedCurrentQueueItemsCountPanel);
		displayedCurrentQueueItemsCountPanel.setLayout(new BoxLayout(displayedCurrentQueueItemsCountPanel, BoxLayout.X_AXIS));

		displayedCurrentQueueItemsCountLabel = new JLabel("displayedCurrentQueueItemsCount");
		displayedCurrentQueueItemsCountPanel.add(displayedCurrentQueueItemsCountLabel);
		displayedCurrentQueueItemsCountLabel.setHorizontalAlignment(SwingConstants.CENTER);

		displayedCurrentQueueItemsCountTextPane = new JTextPane();
        displayedArchivedItemsCountTextPane.setText(String.valueOf(displayedCurrentQueueItemsCount));
		displayedCurrentQueueItemsCountPanel.add(displayedCurrentQueueItemsCountTextPane);

		applyChangesButton = new JButton("Apply");
		applyChangesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		applyChangesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                settings = parent.getSettings();

				try {
					displayedArchivedItemsCount = Integer.valueOf(displayedArchivedItemsCountTextPane.getText());
					displayedCurrentQueueItemsCount = Integer.valueOf(displayedCurrentQueueItemsCountTextPane.getText());
					settings.put("displayedArchivedItemsCount", String.valueOf(displayedArchivedItemsCount));
					settings.put("displayedCurrentQueueItemsCount", String.valueOf(displayedCurrentQueueItemsCount));
                    settings.put("autosaveInterval", String.valueOf(autosaveInterval));
				} catch (Exception e) {
					displayedArchivedItemsCountTextPane.setText("Invalid value");
					displayedCurrentQueueItemsCountTextPane.setText("Invalid value");
				}

                archiveMode = archiveModeCheckBox.isSelected();
                displayPicture = displayPictureCheckBox.isSelected();
                autosave = autosaveCheckBox.isSelected();

				settings.put("archiveMode", archiveMode.toString());
                settings.put("displayPicture", displayPicture.toString());
                settings.put("autosave", autosave.toString());

				parent.setSettings(settings);
                parent.loadSettings();
			}
		});

		autosaveIntervalPanel = new JPanel();
		displayedItemsCountSelectionPanel.add(autosaveIntervalPanel);
		autosaveIntervalPanel.setLayout(new BoxLayout(autosaveIntervalPanel, BoxLayout.X_AXIS));

		autosaveIntervalLabel = new JLabel("autosaveInterval");
		autosaveIntervalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		autosaveIntervalPanel.add(autosaveIntervalLabel);

		autosaveIntervalTextPane = new JTextPane();
        displayedArchivedItemsCountTextPane.setText(String.valueOf(autosaveInterval));
		autosaveIntervalPanel.add(autosaveIntervalTextPane);
		displayedItemsCountSelectionPanel.add(applyChangesButton);

		OKButton = new JButton("OK");
		OKButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        OKButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.getContentPaneLayout().show(parent.getContentPane(), "Queue Panel");
            }
        });
        displayedItemsCountSelectionPanel.add(OKButton);
	}
    public void getUpdatedSettings() {
		parent.loadSettings();
		displayedArchivedItemsCount = parent.getDisplayedArchivedItemsCount();
		displayedCurrentQueueItemsCount = parent.getDisplayedCurrentQueueItemsCount();
		archiveMode = parent.getArchiveMode();
        displayPicture = parent.getDisplayPicture();
	    autosave = parent.getAutosave();
	    autosaveInterval = parent.getAutosaveInterval();
	}
}
