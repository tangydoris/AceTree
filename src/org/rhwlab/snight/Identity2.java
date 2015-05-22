package org.rhwlab.snight;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Hashtable;
import java.util.Vector;

import org.rhwlab.acetree.AceTree;
import org.rhwlab.utils.C;
import org.rhwlab.utils.Line;


public class Identity2 {

    public static Identity      iIdentity;
    private static NucleiMgr    iNucleiMgr;
    private AceTree             iAceTree;
    //private Log                 iDLog;
    private boolean             iDebug;
    private int                 iNamingMethod;
    private static Vector       nuclei_record;
    private static int          iEndingIndex;
    private int                 iStartingIndex;
    private char                tag1;
    private Hashtable           iCanonicalSimpleNamesHash;
    private Hashtable           iRelativePositionHash;
    private int		            iDivisor;
    private int		            iMinCutoff;
    private int			        iNucCount;
    private Hashtable           iNamingHash;
    private char                iTag;
    //private char	            iNamingAxis;
    Line                        iLine;
    private Parameters          iParameters;
    private Movie               iMovie;
    private boolean             iOverrideMissingRule;
    private boolean             iOverrideNoRuleFor;
    private double				iTheta;
    private double				iXCenter;
    private double				iYCenter;

    public Identity2(NucleiMgr nucleiMgr) {
    	System.out.println("Identity2 construtor called.");
        iNucleiMgr = nucleiMgr;
        iParameters = iNucleiMgr.getParameters();
        iMovie = iNucleiMgr.getMovie();
        nuclei_record = iNucleiMgr.getNucleiRecord();
        iEndingIndex = iNucleiMgr.getEndingIndex();
        iNamingMethod = iNucleiMgr.getConfig().iNamingMethod;
        iRelativePositionHash = new Hashtable();
        iDivisor = DIVISOR;
        iMinCutoff = MINCUTOFF;
        iOverrideMissingRule = true;
        iOverrideNoRuleFor = true;
        iTheta = getAngle();

    }

    //20090406 introduce embryo tilt
    private double getAngle() {
    	double ang = 0;
    	Config config = iNucleiMgr.getConfig();
    	Hashtable configHash = config.iConfigHash;
    	String sang = (String)configHash.get("angle");
    	if (sang.length() > 0) {
    		ang = Double.parseDouble(sang);
    	}
    	iXCenter = XCENTER;
    	iYCenter = YCENTER;
    	String x = (String)configHash.get("x");
    	String y = (String)configHash.get("y");
    	if (x.length() > 0) iXCenter = Double.parseDouble(x);
    	if (y.length() > 0) iYCenter = Double.parseDouble(y);
    	return Math.toRadians(ang);
    }

