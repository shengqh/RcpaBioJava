/*
 * This class was automatically generated with
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: PurgeDuplicatePeptide.java,v 1.1 2006/06/30 14:03:36 sheng Exp $
 */

package cn.ac.rcpa.bio.proteomics.results.dtaselect;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 *
 *
 * @version $Revision: 1.1 $ $Date: 2006/06/30 14:03:36 $
**/
public class PurgeDuplicatePeptide implements java.io.Serializable {


      //----------------/
     //- Constructors -/
    //----------------/

    public PurgeDuplicatePeptide() {
        super();
    } //-- cn.ac.rcpa.bio.sequest.PurgeDuplicatePeptide()


      //-----------/
     //- Methods -/
    //-----------/

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
     * @param reader
    **/
    public static cn.ac.rcpa.bio.proteomics.results.dtaselect.PurgeDuplicatePeptide unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (cn.ac.rcpa.bio.proteomics.results.dtaselect.PurgeDuplicatePeptide) Unmarshaller.unmarshal(cn.ac.rcpa.bio.proteomics.results.dtaselect.PurgeDuplicatePeptide.class, reader);
    } //-- cn.ac.rcpa.bio.sequest.PurgeDuplicatePeptide unmarshal(java.io.Reader)

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate()

}
