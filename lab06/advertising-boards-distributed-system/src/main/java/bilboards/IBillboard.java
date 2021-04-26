package bilboards;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Duration;

/**
 * Interfejs, kt�ry powinna implementowa� aplikacja pe�ni�ca rol� tablicy og�oszeniowej (nazwijmy j� Tablica).
 * Aplikacja ta powinna wy�wietla� cyklicznie teksty og�osze� dostarczane metod� addAdvertisement.
 * Za wy�wietlanie tych og�osze� powinien odpowiada� osobny w�tek.
 * W�tek powinien mie� dost�p do bufora na og�oszenia, kt�rego pojemno�� i liczb� wolnych miejsc
 * zwraca metoda getCapacity.
 * Za dodawanie og�oszenia do bufora odpowiada metoda addAdvertisment. 
 * Z chwil� pierwszego wy�wietlenia og�oszenia na tablicy powinien zacz�� zlicza� si� czas jego wy�wietlania.
 * Usuwanie og�oszenia mo�e nast�pi� z dw�ch powod�w: i) og�oszenie mo�e zosta� usuni�te na skutek
 * wywo�ania metody removeAdvertisement (przez Menad�era); ii) og�oszenie mo�e zosta� usuni�te, gdy sko�czy si� przyznany 
 * mu czas wy�wietlania na tablicy (przez w�tek odpowiedzialny w Tablicy za cykliczne wy�wietlanie test�w).
 * Usuwanie og�osze� z bufora i ich dodawanie do bufora wi��e si� z odpowiednim zarz�dzaniem
 * podleg�� struktur� danych
 * W "buforze" mog� si� robi� "dziury", bo og�oszenia mog� mie� przyznany r�ny czas wy�wietlania. 
 * Nale�y wi�c wybra� odpowiedni� struktur� danych, kt�ra pozwoli zapomnie� o nieregularnym wyst�powaniu tych "dziur").
 * Metoda start powinna da� sygna� aplikacji, �e nale�y rozpocz�� cykliczne wy�wietlanie og�osze�.
 * Metoda stop zatrzymuje wy�wietlanie og�osze�.
 * Metody start i stop mo�na odpala� naprzemiennie, przy czym nie powinno to resetowa� zliczonych czas�w wy�wietlania 
 * poszczeg�lnych og�osze�.
 * Uwaga: Tablica powininna by� sparametryzowany numerem portu i hostem rejestru
 * rmi, w kt�rym zarejestrowano namiastk� Menad�era, oraz nazwa, pod kt�r�
 * zarejestrowano t� namiastk�.
 * Jest to potrzebne, by Tablica mog�a dowi�za� si� do Menad�era. 
 * Tablica robi to wywo�uj�c metod� bindBillboard (przekazuj�c jej swoj� namiastk� typu IBillboard).
 * Otrzymuje przy tym identyfikator, kt�ry mo�e u�y�, by si� mog�a wypisa� z Menad�era 
 * (co mo�e sta� si� podczas zamykania tablicy).
 * 
 * @author tkubik
 *
 */

public interface IBillboard extends Remote {
	/**
	 * Metoda dodaj�ca tekst og�oszenia do tablicy og�oszeniowej (wywo�ywana przez
	 * Menad�era po przyj�ciu zam�wienia od Klienta)
	 * 
	 * @param advertTextn   - tekst og�oszenia, jakie ma pojawi� si� na tablicy
	 *                      og�oszeniowej
	 * @param displayPeriod - czas wy�wietlania og�oszenia liczony od pierwszego
	 *                      jego ukazania si� na tablicy og�oszeniowej
	 * @param orderId       - numer og�oszenia pod je zarejestrowano w menad�erze
	 *                      tablic og�oszeniowych
	 * @return - zwraca true, je�li uda�o si� doda� og�oszenie lub false w
	 *         przeciwnym wypadku (gdy tablica jest pe�na)
	 * @throws RemoteException
	 */
	public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException;

	/**
	 * Metoda usuwaj�ca og�oszenie z tablicy (wywo�ywana przez Menad�era po
	 * wycofaniu zam�wienia przez Klienta)
	 * 
	 * @param orderId - numer og�oszenia pod jakim je zarejestrowano w menad�erze
	 *                tablic og�oszeniowych
	 * @return - zwraca true, je�li operacja si� powiod�a lub false w przeciwnym
	 *         wypadku (gdy nie ma og�oszenia o podanym numerze)
	 * @throws RemoteException
	 */
	public boolean removeAdvertisement(int orderId) throws RemoteException;

	/**
	 * Metoda pobieraj�ca informacj� o zaj�to�ci tablicy (wywo�ywana przez
	 * Menad�era)
	 * 
	 * @return - zwraca tablic� dw�ch liczb ca�kowitych - pierwsza to pojemno��
	 *         bufora tablicy, druga to liczba wolnych w nim miejsc
	 * @throws RemoteException
	 */
	public int[] getCapacity() throws RemoteException;

	/**
	 * Metoda pozwalaj�ca ustawi� czas prezentacji danego tekstu og�oszenia na
	 * tablicy w jednym cyklu (wywo�ywana przez Menad�era). Prosz� nie myli� tego z
	 * czasem, przez jaki ma by� wy�wietlany sam tekst og�oszenia. Prezentacja
	 * danego has�a musi by� powt�rzona cyklicznie tyle razy, aby sumaryczny czas
	 * prezentacji by� r�wny lub wi�kszy zam�wionemu czasowi wy�wietlania tekstu
	 * og�oszenia.
	 * 
	 * @param displayInterval - definiuje czas, po kt�rym nast�puje zmiana has�a
	 *                        wy�wietlanego na tablicy. Odwrotno�� tego parametru
	 *                        mo�na interpetowa� jako cz�stotliwo�� zmian pola
	 *                        reklamowego na Tablicy.
	 * @throws RemoteException
	 */
	public void setDisplayInterval(Duration displayInterval) throws RemoteException;

	/**
	 * Metoda startuj�ca cykliczne wy�wietlanie og�osze� (wywo�ywana przez
	 * Menad�era)
	 * 
	 * @return - zwraca true, je�li zostanie poprawnie wykonana lub false w
	 *         przeciwnym wypadku
	 * @throws RemoteException
	 */
	public boolean start() throws RemoteException;

	/**
	 * Metoda stopuj�ca cykliczne wy�wietlanie og�osze� (wywo�ywana przez Menad�era)
	 * 
	 * @return - zwraca true, je�li zostanie poprawnie wykonana lub false w
	 *         przeciwnym wypadku
	 * @throws RemoteException
	 */
	public boolean stop() throws RemoteException;
}
