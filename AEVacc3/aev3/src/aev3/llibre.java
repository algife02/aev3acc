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


public class llibre {
	
		public static ArrayList<Llibre> lista = new ArrayList<Llibre>();
		static Scanner teclat = new Scanner(System.in);
		
		public static class Llibre 
		{
			
			private String id, titol, autor, any, editorial, numPag;
			
			public Llibre(String id, String titol, String autor, String any, String editorial, String numPag) {
				this.id = id;
				this.titol = titol;
				this.autor = autor;
				this.any = any;
				this.editorial = editorial;
				this.numPag = numPag;
				
				
			}
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			
			public String getTitol() {
				return titol;
			}
			public String setTitol(String titol) {
				return this.titol = titol;
			}
			public String getAutor() {
				return autor;
			}
			public String setAutor(String autor) {
				 return this.autor = autor;
			}
		
			public String getAny() {
				return any;
			}
			public String setAny(String any) {
				return this.any = any;
			}
			
			public String setEditorial(String editorial) {
				return this.editorial = editorial;
			}
			public String getEditorial() {
				return editorial;
			}
			public String getNumPag() {
				return numPag;
			}
			public String setNumPag(String numPag) {
				return this.numPag = numPag;
			}
			
		}

		public static int recuperarLlibre(int identif) 
		{
		ArrayList<Llibre> lista = recuperarTots(); 	
		
		for (Llibre llib : lista) {
			if(Integer.parseInt(llib.getId()) == identif) {
				System.out.println("id : " + llib.getId());
				System.out.println("Titulo : " + llib.getTitol());
			}
		}
		return identif;
		}

		public static void mostrarLlibre(Llibre llista) {
			
			System.out.println("id : " + llista.getId());
			System.out.println("Titulo : " + llista.getTitol());
			System.out.println("autor : " + llista.getAutor());
			System.out.println("any : " + llista.getAny());
			System.out.println("editorial : " + llista.getEditorial());
			System.out.println("numPag : " + llista.getNumPag());
			
		}
		
		public static ArrayList<Llibre> recuperarTots() {
			
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document document = dBuilder.parse(new File("libros.xml"));
				Element princ = document.getDocumentElement();
				NodeList nodeList = document.getElementsByTagName("libro");
				for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				System.out.println("");
				if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				System.out.println("");
				
				String id = eElement.getAttribute("id");
				String titol = eElement.getElementsByTagName("titol").item(0).getTextContent();
				String autor = eElement.getElementsByTagName("autor").item(0).getTextContent();
				String any = eElement.getElementsByTagName("any").item(0).getTextContent();
				String editorial = eElement.getElementsByTagName("editorial").item(0).getTextContent();
				String numPag =  eElement.getElementsByTagName("numPag").item(0).getTextContent();
				
				Llibre lib = new Llibre(id,titol,autor,any,editorial,numPag);
				lista.add(lib);
				}
				}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			return lista;
			}
	
		public static void writeXMLfile(ArrayList<Llibre> lista) {
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				
				Document document1 = dBuilder.newDocument();
				Element raiz1 = document1.createElement("libros");
				document1.appendChild(raiz1);
				
				for(Llibre Llib : lista) {
					Element llibre = document1.createElement("libro");
					String id = String.valueOf(Llib.getId());
					llibre.setAttribute("id", id);
					raiz1.appendChild(llibre);
					
					Element autor = document1.createElement("autor");
					autor.appendChild(document1.createTextNode(String.valueOf(Llib.getAutor())));
					llibre.appendChild(autor);
					
					Element titol = document1.createElement("titol");
					titol.appendChild(document1.createTextNode(String.valueOf(Llib.getTitol())));
					llibre.appendChild(titol);
					
					Element any = document1.createElement("any");
					any.appendChild(document1.createTextNode(String.valueOf(Llib.getAny())));
					llibre.appendChild(any);
					
					Element editorial = document1.createElement("editorial");
					editorial.appendChild(document1.createTextNode(String.valueOf(Llib.getEditorial())));
					llibre.appendChild(editorial);
					
					Element numPag = document1.createElement("numPag");
					numPag.appendChild(document1.createTextNode(String.valueOf(Llib.getNumPag())));
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
		
		public static int crearLlibre(Llibre llista) {
			
			ArrayList<Llibre> Llibres = recuperarTots();
			Llibres.add(llista);
			writeXMLfile(Llibres);
			return Integer.parseInt(llista.getId());
		}
		
		public static void actualizarLlibre (int id) {
			ArrayList<Llibre> Llibres = recuperarTots();
			System.out.println("Cambiar titulo");
			Llibres.get(id-1).titol = teclat.nextLine();
			
			System.out.println("Cambiar autor");
			Llibres.get(id-1).autor = teclat.nextLine();
			
			System.out.println("Cambiar any");
			Llibres.get(id-1).any = teclat.nextLine();
			
			System.out.println("Cambiar editorial");
			Llibres.get(id-1).editorial = teclat.nextLine();
			
			System.out.println("Cambiar Num pag");
			Llibres.get(id-1).numPag = teclat.nextLine();
			
			writeXMLfile(Llibres);
		}
		
		public static void borrarLlibre(int id) {
			
			ArrayList<Llibre> Llibres = recuperarTots();
			Llibres.remove(id-1);
			writeXMLfile(Llibres);
		}
	  
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			
			System.out.println("1- Mostrar tots els títols de la biblioteca");
			System.out.println("2- Mostrar informació detallada d’un llibre");
			System.out.println("3- Crear nou llibre");
			System.out.println("4- Actualitzar llibre");
			System.out.println("5- Borrar llibre");
			System.out.println("6- Tanca la biblioteca");
			
			int resp;
			resp = Integer.parseInt(teclat.nextLine());
			
			String id;
			String titol;
			String autor;
			String any;
			String editorial;
			String numPag;
			
			switch(resp) {
				case 1:
					ArrayList<Llibre> Llibres = recuperarTots();
					for(Llibre Llib : Llibres) {
					mostrarLlibre(Llib);
					}
				break;
				case 2:
					System.out.println("dime el id " );
					int ide = Integer.parseInt(teclat.nextLine());
					recuperarLlibre(ide);
				break;
				case 3:
					System.out.print("Introducir Id");
					id = teclat.nextLine();
					
					System.out.print("Introducir titol");
					titol = teclat.nextLine();
					
					System.out.print("Introducir autor");
					autor = teclat.nextLine();
					
					System.out.print("Introducir any publicacio");
					any = teclat.nextLine();
					
					System.out.print("Introducir editorial");
					editorial = teclat.nextLine();
					
					System.out.print("Introducir num pag");
					numPag = teclat.nextLine();
					
					Llibre librw = new Llibre(id,titol,autor,any,editorial,numPag);
					crearLlibre(librw);
				break;
				case 4:
					System.out.println("dime el id " );
					int ident = Integer.parseInt(teclat.nextLine());
					actualizarLlibre(ident);
				break;
				case 5:
					System.out.println("dime el id " );
					int identi = Integer.parseInt(teclat.nextLine());
					borrarLlibre(identi);
				break;
				case 6:
					System.out.println("cerrar biblioteca");
				break;
				
				default: System.out.print("No has introducido del 1 al 6");
				break;
			}
		}
}