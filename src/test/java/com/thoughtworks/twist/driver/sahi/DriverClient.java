/*************************GO-LICENSE-START*********************************
 * Copyright 2016 ThoughtWorks, Inc.
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
 *************************GO-LICENSE-END***********************************/

package com.thoughtworks.twist.driver.sahi;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.cruise.util.CruiseConstants;
import net.sf.sahi.client.Browser;
import net.sf.sahi.config.Configuration;

import com.thoughtworks.twist.core.utils.StringUtils;
import com.thoughtworks.twist.driver.sahi.browsersettings.ProxySettings;

/**
 * DriverClient gives a handle of a browser instance associated with a session on the Proxy
 * 
 * @author Narayan Raman
 */
public class DriverClient {

	public static final String THREADNUMBER = "<threadnumber>";
	private static final ThreadLocal<SahiDriver> browserInLocalThread = new ThreadLocal<SahiDriver>();
	private List<SahiDriver> openBrowsers = new ArrayList<SahiDriver>();
	private String sahiBasePath;
	private boolean sahiNTLM;
	private String sahiNTLMUser;
	private ProxyServer server;
	private int threadCounter = 0;

	public void start() throws Exception {
		startServer();
	}

	private void startServer() {
		Configuration.initJava(sahiBasePath, "sahi/userdata");
		CertInstallerStrategy installer = CertStategyFactory.getStrategyForCurrentOS();
		installer.installCertificateFrom(sahiBasePath);
		server = new ProxyServer.DefaultProxyServer(sahiNTLM, sahiNTLMUser, sahiBasePath);

		server.start();
		ProxySettings.enableProxy(sahiBasePath);

	}

	/**
	 * Returns a handle of a browser instance associated with a session on the Proxy
	 * 
	 * @param browserPath
	 *            The browser executable path
	 * @param browserProcessName
	 *            The process name to look for to run a kill command
	 * @param browserOption
	 *            Any browser options. Leave blank if not required.
	 * @return Browser instance
	 */
	public synchronized Browser getBrowser(String browserPath, String browserProcessName, String browserOption) {
		SahiDriver sahiDriver = browserInLocalThread.get();
		
		if ((sahiDriver != null) && !sahiDriver.isClosed()) {
			return sahiDriver;
		}
		
		if ((sahiDriver != null) && sahiDriver.isClosed()) {
		   removeFromBrowserList(sahiDriver);
		}
		
		browserOption = browserOption.replace(THREADNUMBER, counter());
		browserOption = handleFFMacPath(browserProcessName, browserOption);
		SahiDriver driver = new SahiDriver(removeQuotes(browserPath), browserProcessName, browserOption, server);
		if(CruiseConstants.SAHI_TESTS.equals("Y")) {
			driver.open();
		}
		openBrowsers.add(driver);
		browserInLocalThread.set(driver);
		return driver;
	}

	private void removeFromBrowserList(SahiDriver sahiDriver) {
		System.out.println("Removing closed browser from list and removing from thread local.");
		openBrowsers.remove(sahiDriver);
		browserInLocalThread.remove();
	}

	private CharSequence counter() {
		return String.valueOf(threadCounter++);
	}

	public void stop() throws Exception {
		for (SahiDriver driver : openBrowsers) {
			driver.close();
		}
		openBrowsers.clear();
		ProxySettings.disableProxy(sahiBasePath);
		server.stop();
	}

	public void setSahiBasePath(String sahiBasePath) {
		String basePath = removeQuotes(sahiBasePath);
		if (!StringUtils.isEmpty(basePath)) {
			this.sahiBasePath = basePath;
		} else {
			try {
				this.sahiBasePath = this.getClass().getClassLoader().getResource("sahi").toURI().getPath();
			} catch (URISyntaxException e) {
				e.printStackTrace();
				throw new RuntimeException("Error creating Sahi Driver Factory", e);
			}
		}
	}

	private String handleFFMacPath(String browserProcessName, String browserOption) {
		if (!browserProcessName.contains("firefox-bin")){
			return browserOption;
		}
		List<String> optionParams = getBrowserOptionsParameters(browserOption);
		String profilePath = getProfilePath(optionParams);
		try {
			profilePath = new File(profilePath.replaceAll("^\"", "").replaceAll("\"$", "")).getCanonicalPath();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return geFFMacOptions(optionParams, profilePath);
	}


	private static String geFFMacOptions(List<String> optionParams, String profilePath) {
		StringBuilder optionStr = new StringBuilder();
		for (String part : optionParams) {
			if (part.startsWith("-profile")) {
			    optionStr.append("-profile " + "\"" + profilePath + "\"");	
			} else {
				optionStr.append(" " + part);
			}
		}
		System.out.println("Browser option transformed : " + optionStr.toString());
		return optionStr.toString();
		
	}

	private String removeQuotes(String path) {
		return path.replaceAll("\"", "");
	}

	public void setSahiNTLM(boolean sahiNTLM) {
		this.sahiNTLM = sahiNTLM;
	}

	public void setSahiNTLMUser(String NTLMUser) {
		this.sahiNTLMUser = NTLMUser;

	}
	
//	TODO: write unit tests
//	public static void main(String[] args) {
//		System.out
//				.println(getBrowserOptionsParameters("-profile /Users/angshus/Documents/workspace/twist_documentation/Web Application "
//						+ "Testing With Sahi(uses Mingle)/sahi/userdata/browser/ff/profiles/sahi<threadnumber> -no-remote -x value"));
//		List<String> paramParts = getBrowserOptionsParameters("-profile \"./sahi/userdata/browser/ff/profiles/sahi<threadnumber>\"  -no-remote abc -x test");
//		String profilePath = getProfilePath(paramParts);
//		System.out.println(geFFMacOptions(paramParts, profilePath));
//	}

	public static String getProfilePath(List<String> browserOptionsParameters) {
		for (String param : browserOptionsParameters) {
			if (param.startsWith("-profile")) {
			   return param.replace("-profile", "").trim();	
			}
		}
		return "";
	}

	public static List<String> getBrowserOptionsParameters(String browserOption) {
		List<String> fragments = new ArrayList<String>();
		
		int startPos = browserOption.indexOf("-profile");
		boolean mayHavePart = startPos >= 0;
		
		if (!mayHavePart) {
			fragments.add(browserOption);
			return fragments;
		}
		
		int endPos = browserOption.indexOf("-no-remote", startPos+1);
		if (endPos >= 0) {
			fragments.add(browserOption.substring(startPos, endPos).trim());
			startPos = endPos;
		}
		
		fragments.add(browserOption.substring(startPos, browserOption.length()).trim());
		return fragments;
	}
}
