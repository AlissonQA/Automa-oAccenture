package Steps;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.gl.Dado;
import io.cucumber.java.it.Quando;
import io.cucumber.java.pt.Entao;

public class steps {

	WebDriver driver;

	@Dado("que eu estou na pagina inicial do site")
	public void que_eu_estou_na_pagina_inicial_do_site() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");

		driver = new ChromeDriver(options);
		driver.get("https://demoqa.com/");
	}

	@Quando("clico no submenu Practice Form")
	public void eu_clicar_no_submenu_practice_form() throws InterruptedException {
		WebElement element = driver.findElement(By.xpath("//h5[text()='Forms']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(500);
		element.click();

		driver.findElement(By.xpath("//span[text()='Practice Form']")).click();

		
	}

	@Quando("preencher todo o formulário com valores aleatórios")
	public void preencher_todo_o_formulário_com_valores_aleatórios() {
		driver.findElement(By.id("firstName")).sendKeys("Bryan ");
		driver.findElement(By.id("lastName")).sendKeys("Benedito");
		driver.findElement(By.id("userEmail")).sendKeys("bryan77@gmail.com.br");
		WebElement genderMaleLabel = driver.findElement(By.cssSelector("label[for='gender-radio-1']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", genderMaleLabel);
		driver.findElement(By.id("userNumber")).sendKeys("67997130708");

		((JavascriptExecutor) driver).executeScript(
				"var adContainer = document.getElementById('google_ads_iframe_/21849154601,22343295815/Ad.Plus-Anchor_0_container_');"
						+ "if(adContainer){ adContainer.style.display='none'; }");

		WebElement dateField = driver.findElement(By.id("dateOfBirthInput"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateField);
		Select monthDropdown = new Select(driver.findElement(By.className("react-datepicker__month-select")));
		monthDropdown.selectByVisibleText("April");
		Select yearDropdown = new Select(driver.findElement(By.className("react-datepicker__year-select")));
		yearDropdown.selectByVisibleText("1995");
		WebElement day = driver.findElement(By.xpath(
				"//div[contains(@class, 'react-datepicker__day') and not(contains(@class, 'outside-month')) and text()='15']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", day);
		WebElement hobbiesSportsLabel = driver.findElement(By.cssSelector("label[for='hobbies-checkbox-1']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hobbiesSportsLabel);

		WebElement addressField = driver.findElement(By.id("currentAddress"));
		addressField.sendKeys("Rua dos Testes, 123 - Bairro Testando");

		WebElement stateField = driver.findElement(By.id("state"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", stateField);
		stateField.click();
		WebElement optionNCR = driver.findElement(By.xpath("//div[contains(text(),'NCR')]"));
		optionNCR.click();

		WebElement cityField = driver.findElement(By.id("city"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cityField);
		cityField.click();
		WebElement optionDelhi = driver.findElement(By.xpath("//div[contains(text(),'Delhi')]"));
		optionDelhi.click();
	}

	@Quando("realizo o upload do Arquivo.txt")
	public void realizo_o_upload_do_arquivo_txt() {
		// Caminho absoluto do arquivo a ser enviado
		String filePath = System.getProperty("user.dir") + "/src/test/resources/testeqa.txt";


		// Localiza o input de upload de arquivo
		WebElement uploadInput = driver.findElement(By.id("uploadPicture")); // ajuste o id conforme o seu HTML

		// Envia o caminho do arquivo para o input
		uploadInput.sendKeys(filePath);

	}

	@Quando("clicar em submit para enviar form")
	public void clicar_em_submit_para_enviar_form() {
		WebElement submitButton = driver.findElement(By.id("submit"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
	}

	@Entao("o pop é aberto com sucesso e clico em close")
	public void o_pop_é_aberto_com_sucesso_e_clico_em_close() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("closeLargeModal")));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", closeButton);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton);
	}
}