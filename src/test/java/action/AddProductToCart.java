package action;

import org.openqa.selenium.WebDriver;
import ui.CartPageUI;
import ui.InventoryPageUI;
import ui.LoginPageUI;

import java.util.List;

public class AddProductToCart {
    WebDriver driver;
    LoginPageUI loginPageUI;
    CartPageUI cartPageUI;
    InventoryPageUI inventoryPageUI;

    public AddProductToCart(WebDriver driver) {
        this.driver = driver;
        loginPageUI = new LoginPageUI(driver);
        inventoryPageUI = new InventoryPageUI(driver);
        cartPageUI = new CartPageUI(driver);
    }
    public void addProductToCart(List<String> products) {
        for (String product:products) {
            inventoryPageUI.buttonAddToCart(String.valueOf(product)).click();
        }
        inventoryPageUI.shoppingCart().click();
        cartPageUI.buttonCheckout().click();
    }
}
