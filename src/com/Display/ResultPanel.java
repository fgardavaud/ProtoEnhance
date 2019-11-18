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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.Protocol.Protocol;

public class ResultPanel extends JPanel {
	
	/**************************************************************************************************************************/
	/** 	DECLARATION DES VARIABLES 	**/
	
	//************  BORDER  ***************************************************************************************************
	private Border 		Line = BorderFactory.createLineBorder(new Color(184, 207, 229));
	
	//************  DIMENSION  ************************************************************************************************
	private Dimension 	resultPanelSize, doseDimension, IQDimension;
	
	//************  DOSEPANEL  ************************************************************************************************
	public DosePanel 	lowDoseDicom,
						mediumDoseDicom,
						highDoseDicom;
	
	//************  INT  ******************************************************************************************************
	private int 		sérieProto,
						groupeProto,
						reconProto,
						sérieRef,
						groupeRef,
						reconRef;
	
	//************  IQPANEL  **************************************************************************************************
	public IQPanel 		mtfPanel,
						lcdPanel;
	
	//************  JBUTTON  **************************************************************************************************
	private JButton 	cerveau = new JButton (),
						os = new JButton (),
						mediastin = new JButton (),
						poumon = new JButton (),
						abdo = new JButton (),
						rachis = new JButton ();
	
	//************  JFRAME  ***************************************************************************************************
	private JFrame		MTFFrame,
						LCDFrame;
	
	//************  JPANEL  ***************************************************************************************************
	private JPanel 		dose = new JPanel(),
						IQ = new JPanel(),
						west = new JPanel(),
						center = new JPanel();
	
	//************  PROTOCOL  *************************************************************************************************
	private Protocol	protocole,	
						referenceLowDose, 
						referenceStandard, 
						referenceHighIQ;
	
