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

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.DICOM.DicomHeaderReader;

public class DicomImage extends JLabel {
	
	
	
	public boolean fullscreenChange;
	private BufferedImage DcmImage;
	private byte [] pixelData;
	private DicomHeaderReader dhr;
	private double WLevel;
	private double WWidth;
	private double originalWLevel, originalWWidth;
	private int columns;
	private int rows;
	private int colorMax;
	private int colorMask;
	private int bitsAllocated;
	private int rescaleIntercept;
	private String fenêtrage;
	
	
	
	public DicomImage(){	}
		
	public void setDicomImage(String pPath, Double dimension, String pFenêtrage){
		//Récupération des bytes du fichier Dicom à partir du chemin
		Path path = Paths.get(pPath);
		byte[] dicomBytes;		
		try{
			dicomBytes = Files.readAllBytes(path);
			dhr = new DicomHeaderReader(dicomBytes);
		}
		catch(IOException e2){}
		
		// Récupération des paramètres de l'image
		columns = dhr.getColumns();
		rows = dhr.getRows();
		bitsAllocated = dhr.getBitAllocated();
		 
		// Réglage du fenêtrage initial
		fenêtrage = pFenêtrage;
		rescaleIntercept = dhr.getRescaleIntercept();
		originalWLevel = dhr.getWindowLevel();		
		originalWWidth = dhr.getWindowWidth();
		WLevel = originalWLevel;
		WWidth = originalWWidth;	
		colorMax = 1 << (bitsAllocated-1);
		colorMask = (colorMax << 1) - 1;
				
		try {
			pixelData = dhr.getPixels();
		} catch (IOException e) {}		

		DcmImage = createDcmImage( );
		this.setIcon(new ImageIcon(Scale(DcmImage, dimension)));
	}

	
    public BufferedImage createDcmImage( )
    {
    	byte [] pixels = applyWindow( pixelData );
		return bytesToImage( pixels, columns, rows );
	}
    
    public byte [] applyWindow( byte[] pixels_arg )
    {
    	byte [] pixels = new byte [ pixels_arg.length/2 ];
    	double wl;
    	double ww;
    	
    	
    	switch (fenêtrage){
    	
    	case "abdo":
			wl = 40 - rescaleIntercept;
			ww = 400;
			break;
		case "cerveau":
			wl = 45 - rescaleIntercept;
			ww = 135;
			break;
		case "mediastin":
			wl = 50 - rescaleIntercept;
			ww = 350;
			break;
		case "os":
			wl = 500 - rescaleIntercept;
			ww = 2500;
			break;
		case "poumon":
			wl = -600 - rescaleIntercept;
			ww = 1600;
			break;		
		case "rachis":
			wl = 35 - rescaleIntercept;
			ww = 250;
			break;
		default:
			wl = originalWLevel - rescaleIntercept;
			ww = originalWWidth;
			break;
    	}			
		    	
    	double lmax = wl + 0.5f*ww;
		double lmin = wl - 0.5f*ww;
		int iMax = pixels.length - 4;
		int colorValue, c;
		for ( int i=0 ; i<iMax ; i++ )
		{
			c = (((pixels_arg[2*i + 3] & 0xff) << 24) | ((pixels_arg[2*i + 2] & 0xff) << 16)
	                | ((pixels_arg[2*i + 1] & 0xff) << 8) | (pixels_arg[2*i] & 0xff)) & colorMask;	

			// Fenêtrage
			if ( (c > lmin) && (c < lmax) )
			{
				// Si la valeur du pixel est dans la plage visible
				colorValue = (int) Math.floor( (double) (255*(c-lmin)/ww) );
			}
			else
			{
				// sinon on le fixe noir ou blanc
				colorValue = ( c >= lmax && c < colorMax ) ? 255 : 0;
			}

			pixels[i] = (byte) (colorValue & colorMask);
		}
		
		return pixels;
    }

    public BufferedImage bytesToImage( byte[] pixels, int w, int h )
    {
		DataBuffer db = new DataBufferByte( pixels, w*h );
		WritableRaster raster = Raster.createInterleavedRaster( db,
			w, h, w, 1, new int[]{0}, null );
		ColorSpace cs = ColorSpace.getInstance( ColorSpace.CS_GRAY );
		ColorModel cm = new ComponentColorModel( cs, false, false,
		Transparency.OPAQUE, DataBuffer.TYPE_BYTE );
		return new BufferedImage( cm, raster, false, null );
	}
 
	
	BufferedImage Scale(BufferedImage BufImg, Double dimension)   {		
		AffineTransform tx = new AffineTransform();
		double scaleValue;
		if (dimension == 1){
			scaleValue = dimension;
		}
		else{
			scaleValue = dimension/ (double) BufImg.getWidth();
		}
		tx.scale(scaleValue, scaleValue);
		AffineTransformOp op = new AffineTransformOp (tx, AffineTransformOp.TYPE_BILINEAR);
		BufferedImage newBufImg = new BufferedImage( (int)(BufImg.getWidth()*scaleValue), (int)(BufImg.getHeight()*scaleValue), BufImg.getType());
		return op.filter(BufImg, newBufImg);
	}
}
