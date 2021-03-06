
package co.gov.dian.servicios.facturaelectronica.reportarfactura;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "WSHttpBinding_IWcfDianCustomerServices", targetNamespace = "http://wcf.dian.colombia")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface FacturaElectronicaPortName {


    /**
     * 
     * @param envioFacturaElectronicaPeticion
     * @return
     *     returns co.gov.dian.servicios.facturaelectronica.reportarfactura.AcuseRecibo
     */
    @WebMethod(operationName = "WSHttpBinding_IWcfDianCustomerServices")
    @WebResult(name = "WSHttpBinding_IWcfDianCustomerServices", targetNamespace = "http://wcf.dian.colombia", partName = "EnvioFacturaElectronicaRespuesta")
    public AcuseRecibo envioFacturaElectronica(
        @WebParam(name = "WSHttpBinding_IWcfDianCustomerServices", targetNamespace = "http://wcf.dian.colombia", partName = "EnvioFacturaElectronicaPeticion")
        EnvioFacturaElectronica envioFacturaElectronicaPeticion);

}
