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

package com.Protocol;

public class Recon {
	/**************************************************************************************************************************/
	/** 	DECLARATION DES VARIABLES 	**/
	
	//************  STRING  ***************************************************************************************************
	protected String 	activation,
						thick, 
						inter, 
						filter, 
						typerecon, 
						asirMode,
						asirConfig,
						MBIR,
						iqe, 
						gsi;
	protected String titre;
	protected String pathAnthropomorphic;
	protected String pathMTF;
	protected String pathLCD;
	protected String resoZ;
	protected String resoX;
	protected String resoY;
	protected String SNR0;
	protected String SNR1;
	protected String SNR2;
	protected String SNR3;
	protected String SNR4;
	protected String SNR5;
	protected String bruit0;
	protected String bruit1;
	protected String bruit2;
	protected String bruit3;
	protected String bruit4;
	protected String bruit5;
	protected String uniformité0;
	protected String uniformité1;
	protected String uniformité2;
	protected String uniformité3;
	protected String uniformité4;
	protected String uniformité5;
	
	/**************************************************************************************************************************/
	/** 	CONSTRUCTEUR 	**/
	public Recon(){}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU TITRE DE LA RECON 	**/
	public String getTitre() {	return titre;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU TITRE DE LA RECON 	**/
	public void setTitre(String ptitre){	this.titre = ptitre;	}
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE L'ACTIVATION DE LA RECON 	**/
	public String getActivation() {	return activation;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE L'ACTIVATION DE LA RECON 	**/
	public void setActivation(String pactivation){	this.activation = pactivation;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE L'EPAISSEUR DE COUPE	**/
	public String getThick() {	return thick;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE L'EPAISSEUR DE COUPE 	**/
	public void setThick(String pthick){	this.thick = pthick;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE L'INTERVALE 	**/
	public String getInter() {	return inter;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE L'INTERVALE 	**/
	public void setInter(String pinter){	this.inter = pinter;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU FILTRE DE RECON	**/
	public String getFilter() {	return filter;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU FILTRE DE RECON 	**/
	public void setFilter(String pfilter){	this.filter = pfilter;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU TYPE DE RECON 	**/
	public String getTyperecon() {	return typerecon;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU TYPE DE RECON 	**/
	public void setTyperecon(String ptyperecon){	this.typerecon = ptyperecon;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU MODE D'ASIR 	**/
	public String getAsirMode() {	return asirMode;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU MODE D'ASIR 	**/
	public void setAsirMode(String pasirMode){	this.asirMode = pasirMode;	}
		
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU %AGE D'ASIR 	**/
	public String getAsirConfig() {	return asirConfig;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU %AGE D'ASIR 	**/
	public void setAsirConfig(String pasirConfig){	this.asirConfig = pasirConfig;	}

	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU MBIR 	**/
	public String getMBIR() {	return MBIR;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU MBIR 	**/
	public void setMBIR(String pMBIR){	this.MBIR = pMBIR;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION D'IQE 	**/
	public String getIqe() {	return iqe;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION D'IQE 	**/
	public void setIqe(String piqe){	this.iqe = piqe;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE GSI 	**/
	public String getGsi() {	return gsi;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE GSI 	**/
	public void setGsi(String pgsi){	this.gsi = pgsi;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU CHEMIN DE LA SEQUENCE D'IMAGES ANTHROPOMORPHIQUES  **/
	public String getPathAnthropomorphic() {	return pathAnthropomorphic;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA SEQUENCE D'IMAGES ANTHROPOMORPHIQUE 	**/
	public void setPathAnthropomorphic(String ppath){	this.pathAnthropomorphic = ppath;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE L'IMAGE MTF 	**/
	public String getPathMTF() {	return pathMTF;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE L'IMAGE MTF 	**/
	public void setPathMTF(String ppath){	this.pathMTF = ppath;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE L'IMAGE LCD 	**/
	public String getPathLCD() {	return pathLCD;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE L'IMAGE LCD 	**/
	public void setPathLCD(String ppath){	this.pathLCD = ppath;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR DE LA RESO EN Z 	**/
	public String getResoZ() {	return resoZ;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR DE LA RESO EN Z 	**/
	public void setresoZ(String presoZ){	this.resoZ = presoZ;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR DE LA RESO EN X 	**/
	public String getResoX() {	return resoX;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR DE LA RESO EN X 	**/
	public void setresoX(String presoX){	this.resoX = presoX;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR DE LA RESO EN Y 	**/
	public String getResoY() {	return resoY;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR DE LA RESO EN Y 	**/
	public void setresoY(String presoY){	this.resoY = presoY;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR DE SNR ROI0  **/
	public String getSNR0() {	return SNR0;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR DE SNR ROI0 	**/
	public void setSNR0(String pSNR0){	this.SNR0 = pSNR0;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR DE SNR ROI1  **/
	public String getSNR1() {	return SNR1;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR DE SNR ROI1 	**/
	public void setSNR1(String pSNR1){	this.SNR1 = pSNR1;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR DE SNR ROI2 **/
	public String getSNR2() {	return SNR2;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR DE SNR ROI2 	**/
	public void setSNR2(String pSNR2){	this.SNR2 = pSNR2;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR DE SNR ROI3  **/
	public String getSNR3() {	return SNR3;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR DE SNR ROI03  **/
	public void setSNR3(String pSNR3){	this.SNR3 = pSNR3;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR DE SNR ROI4  **/
	public String getSNR4() {	return SNR4;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR DE SNR ROI4  **/
	public void setSNR4(String pSNR4){	this.SNR4 = pSNR4;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR DE SNR ROI5  **/
	public String getSNR5() {	return SNR5;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU TEMPS DE ROTATION 	**/
	public void setSNR5(String pSNR5){	this.SNR5 = pSNR5;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR DE BRUIT ROI0  **/
	public String getBruit0() {	return bruit0;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR DE BRUIT ROI0  **/
	public void setBruit0(String pbruit0){	this.bruit0 = pbruit0;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR DE BRUIT ROI1  **/
	public String getBruit1() {	return bruit1;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR DE BRUIT ROI1  **/
	public void setBruit1(String pbruit1){	this.bruit1 = pbruit1;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR DE BRUIT ROI2  **/
	public String getBruit2() {	return bruit2;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR DE BRUIT ROI2  **/
	public void setBruit2(String pbruit2){	this.bruit2 = pbruit2;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR DE BRUIT ROI3 	**/
	public String getBruit3() {	return bruit3;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR DE BRUIT ROI3 	**/
	public void setBruit3(String pbruit3){	this.bruit3 = pbruit3;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR DE BRUIT ROI4 	**/
	public String getBruit4() {	return bruit4;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR DE BRUIT ROI4 	**/
	public void setBruit4(String pbruit4){	this.bruit4 = pbruit4;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR DE BRUIT ROI5 	**/
	public String getBruit5() {	return bruit5;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR DE BRUIT ROI5 	**/
	public void setBruit5(String pbruit5){	this.bruit5 = pbruit5;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR D'UNIFORMITE ROI0 	**/
	public String getUniformité0() {	return uniformité0;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR D'UNIFORMITE ROI0 	**/
	public void setUniformité0(String puniformité0){	this.uniformité0 = puniformité0;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR D'UNIFORMITE ROI1 	**/
	public String getUniformité1() {	return uniformité1;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR D'UNIFORMITE ROI1 	**/
	public void setUniformité1(String puniformité1){	this.uniformité1 = puniformité1;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR D'UNIFORMITE ROI2 	**/
	public String getUniformité2() {	return uniformité2;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR D'UNIFORMITE ROI2 	**/
	public void setUniformité2(String puniformité2){	this.uniformité2 = puniformité2;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR D'UNIFORMITE ROI3 	**/
	public String getUniformité3() {	return uniformité3;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR D'UNIFORMITE ROI3 	**/
	public void setUniformité3(String puniformité3){	this.uniformité3 = puniformité3;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR D'UNIFORMITE ROI4 	**/
	public String getUniformité4() {	return uniformité4;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR D'UNIFORMITE ROI4 	**/
	public void setUniformité4(String puniformité4){	this.uniformité4 = puniformité4;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA VALEUR D'UNIFORMITE ROI5 	**/
	public String getUniformité5() {	return uniformité5;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA VALEUR D'UNIFORMITE ROI5 	**/
	public void setUniformité5(String puniformité5){	this.uniformité5 = puniformité5;	}
	
	
}
