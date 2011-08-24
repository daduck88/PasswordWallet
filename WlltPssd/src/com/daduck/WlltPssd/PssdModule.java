package com.daduck.WlltPssd;

import roboguice.config.AbstractAndroidModule;

public class PssdModule extends AbstractAndroidModule {
	  @Override protected void configure() {
		   requestStaticInjection(DatabaseHelper.class);
		   bind(DatabaseHelperI.class).to(DatabaseHelper.class);
		   requestStaticInjection(DbAdapter.class);
		   bind(DbAdapterI.class).to(DbAdapter.class);
	  }
	}