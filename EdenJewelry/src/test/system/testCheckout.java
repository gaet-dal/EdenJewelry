package test.system;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testCheckout {

    @Test
    public void testCheckoutSuccess() {
        System.setProperty("webdriver.chrome.driver" ,"C:\\Users\\Gaetano\\IdeaProjects\\EdenJewelry\\EdenJewelry\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        //apre il nostro sito
        driver.navigate().to("http://localhost:8080/EdenJewelry_Web_exploded/HomeServlet");

        //mette il sito a schermo intero
        driver.manage().window().maximize();

        //aspetta il caricamento
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //selezioniamo l'elemento è ci clickiamo
        WebElement profileIcon = driver.findElement(By.cssSelector("body > header > div > a:nth-child(1) > img"));
        profileIcon.click();

        WebElement textBox1 = driver.findElement(By.name("email"));
        WebElement textBox2 = driver.findElement(By.name("password"));

        textBox1.sendKeys("alevit@gmail.com");
        textBox2.sendKeys("password");

        WebElement logButton = driver.findElement(By.tagName("button"));
        logButton.click();

        WebElement home = driver.findElement(By.cssSelector("body > header:nth-child(2) > a:nth-child(1) > img:nth-child(1)"));
        home.click();

        WebElement product = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/a/img"));
        product.click();

        WebElement addToCart = driver.findElement(By.name("carrello"));
        addToCart.click();

        WebElement checkoutButton = driver.findElement(By.className("checkout-button"));
        checkoutButton.click();

        WebElement street = driver.findElement(By.id("via"));
        WebElement number = driver.findElement(By.id("numero"));
        WebElement card = driver.findElement(By.id("carta"));
        WebElement date = driver.findElement(By.id("scadenza"));
        WebElement cvv = driver.findElement(By.id("cvv"));

        street.sendKeys("Napoli");
        number.sendKeys("1");
        card.sendKeys("1234567891234567");
        date.sendKeys("10 25");
        cvv.sendKeys("321");

        WebElement purchaseButton = driver.findElement(By.className("purchase-button"));
        purchaseButton.click();

        String title = driver.getTitle();
        assertEquals("Ordini Effettuati - Eden Jewelry", title);

        driver.close();
        driver.quit();
    }
}
