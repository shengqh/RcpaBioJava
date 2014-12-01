del /Q ..\src\cn\ac\rcpa\bio\database\ebi\protein\entry\*.*
del /Q ..\src\cn\ac\rcpa\bio\database\ebi\protein\entry\types\*.*

java -cp .;..\lib\xercesImpl.jar;..\lib\xmlParserAPIs.jar;..\lib\castor-xml.jar;..\lib\commons-logging.jar; org.exolab.castor.builder.SourceGenerator -types j2 -i ProteinEntrySet.xsd -package cn.ac.rcpa.bio.database.ebi.protein.entry -dest ..\src