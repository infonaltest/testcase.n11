package com.testcase.n11.base;

import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public abstract class AbstractTest {

    protected static WebDriver driver;
    private static StringBuffer verificationErrors;
    private AbstractPage activePage;
    
    protected abstract String getExecResultsRootDirPath();
    protected abstract String getDatas();
    
   
    private void setActivePage(AbstractPage page) {
    	activePage = page;
    }
    
    protected AbstractPage getActivePage() {
    	return activePage;
    }
    
    protected void navigateTo(AbstractPage page) {
    	
    	setActivePage(page);
    	
    	page.navigateTo();
    }
    
    @BeforeClass
    public static void setUp() {
    	//Chrome Ayarları Yapılması
//    	ChromeOptions options = new ChromeOptions();
//    	options.addArguments("test-type");
//    	options.addArguments("disable-popup-blocking");
//    	options.addArguments("ignore-certificate-errors");
//    	options.addArguments("disable-translate");
//    	options.addArguments("start-maximized");
//    	DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//    	capabilities.setCapability(ChromeOptions.CAPABILITY, options);
    	driver = new FirefoxDriver();
    	verificationErrors = new StringBuffer();
        
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	driver.manage().window().maximize();
//    	driver.manage().window().setSize(new Dimension(800,600));
    }

	@AfterClass
    public static void tearDown() {
    	
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
    }
    
    @Rule
    public TestRule testWatcher = new TestWatcher() {
    	
    	@Override
    	protected void failed(Throwable e, org.junit.runner.Description description) {
    		
    		BufferedImage image = null;
    		PrintStream writer = null;
    		try{
    			
//    			FileUtil.getInstance().deleteFileListing(new File("C:/java_env/selenium/data/exec_results/bankasurans/"));
//    			FileUtil.getInstance().deleteFileListing(new File("C:/java_env/selenium/data/exec_results/bis/"));
//    			FileUtil.getInstance().deleteFileListing(new File("C:/java_env/selenium/data/exec_results/flex/"));
//    			FileUtil.getInstance().deleteFileListing(new File("C:/java_env/selenium/data/exec_results/kis/"));
//    			
//
//    			File dir = new File(getExecResultsRootDirPath()); //klasorun kayit yeri ve adi
//                FileUtil.getInstance().createDirectory(dir);
                
    			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//                FileUtils.copyFile(scrFile, new File(getExecResultsRootDirPath() + "/" + description.getMethodName()+".jpeg"));
                
                writer = new PrintStream(getExecResultsRootDirPath() + "/" +description.getMethodName()+".log", "UTF-8");
                e.printStackTrace(writer);
                
//                try{
//                	
//                	FileUtil.getInstance().writeDatasInLogFile(getExecResultsRootDirPath() + "/" +description.getMethodName(), getDatas(), getActivePage().getTokenId());
//                } catch (Exception exx){
//                	
//                	FileUtil.getInstance().writeDatasInLogFile(getExecResultsRootDirPath() + "/" +description.getMethodName(), getDatas(), "");
//                }
                
                if(getActivePage() != null) {
                	getActivePage().closeErrorMessageDialog();
                }
    		}
    		catch (Exception ex) {
    			
    			ex.printStackTrace();
    		} finally{
    			if(image != null)
    				image.flush();
    			if(writer != null)
    				writer.close();
    		}
    		
    	}

	};
	
	public class RetryTest implements TestRule {
        private int retryCount;

        public RetryTest(int retryCount) {
            this.retryCount = retryCount;
        } 

        public Statement apply(Statement base, Description description) {
        	 return statement(base, description);
		}
        
        private Statement statement(final Statement base, final Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    Throwable caughtThrowable = null;

                    // implement retry logic here
                    for (int i = 0; i < retryCount; i++) {
                        try {
                            base.evaluate();
                            return;
                        } catch (Throwable t) {
                            caughtThrowable = t;
                            System.err.println(description.getDisplayName() + ": Test " + (i+1) + ". kez calisiyor.");
                        }
                    }
                    System.err.println(description.getDisplayName() + ": Test " + retryCount + " kez calistiktan sonra hata aldi");
                    throw caughtThrowable;
                }
            };
        }

		
    }
}