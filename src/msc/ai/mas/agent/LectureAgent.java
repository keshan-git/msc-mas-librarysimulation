package msc.ai.mas.agent;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import msc.ai.mas.behaviour.request.LendBookRequestBehaviour;
import msc.ai.mas.behaviour.request.MessageReceiveBehaviour;
import msc.ai.mas.behaviour.request.NewBookRequestBehaviour;
import msc.ai.mas.behaviour.request.ReturnBookRequestBehaviour;
import msc.ai.mas.behaviour.request.SearchBookRequestBehaviour;
import msc.ai.mas.listeners.AgentActionListener;
import msc.ai.mas.listeners.MessageReceiveListener;
import msc.ai.mas.ui.AgentUIManager;
import msc.ai.mas.ui.panel.AgentWindow;
import msc.ai.mas.utils.AgentVocabulary;

/**
 *
 * @author Keshan De Silva
 */
public class LectureAgent extends Agent
{

    @Override
    protected void setup()
    {
        addBehaviour(getMessageReceiveBehaviour());
        
        AgentWindow agentWindow = new AgentWindow(this);
        AgentUIManager.addAgentWindow(this, agentWindow);
        agentWindow.showWindow();
        
//        agentWindow.addAgentActionButton(new AgentActionListener()
//        {
//            @Override
//            public String getActionName()
//            {
//                return "Search";
//            }
//
//            @Override
//            public void actionPerformed(ActionEvent ae)
//            {
//                String bookTitle = JOptionPane.showInputDialog(agentWindow, "Book Title");
//                searchBook(bookTitle);
//            }
//        });
        
//        agentWindow.addAgentActionButton(new AgentActionListener()
//        {
//            @Override
//            public String getActionName()
//            {
//                return "Lend";
//            }
//
//            @Override
//            public void actionPerformed(ActionEvent ae)
//            {
//                String bookTitle = JOptionPane.showInputDialog(agentWindow, "Book Title");
//                lendBook(bookTitle);
//            }
//        });
        
//        agentWindow.addAgentActionButton(new AgentActionListener()
//        {
//            @Override
//            public String getActionName()
//            {
//                return "Return";
//            }
//
//            @Override
//            public void actionPerformed(ActionEvent ae)
//            {
//                String bookTitle = JOptionPane.showInputDialog(agentWindow, "Book Title");
//                returnBook(bookTitle);
//            }
//        });
        
        agentWindow.addAgentActionButton(new AgentActionListener()
        {
            @Override
            public String getActionName()
            {
                return "Request New Book";
            }

            @Override
            public void actionPerformed(ActionEvent ae)
            {
                String bookTitle = JOptionPane.showInputDialog(agentWindow, "Book Title");
                requestNewBook(bookTitle);
            }
        });
    }

    private void searchBook(String bookTitle)
    {
        if ((bookTitle != null) && (!bookTitle.equals("")))
        {
            SearchBookRequestBehaviour searchBookBehaviour = new SearchBookRequestBehaviour(this, bookTitle);
            addBehaviour(searchBookBehaviour);
        }
    }
    
    private void lendBook(String bookTitle)
    {
        if ((bookTitle != null) && (!bookTitle.equals("")))
        {
            ReturnBookRequestBehaviour returnBookBehaviour = new ReturnBookRequestBehaviour(this, bookTitle);
            addBehaviour(returnBookBehaviour);
        }
    }
    
    private void returnBook(String bookTitle)
    {
        if ((bookTitle != null) && (!bookTitle.equals("")))
        {
            LendBookRequestBehaviour lendBookBehaviour = new LendBookRequestBehaviour(this, bookTitle);
            addBehaviour(lendBookBehaviour);
        }
    }
    
    private void requestNewBook(String bookTitle)
    {
        if ((bookTitle != null) && (!bookTitle.equals("")))
        {
            NewBookRequestBehaviour newBookRequestBehaviour = new NewBookRequestBehaviour(this, bookTitle);
            addBehaviour(newBookRequestBehaviour);
        }
    }
    
    private MessageReceiveBehaviour getMessageReceiveBehaviour()
    {
        return new MessageReceiveBehaviour(this, new MessageReceiveListener()
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
                                searchBook(messageContent[1]);
                                break;
                            }
                            case AgentVocabulary.LEND_REQUEST :
                            {
                                lendBook(messageContent[1]);
                                break;
                            }
                            case AgentVocabulary.RETURN_REQUEST :
                            {
                                returnBook(messageContent[1]);
                                break;
                            }
                            case AgentVocabulary.NEW_BOOK_REQUEST :
                            {
                                requestNewBook(messageContent[1]);
                                break;
                            }
                        }    
                    }
                }
            }
        });
    }
}
