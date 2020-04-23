package msc.ai.mas.listeners;

import jade.lang.acl.ACLMessage;

/**
 *
 * @author Keshan De Silva
 */
public interface MessageReceiveListener
{
    public void onMessageReceived(ACLMessage message);
}
