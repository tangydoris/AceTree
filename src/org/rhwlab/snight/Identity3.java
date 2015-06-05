package org.rhwlab.snight;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Vector;

import org.rhwlab.utils.EUtils;

public class Identity3 {
    //public static Identity      iIdentity;
    private static NucleiMgr    iNucleiMgr;
    private static Vector       nuclei_record;
    int							iNamingMethod;
    int							iStartingIndex;
    int							iNucCount;
    DivisionCaller				iDivisionCaller;
    Parameters					iParameters;
    String						iAxis;
    int							iEndingIndex;
    MeasureCSV					iMeasureCSV;

    PrintWriter					iPrintWriter;
    int							iStartTime;

    public Identity3(NucleiMgr nucleiMgr) {
    	//System.out.println("Identity3 construtor called.");
    	iNucleiMgr = nucleiMgr;
        iNamingMethod = iNucleiMgr.getConfig().iNamingMethod;
        nuclei_record = iNucleiMgr.getNucleiRecord();
        iParameters = iNucleiMgr.getParameters();
        iEndingIndex = iNucleiMgr.getEndingIndex();
        iMeasureCSV = iNucleiMgr.getMeasureCSV();
    }

    public void setPrintWriter(PrintWriter pw) {
    	iPrintWriter = pw;
    }

    // Called by NucleiMgr processNuclei method
    @SuppressWarnings("unused")
	public void identityAssignment() {
    	println("Identity3.identityAssignment, entered");
    	if (iNamingMethod == MANUAL) {
    		println("identityAssignment, skip naming due to MANUAL naming method");
    		return;
    	}
    	
        iStartingIndex = iNucleiMgr.getConfig().iStartingIndex;
        //System.out.println("Starting index from nuclei mgr: "+iStartingIndex);
        
        iStartTime = iNucleiMgr.getStartTime();
        //System.out.println("Starting time from nuclei mgr: "+iStartTime);
        
        int newstart = iStartingIndex;
        if (iStartingIndex < iStartTime)
        	newstart = iStartTime;
        clearAllNames();
        //System.out.println("identityAssignment iStartingIndex: " + iStartingIndex);
        iParameters.axis = 0; // a flag telling if we have an axis 0 = no axis
        int start[] = new int[1];
        start[0] = newstart;
        int [] lineage_ct_p = new int[1];
        lineage_ct_p[0] = 1;
        //lineage_ct_p[0] = 4;
        int lin_ct = lineage_ct_p[0];
        iAxis = tryForAxis(); // sets iParameters.axis to 1 if if finds one
        // if NEWCANONICAL and a an axis was specified we leave this function here
        // we do not run initialID so the user can specify the initial cell names
        if (iParameters.axis == 1 && iNamingMethod == NEWCANONICAL) {
        	println("identityAssignment, using specified axis, " + iAxis);
            useCanonicalRules(start, lineage_ct_p);
            return;
        }
        // if no axis was specified then the initialID code will be run
        // you could still use NEWCANONICAL here if iStartingIndex is greater than one
        // but this should be phased out
        if (newstart >= 1) {
            InitialID initID = new InitialID(iNucleiMgr, iParameters);
            int mm = initID.initialID(start, lineage_ct_p);
            //System.out.println("InitialID.initialID returmed mm: "+mm);
        	if (mm > 0) {
        		System.out.println("detected backtrace failure, lineage from start");
        		start[0] = 0; //start from scratch on failure of initialID
            	iNucCount = 1;
        	}
            if (iParameters.axis > 0) {
            	iParameters.ap = iParameters.apInit;
            	iParameters.dv = iParameters.dvInit;
            	iParameters.lr = iParameters.lrInit;
            	iAxis = getOrientation();

            	//System.out.println("initialID returned: " + mm);
            	lin_ct = lineage_ct_p[0];
            	//System.out.println("identityAssignment starting at: " + start[0]);
                iNucCount = initID.getNucCount();
            	if (iNamingMethod == NEWCANONICAL && start[0] > 0) {
            		useCanonicalRules(start, lineage_ct_p);
            		return;
            	}
            }
        }
        println("identityAssignment, reached code end, " + iStartingIndex + CS + start[0]);
        // Try this
        start[0] = newstart;
        // we are going to assign Nuc names from here on by a simple method
        for (int i = start[0]; i < iEndingIndex; i++) {
            Vector nuclei = (Vector)nuclei_record.elementAt(i);
            Vector nuclei_prev = null;
            int nuc_ct = nuclei.size();
            //System.out.println("Nuclei vector size: "+nuc_ct);
            if (i > 0)
            	nuclei_prev = (Vector)nuclei_record.elementAt(i - 1);
            Nucleus nucleij = null;
            for (int j = 0; j < nuc_ct; j++) {
                nucleij = (Nucleus)nuclei.elementAt(j);
                if (nucleij.status == Nucleus.NILLI) {
                	//System.out.println("Time "+i+", no predecessor for "+nucleij.identity);
                	continue;
                }
                if (nuclei_prev != null && nucleij.predecessor != Nucleus.NILLI) {	
                    Nucleus pred = (Nucleus)nuclei_prev.elementAt(nucleij.predecessor - 1);
                    if (pred.identity == "") {
                    	int z = (int)Math.round(pred.z);
                		pred.identity = NUC + EUtils.makePaddedInt(i) + "_" + z + "_" + pred.x + "_" + pred.y;
                    }
                    if (pred.successor2 == Nucleus.NILLI) {
                     	nucleij.identity = pred.identity;
                       	continue;
                    }
                    else {
                       	// case of dividing pred
                       	Nucleus sister = (Nucleus)nuclei.get(pred.successor2 - 1);
                       	// Nucleus doesn't have forced name
                       	
                       	if (!nucleij.assignedID.equals(""))
                       		nucleij.identity = nucleij.assignedID;
                       	else {
	                       	nucleij.identity = pred.identity + "a";
	                       	sister.identity = pred.identity + "p";
                       	}
	                       	
                       	/*
                       	if (nucleij.identity.startsWith("A"))
                       		System.out.println("Identity, sister identity: "+nucleij.identity+", "+sister.identity);
                       	*/
                       	continue;
                    }
                } else {
                	//System.out.println("No previous nuclei time-record");
                 	// this is the first encounter of this nucleus
                   	//nucleij.identity = NUC + iNucCount++;
                	int z = (int)Math.round(nucleij.z);

                	if (nucleij.assignedID.equals("")) {
                		nucleij.identity = NUC + EUtils.makePaddedInt(i + 1) + "_" + z + "_" + nucleij.x + "_" + nucleij.y;
                	}
                	else {
                		System.out.println("###forced name: " + nucleij.assignedID);
                		nucleij.identity = nucleij.assignedID;
                	}
                	//println("identityAssignment, adding nuc, " + nucleij);
                }
           }
        }
    }
    
