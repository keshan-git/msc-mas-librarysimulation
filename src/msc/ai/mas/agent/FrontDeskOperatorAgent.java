package msc.ai.mas.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import msc.ai.mas.behaviour.operations.SearchBookBehaviour;
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
public class FrontDeskOperatorAgent extends Agent implements LibraryServices
{
    @Override
    protected void setup()
    {
        String operatorType = (String)getArguments()[0];

        ServiceDescription serviceDescription  = new ServiceDescription();
        serviceDescription.setType(operatorType);
        serviceDescription.setName(getLocalName());
        AgentUtils.registerAgent(this, serviceDescription);
        
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
                                
                                if (MathUtils.generateRandomBoolean(0.9))
                                {
                                    searchBook(messageContent[1], message.getSender());
                                }
                                else
                                {
                                    sendBusyMessage(message);
                                }
                                break;
                            }
                            case AgentVocabulary.LEND_REQUEST :
                            {
                                if (MathUtils.generateRandomBoolean(0.9))
                                {
                                    lendBook(messageContent[1], message.getSender());
                                }
                                else
                                {
                                    sendBusyMessage(message);
                                }
                                break;
                            }
                            case AgentVocabulary.RETURN_REQUEST :
                            {
                                if (MathUtils.generateRandomBoolean(0.9))
                                {
                                    returnBook(messageContent[1], message.getSender());
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
        System.out.println("Agent : '" + getLocalName() + "' serach agents to assign search task (" 
                + bookTitle + ")");
        AgentUIManager.getAgentWindow(this).addLog("Agent : '" + getLocalName() + "' serach agents to assign search task (" 
                + bookTitle + ")");
        
        SearchBookBehaviour searchBookBehaviour = new SearchBookBehaviour(this, bookTitle, messageSender);
        this.addBehaviour(searchBookBehaviour);
    }

    private void lendBook(String bookTitle, AID messageSender)
    {
        System.out.println("Agent : '" + getLocalName() + "' lend book (" + bookTitle 
                + ") to " + messageSender.getLocalName());
        AgentUIManager.getAgentWindow(this).addLog("Agent : '" + getLocalName() + "' lend book (" + bookTitle 
                + ") to " + messageSender.getLocalName());
        
        String messageContent = AgentVocabulary.LEND_RESULT + AgentVocabulary.REQUEST_SEPARATOR + bookTitle;
        AgentUtils.sendMessage(this, messageSender, ACLMessage.CONFIRM, messageContent);
    }

    private void returnBook(String bookTitle, AID messageSender)
    {
        System.out.println("Agent : '" + getLocalName() + "' return book (" + bookTitle 
                + ")" + messageSender.getLocalName());
        AgentUIManager.getAgentWindow(this).addLog("Agent : '" + getLocalName() + "' return book (" + bookTitle 
                + ")" + messageSender.getLocalName());
        
        String messageContent = AgentVocabulary.RETURN_RESULT + AgentVocabulary.REQUEST_SEPARATOR + bookTitle;
        AgentUtils.sendMessage(this, messageSender, ACLMessage.CONFIRM, messageContent);
    }
    
    @Override
    public void sendBusyMessage(ACLMessage message)
    {
        System.out.println("Sorry, I am bit busy right now. (" + getAID().getLocalName()+ ")");
        AgentUIManager.getAgentWindow(this).addLog("Sorry, I am bit busy right now. (" + getAID().getLocalName()+ ")");
        
        AgentUtils.sendMessage(this, message.getSender(), ACLMessage.REFUSE, "Busy");
    }
}
