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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;



import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.Protocol.Protocol;
import com.Protocol.Recon;
import com.Protocol.Serie;

public class DosePanel extends JPanel {
	
	/**************************************************************************************************************************/
	/** 	DECLARATION DES VARIABLES 	**/
	//************  BORDER  ***************************************************************************************************
	private Border 		Line = BorderFactory.createLineBorder(new Color(184, 207, 229));
	
	//****************  DICOMIMAGE  ******************************************************************************************
	public DicomImage 	image = new DicomImage(), 
						ZOOM = new DicomImage();
	
	//****************  DIMENSION  *******************************************************************************************
	public Dimension 	imageSize;
	
	//****************  FILE  ************************************************************************************************
	private File 		imageFolder;
	
	//****************  INT  *************************************************************************************************
	public int 			imageIndex,
						sérieProto,
						groupeProto,
						reconProto,
						sérieRef,
						groupeRef,
						reconRef;
	
	//****************  JCHECKBOX  *******************************************************************************************
	public JCheckBox 	tick = new JCheckBox("Sélectionner cette série");
	
	//****************  JFRAME  **********************************************************************************************
	public JFrame 		zoomFrame = new JFrame(), 
						gearFrame = new JFrame();
	
	//****************  JLABEL  **********************************************************************************************
	public JLabel 		doseEstimation = new JLabel(),
						gear = new JLabel(), 
						loupe = new JLabel(); 
	
	//****************  JPANEL  **********************************************************************************************
	private JPanel 		bouttons = new JPanel(), 
						center = new JPanel();
	
	//************  PROTOCOL  *************************************************************************************************
	private Protocol	protocole,
						reference;	
	
	//****************  STRING  **********************************************************************************************
	public String 		folderPath, 
						fenêtrage;
	
	//****************  STRING[]  ********************************************************************************************
	public String[] 	folderContent;
	
	
	/**************************************************************************************************************************/
	/** 	CONSTRUCTEUR  		**/
	public DosePanel(Dimension imageDimension, String pFenêtrage, Protocol pProtocole, Protocol pReference){
		// DEFINITION DU LAYOUT
		this.setLayout(new BorderLayout());
		// RECUPERATION DE LA TAILLE DE L'IMAGE
		imageSize = imageDimension;
		// RECUPERATION DU FENETRAGE
		fenêtrage = pFenêtrage;
		// RECUPERATION DU PROTOCOLE
		protocole = pProtocole;
		// RECUPERATION DE lA REFERENCE
		reference = pReference;
		// DEFINITION DE L'ALIGNEMENT DU LABEL DOSEESTIMATION
		doseEstimation.setHorizontalAlignment(JLabel.CENTER);
		// DEFINITION DE LA POLICE DU LABEL DOSEESTIMATION
		Font doseEstimationFont = new Font("Dialog", Font.BOLD, 16);
		// ASSIGNATION DE LA NOUVELE POLICE AU JLABEL
		doseEstimation.setFont(doseEstimationFont);
		// AJOUT DU LABEL DOSEESTIMATION AU DOSELABEL EN POSITION NORD
		this.add(doseEstimation, BorderLayout.NORTH);
		// AJOUT DE L'IMAGE AU PANEL CENTER
		center.add(image);	
		// AJOUT DU PANEL CENTER AU DOSEPANEL EN POSITION CENTRALE
		this.add(center, BorderLayout.CENTER);
		// AJOUT D'UN TOOLTIPTEXT A l'ICONE GEAR
		gear.setToolTipText("Obtenir les paramètres d'acquisition");
		// AJOUT D'UN MOUSELISTENER A L'ICONE GEAR
		gear.addMouseListener(new gearListener());
		// AJOUT DE L'ICONE GEAR AU PANEL DE BOUTTONS
		bouttons.add(gear);
		// AJOUT D'UN TOOLTIPTEXT A l'ICONE LOUPE
		loupe.setToolTipText("Afficher l'image en taille réelle");
		// AJOUT D'UN MOUSELISTENER A L'ICONE GEAR
		loupe.addMouseListener(new loupeListener());
		// AJOUT DE L'ICONE GEAR AU PANEL DE BOUTTONS
		bouttons.add(loupe);
		// AJOUT DE LA CHECKBOX DE SELECTION DE RECON AU PANEL DE BOUTTONS
		bouttons.add(tick);
		// AJOUT DU PANEL DE BOUTTONS AU DOSEPANEL
		this.add(bouttons, BorderLayout.SOUTH);
	}
	
