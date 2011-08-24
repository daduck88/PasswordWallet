package com.daduck.WlltPssd;

import java.util.List;

import com.google.inject.Module;

import roboguice.application.RoboApplication;

public class PssdApp extends RoboApplication {
	
    protected void addApplicationModules(List<Module> modules) {
        modules.add(new PssdModule());
    }
}
