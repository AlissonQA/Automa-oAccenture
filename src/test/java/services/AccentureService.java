
package services;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccentureService {

    private WebDriver driver;
    private WebDriverWait wait;

    // ABRIR NAVEGADOR
    public void abrirPaginaInicial() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get("https://demoqa.com/");
    }

    // ACESSAR PRACTICE FORM
    public void acessarPracticeForm() {

        WebElement forms = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//h5[normalize-space()='Forms']")
                )
        );

        rolarAte(forms);
        clicar(forms);

        WebElement practiceForm = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//span[normalize-space()='Practice Form']")
                )
        );

        rolarAte(practiceForm);
        clicar(practiceForm);

        wait.until(
                ExpectedConditions.urlContains("automation-practice-form")
        );
    }

    // PREENCHER FORMULÁRIO
    public void preencherFormulario() {

        // NOME
        preencher(By.id("firstName"), "Bryan");
        preencher(By.id("lastName"), "Benedito");
        preencher(By.id("userEmail"), "bryan77@gmail.com.br");

        // GÊNERO
        clicar(wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("label[for='gender-radio-1']")
                )
        ));

        // CELULAR
        preencher(By.id("userNumber"), "67997130708");

        // DATA DE NASCIMENTO
        WebElement dateField = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("dateOfBirthInput")
                )
        );

        clicar(dateField);

        Select monthDropdown = new Select(
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.className("react-datepicker__month-select")
                ))
        );
        monthDropdown.selectByVisibleText("April");

        Select yearDropdown = new Select(
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.className("react-datepicker__year-select")
                ))
        );
        yearDropdown.selectByVisibleText("1995");

        WebElement day = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//div[contains(@class,'react-datepicker__day')"
                                        + " and not(contains(@class,'outside-month'))"
                                        + " and normalize-space(.)='15']"
                        )
                )
        );

        clicar(day);

        // HOBBIES
        WebElement sports = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("label[for='hobbies-checkbox-1']")
                )
        );
        clicar(sports);

        // ENDEREÇO
        preencher(
                By.id("currentAddress"),
                "Rua dos Testes, 123 - Bairro Testando"
        );

        // ESTADO: NCR
        selecionarReactSelect(
                By.id("state"),
                "NCR"
        );

        // CIDADE: DELHI
        selecionarReactSelect(
                By.id("city"),
                "Delhi"
        );
    }

    // SELECIONAR ESTADO OU CIDADE NO REACT SELECT
    private void selecionarReactSelect(By campo, String valor) {

        WebElement container = wait.until(
                ExpectedConditions.visibilityOfElementLocated(campo)
        );

        rolarAte(container);

        // Clica no campo de seleção
        clicar(container);

        // Localiza o input interno do React Select
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                campo.toString().replace("By.id: ", "")
                                        .equals("state")
                                        ? "//*[@id='state']//input"
                                        : "//*[@id='city']//input"
                        )
                )
        );

        input.sendKeys(valor);

        // Busca a opção pelo texto, sem depender do número do ID
        By opcao = By.xpath(
                "//*[@id='state']//div[contains(@id,'option-')"
                        + " and normalize-space(.)='" + valor + "']"
                        + " | //*[@id='city']//div[contains(@id,'option-')"
                        + " and normalize-space(.)='" + valor + "']"
        );

        WebElement resultado = wait.until(
                ExpectedConditions.elementToBeClickable(opcao)
        );

        clicar(resultado);
    }

    // UPLOAD DE ARQUIVO
    public void realizarUploadArquivo() {

        File arquivo = new File(
                System.getProperty("user.dir"),
                "src/test/resources/testeqa.txt"
        );

        if (!arquivo.exists()) {
            throw new RuntimeException(
                    "Arquivo não encontrado: " + arquivo.getAbsolutePath()
            );
        }

        WebElement uploadInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.id("uploadPicture")
                )
        );

        rolarAte(uploadInput);

        // O sendKeys envia o caminho completo para o campo de upload
        uploadInput.sendKeys(arquivo.getAbsolutePath());

        System.out.println(
                "Arquivo enviado: " + arquivo.getAbsolutePath()
        );
    }

    // ENVIAR FORMULÁRIO
    public void enviarFormulario() {

        WebElement submitButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.id("submit")
                )
        );

        rolarAte(submitButton);

        wait.until(
                ExpectedConditions.elementToBeClickable(submitButton)
        );

        clicar(submitButton);
    }

    // FECHAR POP-UP DE RESULTADO
    public void fecharPopUp() {

        WebElement closeButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.id("closeLargeModal")
                )
        );

        rolarAte(closeButton);
        clicar(closeButton);
    }

    // PREENCHER CAMPO
    private void preencher(By localizador, String valor) {

        WebElement campo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(localizador)
        );

        rolarAte(campo);
        campo.clear();
        campo.sendKeys(valor);
    }

    // ROLAGEM AUTOMÁTICA
    private void rolarAte(WebElement elemento) {

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', behavior:'instant'});",
                elemento
        );
    }

    // CLIQUE COM TRATAMENTO DE INTERCEPTAÇÃO
    private void clicar(WebElement elemento) {

        rolarAte(elemento);

        try {
            wait.until(
                    ExpectedConditions.elementToBeClickable(elemento)
            ).click();

        } catch (ElementClickInterceptedException e) {

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    elemento
            );
        }
    }

    // FECHAR NAVEGADOR
    public void fecharNavegador() {

        if (driver != null) {
            driver.quit();
            driver = null;
            wait = null;
        }
    }
}
