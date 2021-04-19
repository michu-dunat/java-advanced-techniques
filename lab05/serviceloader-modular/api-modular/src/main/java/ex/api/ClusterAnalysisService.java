package ex.api;

/**
 * Interfejs serwisu pozwalaj�cego przeprowadzi� analiz� skupie�.
 * Zak�adamy, �e serwis dzia�a asynchronicznie.
 * Na pocz�tek nale�y do serwisu za�adowa� dane.
 * Potem mo�na z serwisu pobra� wyniki analizy.
 * W przypadku niepowodzenia wykonania jakiej� metody wyrzucony zostanie wyj�tek.
 * 
 * @author tkubik
 *
 */
public interface ClusterAnalysisService {
	public void setOptions(String[] options) throws ClusteringException; // ustawia opcje
	// metoda zwracaj�ca nazw� algorytmu
	public String getName();                                   
	// metoda pozwalaj�ca przekaza� dane do analizy
	// wyrzuca wyj�tek, je�li aktualnie trwa przetwarzanie danych
	public void submit(DataSet ds) throws ClusteringException; 
	// metoda pozwalaj�ca pobra� wynik analizy
    // zwraca null - je�li trwa jeszcze przetwarzanie lub nie przekazano danych do analizy
	// wyrzuca wyj�tek - je�li podczas przetwarzania dosz�o do jakich� b��d�w
	// clear = true - je�li wyniki po pobraniu maj� znikn�� z serwisu
    public DataSet retrieve(boolean clear) throws ClusteringException;   
}

