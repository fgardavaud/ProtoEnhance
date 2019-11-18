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

package com.DICOM ;
import java.awt.* ;
import java.util.*;
import java.io.* ;



public  class DicomHeaderReader{
	 byte[] data ;
	 int index  ;
	 private int dataLength;
	 private static  final boolean DEBUG = false ; 
	private int bytesFromTagToValue ;	
	public static final int MAX_HEADER_SIZE = 10000 ;
	private int maxHeaderSize  ;
	public static final String  ImplicitVRLittleEndian 	= "1.2.840.10008.1.2" 	;
	public static final String  ExplicitVRLittleEndian 	= "1.2.840.10008.1.2.1" 	;
	public static final String  ExplicitVRBigEndian 	= "1.2.840.10008.1.2.2" 	;
	public static final String  JPEGCompression 		= "1.2.840.10008.1.2.4." 	;
	public static final String  RLECompression 			= "1.2.840.10008.1.2.5" 	;
	
	public static final int _ImplicitVRLittleEndian = 0 ;
	public static final int _ExplicitVRLittleEndian = 1 ;
	public static final int _ExplicitVRBigEndian = 2 ;
	public static final int _ImplicitVRBigEndian = -2 ;
	public static final int _JPEGCompression = 10 ;
	public static final int _RLECompression = 20 ;
	public static final int _notUnderstood = -1000 ;
	
	private  boolean oneSamplePerPixel = true ;
	private  boolean oneFramePerFile   = true ;
	private int errorDetector = -1 ;
	
	private String VRString = "default implicitVR little endian";
	private String transfertSyntaxUID	= "" ;
	private String imageType		= "unknown" ;//	0x0008,0x0008
	private String studyDate		= "unknown" ;//	0x0008,0x0030	 
	private String modality		= "unknown" ;// 0x0008,0x0060
	private String manufacturer	= "unknown" ;//	0x0008,0x0070
	private String	institution	= "unknown" ;//	0x0008,0x0080
	private String physician	= "unknown" ;//	0x0008,0x0090
	private String patientName		= "unknown" ;//	0x0010,0x0010
	private String patientID		= "unknown" ;//	0x0010,0x0020
	private String patientBirthdate= "unknown" ;//	0x0010,0x0030
	private String sex				= "unknown" ;//	0x0010,0x00400
	
	
	private int numberOfFrames		=  1;	// 0x0028,0x0008 
	private int samplesPerPixel 	=  1;	// 0x0028,0x0002
	private int pixelSpacing		=  1;	// 0x0028,0x0030
	private int	h 					= -1; 	// 0x0028,0x0010
	private int w 					= -1;	// 0x0028, 0x0011
	private int bitsAllocated		= -1;	// 0x0028, 0x0100
	private int bitsStored 			= -1;	// 0x0028, 0x0101
	private int highBit 			= -1; 	// 0x0028, 0x0102
	private int signed 				= -1;	// 0x0028, 0x0103
	private int windowLevel 		= -1 ;	// 0x0028,0x1050
	private int windowWidth 		= -1 ; 	// 0x0028, 0x1051
	private int rescaleIntercept	= -1 ;	// 0x0028, 0x1052
	private int rescaleSlope		= -1 ;	// 0x0028,0x1053
	private int size 				= -1 ;	// 0x7Fe0, 0x0010
	private int n 					= -1 ;	// = 1 or 2 
	private int VR					= _ImplicitVRLittleEndian;
	
	private boolean bE = false ;
	

/**
*	There is only one constructor , it needs an array of byte as an argument ,
*	this array is  the Dicom file you want to read.
*/	 
	
	public DicomHeaderReader ( byte [] dicomArray ){
	 	this.data = dicomArray ;
	 	dataLength =  data.length ;
	 	index =0;
	 	initHeaderSize() ;
	 	getVR();
	 	getEssentialData() ;
	}


