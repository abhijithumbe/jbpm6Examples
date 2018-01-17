package com.sample;

import java.io.File;
import java.util.Date;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;

public class ProcessMain {

    public static void main(String[] args) throws Exception {
	// TODO Auto-generated method stub

	File file = null;
	Resource resource = null;
	KieServices kieServices = KieServices.Factory.get();
	KieFileSystem kfs = kieServices.newKieFileSystem();

	file = loadFile("DBConnection.drl");
	resource = kieServices.getResources().newFileSystemResource(file).setResourceType(ResourceType.DRL);
	kfs.write(resource);

	// System.out.println("Build start " + new Date());

	KieBuilder Kiebuilder = kieServices.newKieBuilder(kfs);
	Kiebuilder.buildAll();
	//System.out.println("Build complete  " + new Date());

	if (Kiebuilder.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
	    throw new RuntimeException("Build Errors:\n" + Kiebuilder.getResults().toString());
	}
	PersonPojo person = new PersonPojo();
	person.setName("Abhijit");

	KieSession ksession = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId())
		.newKieSession();
	ksession.insert(person);

	System.out.println("Number of rules executed:=" + ksession.fireAllRules());

	System.exit(0);
    }

    public static File loadFile(String fileName) {

	File loadfile = new File(ProcessMain.class.getClassLoader().getResource(fileName).getFile());
	return loadfile;
    }

}
