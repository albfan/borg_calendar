/*
This file is part of BORG.

    BORG is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    BORG is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with BORG; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Copyright 2003 by Mike Berger
 */

package net.sf.borg.ui.address;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.borg.common.Errmsg;
import net.sf.borg.common.IOHelper;
import net.sf.borg.common.PrefName;
import net.sf.borg.common.Resource;
import net.sf.borg.model.AddressModel;
import net.sf.borg.model.AddressVcardAdapter;
import net.sf.borg.model.beans.Address;
import net.sf.borg.ui.DockableView;
import net.sf.borg.ui.MultiView;
import net.sf.borg.ui.ResourceHelper;
import net.sf.borg.ui.util.PopupMenuHelper;
import net.sf.borg.ui.util.StripedTable;
import net.sf.borg.ui.util.TablePrinter;
import net.sf.borg.ui.util.TableSorter;



// the AddrListView displays a list of the current todo items and allows the
// suer to mark them as done
public class AddrListView extends DockableView {

    private Collection addrs_; // list of rows currently displayed

 
    private AddrListView() {

	super();
	addModel(AddressModel.getReference());

	this.setLayout(new GridBagLayout());
	// init the gui components
	initComponents();

	// the todos will be displayed in a sorted table with 2 columns -
	// data and todo text
	jTable1.setModel(new TableSorter(new String[] { Resource.getPlainResourceString("First"),
		Resource.getPlainResourceString("Last"), Resource.getPlainResourceString("Email"),
		Resource.getPlainResourceString("Screen_Name"), Resource.getPlainResourceString("Home_Phone"),
		Resource.getPlainResourceString("Work_Phone"), Resource.getPlainResourceString("Birthday") }, new Class[] {
		java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
		java.lang.String.class, java.lang.String.class, java.util.Date.class }));

	refresh();


    }


    public void refresh() {
	AddressModel addrmod_ = AddressModel.getReference();

	try {
	    addrs_ = addrmod_.getAddresses();
	} catch (Exception e) {
	    Errmsg.errmsg(e);
	    return;
	}

	// init the table to empty
	TableSorter tm = (TableSorter) jTable1.getModel();
	tm.addMouseListenerToHeaderInTable(jTable1);
	tm.setRowCount(0);

	Iterator it = addrs_.iterator();
	while (it.hasNext()) {
	    Address r = (Address) it.next();

	    try {

		// add the table row
		Object[] ro = new Object[7];
		ro[0] = r.getFirstName();
		ro[1] = r.getLastName();
		ro[2] = r.getEmail();
		ro[3] = r.getScreenName();
		ro[4] = r.getHomePhone();
		ro[5] = r.getWorkPhone();
		ro[6] = r.getBirthday();
		tm.addRow(ro);
		tm.tableChanged(new TableModelEvent(tm));
	    } catch (Exception e) {
		Errmsg.errmsg(e);
		return;
	    }

	}

	// sort the table by last name
	tm.sortByColumn(1);

    }