    @SuppressWarnings("unused")
	private void clearAllNames() {
        int k = iNucleiMgr.getNucleiRecord().size();
        int endingIndex = iEndingIndex;
        //for (int i = 0; i < iEndingIndex; i++) {
        for (int i = iStartingIndex - 1; i < iEndingIndex; i++) {
            //println("clearAllNames: " + i + CS + iEndingIndex);
            if (!(i < k))
            	break;
            //for (int i = 0; i < iEndingIndex - iNucleiMgr.getConfig().iStartingIndex; i++) {
            if (iStartingIndex > 1 && i == iStartingIndex - 1)
            	continue;
            clearNames((Vector)iNucleiMgr.getNucleiRecord().elementAt(i));
        }
    }

    // Clears all non-forced names
    @SuppressWarnings("unused")
	private void clearNames(Vector nuclei) {
        //println("cleaarNames: " + nuclei.size());
        Nucleus n;
        for (int i=0; i < nuclei.size(); i++) {
            n = (Nucleus)nuclei.elementAt(i);
            String id = n.identity;
            if (n.assignedID.length() > 0) {
            	continue;
            }
            n.identity = "";
        }
    }

    @SuppressWarnings("unused")
	public void useCanonicalRules(int [] start, int [] lineage_ct_p) {
    	Vector report = new Vector();
    	String series = iNucleiMgr.getConfig().getShortName();
    	//println("Identity3.useCanonicalRules, series = " + series + ", axis = " + iAxis);
        double zPixRes = iNucleiMgr.getZPixRes();
    	iDivisionCaller = new DivisionCaller(iAxis, zPixRes, iMeasureCSV);

    	//iNucCount = 1;
        int iEndingIndex = iNucleiMgr.getEndingIndex();
        int k = iNucleiMgr.getNucleiRecord().size();
        int m = Math.min(k, iEndingIndex);
        System.out.println("useCanonicalRules starting at: " + start[0] + CS + iEndingIndex);
        int nuc_ct = 0;
        int i;
        Vector nuclei = null;
        int breakout = 0;
        for (i = start[0]; i < m; i++) {
            if (breakout > 0) {
                System.out.println("Identity3.useCanonicalRules exiting, breakout=" + breakout);
                System.exit(0);
                break;
            }
            nuclei = (Vector)nuclei_record.elementAt(i - 1);
            nuc_ct = nuclei.size();
            Nucleus parent = null;
            Vector nextNuclei = (Vector)nuclei_record.elementAt(i);
            parent = null;
            
            for (int j = 0; j < nuc_ct; j++) {
                parent = (Nucleus)nuclei.elementAt(j);
                //println("useCanonicalRules, " + i + CS + j + CS + parent.identity + CS + parent.status);
                if (parent.status == Nucleus.NILLI) {
                   //println("useCanonicalRules, XXX, " + i + CS + j + CS + parent.identity + CS + parent.status);
                	continue;
                }
                String pname = parent.identity;
                // NUC NAMEING PROCEDURE MODIFIED 20100630
                if (pname == null || pname.length() == 0) {
                    //pname = NUC + iNucCount++;
                	int z = (int)Math.round(parent.z);
                	
                	// Try to only use the Nuc... name when there is no forced name in assignedID
                	if (parent.assignedID.equals(""))
                		pname = NUC + EUtils.makePaddedInt(i + 1) + "_" + z + "_" + parent.x + "_" + parent.y;
                	else
                		pname = parent.assignedID;
                    parent.identity = pname;
                	//println("useCanoncalRules, adding nuc, " + parent.identity);
                }
                if (pname.equals("ABplr") || parent.assignedID.equals("ABplr")) {
                	int kkkk = 0;
                }
                boolean good = (parent.successor1 > 0 && parent.successor2 > 0);
                if (!good) {
                    // not dividing so just extend the name
                    if (parent.successor1 > 0) {
                        Nucleus n = (Nucleus)nextNuclei.elementAt(parent.successor1 - 1);
                        if (n.assignedID.length() <= 0) {
                            //println("useCanonicalRules, XXXXXX, " + i + CS + j + CS + parent.identity + CS + parent.status + CS + n.identity);
                        	//println("useCanonicalRules, XXXXXX, "+parent.identity);
                        	n.identity = pname;
                        } else {
                        	int kkk = 0;
                        }
                    }
                    continue;
                }
                // this canonical parent is dividing
                Nucleus dau1 = (Nucleus)nextNuclei.elementAt(parent.successor1 - 1);
                Nucleus dau2 = (Nucleus)nextNuclei.elementAt(parent.successor2 - 1);
                StringBuffer sb = iDivisionCaller.assignNames(parent, dau1, dau2);
                if (sb != null) {
                	//println(sb.toString());
                	report.add(sb.toString());
                }
                usePreassignedID(dau1, dau2);

            }
        }
        Collections.sort(report);
        for (int ii=0; ii < report.size(); ii++) {
        	//String s = (String)report.get(ii);
        	//if (iPrintWriter != null) iPrintWriter.println(s);
        	//println(s);

        }


    }

