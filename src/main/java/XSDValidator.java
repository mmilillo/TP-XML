import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

import static org.graalvm.compiler.replacements.Log.print;

public class XSDValidator {

    private DocumentBuilder parser;
    private Document document;
    private SchemaFactory factory;
    private Source schemaFile;
    private Schema schema;
    private Validator validator;

    public XSDValidator(string pathXML, string pathXMLSchema) throws ParserConfigurationException, SAXException, IOException
    {
        // parse an XML document into a DOM tree
        parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        //Document document = parser.parse(new File("instance.xml"));
        document = parser.parse(new File(pathXML.ToString()));

        // create a SchemaFactory capable of understanding WXS schemas
        factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        // load a WXS schema, represented by a Schema instance
        //Source schemaFile = new StreamSource(new File("mySchema.xsd"));
        schemaFile = new StreamSource(new File(pathXMLSchema.ToString()));
        schema = factory.newSchema(schemaFile);

        // create a Validator instance, which can be used to validate an instance document
        validator = schema.newValidator();
    }

    public boolean Validar()
    {
        // validate the DOM tree
        try {
            validator.validate(new DOMSource(document));
        }
        catch (SAXException | IOException e)
        {
            // instance document is invalid!
            return false;
        }
        return true;
    }
}
