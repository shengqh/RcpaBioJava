package cn.ac.rcpa.utils;

/*********************************************************************}
{                                                                     }
{  JavaToDPR - Generates a Delphi project file from a Java class      }
{              class file.                                            }
{                                                                     }
{  JavaToDPR produces an Object Pascal file from a Java class.        }
{  This file forms the basis that allows Java and Delphi-produced     }
{  code to interact via the Java Native Interface.                    }
{                                                                     }
{  This functionality is similar to the javah tool from Sun           }
{  Microsystems, Inc., which produces C header files from Java        }
{  class files. Some code is based on JNI2Pas which was written       }
{  by Jeffrey Schwartz                                                }
{                                                                     }
{ Copyright (C) 2001 MMG and Associates                               }
{ www.pacifier.com/~mmead/jni/delphi                                  }
{                                                                     }
{ Portions Copyright (C) 2001 Jeffrey Schwartz                        }
{ www.jeffschwartz.net (me@jeffschwartz.net)                          }
{                                                                     }
{ dollarsToTwentyFours, stretchUnderscores and addSigIfNeeded         }
{ Copyright (C) 2005 Daniel U. Thibault                               }
{ www.bigfoot.com/~D.U.Thibault (D.U.Thibault@Bigfoot.com)            }
{                                                                     }
{ The contents of this file are used with permission, subject to      }
{ the Mozilla Public License Version 1.1 (the "License"); you may     }
{ not use this file except in compliance with the License. You may    }
{ obtain a copy of the License at                                     }
{ http://www.mozilla.org/NPL/NPL-1_1Final.html                        }
{                                                                     }
{ Software distributed under the License is distributed on an         }
{ "AS IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or      }
{ implied. See the License for the specific language governing        }
{ rights and limitations under the License.                           }
{                                                                     }
{ History:                                                            }
{   25 Feb 2005 - fixed bug in mapJavaTypeToSigType                   }
{                 (did not replace periods with slashes)              }
{   23 Feb 2005 - addSigIfNeeded added to handle overloaded methods   }
{                 stretchUnderscores to handle underscores in names   }
{   18 Feb 2005 - dollarsToTwentyFours added to handle inner classes  }
{   06 May 2001 - Created.                                            }
{                                                                     }
{*********************************************************************/

/**
 * Title:        JavaToDPR
 * Description:  JavaToDPR is an Object Pascal version of javah (from Sun Microsystem's JDK) that implements a subset of javah, primarily the generation of function/procedure declarations from native methods declared in a Java class file. The resulting file is a Delphi project file (.dpr)
 * Copyright:    Copyright (c) 2001-2005
 * Company:      MMG and Associates
 * @author Matthew Mead; Daniel U. Thibault
 * @version 1.0.3
 */

import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.Vector;

//*********************************************************************************************************************
//*********************************************************************************************************************
//*********************************************************************************************************************
// JavaToDPR
//

  /**
   * JavaToDPR is the public interface to the set of Java classes that extract native method declarations
   * from a Java class file and produce a Delphi project file, a .dpr file. This class wraps the other
   * classes and provides a simple interface for the user. The result of using this class is a Delphi
   * project file that can immediately be compiled to a native DLL. The programmer must still implement
   * the stub functions/procedures.
   */
