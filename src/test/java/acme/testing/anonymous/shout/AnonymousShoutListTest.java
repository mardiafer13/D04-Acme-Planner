package acme.testing.anonymous.shout;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AnonymousShoutListTest extends AcmePlannerTest{
	
	/*
	 * En este test se va comprobar si cualquier anonimo puede 
	 * acceder a la lista de los shouts recientes
	 * 
	 * Lo esperado es que los shout del listado
	 * coincidan con los establecidos en el archivo csv.
	 */

	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shout/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void ListRecent(final int recordIndex,final String moment, final String author, final String text, final String info) {
		
		// Accedemos como anonimo
		super.clickOnMenu("Anonymous", "List recent shouts");
		// Comprobación de columna
		super.checkColumnHasValue(recordIndex, 0, moment);
		super.checkColumnHasValue(recordIndex, 1, author);
		super.checkColumnHasValue(recordIndex, 2, text);
		super.checkColumnHasValue(recordIndex, 3, info);
		
	}
}
