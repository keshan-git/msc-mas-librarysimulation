package msc.ai.mas.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import msc.ai.mas.behaviour.request.MessageReceiveBehaviour;
import msc.ai.mas.listeners.MessageReceiveListener;
import msc.ai.mas.ontology.BookDetails;
import msc.ai.mas.ui.AgentUIManager;
import msc.ai.mas.ui.panel.AgentWindow;
import msc.ai.mas.utils.AgentVocabulary;
import msc.ai.mas.utils.AgentUtils;
import msc.ai.mas.utils.MathUtils;

/**
 *
 * @author Keshan De Silva
 */
public class BookSupplierAgent extends Agent
{
    @Override
    protected void setup()
    {
        ServiceDescription locationServiceDescription  = new ServiceDescription();
        locationServiceDescription.setType(AgentVocabulary.SERVICE_TYPE_SUPPLY_NEW_BOOK);
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
                                if (MathUtils.generateRandomBoolean(0.9))
                                {
                                    contactSupplier(messageContent[1], message.getSender());
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
    
    private void contactSupplier(String bookTitle, AID messageSender)
    {
        int bookPrice = MathUtils.generateRandomeBookPrice();
        System.out.println("'" + getLocalName() + "' can supply book (" 
                + bookTitle + ") Price : " + bookPrice);
        AgentUIManager.getAgentWindow(this).addLog("'" + getLocalName() + "' can supply book (" 
                + bookTitle + ") Price : " + bookPrice);
                
        if (true) // book can be supply
        {
            //String messageContent = AgentVocabulary.NEW_BOOK_RESULT + AgentVocabulary.REQUEST_SEPARATOR
            //        + bookTitle + AgentVocabulary.REQUEST_SEPARATOR + bookPrice;
            //AgentUtils.sendMessage(this, messageSender, ACLMessage.CONFIRM, messageContent);
            
            BookDetails bookDetails = new BookDetails();
            bookDetails.setBookTitle(bookTitle);
            bookDetails.setSupplierName(this.getLocalName());
            bookDetails.setPrice(bookPrice);
            
            AgentUtils.sendMessage(this, messageSender, ACLMessage.PROPOSE, bookDetails);
            
        }
    }

    public void sendBusyMessage(ACLMessage message)
    {
        if (MathUtils.generateRandomBoolean(0.5))
        {
            System.out.println("Sorry, I am bit busy right now. (" + getAID().getLocalName()+ ")");
            AgentUIManager.getAgentWindow(this).addLog("Sorry, I am bit busy right now. (" + getAID().getLocalName()+ ")");

            AgentUtils.sendMessage(this, message.getSender(), ACLMessage.REFUSE, "Busy");
        }
        else
        {
            String[] messageContent = message.getContent().split("#");
            System.out.println("Sorry, Out of stocks '" + messageContent[1] + "'");
            AgentUIManager.getAgentWindow(this).addLog("Sorry, Out of stocks '" + messageContent[1] + "'");

            AgentUtils.sendMessage(this, message.getSender(), ACLMessage.REFUSE, "OutOfStock");
        }
    }
}
