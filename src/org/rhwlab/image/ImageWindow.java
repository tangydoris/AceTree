/*
 * Copyright 2005 University of Washington Genome Sciences
 * All rights reserved
 */
package org.rhwlab.image;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.IllegalArgumentException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import java.util.zip.ZipEntry;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.net.URL;




import ij.IJ;
import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.OvalRoi;
import ij.io.FileInfo;
import ij.io.FileOpener;
import ij.io.FileSaver;
import ij.io.Opener;
import ij.io.TiffDecoder;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import ij.process.FloatProcessor;
import ij.process.MedianCut;
import ij.ImageStack;
import ij.process.StackProcessor;




import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;
import javax.swing.ImageIcon;




import net.sf.ij.jaiio.BufferedImageCreator;

import org.rhwlab.acetree.PlayerControl;
import org.rhwlab.acetree.AceTree;
import org.rhwlab.acetree.AnnotInfo;
import org.rhwlab.acetree.NucUtils;
import org.rhwlab.help.AceTreeHelp;
//import org.rhwlab.image.Image3D.SublineageDisplayProperty;
//import org.rhwlab.image.Image3D.PropertiesTab.SublineageUI;
import org.rhwlab.nucedit.AddOneDialog;
import org.rhwlab.nucedit.EIDialog1;
import org.rhwlab.nucedit.UnifiedNucRelinkDialog;
import org.rhwlab.snight.NucleiMgr;
import org.rhwlab.snight.Nucleus;
import org.rhwlab.tree.Cell;
import org.rhwlab.utils.C;
import org.rhwlab.utils.EUtils;
import org.rhwlab.acetree.PartsList;

/**
 * Provides a JFrame window to contain the ImageJ ImagePlus object
 *
 * @author biowolp
 * @version 1.0 January 25, 2005
 */
public class ImageWindow extends JFrame implements  KeyListener, Runnable {
	public ImageCanvas      iImgCanvas;
    static ImagePlus        iImgPlus;
    String                  iTitle;
    static Object []        iSpecialEffect;
    AceTree                 iAceTree;
    Vector                  iAnnotsShown;
    MouseHandler            iMouseHandler;
    WinEventMgr 			wem;
    boolean                 iMouseEventHandled;
    int                     iImageTime;
    int                     iTimeInc;
    int                     iImagePlane;
    int                     iPlaneInc;
    boolean                 iIsMainImgWindow;
    boolean                 iIsRightMouseButton;
    boolean                 iSaveImage;
    boolean                 iSaveInProcess;
    String                  iSaveImageDirectory;
    boolean                 iUseRobot;
    boolean                 iNewConstruction;
    PartsList iPartsList;
    //private JTabbedPane     iTabbedPane;
    public static ColorSchemeDisplayProperty []     iDispProps;
    private   JPanel        iControlPanel;
    //protected JMenuBar      iMenuBar;
    protected JToolBar      iToolBar;
    protected JButton	    iHelp;
    protected JButton       iProperties;

    ImageZoomerFrame		iImageZoomerFrame;
    ImageZoomerPanel 		iImageZoomerPanel;
    static boolean         	cAcbTree = false;
    
    //static float contrast1a, contrast1b, contrast2a, contrast2b;
    //static double contrastmin1, contrastmax1, contrastmin2, contrastmax2;
    public static int contrastmin1, contrastmax1, contrastmin2, contrastmax2;
    
    static byte []          iRpix;
    static byte []          iGpix;
    static byte []          iBpix;

    public static int 			imagewindowPlaneNumber;//unlike iImagePlane this includes imcrement number used only for new image access
    public static int 			imagewindowUseStack;

    // static variables and functions

    public static String        cZipTifFilePath;
    public static String        cTifPrefix;
    public static String        cTifPrefixR;
    public static int           cUseZip;
    static ZipImage             cZipImage;
    public static NucleiMgr     cNucleiMgr;
    public static int           cImageWidth;
    public static int           cImageHeight;
    public static int           cLineWidth;
    public static String        cCurrentImageFile;
    public static String        cCurrentImagePart;

    ImagePlus                   currentImage;
    
    protected DefaultListModel	iBookmarkListModel;
    protected ImageContrastTool ict;
    protected JButton			ictApplyButton;
    protected JSlider			iSlider1min;
    protected JSlider			iSlider1max;
    protected JSlider			iSlider2min;
    protected JSlider			iSlider2max;
    
    protected static boolean	setOriginalContrastValues;

    /**
     * this is the constructor that is actually used
     * note that there are many static functions and class variables
     */
    public ImageWindow(String title, ImagePlus imgPlus, PlayerControl  playercontrol) {
        super(title.substring(4,title.length()));
        setOriginalContrastValues = true;
        iPartsList = new PartsList();
        iTitle = title;
        iImgPlus = imgPlus;
        ImageCanvas ic = new ImageCanvas(imgPlus);
        iImgCanvas = ic;
        iDispProps = getDisplayProps();

	    //custom icon
	    URL imageURL = ImageWindow.class.getResource("/images/icon2.gif");
	    ImageIcon test=new ImageIcon(imageURL, "x");	
	    this.setIconImage(test.getImage());

        Container c = getContentPane();
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());

	    //attempt to replace main view with zoomer A.S. 1/17/2013
	    //remmed this out

	    //added these 3
        BufferedImage image = BufferedImageCreator.create((ColorProcessor)iImgPlus.getProcessor());
        iImageZoomerPanel= new ImageZoomerPanel(this, image, 10.0, title,playercontrol);
	    //izf.addKeyListener(this); //added to make zoom window respond to key events -AS 11/23/11
	    jp.add(iImageZoomerPanel);
        c.add(jp);

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        wem = new WinEventMgr();
        addWindowFocusListener(wem);
        addWindowListener(wem);
        iMouseHandler = new MouseHandler(this);
	    //further changing of main to zoom 
	    iImageZoomerPanel.getImage().addMouseMotionListener(iMouseHandler);
	    iImageZoomerPanel.getImage().addMouseListener(iMouseHandler);

	    setImageTimeAndPlaneFromTitle();
        iAnnotsShown = new Vector();
        iIsRightMouseButton = false;
        iSaveImage = false;
        iSaveImageDirectory = null;
        iUseRobot = false;

        iImgCanvas.addKeyListener(this);
        
        iBookmarkListModel = null;
        ict = null;
        ictApplyButton = null;
        iSlider1min = iSlider1max = iSlider2min = iSlider2max = null;
        
        // Original contrast percentages
        System.out.println("ImageWindow set default contrast values: 0 to max16bit/max8bit");
        contrastmin1 = contrastmin2 = 0;
        if (imagewindowUseStack == 1)
        	contrastmax1 = contrastmax2 = MAX16BIT;
        else
        	contrastmax1 = contrastmax2 = MAX8BIT;
        // Hardcoded contrast channel values to begin with for 16-bit to 8-bit conversion
        /*
        contrast1a = 1400;
        contrast1b = 3000;
        contrast2a = 20;
        contrast2b = 235;
        */
        