public class Java2Dpr
{
    /**
     *  This is the starting point for the processing. The command line arguments control how
     *  the other classes extract the methods from a Java class file to produce the Delphi
     *  project file.
     */
  public static void main(String[] args) {
    Java2Dpr javaToDPR = new Java2Dpr();
    try {
      if (!javaToDPR.parseCommandLine(args))
        System.exit(-1);

      javaToDPR.extractMethods();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

    /**
     *  The name of the class to process.
     */
  private String className = "";

    /**
     *  If the output is directed to a file, this is the name. Default is empty string.
     *  (Output is sent to System.out)
     */
  private String outputFilename = "";

    /**
     *  The target platform of the generated code (Linux, Win32, or ALL.) Default is ALL.
     */
  private String platform = "ALL";

    /**
     *  Flag indicating verbose output. Default is <b>false</b>.
     */
  private boolean verboseOutput = false;

    /**
     *  Flag controlling the method extraction. If true, all methods (not just native) are
     *  retrieved from the class file. Default is <b>false</b>.
     */
  private boolean includeNonNativeMethods = false;

    /**
     *  A constant string representing the version of this tool.
     */
  private final String VERSION = "1.0.3";

    /**
     *  The vector of methods that were retrieved from the class file.
     */
  private NativeMethods nativeMethods;

    /**
     *  The destination of the output.
     */
  private PrintWriter outputStream;

    /**
     *  A method used during debugging. Simply outputs the private member fields.
     */
  public void dump() {
    System.out.println("className: " + className);
    System.out.println("outputFilename: " + outputFilename);
    System.out.println("verboseOutput: " + verboseOutput);
    System.out.println("includeNonNativeMethods: " + includeNonNativeMethods);
    System.out.println("platform: " + platform);
  }

    /**
     *  Displays a usage screen.
     */
  private void displayHelp() {
    System.out.println("Usage: JavaToDPR [options] class");
    System.out.println();
    System.out.println("where [options] include:");
    System.out.println();
    System.out.println("        -help                 Print this help message");
    System.out.println("        -o <file>             Output file (default is stdout)");
    System.out.println("        -version              Print version information then quit");
    System.out.println("        -verbose              Enable verbose output");
    System.out.println("        -all                  Include non-native methods (internal debugging)");
    System.out.println("        -platform <platform>  For code generation (Linux|Win32|All)");
    System.out.println();
    System.out.println("<classes> are specified with their fully qualified names (for instance, java.awt.Rectangle).");
    System.out.println();
  }

    /**
     *  Displays the version information.
     */
  private void displayVersion() {
    System.out.println("Version " + VERSION);
  }

    /**
     *  Displays an error message.
     */
  private void displayError(String errMessage) {
    System.err.println(errMessage);
  }

    /**
     *  Parses the command line and sets the private fields from the arguments.
     *
     * @param args[] This is the array of strings that was passed to the
     * <b>public static</b> main method.
     * @return Returns true if execution should continue, false if execution should stop.
     */
  private boolean parseCommandLine(String args[]) throws CommandLineException {

    if (args.length < 1) {
      displayHelp();
      return false;
    }

    if (args[args.length - 1].indexOf('-') != 0)
      className = args[args.length - 1];

    for (int i = 0; i < args.length; i++) {

      if (args[i].compareToIgnoreCase("-all") == 0)
        includeNonNativeMethods = true;
      else if (args[i].compareToIgnoreCase("-o") == 0) {
        if (args.length > i + 1)
          outputFilename = args[i + 1];
        else {
          throw new CommandLineException("No output filename specified with -o");
        }
      }
      else if (args[i].compareToIgnoreCase("-platform") == 0) {
        if (args.length > i + 1)
          platform = args[i + 1].toUpperCase();
        else {
          throw new CommandLineException("No platform specified with -platform");
        }
      }
      else if (args[i].compareToIgnoreCase("-verbose") == 0) {
        String s = System.getProperty("java.class.path");
        System.err.println("[Search path = " + s + "]");
        verboseOutput = true;
      }
      else if (args[i].compareToIgnoreCase("-version") == 0) {
        displayVersion();
        return false;
      }
      else if (args[i].compareToIgnoreCase("-help") == 0) {
        displayHelp();
        return false;
      }
      else if (args[i].indexOf('-') == 0) {
        throw new CommandLineException("Unknown command line argument: " + args[i]);
      }
    }
    return true;
  }

    /**
     *  This method is the work-horse and calls on the other classes, NativeMethods and
     *  NativeMethod to do the real work.
     */
  public void extractMethods() throws NativeMethodException {

    if (className.length() == 0)
      throw new NativeMethodException("No classname specified");

    if (outputFilename.equals(""))
      outputStream = new java.io.PrintWriter(System.out);
    else {
      try {
        outputStream = new java.io.PrintWriter(new FileWriter(outputFilename));
      }
      catch (java.io.IOException e) {
        System.err.println("Unable to create file: " + outputFilename);
        return;
      }
    }

    try {
      nativeMethods = new NativeMethods(className);
      nativeMethods.setPlatform(platform);
      nativeMethods.setIncludeNonNativeMethods(includeNonNativeMethods);
      nativeMethods.setVerboseOutput(verboseOutput);
      boolean b = nativeMethods.retrieveMethods();
      if (b)
        nativeMethods.emitCode(outputStream);
      outputStream.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}



//*********************************************************************************************************************
//*********************************************************************************************************************
//*********************************************************************************************************************
// NativeMethods
//

  /**
   * NativeMethods represents a vector of all of the methods in a Java class file that are
   * declared with the <b>native</b> modifier. The vector contains references to
   * NativeMethod objects. NativeMethods is a container class.
   */
class NativeMethods extends Vector
{
    /**
     *  The name of the class that this object represents.
     */
  private String className;

    /**
     *  Flag indicating verbose output. Default is <b>false</b>.
     */
  private boolean verboseOutput;

    /**
     *  Flag controlling the method extraction. If true, all methods (not just native) are
     *  retrieved from the class file. Default is <b>false</b>.
     */
  private boolean includeNonNativeMethods;

    /**
     *  The target platform of the generated code (Linux, Win32, or ALL.) Default is ALL.
     */
  private String platform;

    /**
     * Constructs an empty NativeMethods vector and sets default values for fields.
     * <br><br><pre>
     * platform = "ALL"
     * verboseOutput = false;
     * includeNonNativeMethods = false;</pre>
     * @param className  The Java class that is to be processed.
     *
     */
  public NativeMethods(String className) {
    this.className = className;
    platform = "ALL";
    verboseOutput = false;
    includeNonNativeMethods = false;
  }


    /**
     *  Gets the name of the class associated with the methods.
     *  @return The name of the class.
     */
  public String getClassName() {
    return className;
  }

    /**
     *  Sets verboseOutput to value.
     */
  public void setVerboseOutput(boolean value) {
    verboseOutput = value;
  }

    /**
     *  Gets the value of verboseOutput.
     *  @return The value of verboseOutput.
     */
  public boolean getVerboseOutput() {
    return verboseOutput;
  }

    /**
     *  Sets includeNonNativeMethods to value.
     *  Setting this field to true will cause the NativeMethods.retriveMethods() method
     *  to retrieve non-native methods as well. Only native methods are necessary
     *  for JNI programming, but it is sometimes useful to extract other methods
     *  for testing purposes.
     */
  public void setIncludeNonNativeMethods(boolean value) {
    includeNonNativeMethods = value;
  }

    /**
     *  Gets the value of includeNonNativeMethods.
     *  @return The value of includeNonNativeMethods.
     */
  public boolean getIncludeNonNativeMethods() {
    return includeNonNativeMethods;
  }

    /**
     *  Sets the platform. Can be one of three values: LINUX, WIN32, or ALL.
     *  The platform controls code generation. Specifically, these values
     *  will cause the native methods to be tagged with either <b>cdecl</b>,
     *  <b>stdcall</b>, or both, via an {$IFDEF} construct.
     *
     */
  public void setPlatform(String value) throws NativeMethodException {
    String temp = value.toUpperCase();
    if (!value.equals("ALL") && !value.equals("LINUX") && !value.equals("WIN32"))
      throw new NativeMethodException("Unknown platform: " + value);

    platform = temp;
  }

   /**
    *  Gets the value of platform.
    *  @return The value of platform, either LINUX, WIN32, or ALL.
    */
  public String getPlatform() {
    return platform;
  }

   /**
    *  Retrieves all of the methods of the class and adds each one to the vector that
    *  NativeMethods represents.
    *  For each method in the class, three pieces of information are retrieved:
    *  <ol>
    *  <li>The name of the method
    *  <li>The method's type (return type)
    *  <li>The types of parameters
    *  </ol>
    *  @return true, if successful, false, if some kind of exception occurred.
    *
    */
  public boolean retrieveMethods() {
    try {
      Class clazz = ClassLoader.getSystemClassLoader().loadClass(className);
      Method[] methods = clazz.getDeclaredMethods();
      for (int i = 0; i < methods.length; i++) {
        int modifiers = methods[i].getModifiers();
        boolean isNative = java.lang.reflect.Modifier.isNative(modifiers);
        if (isNative || includeNonNativeMethods) {
          NativeMethod nativeMethod = new NativeMethod(this);
          nativeMethod.setPlatform(platform);
          nativeMethod.setName(methods[i].getName());

          String returnType = methods[i].getReturnType().toString();
          String OPReturnType = JNISupport.mapJNITypeToOPType(returnType);
          String javaReturnType = JNISupport.mapJavaTypeToSigType(returnType);

          nativeMethod.setOPReturnType(OPReturnType);
          nativeMethod.setJavaReturnType(javaReturnType);

          if (returnType.equals("void"))
            nativeMethod.isFunction = false;
          else
            nativeMethod.isFunction = true;

          Class[] params = methods[i].getParameterTypes();

          for (int j = 0; j < params.length; j++) {
            String OPType = JNISupport.mapJNITypeToOPType(params[j].getName());
            nativeMethod.addOPParamType(OPType);

            String javaType = JNISupport.mapJavaTypeToSigType(params[j].getName());
            nativeMethod.addJavaSignatureType(javaType);
          }
          add(nativeMethod);
        }
      }
      return true;
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

    /**
     *  Generates the Delphi method name from the NativeMethod object.
     *  Unless there are overloaded methods in the elements() Enumeration,
     *  this is simply the NativeMethod.getName().
     *  If there are overloads, then "__" and a representation of the
     *  signature are added.
     *
     *  Examples:
     * Signature:  ()V                      Yields: __
     * Signature:  (ZBC)V                   Yields: __ZBC
     * Signature:  ([B)V                    Yields: ___3B
     * Signature:  (Ljava/lang/String;)V    Yields: __Ljava_lang_String_2
     *
     * @param method The NativeMethod whose name is to be obtained
     */
  public String addSigIfNeeded(NativeMethod method)
    throws NativeMethodException
  {
    String name = method.getName();
    //Find out the overload count
    int overload_count = 0;
    Enumeration en = elements();
    while (en.hasMoreElements())
    {
      if (name.equals(((NativeMethod) en.nextElement()).getName())) overload_count += 1;
    }
    if (overload_count < 1)
      throw new NativeMethodException("Overload count error");

    if (overload_count == 1)
      return JNISupport.dollarsToTwentyFours(JNISupport.stretchUnderscores(name, false));

    //overload_count > 1
    //We need JNISignature, but we cannot be in the NativeMethod class
    //(because we need the Enumeration of all native methods), so we
    //had to change the access of getJNISignature().
    String s = method.getJNISignature();    // Something like "(Ljava/lang/String;)V"
    
    //Strip parentheses
    s = s.substring(1, s.indexOf(")")); // Something like Ljava/lang/String;

    name = JNISupport.stretchUnderscores(name, false).concat("__");

    //A zero-length string at this point indicates void input (signature appended is just "__")
    int index = 0;
    String PrimitiveTypes = "ZBCSIJFD";
    while (index < s.length()) {
      char c = s.charAt(index);
      if (PrimitiveTypes.indexOf(c) > 0) {
        name = name.concat(s.substring(0, 1));
        index += 1;
      } else if (s.charAt(index) == 'L') {
        //Even though its a class name we're putting in, it uses the method name representation of underscores
        name = name.concat(JNISupport.slashesAndDotsToUnderscores(JNISupport.stretchUnderscores(s.substring(index, s.indexOf(";", index)), false)) + "_2"); //semi-colon is "_2"
        index = s.indexOf(";", index) + 1;
      } else if (s.charAt(index) == '[') {
        name = name.concat("_3");
        index += 1;
      } else {
        // There should be no other cases
        throw new NativeMethodException("Signature error");
      } //if
    } //while
    return JNISupport.dollarsToTwentyFours(name);
  }

    /**
     *  Generates a Delphi project file (.dpr) containing stub functions for each
     *  of the native methods in the class file.
     *
     * @param outputStream The destination for the output. This can be set to
     * an open file or to System.out to be redirected to a file.
     */
  public void emitCode(PrintWriter outputStream)
    throws NativeMethodException
  {
      // library (note that dollars become plain underscores in this case)
    outputStream.println("library " + JNISupport.dollarsToUnderscores(JNISupport.slashesAndDotsToUnderscores(JNISupport.stretchUnderscores(className, true))) + ";");
    outputStream.println("");

      // uses
    outputStream.println("uses JNI;");
    outputStream.println("");

      // functions/procedures
    Enumeration en = elements();
    while (en.hasMoreElements())
      ((NativeMethod) en.nextElement()).emitCode(className, outputStream);

      // exports
    outputStream.println("exports");
    en = elements();
    while (en.hasMoreElements()) {
      NativeMethod method = (NativeMethod) en.nextElement();
      String methodName = addSigIfNeeded(method);
      //Even though its a class name, it uses the method name representation of underscores
      outputStream.print("  Java_" + JNISupport.dollarsToTwentyFours(JNISupport.slashesAndDotsToUnderscores(JNISupport.stretchUnderscores(className, false))) + "_" + methodName);
      if (en.hasMoreElements())
        outputStream.println(",");
      else
        outputStream.println(";");
    }

      // end
    outputStream.println("");
    outputStream.println("end.");
  }
}



//*********************************************************************************************************************
//*********************************************************************************************************************
//*********************************************************************************************************************
// NativeMethod
//

  /**
   * A NativeMethod object represents a method in a Java class file that has been declared
   * with the <b>native</b> modifier. A NativeMethod contains information such as the
   * name of the method, the type, and the types of the parameters, if any. A NativeMethod
   * contains a method named emitCode, which generates an Object Pascal representation of
   * the Java method declaration.
   */
class NativeMethod
{
    /**
     *  The NativeMethods object that owns this NativeMethod.
     */
  private NativeMethods owner;

    /**
     *  The name of the method.
     */
  private String name;

    /**
     *  The return type of the method. (Object Pascal type)
     */
  private String OPReturnType = "";

    /**
     *  The return type of the method. (Java type)
     */
  private String javaReturnType = "";

    /**
     *  A vector of the parameters to the method. (Object Pascal types)
     */
  private Vector OPParamTypes;

    /**
     *  A vector of the parameters to the method. (Java signature types)
     */
  private Vector javaSignatureTypes;

    /**
     *  Flag indicating whether the method is an Object Pascal <b>function</b> (true),
     *  or an Object Pascal <b>procedure</b> (false).
     */
  public boolean isFunction;

    /**
     *  The target platform of the generated code (Linux, Win32, or ALL.) Default is ALL.
     */
  private String platform = "ALL";

  /**
   * Creates a new NativeMethod object.
   */
  public NativeMethod(NativeMethods theOwner) {
    owner = theOwner;
    OPParamTypes = new Vector();
    javaSignatureTypes = new Vector();
  }

    /**
     *  Generates an Object Pascal representation of the Java method declaration. The Object Pascal
     *  function/procedure is a stub that contains no executable code.
     *
     * @param className The name of the class that this method belongs to. The name of the
     * function/procedure is determined by the name of the class.
     *
     * @param outputStream The destination for the output. This can be set to
     * an open file or to System.out to be redirected to a file.
     */
  public void emitCode(String className, PrintWriter outputStream)
    throws NativeMethodException
  {

      // Generate header comment
    outputStream.println("(*");
    //Note that dollars are plain underscores, and underscores stretch to "_0005f"
    outputStream.println(" * Class:      " + JNISupport.dollarsToUnderscores(JNISupport.slashesAndDotsToUnderscores(JNISupport.stretchUnderscores(className, true))));
    outputStream.println(" * Method:     " + JNISupport.dollarsToTwentyFours(name));
    outputStream.println(" * Signature:  " + getJNISignature());
    outputStream.println("*)");

    if (isFunction)
      outputStream.print("function ");
    else
      outputStream.print("procedure ");

    //Note that dollars are "_00024", and underscores stretch to "_1" (the exact opposite of the comment!)
    outputStream.println("Java_" + JNISupport.dollarsToTwentyFours(JNISupport.slashesAndDotsToUnderscores(JNISupport.stretchUnderscores(className, false))) + "_" + owner.addSigIfNeeded(this));
    outputStream.print("  (PEnv: PJNIEnv; Obj: JObject");

    Enumeration en = OPParamTypes.elements();
    int count = 1;
    while (en.hasMoreElements()) {
      String param = (String) en.nextElement();
      outputStream.print("; ");
      outputStream.print("Arg" + (count++) + ": " + param.toString());
    }

    outputStream.print(")");
    if (isFunction)
      outputStream.print(": " + OPReturnType);

    if (platform.equals("LINUX"))
      outputStream.println("; cdecl;");
    else if (platform.equals("WIN32"))
      outputStream.println("; stdcall;");
    else //if (platform.equals("ALL"))
      outputStream.println("; {$IFDEF WIN32} stdcall; {$ENDIF} {$IFDEF LINUX} cdecl; {$ENDIF}");

    outputStream.println("begin");
    outputStream.println("end;");
    outputStream.println("");
  }

    /**
     *  Generates a string that represents a Java Native Interface signature.
     *  This is used in the comments that come before each Object Pascal function/procedure
     *  that is generated.
     *  @return A string that represents the signature of the method the object represents,
     *  e.g.:
     *  <pre> "int SomeFunc(long, int, String)" --> "(JILjava/lang/String;)I" </pre>
     */
  protected String getJNISignature() {
    String signature = "(";
    Enumeration en = javaSignatureTypes.elements();
    while (en.hasMoreElements())
      signature += en.nextElement().toString();

    signature += ")";

    if (isFunction)
      signature += javaReturnType;
    else
      signature += "V";

    return signature;
  }

    /**
     *  Gets the name of the NativeMethod object.
     *  @return The name of the method the object represents.
     */
  public String getName() {
    return name;
  }

    /**
     *  Sets the name of the NativeMethod object.
     */
  public void setName(String name) {
    this.name = name;
  }

    /**
     *  Gets the return type (Object Pascal type) of the NativeMethod object.
     *  @return The Object Pascal type (as a String) of the method the object represents.
     */
  public String getOPReturnType() {
    return OPReturnType;
  }

    /**
     *  Sets the return type (Object Pascal type) of the NativeMethod object.
     */
  public void setOPReturnType(String returnType) {
    OPReturnType = returnType;
  }

    /**
     *  Gets the return type (Java type) of the NativeMethod object.
     *  @return The Java type (as a String) of the method that the object represents.
     */
  public String getjavaReturnType() {
    return javaReturnType;
  }

    /**
     *  Sets the return type (Java type) of the NativeMethod object.
     */
  public void setJavaReturnType(String returnType) {
    javaReturnType = returnType;
  }

    /**
     *  Adds a parameter type to the vector of parameters. (Object Pascal type)
     */
  public void addOPParamType(String paramType) {
    OPParamTypes.add(paramType);
  }

    /**
     *  Adds a parameter type to the vector of parameters. (Java type)
     */
  public void addJavaSignatureType(String sigType) {
    javaSignatureTypes.add(sigType);
  }

    /**
     *  Sets the platform. Can be one of three values: LINUX, WIN32, or ALL.
     *  The platform controls code generation. Specifically, these values
     *  will cause the native methods to be tagged with either <b>cdecl</b>,
     *  <b>stdcall</b>, or both, via an {$IFDEF} construct.
     *
     */
  public void setPlatform(String value) throws NativeMethodException {
    String temp = value.toUpperCase();
    if (!temp.equals("ALL") && !temp.equals("LINUX") && !temp.equals("WIN32"))
      throw new NativeMethodException("Unknown platform");

    platform = temp;
  }

    /**
     *  Gets the platform string.
     *  @return The platform, either LINUX, WIN32, or ALL
     */
  public String getPlatform() {
    return platform;
  }

}



//*********************************************************************************************************************
//*********************************************************************************************************************
//*********************************************************************************************************************
// CommandLineException
//

  /**
   *  A CommandLineException is thrown in response to malformed command line arguments.
   */
class CommandLineException extends Exception {
    /**
     * Default constructor for unspecified exceptions.
     */
  public CommandLineException() {
    super("Unspecified exception");
  }

    /**
     * Constructor for specific exceptions.
     *
     * @param message A custom message that describes the type of CommandLineException.
     */
  public CommandLineException(String message) {
    super(message);
  }
}



//*********************************************************************************************************************
//*********************************************************************************************************************
//*********************************************************************************************************************
// NativeMethodException
//

  /**
   *  A NativeMethodException is thrown in response to exceptional circumstances regarding
   *  the processing of native methods within the Java class.
   */
class NativeMethodException extends Exception {
    /**
     * Default constructor for unspecified exceptions.
     */
  public NativeMethodException() {
    super("Unspecified exception");
  }
    /**
     * Constructor for specific exceptions.
     *
     * @param message A custom message that describes the type of NativeMethodException.
     */
  public NativeMethodException(String message) {
    super(message);
  }
}



//*********************************************************************************************************************
//*********************************************************************************************************************
//*********************************************************************************************************************
// JNISupport
//

  /**
   * A support class that contains static methods to help with the generation of Object Pascal code.
   */
class JNISupport
{
   /**
    * Replaces all occurrences of underscores "_" with "_1" (when a method name) or "_0005f" (when class name)
    * @param inputString the String to modify.
    * @param isClassName a boolean specifying whether this is a class name or not.
    * @return The string after the replacements have been made.
    */
  public static String stretchUnderscores(String inputString, boolean isClassName) {
    if (isClassName)
    {
      return inputString.replaceAll("_", "_0005f");
    } else {
      return inputString.replaceAll("_", "_1"); //method name
    }
  }

   /**
    * Replaces all occurrences of slashes "/" and dots (".") with underscores "_".
    * stretchUnderscores should be called before slashesAndDotsToUnderscores.
    * @param inputString the String to modify.
    * @return The string after the replacements have been made.
    */
  public static String slashesAndDotsToUnderscores(String inputString) {
    return inputString.replace('/', '_').replace('.', '_');
  }

   /**
    * Replaces all occurrences of dollars "$" with "_00024".
    * stretchUnderscores and slashesAndDotsToUnderscores should be called before dollarsToTwentyFours.
    * @param inputString the String to modify.
    * @return The string after the replacements have been made.
    */
  public static String dollarsToTwentyFours(String inputString) {
    return inputString.replaceAll("\\$", "_00024");
  }

   /**
    * Replaces all occurrences of dollars "$" with "_".
    * stretchUnderscores and slashesAndDotsToUnderscores should be called before dollarsToUnderscores.
    * @param inputString the String to modify.
    * @return The string after the replacements have been made.
    */
  public static String dollarsToUnderscores(String inputString) {
    return inputString.replaceAll("\\$", "_");
  }

   /**
    *  Given a string representing a JNI type, return the equivalent Object Pascal type.
    *  e.g:
    *  <pre>  "int" -> "JInt"
    *  "[D" -> "JDoubleArray" </pre>
    *
    * @param JNIType the String to modify.
    * @return A string representing the Object Pascal type.
    */
  public static String mapJNITypeToOPType(String JNIType) {

    String OPType;

      // Some types start with "class "
    int index = JNIType.indexOf(" ");
    JNIType = JNIType.substring(index+1);

    if (JNIType.equals("void"))
      OPType = "void";
    else if (JNIType.equals("int"))
      OPType = "JInt";
    else if (JNIType.equals("double"))
      OPType = "JDouble";
    else if (JNIType.equals("float"))
      OPType = "JFloat";
    else if (JNIType.equals("char"))
      OPType = "JChar";
    else if (JNIType.equals("short"))
      OPType = "JShort";
    else if (JNIType.equals("boolean"))
      OPType = "JBoolean";
    else if (JNIType.equals("byte"))
      OPType = "JByte";
    else if (JNIType.equals("long"))
      OPType = "JLong";
    else if (JNIType.equals("java.lang.String"))
      OPType = "JString";
    else if (JNIType.equals("[Z"))
      OPType = "JBooleanArray";
    else if (JNIType.equals("[B"))
      OPType = "JByteArray";
    else if (JNIType.equals("[C"))
      OPType = "JCharArray";
    else if (JNIType.equals("[S"))
      OPType = "JShortArray";
    else if (JNIType.equals("[I"))
      OPType = "JIntArray";
    else if (JNIType.equals("[J"))
      OPType = "JLongArray";
    else if (JNIType.equals("[F"))
      OPType = "JFloatArray";
    else if (JNIType.equals("[D"))
      OPType = "JDoubleArray";
    else if (JNIType.substring(0, 2).equals("[L"))
      OPType = "JObjectArray";
    else if (JNIType.substring(0, 2).equals("[["))
      OPType = "JObjectArray";
    else if (JNIType.equals("java.lang.Class"))
      OPType = "JClass";

      // the next two can be handled in the else if we like
    else if (JNIType.equals("java.lang.Object"))
      OPType = "JObject";
    else if (JNIType.substring(0, 1).equals("L"))
      OPType = "JObject";

    else
      OPType = "JObject";

    return OPType;
  }

   /**
    *  Given a string representing a Java type, return the equivalent signature type.
    *  e.g.:
    *  <pre>  "void" -> "V"
    *  "java.lang.String" -> "Ljava/lang/String;" </pre>
    *
    * @param javaType the String to modify.
    * @return A string representing the Java type.
    */
  public static String mapJavaTypeToSigType(String javaType) {

    String sigType;

      // Some types start with "class "
    int index = javaType.indexOf(" ");
    javaType = javaType.substring(index + 1);

    if (javaType.equals("void"))
      sigType = "V";
    else if (javaType.equals("int"))
      sigType = "I";
    else if (javaType.equals("double"))
      sigType = "D";
    else if (javaType.equals("float"))
      sigType = "F";
    else if (javaType.equals("char"))
      sigType = "C";
    else if (javaType.equals("short"))
      sigType = "S";
    else if (javaType.equals("boolean"))
      sigType = "Z";
    else if (javaType.equals("byte"))
      sigType = "B";
    else if (javaType.equals("long"))
      sigType = "J";

    else {
      sigType = javaType.replace('.', '/');

        // If it's not an array, it's an object, so mark it up
      if (sigType.indexOf("[") != 0)
        sigType = "L" + sigType + ';';
    }
    return sigType;
  }
}


