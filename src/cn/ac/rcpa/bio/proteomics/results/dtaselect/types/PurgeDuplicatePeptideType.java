/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: PurgeDuplicatePeptideType.java,v 1.1 2006/06/30 14:03:34 sheng Exp $
 */

package cn.ac.rcpa.bio.proteomics.results.dtaselect.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.Hashtable;

/**
 * Class PurgeDuplicatePeptideType.
 * 
 * @version $Revision: 1.1 $ $Date: 2006/06/30 14:03:34 $
 */
public class PurgeDuplicatePeptideType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The false type
     */
    public static final int FALSE_TYPE = 0;

    /**
     * The instance of the false type
     */
    public static final PurgeDuplicatePeptideType FALSE = new PurgeDuplicatePeptideType(FALSE_TYPE, "false");

    /**
     * The XCorr type
     */
    public static final int XCORR_TYPE = 1;

    /**
     * The instance of the XCorr type
     */
    public static final PurgeDuplicatePeptideType XCORR = new PurgeDuplicatePeptideType(XCORR_TYPE, "XCorr");

    /**
     * The Intensity type
     */
    public static final int INTENSITY_TYPE = 2;

    /**
     * The instance of the Intensity type
     */
    public static final PurgeDuplicatePeptideType INTENSITY = new PurgeDuplicatePeptideType(INTENSITY_TYPE, "Intensity");

    /**
     * Field _memberTable
     */
    private static java.util.Hashtable _memberTable = init();

    /**
     * Field type
     */
    private int type = -1;

    /**
     * Field stringValue
     */
    private java.lang.String stringValue = null;


      //----------------/
     //- Constructors -/
    //----------------/

    private PurgeDuplicatePeptideType(int type, java.lang.String value) {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- cn.ac.rcpa.bio.proteomics.results.dtaselect.types.PurgeDuplicatePeptideType(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * PurgeDuplicatePeptideType
     * 
     * @return Enumeration
     */
    public static java.util.Enumeration enumerate()
    {
        return _memberTable.elements();
    } //-- java.util.Enumeration enumerate() 

    /**
     * Method getType
     * 
     * Returns the type of this PurgeDuplicatePeptideType
     * 
     * @return int
     */
    public int getType()
    {
        return this.type;
    } //-- int getType() 

    /**
     * Method init
     * 
     * 
     * 
     * @return Hashtable
     */
    private static java.util.Hashtable init()
    {
        Hashtable members = new Hashtable();
        members.put("false", FALSE);
        members.put("XCorr", XCORR);
        members.put("Intensity", INTENSITY);
        return members;
    } //-- java.util.Hashtable init() 

    /**
     * Method readResolve
     * 
     *  will be called during deserialization to replace the
     * deserialized object with the correct constant instance.
     * <br/>
     * 
     * @return Object
     */
    private java.lang.Object readResolve()
    {
        return valueOf(this.stringValue);
    } //-- java.lang.Object readResolve() 

    /**
     * Method toString
     * 
     * Returns the String representation of this
     * PurgeDuplicatePeptideType
     * 
     * @return String
     */
    public java.lang.String toString()
    {
        return this.stringValue;
    } //-- java.lang.String toString() 

    /**
     * Method valueOf
     * 
     * Returns a new PurgeDuplicatePeptideType based on the given
     * String value.
     * 
     * @param string
     * @return PurgeDuplicatePeptideType
     */
    public static cn.ac.rcpa.bio.proteomics.results.dtaselect.types.PurgeDuplicatePeptideType valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid PurgeDuplicatePeptideType";
            throw new IllegalArgumentException(err);
        }
        return (PurgeDuplicatePeptideType) obj;
    } //-- cn.ac.rcpa.bio.proteomics.results.dtaselect.types.PurgeDuplicatePeptideType valueOf(java.lang.String) 

}
