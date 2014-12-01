/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: Feature_table.java,v 1.1 2006/06/30 14:03:32 sheng Exp $
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
public class Feature_table implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private java.lang.String _key_name;

    private int _sequence_from;

    /**
     * keeps track of state for field: _sequence_from
    **/
    private boolean _has_sequence_from;

    private int _sequence_to;

    /**
     * keeps track of state for field: _sequence_to
    **/
    private boolean _has_sequence_to;

    private java.lang.String _ft_description;


      //----------------/
     //- Constructors -/
    //----------------/

    public Feature_table() {
        super();
    } //-- cn.ac.rcpa.bio.database.ebi.protein.entry.Feature_table()


      //-----------/
     //- Methods -/
    //-----------/

    /**
    **/
    public void deleteSequence_from()
    {
        this._has_sequence_from= false;
    } //-- void deleteSequence_from() 

    /**
    **/
    public void deleteSequence_to()
    {
        this._has_sequence_to= false;
    } //-- void deleteSequence_to() 

    /**
     * Returns the value of field 'ft_description'.
     * 
     * @return the value of field 'ft_description'.
    **/
    public java.lang.String getFt_description()
    {
        return this._ft_description;
    } //-- java.lang.String getFt_description() 

    /**
     * Returns the value of field 'key_name'.
     * 
     * @return the value of field 'key_name'.
    **/
    public java.lang.String getKey_name()
    {
        return this._key_name;
    } //-- java.lang.String getKey_name() 

    /**
     * Returns the value of field 'sequence_from'.
     * 
     * @return the value of field 'sequence_from'.
    **/
    public int getSequence_from()
    {
        return this._sequence_from;
    } //-- int getSequence_from() 

    /**
     * Returns the value of field 'sequence_to'.
     * 
     * @return the value of field 'sequence_to'.
    **/
    public int getSequence_to()
    {
        return this._sequence_to;
    } //-- int getSequence_to() 

    /**
    **/
    public boolean hasSequence_from()
    {
        return this._has_sequence_from;
    } //-- boolean hasSequence_from() 

    /**
    **/
    public boolean hasSequence_to()
    {
        return this._has_sequence_to;
    } //-- boolean hasSequence_to() 

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
     * Sets the value of field 'ft_description'.
     * 
     * @param ft_description the value of field 'ft_description'.
    **/
    public void setFt_description(java.lang.String ft_description)
    {
        this._ft_description = ft_description;
    } //-- void setFt_description(java.lang.String) 

    /**
     * Sets the value of field 'key_name'.
     * 
     * @param key_name the value of field 'key_name'.
    **/
    public void setKey_name(java.lang.String key_name)
    {
        this._key_name = key_name;
    } //-- void setKey_name(java.lang.String) 

    /**
     * Sets the value of field 'sequence_from'.
     * 
     * @param sequence_from the value of field 'sequence_from'.
    **/
    public void setSequence_from(int sequence_from)
    {
        this._sequence_from = sequence_from;
        this._has_sequence_from = true;
    } //-- void setSequence_from(int) 

    /**
     * Sets the value of field 'sequence_to'.
     * 
     * @param sequence_to the value of field 'sequence_to'.
    **/
    public void setSequence_to(int sequence_to)
    {
        this._sequence_to = sequence_to;
        this._has_sequence_to = true;
    } //-- void setSequence_to(int) 

    /**
     * 
     * 
     * @param reader
    **/
    public static cn.ac.rcpa.bio.database.ebi.protein.entry.Feature_table unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (cn.ac.rcpa.bio.database.ebi.protein.entry.Feature_table) Unmarshaller.unmarshal(cn.ac.rcpa.bio.database.ebi.protein.entry.Feature_table.class, reader);
    } //-- cn.ac.rcpa.bio.database.ebi.protein.entry.Feature_table unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
