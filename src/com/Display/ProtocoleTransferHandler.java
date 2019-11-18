/**
*
COPYRIGHT
Auteurs: François Gardavaud et Hugo Pasquier 
Contributeurs: Pr. Alain Luciani et Pr. Alain Rahmouni
Validation : Société Française de Physique Médicale (SFPM)
(19/10/2017) 
 
francois.gardavaud@aphp.fr
hugo.pasq@gmail.com

ProtoEnhance est un logiciel destiné à optimiser les protocoles 
d'acquisition scanographique à partir d'une bibliothèque d'images
d'un fantôme anthropomorphique adulte acquise en fonction des 
recommandations du constructeur et des pratiques cliniques de plusieurs
Centres Hospitaliers Universitaires sur le scanner Discovery CT 750HD 
commercialisé par la société GE HealthCare.

Ce logiciel est régi par la licence CeCILL soumise au droit français et
respectant les principes de diffusion des logiciels libres. Vous pouvez
utiliser, modifier et/ou redistribuer ce programme sous les conditions
de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
sur le site "http://www.cecill.info".

En contrepartie de l'accessibilité au code source et des droits de copie,
de modification et de redistribution accordés par cette licence, il n'est
offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
seule une responsabilité restreinte pèse sur l'auteur du programme,  le
titulaire des droits patrimoniaux et les concédants successifs.

A cet égard  l'attention de l'utilisateur est attirée sur les risques
associés au chargement,  à l'utilisation,  à la modification et/ou au
développement et à la reproduction du logiciel par l'utilisateur étant
donné sa spécificité de logiciel libre, qui peut le rendre complexe à
manipuler et qui le réserve donc à des développeurs et des professionnels
avertis possédant  des  connaissances  informatiques approfondies.  Les
utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
logiciel à leurs besoins dans des conditions permettant d'assurer la
sécurité de leurs systèmes et ou de leurs données et, plus généralement,
à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.

Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
pris connaissance de la licence CeCILL, et que vous en avez accepté les
termes.


*****************************************************************************


COPYRIGHT
Authors: François Gardavaud and Hugo Pasquier
Contributors: Pr. Alain Luciani and Pr. Alain Rahmouni
Validation : French Society of Medical Physics (SFPM)
(19/10/2017)
 
francois.gardavaud@aphp.fr
hugo.pasq@gmail.com
 
ProtoEnhance is a software designed to optimize CT protocols
using an image library acquired on an adult anthropomorphic phantom based 
on the manufacturer's recommendations and the clinical practice of several
University Hospitals on the Discovery CT 750HD scanner from GE HealthCare.

This software is governed by the CeCILL license under French law and
abiding by the rules of distribution of free software.  You can  use, 
modify and/ or redistribute the software under the terms of the CeCILL
license as circulated by CEA, CNRS and INRIA at the following URL
"http://www.cecill.info". 

As a counterpart to the access to the source code and  rights to copy,
modify and redistribute granted by the license, users are provided only
with a limited warranty  and the software's author,  the holder of the
economic rights,  and the successive licensors  have only  limited
liability. 

In this respect, the user's attention is drawn to the risks associated
with loading,  using,  modifying and/or developing or reproducing the
software by the user in light of its specific status of free software,
that may mean  that it is complicated to manipulate,  and  that  also
therefore means  that it is reserved for developers  and  experienced
professionals having in-depth computer knowledge. Users are therefore
encouraged to load and test the software's suitability as regards their
requirements in conditions enabling the security of their systems and/or 
data to be ensured and,  more generally, to use and operate it in the 
same conditions as regards security. 

The fact that you are presently reading this means that you have had
knowledge of the CeCILL license and that you accept its terms.
*
*/
package com.Display;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.TransferHandler;

import com.Protocol.Recon;

public class ProtocoleTransferHandler extends TransferHandler{
	
	/**************************************************************************************************************************/
	/** 	DECLARATION DES VARIABLES 	**/
	//****************  INT  *************************************************************************************************
	int 	rowDrag, 
			columnDrag, 
			rowDrop, 
			columnDrop;
	
