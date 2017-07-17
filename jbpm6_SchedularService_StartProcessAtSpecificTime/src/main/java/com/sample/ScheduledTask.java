package com.sample;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class ScheduledTask extends TimerTask {

    public void run() {
	
	System.out.println("Current time" + new Date());
        try {
	   startProcess();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public void startProcess() throws Exception {

	File file = null;
	Resource resource = null;
	KieServices kieServices = KieServices.Factory.get();
	KieFileSystem kfs = kieServices.newKieFileSystem();

	file = loadFile("process_1.bpmn");
	resource = kieServices.getResources().newFileSystemResource(file).setResourceType(ResourceType.BPMN2);
	kfs.write(resource);

	KieBuilder Kiebuilder = kieServices.newKieBuilder(kfs);
	Kiebuilder.buildAll();

	if (Kiebuilder.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
	    throw new RuntimeException("Build Errors:\n" + Kiebuilder.getResults().toString());
	}

	KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

	KieSession ksession = kieContainer.newKieSession();
	//System.out.println("KieSession"+ksession);
        ksession.signalEvent("StartProcess", null);
        
    }

    public static File loadFile(String fileName) {

	File loadfile = new File(ScheduledTask.class.getClassLoader().getResource(fileName).getFile());
	return loadfile;
    }
}