    private void editRow() {
	// figure out which row is selected.
	int index = jTable1.getSelectedRow();
	if (index == -1)
	    return;
	jTable1.getSelectionModel().setSelectionInterval(index, index);
	// ensure only one row is selected.

	try {
	    // need to ask the table for the original (befor sorting) index of
	    // the selected row
	    TableSorter tm = (TableSorter) jTable1.getModel();
	    int k = tm.getMappedIndex(index); // get original index - not
						// current sorted position in
						// tbl
	    Object[] oa = addrs_.toArray();
	    Address addr = (Address) oa[k];
	    
	    MultiView.getMainView().addView(new AddressView(addr));
	} catch (Exception e) {
	    Errmsg.errmsg(e);
	}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private ActionListener alAddNew, alEdit, alDelete;
    private void initComponents()// GEN-BEGIN:initComponents
    {
	java.awt.GridBagConstraints gridBagConstraints;

	jScrollPane1 = new javax.swing.JScrollPane();
	jTable1 = new StripedTable();
	jPanel1 = new javax.swing.JPanel();
	newbutton = new javax.swing.JButton();
	editbutton = new javax.swing.JButton();
	delbutton = new javax.swing.JButton();
	
	

	jScrollPane1.setPreferredSize(new java.awt.Dimension(554, 404));
	jTable1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
	// jTable1.setGridColor(java.awt.Color.blue);
	DefaultListSelectionModel mylsmodel = new DefaultListSelectionModel();
	mylsmodel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	jTable1.setSelectionModel(mylsmodel);
	jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		jTable1MouseClicked(evt);
	    }
	});

	jScrollPane1.setViewportView(jTable1);

	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	gridBagConstraints.weightx = 1.0;
	gridBagConstraints.weighty = 1.0;
	add(jScrollPane1, gridBagConstraints);

	newbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/Add16.gif")));
	ResourceHelper.setText(newbutton, "Add_New");
	newbutton.addActionListener(alAddNew = new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		newbuttonActionPerformed(evt);
	    }
	});

	jPanel1.add(newbutton);

	editbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/Edit16.gif")));
	ResourceHelper.setText(editbutton, "Edit");
	editbutton.addActionListener(alEdit = new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		editbuttonActionPerformed(evt);
	    }
	});

	jPanel1.add(editbutton);

	delbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/Delete16.gif")));
	ResourceHelper.setText(delbutton, "Delete");
	delbutton.addActionListener(alDelete = new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		delbuttonActionPerformed(evt);
	    }
	});

	jPanel1.add(delbutton);


	gridBagConstraints = new java.awt.GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
	add(jPanel1, gridBagConstraints);

	new PopupMenuHelper(jTable1, new PopupMenuHelper.Entry[] { new PopupMenuHelper.Entry(alAddNew, "Add_New"),
		new PopupMenuHelper.Entry(alEdit, "Edit"), new PopupMenuHelper.Entry(alDelete, "Delete"), });

	
    }// GEN-END:initComponents

  

    private void delbuttonActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_delbuttonActionPerformed
    {// GEN-HEADEREND:event_delbuttonActionPerformed
	// figure out which row is selected to be marked as done
	int[] indices = jTable1.getSelectedRows();
	if (indices.length == 0)
	    return;

	int ret = JOptionPane.showConfirmDialog(null, Resource.getResourceString("Delete_Addresses"), Resource
		.getPlainResourceString("Delete"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	if (ret != JOptionPane.OK_OPTION)
	    return;

	AddressModel amod = AddressModel.getReference();
	for (int i = 0; i < indices.length; ++i) {
	    int index = indices[i];
	    try {
		// need to ask the table for the original (befor sorting) index
		// of the selected row
		TableSorter tm = (TableSorter) jTable1.getModel();
		int k = tm.getMappedIndex(index); // get original index - not
						    // current sorted position
						    // in tbl
		Object[] oa = addrs_.toArray();
		Address addr = (Address) oa[k];
		amod.delete(addr, false);
	    } catch (Exception e) {
		Errmsg.errmsg(e);
	    }
	}

	amod.refresh();
    }// GEN-LAST:event_delbuttonActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt)// GEN-FIRST:event_jTable1MouseClicked
    {// GEN-HEADEREND:event_jTable1MouseClicked
	if (evt.getClickCount() < 2)
	    return;
	editRow();
    }// GEN-LAST:event_jTable1MouseClicked

    private void editbuttonActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_editbuttonActionPerformed
    {// GEN-HEADEREND:event_editbuttonActionPerformed
	editRow();
    }// GEN-LAST:event_editbuttonActionPerformed

    private void newbuttonActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_newbuttonActionPerformed
    {// GEN-HEADEREND:event_newbuttonActionPerformed
	Address addr = AddressModel.getReference().newAddress();
	addr.setKey(-1);
	MultiView.getMainView().addView(new AddressView(addr));
    }// GEN-LAST:event_newbuttonActionPerformed

    private void printListActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_printListActionPerformed

	// user has requested a print of the table
	try {
	    TablePrinter.printTable(jTable1);
	} catch (Exception e) {
	    Errmsg.errmsg(e);
	}
    }// GEN-LAST:event_printListActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exitMenuItemActionPerformed
	this.fr_.dispose();
    }// GEN-LAST:event_exitMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton delbutton;

    private javax.swing.JButton editbutton;


    private javax.swing.JPanel jPanel1;

    private javax.swing.JScrollPane jScrollPane1;

    private StripedTable jTable1;

    private javax.swing.JButton newbutton;


 
    private JMenuItem impvcard = null;

    private JMenuItem getImpvcard() {
	if (impvcard == null) {
	    impvcard = new JMenuItem();
	    ResourceHelper.setText(impvcard, "imp_vcard");
	    impvcard.setIcon(new ImageIcon(getClass().getResource("/resource/Import16.gif")));
	    impvcard.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent e) {
		    File file;
		    while (true) {
			// prompt for a file
			JFileChooser chooser = new JFileChooser();

			chooser.setCurrentDirectory(new File("."));
			chooser.setDialogTitle(Resource.getResourceString("choose_file"));
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			int returnVal = chooser.showOpenDialog(null);
			if (returnVal != JFileChooser.APPROVE_OPTION)
			    return;

			String s = chooser.getSelectedFile().getAbsolutePath();
			file = new File(s);

			break;

		    }

		    try {
			FileReader r = new FileReader(file);
			AddressVcardAdapter.importVcard(r);
			r.close();
		    } catch (Exception ex) {
			Errmsg.errmsg(ex);
		    }

		}
	    });
	}
	return impvcard;
    }

    public PrefName getFrameSizePref() {
	return PrefName.ADDRLISTVIEWSIZE;
    }

    public String getFrameTitle() {
	return Resource.getPlainResourceString("Address_Book");
    }

    public JMenuBar getMenuForFrame() {
	
	JMenuBar menuBar = new javax.swing.JMenuBar();
	JMenu fileMenu = new javax.swing.JMenu();
	JMenuItem printList = new javax.swing.JMenuItem();
	JMenuItem exitMenuItem = new javax.swing.JMenuItem();
	JMenuItem htmlitem = new JMenuItem();
	ResourceHelper.setText(fileMenu, "Action");

	JMenuItem mnuitm = new JMenuItem();
	ResourceHelper.setText(mnuitm, "Add_New");
	mnuitm.setIcon(newbutton.getIcon());
	mnuitm.addActionListener(alAddNew);
	fileMenu.add(mnuitm);

	mnuitm = new JMenuItem();
	ResourceHelper.setText(mnuitm, "Edit");
	mnuitm.setIcon(editbutton.getIcon());
	mnuitm.addActionListener(alEdit);
	fileMenu.add(mnuitm);

	mnuitm = new JMenuItem();
	ResourceHelper.setText(mnuitm, "Delete");
	mnuitm.setIcon(delbutton.getIcon());
	mnuitm.addActionListener(alDelete);
	fileMenu.add(mnuitm);

	ResourceHelper.setText(printList, "Print_List");
	printList.setIcon(new ImageIcon(getClass().getResource("/resource/Print16.gif")));
	printList.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		printListActionPerformed(evt);
	    }
	});

	fileMenu.add(printList);

	ResourceHelper.setText(exitMenuItem, "Exit");
	exitMenuItem.setIcon(new ImageIcon(getClass().getResource("/resource/Stop16.gif")));
	exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		exitMenuItemActionPerformed(evt);
	    }
	});

	menuBar.add(fileMenu);

	fileMenu.add(getImpvcard());
	
	htmlitem = new JMenuItem();
	    ResourceHelper.setText(htmlitem, "SaveHTML");
	    htmlitem.setIcon(new ImageIcon(getClass().getResource("/resource/WebComponent16.gif")));
	    htmlitem.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent e) {
		    try {

			JFileChooser chooser = new JFileChooser();

			chooser.setCurrentDirectory(new File("."));
			chooser.setDialogTitle(Resource.getResourceString("choose_file"));
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			int returnVal = chooser.showOpenDialog(null);
			if (returnVal != JFileChooser.APPROVE_OPTION)
			    return;

			String s = chooser.getSelectedFile().getAbsolutePath();

			OutputStream ostr = IOHelper.createOutputStream(s);
			OutputStreamWriter fw = new OutputStreamWriter(ostr, "UTF8");

			StringWriter sw = new StringWriter();
			AddressModel.getReference().export(sw);
			String sorted = transform(sw.toString(), "/resource/addrsort.xsl");
			String output = transform(sorted, "/resource/addr.xsl");
			fw.write(output);
			fw.close();

		    } catch (Exception ex) {
			Errmsg.errmsg(ex);
		    }

		}
	    });
	fileMenu.add(htmlitem);
	fileMenu.add(exitMenuItem);
	
	return menuBar;

    }

    static private String transform( String xml, String xsl ) throws Exception
    {
        URL inurl = AddressModel.getReference().getClass().getResource(xsl);
        if( inurl == null )
            throw new Exception("Transform " + xsl + " not found");
        Source insrc = new StreamSource(inurl.openStream());
        Transformer trans = TransformerFactory.newInstance().newTransformer(insrc);
        StreamResult res = new StreamResult( new StringWriter());
        trans.transform(new StreamSource(new StringReader(xml)), res );
        return( res.getWriter().toString());
    }
    
    private static AddrListView singleton = null;

    public static AddrListView getReference() {
	if (singleton == null || !singleton.isDisplayable())
	    singleton = new AddrListView();
	return (singleton);
    }
    
}
