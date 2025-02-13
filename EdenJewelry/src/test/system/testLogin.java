package test.system;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testLogin {

    @Test
    public void testLoginSuccess(){
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

        String title = driver.getTitle();

        assertEquals(title, "Profilo Utente");
    }
    
}
