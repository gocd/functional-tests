package in.co.sahi;

import junit.framework.TestCase;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ExecutionException;
import net.sf.sahi.config.Configuration;

/**
 * 
 * This is a sample class to get started with Sahi Java.<br/> 
 * Have a look at DriverClientTest.java in sample_java_project dir for more detailed use of APIs.<br/>
 * You need sahi/lib/sahi.jar in your classpath.</br>
 * 
 */
public class JavaClientTest extends TestCase {
	private Browser browser;
	private String sahiBase;
	private String userDataDirectory;

	/**
	 * This starts the Sahi proxy, toggles the proxy settings on Internet Explorer
	 * and starts a browser instance. This could be part of your setUp method in a JUnit test.
	 * 
	 */
	public void setUp(){
		sahiBase = "../"; // where Sahi is installed or unzipped
		userDataDirectory = "myuserdata"; 
		Configuration.initJava(sahiBase, userDataDirectory); // Sets up configuration for proxy. Sets Controller to java mode.

		toggleIEProxy(true); // Not needed for Firefox. Needed for IE, Safari, Chrome on Windows
		
		String browserPath = "C:\\Program Files\\Internet Explorer\\iexplore.exe";
		String browserProcessName = "iexplore.exe";
		String browserOption = "-nomerge"; // Set to "-nomerge" for IE 8, "" for IE 7

// UNCOMMENT for your preferred browser.		
//		String browserPath = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
//		String browserProcessName = "firefox.exe";
//		String browserOption = "-profile D:/sahi/sahi_v3/browser/ff/profiles/sahi0 -no-remote";
		
//		String browserPath = "C:\\Documents and Settings\\Narayan Raman\\Local Settings\\Application Data\\Google\\Chrome\\Application\\chrome.exe";
//		String browserProcessName = "chrome.exe";
//		String browserOption = "--process-per-tab";		

//		String browserPath = "C:\\Program Files\\Safari\\Safari.exe";
//		String browserProcessName = "Safari.exe";
//		String browserOption = "";				
		
		browser = new Browser(browserPath, browserProcessName, browserOption);		
		browser.open();
	}	
	
	public void testGoogle() throws ExecutionException{
		browser.navigateTo("http://www.google.com");
		browser.textbox("q").setValue("sahi forums");
		browser.submit("Google Search").click();
		browser.waitFor(1000);
		browser.link("Sahi - Web Automation and Test Tool").click();		
		browser.link("Login").click();
		System.out.println(":: browser.textbox(\"req_username\").exists() = " + browser.textbox("req_username").exists());
	}
	/**
	 * This closes the browser instance, stops the proxy and toggles back the IE proxy settings.
	 * This could be part of your JUnit tearDown.
	 */
	
	
	public void testZK(){
		browser.navigateTo("http://www.zkoss.org/zkdemo/userguide");
		browser.span("Pure Java").click();
		browser.span("Demo").click();
		assertTrue(browser.span("Hello, World!").exists());
		assertTrue(browser.span("Hello, World!").isVisible());
		browser.div("Various Form").click();
		browser.div("z-row-cnt z-overflow-hidden[1]").click();
		browser.textbox("z-textbox").setValue("Jerry mouse");
		browser.div("Form with Popup").click();
		browser.div("Layout and Windows[1]").click();
		browser.div("Table Layout").click();
		browser.div("Tabboxes").click();
		browser.span("E").click();
		assertTrue(browser.span("This is panel E").isVisible());		
	}
	
	public void tearDown(){
		browser.close();		
//		proxy.stop();
		toggleIEProxy(false); // Not needed for Firefox. Needed for IE, Safari, Chrome on Windows
	}
		
	/**
	 * Toggles the proxy settings on Internet Explorer. <br/>
	 * Needed for IE, Safari, Chrome on Windows.
	 * Not needed for Firefox. 
	 * 
	 * @param enable boolean 
	 */
	
	public void toggleIEProxy(boolean enable){
		try {
			Runtime.getRuntime().exec(new String[]{sahiBase + "tools\\toggle_IE_proxy.exe", (enable ? "enable" : "disable")});
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}	
}
