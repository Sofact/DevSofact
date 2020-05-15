package controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import controller.utilidades.Convertidores;
import controller.utilidades.CreateFile;
import controller.utilidades.Generadores;
import controller.utilidades.SignerTwo;
import model.Cliente;
import model.Departamento;
import model.Empresa;
import model.Factura;
import model.FacturaDetalle;
import model.Municipio;
import model.ResolucionFacturacion;
import model.TipoDocumento;

public class XMLGenerator {

	public void generador(int idFactura) {
		
		GetData gd= new GetData();
		Factura factura;
		List<FacturaDetalle> facturaDetalle;
		Cliente cliente;
		Empresa empresa;
		Municipio municipio;
		Departamento departamento;
		Municipio municipioCustomer;
		Departamento departamentoCustomer;
		ResolucionFacturacion resolucion;
		TipoDocumento tipoDoc;
		TipoDocumento tipoDcoCliente;
		SignerTwo sign = new SignerTwo();
		
		factura = gd.getLastFactura(idFactura);	
		facturaDetalle = gd.getFacturaDetalle(idFactura);
		cliente = gd.getCliente(factura.getCliId());
		empresa = gd.getEmpresa();
		municipio = gd.getMunicipio(empresa.getMuniId());
		departamento = gd.getDepartamento(municipio.getDepId());
		municipioCustomer = gd.getMunicipio(cliente.getMuniId());
		departamentoCustomer = gd.getDepartamento(municipioCustomer.getDepId());
		resolucion = gd.getResolucion("FE");
		tipoDoc = gd.getTipoDoc(empresa.getTidId());
		tipoDcoCliente = gd.getTipoDoc(cliente.getCliTipoIdent());
		Generadores gen = new Generadores();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String FORMATER = "yyyy-MM-dd'T'HH:mm:ss";
		DateFormat hora = new SimpleDateFormat("HH:mm:ss");
		Convertidores convertidores = new Convertidores();
		
		
		
		String fechaFin = dateFormat.format(resolucion.getFechaCreacion());
		int id_tipo = 0;
		switch (cliente.getCliTipoIdent()) {
		
		case 6:
			id_tipo = 31;
			break;
			
		case 3:
			id_tipo = 13;
			break;
			
		case 5:
			id_tipo = 22;
			break;
			
		case 2:
			id_tipo = 12;
			break;
		
	}
		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			
			
			Document doc = docBuilder.newDocument();
			
			

			Element rootElement = doc.createElement("fe:Invoice");
			doc.appendChild(rootElement);
			rootElement.setAttributeNS("http://www.w3.org/2000/xmlns/","xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
			
			Attr xmlns = doc.createAttribute("xmlns");
			xmlns.setValue("http://www.w3.org/2001/XMLSchema");
			rootElement.setAttributeNode(xmlns);
			
			Attr xmlnsExtAtribute = doc.createAttribute("xmlns:ext");
			xmlnsExtAtribute.setValue("urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
			rootElement.setAttributeNode(xmlnsExtAtribute);
			
			Attr xmlnsTns = doc.createAttribute("xmlns:tns");
			xmlns.setValue("http://www.dian.gov.co/contratos/facturaelectronica/v1");
			rootElement.setAttributeNode(xmlns);
			
			Attr xmlnsCbc = doc.createAttribute("xmlns:cbc");
			xmlnsCbc.setValue("urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2");
			rootElement.setAttributeNode(xmlnsCbc);
			
			Attr xmlnsCac = doc.createAttribute("xmlns:cac");
			xmlnsCac.setValue("urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
			rootElement.setAttributeNode(xmlnsCac);
			//rootElement.setAttributeNS("http://www.w3.org/2000/xmlns/","xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
			
			Attr xmlnsfeAtribute = doc.createAttribute("xmlns:fe");
			xmlnsfeAtribute.setValue("http://www.dian.gov.co/contratos/facturaelectronica/v1");
			rootElement.setAttributeNode(xmlnsfeAtribute);
			
			Attr xmlnsfeWsee = doc.createAttribute("xmlns:wsse");
			xmlnsfeWsee.setValue("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
			rootElement.setAttributeNode(xmlnsfeWsee);
						
			Attr xmlnsSts = doc.createAttribute("xmlns:sts");
			xmlnsSts.setValue("http://www.dian.gov.co/contratos/facturaelectronica/v1/Structures");
			rootElement.setAttributeNode(xmlnsSts);
			
			Attr xmlnsXml = doc.createAttribute("xmlns:xml");
			xmlnsXml.setValue("http://www.w3.org/XML/1998/namespace");
			rootElement.setAttributeNode(xmlnsXml);
			
			/*
			Attr TargetNameSpace = doc.createAttribute("TargetNameSpace");
			TargetNameSpace.setValue("http://www.dian.gov.co/contratos/facturaelectronica/v1");
			rootElement.setAttributeNode(TargetNameSpace);
			*/
			/*
			Attr elementFormDefault = doc.createAttribute("elementFormDefault");
			elementFormDefault.setValue("qualified");
			rootElement.setAttributeNode(elementFormDefault);
			*/
		
			Element UBLExtensions = doc.createElement("ext:UBLExtensions");
			rootElement.appendChild(UBLExtensions);
			
		
			/*
			Attr xmlnsExtAtribute = doc.createAttribute("xmlns:ext");
			xmlnsExtAtribute.setValue("urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
			rootElement.setAttributeNode(xmlnsExtAtribute);
			*/
			
			
			Element UBLExtension = doc.createElement("ext:UBLExtension");
			UBLExtensions.appendChild(UBLExtension);
			
			Element extensionContent = doc.createElement("ext:ExtensionContent");
			UBLExtension.appendChild(extensionContent);
			
			Element DianExtensions = doc.createElement("sts:DianExtensions");
			extensionContent.appendChild(DianExtensions);
			
			Element InvoiceControl = doc.createElement("sts:InvoiceControl");
			DianExtensions.appendChild(InvoiceControl);
			
			Element InvoiceAutorization = doc.createElement("sts:InvoiceAuthorization");
			InvoiceControl.appendChild(InvoiceAutorization);
			InvoiceAutorization.appendChild(doc.createTextNode(resolucion.getRefDescripcion()));
					
			Element AuthorizationPeriod = doc.createElement("sts:AuthorizationPeriod");
			InvoiceControl.appendChild(AuthorizationPeriod);
			
			Element StartDateReferencia = doc.createElement("cbc:StartDate");
			AuthorizationPeriod.appendChild(StartDateReferencia);
			StartDateReferencia.appendChild(doc.createTextNode(String.valueOf(resolucion.getRefFechaInicio())));
			
			Element EndDateReferencia = doc.createElement("cbc:EndDate");
			AuthorizationPeriod.appendChild(EndDateReferencia);
			EndDateReferencia.appendChild(doc.createTextNode(fechaFin));
			
			Element AuthorizedInvoices = doc.createElement("sts:AuthorizedInvoices");
			InvoiceControl.appendChild(AuthorizedInvoices);
			
			
			System.out.println("El prefijo ::::" + resolucion.getRefPrefijo() + ":::");
			Element Prefix = doc.createElement("sts:Prefix");
			AuthorizedInvoices.appendChild(Prefix);
			Prefix.appendChild(doc.createTextNode(resolucion.getRefPrefijo()));
			
			Element From = doc.createElement("sts:From");
			AuthorizedInvoices.appendChild(From);
			From.appendChild(doc.createTextNode(resolucion.getRefInicioConsecutivo()));
			
			Element To = doc.createElement("sts:To");
			AuthorizedInvoices.appendChild(To);
			To.appendChild(doc.createTextNode(resolucion.getRefFinConsecutivo()));
			
			Element InvoicesSource = doc.createElement("sts:InvoiceSource");
			DianExtensions.appendChild(InvoicesSource);
			
			Element identificationCode = doc.createElement("cbc:IdentificationCode");
			InvoicesSource.appendChild(identificationCode);
			identificationCode.appendChild(doc.createTextNode("CO"));
			
			Attr IDCode = doc.createAttribute("listAgencyID");
			IDCode.setValue("6");
			identificationCode.setAttributeNode(IDCode);
			
			Attr listSchemeUri = doc.createAttribute("listSchemeURI");
			listSchemeUri.setValue("urn:oasis:names:specification:ubl:codelist:gc:CountryIdentificationCode-2.0");
			identificationCode.setAttributeNode(listSchemeUri);
			
			Attr listAgencyName = doc.createAttribute("listAgencyName");
			listAgencyName.setValue("United Nations Economic Commission for Europe");
			identificationCode.setAttributeNode(listAgencyName);
			
			Element SoftwareProvider = doc.createElement("sts:SoftwareProvider");
			DianExtensions.appendChild(SoftwareProvider);
			
			Element ProviderID = doc.createElement("sts:ProviderID");
			SoftwareProvider.appendChild(ProviderID);
			ProviderID.appendChild(doc.createTextNode(empresa.getEmpNit()));
			
			Attr ProvAgenciId = doc.createAttribute("schemeAgencyID");
			ProvAgenciId.setValue("195");
			ProviderID.setAttributeNode(ProvAgenciId);
			
			Attr ProvAgenciName = doc.createAttribute("schemeAgencyName");
			ProvAgenciName.setValue("CO, DIAN (Direccion de Impuestos y Aduanas Nacionales)");
			ProviderID.setAttributeNode(ProvAgenciName);
			
			Element SoftwareID = doc.createElement("sts:SoftwareID");
			SoftwareProvider.appendChild(SoftwareID);
			SoftwareID.appendChild(doc.createTextNode(resolucion.getRefSoftwareId()));  //+"8216fa1f-7894-4fb8-b415-e51b38647af7"));
			
			Attr ProvAgenciId1 = doc.createAttribute("schemeAgencyID");
			ProvAgenciId1.setValue("195");
			SoftwareID.setAttributeNode(ProvAgenciId1);
			
			Attr ProvAgenciName1 = doc.createAttribute("schemeAgencyName");
			ProvAgenciId1.setValue("CO, DIAN (Direccion de Impuestos y Aduanas Nacionales)");
			SoftwareID.setAttributeNode(ProvAgenciName1);
			
			Element SoftwareSecurityCode = doc.createElement("sts:SoftwareSecurityCode");
			DianExtensions.appendChild(SoftwareSecurityCode);
			SoftwareSecurityCode.appendChild(doc.createTextNode(resolucion.getRefSecurityCode())); //  "e4464719cbb96827359ae9349cb0c8033477a181b64ec2b0e000930a76037b645bac42f454a365e7c7de8add420345a0"));
			
			
			Element UblExtension1 = doc.createElement("ext:UBLExtension");
			UBLExtensions.appendChild(UblExtension1);
			
			Element UBLExtensioncontent = doc.createElement("ext:ExtensionContent");
			UblExtension1.appendChild(UBLExtensioncontent);
			
				
			
			//AQui inicia el contenido de la factura como tal
			
			Element UBLVersion = doc.createElement("cbc:UBLVersionID");
			rootElement.appendChild(UBLVersion);
			UBLVersion.appendChild(doc.createTextNode("UBL 2.0"));
			
			Element ProfileID = doc.createElement("cbc:ProfileID");
			rootElement.appendChild(ProfileID);
			ProfileID.appendChild(doc.createTextNode("DIAN 1.0"));
			
			Element ID = doc.createElement("cbc:ID");
			rootElement.appendChild(ID);
			ID.appendChild(doc.createTextNode(resolucion.getRefPrefijo()+String.valueOf(Integer.parseInt(factura.getFacNumFactura().trim(), 16 ))));
			
			Element UUID = doc.createElement("cbc:UUID");
			rootElement.appendChild(UUID);
			UUID.appendChild(doc.createTextNode(gen.generadorCufe(idFactura)));
			
			Attr IDUUid = doc.createAttribute("schemeAgencyID");
			IDUUid.setValue("195");
			UUID.setAttributeNode(IDUUid);
			
			Attr NameUUid = doc.createAttribute("schemeAgencyName");
			NameUUid.setValue("CO, DIAN (Direccion de Impuestos y Aduanas Nacionales)");
			UUID.setAttributeNode(NameUUid);
			
			Element IssueDate = doc.createElement("cbc:IssueDate");
			rootElement.appendChild(IssueDate);
			IssueDate.appendChild(doc.createTextNode(dateFormat.format( factura.getFacFechaFactura())));
			
			Element IssueTime = doc.createElement("cbc:IssueTime");
			rootElement.appendChild(IssueTime);
			IssueTime.appendChild(doc.createTextNode(hora.format( factura.getFacFechaFactura())));
			
			Element InvoiceTypeCode = doc.createElement("cbc:InvoiceTypeCode");
			rootElement.appendChild(InvoiceTypeCode);
			InvoiceTypeCode.appendChild(doc.createTextNode("1"));
			
			Element DocumentCurrencyCode = doc.createElement("cbc:DocumentCurrencyCode");
			rootElement.appendChild(DocumentCurrencyCode);
			DocumentCurrencyCode.appendChild(doc.createTextNode("COP"));
			
			// inicia el ACCOUNTING SUPPLIER PARTY O LOS DATOS DE LA EMPRESA FACTURADORA
			
			Element AccountingSupplierParty = doc.createElement("fe:AccountingSupplierParty");
			rootElement.appendChild(AccountingSupplierParty);
			
			Element AdditionalAccountID = doc.createElement("cbc:AdditionalAccountID");
			AccountingSupplierParty.appendChild(AdditionalAccountID);
			AdditionalAccountID.appendChild(doc.createTextNode("1"));
			
			Element Party = doc.createElement("fe:Party");
			AccountingSupplierParty.appendChild(Party);
			
			Element PartyIdentification = doc.createElement("cac:PartyIdentification");
			Party.appendChild(PartyIdentification);
			
			Element IDPartyIdentification = doc.createElement("cbc:ID");
			PartyIdentification.appendChild(IDPartyIdentification);
			IDPartyIdentification.appendChild(doc.createTextNode(empresa.getEmpNit()));
			
			Attr IDpi = doc.createAttribute("schemeAgencyID");
			IDpi.setValue("195");
			IDPartyIdentification.setAttributeNode(IDpi);
			
			Attr NamePi = doc.createAttribute("schemeAgencyName");
			NamePi.setValue("CO, DIAN (Direccion de Impuestos y Aduanas Nacionales)");
			IDPartyIdentification.setAttributeNode(NamePi);
			
			Attr SchemaId = doc.createAttribute("schemeID");
			SchemaId.setValue(String.valueOf(tipoDoc.getTidCodigo()));
			IDPartyIdentification.setAttributeNode(SchemaId);
			
			Element PartyName = doc.createElement("cac:PartyName");
			Party.appendChild(PartyName);
			
			Element NameP = doc.createElement("cbc:Name");
			PartyName.appendChild(NameP);
			NameP.appendChild(doc.createTextNode(empresa.getEmpDescripcion()));
			
			Element PhysicalLocation = doc.createElement("fe:PhysicalLocation");
			Party.appendChild(PhysicalLocation);
			
			Element Address = doc.createElement("fe:Address");
			PhysicalLocation.appendChild(Address);
			
			Element Department = doc.createElement("cbc:Department");
			Address.appendChild(Department);
			Department.appendChild(doc.createTextNode(departamento.getDepDescripcion()));
			
			Element CityName = doc.createElement("cbc:CityName");
			Address.appendChild(CityName);
			CityName.appendChild(doc.createTextNode(municipio.getMuniDescripcion()));
			
			Element AddresLine = doc.createElement("cac:AddressLine");
			Address.appendChild(AddresLine);
			
			Element Line = doc.createElement("cbc:Line");
			AddresLine.appendChild(Line);
			Line.appendChild(doc.createTextNode(empresa.getEmpDireccion()));
			
			Element Country = doc.createElement("cac:Country");
			Address.appendChild(Country);
			
			Element IdentificationCodeCountry = doc.createElement("cbc:IdentificationCode");
			Country.appendChild(IdentificationCodeCountry);
			IdentificationCodeCountry.appendChild(doc.createTextNode("CO"));
			
			Element PartyTaxSchema = doc.createElement("fe:PartyTaxScheme");
			Party.appendChild(PartyTaxSchema);
			
			Element TaxLevelCode = doc.createElement("cbc:TaxLevelCode");
			PartyTaxSchema.appendChild(TaxLevelCode);
			TaxLevelCode.appendChild(doc.createTextNode(String.valueOf(empresa.getempRegimen())));
			
			Element TaxScheme = doc.createElement("cac:TaxScheme");
			PartyTaxSchema.appendChild(TaxScheme);
			
			
			
			Element PartyLegalEntity = doc.createElement("fe:PartyLegalEntity");
			Party.appendChild(PartyLegalEntity);
			
			Element RegistrationName = doc.createElement("cbc:RegistrationName");
			PartyLegalEntity.appendChild(RegistrationName);
			RegistrationName.appendChild(doc.createTextNode(empresa.getEmpNit()+ " - "+ empresa.getEmpDescripcion()));
			
			
			// inicia el ACCOUNTING CUSTOMER PARTY O LOS DATOS DEL RECEPTRO DE LA FACTURA
			
			Element AccountingCustomerParty = doc.createElement("fe:AccountingCustomerParty");
			rootElement.appendChild(AccountingCustomerParty);
			
			Element AdditionalAccountIDCustomer = doc.createElement("cbc:AdditionalAccountID");
			AccountingCustomerParty.appendChild(AdditionalAccountIDCustomer);
			AdditionalAccountIDCustomer.appendChild(doc.createTextNode("1"));
			
			Element PartyCustomer = doc.createElement("fe:Party");
			AccountingCustomerParty.appendChild(PartyCustomer);
			
			Element PartyIdentificationCustomer = doc.createElement("cac:PartyIdentification");
			PartyCustomer.appendChild(PartyIdentificationCustomer);
			

			Element IDPartyIdentificationCustomer = doc.createElement("cbc:ID");
			PartyIdentificationCustomer.appendChild(IDPartyIdentificationCustomer);
			IDPartyIdentificationCustomer.appendChild(doc.createTextNode(cliente.getCliNumIdent()));
			
			Attr IDpic = doc.createAttribute("schemeAgencyID");
			IDpic.setValue("195");
			IDPartyIdentificationCustomer.setAttributeNode(IDpic);
			
			Attr NamePic = doc.createAttribute("schemeAgencyName");
			NamePic.setValue("CO, DIAN (Direccion de Impuestos y Aduanas Nacionales)");
			IDPartyIdentificationCustomer.setAttributeNode(NamePic);
			
			Attr SchemaIdc = doc.createAttribute("schemeID");
			SchemaIdc.setValue(String.valueOf(tipoDcoCliente.getTidCodigo()));
			IDPartyIdentificationCustomer.setAttributeNode(SchemaIdc);
			
			Element PartyNameCostumer = doc.createElement("cac:PartyName");
			PartyCustomer.appendChild(PartyNameCostumer);
			
			String nombreCliente = cliente.getCliNombre() + ' '+ cliente.getCliSegundoNombre() + ' '+ cliente.getCliApellidos();
			
			Element NamePc = doc.createElement("cbc:Name");
			PartyNameCostumer.appendChild(NamePc);
			NamePc.appendChild(doc.createTextNode(nombreCliente));
			
			Element PhysicalLocationCustomer = doc.createElement("fe:PhysicalLocation");
			PartyCustomer.appendChild(PhysicalLocationCustomer);
			
			Element AddressCostumer = doc.createElement("fe:Address");
			PhysicalLocationCustomer.appendChild(AddressCostumer);
			
			Element DepartmentCostumer = doc.createElement("cbc:Department");
			AddressCostumer.appendChild(DepartmentCostumer);
			DepartmentCostumer.appendChild(doc.createTextNode(departamentoCustomer.getDepDescripcion()));
			
			Element CityNameCustomer = doc.createElement("cbc:CityName");
			AddressCostumer.appendChild(CityNameCustomer);
			CityNameCustomer.appendChild(doc.createTextNode(municipio.getMuniDescripcion()));
			
			Element AddresLineCustomer = doc.createElement("cac:AddressLine");
			AddressCostumer.appendChild(AddresLineCustomer);
			
			Element LineCustomer = doc.createElement("cbc:Line");
			AddresLineCustomer.appendChild(LineCustomer);
			LineCustomer.appendChild(doc.createTextNode(cliente.getCliDireccion()));
			

			Element CountryCustomer = doc.createElement("cac:Country");
			AddressCostumer.appendChild(CountryCustomer);
			
			Element IdentificationCodeCountryCustomer = doc.createElement("cbc:IdentificationCode");
			CountryCustomer.appendChild(IdentificationCodeCountryCustomer);
			IdentificationCodeCountryCustomer.appendChild(doc.createTextNode("CO"));
			
			Element PartyTaxSchemaCustomer = doc.createElement("fe:PartyTaxScheme");
			PartyCustomer.appendChild(PartyTaxSchemaCustomer);
			
			Element TaxLevelCodeCustomer = doc.createElement("cbc:TaxLevelCode");
			PartyTaxSchemaCustomer.appendChild(TaxLevelCodeCustomer);
			TaxLevelCodeCustomer.appendChild(doc.createTextNode("E-02"));
			
			Element TaxScheme1 = doc.createElement("cac:TaxScheme");
			PartyTaxSchemaCustomer.appendChild(TaxScheme1);
			
			Element PartyLegalEntityCustomer = doc.createElement("fe:PartyLegalEntity");
			PartyCustomer.appendChild(PartyLegalEntityCustomer);
			
			Element RegistrationNameCustomer = doc.createElement("cbc:RegistrationName");
			PartyLegalEntityCustomer.appendChild(RegistrationNameCustomer);
			RegistrationNameCustomer.appendChild(doc.createTextNode(cliente.getCliNumIdent()+" - "+ cliente.getCliNombre()+ " " + cliente.getCliApellidos() ));
			
			// Termina el ACCOUNTING CUSTOMER PARTY O LOS DATOS DEL RECEPTRO DE LA FACTURA
			
			Element PaymentTerms = doc.createElement("cac:PaymentTerms");
			rootElement.appendChild(PaymentTerms);
			
			Element Note = doc.createElement("cbc:Note");
			PaymentTerms.appendChild(Note);
			Note.appendChild(doc.createTextNode("Pago a "+ convertidores.calcularDiferenciaFecha(factura) + " dias"));
			
			Element Amount = doc.createElement("cbc:Amount");
			PaymentTerms.appendChild(Amount);
			Amount.appendChild(doc.createTextNode(String.valueOf(factura.getFacTotal())));
			
			Attr attrCurrenciId = doc.createAttribute("currencyID");
			attrCurrenciId.setValue("COP");
			Amount.setAttributeNode(attrCurrenciId);
			
			Element SettlementPeriod = doc.createElement("cac:SettlementPeriod");
			PaymentTerms.appendChild(SettlementPeriod);
			
			Element StartDate = doc.createElement("cbc:StartDate");
			SettlementPeriod.appendChild(StartDate);
			StartDate.appendChild(doc.createTextNode(String.valueOf(dateFormat.format(factura.getFacFechaFactura()))));
			
			
			// hace falta formatear a solo fecha sin hora
			
			
			Element EndDate = doc.createElement("cbc:EndDate");
			SettlementPeriod.appendChild(EndDate);
			EndDate.appendChild(doc.createTextNode(String.valueOf(factura.getFacFecaPago())));
			
			Element TaxTotal = doc.createElement("fe:TaxTotal");
			rootElement.appendChild(TaxTotal);
			
			Element TaxAmount = doc.createElement("cbc:TaxAmount");
			TaxTotal.appendChild(TaxAmount);
			TaxAmount.appendChild(doc.createTextNode(String.valueOf(factura.getFacIva())));
			
			Attr currencyIdAttr = doc.createAttribute("currencyID");
			currencyIdAttr.setValue("COP");
			TaxAmount.setAttributeNode(currencyIdAttr);
			
			Element  TaxEvidencieIndicator = doc.createElement("cbc:TaxEvidenceIndicator");
			TaxTotal.appendChild(TaxEvidencieIndicator);
			TaxEvidencieIndicator.appendChild(doc.createTextNode("true"));
			
			Element taxSubtotal = doc.createElement("fe:TaxSubtotal");
			TaxTotal.appendChild(taxSubtotal);
			
			Element TaxableAmount = doc.createElement("cbc:TaxableAmount");
			taxSubtotal.appendChild(TaxableAmount);
			TaxableAmount.appendChild(doc.createTextNode(String.valueOf(factura.getFacSubTotal())));
			
			Attr tacurencyID = doc.createAttribute("currencyID");
			tacurencyID.setValue("COP");
			TaxableAmount.setAttributeNode(tacurencyID);
			
			Element TsTaxAmount = doc.createElement("cbc:TaxAmount");
			taxSubtotal.appendChild(TsTaxAmount);
			TsTaxAmount.appendChild(doc.createTextNode(String.valueOf(factura.getFacIva())));
			
			Attr taxaCurrencyID = doc.createAttribute("currencyID");
			taxaCurrencyID.setValue("COP");
			TsTaxAmount.setAttributeNode(taxaCurrencyID);
			
			Element Percent = doc.createElement("cbc:Percent");
			taxSubtotal.appendChild(Percent);
			Percent.appendChild(doc.createTextNode(String.valueOf( Double.parseDouble(gd.getParametroDominio("IVA").getPadValor())*100)));
			
			Element TaxCategory = doc.createElement("cac:TaxCategory");
			taxSubtotal.appendChild(TaxCategory);
			
			Element taxScheme = doc.createElement("cac:TaxScheme");
			TaxCategory.appendChild(taxScheme);
			
			Element TaxSchemeID = doc.createElement("cbc:ID");
			taxScheme.appendChild(TaxSchemeID);
			TaxSchemeID.appendChild(doc.createTextNode("01"));
			
			//inicia tax total 2
			

			Element TaxTotal1 = doc.createElement("fe:TaxTotal");
			rootElement.appendChild(TaxTotal1);
			
			Element TaxAmount1 = doc.createElement("cbc:TaxAmount");
			TaxTotal1.appendChild(TaxAmount1);
			TaxAmount1.appendChild(doc.createTextNode("0.00"));
			
			Attr currencyIdAttr1 = doc.createAttribute("currencyID");
			currencyIdAttr1.setValue("COP");
			TaxAmount1.setAttributeNode(currencyIdAttr1);
			
			Element  TaxEvidencieIndicator1 = doc.createElement("cbc:TaxEvidenceIndicator");
			TaxTotal1.appendChild(TaxEvidencieIndicator1);
			TaxEvidencieIndicator1.appendChild(doc.createTextNode("false"));
			
			Element taxSubtotal1 = doc.createElement("fe:TaxSubtotal");
			TaxTotal1.appendChild(taxSubtotal1);
			
			Element TaxableAmount1 = doc.createElement("cbc:TaxableAmount");
			taxSubtotal1.appendChild(TaxableAmount1);
			TaxableAmount1.appendChild(doc.createTextNode(String.valueOf(factura.getFacSubTotal())));
			
			Attr tacurencyID1 = doc.createAttribute("currencyID");
			tacurencyID1.setValue("COP");
			TaxableAmount1.setAttributeNode(tacurencyID1);
			
			Element TsTaxAmount1 = doc.createElement("cbc:TaxAmount");
			taxSubtotal1.appendChild(TsTaxAmount1);
			TsTaxAmount1.appendChild(doc.createTextNode("0.00"));
			
			Attr taxaCurrencyID1 = doc.createAttribute("currencyID");
			taxaCurrencyID1.setValue("COP");
			TsTaxAmount1.setAttributeNode(taxaCurrencyID1);
			
			Element Percent1 = doc.createElement("cbc:Percent");
			taxSubtotal1.appendChild(Percent1);
			Percent1.appendChild(doc.createTextNode("0.00"));
			
			Element TaxCategory1 = doc.createElement("cac:TaxCategory");
			taxSubtotal1.appendChild(TaxCategory1);
			
			Element taxScheme1 = doc.createElement("cac:TaxScheme");
			TaxCategory1.appendChild(taxScheme1);
			
			Element TaxSchemeID1 = doc.createElement("cbc:ID");
			taxScheme1.appendChild(TaxSchemeID1);
			TaxSchemeID1.appendChild(doc.createTextNode("02"));
			
			
			// finaliza tax total 2
			

			//inicia tax total 3
			

			Element TaxTotal2 = doc.createElement("fe:TaxTotal");
			rootElement.appendChild(TaxTotal2);
			
			Element TaxAmount2 = doc.createElement("cbc:TaxAmount");
			TaxTotal2.appendChild(TaxAmount2);
			TaxAmount2.appendChild(doc.createTextNode("0.00"));
			
			Attr currencyIdAttr2 = doc.createAttribute("currencyID");
			currencyIdAttr2.setValue("COP");
			TaxAmount2.setAttributeNode(currencyIdAttr2);
			
			Element  TaxEvidencieIndicator2 = doc.createElement("cbc:TaxEvidenceIndicator");
			TaxTotal2.appendChild(TaxEvidencieIndicator2);
			TaxEvidencieIndicator2.appendChild(doc.createTextNode("false"));
			
			Element taxSubtotal2 = doc.createElement("fe:TaxSubtotal");
			TaxTotal2.appendChild(taxSubtotal2);
			
			Element TaxableAmount2 = doc.createElement("cbc:TaxableAmount");
			taxSubtotal2.appendChild(TaxableAmount2);
			TaxableAmount2.appendChild(doc.createTextNode(String.valueOf(factura.getFacSubTotal())));
			
			Attr tacurencyID2 = doc.createAttribute("currencyID");
			tacurencyID2.setValue("COP");
			TaxableAmount2.setAttributeNode(tacurencyID2);
			
			Element TsTaxAmount2 = doc.createElement("cbc:TaxAmount");
			taxSubtotal2.appendChild(TsTaxAmount2);
			TsTaxAmount2.appendChild(doc.createTextNode("0.00"));
			
			Attr taxaCurrencyID2 = doc.createAttribute("currencyID");
			taxaCurrencyID2.setValue("COP");
			TsTaxAmount2.setAttributeNode(taxaCurrencyID2);
			
			Element Percent2 = doc.createElement("cbc:Percent");
			taxSubtotal2.appendChild(Percent2);
			Percent2.appendChild(doc.createTextNode("0.00"));
			
			Element TaxCategory2 = doc.createElement("cac:TaxCategory");
			taxSubtotal2.appendChild(TaxCategory2);
			
			Element taxScheme2 = doc.createElement("cac:TaxScheme");
			TaxCategory2.appendChild(taxScheme2);
			
			Element TaxSchemeID2 = doc.createElement("cbc:ID");
			taxScheme2.appendChild(TaxSchemeID2);
			TaxSchemeID2.appendChild(doc.createTextNode("03"));
			
			
			// finaliza tax total 3
			
			
			Element LegalMonetaryTotal = doc.createElement("fe:LegalMonetaryTotal");
			rootElement.appendChild(LegalMonetaryTotal);
			
			Element LineExtensionAmount = doc.createElement("cbc:LineExtensionAmount");
			LegalMonetaryTotal.appendChild(LineExtensionAmount);
			LineExtensionAmount.appendChild(doc.createTextNode(String.valueOf(factura.getFacSubTotal())));
			
			Attr taxaCurrencyID10 = doc.createAttribute("currencyID");
			taxaCurrencyID10.setValue("COP");
			LineExtensionAmount.setAttributeNode(taxaCurrencyID10);
			
			Element TaxExclusiveAmount = doc.createElement("cbc:TaxExclusiveAmount");
			LegalMonetaryTotal.appendChild(TaxExclusiveAmount);
			TaxExclusiveAmount.appendChild(doc.createTextNode(String.valueOf(factura.getFacIva())));
			
			Attr taxaCurrencyID20 = doc.createAttribute("currencyID");
			taxaCurrencyID20.setValue("COP");
			TaxExclusiveAmount.setAttributeNode(taxaCurrencyID20);
			
			Element TaxInclusiveAmount = doc.createElement("cbc:TaxInclusiveAmount");
			LegalMonetaryTotal.appendChild(TaxInclusiveAmount);
			TaxInclusiveAmount.appendChild(doc.createTextNode(String.valueOf(factura.getFacTotal())));
			
			Attr taxaCurrencyID3 = doc.createAttribute("currencyID");
			taxaCurrencyID3.setValue("COP");
			TaxInclusiveAmount.setAttributeNode(taxaCurrencyID3);
			
			Element PayableAmount = doc.createElement("cbc:PayableAmount");
			LegalMonetaryTotal.appendChild(PayableAmount);
			PayableAmount.appendChild(doc.createTextNode(String.valueOf(factura.getFacTotal())));
			
			Attr taxaCurrencyID4 = doc.createAttribute("currencyID");
			taxaCurrencyID4.setValue("COP");
			PayableAmount.setAttributeNode(taxaCurrencyID4);
			
			
			
			
			int contador =0;
			for(FacturaDetalle fac :facturaDetalle) {
				contador++;
				Element InvoiceLine = doc.createElement("fe:InvoiceLine");
				rootElement.appendChild(InvoiceLine);
				
				Element InvoiceLineID = doc.createElement("cbc:ID");
				InvoiceLine.appendChild(InvoiceLineID);
				InvoiceLineID.appendChild(doc.createTextNode(String.valueOf(contador)));
				
				Element InvoiceQuantity = doc.createElement("cbc:InvoicedQuantity");
				InvoiceLine.appendChild(InvoiceQuantity);
				InvoiceQuantity.appendChild(doc.createTextNode(String.valueOf(fac.getFadCantidad())));
				
				Element LineExtensionAmountLine = doc.createElement("cbc:LineExtensionAmount");
				InvoiceLine.appendChild(LineExtensionAmountLine);
				LineExtensionAmountLine.appendChild(doc.createTextNode(String.valueOf(fac.getFadSubtotal())));
				
				Attr CurrencyInvoiceLine = doc.createAttribute("currencyID");
				CurrencyInvoiceLine.setValue("COP");
				LineExtensionAmountLine.setAttributeNode(CurrencyInvoiceLine);
				
				Element Item = doc.createElement("fe:Item");
				InvoiceLine.appendChild(Item);
				
				String descripcion = factura.getFacNumFactura() + " " + gen.generadorInvoiceLineDescription(fac.getPvmId()) ; 
				
				Element Description = doc.createElement("cbc:Description");
				Item.appendChild(Description);
				Description.appendChild(doc.createTextNode(descripcion));
				
				Element Price = doc.createElement("fe:Price");
				InvoiceLine.appendChild(Price);
				
				Element PriceAmount = doc.createElement("cbc:PriceAmount");
				Price.appendChild(PriceAmount);
				PriceAmount.appendChild(doc.createTextNode(String.valueOf(fac.getFadValorUnitario())));
				
				Attr atrCurr = doc.createAttribute("currencyID");
				atrCurr.setValue("COP");
				PriceAmount.setAttributeNode(atrCurr);
				
				
				
						}
			
			//// desde aqui o hasta aqui
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
			String nombreArchivo = gen.generadorNombreArchivo("factura", factura.getFacId());
			String path = gd.getParametroDominio("PATH").getPadValor();
			new File(path + "" + nombreArchivo).mkdirs();
			System.out.println("El nombre del archivo es:::::" + nombreArchivo);
			StreamResult result = new StreamResult(new File(path + nombreArchivo + "\\" + nombreArchivo + "1.xml"));
			transformer.transform(source, result);
			
			String origenPath = path + gen.generadorNombreArchivo("factura", factura.getFacId())+"\\\\"+ gen.generadorNombreArchivo("factura", factura.getFacId());
			sign.signEpes(origenPath);
			
				
			
			CreateFile cf = new CreateFile();
			
			
			//Ejecutor ejec = new Ejecutor();
			cf.create(factura.getFacId(), "factura");
			//ejec.Zipper(factura.getFacId(), "factura");
			
			
			
			
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
