package com.testcase.n11.base.n11;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.testcase.n11.base.AbstractPage;

public abstract class AbstractN11Page extends AbstractPage {

	public AbstractN11Page(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void navigateTo() {
		
	}
	
	@Override
	protected void clickMenus(int mainIndex, int... subIndexes) {
    	
    	if(mainIndex >= 0) {
    		
    		untilElementClickable(".catMenuItem", mainIndex);
    	}
    	
    	if(subIndexes[0] >= 0) {
    		
    		untilElementClickable(".mainCat", mainIndex);
    		clickElement(".mainCat", mainIndex);
    	}
    	
    	untilAjaxComplete();
    }
    
		
	@Override
	protected void untilAjaxComplete() {
    	
    }

	@Override
	protected boolean isErrorMessageDisplayed() {
		
		return isElementInView("#listWorkFlowForm:messages_container span.ui-growl-image-error") || 
			   isElementInView("#newWorkFlowForm:messages_container span.ui-growl-image-error");
	}

	@Override
	protected String getMessageDisplayed() {
		
		WebElement errorMsgElement = null;
		if(isElementExists("#form:messages_container span.ui-growl-title")) { //error growl displayed
			
			errorMsgElement = findElement("#form:messages_container span.ui-growl-title");
		} if(isElementExists("#newWorkFlowForm:messages_container span.ui-growl-title")) { //error growl for workflow displayed
			
			errorMsgElement = findElement("#newWorkFlowForm:messages_container span.ui-growl-title");
		}

		return errorMsgElement!=null ? errorMsgElement.getText() : null;
	}
	
	@Override
	protected void closeErrorMessageDialog(){
		
		boolean isFrameDialogDisplayed = isElementExists(".ui-overlay-visible .ui-dialog-titlebar-icon");
		if(isFrameDialogDisplayed) {
			
			clickElementJS(".ui-overlay-visible .ui-dialog-titlebar-icon");
		} else {
			
			String cssSelector = null;
			if (isElementInView("#listWorkFlowForm:messages_container span.ui-growl-image-error")){
				cssSelector = "#listWorkFlowForm:messages_container";
			}
			
			if(cssSelector != null) {
				
				displayElementJS(cssSelector + " .ui-growl-icon-close");
		    	clickElementJS(cssSelector + " .ui-growl-icon-close");
		    	untilElementDisappear(cssSelector + " .ui-growl-image-error");
			}
		}
	}
	
	protected String getMessageDisplayed(String cssSelector){
		
		WebElement dialogMsgElement = findElement(cssSelector);
		
		return dialogMsgElement.getText();
	}
	
    protected void clickAccordionItem(int index){
    	
    	clickElement(".ui-accordion-header", index);
    }
    

	protected WebDriver switchToIframe()
    {
    	return driver.switchTo().frame(0);
    }
    
    protected void closeDialog(){
    	
    	driver.switchTo().defaultContent();
    	List <WebElement> elements = driver.findElements(By.cssSelector(".ui-icon-closethick"));
    	for (int i = 0; i < elements.size(); i++){
    		if (elements.get(i).isDisplayed()){
            	elements.get(i).click();
            	untilAjaxComplete();
    		}
    	}
    }
}