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
		
		// RSSのURLを取得
        String path = "https://www.gizmodo.jp/index.xml";
        
        // parseXMLメソッドを使用して、RSSの内容をString型の多重配列に入れる
        // [記事の番号][内容]	記事の番号：0〜記事数分、内容：0→タイトル、1→説明、2→公開日、3→画像URL、4→URL
        String[][] itemList = parseXML(path);
    }

	public static String[][] parseXML(String path) {
        try {
        	// 事前準備
            DocumentBuilderFactory  factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder         builder = factory.newDocumentBuilder();
            Document                document = builder.parse(path);
            Element                 root = document.getDocumentElement();

            // メディア名を取得
            NodeList                channel = root.getElementsByTagName("channel");
            NodeList                title = ((Element)channel.item(0)).getElementsByTagName("title");
            System.out.println("\nTitle: " + title.item(0).getFirstChild().getNodeValue() + "\n");

            // ここから記事情報を取得する処理
            NodeList                item_list = root.getElementsByTagName("item");
            String[][]				item_list_str = new String[item_list.getLength()][5];
            
            // 記事数分繰り返す
            for (int i = 0; i <item_list.getLength(); i++) {
            	
            	// nodeからelementに再変換
                Element  element = (Element)item_list.item(i);
                
                // 各タグが冒頭についたnodelistを取得
                NodeList item_title = element.getElementsByTagName("title");
                NodeList item_description  = element.getElementsByTagName("description");
                NodeList item_pubDate  = element.getElementsByTagName("pubDate");
                NodeList item_image  = element.getElementsByTagName("image");
                NodeList item_link  = element.getElementsByTagName("link");
                
                // nodelistから値を取り出し、String配列に格納
                item_list_str[i][0] = item_title.item(0).getFirstChild().getNodeValue();
                item_list_str[i][1] = item_description.item(0).getFirstChild().getNodeValue();
                item_list_str[i][2] = item_pubDate.item(0).getFirstChild().getNodeValue();
                item_list_str[i][3] = item_image.item(0).getFirstChild().getNodeValue();
                item_list_str[i][4] = item_link.item(0).getFirstChild().getNodeValue();
            }
            return item_list_str;
            
        } catch (IOException e) {
            System.out.println("IO Exception");
        } catch (ParserConfigurationException e) {
            System.out.println("Parser Configuration Exception");
        } catch (SAXException e) {
            System.out.println("SAX Exception");
        }
        return null;
    }

}


