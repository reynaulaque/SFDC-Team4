package com.selenium.course.pages;

import com.selenium.course.framework.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static com.selenium.course.common.Globals.TIMEOUT_MIN;
import com.selenium.course.framework.DriverManager;
import static com.selenium.course.common.Globals.TIMEOUT_NORMAL;
import java.util.concurrent.TimeUnit;



public class OpportunityTablePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "ext-gen11")
    @CacheLookup
    WebElement opportunityTable;

    public OpportunityTablePage() {
        wait = DriverManager.getInstance().getWait();
        driver = DriverManager.getInstance().getDriver();
        PageFactory.initElements(driver, this);
        try {
            wait.withTimeout(TIMEOUT_MIN, TimeUnit.SECONDS).until(
                    ExpectedConditions.visibilityOf(opportunityTable));
        } catch (WebDriverException e) {
            throw new WebDriverException(e);
        } finally {
            wait.withTimeout(TIMEOUT_NORMAL, TimeUnit.SECONDS);
        }
    }

    public OpportunityDetail clickOpportunity(String opportunity) {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(.,'" + opportunity + "')]"))));
        driver.findElement(By.xpath("//span[contains(.,'" + opportunity + "')]")).click();
        return new OpportunityDetail();
    }
}

