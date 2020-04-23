package msc.ai.mas.behaviour.operations;

import jade.core.AID;
import msc.ai.mas.behaviour.request.*;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import msc.ai.mas.ui.AgentUIManager;
import msc.ai.mas.utils.AgentVocabulary;
import msc.ai.mas.utils.AgentUtils;

/**
 *
 * @author Keshan De Silva
 */
public class SearchBookBehaviour extends OneShotBehaviour
{
    private String bookTitle;
    private AID requestAgent;
    
    public SearchBookBehaviour(Agent agent, String bookTitle, AID requestAgent)
    {
        super(agent);
        this.bookTitle = bookTitle;
        this.requestAgent = requestAgent;
    }

    @Override
    public void action()
    {
        // Get all Agent who can serach books
        ArrayList<DFAgentDescription> agentDescriptions = AgentUtils.getRegisteredAgents(getAgent(), AgentVocabulary.SERVICE_TYPE_SEARCH_BOOK_LOCATION);    
        for (DFAgentDescription agentDescription : agentDescriptions)
        {
            System.out.println("Found : '" + agentDescription.getName().getLocalName() + "'");
            System.out.println("Check the avalibility of : " + bookTitle);
            
            AgentUIManager.getAgentWindow(getAgent()).addLog("Found : '" + agentDescription.getName().getLocalName() + "'");
            AgentUIManager.getAgentWindow(getAgent()).addLog("Check the avalibility of : " + bookTitle);
                            
            String messageContent = AgentVocabulary.SEARCH_REQUEST + AgentVocabulary.REQUEST_SEPARATOR + bookTitle;
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
                System.out.println("Thank you '" + replyMessage.getSender().getLocalName() 
                        + "' for your coperation.");
                AgentUIManager.getAgentWindow(getAgent()).addLog("Thank you '" + replyMessage.getSender().getLocalName() 
                        + "' for your coperation.");
                
                
                String bookDetails = AgentVocabulary.SEARCH_RESULT + AgentVocabulary.REQUEST_SEPARATOR + bookTitle;
                AgentUtils.sendMessage(getAgent(), requestAgent,
                                    ACLMessage.CONFIRM, bookDetails);
                break;
            }
        }        
    }  
}
