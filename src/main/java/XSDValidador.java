import org.xml.sax.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;

// SAX
//SAX and external XSD

public class XSDValidador {

    private String PATH_XML = "";
    private String PATH_XSD = "";

    private XSDValidador(){};

    public XSDValidador(String xml, String xsd) {
        PATH_XML = xml;
        PATH_XSD = xsd;
    }

    // validate SAX and external XSD 
    public boolean Validar() throws ParserConfigurationException, IOException
    {
        String resulado = "";
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(true);

            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            SAXParser parser = null;
            try {
                factory.setSchema(schemaFactory.newSchema(new Source[] {new StreamSource( PATH_XSD )}));
                parser = factory.newSAXParser();
            }
            catch (SAXException se) {
                System.out.println("SCHEMA : " + se.getMessage());  // problem in the XSD itself
                return false;
            }

            XMLReader reader = parser.getXMLReader();
            reader.setErrorHandler(
                    new ErrorHandler() {
                        public void warning(SAXParseException e) throws SAXException {
                            System.out.println("WARNING: " + e.getMessage()); // do nothing
                        }

                        public void error(SAXParseException e) throws SAXException {
                            System.out.println("ERROR : " + e.getMessage());
                            throw e;
                        }

                        public void fatalError(SAXParseException e) throws SAXException {
                            System.out.println("FATAL : " + e.getMessage());
                            throw e;
                        }
                    }
            );
            reader.parse(new InputSource(PATH_XML));
            return true;
        }
        catch (ParserConfigurationException pce) {
            //throw pce;
            return false;
        }
        catch (IOException io) {
            throw io;
        }
        catch (SAXException se){
            return false;
        }
    }

}