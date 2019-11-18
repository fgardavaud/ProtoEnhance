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

package com.Protocol;

import java.util.ArrayList;

public class Group {
	/**************************************************************************************************************************/
	/** 	DECLARATION DES VARIABLES 	**/
	
	//************  ArrayList<Recon>  *****************************************************************************************
	protected ArrayList<Recon> 	recon = new ArrayList<Recon>();
	
	//************  INT  ******************************************************************************************************
	protected int 				nbRecon = -1;
	
	//************  STRING  ***************************************************************************************************
	protected String 			plane,
								tpsrotation,  
								groupType, 
								thick, 
								pitch, 
								mode, 
								rows, 
								inter, 
								smartCathodeMode, 
								smartCathodeNumDeflections,
								sfovSize,
								sfovType,
								kv, 
								ma, 
								flagMA, 
								ni, 
								minma, 
								maxma, 
								ctdi,
								PDL, 
								filter, 
								typerecon, 
								asir, 
								iqe, 
								gsi,
								pathAnthropomorphic, 
								pathMTF, 
								pathLCD, 
								resoZ,
								resoX,
								resoY,
								SNR0,
								SNR1,
								SNR2,
								SNR3,
								SNR4,
								SNR5,
								bruit0,
								bruit1,
								bruit2,
								bruit3,
								bruit4,
								bruit5,
								uniformit�0,
								uniformit�1,
								uniformit�2,
								uniformit�3,
								uniformit�4,
								uniformit�5;
								
	/**************************************************************************************************************************/
	/** 	CONSTRUCTEUR 	**/
	public Group(){}	
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA RECON 	**/
	public Recon getRecon(int i) {	return recon.get(i);	}	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU NOMBRE DE RECON 	**/
	public int getNbRecon(){ return nbRecon; 	}	
	/**************************************************************************************************************************/
	/** 	METHODE D'AJOUT D'UNE RECON 	**/
	public void setRecon( Recon precon, int indice){	
		this.recon.add(indice, precon);	
		this.nbRecon++;
	}
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE L'ANGLE DE SCOUT 	**/
	public String getPlane() {	return plane;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE L'ANGLE DE SCOUT 	**/
	public void setPlane(String pPlane){	this.plane = pPlane;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU TEMPS DE ROTATION 	**/
	public String getTpsRotation() {	return tpsrotation;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU TEMPS DE ROTATION 	**/
	public void setTpsRotation(String ptpsrotation){	this.tpsrotation = ptpsrotation;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU TYPE D'ACQUISITION 	**/
	public String getGroupType() {	return groupType;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITI0N DU TYPE D'ACQUISITION 	**/
	public void setGroupType(String pGroupType){	this.groupType = pGroupType;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE L'EPAISSEUR DE COUPE 	**/
	public String getThick() {	return thick;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE L'EPAISSEUR DE COUPE 	**/
	public void setThick(String pthick){	this.thick = pthick;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU PITCH 	**/
	public String getPitch() {	return pitch;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU PITCH 	**/
	public void setPitch(String ppitch){	this.pitch=  ppitch;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU MODE DE DETECTEUR 	**/
	public String getMode() {	return mode;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU MODE DE DETECTEUR 	**/
	public void setMode(String pmode){	this.mode = pmode;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE L'INTERVALE 	**/
	public String getInter() {	return inter;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE L'INTERVALE 	**/
	public void setInter(String pinter){	this.inter = pinter;	}
	

	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA COLLIMATION 	**/
	public String getRows() {	return rows;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA COLLIMATION 	**/
	public void setRows(String prows){	this.rows = prows;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU SMARTCATHODEMODE 	**/
	public String getSmartCathodeMode() {	return smartCathodeMode;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU SMARTCATHODEMODE 	**/
	public void setSmartCathodeMode(String pSmartCathodeMode){	this.smartCathodeMode = pSmartCathodeMode;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU SMARTCATHODENUMDEFLECTION 	**/
	public String getSmartCathodeNumDeflections() {	return smartCathodeNumDeflections;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU SMARTCATHODEMODE 	**/
	public void setSmartCathodeNumDeflections(String pSmartCathodeNumDeflections){	this.smartCathodeNumDeflections = pSmartCathodeNumDeflections;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DE LA TAILLE DU SFOV 	**/
	public String getSFOVSize() {	return sfovSize;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DE LA TAILLE DU SFOV 	**/
	public void setSFOVSize(String psfovSize){	this.sfovSize = psfovSize;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU TYPE DE SFOV 	**/
	public String getSFOVType() {	return sfovType;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU TYPE DE SFOV 	**/
	public void setSFOVType(String psfovType){	this.sfovType = psfovType;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DES KV	**/
	public String getKV() {	return kv;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DES KV 	**/
	public void setKV(String pkv){	this.kv = pkv;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DES MA 	**/
	public String getMA() {	return ma;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DES MA 	**/
	public void setMA(String pma){	this.ma = pma;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU FLAGMA 	**/
	public String getFlagMA() {	return flagMA;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU FLAGMA 	**/
	public void setFlagMA(String pFlagma){	this.flagMA = pFlagma;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU NOISE INDEX 	**/
	public String getNI() {	return ni;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU NOISE INDEX 	**/
	public void setNI(String pni){	this.ni = pni;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU MINMA 	**/
	public String getMinma() {	return minma;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU MINMA 	**/
	public void setMinma(String pminma){	this.minma = pminma;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU MAXMA 	**/
	public String getMaxma() {	return maxma;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU MAXMA 	**/
	public void setMaxma(String pmaxma){	this.maxma = pmaxma;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU CTDI 	**/
	public String getCtdi() {	return ctdi;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU CTDI 	**/
	public void setCtdi(String pctdi){	this.ctdi = pctdi;	}
	
	
	/**************************************************************************************************************************/
	/** 	METHODE D'OBTENTION DU PDL 	**/
	public String getPDL() {	return PDL;	}
	/**************************************************************************************************************************/
	/** 	METHODE DE DEFINITION DU PDL 	**/
	public void setPDL(String pPDL){	this.PDL = pPDL;	}

}
