package msc.ai.mas.utils;

/**
 *
 * @author Keshan De Silva
 */
public class AgentVocabulary
{
    private AgentVocabulary(){}
    
    // Front Desk Operator
    public static final String SERVICE_TYPE_SEARCH_BOOK = "serach";
    public static final String SERVICE_TYPE_LEND_BOOK = "lend";
    public static final String SERVICE_TYPE_RETURN_BOOK = "return";
    public static final String SERVICE_TYPE_LEND_AND_RETURN_BOOK = "lend_and_return";
    public static final String SERVICE_TYPE_SEARCH_BOOK_LOCATION = "serach_location";
    public static final String SERVICE_TYPE_NEW_BOOK = "new_book";
    public static final String SERVICE_TYPE_SUPPLY_NEW_BOOK = "new_book_supply";
        
    public static final String REQUEST_SEPARATOR = "#";
    public static final int LEND_REQUEST = 1;
    public static final int RETURN_REQUEST = 2;
    public static final int SEARCH_REQUEST = 3;
    public static final int NEW_BOOK_REQUEST = 4;
    
    public static final int LEND_RESULT = 10;
    public static final int RETURN_RESULT= 20;
    public static final int SEARCH_RESULT = 30;
    public static final int NEW_BOOK_RESULT = 40;
    //public static final int LEND_REQUEST = 1;
}
