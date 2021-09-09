
package acme.testing.controlCheck;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import acme.testing.AcmePlannerTest;

public class AdministratorShowDashboardTest extends AcmePlannerTest {
	
	/*
	 * En este test se va comprobar que un administrador pueda acceder a su dashboard 
	 * y comprobamos si las estadisticas coinciden con los cálculos
	 * de las tasks creadas.
	 */

	@Test
	@Order(30)
	public void administratoDashboardPositive() {
		super.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "Dashboard");
		
		final By locatorNumberCheckTrueTasks;
		locatorNumberCheckTrueTasks = By.xpath("/html/body/div[2]/div/table/tbody/tr[1]/td");
		final WebElement numberOfTrueCheck = this.driver.findElement(locatorNumberCheckTrueTasks);
		Assertions.assertEquals("3.00", numberOfTrueCheck.getText());

		final By locatorRatioCheckTrueTasks;
		locatorRatioCheckTrueTasks = By.xpath("/html/body/div[2]/div/table/tbody/tr[2]/td");
		final WebElement ratioOfTrueCheck = this.driver.findElement(locatorRatioCheckTrueTasks);
		Assertions.assertEquals("0.00", ratioOfTrueCheck.getText());
		
		final By locatorAverageofCurrency1;
		locatorAverageofCurrency1 = By.xpath("/html/body/div[2]/div/table/tbody/tr[3]/td");
		final WebElement averageOfCurrency1 = this.driver.findElement(locatorAverageofCurrency1);
		Assertions.assertEquals("52.33", averageOfCurrency1.getText());
		
		final By locatorAverageofCurrency2;
		locatorAverageofCurrency2 = By.xpath("/html/body/div[2]/div/table/tbody/tr[4]/td");
		final WebElement averageOfCurrency2 = this.driver.findElement(locatorAverageofCurrency2);
		Assertions.assertEquals("62.25", averageOfCurrency2.getText());
		
		final By locatorDeviationeofCurrency1;
		locatorDeviationeofCurrency1 = By.xpath("/html/body/div[2]/div/table/tbody/tr[5]/td");
		final WebElement deviationOfCurrency1 = this.driver.findElement(locatorDeviationeofCurrency1);
		Assertions.assertEquals("27.18", deviationOfCurrency1.getText());
		
		final By locatorDeviationeofCurrency2;
		locatorDeviationeofCurrency2 = By.xpath("/html/body/div[2]/div/table/tbody/tr[6]/td");
		final WebElement deviationOfCurrency2 = this.driver.findElement(locatorDeviationeofCurrency2);
		Assertions.assertEquals("33.09", deviationOfCurrency2.getText());
		
		
		
		
		
		
		By locatorNumberPublicTasks;
		locatorNumberPublicTasks = By.xpath("/html/body/div[2]/div/table/tbody/tr[7]/td");
		final WebElement numberPublicTasks = this.driver.findElement(locatorNumberPublicTasks);
		Assertions.assertEquals("11.00", numberPublicTasks.getText());

		final By locatorNumberPrivateTask;
		locatorNumberPrivateTask = By.xpath("/html/body/div[2]/div/table/tbody/tr[8]/td");
		final WebElement numberPrivateTask = this.driver.findElement(locatorNumberPrivateTask);
		Assertions.assertEquals("1.00", numberPrivateTask.getText());

		By locatorNumberFinalTask;
		locatorNumberFinalTask = By.xpath("/html/body/div[2]/div/table/tbody/tr[9]/td");
		final WebElement numberFinalTask = this.driver.findElement(locatorNumberFinalTask);
		Assertions.assertEquals("6.00", numberFinalTask.getText());

		final By locatorNumberNoFinalTask;
		locatorNumberNoFinalTask = By.xpath("/html/body/div[2]/div/table/tbody/tr[10]/td");
		final WebElement numberNoFinalTask = this.driver.findElement(locatorNumberNoFinalTask);
		Assertions.assertEquals("6.00", numberNoFinalTask.getText());

		final By locatorAverageDurationPeriodTasks;
		locatorAverageDurationPeriodTasks = By.xpath("/html/body/div[2]/div/table/tbody/tr[11]/td");
		final WebElement averageDurationPeriodTasks = this.driver.findElement(locatorAverageDurationPeriodTasks);
		Assertions.assertEquals("17,049.00", averageDurationPeriodTasks.getText());

		By locatorDeviationDurationPeriodTasks;
		locatorDeviationDurationPeriodTasks = By.xpath("/html/body/div[2]/div/table/tbody/tr[12]/td");
		final WebElement deviationDurationPeriodTasks = this.driver.findElement(locatorDeviationDurationPeriodTasks);
		Assertions.assertEquals("1,531.06", deviationDurationPeriodTasks.getText());

		By locatorMinimumDurationPeriodTasks;
		locatorMinimumDurationPeriodTasks = By.xpath("/html/body/div[2]/div/table/tbody/tr[13]/td");
		final WebElement minimumDurationPeriodTasks = this.driver.findElement(locatorMinimumDurationPeriodTasks);
		Assertions.assertEquals("0.00", minimumDurationPeriodTasks.getText());

		By locatorMaximumDurationPeriodTasks;
		locatorMaximumDurationPeriodTasks = By.xpath("/html/body/div[2]/div/table/tbody/tr[14]/td");
		final WebElement maximumDurationPeriodTasks = this.driver.findElement(locatorMaximumDurationPeriodTasks);
		Assertions.assertEquals("3,673.00", maximumDurationPeriodTasks.getText());

		By locatorAverageWorkloadTasks;
		locatorAverageWorkloadTasks = By.xpath("/html/body/div[2]/div/table/tbody/tr[15]/td");
		final WebElement averageWorkloadTasks = this.driver.findElement(locatorAverageWorkloadTasks);
		Assertions.assertEquals("3.36", averageWorkloadTasks.getText());

		By locatorDeviationWorkloadTasks;
		locatorDeviationWorkloadTasks = By.xpath("/html/body/div[2]/div/table/tbody/tr[16]/td");
		final WebElement deviationWorkloadTasks = this.driver.findElement(locatorDeviationWorkloadTasks);
		Assertions.assertEquals("4.09", deviationWorkloadTasks.getText());

		By locatorMinimumWorkloadTasks;
		locatorMinimumWorkloadTasks = By.xpath("/html/body/div[2]/div/table/tbody/tr[17]/td");
		final WebElement minimumWorkloadTasks = this.driver.findElement(locatorMinimumWorkloadTasks);
		Assertions.assertEquals("0.10", minimumWorkloadTasks.getText());

		final By locatorMaximumWorkloadTasks;
		locatorMaximumWorkloadTasks = By.xpath("/html/body/div[2]/div/table/tbody/tr[18]/td");
		final WebElement maximumWorkloadTasks = this.driver.findElement(locatorMaximumWorkloadTasks);
		Assertions.assertEquals("11.25", maximumWorkloadTasks.getText());
		
		
		
		

	}
	/*
	 * Testeamos que ni un manager ni un empleado
	 * puedan acceder a la dashboard 
	 * del administrador mediante su url.
	 */

	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/dashboard/dashboard-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void managerDashboardNegative(final String user, final String password) {

		super.signIn(user, password);

		super.navigate("/administrator/dashboard/show",null);
		super.checkPanicExists();

		super.signOut();
	}

}
