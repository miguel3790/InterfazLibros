public class Book {
	// Book_Id int, title String 20, FK_Author int (Table Author) , Year int,
	// FK_Publisher int (Table Publisher), stock int.
	int Id, Fk_Autor, Fk_Editorial, Anio, Stock;
	String Titulo;

	Book () {
		Id = 0;
		Titulo = "SIN_TITULO";
		Fk_Autor = 0;
		Fk_Editorial = 0;
		Anio = 0;
		Stock = 0;
	}
	
	Book(int Id, String Titulo, int Fk_Autor, int Fk_Editorial, int Anio, int Stock) {
		this.Id = Id;
		this.Titulo = Titulo;
		this.Fk_Autor = Fk_Autor;
		this.Fk_Editorial = Fk_Editorial;
		this.Anio = Anio;
		this.Stock = Stock;
	}

	public void ShowBook (){
		System.out.println("Id: " + Id + " |  " + "Titulo: "
				+ Titulo + " |  " + "Fk_Autor:" + Fk_Autor
				+ " |  " + "Fk_Editorial:" + Fk_Editorial + " |  "
				+ "Año:" + Anio + " |  " + "Stock:"	+ Stock + ".");
	}
	
	// SETTERS
	public void setid(int Id) {
		this.Id = Id;
	}

	public void setTitulo(String Titulo) {
		this.Titulo = Titulo;
	}

	public void setFk_Autor(int Fk_Autor) {
		this.Fk_Autor = Fk_Autor;
	}

	public void setFk_Editorial(int Fk_Editorial) {
		this.Fk_Editorial = Fk_Editorial;
	}

	public void setAnio(int Anio) {
		this.Anio = Anio;
	}

	public void setStock(int Stock) {
		this.Stock = Stock;
	}

	// GETTERS

	public int getId() {
		return Id;
	}

	public String getTitulo() {
		return Titulo;
	}

	public int getFk_Autor() {
		return Fk_Autor;
	}

	public int getFk_Editorial() {
		return Fk_Editorial;
	}

	public int getAnio() {
		return Anio;
	}

	public int getStock() {
		return Stock;
	}
}