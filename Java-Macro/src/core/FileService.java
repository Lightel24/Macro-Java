package core;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


public class FileService {

	public HashMap<String, Macro> loadnParse(File file) throws InvalidDataFile{
		HashMap<String, Macro> macros = new HashMap<String, Macro>();
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		if(!file.getName().contains(".") || !file.getName().split("\\.")[1].equals("xml")) {
			throw new InvalidDataFile("Le fichier doit etre un fichier xml!!");
		}
	    try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xml = builder.parse(file);
			
			Element racine = xml.getDocumentElement();
			
			XPathFactory xpf = XPathFactory.newInstance();
			XPath path = xpf.newXPath();
			
			Node config = (Node) path.evaluate("/racine/Config",racine, XPathConstants.NODE);
            System.out.println(config.getNodeName() + ":" + config.getAttributes().item(0).getNodeName() + "="+config.getAttributes().item(0).getTextContent());
            String version = config.getAttributes().item(0).getTextContent();
            
            switch(version) {
            case"1.0.0":
            	v100(racine,macros);
        	break;
        	default:
        		System.err.println("La version du fichier est inconnue, l'algorithme 1.0.0 est utilisé.");
        		v100(racine, macros);
        		break;
            }
            
            
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new InvalidDataFile("Lecture du fichier impossible " + e.getMessage());
		} catch (XPathExpressionException | java.lang.NullPointerException e) {
			throw new InvalidDataFile("Le fichier existe mais est invalide! " + e.getMessage());
		}
		
		return macros;
	}
	

	private void v100(Element racine, HashMap<String,Macro> macros) {
		//On peuple la HashMap
		NodeList macrosNode = racine.getChildNodes();
		for(int i =0;i<macrosNode.getLength();i++) {
			Node t = macrosNode.item(i);
			if(t instanceof Element) {
				if(t.getNodeName()=="Macro") {	//On traite les noeuds Macro
					Macro macro;
					
					NamedNodeMap att1 = t.getAttributes();
					String nom = att1.getNamedItem("name").getNodeValue();	
					System.out.println(nom);
														//Maintenant on traite les actions.
					NodeList actionNodes = t.getChildNodes();
					ArrayList<Action> actionsList = new ArrayList<Action>();

					for(int k =0;k<macrosNode.getLength();k++) {
						Node actionNode = actionNodes.item(k);
						if(actionNode instanceof Element) {
							
							if(actionNode.getNodeName()=="Action") {	//On récupère les infos de l'action.
								String type;
								NamedNodeMap att = actionNode.getAttributes();
								type = att.getNamedItem("type").getNodeValue();	
								
								if(type.contains("KEY")) { //Donnees du format: "type"="KEY_PRESSED"	"timestamp"="230"		"key"="130"
									String data = actionNode.getTextContent();
									type = getValue("type",data);
									long timestamp = 0;
									int key = 0;
									int rawCode = 0;
									
									try {
										 timestamp = Long.parseLong(getValue("timestamp",data));
										 key = Integer.parseInt(getValue("key",data));
										 rawCode = Integer.parseInt(getValue("rawCode",data));
									}catch(java.lang.NumberFormatException ex) {
										System.err.println("Le fichier contient des erreurs! Les valeurs sont mises a 0.");
									}
									
									actionsList.add(new KeyAction(timestamp, type, key,rawCode));
								}else if(type.contains("MOUSE")) {	//Donnees du format: "type"="MOUSE_MOVED"	"timestamp"="230"		"loc"="220,150"
									String data = actionNode.getTextContent();
									long timestamp = Long.parseLong(getValue("timestamp",data));
									try {
										 timestamp = Long.parseLong(getValue("timestamp",data));
									}catch(java.lang.NumberFormatException ex) {
										System.err.println("Le fichier contient des erreurs! Les valeurs sont mises a 0.");
									}
									type = getValue("type",data);
									String[] locVal = getValue("loc",data).split(",");
									
									actionsList.add(new MouseAction(timestamp, type, new Point(Integer.parseInt(locVal[0]),Integer.parseInt(locVal[1]))));
								}else {
									System.err.println("Action inconnue: " + type);
								}
							}else {
								System.err.println("Noeud inconnu: " + actionNode.getNodeName() +"	ignoré");
							}
							
						}
					}
					if(actionsList.size()>0) {
						 macro = new Macro(actionsList, nom);
						 macros.put(nom, macro);
					}
				}
				
			}
		}
	}
	
	/*
	 * extract a attribute value by its name, return null if not found
	 * */
	private String getValue(String attrib, String data) {
		int start = data.indexOf("\""+attrib+"\"") + attrib.length()+2;
		String value = null;
		if(start-attrib.length()-2>=0) {
			value = data.substring(data.indexOf('\"',start)+1,data.indexOf('\"',start+2));
			System.out.println(value);
		}
		return value;
	}

	public boolean create(File file) {
		
		return false;
	}

}
