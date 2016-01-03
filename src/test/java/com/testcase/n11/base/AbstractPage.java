package com.testcase.n11.base;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testcase.n11.util.Configuration;

public abstract class AbstractPage {
	
	protected Configuration config;
	protected WebDriver driver;
	protected WebDriverWait wait;
	
	public AbstractPage(WebDriver driver){
		this.config=Configuration.getInstance();
		this.driver=driver;
		this.wait=new WebDriverWait(driver, 30);
	}

	public abstract void navigateTo();
	
	protected abstract void clickMenus(int mainIndex, int... subIndexes);
	
	protected abstract void untilAjaxComplete();
	
	protected abstract boolean isErrorMessageDisplayed();
	
	protected abstract void closeErrorMessageDialog();
	
	protected abstract String getMessageDisplayed();
	
	protected JavascriptExecutor getJSExecutor(){
		return (JavascriptExecutor)driver;
	}
	
	protected void fillInputFieldById(String id, String text){
		fillInputFieldById(id, text);
	}
	
	protected void fillInputFieldById(String id, String text, boolean pressEnter){
		fillInputField(id, text, pressEnter);
	}
	
	protected void fillInputField(String cssSelector, String text, int... index){
		fillInputField(cssSelector, text, false, index);
	}
	
	protected void fillInputField(String cssSelector, String text, boolean pressEnter, int... index){
		WebElement element = findElement(cssSelector,index);
		if(element.isEnabled()){
			
			untilElementClickable(cssSelector);
			element.clear();
			element.sendKeys(text);
			
			if(pressEnter){
				
				element.sendKeys(Keys.ENTER);
			}
		}
	}
	
	protected void clickElement(String cssSelector, int...index){
		
		clickElement(cssSelector, true, index);
	}
	
	protected void clickElement(String cssSelector, boolean waitForAjax, int... index){
		
		WebElement element = findElement(cssSelector, index);
		if(element==null){
			throw new RuntimeException("ELEMENT ("+cssSelector+","+(index.length>0 ? index[0] : "")+") ITEM NOT FOUND");
			
		}
		
		untilElementClickable(cssSelector);
		element=findElement(cssSelector, index);
		element.click();
		
}
	protected void clickElementJS(String cssSelector){
		if(!isElementExists(cssSelector)){
			return;
		}
		
		cssSelector=escapeCssSelector(escapeCssSelector(cssSelector));
		
		String jsStmt = String.format("$('%s').click();", cssSelector);
		
		executeJS(jsStmt,true);
	}
	
	protected void displayElementJS(String cssSelector){
		
		if(!isElementExists(cssSelector)){
			return;
		}
		cssSelector = escapeCssSelector(escapeCssSelector(cssSelector));
		
		String jsStmt = String.format("$('%s').show();", cssSelector);
		
		executeJS(jsStmt,true);
	}
	
	protected Object executeJS(String jsStmt, boolean wait){
		return wait ? getJSExecutor().executeScript(jsStmt, "")
				    : getJSExecutor().executeAsyncScript(jsStmt, "");
	}
	
	protected boolean executeBoolJS(String jsStmt) {
		
		return "true".equals(executeJS(jsStmt, true).toString());
	}
	
	protected void scrollTo(int x, int y) {
		
		String jsStmt = String.format("windows.scrollTo(%d, %d)", x,y);
		executeJS(jsStmt,true);
	}
	
	protected void scrollToElement(WebElement element){
		
	if(element != null){
		scrollTo(element.getLocation().getX(), element.getLocation().getY());
	}
}
	
	
	protected void scrollToElement(String cssSelector, int index, int x, int y){
		WebElement element = findElement(cssSelector, index);
		
		if(element != null){
			
			scrollTo(element.getLocation().getX()+x, element.getLocation().getY()+y);			
		}
	}
	
protected boolean isElementExists(String cssSelector, int... index) {
    	
    	cssSelector = escapeCssSelector(escapeCssSelector(cssSelector));
    	
    	String jsStmt = index.length==0 || index[0]<0 ? String.format("return $('%s').size()>0", cssSelector)
    												  : String.format("return $('%s').size()>0 && $('%s').eq(%d).size()>0", cssSelector, cssSelector, index[0]);
    	Object result = executeJS(jsStmt, true);
    	
    	return result!=null && "true".equalsIgnoreCase(result.toString());
    }

    protected boolean isElementInView(String cssSelector, int... index) {
    	
    	cssSelector = escapeCssSelector(escapeCssSelector(cssSelector));
    	
    	String jsStmt = index.length==0 || index[0]<0 ? String.format("return $('%s').size()>0 && $('%s').inviewport();", cssSelector, cssSelector)
    												  : String.format("return $('%s').size()>0 && $('%s').eq(%d).inviewport();", cssSelector, cssSelector, index[0]);
    	Object result = executeJS(jsStmt, true);
    	
    	return result!=null && "true".equalsIgnoreCase(result.toString());
    }
    
    protected WebElement findElement(String cssSelector, int... index) {
    	
    	cssSelector = escapeCssSelector(cssSelector);
    	
    	if(index.length == 0) {
    		
    		return driver.findElement(By.cssSelector(cssSelector));
    	} else if(index[0] >= 0){
    		
    		List<WebElement> elements = driver.findElements(By.cssSelector(cssSelector));
    		if(!elements.isEmpty() && index[0] <= elements.size()) {
    			return elements.get(index[0]);
    		}
    	}
    	
    	return null;
    }
    
    protected WebElement findElement(WebElement parent, String cssSelector, int... index) {
    	
    	cssSelector = escapeCssSelector(cssSelector);
    	
    	if(index.length == 0) {
    		
    		return parent.findElement(By.cssSelector(cssSelector));
    	} else if(index[0] >= 0){
    		
    		List<WebElement> elements = parent.findElements(By.cssSelector(cssSelector));
    		if(!elements.isEmpty() && index[0] <= elements.size()) {
    			return elements.get(index[0]);
    		}
    	}
    	
    	return null;
    }
    
    protected String getCssId(String id) {
		
    	if(id==null || id.trim().length()==0)
    		return "";
    	
		return "#"+id;
	}

    private String escapeCssSelector(String selector) {
    	
    	return selector.replaceAll(Pattern.quote(":"), Matcher.quoteReplacement("\\:"));
    }
    
    protected void untilElementClickable(String cssSelector, int... index) {
    	
    	WebElement element = findElement(cssSelector, index);
    	
    	wait.until(ExpectedConditions.elementToBeClickable(element));
    	
    }
    
    protected void untilElementDisappear(String cssSelector) {
    	
    	cssSelector = escapeCssSelector(cssSelector);
    	
    	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(cssSelector)));
    }
    
    protected void untilElementAppear(String cssSelector) {
    	
    	cssSelector = escapeCssSelector(cssSelector);
    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
    }
    
    protected void switchToFrame(String cssSelector){
		
		driver.switchTo().defaultContent();
		
		untilElementAppear(cssSelector);
		
		driver.switchTo().frame(findElement(cssSelector));
	}
	
	
}
