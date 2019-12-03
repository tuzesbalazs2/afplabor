package org.jSyncManager.SJS.webstart;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AdapterPanel extends JPanel
{
    private static final long serialVersionUID = 2873962722240014536L;
    
    private Launcher controller = null;
    
    private String[] adapters = {"org.jSyncManager.SJS.Adapters.SMTPServer.SMTPServer"};
    
    private Adapter adapter = null;
    
    public AdapterPanel(Launcher controller)
    {
	super(new GridBagLayout());
	this.controller = controller;
	
	initComponents();
    }
    
    public void setAdapter(Adapter adapter)
    {
	this.adapter = adapter;
	getNameField().setText(adapter.getName());
	getPortField().setText(adapter.getPort() + "");
	getAdapterField().setSelectedItem(adapter.getAdapter());
	getLogField().setText(adapter.getLogpath());
	getClassField().setText(adapter.getClasspath());
	getTimeoutField().setText(adapter.getTimeout() +"");
	getConnectionsField().setText(adapter.getMaxConnections() + "");
    }
    
    private void initComponents()
    {
	GridBagConstraints c = new GridBagConstraints();
	c.ipadx = 10;
	
	// add name label
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 0;
	super.add(getNameLabel(), c);
	
	// add name field
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 1;
	c.gridy = 0;
	super.add(getNameField(), c);
	
	// add port label
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 1;
	super.add(getPortLabel(), c);
	
	// add poirt field
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 1;
	c.gridy = 1;
	super.add(getPortField(), c);
	
	// add adapter label
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 2;
	super.add(getAdapterLabel(), c);
	
	// add adapter field
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 1;
	c.gridy = 2;
	super.add(getAdapterField(), c);
	
	// add logpath label
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 3;
	super.add(getLogLabel(), c);
	
	// add logpath field
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 1;
	c.gridy = 3;
	super.add(getLogField(), c);
	
	// add classpath label
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 4;
	super.add(getClassLabel(), c);
	
	// add classpath field
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 1;
	c.gridy = 4;
	super.add(getClassField(), c);
	
	// add classpath label
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 5;
	super.add(getTimeoutLabel(), c);
	
	// add classpath field
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 1;
	c.gridy = 5;
	super.add(getTimeoutField(), c);
	
	// add classpath label
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 6;
	super.add(getConnectionsLabel(), c);
	
	// add classpath field
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 1;
	c.gridy = 6;
	super.add(getConnectionsField(), c);
	
	// add classpath field
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 1;
	c.gridy = 7;
	super.add(getSaveButton(), c);
    }
    
    private JLabel getNameLabel()
    {
	if(nameLabel == null)
	{
	    nameLabel = new JLabel("Name:");
	}
	
	return nameLabel;
    }
    
    private JTextField getNameField()
    {
	if(nameField == null)
	{
	    nameField = new JTextField(15);
	}
	
	return nameField;
    }
    
    private JLabel getPortLabel()
    {
	if(portLabel == null)
	{
	    portLabel = new JLabel("Port:");
	}
	
	return portLabel;
    }
    
    private JTextField getPortField()
    {
	if(portField == null)
	{
	    portField = new JTextField(5);
	}
	
	return portField;
    }
    
    private JLabel getAdapterLabel()
    {
	if(adapterLabel == null)
	{
	    adapterLabel = new JLabel("Adapter:");
	}
	
	return adapterLabel;
    }
    
    private JComboBox getAdapterField()
    {
	if(adapterField == null)
	{
	    adapterField = new JComboBox(adapters);
	    adapterField.setEditable(true);
	    adapterField.setMaximumSize(new Dimension(50, 22));
	    adapterField.setPreferredSize(new Dimension(50, 22));
	}
	
	return adapterField;
    }
    
    private JLabel getLogLabel()
    {
	if(logLabel == null)
	{
	    logLabel = new JLabel("Log Path:");
	}
	
	return logLabel;
    }
    
    private JTextField getLogField()
    {
	if(logField == null)
	{
	    logField = new JTextField(5);
	}
	
	return logField;
    }
    
    private JLabel getClassLabel()
    {
	if(classLabel == null)
	{
	    classLabel = new JLabel("Classpath:");
	}
	
	return classLabel;
    }
    
    private JTextField getClassField()
    {
	if(classField == null)
	{
	    classField = new JTextField(5);
	}
	
	return classField;
    }
    
    private JLabel getTimeoutLabel()
    {
	if(timeoutLabel == null)
	{
	    timeoutLabel = new JLabel("Timeout:");
	}
	
	return timeoutLabel;
    }
    
    private JTextField getTimeoutField()
    {
	if(timeoutField == null)
	{
	    timeoutField = new JTextField(5);
	}
	
	return timeoutField;
    }
    
    private JLabel getConnectionsLabel()
    {
	if(connectionsLabel == null)
	{
	    connectionsLabel = new JLabel("Max Connections:");
	}
	
	return connectionsLabel;
    }
    
    private JTextField getConnectionsField()
    {
	if(connectionsField == null)
	{
	    connectionsField = new JTextField(5);
	}
	
	return connectionsField;
    }
    
    private JButton getSaveButton()
    {
	if(saveButton == null)
	{
	    saveButton = new JButton("Save");
	    saveButton.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent arg0)
		{
		    adapter.setName(getNameField().getText());
		    adapter.setPort(Integer.parseInt(getPortField().getText()));
		    adapter.setAdapter((String) getAdapterField().getSelectedItem());
		    adapter.setLogpath(getLogField().getText());
		    adapter.setClasspath(getClassField().getText());
		    adapter.setTimeout(Integer.parseInt(getTimeoutField().getText()));
		    adapter.setMaxConnections(Integer.parseInt(getConnectionsField().getText()));
		    controller.saveAdapter(adapter);
		}});
	}
	
	return saveButton;
    }
    
    private JLabel nameLabel = null;
    private JTextField nameField = null;
    private JLabel portLabel = null;
    private JTextField portField = null;
    private JLabel adapterLabel = null;
    private JComboBox adapterField = null;
    private JLabel logLabel = null;
    private JTextField logField = null;
    private JLabel classLabel = null;
    private JTextField classField = null;
    private JLabel timeoutLabel = null;
    private JTextField timeoutField = null;
    private JLabel connectionsLabel = null;
    private JTextField connectionsField = null;
    private JButton saveButton = null;
    
}