	/**************************************************************************************************************************/
	/** 	METHODE DE RECUPERATION DES INDICES SGR DU PROTOCOLE ET DE LA REFERENCE		**/
	public void setSGR(int pSérieProto, int pGroupeProto, int pReconProto, int pSérieRef, int pGroupeRef, int pReconRef) {		
		sérieProto = pSérieProto;
		groupeProto = pGroupeProto;
		reconProto = pReconProto;
		sérieRef = pSérieRef;
		groupeRef = pGroupeRef;
		reconRef = pReconRef;		
	}
	
	/**************************************************************************************************************************/
	/** 	METHODE DE CREATION DU PANEL		**/
	public void setPanel() {
		// SI C'EST LA RECON NATIVE
		if(reconProto == 0){
			// AJOUT DU CTDI ET DU PDL AU JLABEL DOSEESTIMATION
			doseEstimation.setText("IDSV = "+reference.getSérie(sérieRef).getGroup(groupeRef).getCtdi().toString()+" mGy / PDL = "+
			reference.getSérie(sérieRef).getGroup(groupeRef).getPDL().toString()+" mGy.cm");			
		}
		// SINON
		else{
			// LE JLABEL DOSEESTIMATION NE CONTIENT PAS D'INFORMATION DE DOSE
			doseEstimation.setText("");
			// SUPPRESSION DE LA JCHECKBOX DE SELECTION DE SERIE
			bouttons.remove(tick);
		}
		// RECUPERATION DU CHEMIN DE LA SEQUENCE D'IMAGES ANTHROPOMORPHIQUES
		folderPath = reference.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathAnthropomorphic();
		// CREATION D'UN DOSSIER A PARTIR DU CHEMIN DE LA sEQUENCE D'IMAGES ANTHROPOMORPHIQUES
		imageFolder = new File(folderPath);
		// ENUMERATION DU NOMBRE D'IMAGES CONTENUES DANS LE DOSSIER
		folderContent = imageFolder.list();
		// SELECTION DE LA PREMIERE IMAGE DU DOSSIER
		imageIndex = 0;
		// AFFICHAGE DE LA PREMIERE IMAGE DU DOSSIER AU BON FENETRAGE
		image.setDicomImage(folderPath+"/"+folderContent[imageIndex], 
							imageSize.getWidth(), fenêtrage);
		// DEFINITION DE LA BORDURE DE LA BORDURE DE L'IMAGE
		image.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
		// CREATION DES ICONES DE PARAMETRES ET DE ZOOM
		gear.setIcon(new ImageIcon("Icons/gear32.png"));
		loupe.setIcon(new ImageIcon("Icons/search32.png"));		
	}
		
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DE L'ICONE DE ZOOM		**/
	class loupeListener  implements MouseListener{
		public void mouseClicked(MouseEvent arg0) {
			// AFFICHAGE DE L'IMAGE L'IMAGE EN COURS AU ON FENETRAGE
			ZOOM.setDicomImage(folderPath+"/"+folderContent[imageIndex], 1d, fenêtrage);
			// AJOUT DE L'IMAGE ZOOMEE A LA FENETRE POPUP DE ZOOM
			zoomFrame.add(ZOOM);
			// AJUSTEMENT DE LA FENETRE A L'IMAGE
			zoomFrame.pack();
			// ON REND FIGE LA TAILLE DE LA FENETRE
			zoomFrame.setResizable(false);
			// CENTRAGE DE LA FENETRE PAR RAPPORT A L'ECRAN
			zoomFrame.setLocationRelativeTo(null);
			// AFFICHAGE DE LA FENETRE
			zoomFrame.setVisible(true);
			// ON REND LA FENETRE PRIORITAIRE
			zoomFrame.setAlwaysOnTop(true);
			// FERMETURE DE LA FEMETRE LORS D'UN CLIC SUR LA CROIX ROUGE
			zoomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						
		}
		public void mouseEntered(MouseEvent arg0) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));	
		}
		public void mouseExited(MouseEvent arg0) {
			setCursor(Cursor.getDefaultCursor());
		}
		public void mousePressed(MouseEvent arg0) {	}
		public void mouseReleased(MouseEvent arg0) { }
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DE L'ICONE DE PARAMETRES		**/
	class gearListener  implements MouseListener{
		public void mouseClicked(MouseEvent arg0) {
			
			// DEFINITION DE LA BORDURE DE l'IQPANEL DEDIE A LA LCD
			TitledBorder panelBorder = BorderFactory.createTitledBorder(Line, "Optimisation des paramètres");	
			// DEFINITION DE LA JUSTIFICATION DE LA BORDURE DE l'IQPANEL DEDIE A LA MTF
			panelBorder.setTitleJustification(TitledBorder.CENTER);
			
			// INSTANCIATION D'UN JLABEL CORRESPONDANT AU TITRE
			JLabel acquisitionTitle = new JLabel("Paramètres d'acquisition");		
			// INSTANCIATION DES TITRES DE COLONNE
			String acquisitionParameter[] = {"", "Temps de rotation (s)", "Avancée de table (mm/rotation)", "Collimation (mm)",
											"HR", "SFOV", "kV", "mA", "Smart mA", "Noise Index", "mA min", "mA max" };
			
			// RECUPERATION DES PARAMETRES D'ACQUISITION DEs DEUX PROTOCOLES
			String rotationTime0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getTpsRotation();
			String tableSpeed0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getPitch() ;
			String collimation0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getRows();
			String HR0; 
			if(protocole.getSérie(sérieProto).getGroup(groupeProto).getSmartCathodeMode().contains("scDynamicDeflect") 
				&& protocole.getSérie(sérieProto).getGroup(groupeProto).getSmartCathodeNumDeflections().contains("2")){
				HR0 = "Yes";
			}
			else{
				HR0 = "No";
			}
			String SFOVType0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getSFOVType();
			String SFOVSize0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getSFOVSize();
			String kV0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getKV();
			String mA0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getMA();
			String smA0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getFlagMA();
			String NI0 = null;
			String mAmin0 = null;
			String mAmax0 = null;
			if (protocole.getSérie(sérieProto).getGroup(groupeProto).getFlagMA().contains("0")){
				smA0 = null;
				NI0 = null;
				mAmin0 = null;
				mAmax0 = null;
			}
			else{
				NI0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getNI();
				mAmin0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getMinma();
				mAmax0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getMaxma();
				mA0 = null;
			}
			
			String rotationTime1 = reference.getSérie(sérieRef).getGroup(groupeRef).getTpsRotation();
			String tableSpeed1 = reference.getSérie(sérieRef).getGroup(groupeRef).getPitch() ;
			String collimation1 = reference.getSérie(sérieRef).getGroup(groupeRef).getRows();
			String HR1; 
			if(reference.getSérie(sérieRef).getGroup(groupeRef).getSmartCathodeMode() .contains("scDynamicDeflect")
				&& reference.getSérie(sérieRef).getGroup(groupeRef).getSmartCathodeNumDeflections().contains("2")){
				HR1 = "Yes";
			}
			else{
				HR1 = "No";
			}
			String SFOVType1 = reference.getSérie(sérieRef).getGroup(groupeRef).getSFOVType();
			String SFOVSize1 = reference.getSérie(sérieRef).getGroup(groupeRef).getSFOVSize();
			String kV1 = reference.getSérie(sérieRef).getGroup(groupeRef).getKV();
			String mA1 = reference.getSérie(sérieRef).getGroup(groupeRef).getMA();
			String smA1 = reference.getSérie(sérieRef).getGroup(groupeRef).getFlagMA();
			String NI1 = reference.getSérie(sérieRef).getGroup(groupeRef).getNI();
			String mAmin1 = reference.getSérie(sérieRef).getGroup(groupeRef).getMinma();
			String mAmax1 = reference.getSérie(sérieRef).getGroup(groupeRef).getMaxma();
			
			// INSTANCIATION DES TITRES DES LIGNES ET RECUPERATION DES VALEURS 
			Object[][] acquisitionData = {{"Avant optimisation", 	rotationTime0, 
																	tableSpeed0,
																	collimation0,
																	HR0,
																	SFOVType0+" ( "+SFOVSize0+" )",
																	kV0, 
																	mA0,
																	smA0,
																	NI0,
																	mAmin0,
																	mAmax0},													
											{"Après optimisation",	rotationTime1, 
																	tableSpeed1,
																	collimation1,
																	HR1,
																	SFOVType1+" ( "+SFOVSize1+" )",
																	kV1, 
																	mA1,
																	smA1,
																	NI1,
																	mAmin1,
																	mAmax1}};
			
			// CREATION DU TABLEAU
			JTable acquisitionTable = new JTable(acquisitionData, acquisitionParameter);
			
			// INSTANCIATION D'UN JLABEL CORRESPONDANT AU TITRE
			JLabel reconTitle = new JLabel("Paramètres de reconstruction");		
			// INSTANCIATION DES TITRES DE COLONNE
			String reconParameter[] = {"", "Filtre de reconstruction", "Type de reconstruction", "Epaisseur de coupe (mm)", 
											"Intervalle (mm)", "ASIR", "IQEnhance"};
			
			String Filter0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getRecon(reconProto).getFilter();
			String ReconType0;
			if(protocole.getSérie(sérieProto).getGroup(groupeProto).getRecon(reconProto).getTyperecon().contains("0")){
				ReconType0 = "Full";
			}
			else{
				ReconType0 = "Plus";
			}
			String Thick0;
			String Inter0;
			String Thick1 = reference.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getThick();
			String Inter1 = reference.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getInter();
			if(reconProto == 0){
				Thick0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getThick();
				Inter0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getInter();
				Thick1 = reference.getSérie(sérieRef).getGroup(groupeRef).getThick();
				Inter1 = reference.getSérie(sérieRef).getGroup(groupeRef).getInter();
			}
			else{
				Thick0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getRecon(reconProto).getThick();
				Inter0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getRecon(reconProto).getInter();
				Thick1 = reference.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getThick();
				Inter1 = reference.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getInter();
			}
			String ASIR0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getRecon(reconProto).getAsirConfig();
			String Iqe0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getRecon(reconProto).getIqe();
			String GSI0 = protocole.getSérie(sérieProto).getGroup(groupeProto).getRecon(reconProto).getGsi();
			
			String Filter1 = reference.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getFilter();
			String ReconType1;
			if(reference.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getTyperecon().contains("0")){
				ReconType1 = "Full";
			}
			else{
				ReconType1 = "Plus";
			}
				
			String ASIR1 = reference.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getAsirConfig();
			String Iqe1 = reference.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getIqe();
			String GSI1 = reference.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getGsi();
					
			// INSTANCIATION DES TITRES DES LIGNES ET RECUPERATION DES VALEURS 
			Object[][] reconData = 	{{"Avant optimisation", Filter0,
															ReconType0,
															Thick0,
															Inter0,
															ASIR0,
															Iqe0,
															GSI0},																	
									{"Après optimisation",	Filter1,
															ReconType1,
															Thick1,
															Inter1,
															ASIR1,
															Iqe1,
															GSI1}};
			
			// CREATION DU TABLEAU
			JTable reconTable = new JTable(reconData, reconParameter);
			
			// CREATION DU JPANEL PARAMETERSCHANGE CONTENANT LES DEUX TABLEAUX
			JPanel parametersChange = new JPanel();
			
			// SI LA RECON A OPTIMISER EST NATIVE
			if( reconProto == 0){
				// DEFINITION DU LAYOUT DU JPANEL PARAMETERSCHANGE
				parametersChange.setLayout(new GridLayout(7, 1));
				// AJOUT DU TABLEAU CONTENANT LES PARAMETRES D'ACQUISITION ET D'UN JLABEL DE SEPARATION AU PANEL PARAMETERSCHANGE
				parametersChange.add(acquisitionTitle);
				parametersChange.add(acquisitionTable.getTableHeader());
				parametersChange.add(acquisitionTable);
				parametersChange.add(new JLabel());
			}
			else{
				parametersChange.setLayout(new GridLayout(3, 1));
			}
			
			// AJOUT DU TABLEAU CONTENANT LES PARAMETRES D'ACQUISITION
			parametersChange.add(reconTitle);
			parametersChange.add(reconTable.getTableHeader());
			parametersChange.add(reconTable);
			
			// ON REND LES TABLEAUX NON OPAQUE
			acquisitionTable.setOpaque(false);
			reconTable.setOpaque(false);
			
			// AJUSTEMENT DE LA TAILLE DES COLONNES EN FONCTION DU CONTENU
			acquisitionTable.getColumnModel().getColumn(0).setPreferredWidth(18*6);
			acquisitionTable.getColumnModel().getColumn(1).setPreferredWidth(21*6);
			acquisitionTable.getColumnModel().getColumn(2).setPreferredWidth(30*6);
			acquisitionTable.getColumnModel().getColumn(3).setPreferredWidth(18*6);	
			acquisitionTable.getColumnModel().getColumn(5).setPreferredWidth(38*6);	
			
			// AJUSTEMENT DE LA TAILLE DES COLONNES EN FONCTION DU CONTENU
			reconTable.getColumnModel().getColumn(0).setPreferredWidth(18*6);
			reconTable.getColumnModel().getColumn(1).setPreferredWidth(23*6);
			reconTable.getColumnModel().getColumn(2).setPreferredWidth(22*6);
			reconTable.getColumnModel().getColumn(3).setPreferredWidth(25*6);
			reconTable.getColumnModel().getColumn(4).setPreferredWidth(15*6);
			
			// DEFINITION DE LA BORDURE DU PANEL
			parametersChange.setBorder(panelBorder);
						
			// AJOUT DU JPANEL PARAMETRES A LA FENETRE PARAMETRESCHANGE
			gearFrame.add(parametersChange);
			// AJUSTEMENT AUTOMATIQUE DE LA FENETRE
			gearFrame.pack();
			// ON REND LA FENETRE NON EDITABLE
			gearFrame.setResizable(false);
			// CENTRAGE DE LA FENETRE
			gearFrame.setLocationRelativeTo(null);
			// AFFICHAGE DE LA FENETRE
			gearFrame.setVisible(true);
			// ON REND LA FENETRE PRIORITAIRE
			zoomFrame.setAlwaysOnTop(true);
			// FERMETURE DE LA FEMETRE LORS D'UN CLIC SUR LA CROIX ROUGE
			gearFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		}
		public void mouseEntered(MouseEvent arg0) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));	
		}
		public void mouseExited(MouseEvent arg0) {
			setCursor(Cursor.getDefaultCursor());
		}
		public void mousePressed(MouseEvent arg0) {	}
		public void mouseReleased(MouseEvent arg0) { }
	}
}
