package controller.utilidades;


import java.awt.*;
import java.awt.print.*;
import java.util.Date;


import javax.swing.JOptionPane;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.Doc;


public class ImprimirTicket {
  
  //Ticket attribute content
  private String contentTicket = 
    "                 {{header}}\n"+
    "          ADRIANA JAZMIN RAVELO BERMUDEZ\n"+
    "                 NIT:{{nit}}\n"+
    "             Av Cra 68 No 28 - 53 sur\n"+
    "           Telefonos: 7101600 - 7100888\n"+
    "              Celular: 3116315348\n"+
    "================================================\n"+
    "RESOLUCION FACTURA POS: {{resolucion}} \n"+
    "DEL: {{fechaInicio}} al {{fechaFin}}\n"+
    "AUTORIZA: {{numeraInicio}} al {{numeraFin}}\n"+
    "{{dateTime}}\n"+
    "================================================\n"+
    "FACTURA No: {{numeroFactura}} del: {{dateTime}} \n"+
    "================================================\n"+
    "{{cliente}}\n"+
    "================================================\n"+
    "{{item}}\n"+
    "================================================\n"+
    "             SUBTOTAL:           {{subTotal}}\n"+
    "             IVA:                {{tax}}\n"+
    "             TOTAL:              {{total}}\n\n"+
    "================================================\n"+
    "              GRACIAS POR SU COMPRA...\n"+
    "	   ESPERAMOS SU VISITA NUEVAMENTE\n"+
    "	       HORARIO DE ATENCION\n"+
    "         Lunes a Sabado 7:30 am a 6:00 pm\n"+
    "	    Festivos 9:00 am a 1:00 pm\n"+
    "\n"+
    "\n"+
    "\n ";
    
  //El constructor que setea los valores a la instancia
  public ImprimirTicket(String nameLocal, String nit, String resolucion, String fechaInicio, String fechaFin, String numeraInicio, String numeraFin, String numeroFactura, String item, String ticket, String caissier, String dateTime, String items, String subTotal, String tax, String total, String cliente, String change) {
    
	this.contentTicket = this.contentTicket.replace("{{header}}", nameLocal);
	this.contentTicket = this.contentTicket.replace("{{nit}}", nit);
	this.contentTicket = this.contentTicket.replace("{{resolucion}}", resolucion);
	this.contentTicket = this.contentTicket.replace("{{fechaInicio}}",  fechaInicio);
	this.contentTicket = this.contentTicket.replace("{{fechaFin}}", fechaFin);
	this.contentTicket = this.contentTicket.replace("{{numeraInicio}}", numeraInicio);
	this.contentTicket = this.contentTicket.replace("{{numeraFin}}", numeraFin);
    this.contentTicket = this.contentTicket.replace("{{numeroFactura}}", numeroFactura);
    this.contentTicket = this.contentTicket.replace("{{item}}", item);
    this.contentTicket = this.contentTicket.replace("{{ticket}}", ticket);
    this.contentTicket = this.contentTicket.replace("{{cajero}}", caissier);
    this.contentTicket = this.contentTicket.replace("{{dateTime}}", dateTime);
    this.contentTicket = this.contentTicket.replace("{{items}}", items);
    this.contentTicket = this.contentTicket.replace("{{subTotal}}", subTotal);
    this.contentTicket = this.contentTicket.replace("{{tax}}", tax);
    this.contentTicket = this.contentTicket.replace("{{total}}", total);
    this.contentTicket = this.contentTicket.replace("{{cliente}}", cliente);
    this.contentTicket = this.contentTicket.replace("{{change}}", change);
  }
    
  public void print() {
    //Especificamos el tipo de dato a imprimir
    //Tipo: bytes; Subtipo: autodetectado
    DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
    
    //Aca obtenemos el servicio de impresion por defatul
    //Si no quieres ver el dialogo de seleccionar impresora usa esto
    PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
   // PrinterService printerService = new PrinterService();
    
    //Con esto mostramos el dialogo para seleccionar impresora
    //Si quieres ver el dialogo de seleccionar impresora usalo
    //Solo mostrara las impresoras que soporte arreglo de bits
    /*
    PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
    PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
    PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
    PrintService service = ServiceUI.printDialog(null, 700, 200, printService, defaultService, flavor, pras);
      */
    //Creamos un arreglo de tipo byte
    byte[] bytes;

    //Aca convertimos el string(cuerpo del ticket) a bytes tal como
    //lo maneja la impresora(mas bien ticketera :p)
    
    System.out.println("Antes de volver bytes::."+ this.contentTicket);
    bytes = this.contentTicket.getBytes();
    
    System.out.println("Los bytes:::"+ bytes.length);

    //Creamos un documento a imprimir, a el se le appendeara
    //el arreglo de bytes
    
   
    
    Doc doc = new SimpleDoc(bytes,flavor,null);
    
      
    //Creamos un trabajo de impresión
    DocPrintJob job = defaultService.createPrintJob();

    //Imprimimos dentro de un try de a huevo
    try {
      //El metodo print imprime
      job.print(doc, null);
      
      
   //   byte[] cutP = new byte[] { 0x1d, 'V', 1 };

	//	printerService.printBytes("EPSON-TM-T20II", cutP);
      
    
    } catch (Exception er) {
      JOptionPane.showMessageDialog(null,"Error al imprimir: " + er.getMessage());
    }
  }

}