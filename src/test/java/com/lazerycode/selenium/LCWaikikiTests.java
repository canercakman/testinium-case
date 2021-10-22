package com.lazerycode.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LCWaikikiTests {
    private static final String URL = "https://www.lcwaikiki.com/tr-TR/TR";
    private static final String email = "canercakman@gmail.com";
    private static final String password = "kskksk35";

    @Test
    public void testiniumCase() {
        // driver oluşturuldu
        WebDriver driver = new ChromeDriver();
        // url'e gidiyoruz
        driver.get(URL);

        // sayfa tam olarak açıldı mı kontrolü
        if (driver.getTitle() != null && driver.getTitle().contains("LC Waikiki")) {
            Actions action = new Actions(driver);

            // giriş yap butonunu bulduk
            WebElement element = driver.findElement(By.linkText("Giriş Yap"));
            // giriş yap butonuna tıkladık
            action.click(element).build().perform();

            // sayfa başarılı yüklendi mi kontrol ettik
            if (driver.getTitle() != null && driver.getTitle().contains("Üye Girişi")) {
                // email ve paralo gireceğimiz alanları seçtik
                WebElement emailText = driver.findElement(By.id("LoginEmail"));
                WebElement passwordText = driver.findElement(By.id("Password"));

                // alanı temizleyip mail adresi girildi
                emailText.clear();
                emailText.sendKeys(email);

                // alanı temizleyip parola girildi
                passwordText.clear();
                passwordText.sendKeys(password);

                // login butonuna basıldı
                element = driver.findElement(By.id("loginLink"));
                action.click(element).build().perform();

                // başarılı giriş yapıldı mı kontrolü
                if (driver.findElement(By.className("dropdown-label")).getText().equalsIgnoreCase("Hesabım")) {
                    element = driver.findElement(By.id("search_input"));
                    element.clear();
                    element.sendKeys("pantolan");

                    element = driver.findElement(By.className("searchButton"));
                    action.click(element).build().perform();

                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("window.scrollBy(0,100000)");

                    element = driver.findElement(By.className("lazy-load-button"));
                    action.click(element).build().perform();

                    js.executeScript("window.scrollBy(0,1000)");

                    element = driver.findElement(By.cssSelector("#model_2141054_5241549 > div.info > div > div.title"));
                    action.click(element).build().perform();

                    element = driver.findElement(By.linkText("12-18 Ay"));
                    action.click(element).build().perform();

                    element = driver.findElement(By.id("pd_add_to_cart"));
                    action.click(element).build().perform();


                    element = driver.findElement(By.cssSelector("#rightInfoBar > div:nth-child(1) > div > div:nth-child(3) > div > div > div > span.price"));
                    String price = element.getText();

                    element = driver.findElement(By.cssSelector("body > div:nth-child(13) > div.container-fluid.header > div:nth-child(2) > div.col-md-3.col-sm-3.col-xs-6.col-lg-2.pull-right > div > div.col-sm-4.col-xs-4.header-cart-section > a"));
                    action.click(element).build().perform();

                    element = driver.findElement(By.cssSelector("#ShoppingCartContent > div.row.mt-20.main-content-row > div.col-md-4.desktop-price-info > div.price-info-area > div.row.grand-total.mt-10.mb-10 > div > span.pull-right"));
                    String basketTotalPrice = element.getText();

                    if (price.equalsIgnoreCase(basketTotalPrice)) {
                        element = driver.findElement(By.className("cart-square-link"));
                        action.click(element).build().perform();

                        element = driver.findElement(By.linkText("Sil"));
                        action.click(element).build().perform();
                        element = driver.findElement(By.className("cart-empty-title"));
                        if (!element.getText().equalsIgnoreCase("Sepetinizde ürün bulunmamaktadır.")) {
                            Assert.fail();
                        }
                    } else {
                        Assert.fail();
                    }
                } else {
                    Assert.fail();
                }
            } else {
                Assert.fail();
            }
        } else {
            Assert.fail();
        }
    }
}
