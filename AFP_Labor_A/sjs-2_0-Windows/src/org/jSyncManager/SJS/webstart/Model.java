package org.jSyncManager.SJS.webstart;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model extends Observable
{
    private String log = "";
    
    private List<Adapter> adapters;
    
    public Model()
    {
	adapters = new ArrayList<Adapter>();
    }
    
    public Adapter getAdapter(int pos)
    {
	return adapters.get(pos);
    }
    
    public void addAdapter(Adapter adapter)
    {
	adapters.add(adapter);
    }
    
    public String getLog()
    {
        return log;
    }

    public void setLog(String log)
    {
        this.log = log;
    }
    
    public int size()
    {
	return adapters.size();
    }
    
    public List<Adapter> getAdapterList()
    {
	return adapters;
    }
}
