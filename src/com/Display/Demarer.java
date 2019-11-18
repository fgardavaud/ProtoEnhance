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
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class Demarer extends JFrame {
		 
	private JButton bouttonEntrer = new JButton("Accepter"),
					bouttonAnnuler = new JButton("Quitter");
	private JLabel 	titre = new JLabel("Décharge");
	private JPanel 	cadre = new JPanel(),
					center = new JPanel(),
					north = new JPanel(),
					south = new JPanel();
		
	public Demarer(){	
		this.setTitle("ProtoEnhance V1.2");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		cadre.setLayout(new BorderLayout());		
		north.add(titre);
		JTextArea avertissement = new JTextArea(	"La modification des protocoles scanographiques est un processus complexe, celui-ci peut engendrer des "+
													"problèmes d’acquisition et de notification sur votre appareil. Vous procédez à cette opération à vos "+
													"propres risques. Il est donc vivement recommandé d'enregistrer au préalable le dossier 'system state' de "+
													"votre système dans sa globalité afin d'avoir à votre disposition une sauvegarde complète de votre scanner. "+
													"Ce faisant, il est conseillé de vérifier, avant toutes acquisition d’images sur patient, tous les paramètres "+
													"d’acquisition, de reconstruction et de notification des protocoles ayant été modifiés. Il vous incombe "+
													"également de vérifier vos envois d'image sur les hôtes à distance pour chaque protocole modifié de même pour "+
													"les impressions de film. \n\n"+
													"Ce logiciel est fourni « tel quel », en aucun cas les auteurs de ProtoEnhance, les collaborateurs du projet "+
													"et la Société Française de Physique Médicale (SFPM)"+
													"ne seront responsables des dommages  directs, indirects ou accessoires (y compris, mais sans s'y limiter, "+
													"le disfonctionnement de votre appareil et la perte de de données) provenant de toute forme d'utilisation de ce "+
													"logiciel.\n\n"+
													"Le fait que vous puissiez accéder à cette décharge signifie que vous vous y conformez. De plus, cela implique "+
													"que vous avez pris connaissance de la licence CeCILL, et que vous en avez accepté les termes décrits à l’adresse "+
													"suivante : http://www.cecill.info/index.fr.html.\n\n\n\n"+
													"Auteurs : François Gardavaud et Hugo Pasquier\n"+
													"Collaborateurs : Pr. Alain Luciani et Pr. Alain Rahmouni\n"+
													"Validation : Société Française de Physique Médicale (SFPM)");

		avertissement.setEditable(false);
		avertissement.setColumns(50);
		avertissement.setOpaque(false);
		avertissement.setLineWrap(true);
		avertissement.setWrapStyleWord(true);
		center.add(avertissement);
		bouttonEntrer.addActionListener(new EntrerListener());
		bouttonAnnuler.addActionListener(new AnnulerListener());
		south.add(bouttonEntrer);
		south.add(bouttonAnnuler);		
		cadre.add(north, BorderLayout.NORTH);
		cadre.add(center, BorderLayout.CENTER);
		cadre.add(south, BorderLayout.SOUTH);
		this.add(cadre);
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);
	}

		
	class EntrerListener implements ActionListener{	
		public void actionPerformed(ActionEvent arg0) {
			//Fermeture de la fenêtre d'accueil
			dispose();
			//Ouverture de la fenêtre principale
			new FenetrePrincipale();
		}
	}	
	           				
	class AnnulerListener implements ActionListener{	
		public void actionPerformed(ActionEvent arg1) {
			dispose();	
		}
	}
	
}

