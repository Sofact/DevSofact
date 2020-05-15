//***************************************************************************************************
// Clase de creación del archivo
// Ejecuta el .bat para crear xml comprimida
//***************************************************************************************************

package controller.utilidades;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import controller.GetData;

public class CreateFile {
	
	public void create(int id, String tipo) {
		
		Generadores gen = new Generadores();
		GetData gd = new GetData();
		String path = gd.getParametroDominio("PATH").getPadValor();
		String pathZip = gd.getParametroDominio("PATH_ZIP").getPadValor();
		String directorioZip = path + gen.generadorNombreArchivo(tipo, id)+"\\\\"+ "compresor.bat" ;
		String archivoZip = path + gen.generadorNombreArchivo(tipo, id)+"\\\\"+ gen.generadorNombreArchivo(tipo, id) + ".zip";
		String archivo = path + gen.generadorNombreArchivo(tipo, id)+"\\\\"+ gen.generadorNombreArchivo(tipo, id) + ".xml";
		 try {
		
			 System.out.println("Entro por el lado de la creación del archivo::::" +  directorioZip);
		            String ruta = directorioZip;
		            String contenido = pathZip +" "+ archivoZip +" "+ archivo;
		            File file = new File(ruta);
		            File arc = new File(archivo);
		            int contador = 0;
		            while (!arc.exists()) {
		            	System.out.println(contador);
		            	contador++;
		            }
		            
		            // Si el archivo no existe es creado
		            if (!file.exists()) {
		                file.createNewFile();
		                
		            }
		            FileWriter fw = new FileWriter(file);
		            BufferedWriter bw = new BufferedWriter(fw);
		            bw.write(contenido);
		            bw.close();
		            
		            Thread.sleep(1000);
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		 
		 
		 Runtime aplicacion = Runtime.getRuntime(); 
	        try{

	        	aplicacion.exec("cmd.exe /K "+ directorioZip); 

	        }
	        catch(Exception e){System.out.println(e);}
		    
	}
	

}
