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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.Protocol.Protocol;

public class ProtoStructurePanel extends JPanel{
	/**************************************************************************************************************************/
	/** 	DECLARATION DES VARIABLES 	**/
	
	//****************  DIMENSION  *******************************************************************************************
	private Dimension 	ProtoStructurePanelSize;
				
		
	//****************  JTable  **********************************************************************************************
	private JTable		protocoleTable,
						referenceTable;
	
	
	//****************  PROTOCOLE  *******************************************************************************************
	private Protocol 	protocole,
						reference;
	
	
	/**************************************************************************************************************************/
	/** 	CONSTRUCTEUR 	**/
	public ProtoStructurePanel(Protocol pProtocole, Protocol pReference, Dimension ProtoStructurePanelDImension){
		// RECUPERATION DES DEUX PROTOCOLES
		protocole = pProtocole;
		reference = pReference;
		ProtoStructurePanelSize = ProtoStructurePanelDImension;
		
		String secondColumnTitle = "Déposer ici les séries/recons souhaitées";
		
		// DEFINITION D'UN MODEL DE TABLE POUR LE PROTOCOLE ET LA REFERENCE
		DefaultTableModel protocoleModel = new DefaultTableModel(new String[]{protocole.getTitre(), secondColumnTitle}, 0);
		DefaultTableModel referenceModel = new DefaultTableModel(new String[]{reference.getTitre()}, 0);
				
		// AJOUT DES LIGNES CORRESPONDANT AUX RECONS POUR CHAQUE PROTOCOLE
		getRows(protocoleModel, protocole);
		getRows(referenceModel, reference);
				
		// CREATION DE 2 JTABLE POUR LE PROTOCOLE ET LA REFERENCE
		protocoleTable = new JTable(protocoleModel);
		referenceTable = new JTable(referenceModel);	
		
		// AJOUT D'UN MOUSELISTENER AUX JTABLES
		referenceTable.addMouseListener(new cellListener());
		protocoleTable.addMouseListener(new cellListener());
		
		// ACTIVATION DU DRAG SUR LES DEUX TABLEAUX
		protocoleTable.setDragEnabled(true);
		referenceTable.setDragEnabled(true);
				
		// SPECIFICATION DU TRASNFERHANDLER
		protocoleTable.setTransferHandler(new ProtocoleTransferHandler(protocoleTable));	
		referenceTable.setTransferHandler(new ReferenceTransferHandler(referenceTable));

		// SPECIFICATION DU CELLRENDERER
		protocoleTable.getColumn(protocole.getTitre()).setCellRenderer(new protocoleButtonRenderer());  		protocoleTable.getColumn(secondColumnTitle).setCellRenderer(new referenceButtonRenderer());
		referenceTable.getColumn(reference.getTitre()).setCellRenderer(new referenceButtonRenderer());
		
		// AJUSTEMENT DES DE LA TAILLE DES COLONNES DES DEUX TABLEAUX
		resizeColumnWidth(protocoleTable);
		resizeColumnWidth(referenceTable);
		
		BoxLayout tablesLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		this.setLayout(tablesLayout);

		// AJOUT DES TABLEAUX AU PANEL
		this.add(new tableContainer(protocoleTable));
		this.add(new tableContainer(referenceTable));
				
		//DEFINITION DE LA TAILLE DU ProtoStructurePanel
		this.setPreferredSize(ProtoStructurePanelSize);
	}	
		
	
		
