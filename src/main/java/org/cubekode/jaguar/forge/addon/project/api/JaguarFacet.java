package org.cubekode.jaguar.forge.addon.project.api;

import java.io.IOException;

import org.cubekode.jaguar.forge.addon.project.api.ini.JaguarIniConfig;
import org.jboss.forge.addon.projects.ProvidedProjectFacet;

public interface JaguarFacet extends ProvidedProjectFacet
{
   /**
    * Install Jaguar INI Configuration
    */
   void installIni(JaguarIniConfig iniConfig) throws IOException;
}
