package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckOutStepTwoPageUI {
    WebDriver driver;

    public CheckOutStepTwoPageUI(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement itemTotal() {
        return  driver.findElement(By.xpath("//div[@class='summary_subtotal_label']"));
    }

    public WebElement tax() {
        return driver.findElement(By.xpath("//div[@class='summary_tax_label']"));
    }

    public WebElement total() {
        return  driver.findElement(By.xpath("//div[@class='summary_total_label']"));
    }

    public WebElement buttonFinish() {
        return  driver.findElement(By.id("finish"));
    }

    public WebElement nameProduct(int index) {
        return  driver.findElement(By.xpath("(//div[@class='inventory_item_name'])[" + index + "]"));
    }
    public WebElement quantityProduct(int index) {
        return driver.findElement(By.xpath("(//div[@class='cart_quantity'])[" + index + "]"));
    }
    public WebElement priceProduct(int index) {
        return driver.findElement(By.xpath("(//div[@class='inventory_item_price'])[" + index + "]"));
    }

    public WebElement priceProduct2() {
        return driver.findElement(By.xpath("(//div[@class='inventory_item_price'])[2]"));
    }

    public WebElement priceProduct3() {
        return driver.findElement(By.xpath("(//div[@class='inventory_item_price'])[3]"));
    }

}
