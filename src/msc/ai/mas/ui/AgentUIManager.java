package msc.ai.mas.ui;

import jade.core.Agent;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.HashMap;
import msc.ai.mas.ui.panel.AgentWindow;

/**
 *
 * @author Keshan De Silva
 */
public final class AgentUIManager
{
    private static final HashMap<Agent, AgentWindow> agentWindowMap = new HashMap<>();

    private static int windowWidth = 460;
    private static int windowHeight = 250;
    private static int windowCount = 0;
    
    public static Point getInitialPosition()
    {
        synchronized (agentWindowMap)
        {
            int xPosition = windowWidth * (windowCount % 3);
            int yPosition = windowHeight * (windowCount / 3);
            //System.out.println("Count : " + windowCount + " X:" + xPosition + " Y: " + yPosition);
            windowCount++;
            return new Point(xPosition, yPosition);    
        }
    }
        
    public static void addAgentWindow(Agent agent, AgentWindow agentWindow)
    {
        synchronized (agentWindowMap)
        {
            agentWindowMap.put(agent, agentWindow);
        }
    }
    
    public static AgentWindow getAgentWindow(Agent agent)
    {
        synchronized (agentWindowMap)
        {
            return agentWindowMap.get(agent);
        }
    }
}
