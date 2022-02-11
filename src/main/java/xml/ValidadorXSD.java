package xml;

import org.xml.sax.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;



public class ValidadorXSD {

    private String PATH_XML = "";
    private String PATH_XSD = "";

    private ValidadorXSD(){};

    public ValidadorXSD(String xml, String xsd) {
        PATH_XML = Thread.currentThread().getContextClassLoader().getResource(xml).getPath();
        PATH_XSD = Thread.currentThread().getContextClassLoader().getResource(xsd).getPath();
    }

    public boolean validar() throws IOException
    {
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
                        public void warning(SAXParseException e) {
                            System.out.println("WARNING: " + e.getMessage());
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