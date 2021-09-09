package acme.testing.controlCheck;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AnonymousControlCheckListTest extends AcmePlannerTest{
	
	/*
	 * En este test se va comprobar si cualquier anonimo puede 
	 * acceder a la lista de los shouts recientes
	 * 
	 * Lo esperado es que los shout del listado
	 * coincidan con los establecidos en el archivo csv.
	 */

	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/controlCheck/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void ListRecent(final int recordIndex,final String moment, final String author, final String text, final String info, final String date, final String money, final String isCheck) {
		
		// Accedemos como anonimo
		super.clickOnMenu("Anonymous", "List recent shouts");
		// Comprobación de columna
		super.checkColumnHasValue(recordIndex, 0, moment);
		super.checkColumnHasValue(recordIndex, 1, author);
		super.checkColumnHasValue(recordIndex, 2, text);
		super.checkColumnHasValue(recordIndex, 3, info);
		super.checkColumnHasValue(recordIndex, 4, date);
		super.checkColumnHasValue(recordIndex, 5, money);
		super.checkColumnHasValue(recordIndex, 6, isCheck);
		
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/dashboard/dashboard-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listAnonymousNegative(final String user, final String password) {

		super.signIn(user, password);

		this.driver.get("localhost:8080/Acme-Planner/anonymous/shout/list-recent");
		super.checkPanicExists();

		super.signOut();
	}
	
	
}
