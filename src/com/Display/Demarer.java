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
	private JLabel 	titre = new JLabel("D�charge");
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
													"probl�mes d�acquisition et de notification sur votre appareil. Vous proc�dez � cette op�ration � vos "+
													"propres risques. Il est donc vivement recommand� d'enregistrer au pr�alable le dossier 'system state' de "+
													"votre syst�me dans sa globalit� afin d'avoir � votre disposition une sauvegarde compl�te de votre scanner. "+
													"Ce faisant, il est conseill� de v�rifier, avant toutes acquisition d�images sur patient, tous les param�tres "+
													"d�acquisition, de reconstruction et de notification des protocoles ayant �t� modifi�s. Il vous incombe "+
													"�galement de v�rifier vos envois d'image sur les h�tes � distance pour chaque protocole modifi� de m�me pour "+
													"les impressions de film. \n\n"+
													"Ce logiciel est fourni � tel quel �, en aucun cas les auteurs de ProtoEnhance, les collaborateurs du projet "+
													"et la Soci�t� Fran�aise de Physique M�dicale (SFPM)"+
													"ne seront responsables des dommages  directs, indirects ou accessoires (y compris, mais sans s'y limiter, "+
													"le disfonctionnement de votre appareil et la perte de de donn�es) provenant de toute forme d'utilisation de ce "+
													"logiciel.\n\n"+
													"Le fait que vous puissiez acc�der � cette d�charge signifie que vous vous y conformez. De plus, cela implique "+
													"que vous avez pris connaissance de la licence CeCILL, et que vous en avez accept� les termes d�crits � l�adresse "+
													"suivante : http://www.cecill.info/index.fr.html.\n\n\n\n"+
													"Auteurs : Fran�ois Gardavaud et Hugo Pasquier\n"+
													"Collaborateurs : Pr. Alain Luciani et Pr. Alain Rahmouni\n"+
													"Validation : Soci�t� Fran�aise de Physique M�dicale (SFPM)");

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
			//Fermeture de la fen�tre d'accueil
			dispose();
			//Ouverture de la fen�tre principale
			new FenetrePrincipale();
		}
	}	
	           				
	class AnnulerListener implements ActionListener{	
		public void actionPerformed(ActionEvent arg1) {
			dispose();	
		}
	}
	
}

