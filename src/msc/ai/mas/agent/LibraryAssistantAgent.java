package msc.ai.mas.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import msc.ai.mas.behaviour.request.MessageReceiveBehaviour;
import msc.ai.mas.listeners.MessageReceiveListener;
import msc.ai.mas.services.LibraryServices;
import msc.ai.mas.ui.AgentUIManager;
import msc.ai.mas.ui.panel.AgentWindow;
import msc.ai.mas.utils.AgentVocabulary;
import msc.ai.mas.utils.AgentUtils;
import msc.ai.mas.utils.MathUtils;

/**
 *
 * @author Keshan De Silva
 */
public class LibraryAssistantAgent extends Agent implements LibraryServices
{
    @Override
    protected void setup()
    {
        ServiceDescription locationServiceDescription  = new ServiceDescription();
        locationServiceDescription.setType(AgentVocabulary.SERVICE_TYPE_SEARCH_BOOK_LOCATION);
        locationServiceDescription.setName(getLocalName());

        AgentUtils.registerAgent(this, locationServiceDescription);
        
        AgentWindow agentWindow = new AgentWindow(this);
        AgentUIManager.addAgentWindow(this, agentWindow);
        agentWindow.showWindow();
        
        addBehaviour(new MessageReceiveBehaviour(this, new MessageReceiveListener()
        {
            @Override
            public void onMessageReceived(ACLMessage message)
            {
                if (message.getPerformative() == ACLMessage.REQUEST)
                {
                    if (message.getContent() != null)
                    {
                        String[] messageContent = message.getContent().split("#");
                        switch (Integer.parseInt(messageContent[0]))
                        {
                            case AgentVocabulary.SEARCH_REQUEST :
                            {
                                if (MathUtils.generateRandomBoolean(0.7))
                                {
                                    searchBook(messageContent[1], message.getSender());
                                }
                                else
                                {
                                    sendBusyMessage(message);
                                }
                                break;
                            }    
                        }    
                    }
                }
            }
        }));
    }
    
    private void searchBook(String bookTitle, AID messageSender)
    {
        System.out.println("Agent : '" + getLocalName() + "' search book location (" 
                + bookTitle + ")");
        
        AgentUIManager.getAgentWindow(this).addLog("Agent : '" + getLocalName() + "' search book location (" 
                + bookTitle + ")");
        
        AgentUIManager.getAgentWindow(this).addLog(MathUtils.getBookLocationDetails());

        if (true) // book found
        {
            String messageContent = AgentVocabulary.SEARCH_RESULT + AgentVocabulary.REQUEST_SEPARATOR + bookTitle;
            AgentUtils.sendMessage(this, messageSender, ACLMessage.CONFIRM, messageContent);
        }
    }
    
    @Override
    public void sendBusyMessage(ACLMessage message)
    {
        System.out.println("Sorry, I am bit busy right now. (" + getAID().getLocalName()+ ")");
        AgentUIManager.getAgentWindow(this).addLog("Sorry, I am bit busy right now. (" + getAID().getLocalName()+ ")");
        
        AgentUtils.sendMessage(this, message.getSender(), ACLMessage.REFUSE, "Busy");
    }
}
