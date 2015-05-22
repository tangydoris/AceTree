/*
 * Created on Jun 23, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.rhwlab.snight;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.rhwlab.acetree.AceTree;
import org.rhwlab.acetree.NucUtils;
//import org.rhwlab.analyze.Analysis3.Line;
import org.rhwlab.tree.CanonicalTree;
import org.rhwlab.utils.C;
import org.rhwlab.utils.Line;
import org.rhwlab.utils.Log;

/**
 * Singleton object to set cell names in nuclei files
 * Based originally on the StarryNite cell_identity.h code
 * @author biowolp
 *
 */
public class DUMPIdentity {

    public static DUMPIdentity      iIdentity;
    private static NucleiMgr    iNucleiMgr;
    private AceTree             iAceTree;
    //private Log                 iDLog;
    private boolean             iDebug;
    private int                 iNamingMethod;
    private static Vector       nuclei_record;
    private static int          iEndingIndex;
    private int                 iStartingIndex;
    private char                tag1;
    //private Hashtable           iCanonicalSimpleNamesHash;
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

    public DUMPIdentity(NucleiMgr nucleiMgr) {
    	System.out.println("DUMPIdentity construtor called.");
        iNucleiMgr = nucleiMgr;
        iParameters = iNucleiMgr.getParameters();
        iMovie = iNucleiMgr.getMovie();
        nuclei_record = iNucleiMgr.getNucleiRecord();
        iEndingIndex = iNucleiMgr.getEndingIndex();
        //iCanonicalSimpleNamesHash = CanonicalTree.getCanonicalTree().getCanonicalSimpleNamesHash();
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


    public Object clone()
    throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException();
    // that'll teach 'em
  }

    public int getNamingMethod() {
        return iNamingMethod;
    }

    public void setNamingMethod(int method) {
        System.out.println("DUMPIdentity.setNamingMethod called with: " + method + C.CS + NAMING_METHOD[method]);
        iNamingMethod = method;
    }


    public Hashtable getRelativePositionHash() {
        return iRelativePositionHash;
    }


