/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: Db_reference.java,v 1.1 2006/06/30 14:03:32 sheng Exp $
 */

package cn.ac.rcpa.bio.database.ebi.protein.entry;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * 
 * 
 * @version $Revision: 1.1 $ $Date: 2006/06/30 14:03:32 $
**/
public class Db_reference implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private java.lang.String _db;

    private java.lang.String _primary_identifier;

    private java.lang.String _secondary_identifier;

    private java.lang.String _tertiary_identifier;


      //----------------/
     //- Constructors -/
    //----------------/

    public Db_reference() {
        super();
    } //-- cn.ac.rcpa.bio.database.ebi.protein.entry.Db_reference()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'db'.
     * 
     * @return the value of field 'db'.
    **/
    public java.lang.String getDb()
    {
        return this._db;
    } //-- java.lang.String getDb() 

    /**
     * Returns the value of field 'primary_identifier'.
     * 
     * @return the value of field 'primary_identifier'.
    **/
    public java.lang.String getPrimary_identifier()
    {
        return this._primary_identifier;
    } //-- java.lang.String getPrimary_identifier() 

    /**
     * Returns the value of field 'secondary_identifier'.
     * 
     * @return the value of field 'secondary_identifier'.
    **/
    public java.lang.String getSecondary_identifier()
    {
        return this._secondary_identifier;
    } //-- java.lang.String getSecondary_identifier() 

    /**
     * Returns the value of field 'tertiary_identifier'.
     * 
     * @return the value of field 'tertiary_identifier'.
    **/
    public java.lang.String getTertiary_identifier()
    {
        return this._tertiary_identifier;
    } //-- java.lang.String getTertiary_identifier() 

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
     * Sets the value of field 'db'.
     * 
     * @param db the value of field 'db'.
    **/
    public void setDb(java.lang.String db)
    {
        this._db = db;
    } //-- void setDb(java.lang.String) 

    /**
     * Sets the value of field 'primary_identifier'.
     * 
     * @param primary_identifier the value of field
     * 'primary_identifier'.
    **/
    public void setPrimary_identifier(java.lang.String primary_identifier)
    {
        this._primary_identifier = primary_identifier;
    } //-- void setPrimary_identifier(java.lang.String) 

    /**
     * Sets the value of field 'secondary_identifier'.
     * 
     * @param secondary_identifier the value of field
     * 'secondary_identifier'.
    **/
    public void setSecondary_identifier(java.lang.String secondary_identifier)
    {
        this._secondary_identifier = secondary_identifier;
    } //-- void setSecondary_identifier(java.lang.String) 

    /**
     * Sets the value of field 'tertiary_identifier'.
     * 
     * @param tertiary_identifier the value of field
     * 'tertiary_identifier'.
    **/
    public void setTertiary_identifier(java.lang.String tertiary_identifier)
    {
        this._tertiary_identifier = tertiary_identifier;
    } //-- void setTertiary_identifier(java.lang.String) 

    /**
     * 
     * 
     * @param reader
    **/
    public static cn.ac.rcpa.bio.database.ebi.protein.entry.Db_reference unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (cn.ac.rcpa.bio.database.ebi.protein.entry.Db_reference) Unmarshaller.unmarshal(cn.ac.rcpa.bio.database.ebi.protein.entry.Db_reference.class, reader);
    } //-- cn.ac.rcpa.bio.database.ebi.protein.entry.Db_reference unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