	/**************************************************************************************************************************/
	/** 	METHODE DE REMPLISSAGE DES LIGNES DU TABLEAU EN FONCTION DE LA STRUCUTRE DU PROTOCOLE  		**/
	public void getRows(DefaultTableModel pmodel, Protocol pProtocol){
		// DE 0 AU NOMBRE TOTAL DE SERIE DU PROTOCOLE 
		int ligne = 0;
		for (int s = 0; s <= pProtocol.getNbSerie(); s++){
			// DE 0 AU NOMBRE DE GROUPE TOTAL DE LA SERIE EN COURS
			for (int g = 0; g <= pProtocol.getSérie(s).getNbGroup(); g++){
				// DE 0 AU NOMBRE DE RECON TOTAL DU GROUPE EN COURS DE LA SERIE EN COURS
				for (int r = 0; r <= pProtocol.getSérie(s).getGroup(g).getNbRecon(); r++){
					if(pProtocol.getSérie(s).getGroup(g).getRecon(r).getActivation().contains("Yes")){
						// DEINITION D'UNE CHAINE DE CARACTERES A PARTIR DES INDICES SERIE/GROUP/RECON
						String SGR = Integer.toString(s).concat(Integer.toString(g)).concat(Integer.toString(r));
						
						// SI C'EST LA RECON NATIVE ET QU'IL Y A PLUSIEURS GROUPES
						if (r == 0 && pProtocol.getSérie(s).getNbGroup() >= 1){
							// AJOUT D'UNE LIGNE AU TABLEMODEL CONTENANT LA RECON DEFINIE PAR SES INDICES SGR
							pmodel.addRow(new Object[]{SGR});
						}
						// SINON SI C'EST LA RECON NATIVE ET SI IL N'Y A QU'UN GROUPE
						else if (r == 0 && pProtocol.getSérie(s).getNbGroup() == 0){
							// AJOUT D'UNE LIGNE AU TABLEMODEL CONTENANT LA RECON DEFINIE PAR SES INDICES SGR
							pmodel.addRow(new Object[]{SGR});
						}
						
						// SINON SI C'EST LA RECON NATIVE, QU'IL Y A PLUSIEURS GROUPES ET QUE CE N'EST PAS LE PREMIER GROUPE
						else if (r == 0 && pProtocol.getSérie(s).getNbGroup() >= 1 && g >= 1){
							// AJOUT D'UNE LIGNE AU TABLEMODEL CONTENANT LA RECON DEFINIE PAR SES INDICES SGR
							pmodel.addRow(new Object[]{SGR});
						}								 
						// SINON SI CE N'EST PAS LA RECON NATIVE ET QU'IL Y A PLUSIEURS GROUPES
						else if (r >= 1  && pProtocol.getSérie(s).getNbGroup() >= 1){
							// AJOUT D'UNE LIGNE AU TABLEMODEL CONTENANT LA RECON DEFINIE PAR SES INDICES SGR
							pmodel.addRow(new Object[]{SGR});
						}								
						// SINON SI CE N'EST PAS LA RECON NATIVE ET QU'IL N'Y A QU'UN GROUPE
						else if (r >= 1  && pProtocol.getSérie(s).getNbGroup() == 0){
							// AJOUT D'UNE LIGNE AU TABLEMODEL CONTENANT LA RECON DEFINIE PAR SES INDICES SGR
							pmodel.addRow(new Object[]{SGR});
						}
						
						// INCREMENT DE LA LIGNE DU TABLEAU
						ligne++;
					}
				}
			}	
		}
	}	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'AJUSTEMENT AUTOMATIQUE DES COLONNES DU TABLEAU  		**/
	public void resizeColumnWidth(JTable table) {
		// OBTENTION DU COLUMNMODEL
	    TableColumnModel columnModel = table.getColumnModel();
	    int column = 0;
	    // DEFINITION DE LA LARGEUR DES COLONNES
	    while(column < table.getModel().getColumnCount()){
	    	columnModel.getColumn(column).setPreferredWidth(ProtoStructurePanelSize.width/(7/2));
	    	column++;
	    }    	    
	} 
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DES RECONS CONTENUES DANS LE PROTOCOLETABLE  		**/
	public String[][] getMatchedRecon() {
		// RECUPERATION DU NOMBRE DE LIGNES
		int nbRow = protocoleTable.getRowCount();
		// RECUPERATION DU NOMBRE DE COLONNES
		int nbColumn = protocoleTable.getColumnCount();
		String[][] matchedRecon = new String[nbRow][nbColumn];
		// DE 0 AU NOMBRE DE LIGNE DU PROTOCOLETABLE
		for (int row = 0; row < nbRow; row ++){
			// DE 0 AU NOMBRE DE COLONNE DU PROTOCOLETABLE
			for (int column = 0; column < nbColumn; column ++){
				matchedRecon[row][column] = (String) protocoleTable.getValueAt(row, column);
			}
		}				
	    return matchedRecon;
	} 
	
	/**************************************************************************************************************************/
	/** 	CLASSE DE CREATION D'UN CONTAINER CONTENANT LE JTABLE ET SON HEADER  		**/
	class tableContainer extends Container {
		