	//****************  JTABLE  **********************************************************************************************
	JTable table;
	
	//****************  STRING  **********************************************************************************************
	String 	reconDrag, 
			reconDrop;	
	
	
	/**************************************************************************************************************************/
	/** 	CONSTRUCTEUR  		**/
	public ProtocoleTransferHandler(JTable pTable){
		// RECUPERATION DU JTABLE
		this.table = pTable;
	}
	
		
	/**************************************************************************************************************************/
	/** 	TYPE D'ACTIONS AUTORISEES  		**/
	public int getSourceActions(JComponent c){
		// SEUL LE DEPLACEMENT EST AUTORISE
		return MOVE;
	}
	
	/**************************************************************************************************************************/
	/** 	TYPE DE DONNEES AUTORISEES  		**/
	public boolean canImport(TransferHandler.TransferSupport info){
		// SI LE DATAFLAVOR NE CORRESPOND PAS A UN STRING
		if(!info.isDataFlavorSupported(DataFlavor.stringFlavor)){
			// RETOURNER FALSE
			return false;
		}
		// SINON
		else{
			// RETOURNER TRUE
			return true;
		}
		
	}
	
	/**************************************************************************************************************************/
	/** 	IMPORT DES DONNEES  		**/
	public boolean importData(TransferHandler.TransferSupport support){
		// RECUPERATION DE L'EMPLACEMENT DU DROP
		JTable.DropLocation dropLocation = (JTable.DropLocation)support.getDropLocation();
		//RECUPERATION DE LA LIGNE DE DROP
		rowDrop = dropLocation.getRow();
		// RECUPERATION DE LA COLONNE DE DROP
		columnDrop = dropLocation.getColumn();
		// RECUPERATION DU TRANSFERABLE
		Transferable data = support.getTransferable();
		try{
			// RECUPERATION DES DONNEES CONTENUES DANS LE TRANSFERABLE
			reconDrop = (String)data.getTransferData(DataFlavor.stringFlavor);
			// ASSIGNATION DE LA RECON DANS LA CELLULE DE DROP
			table.getModel().setValueAt(reconDrop, rowDrop, columnDrop);
		}
		catch(UnsupportedFlavorException e){
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**************************************************************************************************************************/
	/** 	IMPORT DES DONNEES  		**/
	public void exportDone(JComponent c, Transferable t, int action){				
		if(action == MOVE){
			// SUPPRESSION DE LA DATA CONTENUE DANS LA CELLULE DE DRAG
			table.getModel().setValueAt(null, rowDrag, columnDrag);
		}
		
	}
	
	/**************************************************************************************************************************/
	/** 	METHODE DE CREATION DU TRANSFERABLE  		**/
	protected Transferable createTransferable(JComponent c){
		// RECUPERATION DE LA LIGNE CLIQUEE
		rowDrag = ((JTable)c).getSelectedRow();
		// RECUPERATION DE LA COLONNE CLIQUEE
		columnDrag = ((JTable)c).getSelectedColumn();
		// RECUPERATION DE LA RECON DANS LA CELLULE CLIQUEE
		reconDrag = (String) table.getValueAt(rowDrag, columnDrag);
		// ON RETOURNE UN NOUVEAU TRANSFERABLE CONTENANT LA RECON
		return new myTransferable(reconDrag);
		
	}
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE DEFINISSANT LE TRANSFERABLE  		**/
	static class myTransferable implements Transferable{
		public String recon;
		
		
		public myTransferable(String pRecon){
			// RECUPERATION DU TITRE
			this.recon = pRecon;
		}
		
		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException, IOException {
			
			if (flavor == null){
				throw new IOException();
			}
			else if (flavor.equals(DataFlavor.stringFlavor)){
				return recon;
			}
			else{
				throw new UnsupportedFlavorException(flavor);
			}
		}

		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[] {DataFlavor.stringFlavor};
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return (flavor.equals(DataFlavor.stringFlavor));
		}
		
	}
	
	
}