	protected void initHeaderSize(){
		maxHeaderSize = MAX_HEADER_SIZE * 1 ;
		if(maxHeaderSize > dataLength) maxHeaderSize = dataLength ;
	
	}
	


		
protected void getVR(){

	
	transfertSyntaxUID		= getaString (0x0002,0x0010, index);
	if(!transfertSyntaxUID.equals("Unknown") ){
			
		transfertSyntaxUID = transfertSyntaxUID.trim() ;
			
		if(transfertSyntaxUID.equals(ImplicitVRLittleEndian)) VR =_ImplicitVRLittleEndian ;
		else if(transfertSyntaxUID.equals(ExplicitVRLittleEndian)) VR =_ExplicitVRLittleEndian ;	
		else if(transfertSyntaxUID.equals(ExplicitVRBigEndian)) VR =_ExplicitVRBigEndian ;	
		else if(transfertSyntaxUID.startsWith(JPEGCompression)) VR =_JPEGCompression ;		
		else if(transfertSyntaxUID.startsWith(RLECompression)) VR =_RLECompression ;		
		else VR = _notUnderstood ;
		

		switch (VR){
			case _ImplicitVRLittleEndian : VRString = "ImplicitVRLittleEndian";break;
			case _ExplicitVRLittleEndian : VRString = "ExplicitVRLittleEndian";break;
			case _ExplicitVRBigEndian 	: VRString = "ExplicitVRBigEndian";break;
			case _JPEGCompression 		: VRString = "JPEGCompression";break;
			case _RLECompression 		: VRString = "RLECompression" ;break;
			case _notUnderstood 		: VRString = "not understood" ;break;
			default : VRString =" Something curious happened !" ;
		}
		
	}else if(transfertSyntaxUID.equals("Unknown")){ 
		transfertSyntaxUID = "Transfer syntax UID tag not found";
		VRString = "Default VR implicit little endian";
	}
}
	

protected void getEssentialData(){

	imageType 		= getaString (0x0008,0x0008,index);
	studyDate		= getaString (0x0008,0x0020,index);
	modality		= getaString (0x0008,0x0060,index);
	manufacturer	= getaString (0x0008,0x0070,index);
	institution		= getaString (0x0008,0x0080,index);
	physician		= getaString (0x0008,0x0090,index);
	patientName		= getaString (0x0010,0x0010,index);
	patientID		= getaString (0x0010,0x0020,index);
	patientBirthdate= getaString(0x0010,0x0030,index);
	sex				= getaString (0x0010,0x0040,index);

	h = getAnInt(0x0028, 0x0010, index ) ;
	w = getAnInt(0x0028, 0x0011, index ) ;
	pixelSpacing 		= getAnInt(0x0028, 0x0030, index ) ;
	bitsAllocated		= getAnInt(0x0028, 0x0100, index ) ;
	bitsStored 			= getAnInt(0x0028, 0x0101, index ) ;
	highBit 			= getAnInt(0x0028, 0x0102, index ) ;
	signed 				= getAnInt(0x0028, 0x0103, index ) ;
	windowLevel 		= Integer.parseInt(getaString(0x0028, 0x1050, index));
	windowWidth 		= Integer.parseInt(getaString(0x0028, 0x1051, index)) ;
	rescaleIntercept	= Integer.parseInt(getaString(0x0028, 0x1052, index )) ;
	rescaleSlope		= Integer.parseInt(getaString(0x0028,0x1053, index )) ;

	
	debug( "TransfertSyntaxUID : " + transfertSyntaxUID ) ;
	debug( "Value representation : " + VRString ) ;
	debug( "ImageType 	" + imageType ) ;
	debug( " h ::: 						" + h ) ;
	debug( " w :::  					" + w ) ;
	debug( " bitsAllocated ::: 			" + bitsAllocated ) ;
	debug( " bitsStored ::: 			" + bitsStored ) ;
	debug( " highBit ::: 				" + highBit ) ;
	debug( " signed ::: 				" + signed ) ;
	debug( " windowLevel :::			" + windowLevel );		


	
	int pos		= lookForMessagePosition( 0x7Fe0, 0x0010, index);
	if( pos != -1 )  size  =  readMessageLength(pos+4) ;

	debug( "\nValueLength for  0x7FEO,0010 tag	 	" + size ) ;
	int HeaderSize =  pos ;
	debug( "\nHeaderSize    : " + HeaderSize ) ;
	

	int tSize =  samplesPerPixel * w*h*bitsAllocated/8 ;

	int figuredSize = HeaderSize + 8 + tSize ;
	errorDetector = dataLength - figuredSize ;
	debug( "Data Length -  Theoriticaly_figuredSize  : "+ errorDetector ) ;
	
	if(errorDetector == 4 ){
		size = readMessageLength(pos+8) ;
		errorDetector = dataLength - size ;
	}
		
	if( errorDetector != 0){ 
			

		samplesPerPixel 	= getAnInt(0x0028, 0x0002) ;
		if(samplesPerPixel <0 || samplesPerPixel> 3 ){
			samplesPerPixel = 1 ;
			debug( " SamplesPerPixel ::: 	" + samplesPerPixel ) ;
		}
		else if (samplesPerPixel == 1) oneSamplePerPixel = true ;
	
	
	
		try{numberOfFrames = Integer.parseInt(getaString(0x0028,0x0008)) ; }
		catch(NumberFormatException nFE){ numberOfFrames = 1 ;}
		if (numberOfFrames > 1 ) oneFramePerFile = false ;
		tSize = numberOfFrames * tSize * samplesPerPixel; 
		figuredSize = HeaderSize + 8 + tSize ;
		errorDetector = dataLength - figuredSize ;
		

		if (VR == _JPEGCompression) 
			debug( "_JPEGCompression , can't read that file" ) ;		
		
		if (VR == _RLECompression) 
			debug( " RLECompression , can't read that file" ) ;
		
		debug( "Byte difference between figured sized and size tag: "	+ errorDetector 
		+"\n Frame per file  " + 	numberOfFrames 
		+"\n samplesPerPixel "  +		samplesPerPixel) ;
		}

	}

