package org.rhwlab.snight;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import org.rhwlab.acetree.AceTree;


public class DivisionCaller {

	Hashtable		iRulesHash;
	Hashtable		iSulstonHash;
	String			iAxis;
	String			iAxisUse;
	double			iZPixRes;
	MeasureCSV		iMeasureCSV;
	double			iAng;
	double			iEMajor;
	double			iEMinor;
	double			iZSlope;
	Vector2d		iAngVec;
	double			iDMajor;
	double			iDMinor;
	double			iDSlope;
	boolean			iDebug;
	double [] 		iDaCorrected;

	public DivisionCaller(String axis, double zpixRes, MeasureCSV measureCSV) {
		iRulesHash = new Hashtable();
		readNewRules();
		readSulstonRules();
		iAxis = axis.toUpperCase();
		iZPixRes = zpixRes;
		iMeasureCSV = measureCSV;
		getScalingParms();
		//showMeasureCSV();
	}

	public DivisionCaller() {

	}



	void getScalingParms() {
		String sang = (String)iMeasureCSV.iMeasureHash.get("ang");
		if (sang.length() > 0) {
			iAng = Math.toRadians(-Double.parseDouble(sang));
		} else {
			iAng = Math.toRadians(-Double.parseDouble(MeasureCSV.defaultAtt[MeasureCSV.EANG]));
		}
		iAngVec = new Vector2d(Math.cos(iAng), Math.sin(iAng));
		String smaj = (String)iMeasureCSV.iMeasureHash.get("maj");
		if (smaj.length() > 0) {
			iEMajor = Double.parseDouble(smaj);
		} else {
			iEMajor = Double.parseDouble(MeasureCSV.defaultAtt[MeasureCSV.EMAJOR]);
		}

		String smin = (String)iMeasureCSV.iMeasureHash.get("min");
		if (smin.length() > 0) {
			iEMinor = Double.parseDouble(smin);
		} else {
			iEMinor = Double.parseDouble(MeasureCSV.defaultAtt[MeasureCSV.EMINOR]);
		}

		String szslope = (String)iMeasureCSV.iMeasureHash.get("zslope");
		if (szslope.length() > 0) {
			iZSlope = Double.parseDouble(szslope);
		} else {
			iZSlope = Double.parseDouble(iMeasureCSV.defaultAtt[MeasureCSV.ZSLOPE]);
		}
		iDMajor = Double.parseDouble(iMeasureCSV.defaultAtt[MeasureCSV.EMAJOR]);
		iDMinor = Double.parseDouble(iMeasureCSV.defaultAtt[MeasureCSV.EMINOR]);
		iDSlope = Double.parseDouble(iMeasureCSV.defaultAtt[MeasureCSV.ZSLOPE]);

	}

	void showMeasureCSV() {
		Enumeration e = iMeasureCSV.iMeasureHash.keys();
		while (e.hasMoreElements()) {
			String key = (String)e.nextElement();
			String value = (String)iMeasureCSV.iMeasureHash.get(key);
			println("showMeasureCSV, " + key + CS + value);
		}
	}

	public String getRuleString(String parent) {
		Rule r = (Rule)iRulesHash.get(parent);
		if (r == null) return "";
		else return r.toString();
	}

	Rule getRule(Nucleus parent) {
		Rule r = (Rule)iRulesHash.get(parent.identity);
		if (r == null) {
			if (1 == 1) {
				//println("missing rule, " + parent.identity);
				//System.exit(0);
			}
			String pname = parent.identity;
			String sulston = (String)iSulstonHash.get(pname);
			if (sulston == null || pname.startsWith("Nuc")) sulston = "a";
			else sulston = sulston.substring(0, 1);
			String sdau1 = parent.identity + sulston;
			char c = complement(sulston.charAt(0));
			String sdau2 = parent.identity + c;
			int x = 0;
			int y = 0;
			int z = 0;
			if (sulston.equals("a")) {
				x = 1;
			} else if (sulston.equals("l")) {
				z = 1;
			} else {
				y = 1;
			}
			sulston += "0";
			r = new Rule(pname, sulston, sdau1, sdau2, x, y, z);
			// assuming dummy rules are late in embryonic development
			// introduce rotation
			if (iAxis.equals("ADL")) iAxisUse = "ARD";
			if (iAxis.equals("AVR")) iAxisUse = "ALV";
			if (iAxis.equals("PDR")) iAxisUse = "PLD";
			if (iAxis.equals("PVL")) iAxisUse = "PRV";

		}
		return r;

	}