		public tableContainer(JTable table){
			// DEFINITION DU LAYOUT EN BORDERLAYOUT
		    this.setLayout(new BorderLayout());
		    // AJOUT DU HEADER EN PAGE_START
		    this.add(table.getTableHeader(), BorderLayout.PAGE_START);
		    // AJOUT DU TABLE EN CENTER
		    this.add(table, BorderLayout.CENTER);
		}
	}
	
	/**************************************************************************************************************************/
	/** 	CLASSE DE GESTION DE L'AFFICHAGE DES CELLULES DU TABLEAU DU PROTOCOLE  		**/
	class protocoleButtonRenderer extends JButton implements TableCellRenderer{
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
		    // SI LE CONTENU DE LA CELLULE EST UN STRING
			if (value instanceof String){
				// RECUPERATION DE L'INDICE DE LA SERIE
				int s = Integer.parseInt(((String) value).substring(0, 1));
				// RECUPERATION DE L'INDICE DU GROUP
				int g = Integer.parseInt(((String) value).substring(1, 2));
				// RECUPERATION DE L'INDICE DE LA RECON
				int r = Integer.parseInt(((String) value).substring(2));
				// CREATION D'UN BOUTON AFFICHANT LE TITRE DE LA RECON
				JButton titreBouton = new JButton (protocole.getSérie(s).getGroup(g).getRecon(r).getTitre());
				if(r == 0){
					titreBouton.setBackground(new Color(184, 207, 229));
					titreBouton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(184, 207, 229)));
				}
				else{
					titreBouton.setBackground(new Color(230, 230, 250));
					titreBouton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(230, 230, 250)));
				}
				// ON RETOURNE UN BOUTON AFFICHANT LE TITRE DE LA RECON
				return titreBouton;
			}
			else{
				return null;
			}
		}
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE DE GESTION DE L'AFFICHAGE DES CELLULES DU TABLEAU DE LA REFERENCE  		**/
	class referenceButtonRenderer extends JButton implements TableCellRenderer{
			
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
			// SI LE CONTENU DE LA CELLULE EST UN STRING
			if (value instanceof String){
				// RECUPERATION DE L'INDICE DE LA SERIE
				int s = Integer.parseInt(((String) value).substring(0, 1));
				// RECUPERATION DE L'INDICE DU GROUP
				int g = Integer.parseInt(((String) value).substring(1, 2));
				// RECUPERATION DE L'INDICE DE LA RECON
				int r = Integer.parseInt(((String) value).substring(2));
				JButton titreBouton = new JButton (reference.getSérie(s).getGroup(g).getRecon(r).getTitre());
				if(r == 0){
					titreBouton.setBackground(new Color(184, 207, 229));
					titreBouton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(184, 207, 229)));
				}
				else{
					titreBouton.setBackground(new Color(230, 230, 250));
					titreBouton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(230, 230, 250)));
				}
				// ON RETOURNE UN BOUTON AFFICHANT LE TITRE DE LA RECON
				return titreBouton;
			}
			else{
				return null;
			}
		}
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE D'ECOUTE D'ACTION SUR LES CELLULES DE TABLEAU  		**/
	class cellListener implements MouseListener{
		
		/**********************************************************************************************************************/
		/** 	CONSTRUCTEUR  		**/
		public cellListener(){		}
		
		public void mouseClicked(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void mousePressed(MouseEvent e){
			
			// RECUPERATION DU HANDLER
			TransferHandler handler = ((JTable)e.getSource()).getTransferHandler();

			// SI LA SOURCE EST LE TABLEAU DU PROTOCOLE DE REFERENCE		
			if((JTable)e.getSource() == referenceTable){
				// EXPORT DU HANDLER EN DRAG AVEC ACTION DE COPIE
				handler.exportAsDrag((JTable)e.getSource(), e, TransferHandler.COPY);
			}
			// SI LA SOURCE EST LE TABLEAU DU PROTOCOLE A OPTIMISER	
			else if((JTable)e.getSource() == protocoleTable){
				// EXPORT DU HANDLER EN DRAG AVEC ACTION DE DEPLACEMENT
				handler.exportAsDrag((JTable)e.getSource(), e, TransferHandler.MOVE);
			}
		}
	}
}

                    
