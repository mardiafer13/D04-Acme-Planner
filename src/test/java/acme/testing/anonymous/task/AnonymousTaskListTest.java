
package acme.testing.anonymous.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AnonymousTaskListTest extends AcmePlannerTest {

	/*
	 * En este test se va comprobar si cualquier anonimo puede
	 * acceder a la lista de las tareas no finalizadas
	 * 
	 * Lo esperado es que las tasks del listado
	 * coincidan con las establecidas en el archivo csv.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/task/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void ListRecent(final int recordIndex, final String description, final String Link, final String finalPeriod, final String initialPeriod, final String title) {

		super.clickOnMenu("Anonymous", "List unfinished tasks");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, description);
		super.checkColumnHasValue(recordIndex, 2, Link);

		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("link", Link);
		super.checkInputBoxHasValue("periodFinal", finalPeriod);
		super.checkInputBoxHasValue("periodInitial", initialPeriod);

	}


	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/dashboard/dashboard-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listAnonymousNegative(final String user, final String password) {

		super.signIn(user, password);

		this.driver.get("localhost:8080/Acme-Planner/anonymous/task/list");
		super.checkPanicExists();

		super.signOut();
	}
}
