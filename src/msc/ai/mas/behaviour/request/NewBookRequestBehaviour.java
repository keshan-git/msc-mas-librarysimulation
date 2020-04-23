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
public class NewBookRequestBehaviour extends OneShotBehaviour
{
    private String bookTitle;

    public NewBookRequestBehaviour(Agent agent, String bookTitle)
    {
        super(agent);
        this.bookTitle = bookTitle;
    }

    @Override
    public void action()
    {
        block(5000);
        System.out.println("Agent : '" + getAgent().getLocalName() + "' Request New book : " + bookTitle);
        AgentUIManager.getAgentWindow(getAgent()).addLog("Agent : '" + getAgent().getLocalName() + "' Request New book : " + bookTitle);
                
        // Get all librarin agents
        ArrayList<DFAgentDescription> newBookAgentDescriptions = AgentUtils.getRegisteredAgents(getAgent(), AgentVocabulary.SERVICE_TYPE_NEW_BOOK);    
        for (DFAgentDescription agentDescription : newBookAgentDescriptions)
        {
            System.out.println("Found : " + agentDescription.getName().getLocalName());
            System.out.println("Send new book request to : " + agentDescription.getName().getLocalName());
            
            AgentUIManager.getAgentWindow(getAgent()).addLog("Found : " + agentDescription.getName().getLocalName());
            AgentUIManager.getAgentWindow(getAgent()).addLog("Send new book request to : " + agentDescription.getName().getLocalName());
                            
            String messageContent = AgentVocabulary.NEW_BOOK_REQUEST + AgentVocabulary.REQUEST_SEPARATOR + bookTitle;
            AgentUtils.sendMessage(getAgent(), agentDescription.getName(),
                                    ACLMessage.REQUEST, messageContent);
//            ACLMessage replyMessage;
//            do
//            {
//                replyMessage = getAgent().receive();
//            }
//            while (replyMessage == null);
//            
//            if (replyMessage.getPerformative() == ACLMessage.REFUSE)
//            {
//                System.out.println("It's OK '" + replyMessage.getSender().getLocalName() 
//                        + "' I will contact next person.");
//            }
//            else if (replyMessage.getPerformative() == ACLMessage.CONFIRM)
//            {
//                System.out.println("Thank you '" + replyMessage.getSender().getLocalName() 
//                        + "' for your support.");
//                break;
//            }
        }
            
    }  
}
