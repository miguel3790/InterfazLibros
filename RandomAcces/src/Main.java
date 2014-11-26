import java.io.IOException;
import java.util.ArrayList;



public class Main {	
	
	public static void main(String[] args) throws IOException {
		
		final String PathDelCSV = "./resources/libros.csv";		
		BookStore Biblioteca = new BookStore();		
		Book book = new Book(1010, "El libro de prueba", 2000, 2000, 1990, 20);
		
		// Añado todos los libros del CSV al BIN
		Biblioteca.AddCSVtoBIN(PathDelCSV);
		Biblioteca.ShowAll();
		
		// Añado un libro de Id 1010 y lo listo
		Biblioteca.AddBookToBIN(book);
		Biblioteca.getBookById(1010).ShowBook();
		
		// Borro el libro de Id 1010 y lo listo (no deberia salir)
		Biblioteca.DeleteBookFromBin(1010);
		Biblioteca.getBookById(1010).ShowBook();
		
		// Muestro todos los libros del autor 29
		ArrayList<Book> ListaLibrosDelAutor29 = Biblioteca.getBooksByAuthor(29);		
		
		// Muestro todos los libros de la editorial 88
		ArrayList<Book> ListaLibrosPorEditorial88 = Biblioteca.getBooksByEditorial(88);		
		
	}

}