package com.testcase.n11.test.n11;

import org.junit.BeforeClass;
import org.junit.Test;

import com.testcase.n11.base.n11.AbstractN11Test;
import com.testcase.n11.page.n11.N11KontrolPage;
import com.testcase.n11.page.n11.N11LoginPage;
import com.testcase.n11.util.Configuration;

public class N11KontrolTest extends AbstractN11Test {

	N11KontrolPage page = new N11KontrolPage(driver);

	@BeforeClass
	public static void login() throws InterruptedException {

		N11LoginPage login = new N11LoginPage(driver);
		login.navigateTo();
		login.login(Configuration.getInstance().getN11LoginEmail(), Configuration.getInstance().getN11LoginPassword());
	}

	@Test
	public void n11SeacrhAuthor() {
		navigateTo(page);
		page.searchAuthor();
	}

	public String getDatas() {

		return "Kullanıc Adı : " + Configuration.getInstance().getN11LoginEmail();
	}
}
