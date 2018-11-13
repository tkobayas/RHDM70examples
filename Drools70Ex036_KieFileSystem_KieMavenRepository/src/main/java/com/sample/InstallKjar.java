package com.sample;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.drools.compiler.kie.builder.impl.KieFileSystemImpl;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.scanner.KieMavenRepository;

public class InstallKjar { 
    
    private static String GROUP_ID = "com.example";
    private static String ARTIFACT_ID = "my-example-a";
    private static String VERSION = "1.0.0";

    public static final void main(String[] args) {
        try {
            // KieServices is the entry point of APIs
            KieServices ks = KieServices.Factory.get();
            
            KieMavenRepository repository = KieMavenRepository.getKieMavenRepository();

            // KieFileSystem is responsible for gathering resources
            KieFileSystem kfs = ks.newKieFileSystem();

            // You can add your DRL files as InputStream here
            kfs.write("src/main/resources/rules/Sample1.drl",
                    ks.getResources().newInputStreamResource(new FileInputStream(new File("work/Sample1.drl"))));
            kfs.write("src/main/resources/12345.drl",
                    ks.getResources().newInputStreamResource(new FileInputStream(new File("work/Sample2.drl"))));
            
            kfs.write("META-INF/kmodule.xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
                    "<kmodule xmlns=\"http://www.drools.org/xsd/kmodule\">\n" + 
                    "</kmodule>\n");

            String pomContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                    "  <modelVersion>4.0.0</modelVersion>\n" +
                    "\n" +
                    "  <groupId>" + GROUP_ID + "</groupId>\n" +
                    "  <artifactId>" + ARTIFACT_ID + "</artifactId>\n" +
                    "  <version>" + VERSION + "</version></project>\n";
            
            kfs.writePomXML(pomContent);
            
            
            File tempJar = File.createTempFile(ARTIFACT_ID + "-" + VERSION, "tmp");
            tempJar = ((KieFileSystemImpl)kfs).asMemoryFileSystem().writeAsJar(tempJar.getParentFile(), tempJar.getName());
            
            File tempPom = File.createTempFile(ARTIFACT_ID + "-" + VERSION, "tmp.xml");
            FileUtils.writeStringToFile(tempPom, pomContent, "UTF-8");
            
            ReleaseId releaseId = ks.newReleaseId(GROUP_ID, ARTIFACT_ID, VERSION);
            repository.installArtifact(releaseId, tempJar, tempPom);
            
            System.out.println("========== finished ");

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}
