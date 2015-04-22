package org.cubekode.jaguar.forge.addon.test;

import java.io.File;

import javax.inject.Inject;

import org.cubekode.jaguar.forge.addon.api.JaguarFacet;
import org.cubekode.jaguar.forge.addon.api.ini.JaguarIniConfig;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.arquillian.AddonDependencies;
import org.jboss.forge.arquillian.archive.AddonArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Test;
import org.junit.runner.RunWith;

// TODO problemn with furnace?!
// @Ignore
@RunWith(Arquillian.class)
public class JaguarFacetTest
{
   @Deployment
   @AddonDependencies({})
   public static AddonArchive getDeployment()
   {
      return ShrinkWrap
               .create(AddonArchive.class)
               .addBeansXML()
               .addClass(JaguarFacetTest.class);
   }

   @Inject
   private ResourceFactory resourceFactory;

   @Inject
   private ProjectFactory projectFactory;

   @Test
   public void testInstallIni() throws Exception
   {
      Project project = projectFactory.createTempProject();

      FileResource<?> iniFile = resourceFactory.create(new File("C:\\work\\powerlogic\\jaguar615\\meus_projetos\\jcompany_ini_facelets_cdi.zip")).reify(FileResource.class);

      JaguarIniConfig iniConfig = new JaguarIniConfig(iniFile, "org.cubekode.testini", "testini", "1.0.0-SNAPSHOT");

      iniConfig.jaguarVersion("6.1.5");

      project.getFacet(JaguarFacet.class).installIni(iniConfig);
   }
}
