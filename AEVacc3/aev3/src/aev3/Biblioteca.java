package aev3;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Scanner;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;




public class Biblioteca {
	
	public static ArrayList<llibre> lista = new ArrayList<llibre>();
	
	private String titol, autor, editorial;
	private int numPag, any;

	private static int id;
	public Biblioteca(int id, String titol, String autor, int any, String editorial, int numPag) 
	{
		this.id = id;
		this.titol = titol;
		this.autor = autor;
		this.any = any;
		this.editorial = editorial;
		this.numPag = numPag;
	}
	
	public String getTitol() {
		return titol;
	}
	public void setTitol(String titol) {
		this.titol = titol;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getEditorial() {
		return editorial;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	public int getNumPag() {
		return numPag;
		}
	public void setNumPag(int numPag) {
		this.numPag = numPag;
	}
	public int getAny() {
		return any;
	}
	public void setAny(int any) {
		this.any = any;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public static void writeXMLfile(ArrayList<llibre> Llibres) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			//Document document = dBuilder.parse(new File("libros.xml"));
	
			
			Document document1 = dBuilder.newDocument();
			Element raiz1 = document1.createElement("llibres");
			document1.appendChild(raiz1);
			
			for(aev3.llibre Llib : Llibres) {
				Element llibre = document1.createElement("llibre");
				String id = String.valueOf(Llib.getId());
				llibre.setAttribute(id, id);
				raiz1.appendChild(llibre);
				
				Element titol = document1.createElement("titol");
				titol.appendChild(document1.createTextNode(String.valueOf(Llib.getTitol())));
				llibre.appendChild(titol);
				
				Element any = document1.createElement("any");
				titol.appendChild(document1.createTextNode(String.valueOf(Llib.getAny())));
				llibre.appendChild(any);
				
				Element editorial = document1.createElement("editorial");
				titol.appendChild(document1.createTextNode(String.valueOf(Llib.getEditorial())));
				llibre.appendChild(editorial);
				
				Element numPag = document1.createElement("numPag");
				titol.appendChild(document1.createTextNode(String.valueOf(Llib.getNumPag())));
				llibre.appendChild(numPag);
			}
			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();

			
			
			aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1"); 
			aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(document1);
			
			try {
				FileWriter fw = new FileWriter("libros.xml");
				StreamResult result = new StreamResult(fw);
				aTransformer.transform(source, result);
				fw.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		} catch (TransformerException ex) {
			System.out.println("Error escribiendo el documento");

		}
		catch (ParserConfigurationException ex) {
			System.out.println("Error construyendo el documento");

		}
	}
	
	public static void mostrarLlibre(llibre most) {
		
		System.out.println("id : " + llibre.getId());
		System.out.println("Titulo : " + llibre.getTitol());
		System.out.println("autor : " + llibre.getAutor());
		System.out.println("any : " + llibre.getAny());
		System.out.println("editorial : " + llibre.getEditorial());
		System.out.println("numPag : " + llibre.getNumPag());
		
	}
	
	public static ArrayList<llibre> recuperarLlibre(llibre llibre) {
		ArrayList<llibre> Llibrs = RecuperarTots(); 	
		
		for (llibre llib : Llibrs) {
			if(lista.get(id).equals(llibre)) {
				System.out.println("id : " + llib.getId());
				System.out.println("Titulo : " + llib.getTitol());
			}
		}
		return lista;
	}
	
	public static ArrayList<llibre> RecuperarTots() {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File("libros.xml"));
			Element raiz = document.getDocumentElement();
			
			NodeList nodeList = document.getElementsByTagName("libro");
			for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			System.out.println("");
			if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) node;
			System.out.println("");
			
			
			System.out.println("id : " + eElement.getElementsByTagName("id").item(0).getTextContent());
			System.out.println("Titulo : " + eElement.getElementsByTagName("titulo").item(0).getTextContent());
			System.out.println("autor : " + eElement.getElementsByTagName("autor").item(0).getTextContent());
			System.out.println("any : " + eElement.getElementsByTagName("any").item(0).getTextContent());
			System.out.println("editorial : " + eElement.getElementsByTagName("editorial").item(0).getTextContent());
			System.out.println("numPag : " + eElement.getElementsByTagName("numPag").item(0).getTextContent());
			
			String titol = null;
			int id = 0;
			String autor = null;
			int any = 0;
			String editorial = null;
			int numPag = 0;
			llibre llib = new llibre(id,titol,autor,any,editorial,numPag);
			lista.add(llib);
			}
			}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		return lista;
		}
	
	
	public static void crearLlibre(llibre lista) {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("anyadir nuevo libro (s/n)");
		int idUltimo = 0;
		try {
			String respuesta = reader.readLine();
			while(respuesta.equals("s")) {
				System.out.print("Titol"); String titol = reader.readLine();
				System.out.print("autor"); String autor = reader.readLine();
				System.out.print("editorial"); String editorial = reader.readLine();
				System.out.print("numPag"); String numPag = reader.readLine();
				System.out.print("any"); String any = reader.readLine();
				System.out.print("id"); String id = reader.readLine();
				int n1 = Integer.parseInt(numPag);
				int n2 = Integer.parseInt(any);
				int n3 = Integer.parseInt(id);
				
				idUltimo++;
				lista = new llibre(n3,titol,autor,n2,editorial,n1);
				System.out.print("anyadir nuevo libro (s/n)");
				respuesta = reader.readLine();				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void borrarLlibre(int id) {
		
		ArrayList<llibre> Llibres = RecuperarTots();
		Llibres.remove(id-1);
		writeXMLfile(Llibres);
	}
	
	public static void actualizarLlibre (int id) {
		ListaLlibres llista = new ListaLlibres();
		
		Llibre llib;
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File("libros.xml"));
			Element raiz = document.getDocumentElement();
			
			NodeList nodeList = document.getElementsByTagName("libro");
			for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			System.out.println("");
			if (id.equalss(lista.getListaLlibres())) {
			Element eElement = (Element) node;
			System.out.println("");
			
			String titol = eElement.setAttribute(titol, titol);
			} 
			if (node.getNodeType()== Node.ELEMENT_NODE) {
				
				Element eElement = (Element) node;
				System.out.println("");
				
				String id1 = eElement.getAttribute("id");
				System.out.println("id : " + id1);
				
				String titol = eElement.getAttribute("titol");
				System.out.println("Titulo : " + titol);
				
				String autor = eElement.getAttribute("autor");
				System.out.println("autor : " + autor);
				
				String editorial = eElement.getAttribute("editorial");
				System.out.println("editorial : " + editorial);
				
				String any = eElement.getAttribute("any");
				System.out.println("any : " + any);
				
				
				String numPag = eElement.getAttribute("numPag");
				System.out.println("numPag : " + numPag);
			} 
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		llibre llibre = new llibre();
		
		Scanner teclat = new Scanner(System.in);
		int resp = 0;
		
		System.out.println("1- Mostrar tots els t?tols de la biblioteca");
		System.out.println("2- Mostrar informaci? detallada d?un llibre");
		System.out.println("3- Crear nou llibre");
		System.out.println("4- Actualitzar llibre");
		System.out.println("5- Borrar llibre");
		System.out.println("6- Tanca la biblioteca");
		
		resp= teclat.nextInt();
		
		String id;
		String titol;
		String autor;
		String any;
		String editorial;
		String numPag;
		
		switch(resp) {
			case 1:
				Biblioteca.mostrarLlibre(llibre);
			break;
			case 2:
				Biblioteca.recuperarLlibre(llibre);
			break;
			case 3:
				Biblioteca.crearLlibre(llibre);
			break;
			case 4:
				
			break;
			case 5:
				
			break;
			case 6:
				
			break;
			
			default: System.out.print("No has introducido del 1 al 6");
		}
	}

}
