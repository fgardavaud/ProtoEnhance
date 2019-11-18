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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileSystemView;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.Protocol.Group;
import com.Protocol.Protocol;
import com.Protocol.Recon;
import com.Protocol.Serie;

public class OptimizationPanel extends JPanel {
	/**************************************************************************************************************************/
	/** 	DECLARATION DES VARIABLES 	**/
	
	//************  ARRAYLIST  ************************************************************************************************
	ArrayList<String> 	matchProto = new ArrayList<String>(),
						matchRef = new ArrayList<String>();
	
	//************  BOOLEAN  **************************************************************************************************
	public boolean 	optimisationAvanc�e;
	
	//************  DATE  *****************************************************************************************************
	private Date 	currentDate = new Date();
	
	//************  DefaultListModel<STRING>  *********************************************************************************
	private 	DefaultListModel<String> 	ProtocoleHead = new DefaultListModel<String>(), 
											ProtocoleOrbit = new DefaultListModel<String>(),
											ProtocoleCSpine = new DefaultListModel<String>(),
											ProtocoleShoulder = new DefaultListModel<String>(), 
											ProtocoleChest = new DefaultListModel<String>(), 
											ProtocoleAbdomen = new DefaultListModel<String>(), 
											ProtocoleLumbar = new DefaultListModel<String>(), 
											ProtocolePelvis = new DefaultListModel<String>(),
											ProtocoleLowerExtremity = new DefaultListModel<String>(), 
											ProtocoleMiscellaneous = new DefaultListModel<String>(), 
											ReferenceHead = new DefaultListModel<String>(), 
											ReferenceOrbit = new DefaultListModel<String>(), 
											ReferenceCSpine = new DefaultListModel<String>(), 
											ReferenceShoulder = new DefaultListModel<String>(),
											ReferenceChest = new DefaultListModel<String>(), 
											ReferenceAbdomen = new DefaultListModel<String>(), 
											ReferenceLumbar = new DefaultListModel<String>(), 
											ReferencePelvis = new DefaultListModel<String>(),
											ReferenceLowerExtremity = new DefaultListModel<String>(), 
											ReferenceMiscellaneous = new DefaultListModel<String>();
	
	//****************  DIMENSION  *******************************************************************************************
	private Dimension 	analysePanelSize, 
						resultPanelSize;
	
	//************  FILE  ****************************************************************************************************
	private File 	file;
	
	//************  INT  *****************************************************************************************************
	private int 	nbCategory, 
					nbProtocol,
					nbReferenceHead, 
					nbReferenceOrbit,
					nbReferenceCSpine, 
					nbReferenceShoulder, 
					nbReferenceChest, 
					nbReferenceAbdomen, 
					nbReferenceLumbar, 
					nbReferencePelvis, 
					nbReferenceLowerExtremity, 
					nbReferenceMiscellaneous, 
					s�rie = -1, 
					groupe = -1,
					recon = -1,
					ligne = 0,
					endProtocol,
					s�rieProto,
					groupeProto,
					reconProto,
					s�rieRef,
					groupeRef,
					reconRef,
					nativePanelNb,
					matchNb,
					matchedReconRow,
					excelSheetLine = 0,
					startMerge,
					stopMerge;
	
	//************  INT[]  ***************************************************************************************************
	private int[] 	ligneReference, 
					ligneReferenceHead = new int[100], 
					ligneReferenceOrbit = new int[100],
					ligneReferenceCSpine = new int[100], 
					ligneReferenceShoulder = new int[100],	
					ligneReferenceChest = new int[100], 
					ligneReferenceAbdomen = new int[100], 
					ligneReferenceLumbar = new int[100], 
					ligneReferencePelvis = new int[100],
					ligneReferenceLowerExtremity = new int[100], 
					ligneReferenceMiscellaneous = new int[100];
					
	
	//************  JBUTTON  *************************************************************************************************
	private JButton boutonConfiguration = new JButton ("Configuration"),
					boutonOptimisation = new JButton("Optimisation"), 
					boutonValidation = new JButton("Validation");
	
	//********************  JCOMBOBOX<?>  ************************************************************************************
	private JComboBox<?> 	comboProtocole = new JComboBox<Object>(), 
							comboReference = new JComboBox<Object>();
	
	//********************  JLIST<STRING>  ***********************************************************************************
	private JList<String>	listProtocole = new JList<String>(), 
							listReference = new JList<String>();
	
	//****************  JPANEL  **********************************************************************************************
	private JPanel 		cadre = new JPanel(), 
						center = new JPanel(), 
						west = new JPanel();
	
	//****************  JSCROLLPANE  *****************************************************************************************
	private JScrollPane scrollProtocole = new JScrollPane(), 
						scrollReference = new JScrollPane();
	
	//****************  PROTOCOLE  *******************************************************************************************
	private Protocol 	protocole, 
						referenceStandard, 
						referenceLowDose, 
						referenceHighIQ,
						reference;
	
	//****************  PROTOCOLESTRUCTUREPANEL  *****************************************************************************
	private ProtoStructurePanel structurePanel;
	
	//********************  RESULTPANEL[]  ***********************************************************************************
	private ResultPanel[] 	nativePanel;

	ResultPanel reconPanel;
	
	//************  SHEET  ***************************************************************************************************
	private Sheet 	Discovery;
	
	//*********************  RESULTPANEL *************************************************************************************
	private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	
	//************  STRING  **************************************************************************************************
	private String 	currentDatetoString,
					ServiceModDataPath,
					protoFilePath,
					parentPath,
					SGRproto, 
					SGRref;
	
	//************  STRING[]  ************************************************************************************************
	private String[]	zoneAnatomique = 	{"1. T�te", "2. Orbite", "3. Cou", "4. Epaule", "5. Thorax", 
										"6. Abdomen", "7. Lombaire", "8. Pelvis", "9. Membre inf�rieur", "10. Divers"};			
	
	//****************  STRING[][]  ******************************************************************************************
	private String[][]	protoFile = new String[10][99],
						matchedRecon;
	
	//****************  WORKBOOK  ********************************************************************************************
	private Workbook 	baseDeDonn�es,
						excelFileTemp;	
	
	//****************  WRITABLEFONT  ****************************************************************************************
	private WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
	
	//**************** WRITABLEWORKBOOK  *************************************************************************************
	private WritableWorkbook excelFile;
	
