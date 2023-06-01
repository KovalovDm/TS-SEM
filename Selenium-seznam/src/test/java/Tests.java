import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tests {
    WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/dmitrijkovalev/Downloads/chromedriver_mac64/chromedriver");
        driver = new ChromeDriver();
    }

    //log in
    @Test
    public void seznamLogIn() {
        //go to https://www.seznam.cz/
        driver.get("https://www.seznam.cz/");

        //проверить, высвечивается ли окно с куками, если да - нажать souhlas
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



//        WebElement iframeElement = driver.findElement(By.cssSelector("div > div > iframe"));
//        driver.switchTo().frame(iframeElement);


        //?????????????
//        driver.findElement(By.cssSelector("#www-seznam-cz > div.szn-cmp-dialog-container"));
//        driver.findElement(By.cssSelector("div"));
//        driver.findElement(By.cssSelector("div > div"));
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        //desiredElement.click();
//        driver.findElement(By.cssSelector("div > div > div.scmp-scrollable"));
//        driver.findElement(By.cssSelector("div > div > div.scmp-footer"));

        //ТУТ
        //найти и нажать на кнопку Přihlásit
        driver.findElement(By.linkText("Přihlásit")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //переключиться на другое окно - !!!!!!!!!!!!
        Set<String> windowHandles = driver.getWindowHandles();
        String mainWindowHandle = driver.getWindowHandle();
        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(mainWindowHandle)) {
                driver.switchTo().window(windowHandle);
                // Проверьте содержимое всплывающего окна и выполните необходимые действия
                break; // Если нашли нужное окно, можно прервать цикл
            }
        }

        //ввести в поле логин значение
        driver.findElement(By.id("login-username")).sendKeys("dmytro.kovalov");

        //нажать кнопку continue
        driver.findElement(By.xpath("/html/body/main/section[1]/form[1]/button[1]")).click();

        //ввести пароль
        driver.findElement(By.id("login-password")).sendKeys("TS1-kovaldmy");

        //нажать кнопку continue
        driver.findElement(By.xpath("/html/body/main/section[1]/form[1]/button[1]")).click();

        //вернуться на главное меню
        driver.switchTo().window(mainWindowHandle);
    }

    //logs out from the account on the main page
    public void seznamLogOut() {
        //click on the account button
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div[2]/div/div/div[1]/div[1]/div[2]/div/div/div/div/div/button")).click();

        //click on exit button
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div[2]/div/div/div[1]/div[1]/div[2]/div/div/div/div/div/div[2]/div[2]/ul[1]/li[3]/a")).click();
    }

    //1 - logs test
    @Test
    public void loginLogoutTest() {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        seznamLogIn();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[1]/div[2]/div/div/div[1]/div[1]/div[2]/div/div/div/div/div/button")));

        seznamLogOut();

        driver.quit();
    }

    //2 - jízdní řády test - done
    @Test
    public void drivingLinesTest() {
        //expected result
        String expectedUrl = "https://www.seznam.cz/jizdnirady/pubt%3A15708346%3A9hC7ZxXvmY-%3Emuni%3A5740%3A9mMT8xTuNN/2023-08-01T10%3A30%3A00%3Ad?o=257&t=0";

        WebDriverWait wait = new WebDriverWait(driver, 5);

        //log in to https://www.seznam.cz/
        seznamLogIn();

        driver.findElement(By.xpath("/html/body/div/div/div[1]/div[2]/div/div/div[1]/header/div/div/div[2]/ul/li[7]/button/span[1]")).click();

        //enters random values in the "from" and "to" fields to go to the page https://www.seznam.cz/jizdnirady/
        driver.findElement(By.id("mhd-input-from")).sendKeys("test1");
        driver.findElement(By.id("mhd-input-to")).sendKeys("test2");

        //search
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div[2]/div/div/div[1]/header/div/div/div[2]/div[1]/form/button")).click();

        //waits until the desired page opens
        wait.until(ExpectedConditions.urlToBe("https://www.seznam.cz/jizdnirady/"));


        //departure insert
        driver.findElement(By.id("departure")).sendKeys("Praha");

        //initialization of the selection option at the moment of its appearance
        WebElement pragueMainRailwayStationOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]/div/div/div/div/form/div[3]/div[1]/div[2]/div/div/div/ul/li[1]")));
        pragueMainRailwayStationOption.click();

        //arrival insert
        driver.findElement(By.id("arrival")).sendKeys("Brno");


        //date and time selection
        driver.findElement(By.cssSelector("#route-form > div.route-form-fields.route-form-fields__expanded > div.route-form-field.datepicker > button")).click();

        //date selection
        WebElement chooseDateButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#route-form > div.route-form-fields.route-form-fields__expanded > div.route-form-field.datepicker > div > div > div > div > div > div:nth-child(1) > div")));
        chooseDateButton.click();

        //wait until the date selection window appears
        WebElement nextMonthButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#route-form > div.route-form-fields.route-form-fields__expanded > div.route-form-field.datepicker > div > div > div > div > div > div.datepicker-calendar.datepicker-calendar__visible > div > div > div.DayPicker-NavBar > span.DayPicker-NavButton.DayPicker-NavButton--next")));

        //scroll through the months until August 2023 appears
        while (true) {
            nextMonthButton.sendKeys(Keys.SPACE);
            WebElement div = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div/div/form/div[3]/div[4]/div/div/div/div/div/div[3]/div/div/div[2]/div/div[1]/div"));

            if (div.getText().equals("srpna 2023")) {
                break;
            }
        }

        //click on the 1st number (day)
        driver.findElement(By.cssSelector("#route-form > div.route-form-fields.route-form-fields__expanded > div.route-form-field.datepicker > div > div > div > div > div > div.datepicker-calendar.datepicker-calendar__visible > div > div > div.DayPicker-Months > div > div.DayPicker-Body > div:nth-child(1) > div:nth-child(2) > span")).click();

        //enter the desired hour
        WebElement hour = driver.findElement(By.cssSelector("#route-form > div.route-form-fields.route-form-fields__expanded > div.route-form-field.datepicker > div > div > div > div > div > div:nth-child(2) > input.timeInput.hours"));
        hour.clear();
        hour.sendKeys(Keys.COMMAND+ "a"); //select the current value in the field
        hour.sendKeys(Keys.DELETE); //delete current value
        hour.sendKeys("10");

        //enter the desired minutes
        WebElement min = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div/div/form/div[3]/div[4]/div/div/div/div/div/div[2]/input[2]"));
        min.clear();
        min.sendKeys(Keys.COMMAND + "a");
        min.sendKeys(Keys.DELETE);
        min.sendKeys("30");


        //choose options
        driver.findElement(By.cssSelector("#route-form > div.route-form-fields.route-form-fields__expanded > div.route-form-field.options > button > span > span")).click();

        //choose "without transfer"
        driver.findElement(By.name("direct")).sendKeys(Keys.SPACE);

        //choose "by bus"
        driver.findElement(By.id("transport-type-bus-label")).click();

        //choose "with pram"
        driver.findElement(By.cssSelector("#route-form > div.route-form-fields.route-form-fields__expanded > div.route-form-field.options > div > div > div > div > div:nth-child(2) > span.stroller > label > span.checkbox-face > svg")).click();

        //search
        driver.findElement(By.cssSelector("#submit")).click();

        // сhecking for a match
        String actualUrl = driver.getCurrentUrl();
        assertEquals(expectedUrl, actualUrl, "URLs don't match");

        driver.quit();
    }

    //3 - horoskopy test - done
    @Test
    public void horoscopesTest() {
        //expected result
        String expectedUrl = "https://www.horoskopy.cz/jak-se-k-sobe-hodi?znameni1=1&znameni2=5";

        WebDriverWait wait = new WebDriverWait(driver, 5);

        //log in to https://www.seznam.cz/
        seznamLogIn();

        driver.findElement(By.cssSelector("#boxik-185 > div > div > div.gadget__header > div > h2 > a")).click();

        //switch to the opening window
        String currentWindowHandle = driver.getWindowHandle();

        //wait until the second tab opens
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        //switch to the opened tab
        for (String windowHandle: driver.getWindowHandles()) {
            if (!windowHandle.equals(currentWindowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        //choose options
        Select select1 = new Select(driver.findElement(By.name("znameni1")));
        select1.selectByValue("1");

        Select select2 = new Select(driver.findElement(By.name("znameni2")));
        select2.selectByValue("5");

        //click on "show match"
        driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[3]/div[2]/div[2]/div/form/span/input")).click();

        //checking for a match
        String actualUrl = driver.getCurrentUrl();
        assertEquals(expectedUrl, actualUrl);

        driver.quit();
    }

    //4 - zboží test - done
    @Test
    public void goodsTest() {
        //expected result
        String expectedUrl = "https://www.zbozi.cz/telefony-navigace/mobilni-telefony/?barva=zelena&cena-do=19999&cena-od=10000&interni-pamet-do=256&interni-pamet-od=64&kraj=CZ010&q=Iphone%2013&uhlopricka-displeje-do=6.5&uhlopricka-displeje-od=5.5&vyrobce=apple_iphone-13";

        WebDriverWait wait = new WebDriverWait(driver, 10);

        //log in to https://www.seznam.cz/
        seznamLogIn();

        //click on "goods"
        driver.findElement(By.cssSelector("#hp-app > div > div.drag-and-drop > div.main-content > div > div > div.main-content__content > header > div > div > div.search > ul > li.search__tab.search__tab--zbozi > button > span.tab-button__title-inactive")).click();

        //click on "search"
        WebElement searchButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#hp-app > div > div.drag-and-drop > div.main-content > div > div > div.main-content__content > header > div > div > div.search > div.search-form > form > button")));
        searchButton.click();


        //input new value
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("q")));
        inputField.sendKeys("Iphone 13");

        //click "search"
        driver.findElement(By.cssSelector("#search-form > div > button.search")).click();

        //wait until the desired page is loaded
        wait.until(ExpectedConditions.urlToBe("https://www.zbozi.cz/hledej/?q=Iphone%2013"));

        //choose radio "mobile phones"
        driver.findElement(By.cssSelector("#page > main > div.left > div > div > div > ul > div:nth-child(1) > li > ul > li:nth-child(2) > a")).click();

        //choose price
        WebElement choosePriceButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#page > main > div.left > div:nth-child(3) > div > div > ul > li:nth-child(2)")));
        choosePriceButton.click();
        WebElement desiredPriceButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/main/div[1]/div[2]/div/div/ul/li[1]/div/ul/li[3]/button")));
        desiredPriceButton.click();

        //choose place
        WebElement choosePlaceButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/main/div[1]/div[2]/div/div/ul/li[3]/div/button")));
        choosePlaceButton.click();
        driver.findElement(By.cssSelector("#page > main > div.left > div:nth-child(3) > div > div > ul > li:nth-child(4) > div > ul > li:nth-child(2) > button")).click();

        //choose manufacturer and model
        driver.findElement(By.cssSelector("#page > main > div.left > div:nth-child(3) > div > div > ul > div:nth-child(5) > li > ul > li:nth-child(1) > button")).click();
        WebElement desiredModel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#page > main > div.left > div:nth-child(3) > div > div > ul > div:nth-child(5) > li > ul > li:nth-child(1) > ul > li:nth-child(4) > button")));
        desiredModel.click();

        //choose screen size, memory and color
        WebElement chooseScreenSizeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#page > main > div.left > div:nth-child(3) > div > div > ul > div:nth-child(6) > li > ul > li:nth-child(2) > button")));
        chooseScreenSizeButton.click();

        WebElement chooseMemoryButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#page > main > div.left > div:nth-child(3) > div > div > ul > div:nth-child(8) > li > ul > li:nth-child(1) > button")));
        chooseMemoryButton.click();

        WebElement chooseColorButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/main/div[1]/div[2]/div/div/ul/div[15]/li/label")));
        chooseColorButton.click();
        WebElement greenColorButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/div[3]/main/div[1]/div[2]/div/div/ul/div[15]/li/ul[2]/li[6]/button")));
        greenColorButton.click();

        //checking for a match
        String actualUrl = driver.getCurrentUrl();
        assertEquals(expectedUrl, actualUrl);

        driver.quit();
    }

    //5 - registrace test - done (кроме souhlasim)
    @ParameterizedTest
    @CsvSource(
            {"testingmail12345@seznam.com, my_testing_password123, my_testing_password123, 1995",
                    "testingmail12345@seznam.com, my_testing_password123, ABCDEFGHIJK, 2000",
                    "testingmail12345@seznam.com, my_testing_password123, my_testing_password123, 1000"})
    public void registrationTest(String email, String password, String repeatPassword, String yearOfBirth) {
        boolean isCorrectInputs = false;

        WebDriverWait wait = new WebDriverWait(driver, 10);

        //go to https://www.seznam.cz/
        driver.get("https://www.seznam.cz/");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //click on "create new account"
        driver.findElement(By.cssSelector("#boxik-184 > div > div > div.gadget__content > div > a.link.font-14.line-height-20.text-center")).click();

        //choose "new seznam account"
        WebElement newSeznamAccountButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#register > form.intro > button.official")));
        newSeznamAccountButton.click();

        //input email
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#register-username")));
        emailInput.clear();
        emailInput.sendKeys(Keys.COMMAND + "a"); //select the current value in the field
        emailInput.sendKeys(Keys.DELETE); //delete current value
        emailInput.sendKeys(email);

        //input password
        driver.findElement(By.xpath("/html/body/main/section[3]/form[2]/label[2]/input")).sendKeys(password);

        //repeat password
        driver.findElement(By.xpath("/html/body/main/section[3]/form[2]/label[3]/input")).sendKeys(repeatPassword);

        //input year of birth and gender
        driver.findElement(By.xpath("/html/body/main/section[3]/form[2]/label[4]/input")).sendKeys(yearOfBirth);
        driver.findElement(By.cssSelector("#register > form.main > div.gender-row > div > label:nth-child(1) > span")).click();

        //click continue
        driver.findElement(By.cssSelector("#register > form.main > button")).click();

        //wait for the past form to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("/html/body/main/section[3]/form[2]/label[2]/input")));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //if a new form is shown inputs are valid
        if (driver.findElement(By.xpath("/html/body/main/section[3]/form[3]")).isDisplayed()) {
            System.out.println("norm1");
            isCorrectInputs = true;
        }

        if (driver.findElement(By.cssSelector("#register > form.phone")).isDisplayed()) {
            System.out.println("norm2");
            isCorrectInputs = true;
        }

        if (driver.findElement(By.cssSelector("#register > form.phone > label.magic.phone.errorable")).isDisplayed()) {
            System.out.println("norm3");
            isCorrectInputs = true;
        }

        assertTrue(isCorrectInputs, "Invalid inputs!");

        driver.quit();
    }
}
