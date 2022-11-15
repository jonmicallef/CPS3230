package Screenscapper;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main {

    WebDriver driver;

    @BeforeEach
    public void setup() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:/Program Files/chromedriver/chromedriver.exe");

        driver = new ChromeDriver();

        driver.get("https://www.amazon.com/");


    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }



    @Test
    public void testproductfinding() throws Exception {


        Thread.sleep(1000);

//changing the price to eurs for them all to be the same
        driver.findElement(By.id("icp-touch-link-cop")).click();
        driver.findElement(By.id("icp-currency-dropdown-selected-item-prompt")).click();

        driver.findElement(By.id("EUR")).click();
        driver.findElement(By.id("icp-save-button")).click();
        Thread.sleep(1500);
//        picking a catagory so that the alert types is accurate

        WebElement testDropDown = driver.findElement(By.id("searchDropdownBox"));
        Select dropdown = new Select(testDropDown);
        dropdown.selectByVisibleText("Electronics");



//making an array of alert types to use
        String alertTypeTopic[] = {"Automotive","Boat","PropertyForRent","PropertyForSale", "Toys & Games","Electronics"};


        dropdown.selectByVisibleText(alertTypeTopic[5]);

//        searching for the object in that catagory
        WebElement searchField = driver.findElement(By.id("twotabsearchtextbox"));
        searchField.sendKeys("laptop");

        driver.findElement(By.id("nav-search-submit-button")).click();
            driver.findElement(By.xpath("//*[@id=\"p_n_condition-type/2224371011\"]/span/a")).click();

//            finding 5 objects and posting them to the marketlum
        String[] productNumber ={"2", "3","4","7","8"};
        for (int i = 0; i < 3; i++) {


try {
    driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[" + productNumber[i] + "]/div/div/div/div/div/div[2]/div/div/div[1]/h2/a")).click();
} catch (Exception e) {
    throw new RuntimeException(e);
}

            String heading = driver.findElement(By.id("productTitle")).getText();

            System.out.println("\n" + heading);


            String description = driver.findElement(By.id("feature-bullets")).getText();
            System.out.println(description);


            String url = driver.getCurrentUrl();
            System.out.println(url);

            String Imageurl = driver.findElement(By.xpath("//*[@id=\"landingImage\"]")).getAttribute("src");
            System.out.println(Imageurl);




            String soldby = driver.findElement(By.id("bylineInfo")).getText();
            System.out.println("\n" + soldby);




            String euros = driver.findElement(By.id("twister-plus-price-data-price")).getAttribute("value");


            double eurosInCents = Double.parseDouble(euros) * 100;
            int centInString = (int) eurosInCents;
            System.out.println(eurosInCents);


            JSONObject post = new JSONObject();
            post.put("alertType", 6);
            post.put("heading", heading);
            post.put("description", description);
            post.put("url", url);
            post.put("imageurl", Imageurl);
            post.put("postedBy", "157831f5-179f-4640-9634-98d2801da987");
            post.put("postedBy", "soldby");
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
}