	/**************************************************************************************************************************/
	/** 	CONSTRUCTEUR 	**/
	public 	OptimizationPanel(boolean pOtpimisationAvanc�e, int pTop, int pBottom, int pRight, int pLeft) 
			throws IOException, JDOMException, BiffException, WriteException {
		
		
		currentDatetoString = format.format(currentDate);
		
		// DETECTION DE L'ACTIVATION DE L'OPTION D'OPTIMISATION AVANCEE
		optimisationAvanc�e = pOtpimisationAvanc�e;
		
		// DEFINITION DE LA TAILLE DE L'ANALYSEPANEL
		analysePanelSize = new Dimension	((int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()-(pRight + pLeft),
											(int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight()-(pTop + pBottom));
		
		// INSTANCIATION DU JFILECHOOSER
		JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		
		// DEFINITION DU MODE DE SELCTION (DOSSIER UNIQUEMENT)
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// DEFINITION DU TITRE DE LA FENETRE
		fc.setDialogTitle("S�lectionner le dossier service_mode_data contenant les protocoles");
		// AFFICHAGE DU JFILECHOOSER
		int returnVal = fc.showOpenDialog(OptimizationPanel.this);
		 
		// DETECTION DU DOSSIER SELECTIONNE
		if (returnVal == JFileChooser.APPROVE_OPTION){			
			file = fc.getSelectedFile();
		}
		// OBTENTION DU CHEMIN ABSOLUT DU DOSSIER
		ServiceModDataPath = file.getAbsolutePath();
		// OBTENTION DU CHEMIN DU DOSSIER PARENT
		parentPath = file.getParent();
		

		
		try {
			// ON TESTE SI LE FICHIER EXCEL POSTOPTIMISATION EST DEJA PRESENT DANS LE REPERTOIRE
			BufferedReader bufferedReader = new BufferedReader(new FileReader(parentPath+"/Optimisation_ProtoEnhance.xls"));
			// CREATION D'UN WORKBOOK TEMPORAIRE A PARTIR DU FICHIER
			Workbook excelFileTemp = Workbook.getWorkbook(new File(parentPath+"/Optimisation_ProtoEnhance.xls"));
			// CREATION D'UNE COPIE EDITABLE DU WORKBOOK TEMPORAIRE 
			excelFile = Workbook.createWorkbook (new File (parentPath+"/Optimisation_ProtoEnhance.xls"), excelFileTemp);
			// FERMETURE DU FICHIER TEMPORAIRE
			excelFileTemp.close();
			// RECUPERATION DU NUMERO DE LA DERNIERE LIGNE DU FICHIER
			excelSheetLine = excelFile.getSheet(0).getRows();
			// FERMETURE DU BUFFEREDREADER
			bufferedReader.close();
			// ECRITURE PUIS FERMETURE DU FICHIER EXCEL
			excelFile.write();
			excelFile.close();
		}
		catch (FileNotFoundException fnfe) {
			  	
			//CREATION D'UN FICHIER EXCEL AU NIVEAU DU DOSSIER PARENT
			excelFile = Workbook.createWorkbook (new File (parentPath+"/Optimisation_ProtoEnhance.xls"));
			excelFile.createSheet("Protocoles Optimis�s", 0);
			
			// CEATION DE LA POLICE DES TITRES
			WritableFont titreFont = new WritableFont(WritableFont.ARIAL, 10);
			// DEFINITION DE LA COULEUR DE LA POLICE
			titreFont.setColour(jxl.format.Colour.RED);
			// CRETION DU FORMAT DES TITRES
			WritableCellFormat titreFormat = new WritableCellFormat(titreFont);
			// DEFINITION DE L'ALIGNEMENT DES TITRES
			titreFormat.setAlignment(Alignment.CENTRE);
			// DEFINITION DE L'ALIGNEMENT VERTICAL DES TITRES
			titreFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			// DEFINITION DES BORDURES DES TITRES
			titreFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			titreFormat.setBorder(Border.BOTTOM, BorderLineStyle.MEDIUM);
			
			// AJOUT DES DIFFERENTS TITRES DANS LES COLONNES
			excelFile.getSheet(0).addCell(new Label(0,excelSheetLine, "Date", titreFormat));
			excelFile.getSheet(0).addCell(new Label(1,excelSheetLine, "Protocole", titreFormat));
			excelFile.getSheet(0).addCell(new Label(2,excelSheetLine, "S�rie", titreFormat));
			excelFile.getSheet(0).addCell(new Label(3,excelSheetLine, "Groupe", titreFormat));
			excelFile.getSheet(0).addCell(new Label(4,excelSheetLine, "Recon", titreFormat));
			excelFile.getSheet(0).addCell(new Label(5,excelSheetLine, "Description", titreFormat));
			excelFile.getSheet(0).addCell(new Label(6,excelSheetLine, "Optimisation", titreFormat));
			excelFile.getSheet(0).addCell(new Label(7,excelSheetLine, "Type d'acquisition", titreFormat));
			excelFile.getSheet(0).addCell(new Label(8,excelSheetLine, "Temps de rotation (s)", titreFormat));			
			excelFile.getSheet(0).addCell(new Label(9,excelSheetLine, "Epaisseur de coupe (mm)", titreFormat));
			excelFile.getSheet(0).addCell(new Label(10,excelSheetLine, "Nombre d'images", titreFormat));
			excelFile.getSheet(0).addCell(new Label(11,excelSheetLine, "Avanc�e de table (mm/rotation)", titreFormat));			
			excelFile.getSheet(0).addCell(new Label(12,excelSheetLine, "Collimation (mm)", titreFormat));
			excelFile.getSheet(0).addCell(new Label(13,excelSheetLine, "Intervalle (mm)", titreFormat));
			excelFile.getSheet(0).addCell(new Label(14,excelSheetLine, "SFOV", titreFormat));
			excelFile.getSheet(0).addCell(new Label(15,excelSheetLine, "kVp", titreFormat));
			excelFile.getSheet(0).addCell(new Label(16,excelSheetLine, "HiRes", titreFormat));		
			excelFile.getSheet(0).addCell(new Label(17,excelSheetLine, "mA", titreFormat));
			excelFile.getSheet(0).addCell(new Label(18,excelSheetLine, "SmartmA", titreFormat));
			excelFile.getSheet(0).addCell(new Label(19,excelSheetLine, "Noise Index", titreFormat));
			excelFile.getSheet(0).addCell(new Label(20,excelSheetLine, "mA min", titreFormat));
			excelFile.getSheet(0).addCell(new Label(21,excelSheetLine, "mA max", titreFormat));
			excelFile.getSheet(0).addCell(new Label(22,excelSheetLine, "Kernel", titreFormat));
			excelFile.getSheet(0).addCell(new Label(23,excelSheetLine, "Mode de reconstruction", titreFormat));
			excelFile.getSheet(0).addCell(new Label(24,excelSheetLine, "ASIR", titreFormat));
			excelFile.getSheet(0).addCell(new Label(25,excelSheetLine, "IQEnhance", titreFormat));
			excelFile.getSheet(0).addCell(new Label(26,excelSheetLine, "Veo", titreFormat));
			// INCREMENTATION DU NUMERO DE LIGNE
			excelSheetLine++;
			// ECRITURE DU FICHIER
			excelFile.write();
			// FERMETURE DU FICHIER
			excelFile.close();
		} 
		
		// OBTENTION DE LA LISTE DE PROTOCOLES A OPTIMISER
		getProtocole (ServiceModDataPath);
		// OBTETION DE LA LISTE DE PROTOCOLES DE REFERENCE
		getReference();
		
		// INSTANCIATION DU JPANEL DES PROTOCOLES A OPTIMISER
		JPanel protocolePanel = new JPanel();
		// DEFINITION DE LA TAILLE DU JPANEL
		protocolePanel.setPreferredSize(new Dimension((int)analysePanelSize.getWidth()/5, (int)analysePanelSize.getHeight()/3));
		// DEFINITION DU TITRE DU JPANEL
		JLabel label1 = new JLabel("Protocole � otpimiser:");
		// CENTRAGE DU TITRE
		label1.setHorizontalAlignment(0);
		// INSTANCIATION DE LA JCOMBOBOX DE FILTRE ANATOMIQUE
		comboProtocole = new JComboBox<Object>(zoneAnatomique);
		// DEFINITION DE LA TAILLE DE LA JCOMBOBOX
		comboProtocole.setPreferredSize(new Dimension((int)analysePanelSize.getWidth()/5, 20));
		// AJOUT DE L'ACTION DE FILTRE A LA JCOMBOBOX
		comboProtocole.addActionListener(new FiltreProtocole());
		// DEFINITION DU FILTRE HEAD PAR DEFAUT
		comboProtocole.setSelectedIndex(0);
		// DEFINITION DE LA LISTE DE PROTOCOLES HEAD PAR DEFAULT
		listProtocole = new JList<String>(ProtocoleHead);
		// AJOUT DE LA LISTE DE PROTOCOLES DANS UN JSCROLLPANE
		scrollProtocole= new JScrollPane(listProtocole);
		// DEFINITION DE LA TAILLE DU JSCROLLPANE 
		scrollProtocole.setPreferredSize(new Dimension((int)analysePanelSize.getWidth()/5, ((int)analysePanelSize.getHeight())/4));
		
		// AJOUT DE LA JCOMBOBOX ET DU SCROLLPANEL AU JPANEL DE PROTOCOLES A OPTIMISER
		protocolePanel.add(label1);
		protocolePanel.add(comboProtocole);
		protocolePanel.add(scrollProtocole);
		
		
		// INSTANCIATION DU JPANEL DES PROTOCOLES DE REFERENCE
		JPanel referencePanel = new JPanel();
		//DEFINITION DE LA TAILLE DU JPANEL
		referencePanel.setPreferredSize(new Dimension((int)analysePanelSize.getWidth()/5, (int)analysePanelSize.getHeight()/3));
		// DEFINITION DU TITRE DU JPANEL
		JLabel label2 = new JLabel("Protocole de r�f�rence correspondant:");
		// CENTRAGE DU TITRE
		label2.setHorizontalAlignment(0);
		// INSTANCIATION DE LA JCOMBOBOX DE FILTRE ANATOMIQUE
		comboReference = new JComboBox<Object>(zoneAnatomique);
		// DEFINITION DE LA TAILLE DE LA JCOMBOBOX
		comboReference.setPreferredSize(new Dimension((int)analysePanelSize.getWidth()/5, 20));
		// AJOUT DE L'ACTION DE FILTRE A LA JCOMBOBOX
		comboReference.addActionListener(new FiltreReference());
		// DEFINITION DU FILTRE HEAD PAR DEFAUT
		comboReference.setSelectedIndex(0);
		// DEFINITION DE LA LISTE DE PROTOCOLES HEAD PAR DEFAULT
		listReference = new JList<String>(ReferenceHead);
		// AJOUT DE LA LISTE DE PROTOCOLES DANS UN JSCROLLPANE
		scrollReference= new JScrollPane(listReference);
		// DEFINITION DE LA TAILLE DU JSCROLLPANE 
		scrollReference.setPreferredSize(new Dimension((int)analysePanelSize.getWidth()/5, (int)analysePanelSize.getHeight()/4));
		
		// AJOUT DE LA JCOMBOBOX ET DU SCROLLPANEL AU JPANEL DE PROTOCOLES A OPTIMISER
		referencePanel.add(label2);		
		referencePanel.add(comboReference);
		referencePanel.add(scrollReference);
		
		// AJOUT DE L'ACTION DU BOUTON "CONFIGURATION"
		boutonConfiguration.addActionListener(new ConfigurationAction());
		// DEFINITION DE LA POLICE DU BOUTON "CONFIGURATION"
		boutonConfiguration.setFont(new Font("Dialog", Font.BOLD, 25));
		
		// AJOUT DE L'ACTION DU BOUTON "OPTIMISATION"
		boutonOptimisation.addActionListener(new OptimisationAction());
		// DEFINITION DE LA POLICE DU BOUTON "OPTIMISATION"
		boutonOptimisation.setFont(new Font("Dialog", Font.BOLD, 25));
		boutonOptimisation.setVisible(false);
		
		// AJOUT DE L'ACTION DU BOUTON "VALIDATION" 
		boutonValidation.addActionListener(new ValidationAction());
		// DEFINITION DE LA POLICE DU BOUTON "VALIDATION"
		boutonValidation.setFont(new Font("Dialog", Font.BOLD, 25));
		// DESCATIVATION DE L'AFFICHAGE DU BOUTON "VALIDATION"
		boutonValidation.setVisible(false);
	
		// AJOUT DE TOUS LES COMPOSANTS AU JPANEL WEST		
		west.add(protocolePanel);
		west.add(referencePanel);
		west.add(boutonConfiguration);
		west.add(boutonOptimisation);
		west.add(boutonValidation);
		// DEFINITION DE LA TAILLE DU JPANEL WEST
		west.setPreferredSize(new Dimension(((int)analysePanelSize.getWidth()/5)+4, (int)analysePanelSize.getHeight()));
		// DEFINITION DE LA BORDURE DU JPANEL WEST
		west.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(184, 207, 229)));
		
		// DEFINITION DE LA TAILLE DU JPANEL CENTER
		resultPanelSize = new Dimension((((int)analysePanelSize.getWidth()*4)/5)-4, (int)analysePanelSize.getHeight());
		center.setPreferredSize(resultPanelSize);
		
		//DEFINITION DU LAYOUT DU JPANEL CADRE
		cadre.setLayout(new BorderLayout());
		// AJOUT DES JPANEL WEST ET CENTER AU JPANEL CADRE
		cadre.add(west, BorderLayout.WEST);
		cadre.add(center, BorderLayout.CENTER);
		// DEFINITION DU LAYOUT DE L'ANALYSEPANEL
		this.setLayout(new GridLayout(1,1));
		// AJOUT DU JAPNEL CADRE A L'ANALYSEPANEL
		this.add(cadre);
		
	}
	
	
	/**************************************************************************************************************************/
	/** 	FILTRE DES PROTOCOLES A OPTIMISER 	**/
	
	class FiltreProtocole implements ActionListener {
		public void actionPerformed(ActionEvent e){
			// MODIFICATION DE LA LISTE DE PROTOCOLES AFFICHEE EN FONCTION DU FILTRE SELECTIONNE
			int anatomie = comboProtocole.getSelectedIndex();
			switch (anatomie){
			case 0:
				listProtocole.setModel(ProtocoleHead);		
				break;
			case 1:
				listProtocole.setModel(ProtocoleOrbit);
				break;
			case 2:
				listProtocole.setModel(ProtocoleCSpine);
				break;
			case 3:
				listProtocole.setModel(ProtocoleShoulder);
				break;
			case 4:
				listProtocole.setModel(ProtocoleChest);
				break;
			case 5:
				listProtocole.setModel(ProtocoleAbdomen);
				break;
			case 6:
				listProtocole.setModel(ProtocoleLumbar);
				break;
			case 7:
				listProtocole.setModel(ProtocolePelvis);
				break;
			case 8:
				listProtocole.setModel(ProtocoleLowerExtremity);
				break;
			case 9:
				listProtocole.setModel(ProtocoleMiscellaneous);
				break;	
			}
		}
	}
	
	
	/**************************************************************************************************************************/
	/** 	FILTRE DES PROTOCOLES DE REFERENCE		**/
	
	class FiltreReference implements ActionListener {
		public void actionPerformed(ActionEvent e){
			// MODIFICATION DE LA LISTE DE PROTOCOLES AFFICHEE EN FONCTION DU FILTRE SELECTIONNE
			int anatomie = comboReference.getSelectedIndex();
			switch (anatomie){
			case 0:
				listReference.setModel(ReferenceHead);
				ligneReference = ligneReferenceHead;
				break;
			case 1:
				listReference.setModel(ReferenceOrbit);
				ligneReference = ligneReferenceOrbit;
				break;
			case 2:
				listReference.setModel(ReferenceCSpine);
				ligneReference = ligneReferenceCSpine;
				break;
			case 3:
				listReference.setModel(ReferenceShoulder);
				ligneReference = ligneReferenceShoulder;
				break;
			case 4:
				listReference.setModel(ReferenceChest);
				ligneReference = ligneReferenceChest;
				break;
			case 5:
				listReference.setModel(ReferenceAbdomen);
				ligneReference = ligneReferenceAbdomen;
				break;
			case 6:
				listReference.setModel(ReferenceLumbar);
				ligneReference = ligneReferenceLumbar;
				break;
			case 7:
				listReference.setModel(ReferencePelvis);
				ligneReference = ligneReferencePelvis;
				break;
			case 8:
				listReference.setModel(ReferenceLowerExtremity);
				ligneReference = ligneReferenceLowerExtremity;
				break;
			case 9:
				listReference.setModel(ReferenceMiscellaneous);
				ligneReference = ligneReferenceMiscellaneous;
				break;		
			}
		}
	}
	
	/**************************************************************************************************************************/
	/** 	ACTION DU BOUTON "CONFIGURATION"	**/
	
