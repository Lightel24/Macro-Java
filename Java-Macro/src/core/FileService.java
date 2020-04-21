package core;

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
					Macro macro = new Macro();
					
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
								
								switch(type){
								case"KEY":
									break;
								case"MOUSE":
									break;
								
								default:
									System.err.println("Action inconnue: " + type);
									break;
								}
								
								
								

							}else {
								System.err.println("Noeud inconnu: " + actionNode.getNodeName() +"	ignoré");
							}
						}
					}
					
				}
				
			}
		}
	}

	/*
	 * https://openclassrooms.com/fr/courses/2654406-java-et-le-xml/2685791-rappels-sur-le-xml
	 * */
	 public static String description(Node n, String tab){
	      String str = new String();
	      //Nous nous assurons que le nœud passé en paramètre est une instance d'Element
	      //juste au cas où il s'agisse d'un texte ou d'un espace, etc.
	      if(n instanceof Element){
	         
	         //Nous sommes donc bien sur un élément de notre document
	         //Nous castons l'objet de type Node en type Element
	         Element element = (Element)n;
	         
	         //Nous pouvons récupérer le nom du nœud actuellement parcouru 
	         //grâce à cette méthode, nous ouvrons donc notre balise
	         str += "<" + n.getNodeName();
	         
	         //nous contrôlons la liste des attributs présents
	         if(n.getAttributes() != null && n.getAttributes().getLength() > 0){
	            
	            //nous pouvons récupérer la liste des attributs d'un élément
	            NamedNodeMap att = n.getAttributes();
	            int nbAtt = att.getLength();
	            
	            //nous parcourons tous les nœuds pour les afficher
	            for(int j = 0; j < nbAtt; j++){
	               Node noeud = att.item(j);
	               //On récupère le nom de l'attribut et sa valeur grâce à ces deux méthodes
	               str += " " + noeud.getNodeName() + "=\"" + noeud.getNodeValue() + "\" ";
	            }
	         }
	         
	         //nous refermons notre balise car nous avons traité les différents attributs
	         str += ">";
	         
	         //La méthode getChildNodes retournant le contenu du nœud + les nœuds enfants
	         //Nous récupérons le contenu texte uniquement lorsqu'il n'y a que du texte, donc un seul enfant
	         if(n.getChildNodes().getLength() == 1)
	              str += n.getTextContent();
	         
	         //Nous allons maintenant traiter les nœuds enfants du nœud en cours de traitement
	         int nbChild = n.getChildNodes().getLength();
	         //Nous récupérons la liste des nœuds enfants
	         NodeList list = n.getChildNodes();
	         String tab2 = tab + "\t";
	         
	         //nous parcourons la liste des nœuds
	         for(int i = 0; i < nbChild; i++){
	            Node n2 = list.item(i);
	            
	            //si le nœud enfant est un Element, nous le traitons
	            if (n2 instanceof Element){
	               //appel récursif à la méthode pour le traitement du nœud et de ses enfants 
	               str += "\n " + tab2 + description(n2, tab2);
	            }
	         }
	         
	         //Nous fermons maintenant la balise
	         if(n.getChildNodes().getLength() < 2)
	            str += "</" + n.getNodeName() + ">";
	         else
	            str += "\n" + tab +"</" + n.getNodeName() + ">";
	      }
	      
	      return str;
	   }  

	public boolean create(File file) {
		
		return false;
	}

}
