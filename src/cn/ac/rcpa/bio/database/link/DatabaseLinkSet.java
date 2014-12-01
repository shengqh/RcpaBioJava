/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: DatabaseLinkSet.java,v 1.1 2006/06/30 14:03:37 sheng Exp $
 */

package cn.ac.rcpa.bio.database.link;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.ArrayList;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * 
 * 
 * @version $Revision: 1.1 $ $Date: 2006/06/30 14:03:37 $
**/
public class DatabaseLinkSet implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private java.util.ArrayList _databaseLinkList;


      //----------------/
     //- Constructors -/
    //----------------/

    public DatabaseLinkSet() {
        super();
        _databaseLinkList = new ArrayList();
    } //-- cn.ac.rcpa.bio.database.link.DatabaseLinkSet()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vDatabaseLink
    **/
    public void addDatabaseLink(DatabaseLink vDatabaseLink)
        throws java.lang.IndexOutOfBoundsException
    {
        _databaseLinkList.add(vDatabaseLink);
    } //-- void addDatabaseLink(DatabaseLink) 

    /**
     * 
     * 
     * @param index
     * @param vDatabaseLink
    **/
    public void addDatabaseLink(int index, DatabaseLink vDatabaseLink)
        throws java.lang.IndexOutOfBoundsException
    {
        _databaseLinkList.add(index, vDatabaseLink);
    } //-- void addDatabaseLink(int, DatabaseLink) 

    /**
    **/
    public void clearDatabaseLink()
    {
        _databaseLinkList.clear();
    } //-- void clearDatabaseLink() 

    /**
    **/
    public java.util.Enumeration enumerateDatabaseLink()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_databaseLinkList.iterator());
    } //-- java.util.Enumeration enumerateDatabaseLink() 

    /**
     * 
     * 
     * @param index
    **/
    public DatabaseLink getDatabaseLink(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _databaseLinkList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (DatabaseLink) _databaseLinkList.get(index);
    } //-- DatabaseLink getDatabaseLink(int) 

    /**
    **/
    public DatabaseLink[] getDatabaseLink()
    {
        int size = _databaseLinkList.size();
        DatabaseLink[] mArray = new DatabaseLink[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (DatabaseLink) _databaseLinkList.get(index);
        }
        return mArray;
    } //-- DatabaseLink[] getDatabaseLink() 

    /**
    **/
    public int getDatabaseLinkCount()
    {
        return _databaseLinkList.size();
    } //-- int getDatabaseLinkCount() 

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
     * @param vDatabaseLink
    **/
    public boolean removeDatabaseLink(DatabaseLink vDatabaseLink)
    {
        boolean removed = _databaseLinkList.remove(vDatabaseLink);
        return removed;
    } //-- boolean removeDatabaseLink(DatabaseLink) 

    /**
     * 
     * 
     * @param index
     * @param vDatabaseLink
    **/
    public void setDatabaseLink(int index, DatabaseLink vDatabaseLink)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _databaseLinkList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _databaseLinkList.set(index, vDatabaseLink);
    } //-- void setDatabaseLink(int, DatabaseLink) 

    /**
     * 
     * 
     * @param databaseLinkArray
    **/
    public void setDatabaseLink(DatabaseLink[] databaseLinkArray)
    {
        //-- copy array
        _databaseLinkList.clear();
        for (int i = 0; i < databaseLinkArray.length; i++) {
            _databaseLinkList.add(databaseLinkArray[i]);
        }
    } //-- void setDatabaseLink(DatabaseLink) 

    /**
     * 
     * 
     * @param reader
    **/
    public static cn.ac.rcpa.bio.database.link.DatabaseLinkSet unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (cn.ac.rcpa.bio.database.link.DatabaseLinkSet) Unmarshaller.unmarshal(cn.ac.rcpa.bio.database.link.DatabaseLinkSet.class, reader);
    } //-- cn.ac.rcpa.bio.database.link.DatabaseLinkSet unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