	class ConfigurationAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			
			// SI UN PROTOCOLE A OPTIMISER OU UN PROTOCOLE DE REFERENCE N'EST PAS SELECTIONNE
			if (listProtocole.isSelectionEmpty() || listReference.isSelectionEmpty()){
				// AFFICHAGE D'UN AVERTISSEMENT 
				new JOptionPane();
				JOptionPane.showMessageDialog(cadre, "Veuillez d'abord s�lectionner le protocole � optimiser et" +
				" le protocole de r�f�rence correspondant.", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			// SINON
			else{
				// REMPLACEMENT DU BOUTON "CONFIGURATION" PAR LE BOUTON "OPTIMISATION"
				boutonConfiguration.setVisible(false);
				boutonOptimisation.setPreferredSize(boutonConfiguration.getSize());
				boutonOptimisation.setVisible(true);
				
				// CREATION DE L'OBJET A PARTIR DU PROTOCOL SELECTIONNE
				protocole = new Protocol(listProtocole.getSelectedValue().toString());
				protoFilePath = ServiceModDataPath+"/system_state/"+
								protoFile[comboProtocole.getSelectedIndex()][listProtocole.getSelectedIndex()];
				
				try {
					// OUVERTURE DU FICHIER .PROTO DU PROTOCOLE SELECTIONNE
					InputStream ips = 	new FileInputStream(protoFilePath);
					InputStreamReader ipsr = new InputStreamReader (ips);
					BufferedReader br = new BufferedReader(ipsr);
					
					// CREATION D'UN STRING CORRESPONDANT A LA LIGNE EN COURS
					String ligne;
					// CREATION D'UN TABLEAU CONTENANT LE PARAMETRE ET LA VALEUR 
					String [] splitTable = new String [2];
					
					// INITIALISATION DU NOMBRE DE SERIE
					s�rie = -1;
					
					// LECTURE DU FICHIER LIGNE PAR LIGNE
					while ((ligne = br.readLine())!= null){
						// SEGMENTATION DU PARAMETRE ET DE LA VALEUR								
						splitTable = ligne.split(" += +");
						
						switch(splitTable[0]){
						
							// DETECION D'UNE SERIE
							case "	Series {":
								// INCREMENTATION DU NUMERO DE SERIE
								s�rie++;
								// AJOUT D'UNE SERIE AU PROTOCOLE
								protocole.setS�rie(new Serie(), s�rie);
								// REINITIANILISATION DU NOMBRE DE GROUPES
								groupe = -1;
								break;
							
							// DETECTION DU TYPE DE SERIE	
							case "		seriesType":
								// OBTENTION DY TYPE DE SERIE
								protocole.getS�rie(s�rie).setSeriesType(splitTable[1]);
								break;	
								
							// DETECTION D'UN GROUP	
							case "		Group {":
								// INCREMENTATION DU NUMERO DE Groupe
								groupe++;
								// AJOUT D'UN GROUP A LA SERIE COURANTE
								protocole.getS�rie(s�rie).setGroup(new Group(), groupe);
								// REINITIALISATION DU NUMERO DE RECON
								recon = -1;
								break;
							
							// DETECTION DES kV
							case "			kiloVolts":
								// OBTENTION DES kV
								protocole.getS�rie(s�rie).getGroup(groupe).setKV(splitTable[1]);
								break;
								
							// DETECION DU SMARTCATHODEMODE
							case "			smartCathodeMode":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									// OBTENTION DU SMARTCATHODEMODE
									protocole.getS�rie(s�rie).getGroup(groupe).setSmartCathodeMode(splitTable[1]);
								}
								break;	
								
							// DETECION DU SMARTCATHODENUMDEFLECTIONS
							case "			smartCathodeNumDeflections":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									// OBTENTION DU SMARTCATHODEMODE
									protocole.getS�rie(s�rie).getGroup(groupe).setSmartCathodeNumDeflections(splitTable[1]);
								}
								break;	
								
							// DETECTION DES mA
							case "			milliAmps":
								// OBTENTION DES MA
								protocole.getS�rie(s�rie).getGroup(groupe).setMA(splitTable[1]);
								break;	
								
							// DETECTION DU PLAN DU SCOUT					
							case "			scoutPlane":
								if(protocole.getS�rie(s�rie).getSeriesType().contains( "Scout")){
									// OBTENTION DU PLAN DU SCOUT
									protocole.getS�rie(s�rie).getGroup(groupe).setPlane(splitTable[1]);
								}
								break;	
								
							// DETECTION DE LA TAILLE DU SFOV
							case "			scanFieldOfViewSize":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									// OBTENTION DE LA TAILLE DU SFOV
									protocole.getS�rie(s�rie).getGroup(groupe).setSFOVSize(splitTable[1]);
								}
								break;
								
							// DETECTION DU TYPE DE SFOV
							case "			scanFieldOfViewType":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									// OBTENTION DE LA TAILLE DU SFOV
									protocole.getS�rie(s�rie).getGroup(groupe).setSFOVType(splitTable[1]);
								}
								break;
							
							// DETECTION DU TYPE DE GROUP
							case "			groupType":	
								// OBTENTION DU TYPE D'ACQUISITION
								protocole.getS�rie(s�rie).getGroup(groupe).setGroupType(splitTable[1]);
								break;
								
							// DETECION DE L'EPAISSEUR DE COUPE POUR LA RECON NATIVE
							case "			imageThickness":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									if (recon == -1){
										// OBTENTION DE L'EPAISSEUR
										protocole.getS�rie(s�rie).getGroup(groupe).setThick(splitTable[1]);
									}
								}
								break;	
								
							// DETECTION DU TEMPS DE ROTATION
							case "			rotationTime":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									// OBTENTION DU TEMPS DE ROTATION
									protocole.getS�rie(s�rie).getGroup(groupe).setTpsRotation(splitTable[1]);
								}
								break;	
								
