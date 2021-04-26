package bilboards;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfejs, kt�ry powinna implementowa� aplikacja pe�ni�ca rol� menad�era
 * tablic (nazwijmy j� Menad�er). Menad�er powinien wy�wietla� wszystkie
 * dowi�zane do niego tablice oraz ich bie��cy stan. Tablice dowi�zuj� si� do i
 * odwi�zuj� od menad�era wywo�uj�c jego metody bindBillboard oraz
 * unbindBillboard. Z menad�erem mo�e po��czy� si� Klient przekazuj�c mu
 * zam�wienie wy�wietlania danego og�oszenia przez zadany czas. Robi to
 * wywo�uj�c metod� placeOrder. Menad�er, je�li przyjmie zam�wienie, zwraca
 * Klientowi numer zam�wienia wykorzystuj�c przy tym przekazan� w zam�wieniu
 * namiastke. Klient rozpoznaje, czy przyj�to zam�wienie, sprawdzaj�c wynik
 * zwracany z metody placeOrder.
 * Zam�wienia natychmiast po przyj�ciu trafiaj� na dowi�zane Tablice mog�ce w danej chwili przyj�� og�oszenie do wy�wietlania.
 * Je�li w danej chwili nie ma �adnej wolnej Tablicy zam�wienie nie powinno by� przyj�te do realizacji.
 * Aby przekona� si� o stanie tablic Menad�er wywo�uje metody ich namiastek getCapacity.
 * Wystarczy, �e istnieje jedna wolna tablica by przyj�� zam�wienie.
 * Na ile tablic trafi dane zam�wienie decyduje dost�pno�� wolnych miejsc w chwili zam�wienia.  
 * 
 * Uwaga: Menad�er powinien utworzy� lub po��czy� si� z rejestrem rmi o
 * wskazanym numerze portu. Zak�adamy, �e rejestr rmi dzia�a na tym samym
 * komputerze, co Menad�er (mo�e by� cz�ci� aplikacji Menad�era).
 * Menad�er rejestruje w rejestrze rmi posiadan�
 * namiastke IManager pod zadan� nazw� (nazwa ta nie mo�e by� na twardo
 * zakodowanym ci�giem znak�w). Nazwa namiastki menad�era, host i port na kt�rym
 * dzia�a rejest rmi powinny by� dostarczone Klientowi (jako parmetry) oraz Tablicom.
 * 
 * @author tkubik
 *
 */
public interface IManager extends Remote { // port, nazwa, GUI

	/**
	 * Metoda dowi�zywania namiastki Tablicy do Menad�era (wywo�ywana przez Tablic�)
	 * 
	 * @param billboard - dowi�zywana namiastka
	 * @return - zwraca numer przyznany namiastce w Menad�erze
	 * @throws RemoteException
	 */
	public int bindBillboard(IBillboard billboard) throws RemoteException;

	/**
	 * Metoda odwi�zuj�ca namiastk� Tablicy z Menad�era (wywo�ywana przez Tablic�)
	 * @param billboardId - numer odwi�zywanej namiastki
	 * @return
	 * @throws RemoteException
	 */
	public boolean unbindBillboard(int billboardId) throws RemoteException;

	/**
	 * Metoda s�u��ca do sk�adania zam�wienia wy�wietlania og�oszenia (wywo�ywana przez Klienta)
	 * @param order - szczeg�y zam�wienia (wraz z tekstem og�oszenia, czasem jego wy�wietlania i namiastk� klienta)
	 * @return - zwraca true je�li przyj�to zam�wienie oraz false w przeciwnym wypadku
	 * @throws RemoteException
	 */
	public boolean placeOrder(Order order) throws RemoteException;

	/**
	 * Metoda s�u��ca do wycofywania zam�wienia (wywo�ywana przez Klienta)
	 * @param orderId - numer wycofywanego zam�wienia 
	 * @return - zwraca true je�li wycofano zam�wienie oraz false w przeciwnym wypadku
	 * @throws RemoteException
	 */
	public boolean withdrawOrder(int orderId) throws RemoteException;
}