    public void useCanonicalRules(int [] start, int [] lineage_ct_p) {
        iNamingHash = getNamingHashtable();
        int rotate_axis = 1;
        int nuc_ct = 0;
        int i;
        Vector nuclei = null;
        int breakout = 0;

        iParameters.ap = iParameters.apInit;
        iParameters.dv = iParameters.dvInit;
        iParameters.lr = iParameters.lrInit;
        println("useCanonicalRules: " + iParameters.ap + CS + iParameters.dv + CS + iParameters.lr);

        int iEndingIndex = iNucleiMgr.getEndingIndex();
        int k = iNucleiMgr.getNucleiRecord().size();
        int m = Math.min(k, iEndingIndex);
        System.out.println("useCanonicalRules starting at: " + start[0] + CS + iEndingIndex);
        for (i = start[0]; i < m; i++) {
            if (breakout > 0) {
                System.out.println("Identity.useCanonicalRules exiting, breakout=" + breakout);
                System.exit(0);
                break;
            }
            nuclei = (Vector)nuclei_record.elementAt(i - 1);
            nuc_ct = nuclei.size();

            //println("useCanonicalRules:  + rotate_axis: " + Identity.EARLY + CS + nuc_ct);
            if (rotate_axis > 0 && nuc_ct > Identity.EARLY) {
                //rotateAxis();
                //println("useCanonicalRules: after rotateAxis, " + iParameters.ap + CS + iParameters.dv + CS + iParameters.lr);
                //iIdentity.rotateAxis();
                rotate_axis = 0;
            }
            Nucleus parent = null;
            Vector nextNuclei = (Vector)nuclei_record.elementAt(i);
            parent = null;
            for (int j = 0; j < nuc_ct; j++) {
                parent = (Nucleus)nuclei.elementAt(j);
                if (parent.status == Nucleus.NILLI) continue;
                String pname = parent.identity;
                if (pname == null || pname.length() == 0) {
                    pname = NUC + iNucCount++;
                    parent.identity = pname;
                }
                boolean good = (parent.successor1 > 0 && parent.successor2 > 0);
                if (!good) {
                    // not dividing so just extend the name
                    if (parent.successor1 > 0) {
                        Nucleus n = (Nucleus)nextNuclei.elementAt(parent.successor1 - 1);
                        if (n.assignedID.length() <= 0) n.identity = pname;
                    }
                    continue;
                }
                // this canonical parent is dividing
                // use the evolving algorithm to try to name it
                // if there is a rule in place then the name will be canonical
                // and we can continue
                // if there is no rule we will try to name based on the
                // canonical axis. If this passes the noise test we can continue
                // if not we must output the ruleData for this cell
                // and flag this cell as needing rule data from the other series
                // then we exit and allow the next series to be processed
                Nucleus dau1 = (Nucleus)nextNuclei.elementAt(parent.successor1 - 1);
                //println("useCanonicalRules: dau1: " + dau1);
                Nucleus dau2 = (Nucleus)nextNuclei.elementAt(parent.successor2 - 1);
                //println("useCanonicalRules: dau2: " + dau2);
                boolean test = newCanonicalSisterID(parent, dau1, dau2, nuc_ct, i);
                //println("useCanonicalRules:2 dau1: " + dau1);
                //println("useCanonicalRules:2 dau2: " + dau2);
                usePreassignedID(dau1, dau2);
            }
        }
        System.out.println("useCanonicalRules exiting");

    }

    public static Hashtable getNamingHashtable() {
        Hashtable namingHash = new Hashtable();
        URL url = AceTree.class.getResource("/org/rhwlab/snight/namesHash.txt");
        InputStream istream = null;
        try {
            istream = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(istream));
            String s;
            while (br.ready()) {
                s = br.readLine();
                if (s.length() == 0) continue;
                String [] sa = s.split(",");
                namingHash.put(sa[0], sa[1]);
            }
            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return namingHash;

    }

