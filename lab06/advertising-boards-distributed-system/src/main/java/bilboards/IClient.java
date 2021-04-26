package bilboards;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfejs, kt�ry powinien zaimplementowa� klient (nazwijmy t� alikacj�
 * Klient) komunikuj�cy si� z menad�erem tablic. Klient powinien mie� interfejs
 * pozwalaj�cy: i) definiowa� zam�wienia wy�wietlania og�oszenia (tekst
 * og�oszenia, czas wy�wietlania) ii) sk�ada� zam�wienia wy�wietlania og�oszenia
 * Menad�erowi, iii) wycofywa� z�o�one zam�wienia.
 * 
 * Przy okazji sk�adania zam�wienia wy�wietlania og�oszenia Klient przekazuje
 * Menad�erowi namiastk� IClient. Menagd�er u�yje tej namiastki, by zwrotnie
 * przekaza� klientowi numer zam�wienia (je�li oczywi�cie zam�wienie zostanie
 * przyj�te). Ma to dzia�a� podobnie jak ValueSetInterface z przyk�adu RMITest.
 * 
 * Numery zam�wie� i tre�ci og�osze� przyj�tych przez Menad�era powinny by�
 * widoczne na interfejsie Klienta. Klient powinien sam zadba� o usuwanie
 * wpis�w, kt�rych okres wy�wietlania zako�czy� si� (brak synchronizacji w tym
 * wzgl�dzie z menad�erem)
 * 
 * Uwaga: Klient powinien by� sparametryzowany numerem portu i hostem rejestru
 * rmi, w kt�rym zarejestrowano namiastk� Menad�era, oraz nazwa, pod kt�r�
 * zarejestrowano t� namiastk�.
 * 
 * @author tkubik
 *
 */
public interface IClient extends Remote { // host, port, nazwa
	/**
	 * Metoda s�u��ca do przekazania numeru przyj�tego zam�wienia (wywo�ywana przez
	 * Menad�era na namiastce klienta przekazanej w zam�wieniu)
	 * 
	 * @param orderId
	 * @throws RemoteException
	 */
	public void setOrderId(int orderId) throws RemoteException;
}
