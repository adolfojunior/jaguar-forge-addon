package org.cubekode.jaguar.forge.addon.test;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.arquillian.AddonDependencies;
import org.jboss.forge.arquillian.archive.AddonArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@Ignore
@RunWith(Arquillian.class)
public class JaguarFacetTest
{
   @Deployment
   @AddonDependencies({})
   public static AddonArchive getDeployment()
   {
      return ShrinkWrap
               .create(AddonArchive.class)
               .addBeansXML();
   }

   @Inject
   private ResourceFactory resourceFactory;

   @Inject
   private ProjectFactory projectFactory;

   @Test
   public void testInstallIni() throws Exception
   {
//      Project project = projectFactory.createTempProject();
//
//      JaguarIniConfig config = new JaguarIniConfig(iniFile, "org.cubekode.testini", "testini", "1.0.0-SNAPSHOT");
//
//      project.getFacet(JaguarFacet.class).installIni(config);
//
//      System.out.println(project.getRoot().getFullyQualifiedName());
   }
}
