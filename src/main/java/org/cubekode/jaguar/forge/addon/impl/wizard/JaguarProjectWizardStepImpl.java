package org.cubekode.jaguar.forge.addon.impl.wizard;

import javax.inject.Inject;

import org.cubekode.jaguar.forge.addon.api.JaguarFacet;
import org.cubekode.jaguar.forge.addon.api.ini.JaguarIniConfig;
import org.cubekode.jaguar.forge.addon.api.wizard.JaguarProjectWizardStep;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.MetadataFacet;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.context.UINavigationContext;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.NavigationResult;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;

public class JaguarProjectWizardStepImpl extends AbstractUICommand implements JaguarProjectWizardStep
{
   @Inject
   @WithAttributes(label = "Jaguar INI", description = "The location of Jaguar INI Project Template", required = true)
   protected UIInput<FileResource<?>> iniLocation;

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
      builder.add(iniLocation);
   }

   @Override
   public NavigationResult next(UINavigationContext navigationContext) throws Exception
   {
      return null;
   }

   @Override
   public Result execute(UIExecutionContext executionContext) throws Exception
   {
      Project project = (Project) executionContext.getUIContext().getAttributeMap().get(Project.class);

      JaguarFacet jaguarFacet = project.getFacet(JaguarFacet.class);
      MetadataFacet metadataFacet = project.getFacet(MetadataFacet.class);

      JaguarIniConfig iniConfig = new JaguarIniConfig(iniLocation.getValue(), metadataFacet.getProjectGroupName(), metadataFacet.getProjectName(), metadataFacet.getProjectVersion());

      // TODO must be in INI or in coordinator?
      // I think that INI must be versioned for each Jaguar version!
      iniConfig.jaguarVersion("6.1.5");

      jaguarFacet.installIni(iniConfig);
      
      return Results.success("Jaguar Project Created in: " + project.getRoot().getFullyQualifiedName());
   }
}
