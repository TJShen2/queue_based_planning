//Stop using centre aligned buttons
//Place settings in separate window or dialog box
//Read maven tutorial
//Modern Java in Action

package com.tj.queuebasedplanning.windows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.google.gson.reflect.TypeToken;

import com.tj.queuebasedplanning.Json;
import com.tj.queuebasedplanning.QueueItem;
import com.tj.queuebasedplanning.panels.AddItemPanel;
import com.tj.queuebasedplanning.panels.EditItemPanel;
import com.tj.queuebasedplanning.panels.QueuePanel;
import com.tj.queuebasedplanning.panels.SettingsPanel;

public class MainWindow extends JFrame {

	private JFrame frame;
	public JFrame getFrame() { return frame; }
	public void setFrame(JFrame value) { frame = value; }

	private JPanel contentPane;
	public JPanel getContentPane() { return contentPane; }
	public void setContentPane(JPanel value) { contentPane = value; }

	private CardLayout contentPaneLayout;
	public CardLayout getContentPaneLayout() { return contentPaneLayout; }
	public void setContentPaneLayout(CardLayout value) { contentPaneLayout = value; }

	//Queue item hash maps
	private List<QueueItem> currentQueueItems;
	public List<QueueItem> getCurrentQueueItems() { return currentQueueItems; }
	public void setCurrentQueueItems(List<QueueItem> value) { currentQueueItems = value; }

	private List<QueueItem> archivedItems;
	public List<QueueItem> getArchivedItems() { return archivedItems; }
	public void setArchivedItems(List<QueueItem> value) { archivedItems = value; }

	//JSON handling
	private java.lang.reflect.Type queueItemsType;
	public java.lang.reflect.Type getQueueItemsType() { return queueItemsType; }
	public void setQueueItemsType(java.lang.reflect.Type value) { queueItemsType = value; }

	private Json queueItemsJsonHandler;
	public Json getQueueItemsJsonHandler() { return queueItemsJsonHandler; }
	public void setQueueItemsJsonHandler(Json value) { queueItemsJsonHandler = value; }

	private Json archivedItemsJsonHandler;
	public Json getArchivedItemsJsonHandler() { return archivedItemsJsonHandler; }
	public void setArchivedItemsJsonHandler(Json value) { archivedItemsJsonHandler = value; }

	private Json settingsJsonHandler;
	private java.lang.reflect.Type settingsType;

	//panels
	private AddItemPanel addItemPanel;
	public AddItemPanel getAddItemPanel() { return addItemPanel; }
	public void setAddItemPanel(AddItemPanel value) { addItemPanel = value; }

	private QueuePanel queuePanel;
	public QueuePanel getQueuePanel() { return queuePanel; }
	public void setQueuePanel(QueuePanel value) { queuePanel = value; }

	private EditItemPanel editItemPanel;
	public EditItemPanel getEditItemPanel() { return editItemPanel; }
	public void setEditItemPanel(EditItemPanel value) { editItemPanel = value; }

	private SettingsPanel settingsPanel;
	public SettingsPanel getSettingsPanel() { return settingsPanel; }
	public void setSettingsPanel(SettingsPanel value) { settingsPanel = value; }

	//Application settings
	private HashMap<String,String> settings;
	public HashMap<String,String> getSettings() { return settings; }
	public void setSettings(HashMap<String,String> value) { settings = value; }

	private int displayedArchivedItemsCount; //The number of archived items that are displayed
	public int getDisplayedArchivedItemsCount() { return displayedArchivedItemsCount; }
    public void setDisplayedArchivedItemsCount(int value) { displayedArchivedItemsCount = value; }

	private int displayedCurrentQueueItemsCount; //The number of current queue items that are displayed
    public int getDisplayedCurrentQueueItemsCount() { return displayedCurrentQueueItemsCount; }
    public void setDisplayedCurrentQueueItemsCount(int value) { displayedCurrentQueueItemsCount = value; }

	private Boolean archiveMode; //Whether the displayed items are the archived items
    public Boolean getArchiveMode() { return archiveMode; }
    public void setArchiveMode(Boolean value) { archiveMode = value; }

