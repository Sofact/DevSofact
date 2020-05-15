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
import controller.utilidades.Ejecutor;
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

public class NotaCreditoXMLGenerator {

	public void generador(int idFactura) {
		
		GetData gd= new GetData();
		Factura factura;
		Factura facturaNota;
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
		facturaNota = gd.getFacturaNumeroByFNum(factura.getFacNumFactura());
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
			
			

			Element rootElement = doc.createElement("fe:CreditNote");
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
			
				
			
			//Aqui inicia el contenido de la Nota credito como tal
			
			
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
			
			/*
			Element InvoiceTypeCode = doc.createElement("cbc:InvoiceTypeCode");
			rootElement.appendChild(InvoiceTypeCode);
			InvoiceTypeCode.appendChild(doc.createTextNode("1"));
			*/
			
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
			RegistrationName.appendChild(doc.createTextNode(empresa.getEmpNit()));
			
			
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
			RegistrationNameCustomer.appendChild(doc.createTextNode(cliente.getCliNumIdent()));
			
			// Termina el ACCOUNTING CUSTOMER PARTY O LOS DATOS DEL RECEPTRO DE LA FACTURA
			
			Element LegalMonetaryTotal = doc.createElement("fe:LegalMonetaryTotal");
			rootElement.appendChild(LegalMonetaryTotal);
			
			Element LineExtensionAmount = doc.createElement("cbc:LineExtensionAmount");
			LegalMonetaryTotal.appendChild(LineExtensionAmount);
			LineExtensionAmount.appendChild(doc.createTextNode(String.valueOf(factura.getFacSubTotal())));
			
			Attr taxaCurrencyID1 = doc.createAttribute("currencyID");
			taxaCurrencyID1.setValue("COP");
			LineExtensionAmount.setAttributeNode(taxaCurrencyID1);
			
			Element TaxExclusiveAmount = doc.createElement("cbc:TaxExclusiveAmount");
			LegalMonetaryTotal.appendChild(TaxExclusiveAmount);
			TaxExclusiveAmount.appendChild(doc.createTextNode(String.valueOf(factura.getFacIva())));
			
			Attr taxaCurrencyID2 = doc.createAttribute("currencyID");
			taxaCurrencyID2.setValue("COP");
			TaxExclusiveAmount.setAttributeNode(taxaCurrencyID2);
						
			Element PayableAmount = doc.createElement("cbc:PayableAmount");
			LegalMonetaryTotal.appendChild(PayableAmount);
			PayableAmount.appendChild(doc.createTextNode(String.valueOf(factura.getFacTotal())));
			
			Attr taxaCurrencyID4 = doc.createAttribute("currencyID");
			taxaCurrencyID4.setValue("COP");
			PayableAmount.setAttributeNode(taxaCurrencyID4);
			
			// credit note lines
			
			
				Element CreditNoteLine = doc.createElement("cac:CreditNoteLine");
				rootElement.appendChild(CreditNoteLine);
				
				Element CreditNoteLineID = doc.createElement("cbc:ID");
				CreditNoteLine.appendChild(CreditNoteLineID);
				CreditNoteLineID.appendChild(doc.createTextNode("1"));
				
				Element UUIDNoteLineID = doc.createElement("cbc:UUID");
				CreditNoteLine.appendChild(UUIDNoteLineID);
				UUIDNoteLineID.appendChild(doc.createTextNode(gen.generadorCufe(facturaNota.getFacId())));
				
				Element LineExtensionAmountLine = doc.createElement("cbc:LineExtensionAmount");
				CreditNoteLine.appendChild(LineExtensionAmountLine);
				LineExtensionAmountLine.appendChild(doc.createTextNode(String.valueOf(factura.getFacSubTotal())));
				
				Attr CurrencyInvoiceLine = doc.createAttribute("currencyID");
				CurrencyInvoiceLine.setValue("COP");
				LineExtensionAmountLine.setAttributeNode(CurrencyInvoiceLine);
				
				Element Item = doc.createElement("cac:Item");
				CreditNoteLine.appendChild(Item);
				
				//String descripcion = factura.getFacNumFactura() + " " + gen.generadorInvoiceLineDescription(fac.getPvmId()) ; 
				
				Element Description = doc.createElement("cbc:Description");
				Item.appendChild(Description);
				Description.appendChild(doc.createTextNode(factura.getFacObservacion()));
				
							
			
			
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			String nombreArchivo = gen.generadorNombreArchivo("credito", factura.getFacId());
			String path = gd.getParametroDominio("PATH").getPadValor();
			new File(path + "" + nombreArchivo).mkdirs();
			StreamResult result = new StreamResult(new File(path + nombreArchivo + "\\" + nombreArchivo + "1.xml"));
			transformer.transform(source, result);
			
			String origenPath = path + gen.generadorNombreArchivo("credito", factura.getFacId())+"\\\\"+ gen.generadorNombreArchivo("credito", factura.getFacId());
			sign.signEpes(origenPath);
			
			System.out.println("TErmino con la firma y sigue al zipper");
			
			CreateFile cf = new CreateFile();
			
			
			Ejecutor ejec = new Ejecutor();
			cf.create(factura.getFacId(), "credito");
			
			
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
