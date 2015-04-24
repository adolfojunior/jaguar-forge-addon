package org.cubekode.jaguar.forge.addon.project.impl;

import java.io.IOException;

import org.cubekode.jaguar.forge.addon.project.api.JaguarFacet;
import org.cubekode.jaguar.forge.addon.project.api.ini.JaguarIniConfig;
import org.cubekode.jaguar.forge.addon.project.impl.ini.JaguarIniInstaller;
import org.jboss.forge.addon.facets.AbstractFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.resource.DirectoryResource;

public class JaguarFacetImpl extends AbstractFacet<Project> implements JaguarFacet
{
   @Override
   public boolean install()
   {
      return true;
   }

   @Override
   public boolean isInstalled()
   {
      return true;
   }
   
   @Override
   public void installIni(JaguarIniConfig iniConfig) throws IOException
   {
      new JaguarIniInstaller(iniConfig).install(getFaceted().getRoot().reify(DirectoryResource.class));
   }
}
