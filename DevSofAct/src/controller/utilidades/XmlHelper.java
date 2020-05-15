package controller.utilidades;


import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlHelper
{
  public static Document getDocument(String path)
    throws Exception
  {
    File fXmlFile = new File(path);
    
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
  //  dbFactory.setNamespaceAware(true);
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(fXmlFile);
    doc.getDocumentElement().normalize();
    return doc;
  }
}
