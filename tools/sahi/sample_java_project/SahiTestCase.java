package net.sf.sahi.client;

/**
 * Sahi - Web Automation and Test Tool
 * 
 * Copyright  2006  V Narayan Raman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import net.sf.sahi.Proxy;
import net.sf.sahi.config.Configuration;
import junit.framework.TestCase;

public abstract class SahiTestCase extends TestCase {
	private static final long serialVersionUID = 9094239240720483156L;
	protected Browser browser;
	protected Proxy proxy;
	protected String sahiBasePath = ".";
	protected String userDataDirectory = "./userdata/";
	protected boolean isProxyInSameProcess = false;
	protected String browserPath;
	protected String browserProcessName;
	protected String browserOption; 	

	
	public abstract void setBrowser();
	
	public void setUp(){
		Configuration.initJava(sahiBasePath, userDataDirectory);
		
		if (isProxyInSameProcess) {
			proxy = new Proxy();
			proxy.start(true);
		}

		setBrowser();
		
		toggleIEProxy(true);
		
		browser = new Browser(browserPath, browserProcessName, browserOption);		
		browser.open();
	}

	public void ie8(){
		browserPath = "C:\\Program Files\\Internet Explorer\\iexplore.exe";
		browserProcessName = "iexplore.exe";
		browserOption = "-noframemerging";		
	}
	
	public void firefox(){
		browserPath = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
		browserProcessName = "firefox.exe";
		browserOption = "-profile " + userDataDirectory + "browser/ff/profiles/sahi0 -no-remote";
	}
	
	public void googleChrome(){
		browserPath = "C:\\Documents and Settings\\Narayan Raman\\Local Settings\\Application Data\\Google\\Chrome\\Application\\chrome.exe";
		browserProcessName = "chrome.exe";
		browserOption = "--process-per-tab";		
	}
	
	public void safari(){
		browserPath = "C:\\Program Files\\Safari\\Safari.exe";
		browserProcessName = "Safari.exe";
		browserOption = "";		
	}
	
	public void tearDown(){
		browser.setSpeed(100);
		browser.close();		
		if (isProxyInSameProcess) {
			proxy.stop();
		}
		toggleIEProxy(false);
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
			Runtime.getRuntime().exec(new String[]{sahiBasePath + "\\tools\\toggle_IE_proxy.exe", (enable ? "enable" : "disable")});
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}	
}
