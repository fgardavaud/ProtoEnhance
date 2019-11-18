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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


import com.Protocol.Group;
import com.Protocol.Protocol;
import com.Protocol.Recon;
import com.Protocol.Serie;

public class EditionPanel extends JPanel{
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
	private Dimension 	editionPanelSize, 
						ParametersEditionPanelSize;
	
	//************  FILE  ****************************************************************************************************
	private File 	file;
	
	//************  INT  *****************************************************************************************************
	private int 	nbCategory, 
					nbProtocol,
					s�rie = -1, 
					groupe = -1,
					recon = -1,
					excelSheetLine = 0;
	
	//************  JBUTTON  *************************************************************************************************
	private JButton boutonEdition = new JButton ("Edition"),
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
	private Protocol 	protocole; 
						
	
	ResultPanel reconPanel;
	
	//****************  SERIEPANEL  ******************************************************************************************
	private SeriePanel[] seriesPanel;
	
	//*********************  RESULTPANEL *************************************************************************************
	private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	
	//************  STRING  **************************************************************************************************
	private String 	ServiceModDataPath,
					protoFilePath,
					parentPath;
	
	//************  STRING[]  ************************************************************************************************
	private String[]	zoneAnatomique = 	{"1. T�te", "2. Orbite", "3. Cou", "4. Epaule", "5. Thorax", 
										"6. Abdomen", "7. Lombaire", "8. Pelvis", "9. Membre inf�rieur", "10. Divers"};			
	
	//****************  STRING[][]  ******************************************************************************************
	private String[][]	protoFile = new String[10][99];
	
	//****************  WRITABLEFONT  ****************************************************************************************
	private WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
	
	//**************** WRITABLEWORKBOOK  *************************************************************************************
	private WritableWorkbook excelFile;
	
