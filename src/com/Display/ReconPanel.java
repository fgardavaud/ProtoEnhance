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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.Protocol.Group;
import com.Protocol.Serie;

public class ReconPanel extends JPanel{

		
	//****************  DIMENSION  *******************************************************************************************
	public Dimension 	ParametersEditionPanelSize;
	
	//****************  SERIE  ***********************************************************************************************
	public Group 		group;
	
	
	//****************  INT  *************************************************************************************************
	public int 			indice;
	
	
	//****************  JButton[]  *******************************************************************************************
	public JButton[] 	reconNbButton;
	
	//****************  JPANEL  **********************************************************************************************
	public JPanel 		center = new JPanel(),
						east = new JPanel();
	
	public JPanel[]		reconParametersPanel;		
	
	//****************  RECONSTRUCTIONTABLE  *********************************************************************************
	public ReconstructionTable reconTable[];
	
	//****************  SERIE  ***********************************************************************************************
	public Serie 		serie;
	
	//****************  STRING  **********************************************************************************************
	public String 		serieType;
	
	/**************************************************************************************************************************/
	/** 	CONSTRUCTEUR 	**/
	public ReconPanel(Serie pSerie, Group pGroup, Dimension ParametersEditionPanelDimension){
		// RECUPERATION DE LA SERIE, DU GROUP ET DE LA DIMENSION
		this.serie = pSerie;
		this.group = pGroup;
		this.ParametersEditionPanelSize = ParametersEditionPanelDimension;
		
		//INSTANCIATION DU center
		center = new JPanel();
		//DEFINITION DU LAYOUT DU RECONNPANEL
		center.setLayout(new GridLayout(5,2));
		
		//INSTANCIATION DES 10 JPANEL CORRESPONDANT AUX RECONS
		reconParametersPanel = new JPanel[10];
		//INSTANCIATION DES 10 BOUTTONS ET DES 10 TABLEAUX CORRESPONDANT AUX RECONS
		reconNbButton = new JButton[10];
		reconTable = new ReconstructionTable[10];
		
		// POUR CHAQUE RECON
		for(int i = 0; i<10; i++){
			// DEFINITION DU TITRE DU BOUTON
			reconNbButton[i] = new JButton("R"+Integer.toString(i+1));
			// AJOUT DE L'ACTION DU BOUTON DE LA RECON
			reconNbButton[i].addActionListener(new reconNbButtonAction());
			
			// AJOUT DU BOUTON EN COURS AU JPANEL CENTER
			center.add(reconNbButton[i]);
			
			
			// DEFINITION DE LA COULEUR DU BOUTON EN FONCTION DE L'ETAT D'ACTIVATION DE LA RECON
			if(group.getRecon(i).getActivation().contains("Yes")){
				reconNbButton[i].setBackground(new Color(184, 207, 229));
			}
			else{
				reconNbButton[i].setBackground(new Color(230, 230, 250));
			}
			
			// INSTANCIATION DU RECONPARAMETERSPANEL DE LA RECON EN COURS
			reconParametersPanel[i] = new JPanel();
			// INSTANCIATION DU RECONTABLE DE LA RECON EN COURS
			reconTable[i] = new ReconstructionTable(group, group.getRecon(i), ParametersEditionPanelSize);
			reconTable[i].getModel().addTableModelListener(new ReconstructionListener());
			// AJOUT DU TABLEAU DE VALEUR AU RECOMPARAMETERSPANEL
			reconParametersPanel[i].add(new tableContainer(reconTable[i]));
		}
		// SELECTION DU BOUTON DE LA RECON NATIVE
		reconNbButton[0].setForeground(Color.white);
		
		
		
		// AJOUT DU RECONPARAMETERSPANEL DE LA RECON NATIVE AU JPANEL EAST
		indice = 0;
		east.add(reconParametersPanel[indice]);
		
		// DEFINITION DU LAYOUT DU RECONPANEL
		this.setLayout(new BorderLayout());
		// AJOUT DU JPANEL CONTENANT LES DIX BOUTTONS ET DU RECONPARAMETERSPANEL DE LA RECON NATIVE
		this.add(center, BorderLayout.CENTER);
		this.add(east, BorderLayout.EAST);
		// DEFINITION DE LA TAILLE DU RECONPANEL
		this.setPreferredSize(new Dimension((int)ParametersEditionPanelDimension.getWidth(), 
								(int)ParametersEditionPanelSize.getHeight()/3));
	}
	
	
	/**************************************************************************************************************************/
	/** 	ACTION DU BOUTON DE RECON	**/
	
