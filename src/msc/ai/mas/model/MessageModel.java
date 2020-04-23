package msc.ai.mas.model;

import jade.core.AID;
import jade.core.Agent;
import java.util.Date;

/**
 *
 * @author Keshan De Silva
 */
public class MessageModel
{
    private Date timeStamp;
    private Agent sender;
    private AID receiver;
    private String containt;

    public MessageModel()
    {
        this.timeStamp = new Date();
    }

    public Date getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public Agent getSender()
    {
        return sender;
    }

    public void setSender(Agent sender)
    {
        this.sender = sender;
    }

    public AID getReceiver()
    {
        return receiver;
    }

    public void setReceiver(AID receiver)
    {
        this.receiver = receiver;
    }

    public String getContaint()
    {
        return containt;
    }

    public void setContaint(String containt)
    {
        this.containt = containt;
    }
    
    
}
