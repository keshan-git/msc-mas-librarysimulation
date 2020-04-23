package msc.ai.mas.utils;

/**
 *
 * @author Keshan De Silva
 */
public class MathUtils
{
    private MathUtils(){}
    
    public static boolean generateRandomBoolean()
    {
        return Math.random() > 0.5;
    }
    
    public static boolean generateRandomBoolean(double boundary)
    {
        return Math.random() > (1 - boundary);
    }
    
    public static int generateRandomeBookPrice()
    {
        return (int)(Math.random() * 2500);
    }
    
    public static String getBookLocationDetails()
    {
        char shelfName = (char)(Math.round(Math.random() * 10) + 65);
        int sectionNUmber = (int)Math.round(Math.random() * 10);
        
        return "Available on Section : " + sectionNUmber + " at Shelf " + shelfName;
    }
}
