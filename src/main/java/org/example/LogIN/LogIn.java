package org.example.LogIN;

import com.opencsv.exceptions.CsvException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;


public class LogIn {

    @Test(dataProvider = "loginDataFromCSV")
    public static void LogIn_fun(String username,String password ,String message ) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.petfinder.com/");
        Thread.sleep(500);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
        driver.manage().window().maximize();
        //first window
        Thread.sleep(200);

        driver.findElement(By.xpath("//button[@aria-label='sign in']")).click();
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(200);

        driver.findElement(By.xpath("(//div//input[@id='signInFormUsername'])[2]")).click();


        driver.findElement(By.xpath("(//div//input[@id='signInFormUsername'])[2]")).sendKeys(username);
        driver.findElement(By.xpath("(//div//input[@id='signInFormPassword'])[2]")).click();

        driver.findElement(By.xpath("(//div//input[@id='signInFormPassword'])[2]")).sendKeys(password);
        driver.findElement(By.xpath("(//input[@name='signInSubmitButton'])[2]")).click();

        //wrong user on password
        if (message ==String.valueOf(0))
        {
            Assert.assertTrue(driver.findElement(By.xpath("(//p[@id='loginErrorMessage'])[1]")).isDisplayed(), "Incorrect username or password.");

        }
        //nothing happened
        else if (message ==String.valueOf(1)) {
            Assert.assertFalse(driver.findElement(By.xpath("(//p[@id='loginErrorMessage'])[1]")).isDisplayed(), "Incorrect username or password.");
            Assert.assertFalse(driver.findElement(By.id("loginErrorMessage")).isDisplayed(), "User does not exist.");
            Assert.assertNotEquals(driver.findElement(By.xpath("//span[@class='ptw-text-body-sm ptw-text-surface-textSubdued']\n")).getText(),"Good morning,");

        }
        //right
        else if (message == String.valueOf(2)){
            // Check if the error message is displayed
            driver.findElement(By.xpath("//button[contains(@class, 'ptw-bg-primary-base')]")).click();
            Assert.assertEquals(driver.findElement(By.xpath("//span[@class='ptw-text-body-sm ptw-text-surface-textSubdued']\n")).getText(),"Good morning,");
        }
        else if (message == String.valueOf(3)){
            Assert.assertTrue(driver.findElement(By.id("loginErrorMessage")).isDisplayed(), "User does not exist.");

        }
        Thread.sleep(200);
        driver.quit();
    }

    public static void forget_password(Boolean choise) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.petfinder.com/");
        Thread.sleep(500);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
        driver.findElement(By.xpath("//button[@aria-label='sign in']")).click();
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath("(//a[@class='redirect-customizable'])[2]")).click();
        driver.findElement(By.xpath("//h1[text()='Forgot your password?']")).click();
        driver.findElement(By.id("username")).click();
        if (choise ) {
            driver.findElement(By.id("username")).sendKeys("a@gmail.com");
            driver.findElement(By.name("reset_my_password")).click();
            Thread.sleep(500);

            Assert.assertEquals(driver.findElement(By.xpath("//label[@for='forgot_password_code']")).getText(), "Code");
        }else {
            driver.findElement(By.id("username")).sendKeys("adh");
            driver.findElement(By.name("reset_my_password")).click();
            Thread.sleep(200);
            Assert.assertEquals(driver.findElement(By.id("errorMessage")).getText(), "Could not reset password for the account, please contact support or try again");

        }
driver.quit();
    }

    @DataProvider(name = "loginDataFromCSV")
    public Object[][] provideDataFromCSV() throws IOException, CsvException {
        return CSVHelper_login.getCSVData("target/classes/org/example/TestData/login.csv"); // Update with the actual file path
    }

}
