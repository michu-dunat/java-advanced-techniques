package bilboards;

import java.io.Serializable;
import java.time.Duration; // available since JDK 1.8

/**
 * Klasa reprezentuj�ca zam�wienie wy�wietlania og�oszenia o zadanej tre�ci
 * przez zadany czas ze wskazaniem na namiastke klienta, przez kt�r� mo�na
 * przes�a� informacje o numerze zam�wienia w przypadku jego przyj�cia
 * 
 * @author tkubik
 *
 */
public class Order implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String advertText;
	public Duration displayPeriod;
	public IClient client;
}