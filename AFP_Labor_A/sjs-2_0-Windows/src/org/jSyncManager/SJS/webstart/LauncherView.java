package org.jSyncManager.SJS.webstart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LauncherView extends JFrame
{
    private static final long serialVersionUID = 4406987755610516837L;

    private Launcher controller = null;
    
    private DefaultListModel listModel;
    
    public LauncherView(Launcher launcher)
    {
	super("SJS Launcher");
	this.controller = launcher;
	
	listModel = new DefaultListModel();

	initComponents();
    }
    
    public void updateAdapterList(Model model)
    {
	listModel.clear();
	Iterator<Adapter> itr = model.getAdapterList().iterator();
	while(itr.hasNext())
	{
	    listModel.addElement(itr.next().getName());
	}
	
	getAdapterList().setModel(listModel);
    }
    
    public void setAdapter(Adapter adapter)
    {
	getAdapterPanel().setAdapter(adapter);
	getLaunchMenuItem().setEnabled(adapter != null);
    }

    private void initComponents()
    {
	super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	super.setSize(500, 300);
	super.setJMenuBar(getMainMenuBar());
	super.getContentPane().add(getMainPanel());
	super.setVisible(true);
    }

    private JMenuBar getMainMenuBar()
    {
	if (menuBar == null)
	{
	    menuBar = new JMenuBar();
	    menuBar.add(getFileMenu());
	}

	return menuBar;
    }

    private JMenu getFileMenu()
    {
	if (fileMenu == null)
	{
	    fileMenu = new JMenu("File");
	    fileMenu.setMnemonic(KeyEvent.VK_F);
	    fileMenu.add(getNewMenuItem());
	    fileMenu.add(getLaunchMenuItem());
	    fileMenu.addSeparator();
	    fileMenu.add(getCloseMenuItem());
	}

	return fileMenu;
    }

    private JMenuItem getCloseMenuItem()
    {
	if (exitItem == null)
	{
	    exitItem = new JMenuItem("Exit");
	    exitItem.setMnemonic(KeyEvent.VK_X);
	    exitItem.addActionListener(new ActionListener()
	    {
		public void actionPerformed(ActionEvent arg0)
		{
		    System.exit(0);
		}
	    });
	}

	return exitItem;
    }
    
    private JPanel getMainPanel()
    {
	if(mainPanel == null)
	{
	    mainPanel = new JPanel(new BorderLayout());
	    mainPanel.add(getAdapterScrollPane(), BorderLayout.WEST);
	    mainPanel.setBorder(BorderFactory.createTitledBorder("JSJ Adapter Configuration"));
	    mainPanel.add(getAdapterPanel(), BorderLayout.CENTER);
	}
	
	return mainPanel;
    }
    
    private JList getAdapterList()
    {
	if(adapterList == null)
	{
	    adapterList = new JList();
	    adapterList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    adapterList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	    
	    adapterList.setModel(listModel);
	    
	    adapterList.addListSelectionListener(new ListSelectionListener() {

		public void valueChanged(ListSelectionEvent arg0)
		{
		    if(!arg0.getValueIsAdjusting() && getAdapterList().getSelectedIndex() >= 0)
		    {
			controller.setSelectedAdapter(getAdapterList().getSelectedIndex());
		    }
		}});
	}
	
	return adapterList;
    }
    
    private JScrollPane getAdapterScrollPane()
    {
	if(adapterScrollPane == null)
	{
	    adapterScrollPane = new JScrollPane(getAdapterList());
	    adapterScrollPane.setPreferredSize(new Dimension(150, 300));
	    adapterScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	return adapterScrollPane;
    }
    
    public AdapterPanel getAdapterPanel()
    {
	if(adapterPanel == null)
	{
	    adapterPanel = new AdapterPanel(controller);
	}
	
	return adapterPanel;
    }
    

    private JMenuItem getLaunchMenuItem()
    {
	if (launchItem == null)
	{
	    launchItem = new JMenuItem("Launch Server");
	    launchItem.setMnemonic(KeyEvent.VK_L);
	    launchItem.addActionListener(new ActionListener()
	    {
		public void actionPerformed(ActionEvent arg0)
		{
		    controller.launch();
		}
	    });
	}

	return launchItem;
    }
    
    private JMenuItem getNewMenuItem()
    {
	if(newItem == null)
	{
	    newItem = new JMenuItem("New Adapter");
	    newItem.setMnemonic(KeyEvent.VK_N);
	    newItem.addActionListener(new ActionListener()
	    {
		public void actionPerformed(ActionEvent arg0)
		{
		    controller.addNewAdapter();
		}
	    });
	    
	}
	
	return newItem;
    }
    
    private AdapterPanel adapterPanel = null;
    private JMenuBar menuBar = null;
    private JMenu fileMenu = null;
    private JMenuItem exitItem = null;
    private JMenuItem launchItem = null;
    private JMenuItem newItem = null;
    private JPanel mainPanel = null;
    private JList adapterList = null;
    private JScrollPane adapterScrollPane = null;
}