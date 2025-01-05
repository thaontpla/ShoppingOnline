package feature;

import action.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import ui.CartPageUI;
import ui.InventoryPageUI;

import java.util.ArrayList;
import java.util.List;

public class AddToCartTest {
    private WebDriver driver;
    private InventoryPageUI inventoryPageUI;
    private CartPageUI cartPageUI;
    List<String> productNames;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        inventoryPageUI = new InventoryPageUI(driver);
        cartPageUI = new CartPageUI(driver);
        Login login = new Login(driver);
        // Đăng nhập
        login.login();
        productNames = new ArrayList<>();
        // Thêm tên sản phẩm vào danh sách
        productNames.add("Sauce Labs Backpack");
        productNames.add("Sauce Labs Bike Light");
    }

    @Test
    public void CustomerAddedTwoNewProductsSuccessfully() {
        // Thêm sản phẩm
        inventoryPageUI.buttonAddToCart(productNames.get(0)).click();
        inventoryPageUI.buttonAddToCart(productNames.get(1)).click();

        // Kiểm tra giỏ hàng chứa đúng số lượng
        validateCartQuantity("2");

        // Mở giỏ hàng
        inventoryPageUI.shoppingCart().click();

        // Kiểm tra thông tin sản phẩm
        validateProductDetails(
                inventoryPageUI.nameProduct1(),
                inventoryPageUI.priceProduct1(),
                cartPageUI.nameProduct1(),
                cartPageUI.priceProduct1()
        );

        validateProductDetails(
                inventoryPageUI.nameProduct2(),
                inventoryPageUI.priceProduct2(),
                cartPageUI.nameProduct2(),
                cartPageUI.priceProduct2()
        );
    }

    @Test
    public void CustomerRemoveOneProductSuccessfully() {
        // Thêm 2 sản phẩm vào giỏ hàng
        inventoryPageUI.buttonAddToCart(productNames.get(0)).click();
        inventoryPageUI.buttonAddToCart(productNames.get(1)).click();

        // Xóa sản phẩm 2
        inventoryPageUI.removeProduct(productNames.get(1)).click();

        // Kiểm tra số lượng sản phẩm trong giỏ hàng
        validateCartQuantity("1");

        // Mở giỏ hàng
        inventoryPageUI.shoppingCart().click();

        // Kiểm tra thông tin sản phẩm 1
        validateProductDetails(
                inventoryPageUI.nameProduct1(),
                inventoryPageUI.priceProduct1(),
                cartPageUI.nameProduct1(),
                cartPageUI.priceProduct1()
        );

        // Kiểm tra sản phẩm 2 đã bị xóa
        List<WebElement> products = driver.findElements(By.xpath("//*[text()='Sauce Labs Bike Light']"));
        Assert.assertTrue(products.isEmpty(), "The product 'Sauce Labs Bike Light' is unexpectedly found.");
    }

    private void validateCartQuantity(String expectedQuantity) {
        String actualQuantity = inventoryPageUI.quantityCart().getText();
        Assert.assertEquals(actualQuantity, expectedQuantity, "Cart quantity mismatch");
    }

    private void validateProductDetails(WebElement nameInventory, WebElement priceInventory, WebElement nameCart, WebElement priceCart) {
        Assert.assertEquals(nameCart.getText(), nameInventory.getText(), "Product name mismatch");
        Assert.assertEquals(priceCart.getText(), priceInventory.getText(), "Product price mismatch");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
