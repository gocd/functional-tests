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

package com.thoughtworks.cruise.page.admin;

import net.sf.sahi.client.Browser;

public class PageElementIdentifiers {
	public static PageElement<String> OAUTH_CLIENT_NAME = new PageElement<String>("oauth_client[name]", "client[name]");
	public static PageElement<String> OAUTH_CLIENT_REDIRECT_URI = new PageElement<String>("oauth_client[redirect_uri]", "client[redirect_uri]");
	public static PageElement<Predicate> OAUTH_DELETE_BUTTON = new PageElement<Predicate>(new Predicate() {
		@Override
		public Object invoke(Browser browser) {
			return browser.link("/Delete/i");
		}
	}, new Predicate() {
		@Override
		public Object invoke(Browser browser) {
			return browser.submit("Delete");
		}
		
	});
	
	public static <T> T get(PageElement<T> element) {
		return element.get(isOldRails());
	}

	private static boolean isOldRails() {
		String newRails = System.getenv("USE_NEW_RAILS");
		return newRails != null && "N".equals(newRails);
	}
}

class PageElement<T> {
	private final T forRails2;
	private final T forRails4;
	
	public PageElement(T forRails2, T forRails4) {
		this.forRails2 = forRails2;
		this.forRails4 = forRails4;		
	}
	
	public T get(boolean isRails2) {
		return isRails2 ? forRails2 : forRails4;
	}
}