package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InventoryPageUI {
    WebDriver driver;

    public InventoryPageUI(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement buttonAddToCart(String product) {
        return driver.findElement(By.xpath("//div[contains(text(),'"+product+"')]/../../../descendant::button"));
    }

    public WebElement quantityCart() {
        return  driver.findElement(By.xpath("//span[@class='shopping_cart_badge']"));
    }

    public WebElement shoppingCart() {
        return  driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
    }

    public WebElement nameProduct1() {
        return driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']"));
    }

    public WebElement nameProduct2() {
        return driver.findElement(By.xpath("//div[text()='Sauce Labs Bike Light']"));
    }

    public WebElement nameProduct3() {
        return driver.findElement(By.xpath("//div[text()='Sauce Labs Bolt T-Shirt']"));
    }

    public WebElement priceProduct1() {
        return driver.findElement(By.xpath("(//div[@class='inventory_item_price'])[1]"));
    }

    public WebElement priceProduct2() {
        return driver.findElement(By.xpath("(//div[@class='inventory_item_price'])[2]"));
    }

    public WebElement priceProduct3() {
        return driver.findElement(By.xpath("(//div[@class='inventory_item_price'])[3]"));
    }

    public WebElement removeProduct(String product) {
        return driver.findElement(By.xpath("//div[contains(text(),'"+product+"')]/../../../descendant::button"));
    }




}
