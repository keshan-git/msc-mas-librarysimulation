package msc.ai.mas.behaviour.request;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import msc.ai.mas.listeners.MessageReceiveListener;

/**
 *
 * @author Keshan De Silva
 */
public class MessageReceiveBehaviour extends CyclicBehaviour
{
    private MessageReceiveListener messageReceiveListener;

    public MessageReceiveBehaviour(Agent agent, MessageReceiveListener messageReceiveListener)
    {
        super(agent);
        this.messageReceiveListener = messageReceiveListener;
    }

    @Override
    public void action()
    {
        ACLMessage msg = getAgent().receive();
        if (msg != null)
        {
            messageReceiveListener.onMessageReceived(msg);
        }
        block();
    }
    
}