	//************  STRING  ***************************************************************************************************
	private String 		type = new String(),
						selectedImage = new String(), 
						fenêtrage =  "original";
	
	
	/**************************************************************************************************************************/
	/** 	CONSTRUCTEUR 	**/
	public ResultPanel(Protocol pProtocole, String pSGRproto, Protocol pReferenceLowDose, Protocol pReferenceStandard, Protocol pReferenceHighIQ, 
			String pSGRref, Dimension resultPanelDimension){
		
		// RECUPERATION DE LA DIMENSION DU RESULTPANEL
		resultPanelSize = resultPanelDimension;
		// DEFINITION DE LA DIMENSION DU COMPOSANT "DOSE" DU RESULTPANEL
		doseDimension = new Dimension((int)resultPanelSize.getWidth()*2/7, (int)resultPanelSize.getWidth()*2/7);	
		
		// RECUPERATION DU PROTOCOLE
		protocole = pProtocole;

		// RECUPERATION DES TROIS PROTOCOLES DE REFERENCE
		referenceLowDose = pReferenceLowDose;																		
		referenceStandard = pReferenceStandard;
		referenceHighIQ = pReferenceHighIQ;
		
		// RECUPERATION DES INDICES S/G/R DU PROTOCOLE
		sérieProto = Integer.parseInt(((String) pSGRproto).substring(0, 1));
		groupeProto = Integer.parseInt(((String) pSGRproto).substring(1, 2));
		reconProto = Integer.parseInt(((String) pSGRproto).substring(2));
		
		// RECUPERATION DES INDICES S/G/R DES REFERENCES
		sérieRef = Integer.parseInt(((String) pSGRref).substring(0, 1));
		groupeRef = Integer.parseInt(((String) pSGRref).substring(1, 2));
		reconRef = Integer.parseInt(((String) pSGRref).substring(2));
		
		// CREATION D'UN DOSEPANEL POUR CHAQUE PROTOCOLE DE REFERENCE
		lowDoseDicom = new DosePanel(doseDimension, fenêtrage, protocole, referenceLowDose);														
		mediumDoseDicom = new DosePanel(doseDimension, fenêtrage, protocole, referenceStandard);
		highDoseDicom = new DosePanel(doseDimension, fenêtrage, protocole, referenceHighIQ);		
		
		// INSTANCIATION DE LA BORDURE DU JLABEL AFFICHANT L'ESTIMATION DE DOSE
		TitledBorder doseEstimationBorder;										
				
		// DEFINITION DES INDICES SGR POUR LES 3 DOSEPANELS
		lowDoseDicom.setSGR(sérieProto, groupeProto, reconProto, sérieRef, groupeRef, reconRef);
		mediumDoseDicom.setSGR(sérieProto, groupeProto, reconProto, sérieRef, groupeRef, reconRef);
		highDoseDicom.setSGR(sérieProto, groupeProto, reconProto, sérieRef, groupeRef, reconRef);
		
		// DEFINITION DES TROIS DOSEPANEL
		lowDoseDicom.setPanel();
		mediumDoseDicom.setPanel();
		highDoseDicom.setPanel();
			
		// DEFINITION DE LA BORDURE DU DOSEPANEL LOWDOSE
		doseEstimationBorder = BorderFactory.createTitledBorder(Line, "Série \"Basse Dose\"");	
		// DEFINITION DE LA JUSTIFICATION DU TITRE DE LA BORDURE DU DOSEPANEL LOWDOSE
		doseEstimationBorder.setTitleJustification(TitledBorder.CENTER);
		// APPLICATION DE LA BORDURE AU DOSEPANEL LOWDOSE
		lowDoseDicom.doseEstimation.setBorder(doseEstimationBorder);												
		// DEFINITION DE LA BORDURE DU DOSEPANEL STANDARD
		doseEstimationBorder = BorderFactory.createTitledBorder(Line, "Série \"Standard\"");
		// DEFINITION DE LA JUSTIFICATION DU TITRE DE LA BORDURE DU DOSEPANEL STANDaRD
		doseEstimationBorder.setTitleJustification(TitledBorder.CENTER);
		// APPLICATION DE LA BORDURE AU DOSEPANEL LOWDOSE
		mediumDoseDicom.doseEstimation.setBorder(doseEstimationBorder);
		// DEFINITION DE LA BORDURE DU DOSEPANEL HIGHIQ
		doseEstimationBorder = BorderFactory.createTitledBorder(Line, "Série \"Haute Qualité d'Image\"");
		// DEFINITION DE LA JUSTIFICATION DU TITRE DE LA BORDURE DU DOSEPANEL HIGHIQ
		doseEstimationBorder.setTitleJustification(TitledBorder.CENTER);
		// APPLICATION DE LA BORDURE AU DOSEPANEL LOWDOSE
		highDoseDicom.doseEstimation.setBorder(doseEstimationBorder);
					
		// AJOUT D'UN MOUSELISTENER A L'IMAGE DES DOSEPANELS
		lowDoseDicom.image.addMouseListener(new lowDoseDicomListener());
		mediumDoseDicom.image.addMouseListener(new mediumDoseDicomListener());
		highDoseDicom.image.addMouseListener(new highDoseDicomListener());
		
		// AJOUT D'UN MOUSEWHEELLISTENER AU ZOOM DES DOSEPANELS
		lowDoseDicom.ZOOM.addMouseWheelListener(new lowDoseZoomListener());		
		mediumDoseDicom.ZOOM.addMouseWheelListener(new mediumDoseZoomListener());
		highDoseDicom.ZOOM.addMouseWheelListener(new highDoseZoomListener());
		
		// AJOUT D'UN WINDOWLISTENER AU FENETRE DE ZOOM DES DOSEPANELS
		lowDoseDicom.zoomFrame.addWindowListener(new zoomFrameListener());											
		mediumDoseDicom.zoomFrame.addWindowListener(new zoomFrameListener());
		highDoseDicom.zoomFrame.addWindowListener(new zoomFrameListener());
		
		// AJOUT D'UN ACTIONLISTENER AU TICK DES DOSEPANELS
		lowDoseDicom.tick.addActionListener(new lowDoseTickListener());												 		
		mediumDoseDicom.tick.addActionListener(new mediumDoseTickListener());												
		highDoseDicom.tick.addActionListener(new highDoseTickListener());
		
		// AJOUTE DES TROIS DOSEPANELS AU COMPOSANT "DOSE" DU RESULTPANEL
		dose.add(lowDoseDicom);																						
		dose.add(mediumDoseDicom);
		dose.add(highDoseDicom);		
		
		// DEFINITION DE LA DIMENSION DU COMPOSANT "IQ" DU RESULTPANEL
		IQDimension = new Dimension((int)resultPanelSize.getHeight()/4, (int)resultPanelSize.getHeight()/4);	
		// CREATION DE DEUX IQPANELS DEDIES A LA MTF ET A LA LCD
		mtfPanel = new IQPanel(IQDimension, fenêtrage);																
		lcdPanel = new IQPanel(IQDimension, fenêtrage);
		
		// AJOUT DES DEUX DOSEPANEL AU COMPOSANT "IQ"
		IQ.add(mtfPanel);	
		IQ.add(lcdPanel);
		
		// AJOUT D'UN MOUSELISTENER AUX 2 DOSEPANELS
		mtfPanel.image.addMouseListener(new MTFListener());																			
		lcdPanel.image.addMouseListener(new LCDListener());	
		
		// DEFINITION DU LAYOUT DU COMPOSANT "WEST"
		west.setLayout(new BorderLayout());		
		// AJOUT DES COMPOSANTS "DOSE" ET "IQ" AU COMPOSANT "WEST"
		west.add(dose, BorderLayout.NORTH);																			
		west.add(IQ, BorderLayout.CENTER);
		
		// DEFINITION DU LAYOUT DU COMPOSANT "CENTER"
		center.setLayout(new GridLayout(6,1));
		
		// AJOUT D'UN ACTIONLISTENER AUX BOUTONS DE FENETRAGE
		cerveau.addActionListener(new cerveauAction());	
		os.addActionListener(new osAction());
		poumon.addActionListener(new poumonAction());
		mediastin.addActionListener(new mediastinAction());			
		abdo.addActionListener(new abdoAction());
		rachis.addActionListener(new rachisAction());
		
		// RECUPERATION DE L'IMAGE DE CHAQUE BOUTON DE FENETRAGE
		Image cerveauImage = java.awt.Toolkit.getDefaultToolkit().getImage("Window/cerveau.jpg");
		Image osImage = java.awt.Toolkit.getDefaultToolkit().getImage("Window/os.jpg");
		Image poumonImage = java.awt.Toolkit.getDefaultToolkit().getImage("Window/poumon.jpg");
		Image mediastinImage = java.awt.Toolkit.getDefaultToolkit().getImage("Window/mediastin.jpg");
		Image abdoImage = java.awt.Toolkit.getDefaultToolkit().getImage("Window/abdomen.jpg");
		Image rachisImage = java.awt.Toolkit.getDefaultToolkit().getImage("Window/rachis.jpg");
		
		// APPLICATION DE L'IMAGE A CHAQUE BOUTON DE FENETRAGE
		cerveau.setIcon(new ImageIcon(Scale(toBuff(cerveauImage), resultPanelSize.getHeight()/7)));
		os.setIcon(new ImageIcon(Scale(toBuff(osImage), resultPanelSize.getHeight()/7)));
		poumon.setIcon(new ImageIcon(Scale(toBuff(poumonImage), resultPanelSize.getHeight()/7)));
		mediastin.setIcon(new ImageIcon(Scale(toBuff(mediastinImage), resultPanelSize.getHeight()/7)));
		abdo.setIcon(new ImageIcon(Scale(toBuff(abdoImage), resultPanelSize.getHeight()/7)));
		rachis.setIcon(new ImageIcon(Scale(toBuff(rachisImage), resultPanelSize.getHeight()/7)));
		
		// AJOUT D'UN TOOLTIPTEXT A CHAQUE BOUTON DE FENETRAGE
		cerveau.setToolTipText("Cerveau");
		os.setToolTipText("Os");
		poumon.setToolTipText("Poumon");
		mediastin.setToolTipText("Médiastin");
		abdo.setToolTipText("Abdomen");
		rachis.setToolTipText("Rachis");
		
		// AJOUT DES BOUTONS DE FENETRAGE AU COMPOSANT "CENTER"
		center.add(cerveau);																						
		center.add(os);
		center.add(poumon);
		center.add(mediastin);
		center.add(abdo);
		center.add(rachis);
		
		// AJOUT D'UN MOUSEWHEELLISTENER AU RESULTPANEL
		this.addMouseWheelListener(new scrollListener());
		// DEFINITION DU LAYOUT DU RESULTPANEL
		this.setLayout(new BorderLayout());	
		// AJOUT DES COMPOSANT "WEST" ET "CENTER" AU DOSEPANEL
		this.add(west, BorderLayout.WEST);																			
		this.add(center, BorderLayout.CENTER);
		// DEFINITION DE LA DIMENSION DU RESULTPANEL
		this.setPreferredSize(resultPanelSize);																		
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU TICK DU LOWDOSEDICOM	**/
	class lowDoseTickListener  implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// ON DECOCHE LES TICKS DES DOSEPANELS STANDARD ET HIGHIQ
			mediumDoseDicom.tick.setSelected(false);																
			highDoseDicom.tick.setSelected(false);	
		}
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU LOWDOSEDICOM	**/
	class lowDoseDicomListener  implements MouseListener{
		public void mouseClicked(MouseEvent arg0) {
			// ON DEFINIE LA SELECTEDIMAGE
			selectedImage = "lowDose";			
			// ON APPLIQUE UNE BORDURE ORANGE A L'IMAGE SELECTIONNEE ET UNE BORDURE NOIRE AUX DEUX AUTRES
			lowDoseDicom.image.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.orange));				
			mediumDoseDicom.image.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
			highDoseDicom.image.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
			// AFFICHAGE DES DEUX IQPANELS
			mtfPanel.setPanel(referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF());											
			lcdPanel.setPanel(referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD());	
		}
		
		public void mouseEntered(MouseEvent arg0) {}		
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {	}
		public void mouseReleased(MouseEvent arg0) { }

	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU TICK DU MEDIUMDOSEDICOM	**/
	class mediumDoseTickListener  implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// ON DECOCHE LES TICKS DES DOSEPANELS LOWDOSE ET HIGHIQ
			lowDoseDicom.tick.setSelected(false);																	
			highDoseDicom.tick.setSelected(false);	
		}
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU MEDIUMDOSEDICOM	**/
	class mediumDoseDicomListener  implements MouseListener{
		public void mouseClicked(MouseEvent arg0) {
			// ON DEFINIE LA SELECTEDIMAGE
			selectedImage = "mediumDose";		
			// ON APPLIQUE UNE BORDURE ORANGE A L'IMAGE SELECTIONNEE ET UNE BORDURE NOIRE AUX DEUX AUTRES
			mediumDoseDicom.image.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.orange));				
			lowDoseDicom.image.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
			highDoseDicom.image.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
			// AFFICHAGE DES DEUX IQPANELS
			mtfPanel.setPanel(referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF());											
			lcdPanel.setPanel(referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD());
			
		}
		
		public void mouseEntered(MouseEvent arg0) {}		
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {	}
		public void mouseReleased(MouseEvent arg0) { }

	}
	

	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU TICK DU HIGHDOSEDICOM	**/
	class highDoseTickListener  implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// ON DECOCHE LES TICKS DES DOSEPANELS LOWDOSE ET STANDARD
			mediumDoseDicom.tick.setSelected(false);
			lowDoseDicom.tick.setSelected(false);	
		}
	}


	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU HIGHDOSEDICOM	**/
	class highDoseDicomListener  implements MouseListener{
		public void mouseClicked(MouseEvent arg0) {
			// ON DEFINIE LA SELECTEDIMAGE
			selectedImage = "highDose";
			// ON APPLIQUE UNE BORDURE ORANGE A L'IMAGE SELECTIONNEE ET UNE BORDURE NOIRE AUX DEUX AUTRES
			highDoseDicom.image.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.orange));
			mediumDoseDicom.image.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
			lowDoseDicom.image.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
			// AFFICHAGE DES DEUX IQPANELS
			mtfPanel.setPanel(referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF());											
			lcdPanel.setPanel(referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD());
		}
				
		public void mouseEntered(MouseEvent arg0) {}		
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {	}
		public void mouseReleased(MouseEvent arg0) { }

	}


	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU COMPOSANT MTF	**/
	class MTFListener  implements MouseListener{
		public void mouseClicked(MouseEvent arg0) {
			// DEFINITION DE LA BORDURE DE l'IQPANEL DEDIE A LA MTF
			TitledBorder panelBorder = BorderFactory.createTitledBorder(Line, "CATPHAN 600 - Module CTP591");
			// DEFINITION DE LA JUSTIFICATION DE LA BORDURE DE L'IQPANEL DEDIE A LA MTF
			panelBorder.setTitleJustification(TitledBorder.CENTER);
			
			// INSTANCIATION  DES TROIS GRANDEURS DE RESOLUTION SPATIALE
			String resoZ;
			String resoX;
			String resoY;
			
			// RECUPERATION DES VALEURS DE RESOLUTION SPATIALE DANS LES TROIS DIMENSIONS EN FONCTION DE l'IMAGE SELECTIONNEE
			if (selectedImage == "lowDose" ){
				resoZ = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getResoZ();
				resoX = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getResoX();
				resoY = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getResoY();
			}
			else if (selectedImage == "mediumDose"){
				resoZ = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getResoZ();
				resoX = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getResoX();
				resoY = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getResoY();			
			}
			else{
				resoZ = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getResoZ();
				resoX = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getResoX();
				resoY = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getResoY();
			}
			
			// INSTANCIATION DU JLABEL REZOZTITLE CORRESPONDANT AU TITRE DU TABLEAU
			JLabel resoZTitle = new JLabel("Résolution suivant l'axe Z");
			// INSTANCIATION DES TITRES DES LIGNES
			String resoZParameter[] = {"SSP (mm)", "Diamètre de la bille (mm)"};
			// RECUPERATION DES VALEURS DE RESO SUIVANT Z
			Object[][] resoZData = 	{{resoZ, "0,28"}};
			// CREATION DU TABLEAU
			JTable resoZTable = new JTable(resoZData, resoZParameter);
			
			// INSTANCIATION DU JLABEL REZOZTITLE CORRESPONDANT AU TITRE DE LA COLONNE
			JLabel resoSpatialeTitle = new JLabel("Résolution spatiale");
			// INSTANCIATION DES TITRES DES LIGNES
			String resoSpatialeParameter[] = {"", "Axe X", "Axe Y"};
			// RECUPERATION DES VALEURS DE RESO SUIVANT Z
			Object[][] resoSpatialeData = 	{{"FDP (mm)", resoX, resoY}};
			// CREATION DU TABLEAU
			JTable resoSpatialeTable = new JTable(resoSpatialeData, resoSpatialeParameter);
			
			// DEFINITION DE LA TAILLE DE LA 2eme COLONNE
			resoZTable.getColumnModel().getColumn(1).setPreferredWidth(25*6);
			// ON DEFINI LES TABLEAU COMME NON OPAQUES
			resoZTable.setOpaque(false);
			resoSpatialeTable.setOpaque(false);
			// INSTANCIATION D'UN NOUVEAU JPANEL
			JPanel panel = new JPanel();
			// DEFINITION DU LAYOUT DU JPANEL PANEL
			panel.setLayout(new GridLayout(7, 1));
			// DEFINITION DE LA BORDURE DU JPANEL PANEL
			panel.setBorder(panelBorder);
			// AJOUT DU TITRE (REZOZ) AU PANEL
			panel.add(resoZTitle);
			// AJOUT DU HEADER AU PANEL
			panel.add(resoZTable.getTableHeader());
			// AJOUT DU TABLEAU AU PANEL
			panel.add(resoZTable);
			// AJOUT D'UN JLABEL POUR SEPARER LES DEUX TABLEAUX
			panel.add(new JLabel());
			// AJOUT DU TITRE (REZOSPATIALE) AU PANEL
			panel.add(resoSpatialeTitle);
			// AJOUT DU HEADER AU JPANEL
			panel.add(resoSpatialeTable.getTableHeader());
			// AJOUT DU TABLEU AU PANEL
			panel.add(resoSpatialeTable);
			
			// INSTANCIATION D'UNE JFRAME DEDIEE A LA MTF
			MTFFrame = new JFrame();
			// AJOUT DU PANEL CONTENANT LES TABLEAU
			MTFFrame.add(panel);
			// AJUTSEMENT AUTOMATIQUE DE LA FENETRE
			MTFFrame.pack();
			// ON FIGE LA TAILLE DE LA FENETRE
			MTFFrame.setResizable(false);
			// CENTRAGE DE LA FENETRE
			MTFFrame.setLocationRelativeTo(null);
			// AFFICHAGE DE LA FENETRE
			MTFFrame.setVisible(true);
			// FERMETUR DE LA FENETRE LORS D'UN CLIC SUR LA CROIX ROUGE
			MTFFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
		}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}


	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU COMPOSANT LCD	**/
	class LCDListener  implements MouseListener{
		public void mouseClicked(MouseEvent arg0) {
			// DEFINITION DE LA BORDURE DE l'IQPANEL DEDIE A LA LCD
			TitledBorder panelBorder = BorderFactory.createTitledBorder(Line, "CATPHAN 600 - Module CTP486");	
			// DEFINITION DE LA JUSTIFICATION DE LA BORDURE DE l'IQPANEL DEDIE A LA MTF
			panelBorder.setTitleJustification(TitledBorder.CENTER);
			
			// INSTANCIATION  DES TROIS GRANDEURS DE BRUIT ET UNIFORMITE
			String SNR0;
			String SNR1;
			String SNR2;
			String SNR3;
			String SNR4;
			String SNR5;
			String Bruit0;
			String Bruit1;
			String Bruit2;
			String Bruit3;
			String Bruit4;
			String Bruit5;
			String Uniformité0;
			String Uniformité1;
			String Uniformité2;
			String Uniformité3;
			String Uniformité4;
			String Uniformité5;
			
			// RECUPERATION DES VALEURS DE BRUIT ET UNIFORMITE EN FONCTION DE l'IMAGE SELECTIONNEE
			if (selectedImage == "lowDose" ){
				SNR0 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR0();
				SNR1 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR1();
				SNR2 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR2();
				SNR3 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR3();
				SNR4 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR4();
				SNR5 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR5();
				Bruit0 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit0();
				Bruit1 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit1();
				Bruit2 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit2();
				Bruit3 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit3();
				Bruit4 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit4();
				Bruit5 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit5();
				Uniformité0 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité0();
				Uniformité1 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité1();
				Uniformité2 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité2();
				Uniformité3 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité3();
				Uniformité4 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité4();
				Uniformité5 = referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité5();		
			}	
			else if (selectedImage == "mediumDose"){
				SNR0 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR0();
				SNR1 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR1();
				SNR2 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR2();
				SNR3 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR3();
				SNR4 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR4();
				SNR5 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR5();
				Bruit0 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit0();
				Bruit1 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit1();
				Bruit2 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit2();
				Bruit3 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit3();
				Bruit4 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit4();
				Bruit5 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit5();
				Uniformité0 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité0();
				Uniformité1 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité1();
				Uniformité2 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité2();
				Uniformité3 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité3();
				Uniformité4 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité4();
				Uniformité5 = referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité5();
			}				
			else{
				SNR0 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR0();
				SNR1 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR1();
				SNR2 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR2();
				SNR3 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR3();
				SNR4 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR4();
				SNR5 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getSNR5();
				Bruit0 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit0();
				Bruit1 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit1();
				Bruit2 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit2();
				Bruit3 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit3();
				Bruit4 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit4();
				Bruit5 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getBruit5();
				Uniformité0 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité0();
				Uniformité1 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité1();
				Uniformité2 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité2();
				Uniformité3 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité3();
				Uniformité4 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité4();
				Uniformité5 = referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getUniformité5();
			}
			
			// INSTANCIATION D'UN JLABEL CORRESPONDANT AU TITRE
			JLabel bruitTitle = new JLabel("Paramètres de bruit");		
			// INSTANCIATION DES TITRES DE COLONNE
			String bruitParameter[] = {"", "ROI 0", "ROI 1", "ROI 2", "ROI 3", "ROI 4", "ROI 5"};
			// INSTANCIATION DES TITRES DES LIGNES ET RECUPERATION DES VALEURS 
			Object[][] bruitData = 	{{"Rapport Signal/Bruit", SNR0, SNR1, SNR2, SNR3, SNR4, SNR5},
									{"Bruit (%)",Bruit0, Bruit1, Bruit2, Bruit3, Bruit4, Bruit5}};
			// CREATION DU TABLEAU
			JTable bruitTable = new JTable(bruitData, bruitParameter);
				
			// INSTANCIATION D'UN JLABEL CORRESPONDANT AU TITRE
			JLabel uniformitéTitle = new JLabel("Uniformité");
			// INSTANCIATION DES TITRES DE COLONNE
			String uniformitéParameter[] = {"", "ROI 0", "ROI 1", "ROI 2", "ROI 3", "ROI 4", "ROI 5"};
			// INSTANCIATION DES TITRES DES LIGNES ET RECUPERATION DES VALEURS 
			Object[][] uniformitéData = 	{{"Uniformité relative (%)", Uniformité0, Uniformité1, Uniformité2, Uniformité3, Uniformité4, Uniformité5}};
			// CREATION DU TABLEAU
			JTable uniformitéTable = new JTable(uniformitéData, uniformitéParameter);			
			
			// AJUSTEMENT DE LA TAILLE DES COLONNES EN FONCTIONS DU CONTENU
			bruitTable.getColumnModel().getColumn(0).setPreferredWidth(23*6);
			uniformitéTable.getColumnModel().getColumn(0).setPreferredWidth(23*6);	
			
			// ON REND LES TABLEAUX NON OPAQUE
			bruitTable.setOpaque(false);
			uniformitéTable.setOpaque(false);
			
			// CREATION DE DEUX JPANEL : L'UN CONTIENT LES TABLEAUX, L'AUTRE L'IMAGE DU CATPHAN
			JPanel panelWest = new JPanel();
			JLabel panelEast = new JLabel();	
			// DEFINITION DU LAYOUT DU PANEL WEST
			panelWest.setLayout(new GridLayout(7, 1));
			// AJOUT DES DEUX TABLEAUX ET D'UN JLABEL DE SEPARATION AU PANEL WEST
			panelWest.add(bruitTitle);
			panelWest.add(bruitTable.getTableHeader());
			panelWest.add(bruitTable);
			panelWest.add(new JLabel());
			panelWest.add(uniformitéTitle);
			panelWest.add(uniformitéTable.getTableHeader());
			panelWest.add(uniformitéTable);
			// RECUPERATION DE L'IMAGE CATPHAN
			Image CTP486 = java.awt.Toolkit.getDefaultToolkit().getImage("Catphan/CTP486.jpg");
			// AJOUT DE L4IMAGE CATPHAN AU PANEL EAST
			panelEast.setIcon(new ImageIcon(Scale(toBuff(CTP486), resultPanelSize.getHeight()/3)));
			
			// CREATION D'UN JPANEL QUI CONTIENDRA PANEL WEST ET EAST
			JPanel panel = new JPanel();
			// DEFINITION DU LAYOUT DU JPANEL
			panel.setLayout(new BorderLayout());
			// DEFINITION DE LA BORDURE DU PANEL
			panel.setBorder(panelBorder);
			// AJOUT DE EAST ET WEST AU PANEL
			panel.add(panelWest, BorderLayout.WEST);
			panel.add(panelEast, BorderLayout.EAST);
			
			// CREATION D'UNE NOUVELLE FENETRE
			LCDFrame = new JFrame();
			// AJOUT DU PANEL A LA FENETRE
			LCDFrame.add(panel);
			// AJUSTEMENT AUTOMATIQUE DE LA FENETRE
			LCDFrame.pack();
			// ON FIGE LA TAILLE DE LA FENETRE
			MTFFrame.setResizable(false);
			// CENTRAGE DE LA FENETRE
			LCDFrame.setLocationRelativeTo(null);
			// AFFICHAGE DE LA FENETRE
			LCDFrame.setVisible(true);
			// FERMETURE DE LA FENETRE LORS D'UN CLIC SUR LA CROIX ROUGE
			LCDFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
		}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU SCROLL DE LA SOURIS	**/
	class scrollListener implements MouseWheelListener {
		public void mouseWheelMoved(MouseWheelEvent event) {
			// SI LA MOLETTE EST INCREMENTEE
			if(event.getWheelRotation() == 1){
				// INCREMENTATION DE L'INDEX DE L'IMAGE BASSE DOSE
				lowDoseDicom.imageIndex++;
				// SI L'INDEX DE L'IMAGE RESTE INFERIEUR AU NOMBRE D'IMAGE DU FOLDER
				if(lowDoseDicom.imageIndex < lowDoseDicom.folderContent.length){
					// AFFICHAGE DE L'IMAGE
					lowDoseDicom.image.setDicomImage(lowDoseDicom.folderPath+"/"+lowDoseDicom.folderContent[lowDoseDicom.imageIndex], 
							doseDimension.getWidth(), fenêtrage);
				}
				// SINON L'INDEX DE L'IMAGE EST DECREMENTE
				else{ lowDoseDicom.imageIndex = lowDoseDicom.folderContent.length-1;}
				
				// INCREMENTATION DE L'INDEX DE L'IMAGE STANDARD
				mediumDoseDicom.imageIndex++;
				// SI L'INDEX DE L'IMAGE RESTE INFERIEUR AU NOMBRE D'IMAGE DU FOLDER
				if(mediumDoseDicom.imageIndex < mediumDoseDicom.folderContent.length){
					// AFFICHAGE DE L'IMAGE
					mediumDoseDicom.image.setDicomImage(mediumDoseDicom.folderPath+"/"+mediumDoseDicom.folderContent[mediumDoseDicom.imageIndex], 
														doseDimension.getWidth(), fenêtrage);
				}
				// SINON L'INDEX DE L'IMAGE EST DECREMENTE
				else{ mediumDoseDicom.imageIndex = mediumDoseDicom.folderContent.length-1;}
				
				// INCREMENTATION DE L'INDEX DE L'IMAGE HAUTE QI
				highDoseDicom.imageIndex++;
				// SI L'INDEX DE L'IMAGE RESTE INFERIEUR AU NOMBRE D'IMAGE DU FOLDER
				if(highDoseDicom.imageIndex < highDoseDicom.folderContent.length){
					// AFFICHAGE DE L'IMAGE
					highDoseDicom.image.setDicomImage(highDoseDicom.folderPath+"/"+highDoseDicom.folderContent[highDoseDicom.imageIndex], 
														doseDimension.getWidth(), fenêtrage);
				}
				// SINON L'INDEX DE L'IMAGE EST DECREMENTE
				else{ highDoseDicom.imageIndex = highDoseDicom.folderContent.length-1;}
			}
			// SI LA MOLETTE EST DECREMENTEE
			if(event.getWheelRotation() == -1){
				// DECREMENTATION DE L'INDEX DE L'IMAGE BASSE DOSE
				lowDoseDicom.imageIndex--;
				// SI L'INDEX DE L'IMAGE RESTE SUPERIEUR OU EGAL A 0
				if(lowDoseDicom.imageIndex >= 0){
					// AFFICHAGE DE L'IMAGE
					lowDoseDicom.image.setDicomImage(lowDoseDicom.folderPath+"/"+lowDoseDicom.folderContent[lowDoseDicom.imageIndex], 
														doseDimension.getWidth(), fenêtrage);
				}
				// SINON L'INDEX DE L'IMAGE EST REMIS A 0
				else { lowDoseDicom.imageIndex = 0;}
				
				// DECREMENTATION DE L'INDEX DE L'IMAGE STANDARD
				mediumDoseDicom.imageIndex--;
				// SI L'INDEX DE L'IMAGE RESTE SUPERIEUR OU EGAL A 0
				if(mediumDoseDicom.imageIndex >= 0){
					// AFFICHAGE DE L'IMAGE
					mediumDoseDicom.image.setDicomImage(mediumDoseDicom.folderPath+"/"+mediumDoseDicom.folderContent[mediumDoseDicom.imageIndex], 
														doseDimension.getWidth(), fenêtrage);
				}
				// SINON L'INDEX DE L'IMAGE EST DECREMENTE
				else { mediumDoseDicom.imageIndex = 0;}
				
				// DECREMENTATION DE L'INDEX DE L'IMAGE HAUTE QI
				highDoseDicom.imageIndex--;
				// SI L'INDEX DE L'IMAGE RESTE SUPERIEUR OU EGAL A 0
				if(highDoseDicom.imageIndex >= 0){
					// AFFICHAGE DE L'IMAGE
					highDoseDicom.image.setDicomImage(highDoseDicom.folderPath+"/"+highDoseDicom.folderContent[highDoseDicom.imageIndex], 
														doseDimension.getWidth(), fenêtrage);
				}
				// SINON L'INDEX DE L'IMAGE EST DECREMENTE
				else { highDoseDicom.imageIndex = 0;}
			}
		}
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DE LA FENETRE DE ZOOM	**/
	class zoomFrameListener implements WindowListener{

		public void windowActivated(WindowEvent arg0) {}
		public void windowClosed(WindowEvent arg0) {}

		public void windowClosing(WindowEvent arg0) {
			// MISE A JOUR DES IMAGES AVEC LE BON INDEX
			lowDoseDicom.image.setDicomImage(lowDoseDicom.folderPath+"/"+lowDoseDicom.folderContent[lowDoseDicom.imageIndex], 
					doseDimension.getWidth(), fenêtrage);
			mediumDoseDicom.image.setDicomImage(mediumDoseDicom.folderPath+"/"+mediumDoseDicom.folderContent[mediumDoseDicom.imageIndex], 
					doseDimension.getWidth(), fenêtrage);
			highDoseDicom.image.setDicomImage(highDoseDicom.folderPath+"/"+highDoseDicom.folderContent[highDoseDicom.imageIndex], 
					doseDimension.getWidth(), fenêtrage);
		}

		public void windowDeactivated(WindowEvent arg0) {
			// MISE A JOUR DES IMAGES AVEC LE BON INDEX
			lowDoseDicom.image.setDicomImage(lowDoseDicom.folderPath+"/"+lowDoseDicom.folderContent[lowDoseDicom.imageIndex], 
					doseDimension.getWidth(), fenêtrage);
			mediumDoseDicom.image.setDicomImage(mediumDoseDicom.folderPath+"/"+mediumDoseDicom.folderContent[mediumDoseDicom.imageIndex], 
					doseDimension.getWidth(), fenêtrage);
			highDoseDicom.image.setDicomImage(highDoseDicom.folderPath+"/"+highDoseDicom.folderContent[highDoseDicom.imageIndex], 
					doseDimension.getWidth(), fenêtrage);
		}
		public void windowDeiconified(WindowEvent arg0) {}
		public void windowIconified(WindowEvent arg0) {}
		public void windowOpened(WindowEvent arg0) {}		
	}	
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU SCROLL SOURIS SUR LE ZOOM DE LA SEQUENCE LOWDOSE	**/
	class lowDoseZoomListener implements MouseWheelListener {
		public void mouseWheelMoved(MouseWheelEvent event) {
			// SI LA MOLETTE DE LA SOURIS EST INCREMENTEE
			if(event.getWheelRotation() == 1){
				// INCREMENTATION DE L'INDEX DES TROIS IMAGES
				lowDoseDicom.imageIndex++;
				mediumDoseDicom.imageIndex++;
				highDoseDicom.imageIndex++;
				// SI L'INDEX RESTE INFERIEUR AU NOMBRE D'IMAGE DU FOLDER
				if(lowDoseDicom.imageIndex < lowDoseDicom.folderContent.length){
					// AFFICHAGE DE L'IMAGE LOWDOSE
					lowDoseDicom.ZOOM.setDicomImage(lowDoseDicom.folderPath+"/"+lowDoseDicom.folderContent[lowDoseDicom.imageIndex], 1d, fenêtrage);
				}
				// SINON L'INDEX EST DECREMENTE
				else{ lowDoseDicom.imageIndex = lowDoseDicom.folderContent.length-1;}			
			}
			// SI LA MOLETTE DE LA SOURIS EST DECREMENTEE
			if(event.getWheelRotation() == -1){
				// DECREMENTATION DE L'INDEX DES TROIS IMAGES
				lowDoseDicom.imageIndex--;
				highDoseDicom.imageIndex--;
				highDoseDicom.imageIndex--;
				// SI L'INDEX RESTE SUPERIEUR OU EGAL A 0
				if(lowDoseDicom.imageIndex >= 0){
					// AFFICHAGE DE L'IMAGE LOWDOSE
					lowDoseDicom.ZOOM.setDicomImage(lowDoseDicom.folderPath+"/"+lowDoseDicom.folderContent[lowDoseDicom.imageIndex], 1d, fenêtrage);
				}
				// SINON L'INDEX EST INCREMENTE
				else { lowDoseDicom.imageIndex = 0;}
			}
		}
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU SCROLL SOURIS SUR LE ZOOM DE LA SEQUENCE MEDIUMDOSE	**/	
	class mediumDoseZoomListener implements MouseWheelListener {
		public void mouseWheelMoved(MouseWheelEvent event) {
			// SI LA MOLETTE DE LA SOURIS EST INCREMENTEE
			if(event.getWheelRotation() == 1){
				// INCREMENTATION DE L'INDEX DES TROIS IMAGES
				lowDoseDicom.imageIndex++;
				mediumDoseDicom.imageIndex++;
				highDoseDicom.imageIndex++;
				// SI L'INDEX RESTE INFERIEUR AU NOMBRE D'IMAGE DU FOLDER
				if(mediumDoseDicom.imageIndex < mediumDoseDicom.folderContent.length){
					// AFFICHAGE DE L'IMAGE STANDARD
					mediumDoseDicom.ZOOM.setDicomImage(mediumDoseDicom.folderPath+"/"+mediumDoseDicom.folderContent[mediumDoseDicom.imageIndex], 1d, fenêtrage);
				}
				// SINON L'INDEX EST DECREMENTE
				else{ mediumDoseDicom.imageIndex = mediumDoseDicom.folderContent.length-1;}			
			}
			// SI LA MOLETTE DE LA SOURIS EST DECREMENTEE
			if(event.getWheelRotation() == -1){
				// DECREMENTATION DE L'INDEX DES TROIS IMAGES
				lowDoseDicom.imageIndex--;
				mediumDoseDicom.imageIndex--;
				highDoseDicom.imageIndex--;
				// SI L'INDEX RESTE SUPERIEUR OU EGAL A 0
				if(mediumDoseDicom.imageIndex >= 0){
					// AFFICHAGE DE L'IMAGE STANDARD
					mediumDoseDicom.ZOOM.setDicomImage(mediumDoseDicom.folderPath+"/"+mediumDoseDicom.folderContent[mediumDoseDicom.imageIndex], 1d, fenêtrage);
				}
				// SINON L'INDEX EST INCREMENTE
				else { mediumDoseDicom.imageIndex = 0;}
			}
		}
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU SCROLL SOURIS SUR LE ZOOM DE LA SEQUENCE HIGHDOSE	**/
	class highDoseZoomListener implements MouseWheelListener {
		public void mouseWheelMoved(MouseWheelEvent event) {
			// SI LA MOLETTE DE LA SOURIS EST INCREMENTEE
			if(event.getWheelRotation() == 1){
				// INCREMENTATION DE L'INDEX DES TROIS IMAGES
				lowDoseDicom.imageIndex++;
				mediumDoseDicom.imageIndex++;
				highDoseDicom.imageIndex++;
				// SI L'INDEX RESTE INFERIEUR AU NOMBRE D'IMAGE DU FOLDER
				if(highDoseDicom.imageIndex < highDoseDicom.folderContent.length){
					// AFFICHAGE DE L'IMAGE STANDARD
					highDoseDicom.ZOOM.setDicomImage(highDoseDicom.folderPath+"/"+highDoseDicom.folderContent[highDoseDicom.imageIndex], 1d, fenêtrage);
				}
				// SINON L'INDEX EST DECREMENTE
				else{ highDoseDicom.imageIndex = highDoseDicom.folderContent.length-1;}			
			}
			// SI LA MOLETTE DE LA SOURIS EST DECREMENTEE
			if(event.getWheelRotation() == -1){
				lowDoseDicom.imageIndex--;
				mediumDoseDicom.imageIndex--;
				highDoseDicom.imageIndex--;
				// SI L'INDEX RESTE SUPERIEUR OU EGAL A 0
				if(highDoseDicom.imageIndex >= 0){
					// AFFICHAGE DE L'IMAGE STANDARD
					highDoseDicom.ZOOM.setDicomImage(highDoseDicom.folderPath+"/"+highDoseDicom.folderContent[highDoseDicom.imageIndex], 1d, fenêtrage);
				}
				// SINON L'INDEX EST INCREMENTE
				else { highDoseDicom.imageIndex = 0;}
			}
		}
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU BOUTON DE FENETRAGE CEREBRAL	**/
	class cerveauAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			// SELECTION DU FENETRAGE CEREBRAL
			fenêtrage = "cerveau";
			// INACTIVATION DU BOUTON DE FENETRAGE CEREBRAL
			cerveau.setEnabled(false);
			// SI LE AUTRES BOUTONS SONT INACTIFS, ON LES REND ACTIFS
			if(os.isEnabled() == false) os.setEnabled(true);
			if(poumon.isEnabled() == false) poumon.setEnabled(true);
			if(abdo.isEnabled() == false) abdo.setEnabled(true);
			if(rachis.isEnabled() == false) rachis.setEnabled(true);
			if(mediastin.isEnabled() == false) mediastin.setEnabled(true);
			// APPLICATION DU BON FENETRAGE A TOUTES LES IMAGES
			lowDoseDicom.fenêtrage = fenêtrage;
			mediumDoseDicom.fenêtrage = fenêtrage;
			highDoseDicom.fenêtrage = fenêtrage;
			mtfPanel.fenêtrage = fenêtrage;
			lcdPanel.fenêtrage = fenêtrage;
			// AFFICAHGE DES TROIS IMAGES ANTHROPOMORPHIQUES AVEC LE BON FENETRAGEs
			lowDoseDicom.image.setDicomImage(lowDoseDicom.folderPath+"/"+lowDoseDicom.folderContent[lowDoseDicom.imageIndex], 
												doseDimension.getWidth(), fenêtrage);
			mediumDoseDicom.image.setDicomImage(mediumDoseDicom.folderPath+"/"+mediumDoseDicom.folderContent[mediumDoseDicom.imageIndex], 
												doseDimension.getWidth(), fenêtrage);
			highDoseDicom.image.setDicomImage(highDoseDicom.folderPath+"/"+highDoseDicom.folderContent[highDoseDicom.imageIndex], 
												doseDimension.getWidth(), fenêtrage);
			// SI L'IMAGE SELECTIONNE EST LA BASSE DOSE
			if(selectedImage == "lowDose"){
				// AFFICHAGE DEs IMAGES CATPHAN BASSE DOSE
				mtfPanel.image.setDicomImage(referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
			// SI L'IMAGE SELECTIONNE EST LA STANDARD
			if(selectedImage == "mediumDose"){
				// AFFICHAGE DEs IMAGES CATPHAN STANDARD
				mtfPanel.image.setDicomImage(referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
			// SI L'IMAGE SELECTIONNE EST LA HIGH QI
			if(selectedImage == "highDose"){
				// AFFICHAGE DEs IMAGES CATPHAN HIGH QI
				mtfPanel.image.setDicomImage(referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
		}
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU BOUTON DE FENETRAGE OSSEUX	**/
	class osAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			// SELECTION DU FENETRAGE OSSEUX
			fenêtrage = "os";
			// INACTIVATION DU BOUTON DE FENETRAGE OSSEUX
			os.setEnabled(false);
			// SI LE AUTRES BOUTONS SONT INACTIFS, ON LES REND ACTIFS
			if(cerveau.isEnabled() == false) cerveau.setEnabled(true);
			if(poumon.isEnabled() == false) poumon.setEnabled(true);
			if(abdo.isEnabled() == false) abdo.setEnabled(true);
			if(rachis.isEnabled() == false) rachis.setEnabled(true);
			if(mediastin.isEnabled() == false) mediastin.setEnabled(true);
			// APPLICATION DU BON FENETRAGE A TOUTES LES IMAGES
			lowDoseDicom.fenêtrage = fenêtrage;
			mediumDoseDicom.fenêtrage = fenêtrage;
			highDoseDicom.fenêtrage = fenêtrage;
			mtfPanel.fenêtrage = fenêtrage;
			lcdPanel.fenêtrage = fenêtrage;
			// AFFICAHGE DES TROIS IMAGES ANTHROPOMORPHIQUES AVEC LE BON FENETRAGE
			lowDoseDicom.image.setDicomImage(lowDoseDicom.folderPath+"/"+lowDoseDicom.folderContent[lowDoseDicom.imageIndex], 
					doseDimension.getWidth(), fenêtrage);
			mediumDoseDicom.image.setDicomImage(mediumDoseDicom.folderPath+"/"+mediumDoseDicom.folderContent[mediumDoseDicom.imageIndex], 
												doseDimension.getWidth(), fenêtrage);
			highDoseDicom.image.setDicomImage(highDoseDicom.folderPath+"/"+highDoseDicom.folderContent[highDoseDicom.imageIndex], 
												doseDimension.getWidth(), fenêtrage);
			// SI L'IMAGE SELECTIONNE EST LA BASSE DOSE
			if(selectedImage == "lowDose"){
				// AFFICHAGE DES IMAGES CATPHAN BASSE DOSE
				mtfPanel.image.setDicomImage(referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
			// SI L'IMAGE SELECTIONNE EST LA STANDARD
			if(selectedImage == "mediumDose"){
				// AFFICHAGE DES IMAGES CATPHAN STANDARD
				mtfPanel.image.setDicomImage(referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
			// SI L'IMAGE SELECTIONNE EST LA HIGH QI
			if(selectedImage == "highDose"){
				// AFFICHAGE DEs IMAGES CATPHAN HIGH QI
				mtfPanel.image.setDicomImage(referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
		}
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU BOUTON DE FENETRAGE MEDIASTINAL	**/
	class mediastinAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			// SELECTION DU FENETRAGE MEDIASTINAL
			fenêtrage = "mediastin";
			// INACTIVATION DU BOUTON DE FENETRAGE MEDIASTINAL
			mediastin.setEnabled(false);
			// SI LE AUTRES BOUTONS SONT INACTIFS, ON LES REND ACTIFS
			if(os.isEnabled() == false) os.setEnabled(true);
			if(poumon.isEnabled() == false) poumon.setEnabled(true);
			if(abdo.isEnabled() == false) abdo.setEnabled(true);
			if(rachis.isEnabled() == false) rachis.setEnabled(true);
			if(cerveau.isEnabled() == false) cerveau.setEnabled(true);
			// APPLICATION DU BON FENETRAGE A TOUTES LES IMAGES
			lowDoseDicom.fenêtrage = fenêtrage;
			mediumDoseDicom.fenêtrage = fenêtrage;
			highDoseDicom.fenêtrage = fenêtrage;
			mtfPanel.fenêtrage = fenêtrage;
			lcdPanel.fenêtrage = fenêtrage;
			// AFFICAHGE DES TROIS IMAGES ANTHROPOMORPHIQUES AVEC LE BON FENETRAGE
			lowDoseDicom.image.setDicomImage(lowDoseDicom.folderPath+"/"+lowDoseDicom.folderContent[lowDoseDicom.imageIndex], 
												doseDimension.getWidth(), fenêtrage);
			mediumDoseDicom.image.setDicomImage(mediumDoseDicom.folderPath+"/"+mediumDoseDicom.folderContent[mediumDoseDicom.imageIndex], 
												doseDimension.getWidth(), fenêtrage);
			highDoseDicom.image.setDicomImage(highDoseDicom.folderPath+"/"+highDoseDicom.folderContent[highDoseDicom.imageIndex], 
												doseDimension.getWidth(), fenêtrage);
			
			// SI L'IMAGE SELECTIONNE EST LA BASSE DOSE
			if(selectedImage == "lowDose"){
				// AFFICHAGE DES IMAGES CATPHAN BASSE DOSE
				mtfPanel.image.setDicomImage(referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
			// SI L'IMAGE SELECTIONNE EST LA STANDARD
			if(selectedImage == "mediumDose"){
				// AFFICHAGE DES IMAGES CATPHAN STANDARD
				mtfPanel.image.setDicomImage(referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
			// SI L'IMAGE SELECTIONNE EST LA HIGH QI
			if(selectedImage == "highDose"){
				// AFFICHAGE DEs IMAGES CATPHAN HIGH QI
				mtfPanel.image.setDicomImage(referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
		}
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU BOUTON DE FENETRAGE RACHIS	**/
	class rachisAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			// SELECTION DU FENETRAGE RACHIS
			fenêtrage = "rachis";
			// INACTIVATION DU BOUTON DE FENETRAGE RACHIS
			rachis.setEnabled(false);
			// SI LE AUTRES BOUTONS SONT INACTIFS, ON LES REND ACTIFS
			if(os.isEnabled() == false) os.setEnabled(true);
			if(poumon.isEnabled() == false) poumon.setEnabled(true);
			if(abdo.isEnabled() == false) abdo.setEnabled(true);
			if(cerveau.isEnabled() == false) cerveau.setEnabled(true);
			if(mediastin.isEnabled() == false) mediastin.setEnabled(true);
			// APPLICATION DU BON FENETRAGE A TOUTES LES IMAGES
			lowDoseDicom.fenêtrage = fenêtrage;
			mediumDoseDicom.fenêtrage = fenêtrage;
			highDoseDicom.fenêtrage = fenêtrage;
			mtfPanel.fenêtrage = fenêtrage;
			lcdPanel.fenêtrage = fenêtrage;
			// AFFICAHGE DES TROIS IMAGES ANTHROPOMORPHIQUES AVEC LE BON FENETRAGE
			lowDoseDicom.image.setDicomImage(lowDoseDicom.folderPath+"/"+lowDoseDicom.folderContent[lowDoseDicom.imageIndex], 
												doseDimension.getWidth(), fenêtrage);
			mediumDoseDicom.image.setDicomImage(mediumDoseDicom.folderPath+"/"+mediumDoseDicom.folderContent[mediumDoseDicom.imageIndex], 
												doseDimension.getWidth(), fenêtrage);
			highDoseDicom.image.setDicomImage(highDoseDicom.folderPath+"/"+highDoseDicom.folderContent[highDoseDicom.imageIndex], 
												doseDimension.getWidth(), fenêtrage);
			
			// SI L'IMAGE SELECTIONNE EST LA BASSE DOSE
			if(selectedImage == "lowDose"){
				// AFFICHAGE DES IMAGES CATPHAN BASSE DOSE
				mtfPanel.image.setDicomImage(referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
			// SI L'IMAGE SELECTIONNE EST LA STANDARD
			if(selectedImage == "mediumDose"){
				// AFFICHAGE DES IMAGES CATPHAN STANDARD
				mtfPanel.image.setDicomImage(referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
			// SI L'IMAGE SELECTIONNE EST LA HIGH QI
			if(selectedImage == "highDose"){
				// AFFICHAGE DEs IMAGES CATPHAN HIGH QI
				mtfPanel.image.setDicomImage(referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
		}
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU BOUTON DE FENETRAGE PULMONAIRE	**/
	class poumonAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			// SELECTION DU FENETRAGE PULMONAIRE
			fenêtrage = "poumon";
			// INACTIVATION DU BOUTON DE FENETRAGE PULMONAIRE
			poumon.setEnabled(false);
			// SI LE AUTRES BOUTONS SONT INACTIFS, ON LES REND ACTIFS
			if(cerveau.isEnabled() == false) cerveau.setEnabled(true);
			if(os.isEnabled() == false) os.setEnabled(true);
			if(abdo.isEnabled() == false) abdo.setEnabled(true);
			if(rachis.isEnabled() == false) rachis.setEnabled(true);
			if(mediastin.isEnabled() == false) mediastin.setEnabled(true);
			// APPLICATION DU BON FENETRAGE A TOUTES LES IMAGES
			lowDoseDicom.fenêtrage = fenêtrage;
			mediumDoseDicom.fenêtrage = fenêtrage;
			highDoseDicom.fenêtrage = fenêtrage;
			mtfPanel.fenêtrage = fenêtrage;
			lcdPanel.fenêtrage = fenêtrage;
			// AFFICAHGE DES TROIS IMAGES ANTHROPOMORPHIQUES AVEC LE BON FENETRAGE
			lowDoseDicom.image.setDicomImage(lowDoseDicom.folderPath+"/"+lowDoseDicom.folderContent[lowDoseDicom.imageIndex], 
					doseDimension.getWidth(), fenêtrage);
			mediumDoseDicom.image.setDicomImage(mediumDoseDicom.folderPath+"/"+mediumDoseDicom.folderContent[mediumDoseDicom.imageIndex], 
												doseDimension.getWidth(), fenêtrage);
			highDoseDicom.image.setDicomImage(highDoseDicom.folderPath+"/"+highDoseDicom.folderContent[highDoseDicom.imageIndex], 
												doseDimension.getWidth(), fenêtrage);
			
			// SI L'IMAGE SELECTIONNE EST LA BASSE DOSE
			if(selectedImage == "lowDose"){
				// AFFICHAGE DES IMAGES CATPHAN BASSE DOSE
				mtfPanel.image.setDicomImage(referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
			// SI L'IMAGE SELECTIONNE EST LA STANDARD
			if(selectedImage == "mediumDose"){
				// AFFICHAGE DES IMAGES CATPHAN STANDARD
				mtfPanel.image.setDicomImage(referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
			// SI L'IMAGE SELECTIONNE EST LA HIGH QI
			if(selectedImage == "highDose"){
				// AFFICHAGE DEs IMAGES CATPHAN HIGH QI
				mtfPanel.image.setDicomImage(referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
		}
	}
	
	
	/**************************************************************************************************************************/
	/** 	CLASSE INTERNE D'ECOUTE DU BOUTON DE FENETRAGE ABDOMINAL	**/
	class abdoAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			// SELECTION DU FENETRAGE ABDOMINAL
			fenêtrage = "abdo";
			// INACTIVATION DU BOUTON DE FENETRAGE ABDOMINAL
			abdo.setEnabled(false);
			// SI LE AUTRES BOUTONS SONT INACTIFS, ON LES REND ACTIFS
			if(os.isEnabled() == false) os.setEnabled(true);
			if(poumon.isEnabled() == false) poumon.setEnabled(true);
			if(cerveau.isEnabled() == false) cerveau.setEnabled(true);
			if(rachis.isEnabled() == false) rachis.setEnabled(true);
			if(mediastin.isEnabled() == false) mediastin.setEnabled(true);
			// APPLICATION DU BON FENETRAGE A TOUTES LES IMAGES
			lowDoseDicom.fenêtrage = fenêtrage;
			mediumDoseDicom.fenêtrage = fenêtrage;
			highDoseDicom.fenêtrage = fenêtrage;
			mtfPanel.fenêtrage = fenêtrage;
			lcdPanel.fenêtrage = fenêtrage;
			// AFFICAHGE DES TROIS IMAGES ANTHROPOMORPHIQUES AVEC LE BON FENETRAGE
			lowDoseDicom.image.setDicomImage(lowDoseDicom.folderPath+"/"+lowDoseDicom.folderContent[lowDoseDicom.imageIndex], 
					doseDimension.getWidth(), fenêtrage);
			mediumDoseDicom.image.setDicomImage(mediumDoseDicom.folderPath+"/"+mediumDoseDicom.folderContent[mediumDoseDicom.imageIndex], 
												doseDimension.getWidth(), fenêtrage);
			highDoseDicom.image.setDicomImage(highDoseDicom.folderPath+"/"+highDoseDicom.folderContent[highDoseDicom.imageIndex], 
												doseDimension.getWidth(), fenêtrage);
			
			// SI L'IMAGE SELECTIONNE EST LA BASSE DOSE
			if(selectedImage == "lowDose"){
				// AFFICHAGE DES IMAGES CATPHAN BASSE DOSE
				mtfPanel.image.setDicomImage(referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceLowDose.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
			// SI L'IMAGE SELECTIONNE EST LA STANDARD
			if(selectedImage == "mediumDose"){
				// AFFICHAGE DES IMAGES CATPHAN STANDARD
				mtfPanel.image.setDicomImage(referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceStandard.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
			// SI L'IMAGE SELECTIONNE EST LA HIGH QI
			if(selectedImage == "highDose"){
				// AFFICHAGE DEs IMAGES CATPHAN HIGH QI
				mtfPanel.image.setDicomImage(referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathMTF(), IQDimension.getWidth(), fenêtrage);
				lcdPanel.image.setDicomImage(referenceHighIQ.getSérie(sérieRef).getGroup(groupeRef).getRecon(reconRef).getPathLCD(), IQDimension.getWidth(), fenêtrage);
			}
		}
	}
	
	
	
	/**************************************************************************************************************************/
	/** 	METHODE DE REDIMENSIONNEMENT D'IMAGE	**/
	BufferedImage Scale(BufferedImage BufImg, Double dimension)   {	
		// INSTANTIATION D'UNE TRANSFROMATION AFINE TX
		AffineTransform tx = new AffineTransform();
		// INSTANCIATION D'UN DOUBLE SCALEVALE
		double scaleValue;
		// SI LA DIMENSION EST EGALE A 1
		if (dimension == 1){
			// SCALEVALUE = DIMENSION
			scaleValue = dimension;
		}
		// SINON
		else{
			// SCALEVALUE = DIMENSION DIVISEE PAR LA LARGEUR DE L'IMAGE
			scaleValue = dimension / (double) BufImg.getWidth();
		}
		// DEFINITION DE L'ECHELLE DE LA TRANSFORMATION AFFINE
		tx.scale(scaleValue, scaleValue);
		// DEFINITION DE L'OPERATEUR DE TRANSFORMATION AFFINE
		AffineTransformOp op = new AffineTransformOp (tx, AffineTransformOp.TYPE_BILINEAR);
		// CREATION D'UNE NOUVELLE BUFFEREDIMAGE DE DIMENSION SCALEVALUExSCALEVALUE
		BufferedImage newBufImg = new BufferedImage( (int)(BufImg.getWidth()*scaleValue), (int)(BufImg.getHeight()*scaleValue), BufImg.getType());		
		return op.filter(BufImg, newBufImg);
	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE DE CREATION D'UNE BUFFERDEIMAGE A PARTIR D'UNE IMAGE	**/
	BufferedImage toBuff (Image pImage) {	
		// SI L'IMAGE EST DEJA DE TYPE BUFFEREDIMAGE
		if(pImage instanceof BufferedImage){
			// ON RETOURNE L'IMAGE
			return((BufferedImage) pImage);
		// SINON	
		} else { 
			pImage = new ImageIcon(pImage).getImage();
			BufferedImage pBufferedImage = new BufferedImage(
									pImage.getWidth(null), 
									pImage.getHeight(null),
									BufferedImage.TYPE_INT_RGB);
			Graphics g = pBufferedImage.createGraphics();
			g.drawImage(pImage,0,0,null);
			g.dispose();
			return (pBufferedImage);
		}		
	}
} 




		