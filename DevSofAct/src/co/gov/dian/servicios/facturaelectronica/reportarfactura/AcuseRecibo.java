
package co.gov.dian.servicios.facturaelectronica.reportarfactura;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para AcuseRecibo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="AcuseRecibo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReceivedInvoice" type="{http://www.dian.gov.co/servicios/facturaelectronica/ReportarFactura}ReceivedInvoice" minOccurs="0"/>
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReceivedDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ResponseDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Response" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcuseRecibo", propOrder = {
    "receivedInvoice",
    "version",
    "receivedDateTime",
    "responseDateTime",
    "response",
    "comments"
})
public class AcuseRecibo {

    @XmlElement(name = "ReceivedInvoice")
    protected ReceivedInvoice receivedInvoice;
    @XmlElement(name = "Version", required = true)
    protected String version;
    @XmlElement(name = "ReceivedDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar receivedDateTime;
    @XmlElement(name = "ResponseDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar responseDateTime;
    @XmlElement(name = "Response")
    protected int response;
    @XmlElement(name = "Comments")
    protected String comments;

    /**
     * Obtiene el valor de la propiedad receivedInvoice.
     * 
     * @return
     *     possible object is
     *     {@link ReceivedInvoice }
     *     
     */
    public ReceivedInvoice getReceivedInvoice() {
        return receivedInvoice;
    }

    /**
     * Define el valor de la propiedad receivedInvoice.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivedInvoice }
     *     
     */
    public void setReceivedInvoice(ReceivedInvoice value) {
        this.receivedInvoice = value;
    }

    /**
     * Obtiene el valor de la propiedad version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Define el valor de la propiedad version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Obtiene el valor de la propiedad receivedDateTime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReceivedDateTime() {
        return receivedDateTime;
    }

    /**
     * Define el valor de la propiedad receivedDateTime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReceivedDateTime(XMLGregorianCalendar value) {
        this.receivedDateTime = value;
    }

    /**
     * Obtiene el valor de la propiedad responseDateTime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getResponseDateTime() {
        return responseDateTime;
    }

    /**
     * Define el valor de la propiedad responseDateTime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setResponseDateTime(XMLGregorianCalendar value) {
        this.responseDateTime = value;
    }

    /**
     * Obtiene el valor de la propiedad response.
     * 
     */
    public int getResponse() {
        return response;
    }

    /**
     * Define el valor de la propiedad response.
     * 
     */
    public void setResponse(int value) {
        this.response = value;
    }

    /**
     * Obtiene el valor de la propiedad comments.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Define el valor de la propiedad comments.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

}
