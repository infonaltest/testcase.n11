package com.testcase.n11.page.n11;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.testcase.n11.base.n11.AbstractN11Page;

import junit.framework.Assert;

public class N11KontrolPage extends AbstractN11Page {

	public N11KontrolPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void navigateTo() {
		// TODO Auto-generated method stub

	}

	public void searchAuthor() {

		clickElement(".catMenuItem", 7);
		clickElement(".subCatMenu .mainCat a[title*='Kitap']", 0);
		clickElement(".filterArea a[title*='Yazarlar']");

		List<WebElement> elements = driver.findElements(By.cssSelector("#authorsList li"));

		if (elements.size() >= 80) {
			
			clickElement(".pagination .pageLink", 1);
			
			if(findElement(".pageInfo .currentPage").getAttribute("value").equals("2")){
				System.out.println("2. Sayfadasınız.");
			}
			
						
		}

	}

}
