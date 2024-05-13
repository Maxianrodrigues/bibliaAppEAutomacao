package devandroid.muller.bibliasagrada.util;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class UtilExtraiTextoXml {

    public static String extraiTextoDoXml(String xmlDados){


        StringBuilder extraiTexto = new StringBuilder();
        //extraiTexto = new StringBuilder(xmlDados);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlDados));
            Log.i("XMLDADOS", "extraiTextoDoXml: " + is);
            Document document = builder.parse(is);
            org.w3c.dom.Element raiz = document.getDocumentElement();
            extrairTextoRecursivo(raiz, extraiTexto);
            //extraiTexto = new StringBuilder(document.getTextContent());

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao extrair texto do xml";
        }
        return extraiTexto.toString();
    }

    private static void extrairTextoRecursivo(Node node, StringBuilder extraiTexto) {
        if (node.getNodeType() == Node.TEXT_NODE) {
            extraiTexto.append(node.getNodeValue().trim()).append("\n");
        }else if (node.getNodeType() == Node.ELEMENT_NODE) {
            NodeList elementosFilhos = node.getChildNodes();
            for (int i = 0; i < elementosFilhos.getLength(); i++) {
                extrairTextoRecursivo(elementosFilhos.item(i),extraiTexto);
            }
        }
    }

   /* private static void extraindoTexto(Node node, StringBuilder extraiTexto) {
        // Adicionando logging para diagnóstico
        System.out.println("Node Name: " + node.getNodeName() + ", Node Type: " + node.getNodeType() + ", Value: " + node.getNodeValue());

        if (node.getNodeType() == Node.TEXT_NODE) {
            extraiTexto.append(node.getNodeValue()).append("\n");
        }else if (node.getNodeType() == Node.ELEMENT_NODE) {
            NodeList textos = node.getChildNodes();
            for (int i = 0; i < textos.getLength(); i++) {
                extraindoTexto(textos.item(i),extraiTexto);
            }
        }
    }*/
}
