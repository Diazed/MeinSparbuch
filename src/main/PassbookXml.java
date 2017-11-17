package main;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class PassbookXml {

  public String toXml(Passbook passbook){
    String xmlString = "";
    try {
      JAXBContext context = JAXBContext.newInstance(Passbook.class);
      Marshaller m = context.createMarshaller();
      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      StringWriter sw = new StringWriter();
      m.marshal(passbook, sw);
      xmlString = sw.toString();

    } catch (JAXBException e) {
      e.printStackTrace();
    }

    return xmlString;
  }

  public Passbook toPassbook(String xml){

    Passbook passbook = new Passbook();

    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(Passbook.class);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

      StringReader reader = new StringReader(xml);
      passbook = (Passbook) unmarshaller.unmarshal(reader);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return passbook;
  }
}
