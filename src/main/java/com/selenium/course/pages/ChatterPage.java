package com.selenium.course.pages;

import com.selenium.course.framework.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 6/22/2015.
 */
public class ChatterPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id ="publishereditablearea")
    @CacheLookup
    protected WebElement postChatTextArea;

    @FindBy(id ="publishersharebutton")
    @CacheLookup
    protected WebElement shareButton;

    public ChatterPage(){
        wait = DriverManager.getInstance().getWait();
        driver = DriverManager.getInstance().getDriver();
        PageFactory.initElements(driver, this);
        try {
            wait.withTimeout(5, TimeUnit.SECONDS).until(
                    ExpectedConditions.visibilityOf(postChatTextArea));

        } catch (WebDriverException e) {
            throw new WebDriverException(e);
        } finally {
            wait.withTimeout(15, TimeUnit.SECONDS);
        }
    }

    public WebElement getPostChatTextArea() {
        return postChatTextArea;
    }

    public void setValuePostChatArea(String value) {
        this.postChatTextArea.sendKeys(value);
    }

    public ChatterPage clickShareButton(){
        try {
            wait.withTimeout(5, TimeUnit.SECONDS).until(
                    ExpectedConditions.visibilityOf(shareButton));
        } catch (WebDriverException e) {
            throw new WebDriverException(e);
        } finally {
            wait.withTimeout(15, TimeUnit.SECONDS);
        }

        shareButton.click();
        return new ChatterPage();

    }

    public ChatterPage createAnewPost(String value){

        setValuePostChatArea(value);
        return clickShareButton();
    }

    public void waitToPublishedPost(){
        WebElement adTopicsLink = driver.findElement(By.xpath("//a[@class='editLink']"));

        try {
            wait.withTimeout(5, TimeUnit.SECONDS).until(
                    ExpectedConditions.visibilityOf(adTopicsLink));

        } catch (WebDriverException e) {
            throw new WebDriverException(e);
        } finally {
            wait.withTimeout(15, TimeUnit.SECONDS);
        }
    }

    public WebElement getLatestPost(){

        By by = By.cssSelector(".feeditemtext.cxfeeditemtext");
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        List<WebElement> postsShared = driver.findElements(by);

        if(postsShared.size() != 0){
            return postsShared.get(0);
        }
        System.out.println("The latest post could not be  found");
        return null;
    }

    public String getTextFromLatestPost(){
        WebElement latestPost = getLatestPost();
        return latestPost.getText().toString();
    }

    public boolean verifyNewPostIsThere(String post) {
        String latestPost = getTextFromLatestPost();
        System.out.println("text from latest post" + latestPost);
        return latestPost.trim().equals(post);
    }

    public WebElement getDeleteElementFromLatestPost() {
        By by  = By.xpath("//a[@title='Delete this post']");
        List<WebElement> deleteItem = driver.findElements(by);
        if(deleteItem.size() != 0){
            return deleteItem.get(0);
        }
        System.out.println("The latest delete button could not be  found");
        return null;
    }

    public ChatterPage removeLatestPost(){
        WebElement deleteLatestPost = getDeleteElementFromLatestPost();
        deleteLatestPost.click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        return this;
    }

    public void expandLatestDropDown(){
        By by  = By.xpath("//span[@class='feeditemActionMenu']//a[contains(@id,'spillovermenu')]");
        List<WebElement> dropBoxMenu = driver.findElements(by);
        if(dropBoxMenu.size() != 0){
            dropBoxMenu.get(0).click();
        }
    }

    public void refreshPage(){
        driver.navigate().refresh();
        PageFactory.initElements(driver, this);
        try {
            wait.withTimeout(5, TimeUnit.SECONDS).until(
                    ExpectedConditions.visibilityOf(shareButton));

        } catch (WebDriverException e) {
            throw new WebDriverException(e);
        } finally {
            wait.withTimeout(15, TimeUnit.SECONDS);
        }
    }

    public WebElement getEditMenuItemFromLatestPost() {
        By by = By.xpath("//a[@title='Edit this post']");
        List<WebElement> editMenuItem = driver.findElements(by);

        if(editMenuItem.size() != 0){
            return editMenuItem.get(0);
        }
        System.out.println("The latest edit menu item could not be  found");
        return null;
    }

    public EditChatPage openEditDialog(){
        WebElement editLatestPost = getEditMenuItemFromLatestPost();
        editLatestPost.click();
        return new EditChatPage(driver);
    }

    public boolean  verifyNewPostIsDeleted(String txt){
        return (!verifyNewPostIsThere(txt));
    }

    public void closeDialog(){
        driver.close();
    }
}
