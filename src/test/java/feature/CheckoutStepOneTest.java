package feature;

import action.AddProductToCart;
import action.Login;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import ui.CheckoutStepOnePageUI;
import utils.ExcelUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckoutStepOneTest {
    private WebDriver driver;
    private CheckoutStepOnePageUI checkoutStepOnePageUI;
    List<String> productNames;
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://www.saucedemo.com/");

        // Khởi tạo các đối tượng UI và action
        checkoutStepOnePageUI = new CheckoutStepOnePageUI(driver);
        Login login = new Login(driver);
        AddProductToCart addProductToCart = new AddProductToCart(driver);
        productNames = new ArrayList<>();
        // Thêm tên sản phẩm vào danh sách
        productNames.add("Sauce Labs Backpack");
        productNames.add("Sauce Labs Bike Light");
        productNames.add("Sauce Labs Bolt T-Shirt");
        // Đăng nhập và thêm sản phẩm vào giỏ
        login.login();
        addProductToCart.addProductToCart(productNames);
    }

    @DataProvider(name = "demoData")
    public Object[][] provideTestData() {
        String excelFilePath = "inputInformation.xlsx";
        String sheetName = "Sheet1";

        try {
            // Đọc dữ liệu từ file Excel
            List<Map<String, String>> excelData = ExcelUtils.readExcelData(excelFilePath, sheetName);
            Object[][] data = new Object[excelData.size()][1];
            for (int i = 0; i < excelData.size(); i++) {
                data[i][0] = excelData.get(i);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi đọc file Excel: " + e.getMessage());
        }
    }

    private void fillForm(Map<String, String> rowData) {
        if (rowData == null) {
            throw new IllegalArgumentException("Dữ liệu đầu vào không hợp lệ!");
        }

        try {
            checkoutStepOnePageUI.inputFirstName().sendKeys(rowData.get("First Name"));
            checkoutStepOnePageUI.inputLastName().sendKeys(rowData.get("Last Name"));
            checkoutStepOnePageUI.inputCode().sendKeys(rowData.get("Zip/Postal Code"));
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi điền form: " + e.getMessage());
        }
    }

    private void validateErrorMessage(String expectedError) {
        String actualError = checkoutStepOnePageUI.errorMessage().getText();
        Assert.assertEquals(actualError, expectedError, "Thông báo lỗi không khớp.");
    }

    @Test(dataProvider = "demoData")
    public void testInformationSuccessful(Map<String, String> rowData) {
        fillForm(rowData);
        checkoutStepOnePageUI.buttonContinue().click();
    }

    @Test
    public void testContinueButtonWithoutFillingForm() {
        checkoutStepOnePageUI.buttonContinue().click();
        validateErrorMessage("Error: First Name is required");
    }

    @Test(dataProvider = "demoData")
    public void testFirstNameNull(Map<String, String> rowData) {
        rowData.put("First Name", "");
        fillForm(rowData);
        checkoutStepOnePageUI.buttonContinue().click();
        validateErrorMessage("Error: First Name is required");
    }

    @Test(dataProvider = "demoData")
    public void testLastNameNull(Map<String, String> rowData) {
        rowData.put("Last Name", "");
        fillForm(rowData);
        checkoutStepOnePageUI.buttonContinue().click();
        validateErrorMessage("Error: Last Name is required");
    }

    @Test(dataProvider = "demoData")
    public void testZipCodeNull(Map<String, String> rowData) {
        rowData.put("Zip/Postal Code", "");
        fillForm(rowData);
        checkoutStepOnePageUI.buttonContinue().click();
        validateErrorMessage("Error: Postal Code is required");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
