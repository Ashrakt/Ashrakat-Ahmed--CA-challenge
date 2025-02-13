package org.example.Petfinder_Register;

import com.opencsv.exceptions.CsvException;
import org.example.LogIN.CSVHelper_login;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

public class Register {

    @Test(dataProvider = "RegisterDataFromCSV")
    public static void Register_fun(String email, String firstname, String lastname, String postal, String cats, String dogs, String password, String message) throws InterruptedException
    {
        WebDriver driver ;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        // Set user-agent to make it look like a real user
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        // Disable extensions
        options.addArguments("--disable-extensions");
        driver = new ChromeDriver(options);
        driver.get("https://www.petfinder.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2500));
        driver.findElement(By.xpath("//button[@aria-label='sign in']")).click();
        driver.findElement(By.xpath("//button[text()='Create Account']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//h1[text()='Get Started']")).getText(),"Get Started");
        Assert.assertEquals(driver.findElement(By.cssSelector("p[class='pds-text-surface-textSubdued']")).getText(),"It’s easy and convenient – create a single account to access all Purina platforms.");
        Assert.assertEquals(driver .findElement(By.cssSelector("label[for='emailAddress']")).getText(),"Email Address");
        driver.findElement(By.id("emailAddress")).click();

        driver.findElement(By.id("emailAddress")).sendKeys(email);
        driver.findElement(By.xpath("//button[contains(@class, 'pds-bg-primary')]")).click();
        Thread.sleep(200);
        if(Objects.equals(message, String.valueOf(0))){
            Assert.assertEquals(driver.findElement(By.id("radix-:R77rqjqrkqH1:")).getText(),"We recognize you!");
        }//mail wrong format
         else if (Objects.equals(message, String.valueOf(1))) {
            Assert.assertEquals(driver.findElement(By.xpath("//span[@class='error-text']")).getText(),"Email is missing @");
        }
         else if (Objects.equals(message, String.valueOf(2))) {
            Assert.assertEquals(driver.findElement(By.xpath("//span[@class='error-text']")).getText(),"Email is missing a domain ex: .com");
        } else if (Objects.equals(message, String.valueOf(3))) {
            Assert.assertEquals(driver.findElement(By.xpath("//span[@class='error-text']")).getText(),"Please provide an email in this format: name@example.com");
        }
         if(!Objects.equals(message, String.valueOf(0)) &&!Objects.equals(message, String.valueOf(1))&&!Objects.equals(message, String.valueOf(2))&&!Objects.equals(message, String.valueOf(3))) {
        //correct email
             Thread.sleep(300);

//            Assert.assertEquals(driver.findElement(By.xpath("//h1[contains(@class,'pds-text-title-sm')]")).getText(),"Tell us about yourself");
            Assert.assertEquals(driver.findElement(By.xpath("//label[@for='firstName']")).getText(),"First Name");
            driver.findElement(By.id("firstName")).click();
            driver.findElement(By.id("firstName")).sendKeys(firstname);
            Assert.assertEquals(driver.findElement(By.xpath("//label[@for='lastName']")).getText(),"Last Name");
            driver.findElement(By.id("lastName")).click();
            driver.findElement(By.id("lastName")).sendKeys(lastname);
            Assert.assertEquals(driver.findElement(By.xpath("//label[@for='postalCode']")).getText(),"ZIP/Postal Code");
            driver.findElement(By.id("postalCode")).click();
            driver.findElement(By.id("postalCode")).sendKeys(postal);

            if (Objects.equals(message, String.valueOf(4))){
                Thread.sleep(200);

                //MISSING FIRST
                driver.findElement(By.id("firstName")).sendKeys(Keys.CONTROL + "a");
                driver.findElement(By.id("firstName")).sendKeys(Keys.BACK_SPACE);
                Assert.assertEquals(driver.findElement(By.xpath("//span[@class='error-text']")).getText(),"First Name is required");
                driver.findElement(By.id("firstName")).sendKeys(firstname);
                //MISSING SECOND
                driver.findElement(By.id("lastName")).sendKeys(Keys.CONTROL + "a");
                driver.findElement(By.id("lastName")).sendKeys(Keys.BACK_SPACE);
                Assert.assertEquals(driver.findElement(By.xpath("//span[@class='error-text']")).getText(),"Last Name is required");
                driver.findElement(By.id("lastName")).sendKeys(lastname);

                //MISSING POSTAL
                driver.findElement(By.id("postalCode")).sendKeys(Keys.CONTROL + "a");
                driver.findElement(By.id("postalCode")).sendKeys(Keys.BACK_SPACE);
                Assert.assertEquals(driver.findElement(By.xpath("//span[@class='error-text']")).getText(),"ZIP/Postal Code is required");
                driver.findElement(By.id("postalCode")).sendKeys(postal);
                //missing cats and dogs
                Assert.assertFalse(driver.findElement(By.xpath("//button[@type='submit']")).isEnabled());

            } else if (Objects.equals(message, String.valueOf(6))) {//wrong postal code
                Assert.assertEquals(driver.findElement(By.xpath("//span[@class='error-text']")).getText(), "Must enter valid US ZIP or CA Postal Code. ex: 55555 or A1A 1A1");
            }

            if (message != String.valueOf(5) && message == String.valueOf(6)) {//password page tests
                driver.findElement(By.xpath("//select[@name ='dogCount']//option[@value='"+dogs+"']")).click();
                driver.findElement(By.id("catCount-trigger")).click();
                driver.findElement(By.xpath("//select[@name ='catCount']//option[@value='"+cats+"']")).click();
                Assert.assertTrue(driver.findElement(By.xpath("//button[@type='submit']")).isEnabled());
                Assert.assertEquals(driver.findElement(By.xpath("//button[@type='submit']")).getText(), "Continue");
                driver.findElement(By.xpath("//button[@type='submit']")).click();

                Assert.assertTrue(driver.findElement(By.xpath("//h1[@class='pds-text-title-sm pds-font-extrabold']")).isDisplayed());
                if (message == String.valueOf(7)){
                    driver.findElement(By.id("password")).click();
                    driver.findElement(By.id("password")).sendKeys(password);
                    driver.findElement(By.id("confirmPassword")).sendKeys(password);
                    driver.findElement(By.id("terms")).click();
                    driver.findElement(By.xpath("//button[@type='submit']")).click();

                    Assert.assertTrue(driver.findElement(By.xpath("//svg/path[@fill='none']")).isDisplayed());
                }
                if (message == String.valueOf(8)){
                    driver.findElement(By.id("password")).click();
                    driver.findElement(By.id("password")).sendKeys(password);
                    driver.findElement(By.id("confirmPassword")).sendKeys(password);
                    driver.findElement(By.id("terms")).click();
                    driver.findElement(By.xpath("//button[@type='submit']")).click();
                    Assert.assertEquals(driver.findElement(By.xpath("//h1[@class='pds-text-title-sm pds-font-extrabold']")).getText(),"Verify your email");
                }


            }


        }




        Thread.sleep(300);

        driver.quit();
    }

    @DataProvider(name = "RegisterDataFromCSV")
    public Object[][] provideDataFromCSV() throws IOException, CsvException {
        return CSVHelper_Register.getCSVData("target/classes/org/example/TestData/Register.csv"); // Update with the actual file path
    }
 }
