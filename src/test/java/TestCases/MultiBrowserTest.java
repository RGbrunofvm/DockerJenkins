package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MultiBrowserTest extends BaseTest {

    @Test
    public void testGoogleHomePage() {
        driver.get("https://www.google.com");
        Assert.assertTrue(driver.getTitle().contains("Google"));
    }
}