    private boolean newCanonicalSisterID(Nucleus parent, Nucleus dau1, Nucleus dau2,
            int cellCount, int index) {
        String pname = parent.identity;
        if (pname.equals("Cppp")) {
            int uuu = 8; // debug spot only
        }
        String prule = (String)iNamingHash.get(pname);
        if (prule == null) {
            if (iOverrideNoRuleFor) {
                prule = "a0xa";
            } else {
                System.out.println("PROCESSING CANNOT CONTINUE DUE TO NO RULE FOR PARENT");
                System.exit(2);
            }
        }
        // we first try the canonical axis
        // if it passes the noise test we are done
        // if not, we look for a rule
        // if there is no rule we have a naming failure
        // if the rule fails the noise test we have a naming failure
        // but if the rule passes the noise test we are done
        // a rule consists of a different axis and a mapping onto the canonical axis
        // so, if we have a function that can handle any axis we can
        // reuse it with the "different axis"
        boolean canonicalTry = false;
        boolean ruleTry = false;

        char caxis = prule.charAt(0);
        if (caxis != IGNORESULSTON) {
            canonicalTry = makeAxisDetermination(0, prule, cellCount, dau1, dau2);
        } else {
            canonicalTry = false; // force system thru the rule path
        }


        if (!canonicalTry) {
            //System.out.println("need a rule for: " + pname + " ;available rule: " + prule);
            if (pname.equals("Cpp")) {
                //println("newCanonicalSister, debug stop");
            }
            if (prule.length() < 3) {
                String x = "*** RULE MISSING *** " + pname + CS + prule;
                //System.out.println(x);
                if (iOverrideMissingRule) {
                    char u = prule.charAt(0);
                    char add = 'x';
                    switch(u) {
                        case 'a':
                            break;
                        case 'd':
                            add = 'y';
                            break;
                        default:
                            add = 'z';

                    }
                    prule += add;
                    prule += u;
                    x = "*** USING FORCED RULE *** " + pname + CS + prule;
                    //System.out.println(x);
                } else {
                    System.out.println("PROCESSING CANNOT PROCEED DUE TO MISSING RULE");
                    System.exit(1);
                }

            }
            ruleTry = makeAlternateDetermination(prule, cellCount, dau1, dau2);
            if (!ruleTry) {
                String x = "*** RULE FAILURE *** " + pname + CS + prule;
                //System.out.println(x);
            }

        }
        if (!canonicalTry && !ruleTry) {
        	//println("newCanonicalSisterID, " + canonicalTry + CS + ruleTry);
        	StringBuffer sb = new StringBuffer("RULEFAILURE");
        	sb.append(C + parent.identity);
        	sb.append(C + prule);
        	sb.append(C + dau1.x);
        	sb.append(C + dau2.x);
        	sb.append(C + dau1.y);
        	sb.append(C + dau2.y);
        	sb.append(C + dau1.z);
        	sb.append(C + dau2.z);
        	sb.append(C + cellCount);
        	println(sb.toString());
        	
        }
        nameDaughters(parent, dau1, dau2);
        return canonicalTry;
    }

    private void usePreassignedID(Nucleus dau1, Nucleus dau2) {
        //println("usePreassignedID: " + dau1.identity + CS + dau2.identity);
        //println("usePreassignedID:2 " + dau1.assignedID + CS + dau2.assignedID);
        if (dau1.assignedID.length() == 0 && dau2.assignedID.length()== 0) {
            return;
        }
        //println("usePreassignedID:3 " + dau1.assignedID + CS + dau2.assignedID);
        if (dau1.assignedID.length() > 0) dau1.identity = dau1.assignedID;
        if (dau2.assignedID.length() > 0) dau2.identity = dau2.assignedID;

        if (dau1.identity.equals(dau2.identity)) {
            String s = dau2.identity;
            s = s.substring(0, s.length() - 1);
            s = s + "X";
            dau2.identity = s;
        }
    }


    public void rotateAxis() {
        iParameters.lr *= iParameters.ap*(-1);
        iParameters.dv *= iParameters.ap;
    }

    // function sets iTag for the first daughter
    // and returns true if the name passed the noise test
    private boolean makeAxisDetermination(int k, String rule, int cellCount, Nucleus dau1, Nucleus dau2) {
        char caxis = rule.charAt(k);
        // this is the standard implementation of Sulston's rule
        int divisor = (dau1.size + dau2.size)/Identity.DIVISOR;
        Loc dau1L = new Loc(dau1, iNucleiMgr);
        Loc dau2L = new Loc(dau2, iNucleiMgr);
        int value = 0;
        iTag = 'X';
        angleAdjustXY(dau1L);
        angleAdjustXY(dau2L);
        if (caxis == 'a') {
            value = (dau1L.x - dau2L.x)*100/divisor;
            value *= iParameters.ap; // Loc does not compensate for this (102705 series)
            if (value > 0) iTag = 'p';
            else iTag = 'a';
        }
        else if (caxis == 'd') {
            value = (dau1L.y - dau2L.y)*100/divisor;
            // assume this axis is not affected by ap reversal
            if (value > 0) iTag = 'v';
            else iTag = 'd';
        }
        // use the z axis
        else {
            value = (dau1L.z - dau2L.z)*100/divisor;
            value *= iParameters.ap; //added based on 102705 series
            if (value < 0) iTag = 'l';
            else iTag = 'r';
        }
        return (Math.abs(value) > 100);
    }


