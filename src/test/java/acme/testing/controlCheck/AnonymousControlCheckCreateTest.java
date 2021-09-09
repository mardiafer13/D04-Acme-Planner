
package acme.testing.controlCheck;

import java.time.LocalDate;
import java.util.Random;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AnonymousControlCheckCreateTest extends AcmePlannerTest {
	
	/*
	 * En este test se va comprobar si cualquier anónimo puede crear un shout
	 * y comprobar que se ha creado correctamente.
	 */

	
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/controlCheck/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createPositive(final int recordIndex, final String author, final String text, final String info, final String budget, final String isCheck) {
		
		String insignia;
		final String generatedString = this.getSaltString();
		final String [] moment = LocalDate.now().toString().split("-");
		insignia= generatedString+":"+moment[0].substring(2)+moment[1]+":"+moment[2];
		
		String deadline;
		deadline = LocalDate.now().plusWeeks(1).toString().replace("-", "/") + " 23:59";
		
		// Accedemos al formulario
		super.clickOnMenu("Anonymous", "Create a shout");
		
		// Introducimos los datos
		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("info", info);
		super.fillInputBoxIn("rocke.deadline", deadline);
		super.fillInputBoxIn("rocke.insignia", insignia);
		super.fillInputBoxIn("rocke.budget", budget);
		super.fillInputBoxIn("rocke.important", isCheck);
		
		// Le damos al boton
		super.clickOnSubmitButton("Shout!");
		
		super.clickOnMenu("Anonymous", "List recent shouts");
		
		// Comprobamos cada columna
		super.checkColumnHasValue(recordIndex, 1, author);
		super.checkColumnHasValue(recordIndex, 2, text);
		super.checkColumnHasValue(recordIndex, 3, info);
		super.checkColumnHasValue(recordIndex, 4, deadline);
		super.checkColumnHasValue(recordIndex, 5,insignia);
		super.checkColumnHasValue(recordIndex, 6, budget);
		super.checkColumnHasValue(recordIndex, 7, isCheck);
		
	}
	
	/*
	 * En este test se va comprobar si cualquier anónimo no puede crear un shout
	 * se imponen las siguientes restricciones:
	 * 	-El campo autor debe contener entre 5 y 25 palabras
	 * 	-El campo autor no puede contener palabras spam
	 * 	-El campo autor no puede estar vacío
	 * 	-El campo texto no puede contener palabras spam
	 * 	-El campo texto no puede estar vacío
	 * 	-El campo texto puede contener como máximo 100 caracteres
	 */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/controlCheck/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createNegative(final int recordIndex, final String author, final String text, final String info, final String deadline, final String insignia, final String budget, final String isCheck) {
		
		super.clickOnMenu("Anonymous", "Create a shout");
		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("info", info);
		super.fillInputBoxIn("rocke.deadline", deadline);
		super.fillInputBoxIn("rocke.insignia", insignia);
		super.fillInputBoxIn("rocke.budget", budget);
		super.fillInputBoxIn("rocke.important", isCheck);
		super.clickOnSubmitButton("Shout!");
		super.checkErrorsExist();
			
	}
	
	
	
	// Generador de Strings aleatorios para el campo Keylen
		protected String getSaltString() {
	        final String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        final StringBuilder salt = new StringBuilder();
	        final Random rnd = new Random();
	        while (salt.length() < 6) { // length of the random string.
	            final int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        final String saltStr = salt.toString();
	        return saltStr;

	    }
	
	
}
