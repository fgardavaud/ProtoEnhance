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

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.Protocol.Group;
import com.Protocol.Recon;

public class ReconstructionTable extends JTable{
	
	//****************  DEFAULTTABLEMODEL  ***********************************************************************************
	public 				DefaultTableModel 	reconModel;
			
	//****************  DIMENSION  *******************************************************************************************
	public Dimension 	ParametersEditionPanelSize;	
	
	//****************  GROUP  ***********************************************************************************************
	public Group 		group;
	
	//****************  INT  ***********************************************************************************************
	public int 			activeIndice = 0,
						filterIndice = 0,
						iqeIndice = 0,
						thicknessIndice = 0,
						reconModeIndice = 0,
						asirIndice = 0,
						veoIndice = 0;
	
	//****************  FLOAT  ***********************************************************************************************
	public float		lowInter,
						highInter;
	
	//****************  JCHECKBOX  *******************************************************************************************
	public JCheckBox 	checkBox = new JCheckBox();	
	
	//****************  JCOMBOBOX  *******************************************************************************************
	public JComboBox 	asirComboBox = new JComboBox(),
						activeComboBox = new JComboBox(),
						filterComboBox = new JComboBox(),
						iqeComboBox = new JComboBox(),
						thicknessComboBox = new JComboBox(),
						reconModeComboBox = new JComboBox(),
						veoComboBox = new JComboBox();
	
	//****************  RECON  ***********************************************************************************************
	public Recon 		recon;
	
	//****************  STRING[]  ********************************************************************************************
	public String[] 	asir = {"\"\"", "\"SS10:Slice\"", "\"SS20:Slice\"", "\"SS30:Slice\"", "\"SS40:Slice\"", 
								"\"SS50:Slice\"", "\"SS60:Slice\"",	"\"SS70:Slice\"", "\"SS80:Slice\"", "\"SS90:Slice\"", 
								"\"SS100:SLice\""},
						active = {"Yes", "No"},
						filterList  = {},
						filters = {"Bone", "BoneHD", "BonePlus", "BonePlusHD", "Chest", "Detail", "DetailHD", "Edge", "EdgeHD", 
									"Lung", "LungHD", "Soft", "Standard", "StandardHD", "UltraHD"},
						iqe = {"Yes", "No"},
						nonHDfilters = {"Bone", "BonePlus", "Chest", "Detail", "Edge", "Lung", "Soft", "Standard"},
						reconMode = {"Full", "Plus"},
						thickness = {"0.625", "1.25", "2.5", "5"},
						veo = {"Yes", "No"};
	public String		intervalle;
	
	/**************************************************************************************************************************/
	/** 	CONSTRUCTEUR 	**/
	public ReconstructionTable (Group pGroup, Recon pRecon,  Dimension ParametersEditionPanelDimension){
		// RECUPERATION DU GROUP DE LA RECON
		this.group = pGroup;
		this.recon = pRecon;
		ParametersEditionPanelSize = ParametersEditionPanelDimension;
		
		// CREATION D'UN MODEL DE TABLE POUR RECON 
		reconModel = new DefaultTableModel(new String[]{"Titre", "Active", "Filtre de reconstruction", "Mode de reconstruction", "Epaisseur de coupe",
																			"Intervalle", "ASIR", "IQE", "Veo"}, 0);
		// REMPLISSAGE DU TABLEAU
		getRows(reconModel);
		
		// ASSIGNATION DU MODEL AU RECONSTRUCTIONTABLE
		this.setModel(reconModel);
		// DEFINITION DES EDITORS DES DIFFERENTES CELLULES
		this.getColumn("Active").setCellEditor(new DefaultCellEditor(activeComboBox));
		this.getColumn("IQE").setCellEditor(new DefaultCellEditor(iqeComboBox));
		this.getColumn("Veo").setCellEditor(new DefaultCellEditor(veoComboBox));
		
		this.getColumn("Filtre de reconstruction").setCellEditor(new DefaultCellEditor(filterComboBox));
		this.getColumn("Mode de reconstruction").setCellEditor(new DefaultCellEditor(reconModeComboBox));
		this.getColumn("Epaisseur de coupe").setCellEditor(new DefaultCellEditor(thicknessComboBox));
		this.getColumn("ASIR").setCellEditor(new DefaultCellEditor(asirComboBox));
		
		// ON EMPECHE LA SELECTION DES CELLULES
		this.setCellSelectionEnabled(false);
	}
	