    private boolean makeAlternateDetermination(String rule, int cellCount, Nucleus dau1, Nucleus dau2) {
        char caxis = rule.charAt(2);
        char ruleChar = rule.charAt(3);
        int divisor = (dau1.size + dau2.size)/Identity.DIVISOR;
        Loc dau1L = new Loc(dau1, iNucleiMgr);
        Loc dau2L = new Loc(dau2, iNucleiMgr);
        angleAdjustXY(dau1L);
        angleAdjustXY(dau2L);
        int value = 0;
        iTag = 'X';
        if (caxis == 'x') {
            value = (dau1L.x - dau2L.x)*100/divisor;
        }
        else if (caxis == 'y') {
            value = (dau1L.y - dau2L.y)*100/divisor;
        }
        // use the z axis
        else value = (dau1L.z - dau2L.z)*100/divisor;
        if (value < 0) iTag = ruleChar;
        else iTag = complement(ruleChar);
        return (Math.abs(value) > 100);
    }


    private void nameDaughters(Nucleus parent, Nucleus dau1, Nucleus dau2) {
        tag1 = iTag;
        if (specialCases(parent, dau1, dau2)) return;
        String oldDau1ID = dau1.identity;
        String oldDau2ID = dau2.identity;
        dau1.identity = parent.identity + iTag;
        dau2.identity = replaceLastChar(dau1.identity);
        if (!dau1.identity.equals(oldDau1ID) || !dau2.identity.equals(oldDau2ID)) {
        	println("NAMECHANGED, " + oldDau1ID + CS + dau1.identity + CS + oldDau2ID + CS + dau2.identity);
        }
    }

    private void angleAdjustXY(Loc u) {
    	double x1 = u.x;
    	double y1 = u.y;
    	double x2 = x1 - iXCenter;
    	double y2 = y1 - iYCenter;
		double r = Math.sqrt(x2*x2 + y2*y2);
		double a = Math.atan2(y2, x2);
		double aa = a + iTheta;
		double x3 = Math.round(r * Math.cos(aa));
		double y3 = Math.round(r * Math.sin(aa));
		u.x = (int)Math.round(x3 + iXCenter);
		u.y = (int)Math.round(y3 + iYCenter);
    }

    private char complement(char x) {
        switch(x) {
            case 'a':
                return 'p';
            case 'p':
                return 'a';
            case 'd':
                return 'v';
            case 'v':
                return 'd';
            case 'l':
                return 'r';
            case 'r':
                return 'l';

        }
        return 'g';
    }