	private Boolean displayPicture; //Whether a photo is displayed after adding an item
    public Boolean getDisplayPicture() { return displayPicture; }
    public void setDisplayPicture(Boolean value) { displayPicture = value; }

	private Boolean autosave; //Whether autosave is enabled
    public Boolean getAutosave() { return autosave; }
    public void setAutosave(Boolean value) { autosave = value; }

	private int autosaveInterval; //Frequency of autosaves
    public int getAutosaveInterval() { return autosaveInterval; }
    public void setAutosaveInterval(int value) { autosaveInterval = value; }

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		queueItemsJsonHandler = new Json("data/queueItems.json");
		archivedItemsJsonHandler = new Json("data/archivedItems.json");
		queueItemsType = new TypeToken<List<QueueItem>>() {}.getType();

		settingsJsonHandler = new Json("data/settings.json");
		settingsType = new TypeToken<HashMap<String,String>>() {}.getType();

		try {
			currentQueueItems = queueItemsJsonHandler.readObjectFromJson(queueItemsType);
		} catch (Exception e) {
			currentQueueItems = new ArrayList<QueueItem>();
			e.printStackTrace();
		}
		try {
			archivedItems = archivedItemsJsonHandler.readObjectFromJson(queueItemsType);
		} catch (Exception e) {
			archivedItems = new ArrayList<QueueItem>();
			e.printStackTrace();
		}
		try {
			settings = settingsJsonHandler.readObjectFromJson(settingsType);
		} catch (Exception e) {
			settings = new HashMap<String,String>();
		}

		Timer saveChangesTimer = new Timer(autosaveInterval, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (autosave) {
					saveChanges();
				}
			}
		});

		frame = new JFrame();
		frame.pack();
		contentPaneLayout = new CardLayout(0, 0);
		contentPane = new JPanel(contentPaneLayout, rootPaneCheckingEnabled);

		addItemPanel = new AddItemPanel(this);
		queuePanel = new QueuePanel(this);
		editItemPanel = new EditItemPanel(this);
		settingsPanel = new SettingsPanel(this);

		frame.setBounds(100, 100, 960, 540);
		frame.setMinimumSize(new Dimension(480, 360));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		class WindowClosingEvent extends WindowAdapter {
			@Override
			public void windowClosing(WindowEvent e) {
				saveChanges();
			}
		}
		frame.addWindowListener(new WindowClosingEvent());

		contentPane.add(addItemPanel, "Add Item Panel");
		contentPane.add(queuePanel, "Queue Panel");
		contentPane.add(editItemPanel, "Edit Item Panel");
		contentPane.add(settingsPanel, "Settings Panel");

		contentPaneLayout.addLayoutComponent(addItemPanel, "Add Item Panel");
		contentPaneLayout.addLayoutComponent(queuePanel, "Queue Panel");
		contentPaneLayout.addLayoutComponent(editItemPanel, "Edit Item Panel");
		contentPaneLayout.addLayoutComponent(settingsPanel, "Settings Panel");

		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.show(contentPane, "Queue Panel");

		frame.setContentPane(contentPane);
		saveChangesTimer.start();

		frame.setVisible(true);
	}
	public void saveChanges() {
		queueItemsJsonHandler.writeObjectToJson(currentQueueItems, queueItemsType);
		archivedItemsJsonHandler.writeObjectToJson(archivedItems, queueItemsType);
	}
	public void saveSettings() {
		settingsJsonHandler.writeObjectToJson(settings, settingsType);
	}
	public void loadSettings() {
		try {
			displayedArchivedItemsCount = Integer.valueOf(settings.get("displayedArchivedItemsCount"));
			displayedCurrentQueueItemsCount = Integer.valueOf(settings.get("displayedCurrentQueueItemsCount"));
			archiveMode = Boolean.valueOf(settings.get("archiveMode"));
			displayPicture = Boolean.valueOf(settings.get("displayPicture"));
			autosave = Boolean.valueOf(settings.get("autosave"));
			autosaveInterval = Integer.valueOf(settings.get("autosaveInterval"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
}
