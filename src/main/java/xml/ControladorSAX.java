package xml;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ControladorSAX extends DefaultHandler
{
    int figuraNumero = 0;
    boolean isFugira = false;

    public ControladorSAX () {}

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //nombre del elemento, aca podes accedera los atributos
        if(qName.equalsIgnoreCase("figura")){
            figuraNumero++;
            System.out.print(figuraNumero + " - La figura del partido es ");
            isFugira = true;
        }
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //aca ya trabaja con el valor contenido en el elemnto figura, no con sus atributos
        if(isFugira){
            System.out.print(new String(ch, start, length) + "." + "\n");
            isFugira = false;
        }

    }


}
