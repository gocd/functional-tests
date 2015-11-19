/*************************GO-LICENSE-START*********************************
 * Copyright 2015 ThoughtWorks, Inc.
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

import java.io.IOException;

public class SahiMacCertInstaller implements CertInstallerStrategy {

	public void installCertificateFrom(String sahiBasePath) {
		if (certificateIsAlreadyPresent()) {
			return;
		}

		try {
			CommandUtils.execute(addCertificateCommand(sahiBasePath));
			CommandUtils.execute(addTrustedCertificateCommand(sahiBasePath));
		} catch (Exception e) {
			System.err.println(CERT_INSTALL_ERROR + certFile(sahiBasePath));
		} 
	}
	
	public boolean certificateIsAlreadyPresent() {
		try {
			CommandResult result = CommandUtils.execute(verifyCertificateCommand());
			return !result.contains("The specified item could not be found in the keychain");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	private String[] addCertificateCommand(String sahiBasePath) {
		return genericSecurityCommand(sahiBasePath, "add-certificate");
	}

	private String[] verifyCertificateCommand() {
		return new String[]{"security", "find-certificate", "-e", "support@thoughtworks.com"};
	}
	
	private String[] addTrustedCertificateCommand(String sahiBasePath) {
		return genericSecurityCommand(sahiBasePath, "add-trusted-cert");
	}
	
	private String[] genericSecurityCommand(String sahiBasePath, String certOption) {
		String command = "security";
		String certificate = certFile(sahiBasePath);
		return new String[]{command, certOption, certificate};
	}

	protected String certFile(String sahiBasePath) {
		return sahiBasePath + CERT_FILE;
	}

	public static void main(String[] args) {
		SahiMacCertInstaller installer = new SahiMacCertInstaller();
		installer.installCertificateFrom("/Users/srijays/Twist/com.thoughtworks.twist.driver.sahi/sahi/");
	}

}
