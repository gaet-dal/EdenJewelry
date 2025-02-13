package test.system;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testRegister {

    @Test
    public void testRegisterSuccess(){
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

        WebElement registerLink = driver.findElement(By.className("link1"));
        registerLink.click();

        WebElement textboxName = driver.findElement(By.name("nome"));
        WebElement textboxCognome = driver.findElement(By.name("cognome"));
        WebElement textboxEmail = driver.findElement(By.name("email"));
        WebElement textboxPassword = driver.findElement(By.name("password"));

        textboxName.sendKeys("Alessandro");
        textboxCognome.sendKeys("Di Palma");
        textboxEmail.sendKeys("alepal@gmail.com");
        textboxPassword.sendKeys("password");

        WebElement logButton = driver.findElement(By.name("submitRegistrazione"));
        logButton.click();

        String title = driver.getTitle();

        assertEquals(title, "EdenJewelry");

        driver.close();
        driver.quit();
    }

}
