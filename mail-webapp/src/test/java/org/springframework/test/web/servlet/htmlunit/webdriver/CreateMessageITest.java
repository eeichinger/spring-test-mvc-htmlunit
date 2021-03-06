/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.springframework.test.web.servlet.htmlunit.webdriver;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.springframework.test.web.servlet.htmlunit.webdriver.pages.CreateMessagePage;
import org.springframework.test.web.servlet.htmlunit.webdriver.pages.ViewMessagePage;

/**
 * <p>
 * An integration test that validates the {@link CreateMessagePage}. Notice that we are able to reuse the same page
 * objects as the {@link MockMvcCreateMessageTest} and the {@link MockitoMvcCreateMessageTest}.
 * </p>
 * <p>
 * <strong>NOTE</strong> The web application must actually be running for this test to pass.
 * </p>
 *
 * @author Rob Winch
 * @see MockMvcCreateMessageTest
 * @see MockitoMvcCreateMessageTest
 */
public class CreateMessageITest extends AbstractWebDriverTest {

	@Test
	public void missingFieldWithJavascriptValidationDisplaysError() {
		CreateMessagePage messagePage = CreateMessagePage.to(getDriver());
		messagePage = messagePage.createMessage(CreateMessagePage.class, "", "");
		assertThat(messagePage.getErrors()).isEqualTo("This field is required.");
	}

	@Test
	public void missingFieldServerSideValidationDisplaysError() {
		CreateMessagePage messagePage = CreateMessagePage.to(getDriver());
		messagePage = messagePage.createMessage(CreateMessagePage.class, "Summary", "");
		assertThat(messagePage.getErrors()).isEqualTo("Message is required.");
	}

	@Test
	public void successfullyCreateMessage() {
		String expectedSummary = "Summary";
		String expectedMessage = "Detailed message that you can see";
		CreateMessagePage page = CreateMessagePage.to(getDriver());

		ViewMessagePage viewMessagePage = page.createMessage(ViewMessagePage.class, expectedSummary, expectedMessage);
		assertThat(viewMessagePage.getId()).isNotEmpty();
		assertThat(viewMessagePage.getCreated()).isNotEmpty();
		assertThat(viewMessagePage.getSummary()).isEqualTo(expectedSummary);
		assertThat(viewMessagePage.getText()).isEqualTo(expectedMessage);
		assertThat(viewMessagePage.getSuccess()).isEqualTo("Successfully created a new message");
	}
}
