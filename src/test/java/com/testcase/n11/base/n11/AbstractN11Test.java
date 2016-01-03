package com.testcase.n11.base.n11;

import com.testcase.n11.base.AbstractTest;
import com.testcase.n11.util.Configuration;

public abstract class AbstractN11Test extends AbstractTest{

	@Override
	protected String getExecResultsRootDirPath() {
		
		return "C:/java_env/n11" + Configuration.getInstance().getServerUrl();
	}
	
	@Override
	protected abstract String getDatas();
}