	/**************************************************************************************************************************/
	/** 	METHODE DE REMPLISSAGE DE LA LIGNE DU TABLEAU  		**/
	public void getRows(DefaultTableModel pmodel){
		
		// SELECTION DE L'ETAT INITIAL D'ACTIVATION DANS LA COMBOBOX CORRESPONDANTE
		for(int i = 0; i < active.length; i++ ){			
			if(active[i].contains(recon.getActivation())){
				activeIndice = i;
			}
		}	
		// INSTANCIATION DE L'ACTIVECOMBOBOX A PARTIR DE LA LISTE D'ACTIVATION
		activeComboBox = new JComboBox(active);
		

		// DEFINITION DE LA LISTE DE FILTRES (HD OU NON) 
		if(group.getSmartCathodeMode().contains("scDynamicDeflect") && group.getSmartCathodeNumDeflections().contains("2")){
			filterList = filters;
		}
		else{			
			filterList = nonHDfilters;
		}
		// SELECTION DU FILTRE INITIAL DANS LA COMBOBOX CORRESPONDANTE
		for(int i = 0; i < filterList.length; i++ ){
			if(filterList[i].contains(recon.getFilter())){
				filterIndice = i;
			}
		}
		// INSTANcIATION DE LA FILTERCOMBOBOX A PARTIR DE LA LISTE DE FILTRE
		filterComboBox = new JComboBox(filterList);
			

		// SELECTION DE L'IQE INITIAL D'ACTIVATION DANS LA COMBOBOX CORRESPONDANTE
		for(int i = 0; i < iqe.length; i++ ){			
			if(iqe[i].contains(recon.getIqe())){
				iqeIndice = i;
			}
		}
		// INSTANCIATION DE L'ACTIVECOMBOBOX A PARTIR DE LA LISTE D'IQE
		iqeComboBox = new JComboBox(iqe);
		
		
		// SELECTION DU FILTRE INITIAL DANS LA COMBOBOX CORRESPONDANTE
		for(int i = 0; i < thickness.length; i++ ){			
			if(thickness[i].contains(recon.getThick())){
				thicknessIndice = i;
			}
		}
		// INSTANcIATION DE LA THICKNESSCOMBOBOX A PARTIR DE LA LISTE D'EPAISSEUR
		thicknessComboBox = new JComboBox(thickness);
		
		// RECUPERATION DE L'INTERVALLE
		intervalle = recon.getInter();
		lowInter = Float.parseFloat(intervalle)/2;
		highInter = Float.parseFloat(intervalle)*2;
		
		// // SELECTION DU MODE DE RECON INITIAL DANS LA COMBOBOX CORRESPONDANTE
		if(recon.getTyperecon().contains("0")){
			reconModeIndice = 0;
		}
		else if(recon.getTyperecon().contains("7")){
			reconModeIndice = 1;
		}		
		// INSTANcIATION DE LA THICKNESSCOMBOBOX A PARTIR DE LA LISTE D'EPAISSEUR
		reconModeComboBox = new JComboBox(reconMode);
		
		
		// SELECTION DU POURCENATGE D'ASIR INITIAL DANS LA COMBOBOX CORRESPONDANTE
		for(int i = 0; i < asir.length; i++ ){			
			if(asir[i].contains(recon.getAsirConfig())){
				asirIndice = i;
			}
		}
		// INSTANcIATION DE LA ASIRCOMBOBOX A PARTIR DE LA LISTE DE POURCENTAGE
		asirComboBox = new JComboBox(asir);
		
		// SELECTION DE L'ETAT INITIAL D'ACTIVATION DANS LA COMBOBOX CORRESPONDANTE
		for(int i = 0; i < veo.length; i++ ){			
			if(veo[i].contains(recon.getMBIR())){
				veoIndice = i;
			}
		}		
		// INSTANCIATION DE L'ACTIVECOMBOBOX A PARTIR DE LA LISTE D'ACTIVATION
		veoComboBox = new JComboBox(veo);
						
		pmodel.addRow(new Object[]{recon.getTitre(), active[activeIndice], filterList[filterIndice], reconMode[reconModeIndice], thickness[thicknessIndice], 
									intervalle, asir[asirIndice], iqe[iqeIndice], veo[veoIndice]});
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE DE GESTION DE L'AFFICHAGE DES CELLULES CONTENANT UNE JCHECKBOX  		**/
	public class CheckBoxRenderer extends JCheckBox implements TableCellRenderer{

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
			if(value.toString().contains("Yes")){
				 // LA CHECKBOX EST COCHEE
				 checkBox.setSelected(true);
			}
			// SINON
			else if(value.toString().contains("No")){
				 // LA CHECKBOX EST DECOCHEE
				 checkBox.setSelected(false);
			}	
		    return checkBox;
		}
	}
}
