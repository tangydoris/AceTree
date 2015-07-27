/*
 * Created on May 15, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.rhwlab.snight;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Hashtable;

import qdxml.DocHandler;
import qdxml.QDParser;

/**
 * @author biowolp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XMLConfig implements DocHandler {

    static Config   iConfig;
    static String   iConfigFileName;
    String   iZipFile;
    String   iImageFile;
    String   iEndIndex;

    public static Config createConfigFromXMLFile(String fileName) {
        iConfigFileName = fileName;
        //println("XMLConfig: reading config file: " + iConfigFileName);
        new XMLConfig(fileName, null);
        // NB Config.setStartingParms was just called
        return iConfig;
    }

    public XMLConfig(String fileName) {
        try {
            FileReader fr = new FileReader(fileName);
            QDParser.parse(this, fr);
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public XMLConfig(String fileName, Config config) {
    	iConfig = config;
        try {
            FileReader fr = new FileReader(fileName);
            QDParser.parse(this, fr);
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) {
    }

    /* (non-Javadoc)
     * @see qdxml.DocHandler#startElement(java.lang.String, java.util.Hashtable)
     */
    public void startElement(String tag, Hashtable h) throws Exception {

        if (tag.equals("embryo")) {
            if (iConfig == null) {
            	iConfig = new Config(iConfigFileName, true);
            }
            iConfig.iConfigHash = new Hashtable();
            for (int i=0; i < iConfig.configParams.length; i++) {
                iConfig.iConfigHash.put(iConfig.configParams[i], "");
            }
        } else if (tag.equals("nuclei")) {
            String file = (String)h.get("file");
            iConfig.iConfigHash.put("zipFileName", file);
        } else if (tag.equals("image")) {
            String typical = (String)h.get("file");
            iConfig.iConfigHash.put("typical image", typical);
        } else if (tag.equals("start")) {
            String startIndex = (String)h.get("index");
            iConfig.iConfigHash.put("starting index", startIndex);
        } else if (tag.equals("end")) {
            String endIndex = (String)h.get("index");
            iConfig.iConfigHash.put("ending index", endIndex);
        } else if (tag.equals("naming")) {
            String method = (String)h.get("method");
            iConfig.iConfigHash.put("namingMethod", method);
        } else if (tag.equals("axis")) {
            String axis = (String)h.get("axis");
            iConfig.iConfigHash.put("axis", axis);
        } else if (tag.equals("polar")) {
            String size = (String)h.get("size");
            iConfig.iConfigHash.put("polarSize", size);
            println("startElement: " + size);
        } else if (tag.equals("resolution")) {
            String xyRes = (String)h.get("xyRes");
            iConfig.iConfigHash.put("xyRes", xyRes);
            String zRes = (String)h.get("zRes");
            iConfig.iConfigHash.put("zRes", zRes);
            String planeEnd = (String)h.get("planeEnd");
            iConfig.iConfigHash.put("planeEnd", planeEnd);
        } else if (tag.equals("exprCorr")) {
            String exprCorr = (String)h.get("type");
            iConfig.iConfigHash.put("exprCorr", exprCorr);
        } else if (tag.equals("useZip")) {
            String useZip = (String)h.get("type");
            iConfig.iConfigHash.put("use zip", useZip);
        } else if (tag.equals("useStack")) {
            String useStack = (String)h.get("type");
            iConfig.iConfigHash.put("use stack", useStack);   
        } else if (tag.equals("splitChannelImage")) {
        	String splitChannelImage = (String)h.get("type");
        	iConfig.iConfigHash.put("splitChannelImage", splitChannelImage);
        } else if (tag.equals("angle")) {
        	String degrees = (String)h.get("degrees");
        	iConfig.iConfigHash.put("angle", degrees);
        } else if (tag.equals("center")) {
        	String x = (String)h.get("x");
        	String y = (String)h.get("y");
        	iConfig.iConfigHash.put("x", x);
        	iConfig.iConfigHash.put("y", y);
        }


    }

    /* (non-Javadoc)
     * @see qdxml.DocHandler#endElement(java.lang.String)
     */
    public void endElement(String tag) throws Exception {
        //println("endElement: " + tag);

    }

    /* (non-Javadoc)
     * @see qdxml.DocHandler#startDocument()
     */
    public void startDocument() throws Exception {
        //println("startDocument: ");

    }

    /* (non-Javadoc)
     * @see qdxml.DocHandler#endDocument()
     */
    public void endDocument() throws Exception {
        //println("endDocument: ");
        iConfig.setStartingParms();
    }

    /* (non-Javadoc)
     * @see qdxml.DocHandler#text(java.lang.String)
     */
    public void text(String str) throws Exception {
        //println("text: " + str);

    }
    public static void println(String s) {System.out.println(s);}
    public static final String CS = ", ";

}