    private void usePreassignedID(Nucleus dau1, Nucleus dau2) {
        //println("usePreassignedID: " + dau1.identity + CS + dau2.identity);
        //println("usePreassignedID:2 " + dau1.assignedID + CS + dau2.assignedID);
        if (dau1.assignedID.length() == 0 && dau2.assignedID.length()== 0) {
            return;
        }
        //println("usePreassignedID:3 " + dau1.assignedID + CS + dau2.assignedID);
        if (dau1.assignedID.length() > 0) 
        	dau1.identity = dau1.assignedID;
        if (dau2.assignedID.length() > 0) 
        	dau2.identity = dau2.assignedID;

        if (dau1.identity.equals(dau2.identity)) {
            String s = dau2.identity;
            s = s.substring(0, s.length() - 1);
            s = s + "X";
            dau2.identity = s;
        }
    }


    String tryForAxis() {
        String axis = iNucleiMgr.getConfig().iAxisGiven;
        if (axis.length() > 0) iParameters.axis = 1;
        return axis;

    }

    @SuppressWarnings("unused")
	public String getOrientation() {
        //println("getOrientation: ");
        //new Throwable().printStackTrace();
        String orientation = "A";
        String late = "A";
        if (iParameters.ap < 0) orientation = "P";
        if (iParameters.dv > 0) orientation += "D";
        else orientation += "V";
        if (iParameters.lr > 0) orientation += "L";
        else orientation += "R";
        if (orientation.equals("ADL")) late = "ARD";
        else if (orientation.equals("AVR")) late = "ALV";
        else if (orientation.equals("PDR")) late = "PLD";
        else if (orientation.equals("PVL")) late = "PRV";
        return orientation;
    }

