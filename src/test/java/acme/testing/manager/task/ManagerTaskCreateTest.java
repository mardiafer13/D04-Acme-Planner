
package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerTaskCreateTest extends AcmePlannerTest {
	
	/*
	 * En este test se va comprobar si un manager puede crear una tarea
	 * y comprobar que se ha creado correctamente.
	 */

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void createPositive(final int recordIndex, final String title, final String description, final String periodInitial, final String periodFinal, final String workloadInHours, final String Link, final Boolean isPublic) {

		// Accedemos como manager
		super.signIn("manager1", "manager1");

		// Accedemos al formulario de crear una tarea
		super.clickOnMenu("Manager", "Create a task");


		// Añadimos los valores
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("periodInitial", periodInitial);
		super.fillInputBoxIn("periodFinal", periodFinal);
		super.fillInputBoxIn("workloadInHours", workloadInHours);
		super.fillInputBoxIn("link", Link);
		super.fillInputBoxIn("isPublic", String.valueOf(isPublic));
		super.clickOnSubmitButton("Create");
		
		
		super.clickOnMenu("Manager", "List my tasks");

		// Pulsamos el boton
	
		super.checkColumnHasValue(recordIndex, 0, periodInitial);
		super.checkColumnHasValue(recordIndex, 1, periodFinal);
		super.checkColumnHasValue(recordIndex, 2, title);
		
		 //Vuelve a recorrer los valores
		super.clickOnListingRecord(recordIndex);

		// Y por ultimo, comprueba que los valores se han actualizado correctamente
		// y coinciden con los del csv
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("periodInitial", periodInitial);
		super.checkInputBoxHasValue("periodFinal", periodFinal);
		super.checkInputBoxHasValue("workloadInHours", workloadInHours);
		super.checkInputBoxHasValue("link", Link);
		super.checkInputBoxHasValue("isPublic", String.valueOf(isPublic));

	}
	
	/*
	 * En este test se va comprobar si un manager no puede crear una tarea
	 * se imponen las siguientes restricciones:
	 * 	-Cualquier campo no puede estar vacío
	 * 	-El campo titulo no puede contener palabras spam
	 * 	-El campo descripcion no puede contener palabras spam
	 * 	-La fecha de inicio no puede ser anterior al dia de hoy
	 *  -La fecha de inicio no puede ser posterior a la fecha de fin
	 *  -La fecha de fin no puede ser anterior a la fecha de inicio
	 *  -El wokload no puede ser mas grande que la diferencia entre las 2 fechas
	 *  -El wokload no puede ser negativo
	 *  -El wokload no puede tener mas de 2 cifras decimales
	 *  -El campo link debe tener un patron URL
	 */

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createNegative(final int recordIndex, final String title, final String description, final String periodInitial, final String periodFinal, final String workloadInHours, final String Link, final Boolean isPublic) {

		// Accedemos como manager
		super.signIn("manager1", "manager1");

		// Accedemos al formulario de crear una tarea
		super.clickOnMenu("Manager", "Create a task");

		// Añadimos los valores
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("periodInitial", periodInitial);
		super.fillInputBoxIn("periodFinal", periodFinal);
		super.fillInputBoxIn("workloadInHours", workloadInHours);
		super.fillInputBoxIn("link", Link);
		super.fillInputBoxIn("isPublic", String.valueOf(isPublic));

		// Pulsamos el boton
		super.clickOnSubmitButton("Create");

		// Debe haber errores
		super.checkErrorsExist();
	}
}
