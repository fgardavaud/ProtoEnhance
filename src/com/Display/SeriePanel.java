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
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.Protocol.Serie;

public class SeriePanel extends JPanel{
	//****************  SERIE  ***********************************************************************************************
	public AcquisitionTable 	acquisitionTable;
		
	//****************  DIMENSION  *******************************************************************************************
	public Dimension 	ParametersEditionPanelSize;
	
	//****************  JPANEL  **********************************************************************************************
	public JPanel 		acquisitionPanel;
	
	//****************  JPANEL  **********************************************************************************************
	public ReconPanel[] 	reconPanel;
	
	//****************  SERIE  ***********************************************************************************************
	public Serie 		serie;
	
	
	/**************************************************************************************************************************/
	/** 	CONSTRUCTEUR 	**/
	public SeriePanel(Serie pSerie,  Dimension ParametersEditionPanelDimension){
		// RECUPERATION DE LA SERIE
		this.serie = pSerie;
		this.ParametersEditionPanelSize = ParametersEditionPanelDimension;
		
		// INSTANCIATION DU JPANEL QUI CONTIENDRA LES PARAMETRES D'ACQUISITION
		acquisitionPanel = new JPanel();
		// INSTANCIATION DE L'ACQUISITIONTABLE DE CHAQUE GROUPE DE LA SERIE
		acquisitionTable = new AcquisitionTable(serie, ParametersEditionPanelSize);
		// AJOUT DE L'ACQUISITIONTABLE CORRESPONDANT A L'ACQUISITIONPANEL
		acquisitionPanel.add(new tableContainer(acquisitionTable));
		acquisitionTable.getModel().addTableModelListener(new AcquisitionListener());
		// INSTANCIATION DU RECON PANEL DE CHAQUE GROUPE DE LA SERIE
		reconPanel = new ReconPanel[serie.getNbGroup()+1];
		
		// POUR CHAQUE GROUPE DE LA SERIE
		for(int g = 0; g <= serie.getNbGroup(); g++){
			if(!serie.getSeriesType().contains("Scout")){
				// CREATION DU RECONPANEL CORRESPONDANT
				reconPanel[g] = new ReconPanel(serie, serie.getGroup(g), ParametersEditionPanelSize);
			}	
		}
		
		// DEFINITION DE LA TAILLE DE L'ACQUISITIONPANEL
		acquisitionPanel.setPreferredSize(new Dimension((int)ParametersEditionPanelDimension.getWidth(), (int)ParametersEditionPanelDimension.getHeight()*2/3));
		this.setLayout(new BorderLayout());
		// AJOUT DE L'ACQUISITIONPANEL ET DU RECONPANEL (EN FONCTION DU TYPE D'ACQUISITION) AU SERIEPANEL
		this.add(acquisitionPanel, BorderLayout.PAGE_START);
		if(!serie.getSeriesType().contains("Scout")){
			this.add(reconPanel[0], BorderLayout.CENTER);
		}	
		
		// DEFINITION DE LA TAILLE DU SERIEPANEL
		this.setPreferredSize(new Dimension((int)ParametersEditionPanelDimension.getWidth(), (int)ParametersEditionPanelDimension.getHeight()));
	}
	
	/**************************************************************************************************************************/
	/** 	CLASSE DE CREATION D'UN CONTAINER CONTENANT LE JTABLE ET SON HEADER  		**/
	class tableContainer extends Container {
		
		public tableContainer(AcquisitionTable table){
			// DEFINITION DU LAYOUT EN BORDERLAYOUT
		    this.setLayout(new BorderLayout());
		    // AJOUT DU HEADER EN PAGE_START
		    this.add(table.getTableHeader(), BorderLayout.PAGE_START);
		    // AJOUT DU TABLE EN CENTER
		    this.add(table, BorderLayout.CENTER);
		}
	}
	
	/**************************************************************************************************************************/
	/** 	CLASSE D'ECOUTE DU TABLEMODEL D'ACQUISITION  		**/
	public class AcquisitionListener implements TableModelListener{
		