    public String getAxis() {
    	return iAxis;
    }

    public int getNamingMethod() {
        return iNamingMethod;
    }

    public void setNamingMethod(int method) {
        System.out.println("Identity3.setNamingMethod called with: " + method + CS + NAMING_METHOD[method]);
        iNamingMethod = method;
    }


    // called from class Analysis
    public String makeSisterName(String s) {
        String sis = null;
        char x = s.charAt(0);
        int n = s.length();
        boolean b = n == 1;
        switch(x) {
            case 'C':
                if (b) return("P3");
            case 'D':
                if (b) return("P4");
                else {
                    sis = replaceLastChar(s);
                    break;
                }
            case 'E':
                if (b) return ("MS");
                else {
                    sis = replaceLastChar(s);
                    break;
                }
            case 'M':
                if (n == 2) return("E");
                else {
                    sis = replaceLastChar(s);
                    break;
                }
            case 'A':
                if (s.equals("ABal")) return("ABar");
                if (s.equals("ABpl")) return ("ABpr");
                sis = replaceLastChar(s);
                break;
            case 'R': /*20050824 was Z but no such special case */
                if (s.equals("Z2")) sis = "Z3";
                else sis = "Z2";
                break;
            case 'P':
                if (s.equals("P2")) sis = "EMS";
                else if (s.equals("P3")) sis = "C";
                else if (s.equals("P4")) sis = "D";
                break;
            default:
                sis = replaceLastChar(s);
        }
        return sis;
    }


    public String replaceLastChar(String s) {
        StringBuffer sb = new StringBuffer(s);
        int n = sb.length() - 1;
        char x = sb.charAt(n);
        switch(x) {
        case 'a':
            sb.setCharAt(n, 'p');
            break;
        case 'l':
            sb.setCharAt(n, 'r');
            break;
        case 'd':
            sb.setCharAt(n, 'v');
            break;
        case 'p':
            sb.setCharAt(n, 'a');
            break;
        case 'r':
            sb.setCharAt(n, 'l');
            break;
        case 'v':
            sb.setCharAt(n, 'd');
            break;

        }
        return sb.toString();
    }



    public static final String [] NAMING_METHOD = {
        "NONE"
        ,"STANDARD"
        ,"MANUAL"
        ,"NEWCANONICAL"
    };
    
    public static final int
	    EARLY = 50
	   ,MID = 450
	   ,DEAD = -1
	   ,DEADZERO = 0
	   ,DIVISOR = 8
	   ,MINCUTOFF = 5
   ;

    public static final int
    	  STANDARD = 1
    	 ,MANUAL = 2
         ,NEWCANONICAL = 3
	 ;


    private static final String
		 NUC = "Nuc"
	;

    private static void println(String s) {System.out.println(s);}
    private static void print(String s) {System.out.print(s);}
    private static final String CS = ", ", C = ",", TAB = "\t";
    private static final DecimalFormat DF0 = new DecimalFormat("####");
    private static final DecimalFormat DF1 = new DecimalFormat("####.#");
    private static final DecimalFormat DF4 = new DecimalFormat("####.####");
    private static String fmt4(double d) {return DF4.format(d);}
    private static String fmt1(double d) {return DF1.format(d);}
    private static String fmt0(double d) {return DF0.format(d);}

}
