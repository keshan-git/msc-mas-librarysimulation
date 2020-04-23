package msc.ai.mas.ui.tablemodels;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import msc.ai.mas.model.MessageModel;

/**
 *
 * @author Keshan De Silva
 */
public class MessageLogTableModel extends AbstractTableModel
{
    private String[] columnNames = new String[]{"Time", "From", "To", "Message"};
    private ArrayList<MessageModel> dataSet = new ArrayList<>();
    
    public void addLogMessage(MessageModel messageModel)
    {
        dataSet.add(messageModel);
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount()
    {
        return dataSet.size();
    }

    @Override
    public int getColumnCount()
    {
        return 4;
    }

    @Override
    public Class<?> getColumnClass(int column)
    {
        return String.class;
    }

    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int column)
    {
        MessageModel messageModel = dataSet.get(row);
        
        switch (column)
        {
            case 0 : return messageModel.getTimeStamp().toString();
            case 1 : return messageModel.getSender().getLocalName();
            case 2 : return messageModel.getReceiver().getLocalName();
            case 3 : return messageModel.getContaint();
        }
        
        return "";
    }
    
}
