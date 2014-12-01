/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: ProteinEntry.java,v 1.1 2006/06/30 14:03:32 sheng Exp $
 */

package cn.ac.rcpa.bio.database.ebi.protein.entry;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.ArrayList;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * IPI or SwissProt Entry
 * 
 * @version $Revision: 1.1 $ $Date: 2006/06/30 14:03:32 $
**/
public class ProteinEntry implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private java.lang.String _entry_name;

    private java.lang.String _data_class;

    private java.lang.String _molecule_type;

    private int _sequence_length;

    /**
     * keeps track of state for field: _sequence_length
    **/
    private boolean _has_sequence_length;

    private java.lang.String _create;

    private java.lang.String _sequence_update;

    private java.lang.String _annotation_update;

    private java.lang.String _description;

    private java.lang.String _gene_name;

    private java.lang.String _organism_species;

    private java.lang.String _organelle;

    private java.lang.String _organism_classification;

    private java.lang.String _taxonomy_id;

    private java.lang.String _keyword;

    private int _mw;

    /**
     * keeps track of state for field: _mw
    **/
    private boolean _has_mw;

    private java.lang.String _crc;

    private java.lang.String _sequence;

    private java.util.ArrayList _ac_numberList;

    private java.util.ArrayList _free_commentList;

    private java.util.ArrayList _referenceList;

    private java.util.ArrayList _db_referenceList;

    private java.util.ArrayList _feature_tableList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ProteinEntry() {
        super();
        _ac_numberList = new ArrayList();
        _free_commentList = new ArrayList();
        _referenceList = new ArrayList();
        _db_referenceList = new ArrayList();
        _feature_tableList = new ArrayList();
    } //-- cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vAc_number
    **/
    public void addAc_number(java.lang.String vAc_number)
        throws java.lang.IndexOutOfBoundsException
    {
        _ac_numberList.add(vAc_number);
    } //-- void addAc_number(java.lang.String) 

    /**
     * 
     * 
     * @param index
     * @param vAc_number
    **/
    public void addAc_number(int index, java.lang.String vAc_number)
        throws java.lang.IndexOutOfBoundsException
    {
        _ac_numberList.add(index, vAc_number);
    } //-- void addAc_number(int, java.lang.String) 

    /**
     * 
     * 
     * @param vDb_reference
    **/
    public void addDb_reference(Db_reference vDb_reference)
        throws java.lang.IndexOutOfBoundsException
    {
        _db_referenceList.add(vDb_reference);
    } //-- void addDb_reference(Db_reference) 

    /**
     * 
     * 
     * @param index
     * @param vDb_reference
    **/
    public void addDb_reference(int index, Db_reference vDb_reference)
        throws java.lang.IndexOutOfBoundsException
    {
        _db_referenceList.add(index, vDb_reference);
    } //-- void addDb_reference(int, Db_reference) 

    /**
     * 
     * 
     * @param vFeature_table
    **/
    public void addFeature_table(Feature_table vFeature_table)
        throws java.lang.IndexOutOfBoundsException
    {
        _feature_tableList.add(vFeature_table);
    } //-- void addFeature_table(Feature_table) 

    /**
     * 
     * 
     * @param index
     * @param vFeature_table
    **/
    public void addFeature_table(int index, Feature_table vFeature_table)
        throws java.lang.IndexOutOfBoundsException
    {
        _feature_tableList.add(index, vFeature_table);
    } //-- void addFeature_table(int, Feature_table) 

    /**
     * 
     * 
     * @param vFree_comment
    **/
    public void addFree_comment(Free_comment vFree_comment)
        throws java.lang.IndexOutOfBoundsException
    {
        _free_commentList.add(vFree_comment);
    } //-- void addFree_comment(Free_comment) 

    /**
     * 
     * 
     * @param index
     * @param vFree_comment
    **/
    public void addFree_comment(int index, Free_comment vFree_comment)
        throws java.lang.IndexOutOfBoundsException
    {
        _free_commentList.add(index, vFree_comment);
    } //-- void addFree_comment(int, Free_comment) 

    /**
     * 
     * 
     * @param vReference
    **/
    public void addReference(Reference vReference)
        throws java.lang.IndexOutOfBoundsException
    {
        _referenceList.add(vReference);
    } //-- void addReference(Reference) 

    /**
     * 
     * 
     * @param index
     * @param vReference
    **/
    public void addReference(int index, Reference vReference)
        throws java.lang.IndexOutOfBoundsException
    {
        _referenceList.add(index, vReference);
    } //-- void addReference(int, Reference) 

    /**
    **/
    public void clearAc_number()
    {
        _ac_numberList.clear();
    } //-- void clearAc_number() 

    /**
    **/
    public void clearDb_reference()
    {
        _db_referenceList.clear();
    } //-- void clearDb_reference() 

    /**
    **/
    public void clearFeature_table()
    {
        _feature_tableList.clear();
    } //-- void clearFeature_table() 

    /**
    **/
    public void clearFree_comment()
    {
        _free_commentList.clear();
    } //-- void clearFree_comment() 

    /**
    **/
    public void clearReference()
    {
        _referenceList.clear();
    } //-- void clearReference() 

    /**
    **/
    public void deleteMw()
    {
        this._has_mw= false;
    } //-- void deleteMw() 

    /**
    **/
    public void deleteSequence_length()
    {
        this._has_sequence_length= false;
    } //-- void deleteSequence_length() 

    /**
    **/
    public java.util.Enumeration enumerateAc_number()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_ac_numberList.iterator());
    } //-- java.util.Enumeration enumerateAc_number() 

    /**
    **/
    public java.util.Enumeration enumerateDb_reference()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_db_referenceList.iterator());
    } //-- java.util.Enumeration enumerateDb_reference() 

    /**
    **/
    public java.util.Enumeration enumerateFeature_table()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_feature_tableList.iterator());
    } //-- java.util.Enumeration enumerateFeature_table() 

    /**
    **/
    public java.util.Enumeration enumerateFree_comment()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_free_commentList.iterator());
    } //-- java.util.Enumeration enumerateFree_comment() 

    /**
    **/
    public java.util.Enumeration enumerateReference()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_referenceList.iterator());
    } //-- java.util.Enumeration enumerateReference() 

    /**
     * 
     * 
     * @param index
    **/
    public java.lang.String getAc_number(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _ac_numberList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_ac_numberList.get(index);
    } //-- java.lang.String getAc_number(int) 

    /**
    **/
    public java.lang.String[] getAc_number()
    {
        int size = _ac_numberList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_ac_numberList.get(index);
        }
        return mArray;
    } //-- java.lang.String[] getAc_number() 

    /**
    **/
    public int getAc_numberCount()
    {
        return _ac_numberList.size();
    } //-- int getAc_numberCount() 

    /**
     * Returns the value of field 'annotation_update'.
     * 
     * @return the value of field 'annotation_update'.
    **/
    public java.lang.String getAnnotation_update()
    {
        return this._annotation_update;
    } //-- java.lang.String getAnnotation_update() 

    /**
     * Returns the value of field 'crc'.
     * 
     * @return the value of field 'crc'.
    **/
    public java.lang.String getCrc()
    {
        return this._crc;
    } //-- java.lang.String getCrc() 

    /**
     * Returns the value of field 'create'.
     * 
     * @return the value of field 'create'.
    **/
    public java.lang.String getCreate()
    {
        return this._create;
    } //-- java.lang.String getCreate() 

    /**
     * Returns the value of field 'data_class'.
     * 
     * @return the value of field 'data_class'.
    **/
    public java.lang.String getData_class()
    {
        return this._data_class;
    } //-- java.lang.String getData_class() 

    /**
     * 
     * 
     * @param index
    **/
    public Db_reference getDb_reference(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _db_referenceList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (Db_reference) _db_referenceList.get(index);
    } //-- Db_reference getDb_reference(int) 

    /**
    **/
    public Db_reference[] getDb_reference()
    {
        int size = _db_referenceList.size();
        Db_reference[] mArray = new Db_reference[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (Db_reference) _db_referenceList.get(index);
        }
        return mArray;
    } //-- Db_reference[] getDb_reference() 

    /**
    **/
    public int getDb_referenceCount()
    {
        return _db_referenceList.size();
    } //-- int getDb_referenceCount() 

    /**
     * Returns the value of field 'description'.
     * 
     * @return the value of field 'description'.
    **/
    public java.lang.String getDescription()
    {
        return this._description;
    } //-- java.lang.String getDescription() 

    /**
     * Returns the value of field 'entry_name'.
     * 
     * @return the value of field 'entry_name'.
    **/
    public java.lang.String getEntry_name()
    {
        return this._entry_name;
    } //-- java.lang.String getEntry_name() 

    /**
     * 
     * 
     * @param index
    **/
    public Feature_table getFeature_table(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _feature_tableList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (Feature_table) _feature_tableList.get(index);
    } //-- Feature_table getFeature_table(int) 

    /**
    **/
    public Feature_table[] getFeature_table()
    {
        int size = _feature_tableList.size();
        Feature_table[] mArray = new Feature_table[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (Feature_table) _feature_tableList.get(index);
        }
        return mArray;
    } //-- Feature_table[] getFeature_table() 

    /**
    **/
    public int getFeature_tableCount()
    {
        return _feature_tableList.size();
    } //-- int getFeature_tableCount() 

    /**
     * 
     * 
     * @param index
    **/
    public Free_comment getFree_comment(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _free_commentList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (Free_comment) _free_commentList.get(index);
    } //-- Free_comment getFree_comment(int) 

    /**
    **/
    public Free_comment[] getFree_comment()
    {
        int size = _free_commentList.size();
        Free_comment[] mArray = new Free_comment[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (Free_comment) _free_commentList.get(index);
        }
        return mArray;
    } //-- Free_comment[] getFree_comment() 

    /**
    **/
    public int getFree_commentCount()
    {
        return _free_commentList.size();
    } //-- int getFree_commentCount() 

    /**
     * Returns the value of field 'gene_name'.
     * 
     * @return the value of field 'gene_name'.
    **/
    public java.lang.String getGene_name()
    {
        return this._gene_name;
    } //-- java.lang.String getGene_name() 

    /**
     * Returns the value of field 'keyword'.
     * 
     * @return the value of field 'keyword'.
    **/
    public java.lang.String getKeyword()
    {
        return this._keyword;
    } //-- java.lang.String getKeyword() 

    /**
     * Returns the value of field 'molecule_type'.
     * 
     * @return the value of field 'molecule_type'.
    **/
    public java.lang.String getMolecule_type()
    {
        return this._molecule_type;
    } //-- java.lang.String getMolecule_type() 

    /**
     * Returns the value of field 'mw'.
     * 
     * @return the value of field 'mw'.
    **/
    public int getMw()
    {
        return this._mw;
    } //-- int getMw() 

    /**
     * Returns the value of field 'organelle'.
     * 
     * @return the value of field 'organelle'.
    **/
    public java.lang.String getOrganelle()
    {
        return this._organelle;
    } //-- java.lang.String getOrganelle() 

    /**
     * Returns the value of field 'organism_classification'.
     * 
     * @return the value of field 'organism_classification'.
    **/
    public java.lang.String getOrganism_classification()
    {
        return this._organism_classification;
    } //-- java.lang.String getOrganism_classification() 

    /**
     * Returns the value of field 'organism_species'.
     * 
     * @return the value of field 'organism_species'.
    **/
    public java.lang.String getOrganism_species()
    {
        return this._organism_species;
    } //-- java.lang.String getOrganism_species() 

    /**
     * 
     * 
     * @param index
    **/
    public Reference getReference(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _referenceList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (Reference) _referenceList.get(index);
    } //-- Reference getReference(int) 

    /**
    **/
    public Reference[] getReference()
    {
        int size = _referenceList.size();
        Reference[] mArray = new Reference[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (Reference) _referenceList.get(index);
        }
        return mArray;
    } //-- Reference[] getReference() 

    /**
    **/
    public int getReferenceCount()
    {
        return _referenceList.size();
    } //-- int getReferenceCount() 

    /**
     * Returns the value of field 'sequence'.
     * 
     * @return the value of field 'sequence'.
    **/
    public java.lang.String getSequence()
    {
        return this._sequence;
    } //-- java.lang.String getSequence() 

    /**
     * Returns the value of field 'sequence_length'.
     * 
     * @return the value of field 'sequence_length'.
    **/
    public int getSequence_length()
    {
        return this._sequence_length;
    } //-- int getSequence_length() 

    /**
     * Returns the value of field 'sequence_update'.
     * 
     * @return the value of field 'sequence_update'.
    **/
    public java.lang.String getSequence_update()
    {
        return this._sequence_update;
    } //-- java.lang.String getSequence_update() 

    /**
     * Returns the value of field 'taxonomy_id'.
     * 
     * @return the value of field 'taxonomy_id'.
    **/
    public java.lang.String getTaxonomy_id()
    {
        return this._taxonomy_id;
    } //-- java.lang.String getTaxonomy_id() 

    /**
    **/
    public boolean hasMw()
    {
        return this._has_mw;
    } //-- boolean hasMw() 

    /**
    **/
    public boolean hasSequence_length()
    {
        return this._has_sequence_length;
    } //-- boolean hasSequence_length() 

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
     * @param vAc_number
    **/
    public boolean removeAc_number(java.lang.String vAc_number)
    {
        boolean removed = _ac_numberList.remove(vAc_number);
        return removed;
    } //-- boolean removeAc_number(java.lang.String) 

    /**
     * 
     * 
     * @param vDb_reference
    **/
    public boolean removeDb_reference(Db_reference vDb_reference)
    {
        boolean removed = _db_referenceList.remove(vDb_reference);
        return removed;
    } //-- boolean removeDb_reference(Db_reference) 

    /**
     * 
     * 
     * @param vFeature_table
    **/
    public boolean removeFeature_table(Feature_table vFeature_table)
    {
        boolean removed = _feature_tableList.remove(vFeature_table);
        return removed;
    } //-- boolean removeFeature_table(Feature_table) 

    /**
     * 
     * 
     * @param vFree_comment
    **/
    public boolean removeFree_comment(Free_comment vFree_comment)
    {
        boolean removed = _free_commentList.remove(vFree_comment);
        return removed;
    } //-- boolean removeFree_comment(Free_comment) 

    /**
     * 
     * 
     * @param vReference
    **/
    public boolean removeReference(Reference vReference)
    {
        boolean removed = _referenceList.remove(vReference);
        return removed;
    } //-- boolean removeReference(Reference) 

    /**
     * 
     * 
     * @param index
     * @param vAc_number
    **/
    public void setAc_number(int index, java.lang.String vAc_number)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _ac_numberList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _ac_numberList.set(index, vAc_number);
    } //-- void setAc_number(int, java.lang.String) 

    /**
     * 
     * 
     * @param ac_numberArray
    **/
    public void setAc_number(java.lang.String[] ac_numberArray)
    {
        //-- copy array
        _ac_numberList.clear();
        for (int i = 0; i < ac_numberArray.length; i++) {
            _ac_numberList.add(ac_numberArray[i]);
        }
    } //-- void setAc_number(java.lang.String) 

    /**
     * Sets the value of field 'annotation_update'.
     * 
     * @param annotation_update the value of field
     * 'annotation_update'.
    **/
    public void setAnnotation_update(java.lang.String annotation_update)
    {
        this._annotation_update = annotation_update;
    } //-- void setAnnotation_update(java.lang.String) 

    /**
     * Sets the value of field 'crc'.
     * 
     * @param crc the value of field 'crc'.
    **/
    public void setCrc(java.lang.String crc)
    {
        this._crc = crc;
    } //-- void setCrc(java.lang.String) 

    /**
     * Sets the value of field 'create'.
     * 
     * @param create the value of field 'create'.
    **/
    public void setCreate(java.lang.String create)
    {
        this._create = create;
    } //-- void setCreate(java.lang.String) 

    /**
     * Sets the value of field 'data_class'.
     * 
     * @param data_class the value of field 'data_class'.
    **/
    public void setData_class(java.lang.String data_class)
    {
        this._data_class = data_class;
    } //-- void setData_class(java.lang.String) 

    /**
     * 
     * 
     * @param index
     * @param vDb_reference
    **/
    public void setDb_reference(int index, Db_reference vDb_reference)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _db_referenceList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _db_referenceList.set(index, vDb_reference);
    } //-- void setDb_reference(int, Db_reference) 

    /**
     * 
     * 
     * @param db_referenceArray
    **/
    public void setDb_reference(Db_reference[] db_referenceArray)
    {
        //-- copy array
        _db_referenceList.clear();
        for (int i = 0; i < db_referenceArray.length; i++) {
            _db_referenceList.add(db_referenceArray[i]);
        }
    } //-- void setDb_reference(Db_reference) 

    /**
     * Sets the value of field 'description'.
     * 
     * @param description the value of field 'description'.
    **/
    public void setDescription(java.lang.String description)
    {
        this._description = description;
    } //-- void setDescription(java.lang.String) 

    /**
     * Sets the value of field 'entry_name'.
     * 
     * @param entry_name the value of field 'entry_name'.
    **/
    public void setEntry_name(java.lang.String entry_name)
    {
        this._entry_name = entry_name;
    } //-- void setEntry_name(java.lang.String) 

    /**
     * 
     * 
     * @param index
     * @param vFeature_table
    **/
    public void setFeature_table(int index, Feature_table vFeature_table)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _feature_tableList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _feature_tableList.set(index, vFeature_table);
    } //-- void setFeature_table(int, Feature_table) 

    /**
     * 
     * 
     * @param feature_tableArray
    **/
    public void setFeature_table(Feature_table[] feature_tableArray)
    {
        //-- copy array
        _feature_tableList.clear();
        for (int i = 0; i < feature_tableArray.length; i++) {
            _feature_tableList.add(feature_tableArray[i]);
        }
    } //-- void setFeature_table(Feature_table) 

    /**
     * 
     * 
     * @param index
     * @param vFree_comment
    **/
    public void setFree_comment(int index, Free_comment vFree_comment)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _free_commentList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _free_commentList.set(index, vFree_comment);
    } //-- void setFree_comment(int, Free_comment) 

    /**
     * 
     * 
     * @param free_commentArray
    **/
    public void setFree_comment(Free_comment[] free_commentArray)
    {
        //-- copy array
        _free_commentList.clear();
        for (int i = 0; i < free_commentArray.length; i++) {
            _free_commentList.add(free_commentArray[i]);
        }
    } //-- void setFree_comment(Free_comment) 

    /**
     * Sets the value of field 'gene_name'.
     * 
     * @param gene_name the value of field 'gene_name'.
    **/
    public void setGene_name(java.lang.String gene_name)
    {
        this._gene_name = gene_name;
    } //-- void setGene_name(java.lang.String) 

    /**
     * Sets the value of field 'keyword'.
     * 
     * @param keyword the value of field 'keyword'.
    **/
    public void setKeyword(java.lang.String keyword)
    {
        this._keyword = keyword;
    } //-- void setKeyword(java.lang.String) 

    /**
     * Sets the value of field 'molecule_type'.
     * 
     * @param molecule_type the value of field 'molecule_type'.
    **/
    public void setMolecule_type(java.lang.String molecule_type)
    {
        this._molecule_type = molecule_type;
    } //-- void setMolecule_type(java.lang.String) 

    /**
     * Sets the value of field 'mw'.
     * 
     * @param mw the value of field 'mw'.
    **/
    public void setMw(int mw)
    {
        this._mw = mw;
        this._has_mw = true;
    } //-- void setMw(int) 

    /**
     * Sets the value of field 'organelle'.
     * 
     * @param organelle the value of field 'organelle'.
    **/
    public void setOrganelle(java.lang.String organelle)
    {
        this._organelle = organelle;
    } //-- void setOrganelle(java.lang.String) 

    /**
     * Sets the value of field 'organism_classification'.
     * 
     * @param organism_classification the value of field
     * 'organism_classification'.
    **/
    public void setOrganism_classification(java.lang.String organism_classification)
    {
        this._organism_classification = organism_classification;
    } //-- void setOrganism_classification(java.lang.String) 

    /**
     * Sets the value of field 'organism_species'.
     * 
     * @param organism_species the value of field 'organism_species'
    **/
    public void setOrganism_species(java.lang.String organism_species)
    {
        this._organism_species = organism_species;
    } //-- void setOrganism_species(java.lang.String) 

    /**
     * 
     * 
     * @param index
     * @param vReference
    **/
    public void setReference(int index, Reference vReference)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _referenceList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _referenceList.set(index, vReference);
    } //-- void setReference(int, Reference) 

    /**
     * 
     * 
     * @param referenceArray
    **/
    public void setReference(Reference[] referenceArray)
    {
        //-- copy array
        _referenceList.clear();
        for (int i = 0; i < referenceArray.length; i++) {
            _referenceList.add(referenceArray[i]);
        }
    } //-- void setReference(Reference) 

    /**
     * Sets the value of field 'sequence'.
     * 
     * @param sequence the value of field 'sequence'.
    **/
    public void setSequence(java.lang.String sequence)
    {
        this._sequence = sequence;
    } //-- void setSequence(java.lang.String) 

    /**
     * Sets the value of field 'sequence_length'.
     * 
     * @param sequence_length the value of field 'sequence_length'.
    **/
    public void setSequence_length(int sequence_length)
    {
        this._sequence_length = sequence_length;
        this._has_sequence_length = true;
    } //-- void setSequence_length(int) 

    /**
     * Sets the value of field 'sequence_update'.
     * 
     * @param sequence_update the value of field 'sequence_update'.
    **/
    public void setSequence_update(java.lang.String sequence_update)
    {
        this._sequence_update = sequence_update;
    } //-- void setSequence_update(java.lang.String) 

    /**
     * Sets the value of field 'taxonomy_id'.
     * 
     * @param taxonomy_id the value of field 'taxonomy_id'.
    **/
    public void setTaxonomy_id(java.lang.String taxonomy_id)
    {
        this._taxonomy_id = taxonomy_id;
    } //-- void setTaxonomy_id(java.lang.String) 

    /**
     * 
     * 
     * @param reader
    **/
    public static cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry) Unmarshaller.unmarshal(cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry.class, reader);
    } //-- cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