	private  int lookForMessagePosition(int groupElement, int dataElement ){
		return lookForMessagePosition(groupElement, dataElement, 0 );
	}
	
	
	private  int lookForMessagePosition(int groupElement, int dataElement, int  j ){
		int LenMax = data.length -3;
	 	boolean found = false ;
	 	byte testByte1 = (byte) (groupElement & 0xff);
	 	byte testByte2 = (byte) ((groupElement & 0xff00)>>8);
	 	byte testByte3 = (byte) (dataElement & 0xff);
	 	byte testByte4 = (byte) ((dataElement & 0xff00)>> 8);
	 	
		 	debug(" Looking for :"+ Integer.toHexString(testByte1)
	 				+Integer.toHexString(testByte2)+", "
	 				+Integer.toHexString(testByte3) 
	 				+Integer.toHexString(testByte4));
		
		for ( ;  j< LenMax || found ; j++){
	 		if( ( data[j] == testByte1 ) && (data[j+1] == testByte2) && (data[j+2]== testByte3) && (data[j+3]== testByte4)){
	 				found = true ;
	 				return j ;
	 		}
		}
		return -1 ;
	}
	

	private int readMessageLength(int i){
		int i0 = (int) (data[i ] &0xff);
	 	int i1 =	(int) (data[i + 1 ] &0xff);
	 	int i2 =	(int) (data[i + 2 ] &0xff);
	 	int i3 =	(int) (data[i + 3 ] &0xff);
		return i3<<24| i2<<16 |i1<< 8|i0 ;	
	}
	
	
	private int readVRMessageLength(int tagPos){
		if(VR == _ImplicitVRLittleEndian ){
			bytesFromTagToValue = 8 ;
			return readInt32(tagPos + 4 ) ;
		}

		String VRTypeOf = 
			new String(new byte[]{data[tagPos+4],data[tagPos+5]});	
		debug ("VR type of : "+ VRTypeOf );
		if( VRTypeOf.equals("OB")|
				VRTypeOf.equals("OW")|
				VRTypeOf.equals("SQ")|
				VRTypeOf.equals("UN")){
			bytesFromTagToValue = 12;

			return readInt32(tagPos + 8 );
		}

	 	else{
	 		bytesFromTagToValue = 8 ;
	 		return	readInt16(tagPos + 6 ) ;
		}
	}
	
