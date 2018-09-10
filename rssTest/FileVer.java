package rssTest;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FileVer {

    public static void main(String[] args) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("/Users/ryosuke/Desktop/Sample2.xml"));

            Element element = doc.getDocumentElement();
            NodeList NodeList1stLine = element.getChildNodes();

            // 要素の長さだけ繰り返す
            for ( int i = 0; i < NodeList1stLine.getLength(); i++ ) {
            	// nodeList内のi番目のブロックを返す
                Node node1stLine = NodeList1stLine.item(i);
                // そのブロックが要素（エレメント）だった場合で、かつitemなら
                if ( node1stLine.getNodeType() == Node.ELEMENT_NODE && node1stLine.getNodeName().equals("item") ) {

                	// そのnodeをさらに分解
                	NodeList nodeList2ndLine = node1stLine.getChildNodes();

                	// 要素の長さだけ繰り返す
                    for ( int j = 0; j < nodeList2ndLine.getLength(); j++ ) {
                    	// nodeList内のi番目のブロックを返す
                        Node node2ndLine = nodeList2ndLine.item(j);

                        // そのブロックが要素（エレメント）だった場合で、かつitemなら
                        if ( node2ndLine.getNodeType() == Node.ELEMENT_NODE && node2ndLine.getNodeName().equals("title") ) {
                        	System.out.println(node2ndLine.getNodeName() + ": " + node2ndLine.getTextContent());
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}