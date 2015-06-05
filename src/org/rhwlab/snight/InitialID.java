package org.rhwlab.snight;

import java.util.Vector;

import javax.vecmath.Vector2d;

import org.rhwlab.utils.C;

public class InitialID {

	NucleiMgr		iNucleiMgr;
	Vector 			nuclei_record;
	Parameters		iParameters;
	int				iNucCount;
	int				iEndingIndex;
    int		        iDivisor;
    int		        iMinCutoff;
    String			iAxis;
    MeasureCSV		iMeasureCSV;

    double			iAng;
    int				iXC;
    int				iYC;
    int				iX; //transient
    int				iY;


	public InitialID(NucleiMgr nucMgr, Parameters parameters) {
		iNucleiMgr = nucMgr;
		nuclei_record = iNucleiMgr.getNucleiRecord();
		iParameters = parameters;
		iNucCount = 1;
		iEndingIndex = iNucleiMgr.iEndingIndex;
		iMeasureCSV = iNucleiMgr.getMeasureCSV();
		getCoordinateParms();

		println("InitialID measureCSV: ");
		println("" + iMeasureCSV);

	}

	void getCoordinateParms() {
		String sang = (String)iMeasureCSV.iMeasureHash.get("ang");
		if (sang.length() > 0) {
			iAng = Math.toRadians(-Double.parseDouble(sang));
		} else {
			iAng = Math.toRadians(-Double.parseDouble(MeasureCSV.defaultAtt[MeasureCSV.EANG]));
		}
		String x = (String)iMeasureCSV.iMeasureHash.get("xc");
		if (x.length() > 0) {
			iXC = Integer.parseInt(x);
		} else {
			iXC = Integer.parseInt(MeasureCSV.defaultAtt[MeasureCSV.EXCENTER]);

		}
		String y = (String)iMeasureCSV.iMeasureHash.get("yc");
		if (y.length() > 0) {
			iYC = Integer.parseInt(x);
		} else {
			iYC = Integer.parseInt(MeasureCSV.defaultAtt[MeasureCSV.EYCENTER]);

		}
	}

	void applyTransformation(int [] ia) {
		int x = ia[0] - iXC;
		int y = ia[1] - iYC;
		double [] da = DivisionCaller.handleRotation(x, y, iAng);
		ia[0] = iXC + (int)Math.round(da[0]);
		ia[1] = iYC + (int)Math.round(da[1]);
	}



	int getNucCount() {
		return iNucCount;
	}

    ///////// code connected to initialID determination below here
    // note modifications made on 20050804 to prevent a nucleus
    // named polar from being renamed Nuc-xxx
    // in three places
    int initialID(int [] start_p, int [] lineage_ct_p) {
    	println("initialID called: " + start_p[0] + CS + lineage_ct_p[0]);
    	int startingIndex = start_p[0];
        int rtn = 0;
        int lin_ct = lineage_ct_p[0];
        int first_four = -1, last_four = -1, four_cells;
        Vector nuclei = (Vector)nuclei_record.elementAt(startingIndex - 1);
        //Vector nuclei = (Vector)nuclei_record.elementAt(0);
        int nuc_ct = nuclei.size();
        int cell_ct = countCells(nuclei);
        if (cell_ct <= 6) {
        	System.out.println("<= 6 cells");
            polarBodies();
            cell_ct = countCells(nuclei);
        }
        if (cell_ct > 4) {
        	System.out.println("> 4 cells");
            Nucleus nucleij = null;
            for (int j=0; j < nuc_ct; j++) {
                nucleij = (Nucleus)nuclei.elementAt(j);
                if (nucleij.status == -1)
                	continue;
                //lin_ct++;
                if (nucleij.identity.indexOf(POLAR) > -1)
                	continue; //modification 20050804
                nucleij.identity = NUC + iNucCount++;
            }
            iParameters.axis = 0;
            start_p[0] = 0;
            lineage_ct_p[0] = lin_ct;
            System.out.println("Starting with more than 4 cells.  No canonical ID assigned.");
            return 0;
        } else {
            iParameters.axis = 1;
            if (cell_ct == 4)
            	first_four = 0;
            for (int i=startingIndex - 1; i < iEndingIndex - 1; i++) {
                nuclei = (Vector)nuclei_record.elementAt(i);
                nuc_ct = nuclei.size();
                cell_ct = countCells(nuclei);
                if (cell_ct > 4)
                	break;
                if (cell_ct == 4) {
                    if (first_four < 0) first_four = i;
                    last_four = i;
                }
            }

            if (first_four == -1) {
                //nuclei = (Vector)nuclei_record.elementAt(0);
            	nuclei = (Vector)nuclei_record.elementAt(3);
                nuc_ct = nuclei.size();
                Nucleus nucleij = null;
                for (int j=0; j < nuc_ct; j++) {
                    nucleij = (Nucleus)nuclei.elementAt(j);
                    if (nucleij.status == -1)
                    	continue;
                    if (nucleij.identity.indexOf(POLAR) > -1)
                    	continue;  //modification 20050804
                    lin_ct++;
                    nucleij.identity = NUC + iNucCount++;
                }
                iParameters.axis = 0;
                start_p[0] = 0;
                //start_p[0] = 4;
                lineage_ct_p[0] = lin_ct;
                System.out.println("Movie too short to see four cells");
                //new Throwable().printStackTrace();
                return 0;
            }
        }

        four_cells = (first_four + last_four)/2;
        start_p[0] = four_cells + 1;

        rtn = fourCellID(four_cells, lineage_ct_p);
        if (rtn != 0)
        	rtn = backAssignment(four_cells, lineage_ct_p);
        if (rtn == 0) {
            iParameters.axis = 0;
            return 1;
        }
        return 0;
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
            int [] ia = new int[2];
            ia[0] = nucleii.x;
            ia[1] = nucleii.y;
            //println("alignDiamond, BEFORE, " + ia[0] + CS + ia[1] + CS + xmin + CS + xmax + CS + ymin + CS + ymax);
            applyTransformation(ia);
            //println("alignDiamond, AFTER , " + ia[0] + CS + ia[1]);
            if (ia[0] < xmin) {xmin = ia[0]; west = nucleii;}
            if (ia[0] > xmax) {xmax = ia[0]; east = nucleii;}
            if (ia[1] < ymin) {ymin = ia[1]; north = nucleii;}
            if (ia[1] > ymax) {ymax = ia[1]; south = nucleii;}
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


    private int alignDiamond(Vector nuclei, boolean old) {
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
	private int backAssignment(int four_cells, int [] lineage_ct_p) {
        //System.out.println("backAssignment: " + four_cells);
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
            //println("backAssignment: " + i);
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


    public int newBornID(Nucleus mother, Nucleus dau1, Nucleus dau2) {
        //println("newBornID: " + mother.identity + CS + dau1.identity + CS + dau2.identity);
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


    private int timeToDivide(int current_time, Nucleus nuc) {
        while (current_time < iEndingIndex) {
            if (nuc.successor1 == Nucleus.NILLI) return -1;
            if (nuc.successor2 == Nucleus.NILLI) current_time++;
            else break;
            nuc = (Nucleus)((Vector)nuclei_record.elementAt(current_time)).elementAt(nuc.successor1 - 1);

        }
        return current_time;
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

    // this is a dummy version of sisterID so if it is really needed
    // we are toast
    private void sisterID(Nucleus nuc1, Nucleus nuc2, int nuc_ct) {
    	nuc1.id_tag = A;
        nuc2.id_tag = P;
        return;
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





	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

    private static void println(String s) {System.out.println(s);}
    private static final String CS = ", ";

}
