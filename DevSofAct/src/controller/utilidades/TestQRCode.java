package controller.utilidades;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import controller.GetData;
import model.Cliente;
import model.Empresa;
import model.Factura;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
 

public class TestQRCode {
 
    public void generador(int id, String tipo) {
 
    	Generadores gen = new Generadores();
        TestQRCode qr = new TestQRCode();
        GetData gd = new GetData();
        String path = gd.getParametroDominio("PATH").getPadValor();
        Factura factura = new Factura();
        Empresa empresa = new Empresa();
        Cliente cliente = new Cliente();
       
        factura =  gd.getLastFactura(id);
        empresa = gd.getEmpresa();
        cliente = gd.getCliente(factura.getCliId());
        		
        String nombreArchivo= null;
        

        nombreArchivo = gen.generadorNombreArchivo(tipo, id);
        File f = new File(path + nombreArchivo +"\\" + "codigoqr"+".png");
               String text = "NumFac: "+ factura.getFacNumFactura() + "\r\nFecFac: " + factura.getFacFechaFactura() + "\r\nNitFac: " + empresa.getEmpNit() + "\r\nDocAdq: "+ cliente.getCliNumIdent() + 
        				"\r\nValFac: " + factura.getFacSubTotal() + "\r\nValIva: " + factura.getFacIva() + "\r\nValFacImp: " + factura.getFacTotal();
 
        try {
 
            qr.generateQR(f, text, 300, 300);
            
           
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
 
    public File generateQR(File file, String text, int h, int w) throws Exception {
 
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(text, com.google.zxing.BarcodeFormat.QR_CODE, w, h);
 
        BufferedImage image = new BufferedImage(matrix.getWidth(), matrix.getHeight(), BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
 
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrix.getWidth(), matrix.getHeight());
        graphics.setColor(Color.BLACK);
 
        for (int i = 0; i < matrix.getWidth(); i++) {
            for (int j = 0; j < matrix.getHeight(); j++) {
                if (matrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
 
        ImageIO.write(image, "png", file);
 
        return file;
 
    }
 
 
    public String decoder(File file) throws Exception {
 
        FileInputStream inputStream = new FileInputStream(file);
 
        BufferedImage image = ImageIO.read(inputStream);
 
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
 
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
 
        // decode the barcode
        QRCodeReader reader = new QRCodeReader();
        Result result = reader.decode(bitmap);
        return new String(result.getText());
    }
}