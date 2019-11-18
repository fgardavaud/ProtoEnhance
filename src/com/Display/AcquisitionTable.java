/**
*
COPYRIGHT
Auteurs: Fran�ois Gardavaud et Hugo Pasquier 
Contributeurs: Pr. Alain Luciani et Pr. Alain Rahmouni
Validation : Soci�t� Fran�aise de Physique M�dicale (SFPM)
(19/10/2017) 
 
francois.gardavaud@aphp.fr
hugo.pasq@gmail.com

ProtoEnhance est un logiciel destin� � optimiser les protocoles 
d'acquisition scanographique � partir d'une biblioth�que d'images
d'un fant�me anthropomorphique adulte acquise en fonction des 
recommandations du constructeur et des pratiques cliniques de plusieurs
Centres Hospitaliers Universitaires sur le scanner Discovery CT 750HD 
commercialis� par la soci�t� GE HealthCare.

Ce logiciel est r�gi par la licence CeCILL soumise au droit fran�ais et
respectant les principes de diffusion des logiciels libres. Vous pouvez
utiliser, modifier et/ou redistribuer ce programme sous les conditions
de la licence CeCILL telle que diffus�e par le CEA, le CNRS et l'INRIA
sur le site "http://www.cecill.info".

En contrepartie de l'accessibilit� au code source et des droits de copie,
de modification et de redistribution accord�s par cette licence, il n'est
offert aux utilisateurs qu'une garantie limit�e.  Pour les m�mes raisons,
seule une responsabilit� restreinte p�se sur l'auteur du programme,  le
titulaire des droits patrimoniaux et les conc�dants successifs.

A cet �gard  l'attention de l'utilisateur est attir�e sur les risques
associ�s au chargement,  � l'utilisation,  � la modification et/ou au
d�veloppement et � la reproduction du logiciel par l'utilisateur �tant
donn� sa sp�cificit� de logiciel libre, qui peut le rendre complexe �
manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels
avertis poss�dant  des  connaissances  informatiques approfondies.  Les
utilisateurs sont donc invit�s � charger  et  tester  l'ad�quation  du
logiciel � leurs besoins dans des conditions permettant d'assurer la
s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement,
� l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.

Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez
pris connaissance de la licence CeCILL, et que vous en avez accept� les
termes.


*****************************************************************************


COPYRIGHT
Authors: Fran�ois Gardavaud and Hugo Pasquier
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
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.Protocol.Serie;

public class AcquisitionTable extends JTable{
	//****************  DIMENSION  *******************************************************************************************
	private Dimension 	ParametersEditionPanelSize;
	
	//****************  DEFAULTTABLEMODEL  ***********************************************************************************
	DefaultTableModel 	model,
						scoutModel,
						helicalModel;
	
	//************  ARRAYLIST  ************************************************************************************************
	ArrayList 			automAIndice = new ArrayList(),
						collimationIndice = new ArrayList(),
						HRIndice = new ArrayList(),
						kVIndice = new ArrayList(),
						pitchIndice = new ArrayList(),
						rotationIndice = new ArrayList(),
						SFOVIndice = new ArrayList(),
						smAIndice = new ArrayList(),
						typeIndice = new ArrayList();	
	
	//****************  JCHECKBOX  *******************************************************************************************
	public JCheckBox 	checkBox = new JCheckBox();	
	
	//****************  JCOMBOBOX  *******************************************************************************************
	public JComboBox	automAComboBox = new JComboBox(),
						collimationComboBox = new JComboBox(),
						HRComboBox = new JComboBox(),
						kVComboBox = new JComboBox(),
						pitchComboBox = new JComboBox(),
						rotationComboBox = new JComboBox(),
						SFOVComboBox = new JComboBox(),
						smAComboBox = new JComboBox(),
						typeComboBox = new JComboBox();
	
	//****************  SERIE  ***********************************************************************************************
	public Serie 		serie;
	
	//****************  STRING[]  ********************************************************************************************
	public String[] 	pitch = {},
						pitch32 = {"0.531", "0.969", "1.375"},
						pitch64 = {"0.516", "0.984", "1.375"},
						automA = {"Yes", "No"},
						axialRotation = {"0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0", "2.0"},
						collimation = {"20", "40"},
						helicalRotation = {"0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0"},
						HR = {"Yes", "No"},
						kV = {"80", "100", "120", "140"},
						rotation = {},
						SFOV = {"Head", "Large Body", "Medium Body", "Ped Body", "Ped head", "SmallBody", "SmallHead"},  
						smA = {"Yes", "No"},
						type = {"Axial", "Helical"};
		
		
	
	/**************************************************************************************************************************/
	/** 	CONSTRUCTEUR 	**/
	public AcquisitionTable (Serie pSerie,  Dimension ParametersEditionPanelDimension){
		// RECUPERATION DU PROTOCOLE
		serie = pSerie;
		ParametersEditionPanelSize = ParametersEditionPanelDimension;
				
		// DEFINITION D'UN MODEL DE TABLE POUR ACQUISITION HELICOIDALE 
		helicalModel = new DefaultTableModel(new String[]{"Groupe", "Type d'acquisition","Temps de rotation", "Pitch", "Collimation", "HR", "SFOV",
																			"kV", "mA", "AutomA", "SmartmA", "Noise Index", "mA min", "mA max"}, 0);
		// DEFINITION D'UN MODEL DE TABLE POUR SCOUT 
		scoutModel = new DefaultTableModel(new String[]{"Scout", "Angle", "kV", "mA"}, 0);		
				
				
		// SI LA SERIE EST UN SCOUT
		if(serie.getSeriesType().contains("Scout")){
			model = scoutModel;
		}
		else{
			model = helicalModel;
		}	
		
		this.getRows(serie);
		this.setModel(model);

		
		// DEFINITION DES RENDERERS ET DES EDITORS DES DIFFERENTES CELLULES
		if(model != scoutModel){
			this.getColumn("Type d'acquisition").setCellEditor(new DefaultCellEditor(typeComboBox));
			this.getColumn("Temps de rotation").setCellEditor(new  DefaultCellEditor(rotationComboBox));
			this.getColumn("Pitch").setCellEditor(new DefaultCellEditor(pitchComboBox));
			this.getColumn("Collimation").setCellEditor(new DefaultCellEditor(collimationComboBox));
			this.getColumn("HR").setCellEditor(new DefaultCellEditor(HRComboBox));
			this.getColumn("SFOV").setCellEditor(new DefaultCellEditor(SFOVComboBox));
			this.getColumn("AutomA").setCellEditor(new DefaultCellEditor(automAComboBox));
			this.getColumn("SmartmA").setCellEditor(new DefaultCellEditor(smAComboBox));
		}	
		this.getColumn("kV").setCellEditor(new DefaultCellEditor(kVComboBox));
		// ON EMPECHE LA SELECTION DES CELLULES
		this.setCellSelectionEnabled(false);
	}
	
	/**************************************************************************************************************************/
	/** 	METHODE DE REMPLISSAGE DE LA LIGNE DU TABLEAU  		**/
	public void getRows(Serie pSerie){
		for(int g = 0; g <= serie.getNbGroup(); g++){	
			
			// SELECTION DES KV INITIAUX DANS LA COMBOBOX CORRESPONDANTE
			for(int i = 0; i < kV.length; i++ ){			
				if(kV[i].contains(serie.getGroup(g).getKV())){
					kVIndice.add(i);
				}
			}
			// INSTANCIATION DE LA KVCOMBOBOX A PARTIR DE LA LISTE DE KV
			kVComboBox = new JComboBox(kV);
			
		
			if(model == helicalModel){		
				// SELECTION DU TYPE INITIAL DANS LA COMBOBOX CORRESPONDANTE
				for(int i = 0; i < type.length; i++ ){			
					if(type[i].contains(serie.getGroup(g).getGroupType())){
						typeIndice.add(i);
					}
				}
				// INSTANCIATION DE LA TYPECOMBOBOX A PARTIR DE LA LISTE DE TYPE
				typeComboBox = new JComboBox(type);

				if(serie.getGroup(g).getGroupType().contains("Helical")){
					rotation = helicalRotation;
				}
				else{
					rotation = axialRotation;
				}
				// SELECTION DU TPS DE ROTATION INITIAL DANS LA COMBOBOX CORRESPONDANTE
				for(int i = 0; i < rotation.length; i++ ){			
					if(rotation[i].contains(serie.getGroup(g).getTpsRotation())){
						rotationIndice.add(i);
					}
				}
				// INSTANCIATION DE LA ROTATIONCOMBOBOX A PARTIR DE LA LISTE DE TEMPS DE ROTATION
				rotationComboBox = new JComboBox(rotation);
				
				
				// SELECTION DE LA COLLIMATION ET DU PITCH INITIAUX DANS LA COMBOBOX CORRESPONDANTE
				if(serie.getGroup(g).getRows().contains("32")){
					collimationIndice.add(0);
					pitch = pitch32;
					if(serie.getGroup(g).getPitch().contains("10.625")){
						pitchIndice.add(0);
					}
					else if(serie.getGroup(g).getPitch().contains("19.375")){
						pitchIndice.add(1);
					}
					else if(serie.getGroup(g).getPitch().contains("27.5")){
						pitchIndice.add(2);
					}
				}
				else if(serie.getGroup(g).getRows().contains("64")){
					collimationIndice.add(1);
					pitch = pitch64;
					if(serie.getGroup(g).getPitch().contains("20.625")){
						pitchIndice.add(0);
					}
					if(serie.getGroup(g).getPitch().contains("39.375")){
						pitchIndice.add(1);
					}
					if(serie.getGroup(g).getPitch().contains("55")){
						pitchIndice.add(2);
					}
				}
				// INSTANCIATION DE LA COLLIMATIONCOMBOBOX A PARTIR DE LA LISTE DE COLLIMATION
				collimationComboBox = new JComboBox(collimation);		
				// INSTANCIATION DE LA PITCHCOMBOBOX A PARTIR DE LA LISTE DE PITCH
				pitchComboBox = new JComboBox(pitch);		
				
				
				// SELECTION DE LA HR INITIALE DANS LA COMBOBOX CORRESPONDANTE
				if(serie.getGroup(g).getSmartCathodeMode().contains("scDynamicDeflect") && serie.getGroup(g).getSmartCathodeNumDeflections().contains("2")){
					HRIndice.add(0);
				}
				else if(serie.getGroup(g).getSmartCathodeMode().contains("scBiasMode") && serie.getGroup(g).getSmartCathodeNumDeflections().contains("1")){
					HRIndice.add(1);
				}
				// INSTANCIATION DE LA HRCOMBOBOX A PARTIR DE LA LISTE DE COLLIMATION
				HRComboBox = new JComboBox(HR);
				
				
				// SELECTION DE L'AUTO MA ET DU SMA INITIAUX DANS LES COMBOBOX CORRESPONDANTES
				if(serie.getGroup(g).getFlagMA().contains("2")){
					automAIndice.add(0);
					smAIndice.add(0);
				}
				else if(serie.getGroup(g).getFlagMA().contains("1")){
					automAIndice.add(0);
					smAIndice.add(1);
				}
				else if(serie.getGroup(g).getFlagMA().contains("0")){
					automAIndice.add(1);
					smAIndice.add(1);
				}
				// INSTANCIATION DE LA AUTOMACOMBOBOX A PARTIR DE LA LISTE DE AUTOMA
				automAComboBox = new JComboBox(automA);
				// INSTANCIATION DE LA SMACOMBOBOX A PARTIR DE LA LISTE DE SMA
				smAComboBox = new JComboBox(smA);
				
				
				// SELECTION DU SFOV INITIAL DANS LA COMBOBOX CORRESPONDANTE
				if(serie.getGroup(g).getSFOVType().contains("ScanFieldOfViewHeadVCT")){
					SFOVIndice.add(0);
				}
				else if(serie.getGroup(g).getSFOVType().contains("ScanFieldOfViewLargeBodyVCT")){
					SFOVIndice.add(1);
				}
				else if(serie.getGroup(g).getSFOVType().contains("ScanFieldOfViewMediumBodyVCT")){
					SFOVIndice.add(2);
				}
				else if(serie.getGroup(g).getSFOVType().contains("ScanFieldOfViewPedBodyVCT")){
					SFOVIndice.add(3);
				}
				else if(serie.getGroup(g).getSFOVType().contains("ScanFieldOfViewPedHeadVCT")){
					SFOVIndice.add(4);
				}
				else if(serie.getGroup(g).getSFOVType().contains("ScanFieldOfViewSmallBodyVCT")){
					SFOVIndice.add(5);
				}
				else if(serie.getGroup(g).getSFOVType().contains("ScanFieldOfViewSmallHeadVCT")){
					SFOVIndice.add(6);
				}
				
				// INSTANCIATION DE LA SFOVCOMBOBOX A PARTIR DE LA LISTE DE SFOV
				SFOVComboBox = new JComboBox(SFOV);
								
				
				model.addRow(new Object[]{pSerie.getGroup(g).getRecon(0).getTitre(), type[(int) typeIndice.get(g)], rotation[(int) rotationIndice.get(g)], 
						pitch[(int) pitchIndice.get(g)], collimation[(int) collimationIndice.get(g)], HR [(int) HRIndice.get(g)], 
						SFOV[(int) SFOVIndice.get(g)], kV[(int) kVIndice.get(g)], pSerie.getGroup(g).getMA(), automA[(int) automAIndice.get(g)], 
						smA[(int) smAIndice.get(g)], pSerie.getGroup(g).getNI(), pSerie.getGroup(g).getMinma(), pSerie.getGroup(g).getMaxma()});
			}
			else{
				model.addRow(new Object[]{model.getRowCount()+1, pSerie.getGroup(g).getPlane(), pSerie.getGroup(g).getKV(), pSerie.getGroup(g).getMA()});
			}
		}	
	}
	
	/**************************************************************************************************************************/
	/** 	CLASSE DE GESTION DE L'AFFICHAGE DES CELLULES CONTENANT UNE JCHECKBOX  		**/
	public class CheckBoxRenderer extends JCheckBox implements TableCellRenderer{

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
			// SI LA VALEUR EST "YES"
			if(value.toString().contains("Yes")){
				 // LA CHECKBOX EST COCHEE
				 checkBox.setSelected(true);
			}
			// SINON SI LA VALEUR EST "NO"
			else if(value.toString().contains("No")){
				 // LA CHECKBOX EST DECOCHEE
				 checkBox.setSelected(false);
			}
			// SINON SI LA VALEUR EST "0"
			else if(value.toString().contains("0")){
				 // LA CHECKBOX EST DECOCHEE
				 checkBox.setSelected(false);
			}			
			// SINON SI LA VALEUR EST "1"
			else if(value.toString().contains("1")){
				 // LA CHECKBOX EST DECOCHEE
				 checkBox.setSelected(false);
			}
			// SINON SI LA VALEUR EST "NO"
			else if(value.toString().contains("2")){
				 // LA CHECKBOX EST COCHEE
				 checkBox.setSelected(true);
			}
		    return checkBox;
		}
	}
}
