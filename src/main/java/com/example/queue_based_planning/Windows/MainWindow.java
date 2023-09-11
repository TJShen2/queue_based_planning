package com.example.queue_based_planning.Windows;

import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.example.queue_based_planning.JsonHandler;
import com.example.queue_based_planning.QueueItem;
import com.example.queue_based_planning.Panels.AddItemPanel;
import com.example.queue_based_planning.Panels.EditItemPanel;
import com.example.queue_based_planning.Panels.QueuePanel;
import com.google.gson.reflect.TypeToken;

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
	
	private LinkedHashMap<String, QueueItem> queueItems;
	public LinkedHashMap<String, QueueItem> getQueueItems() { return queueItems; }
	public void setQueueItems(LinkedHashMap<String, QueueItem> value) { queueItems = value; }

	private LinkedHashMap<String, QueueItem> archivedItems;
	public LinkedHashMap<String, QueueItem> getArchivedItems() { return archivedItems; }
	public void setArchivedItems(LinkedHashMap<String, QueueItem> value) { archivedItems = value; }

	private java.lang.reflect.Type queueItemsType;
	public java.lang.reflect.Type getQueueItemsType() { return queueItemsType; }
	public void setQueueItemsType(java.lang.reflect.Type value) { queueItemsType = value; }

	private JsonHandler queueItemsJsonHandler;
	public JsonHandler getQueueItemsJsonHandler() { return queueItemsJsonHandler; }
	public void setQueueItemsJsonHandler(JsonHandler value) { queueItemsJsonHandler = value; }

	private JsonHandler archivedItemsJsonHandler;
	public JsonHandler getArchivedItemsJsonHandler() { return archivedItemsJsonHandler; }
	public void setArchivedItemsJsonHandler(JsonHandler value) { archivedItemsJsonHandler = value; }

	//Panels
	private AddItemPanel addItemPanel;
	public AddItemPanel getAddItemPanel() { return addItemPanel; }
	public void setAddItemPanel(AddItemPanel value) { addItemPanel = value; }

	private QueuePanel queuePanel;
	public QueuePanel getQueuePanel() { return queuePanel; }
	public void setQueuePanel(QueuePanel value) { queuePanel = value; }

	private EditItemPanel editItemPanel;
	public EditItemPanel getEditItemPanel() { return editItemPanel; }
	public void setEditItemPanel(EditItemPanel value) { editItemPanel = value; }

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
		queueItemsJsonHandler = new JsonHandler("queueItems.json");
		archivedItemsJsonHandler = new JsonHandler("archivedItems.json");
		queueItemsType = new TypeToken<LinkedHashMap<String,QueueItem>>() {}.getType();

		try {
			queueItems = (LinkedHashMap<String, QueueItem>) queueItemsJsonHandler.ReadObjectFromJson(queueItemsType);
		} catch (ClassCastException|NoSuchElementException e) {
			queueItems = new LinkedHashMap<String, QueueItem>(32, 0.75f);
			System.out.println(e.getMessage());
		}
		try {
			archivedItems = (LinkedHashMap<String, QueueItem>) archivedItemsJsonHandler.ReadObjectFromJson(queueItemsType);
		} catch (ClassCastException|NoSuchElementException e) {
			archivedItems = new LinkedHashMap<String, QueueItem>(32, 0.75f);
			System.out.println(e.getMessage());
		}
		
		frame = new JFrame();
		frame.pack();
		contentPaneLayout = new CardLayout(0, 0);
		contentPane = new JPanel(contentPaneLayout, rootPaneCheckingEnabled);
		addItemPanel = new AddItemPanel(this);
		queuePanel = new QueuePanel(this);
		editItemPanel = new EditItemPanel(this);

		frame.setBounds(100, 100, 960, 540);
		frame.setMinimumSize(new Dimension(480, 270));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		class WindowClosingEvent extends WindowAdapter {
			@Override
			public void windowClosing(WindowEvent e) {
				performCleanup();
			}
		}
		frame.addWindowListener(new WindowClosingEvent());
		
		contentPane.add(addItemPanel, "Add Item Panel");
		contentPane.add(queuePanel, "Queue Panel");
		contentPane.add(editItemPanel, "Edit Item Panel");
		
		contentPaneLayout.addLayoutComponent(addItemPanel, "Add Item Panel");
		contentPaneLayout.addLayoutComponent(queuePanel, "Queue Panel");
		contentPaneLayout.addLayoutComponent(editItemPanel, "Edit Item Panel");

		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.show(contentPane, "Queue Panel");

		frame.setContentPane(contentPane);
		frame.setVisible(true);
	}
	private void performCleanup() {
		queueItemsJsonHandler.WriteObjectAsJson(queueItems, queueItemsType);
		archivedItemsJsonHandler.WriteObjectAsJson(archivedItems, queueItemsType);
	}
}