        //20, 235, 1400, 3000
    }
    
    public void removeHandlers() {
    	// Remove mouse handler
    	iImageZoomerPanel.getImage().removeMouseListener(iMouseHandler);
	    iImageZoomerPanel.getImage().removeMouseListener(iMouseHandler);
	    iMouseHandler = null;
	    
	    // Remove window event manager
	    removeWindowFocusListener(wem);
        removeWindowListener(wem);
        wem = null;
    }

    public void setBookmarkList(ListModel list) {
    	iBookmarkListModel = (DefaultListModel)list;
    }
    
    public MouseHandler getMouseHandler() {
    	return iMouseHandler;
    }


    public static void setStaticParameters(String zipTifFilePath, String tifPrefix, int useZip) {
        //System.out.println("ImageWindow.setStaticParameters entered");
        cZipTifFilePath = zipTifFilePath;
        cTifPrefix = tifPrefix;
        cUseZip = useZip;
        if (cUseZip == 1) cZipImage = new ZipImage(cZipTifFilePath);
        cLineWidth = 1;//LINEWIDTH; //set to 1 default -AS 11/23/11
        String [] sa = cTifPrefix.split("/");
        if(sa.length > 1) cTifPrefixR = sa[0] + "R" + C.Fileseparator + sa[1];
        //System.out.println("cZipTifFilePath, cTifPrefix, cTifPrefixR: " +
        //        cZipTifFilePath + CS + cTifPrefix + CS + cTifPrefixR);
    }
    public static void setNucleiMgr(NucleiMgr nucleiMgr) {
        cNucleiMgr = nucleiMgr;
    }

     public static ImagePlus makeImage(String s) {
        cCurrentImageFile = s;
        //new Throwable().printStackTrace();
        ImagePlus ip = null;
        //iSpecialEffect = null;
        //if (iSpecialEffect != null) iSpecialEffect = null;

        //println("makeImage, cUseZip=" + cUseZip);
        switch(cUseZip) {
        case 0:
        case 3:
            ip = doMakeImageFromTif(s);
            break;
        case 1:
            ip = doMakeImageFromZip(s);
            break;
        default:
            ip = doMakeImageFromZip2(s);
            break;

        }

        if (ip != null) {
            cImageWidth = ip.getWidth();
            cImageHeight = ip.getHeight();
            //System.out.println("***ImageWindow: " + cImageWidth + CS + cImageHeight);
        }
        if (ip == null) return iImgPlus;
        else return ip;
    } 
     
    public String getCurrentImageName(){
	    return cZipTifFilePath +  C.Fileseparator +cTifPrefix + iAceTree.makeImageName();
    }

    public static ImagePlus makeImage2(String s, int iplane, int ustack) {
    	System.out.println("ImageWindow.makeImage2: "+s);
        cCurrentImageFile = s;
        ImagePlus ip = null;

        imagewindowPlaneNumber = iplane;
        imagewindowUseStack = ustack;
        switch(cUseZip) {
            case 0:
            case 3:
               ip = doMakeImageFromTif(s);
               break;
            case 1:
               ip = doMakeImageFromZip(s);
               break;
            default:
               ip = doMakeImageFromZip2(s);
               break;
        }

        if (ip != null) {
            cImageWidth = ip.getWidth();
            cImageHeight = ip.getHeight();
        }

        if (ip == null) {
        	return iImgPlus;
        }
        else {
        	return ip;
        }
    }
        


    public static ImagePlus doMakeImageFromZip(String s) {
        //System.out.println("ImageWindow.doMakeImageFromZip entered: " + s);
        if (cZipImage == null) cZipImage = new ZipImage(cZipTifFilePath);
        ZipEntry ze = cZipImage.getZipEntry(s);
        ImagePlus ip;
        if (ze == null) {
            ip = new ImagePlus();
            ImageProcessor iproc = new ColorProcessor(cImageWidth, cImageHeight);
            ip.setProcessor(s, iproc);
        }
        else ip = cZipImage.readData(ze);
        //System.out.println("ImageWindow.makeImage exiting");
        return ip;
    }



    public static ImagePlus doMakeImageFromZip2(String s) {
        cZipImage = new ZipImage(cZipTifFilePath + "/" + s);
        int k1 = s.indexOf("/") + 1;
        String ss = s.substring(k1);
        int k2 = ss.indexOf(".");
        ss = ss.substring(0, k2);
        ZipEntry ze = null;
        if (cZipImage != null) ze = cZipImage.getZipEntry(ss + ".tif");
        //System.out.println("ZipEntry: " + ze);
        //if (cZipImage == null) cZipImage = new ZipImage(cZipTifFilePath);
        //ZipEntry ze = cZipImage.getZipEntry(s);
        ImagePlus ip;
        if (ze == null) {
            ip = new ImagePlus();
            ImageProcessor iproc = new ColorProcessor(cImageWidth, cImageHeight);
            ip.setProcessor(s, iproc);
        }
        else ip = cZipImage.readData(ze);
        //System.out.println("ImageWindow.makeImage exiting");
        //ip = convertToRGB(ip);
        ColorProcessor iprocColor = (ColorProcessor)ip.getProcessor();
        int [] all = (int [])iprocColor.getPixels();
        byte [] R = new byte[all.length];
        byte [] G = new byte[all.length];
        byte [] B = new byte[all.length];
        //ColorProcessor iproc3 = new ColorProcessor(iproc.getWidth(), iproc.getHeight());
        iprocColor.getRGB(R, G, B);
        //G = bpix;
        //R = getRedChannel(R);
        iRpix = R;
        iGpix = G;
        iBpix = B;
        return ip;
    }

    private static void showError(String fileName) {
	new Throwable().printStackTrace();
        String message = "Exiting: cannot find image\n";
        message += fileName;
        JOptionPane pane = new JOptionPane(message);
        JDialog dialog = pane.createDialog(null, "Error");
        dialog.setVisible(true);
    }


    public static ImagePlus doMakeImageFromTif(String s) {
		if (cUseZip == 3)
			s = s.replaceAll("tif", "jpg");
        println("ImageWindow.doMakeImageFromTif entered: " + s);

        cCurrentImagePart = s;
        //FileInputStream fis;
        ImagePlus ip = null;
        String ss = cZipTifFilePath + C.Fileseparator + s;
        println("ImageWindow.makeImage entered: " + ss);
        
        //System.out.println("ImageWindow using stack: "+imagewindowUseStack);
	    if (imagewindowUseStack == 1){
	    	//System.out.println("ImageWindow doMakeImageFromTif using stack: 1");
	    	try {
	    		ip = new Opener().openImage(ss, imagewindowPlaneNumber);
	    		if (setOriginalContrastValues){
	    			// Set contrast values from original image
	    			int ipmin = (int)(ip.getDisplayRangeMin());
	    			int ipmax = (int)(ip.getDisplayRangeMax());
	                System.out.println("ImageWindow set Red min, max from image: "+ipmin+CS+ipmax);
	                ImageWindow.contrastmin1 = ipmin;
	                ImageWindow.contrastmax1 = ipmax;
	                ImageWindow.contrastmin2 = ipmin;
	                ImageWindow.contrastmax2 = ipmax;
	                setOriginalContrastValues = false;
	    		}
	    	} catch (IllegalArgumentException iae) {
	    		System.out.println("Exception in making image from Image3DOverlayGenerator.loadImage()");
            	System.out.println("TIFF file required.");
	    	}

	    } else{
	    	//System.out.println("ImageWindow doMakeImageFromTif using stack: 0");
	    	ip = new Opener().openImage(ss);
	    }
   
        if (ip != null) {
            cImageWidth = ip.getWidth();
            cImageHeight = ip.getHeight();
            ip = convertToRGB(ip);
        } else {
            ip = new ImagePlus();
            ImageProcessor iproc = new ColorProcessor(cImageWidth, cImageHeight);
            ip.setProcessor(s, iproc);
        }

        return ip;
    }

    @SuppressWarnings("unused")
	public static ImagePlus readData(FileInputStream fis, boolean bogus) {
        if (fis == null)
        	return null;
        int byteCount;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        try {
            InputStream is = (InputStream)fis;
            byte data[] = new byte[DATA_BLOCK_SIZE];

            //  4. read source zipped data and write to uncompressed stream
            while ( (byteCount = is.read(data, 0, DATA_BLOCK_SIZE)) != -1) {
                out.write(data, 0, byteCount);
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return openTiff(new ByteArrayInputStream(out.toByteArray()), true);

    }

    public static ImagePlus readData(FileInputStream fis) {
        if (fis == null) return null;
        byte [] ba = readByteArray(fis);
        return openTiff(new ByteArrayInputStream(ba), true);
    }

    @SuppressWarnings("unused")
	public static byte[] readByteArray(FileInputStream fis) {
        if (fis == null) return null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int byteCount;
        byte[] buf = new byte[4096];
        try {
            InputStream is = (InputStream)fis;
            byte data[] = new byte[DATA_BLOCK_SIZE];

            //  4. read source zipped data and write to uncompressed stream
            while ( (byteCount = is.read(data, 0, DATA_BLOCK_SIZE)) != -1) {
                out.write(data, 0, byteCount);
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return out.toByteArray();

    }
 

    /** Attempts to open the specified inputStream as a
    TIFF, returning an ImagePlus object if successful. */
    public static ImagePlus openTiff(InputStream in, boolean convertToRGB) {
        //System.out.println("openTiff entered");
        if (in == null) return null;
        FileInfo[] info = null;
        try {
            TiffDecoder td = new TiffDecoder(in, null);
            info = td.getTiffInfo();
        } catch (FileNotFoundException e) {
            IJ.error("TiffDecoder", "File not found: "+e.getMessage());
            return null;
        } catch (Exception e) {
            IJ.error("TiffDecoder", ""+e);
            return null;
        }
        ImagePlus imp = null;
        if (IJ.debugMode) // dump tiff tags
            IJ.log(info[0].info);
        FileOpener fo = new FileOpener(info[0]);
        imp = fo.open(false);
        // detect 8 bit or RGB from the FileInfo object info[0]
	//  if (info[0].getBytesPerPixel() == 1 && convertToRGB) {
	//      imp = convertToRGB(imp);
	//  }
        //IJ.showStatus("");
        return imp;
    }

   /**
       takes side by side tiff slice and returns half of it specified by
       channel in the origian ImagePlus object
     **/
    public static ImagePlus splitImage(ImagePlus ip, int channel){
	ImageProcessor iproc = ip.getProcessor();
	iproc.flipHorizontal();
	if (channel==2)
	    iproc.setRoi(new Rectangle(ip.getWidth() / 2,0,ip.getWidth()/2,ip.getHeight()));
	else
	     iproc.setRoi(new Rectangle(0,0,ip.getWidth() / 2,ip.getHeight()));
	ImageProcessor cropped=iproc.crop();
	ip.setProcessor(cropped);
	return ip;
	
    }


    /**
     * If the images in the zip archive are 8 bit tiffs,
     * we use that as the green plane of an RGB image processor
     * so the program is always showing RGB images
     *
     * @param ip an Image processor obtained from the image file
     * @return
     */
    @SuppressWarnings("unused")
	private static ImagePlus convertToRGB(ImagePlus ip) {
    	System.out.println("Image width, height: "+ip.getWidth()+CS+ip.getHeight());
        //System.out.println("convertToRGB entered");
    	// this is where ted put code for splitting which need to test

		if(imagewindowUseStack == 1) {
			FileInfo fi = new FileInfo();
	    	fi = ip.getFileInfo();
			//need this		
			
			ImageProcessor iproc = ip.getProcessor();
			
			iproc.flipHorizontal();
			//ImageProcessor iproc2 = new ImageProcessor(ip.getProcessor());
			iproc.setRoi(new Rectangle(ip.getWidth()/2, 0, ip.getWidth()/2, ip.getHeight()));
			ImageProcessor cropped = iproc.crop();
			iproc.setRoi(new Rectangle(0, 0, ip.getWidth()/2, ip.getHeight()));
			ImageProcessor cropped2 = iproc.crop();
			
			// Crop 16-bit color values to 8-bit
		    // 20, 235 hardcorded values for 8-bit scale
			//System.out.println("Green channel: "+contrastmin1+CS+contrastmax1);
			//System.out.println("Red channel: "+contrastmin2+CS+contrastmax2);
			cropped = convertTo8Bits(cropped, (float)contrastmin1, (float)contrastmax1, 20, 235);
			cropped2 = convertTo8Bits(cropped2, (float)contrastmin2, (float)contrastmax2, 20, 235);
			
			byte [] G = (byte [])cropped2.getPixels();   
			byte [] R =(byte [])cropped.getPixels();
			byte [] B = new byte[G.length]; 
			iRpix = R;
			iGpix = G;
			iBpix = B;
			//ImageProcessor iproc = ip.getProcessor();
			ColorProcessor iproc3 = new ColorProcessor(cropped.getWidth(), cropped.getHeight());
	
		    iproc3.setRGB(iRpix, iGpix, iBpix);
	        ip.setProcessor("test", iproc3);

	        return ip;
	    } else {
	    	//original version
    		FileInfo fi = new FileInfo();
    		fi = ip.getFileInfo();
    		if (fi.getBytesPerPixel() != 8) {
    			ip = convertTo8Bits(ip);
    		}
	            
	        ImageProcessor iproc = ip.getProcessor();
	        byte [] bpix = (byte [])iproc.getPixels();
	        byte [] R = new byte[bpix.length];
	        byte [] G = new byte[bpix.length];
	        byte [] B = new byte[bpix.length];
	        ColorProcessor iproc3 = new ColorProcessor(iproc.getWidth(), iproc.getHeight());
	        iproc3.getRGB(R, G, B);
	        // special test removal
	        G = bpix;
	        R = getRedChannel(R);
	        // end special
	        iRpix = R;
	        iGpix = G;
	        iBpix = B;
	        return buildImagePlus(ip);
		}
	 }
    private static ImageProcessor convertTo8Bits(ImageProcessor ip, float tone, float ttwo, float toneb, float ttwob) {
	    // if already byte image do nothing
	    if(ip instanceof ByteProcessor) 
	    	return ip;
	
	    short [] pixels = (short[])ip.getPixels();
	    byte [] bpixels = new byte[pixels.length];
	 
	    double minval = 9000000;
	    double maxval = 0;
	    for (int y = 0; y<pixels.length; y++) {  
	       // Editing pixel at x,y position
	       short p = pixels[y];
	       if (p > maxval) 
	    	   maxval=p;
	       if (p < minval) 
	    	   minval=p;
	    }
	    //System.out.println("Min/max of imageprocessor: "+minval+", "+maxval);

	    for (int y=0; y<pixels.length; y++) {
	       //works
	       //  double val=((double)(pixels[y])-tone)/(ttwo-tone)*255;
	       short p=pixels[y];
	       double val=0;
	       if(p>=ttwo)
	    	   val=(p/maxval)*(255-ttwob)+ttwob;
	       if(p<=tone) 	
	    	   val=(p/tone*toneb);	
	       if(p>tone && p<ttwo) 	
	    	   val=(p-tone)/(ttwo-tone)*ttwob;
	       if (val>255) 
	    	   val=255;
	       if (val<0) 
	    	   val=0;
	       bpixels[y]=(byte)val;
	   } 
	   //System.out.println("min "+minval+" max "+maxval+"\n"); 
	   ip = new ByteProcessor(ip.getWidth(),ip.getHeight());
	   ip.setPixels(bpixels);
	   return ip;
    }

    @SuppressWarnings("unused")
	private static ImagePlus convertTo8Bits(ImagePlus img) {
	ImageProcessor ip = img.getProcessor();
	if (ip instanceof ColorProcessor) {
	    MedianCut mc = new MedianCut((int[])ip.getPixels(), ip.getWidth(), ip.getHeight());
	    img.setProcessor(null, mc.convertToByte(256));
	} else {
	    // if already byte image do nothing
	    if(ip instanceof ByteProcessor) return img;
	
	    //else scale to byte range and copy to byte array
	    short [] pixels =(short[])ip.getPixels();
	    byte []bpixels=new byte[pixels.length];
	    float tone = 1400;
	    float toneb = 20;
	    float ttwo = 3000;//%this was 2600 in current wrong
	    float ttwob = 235;
	    double minval=9000000;
	    double maxval=0;
	    
        for (int y=0; y<pixels.length; y++) {  
		
		  // Editing pixel at x,y position
		  short p=pixels[y];
		  if (p>maxval) maxval=p;
	      if (p<minval) minval=p;

	      double val=((double)(pixels[y])-tone)/(ttwo-tone)*255;
		  if (val>255) val=255;
		      bpixels[y]=(byte)val;
	    }  
	    
        System.out.println("min "+minval+" max "+maxval+"\n"); 
	    ip=new ByteProcessor(ip.getWidth(),ip.getHeight());
	    ip.setPixels(bpixels);
	    
	    //	ip = ip.convertToByte(true);
	    img.setProcessor(null, ip);
	}
	return img;
    }


    private static ImagePlus buildImagePlus(ImagePlus ip) {
        ImageProcessor iproc = ip.getProcessor();
        ColorProcessor iproc3 = new ColorProcessor(iproc.getWidth(), iproc.getHeight());
        iproc3.setRGB(iRpix, iGpix, iBpix);
        ip.setProcessor("test", iproc3);
        return ip;

    }

    protected static ImagePlus makeRedImagePlus(ImagePlus ip) {
        ImageProcessor iproc = ip.getProcessor();
        ColorProcessor iproc3 = new ColorProcessor(iproc.getWidth(), iproc.getHeight());
        iproc3.setRGB(iRpix, new byte[iRpix.length], new byte[iRpix.length]);
        ip.setProcessor("test", iproc3);
        return ip;
    }

    protected static ImagePlus makeGreenImagePlus(ImagePlus ip) {
        ImageProcessor iproc = ip.getProcessor();
        //System.out.println("makeGreenImagePlus: " + iproc);
        ColorProcessor iproc3 = new ColorProcessor(iproc.getWidth(), iproc.getHeight());
        //System.out.println("makeGreenImagePlus2: " + iproc + CS + iGpix  + CS + iRpix);
        iproc3.setRGB(new byte[iRpix.length], iGpix, new byte[iRpix.length]);
        ip.setProcessor("test", iproc3);
        return ip;
    }

    protected static ImagePlus makePlainImagePlus(ImagePlus ip) {
        ImageProcessor iproc = ip.getProcessor();
        ColorProcessor iproc3 = new ColorProcessor(iproc.getWidth(), iproc.getHeight());
        if (cAcbTree) {
            //byte [] added = new byte[iRpix.length];
            //for (int i=0; i < iRpix.length; i++) {
             //   added[i] = (byte)(iRpix[i] + iGpix[i]);
            //}
            iproc3.setRGB(iRpix, iRpix, iRpix);
        } else {
            iproc3.setRGB(new byte[iRpix.length], new byte[iRpix.length], new byte[iRpix.length]);

        }
        ip.setProcessor("test", iproc3);
        return ip;
    }



    @SuppressWarnings("unused")
	private static byte[] getRedChannel(byte [] R) {
        String fileName = makeRedChannelName();
        //System.out.println("getRedChannel: " + fileName);
        File f = new File(fileName);
        if (f.exists()) {
            FileInputStream fis;
            ImagePlus ip = null;
           
	    	if (imagewindowUseStack==1){
	    ip = new Opener().openImage(fileName,imagewindowPlaneNumber);
	}else{
	    ip = new Opener().openImage(fileName);
	}
		FileInfo fi = new FileInfo();
    		fi = ip.getFileInfo();		    		
    	    if (fi.getBytesPerPixel() != 8)
    	    {
    	        ip = convertTo8Bits(ip);
    	    }

            if (ip != null) {
                ByteProcessor bproc = (ByteProcessor)ip.getProcessor();
                R = (byte [])bproc.getPixels();
            } else {
                System.out.println("getRedChannel, Opener returned null ip");
            }
        } else {
            //System.out.println("getRedChannel, file does not exist");

        }
        return R;

    }

    private static String makeRedChannelName() {
        // 20071108 rehacked this because windows vista was very picky
        // and backslashes were plagueing me
        // the green parsing was working so I created cCurrentImagePart
        // to go from there to red by substituting "tifR" for "tif"
        String s = cCurrentImageFile;
        //int k = s.indexOf(cTifPrefix) + cTifPrefix.length();
        String ss = cCurrentImagePart;
        //System.out.println("getRedChannelName, " + ss);
        ss = ss.substring(3);
        //System.out.println("getRedChannelName, " + ss);
        //s = cZipTifFilePath + C.Fileseparator + cTifPrefixR + s.substring(k);
        s = cZipTifFilePath + C.Fileseparator + "/tifR/" + ss;
        if (cUseZip == 3) s = cZipTifFilePath + C.Fileseparator + "/jpgR/" + ss;

        return s;
    }

    // end of static stuff

    public ImageWindow() {

    }

    /**
     * this constructor for test purposes only
     */
    public ImageWindow(String title, ImagePlus imgPlus, boolean test) {
        super(title);
        iTitle = title;
        iImgPlus = imgPlus;
        ImageCanvas ic = new ImageCanvas(imgPlus);
        iImgCanvas = ic;
        getContentPane().add(ic);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public class ColorSchemeDisplayProperty {
        public String iName;
        public int    iLineageNum;

        public ColorSchemeDisplayProperty(String name, int lineageNum) {
            iName = name;
            iLineageNum = lineageNum;
        }
    }

    public class ColorSchemeUI {
        public JPanel       iPanel;
        public JTextField   iTF;
        public JComboBox    iCB;
        public JLabel       iLabel;

        public ColorSchemeUI(int i) {
            iPanel = new JPanel();
            iPanel.setLayout(new GridLayout(1,2));
            iTF = new JTextField(iDispProps[i].iName, WIDTH);
            iLabel = new JLabel(iDispProps[i].iName);
            String [] list;
            list = COLORS;
            if (i == 5) 
            	list = SIZES;
            if (i == 6) 
            	list = SHAPES;
            iCB = new JComboBox(list);
            iCB.setSelectedIndex(iDispProps[i].iLineageNum);
            //iPanel.add(iTF);
            iPanel.add(iLabel);
            iPanel.add(iCB);
            iPanel.setMaximumSize(new Dimension(200,10));
        }
        private String [] COLORS = {
                "red"
                ,"blue"
                ,"green"
                ,"yellow"
                ,"cyan"
                ,"magenta"
                ,"pink"
                ,"gray"
                ,"white"

        };

	private String[] SHAPES = { "circle", "dot" };

	// increase line sizes to choos from when determining circle and dot line thickness
        private String [] SIZES = {"1", "2", "3", "4", "5","6","7","8","9","10"};

    }


    public class PropertiesTab implements ActionListener {
        JPanel                          iPanel;
        //SublineageDisplayProperty []    iDispProps;
        ColorSchemeUI []                 iCSUI;

        @SuppressWarnings("unused")
		public PropertiesTab() {
            Border blackline = BorderFactory.createLineBorder(Color.black);
            iDispProps = getDisplayProps();
            iCSUI = new ColorSchemeUI[iDispProps.length];
            iPanel = new JPanel();
            iPanel.setLayout(new BorderLayout());
            iPanel.setBorder(blackline);
            //iPanel.setLayout(new BoxLayout(iPanel, BoxLayout.PAGE_AXIS));
            JPanel lineagePanel = new JPanel();
            JPanel dummyPanel = new JPanel();
            JPanel topPart = new JPanel();
            topPart.setLayout(new GridLayout(1,2));
            lineagePanel.setLayout(new GridLayout(0,1));
            lineagePanel.setBorder(blackline);
            //lineagePanel.setMaximumSize(new Dimension(300,400));
            topPart.add(lineagePanel);
            topPart.add(dummyPanel);
            JPanel [] testPanel = new JPanel[iDispProps.length];
            JTextField textField;
            JComboBox cb;
            JPanel labelPanel = new JPanel();
            JLabel sublineage = new JLabel("item");
            JLabel color = new JLabel("color");
            labelPanel.setLayout(new GridLayout(1,2));
            labelPanel.add(sublineage);
            labelPanel.add(color);
            lineagePanel.add(labelPanel);

            for (int i=0; i < iDispProps.length; i++) {
                iCSUI[i] = new ColorSchemeUI(i);
                lineagePanel.add(iCSUI[i].iPanel);
            }
            lineagePanel.setMaximumSize(new Dimension(200, 200));
            iPanel.add(topPart, BorderLayout.NORTH);
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1,3));
            //buttonPanel.setMinimumSize(new Dimension(400, 100));

            JButton reset = new JButton("Reset");
            JButton apply = new JButton("Apply");
            JButton cancel = new JButton("Cancel");
            buttonPanel.add(apply);
            reset.addActionListener(this);
            apply.addActionListener(this);
            cancel.addActionListener(this);
            buttonPanel.add(reset);
            buttonPanel.add(apply);
            buttonPanel.add(cancel);
            JPanel botPart = new JPanel();
            botPart.setLayout(new GridLayout(5,1));
            botPart.add(new JPanel());
            botPart.add(buttonPanel);
            botPart.add(new JPanel());
            botPart.add(new JPanel());
            botPart.add(new JPanel());
            iPanel.add(botPart, BorderLayout.CENTER);

            //iPanel.add(buttonPanel, BorderLayout.CENTER);
            //iPanel.add(new JPanel(), BorderLayout.CENTER);

        }

        // I do not think that this action listener is being used -DT
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("Reset")) {
                iDispProps = getDisplayProps();
                for (int i=0; i < iDispProps.length; i++) {
                    iCSUI[i].iLabel.setText(iDispProps[i].iName);
                    iCSUI[i].iCB.setSelectedIndex(iDispProps[i].iLineageNum);
                }


            } else if (command.equals("Apply")) {
                for (int i=0; i < iDispProps.length; i++) {
                    String name = iCSUI[i].iTF.getText();
                    if (name.length() == 0) 
                    	name = "-";
                    int num = iCSUI[i].iCB.getSelectedIndex();
                    iDispProps[i].iName = name;
                    iDispProps[i].iLineageNum = num;
                }

            }
        }

        public JPanel getPanel() {
            return iPanel;
        }

        private String [] COLORS = {
            "red"
            ,"blue"
            ,"green"
            ,"yellow"
            ,"cyan"
            ,"magenta"
            ,"pink"
            ,"gray"
            ,"white"
        };

        private String [] SIZES = {"1", "2", "3"};

        private static final int WIDTH = 15;

    }
    // END class ColorSchemeUI

    public ColorSchemeDisplayProperty [] getDisplayProps() {
        ColorSchemeDisplayProperty [] dispProps = {
		    new ColorSchemeDisplayProperty("normal centroid", 1)
		    ,new ColorSchemeDisplayProperty("selected centroid", 8)
		    ,new ColorSchemeDisplayProperty("annotations", 8)
		    ,new ColorSchemeDisplayProperty("upper sister", 4)
		    ,new ColorSchemeDisplayProperty("lower sister", 5)
		    ,new ColorSchemeDisplayProperty("line size" , 0)
		    ,new ColorSchemeDisplayProperty("nucleus marker", 0)
		    ,new ColorSchemeDisplayProperty("bookmarked centroid", 1)
        };
        return dispProps;
    }

    private int getLineageNumber(String name) {
        int num = iDispProps.length;
        for (int i=0; i < iDispProps.length; i++) {
            if (name.indexOf(iDispProps[i].iName) >= 0) {
                num = iDispProps[i].iLineageNum;
                break;
            }
        }
        return num;
    }

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        //JMenu menu = new JMenu(FILE);
        //menuBar.add(menu);
        //JMenuItem test = new JMenuItem(SAVEAS);
        //menu.add(test);
        //test.addActionListener(this);
        JMenu menu = null;
        JMenuItem test = null;

        menu = new JMenu("dummy");
        menuBar.add(menu);
        test = new JMenuItem("dummy");
        menu.add(test);
        return menuBar;

    }

    public void setAceTree(AceTree aceTree) {
        iAceTree = aceTree;
    }

    public AceTree getAceTree() {
        return iAceTree;
    }

    public ImagePlus refreshDisplay(String imageName) {
    	//println("refreshDisplay, ");
        if (imageName == null)
        	imageName = iTitle;
        else {
            if (imageName.indexOf(cTifPrefix) == -1) {
                imageName = cTifPrefix +imageName;
            }
            iTitle = imageName;
            //setTitle(iTitle);
            setTitle(imageName.substring(4,imageName.length()));
        }
        System.out.println("ImageWindow.refreshDisplay2: " + imageName);
        if (iIsMainImgWindow) {
            iTimeInc = iAceTree.getTimeInc();
            iPlaneInc = iAceTree.getPlaneInc();
            iImageTime = iAceTree.getImageTime();
            iImagePlane = iAceTree.getImagePlane();
            imagewindowPlaneNumber = iAceTree.getImagePlane()+iPlaneInc;
        } else {
            iTimeInc = 0;
            iPlaneInc = 0;
            setImageTimeAndPlaneFromTitle();
        }
        
        imagewindowUseStack = iAceTree.getUseStack();
        // Append plane number to ImageWindow title in stack mode
        if (imagewindowUseStack == 1) {
        	setTitle(imageName.substring(4,imageName.length()) + " (plane "+imagewindowPlaneNumber+")");
        }
        
        String random = RANDOMT;
        if (cUseZip > 0)
        	random = RANDOMF;
        int k = imageName.indexOf(random);
        if (k > -1)
        	imageName = imageName.substring(0, k + random.length() - 1 );
        ImagePlus ip = null;

        //System.out.println("ImageWindow.refreshDisplay3: " + System.currentTimeMillis());
        //new IOException().printStackTrace();
        //System.out.println("Refresh Display "+imagewindowPlaneNumber+" "+iImagePlane+" "+iAceTree.getImagePlane()+" "+iPlaneInc);

        ip = makeImage(imageName);
        currentImage = ip;

        if (ip == null) {
            iAceTree.pausePlayerControl();
            System.out.println("no ImagePlus for: " + iTitle);
        }

        if (iAceTree == null) 
        	return null;

        switch (iAceTree.getColor()) {
            case 1:
                ip = makeGreenImagePlus(ip);
                break;
            case 2:
                ip = makeRedImagePlus(ip);
                break;
            case 3:
                ip = makePlainImagePlus(ip);
                break;
            default:
        }
        //ip = makeGreenImagePlus(ip);
        // Set contrast values for red/green channels
        if (ip != null && imagewindowUseStack != 1) {
        	// red channel
        	ip.setDisplayRange((double)contrastmin1, (double)contrastmax1, 4);
        	// green channel
        	ip.setDisplayRange((double)contrastmin2, (double)contrastmax2, 2);
        }

        if (ip != null) 
        	iImgPlus.setProcessor(imageName, ip.getProcessor());
        if (iIsMainImgWindow && iAceTree.isTracking()) 
        	iAceTree.addMainAnnotation();
        if (iAceTree.getShowCentroids())
        	showCentroids();
        //System.out.println("AceTree show annotations: "+iAceTree.getShowAnnotations());
        if (iAceTree.getShowAnnotations())
        	showAnnotations();
        if (iSpecialEffect != null)
        	showSpecialEffect();
        
        //iSpecialEffect = null;
        iImgCanvas.repaint();
        
		if(iImageZoomerPanel!=null){
			BufferedImage image = BufferedImageCreator.create((ColorProcessor)iImgPlus.getProcessor());
			iImageZoomerPanel.updateImage(image);
		}
		if (iImageZoomerFrame != null) {
	    	BufferedImage image = BufferedImageCreator.create((ColorProcessor)iImgPlus.getProcessor());
	    	iImageZoomerFrame.updateImage(image);
	    }
    	
	    return iImgPlus;
    }

    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        int mods = e.getModifiers();
        boolean shift = (mods & InputEvent.SHIFT_MASK) == InputEvent.SHIFT_MASK;
        boolean ctrl = (mods & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK;
        println("ImageWindow.keyPressed, " + code + CS + shift + CS + ctrl + CS + e);
        if (shift || ctrl) sendToEIDialog2(code, shift, ctrl);
        else {
        switch(code) {
            case KeyEvent.VK_UP:
                iAceTree.actionPerformed(new ActionEvent(this, 0, AceTree.UP));
                break;
            case KeyEvent.VK_DOWN:
                iAceTree.actionPerformed(new ActionEvent(this, 0, AceTree.DOWN));
                break;
            case KeyEvent.VK_LEFT:
                iAceTree.actionPerformed(new ActionEvent(this, 0, AceTree.PREV));
                break;
            case KeyEvent.VK_RIGHT:
                iAceTree.actionPerformed(new ActionEvent(this, 0, AceTree.NEXTT));
                break;
            case KeyEvent.VK_F2:
                iAceTree.actionPerformed(new ActionEvent(this, 0, "F2"));
                break;
            default:
                return;

        }
        }
    }

    @SuppressWarnings("unused")
	private void sendToEIDialog2(int keycode, boolean alt, boolean ctrl) {
    	println("sendToEIDialog2, ");
    	ActionEvent a = null;
        switch(keycode) {
	        case KeyEvent.VK_UP:
	            iAceTree.actionPerformed(new ActionEvent(this, 0, AceTree.UP));
	            break;
	        case KeyEvent.VK_DOWN:
	            iAceTree.actionPerformed(new ActionEvent(this, 0, AceTree.DOWN));
	            break;
	        case KeyEvent.VK_LEFT:
	        	//if (ctrl) a = new ActionEvent(this, 0, EIDialog2.LEFT);
	        	//else a = new ActionEvent(this, 0, EIDialog2.BIG);
	        	AddOneDialog addOne = iAceTree.iAddOneDialog;
	            if (addOne != null) addOne.actionPerformed(new ActionEvent(this, 0, AddOneDialog.LEFT));
	            break;
	        case KeyEvent.VK_RIGHT:
	            iAceTree.actionPerformed(new ActionEvent(this, 0, AceTree.NEXTT));
	            break;
	        case KeyEvent.VK_F2:
	            iAceTree.actionPerformed(new ActionEvent(this, 0, "F2"));
	            break;
	        default:
	            return;
        }
    }

    public void quickRefresh() {
        iImgCanvas.repaint();
    }

    public void setSpecialEffect(Object [] specialEffect) {
        iSpecialEffect = specialEffect;
    }

    protected void showSpecialEffect() {
        if (!iAceTree.isTracking()) 
        	return;
        int x1 = ((Integer)iSpecialEffect[0]).intValue();
        int y1 = ((Integer)iSpecialEffect[1]).intValue();
        int z1 = ((Integer)iSpecialEffect[2]).intValue();
        int x2 = ((Integer)iSpecialEffect[3]).intValue();
        int y2 = ((Integer)iSpecialEffect[4]).intValue();
        int r2 = ((Integer)iSpecialEffect[5]).intValue();
        int z2 = ((Integer)iSpecialEffect[6]).intValue();
        String s = (String)iSpecialEffect[7];
        int offset = r2 + 4;
        if (y2 < y1) offset = -offset;


        ImageProcessor iproc = getImagePlus().getProcessor();
        //iproc.setColor(Color.magenta);
        iproc.setColor(COLOR[iDispProps[LOWERSIS].iLineageNum]);
        if (z2 <= z1) 
        	iproc.setColor(COLOR[iDispProps[UPPERSIS].iLineageNum]);
        //if (z2 <= z1) iproc.setColor(Color.cyan);

        iproc.setLineWidth(cLineWidth);
        //iproc.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        iproc.drawLine(x1, y1, x2, y2);
        iproc.drawPolygon(EUtils.pCircle((int)x2, (int)y2, (int)r2));
        iproc.drawString("    " + s + "(" + z2 + ")", x2, y2 + offset);
    }

    private void redrawMe() {
        iImgCanvas.repaint();
    }

    protected void setImageTimeAndPlaneFromTitle() {
        int k = iTitle.lastIndexOf(DASHT) + DASHT.length();
        if (k <= 1) {
            iImageTime = 1;
            iImagePlane = 15;
            iTimeInc = 0;
            iPlaneInc = 0;
            String random = RANDOMT;
            if (cUseZip > 0) random = RANDOMF;
            iIsMainImgWindow = iTitle.indexOf(random) == -1;
            return;
        }
        System.out.println("setImage..: " + k);
        String time = iTitle.substring(k, k + 3);
        iImageTime = Integer.parseInt(time);
        String s = iTitle.substring(k);
        k = s.indexOf(DASHP) + DASHP.length();
        String plane = s.substring(k, k + 2);
        iImagePlane = Integer.parseInt(plane);
        iTimeInc = 0;
        iPlaneInc = 0;
        String random = RANDOMT;
        if (cUseZip > 0) random = RANDOMF;
        iIsMainImgWindow = iTitle.indexOf(random) == -1;
    }


    public ImageCanvas getCanvas() {
        return iImgCanvas;
    }

    public ImagePlus getImagePlus() {
        return iImgPlus;
    }

    ////////////////////////////////////////

    @SuppressWarnings("unused")
	public void addAnnotation(int mx, int my, boolean dontRemove) {
        if (iIsMainImgWindow) {
            iTimeInc = iAceTree.getTimeInc();
            iImageTime = iAceTree.getImageTime();
            iPlaneInc = iAceTree.getPlaneInc();
        } else {
            iTimeInc = 0;
            iPlaneInc = 0;
        }
        double x, y, r;
        boolean g;
        Nucleus n = cNucleiMgr.findClosestNucleus(mx, my, iImagePlane + iPlaneInc, iImageTime + iTimeInc);
        if (cNucleiMgr.hasCircle(n, (double)(iImagePlane + iPlaneInc))) {
			String propername = iPartsList.lookupSulston(n.identity);
			String label = n.identity;
			if(propername != null){
			    label=label+" "+propername;
			}
	        AnnotInfo ai = new AnnotInfo(label, n.x, n.y);
	        // now, if this one is not in the vector add it
	        // otherwise remove it
	        boolean itemRemoved = false;
	        boolean itemAlreadyPresent = false;
	        String test = label;//n.identity;
	        AnnotInfo aiTest = null;
	        for (int k=0; k < iAnnotsShown.size(); k++) {
	            aiTest =(AnnotInfo)iAnnotsShown.elementAt(k);
	            if (aiTest.iName.equals(test)) {
	                itemAlreadyPresent = true;
	                if (!dontRemove) {
	                    iAnnotsShown.remove(k);
	                    itemRemoved = true;
	                }
	                break;
	            }
	
	        }
	
	        if (!itemRemoved && !itemAlreadyPresent) {
	            iAnnotsShown.add(ai);
	        }
	        // if this was a button 3 mouse click
	        // and this is the main window
	        // we will make this the current cell and makeDisplayText agree
	        if (iIsRightMouseButton && iIsMainImgWindow) {
	            iIsRightMouseButton = false;
	        }
	    }
	}


    public static final int [] WIDTHS = {1,2,3,4,5,6,7,8,9,10};
    
    @SuppressWarnings("unused")
	protected void showCentroids() {
        int time = iImageTime + iTimeInc;
        if (time < 0) {
            iImageTime = 1;
            iTimeInc = 0;
        }
        
        Vector v = (Vector)cNucleiMgr.getElementAt(iImageTime + iTimeInc - 1);
        
        ImageProcessor iproc = getImagePlus().getProcessor();
        iproc.setColor(COLOR[iDispProps[NCENTROID].iLineageNum]);
        iproc.setLineWidth(WIDTHS[iDispProps[LINEWIDTH].iLineageNum]);
        Polygon p = null;
        
        Enumeration e = v.elements();
        Cell currentCell = iAceTree.getCurrentCell();
        while(e.hasMoreElements()) {
            Nucleus n = (Nucleus)e.nextElement();
            if (n.status < 0) 
            	continue;

            double x = cNucleiMgr.nucDiameter(n,
                    (double)(iImagePlane + iPlaneInc));
            if (x > 0) {
            	// Manage bookmarked cells
                if (iBookmarkListModel != null && !iBookmarkListModel.isEmpty()) {
                	String name = n.identity;
                	if (iBookmarkListModel.contains(name))
                		iproc.setColor(COLOR[iDispProps[BMCENTROID].iLineageNum]);
                }
                if (currentCell != null && n.hashKey != null && n.hashKey.equals(currentCell.getHashKey()) && iAceTree.isTracking()) {
                    iproc.setColor(COLOR[iDispProps[SCENTROID].iLineageNum]);
                }
				int TOGGLE_OPTION = 6; // toggle option colorscheme display property is 6th element in iDispProp
				if(iDispProps[TOGGLE_OPTION].iLineageNum == 0) { // don't toggle, default to empty circles
				    iproc.drawPolygon(EUtils.pCircle(n.x, n.y, (int)(x/2.)));
				} else {
				    iproc.drawDot(n.x, n.y);
				}
                iproc.setColor(COLOR[iDispProps[NCENTROID].iLineageNum]);
            }
        }
    }

    @SuppressWarnings("unused")
	private void drawRoi(int plane, Nucleus c, ImageProcessor iproc) {
        double d = cNucleiMgr.nucDiameter(c, plane);
        float fxx = c.x;
        float fyy = c.y;
        fxx -= d/2;
        fyy -= d/2;
        int xx = (int)(fxx + 0.5);
        int yy = (int)(fyy + 0.5);
        int dd = (int)(d + 0.5);

        //int d = (int)(c.d + 0.5);
        //System.out.println("processImage, d=" + d + C.CS + c.d);
        //int xx = c.x - d/2;
        //int yy = c.y - d/2;
        OvalRoi oRoi = new OvalRoi(xx, yy, dd, dd);
        //Color csave = iproc.getColor();
        iproc.setColor(new Color(0, 0, 255));
        oRoi.drawPixels(iproc);
        Rectangle r = oRoi.getBounds();
        int width = iproc.getWidth();
        int offset, i;
        for (int y=r.y; y < (r.y + r.height); y++) {
            offset = y * width;
            for (int x = r.x; x <= (r.x + r.width); x++) {
                i = offset + x;
                if (oRoi.contains(x, y)) {
                    //iproc.drawPixel(x,y);
                    int k = iproc.getPixel(x, y);
                    int m = k & -16711936;
                    //System.out.println("drawRoi: " + k + C.CS + m);
                    //redSum += Math.abs(redPixels[i]);
                }
            }
        }



    }

    @SuppressWarnings("unused")
	protected void showAnnotations() {
        //showWhichAnnotations();
        Vector v = (Vector)cNucleiMgr.getNucleiRecord().elementAt(iImageTime  + iTimeInc - 1);
        int size = v.size();
        int [] x = new int[size];
        int [] y = new int[size];
        Vector annots = new Vector();
        Enumeration e = v.elements();
        while(e.hasMoreElements()) {
            AnnotInfo ai = null;
            Nucleus n = (Nucleus)e.nextElement();
            String propername = iPartsList.lookupSulston(n.identity);
			String label = n.identity;
			//System.out.println("name is "+label+" "+propername);
			if(propername != null){
			    label = label + " " + propername;
			}
         
            if (n.status >= 0 && (isInList(label) != null)) {
                ai = new AnnotInfo(label, n.x, n.y);
                if (cNucleiMgr.hasCircle(n, (double)(iImagePlane + iPlaneInc))) {
                    annots.add(ai);
                }
            }
        }
        drawStrings(annots, this);
        //NucUtils.drawStrings(annots, this);
        //iShow.setText(HIDE);
    }

    private void drawStrings(Vector annots, ImageWindow imgWin) {
        ImagePlus imgPlus = imgWin.getImagePlus();
        ImageProcessor imgProc = imgPlus.getProcessor();
        ImageCanvas imgCan = imgWin.getCanvas();
        //imgProc.setColor(Color.yellow);
        //System.out.println("iDispProps: " + iDispProps);
        imgProc.setColor(COLOR[iDispProps[ANNOTATIONS].iLineageNum]);
        imgProc.setFont(new Font("SansSerif", Font.BOLD, 13));
        Enumeration e = annots.elements();
        while (e.hasMoreElements()) {
            AnnotInfo ai = (AnnotInfo)e.nextElement();
            imgProc.moveTo(imgCan.offScreenX(ai.iX),imgCan.offScreenY(ai.iY));
            
            // If there is a proper name appended, shwo only the proper name
            String name = ai.iName;
            int i = name.indexOf(" ");
            if (i > 0)
            	name = name.substring(i+1, name.length());
            imgProc.drawString(name);
        }
        imgPlus.updateAndDraw();
    }

    private void showWhichAnnotations() {
        for (int i=0; i < iAnnotsShown.size(); i++) {
            System.out.println((AnnotInfo)iAnnotsShown.elementAt(i));

        }

    }

    public void updateCurrentCellAnnotation(Cell newCell, Cell old, int time) {
        //new Throwable().printStackTrace();
        //println("updateCurrentCellAnnotation: " + newCell + CS + old + CS + time);
        AnnotInfo ai = null;
        if (old != null) ai = isInList(old.getName());
        if (ai != null) iAnnotsShown.remove(ai);
        if (time == -1) time = newCell.getTime();
        String s = newCell.getHashKey();
        Nucleus n = null;
        //println("updateCurrentCellAnnotation:2 " + s);
        if (s != null) {
            n = cNucleiMgr.getNucleusFromHashkey(newCell.getHashKey(), time);
            //println("updateCurrentCellAnnotation:3 " + n);
        }
        if ((n != null) && (isInList(newCell.getName()) == null)) {
            ai = new AnnotInfo(newCell.getName(), n.x, n.y);
            iAnnotsShown.add(ai);
        }
    }

    public void clearAnnotations() {
        iAnnotsShown.clear();
    }

    public void addAnnotation(String name, int x, int y) {
        AnnotInfo ai = new AnnotInfo(name, x, y);
        iAnnotsShown.add(ai);
    }

    protected AnnotInfo isInList(String name) {
        //System.out.println("isInList: " + name + CS + iAnnotsShown.size());
        AnnotInfo aiFound = null;
        Enumeration e = iAnnotsShown.elements();
        while(e.hasMoreElements()) {
            AnnotInfo ai = (AnnotInfo)e.nextElement();
            boolean is = ((String)ai.iName).equals(name);
            if (is) {
                aiFound = ai;
                break;
            }
        }
        return aiFound;
    }

    public void saveImageIfEnabled() {
        if (iSaveImage) {
            while(iSaveInProcess);
            new Thread(this).start();
        }
    }

    public void run() {
        iSaveInProcess = true;
        int k = 1000;
        if (iNewConstruction) {
            k = 5000; // long delay needed on new open
            iNewConstruction = false;
        }
        try {
            Thread.sleep(k);
        } catch(InterruptedException ie) {
            ie.printStackTrace();
        }
        saveImage();

    }

	void saveJpeg(BufferedImage bi, String outFileName, int quality) {
		Iterator iter = ImageIO.getImageWritersByFormatName("jpeg");
		javax.imageio.ImageWriter writer = (javax.imageio.ImageWriter)iter.next();
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwp.setCompressionQuality(1);   // an integer between 0 and 1
		//println("otherSaveJpeg, " + iwp.canWriteCompressed() + CS + iwp.getCompressionQuality());
		//outFileName = "jpg" + quality + ".jpg";
		File file = new File(outFileName);
		if (file.exists()) file.delete();
		file = new File(outFileName);
		try {
			FileImageOutputStream output = new FileImageOutputStream(file);
			writer.setOutput(output);
			IIOImage image = new IIOImage(bi, null, null);
			writer.write(null, image, iwp);
			writer.dispose();
			output.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void zoomView() {
        String title = "zoom";
        BufferedImage image = BufferedImageCreator.create((ColorProcessor)iImgPlus.getProcessor());
    	ImageZoomerFrame izf = new ImageZoomerFrame(this, image, 10.0, title);
    	izf.addKeyListener(this); //added to make zoom window respond to key events -AS 11/23/11
    	iImageZoomerFrame = izf;
	}

    @SuppressWarnings("unused")
	public void saveImage() {
        String title = makeTitle();
        if (title == null) {
            cancelSaveOperations();
            return;
        }
        Rectangle screenRect = this.getBounds();
        int topAdjust = 23;
        int y = screenRect.y;
        screenRect.y += topAdjust;
        int height = screenRect.height;
        screenRect.height -= topAdjust;
        // create screen shot
        //File f = new File("ij-ImageIO_.jar");
        //if (!f.exists()) {
        //    println("CANNOT SAVE FILES -- MISSING ij-ImageIO_.jar");
        //    return;
        //}

        Robot robot = null;
        BufferedImage image = null;
        if (iUseRobot) {
            try {
                robot = new Robot();
            } catch(AWTException e) {
                println("EXCEPTION -- NO ROBOT -- NOT SAVING");
                iSaveInProcess = false;
                iSaveImage = false;
                iAceTree.iAceMenuBar.resetSaveState();
                return;
            }
            image = robot.createScreenCapture(screenRect);
        } else {
            image = BufferedImageCreator.create((ColorProcessor)iImgPlus.getProcessor());
        }

        saveJpeg(image, title, 20);
        /*
        try {
            //robot = new Robot();
            //BufferedImage image = robot.createScreenCapture(screenRect);
            //BufferedImage image = BufferedImageCreator.create((ColorProcessor)iImgPlus.getProcessor());
            ImageIO.write(image, "jpeg", new File(title));
            //ImageIO.write(image, "png", new File(title));
        //} catch(AWTException awtex) {
        //    awtex.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        */

        System.out.println("file: " + title + " written");
        iSaveInProcess = false;
    }

    public void cancelSaveOperations() {
        println("WARNING: NO IMAGE SAVE PATH -- NOT SAVING!");
        iSaveInProcess = false;
        iSaveImage = false;
        iAceTree.iAceMenuBar.resetSaveState();
        return;

    }

    public String getSaveImageDirectory() {
        if (iSaveImageDirectory != null) return iSaveImageDirectory;
        try {
            Class.forName("net.sf.ij.jaiio.BufferedImageCreator");
        } catch(ClassNotFoundException e) {
            iUseRobot = true;
            println("USING ROBOT FOR IMAGE2D SAVING");
        }

        try {
            JFileChooser fc = new JFileChooser("");
            fc.setDialogTitle("Save images to: ");
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fc.showOpenDialog(null);
            String path = null;
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                path = fc.getSelectedFile().getPath();
                iSaveImageDirectory = path;
                System.out.println("Saving images to: " + path);
                return path;
            }
            else {
            	System.out.println("Save cancelled by user.");
            	return null;
            }
        } catch (Exception e){
        	System.out.println("Failed to get save image directory.");
        	e.printStackTrace();
        	return null;
        }

    }

    private String makeTitle() {
        if (iSaveImageDirectory == null) {
            String dir = getSaveImageDirectory();
            iSaveImageDirectory = dir;
            if (dir == null) return null;
        }
        String s = iTitle;
        int j = s.lastIndexOf(C.Fileseparator) + 1;
        int k = s.lastIndexOf(".");
        s = s.substring(j, k) + ".jpeg";
        //s = s.substring(j, k) + ".png";
        s = iSaveImageDirectory + "/" + s;
        return s;
    }

    public void setSaveImageState(boolean saveIt) {
        iSaveImage = saveIt;
    }

    public Object [] getSpecialEffect() {
        return iSpecialEffect;
    }

    private class WinEventMgr extends WindowAdapter {
        public void windowGainedFocus(WindowEvent e) {
            //System.out.println("windowGainedFocus, ");
            //refreshDisplay(null);
        	iAceTree.requestFocus();
        }
        
        public void windowClosing(WindowEvent e) {
            //System.out.println("windowClosing: " + iIsMainImgWindow);
            if (iIsMainImgWindow) 
            	dispose();
        }
    }

    class MouseHandler extends MouseInputAdapter {
    	ImageWindow iw;
        public MouseHandler(ImageWindow iw) {
            super();
            this.iw=iw;
        }

        public void mouseMoved(MouseEvent e) {
		    //handle zoom view transform to original coordinate system
	        int x = e.getX();
	    	int y = e.getY();
	    	int x2 = iImageZoomerPanel.transform(x);
	    	//(int)Math.round(x * 100./ (double)m_imagePanel.getZoomedTo());
	    	int y2 = iImageZoomerPanel.transform(y);
	    	// (int)Math.round(y * 100./ (double)m_imagePanel.getZoomedTo());
	    	MouseEvent e2 = new MouseEvent(iw, 0, 0, 0, x2, y2, 0, false, e.getButton());
	    	try {
	    		iAceTree.mouseMoved(e2);
	    	}
	    	catch (NullPointerException npe) {
	    		// If the image is copiedw ith the END hotkey, it will not have a reference to AceTree
	    		// AceTree will be null in this case
	    		return;
	    	}
        }

        public void mouseClicked(MouseEvent e) {
        	int x = e.getX();
	    	int y = e.getY();
	    	int x2 = iImageZoomerPanel.transform(x);
	    	//(int)Math.round(x * 100./ (double)m_imagePanel.getZoomedTo());
	    	int y2 = iImageZoomerPanel.transform(y);
	        	//println("ImageWindow.mouseClicked, " + e.getX() + CS + e.getY());
            int button = e.getButton();
            
            // e.BUTTON3 ie right click -DT
            if (button == e.BUTTON3|e.isControlDown()) {
                iIsRightMouseButton = true;
            } else {
                iIsRightMouseButton = false;
            }
            if (button == e.BUTTON3|e.isControlDown()) {
                Nucleus n = cNucleiMgr.findClosestNucleus(x2,y2, iImagePlane + iPlaneInc, iImageTime + iTimeInc);
                if (n == null) {
                	//System.out.println("No nucleus selected to be active, cannot set current cell.");
                	return;
                }
                Cell c = iAceTree.getCellByName(n.identity);
                if (c != null) {
	                iAceTree.setCurrentCell(c, iImageTime + iTimeInc, AceTree.RIGHTCLICKONIMAGE);
	                //System.out.println("Current cell set to "+n.identity);
                }
            } 
            else if (button == e.BUTTON1){
                //System.out.println("mouseClicked " + e.getX());
                addAnnotation(x2, y2, false);
                refreshDisplay(null);
            }
            
            iAceTree.cellAnnotated(getClickedCellName(x2, y2));
            iAceTree.updateDisplay();
            //if (cEditImage3 != null) cEditImage3.processEditMouseEvent(e);
            MouseEvent e2 = new MouseEvent(iw, 0, 0, 0, x2, y2, 0, false, e.getButton());
            processEditMouseEvent(e2);
        }

    }

    @SuppressWarnings("unused")
	private String getClickedCellName(int x, int y) {
        int timeInc = 0;
        int planeInc = 0;
        if (iIsMainImgWindow) {
            timeInc = iAceTree.getTimeInc();
            planeInc = iAceTree.getPlaneInc();
        }
        String name = "";
        Nucleus n = cNucleiMgr.findClosestNucleus(x, y, iImageTime + iTimeInc);
        if (cNucleiMgr.hasCircle(n, (double)(iImagePlane + iPlaneInc))) {
            name = n.identity;
        }
        return name;
    }

    protected static final String
    RANDOMF = ".zip0"
   ,RANDOMT = ".tif0"
   ,DASHT = "-t"
   ,DASHP = "-p"
   ;

    private static final int
    	GREENCHANNEL = 2,
    	REDCHANNEL = 4,
    	CHANNELMAX = 255,
    	CHANNELMIN = 0
	;
    
    public static final Integer
         ANTERIOR = new Integer(1)
        ,POSTERIOR = new Integer(2)
        ,NONE = new Integer(0)
        ;

    private static final String
         CS = ", "
        ;

    private static final int
    DATA_BLOCK_SIZE  = 2048
   //,LINEWIDTH = 1
   ;

    public static final int
         NCENTROID = 0
        ,SCENTROID = 1
        ,ANNOTATIONS = 2
        ,UPPERSIS = 3
        ,LOWERSIS = 4
        ,LINEWIDTH = 5
        ,BMCENTROID = 7
        ;

    public static final Color [] COLOR = {
        Color.RED
        ,new Color(140,70,255)
        ,Color.GREEN
        ,Color.YELLOW
        ,Color.CYAN
        ,Color.MAGENTA
        ,Color.PINK
        ,Color.LIGHT_GRAY
        ,Color.WHITE
    };


    private static void println(String s) {
    	System.out.println(s);
	}


    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }


    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

	//     private JButton     iAddSeries;
	//     private JButton     iAddOne;
	public JDialog 		iDialog;
	public JDialog 		iDialog2;


    public void launchImageParamsDialog(){
    	new ImageParamsDialog(this);
    }
    
    // Display channels contrast tool for 16-bit to 8-bit images
    public void launchContrastTool() {
    	if (ict == null) {
    		ict = new ImageContrastTool(this, imagewindowUseStack);
    		iSlider1min = ict.getSlider1min();
    		iSlider1max = ict.getSlider1max();
    		iSlider2min = ict.getSlider2min();
    		iSlider2max = ict.getSlider2max();
    		
    		ict.setSlider1min(contrastmin1);
        	ict.setSlider1max(contrastmax1);
        	ict.setSlider2min(contrastmin2);
        	ict.setSlider2max(contrastmax2);
    		
    		SliderListener sl = new SliderListener();
        	iSlider1min.addChangeListener(sl);
        	iSlider1max.addChangeListener(sl);
        	iSlider2min.addChangeListener(sl);
        	iSlider2max.addChangeListener(sl);
    	}
    	else {
    		ict.setSlider1min(contrastmin1);
        	ict.setSlider1max(contrastmax1);
        	ict.setSlider2min(contrastmin2);
        	ict.setSlider2max(contrastmax2);
        	
        	ict.setVisible(true);
    	}
    }
    
    // Change event listener implementation for sliders
    public class SliderListener implements ChangeListener {
    	public void stateChanged(ChangeEvent e) {
    		JSlider source = (JSlider)e.getSource();
    		if (!source.getValueIsAdjusting()) {
    			int min1 = iSlider1min.getValue();
				int max1 = iSlider1max.getValue();
    			if (min1 <= max1) {
    				//System.out.println("ImageWindow set Red min, max: "+min1+CS+max1);
    				contrastmin1 = min1;
	    			contrastmax1 = max1;
    			}
    			int min2 = iSlider2min.getValue();
    			int max2 = iSlider2max.getValue();
    			if (min2 <= max2) {
    				//System.out.println("ImageWindow set Green min, max: "+min2+CS+max2);
	    			contrastmin2 = min2;
	    			contrastmax2 = max2;
    			}
    			refreshDisplay(null);
    		}
    	}
    }

    public void updateCellAnnotation(Cell newCell, String oldName, int time) {
        AnnotInfo ai = isInList(oldName);
        if (ai != null) {
            iAnnotsShown.remove(ai);
            //if (time == -1) time = newCell.getTime();
            Nucleus n = ImageWindow.cNucleiMgr.getNucleusFromHashkey(newCell.getHashKey(), time);
            String name = newCell.getName();
            if (isInList(name) == null) {
            	int space = name.indexOf(" ");
            	if (space >= 0)
            		name = name.substring(space+1, name.length());
                ai = new AnnotInfo(name, n.x, n.y);
                iAnnotsShown.add(ai);
            }
        }
    }

    public void processEditMouseEvent(MouseEvent e) {
		if (iDialog == null & iDialog2==null) 
			return;
		if(iDialog2 !=null){
		    UnifiedNucRelinkDialog relink=(UnifiedNucRelinkDialog)iDialog2;
		    relink.processMouseEvent(e);
		}
		if (iDialog !=null){
			AddOneDialog addOne = (AddOneDialog)iDialog;
			addOne.processMouseEvent(e);
	    }
    }

    public static void setUseStack(int x) {
    	imagewindowUseStack = x;
    }
    
    private static final int MAX8BIT = 255, MAX16BIT = 65535;

}