	private int readInt32(int i) { 

		int i0 = 	(int) (data[i ] &0xff);
	 	int i1 =	(int) (data[i + 1 ] &0xff);
	 	int i2 =	(int) (data[i + 2 ] &0xff);
	 	int i3 =	(int) (data[i + 3 ] &0xff);
		return i3<<24| i2<<16 |i1<< 8|i0 ;	

	}
	
	private int readInt16( int i ){
	 		
	 	int  i1 = data[i+1]&0xff ;
	 	int  i0 = data[i]&0xff ;	
	 	int anInt =  i1<<8|i0 ;
	 	if (anInt < -1){ anInt= (int)(data[i]*256)&0xff + data[i+1]&0xff ;
	 	  	debug("Byte swapped at readInt16 :" + anInt) ;
	 	}
		return anInt ;
	}
	
	private void skip (int length){
		index += length;
	}
	
	/**
	*	This method get an integer if you give it the tags.
	*/
	public int  getAnInt(int groupElement, int dataElement){
		return getAnInt( groupElement, dataElement, 0);
	}
	
	private int  getAnInt(int groupElement, int dataElement, int j){
		int pos = lookForMessagePosition( groupElement, dataElement, j);
		if(pos < maxHeaderSize && pos != -1 ){
			index = pos ;
			if (readVRMessageLength(pos) == 2 )
					return readInt16( pos + bytesFromTagToValue );
			else if(readVRMessageLength(pos) == 4 )
					return readInt32(pos + bytesFromTagToValue );
			else return -1; 
		}
		else return -1 ;	
	}

	public int  getSize()	{ return dataLength;}
	public int getNumberOfFrames(){ return numberOfFrames ;}
	public int getSamplesPerPixels() { return samplesPerPixel ;}
	public int getPixelSpacing() { return pixelSpacing;}
	public int getPixelDataSize(){ 
		if (errorDetector == 0 )return size ;
		else return -1 ;
	}
	
	
	
/**	The  height   : */
	public int  getRows()	{return h ;}
/**	The width 		:*/
	public int  getColumns(){ return w;}	
	public int  getBitAllocated(){return bitsAllocated;}	
/** the bits stored per pixel of image   */
	public int  getBitStored(){return bitsStored;}	
/** Other values : */
	public int  getHighBit(){return	highBit;}
	public int  getSamplesPerPixel(){return	samplesPerPixel;}
	public int  getPixelRepresentation(){return	signed;}
	public int 	getWindowLevel() {return windowLevel;}
	public int 	getWindowWidth() {return windowWidth;}
	public int 	getRescaleIntercept() {return rescaleIntercept;}
	public int 	getRescaleSlope() {return rescaleSlope;}
	
	public String  getPatientName(){ 	return patientName; 		}
	public String  getPatientBirthdate(){ return patientBirthdate; 	}
	public String  getManufacturer(){ 	return manufacturer ;		}
	public String  getPatientID(){ 		return patientID 	; 		}
	public String  getImageType(){ 		return imageType; 			}
	public String  getStudyDate(){ 		return studyDate; 			}
	public String  getModality(){ 		return modality; 			}
	
/** Retrieves a String when you knows the tags  */	
	public String  getaString(int groupNumber, int elementNumber){
		return getaString( groupNumber, elementNumber, 0) ;
	}
	
	private String  getaString(int groupNumber, int elementNumber, int j ){
		int pos = lookForMessagePosition(groupNumber,elementNumber,j);
		
		if(pos < MAX_HEADER_SIZE && pos != -1 ){
			int length = readMessageLength(pos+4);
			if (length >256)length = readInt16( pos + 6 );
			if (length > 64 || length < 0 ) length = 64 ;
			if (length > (dataLength - pos-8)) length = dataLength -pos -9 ;
			index = pos  ;
			pos += 8;
			char[] result = new char[length];
			
			for (int i = 0; i < length ;i++){
				result[i] = (char)data[pos++];
			}
			return new String( result ).trim();
			}
		else return "Unknown" ;	
	}
		
