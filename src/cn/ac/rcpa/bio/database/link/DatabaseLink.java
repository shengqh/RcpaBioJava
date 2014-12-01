/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: DatabaseLink.java,v 1.1 2006/06/30 14:03:37 sheng Exp $
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
public class DatabaseLink implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private java.lang.String _shortlabel;

    private java.lang.String _term;

    private java.lang.String _search_url;

    private java.lang.String _search_url_ascii;

    private java.util.ArrayList _urlList;

    private java.lang.String _definition;

    private java.lang.String _example;

    private java.lang.String _remark;

    private java.lang.String _comment;


      //----------------/
     //- Constructors -/
    //----------------/

    public DatabaseLink() {
        super();
        _urlList = new ArrayList();
    } //-- cn.ac.rcpa.bio.database.link.DatabaseLink()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vUrl
    **/
    public void addUrl(java.lang.String vUrl)
        throws java.lang.IndexOutOfBoundsException
    {
        _urlList.add(vUrl);
    } //-- void addUrl(java.lang.String) 

    /**
     * 
     * 
     * @param index
     * @param vUrl
    **/
    public void addUrl(int index, java.lang.String vUrl)
        throws java.lang.IndexOutOfBoundsException
    {
        _urlList.add(index, vUrl);
    } //-- void addUrl(int, java.lang.String) 

    /**
    **/
    public void clearUrl()
    {
        _urlList.clear();
    } //-- void clearUrl() 

    /**
    **/
    public java.util.Enumeration enumerateUrl()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_urlList.iterator());
    } //-- java.util.Enumeration enumerateUrl() 

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
     * Returns the value of field 'definition'.
     * 
     * @return the value of field 'definition'.
    **/
    public java.lang.String getDefinition()
    {
        return this._definition;
    } //-- java.lang.String getDefinition() 

    /**
     * Returns the value of field 'example'.
     * 
     * @return the value of field 'example'.
    **/
    public java.lang.String getExample()
    {
        return this._example;
    } //-- java.lang.String getExample() 

    /**
     * Returns the value of field 'remark'.
     * 
     * @return the value of field 'remark'.
    **/
    public java.lang.String getRemark()
    {
        return this._remark;
    } //-- java.lang.String getRemark() 

    /**
     * Returns the value of field 'search_url'.
     * 
     * @return the value of field 'search_url'.
    **/
    public java.lang.String getSearch_url()
    {
        return this._search_url;
    } //-- java.lang.String getSearch_url() 

    /**
     * Returns the value of field 'search_url_ascii'.
     * 
     * @return the value of field 'search_url_ascii'.
    **/
    public java.lang.String getSearch_url_ascii()
    {
        return this._search_url_ascii;
    } //-- java.lang.String getSearch_url_ascii() 

    /**
     * Returns the value of field 'shortlabel'.
     * 
     * @return the value of field 'shortlabel'.
    **/
    public java.lang.String getShortlabel()
    {
        return this._shortlabel;
    } //-- java.lang.String getShortlabel() 

    /**
     * Returns the value of field 'term'.
     * 
     * @return the value of field 'term'.
    **/
    public java.lang.String getTerm()
    {
        return this._term;
    } //-- java.lang.String getTerm() 

    /**
     * 
     * 
     * @param index
    **/
    public java.lang.String getUrl(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _urlList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_urlList.get(index);
    } //-- java.lang.String getUrl(int) 

    /**
    **/
    public java.lang.String[] getUrl()
    {
        int size = _urlList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_urlList.get(index);
        }
        return mArray;
    } //-- java.lang.String[] getUrl() 

    /**
    **/
    public int getUrlCount()
    {
        return _urlList.size();
    } //-- int getUrlCount() 

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
     * @param vUrl
    **/
    public boolean removeUrl(java.lang.String vUrl)
    {
        boolean removed = _urlList.remove(vUrl);
        return removed;
    } //-- boolean removeUrl(java.lang.String) 

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
     * Sets the value of field 'definition'.
     * 
     * @param definition the value of field 'definition'.
    **/
    public void setDefinition(java.lang.String definition)
    {
        this._definition = definition;
    } //-- void setDefinition(java.lang.String) 

    /**
     * Sets the value of field 'example'.
     * 
     * @param example the value of field 'example'.
    **/
    public void setExample(java.lang.String example)
    {
        this._example = example;
    } //-- void setExample(java.lang.String) 

    /**
     * Sets the value of field 'remark'.
     * 
     * @param remark the value of field 'remark'.
    **/
    public void setRemark(java.lang.String remark)
    {
        this._remark = remark;
    } //-- void setRemark(java.lang.String) 

    /**
     * Sets the value of field 'search_url'.
     * 
     * @param search_url the value of field 'search_url'.
    **/
    public void setSearch_url(java.lang.String search_url)
    {
        this._search_url = search_url;
    } //-- void setSearch_url(java.lang.String) 

    /**
     * Sets the value of field 'search_url_ascii'.
     * 
     * @param search_url_ascii the value of field 'search_url_ascii'
    **/
    public void setSearch_url_ascii(java.lang.String search_url_ascii)
    {
        this._search_url_ascii = search_url_ascii;
    } //-- void setSearch_url_ascii(java.lang.String) 

    /**
     * Sets the value of field 'shortlabel'.
     * 
     * @param shortlabel the value of field 'shortlabel'.
    **/
    public void setShortlabel(java.lang.String shortlabel)
    {
        this._shortlabel = shortlabel;
    } //-- void setShortlabel(java.lang.String) 

    /**
     * Sets the value of field 'term'.
     * 
     * @param term the value of field 'term'.
    **/
    public void setTerm(java.lang.String term)
    {
        this._term = term;
    } //-- void setTerm(java.lang.String) 

    /**
     * 
     * 
     * @param index
     * @param vUrl
    **/
    public void setUrl(int index, java.lang.String vUrl)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _urlList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _urlList.set(index, vUrl);
    } //-- void setUrl(int, java.lang.String) 

    /**
     * 
     * 
     * @param urlArray
    **/
    public void setUrl(java.lang.String[] urlArray)
    {
        //-- copy array
        _urlList.clear();
        for (int i = 0; i < urlArray.length; i++) {
            _urlList.add(urlArray[i]);
        }
    } //-- void setUrl(java.lang.String) 

    /**
     * 
     * 
     * @param reader
    **/
    public static cn.ac.rcpa.bio.database.link.DatabaseLink unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (cn.ac.rcpa.bio.database.link.DatabaseLink) Unmarshaller.unmarshal(cn.ac.rcpa.bio.database.link.DatabaseLink.class, reader);
    } //-- cn.ac.rcpa.bio.database.link.DatabaseLink unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