							// DETECTION DE L'INTERVALE POUR LA RECON NATIVE
							case "			scanSpacing":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									if (recon == -1){
										// OBTENTION DE L'INTEVRALE
										protocole.getS�rie(s�rie).getGroup(groupe).setInter(splitTable[1]);
									}
								}
								break;	
								
							// DETECTION DE LA VITESSE DE TABLE
							case "			tableSpeed":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									// OBTENTION DU PITCH
									protocole.getS�rie(s�rie).getGroup(groupe).setPitch(splitTable[1]);
								}
								break;
								
							// DETECTION DE LA MODULATION D'INTENSITE
							case "			automAFlag":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									// OBTENTION DE LA MODULATION D'INTENSITE
									protocole.getS�rie(s�rie).getGroup(groupe).setFlagMA(splitTable[1]);
								}
								break;		
																
							// DETECTION DU NI
							case "			automaNoiseIndex":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									// OBTENTION DU NOISE INDEX
									protocole.getS�rie(s�rie).getGroup(groupe).setNI(splitTable[1]);
								}
								break;				
							
							// DETECION DES mA MAX
							case "			automaMaxMilliAmps":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									// OBTENTION DU mA MAX
									protocole.getS�rie(s�rie).getGroup(groupe).setMaxma(splitTable[1]);
								}
								break;
								
							// DETECION DES mA MIN
							case "			automaMinMilliAmps":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									// OBTENTION DU mA MIN
									protocole.getS�rie(s�rie).getGroup(groupe).setMinma(splitTable[1]);
								}
								break;	
								
							// DETECION DU MODE DE DETECTEUR
							case "			numImagesPerRotation":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									if(protocole.getS�rie(s�rie).getGroup(groupe).getGroupType().contains("Axial")){
										// OBTENTION DE MODE DE DETECTEUR
										protocole.getS�rie(s�rie).getGroup(groupe).setMode(splitTable[1]);
									}
								}	
								break;	
								
							// DETECION DE LA LARGEUR DE COLLIMATION
							case "			macroRowNumber":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									// OBTENTION DE LA COLIMATION
									protocole.getS�rie(s�rie).getGroup(groupe).setRows(splitTable[1]);
								}
								break;
							
							
							// DETECTION D'UNE RECON
							case "			Recon {":
								recon++;
								// AJOUT D'UNE RECON AU GROUP EN COURS DE LA SERIE EN COURS
								protocole.getS�rie(s�rie).getGroup(groupe).setRecon(new Recon(), recon);
								break;
								
							// DETECTION DE L'ACTIVATION DE LA RECON	
							case "				isReconEnabled":
								protocole.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setActivation(splitTable[1]);
								break;	
							
							// DETECTION DU FILTRE DE RECONSTRUCTION	
							case "				algorithm":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									protocole.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setFilter(splitTable[1]);
								}	
								break;
								
							// DETECTION DE L'EPAISSEUR DE RECONSTRUCTION	
							case "				PMRimageThickness":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									protocole.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setThick(splitTable[1]);								
								}	
								break;	
								
							// DETECTION DE L'EPAISSEUR DE RECONSTRUCTION	
							case "				PMRinterval":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									protocole.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setInter(splitTable[1]);								
								}	
								break;		
								
							// DETECTION D'IQE	
							case "				isIQEnhancedEnabled":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									protocole.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setIqe(splitTable[1]);	
								}	
								break;		
								
							// DETECTION DU TYPE DE RECON	
							case "				segments":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									protocole.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setTyperecon(splitTable[1]);
								}	
								break;	
								
							// DETECTION DU MODE D'ASIR	
							case "				iterativeMode":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									protocole.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setAsirMode(splitTable[1]);
								}	
								break;		
								
							// DETECTION DU %age D'ASIR	
							case "				iterativeConfig":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
									protocole.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setAsirConfig(splitTable[1]);
								}	
								break;		
								
							// DETECTION DU MBIR	
							case "				MBIROn":
								if(!protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
										protocole.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setMBIR(splitTable[1]);
								}	
								break;	
								
							// DETECTION DU TITRE DE LA SERIE	
							case "				seriesDescriptionRecon":
								protocole.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setTitre(splitTable[1]);								
								break;						
						}			
					}	
					// FERMETURE DU BUFFEREDREADER
					br.close();
				} catch (IOException e1) {
					System.out.println("Fichier introuvable");
				}							
							
				// CREATION DE TROIS OBJETS PROTOCOL A PARTIR DE LA REFERENCE SELECTIONNEE
				referenceLowDose = new Protocol (listReference.getSelectedValue().toString());
				referenceStandard = new Protocol (listReference.getSelectedValue().toString());
				referenceHighIQ = new Protocol (listReference.getSelectedValue().toString());
											
				try {
					// OUVERTURE DU FICHIER DE BdD
					baseDeDonn�es = Workbook.getWorkbook(new File ("Parameters/Base_de_donnees.xls"));
				} catch (BiffException e1) {e1.printStackTrace();
				} catch (IOException e1) {e1.printStackTrace();}	
				// SELECTION DE LA PREMIERE FEUILLE DU CLASSEUR
				Discovery = baseDeDonn�es.getSheet(0);
				
				// DETECION DE L'INDEX DE LA REFERENCE SELECTIONNEE
				int j = listReference.getSelectedIndex();
				ligne = ligneReference[j];
								
				// DETECTION DES PARAMETRES DES TROIS PROPOSITIONS D'OPTIMISATION
				setProtocolStructure(referenceLowDose, referenceStandard, referenceHighIQ, ligne);			
				
				// AFFICHAGE DE LA COMPOSITION DES PROTOCOLES A OPTIMISER ET DE REFERENCE
				structurePanel = new ProtoStructurePanel(protocole, referenceStandard, resultPanelSize);
				
				// DEFINITION DU LAYOUT DU JPANEL CENTER
				center.setLayout(new GridLayout(1,1));
				// AJOUT DU STRUCTUREPANEL AU JPANEL CENTER
				center.add(structurePanel);
				// ACTUALISATION DU JPANEL CENTER
				center.revalidate();	
			}
		}
	}
	
	/**************************************************************************************************************************/
	/** 	ACTION DU BOUTON "OPTIMISATION"	**/
	
	class OptimisationAction implements ActionListener {
		public void actionPerformed(ActionEvent e){	
			
			// REMPLACEMENT DU BOUTON "OPTIMISATION" PAR LE BOUTON "VALISATION"
			boutonOptimisation.setVisible(false);
			boutonValidation.setPreferredSize(boutonOptimisation.getSize());
			boutonValidation.setVisible(true);
			
			// CREATION D'UN JTABBEDPANE QUI CONTIENDRA LES ONGLETS DE RECON NATIVES
			JTabbedPane barreOngletNatif = new JTabbedPane();
			// CREATION D'UNE ARRAYLIST DE JTABBEDPANE QUI CONTIENDRA LES BARRES D'ONGLETS DE RECON DE CHAQUE SERIE
			ArrayList<JTabbedPane> barreOngletRecon = new ArrayList<JTabbedPane>();
			
			// RECUPERATION DU MATCHING ENTRE LE PROTOCOLE A OPTIMISER ET LE PROTOCOLE DE REF
			matchedRecon = structurePanel.getMatchedRecon();
			
			
			// INSTANCIATION D'UN TABLEAU DE RESULTPANELS (AUTANT DE RESULTPANELS QUE DE SERIES)
			nativePanel = new ResultPanel[50];
			// INITIALISATION DU NOMBRE DE NATIVEPANEL
			nativePanelNb = -1;

			// INITIALISATION DU NOMBRE DE MATCH
			matchNb = -1;
			
			//INSTANCIATION DU NUMERO DE LIGNE A 0
			matchedReconRow = 0;
			
			//TANT QUE LA PREMIERE COLONNE CONTIENT UNE RECON
			while(matchedReconRow < matchedRecon.length){
				// SI A LA MEME LIGNE LA DEUXIEME COLONNE CONTIENT UNE RECON
				if(matchedRecon[matchedReconRow][1] != null){
					// INCREMENTATION DU NOMBRE DE MATCHS
					matchNb++;
					// AJOUT DE LA RECON A OPTIMISER DANS LA LISTE APROPRIEE
					matchProto.add(matchNb, matchedRecon[matchedReconRow][0]);
					// AJOUT DE LA RECON A OPTIMISER DANS LA LISTE APROPRIEE
					matchRef.add(matchNb, matchedRecon[matchedReconRow][1]);
					
					// RECUPERATION DES INDICES DU PROTO ET DE LA REF
					SGRproto = matchedRecon[matchedReconRow][0];
					SGRref = matchedRecon[matchedReconRow][1];					
					s�rieRef = Integer.parseInt(((String) SGRref).substring(0, 1));
					groupeRef = Integer.parseInt(((String) SGRref).substring(1, 2));
					reconRef = Integer.parseInt(((String) SGRref).substring(2));
					
					
					// SI LA RECON DU PROTO EST LA RECON NATIVE
					if(Integer.parseInt(((String) SGRproto).substring(2)) == 0){
						// INCREMENTATION DE MATCHSERIENB
						nativePanelNb++;										
						// AJOUT D'UN JTABBEDPANE A LA LIST 
						barreOngletRecon.add(nativePanelNb, new JTabbedPane());		
						// CREATION D'UN NOUVEAU RESULTPANEL
						nativePanel[nativePanelNb] = new ResultPanel(protocole, SGRproto, referenceLowDose, referenceStandard, referenceHighIQ, SGRref, resultPanelSize);
						// AJOUT DU RESULPANEL A LA BARRE D'ONGLET DE RECON
						barreOngletRecon.get(nativePanelNb).add(referenceStandard.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getTitre(), nativePanel[nativePanelNb]);
						// AJOUT DU LA BARRE D'ONGLET DE RECON A LA BARRE D'ONGLET DE SERIE
						barreOngletNatif.add(referenceStandard.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getTitre(), barreOngletRecon.get(nativePanelNb));
					}
					// SINON LE MATCH EST DE TYPE RECON
					else{
						// CREATION D'UN NOUVEAU RESULTPANEL
						reconPanel = new ResultPanel(protocole, SGRproto, referenceLowDose, referenceStandard, referenceHighIQ, SGRref, resultPanelSize);
						// AJOUT DU RESULPANEL A LA BARRE D'ONGLET DE RECON
						barreOngletRecon.get(nativePanelNb).add(referenceStandard.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getTitre(), reconPanel);
					}
				}
				// INCREMENTATION DE LA LIGNE
				matchedReconRow++;
			}
							
			//  SUPPRESSION DE TOUS LES ANCIENS ELEMENTS DE CENTER
			center.removeAll();
			// AJOUT DE LE BARRE D'ONGLET DE SERIE A CENTER
			center.add(barreOngletNatif);
			// ACTUALISATION DU JPANEL CENTER
			center.revalidate();
		}
	}


	/**************************************************************************************************************************/
	/** 	ACTION DU BOUTON "VALIDATION"	**/
	
	class ValidationAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			// INSTANCIATION DU BOOLEAN VALIDATION A TRUE
			boolean validation = true;
			// POUR TOUS LES PANEL DE RECON NATIVE
			for(int n=0; n <= nativePanelNb; n++){
				// SI AUCUNE DES PROPOSITIONS D'OPTIMISATION N'EST SELECTIONNEE
				if(!nativePanel[n].lowDoseDicom.tick.isSelected() && !nativePanel[n].mediumDoseDicom.tick.isSelected() && !nativePanel[n].highDoseDicom.tick.isSelected()){
					validation = false;
				}
			}
			// SI VALIDATION = TRUE
			if (validation == true){
				
				// UTILISATION DE LA METHODE D'ECRITURE DE PROTOCOL DANS LE FICHIER APPROPRIE
				try {
					ajoutProtocol();
				} catch (IOException | BiffException | WriteException e1) {e1.printStackTrace();}
				 
				
				if (optimisationAvanc�e == true){
					new JOptionPane();
					JLabel label1 = new JLabel("Voulez-vous cr�er un autre protocole ''"+protocole.getTitre()+"'' � partir des s�ries propos�es?");
					JLabel label2 = new JLabel("(Cette option peut-�tre d�sactiv�e dans le menu ''Outils''.)");
					label1.setHorizontalAlignment(JLabel.CENTER);
					label2.setHorizontalAlignment(JLabel.CENTER);
					JLabel message[] = {label1, label2};
					int option = JOptionPane.showConfirmDialog(cadre,  message,
							"Optimisation avanc�e", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (option == JOptionPane.NO_OPTION){
						boutonValidation.setVisible(false);
						boutonConfiguration.setVisible(true);
						center.removeAll();
						center.updateUI();
					}
				}				
				else {
					boutonValidation.setVisible(false);
					boutonConfiguration.setVisible(true);
					center.removeAll();
					center.updateUI();
				}
				new JOptionPane();
				JOptionPane.showMessageDialog(cadre, 
						"Le protocole ''"+protocole.getTitre()+"'' a �t� optimis� avec succ�s.\n"+
						"Un fichier de suivi d'optimisation a �t� cr�� � l'emplacement suivant: ''"+
						parentPath.toString()+"/Optimisation_ProtoEnhance.xls''.", "ProtoEnhance V1.2", JOptionPane.INFORMATION_MESSAGE);			
			}
			else{
				new JOptionPane();
				JOptionPane.showMessageDialog(cadre, "Veuillez d'abord s�lectionner une proposition" +
				" pour chaque s�rie.", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}


	/**************************************************************************************************************************/
	/** 	METHODE DE DETECTION DES PROTOCOLES		**/
	
	void getProtocole (String pPath) throws JDOMException, IOException {
		
		SAXBuilder builder = new SAXBuilder();
		//Contruction du document bas� sur le fichier .xml
		Document Protocol_adult = builder.build(new File(pPath+"/system_state/Protocol.adult.xml")); 
		//R�cup�ration de l'�l�ment racine
		Element root = Protocol_adult.getRootElement();
		//Cr�ation d'une liste contenant toute les zone anatomique
		List listCategory = root.getChildren("category");
		// Cr�ation d'un iterator sur la liste
		Iterator i = listCategory.iterator();
		while(i.hasNext()){
			// Cr�ation de l'�l�ment courrant
			Element currentCategory = (Element)i.next();
			int category = Integer.parseInt(currentCategory.getAttributeValue("id").toString())+1;
			//Cr�ation d'une liste des protocoles de la cat�gorie courrante
			List listProtocol = currentCategory.getChildren();
			// Cr�ation d'un iterator sur la liste
			Iterator j = listProtocol.iterator();
			while(j.hasNext()){
				Element currentProtocol = (Element)j.next();
				int protocol = Integer.parseInt(currentProtocol.getAttributeValue("id").toString())+1;				
				// Classement des protocoles en fonction de la zone anatomique correspondante
				if(currentCategory.getAttributeValue("name").toString().contains("head")){
					ProtocoleHead.addElement(Integer.toString(category)+"."+Integer.toString(protocol)+" "+currentProtocol.getAttributeValue("name").toString());
				}
				
				if(currentCategory.getAttributeValue("name").toString().contains("orbit")){
					ProtocoleOrbit.addElement(Integer.toString(category)+"."+Integer.toString(protocol)+" "+currentProtocol.getAttributeValue("name").toString());
				}
	
				if(currentCategory.getAttributeValue("name").toString().contains("cspine")){
					ProtocoleCSpine.addElement(Integer.toString(category)+"."+Integer.toString(protocol)+" "+currentProtocol.getAttributeValue("name").toString());
				}
	
				if(currentCategory.getAttributeValue("name").toString().contains("shoulder")){
					ProtocoleShoulder.addElement(Integer.toString(category)+"."+Integer.toString(protocol)+" "+currentProtocol.getAttributeValue("name").toString());
				}
				
				if(currentCategory.getAttributeValue("name").toString().contains("chest")){
					ProtocoleChest.addElement(Integer.toString(category)+"."+Integer.toString(protocol)+" "+currentProtocol.getAttributeValue("name").toString());
				}
				
				if(currentCategory.getAttributeValue("name").toString().contains("abdomen")){
					ProtocoleAbdomen.addElement(Integer.toString(category)+"."+Integer.toString(protocol)+" "+currentProtocol.getAttributeValue("name").toString());
				}
	
				if(currentCategory.getAttributeValue("name").toString().contains("lumbar")){
					ProtocoleLumbar.addElement(Integer.toString(category)+"."+Integer.toString(protocol)+" "+currentProtocol.getAttributeValue("name").toString());
				}
	
				if(currentCategory.getAttributeValue("name").toString().contains("pelvis")){
					ProtocolePelvis.addElement(Integer.toString(category)+"."+Integer.toString(protocol)+" "+currentProtocol.getAttributeValue("name").toString());
				}
				
				if(currentCategory.getAttributeValue("name").toString().contains("lower_extremity")){
					ProtocoleLowerExtremity.addElement(Integer.toString(category)+"."+Integer.toString(protocol)+" "+currentProtocol.getAttributeValue("name").toString());
				}
	
				if(currentCategory.getAttributeValue("name").toString().contains("miscellaneous")){
					ProtocoleMiscellaneous.addElement(Integer.toString(category)+"."+Integer.toString(protocol)+" "+currentProtocol.getAttributeValue("name").toString());
				}
				//Enregistrement du nom du fichier proto dans la case du tableau correspondante
				protoFile[nbCategory][nbProtocol] = currentProtocol.getAttributeValue("file").toString();
				nbProtocol++;
			}
			nbCategory++;
			nbProtocol = 0;
		}		
	}

	/**************************************************************************************************************************/
	/** 	METHODE DE DETECTION DES REFERENCES DANS LA BdD		**/
	
	public void getReference() throws BiffException, IOException, WriteException{		
		baseDeDonn�es = Workbook.getWorkbook(new File("Parameters/Base_de_donnees.xls")); 																				
		Discovery = baseDeDonn�es.getSheet(0); 
		for(int y = 2; y < Discovery.getRows(); y++){
			if (Discovery.getCell(0, y).getContents().contains("ADULT HEAD")){
				ligneReferenceHead[nbReferenceHead] = y;
				ReferenceHead.addElement(Discovery.getCell(1, y).getContents().toString());
				nbReferenceHead++;
			}
			if (Discovery.getCell(0, y).getContents().contains("ADULT ORBIT")){
				ligneReferenceOrbit[nbReferenceOrbit] = y;
				ReferenceOrbit.addElement(Discovery.getCell(1, y).getContents().toString());
				nbReferenceOrbit++;
			}
			if (Discovery.getCell(0, y).getContents().contains("ADULT CSPINE")){
				ligneReferenceCSpine[nbReferenceCSpine] = y;
				ReferenceCSpine.addElement(Discovery.getCell(1, y).getContents().toString());
				nbReferenceCSpine++;
			}
			if (Discovery.getCell(0, y).getContents().contains("ADULT SHOULDER")){
				ligneReferenceShoulder[nbReferenceShoulder] = y;
				ReferenceShoulder.addElement(Discovery.getCell(1, y).getContents().toString());
				nbReferenceShoulder++;
			}
			if (Discovery.getCell(0, y).getContents().contains("ADULT CHEST")){
				ligneReferenceChest[nbReferenceChest] = y;
				ReferenceChest.addElement(Discovery.getCell(1, y).getContents().toString());
				nbReferenceChest++;
			}
			if (Discovery.getCell(0, y).getContents().contains("ADULT ABDOMEN")){
				ligneReferenceAbdomen[nbReferenceAbdomen] = y;
				ReferenceAbdomen.addElement(Discovery.getCell(1, y).getContents().toString());
				nbReferenceAbdomen++;
			}
			if (Discovery.getCell(0, y).getContents().contains("ADULT LUMBAR")){
				ligneReferenceLumbar[nbReferenceLumbar] = y;
				ReferenceLumbar.addElement(Discovery.getCell(1, y).getContents().toString());
				nbReferenceLumbar++;
			}
			if (Discovery.getCell(0, y).getContents().contains("ADULT PELVIS")){
				ligneReferencePelvis[nbReferencePelvis] = y;
				ReferencePelvis.addElement(Discovery.getCell(1, y).getContents().toString());
				nbReferencePelvis++;
			}
			if (Discovery.getCell(0, y).getContents().contains("ADULT LOWER E0TREMITY")){
				ligneReferenceLowerExtremity[nbReferenceLowerExtremity] = y;
				ReferenceLowerExtremity.addElement(Discovery.getCell(1, y).getContents().toString());
				nbReferenceLowerExtremity++;
			}
			if (Discovery.getCell(0, y).getContents().contains("ADULT MISCELLANEOUS")){
				ligneReferenceMiscellaneous[nbReferenceMiscellaneous] = y;
				ReferenceMiscellaneous.addElement(Discovery.getCell(1, y).getContents().toString());
				nbReferenceMiscellaneous++;
			}		
		}
	}
	
	
	
	/**************************************************************************************************************************/
	/** 	METHODE DE DETECTION DE LA STRUCTURE DES TROIS PROTOCOLES DE REFERENCE		**/
	
	public void setProtocolStructure(Protocol pReferenceLowDose, Protocol pReferenceStandard, Protocol pReferenceHighIQ, int pLigne) {
		
		
		// REINITIALISATION DU NUMERO DE SERIE
		s�rie = -1;	
		
		// DU DEBUT DU PROTOCOL A LA FIN DU FICHIER EXCEL, I++
		for (int i = pLigne; i <= Discovery.getRows(); i++){
			// SI LA CELLULE CORRESPOND A LA FIN DU PROTOCOL
			if(Discovery.getCell(0, i).getContents().contains("END")){
				// DETECTION DU NUMERO DE LA LIGNE
				endProtocol = i;
				i = Discovery.getRows();
			}
		}
		
		// ANALYSE DE TOUTE LES LIGNES DU PROTOCOLE
		while(pLigne <= endProtocol){
			
			// SI LA CELLULE DE LA 3eme COLONNE LIGNE pLigne CONTIENT "Serie"
			if (Discovery.getCell(2, pLigne).getContents().contains("Serie")){
				// INCREMENT DU NUMERO DE SERIE
				s�rie++;
				// AJOUT D'UNE SERIE AUX TROIS PROTOCOLES DE REFERENCE
				pReferenceLowDose.setS�rie(new Serie(), s�rie);
				pReferenceStandard.setS�rie(new Serie(), s�rie);
				pReferenceHighIQ.setS�rie(new Serie(), s�rie);	
				// REINITIALISATION DU NUMERO DE GROUP
				groupe = -1;
			}	
				
			// SI LA CELLULE DE LA 4eme COLONNE LIGNE pLigne CONTIENT "Group"
			if (Discovery.getCell(3, pLigne).getContents().contains("Group")){
				// INCREMENT DU NUMERO DE GROUP
				groupe++;
				// AJOUT D'UN GROUP A LA SERIE EN COURS POUR LES TROIS PROTOCOLES DE REFERENCE
				pReferenceLowDose.getS�rie(s�rie).setGroup(new Group(), groupe);
				pReferenceStandard.getS�rie(s�rie).setGroup(new Group(), groupe);
				pReferenceHighIQ.getS�rie(s�rie).setGroup(new Group(), groupe);
				
				//DETECTION DU TYPE DE SERIE
				pReferenceLowDose.getS�rie(s�rie).setSeriesType(Discovery.getCell(5, pLigne).getContents());
				pReferenceStandard.getS�rie(s�rie).setSeriesType(Discovery.getCell(5, pLigne+1).getContents());
				pReferenceHighIQ.getS�rie(s�rie).setSeriesType(Discovery.getCell(5, pLigne+2).getContents());
				
				// REINITIALISATION DU NUMERO DE RECON
				recon = -1;
				// RECUEIL DES PARAMETRES DU GROUP EN COURS DE LA SERIE EN COURS POUR LES TROIS PROTOCOLES DE REFERENCE
				setGroupParameters(pReferenceLowDose, pLigne);
				setGroupParameters(pReferenceStandard, pLigne+1);
				setGroupParameters(pReferenceHighIQ, pLigne+2);				
			}
				
			// SI LA CELLULE DE LA 5eme COLONNE LIGNE pLigne CONTIENT "Recon"
			if (Discovery.getCell(4, pLigne).getContents().contains("Recon")){
				recon++;
				// AJOUT D'UNE RECON AU GROUP EN COURS DE LA SERIE EN COURS POUR LES TROIS PROTOCOLES DE REFERENCE
				pReferenceLowDose.getS�rie(s�rie).getGroup(groupe).setRecon(new Recon(), recon);
				pReferenceStandard.getS�rie(s�rie).getGroup(groupe).setRecon(new Recon(), recon);
				pReferenceHighIQ.getS�rie(s�rie).getGroup(groupe).setRecon(new Recon(), recon);			
				// RECUEIL DES PARAMETRES DE LA RECON DU GROUP EN COURS DE LA SERIE EN COURS POUR LES TROIS PROTOCOLES DE REFERENCE
				setReconParameters(pReferenceLowDose, pLigne);
				setReconParameters(pReferenceStandard, pLigne+1);
				setReconParameters(pReferenceHighIQ, pLigne+2);				
				// DETECTION DU NOM DE LA RECON 
				String [] splitTable = new String [2];
				splitTable = Discovery.getCell(4, pLigne).getContents().split(" +: +");
				pReferenceLowDose.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setTitre(splitTable[1]);
				pReferenceStandard.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setTitre(splitTable[1]);
				pReferenceHighIQ.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setTitre(splitTable[1]);		
			}
			// PASSAGE A LA SERIE/GROUP/RECON SUIVANTE
			pLigne = pLigne + 3;
		}
	}	
		
		

	/**************************************************************************************************************************/
	/** 	METHODE DE DETECTION DES PARAMETRES DU GROUP D'UN PROTOCOLE DE REFERENCE		**/
	
	public void setGroupParameters(Protocol pReference, int pLigne) {
	
		
		//DETECTION DU TYPE DE GROUP
		pReference.getS�rie(s�rie).getGroup(groupe).setGroupType(Discovery.getCell(6, pLigne).getContents());
		// DETECTION DU TEMPS DE ROTATION
		pReference.getS�rie(s�rie).getGroup(groupe).setTpsRotation(Discovery.getCell(7, pLigne).getContents());
		// DETECTION DE L'EPAISSEUR DE COUPE (RECON NATIVE)
		pReference.getS�rie(s�rie).getGroup(groupe).setThick(Discovery.getCell(8, pLigne).getContents());
		//SI C'EST UNE ACQUISITION AXIALE
		if(pReference.getS�rie(s�rie).getGroup(groupe).getGroupType() == "Axial"){
			// DETECTION DU MODE DE DETECTEUR
			pReference.getS�rie(s�rie).getGroup(groupe).setMode(Discovery.getCell(9, pLigne).getContents());
		}
		// SINON
		else{
			// DETECTION DU PITCH
			pReference.getS�rie(s�rie).getGroup(groupe).setPitch(Discovery.getCell(10, pLigne).getContents());
		}
		// DETECTION DE LA COLLIMATION
		pReference.getS�rie(s�rie).getGroup(groupe).setRows(Discovery.getCell(11, pLigne).getContents());
		// DETECTION DE L'INTERVALE DE COUPE (RECON NATIVE)
		pReference.getS�rie(s�rie).getGroup(groupe).setInter(Discovery.getCell(12, pLigne).getContents());
		// DETECTION DU TYPE DE SFOV
		pReference.getS�rie(s�rie).getGroup(groupe).setSFOVType(Discovery.getCell(13, pLigne).getContents());
		// SI LE SFOV EST DE TYPE "LARGEBODY"
		if(pReference.getS�rie(s�rie).getGroup(groupe).getSFOVType()== "ScanFieldOfViewLargeBodyVCT"){
			// LA TAILLE EST DE 50cm
			pReference.getS�rie(s�rie).getGroup(groupe).setSFOVSize("50");
		}
		else{
			// SINON ELLE EST DE 32cm
			pReference.getS�rie(s�rie).getGroup(groupe).setSFOVSize("32");
		}
		// DETECTION DES kV
		pReference.getS�rie(s�rie).getGroup(groupe).setKV(Discovery.getCell(14, pLigne).getContents());
		// DETECTION DU SMACRTCATHODEMODE
		pReference.getS�rie(s�rie).getGroup(groupe).setSmartCathodeMode(Discovery.getCell(15, pLigne).getContents());
		// DETECTION DU SMACRTCATHODENUMDEFLECTIONS
		pReference.getS�rie(s�rie).getGroup(groupe).setSmartCathodeNumDeflections(Discovery.getCell(16, pLigne).getContents());
		// DETECTION DU FlagmA
		pReference.getS�rie(s�rie).getGroup(groupe).setFlagMA(Discovery.getCell(17, pLigne).getContents());
		if(pReference.getS�rie(s�rie).getGroup(groupe).getFlagMA().contains("0")){
			// DETECTION DES mA
			pReference.getS�rie(s�rie).getGroup(groupe).setMA(Discovery.getCell(18, pLigne).getContents());
		}
		else{
			// DETECTION DU NI
			pReference.getS�rie(s�rie).getGroup(groupe).setNI(Discovery.getCell(19, pLigne).getContents());
			// DETECTION DU mimA
			pReference.getS�rie(s�rie).getGroup(groupe).setMinma(Discovery.getCell(20, pLigne).getContents());
			// DETECTION DU maxMA
			pReference.getS�rie(s�rie).getGroup(groupe).setMaxma(Discovery.getCell(21, pLigne).getContents());
		}		
		// DETECTION DU CTDI
		pReference.getS�rie(s�rie).getGroup(groupe).setCtdi(Discovery.getCell(22, pLigne).getContents());
		// DETECTION DU PDL
		pReference.getS�rie(s�rie).getGroup(groupe).setPDL(Discovery.getCell(23, pLigne).getContents());
		
	}	
		
	/**************************************************************************************************************************/
	/** 	DETECTION DES PARAMETRES DE RECON D'UN PROTOCOLE DE REFERENCE		**/	
		
	public void setReconParameters(Protocol pReference, int pLigne) {
		// DETECTION DE L'ACTIVATION DE LA RECON (tjs "yes" dans le cas des r�f�recnes)
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setActivation("Yes");
		// DETECTION DE L'EPAISSEUR DE COUPE
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setThick(Discovery.getCell(8, pLigne).getContents());
		// DETECTION DE L'INTERVALE DE COUPE
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setInter(Discovery.getCell(12, pLigne).getContents());		
		// DETECTION DU FILTRE DE RECON
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setFilter(Discovery.getCell(24, pLigne).getContents());
		// DETECTION DU TYPE DE RECON
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setTyperecon(Discovery.getCell(25, pLigne).getContents());
		// DETECTION DE LA CONFIG D'ASIR
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setAsirConfig(Discovery.getCell(26, pLigne).getContents());
		// DETECTION DU MODE D'ASIR
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setAsirMode(Discovery.getCell(27, pLigne).getContents());
		// DETECTION DE IQE
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setIqe(Discovery.getCell(28, pLigne).getContents());
		// DETECTION DE VEO
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setMBIR(Discovery.getCell(29, pLigne).getContents());
		// DETECTION DU CHEMIN DES IMAGES ANTHROPOMORPHIQUES
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setPathAnthropomorphic(Discovery.getCell(30, pLigne).getContents());
		// DETECTION DU CHEMIN DE L'IMAGE MTF
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setPathMTF(Discovery.getCell(31, pLigne).getContents());
		// DETECTION DU CHEMIN DE L'IMAGE LCD
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setPathLCD(Discovery.getCell(32, pLigne).getContents());
		// DETECTION DES VALEURS DE RESO EN X, Y ET Z
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setresoZ(Discovery.getCell(33, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setresoX(Discovery.getCell(34, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setresoY(Discovery.getCell(35, pLigne).getContents());
		// DETECTION DES VALEURS DE SNR
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setSNR0(Discovery.getCell(36, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setSNR1(Discovery.getCell(37, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setSNR2(Discovery.getCell(38, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setSNR3(Discovery.getCell(39, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setSNR4(Discovery.getCell(40, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setSNR5(Discovery.getCell(41, pLigne).getContents());
		// DETECTION DES VALEURS DE BRUIT
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setBruit0(Discovery.getCell(42, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setBruit1(Discovery.getCell(43, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setBruit2(Discovery.getCell(44, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setBruit3(Discovery.getCell(45, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setBruit4(Discovery.getCell(46, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setBruit5(Discovery.getCell(47, pLigne).getContents());
		// DETECTION DES VALEURS D'UNIFORMITE
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setUniformit�0(Discovery.getCell(48, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setUniformit�1(Discovery.getCell(49, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setUniformit�2(Discovery.getCell(50, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setUniformit�3(Discovery.getCell(51, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setUniformit�4(Discovery.getCell(52, pLigne).getContents());
		pReference.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setUniformit�5(Discovery.getCell(53, pLigne).getContents());
	}	


	/**************************************************************************************************************************/
	/** 	ECRITURE DU PROTOCOLE CHOISI DANS LE FICHIER PROTO ET DANS LE FICHIER EXCEL		**/	

	void ajoutProtocol () throws IOException, BiffException, WriteException {	
		
		String editionSerie = "No";
		String editionGroupe = "No";
		String editionRecon = "No";
		
		// OUVERTURE DU FICHIER .PROTO DU PROTOCOLE SELECTIONNE
		InputStream ips = 	new FileInputStream(protoFilePath);
		InputStreamReader ipsr = new InputStreamReader (ips);
		BufferedReader br = new BufferedReader(ipsr);
		
		// CREATION D'UN FICHIER A PARTIR DU FICHIER PROTO ORIGINAL
		File originalFile = new File(protoFilePath);
		
		// CREATION D'UN FICHIER PROTO TEMPORAIRE
		String tempFilePath = protoFilePath.substring(0, protoFilePath.length()-6)+"_Temp.proto";
		File tempFile = new File(tempFilePath);	
		FileWriter fw = new FileWriter (tempFile);
		// INSTANCIATION DU BUFFEREDWRITER
		BufferedWriter bw = new BufferedWriter (fw);
		
		// CREATION D'UN STRING CORRESPONDANT A LA LIGNE EN COURS
		String ligne;
		// CREATION D'UN STRING CORRESPONDANT A LA LIGNE UNE FOIS EDITEE
		String ligneEdit�e;
		
		// CREATION D'UN TABLEAU CONTENANT LE PARAMETRE ET LA VALEUR 
		String [] splitTable = new String [2];
		
		// INITIALISATION DU NUMERO DE SERIE
		s�rie = -1;		
		// INITIALISATION DU NUMERO DE MATCHES
		matchNb = 0;
		// INITIALISATION DU NUMERO DE NATIVEPANEL
		nativePanelNb = -1;
		
		// LECTURE DE LA TOTALITE DU FICHIER PROTO
		while((ligne = br.readLine()) != null){
			ligneEdit�e = ligne;
			
			// SI LA TOTALITE DES MATCHES N'ONT PAS ETE EDITE
			if (matchNb < matchProto.size()){
				// DESACTIVATION DE L'EDITION DU FICHIER
				editionSerie = "No";
				editionGroupe = "No";
				editionRecon = "No";
				
				// RECUPERATION DES INDICES SGR EN COURS DU PROTOCOLE
				SGRproto = matchProto.get(matchNb);
				s�rieProto = Integer.parseInt(((String) SGRproto).substring(0, 1));
				groupeProto = Integer.parseInt(((String) SGRproto).substring(1, 2));
				reconProto = Integer.parseInt(((String) SGRproto).substring(2));
				
				// SI LA SERIE ANALYSEE DANS LE FICHIER PROTO CORRESPOND A LA SERIE D'UN MATCH
				if(s�rieProto == s�rie){
					// ACTIVATION DE L'EDITION DE LA PARTIE SERIE DU FICHIER
					editionSerie = "Yes";
					
					// SI LA RECON DU MATCH A OPTIMISER EST NATIVE
					if (reconProto == 0){
						// RECUPERATION DU PROTOCOLE CHOISI PAR L'UTILISATEUR
						if(nativePanel[nativePanelNb].lowDoseDicom.tick.isSelected()){
							reference = referenceLowDose;
						}
						else if(nativePanel[nativePanelNb].mediumDoseDicom.tick.isSelected()){
							reference = referenceStandard;
						}
						else if(nativePanel[nativePanelNb].highDoseDicom.tick.isSelected()){
							reference = referenceHighIQ;
						}
					}
					// SINON
					else{
						// LE PROTOCOLE DE REFERENCE EST LE PROTOCOLE STANDARD
						reference = referenceStandard;
					}
				}
				
				// SI LA SERIE ET LE GROUP ANALYSES DANS LE FICHIER PROTO CORRESPONDENT A LA SERIE ET AU GROUPE D'UN MATCH
				if(s�rieProto == s�rie && groupeProto == groupe){
					// ACTIVATION DE L'EDITION DE LA PARTIE GROUPE DU FICHIER
					editionGroupe = "Yes";
				}
				
				// SI LA SERIE, LE GROUP ET LA RECON ANALYSES DANS LE FICHIER PROTO CORRESPONDENT A LA SERIE, AU GROUPE ET A LA RECON D'UN MATCH
				if(s�rieProto == s�rie && groupeProto == groupe && reconProto == recon){
					// ACTIVATION DE L'EDITION DE LA PARTIE RECON DU FICHIER
					editionRecon = "Yes";
				}
	
				
				if (editionSerie == "Yes" || editionGroupe == "Yes" || editionRecon == "Yes"){
					// RECUPERATION DU SGR DE REFERENCE
					SGRref = matchRef.get(matchNb);
					s�rieRef = Integer.parseInt(((String) SGRref).substring(0, 1));
					groupeRef = Integer.parseInt(((String) SGRref).substring(1, 2));
					reconRef = Integer.parseInt(((String) SGRref).substring(2));
				}	
						
				// SEPARATION DU PARAMETRES ET DE LA VALEUR
				splitTable = ligne.split(" += +");
				
				switch(splitTable[0]){
				// DETECION D'UNE SERIE
				case "	Series {":
					// INCREMENTATION DU NUMERO DE SERIE
					s�rie++;
					// REINITIANILISATION DU NOMBRE DE GROUPE
					groupe = -1;
					// SI LA SERIE ANALISEE CORRESPOND A LA SERIE DU MATCH
					if(s�rieProto == s�rie){
						// INCREMENTATION DU NUMERO DU NATIVEPANEL
						nativePanelNb++;
					}
					break;
				
				// DETECTION DE LA FIN D'UNE RECON	
				case "			}":
					if (editionRecon == "Yes"){
						// INCREMENTATION DU NUMERO DE MATCH
						matchNb++;
					}
						
					break;	
				
				// DETECTION DU TYPE DE SERIE	
				case "		seriesType":
					if (editionSerie == "Yes"){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getSeriesType();
					}				
					break;	
					
				// DETECTION D'UN GROUP	
				case "		Group {":
					// INCREMENTATION DU NUMERO DE Groupe
					groupe++;
					// REINITIALISATION DU NUMERO DE RECON
					recon = -1;
					break;	
				
				// DETECTION DES kV
				case "			kiloVolts":
					if (editionGroupe == "Yes"){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getKV();
					}						
					break;
					
				// DETECION DU SMARTCATHODEMODE
				case "			smartCathodeMode":
					if(editionGroupe == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getSmartCathodeMode();
					}
					break;	
						
				// DETECION DU SMARTCATHODENUMDEFLECTIONS
				case "			smartCathodeNumDeflections":
					if(editionGroupe == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getSmartCathodeNumDeflections();
					}
					break;		
					
				// DETECTION DES mA
				case "			milliAmps":
				if (editionGroupe == "Yes" && reference.getS�rie(s�rieRef).getGroup(groupeRef).getFlagMA().contains("0")){
						
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getMA();
					}
					break;	
					
				// DETECTION DU PLAN DU SCOUT					
				case "			scoutPlane":
					if(editionGroupe == "Yes" && reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getPlane();
					}
					break;	
					
				// DETECTION DE LA TAILLE DU SFOV
				case "			scanFieldOfViewSize":
					if(editionGroupe == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getSFOVSize();
					}
					break;
					
				// DETECTION DU TYPE DE SFOV
				case "			scanFieldOfViewType":
					if(editionGroupe == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getSFOVType();
					}
					break;
				
				// DETECTION DU TYPE DE GROUP
				case "			groupType":	
					if(editionGroupe == "Yes"){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getGroupType();
					}
					break;
					
				// DETECION DE L'EPAISSEUR DE COUPE POUR LA RECON NATIVE
				case "			imageThickness":
					if(editionGroupe == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout") && recon == -1){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getThick();
					}
					break;
					
				// DETECTION DU TEMPS DE ROTATION
				case "			rotationTime":
					if(editionGroupe == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getTpsRotation();
					}
					break;	
					
				// DETECTION DE L'INTERVALE POUR LA RECON NATIVE
				case "			scanSpacing":
					if(editionGroupe == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout") && recon == -1){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getInter();
					}
					break;	
					
				// DETECTION DE LA VITESSE DE TABLE
				case "			tableSpeed":
					if(editionGroupe == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getPitch();
					}
					break;
					
				// DETECTION DE LA MODULATION D'INTENSITE
				case "			automAFlag":
					if(editionGroupe == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getFlagMA();
					}
					break;		
													
				// DETECTION DU NI
				case "			automaNoiseIndex":
					if(editionGroupe == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout") 
					&& reference.getS�rie(s�rieRef).getGroup(groupeRef).getFlagMA().contains("2")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getNI();
					}
					break;				
				
				// DETECION DES mA MAX
				case "			automaMaxMilliAmps":
					if(editionGroupe == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")
					&& reference.getS�rie(s�rieRef).getGroup(groupeRef).getFlagMA().contains("2")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getMaxma();
					}
					break;
					
				// DETECION DES mA MIN
				case "			automaMinMilliAmps":
					if(editionGroupe == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")
					&& reference.getS�rie(s�rieRef).getGroup(groupeRef).getFlagMA().contains("2")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getMinma();
					}
					break;	
					
				// DETECION DU MODE DE DETECTEUR
				case "			numImagesPerRotation":
					if(editionGroupe == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout") 
						&& reference.getS�rie(s�rieRef).getGroup(groupeRef).getGroupType().contains("Axial")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getMode();
					}
					break;	
					
				// DETECION DE LA LARGEUR DE COLLIMATION
				case "			macroRowNumber":
					if(editionGroupe == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getRows();
					}
					break;	
				
				// DETECTION D'UNE RECON
				case "			Recon {":
					// INCREMENTATION DU NUMERO DE LA SERIE ANALYSEE
					recon++;
					break;
					
				// DETECTION DE L'ACTIVATION DE LA RECON	
				case "				isReconEnabled":
					if(editionRecon == "Yes"){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getActivation();
					}
					break;	
				
				// DETECTION DU FILTRE DE RECONSTRUCTION	
				case "				algorithm":
					if(editionRecon == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getFilter();
					}
					break;
					
				// DETECTION DE L'EPAISSEUR DE RECONSTRUCTION	
				case "				PMRimageThickness":
					if(editionRecon == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getThick();
					}
					break;	
					
				// DETECTION DE L'EPAISSEUR DE RECONSTRUCTION	
				case "				PMRinterval":
					if(editionRecon == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getInter();
					}	
					break;		
					
				// DETECTION D'IQE	
				case "				isIQEnhancedEnabled":
					if(editionRecon == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getIqe();
					}	
					break;		
					
				// DETECTION DU TYPE DE RECON	
				case "				segments":
					if(editionRecon == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getTyperecon();
					}
					break;	
					
				// DETECTION DU MODE D'ASIR	
				case "				iterativeMode":
					if(editionRecon == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getAsirMode();
					}
					break;		
					
				// DETECTION DU %age D'ASIR	
				case "				iterativeConfig":
					if(editionRecon == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getAsirConfig();
					}	
					break;		
					
				// DETECTION DU MBIR	
				case "				MBIROn":
					if(editionRecon == "Yes" && !reference.getS�rie(s�rieRef).getSeriesType().contains("Scout")){
						ligneEdit�e = splitTable[0]+" = "+reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getMBIR();
					}	
					break;						
				}
			}	
			// ECRITURE DE LA LIGNE EDITEE DANS LE FICHIER TEMPORAIRE
			bw.write(ligneEdit�e+"\n");
		    bw.flush();
		}
		// FERMETURE DU BUFFEREDREADER ET DU BUFFEREDWRITER
		br.close();
		bw.close();
		
		// SUPPRESION DU FCHIER ORIGINAL
		originalFile.delete();
		// ON RENOME LE FICHIER TEMPORAIRE AVEC LE NOM DU FICHIER ORIGINAL
		tempFile.renameTo(new File(protoFilePath));
		
		// OUVERTURE D'UN FICHIER EXCEL TEMP A PARIR DU FICHIER PRECEDEMENT CREER
		excelFileTemp = Workbook.getWorkbook (new File(parentPath+"/Optimisation_ProtoEnhance.xls"));
		// CREATION D'UN FICHIER EXCEL EDITABLE A PARTIR DU FICHIER TEMP
		excelFile = Workbook.createWorkbook (new File (parentPath+"/Optimisation_ProtoEnhance.xls"), excelFileTemp);
		// FERMETURE DU FICHIER TEMP
		excelFileTemp.close();		
		

		
		// CREATION DU FORMAT DES CELLULES DE RECON NATIVES
		WritableCellFormat s�rieFormat = new WritableCellFormat(cellFont);
		// DEFINITION DE L'ALIGNEMENT DES CELLULES DE RECON NATIVES
		s�rieFormat.setAlignment(Alignment.CENTRE);
		s�rieFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		// DEFINITION DES BORDURES DES CELLULES DE RECON NATIVES
		s�rieFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		s�rieFormat.setBorder(Border.TOP, BorderLineStyle.MEDIUM);
		
		// CREATION DU FORMAT DES CELLULES DE RECON NON NATIVES
		WritableCellFormat reconFormat = new WritableCellFormat(cellFont);
		// DEFINITION DE L'ALIGNEMENT DES CELLULES DE RECON NON NATIVES
		reconFormat.setAlignment(Alignment.CENTRE);
		reconFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		// DEFINITION DES BORDURES DES CELLULES DES RECON NON NATIVES
		reconFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		
		// CREATION DU FORMAT DES CELLULES VIDES
		WritableCellFormat blankFormat = new WritableCellFormat();
		// DEFINITION DU REMPLISSAGE DES CELULES VIDES
		blankFormat.setBackground(jxl.format.Colour.GRAY_25);
		// DEFINITION DES BORDURES DES CELLULES VIDES
		blankFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		
		// CREATION DU FORMAT DES CELLULES D'ESPACEMENT ENTRE PROTOCOLE
		WritableCellFormat spaceFormat = new WritableCellFormat();
		// DEFINITION DES BORDURES DES CELLULES D'ESPACEMENT ENTRE PROTOCOLE
		spaceFormat.setBorder(Border.TOP, BorderLineStyle.MEDIUM);
				
		// AJOUT DE LA DATE D'OPTIMISATION
		excelFile.getSheet(0).addCell(new Label(0, excelSheetLine, currentDatetoString, s�rieFormat));
		// AJOUT DU TITRE DU PROTOCOLE
		excelFile.getSheet(0).addCell(new Label(1, excelSheetLine, protocole.getTitre(), s�rieFormat));
		// DEFINITION DE LA LIGNE DE DEBUT DE FUSION DE CELLULE
		startMerge = excelSheetLine;

		// INITIALISATION DU NUMERO DE MATCHES
		matchNb = 0;
		// INITIALISATION DU NUMERO DE NATIVEPANEL
		nativePanelNb = 0;
		
		// DE 0 AU NOMBRE TOTAL DE SERIE DU PROTOCOLE 
		for (int s = 0; s <= protocole.getNbSerie(); s++){
			// DE 0 AU NOMBRE DE GROUPE TOTAL DE LA SERIE EN COURS
			for (int g = 0; g <= protocole.getS�rie(s).getNbGroup(); g++){
				// DE 0 AU NOMBRE DE RECON TOTAL DU GROUPE EN COURS DE LA SERIE EN COURS
				for (int r = 0; r <= protocole.getS�rie(s).getGroup(g).getNbRecon(); r++){
					// SI LE SGR EN COURS NE CORRESPOND NI A UN SCOUT NI A UNE RECON NON ACIVE
					if(!protocole.getS�rie(s).getSeriesType().contains("Scout") && protocole.getS�rie(s).getGroup(g).getRecon(r).getActivation().contains("Yes")){
						// RECUPERATION DES INDICES SGR EN COURS DU PROTOCOLE
						SGRproto = matchProto.get(matchNb);
						s�rieProto = Integer.parseInt(((String) SGRproto).substring(0, 1));
						groupeProto = Integer.parseInt(((String) SGRproto).substring(1, 2));
						reconProto = Integer.parseInt(((String) SGRproto).substring(2));
						
						// SI LE SGR EN COURS CORRESPOND A UN MATCH
						if(s == s�rieProto && g == groupeProto && r == reconProto){
							// SI LA RECON DU MATCH A OPTIMISER EST NATIVE
							if (reconProto == 0){
								// RECUPERATION DU PROTOCOLE CHOISI PAR L'UTILISATEUR
								if(nativePanel[nativePanelNb].lowDoseDicom.tick.isSelected()){
									reference = referenceLowDose;
								}
								else if(nativePanel[nativePanelNb].mediumDoseDicom.tick.isSelected()){
									reference = referenceStandard;
								}
								else if(nativePanel[nativePanelNb].highDoseDicom.tick.isSelected()){
									reference = referenceHighIQ;
								}
								nativePanelNb++;
							}
							// SINON
							else{
								// LE PROTOCOLE DE REFERENCE EST LE PROTOCOLE STANDARD
								reference = referenceStandard;
							}
							// RECUPERATION DES INDICES SGR DE LA REFERENCE 
							SGRref = matchRef.get(matchNb);				
							s�rieRef = Integer.parseInt(((String) SGRref).substring(0, 1));
							groupeRef = Integer.parseInt(((String) SGRref).substring(1, 2));
							reconRef = Integer.parseInt(((String) SGRref).substring(2));
							
							// SI LA TOTALITE DES MATCHS N'A PAS ETE DETECTEE
							if (matchNb < matchProto.size()-1){
								// INCREMENTATION DU NOMBRE DE MATCHS
								matchNb++;
							}
							
							// SI LA RECON EST NATIVE
							if (r == 0){
								excelFile.getSheet(0).addCell(new Label(2, excelSheetLine, Integer.toString(s+1), s�rieFormat));
								excelFile.getSheet(0).mergeCells(2, excelSheetLine, 2, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(3, excelSheetLine, Integer.toString(g+1), s�rieFormat));
								excelFile.getSheet(0).mergeCells(3, excelSheetLine, 3, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(4, excelSheetLine, Integer.toString(r+1), s�rieFormat));
								excelFile.getSheet(0).mergeCells(4, excelSheetLine, 4, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(5, excelSheetLine, protocole.getS�rie(s).getGroup(g).getRecon(r).getTitre(), s�rieFormat));
								excelFile.getSheet(0).mergeCells(5, excelSheetLine, 5, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(6, excelSheetLine, "Avant", s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(6, excelSheetLine+1, "Apr�s", s�rieFormat));
								writeParameters(7, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getGroupType(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getGroupType(), s�rieFormat);
								writeParameters(8, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getTpsRotation(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getTpsRotation(), s�rieFormat);
								writeParameters(9, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getThick(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getThick(), s�rieFormat);
								writeParameters(10, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getMode(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getMode(), s�rieFormat);
								writeParameters(11, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getPitch(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getPitch(), s�rieFormat);
								writeParameters(12, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getRows(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getRows(), s�rieFormat);
								writeParameters(13, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getInter(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getInter(), s�rieFormat);
								writeParameters(14, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getSFOVType(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getSFOVType(), s�rieFormat);
								writeParameters(15, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getKV(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getKV(), s�rieFormat);
								writeParameters(16, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getSmartCathodeMode(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getSmartCathodeMode(), s�rieFormat);
								writeParameters(17, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getMA(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getMA(), s�rieFormat);
								writeParameters(18, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getFlagMA(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getFlagMA(), s�rieFormat);
								writeParameters(19, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getNI(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getNI(), s�rieFormat);
								writeParameters(20, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getMinma(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getMinma(), s�rieFormat);
								writeParameters(21, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getMaxma(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getMaxma(), s�rieFormat);
								writeParameters(22, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getRecon(reconProto).getFilter(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getFilter(), s�rieFormat);
								writeParameters(23, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getRecon(reconProto).getTyperecon(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getTyperecon(), s�rieFormat);
								writeParameters(24, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getRecon(reconProto).getAsirConfig(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getAsirConfig(), s�rieFormat);
								writeParameters(25, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getRecon(reconProto).getIqe(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getIqe(), s�rieFormat);
								writeParameters(26, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getRecon(reconProto).getMBIR(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getMBIR(), s�rieFormat);					
							}
							// SINON SI LA RECON N'EST PAS NATIVE
							else{
								excelFile.getSheet(0).addCell(new Label(2, excelSheetLine, Integer.toString(s+1), reconFormat));
								excelFile.getSheet(0).mergeCells(2, excelSheetLine, 2, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(3, excelSheetLine, Integer.toString(g+1), reconFormat));
								excelFile.getSheet(0).mergeCells(3, excelSheetLine, 3, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(4, excelSheetLine, Integer.toString(r+1), reconFormat));
								excelFile.getSheet(0).mergeCells(4, excelSheetLine, 4, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(5, excelSheetLine, protocole.getS�rie(s).getGroup(g).getRecon(r).getTitre(), reconFormat));
								excelFile.getSheet(0).mergeCells(5, excelSheetLine, 5, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(6, excelSheetLine, "Avant", reconFormat));
								excelFile.getSheet(0).addCell(new Label(6, excelSheetLine+1, "Apr�s", reconFormat));								
								excelFile.getSheet(0).addCell(new Label(7, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).mergeCells(7, excelSheetLine, 7, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(8, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).mergeCells(8, excelSheetLine, 8, excelSheetLine+1);
								writeParameters(9, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getThick(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getThick(), reconFormat);
								excelFile.getSheet(0).addCell(new Label(10, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).mergeCells(10, excelSheetLine, 10, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(11, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).mergeCells(11, excelSheetLine, 11, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(12, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).mergeCells(12, excelSheetLine, 12, excelSheetLine+1);
								writeParameters(13, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getInter(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getInter(), reconFormat);
								excelFile.getSheet(0).addCell(new Label(14, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).mergeCells(14, excelSheetLine, 14, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(15, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).mergeCells(15, excelSheetLine, 15, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(16, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).mergeCells(16, excelSheetLine, 16, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(17, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).mergeCells(17, excelSheetLine, 17, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(18, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).mergeCells(18, excelSheetLine, 18, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(19, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).mergeCells(19, excelSheetLine, 19, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(20, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).mergeCells(20, excelSheetLine, 20, excelSheetLine+1);
								excelFile.getSheet(0).addCell(new Label(21, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).mergeCells(21, excelSheetLine, 21, excelSheetLine+1);
								writeParameters(22, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getRecon(reconProto).getFilter(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getFilter(), reconFormat);
								writeParameters(23, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getRecon(reconProto).getTyperecon(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getTyperecon(), reconFormat);
								writeParameters(24, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getRecon(reconProto).getAsirConfig(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getAsirConfig(), reconFormat);
								writeParameters(25, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getRecon(reconProto).getIqe(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getIqe(), reconFormat);
								writeParameters(26, protocole.getS�rie(s�rieProto).getGroup(groupeProto).getRecon(reconProto).getMBIR(), reference.getS�rie(s�rieRef).getGroup(groupeRef).getRecon(reconRef).getMBIR(), reconFormat);
							}	
							stopMerge = excelSheetLine + 1;
							excelSheetLine = excelSheetLine + 2;
						}
						// SINON SI LE SGR EN COURS DE CORRESPOND PAS A UN MATCH
						else{
							// SI LA RECON EST NATIVE
							if (r == 0){
								excelFile.getSheet(0).addCell(new Label(2, excelSheetLine, Integer.toString(s+1), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(3, excelSheetLine, Integer.toString(g+1), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(4, excelSheetLine, Integer.toString(r+1), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(5, excelSheetLine, protocole.getS�rie(s).getGroup(g).getRecon(r).getTitre(), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(6, excelSheetLine, "Non", s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(7, excelSheetLine, protocole.getS�rie(s).getGroup(g).getGroupType(), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(8, excelSheetLine, protocole.getS�rie(s).getGroup(g).getTpsRotation(), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(9, excelSheetLine, protocole.getS�rie(s).getGroup(g).getThick(), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(10, excelSheetLine, protocole.getS�rie(s).getGroup(g).getMode(), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(11, excelSheetLine, protocole.getS�rie(s).getGroup(g).getPitch(), s�rieFormat));
								if(protocole.getS�rie(s).getGroup(g).getRows().contains("32")){
									excelFile.getSheet(0).addCell(new Label(12, excelSheetLine, "20", s�rieFormat));
								}
								else if(protocole.getS�rie(s).getGroup(g).getRows().contains("64")){
									excelFile.getSheet(0).addCell(new Label(12, excelSheetLine, "40", s�rieFormat));
								}
								excelFile.getSheet(0).addCell(new Label(13, excelSheetLine, protocole.getS�rie(s).getGroup(g).getInter(), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(14, excelSheetLine, protocole.getS�rie(s).getGroup(g).getSFOVType(), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(15, excelSheetLine, protocole.getS�rie(s).getGroup(g).getKV(), s�rieFormat));
								if(protocole.getS�rie(s).getGroup(g).getSmartCathodeMode().contains("scBiasMode")){
									excelFile.getSheet(0).addCell(new Label(16, excelSheetLine, "No", s�rieFormat));
								}
								else if(protocole.getS�rie(s).getGroup(g).getSmartCathodeMode().contains("scDynamicDeflect")){
									excelFile.getSheet(0).addCell(new Label(16, excelSheetLine, "Yes", s�rieFormat));
								}
								excelFile.getSheet(0).addCell(new Label(17, excelSheetLine, protocole.getS�rie(s).getGroup(g).getMA(), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(18, excelSheetLine, protocole.getS�rie(s).getGroup(g).getFlagMA(), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(19, excelSheetLine, protocole.getS�rie(s).getGroup(g).getNI(), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(20, excelSheetLine, protocole.getS�rie(s).getGroup(g).getMinma(), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(21, excelSheetLine, protocole.getS�rie(s).getGroup(g).getMaxma(), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(22, excelSheetLine, protocole.getS�rie(s).getGroup(g).getRecon(r).getFilter(), s�rieFormat));
								if(protocole.getS�rie(s).getGroup(g).getRecon(r).getTyperecon().contains("0")){
									excelFile.getSheet(0).addCell(new Label(23, excelSheetLine, "Full", reconFormat));
								}	
								else{
									excelFile.getSheet(0).addCell(new Label(23, excelSheetLine, "Plus", reconFormat));
								}
								excelFile.getSheet(0).addCell(new Label(24, excelSheetLine, protocole.getS�rie(s).getGroup(g).getRecon(r).getAsirConfig(), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(25, excelSheetLine, protocole.getS�rie(s).getGroup(g).getRecon(r).getIqe(), s�rieFormat));
								excelFile.getSheet(0).addCell(new Label(26, excelSheetLine, protocole.getS�rie(s).getGroup(g).getRecon(r).getMBIR(), s�rieFormat));				
							}
							// SINON SI LA RECON N'EST PAS NATIVE
							else{
								excelFile.getSheet(0).addCell(new Label(2, excelSheetLine, Integer.toString(s+1), reconFormat));
								excelFile.getSheet(0).addCell(new Label(3, excelSheetLine, Integer.toString(g+1), reconFormat));
								excelFile.getSheet(0).addCell(new Label(4, excelSheetLine, Integer.toString(r+1), reconFormat));
								excelFile.getSheet(0).addCell(new Label(5, excelSheetLine, protocole.getS�rie(s).getGroup(g).getRecon(r).getTitre(), reconFormat));
								excelFile.getSheet(0).addCell(new Label(6, excelSheetLine, "Non", reconFormat));
								excelFile.getSheet(0).addCell(new Label(7, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).addCell(new Label(8, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).addCell(new Label(9, excelSheetLine, protocole.getS�rie(s).getGroup(g).getThick(), reconFormat));
								excelFile.getSheet(0).addCell(new Label(10, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).addCell(new Label(11, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).addCell(new Label(12, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).addCell(new Label(13, excelSheetLine, protocole.getS�rie(s).getGroup(g).getInter(), reconFormat));
								excelFile.getSheet(0).addCell(new Label(14, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).addCell(new Label(15, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).addCell(new Label(16, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).addCell(new Label(17, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).addCell(new Label(18, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).addCell(new Label(19, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).addCell(new Label(20, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).addCell(new Label(21, excelSheetLine, "", blankFormat));
								excelFile.getSheet(0).addCell(new Label(22, excelSheetLine, protocole.getS�rie(s).getGroup(g).getRecon(r).getFilter(), reconFormat));
								if(protocole.getS�rie(s).getGroup(g).getRecon(r).getTyperecon().contains("0")){
									excelFile.getSheet(0).addCell(new Label(23, excelSheetLine, "Full", reconFormat));
								}	
								else{
									excelFile.getSheet(0).addCell(new Label(23, excelSheetLine, "Plus", reconFormat));
								}
								excelFile.getSheet(0).addCell(new Label(24, excelSheetLine, protocole.getS�rie(s).getGroup(g).getRecon(r).getAsirConfig(), reconFormat));
								excelFile.getSheet(0).addCell(new Label(25, excelSheetLine, protocole.getS�rie(s).getGroup(g).getRecon(r).getIqe(), reconFormat));
								excelFile.getSheet(0).addCell(new Label(26, excelSheetLine, protocole.getS�rie(s).getGroup(g).getRecon(r).getMBIR(), reconFormat));
							}
							stopMerge = excelSheetLine;
							excelSheetLine++;
						}
					}	
				}
			}
		}
		// FUSION DES CELLULES DES COLL0NNES CORRESPONDANT A LA DATE ET AU PROTOCOLE
		excelFile.getSheet(0).mergeCells(0, startMerge, 0, stopMerge);
		excelFile.getSheet(0).mergeCells(1, startMerge, 1, stopMerge);
		
		for (int c = 0; c < 26; c++){
			excelFile.getSheet(0).addCell(new Label(c, excelSheetLine, "",spaceFormat ));
		}
		excelSheetLine++;
		excelFile.write();
		excelFile.close();
	}
		
	/**************************************************************************************************************************/
	/** 	ECRITURE DES PARAMETRES DANS LE FICHIERS EXCEL EN FONCTION DES MODIFICATIONS	**/

	void writeParameters (int pColonne,  String pAvantOptimisation, String pApresOptimisation, WritableCellFormat pFormat ) throws RowsExceededException, WriteException, IndexOutOfBoundsException  {	
		
		WritableCellFormat doseMoinsFormat = new WritableCellFormat(cellFont);
		// DEFINITION DE L'ALIGNEMENT DES CELLULES DE RECON NON NATIVES
		doseMoinsFormat.setAlignment(Alignment.CENTRE);
		doseMoinsFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		// DEFINITION DES BORDURES DES CELLULES DES RECON NON NATIVES
		doseMoinsFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		// DEFINITION DES BORDURES DES CELLULES DES RECON NON NATIVES
		doseMoinsFormat.setBackground(jxl.format.Colour.GREEN);
		
		WritableCellFormat dosePlusFormat = new WritableCellFormat(cellFont);
		// DEFINITION DE L'ALIGNEMENT DES CELLULES DE RECON NON NATIVES
		dosePlusFormat.setAlignment(Alignment.CENTRE);
		dosePlusFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		// DEFINITION DES BORDURES DES CELLULES DES RECON NON NATIVES
		dosePlusFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		// DEFINITION DES BORDURES DES CELLULES DES RECON NON NATIVES
		dosePlusFormat.setBackground(jxl.format.Colour.RED);
		
		// SI LA VARIABLE pAvantOptimisation EST NULL
		if(pAvantOptimisation == null){
			// ON LE REMPLACE PAR UN CHAMP VIDE
			pAvantOptimisation = "";
		}
		// SINON SI LA COLONNE CORRESPOND A LA COLLIMATION ET QUE LA VALEUR EST DE 32
		else if(pColonne == 12 && pAvantOptimisation.contains("32")){
			// ON LA REMPLACE PAR 20
			pAvantOptimisation = "20";
		}
		// SINON SI LA COLONNE CORRESPOND A LA COLLIMATION ET QUE LA VALEUR EST DE 64
		else if(pColonne == 12 && pAvantOptimisation.contains("64")){
			// ON LA REMPLACE PAR 40
			pAvantOptimisation = "40";
		}
		// SINON SI LA COLONNE CORRESPOND AU SMARTCATHODEMODE ET QUE LA VALEUR EST SCBIASMODE
		else if(pColonne == 16 && pAvantOptimisation.contains("scBiasMode")){
			// ON LA REMPLACE PAR NO
			pAvantOptimisation = "No";
		}
		// SINON SI LA COLONNE CORRESPOND AU SMARTCATHODEMODE ET QUE LA VALEUR EST SCDYNAMICDEFLECT
		else if(pColonne == 16 && pAvantOptimisation.contains("scDynamicDeflect")){
			// ON LA REMPLACE PAR YES
			pAvantOptimisation = "Yes";
		}
		// SINON SI LA COLONNE CORRESPOND AU TYPE DE RECON ET QUE LA VALEUR EST DE 0
		else if(pColonne == 23 && pAvantOptimisation.contains("0")){
			// ON LA REMPLACE PAR FULL
			pAvantOptimisation = "Full";
		}
		// SINON SI LA COLONNE CORRESPOND AU TYPE DE RECON ET QUE LA VALEUR EST DE 7
		else if(pColonne == 23 && pAvantOptimisation.contains("7")){
			// ON LA REMPLACE PAR PLUS
			pAvantOptimisation = "Plus";
		}
		
		
		// MEME CHOSE QUE PRECEDEMMENT MAIS POUR LA VARIABLE pApresOptimisation
		if(pApresOptimisation == null){
			pApresOptimisation = "";
		}
		else if(pColonne == 12 && pApresOptimisation.contains("32")){
			pApresOptimisation = "20";
		}
		else if(pColonne == 12 && pApresOptimisation.contains("64")){
			pApresOptimisation = "40";
		}
		else if(pColonne == 16 && pApresOptimisation.contains("scBiasMode")){
			pApresOptimisation = "No";
		}
		else if(pColonne == 16 && pApresOptimisation.contains("scDynamicDeflect")){
			pApresOptimisation = "Yes";
		}
		else if(pColonne == 23 && pApresOptimisation.contains("0")){
			pApresOptimisation = "Full";
		}
		else if(pColonne == 23 && pApresOptimisation.contains("7")){
			pApresOptimisation = "Plus";
		}
		
		// SI LE PARAMETRE AVANT OPTIMISATION EST EGAL AU PARAMETRE APRES OPTIMISATION
		if (pAvantOptimisation.contains(pApresOptimisation)){
			// AJOUT DU PARAMETRE DANS LA CELLULE CORRESPONDANTE
			excelFile.getSheet(0).addCell(new Label(pColonne, excelSheetLine, pAvantOptimisation, pFormat));
			// FUSION AVEC LA CELLULE SOUS-JACENTE
			excelFile.getSheet(0).mergeCells(pColonne, excelSheetLine, pColonne, excelSheetLine+1);
		}
		// SINON 
		else{
			// AJOUT DU PARAMETRE AVANT OPTIMISATION DANS LA CELLULE CORRESPONDANTE
			excelFile.getSheet(0).addCell(new Label(pColonne, excelSheetLine, pAvantOptimisation, pFormat));
			
			// SI LA COLONNE EST LA 8, LA 15, LA 17, LA 20 OU LA 21
			if(pColonne == 8 ||  pColonne == 15 || pColonne == 17 || pColonne == 20 || pColonne == 21){
				// CREATION DE DEUX FLOAT A PARTIR DES DEUX PARAMETRES
				Float avantF = Float.parseFloat(pAvantOptimisation);
				Float apr�sF = Float.parseFloat(pApresOptimisation);
				// SI LE PARAMETRE AVANT OPTIMISATION EST SUPERIEUR AU PARAMETRE APRES
				if(avantF > apr�sF){
					// ON ATTRIBUE LE FORMAT DE REDUCTION DE DOSE A LA CELLULE
					pFormat = doseMoinsFormat;
				}
				// SINON 
				else if(avantF < apr�sF){
					// ON ATTRIBUE LE FORMAT D'AUGMENTATION DE DOSE A LA CELLULE
					pFormat = dosePlusFormat;
				}
			}
			// SI LA COLONNE EST LA 9, LA 12, LA 19
			if(pColonne == 9 || pColonne == 12 || pColonne == 19){
				// CREATION DE DEUX FLOAT A PARTIR DES DEUX PARAMETRES
				Float avantF = Float.parseFloat(pAvantOptimisation);
				Float apr�sF = Float.parseFloat(pApresOptimisation);
				// SI LE PARAMETRE AVANT OPTIMISATION EST INFERIEUR AU PARAMETRE APRES
				if(avantF < apr�sF){
					// ON ATTRIBUE LE FORMAT DE REDUCTION DE DOSE A LA CELLULE
					pFormat = doseMoinsFormat;
				}
				else if(avantF > apr�sF){
					// ON ATTRIBUE LE FORMAT D'AUGMENTATION DE DOSE A LA CELLULE
					pFormat = dosePlusFormat;
				}
			}			
			// AJOUT DU PARAMETRE AVANT OPTIMISATION DANS LA CELLULE CORRESPONDANTE
			excelFile.getSheet(0).addCell(new Label(pColonne, excelSheetLine+1, pApresOptimisation, pFormat));			
		}	
	}
}




