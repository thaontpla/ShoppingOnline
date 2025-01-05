package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutCompleteUI {
    WebDriver driver;

    public CheckoutCompleteUI(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement messageComplete() {
        return  driver.findElement(By.xpath("//span[@class='title']"));
    }
}
