package feature;

import action.AddProductToCart;
import action.InputInformation;
import action.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CheckoutStepTwoTest {
    WebDriver driver;
    WebDriverWait wait;
    public LoginPageUI loginPageUI;
    public InventoryPageUI inventoryPageUI;
    public CartPageUI cartPageUI;
    public CheckoutStepOnePageUI checkoutStepOnePageUI;
    public CheckOutStepTwoPageUI checkOutStepTwoPageUI;
    public CheckoutCompleteUI checkoutCompleteUI;
    public Login login;
    public AddProductToCart addProductToCart;
    public InputInformation inputInformation;
    List<String> productNames;

    // Chuyển đổi giá sản phẩm từ String thành Double
    private double convertToDouble(String priceString) {
        String numberString = priceString.replaceAll("[^0-9\\.]", ""); // Loại bỏ ký tự không phải số
        return Double.parseDouble(numberString);
    }

    // Phương thức kiểm tra thông tin sản phẩm dựa trên chỉ số sản phẩm
    private void checkProductInformation(int index, String expectedName, String expectedPrice) {
        // Lấy tên
        Assert.assertEquals(checkOutStepTwoPageUI.nameProduct(index).getText(), expectedName, "Tên sản phẩm không khớp.");
        // Lấy số lượng
        Assert.assertEquals(checkOutStepTwoPageUI.quantityProduct(index).getText(), "1", "Số lượng sản phẩm không đúng.");
        // Lấy giá
        Assert.assertEquals(checkOutStepTwoPageUI.priceProduct(index).getText(), expectedPrice, "Giá sản phẩm không khớp.");
    }
    // Kiểm tra tổng giá sản phẩm
    private double calculateTotalPrice(int productCount) {
        double total = 0.0;
        for (int i = 1; i <= productCount; i++) {
            String price = driver.findElement(By.xpath("(//div[@class='inventory_item_price'])[" + i + "]")).getText();
            total += convertToDouble(price);
        }
        return total;
    }
    // Tính toán và kiểm tra thuế
    private double calculateTax(double totalPrice, double taxRate) {
        return totalPrice * taxRate / 100;
    }
    @BeforeTest
    public void setURL() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://www.saucedemo.com/");

        loginPageUI = new LoginPageUI(driver);
        inventoryPageUI = new InventoryPageUI(driver);
        cartPageUI = new CartPageUI(driver);
        checkoutStepOnePageUI = new CheckoutStepOnePageUI(driver);
        checkOutStepTwoPageUI = new CheckOutStepTwoPageUI(driver);
        checkoutCompleteUI = new CheckoutCompleteUI(driver);
        login = new Login(driver);
        addProductToCart = new AddProductToCart(driver);
        inputInformation = new InputInformation(driver);
        productNames = new ArrayList<>();
        // Thêm tên sản phẩm vào danh sách
        productNames.add("Sauce Labs Backpack");
        productNames.add("Sauce Labs Bike Light");
        productNames.add("Sauce Labs Bolt T-Shirt");
        login.login();
        addProductToCart.addProductToCart(productNames);
        inputInformation.inputInformation();
    }

    @Test
    public void checkInformationAndTotal() {
        // Kiểm tra thông tin 3 sản phẩm
        checkProductInformation(1, inventoryPageUI.nameProduct1().getText(), inventoryPageUI.priceProduct1().getText());
        checkProductInformation(2, inventoryPageUI.nameProduct2().getText(), inventoryPageUI.priceProduct2().getText());
        checkProductInformation(3, inventoryPageUI.nameProduct3().getText(), inventoryPageUI.priceProduct3().getText());

        // Kiểm tra tổng giá trước thuế
        double expectedTotalPrice = calculateTotalPrice(3);
        String actualItemTotalText = checkOutStepTwoPageUI.itemTotal().getText();
        double actualItemTotal = convertToDouble(actualItemTotalText);
        Assert.assertEquals(actualItemTotal, expectedTotalPrice, "Tổng giá trước thuế không khớp.");

        // Kiểm tra thuế
        double expectedTax = calculateTax(expectedTotalPrice, 8); // 8% thuế
        String actualTaxText = checkOutStepTwoPageUI.tax().getText();
        double actualTax = convertToDouble(actualTaxText);
        Assert.assertEquals(actualTax, expectedTax, 0.01, "Tiền thuế không khớp.");

        // Kiểm tra tổng giá cuối cùng
        double expectedTotal = expectedTotalPrice + expectedTax;
        String actualTotalText = checkOutStepTwoPageUI.total().getText();
        double actualTotal = convertToDouble(actualTotalText);
        Assert.assertEquals(actualTotal, expectedTotal, 0.01, "Tổng giá cuối cùng không khớp.");
    }

    @Test
    public void finishCheckout() {
        // Hoàn tất quá trình thanh toán
        checkOutStepTwoPageUI.buttonFinish().click();
        Assert.assertEquals(checkoutCompleteUI.messageComplete().getText(), "Checkout: Complete!", "Thông báo hoàn tất không chính xác" +
                ".");
    }
    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