   public int  getFileDataLength(){
   		int pos = lookForMessagePosition( 0x7Fe0, 0x0010);
   		if ( pos != -1) return  readMessageLength(pos+4) ;
   		else return -1 ;
	}
			
	public byte[] getPixels() throws IOException{
		
	
		if (VR == _JPEGCompression ) 
			throw new IOException("DICOM JPEG compression not yet supported ") ;	
			
		int w = getRows() ;
			if ( w ==  -1){
				throw new IOException("Format not recognized") ;		
				 //return null ;
			}
		int h = getColumns();
			if( h == -1) throw new IOException("Format not recognized") ;
		
		int ba = getBitAllocated();
		if( ba%8 == 0) { ba = ba/8 ;}
		else ba = (ba+8)/8 ;
		int fileLength = w * h * ba ;
		int offset = dataLength - fileLength ; 
		byte[]  pixData = new byte[ fileLength ];
		java.lang.System.arraycopy (data, offset, pixData,0, fileLength );	
		return pixData ;
	
	}
	
	public byte[] getPixels(int number) throws IOException{
		
		if( number > numberOfFrames ) throw new IOException( "Doesn't have such a frame ! ") ;
		if (VR == _JPEGCompression ) 
			throw new IOException("DICOM JPEG compression not yet supported ") ;	
			
		int w = getRows() ;
			if ( w ==  -1){
				throw new IOException("Format not recognized") ;		
				 //return null ;
			}
		int h = getColumns();
			if( h == -1) throw new IOException("Format not recognized") ;
		
		int ba = getBitAllocated();
			if( ba%8 == 0) { ba = ba/8 ;}
				else ba = (ba+8)/8 ;
		int fileLength = w * h * ba ;
		int offset = dataLength - (fileLength * number);
		byte[]  pixData = new byte[ fileLength ];
			java.lang.System.arraycopy (data, offset, pixData,0, fileLength );	
		return pixData ;
	
	}
		
	public String[] getInfo(){
		
		String [] info = new String[16]; 
		info[0]  = "Patient 's name              : " + getPatientName();
		info[1]  = "Patient 's ID                : " + getPatientID();
		info[2]  = "Patient 's birthdate         : " + getPatientBirthdate();
		info[3]  = "Patient 's sex               : " + sex;
		info[4]  = "Study Date                   : " + getStudyDate();
		info[5]  = "Modality                     : " + getModality();
		info[6]  = "Manufacturer                 : " + getManufacturer();
		info[7]  = "Number of frames             : " + getNumberOfFrames();
		info[8]  = "Width                        : " + getColumns();
		info[9]  = "Height                       : " + getRows();
		info[10] = "Bits allocated               : " + getBitAllocated();
		info[11] = "Bits stored                  : " + getBitStored();
		info[12] = "Sample per pixels            : " + getSamplesPerPixel();
		info[13] = "Physician                    : " + physician;
		info[14] = "Institution                  : " + institution;
		info[15] = "Transfert syntax UID         : " + transfertSyntaxUID ;
		
	return info ;
	}

	public Hashtable getMedicalInfos() {
		Hashtable table = new Hashtable(8);		
		table.put("patient.name",getPatientName());
		table.put("patient.id",getPatientID());
		table.put("patient.birthdate",getPatientBirthdate());
		table.put("sex",sex);
		table.put("study.date",getStudyDate());
		table.put("physician",physician);
		table.put("institution",institution);
		table.put("transfert.syntax.uid",transfertSyntaxUID);
		return table;
	}	
	protected  final static String author(){
			return ("Serge Derhy") ;
		}

	private void  debug(String message){
		if (DEBUG )System.out.println(message);	
	}
}