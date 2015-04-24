package org.cubekode.jaguar.forge.addon.test;

import java.util.List;

import javax.inject.Inject;

import org.cubekode.jaguar.forge.addon.project.api.JaguarDependencyUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.addon.dependencies.Coordinate;
import org.jboss.forge.addon.dependencies.DependencyResolver;
import org.jboss.forge.addon.dependencies.builder.DependencyQueryBuilder;
import org.jboss.forge.arquillian.AddonDependencies;
import org.jboss.forge.arquillian.archive.AddonArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@Ignore
@RunWith(Arquillian.class)
public class JaguarMavenTest
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
   private DependencyResolver dependencyResolver;

   @Test
   public void testJaguarVersions() throws Exception
   {
      String iniType = JaguarDependencyUtil.getIniTypes().get(0);

      DependencyQueryBuilder jaguarQuery = DependencyQueryBuilder
               .create(JaguarDependencyUtil.getIniCoordinate(iniType))
               .setRepositories(JaguarDependencyUtil.getReleasesDependencyRepository());

      List<Coordinate> jaguarVersions = dependencyResolver.resolveVersions(jaguarQuery);

      if (jaguarVersions.isEmpty())
      {
         Assert.fail("no ini versions avaible");
      }
      else
      {
         for (Coordinate version : jaguarVersions)
         {
            System.out.println(version);
         }
      }
   }
}
