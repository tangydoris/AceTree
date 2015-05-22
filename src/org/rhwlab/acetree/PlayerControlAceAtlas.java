/*
 * Created on Apr 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.rhwlab.acetree;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JComponent;
/**
 * @author biowolp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PlayerControlAceAtlas extends PlayerControl {

  
    public PlayerControlAceAtlas(AceTree aceTree) {
	super(aceTree);
    }

    protected void addButtons() {
        iStepBack = makeButton("/images/StepBack16");
	iStepBack.setToolTipText("Step Backwards");
        iToolBar.add(iStepBack);
        iReverse = makeButton("/images/PlayBack16");
	iReverse.setToolTipText("Play Backwards");
        iToolBar.add(iReverse);
        iToolBar.add(new JToolBar.Separator());
        iPause = makeButton("/images/Pause16");
	iPause.setToolTipText("Pause");
        iToolBar.add(iPause);
        iToolBar.add(new JToolBar.Separator());
        iPlay = makeButton("/images/Play16");
	iPlay.setToolTipText("Play");
        iToolBar.add(iPlay);
        iStepForward = makeButton("/images/StepForward16");
	iStepForward.setToolTipText("Step Forward");
        iToolBar.add(iStepForward);
	iToolBar.add(new JToolBar.Separator());


        setEnabledAll(true);
        iPause.setEnabled(false);
    }

    
}
