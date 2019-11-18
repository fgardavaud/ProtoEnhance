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
	private boolean optimisationAvancée = false;
	private JMenuBar menu = null;
		private JMenu fichier = null;
				private JMenuItem optimiser = null;
				private JMenuItem éditer = null;
				private JMenuItem quitter = null;
		private JMenu àpropos = null;
			private JMenuItem aP = null;
		private JMenu outils = null;
			
		private JMenuItem activerOption = null;
		private JMenuItem désactiverOption = null;
		

	private JPanel conteneur = new JPanel();
			
	public FenetrePrincipale(){
		this.setTitle("ProtoEnhance V1.2");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
				
		menu = new JMenuBar();
		
		fichier = new JMenu("Fichier");
		outils = new JMenu("Outils");
		àpropos = new JMenu("A propos");
		
		
		
		optimiser = new JMenuItem("Optimisation de(s) protocole(s)");
		optimiser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
		                                                  InputEvent.CTRL_MASK));
		optimiser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				conteneur.removeAll();
				if( OptimizationPanel.class.isInstance(arg0) == false ){
					try {						
						oP = new OptimizationPanel(optimisationAvancée, getInsets().top, getInsets().bottom, getInsets().right, getInsets().left);
					} catch (IOException | JDOMException | BiffException | WriteException e) {			}
					conteneur.setLayout(new GridLayout(1,1));
		    		conteneur.add(oP);
		    		conteneur.revalidate();
		    	}
			}
	    });
		
		éditer = new JMenuItem("Edition de(s) protocole(s)");
		éditer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
		                                                  InputEvent.CTRL_MASK));
		éditer.addActionListener(new ActionListener(){
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
		éditer.setEnabled(false);
		
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
						"Auteurs: François Gardavaud et Hugo Pasquier\n"+
						"Contributeurs: Pr. Alain Luciani et Pr. Alain Rahmouni\n"+
						"Validation: Société Française de Physique Médicale (SFPM)\n\n"+
						
						"Contact: francois.gardavaud@aphp.fr ou hugo.pasq@gmail.com\n\n"+
						
						"ProtoEnhance est un logiciel destiné à optimiser les protocoles\n"+ 
						"d'acquisition scanographique à partir d'une bibliothèque d'images\n"+
						"d'un fantôme anthropomorphique adulte acquise en fonction des\n"+
						"recommandations du constructeur et des pratiques cliniques de\n"+
						"plusieurs Centres Hospitaliers Universitaires sur le scanner\n"+
						"Discovery CT 750HD commercialisé par la société GE HealthCare.\n\n"+
						"ProtoEnhance inclut le logiciel développé dans le cadre du projet\n"+
						"JDOM (http://www.jdom.org/) et le JavaExcel API (v2.6.12) sous\n"+
						"Licence publique générale limitée GNU.", "A propos", JOptionPane.PLAIN_MESSAGE);

			}
				
	    });
	    
	   	activerOption = new JMenuItem("Activer l'option d'optimisation avancée");
	   	activerOption.setEnabled(false);
		activerOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(oP == null) optimisationAvancée = true;
				else oP.optimisationAvancée = true;
		    	activerOption.setEnabled(false);
		    	désactiverOption.setEnabled(true);
		    }
		});
		
		désactiverOption = new JMenuItem("Désactiver l'option d'optimisation avancée");
		désactiverOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(oP == null) optimisationAvancée = false;
				else oP.optimisationAvancée = false;
		    	activerOption.setEnabled(true);
		    	désactiverOption.setEnabled(false);
		    }
		});
	      
	    fichier.add(optimiser);
	    fichier.add(éditer);
	    fichier.addSeparator();
	    fichier.add(quitter);
	    
	    àpropos.add(aP);
	    
	    outils.add(activerOption);
	    outils.add(désactiverOption);
	    
	    menu.add(fichier);
	    //menu.add(outils);
	    menu.add(àpropos);
	    
	    this.setJMenuBar(menu);
	
	this.setLayout(new GridLayout(1,1)); 
	this.add(conteneur);
	this.setVisible(true);
	}
	
}
