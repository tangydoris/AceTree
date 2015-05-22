package org.rhwlab.snight;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Hashtable;

public class MeasureCSV {

	Hashtable		iMeasureHash;
	String			iFilePath;
	int				iGoodLinesRead;

	public MeasureCSV() {
		iMeasureHash = new Hashtable();
		for (int i=0; i < att.length; i++) {
			iMeasureHash.put(att[i], "");
		}
	}


	public MeasureCSV(String filepath) {
		this();
		iFilePath = filepath;
		int goodLinesRead = 0;
		boolean namesRead = false;
		String [] names = null;
		try {
			FileInputStream fis = new FileInputStream(filepath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			while(br.ready()) {
				String s = br.readLine();
				if (s.length() < 2) break;
				goodLinesRead++;
				if (!namesRead) {
					names = s.split(C);
					namesRead = true;
					continue;
				} else {
					String [] values = s.split(C);
					for (int i=0; i < values.length; i++) {
						String value = (String)iMeasureHash.get(names[i]);
						iMeasureHash.put(names[i], values[i]);
					}
				}
			}
		} catch(IOException ioe) {
			println("MeasureCSV file exception");
		}
		iGoodLinesRead = goodLinesRead;
		//checkHash();
	}

	public int getGoodLinesRead() {
		return iGoodLinesRead;
	}

	public int isMeasured() {
    	for (int i=0; i < att.length - 2; i++) {
    		String value = (String)iMeasureHash.get(att[i]);
    		if (value.length() == 0) return 1;
    	}
		return 0;
	}

	public void put(String item, String value) {
		iMeasureHash.put(item, value);
	}

	public String get(String item) {
		return (String)iMeasureHash.get(item);
	}

	public String toString() {
		String r = get("name");
		for (int i=1; i < att.length; i++) {
			r += C + get(att[i]);
		}

		return r;
	}

	public void writeCSV() {
		writeCSV(iFilePath);
	}

    public void writeCSV(String filePath) {

    	/*
        int k = filePath.lastIndexOf("/");
        String s = "";
        String series = (String)iMeasureHash.get(att[SERIES]);
        if (k == filePath.length() - 1) s = filePath + series;
        else s = filePath + "/" + series;
        s += "AuxInfo.csv";
        //println("writeCSV: " + s);
         */
        PrintWriter pw = null;
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            pw = new PrintWriter(fos);
        } catch(IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
        StringBuffer sb = new StringBuffer(att[0]);
        for (int i=1; i < att.length; i++) {
        	sb.append(C + att[i]);
        }
        pw.println(sb.toString());
        sb = new StringBuffer((String)iMeasureHash.get(att[0]));
        for (int i=1; i < att.length; i++) {
        	sb.append(C + (String)iMeasureHash.get(att[i]));
        }
        pw.println(sb.toString());
        pw.close();


    }

    public static String [] att = {
   	 "name"
   	,"slope"
   	,"intercept"
   	,"xc"
   	,"yc"
   	,"maj"
   	,"min"
   	,"ang"
   	,"zc"
   	,"zslope"
   	,"time"
   	,"zpixres"
   	,"axis"

   };

    // revised 20090701
    public static String [] defaultAtt = {
    	"xxxx"
    	,"0.9"
    	,"-27"
    	,"360"
    	,"255"
    	,"585"
    	,"390"
    	,"0"
    	,"14"
    	,"10.4"
    	,"160"
    	,"11.1"
    	,"XXX"
    };


    public static final int
    SERIES = 0
   ,TSLOPE = 1
   ,TINTERCEPT = 2
   ,EXCENTER = 3
   ,EYCENTER = 4
   ,EMAJOR = 5
   ,EMINOR = 6
   ,EANG = 7
   ,ZCENTER = 8
   ,ZSLOPE = 9
   ,TIME = 10
   ,ZPIXRES = 11
   ,AXIS = 12
   ;



    public void checkHash() {
    	for (int i=0; i < att.length; i++) {
    		String value = (String)iMeasureHash.get(att[i]);
    		println("checkHash, " + att[i] + CS + value);
    	}
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		println("MeasureCSV.main, ");
		String filepath = "/nfs/waterston1/annots/bao/081505/dats/081505AuxInfo.csv";
		filepath = "/nfs/waterston1/annots/murray/20060706_pha4_b2/dats/20060706_pha4_b2AuxInfo.csv";
		filepath = "/net/waterston/vol1/annots/murray/010306_pha4red/dats/010306_pha4redAuxInfo.csv";
		MeasureCSV mcsv = new MeasureCSV(filepath);
		mcsv.checkHash();
		int g = mcsv.getGoodLinesRead();
		if (g != 2) println("MeasureCSV.main, " + g + CS + "questionable AuxInfo");

	}

	private static void println(String s) {System.out.println(s);}
    private static void print(String s) {System.out.print(s);}
    private static final String CS = ", ", C = ",";
    private static final String TAB = "\t";
    private static final DecimalFormat DF0 = new DecimalFormat("####");
    private static final DecimalFormat DF1 = new DecimalFormat("####.#");
    private static final DecimalFormat DF4 = new DecimalFormat("####.####");
    private static String fmt4(double d) {return DF4.format(d);}
    private static String fmt1(double d) {return DF1.format(d);}
    private static String fmt0(double d) {return DF0.format(d);}


}
