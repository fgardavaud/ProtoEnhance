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

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.table.TableCellRenderer;

import com.Protocol.Protocol;

public class ParametersEditionPanel extends JPanel{
	//****************  PROTOCOLE  *******************************************************************************************
	private Protocol 	protocole;
		
	//****************  DIMENSION  *******************************************************************************************
	private Dimension 	ParametersEditionPanelSize;
		
	//****************  SERIEPANEL  ******************************************************************************************
	private SeriePanel[] seriesPanel;
	
	/**************************************************************************************************************************/
	/** 	CONSTRUCTEUR 	**/
	public ParametersEditionPanel(Protocol pProtocole,  Dimension ParametersEditionPanelDimension){
		// RECUPERATION DU PROTOCOLE
		this.protocole = pProtocole;
		this.ParametersEditionPanelSize = ParametersEditionPanelDimension;
		
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
		this.add(barreOngletSGR);
	}
}
