package acme.testing.administrator.configuration;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AdministratorConfigurationUpdateTest extends AcmePlannerTest{
	/*
	 * 	En este test se va comprobar si un administrador puede actualizar una 
	 * 	palabra spam, así como su threshold
	 * 
	 *  El resultado esperado es que cada una de las palabras spam
	 *  se hayan editado correctamente con los valores establecidos
	 *	en el archivo csv.
	 */
	

	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/configuration/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void updatepositive(final int recordIndex,final String spam, final Double threshold) {
		
		// Accedemos como administrador
		super.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "List spam words");
		
		// Accedemos a cada registro
		super.clickOnListingRecord(recordIndex);
		
		// Introducimos los nuevos valores
		super.fillInputBoxIn("spamWords", spam);
		super.fillInputBoxIn("spamThreshold", threshold + "");
		super.clickOnSubmitButton("Update");
		
		super.clickOnListingRecord(recordIndex);
		
		// Comprobamos valor de la columna
		super.checkInputBoxHasValue("spamWords", spam);
		//super.checkInputBoxHasValue("spamThreshold", threshold+ "");

		// Cerramos sesión
		super.signOut();
	}
	/*
	 * Testeamos si un administrador no puede actualizar una palabra spam
	 * se imponen las siguientes restricciones:
	 * 	-El campo spam no puede estar vacío
	 * 	-El campo spam no puede ser nulo
	 * 	-Threshold no puede ser menor que 0
	 * 	-Threshold no puede ser mayor que 1
	 */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/configuration/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void updatenegative(final int recordIndex,final String spam, final Double threshold) {
		// Accedemos como administrador
		super.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "List spam words");
		
		// Accedemos a cada registro
		super.clickOnListingRecord(recordIndex);
		
		// Introducimos los nuevos valores
		super.fillInputBoxIn("spamWords", spam);
		super.fillInputBoxIn("spamThreshold", threshold + "");
		super.clickOnSubmitButton("Update");
		
		// Comprobamos que hay errores
		super.checkErrorsExist();
		
		//Cerramos sesión
		super.signOut();
	}
}
