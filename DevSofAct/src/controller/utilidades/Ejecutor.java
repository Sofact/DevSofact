//***************************************************************************************************
// Clase para ejecutar el zipper
//***************************************************************************************************


package controller.utilidades;

import java.io.File;
import controller.GetData;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
 
public class Ejecutor {
 
	
	 public static void zip(String targetPath, String destinationFilePath) {
		          
	        try {
	            ZipParameters parameters = new ZipParameters();
	            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
	            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

	            ZipFile zipFile = new ZipFile(destinationFilePath);

	            File targetFile = new File(targetPath);
	            if(targetFile.isFile()){
	            	
	                zipFile.addFile(targetFile, parameters);            
	            }else if(targetFile.isDirectory()){
	                zipFile.addFolder(targetFile, parameters);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
	public void Zipper(int id, String tipo) {
		// cadena que contiene la ruta donde están los archivos a comprimir
		
		Generadores gen = new Generadores();
		GetData gd = new GetData();
		String path = gd.getParametroDominio("PATH").getPadValor();
		String directorioZip = path + gen.generadorNombreArchivo(tipo, id)+"\\\\"+ gen.generadorNombreArchivo(tipo, id) + ".zip" ;
		String archivo = path + gen.generadorNombreArchivo(tipo, id)+"\\\\"+ gen.generadorNombreArchivo(tipo, id) + ".xml";
		
		Ejecutor.zip(archivo, directorioZip);
		
	}
}