	public double getDotProduct(Nucleus parent, Nucleus dau1, Nucleus dau2) {
		Rule r = getRule(parent);
		return getDotProduct(parent, dau1, dau2, r);
	}

	public double [] getDaCorrected() {
		return iDaCorrected;
	}


	double getDotProduct(Nucleus parent, Nucleus dau1, Nucleus dau2, Rule r) {
		iAxisUse = iAxis;
		Vector3d template = new Vector3d(r.iX, r.iY, r.iZ);

		double [] daCorrected = diffsCorrected(dau1, dau2);
		iDaCorrected = daCorrected;
		Vector3d sample = new Vector3d(daCorrected);
		sample.normalize();
		double dotCorrected = template.dot(sample);
		double dot = dotCorrected;
		Double Dot = new Double(dot);
		if (Dot.isNaN()) dot = 0;
		return dot;
	}

	StringBuffer assignNames(Nucleus parent, Nucleus dau1, Nucleus dau2) {
		String newd1 = "";
		String newd2 = "";
		Rule r = getRule(parent);
		double dot = getDotProduct(parent, dau1, dau2, r);
		if (dot > 0) {
			newd1 = r.iDau1;
			newd2 = r.iDau2;
		}
		else {
			newd1 = r.iDau2;
			newd2 = r.iDau1;
		}
		dau1.identity = newd1;
		dau2.identity = newd2;

		String pname = parent.identity;
		//if (pname.startsWith("E")) {
		//	println("DivisionCaller, " + fmt4(Math.abs(dot)) + CS + pname);
		//}


		return null;

	}


	StringBuffer assignNames(Nucleus parent, Nucleus dau1, Nucleus dau2, boolean obsolete) {
		StringBuffer sb = null;
		iDebug = false;
		iAxisUse = iAxis;
		if (parent.identity.equals("ABalaaapa")) {
			iDebug = true;
		}
		//String oldd1 = dau1.identity;
		//String oldd2 = dau2.identity;
		String newd1 = "";
		String newd2 = "";
		Rule r = (Rule)iRulesHash.get(parent.identity);
		//if (iDebug) println("assignNames, rule=" + r);
		if (r == null) {
			if (1 == 1) {
				//println("missing rule, " + parent.identity);
				//System.exit(0);
			}
			String pname = parent.identity;
			String sulston = (String)iSulstonHash.get(pname);
			if (sulston == null || pname.startsWith("Nuc")) sulston = "a";
			else sulston = sulston.substring(0, 1);
			String sdau1 = parent.identity + sulston;
			char c = complement(sulston.charAt(0));
			String sdau2 = parent.identity + c;
			int x = 0;
			int y = 0;
			int z = 0;
			if (sulston.equals("a")) {
				x = 1;
			} else if (sulston.equals("l")) {
				z = 1;
			} else {
				y = 1;
			}
			sulston += "0";
			r = new Rule(pname, sulston, sdau1, sdau2, x, y, z);
			// assuming dummy rules are late in embryonic development
			// introduce rotation
			if (iAxis.equals("ADL")) iAxisUse = "ARD";
			if (iAxis.equals("AVR")) iAxisUse = "ALV";
			if (iAxis.equals("PDR")) iAxisUse = "PLD";
			if (iAxis.equals("PVL")) iAxisUse = "PRV";
			if (!iAxisUse.equals(iAxis)) {
				//println("USING ROTATED AXIS");
			}

		}
		/*
		 *     	if (orientation.equals("ADL")) late = "ARD";
	     *		else if (orientation.equals("AVR")) late = "ALV";
	     *		else if (orientation.equals("PDR")) late = "PLD";
	     *		else if (orientation.equals("PVL")) late = "PRV";
		 */
		//println("assignNames, " + r);
		Vector3d template = new Vector3d(r.iX, r.iY, r.iZ);

		//double [] daStraight = diffsStraight(dau1, dau2);
		//Vector3d sample = new Vector3d(daStraight);
		//sample.normalize();
		//double dotStraight = template.dot(sample);

		double [] daCorrected = diffsCorrected(dau1, dau2);
		Vector3d sample = new Vector3d(daCorrected);
		sample.normalize();
		double dotCorrected = template.dot(sample);
		double dot = dotCorrected;


		if (dot > 0) {
			newd1 = r.iDau1;
			newd2 = r.iDau2;
		}
		else {
			newd1 = r.iDau2;
			newd2 = r.iDau1;
		}

		/*
		boolean nochange1 = oldd1.equals(newd1);
		boolean nochange2 = oldd2.equals(newd2);
		boolean changed = !(nochange1 && nochange2);
		//changed = changed && (dotCorrected * dotStraight < 0);

		if (changed) {
			sb = new StringBuffer(r.toString());
			sb.append(C + iAxis + C + iAxisUse);
			sb.append(C + fmt4(dotCorrected));
			//sb.append(C + fmt4(dotStraight));
			sb.append(C + fmt4(daCorrected[0]) + C + fmt4(daCorrected[1]) + C + fmt4(daCorrected[2]));
			//sb.append(C + fmt4(daStraight[0]) + C + fmt4(daStraight[1]) + C + fmt4(daStraight[2]));

		}
		*/

		dau1.identity = newd1;
		dau2.identity = newd2;
		return sb;
	}