    @SuppressWarnings("unused")
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
                System.out.println("DUMPIdentity.useCanonicalRules exiting, breakout=" + breakout);
                System.exit(0);
                break;
            }
            nuclei = (Vector)nuclei_record.elementAt(i - 1);
            nuc_ct = nuclei.size();

            //println("useCanonicalRules:  + rotate_axis: " + DUMPIdentity.EARLY + CS + nuc_ct);
            if (rotate_axis > 0 && nuc_ct > DUMPIdentity.EARLY) {
                rotateAxis();
                println("useCanonicalRules: after rotateAxis, " + iParameters.ap + CS + iParameters.dv + CS + iParameters.lr);
                //iDUMPIdentity.rotateAxis();
                rotate_axis = 0;
            }
            Nucleus parent = null;
            Vector nextNuclei = (Vector)nuclei_record.elementAt(i);
            parent = null;
            for (int j = 0; j < nuc_ct; j++) {
                parent = (Nucleus)nuclei.elementAt(j);
                if (parent.status == Nucleus.NILLI) 
                	continue;
                String pname = parent.identity;
                
                // If no name assigned to parent Nucleus use a "NUC..." name
                if (pname == null || pname.length() == 0) {
                    pname = NUC + iNucCount++;
                    parent.identity = pname;
                }
                
                boolean good = (parent.successor1 > 0 && parent.successor2 > 0);
                if (!good) {
                    // not dividing so just extend the name
                    if (parent.successor1 > 0) {
                        Nucleus n = (Nucleus)nextNuclei.elementAt(parent.successor1 - 1);
                        if (n.assignedID.length() <= 0) 
                        	n.identity = pname;
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
                if (!parentRelevant(pname)) {
                    System.out.println("not relevantParent: " + pname);
                    continue;
                }
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

    // Forced name used here
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


    private boolean parentRelevant(String pname) {
        boolean rtn = true;
//        rtn = rtn && !pname.equals("AB");
//        rtn = rtn && !pname.equals("ABa");
//        rtn = rtn && !pname.equals("ABp");
//        rtn = rtn && !pname.equals("EMS");
//        rtn = rtn && !pname.equals("P2");
//        rtn = rtn && !pname.equals("P3");
//        rtn = rtn && !pname.equals("P1");
//        rtn = rtn && !pname.equals("P4");
//        rtn = rtn && !pname.equals("Z2");
//        rtn = rtn && !pname.equals("Z3");
        return rtn;
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

    @SuppressWarnings("unused")
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
            System.out.println("need a rule for: " + pname + " ;available rule: " + prule);
            if (pname.equals("Cpp")) {
                println("newCanonicalSister, debug stop");
            }
            if (prule.length() < 3) {
                String x = "*** RULE MISSING *** " + pname + C.CS + prule;
                System.out.println(x);
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
                    x = "*** USING FORCED RULE *** " + pname + C.CS + prule;
                    System.out.println(x);
                } else {
                    System.out.println("PROCESSING CANNOT PROCEED DUE TO MISSING RULE");
                    System.exit(1);
                }

            }
            ruleTry = makeAlternateDetermination(prule, cellCount, dau1, dau2);
            if (!ruleTry) {
                String x = "*** RULE FAILURE *** " + pname + C.CS + prule;
                System.out.println(x);
            }

        }
        nameDaughters(parent, dau1, dau2);
        return canonicalTry;
    }

    private void nameDaughters(Nucleus parent, Nucleus dau1, Nucleus dau2) {
        tag1 = iTag;
        if (specialCases(parent, dau1, dau2)) return;
        dau1.identity = parent.identity + iTag;
        dau2.identity = replaceLastChar(dau1.identity);
    }


    private static final int
    	 XCENTER = 350
    	,YCENTER = 250
    	;
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

    // function sets iTag for the first daughter
    // and returns true if the name passed the noise test
    private boolean makeAxisDetermination(int k, String rule, int cellCount, Nucleus dau1, Nucleus dau2) {
        char caxis = rule.charAt(k);
        // this is the standard implementation of Sulston's rule
        int divisor = (dau1.size + dau2.size)/DUMPIdentity.DIVISOR;
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
        int divisor = (dau1.size + dau2.size)/DUMPIdentity.DIVISOR;
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

    public void identityAssignment() {
        System.out.println("\n\nidentityAssignment: " + iParameters.axis
                + C.CS + iParameters.ap
                + C.CS + iParameters.dv
                + C.CS + iParameters.lr);
        iMovie = iNucleiMgr.getMovie();
        iNucCount = 1;
        System.out.println("NamingMethod: " + NAMING_METHOD[iNamingMethod]);
        if (iNamingMethod == MANUAL) 
        	return;
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
        clearAllNames();
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
            int mm = initialID(start, lineage_ct_p);
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
                    System.out.println("Flaw in nuclei files at indices i, j: " + i + C.CS + j);
                    System.out.println("DUMPIdentity cannot continue -- shutting down");
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
                                if (iNamingMethod != STANDARD) {
                                    sisterID(dau1, dau2, nuc_ct);
                                    dau1.identity = pred.identity + dau1.id_tag;
                                    dau2.identity = pred.identity + dau2.id_tag;
                                } else {
                                    newSisterID(pred, dau1, dau2, nuc_ct);
                                }
                            }

                        }
                    }
                }
            }
        }
        lineage_ct_p[0] = lin_ct;
        println("identity_assignment, ending normally");

    }


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


    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
	public void newSisterID(Nucleus parent, Nucleus nuc1, Nucleus nuc2, int cellCount) {
        //System.out.println("newSisterID1: " + parent);
        //System.out.println("newSisterID2: " + nuc1);
        //System.out.println("newSisterID3: " + nuc2);
        boolean b = false;
        if (iParameters.axis == 0) {
            sisterNucs(parent, nuc1, nuc2);
            System.out.println("newSisterID: " + nuc1.identity + C.CS + nuc2.identity);
            return;
        }
        int k = relativePosition(nuc1, nuc2);
        iRelativePositionHash.put(nuc1.identity, new Integer(k));
        if (cellCount < EARLY) tag1 = earlyFirstCellTag(k);
        else tag1 = midFirstCellTag(k);

        if (specialCases(parent, nuc1, nuc2)) {
            return;
        }
        String newName = (new StringBuffer(parent.identity).append(tag1)).toString();
        nuc1.identity = newName;
        nuc2.identity = makeSisterName(newName);
    }

    /**
     * return -1 if x is controlling and cd1 is left of cd2
     * return +1 if x is controlling and cd1 is right of cd2
     * return -2 if y is controlling and cd1 is above cd2
     * return +2 if y is controlling and cd1 is below cd2
     * return -3 if z is controlling and cd1 is in front of cd2
     * return +3 if z is controlling and cd1 is behind cd2
     * decide on control using the existing StarryNite rules
     *
     */
    private int relativePosition(Nucleus cd1, Nucleus cd2) {
        int cutoff = (cd1.size + cd2.size)/iDivisor;
        cutoff = Math.max(cutoff, iMinCutoff);
        int xdiff = cd1.x - cd2.x;
        int ydiff = cd1.y - cd2.y;
        int zdiff = (int)((cd1.z - cd2.z) * iNucleiMgr.getZPixRes());
        if (Math.abs(xdiff) > cutoff) {
            if (xdiff < 0) return -1;
            else return 1;
        }
        else if (Math.abs(ydiff) > cutoff) {
            if (ydiff < 0) return -2;
            else return 2;
        }
        else if (Math.abs(zdiff) > cutoff) {
            if (zdiff < 0) return -3;
            else return 3;
        } else {
            int maxThing = 1;
            int maxValue = xdiff;
            int testValue = ydiff;
            if (Math.abs(testValue) > Math.abs(maxValue)) {
                maxValue = testValue;
                maxThing = 2;
            }
            testValue = zdiff;
            if (Math.abs(testValue) > Math.abs(maxValue)) {
                maxValue = testValue;
                maxThing = 3;
            }
            if (maxValue < 0) return -maxThing;
            else return maxThing;
        }
    }

    private char earlyFirstCellTag(int k) {
        int parameterslr = iParameters.lr;
        int parametersdv = iParameters.dv;

        int ka = Math.abs(k);
        int m = 1;
        switch(ka) {
        case 1:
            m = k * iParameters.ap;
            if (m < 0) return A;
            else return P;
        case 2:
            m = k * parametersdv;
            if (m < 0) return D;
            else return V;
        default:
            m = k * parameterslr;
            if (m < 0) return L;
            else return R;
        }
    }

    private char midFirstCellTag(int k) {
        //int parameterslr = -Parameters.lr * Parameters.ap;
        //int parametersdv = Parameters.dv * Parameters.ap;
        int parameterslr = iParameters.lr;
        int parametersdv = iParameters.dv;

        int ka = Math.abs(k);
        int m = 1;
        switch(ka) {
        case 1:
            m = k * iParameters.ap;
            if (m < 0) return A;
            else return P;
        case 2:
            m = k * parameterslr;
            if (m < 0) return L;
            //if (m > 0) return L;
            else return R;
        default:
            m = k * parametersdv;
            if (m < 0) return D;
            else return V;
        }
    }

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

    private void sisterNucs(Nucleus parent, Nucleus nuc1, Nucleus nuc2) {
        int xdiff = nuc1.x - nuc2.x;
        int ydiff = (nuc1.y - nuc2.y);  // /Movie.framewidth;
        int cutoff = (nuc1.size + nuc2.size)/iDivisor;
        cutoff = Math.max(cutoff, iMinCutoff);
        char tag1 = ' ';
        char tag2 = ' ';
        if (xdiff < 0 - cutoff) {
            tag1 = W;
            tag2 = E;
        }
        else if (xdiff > cutoff) {
            tag1 = E;
            tag2 = W;
        }
        else if (ydiff < 0 - cutoff) {
            tag1 = N;
            tag2 = S;
        }
        else if (ydiff > cutoff) {
            tag1 = S;
            tag2 = N;
        }
        else if (nuc1.z < nuc2.z) {
            tag1 = T;
            tag2 = B;
        }
        else {
            tag1 = B;
            tag2 = T;
        }
        nuc1.identity = parent.identity + tag1;
        nuc2.identity = parent.identity + tag2;

    }


    // only "support" functions below here

    @SuppressWarnings("unused")
	private void earlyAxis(Nucleus nuc1, Nucleus nuc2) {
        char left = T, right = B, dorsal = D, ventral = V;
        char tag1, tag2;
        if (iParameters.lr == 1) {left = T; right = B;}
        else {left = B; right = T;}
        if (iParameters.dv == 1) {dorsal = V; ventral = D;}
        else {dorsal = D; ventral = V;}

        char tag = nuc1.id_tag;
        if (tag == left) {tag1 = L; tag2 = R;}
        else if (tag == right) {tag1 = R; tag2 = L;}
        else if (tag == dorsal) {tag1 = D; tag2 = V;}
        else {tag1 = V; tag2 = D;}

        nuc1.id_tag = tag1;
        nuc2.id_tag = tag2;
    }

    @SuppressWarnings("unused")
	private void midAxis(Nucleus nuc1, Nucleus nuc2) {
        char left = T, right = B, dorsal = D, ventral = V;
        char tag1, tag2;
        if (iParameters.lr == 1) {left = V; right = D;}
        else {left = D; right = V;}
        if (iParameters.dv == 1) {dorsal = T; ventral = B;}
        else {dorsal = B; ventral = T;}

        char tag = nuc1.id_tag;
        if (tag == left) {tag1 = L; tag2 = R;}
        else if (tag == right) {tag1 = R; tag2 = L;}
        else if (tag == dorsal) {tag1 = D; tag2 = V;}
        else {tag1 = V; tag2 = D;}
        nuc1.id_tag = tag1;
        nuc2.id_tag = tag2;
    }


    private void sisterID(Nucleus nuc1, Nucleus nuc2, int nuc_ct) {
        int xdiff = nuc1.x - nuc2.x;
        int ydiff = (nuc1.y - nuc2.y);  // /Movie.framewidth;
        int cutoff = (nuc1.size + nuc2.size)/iDivisor;
        cutoff = Math.max(cutoff, iMinCutoff);
        char tag1 = ' ';
        char tag2 = ' ';
        Integer one = new Integer(1);
        Integer two = new Integer(2);
        Integer three = new Integer(3);
        if (iParameters.axis == 0) {
            if (xdiff < 0 - cutoff) {
                tag1 = W;
                tag2 = E;
                iRelativePositionHash.put(nuc1.identity, one);
            }
            else if (xdiff > cutoff) {
                tag1 = E;
                tag2 = W;
                iRelativePositionHash.put(nuc1.identity, one);
            }
            else if (ydiff < 0 - cutoff) {
                tag1 = N;
                tag2 = S;
                iRelativePositionHash.put(nuc1.identity, two);
            }
            else if (ydiff > cutoff) {
                tag1 = S;
                tag2 = N;
                iRelativePositionHash.put(nuc1.identity, two);
            }
            else if (nuc1.z < nuc2.z) {
                tag1 = T;
                tag2 = B;
                iRelativePositionHash.put(nuc1.identity, three);
            }
            else {
                tag1 = B;
                tag2 = T;
                iRelativePositionHash.put(nuc1.identity, three);
            }
            nuc1.id_tag = tag1;
            nuc2.id_tag = tag2;
            return;
        }
        if (xdiff*iParameters.ap < 0-cutoff) {
            tag1 = A;
            tag2 = P;
            iRelativePositionHash.put(nuc1.identity, one);
        }
        else if (xdiff*iParameters.ap > cutoff) {
            tag1 = P;
            tag2 = A;
            iRelativePositionHash.put(nuc1.identity, one);
        }

        else if (ydiff*iParameters.ap < 0-cutoff) {
            tag1 = V;
            tag2 = D;
            iRelativePositionHash.put(nuc1.identity, two);
        }
        else if (ydiff*iParameters.ap > cutoff) {
            tag1 = D;
            tag2 = V;
            iRelativePositionHash.put(nuc1.identity, two);
        }

        else if (nuc1.z < nuc2.z) {
            tag1 = T;
            tag2 = B;
            iRelativePositionHash.put(nuc1.identity, three);
        }
        else {
            tag1 = B;
            tag2 = T;
            iRelativePositionHash.put(nuc1.identity, three);
        }
        nuc1.id_tag = tag1;
        nuc2.id_tag = tag2;
        if (tag1 == A || tag1 == P) return;

        if (nuc_ct < EARLY) earlyAxis(nuc1, nuc2);
        else if (nuc_ct < MID) midAxis(nuc1, nuc2);

    }

    public void rotateAxis() {
        iParameters.lr *= iParameters.ap*(-1);
        iParameters.dv *= iParameters.ap;
    }

    public int newBornID(Nucleus mother, Nucleus dau1, Nucleus dau2) {
        println("newBornID: " + mother.identity + CS + dau1.identity + CS + dau2.identity);
        int rtn = 0; // return 0 if newBornID handles the assignment
                     //        -1 otherwise
        float diff;
        int difi;
        char tag1 = X;
        char tag2 = X;
        if (mother.identity.indexOf(POLAR) > -1) {
            System.out.println("Dividing polar body");
        } else if (mother.identity.equals("ABa")) {
            diff = (dau1.z - dau2.z) * iParameters.lr;
            if (diff < 0) {tag1 = L; tag2 = R;}
            else {tag1 = R; tag2 = L;}
            //else if (diff > 0) {tag1 = R; tag2 = L;}
            dau1.identity = mother.identity + tag1;
            dau2.identity = mother.identity + tag2;
            return 0;
        } else if (mother.identity.equals("ABp")) {
            diff = (dau1.z - dau2.z) * iParameters.lr;
            if (diff < 0) {tag1 = L; tag2 = R;}
            else {tag1 = R; tag2 = L;}
            //else if (diff > 0) {tag1 = R; tag2 = L;}
            dau1.identity = mother.identity + tag1;
            dau2.identity = mother.identity + tag2;
            return 0;
        } else if (mother.identity.equals("EMS")) {
            int k = relativePosition(dau1, dau2);
            dau1.id_tag = earlyFirstCellTag(k);
            if (dau1.id_tag == 'a') {
                dau1.identity = "MS"; dau2.identity = "E";
                return 0;
            }
            else if (dau1.id_tag == 'p') {
                dau1.identity = "E"; dau2.identity = "MS";
                return 0;
            }
        } else if (mother.identity.equals("P2")) {
            difi = (dau1.y - dau2.y) * iParameters.dv;
            if (difi < -dau1.size/2) {
                tag1 = D; tag2 = V;
            } else if (difi > dau1.size/2) {
            //} else if (difi > dau1.size/2) {
                tag1 = V; tag2 = D;
            }
            if (tag1 == D) {
                dau1.identity = "C"; dau2.identity = "P3";
                return 0;
            } else if (tag1 == V) {
                dau1.identity = "P3"; dau2.identity = "C";
                return 0;
            }
        } else if (mother.identity.equals("P3")) {
	    //System.out.println("newBornID handling P3");
            difi = (dau1.y - dau2.y) * iParameters.dv;
            if (difi < -dau1.size/2) {
                tag1  = D; tag2 = V;
            } else if (difi > dau1.size/2) {
                tag1 = V; tag2 = D;
            }
            if (tag1 == D) {
                dau1.identity = "D"; dau2.identity = "P4";
                return 0;
            } else if (tag1 == V) {
                dau1.identity = "P4"; dau2.identity = "D";
                return 0;
            }
            System.out.println("P3 NOT RESOLVED IN newBornID");

        } else if (mother.identity.equals("P4")) {
            int k = relativePosition(dau1, dau2);
            dau1.id_tag = midFirstCellTag(k);
            if (dau1.id_tag == A || dau1.id_tag == L) {
                dau1.identity = "Z3"; dau2.identity = "Z2";
                return 0;
            } else if (dau1.id_tag == P || dau1.id_tag == R) {
                dau1.identity = "Z2"; dau2.identity = "Z3";
                return 0;
            }
        }

        if (tag1 != X) {
            dau1.id_tag = tag1;
            dau2.id_tag = tag2;
            dau1.identity = mother.identity + tag1;
            dau2.identity = mother.identity + tag2;
        } else {
            rtn = -1;
        }
        return rtn;
    }

    // note modifications made on 20050804 to prevent a nucleus
    // named polar from being renamed Nuc-xxx
    // in three places
    private int initialID(int [] start_p, int [] lineage_ct_p) {
    	println("initialID called: " + start_p[0] + CS + lineage_ct_p[0]);
        int rtn = 0;
        int lin_ct = lineage_ct_p[0];
        int first_four = -1, last_four = -1, four_cells;
        Vector nuclei = (Vector)nuclei_record.elementAt(0);
        int nuc_ct = nuclei.size();
        int cell_ct = countCells(nuclei);
        if (cell_ct <= 6) {
            polarBodies();
            cell_ct = countCells(nuclei);
        }

        if (cell_ct > 4) {
            Nucleus nucleij = null;
            for (int j=0; j < nuc_ct; j++) {
                nucleij = (Nucleus)nuclei.elementAt(j);
                if (nucleij.status == -1) continue;
                //lin_ct++;
                if (nucleij.identity.indexOf(POLAR) > -1) continue; //modification 20050804
                nucleij.identity = NUC + iNucCount++;
            }
            iParameters.axis = 0;
            start_p[0] = 0;
            lineage_ct_p[0] = lin_ct;
            System.out.println("Starting with more than 4 cells.  No canonical ID assigned.");
            return 0;

        } else {
            iParameters.axis = 1;
            if (cell_ct == 4) first_four = 0;
            for (int i=0; i < iEndingIndex - 1; i++) {
                nuclei = (Vector)nuclei_record.elementAt(i);
                nuc_ct = nuclei.size();
                cell_ct = countCells(nuclei);
                if (cell_ct > 4) break;
                if (cell_ct == 4) {
                    if (first_four < 0) first_four = i;
                    last_four = i;
                }
            }

            if (first_four == -1) {
                nuclei = (Vector)nuclei_record.elementAt(0);
                nuc_ct = nuclei.size();
                Nucleus nucleij = null;
                for (int j=0; j < nuc_ct; j++) {
                    nucleij = (Nucleus)nuclei.elementAt(j);
                    if (nucleij.status == -1) continue;
                    if (nucleij.identity.indexOf(POLAR) > -1) continue;  //modification 20050804
                    lin_ct++;
                    nucleij.identity = NUC + iNucCount++;
                }
                iParameters.axis = 0;
                start_p[0] = 0;
                lineage_ct_p[0] = lin_ct;
                System.out.println("Movie too short to see four cells");
                //new Throwable().printStackTrace();
                return 0;
            }
        }

        four_cells = (first_four + last_four)/2;
        start_p[0] = four_cells + 1;

        rtn = fourCellID(four_cells, lineage_ct_p);
        if (rtn != 0) rtn = backAssignment(four_cells, lineage_ct_p);
        if (rtn == 0) {
            iParameters.axis = 0;
            return 1;
        }
        return 0;
    }


    @SuppressWarnings("unused")
	private int fourCellID(int four_cells, int [] lineage_ct_p) {
        Integer k;
        Vector nuclei = null, nuclei_next = null;
        Nucleus nucleii = null;
        int nuc_ct;
        int ind1, ind2;
        int i;
        int lin_ct = lineage_ct_p[0];

        nuclei = (Vector)nuclei_record.elementAt(four_cells);
        nuc_ct = nuclei.size();
        int r = alignDiamond(nuclei);
        if (r == 0) return 0;
        r = fourCellIDAssignment(four_cells);
        if (r == 0) return 0;

        if (four_cells < iEndingIndex) nuclei_next = (Vector)nuclei_record.elementAt(four_cells+1);
        for (i=0; i<nuc_ct; i++) {
            nucleii = (Nucleus)nuclei.elementAt(i);
            if (nucleii.identity.indexOf(POLAR) > -1) continue;
            // 20050809 changed sense of the next line -- should now match SN
            if (nucleii.predecessor == Nucleus.NILLI) lin_ct ++;
            if (nucleii.successor2 != Nucleus.NILLI) {
                Nucleus d1 = (Nucleus)nuclei_next.elementAt(nucleii.successor1 - 1);
                Nucleus d2 = (Nucleus)nuclei_next.elementAt(nucleii.successor2 - 1);
                sisterID(d1, d2, nuc_ct);
            }
        }
        lineage_ct_p[0] = lin_ct;
        return 1;
      }

    private int fourCellIDAssignment(int four_cells) {
        Vector nuclei;
        int nuc_ct;
        Nucleus north, south, west, east, ABa, ABp, EMS, P2;
        north=south=west=east=ABa=ABp=EMS=P2=null;
        int ntime, stime, etime, wtime;
        int i;
        nuclei = (Vector)nuclei_record.elementAt(four_cells);
        nuc_ct = nuclei.size();

        for (i=0; i<nuc_ct; i++) {
            Nucleus nucleii = (Nucleus)nuclei.elementAt(i);
            if (nucleii.id_tag == 'n') north = nucleii;
            else if (nucleii.id_tag == 's') south = nucleii;
            else if (nucleii.id_tag == 'e') east = nucleii;
            else if (nucleii.id_tag == 'w') west = nucleii;
        }
        ntime = timeToDivide(four_cells, north);
        if (ntime < 0) return 0;
        stime = timeToDivide(four_cells, south);
        if (stime < 0) return 0;
        etime = timeToDivide(four_cells, east);
        if (etime < 0) return 0;
        wtime = timeToDivide(four_cells, west);
        if (wtime < 0) return 0;

        if (wtime < etime) {ABa=west; P2=east; iParameters.ap=1;}
        else if (wtime > etime) {ABa=east; P2=west; iParameters.ap=-1; iParameters.apInit = -1;}
        else {
          System.out.println("putative ABa and P2 divide simutaneously.");
          return 0;
        }

        if (ntime < stime) {ABp=north; EMS=south; iParameters.dv=1; iParameters.dvInit = 1;}
        else if (ntime > stime) {ABp=south; EMS=north; iParameters.dv=-1; iParameters.dvInit = -1;}
        else {
            System.out.println("putative ABp and EMS divide simutaneously.");
          return 0;
        }
        iParameters.lr = iParameters.ap * iParameters.dv;
        iParameters.lrInit = iParameters.lr;
        ABa.identity = "ABa";
        ABp.identity = "ABp";
        EMS.identity = "EMS";
        P2.identity = "P2";
        String o = iNucleiMgr.getOrientation();
        //NucUtils.setOrientation(o);
        System.out.println("axis xyz = " + o + C.CS + iParameters.dvInit + C.CS + iParameters.dv);
        return 1;
    }

    private int timeToDivide(int current_time, Nucleus nuc) {
        while (current_time < iEndingIndex) {
            if (nuc.successor1 == Nucleus.NILLI) return -1;
            if (nuc.successor2 == Nucleus.NILLI) current_time++;
            else break;
            nuc = (Nucleus)((Vector)nuclei_record.elementAt(current_time)).elementAt(nuc.successor1 - 1);

        }
        return current_time;
    }

    private int alignDiamond(Vector nuclei) {
        int rtn = 1;
        int xmin, xmax, ymin, ymax;
        Nucleus north=null, south=null, west=null, east=null;
        int i;

        xmin = Integer.MAX_VALUE; //Movie.framewidth;
        xmax = 0;
        ymin = Integer.MAX_VALUE; //Movie.frameheight * Movie.framewidth;
        ymax = 0;
        for (i=0; i<nuclei.size(); i++) {
            Nucleus nucleii = (Nucleus)nuclei.elementAt(i);
            if (nucleii.status < 0 || nucleii.identity.indexOf(POLAR) > -1) continue;
            if (nucleii.x < xmin) {xmin = nucleii.x; west = nucleii;}
            if (nucleii.x > xmax) {xmax = nucleii.x; east = nucleii;}
            if (nucleii.y < ymin) {ymin = nucleii.y; north = nucleii;}
            if (nucleii.y > ymax) {ymax = nucleii.y; south = nucleii;}
         }
        if (north == null || south == null || west == null || east == null) {
            System.out.println("No diamond four cell stage at time:1 " + iParameters.t);
            return 0;
        }
        if (north==south || north==west || north==east || south==west || south==east || west==east) {
            System.out.println("No diamond four cell stage at time:2 " + iParameters.t);
            return 0;
        }
        north.id_tag = 'n';
        south.id_tag = 's';
        east.id_tag = 'e';
        west.id_tag = 'w';
        return rtn;
    }

    @SuppressWarnings("unused")
	private void polarBodies() {
        Vector nuclei = (Vector)nuclei_record.elementAt(0);
        Vector nuclei_next = null;
        int nuc_ct = nuclei.size();
        int i, t;
        int p_ct = 0;
        Nucleus nucleii;
        for (i = 0; i < nuc_ct; i++) {
            nucleii = (Nucleus)nuclei.elementAt(i);
            if (nucleii.status < 0) continue;
            if (nucleii.size < iParameters.polar_size) {
                nucleii.identity = POLAR + (p_ct + 1);
                p_ct++;
            }
        }
        if (p_ct == 0) return;

        for(i = 0; i < iEndingIndex; i++) {
            nuclei = (Vector)nuclei_record.elementAt(i);
            nuc_ct = nuclei.size();
            try {
                nuclei_next = (Vector)nuclei_record.elementAt(i + 1);
            } catch(ArrayIndexOutOfBoundsException oob) {
                break;
            }
            Nucleus nucleij = null;
            for (int j = 0; j < nuc_ct; j++) {
                nucleij = (Nucleus)nuclei.elementAt(j);
                if (nucleij.identity.indexOf(POLAR) == -1) continue;
                if (nucleij.successor1 == Nucleus.NILLI) p_ct--;
                if (p_ct == 0) break;
                if (nucleij.successor2 != Nucleus.NILLI) {
                    System.out.println("Polar body divided: "
                            + i + 1 + ":" + j + 1 + "->"
                            + i + 2 + ":" + nucleij.successor1 + " and "
                            + i + 2 + ":" + nucleij.successor2
                    );
                } else {
                    if (nucleij.successor1 == -1) continue;
                    Nucleus suc = (Nucleus)nuclei_next.elementAt(nucleij.successor1 - 1);
                    suc.identity = nucleij.identity;
                }
            }
        }

    }
    private int countCells(Vector nuclei) {
        int cell_ct = 0;
        Nucleus n;
        for (int i=0; i < nuclei.size(); i++) {
            n = (Nucleus)nuclei.elementAt(i);
            //if (n.status > -1 && !n.identity.equals(POLAR)) cell_ct++;
            if (n.status > -1 && n.identity.indexOf(POLAR) == -1) cell_ct++;
        }
        return cell_ct;
    }

    
    // Clears names of nuclei
    // If there is a forced name, the assignedID remains the same and the normal name (identity) is cleared
    @SuppressWarnings("unused")
	private void clearNames(Vector nuclei) {
        //println("clearNames: " + nuclei.size());
        Nucleus n;
        for (int i=0; i < nuclei.size(); i++) {
            n = (Nucleus)nuclei.elementAt(i);
            String id = n.identity;
            if (n.assignedID.length() > 0) 
            	continue;
            n.identity = "";
        }
    }
    
    
    // Forced names are kept while normal nuclei names (identity) is cleared
    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
	private int backAssignment(int four_cells, int [] lineage_ct_p) {
        System.out.println("backAssignment: " + four_cells);
        int i, j;
        Vector nuclei = null, nuclei_next = null, nuclei_prev = null;
        Nucleus nucleij = null, nucleijn = null;
        Nucleus suc1 = null, suc2 = null, pred = null;
        int nuc_ct;
        int lin_ct = lineage_ct_p[0];
        int successor1 = Nucleus.NILLI;
        int successor2 = Nucleus.NILLI;
        int badExit = 0;

        for (i=four_cells-1; i>=0; i--) {
            println("backAssignment: " + i);
            nuclei = (Vector)nuclei_record.elementAt(i);
            nuc_ct = nuclei.size();
            nuclei_next = (Vector)nuclei_record.elementAt(i + 1);
            successor1 = Nucleus.NILLI;
            successor2 = Nucleus.NILLI;
            // 20050809 key bug fix here to handle the case where
            // both successors were null implying a NUC cell name
            // in a backAssignment
            for (j = 0; j < nuc_ct; j++) {
                suc1 = null;
                suc2 = null;
                nucleij = (Nucleus)nuclei.elementAt(j);
                if (nucleij.identity.indexOf(POLAR) > -1) continue;
                if (nucleij.status == Nucleus.NILLI) continue;
                successor1 = nucleij.successor1;
                successor2 = nucleij.successor2;
                if (successor1 != Nucleus.NILLI) suc1 = (Nucleus)nuclei_next.elementAt(successor1 - 1);
                if (successor2 == Nucleus.NILLI) {
                    if (suc1 != null)
                        nucleij.identity = suc1.identity;
                    else
                        nucleij.identity = NUC + iNucCount++;
                }
                else {
                    suc2 = (Nucleus)nuclei_next.elementAt(successor2 - 1);
                    String s1 = suc1.identity;
                    String s2 = suc2.identity;
                    if (s1.equals("P2") || s1.equals("EMS")) {
                        if (s2.equals("P2") || s2.equals("EMS")) {
                            nucleij.identity = "P1";
                        } else {
                            System.out.println("bad sister names: " + s1 + ", " + s2);
                            badExit = 1;
                            break;
                        }
                    } else if ( s1.equals("ABa") || s1.equals("ABp")) {
                        if (s2.equals("ABa") || s2.equals("ABp")) {
                            nucleij.identity = "AB";
                        } else {
                            System.out.println("bad sister names: " + s1 + ", " + s2);
                            badExit = 1;
                            break;
                        }
                    } else if ( s1.equals("AB") || s1.equals("P1")) {
                        if (s2.equals("AB") || s2.equals("P1")) {
                            nucleij.identity = "P0";
                        } else {
                            System.out.println("bad sister names: " + s1 + ", " + s2);
                            badExit = 1;
                            break;
                        }
                    } else {
                            System.out.println("bad sister names: " + s1 + ", " + s2);
                            badExit = 1;
                            break;
                    }
                }
            }
            // test here for failure
            if (badExit > 0) {
                System.out.println("backtrace failure: " + i + C.CS + j + C.CS + nuc_ct);
                return 0;
            }
        }

        // process the first set of data only
        nuclei = (Vector)nuclei_record.elementAt(0);
        nuc_ct = nuclei.size();
        for (j=0; j < nuc_ct; j++) {
            nucleij = (Nucleus)nuclei.elementAt(j);
            if (nucleij.identity.indexOf(POLAR) > -1) continue;
            if (nucleij.identity == null) {
                nucleij.identity = NUC + iNucCount++;
            }
        }

        // now process the rest up to four_cells
        for(i = 1; i < four_cells; i++) {
            nuclei = (Vector)nuclei_record.elementAt(i);
            nuc_ct = nuclei.size();
            nuclei_next = (Vector)nuclei_record.elementAt(i + 1);
            nuclei_prev = (Vector)nuclei_record.elementAt(i - 1);
            for (j=0; j<nuc_ct; j++) {
                nucleij = (Nucleus)nuclei.elementAt(j);
                if (nucleij.identity.indexOf(POLAR) > -1) continue;
                boolean validId = nucleij.identity != null && !nucleij.identity.equals("");
                if (!validId && nucleij.predecessor == Nucleus.NILLI) {
                    lin_ct++;
                    nucleij.identity = NUC + iNucCount++;
                } else if (nucleij.identity == null) {
                    pred = (Nucleus)nuclei_prev.elementAt(nucleij.predecessor - 1);
                    successor2 = pred.successor2;
                    if (successor2 == Nucleus.NILLI) {
                        pred.identity = nucleij.identity;
                    } else {
                        newBornID(pred,
                                (Nucleus)nuclei.elementAt(pred.successor1 - 1),
                                (Nucleus)nuclei.elementAt(successor2 - 1)
                        );
                    }
                }
                // deliberate change from C code in next line initial reference
//                if (nucleij.successor2 != Nucleus.NILLI) {
                if (((Nucleus)nuclei.elementAt(j)).successor2 != Nucleus.NILLI) {
                    //iDLog.append("reached deliberate code change");
                    sisterID((Nucleus)nuclei_next.elementAt(nucleij.successor1 - 1)
                            ,(Nucleus)nuclei_next.elementAt(nucleij.successor2 - 1)
                            , nuc_ct
                    );
                }
            }
        }
        lineage_ct_p[0] = lin_ct;
        return 1;
    }

    public int getDivisor() {
        return iDivisor;
    }
    public void setDivisor(int divisor) {
        iDivisor = divisor;
    }
    public int getMinCutoff() {
        return iMinCutoff;
    }
    public void setMinCutoff(int minCutoff) {
        iMinCutoff = minCutoff;
    }

    public Vector [] getIdentities() {
        int rows = nuclei_record.size();
        Vector [] ida = new Vector[nuclei_record.size()];
        Vector names = null;
        Vector nuclei = null;
        Nucleus n = null;
        for (int i=0; i < rows; i++) {
            nuclei = (Vector)nuclei_record.elementAt(i);
            names = new Vector();
            Enumeration e = nuclei.elements();
            while(e.hasMoreElements()) {
                n = (Nucleus)e.nextElement();
                names.add(n.identity);
            }
            ida[i] = names;
        }
        return ida;
    }

    public void putIdentities(Vector [] ida) {
        Vector names = null;
        Vector nuclei = null;
        Nucleus n = null;
        for (int i=0; i < ida.length; i++) {
            nuclei = (Vector)nuclei_record.elementAt(i);
            names = ida[i];
            for (int j=0; j < names.size(); j++) {
                n = (Nucleus)nuclei.elementAt(j);
                n.identity = (String)names.elementAt(j);
            }
        }
    }

    private void reportDividingCell(int time, Nucleus n) {
        //System.out.println("processDividingCell: " + time);
        //System.out.println(n);
        iLine = new Line();
        iLine.add(getConfigFileInfo(iNucleiMgr.getConfigFileName()));
        //append(time + C.CS + n.toString());
        int liveCells = NucUtils.countLiveCells((Vector)nuclei_record.elementAt(time));
        iLine.add(liveCells);
        int kd1 = n.successor1 - 1;
        int kd2 = n.successor2 - 1;
        //append("kd1, kd2: " + kd1 + C.CS + kd2);
        Nucleus nd1 = null;
        Nucleus nd2 = null;;
        for (int i=time+1; i <= time+5 && i < iNucleiMgr.getEndingIndex(); i++) {
            //System.out.println("processDividingCell, i=" + i);
            Vector nuclei = (Vector)nuclei_record.elementAt(i);
            try {
                nd1 = (Nucleus)nuclei.elementAt(kd1);
                nd2 = (Nucleus)nuclei.elementAt(kd2);
                //append(i + C.CS + nd1.toString());
                //append(i + C.CS + nd2.toString());
            } catch(ArrayIndexOutOfBoundsException aiob) {
                System.out.println("ArrayIndexOutOfBounds: " + time + C.CS + i + C.CS + kd1 + C.CS + kd2);
                System.out.println(n);
                System.out.println(nd1);
                System.out.println(nd2);
                return;
            }
            processPair(nd1, nd2);
            kd1 = nd1.successor1 - 1;
            kd2 = nd2.successor1 - 1;
        }
        iLine.add(n.identity);
        iLine.add(time);
        try {
        if (nd1 != null) iLine.add(nd1.identity);
        if (nd2 != null) iLine.add(nd2.identity);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(nd1);
            System.out.println(nd2);

        }
        System.out.println(iLine.iBuf.toString());
    }

    private void processPair(Nucleus nd1, Nucleus nd2) {
        int avg = (nd1.size + nd2.size)/DUMPIdentity.DIVISOR;
        Loc nd1L = new Loc(nd1, iNucleiMgr);
        Loc nd2L = new Loc(nd2, iNucleiMgr);
        int x = 100*(nd1L.x - nd2L.x)/avg;
        int y = 100*(nd1L.y - nd2L.y)/avg;
        int z = 100*(nd1L.z - nd2L.z)/avg;
        iLine.add(x);
        iLine.add(y);
        iLine.add(z);
    }

    private String getConfigFileInfo(String longName) {
        System.out.println("getConfigFileInfo: " + longName);
        String s = longName.substring(longName.lastIndexOf('/') + 1);
        s = s.substring(0, s.indexOf('.'));
        return s;
    }

    public Parameters getParameters() {
        return iParameters;
    }


    private static final char
    E = 'e'
   ,W = 'w'
   ,V = 'v'
   ,D = 'd'
   ,B = 'b' //'d' based on identity study 20050614
   ,T = 't' //'v'
   ,A = 'a'
   ,P = 'p'
   ,L = 'l'
   ,R = 'r'
   ,X = 'X'    // a dummy tag used in newBornID
   ,N = 'n'
   ,S = 's'
   ,IGNORESULSTON = 'i'
   ;



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

    public static final String [] NAMING_METHOD = {
        "NONE"
        ,"STANDARD"
        ,"MANUAL"
        ,"NEWCANONICAL"
    };



    private static final String
    POLAR = "polar"
   ,NUC = "Nuc"
   ,AB = "AB"
   ,MS = "MS"
   ;
    public static void main(String[] args) {
    }

    private static void println(String s) {System.out.println(s);}
    private static final String CS = ", ";

/*








*/
}
/*
private String getOrientation() {
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
    return orientation + " -> " + late;
}
*/

