package msc.ai.mas.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import msc.ai.mas.behaviour.operations.RequestNewBookBehaviour;
import msc.ai.mas.behaviour.request.MessageReceiveBehaviour;
import msc.ai.mas.listeners.MessageReceiveListener;
import msc.ai.mas.services.LibraryServices;
import msc.ai.mas.ui.AgentUIManager;
import msc.ai.mas.ui.panel.AgentWindow;
import msc.ai.mas.utils.AgentVocabulary;
import msc.ai.mas.utils.AgentUtils;

/**
 *
 * @author Keshan De Silva
 */
public class LibrarianAgent extends Agent implements LibraryServices
{
    private boolean interupFlag = false;
    
    @Override
    protected void setup()
    {
        ServiceDescription locationServiceDescription  = new ServiceDescription();
        locationServiceDescription.setType(AgentVocabulary.SERVICE_TYPE_NEW_BOOK);
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
                            case AgentVocabulary.NEW_BOOK_REQUEST :
                            {
                                contactSupplier(messageContent[1], message.getSender());
                                break;
                            }    
                        }    
                    }
                }
            }
        }));
        
        addBehaviour(new TickerBehaviour(this, 12000)
        {   
            @Override
            protected void onTick()
            {
                if (!interupFlag)
                {
                    System.out.println("Agent : " + getAgent().getLocalName() + "Updating book catalog");
                    AgentUIManager.getAgentWindow(getAgent()).addLog("Agent : " 
                                                 + getAgent().getLocalName() + "Updating book catalog");
                }
            }
        });
    }
    
    private void contactSupplier(String bookTitle, AID messageSender)
    {
        interupFlag = true;
        System.out.println("'" + getLocalName() + "' serach agents to assign supply task (" 
                + bookTitle + ")");
        AgentUIManager.getAgentWindow(this).addLog("'" + getLocalName() + "' serach agents to assign supply task (" 
                + bookTitle + ")");
        
        RequestNewBookBehaviour requestNewBookBehaviour = new RequestNewBookBehaviour
                                                                    (this, bookTitle, messageSender);
        this.addBehaviour(requestNewBookBehaviour);
    }
    
    @Override
    public void sendBusyMessage(ACLMessage message)
    {
        System.out.println("Sorry, I am bit busy right now. (" + getAID().getLocalName()+ ")");
        AgentUIManager.getAgentWindow(this).addLog("");
                
        AgentUtils.sendMessage(this, message.getSender(), ACLMessage.REFUSE, "Busy");
    }
}
