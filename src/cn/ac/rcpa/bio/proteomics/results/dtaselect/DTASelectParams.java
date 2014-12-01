/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: DTASelectParams.java,v 1.1 2006/06/30 14:03:36 sheng Exp $
 */

package cn.ac.rcpa.bio.proteomics.results.dtaselect;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class DTASelectParams.
 * 
 * @version $Revision: 1.1 $ $Date: 2006/06/30 14:03:36 $
 */
public class DTASelectParams implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _sequestResultFile
     */
    private java.lang.String _sequestResultFile;

    /**
     * Field _database
     */
    private java.lang.String _database;

    /**
     * Field _directory
     */
    private java.lang.String _directory;

    /**
     * Field _xcorrCharge1
     */
    private double _xcorrCharge1 = 1.9;

    /**
     * keeps track of state for field: _xcorrCharge1
     */
    private boolean _has_xcorrCharge1;

    /**
     * Field _xcorrCharge2
     */
    private double _xcorrCharge2 = 2.2;

    /**
     * keeps track of state for field: _xcorrCharge2
     */
    private boolean _has_xcorrCharge2;

    /**
     * Field _xcorrCharge3
     */
    private double _xcorrCharge3 = 3.75;

    /**
     * keeps track of state for field: _xcorrCharge3
     */
    private boolean _has_xcorrCharge3;

    /**
     * Field _deltacn
     */
    private double _deltacn = 0.1;

    /**
     * keeps track of state for field: _deltacn
     */
    private boolean _has_deltacn;

    /**
     * Field _maxSpRank
     */
    private int _maxSpRank = 1000;

    /**
     * keeps track of state for field: _maxSpRank
     */
    private boolean _has_maxSpRank;

    /**
     * Field _minSpScore
     */
    private double _minSpScore = -1.0;

    /**
     * keeps track of state for field: _minSpScore
     */
    private boolean _has_minSpScore;

    /**
     * Field _minUniquePeptideCount
     */
    private int _minUniquePeptideCount = 1;

    /**
     * keeps track of state for field: _minUniquePeptideCount
     */
    private boolean _has_minUniquePeptideCount;

    /**
     * Field _minPeptideCount
     */
    private int _minPeptideCount = 1;

    /**
     * keeps track of state for field: _minPeptideCount
     */
    private boolean _has_minPeptideCount;

    /**
     * Field _purgeDuplicatePeptide
     */
    private cn.ac.rcpa.bio.proteomics.results.dtaselect.types.PurgeDuplicatePeptideType _purgeDuplicatePeptide;

    /**
     * Field _removeSubsetProteins
     */
    private boolean _removeSubsetProteins = true;

    /**
     * keeps track of state for field: _removeSubsetProteins
     */
    private boolean _has_removeSubsetProteins;


      //----------------/
     //- Constructors -/
    //----------------/

    public DTASelectParams() {
        super();
    } //-- cn.ac.rcpa.bio.proteomics.results.dtaselect.DTASelectParams()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteDeltacn
     * 
     */
    public void deleteDeltacn()
    {
        this._has_deltacn= false;
    } //-- void deleteDeltacn() 

    /**
     * Method deleteMaxSpRank
     * 
     */
    public void deleteMaxSpRank()
    {
        this._has_maxSpRank= false;
    } //-- void deleteMaxSpRank() 

    /**
     * Method deleteMinPeptideCount
     * 
     */
    public void deleteMinPeptideCount()
    {
        this._has_minPeptideCount= false;
    } //-- void deleteMinPeptideCount() 

    /**
     * Method deleteMinSpScore
     * 
     */
    public void deleteMinSpScore()
    {
        this._has_minSpScore= false;
    } //-- void deleteMinSpScore() 

    /**
     * Method deleteMinUniquePeptideCount
     * 
     */
    public void deleteMinUniquePeptideCount()
    {
        this._has_minUniquePeptideCount= false;
    } //-- void deleteMinUniquePeptideCount() 

    /**
     * Method deleteRemoveSubsetProteins
     * 
     */
    public void deleteRemoveSubsetProteins()
    {
        this._has_removeSubsetProteins= false;
    } //-- void deleteRemoveSubsetProteins() 

    /**
     * Method deleteXcorrCharge1
     * 
     */
    public void deleteXcorrCharge1()
    {
        this._has_xcorrCharge1= false;
    } //-- void deleteXcorrCharge1() 

    /**
     * Method deleteXcorrCharge2
     * 
     */
    public void deleteXcorrCharge2()
    {
        this._has_xcorrCharge2= false;
    } //-- void deleteXcorrCharge2() 

    /**
     * Method deleteXcorrCharge3
     * 
     */
    public void deleteXcorrCharge3()
    {
        this._has_xcorrCharge3= false;
    } //-- void deleteXcorrCharge3() 

    /**
     * Returns the value of field 'database'.
     * 
     * @return String
     * @return the value of field 'database'.
     */
    public java.lang.String getDatabase()
    {
        return this._database;
    } //-- java.lang.String getDatabase() 

    /**
     * Returns the value of field 'deltacn'.
     * 
     * @return double
     * @return the value of field 'deltacn'.
     */
    public double getDeltacn()
    {
        return this._deltacn;
    } //-- double getDeltacn() 

    /**
     * Returns the value of field 'directory'.
     * 
     * @return String
     * @return the value of field 'directory'.
     */
    public java.lang.String getDirectory()
    {
        return this._directory;
    } //-- java.lang.String getDirectory() 

    /**
     * Returns the value of field 'maxSpRank'.
     * 
     * @return int
     * @return the value of field 'maxSpRank'.
     */
    public int getMaxSpRank()
    {
        return this._maxSpRank;
    } //-- int getMaxSpRank() 

    /**
     * Returns the value of field 'minPeptideCount'.
     * 
     * @return int
     * @return the value of field 'minPeptideCount'.
     */
    public int getMinPeptideCount()
    {
        return this._minPeptideCount;
    } //-- int getMinPeptideCount() 

    /**
     * Returns the value of field 'minSpScore'.
     * 
     * @return double
     * @return the value of field 'minSpScore'.
     */
    public double getMinSpScore()
    {
        return this._minSpScore;
    } //-- double getMinSpScore() 

    /**
     * Returns the value of field 'minUniquePeptideCount'.
     * 
     * @return int
     * @return the value of field 'minUniquePeptideCount'.
     */
    public int getMinUniquePeptideCount()
    {
        return this._minUniquePeptideCount;
    } //-- int getMinUniquePeptideCount() 

    /**
     * Returns the value of field 'purgeDuplicatePeptide'.
     * 
     * @return PurgeDuplicatePeptideType
     * @return the value of field 'purgeDuplicatePeptide'.
     */
    public cn.ac.rcpa.bio.proteomics.results.dtaselect.types.PurgeDuplicatePeptideType getPurgeDuplicatePeptide()
    {
        return this._purgeDuplicatePeptide;
    } //-- cn.ac.rcpa.bio.proteomics.results.dtaselect.types.PurgeDuplicatePeptideType getPurgeDuplicatePeptide() 

    /**
     * Returns the value of field 'removeSubsetProteins'.
     * 
     * @return boolean
     * @return the value of field 'removeSubsetProteins'.
     */
    public boolean getRemoveSubsetProteins()
    {
        return this._removeSubsetProteins;
    } //-- boolean getRemoveSubsetProteins() 

    /**
     * Returns the value of field 'sequestResultFile'.
     * 
     * @return String
     * @return the value of field 'sequestResultFile'.
     */
    public java.lang.String getSequestResultFile()
    {
        return this._sequestResultFile;
    } //-- java.lang.String getSequestResultFile() 

    /**
     * Returns the value of field 'xcorrCharge1'.
     * 
     * @return double
     * @return the value of field 'xcorrCharge1'.
     */
    public double getXcorrCharge1()
    {
        return this._xcorrCharge1;
    } //-- double getXcorrCharge1() 

    /**
     * Returns the value of field 'xcorrCharge2'.
     * 
     * @return double
     * @return the value of field 'xcorrCharge2'.
     */
    public double getXcorrCharge2()
    {
        return this._xcorrCharge2;
    } //-- double getXcorrCharge2() 

    /**
     * Returns the value of field 'xcorrCharge3'.
     * 
     * @return double
     * @return the value of field 'xcorrCharge3'.
     */
    public double getXcorrCharge3()
    {
        return this._xcorrCharge3;
    } //-- double getXcorrCharge3() 

    /**
     * Method hasDeltacn
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasDeltacn()
    {
        return this._has_deltacn;
    } //-- boolean hasDeltacn() 

    /**
     * Method hasMaxSpRank
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasMaxSpRank()
    {
        return this._has_maxSpRank;
    } //-- boolean hasMaxSpRank() 

    /**
     * Method hasMinPeptideCount
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasMinPeptideCount()
    {
        return this._has_minPeptideCount;
    } //-- boolean hasMinPeptideCount() 

    /**
     * Method hasMinSpScore
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasMinSpScore()
    {
        return this._has_minSpScore;
    } //-- boolean hasMinSpScore() 

    /**
     * Method hasMinUniquePeptideCount
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasMinUniquePeptideCount()
    {
        return this._has_minUniquePeptideCount;
    } //-- boolean hasMinUniquePeptideCount() 

    /**
     * Method hasRemoveSubsetProteins
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasRemoveSubsetProteins()
    {
        return this._has_removeSubsetProteins;
    } //-- boolean hasRemoveSubsetProteins() 

    /**
     * Method hasXcorrCharge1
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasXcorrCharge1()
    {
        return this._has_xcorrCharge1;
    } //-- boolean hasXcorrCharge1() 

    /**
     * Method hasXcorrCharge2
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasXcorrCharge2()
    {
        return this._has_xcorrCharge2;
    } //-- boolean hasXcorrCharge2() 

    /**
     * Method hasXcorrCharge3
     * 
     * 
     * 
     * @return boolean
     */
    public boolean hasXcorrCharge3()
    {
        return this._has_xcorrCharge3;
    } //-- boolean hasXcorrCharge3() 

    /**
     * Method isValid
     * 
     * 
     * 
     * @return boolean
     */
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
     * Method marshal
     * 
     * 
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * 
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Sets the value of field 'database'.
     * 
     * @param database the value of field 'database'.
     */
    public void setDatabase(java.lang.String database)
    {
        this._database = database;
    } //-- void setDatabase(java.lang.String) 

    /**
     * Sets the value of field 'deltacn'.
     * 
     * @param deltacn the value of field 'deltacn'.
     */
    public void setDeltacn(double deltacn)
    {
        this._deltacn = deltacn;
        this._has_deltacn = true;
    } //-- void setDeltacn(double) 

    /**
     * Sets the value of field 'directory'.
     * 
     * @param directory the value of field 'directory'.
     */
    public void setDirectory(java.lang.String directory)
    {
        this._directory = directory;
    } //-- void setDirectory(java.lang.String) 

    /**
     * Sets the value of field 'maxSpRank'.
     * 
     * @param maxSpRank the value of field 'maxSpRank'.
     */
    public void setMaxSpRank(int maxSpRank)
    {
        this._maxSpRank = maxSpRank;
        this._has_maxSpRank = true;
    } //-- void setMaxSpRank(int) 

    /**
     * Sets the value of field 'minPeptideCount'.
     * 
     * @param minPeptideCount the value of field 'minPeptideCount'.
     */
    public void setMinPeptideCount(int minPeptideCount)
    {
        this._minPeptideCount = minPeptideCount;
        this._has_minPeptideCount = true;
    } //-- void setMinPeptideCount(int) 

    /**
     * Sets the value of field 'minSpScore'.
     * 
     * @param minSpScore the value of field 'minSpScore'.
     */
    public void setMinSpScore(double minSpScore)
    {
        this._minSpScore = minSpScore;
        this._has_minSpScore = true;
    } //-- void setMinSpScore(double) 

    /**
     * Sets the value of field 'minUniquePeptideCount'.
     * 
     * @param minUniquePeptideCount the value of field
     * 'minUniquePeptideCount'.
     */
    public void setMinUniquePeptideCount(int minUniquePeptideCount)
    {
        this._minUniquePeptideCount = minUniquePeptideCount;
        this._has_minUniquePeptideCount = true;
    } //-- void setMinUniquePeptideCount(int) 

    /**
     * Sets the value of field 'purgeDuplicatePeptide'.
     * 
     * @param purgeDuplicatePeptide the value of field
     * 'purgeDuplicatePeptide'.
     */
    public void setPurgeDuplicatePeptide(cn.ac.rcpa.bio.proteomics.results.dtaselect.types.PurgeDuplicatePeptideType purgeDuplicatePeptide)
    {
        this._purgeDuplicatePeptide = purgeDuplicatePeptide;
    } //-- void setPurgeDuplicatePeptide(cn.ac.rcpa.bio.proteomics.results.dtaselect.types.PurgeDuplicatePeptideType) 

    /**
     * Sets the value of field 'removeSubsetProteins'.
     * 
     * @param removeSubsetProteins the value of field
     * 'removeSubsetProteins'.
     */
    public void setRemoveSubsetProteins(boolean removeSubsetProteins)
    {
        this._removeSubsetProteins = removeSubsetProteins;
        this._has_removeSubsetProteins = true;
    } //-- void setRemoveSubsetProteins(boolean) 

    /**
     * Sets the value of field 'sequestResultFile'.
     * 
     * @param sequestResultFile the value of field
     * 'sequestResultFile'.
     */
    public void setSequestResultFile(java.lang.String sequestResultFile)
    {
        this._sequestResultFile = sequestResultFile;
    } //-- void setSequestResultFile(java.lang.String) 

    /**
     * Sets the value of field 'xcorrCharge1'.
     * 
     * @param xcorrCharge1 the value of field 'xcorrCharge1'.
     */
    public void setXcorrCharge1(double xcorrCharge1)
    {
        this._xcorrCharge1 = xcorrCharge1;
        this._has_xcorrCharge1 = true;
    } //-- void setXcorrCharge1(double) 

    /**
     * Sets the value of field 'xcorrCharge2'.
     * 
     * @param xcorrCharge2 the value of field 'xcorrCharge2'.
     */
    public void setXcorrCharge2(double xcorrCharge2)
    {
        this._xcorrCharge2 = xcorrCharge2;
        this._has_xcorrCharge2 = true;
    } //-- void setXcorrCharge2(double) 

    /**
     * Sets the value of field 'xcorrCharge3'.
     * 
     * @param xcorrCharge3 the value of field 'xcorrCharge3'.
     */
    public void setXcorrCharge3(double xcorrCharge3)
    {
        this._xcorrCharge3 = xcorrCharge3;
        this._has_xcorrCharge3 = true;
    } //-- void setXcorrCharge3(double) 

    /**
     * Method unmarshal
     * 
     * 
     * 
     * @param reader
     * @return Object
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (cn.ac.rcpa.bio.proteomics.results.dtaselect.DTASelectParams) Unmarshaller.unmarshal(cn.ac.rcpa.bio.proteomics.results.dtaselect.DTASelectParams.class, reader);
    } //-- java.lang.Object unmarshal(java.io.Reader) 

    /**
     * Method validate
     * 
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