    private boolean specialCases(Nucleus parent, Nucleus nuc1, Nucleus nuc2) {
        //System.out.println("specialCases1: " + parent);
        //System.out.println("specialCases2: " + nuc1);
        //System.out.println("specialCases3: " + nuc2);
        boolean rtn = true;
        if (parent.identity.equals("EMS")) {
            switch(tag1) {
            case A:
            case D:
            case L:
                nuc1.identity = "MS";
                nuc2.identity = "E";
                break;
            default:
                nuc1.identity = "E";
                nuc2.identity = "MS";
            }
        } else if (parent.identity.equals("P1")) {
            switch(tag1) {
            case A:
            case D:
            case L:
                nuc1.identity = "EMS";
                nuc2.identity = "P2";
                break;
            default:
                nuc1.identity = "P2";
                nuc2.identity = "EMS";
            }
        } else if (parent.identity.equals("P2")) {
            switch(tag1) {
            case A:
            case D:
            case L:
                nuc1.identity = "C";
                nuc2.identity = "P3";
                break;
            default:
                nuc1.identity = "P3";
                nuc2.identity = "C";
            }
        } else if (parent.identity.equals("P4")) {
            switch(tag1) {
            case A:
            case D:
            case L:
                nuc1.identity = "Z3";
                nuc2.identity = "Z2";
                break;
            default:
                nuc1.identity = "Z2";
                nuc2.identity = "Z3";
            }
        } else if (parent.identity.equals("P3")) {
            char tag2 = ' ';
            int difi = (nuc1.y - nuc2.y) * iParameters.dv;
            if (difi < -nuc1.size/2) {
                tag1  = D; tag2 = V;
            } else {
                tag1 = V; tag2 = D;
            }
            if (tag1 == D) {
                nuc1.identity = "D"; nuc2.identity = "P4";
            } else if (tag1 == V) {
                nuc1.identity = "P4"; nuc2.identity = "D";
            }
        } else if (parent.identity.equals("P0")) {
            switch(tag1) {
            case A:
            case D:
            case L:
                nuc1.identity = "AB";
                nuc2.identity = "P1";
                break;
            default:
                nuc1.identity = "P1";
                nuc2.identity = "AB";
            }
        } else rtn = false;
        return rtn;
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


    public void setNamingMethod(int method) {
        System.out.println("Identity.setNamingMethod called with: " + method + CS + NAMING_METHOD[method]);
        iNamingMethod = method;
    }

    public int getNamingMethod() {
        return iNamingMethod;
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

    // called from class Options
    public int getDivisor() {
        return iDivisor;
    }

    // called from class Options
    public int getMinCutoff() {
        return iMinCutoff;
    }


    private static final String
		 NUC = "Nuc"
	    ,POLAR = "polar"
	;

public static final int
	 DIVISOR = 8
	,MINCUTOFF = 5
	,XCENTER = 350
	,YCENTER = 250
    ,EARLY = 50
    ,MID = 450
    ,DEAD = -1
    ,DEADZERO = 0
    ;

private static final char
	 IGNORESULSTON = 'i'
	,A = 'a'
	,D = 'd'
	,L = 'l'
	,V = 'v'
	,E = 'e'
	,W = 'w'
	,N = 'n'
	,S = 's'
	,B = 'b' //'d' based on identity study 20050614
	,T = 't' //'v'
	,P = 'p'
	,R = 'r'
	,X = 'X'    // a dummy tag used in newBornID

	;

public static final String [] NAMING_METHOD = {
    "NONE"
    ,"STANDARD"
    ,"MANUAL"
    ,"NEWCANONICAL"
};

public static final int
	  STANDARD = 1
	 ,MANUAL = 2
     ,NEWCANONICAL = 3
	 ;



    /////////// code beyond useCanonicalRules

    public void identityAssignment() {
        System.out.println("\n\nidentityAssignment: " + iParameters.axis
                + CS + iParameters.ap
                + CS + iParameters.dv
                + CS + iParameters.lr);
        iMovie = iNucleiMgr.getMovie();
        iNucCount = 1;
        System.out.println("NamingMethod: " + NAMING_METHOD[iNamingMethod]);
        if (iNamingMethod == MANUAL) return;
        int [] lineage_ct_p = new int[1];
        lineage_ct_p[0] = 1;
        int lin_ct = lineage_ct_p[0];
        int start[] = new int[1];
        start[0] = 1;
        int rotate_axis = 1;
        int nuc_ct = 0;
        Vector nuclei = null;
        Vector nuclei_prev = null;
        iRelativePositionHash.clear();
        iStartingIndex = iNucleiMgr.getConfig().iStartingIndex;
        //clearAllNames();
        System.out.println("identityAssignment iStartingIndex: " + iStartingIndex);
        iParameters.axis = 0;
        start[0] = iStartingIndex;
        tryForAxis();

        // if NEWCANONICAL and a an axis was specified we leave this function here
        // we do not run initialID so the user can specify the initial cell names
        if (iParameters.axis == 1 && iNamingMethod == NEWCANONICAL) {
            useCanonicalRules(start, lineage_ct_p);
            return;

        }
        // if no axis was specified then the initialID code will be run
        // you could still use NEWCANONICAL here if iStartingIndex is greater than one
        // but this should be phased out
        if (iStartingIndex == 1) {
            //int mm = initialID(start, lineage_ct_p);
            InitialID initID = new InitialID(iNucleiMgr, iParameters);
            int mm = initID.initialID(start, lineage_ct_p);
            iNucCount = initID.getNucCount();
            
            System.out.println("initialID returned: " + mm);
            if (mm > 0) {
                System.out.println("detected backtrace failure, lineage from start");
                start[0] = 0; //start from scratch on failure of initialID
            }
            lin_ct = lineage_ct_p[0];
            System.out.println("identityAssignment starting at: " + start[0]);
            if (iNamingMethod == NEWCANONICAL && start[0] > 0) {
                useCanonicalRules(start, lineage_ct_p);
                return;
            }
        } else {
            // here, since initialID was not run,
            // we make sure that all cells at index 0 have Nuc names
            // so naming can proceed from time point 2
            nuclei = (Vector)nuclei_record.elementAt(iStartingIndex - 1);
            //
            for (int j=0; j < nuclei.size(); j++) {
                Nucleus n = (Nucleus)nuclei.elementAt(j);
                if (n.status == Nucleus.NILLI) continue;
                if (n.identity.length() > 0) {
                    if (n.identity.indexOf(NUC) == 0) {
                        int k = getNumber(n.identity.substring(3));
                        iNucCount = Math.max(k, iNucCount);
                    }
                }
            }
            iNucCount++;
            for (int j=0; j < nuclei.size(); j++) {
                Nucleus n = (Nucleus)nuclei.elementAt(j);
                if (n.status == Nucleus.NILLI) continue;
                if (n.identity.length() > 0) continue;
                n.identity = NUC + iNucCount++;
                System.out.println("founder: " + n.identity);

            }

            tryForAxis();
        }
        System.out.println("identityAssignment, iEndingIndex=" + iEndingIndex);
        // below here, we are naming without benefit of NEWCANONICAL
        for (int i = start[0]; i < iEndingIndex; i++) {
            iDebug = false;
            nuclei = (Vector)nuclei_record.elementAt(i);
            nuc_ct = nuclei.size();
            if (i > 0) nuclei_prev = (Vector)nuclei_record.elementAt(i - 1);
            if (rotate_axis > 0 && nuc_ct > EARLY) {
                rotateAxis();
                rotate_axis = 0;
            }
            Nucleus nucleij = null;
            for (int j = 0; j < nuc_ct; j++) {
                nucleij = (Nucleus)nuclei.elementAt(j);
                if (nucleij.status == DEAD || nucleij.status == DEADZERO) continue;
                String s = nucleij.identity;
                if (s == null) {
                    System.out.println("Flaw in nuclei files at indices i, j: " + i + CS + j);
                    System.out.println("Identity cannot continue -- shutting down");
                    System.exit(11);
                }
                if (s.length() > 0) continue;
                nucleij.identity = ""; // clear it so this program is in control
                if (nucleij.identity.length() == 0) {
                    Nucleus pred = null;
                    if (nucleij.predecessor != Nucleus.NILLI) {
                        pred = (Nucleus)nuclei_prev.elementAt(nucleij.predecessor - 1);
                    }
                    if (nucleij.predecessor == Nucleus.NILLI || pred.status == Nucleus.NILLI) {
                        // assign it to the root if this is the first time it is seen
                        nucleij.identity = NUC + iNucCount++ + s;
                    } else {
                        if (pred.successor2  == Nucleus.NILLI) {
                            // copy name if no cell division occurred
                            nucleij.identity = pred.identity;
                        }
                        else {
                            Nucleus dau1 = (Nucleus)nuclei.elementAt(pred.successor1 - 1);
                            Nucleus dau2 = (Nucleus)nuclei.elementAt(pred.successor2 - 1);
                            int r = 1;
                            if (r != 0) {
                            	// try a new simplified approach to nuc naming
                                sisterID(dau1, dau2, nuc_ct);
                                dau1.identity = pred.identity + dau1.id_tag;
                                dau2.identity = pred.identity + dau2.id_tag;
                                /*
                            	if (iNamingMethod != STANDARD) {
                                    sisterID(dau1, dau2, nuc_ct);
                                    dau1.identity = pred.identity + dau1.id_tag;
                                    dau2.identity = pred.identity + dau2.id_tag;
                                } else {
                                    newSisterID(pred, dau1, dau2, nuc_ct);
                                }
                                */
                            }

                        }
                    }
                }
            }
        }
        lineage_ct_p[0] = lin_ct;
        println("identity_assignment, ending normally");


    }

    private void clearAllNames() {
        int k = iNucleiMgr.getNucleiRecord().size();
        int endingIndex = iEndingIndex;
        //for (int i = 0; i < iEndingIndex; i++) {
        for (int i = iStartingIndex - 1; i < iEndingIndex; i++) {
            //println("clearAllNames: " + i + CS + iEndingIndex);
            if (!(i < k)) break;
            //for (int i = 0; i < iEndingIndex - iNucleiMgr.getConfig().iStartingIndex; i++) {
            if (iStartingIndex > 1 && i == iStartingIndex - 1) continue;
            clearNames((Vector)iNucleiMgr.getNucleiRecord().elementAt(i));

        }
    }


    private void tryForAxis() {
        String axis = iNucleiMgr.getConfig().iAxisGiven;
        println("tryForAxis: " + axis);
        if (axis == null || axis.length() < 3) {
            iParameters.axis = 0;
            return;
        }
        iParameters.ap = 1;
        iParameters.dv = 1;
        iParameters.lr = 1;
        if (axis.charAt(0) == 'p') iParameters.ap = -1;
        if (axis.charAt(1) == 'v') iParameters.dv = -1;
        if (axis.charAt(2) == 'r') iParameters.lr = -1;
        iParameters.axis = 1;
        iParameters.apInit = iParameters.ap;
        iParameters.dvInit = iParameters.dv;
        iParameters.lrInit = iParameters.lr;
        println("tryForAxis:2 " + iParameters.ap + CS + iParameters.dv + CS + iParameters.lr);
    }


    private void clearNames(Vector nuclei) {
        //println("cleaarNames: " + nuclei.size());
        Nucleus n;
        for (int i=0; i < nuclei.size(); i++) {
            n = (Nucleus)nuclei.elementAt(i);
            String id = n.identity;
            if (n.assignedID.length() > 0) continue;
            n.identity = "";
        }

    }

    // extract the numeric portion of a Nucnnn name
    private int getNumber(String sin) {
        //System.out.println("getNumber: " + sin);
        String s = "";
        for (int i=0; i < sin.length(); i++) {
            char c = sin.charAt(i);
            if (Character.isDigit(c)) s += c;

        }
        int k = Integer.parseInt(s);
        //System.out.println("getNumber: " + s + C.CS + k);
        return k;
    }

    ////// sisterID and newSisterID are from the fall-thru naming path
    // sisterID is used if naming method is not STANDARD
    // newSisterID is used if naming method is STANDARD
    // I reduced it to a trivial piece of code 20090407

    private void sisterID(Nucleus nuc1, Nucleus nuc2, int nuc_ct) {
    	nuc1.id_tag = A;
        nuc2.id_tag = P;
        return;
    }



    private static void println(String s) {System.out.println(s);}
    private static final String CS = ", ", C=",";

}
