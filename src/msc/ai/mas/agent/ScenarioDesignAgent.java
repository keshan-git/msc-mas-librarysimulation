package msc.ai.mas.agent;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import msc.ai.mas.listeners.ScenarionDesignListener;
import msc.ai.mas.ui.AgentUIManager;
import msc.ai.mas.ui.panel.MessageLogWindow;
import msc.ai.mas.ui.panel.ScenarioDesignWindow;
import msc.ai.mas.utils.AgentVocabulary;

/**
 *
 * @author Keshan De Silva
 */
public class ScenarioDesignAgent extends Agent
{
    @Override
    protected void setup()
    {
        addBehaviour(new OneShotBehaviour(this)
        {
            
            @Override
            public void action()
            {
                ScenarioDesignWindow scenarioDesignWindow = 
                new ScenarioDesignWindow(new ScenarionDesignListener()
                {

                    @Override
                    public void onScenarioSetup(int studentCount, int lecCount, 
                            int libCount, int deskCount, int assisCount, int supCount)
                    {
                        AgentContainer agentContainer = getAgent().getContainerController();
                        Object[] args;
                        try
                        {
                            for (int i = 0; i < studentCount; i++)
                            {
                                agentContainer.createNewAgent("Student " + (i + 1) , "msc.ai.mas.agent.StudentAgent", null).start();
                            }
                            
                            for (int i = 0; i < lecCount; i++)
                            {
                                agentContainer.createNewAgent("Lecturer " + (i + 1) , "msc.ai.mas.agent.LectureAgent", null).start();
                            }
                            
                            for (int i = 0; i < libCount; i++)
                            {
                                agentContainer.createNewAgent("Librarian "  + (i + 1), "msc.ai.mas.agent.LibrarianAgent", null).start();
                            }
                            
                            for (int i = 0; i < deskCount; i++)
                            {
                                if ((i % 3) == 0)
                                {
                                    args = new Object[]{AgentVocabulary.SERVICE_TYPE_SEARCH_BOOK};
                                }
                                else if ((i % 3) == 1)
                                {
                                    args = new Object[]{AgentVocabulary.SERVICE_TYPE_LEND_BOOK};
                                }
                                else
                                {
                                    args = new Object[]{AgentVocabulary.SERVICE_TYPE_RETURN_BOOK};
                                }

                                agentContainer.createNewAgent("Front Desk Operator "  + (i + 1), "msc.ai.mas.agent.FrontDeskOperatorAgent", args).start();
                            }
                            
                            for (int i = 0; i < assisCount; i++)
                            {
                                agentContainer.createNewAgent("Library Assistant "  + (i + 1), "msc.ai.mas.agent.LibraryAssistantAgent", null).start();
                            }
                            
                            for (int i = 0; i < supCount; i++)
                            {
                                agentContainer.createNewAgent("Book Supplier "  + (i + 1), "msc.ai.mas.agent.BookSupplierAgent", null).start();
                            }
                        }
                        catch (Exception ex){}
                    }
                });
                scenarioDesignWindow.setVisible(true);
            }
        });
        
//        addBehaviour(new OneShotBehaviour(this)
//        {
//            @Override
//            public void action()
//            {
//                AgentContainer agentContainer = getAgent().getContainerController();
//                Object[] args;
//                try
//                {
//                    args = new Object[]{AgentConstants.SERVICE_TYPE_LEND_BOOK};
//                    agentContainer.createNewAgent("Front Desk Operator 1", "msc.ai.mas.agent.FrontDeskOperatorAgent", args).start();
//                            
//                    args = new Object[]{AgentConstants.SERVICE_TYPE_LEND_BOOK};
//                    agentContainer.createNewAgent("Front Desk Operator 2", "msc.ai.mas.agent.FrontDeskOperatorAgent", args).start();
//                    
//                    args = new Object[]{AgentConstants.SERVICE_TYPE_RETURN_BOOK};
//                    agentContainer.createNewAgent("Front Desk Operator 3", "msc.ai.mas.agent.FrontDeskOperatorAgent", args).start();
//                    
//                    args = new Object[]{AgentConstants.SERVICE_TYPE_LEND_AND_RETURN_BOOK};
//                    agentContainer.createNewAgent("Front Desk Operator 4", "msc.ai.mas.agent.FrontDeskOperatorAgent", args).start();
//                    
//                    args = new Object[]{AgentConstants.SERVICE_TYPE_SEARCH_BOOK};
//                    agentContainer.createNewAgent("Front Desk Operator 5", "msc.ai.mas.agent.FrontDeskOperatorAgent", args).start();
//
//                    agentContainer.createNewAgent("Librarian", "msc.ai.mas.agent.LibrarianAgent", null).start();
//                    
//                    agentContainer.createNewAgent("Library Assistant 1", "msc.ai.mas.agent.LibraryAssistantAgent", null).start();
//                    agentContainer.createNewAgent("Library Assistant 2", "msc.ai.mas.agent.LibraryAssistantAgent", null).start();
//                    agentContainer.createNewAgent("Library Assistant 3", "msc.ai.mas.agent.LibraryAssistantAgent", null).start();
//                    
//                    agentContainer.createNewAgent("Book Supplier 1", "msc.ai.mas.agent.BookSupplierAgent", null).start();
//                    agentContainer.createNewAgent("Book Supplier 2", "msc.ai.mas.agent.BookSupplierAgent", null).start();
//                    agentContainer.createNewAgent("Book Supplier 3", "msc.ai.mas.agent.BookSupplierAgent", null).start();
//                
//                    agentContainer.createNewAgent("Student 1", "msc.ai.mas.agent.StudentAgent", null).start();
//                    //agentContainer.createNewAgent("Student 2", "msc.ai.mas.agent.StudentAgent", null).start();
//                    
//                    agentContainer.createNewAgent("Lecture 1", "msc.ai.mas.agent.LectureAgent", null).start();
//                }
//                catch (Exception exception)
//                {
//                    exception.printStackTrace();
//                }
//            }
//        });
               
        addBehaviour(new OneShotBehaviour(this)
        {
            @Override
            public void action()
            {
                MessageLogWindow.getInstance().showWindow();
            }
        });
    }
}
