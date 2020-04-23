package msc.ai.mas.behaviour.request;

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
public class SearchBookRequestBehaviour extends OneShotBehaviour
{
    private String bookTitle;

    public SearchBookRequestBehaviour(Agent agent, String bookTitle)
    {
        super(agent);
        this.bookTitle = bookTitle;
    }

    @Override
    public void action()
    {
        System.out.println("Agent : " + getAgent().getLocalName() + " Search for book : " + bookTitle);
        AgentUIManager.getAgentWindow(getAgent()).addLog("Search for book : " + bookTitle);
        
        // Get all Agent who can serach books
        ArrayList<DFAgentDescription> agentDescriptions = AgentUtils.getRegisteredAgents(getAgent(), AgentVocabulary.SERVICE_TYPE_SEARCH_BOOK);    
        for (DFAgentDescription agentDescription : agentDescriptions)
        {
            System.out.println("Found : " + agentDescription.getName().getLocalName());
            System.out.println("Send search request to : " + agentDescription.getName().getLocalName());
            
            AgentUIManager.getAgentWindow(getAgent()).addLog("Found : " + agentDescription.getName().getLocalName());
            AgentUIManager.getAgentWindow(getAgent()).addLog("Send search request to : " + agentDescription.getName().getLocalName());
                            
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
                        + "' for your support.");
                AgentUIManager.getAgentWindow(getAgent()).addLog("Thank you '" + replyMessage.getSender().getLocalName() 
                        + "' for your support.");
                break;
            }
        }        
    }  
}
