package msc.ai.mas.utils;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import msc.ai.mas.model.MessageModel;
import msc.ai.mas.ui.panel.MessageLogWindow;

/**
 *
 * @author Keshan De Silva
 */
public class AgentUtils
{
    private AgentUtils(){}
    
    public static void registerAgent(Agent agent, ServiceDescription serviceDescription)
    {
        DFAgentDescription agentDescription = new DFAgentDescription();
        agentDescription.setName(agent.getAID());
        agentDescription.addServices(serviceDescription);

        try
        {  
            DFService.register(agent, agentDescription);  
        }
        catch (FIPAException exception) 
        {
            exception.printStackTrace(); 
        }
    }
    
    public static ArrayList<DFAgentDescription> getRegisteredAgents(Agent agent, String... services)
    {
        ArrayList<DFAgentDescription> resultSet = new ArrayList<>();
        
        for (String service : services)
        {
            DFAgentDescription agentDescription = new DFAgentDescription();
            ServiceDescription serviceDescription = new ServiceDescription();
            serviceDescription.setType(service);
            agentDescription.addServices(serviceDescription);

            SearchConstraints searchConstraints = new SearchConstraints();
            searchConstraints.setMaxResults(new Long(-1));
            try
            {
                DFAgentDescription[] result = DFService.search(agent, agentDescription, searchConstraints);
                resultSet.addAll(Arrays.asList(result));
            }
            catch (FIPAException exception)
            {
                exception.printStackTrace();
            }
        }

        return resultSet;
    }

    public static void sendMessage(Agent sender, AID receiver, int messageType, String messageContent)
    {
        ACLMessage message = new ACLMessage(messageType);
        message.setContent(messageContent);
        message.addReceiver(receiver);
        try
        {
            Thread.sleep(3000);
        }
        catch (InterruptedException ex){}
        sender.send(message);
        
        MessageModel messageModel = new MessageModel();
        messageModel.setSender(sender);
        messageModel.setReceiver(receiver);
        messageModel.setContaint(messageContent);
        MessageLogWindow.getInstance().addLogMessage(messageModel);
    }
    
    public static void sendMessage(Agent sender, AID receiver, int messageType, Serializable content)
    {
        try
        {
            ACLMessage message = new ACLMessage(messageType);
            //message.setContent(messageContent);
            message.setContentObject(content);
            message.addReceiver(receiver);
            sender.send(message);

            MessageModel messageModel = new MessageModel();
            messageModel.setSender(sender);
            messageModel.setReceiver(receiver);
            messageModel.setContaint(content.toString());
            MessageLogWindow.getInstance().addLogMessage(messageModel);
        }
        catch (IOException ex){}
    }
}
