/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: Free_comment.java,v 1.1 2006/06/30 14:03:32 sheng Exp $
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
public class Free_comment implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private java.lang.String _cc_topic;

    private java.lang.String _cc_details;


      //----------------/
     //- Constructors -/
    //----------------/

    public Free_comment() {
        super();
    } //-- cn.ac.rcpa.bio.database.ebi.protein.entry.Free_comment()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'cc_details'.
     * 
     * @return the value of field 'cc_details'.
    **/
    public java.lang.String getCc_details()
    {
        return this._cc_details;
    } //-- java.lang.String getCc_details() 

    /**
     * Returns the value of field 'cc_topic'.
     * 
     * @return the value of field 'cc_topic'.
    **/
    public java.lang.String getCc_topic()
    {
        return this._cc_topic;
    } //-- java.lang.String getCc_topic() 

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
     * Sets the value of field 'cc_details'.
     * 
     * @param cc_details the value of field 'cc_details'.
    **/
    public void setCc_details(java.lang.String cc_details)
    {
        this._cc_details = cc_details;
    } //-- void setCc_details(java.lang.String) 

    /**
     * Sets the value of field 'cc_topic'.
     * 
     * @param cc_topic the value of field 'cc_topic'.
    **/
    public void setCc_topic(java.lang.String cc_topic)
    {
        this._cc_topic = cc_topic;
    } //-- void setCc_topic(java.lang.String) 

    /**
     * 
     * 
     * @param reader
    **/
    public static cn.ac.rcpa.bio.database.ebi.protein.entry.Free_comment unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (cn.ac.rcpa.bio.database.ebi.protein.entry.Free_comment) Unmarshaller.unmarshal(cn.ac.rcpa.bio.database.ebi.protein.entry.Free_comment.class, reader);
    } //-- cn.ac.rcpa.bio.database.ebi.protein.entry.Free_comment unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
