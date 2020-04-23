package msc.ai.mas.behaviour.operations;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import msc.ai.mas.ontology.BookDetails;
import msc.ai.mas.ui.AgentUIManager;
import msc.ai.mas.utils.AgentVocabulary;
import msc.ai.mas.utils.AgentUtils;

/**
 *
 * @author Keshan De Silva
 */
public class RequestNewBookBehaviour extends OneShotBehaviour
{
    private String bookTitle;
    private AID requestAgent;
    
    public RequestNewBookBehaviour(Agent agent, String bookTitle, AID requestAgent)
    {
        super(agent);
        this.bookTitle = bookTitle;
        this.requestAgent = requestAgent;
    }

    @Override
    public void action()
    {
        ArrayList<Integer> priceList = new ArrayList<>();
        
        // Get all Agent who can serach books
        ArrayList<DFAgentDescription> agentDescriptions = AgentUtils.getRegisteredAgents(getAgent(), AgentVocabulary.SERVICE_TYPE_SUPPLY_NEW_BOOK);    
        for (DFAgentDescription agentDescription : agentDescriptions)
        {
            System.out.println("Found : '" + agentDescription.getName().getLocalName() + "'");
            System.out.println("Check the avalibility of : " + bookTitle);
            
            AgentUIManager.getAgentWindow(getAgent()).addLog("Found : '" + agentDescription.getName().getLocalName() + "'");
            AgentUIManager.getAgentWindow(getAgent()).addLog("Check the avalibility of : " + bookTitle);
                                          
            String messageContent = AgentVocabulary.NEW_BOOK_REQUEST + AgentVocabulary.REQUEST_SEPARATOR + bookTitle;
            AgentUtils.sendMessage(getAgent(), agentDescription.getName(),
                                    ACLMessage.REQUEST, messageContent);
            
            ACLMessage replyMessage;
            do
            {
                replyMessage = getAgent().receive();
            }
            while (replyMessage == null);
            
            if (replyMessage.getPerformative() == ACLMessage.REFUSE)
            {
                System.out.println("It's OK '" + replyMessage.getSender().getLocalName() 
                        + "' I will contact next person.");
                AgentUIManager.getAgentWindow(getAgent()).addLog("It's OK '" + replyMessage.getSender().getLocalName() 
                        + "' I will contact next person.");
                        
            }
            else if (replyMessage.getPerformative() == ACLMessage.CONFIRM)
            {
                String split[] = replyMessage.getContent().split("#");
                int price = Integer.parseInt(split[split.length - 1]);
                
                System.out.println("Price Received from '" + replyMessage.getSender().getLocalName() 
                        + "' Rs : " + price);
                AgentUIManager.getAgentWindow(getAgent()).addLog("Price Received from '" + replyMessage.getSender().getLocalName() 
                        + "' Rs : " + price);
                
                System.out.println("Thank you '" + replyMessage.getSender().getLocalName() 
                        + "' for your coperation.");
                AgentUIManager.getAgentWindow(getAgent()).addLog("Thank you '" + replyMessage.getSender().getLocalName() 
                        + "' for your coperation.");
                
                priceList.add(price);
            }
            else if (replyMessage.getPerformative() == ACLMessage.PROPOSE)
            {
                try
                {
                    BookDetails bookDetails = (BookDetails)replyMessage.getContentObject();
                    int price = bookDetails.getPrice();

                    System.out.println("Price Received from '" + replyMessage.getSender().getLocalName() 
                            + "' Rs : " + price);
                    AgentUIManager.getAgentWindow(getAgent()).addLog("Price Received from '" + replyMessage.getSender().getLocalName() 
                            + "' Rs : " + price);

                    System.out.println("Thank you '" + replyMessage.getSender().getLocalName() 
                            + "' for your coperation.");
                    AgentUIManager.getAgentWindow(getAgent()).addLog("Thank you '" + replyMessage.getSender().getLocalName() 
                            + "' for your coperation.");

                    priceList.add(price);
                }
                catch (UnreadableException ex){}
            }
        }
        
        if (!priceList.isEmpty())
        {
            Collections.sort(priceList);
            System.out.println("Least price selected : Rs " + priceList.get(0));
            AgentUIManager.getAgentWindow(getAgent()).addLog("Least price selected : Rs " + priceList.get(0));
        }
    }  
}
