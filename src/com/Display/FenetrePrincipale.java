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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import org.jdom2.JDOMException;

import com.Display.Demarer.AnnulerListener;
import com.Display.Demarer.EntrerListener;


public class FenetrePrincipale extends JFrame {
	private OptimizationPanel oP = null;
	private EditionPanel eP = null;
	private boolean optimisationAvanc�e = false;
	private JMenuBar menu = null;
		private JMenu fichier = null;
				private JMenuItem optimiser = null;
				private JMenuItem �diter = null;
				private JMenuItem quitter = null;
		private JMenu �propos = null;
			private JMenuItem aP = null;
		private JMenu outils = null;
			
		private JMenuItem activerOption = null;
		private JMenuItem d�sactiverOption = null;
		

	private JPanel conteneur = new JPanel();
			
	public FenetrePrincipale(){
		this.setTitle("ProtoEnhance V1.2");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
				
		menu = new JMenuBar();
		
		fichier = new JMenu("Fichier");
		outils = new JMenu("Outils");
		�propos = new JMenu("A propos");
		
		
		
		optimiser = new JMenuItem("Optimisation de(s) protocole(s)");
		optimiser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
		                                                  InputEvent.CTRL_MASK));
		optimiser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				conteneur.removeAll();
				if( OptimizationPanel.class.isInstance(arg0) == false ){
					try {						
						oP = new OptimizationPanel(optimisationAvanc�e, getInsets().top, getInsets().bottom, getInsets().right, getInsets().left);
					} catch (IOException | JDOMException | BiffException | WriteException e) {			}
					conteneur.setLayout(new GridLayout(1,1));
		    		conteneur.add(oP);
		    		conteneur.revalidate();
		    	}
			}
	    });
		
		�diter = new JMenuItem("Edition de(s) protocole(s)");
		�diter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
		                                                  InputEvent.CTRL_MASK));
		�diter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				conteneur.removeAll();
				if( EditionPanel.class.isInstance(arg0) == false ){
					try {						
						eP = new EditionPanel(getInsets().top, getInsets().bottom, getInsets().right, getInsets().left);
					} catch (IOException | JDOMException | BiffException | WriteException e) {			}
					conteneur.setLayout(new GridLayout(1,1));
		    		conteneur.add(eP);
		    		conteneur.revalidate();
		    	}
			}
	    });
		�diter.setEnabled(false);
		
		quitter = new JMenuItem("Quitter");
	    quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
	                                                  KeyEvent.CTRL_MASK));
	    quitter.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	    		System.exit(0);
	    	}
	    });
	    
	    aP = new JMenuItem("A propos de ProtoEnhance");
	    aP.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				new JOptionPane();
				JOptionPane.showMessageDialog(aP, 
						"Auteurs: Fran�ois Gardavaud et Hugo Pasquier\n"+
						"Contributeurs: Pr. Alain Luciani et Pr. Alain Rahmouni\n"+
						"Validation: Soci�t� Fran�aise de Physique M�dicale (SFPM)\n\n"+
						
						"Contact: francois.gardavaud@aphp.fr ou hugo.pasq@gmail.com\n\n"+
						
						"ProtoEnhance est un logiciel destin� � optimiser les protocoles\n"+ 
						"d'acquisition scanographique � partir d'une biblioth�que d'images\n"+
						"d'un fant�me anthropomorphique adulte acquise en fonction des\n"+
						"recommandations du constructeur et des pratiques cliniques de\n"+
						"plusieurs Centres Hospitaliers Universitaires sur le scanner\n"+
						"Discovery CT 750HD commercialis� par la soci�t� GE HealthCare.\n\n"+
						"ProtoEnhance inclut le logiciel d�velopp� dans le cadre du projet\n"+
						"JDOM (http://www.jdom.org/) et le JavaExcel API (v2.6.12) sous\n"+
						"Licence publique g�n�rale limit�e GNU.", "A propos", JOptionPane.PLAIN_MESSAGE);

			}
				
	    });
	    
	   	activerOption = new JMenuItem("Activer l'option d'optimisation avanc�e");
	   	activerOption.setEnabled(false);
		activerOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(oP == null) optimisationAvanc�e = true;
				else oP.optimisationAvanc�e = true;
		    	activerOption.setEnabled(false);
		    	d�sactiverOption.setEnabled(true);
		    }
		});
		
		d�sactiverOption = new JMenuItem("D�sactiver l'option d'optimisation avanc�e");
		d�sactiverOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(oP == null) optimisationAvanc�e = false;
				else oP.optimisationAvanc�e = false;
		    	activerOption.setEnabled(true);
		    	d�sactiverOption.setEnabled(false);
		    }
		});
	      
	    fichier.add(optimiser);
	    fichier.add(�diter);
	    fichier.addSeparator();
	    fichier.add(quitter);
	    
	    �propos.add(aP);
	    
	    outils.add(activerOption);
	    outils.add(d�sactiverOption);
	    
	    menu.add(fichier);
	    //menu.add(outils);
	    menu.add(�propos);
	    
	    this.setJMenuBar(menu);
	
	this.setLayout(new GridLayout(1,1)); 
	this.add(conteneur);
	this.setVisible(true);
	}
	
}