		public void tableChanged(TableModelEvent TME) {
			// RECUPERATION DE LA COLONNE 
			int column = TME.getColumn();
			// SI LA COLONNE CORRESPOND AU TYPE D'ACQUISITION ET QUE LA VALEUR A CHANGEE
			if(column == 1 && acquisitionTable.typeComboBox.getSelectedIndex() != (int)acquisitionTable.typeIndice.get(TME.getFirstRow())){
				// MISE A JOUR DU TYPEINDICE
				acquisitionTable.typeIndice.add(TME.getFirstRow(), acquisitionTable.typeComboBox.getSelectedIndex());
			}
			
			// SI LA COLONNE CORRESPOND AU TEPS DE ROTATION ET QUE LA VALEUR A CHANGEE
			else if(column == 2 && acquisitionTable.rotationComboBox.getSelectedIndex() != (int)acquisitionTable.rotationIndice.get(TME.getFirstRow())){
				// MISE A JOUR DU ROTATIONIndice.get(TME.getFirstRow()) 
				acquisitionTable.rotationIndice.add(TME.getFirstRow(), acquisitionTable.rotationComboBox.getSelectedIndex());
			}
			
			// SI LA COLONNE CORRESPOND AU PITCH ET QUE LA VALEUR A CHANGEE
			else if(column == 3 && acquisitionTable.pitchComboBox.getSelectedIndex() != (int)acquisitionTable.pitchIndice.get(TME.getFirstRow())){
				// MISE A JOUR DU PITCHIndice.get(TME.getFirstRow()) 
				acquisitionTable.pitchIndice.add(TME.getFirstRow(), acquisitionTable.pitchComboBox.getSelectedIndex());
			}
			
			// SI LA COLONNE CORRESPOND A LA COLLIMATION ET QUE LA VALEUR A CHANGEE
			else if(column == 4 && acquisitionTable.collimationComboBox.getSelectedIndex() != (int)acquisitionTable.collimationIndice.get(TME.getFirstRow())){
				// ON MET A JOUR LE COLLIMATIONIndice.get(TME.getFirstRow()) 
				acquisitionTable.collimationIndice.add(TME.getFirstRow(), acquisitionTable.collimationComboBox.getSelectedIndex());
				// SI L'INDICE EST DE 0
				if((int)acquisitionTable.collimationIndice.get(TME.getFirstRow()) == 0){
					// LA LISTE DES PITCH EST CELLE POUR LA COLLIMATION DE 32
					acquisitionTable.pitch = acquisitionTable.pitch32;
				}
				//SINON
				else{
					// LA LISTE DES PITCH EST CELLE POUR LA COLLIMATION DE 64
					acquisitionTable.pitch = acquisitionTable.pitch64;
				}
				// MISE A JOUR DE LA PITCHCOMBOBOX
				acquisitionTable.pitchComboBox = new JComboBox(acquisitionTable.pitch);
				acquisitionTable.pitchComboBox.setSelectedIndex((int)acquisitionTable.pitchIndice.get(TME.getFirstRow()));
				// MISE A JOUR DU CELLEDITOR DE LA COLONNE DE PITCH
				acquisitionTable.getColumn("Pitch").setCellEditor(new DefaultCellEditor(acquisitionTable.pitchComboBox));
				acquisitionTable.model.setValueAt(acquisitionTable.pitch[(int)acquisitionTable.pitchIndice.get(TME.getFirstRow())], TME.getFirstRow(), column-1);
			}
			
			// SINON SI LA COLONNE CORRESPOND A LA HE ET QUE LA VALEUR A CHANGEE
			else if(column == 5 && acquisitionTable.HRComboBox.getSelectedIndex() != (int)acquisitionTable.HRIndice.get(TME.getFirstRow())){
				// ON MET A JOUR LE HRINDICE 
				acquisitionTable.HRIndice.add(TME.getFirstRow(), acquisitionTable.HRComboBox.getSelectedIndex());
				// SI L'INDICE EST DE 0
				if((int)acquisitionTable.HRIndice.get(TME.getFirstRow()) == 0){
					// LA LISTE DES FILTRES EST CELLE POUR LA HD
					reconPanel[0].reconTable[reconPanel[0].indice].filterList = reconPanel[0].reconTable[reconPanel[0].indice].filters;
				}
				//SINON
				else{
					// LA LISTE DES FILTRES EST CELLE POUR LA NONHD
					reconPanel[0].reconTable[reconPanel[0].indice].filterList = reconPanel[0].reconTable[reconPanel[0].indice].nonHDfilters;
				}
				// MISE A JOUR DE LA FILTERCOMBOBOX
				reconPanel[0].reconTable[reconPanel[0].indice].filterComboBox = new JComboBox(reconPanel[0].reconTable[reconPanel[0].indice].filterList);
				// MISE A JOUR DU CELLEDITOR DE LA COLONNE DE FILTRES
				reconPanel[0].reconTable[reconPanel[0].indice].getColumn("Filtre de reconstruction").setCellEditor(new DefaultCellEditor(reconPanel[0].reconTable[reconPanel[0].indice].filterComboBox));
				reconPanel[0].reconTable[reconPanel[0].indice].reconModel.setValueAt(reconPanel[0].reconTable[reconPanel[0].indice].filterList[0], 0, 2);
				reconPanel[0].reconTable[reconPanel[0].indice].filterIndice = 0;
			}
			
			// SI LA COLONNE CORRESPOND AU SFOV ET QUE LA VALEUR A CHANGEE
			else if(column == 6 && acquisitionTable.SFOVComboBox.getSelectedIndex() != (int)acquisitionTable.SFOVIndice.get(TME.getFirstRow())){
				// MISE A JOUR DU SFOVINDICE 
				acquisitionTable.SFOVIndice.add(TME.getFirstRow(), acquisitionTable.SFOVComboBox.getSelectedIndex());
			}
			
			// SI LA COLONNE CORRESPOND AU KV ET QUE LA VALEUR A CHANGEE
			else if(column == 7 && acquisitionTable.kVComboBox.getSelectedIndex() != (int)acquisitionTable.kVIndice.get(TME.getFirstRow())){
				// MISE A JOUR DU KVINDICE 
				acquisitionTable.kVIndice.add(TME.getFirstRow(), acquisitionTable.kVComboBox.getSelectedIndex());
			}
			
			// SI LA COLONNE CORRESPOND A L'AUTOMA ET QUE LA VALEUR A CHANGEE
			else if(column == 9 && acquisitionTable.automAComboBox.getSelectedIndex() != (int)acquisitionTable.automAIndice.get(TME.getFirstRow())){
				// ON MET A JOUR LE AUTOMAINDICE 
				acquisitionTable.automAIndice.add(TME.getFirstRow(), acquisitionTable.automAComboBox.getSelectedIndex());
				// SI L'INDICE EST DE 1
				if((int)acquisitionTable.automAIndice.get(TME.getFirstRow()) == 1){
					// LE SMAINDICE EST DE 1
					acquisitionTable.smAIndice.add(TME.getFirstRow(), 1);
					acquisitionTable.smAComboBox.setSelectedIndex(1);
				}
				else{
					// LE SMAINDICE EST DE 0
					acquisitionTable.smAIndice.add(TME.getFirstRow(), 0);
					acquisitionTable.smAComboBox.setSelectedIndex(0);
				}	
				// MISE A JOUR DE LA COLONNE DE SMA
				acquisitionTable.model.setValueAt(acquisitionTable.smA[(int)acquisitionTable.smAIndice.get(TME.getFirstRow())], TME.getFirstRow(), column+1);
				
			}
			
			// SI LA COLONNE CORRESPOND AU SMA ET QUE LA VALEUR A CHANGEE
			else if(column == 10 && acquisitionTable.smAComboBox.getSelectedIndex() != (int)acquisitionTable.smAIndice.get(TME.getFirstRow())){
				// ON MET A JOUR LE SMAINDICE 
				acquisitionTable.smAIndice.add(TME.getFirstRow(), acquisitionTable.smAComboBox.getSelectedIndex());
				// SI LE SMAINDICE EST DE 0
				if((int)acquisitionTable.smAIndice.get(TME.getFirstRow()) == 0){
					// L'AUTOMAINDICE EST DE 0
					acquisitionTable.automAIndice.add(TME.getFirstRow(), 0);
					acquisitionTable.automAComboBox.setSelectedIndex(0);
					// MISE A JOUR DE LA COLONNE DE AUTOMA
					acquisitionTable.model.setValueAt(acquisitionTable.automA[(int)acquisitionTable.automAIndice.get(TME.getFirstRow())], TME.getFirstRow(), column-1);
				}					
			}
		}
	}			
}
