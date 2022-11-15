package cucumber;
import Screenscapper.Main;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Steps {
    WebDriver driver;

    @Given("I am a user of marketalertum")
    public void gettingtomarketlertum() throws Throwable {
        System.setProperty("webdriver.chrome.driver", "C:/Program Files/chromedriver/chromedriver.exe");

        driver = new ChromeDriver();
        driver.get("https://www.marketalertum.com");

        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);

        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);
    }

    @When("I login using valid credentials")
    public void valid() throws Throwable {
        WebElement creditals = driver.findElement(By.name("UserId"));
        creditals.sendKeys("157831f5-179f-4640-9634-98d2801da987");
        WebElement submitButton = driver.findElement(By.name("__RequestVerificationToken"));
        submitButton.submit();
    }

    @Then("I should see my alerts")
    public void seealerts() throws Throwable {
        String currentUrl = driver.getCurrentUrl();
        String result = "https://www.marketalertum.com/Alerts/List";
        Assertions.assertEquals(currentUrl, result);
    }

    @When("I login using invalid credentials")
    public void Invalid() throws Throwable {
        WebElement InvalidCreditals = driver.findElement(By.name("UserId"));
        InvalidCreditals.sendKeys("Invalid-:))");
        WebElement submitButton = driver.findElement(By.name("__RequestVerificationToken"));
        submitButton.submit();
    }

    @Then("I should see the login screen again")
    public void SamePage() throws Throwable {
        String currentUrl = driver.getCurrentUrl();
        String result = "https://www.marketalertum.com/Alerts/Login";
        Assertions.assertEquals(currentUrl, result);
    }

    @When("I am an administrator of the website and I upload {int} alerts")
    @When("I am an administrator of the website and I upload more than {int} alerts")
    public void upload3(int arg0) throws JSONException, InterruptedException {
        if (arg0 == 5) {
            arg0 = arg0 + 2;
        }
        Thread.sleep(5000);

        System.setProperty("webdriver.chrome.driver", "C:/Program Files/chromedriver/chromedriver.exe");

        driver = new ChromeDriver();
        driver.get("https://www.amazon.com/");

        driver.findElement(By.id("icp-touch-link-cop")).click();
        driver.findElement(By.id("icp-currency-dropdown-selected-item-prompt")).click();

        driver.findElement(By.id("EUR")).click();
        driver.findElement(By.id("icp-save-button")).click();
        Thread.sleep(3000);

        WebElement testDropDown = driver.findElement(By.id("searchDropdownBox"));
        Select dropdown = new Select(testDropDown);
        dropdown.selectByVisibleText("Electronics");


        String alertTypeTopic[] = {"Automotive", "Boat", "PropertyForRent", "PropertyForSale", "Toys & Games", "Electronics"};


        dropdown.selectByVisibleText(alertTypeTopic[5]);

        WebElement searchField = driver.findElement(By.id("twotabsearchtextbox"));
        searchField.sendKeys("laptop");

        driver.findElement(By.id("nav-search-submit-button")).click();
        driver.findElement(By.xpath("//*[@id=\"p_n_condition-type/2224371011\"]/span/a")).click();

        String[] productNumber = {"2", "7", "8", "9", "10" , "11", "12"};
        for (int i = 0; i < arg0; i++) {
            Thread.sleep(3000);


            driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[" + productNumber[i] + "]/div/div/div/div/div/div[2]/div/div/div[1]/h2/a")).click();

            String heading = driver.findElement(By.id("productTitle")).getText();


            String description = driver.findElement(By.id("feature-bullets")).getText();


            String url = driver.getCurrentUrl();

            String Imageurl = driver.findElement(By.xpath("//*[@id=\"landingImage\"]")).getAttribute("src");


            String soldby = driver.findElement(By.id("bylineInfo")).getText();


            String euros = driver.findElement(By.id("twister-plus-price-data-price")).getAttribute("value");


            double eurosInCents = Double.parseDouble(euros) * 100;
            int centInString = (int) eurosInCents;


            JSONObject post = new JSONObject();
            post.put("alertType", 6);
            post.put("heading", heading);
            post.put("description", description);
            post.put("url", url);
            post.put("imageurl", Imageurl);
            post.put("postedBy", "157831f5-179f-4640-9634-98d2801da987");
            post.put("priceInCents", centInString);

            String json = post.toString();

            String query_url = "https://api.marketalertum.com/Alert";


            try {
                URL Apiurl = new URL(query_url);
                HttpURLConnection conn = (HttpURLConnection) Apiurl.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                OutputStream os = conn.getOutputStream();
                os.write(json.getBytes("UTF-8"));
                os.close();
                // read the response
                InputStream in = new BufferedInputStream(conn.getInputStream());
                String result = IOUtils.toString(in, "UTF-8");
                System.out.println(result);
                System.out.println("result after Reading JSON Response");
                JSONObject myResponse = new JSONObject(result);
                in.close();
                conn.disconnect();
            } catch (Exception e) {
                System.out.println(e);
            }
            eurosInCents = 0;
            String new_url = driver.getCurrentUrl();
            driver.get(new_url);


            driver.navigate().back();
        }
    }

    @When("I view a list of alerts")
    public void viewalerts() throws Throwable {

        WebElement creditals = driver.findElement(By.name("UserId"));
        creditals.sendKeys("157831f5-179f-4640-9634-98d2801da987");
        WebElement submitButton = driver.findElement(By.name("__RequestVerificationToken"));
        submitButton.submit();
    }


    @Then("each alert should contain an icon")

    public void Icon() throws Throwable {
        for (int i = 1; i < 4; i++) {
            String icon = driver.findElement(By.xpath("/html/body/div/main/table[" + i + "]/tbody/tr[1]/td/h4/img")).getAttribute("src");
            String iconImage = "https://www.marketalertum.com/images/icon-electronics.png";
            Assertions.assertEquals(icon, iconImage);
        }
    }


    @And("each alert should contain a heading")
    public void Heading() throws Throwable {
        for (int i = 1; i < 4; i++) {
            String heading = driver.findElement(By.xpath("/html/body/div/main/table[" + i + "]/tbody/tr[1]/td/h4")).getText();
            System.out.println(heading);
            System.out.println(heading.isEmpty());

            Assertions.assertEquals(heading.isEmpty(), false);
        }
    }

    @And("each alert should contain a description")
    public void description() throws Throwable {
        for (int i = 1; i < 4; i++) {
            String description = driver.findElement(By.xpath("/html/body/div/main/table[" + i + "]/tbody/tr[3]/td")).getText();
            System.out.println(description);
            System.out.println(description.isEmpty());

            Assertions.assertEquals(description.isEmpty(), false);
        }
    }

    @And("each alert should contain an image")
    public void image() throws Throwable {
        for (int i = 1; i < 4; i++) {
            String image = driver.findElement(By.xpath("/html/body/div/main/table[" + i + "]/tbody/tr[2]/td/img")).getAttribute("src");
            System.out.println(image);
            System.out.println(image.isEmpty());

            Assertions.assertEquals(image.isEmpty(), false);
        }
    }

    @And("each alert should contain a price")
    public void price() throws Throwable {
        for (int i = 1; i < 4; i++) {
            String price = driver.findElement(By.xpath("/html/body/div/main/table[" + i + "]/tbody/tr[4]/td")).getText();
            System.out.println(price);
            System.out.println(price.isEmpty());

            Assertions.assertEquals(price.isEmpty(), false);
        }
    }

    @And("each alert should contain a link to the original product website")
    public void link() throws Throwable {
        for (int i = 1; i < 4; i++) {
            String link = driver.findElement(By.xpath("/html/body/div/main/table[" + i + "]/tbody/tr[5]/td/a")).getAttribute("href");
            System.out.println(link);
            System.out.println(link.isEmpty());

            Assertions.assertEquals(link.isEmpty(), false);
        }
    }

    @When("I view a list of alerts I should see {int} alerts")
    @Then("I should see {int} alerts")
    public void alertCount(int arg0) throws Throwable {
      int i =1 ;
        do {
            driver.findElement(By.xpath("/html/body/div/main/table[" + i + "]"));

            i++;
      }while( i == arg0);
        }



    @When("I am an administrator of the website and I upload an alert of type {int}")
    public void type(int alerttype) throws JSONException, InterruptedException {




        String alertTypeTopic[] = {"Automotive", "Boat", "PropertyForRent", "PropertyForSale", "Toys & Games", "Electronics"};





        JSONObject post = new JSONObject();
        post.put("alertType", alerttype);
        post.put("postedBy", "157831f5-179f-4640-9634-98d2801da987");




        String json = post.toString();

        String query_url = "https://api.marketalertum.com/Alert";


        try {
            URL Apiurl = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) Apiurl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = IOUtils.toString(in, "UTF-8");
            System.out.println(result);
            System.out.println("result after Reading JSON Response");
            JSONObject myResponse = new JSONObject(result);
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    @And("the icon displayed should be {word}")
    public void type(String iconfilename) {
        String icon = driver.findElement(By.xpath("/html/body/div/main/table[1]/tbody/tr[1]/td/h4/img")).getAttribute("src");
        String iconImage = "https://www.marketalertum.com/images/"+iconfilename;
        Assertions.assertEquals(icon, iconImage);
    }
}




