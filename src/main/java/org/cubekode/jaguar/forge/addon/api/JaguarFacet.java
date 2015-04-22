package org.cubekode.jaguar.forge.addon.api;

import java.io.IOException;

import org.cubekode.jaguar.forge.addon.api.ini.JaguarIniConfig;
import org.jboss.forge.addon.projects.ProvidedProjectFacet;

public interface JaguarFacet extends ProvidedProjectFacet
{
   /**
    * Install Jaguar INI Configuration
    */
   void installIni(JaguarIniConfig iniConfig) throws IOException;
}