	/**************************************************************************************************************************/
	/** 	CONSTRUCTEUR 	**/
	public 	EditionPanel(int pTop, int pBottom, int pRight, int pLeft) throws IOException, BiffException, WriteException, JDOMException {
		
		// DEFINITION DE LA TAILLE DE L'ANALYSEPANEL
		editionPanelSize = new Dimension((int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()-(pRight + pLeft),
													(int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight()-(pTop + pBottom));
		
		// INSTANCIATION DU JFILECHOOSER
		JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		// DEFINITION DU TITRE DE LA FENETRE
		fc.setDialogTitle("S�lectionner le dossier service_mode_data contenant les protocoles");
		// DEFINITION DU MODE DE SELCTION (DOSSIER UNIQUEMENT)
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// AFFICHAGE DU JFILECHOOSER
		int returnVal = fc.showOpenDialog(EditionPanel.this);
				 
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
		
		// INSTANCIATION DU JPANEL DES PROTOCOLES A OPTIMISER
		JPanel protocolePanel = new JPanel();
		// DEFINITION DE LA TAILLE DU JPANEL
		protocolePanel.setPreferredSize(new Dimension((int)editionPanelSize.getWidth()/5, (int)editionPanelSize.getHeight()/3));
		// DEFINITION DU TITRE DU JPANEL
		JLabel label1 = new JLabel("S�lectionner le protocole � �diter :");
		// CENTRAGE DU TITRE
		label1.setHorizontalAlignment(0);
		// INSTANCIATION DE LA JCOMBOBOX DE FILTRE ANATOMIQUE
		comboProtocole = new JComboBox<Object>(zoneAnatomique);
		// DEFINITION DE LA TAILLE DE LA JCOMBOBOX
		comboProtocole.setPreferredSize(new Dimension((int)editionPanelSize.getWidth()/5, 20));
		// AJOUT DE L'ACTION DE FILTRE A LA JCOMBOBOX
		comboProtocole.addActionListener(new FiltreProtocole());
		// DEFINITION DU FILTRE HEAD PAR DEFAUT
		comboProtocole.setSelectedIndex(0);
		// DEFINITION DE LA LISTE DE PROTOCOLES HEAD PAR DEFAULT
		listProtocole = new JList<String>(ProtocoleHead);
		// AJOUT DE LA LISTE DE PROTOCOLES DANS UN JSCROLLPANE
		scrollProtocole= new JScrollPane(listProtocole);
		// DEFINITION DE LA TAILLE DU JSCROLLPANE 
		scrollProtocole.setPreferredSize(new Dimension((int)editionPanelSize.getWidth()/5, ((int)editionPanelSize.getHeight())/4));
		
		// AJOUT DE LA JCOMBOBOX ET DU SCROLLPANEL AU JPANEL DE PROTOCOLES A OPTIMISER
		protocolePanel.add(label1);
		protocolePanel.add(comboProtocole);
		protocolePanel.add(scrollProtocole);
		
		// AJOUT DE L'ACTION DU BOUTON "EDITION"
		boutonEdition.addActionListener(new EditionAction());
		// DEFINITION DE LA POLICE DU BOUTON "EDITION"
		boutonEdition.setFont(new Font("Dialog", Font.BOLD, 25));
		// AJOUT DE L'ACTION DU BOUTON "VALIDATION" 
		boutonValidation.addActionListener(new ValidationAction());
		// DEFINITION DE LA POLICE DU BOUTON "VALIDATION"
		boutonValidation.setFont(new Font("Dialog", Font.BOLD, 25));
		boutonValidation.setVisible(false);
		
		// AJOUT DE TOUS LES COMPOSANTS AU JPANEL WEST		
		west.add(protocolePanel);
		west.add(boutonEdition);
		west.add(boutonValidation);
		
		// DEFINITION DE LA TAILLE DU JPANEL WEST
		west.setPreferredSize(new Dimension(((int)editionPanelSize.getWidth()/5)+4, (int)editionPanelSize.getHeight()));
		// DEFINITION DE LA BORDURE DU JPANEL WEST
		west.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(184, 207, 229)));
				
		// DEFINITION DE LA TAILLE DU JPANEL CENTER
		ParametersEditionPanelSize = new Dimension((((int)editionPanelSize.getWidth()*4)/5)-4, (int)editionPanelSize.getHeight());
		center.setPreferredSize(ParametersEditionPanelSize);
			
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
	/** 	ACTION DU BOUTON "EDITION"	**/
	class EditionAction implements ActionListener {
		public void actionPerformed(ActionEvent e){		
			
			// SI UN PROTOCOLE A OPTIMISER OU UN PROTOCOLE DE REFERENCE N'EST PAS SELECTIONNE
			if (listProtocole.isSelectionEmpty()){
				// AFFICHAGE D'UN AVERTISSEMENT 
				new JOptionPane();
				JOptionPane.showMessageDialog(cadre, "Veuillez d'abord s�lectionner le protocole � �diter.",
												"Information", JOptionPane.INFORMATION_MESSAGE);
			}
			// SINON
			else{

				// REMPLACEMENT DU BOUTON "OPTIMISATION" PAR LE BOUTON "VALISATION"
				boutonEdition.setVisible(false);
				boutonValidation.setVisible(true);
				
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
								protocole.getS�rie(s�rie).getGroup(groupe).getRecon(recon).setFilter(splitTable[1]);
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
				/**															
				// AFFICHAGE DE LA COMPOSITION DU PROTOCOLE A EDITER
				structureTable = new ProtoStructureTable(protocole, new Dimension((int)editionPanelSize.getWidth()/5, (int)editionPanelSize.getHeight()/3));
							
				// AJOUT DU STRUCTURETABLE AU JPANEL WEST
				west.add(structureTable);
				// ACTUALISATION DU JPANEL WEST
				west.revalidate();	
				**/
				
				// CREATION D'UN JTABBEDPANE QUI CONTIENDRA LES ONGLETS DE SERIES
				JTabbedPane barreOngletSGR = new JTabbedPane();
								
				// INSTANCIATION D'UN TABLEAU DE SERIEPANELS (AUTANT DE RESULTPANELS QUE DE SERIES)
				seriesPanel = new SeriePanel[protocole.getNbSerie()+1];

				// POUR TOUTES LES SERIES DU PROTOCOLE
				for(int i = 0; i <= protocole.getNbSerie(); i++){
					// CREATION D'UN SERIEPANEL
					seriesPanel[i] = new SeriePanel(protocole.getS�rie(i), ParametersEditionPanelSize);
					// AJOUT DU SERIEPANEL AU JTABBEDPANE
					barreOngletSGR.add(protocole.getS�rie(i).getGroup(0).getRecon(0).getTitre(), seriesPanel[i]);
				}
				center.setLayout(new GridLayout(1,1));
				// AJOUT DES ONGLETS DE SERIES AU JPANEL CENTER
				center.add(barreOngletSGR);
				// ACTUALISATION DU JPANEL CENTER
				center.revalidate();
			}
		}
	}
	
	/**************************************************************************************************************************/
	/** 	ACTION DU BOUTON "VALIDATION"	**/
	
	class ValidationAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			
			// UTILISATION DE LA METHODE D'ECRITURE DE PROTOCOL DANS LE FICHIER APPROPRIE
			try {
				ajoutProtocol();
			} catch (IOException | BiffException | WriteException e1) {e1.printStackTrace();}
			boutonValidation.setVisible(false);
			boutonEdition.setVisible(true);
			center.removeAll();
			center.updateUI();
		}
	}
	
	/**************************************************************************************************************************/
	/** 	ECRITURE DU PROTOCOLE CHOISI DANS LE FICHIER PROTO ET DANS LE FICHIER EXCEL		**/	

	void ajoutProtocol () throws IOException, BiffException, WriteException {	
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
		
		// LECTURE DE LA TOTALITE DU FICHIER PROTO
		while((ligne = br.readLine()) != null){
			ligneEdit�e = ligne;

			// SEPARATION DU PARAMETRES ET DE LA VALEUR
			splitTable = ligne.split(" += +");
				
			switch(splitTable[0]){
			// DETECION D'UNE SERIE
			case "	Series {":
				// INCREMENTATION DU NUMERO DE SERIE
				s�rie++;
				// REINITIANILISATION DU NOMBRE DE GROUPE
				groupe = -1;
				break;
				
			// DETECTION DE LA FIN D'UNE RECON	
			case "			}":
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
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 7);
				}
				else{
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 2);
				}
				break;
					
			// DETECION DU SMARTCATHODEMODE
			case "			smartCathodeMode":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 5)).contains("Yes")){
						ligneEdit�e = splitTable[0]+" = scDynamicDeflect";
					}
					else{
						ligneEdit�e = splitTable[0]+" = scBiasMode";
					}
				}
				break;	
						
			// DETECION DU SMARTCATHODENUMDEFLECTIONS
			case "			smartCathodeNumDeflections":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 5)).contains("Yes")){
						ligneEdit�e = splitTable[0]+" = 2";
					}
					else{
						ligneEdit�e = splitTable[0]+" = 1";
					}
				}
				break;		
					
			// DETECTION DES mA
			case "			milliAmps":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 8);
				}
				else{
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 3);
				}
				break;	
					
			// DETECTION DU PLAN DU SCOUT					
			case "			scoutPlane":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Scout")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 1);
				}
				break;	
					
			// DETECTION DE LA TAILLE DU SFOV
			case "			scanFieldOfViewSize":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 6)).contains("Large Body")){
						// LA TAILLE EST DE 50cm
						ligneEdit�e = splitTable[0]+" = 50";
					}
					else{
						// SINON ELLE EST DE 32cm
						ligneEdit�e = splitTable[0]+" = 32";
					}
				}
				break;
					
			// DETECTION DU TYPE DE SFOV
			case "			scanFieldOfViewType":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 6)).contains("Head")){
						ligneEdit�e = splitTable[0]+" = ScanFieldOfViewHeadVCT";
					}
					else if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 6)).contains("Large Body")){
						ligneEdit�e = splitTable[0]+" = ScanFieldOfViewLargeBodyVCT";
					}
					else if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 6)).contains("Medium Body")){
						ligneEdit�e = splitTable[0]+" = ScanFieldOfViewMediumBodyVCT";
					}
					else if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 6)).contains("Ped Body")){
						ligneEdit�e = splitTable[0]+" = ScanFieldOfViewPedBodyVCT";
					}
					else if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 6)).contains("Ped Head")){
						ligneEdit�e = splitTable[0]+" = ScanFieldOfViewPedHeadVCT";
					}
					else if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 6)).contains("Small Body")){
						ligneEdit�e = splitTable[0]+" = ScanFieldOfViewSmallBodyVCT";
					}
					else if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 6)).contains("Small Head")){
						ligneEdit�e = splitTable[0]+" = ScanFieldOfViewSmallHeadVCT";
					}
				}
				break;
				
			// DETECTION DU TYPE DE GROUP
			case "			groupType":	
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 1);
				}
				break;
					
			// DETECION DE L'EPAISSEUR DE COUPE POUR LA RECON NATIVE
			case "			imageThickness":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].reconPanel[groupe].reconTable[0].getValueAt(0, 4);
				}
				break;
					
			// DETECTION DU TEMPS DE ROTATION
			case "			rotationTime":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 2);
				}
				break;	
					
			// DETECTION DE L'INTERVALE POUR LA RECON NATIVE
			case "			scanSpacing":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].reconPanel[groupe].reconTable[0].getValueAt(0, 5);
				}
				break;	
					
			// DETECTION DE LA VITESSE DE TABLE
			case "			tableSpeed":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 3)).contains("0.531")
							&& ((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 4)).contains("20")){
						ligneEdit�e = splitTable[0]+" = 10.625";
					}
					else if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 3)).contains("0.516")
							&& ((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 4)).contains("40")){
						ligneEdit�e = splitTable[0]+" = 20.625";
					}
					else if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 3)).contains("0.969")
							&& ((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 4)).contains("20")){
						ligneEdit�e = splitTable[0]+" = 19.375";
					}
					else if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 3)).contains("0.984")
							&& ((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 4)).contains("40")){
						ligneEdit�e = splitTable[0]+" = 39.375";
					}
					else if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 3)).contains("1.375")
							&& ((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 4)).contains("20")){
						ligneEdit�e = splitTable[0]+" = 27.5";
					}
					else if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 3)).contains("1.375")
							&& ((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 4)).contains("40")){
						ligneEdit�e = splitTable[0]+" = 55";
					}
				}
				break;
					
			// DETECTION DE LA MODULATION D'INTENSITE
			case "			automAFlag":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 9)).contains("Yes")
						&& ((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 10)).contains("Yes")){
						ligneEdit�e = splitTable[0]+" = 2";
					}	
					else if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 9)).contains("Yes")
							&& ((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 10)).contains("No")){
							ligneEdit�e = splitTable[0]+" = 1";
					}
					if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 9)).contains("No")
							&& ((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 10)).contains("No")){
							ligneEdit�e = splitTable[0]+" = 2";
					}					
				}
				break;		
													
			// DETECTION DU NI
			case "			automaNoiseIndex":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 11);
				}
				break;				
				
			// DETECION DES mA MAX
			case "			automaMaxMilliAmps":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 13);
				}
				break;	
					
			// DETECION DES mA MIN
			case "			automaMinMilliAmps":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 12);
				}
				break;		
					
			// DETECION DU MODE DE DETECTEUR
			case "			numImagesPerRotation":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical") 
					&& protocole.getS�rie(s�rie).getGroup(groupe).getGroupType().contains("Axial")){
					
				}
				break;	
					
			// DETECION DE LA LARGEUR DE COLLIMATION
			case "			macroRowNumber":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					if(((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 4)).contains("20")){
						ligneEdit�e = splitTable[0]+" = 32";
					}
					else if (((String)seriesPanel[s�rie].acquisitionTable.getValueAt(groupe, 4)).contains("40")){
						ligneEdit�e = splitTable[0]+" = 64";
					}
				}
				break;	
				
			// DETECTION D'UNE RECON
			case "			Recon {":
				// INCREMENTATION DU NUMERO DE LA SERIE ANALYSEE
				recon++;
				break;
					
			// DETECTION DE L'ACTIVATION DE LA RECON	
			case "				isReconEnabled":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].reconPanel[groupe].reconTable[0].getValueAt(0, 1);
				}
				break;	
				
			// DETECTION DU FILTRE DE RECONSTRUCTION	
			case "				algorithm":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].reconPanel[groupe].reconTable[0].getValueAt(0, 2);
				}
				break;
				
			// DETECTION DE L'EPAISSEUR DE RECONSTRUCTION	
			case "				PMRimageThickness":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].reconPanel[groupe].reconTable[0].getValueAt(0, 4);
				}
				break;	
				
			// DETECTION DE L'EPAISSEUR DE RECONSTRUCTION	
			case "				PMRinterval":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].reconPanel[groupe].reconTable[0].getValueAt(0, 5);
				}
				break;		
				
			// DETECTION D'IQE	
			case "				isIQEnhancedEnabled":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].reconPanel[groupe].reconTable[0].getValueAt(0, 7);
				}
				break;		
				
			// DETECTION DU TYPE DE RECON	
			case "				segments":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					if(((String)seriesPanel[s�rie].reconPanel[groupe].reconTable[0].getValueAt(0, 3)).contains("Full")){
						ligneEdit�e = splitTable[0]+" = 0";
					}
					else if(((String)seriesPanel[s�rie].reconPanel[groupe].reconTable[0].getValueAt(0, 3)).contains("Plus")){
						ligneEdit�e = splitTable[0]+" = 7";
					}
				}	
				break;	
				
			// DETECTION DU MODE D'ASIR	
			case "				iterativeMode":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					
				}
				break;		
				
			// DETECTION DU %age D'ASIR	
			case "				iterativeConfig":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].reconPanel[groupe].reconTable[0].getValueAt(0, 6);
				}
				break;		
				
			// DETECTION DU MBIR	
			case "				MBIROn":
				if(protocole.getS�rie(s�rie).getSeriesType().contains("Helical")){
					ligneEdit�e = splitTable[0]+" = "+seriesPanel[s�rie].reconPanel[groupe].reconTable[0].getValueAt(0, 8);
				}
				break;						
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