	class reconNbButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e){	
			// RECUPERATION DE L'INDICE DU BOUTON
			String titre = ((JButton)e.getSource()).getText().substring(1);
			indice = Integer.parseInt(titre)-1;
			// DEFINITION DU FOREGROUND DES 10 BOUTONS DE RECON
			for(int i = 0; i<10; i++){
				if(i == indice){
					reconNbButton[i].setForeground(Color.white);
				}
				else{
					reconNbButton[i].setForeground(Color.black);
				}
			}
			//  SUPPRESSION DE TOUS LES ANCIENS ELEMENTS DE EAST
			east.removeAll();
			// AJOUT DU RECONPARAMETERSPANEL DE LA RECON C0RRESPONDANTE AU JPANEL EAST
			east.add(reconParametersPanel[indice]);

			// ACTUALISATION DU JPANEL EAST
			east.updateUI();
		}
	}
	
	/**************************************************************************************************************************/
	/** 	CLASSE DE CREATION D'UN CONTAINER CONTENANT LE JTABLE ET SON HEADER  		**/
	class tableContainer extends Container {
		
		public tableContainer(ReconstructionTable table){
			// DEFINITION DU LAYOUT EN BORDERLAYOUT
		    this.setLayout(new BorderLayout());
		    // AJOUT DU HEADER EN PAGE_START
		    this.add(table.getTableHeader(), BorderLayout.PAGE_START);
		    // AJOUT DU TABLE EN CENTER
		    this.add(table, BorderLayout.CENTER);
		}
	}
	
	/**************************************************************************************************************************/
	/** 	CLASSE D'ECOUTE DU TABLEMODEL DE RECONSTRUCTION 		**/
	public class ReconstructionListener implements TableModelListener{
		
		public void tableChanged(TableModelEvent TME) {
			// RECUPERATION DE LA COLONNE 
			int column = TME.getColumn();
			
			// SI LA COLONNE CORRESPOND A L'ACTIVATION ET QUE LA VALEUR A CHANGEE
			if(column == 1 && reconTable[indice].activeComboBox.getSelectedIndex() != reconTable[indice].activeIndice){
				// ON MET A JOUR LE ACTIVATIONINDICE 
				reconTable[indice].activeIndice = reconTable[indice].activeComboBox.getSelectedIndex();
				// SI L'INDICE EST DE 0
				if(reconTable[indice].activeIndice == 0){
					// LA LISTE DES PITCH EST CELLE POUR LA COLLIMATION DE 32
					reconNbButton[indice].setBackground(new Color(184, 207, 229));
				}
				//SINON
				else{
					// LA LISTE DES PITCH EST CELLE POUR LA COLLIMATION DE 64
					reconNbButton[indice].setBackground(new Color(230, 230, 250));
				}
			}
			
			// SI LA COLONNE CORRESPOND A L'EPAISSEUR ET QUE LA VALEUR A CHANGEE
			if(column == 4 && reconTable[indice].thicknessComboBox.getSelectedIndex() != reconTable[indice].thicknessIndice){
				// ON MET A JOUR L'EPAISSEUR ET LES VALEURS LIMITES HAUTE ET BASSE
				reconTable[indice].thicknessIndice = reconTable[indice].thicknessComboBox.getSelectedIndex();
				reconTable[indice].lowInter = Float.parseFloat((String)reconTable[indice].thicknessComboBox.getSelectedItem())/2;
				reconTable[indice].highInter = Float.parseFloat((String)reconTable[indice].thicknessComboBox.getSelectedItem())*2;
				// SI LA VALEUR D'INTERVALLE SAISIE EST SUPERIEURE A LA LIMITE HAUTE
				if(Float.parseFloat(reconTable[indice].intervalle) > reconTable[indice].highInter){
					// ON REMPLACE LA VALEUR SAISIE PAR LA LIMITE HAUTE
					reconTable[indice].intervalle = Float.toString(reconTable[indice].highInter);
					reconTable[indice].getModel().setValueAt(reconTable[indice].intervalle, 0, column+1);
				}
				// SINON SI LA VALEUR SAISIE EST INFERIEURE A LA LIMITE BASSE
				else if((Float.parseFloat(reconTable[indice].intervalle) < reconTable[indice].lowInter)){
					// ON REMPLACE LA VALEUR SAISIE PAR LA LIMITE BASSE
					reconTable[indice].intervalle = Float.toString(reconTable[indice].lowInter);
					reconTable[indice].getModel().setValueAt(reconTable[indice].intervalle, 0, column+1);
				}
			}
			
			// SI LA COLONNE CORRESPOND A L'EPAISSEUR ET QUE LA VALEUR A CHANGEE
			if(column == 5 && (String)reconTable[indice].getValueAt(0, 5) != reconTable[indice].intervalle){
				// ON MET A JOUR L'INTERVALLE ET LES VALEURS LIMITES HAUTE ET BASSE
				reconTable[indice].intervalle = (String)reconTable[indice].getValueAt(0, 5);
				// SI LA VALEUR D'INTERVALLE SAISIE EST SUPERIEURE A LA LIMITE HAUTE
				if(Float.parseFloat(reconTable[indice].intervalle) > reconTable[indice].highInter){
					// ON REMPLACE LA VALEUR SAISIE PAR LA LIMITE HAUTE
					reconTable[indice].intervalle = Float.toString(reconTable[indice].highInter);	
					reconTable[indice].getModel().setValueAt(reconTable[indice].intervalle, 0, column);
				}
				// SINON SI LA VALEUR SAISIE EST INFERIEURE A LA LIMITE BASSE
				else if((Float.parseFloat(reconTable[indice].intervalle) < reconTable[indice].lowInter)){
					// ON REMPLACE LA VALEUR SAISIE PAR LA LIMITE BASSE
					reconTable[indice].intervalle = Float.toString(reconTable[indice].lowInter);
					reconTable[indice].getModel().setValueAt(reconTable[indice].intervalle, 0, column);
				}
			}
		}
	}
}
