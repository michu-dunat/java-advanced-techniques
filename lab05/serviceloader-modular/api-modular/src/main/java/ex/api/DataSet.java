package ex.api;
/**
 * Klasa reprezentuj�ca zbi�r danych w postaci tabelarycznej.
 * Przechowuje nag��wek (jednowymiarowa tablica z nazwami kolumn) 
 * oraz dane (dwuwymiarowa tablica, kt�rej wiersze reprezentuj� wektory danych).
 * Zak�adamy, �e b�d� zawsze istnie� przynajmniej dwie kolumny o nazwach:
 * "RecordId" - w kolumnie tej przechowywane s� identyfikatory rekord�w danych;
 * "CategoryId" - w kolumnie tej przechowywane s� identyfikatory kadegorii rekord�w danych (wynik analizy skupie�).
*
 * @author tkubik
 *
 */
public class DataSet {
	private String[] header = {}; 
	private String[][] data = {{}};

	private <T> T[][] deepCopy(T[][] matrix) {
	    return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray(i -> matrix.clone());
	}
	
	public String[] getHeader() {
		return header;
	}
	public void setHeader(String[] header) {
		this.header = header.clone();
	}
	public String[][] getData() {
		return data;
	}
	public void setData(String[][] data) {
		this.data = deepCopy(data);
	}
}