	double [] diffs(Nucleus d1, Nucleus d2) {
		double [] da = new double[3];
		da[0] = d2.x - d1.x;
		da[1] = d2.y - d1.y;
		da[2] = d2.z - d1.z;
		da[2] *= iZPixRes;
		measurementCorrection(da);
		if (iAxisUse.equals("AVR")) {
			da[1] *= -1;
			da[2] *= -1;
		} else if (iAxisUse.equals("PVL")) {
			da[0] *= -1;
			da[1] *= -1;
		} else if (iAxisUse.equals("PDR")) {
			da[0] *= -1;
			da[2] *= -1;
		} else if (iAxisUse.equals("ARD")) {
			da[1] *= -1;
		} else if (iAxisUse.equals("ALV")) {
			da[2] *= -1;
		} else if (iAxisUse.equals("PLD")) {
			da[0] *= -1;
		} else if (iAxisUse.equals("PRV")) {
			da[1] *= -1;
			da[2] *= -1;
		}
		return da;
	}


	double [] diffsStraight(Nucleus d1, Nucleus d2) {
		double [] da = new double[3];
		da[0] = d2.x - d1.x;
		da[1] = d2.y - d1.y;
		da[2] = d2.z - d1.z;
		da[2] *= iZPixRes;
		//if (iDebug) println("diffs, " + fmt4(da[0]) + CS + fmt4(da[1]) + CS + fmt4(da[2]));
		//measurementCorrection(da);
		//if (iDebug) println("diffs, " + fmt4(da[0]) + CS + fmt4(da[1]) + CS + fmt4(da[2]));
		if (iAxisUse.equals("AVR")) {
			da[1] *= -1;
			da[2] *= -1;
		} else if (iAxisUse.equals("PVL")) {
			da[0] *= -1;
			da[1] *= -1;
		} else if (iAxisUse.equals("PDR")) {
			da[0] *= -1;
			da[2] *= -1;
		} else if (iAxisUse.equals("ARD")) {
			da[1] *= -1;
		} else if (iAxisUse.equals("ALV")) {
			da[2] *= -1;
		} else if (iAxisUse.equals("PLD")) {
			da[0] *= -1;
		} else if (iAxisUse.equals("PRV")) {
			da[1] *= -1;
			da[2] *= -1;
		}
		return da;
	}

	double [] diffsCorrected(Nucleus d1, Nucleus d2) {
		double [] da = new double[3];
		da[0] = d2.x - d1.x;
		da[1] = d2.y - d1.y;
		da[2] = d2.z - d1.z;
		da[2] *= iZPixRes;
		//if (iDebug) println("diffs, " + fmt4(da[0]) + CS + fmt4(da[1]) + CS + fmt4(da[2]));
		measurementCorrection(da);
		//if (iDebug) println("diffs, " + fmt4(da[0]) + CS + fmt4(da[1]) + CS + fmt4(da[2]));
		if (iAxisUse.equals("AVR")) {
			da[1] *= -1;
			da[2] *= -1;
		} else if (iAxisUse.equals("PVL")) {
			da[0] *= -1;
			da[1] *= -1;
		} else if (iAxisUse.equals("PDR")) {
			da[0] *= -1;
			da[2] *= -1;
		} else if (iAxisUse.equals("ARD")) {
			da[1] *= -1;
		} else if (iAxisUse.equals("ALV")) {
			da[2] *= -1;
		} else if (iAxisUse.equals("PLD")) {
			da[0] *= -1;
		} else if (iAxisUse.equals("PRV")) {
			da[1] *= -1;
			da[2] *= -1;
		}
		return da;
	}

