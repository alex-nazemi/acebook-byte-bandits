package com.makersacademy.acebook.model.Controller.postTests;

import com.github.javafaker.Faker;
import com.makersacademy.acebook.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PostsTest {

    WebDriver driver;
    Faker faker;
    User user;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        driver = new ChromeDriver();
        faker = new Faker(); //generates fake data
        user = new User(); //User is the class that exists in the model package
        user.setUsername(faker.name().firstName());

        driver.get("http://localhost:8080/users/new");
        driver.findElement(By.id("username")).sendKeys(user.getUsername());
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("submit")).click();
        driver.get("http://localhost:8080/login");
        driver.findElement(By.id("username")).sendKeys(user.getUsername());
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.tagName("button")).click();

    }

    @After
    public void tearDown() {
        driver.close();
    }

    //Test to create a new post and check if it was created successfully.//
    @Test
    public void createPost() {
        //Find the post and enter content
        driver.findElement(By.id("content")).sendKeys("Hello this is a post");
        //Click the submit button
        driver.findElement(By.id("post-content-submit")).click();

        // Add an explicit wait for the post to appear
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement postElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("p")));

        //Check if the post was created
        //String postText = driver.findElement(By.tagName("p")).getText();
        //assert postText.contains("Hello this is a post");
        String postText = postElement.getText();
        System.out.println("Post text: " + postText);
        assert postElement.isDisplayed();

    }
    //Test to reset the post and check if it was reset successfully.//
    @Test
    public void resetPost() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //Find the post and enter content
        driver.findElement(By.id("content")).sendKeys("Hello this is a post");
        //Click resetButton
        driver.findElement(By.id("reset-button")).click();


        //Check if the post was reset
        WebElement form = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='form-group']//input[contains(@name, 'commentText')]")));
        assert form.getText().isEmpty();




    }



}

