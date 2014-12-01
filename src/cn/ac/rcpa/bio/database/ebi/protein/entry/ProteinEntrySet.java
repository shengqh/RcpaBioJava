/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: ProteinEntrySet.java,v 1.1 2006/06/30 14:03:32 sheng Exp $
 */

package cn.ac.rcpa.bio.database.ebi.protein.entry;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.ArrayList;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * one or more proteinentry
 * 
 * @version $Revision: 1.1 $ $Date: 2006/06/30 14:03:32 $
**/
public class ProteinEntrySet implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * IPI or SwissProt Entry
    **/
    private java.util.ArrayList _proteinEntryList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ProteinEntrySet() {
        super();
        _proteinEntryList = new ArrayList();
    } //-- cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntrySet()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vProteinEntry
    **/
    public void addProteinEntry(ProteinEntry vProteinEntry)
        throws java.lang.IndexOutOfBoundsException
    {
        _proteinEntryList.add(vProteinEntry);
    } //-- void addProteinEntry(ProteinEntry) 

    /**
     * 
     * 
     * @param index
     * @param vProteinEntry
    **/
    public void addProteinEntry(int index, ProteinEntry vProteinEntry)
        throws java.lang.IndexOutOfBoundsException
    {
        _proteinEntryList.add(index, vProteinEntry);
    } //-- void addProteinEntry(int, ProteinEntry) 

    /**
    **/
    public void clearProteinEntry()
    {
        _proteinEntryList.clear();
    } //-- void clearProteinEntry() 

    /**
    **/
    public java.util.Enumeration enumerateProteinEntry()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_proteinEntryList.iterator());
    } //-- java.util.Enumeration enumerateProteinEntry() 

    /**
     * 
     * 
     * @param index
    **/
    public ProteinEntry getProteinEntry(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _proteinEntryList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (ProteinEntry) _proteinEntryList.get(index);
    } //-- ProteinEntry getProteinEntry(int) 

    /**
    **/
    public ProteinEntry[] getProteinEntry()
    {
        int size = _proteinEntryList.size();
        ProteinEntry[] mArray = new ProteinEntry[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (ProteinEntry) _proteinEntryList.get(index);
        }
        return mArray;
    } //-- ProteinEntry[] getProteinEntry() 

    /**
    **/
    public int getProteinEntryCount()
    {
        return _proteinEntryList.size();
    } //-- int getProteinEntryCount() 

    /**
    **/
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * 
     * 
     * @param out
    **/
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * 
     * 
     * @param handler
    **/
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * 
     * 
     * @param vProteinEntry
    **/
    public boolean removeProteinEntry(ProteinEntry vProteinEntry)
    {
        boolean removed = _proteinEntryList.remove(vProteinEntry);
        return removed;
    } //-- boolean removeProteinEntry(ProteinEntry) 

    /**
     * 
     * 
     * @param index
     * @param vProteinEntry
    **/
    public void setProteinEntry(int index, ProteinEntry vProteinEntry)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _proteinEntryList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _proteinEntryList.set(index, vProteinEntry);
    } //-- void setProteinEntry(int, ProteinEntry) 

    /**
     * 
     * 
     * @param proteinEntryArray
    **/
    public void setProteinEntry(ProteinEntry[] proteinEntryArray)
    {
        //-- copy array
        _proteinEntryList.clear();
        for (int i = 0; i < proteinEntryArray.length; i++) {
            _proteinEntryList.add(proteinEntryArray[i]);
        }
    } //-- void setProteinEntry(ProteinEntry) 

    /**
     * 
     * 
     * @param reader
    **/
    public static cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntrySet unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntrySet) Unmarshaller.unmarshal(cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntrySet.class, reader);
    } //-- cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntrySet unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