	void measurementCorrection(double [] da) {
		// correct for angle
		double [] dxy = handleRotation(da[0], da[1], iAng);
		da[0] = dxy[0];
		da[1] = dxy[1];
		// correct for x stretch
		da[0] *= (iEMajor/iDMajor);
		// correct for y stretch
		da[1] *= (iEMinor/iDMinor);
		// correct for z stretch
		da[2] *= (iZSlope/iDSlope);
	}

	public static double [] handleRotation(double x, double y, double ang) {
		//double x = da[0];
		//double y = da[1];
		//double ang = iAng;
		//ang -= 1;
		double cosang = Math.cos(ang);
		double sinang = Math.sin(ang);
		double denom = cosang * cosang + sinang * sinang;
		double xpnum = x * cosang + y * sinang;
		double xp = xpnum / denom;
		double yp = (y - xp * sinang) / cosang;
		double [] da = new double[2];
		da[0] = xp;
		da[1] = yp;
		return da;

	}

	void readNewRules() {
	    URL url = AceTree.class.getResource("/org/rhwlab/snight/NewRules.txt");
	    InputStream istream = null;
	    try {
	        istream = url.openStream();
	        BufferedReader br = new BufferedReader(new InputStreamReader(istream));
	        String s;
	        br.readLine(); //toss the header
	        while (br.ready()) {
	            s = br.readLine();
	            if (s.length() == 0) continue;
	            //println("readNewRules, " + s);
	            String [] sa = s.split(TAB);
	            Rule r = new Rule(sa);
	            iRulesHash.put(sa[0], r);
	        }
	        br.close();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}


    public void readSulstonRules() {
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
        iSulstonHash = namingHash;

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


	public class Rule {
		double  DOTTOL = 0.6;
		String 	iParent;
		String  iRule;
		String	iDau1;
		String  iDau2;
		public double	iX;
		public double	iY;
		public double   iZ;

		public Rule(String [] sa) {
			iParent = sa[0];
			iRule = sa[1];
			iDau1 = sa[2];
			iDau2 = sa[3];
			iX = Double.parseDouble(sa[4]);
			iY = Double.parseDouble(sa[5]);
			iZ = Double.parseDouble(sa[6]);
		}

		// constructor for default rule
		public Rule(String parent, String rule, String dau1, String dau2, double x, double y, double z) {
			iParent = parent;
			iRule = rule;
			iDau1 = dau1;
			iDau2 = dau2;
			iX = x;
			iY = y;
			iZ = z;
			//println("Rule, default rule in use");
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append(iParent);
			//sb.append(CS + iRule);
			sb.append(C + iDau1);
			sb.append(C + iDau2);
			sb.append(C + fmt4(iX));
			sb.append(C + fmt4(iY));
			sb.append(C + fmt4(iZ));
			return sb.toString();
		}

	}

	public static void test() {
		double x = 20;
		double y = 10;
		double deg = -15;
		double ang = Math.toRadians(deg);
		double cosang = Math.cos(ang);
		double sinang = Math.sin(ang);
		double denom = cosang * cosang + sinang * sinang;
		double xpnum = x * cosang + y * sinang;
		double xp = xpnum / denom;
		double yp = (y - xp * sinang) / cosang;
		println("test, " + x + CS + y + CS + fmt4(xp) + CS + fmt4(yp));
		double check0 = x * x + y * y;
		double check1 = xp * xp + yp * yp;
		println("test, " + fmt4(check0) + CS + fmt4(check1));
	}

	public static void test2() {
		double hyp = 50;
		double deg = 30;
		double ang = Math.toRadians(deg);
		double y = hyp * Math.sin(ang);
		double x = hyp * Math.cos(ang);
		double [] da = handleRotation(x, y, ang);
		double xp = da[0];
		double yp = da[1];
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//DivisionCaller dc = new DivisionCaller("ADL", 11.1);
		//dc.readNewRules();
		//println("main, " + dc.iRulesHash.size());
		test2();

	}

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
