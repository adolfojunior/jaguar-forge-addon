package org.cubekode.jaguar.forge.addon.project.api;

import java.util.Collections;

import org.cubekode.jaguar.forge.addon.project.api.wizard.JaguarProjectWizardStep;
import org.jboss.forge.addon.projects.AbstractProjectType;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.ui.wizard.UIWizardStep;

public class JaguarProjectType extends AbstractProjectType
{
   @Override
   public String getType()
   {
      return "Jaguar Project";
   }

   @Override
   public Class<? extends UIWizardStep> getSetupFlow()
   {
      return JaguarProjectWizardStep.class;
   }

   @Override
   public Iterable<Class<? extends ProjectFacet>> getRequiredFacets()
   {
      return Collections.emptyList();
   }

   @Override
   public int priority()
   {
      return 1;
   }

   @Override
   public String toString()
   {
      return "jaguar";
   }
}