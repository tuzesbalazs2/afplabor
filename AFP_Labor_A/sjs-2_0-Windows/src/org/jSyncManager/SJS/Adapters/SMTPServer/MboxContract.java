package org.jSyncManager.SJS.Adapters.SMTPServer;

import org.jSyncManager.SJS.Adapters.SMTPServer.Config.Domain;
import org.jSyncManager.SJS.Adapters.SMTPServer.Config.User;
import org.jSyncManager.SJS.Adapters.SMTPServer.util.SMTPUtils;

import net.sourceforge.c4j.ContractBase;

import java.io.File;

public class MboxContract extends ContractBase<Mbox>
{
	public MboxContract(Mbox box)
	{
		super(box);
	}

	public void classInvariant()
	{
        // all data is stored on disk. No members to fuss about.
	}

    public void pre_storeMessages(Message[] msgs)
    {
        assert msgs != null;
        assert msgs.length > 0;
        for (int i = 0; i < msgs.length; i++) {
            assert msgs[i] != null;
        }
    }
	public void post_storeMessages(Message[] msgs)
	{
        // detailed post condition not required, will be enforced by storeMessage() post condition.
	}
    
	public void pre_storeMessage(Message msg, Address to)
	{
        assert msg != null;
        assert msg.getBody() != null;
        assert msg.getFrom() != null;
        assert msg.getFrom().getUser() != null;
        assert msg.getFrom().getUser().getName() != null;
        assert msg.getFrom().getUser().getName().equals("")==false;
        
        assert to != null;
        assert to.getUser() != null;
        assert to.getUser().getName() != null;
        assert to.getUser().getName().equals("")==false;
	}
	public void post_storeMessage(Message msg, Address to)
	{
        File mboxFile = new File(to.getUser().getPath() + Mbox.PATH_SEP + to.getUser().getName());
        assert mboxFile.exists();
	}
    
    public void pre_createMessageFromMboxData(String[] msg, User user)
    {
        assert msg != null;
        assert msg.length >= 6;
        
        assert user != null;
        assert user.getName() != null;
        assert user.getName().equals("")==false;
    }
    public void post_createMessageFromMboxData(String[] msg, User user)
    {
    }
    
    public void pre_getMessages(User user)
    {
        assert user != null;
        assert user.getName() != null;
        assert user.getName().equals("")==false;
        
        // record if a mailbox exists for this user
        File mboxFile = new File(user.getPath() + Mbox.PATH_SEP + user.getName());
        super.setPreconditionValue("mailbox-exists", new Boolean(mboxFile.exists()));
    }
    public void post_getMessages(User user)
    {
        // if a mailbox existed for the user, then there should be 1 or more messages!
        if (super.getPreconditionValue("mailbox-exists").equals(Boolean.TRUE)) {
            assert super.getReturnValue() != null;
            assert ((Message[])super.getReturnValue()).length > 0;
        } else {
            assert super.getReturnValue() != null;
            assert ((Message[])super.getReturnValue()).length == 0;
        }
    }
}
