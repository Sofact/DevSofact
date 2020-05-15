
package co.gov.dian.servicios.facturaelectronica.reportarfactura;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.gov.dian.servicios.facturaelectronica.reportarfactura package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EnvioFacturaElectronicaRespuesta_QNAME = new QName("http://wcf.dian.colombia", "IWcfDianCustomerServices");
    private final static QName _EnvioFacturaElectronicaPeticion_QNAME = new QName("http://wcf.dian.colombia", "IWcfDianCustomerServices");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.gov.dian.servicios.facturaelectronica.reportarfactura
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EnvioFacturaElectronica }
     * 
     */
    public EnvioFacturaElectronica createEnvioFacturaElectronica() {
        return new EnvioFacturaElectronica();
    }

    /**
     * Create an instance of {@link AcuseRecibo }
     * 
     */
    public AcuseRecibo createAcuseRecibo() {
        return new AcuseRecibo();
    }

    /**
     * Create an instance of {@link ReceivedInvoice }
     * 
     */
    public ReceivedInvoice createReceivedInvoice() {
        return new ReceivedInvoice();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AcuseRecibo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "IWcfDianCustomerServices")
    public JAXBElement<AcuseRecibo> createEnvioFacturaElectronicaRespuesta(AcuseRecibo value) {
        return new JAXBElement<AcuseRecibo>(_EnvioFacturaElectronicaRespuesta_QNAME, AcuseRecibo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnvioFacturaElectronica }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "IWcfDianCustomerServices")
    public JAXBElement<EnvioFacturaElectronica> createEnvioFacturaElectronicaPeticion(EnvioFacturaElectronica value) {
        return new JAXBElement<EnvioFacturaElectronica>(_EnvioFacturaElectronicaPeticion_QNAME, EnvioFacturaElectronica.class, null, value);
    }

}
