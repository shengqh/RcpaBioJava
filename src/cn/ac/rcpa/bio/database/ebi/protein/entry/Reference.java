/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: Reference.java,v 1.1 2006/06/30 14:03:32 sheng Exp $
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
public class Reference implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private int _num;

    /**
     * keeps track of state for field: _num
    **/
    private boolean _has_num;

    private java.lang.String _position;

    private java.lang.String _comment;

    private int _medline_num;

    /**
     * keeps track of state for field: _medline_num
    **/
    private boolean _has_medline_num;

    private int _pubmed_num;

    /**
     * keeps track of state for field: _pubmed_num
    **/
    private boolean _has_pubmed_num;

    private java.lang.String _doi_num;

    private java.lang.String _author;

    private java.lang.String _title;

    private java.lang.String _location;


      //----------------/
     //- Constructors -/
    //----------------/

    public Reference() {
        super();
    } //-- cn.ac.rcpa.bio.database.ebi.protein.entry.Reference()


      //-----------/
     //- Methods -/
    //-----------/

    /**
    **/
    public void deleteMedline_num()
    {
        this._has_medline_num= false;
    } //-- void deleteMedline_num() 

    /**
    **/
    public void deleteNum()
    {
        this._has_num= false;
    } //-- void deleteNum() 

    /**
    **/
    public void deletePubmed_num()
    {
        this._has_pubmed_num= false;
    } //-- void deletePubmed_num() 

    /**
     * Returns the value of field 'author'.
     * 
     * @return the value of field 'author'.
    **/
    public java.lang.String getAuthor()
    {
        return this._author;
    } //-- java.lang.String getAuthor() 

    /**
     * Returns the value of field 'comment'.
     * 
     * @return the value of field 'comment'.
    **/
    public java.lang.String getComment()
    {
        return this._comment;
    } //-- java.lang.String getComment() 

    /**
     * Returns the value of field 'doi_num'.
     * 
     * @return the value of field 'doi_num'.
    **/
    public java.lang.String getDoi_num()
    {
        return this._doi_num;
    } //-- java.lang.String getDoi_num() 

    /**
     * Returns the value of field 'location'.
     * 
     * @return the value of field 'location'.
    **/
    public java.lang.String getLocation()
    {
        return this._location;
    } //-- java.lang.String getLocation() 

    /**
     * Returns the value of field 'medline_num'.
     * 
     * @return the value of field 'medline_num'.
    **/
    public int getMedline_num()
    {
        return this._medline_num;
    } //-- int getMedline_num() 

    /**
     * Returns the value of field 'num'.
     * 
     * @return the value of field 'num'.
    **/
    public int getNum()
    {
        return this._num;
    } //-- int getNum() 

    /**
     * Returns the value of field 'position'.
     * 
     * @return the value of field 'position'.
    **/
    public java.lang.String getPosition()
    {
        return this._position;
    } //-- java.lang.String getPosition() 

    /**
     * Returns the value of field 'pubmed_num'.
     * 
     * @return the value of field 'pubmed_num'.
    **/
    public int getPubmed_num()
    {
        return this._pubmed_num;
    } //-- int getPubmed_num() 

    /**
     * Returns the value of field 'title'.
     * 
     * @return the value of field 'title'.
    **/
    public java.lang.String getTitle()
    {
        return this._title;
    } //-- java.lang.String getTitle() 

    /**
    **/
    public boolean hasMedline_num()
    {
        return this._has_medline_num;
    } //-- boolean hasMedline_num() 

    /**
    **/
    public boolean hasNum()
    {
        return this._has_num;
    } //-- boolean hasNum() 

    /**
    **/
    public boolean hasPubmed_num()
    {
        return this._has_pubmed_num;
    } //-- boolean hasPubmed_num() 

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
     * Sets the value of field 'author'.
     * 
     * @param author the value of field 'author'.
    **/
    public void setAuthor(java.lang.String author)
    {
        this._author = author;
    } //-- void setAuthor(java.lang.String) 

    /**
     * Sets the value of field 'comment'.
     * 
     * @param comment the value of field 'comment'.
    **/
    public void setComment(java.lang.String comment)
    {
        this._comment = comment;
    } //-- void setComment(java.lang.String) 

    /**
     * Sets the value of field 'doi_num'.
     * 
     * @param doi_num the value of field 'doi_num'.
    **/
    public void setDoi_num(java.lang.String doi_num)
    {
        this._doi_num = doi_num;
    } //-- void setDoi_num(java.lang.String) 

    /**
     * Sets the value of field 'location'.
     * 
     * @param location the value of field 'location'.
    **/
    public void setLocation(java.lang.String location)
    {
        this._location = location;
    } //-- void setLocation(java.lang.String) 

    /**
     * Sets the value of field 'medline_num'.
     * 
     * @param medline_num the value of field 'medline_num'.
    **/
    public void setMedline_num(int medline_num)
    {
        this._medline_num = medline_num;
        this._has_medline_num = true;
    } //-- void setMedline_num(int) 

    /**
     * Sets the value of field 'num'.
     * 
     * @param num the value of field 'num'.
    **/
    public void setNum(int num)
    {
        this._num = num;
        this._has_num = true;
    } //-- void setNum(int) 

    /**
     * Sets the value of field 'position'.
     * 
     * @param position the value of field 'position'.
    **/
    public void setPosition(java.lang.String position)
    {
        this._position = position;
    } //-- void setPosition(java.lang.String) 

    /**
     * Sets the value of field 'pubmed_num'.
     * 
     * @param pubmed_num the value of field 'pubmed_num'.
    **/
    public void setPubmed_num(int pubmed_num)
    {
        this._pubmed_num = pubmed_num;
        this._has_pubmed_num = true;
    } //-- void setPubmed_num(int) 

    /**
     * Sets the value of field 'title'.
     * 
     * @param title the value of field 'title'.
    **/
    public void setTitle(java.lang.String title)
    {
        this._title = title;
    } //-- void setTitle(java.lang.String) 

    /**
     * 
     * 
     * @param reader
    **/
    public static cn.ac.rcpa.bio.database.ebi.protein.entry.Reference unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (cn.ac.rcpa.bio.database.ebi.protein.entry.Reference) Unmarshaller.unmarshal(cn.ac.rcpa.bio.database.ebi.protein.entry.Reference.class, reader);
    } //-- cn.ac.rcpa.bio.database.ebi.protein.entry.Reference unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
