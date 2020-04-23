package msc.ai.mas.ontology;

import java.io.Serializable;

/**
 *
 * @author Keshan De Silva
 */
public class BookDetails implements Serializable
{
    private String bookTitle;
    private String supplierName;
    private int price;

    public BookDetails()
    {
    }

    public String getBookTitle()
    {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle)
    {
        this.bookTitle = bookTitle;
    }

    public String getSupplierName()
    {
        return supplierName;
    }

    public void setSupplierName(String supplierName)
    {
        this.supplierName = supplierName;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return "BookDetails {" + bookTitle + "," + supplierName + "," + price + '}';
    }  
}
