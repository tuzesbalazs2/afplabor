package org.jSyncManager.SJS.webstart;

public class Adapter
{
    private String name;
    
    private int port;
    
    private String adapter;
    
    private String logpath;
    
    private String classpath;
    
    private int timeout;
    
    private int maxConnections;
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getAdapter()
    {
        return adapter;
    }

    public void setAdapter(String adapter)
    {
        this.adapter = adapter;
    }

    public String getLogpath()
    {
        return logpath;
    }

    public void setLogpath(String logpath)
    {
        this.logpath = logpath;
    }

    public String getClasspath()
    {
        return classpath;
    }

    public void setClasspath(String classpath)
    {
        this.classpath = classpath;
    }

    public int getTimeout()
    {
        return timeout;
    }

    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }

    public int getMaxConnections()
    {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections)
    {
        this.maxConnections = maxConnections;
    }
}
