
package co.gov.dian.servicios.facturaelectronica.reportarfactura;


import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Envoltorio para recibir facturas electrónicas por medio de web service
 * 
 * <p>Clase Java para EnvioFacturaElectronica complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="EnvioFacturaElectronica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NIT" type="{http://www.dian.gov.co/servicios/facturaelectronica/ReportarFactura}NitType"/>
 *         &lt;element name="InvoiceNumber" type="{http://www.dian.gov.co/servicios/facturaelectronica/ReportarFactura}InvoiceNumberType"/>
 *         &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Document" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnvioFacturaElectronica", propOrder = {
    "nit",
    "invoiceNumber",
    "issueDate",
    "document"
})
public class EnvioFacturaElectronica {

    @XmlElement(name = "NIT", required = true)
    protected String nit;
    @XmlElement(name = "InvoiceNumber", required = true)
    protected String invoiceNumber;
    @XmlElement(name = "IssueDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar issueDate;
    @XmlElement(name = "Document", required = true)
    @XmlMimeType("*/*")
    protected byte[] document;

    /**
     * Obtiene el valor de la propiedad nit.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNIT() {
        return nit;
    }

    /**
     * Define el valor de la propiedad nit.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNIT(String value) {
        this.nit = value;
    }

    /**
     * Obtiene el valor de la propiedad invoiceNumber.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Define el valor de la propiedad invoiceNumber.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceNumber(String value) {
        this.invoiceNumber = value;
    }

    /**
     * Obtiene el valor de la propiedad issueDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getIssueDate() {
        return issueDate;
    }

    /**
     * Define el valor de la propiedad issueDate.
     * 
     * @param cal
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIssueDate(XMLGregorianCalendar cal) {
        this.issueDate = cal;
    }

    /**
     * Obtiene el valor de la propiedad document.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public byte[] getDocument() {
        return document;
    }

    /**
     * Define el valor de la propiedad document.
     * 
     * @param document2
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setDocument(byte[] document2) {
        this.document = document2;
    }

}
