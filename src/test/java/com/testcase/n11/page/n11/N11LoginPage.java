package com.testcase.n11.page.n11;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.testcase.n11.base.n11.AbstractN11Page;

public class N11LoginPage extends AbstractN11Page {

	public N11LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void navigateTo() {

		driver.navigate().to(config.getServerUrl());
	}

	public void login(String loginEmail, String loginPassword) throws InterruptedException {

		// Giris button
		clickElement(".btnSignIn");

		// Facebook ile Giriş Yap
		clickElement(".facebookBtn");
		String homePage=driver.getWindowHandle();//Sayfanın adresi çekiliyor.
		driver.findElement(By.className("facebookBtn")).click();
        //String popPage=driver.getWindowHandle();	
        Set<String> windows= driver.getWindowHandles();
        Iterator<String> iterator=windows.iterator();
        String currentWindowId;
        int sayac=0;
        while(iterator.hasNext())
        {
        	currentWindowId=iterator.next().toString();
        	if(!currentWindowId.equals(homePage))
        	{
        		driver.switchTo().window(currentWindowId);
        		Thread.sleep(5000);
        		fillInputField("#email", loginEmail);
    			fillInputField("#pass", loginPassword);
    			clickElement("#loginbutton");
        		sayac=1;
        	}
        	 if(sayac==1)
           	{
        		driver.switchTo().window(homePage);
        		driver.navigate().to(config.getServerUrl());
           	}
        }
	}
}
