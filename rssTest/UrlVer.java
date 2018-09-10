package rssTest;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UrlVer {

	public static void main(String[] args) {
        String path = "https://www.gizmodo.jp/index.xml";
        parseXML(path);
    }

	public static void parseXML(String path) {
        try {
            DocumentBuilderFactory  factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder         builder = factory.newDocumentBuilder();
            Document                document = builder.parse(path);
            Element                 root = document.getDocumentElement();

            /* Get and print Title of RSS Feed. */
            NodeList                channel = root.getElementsByTagName("channel");
            NodeList                title = ((Element)channel.item(0)).getElementsByTagName("title");
            System.out.println("\nTitle: " + title.item(0).getFirstChild().getNodeValue() + "\n");

            /* Get Node list of RSS items */
            NodeList                item_list = root.getElementsByTagName("item");
            for (int i = 0; i <item_list.getLength(); i++) {
                Element  element = (Element)item_list.item(i);
                NodeList item_title = element.getElementsByTagName("title");
                NodeList item_description  = element.getElementsByTagName("description");
                NodeList item_pubDate  = element.getElementsByTagName("pubDate");
                NodeList item_image  = element.getElementsByTagName("image");
                NodeList item_link  = element.getElementsByTagName("link");
                System.out.println(" title: " + item_title.item(0).getFirstChild().getNodeValue());
                System.out.println(" description: " + item_description.item(0).getFirstChild().getNodeValue());
                System.out.println(" pubDate: " + item_pubDate.item(0).getFirstChild().getNodeValue());
                System.out.println(" image: " + item_image.item(0).getFirstChild().getNodeValue());
                System.out.println(" link:  " + item_link.item(0).getFirstChild().getNodeValue() + "\n");
            }
        } catch (IOException e) {
            System.out.println("IO Exception");
        } catch (ParserConfigurationException e) {
            System.out.println("Parser Configuration Exception");
        } catch (SAXException e) {
            System.out.println("SAX Exception");
        }
        return;
    }

}


