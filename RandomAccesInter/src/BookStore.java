import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class BookStore {

	String PathDelBin = "./resources/biblio.bin";

	private static int TAM_COLUMNAS = 6;
	
	final static int TAM_ID = 4;
	final static int CARACTERES_TITULO = 40; // 20 Caracteres
	final static int TAM_TITULO = 80; // Que ocupan 40
	final static int TAM_FK_AUTOR = 4;
	final static int TAM_FK_EDITORIAL = 4; // 4 Para un int (4 Bytes)
	final static int TAM_ANIO = 4;
	final static int TAM_STOCK = 4;

	final static int TAM_RECORD = TAM_ID + TAM_TITULO + TAM_FK_AUTOR + TAM_FK_EDITORIAL + TAM_ANIO + TAM_STOCK;

	// Crear un método que cargue el fichero de texto de tipo CSV separados los
	// campos por puntos y coma y añadirlos al final de un fichero llamado de
	// acceso aleatorio BIBLIO.BIN
	public void AddCSVtoBIN(String PathDelCSV) throws IOException {				
		BufferedReader br = null; 		
		String cvsSplitBy = ";";
		int NumRegistrosInsertados = 0;
		try {			
			br = new BufferedReader(new FileReader(PathDelCSV));
			String line = null;
			Scanner scanner = null;
			int index = 0;
			while ((line = br.readLine()) != null) {
				Book book = new Book();
				scanner = new Scanner(line);
	            scanner.useDelimiter(cvsSplitBy);	            
	            while(scanner.hasNext()){
	            	String data = scanner.next();
	            	if (index == 0)
	            		book.setid(Integer.valueOf(data));
	                else if (index == 1)
	                	book.setTitulo(data);
	                else if (index == 2)
	                	book.setFk_Autor(Integer.valueOf(data));
	                else if (index == 3)
	                	book.setFk_Editorial(Integer.valueOf(data));
	                else if (index == 4)
	                	book.setAnio(Integer.valueOf(data));
	                else if (index == 5)
	                	book.setStock(Integer.valueOf(data));
	                else
	                    System.out.println("invalid data::" + data);
	            	index++;
	            }
	            index = 0;        
				if (AddBookToBIN(book)){
					NumRegistrosInsertados++;	
					System.out.print("INSERTADO CORRECTAMENTE -> ");
					book.ShowBook();
				}				
			}
			System.out.println(" - "+ NumRegistrosInsertados +" REGISTROS INSERTADOS.\n");		
		} finally {			
			if (br != null) {
				br.close();
			}
		}
	}	

	/**
	 * Realiza la busqueda de los libros ya sea por filtro o no
	 * @param nombreColumna
	 * @param busqueda
	 * @return
	 */
	public String[][] getBooks(String nombreColumna, String busqueda){
		String[][] bookdata = null;
		boolean esta = false;
		int cont=0;
		try {
			RandomAccessFile write = new RandomAccessFile(PathDelBin, "r");
			bookdata = new String[(int) (write.length()/TAM_RECORD)][TAM_COLUMNAS];
			do{
				String id = Integer.toString(write.readInt());
				String title = leerString(write);
				String autor = Integer.toString( write.readInt());
				String publi = Integer.toString(write.readInt());
				String year = Integer.toString(write.readInt());
				String stock = Integer.toString(write.readInt());
				if(nombreColumna != null){
					switch(nombreColumna){
					// Investigar
					case "ID": if(id.toLowerCase().indexOf(busqueda)!=-1) esta = true; break;
					case "TITLE": if(title.toLowerCase().indexOf(busqueda)!=-1) esta = true; break;
					case "AUTHOR": if(autor.toLowerCase().indexOf(busqueda)!=-1) esta = true; break;
					case "PUBLISHER": if(publi.toLowerCase().indexOf(busqueda)!=-1) esta = true; break;
					case "YEAR": if(year.toLowerCase().indexOf(busqueda)!=-1) esta = true; break;
					case "STOCK": if(stock.toLowerCase().indexOf(busqueda)!=-1) esta = true; break;
					}
				}else esta = true;
				if(esta){
					bookdata[cont][0]=id;
					bookdata[cont][1]=title;
					bookdata[cont][2]=autor;
					bookdata[cont][3]=publi;
					bookdata[cont][4]=year;
					bookdata[cont][5]=stock;
					cont++;
					esta = false;
				}
			}while(write.getFilePointer() < write.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bookdata;
	}
	


	private String leerString(RandomAccessFile streamIn) throws IOException {
		char aux;
		char[] charString = new char[TAM_TITULO/2];
		String st;
		for (int i = 0; i < TAM_TITULO/2; i++) {
			aux = streamIn.readChar();
			if ((int) aux != 0)
				charString[i] = aux;
			else
				charString[i] = ' ';
			}
		st = new String(charString);
		return st;
	}
	
	
	
	// Crear un método que reciba un objeto Book y lo añada al fichero
	// “BIBLIO.BIN”, devolverá true si se realiza de forma exitosa y false en
	// caso contrario.
	public Boolean AddBookToBIN(Book book) throws IOException {
		boolean guardado = true;
		RandomAccessFile streamOut = null;
		StringBuilder sb = new StringBuilder(book.getTitulo());
		sb.setLength(CARACTERES_TITULO);
		try {
			streamOut = new RandomAccessFile(PathDelBin, "rw");
			// Busco donde he de añadirlo sabiendo el tamaño de cada registro
			// libro y el id de este.
			int position = (book.getId() - 1) * TAM_RECORD;
			streamOut.seek(position);
			// Añado el Libro
			streamOut.writeInt(book.getId());
			streamOut.writeChars(sb.toString());
			streamOut.writeInt(book.getFk_Autor());
			streamOut.writeInt(book.getFk_Editorial());
			streamOut.writeInt(book.getAnio());
			streamOut.writeInt(book.getStock());
			guardado = true;
		} catch (IOException e) {
			e.printStackTrace();
			guardado = false;
		}
		return guardado;
	}
	

	public String leerTitulo(RandomAccessFile streamIn) throws IOException {
		char aux;
		char[] aCharTitulo = new char[CARACTERES_TITULO];
		String sTitulo;
		for (int i = 0; i < CARACTERES_TITULO; i++) {
			aux = streamIn.readChar(); // Recorremos uno a uno los caracteres del titulo
			if ((int) aux != 0) {
				aCharTitulo[i] = aux;
			} else {
				aCharTitulo[i] = ' ';
			}
		}
		sTitulo = new String(aCharTitulo);
		return sTitulo;
	}

	// Crear un método que reciba un id y devuelva un objeto Book del fichero “BIBLIO.BIN”
	public Book getBookById(int Id) {
		Book book = null;
		RandomAccessFile streamIn = null;
		try {
			streamIn = new RandomAccessFile(PathDelBin, "r");
			int position = (Id - 1) * TAM_RECORD;
			if (position < streamIn.length()) {
				streamIn.seek(position);
				// Recupero el libro
				int id = streamIn.readInt();
				String titulo = leerTitulo(streamIn);
				int Fk_Autor = streamIn.readInt();
				int Fk_Editorial = streamIn.readInt();
				int Anio = streamIn.readInt();
				int Stock = streamIn.readInt();
				book = new Book(id, titulo, Fk_Autor, Fk_Editorial, Anio, Stock);
			}
		} catch (FileNotFoundException e) {		
		} catch (IOException e) {			
		} finally {			
			if (streamIn != null)
				try {
					streamIn.close();					
				} catch (IOException e) {					
				}
		}
		return book;
	}

	// Crear un método que reciba un id de un libro y lo elimine del fichero
	// "BIBLIO.BIN" , devolverá true si se realiza de forma exitosa y false en
	// caso contrario.
	public Boolean DeleteBookFromBin(int Id) {
		boolean borrado = false;
		RandomAccessFile streamOut = null;
		Book book = new Book();
		StringBuilder sb = new StringBuilder(book.getTitulo());
		sb.setLength(CARACTERES_TITULO);
		try {
			streamOut = new RandomAccessFile(PathDelBin, "rw");
			int position = (Id - 1) * TAM_RECORD;
			if (position < streamOut.length()) {
				streamOut.seek(position);
				// Actualizo el Libro a uno que representa BORRADO
				streamOut.writeInt(book.getId());
				streamOut.writeChars(sb.toString());
				streamOut.writeInt(book.getFk_Autor());
				streamOut.writeInt(book.getFk_Editorial());
				streamOut.writeInt(book.getAnio());
				streamOut.writeInt(book.getStock());
				borrado = true;
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			if (streamOut != null)
				try {
					streamOut.close();
				} catch (IOException e) {
					borrado = false;
				}
		}
		return borrado;
	}

	// Crear un método que devuelva en un ArrayList todos los libros de un autor dado.
	public ArrayList<Book> getBooksByAuthor(int Autor) {
		ArrayList<Book> ListaDeLibros = new ArrayList<Book>();
		Book book = null;
		RandomAccessFile streamIn = null;
		int Id = 1;
		int position = (Id - 1) * TAM_RECORD;
		try {
			streamIn = new RandomAccessFile(PathDelBin, "r");			
			do {
				streamIn.seek(position);
				// Recupero el libro
				int id = streamIn.readInt();
				String titulo = leerTitulo(streamIn);
				int Fk_Autor = streamIn.readInt();
				if (Fk_Autor == Autor) {
					int Fk_Editorial = streamIn.readInt();
					int Anio = streamIn.readInt();
					int Stock = streamIn.readInt();
					book = new Book(id, titulo, Fk_Autor, Fk_Editorial, Anio,Stock);
					book.ShowBook();
					ListaDeLibros.add(book);
				}
				Id++;
				position = (Id - 1) * TAM_RECORD;
			} while (position < streamIn.length());

		} catch (FileNotFoundException e) {
			ListaDeLibros = null;
		} catch (IOException e) {
			ListaDeLibros = null;
		} finally {
			if (streamIn != null)
				try {
					streamIn.close();
					ListaDeLibros = null;
				} catch (IOException e) {
					ListaDeLibros = null;
				}
		}
		return ListaDeLibros;
	}

	// Crear un método que devuelva en un ArrayList todos los libros de una
	// editorial dada.
	public ArrayList<Book> getBooksByEditorial(int Editorial) {
		ArrayList<Book> ListaDeLibros = new ArrayList<Book>();
		Book book = null;
		RandomAccessFile streamIn = null;
		int Id = 1;
		int position = (Id - 1) * TAM_RECORD;
		try {
			streamIn = new RandomAccessFile(PathDelBin, "r");			
			do {
				streamIn.seek(position);
				// Recupero el libro
				int id = streamIn.readInt();
				String titulo = leerTitulo(streamIn);
				int Fk_Autor = streamIn.readInt();
				int Fk_Editorial = streamIn.readInt();
				if (Fk_Editorial == Editorial) {
					int Anio = streamIn.readInt();
					int Stock = streamIn.readInt();
					book = new Book(id, titulo, Fk_Autor, Fk_Editorial, Anio,Stock);
					book.ShowBook();
					ListaDeLibros.add(book);
				}
				Id++;
				position = (Id - 1) * TAM_RECORD;
			} while (position < streamIn.length());

		} catch (FileNotFoundException e) {
			ListaDeLibros = null;
		} catch (IOException e) {
			ListaDeLibros = null;
		} finally {
			if (streamIn != null)
				try {
					streamIn.close();
					ListaDeLibros = null;
				} catch (IOException e) {
					ListaDeLibros = null;
				}
		}
		return ListaDeLibros;
	}

	// Crear un método que imprima todo el contenido del fichero “BIBLIO.BIN”
	public void ShowAll() {
		RandomAccessFile streamIn = null;
		try {
			streamIn = new RandomAccessFile(PathDelBin, "r");
			try {
				do {
					int id = streamIn.readInt();
					System.out.println("Id: " + id );
					String titulo = leerTitulo(streamIn);
					System.out.println("Titulo: " + titulo);
					int Fk_Autor = streamIn.readInt();
					System.out.println("Fk_Autor: " + Fk_Autor);
					int Fk_Editorial = streamIn.readInt();
					System.out.println("Fk_Editorial: " + Fk_Editorial);
					int Anio = streamIn.readInt();
					System.out.println("Anio: " + Anio);
					int Stock = streamIn.readInt();
					System.out.println("Stock: " + Stock);
					System.out.println("");
				} while (streamIn.getFilePointer() <= streamIn.length());
			} catch (EOFException e) {
			}
		} catch (FileNotFoundException e) {
			System.err.println("Fichero no encontrado " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Error E/S " + e.getMessage());
		} finally {
			if (streamIn != null) {
				try {
					streamIn.close();
				} catch (IOException e) {
					System.out.println("Error E/S " + e.getMessage());
				}
			}
		}
	}
}