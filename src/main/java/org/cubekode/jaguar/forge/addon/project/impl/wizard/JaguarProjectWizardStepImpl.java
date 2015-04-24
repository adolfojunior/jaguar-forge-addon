package org.cubekode.jaguar.forge.addon.project.impl.wizard;

import java.util.List;

import javax.inject.Inject;

import org.apache.maven.model.Model;
import org.cubekode.jaguar.forge.addon.project.api.JaguarDependencyUtil;
import org.cubekode.jaguar.forge.addon.project.api.JaguarFacet;
import org.cubekode.jaguar.forge.addon.project.api.ini.JaguarIniConfig;
import org.cubekode.jaguar.forge.addon.project.api.wizard.JaguarProjectWizardStep;
import org.jboss.forge.addon.convert.Converter;
import org.jboss.forge.addon.dependencies.Coordinate;
import org.jboss.forge.addon.dependencies.Dependency;
import org.jboss.forge.addon.dependencies.DependencyRepository;
import org.jboss.forge.addon.dependencies.DependencyResolver;
import org.jboss.forge.addon.dependencies.builder.DependencyQueryBuilder;
import org.jboss.forge.addon.maven.projects.MavenFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.MetadataFacet;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.context.UINavigationContext;
import org.jboss.forge.addon.ui.input.UISelectOne;
import org.jboss.forge.addon.ui.input.ValueChangeListener;
import org.jboss.forge.addon.ui.input.events.ValueChangeEvent;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.NavigationResult;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;

public class JaguarProjectWizardStepImpl extends AbstractUICommand implements JaguarProjectWizardStep
{
   @Inject
   @WithAttributes(label = "Jaguar INI", description = "Type of template, simple, bridge, etc...", required = true)
   protected UISelectOne<String> iniType;

   @Inject
   @WithAttributes(label = "Jaguar Version", description = "Version of framework Jaguar", required = true)
   protected UISelectOne<Coordinate> iniVersion;

   @Inject
   private DependencyResolver dependencyResolver;

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
      iniType.setValueChoices(JaguarDependencyUtil.getIniTypes());
      iniType.addValueChangeListener(new ValueChangeListener()
      {
         @Override
         public void valueChanged(ValueChangeEvent event)
         {
            String selectedType = (String) event.getNewValue();

            updateVersions(selectedType);
         }
      });

      iniVersion.setEnabled(false);
      iniVersion.setItemLabelConverter(new Converter<Coordinate, String>()
      {
         @Override
         public String convert(Coordinate coordinate)
         {
            return coordinate != null ? coordinate.getVersion() : "";
         }
      });

      builder.add(iniType)
               .add(iniVersion);
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

      Dependency iniDependency = getIniDependency(iniVersion.getValue());

      if (iniDependency != null)
      {
         JaguarFacet jaguarFacet = project.getFacet(JaguarFacet.class);
         MetadataFacet metadataFacet = project.getFacet(MetadataFacet.class);

         String projectName = metadataFacet.getProjectName();
         String projectGroupName = metadataFacet.getProjectGroupName();
         String projectVersion = metadataFacet.getProjectVersion();
         
         JaguarIniConfig config = new JaguarIniConfig(iniDependency.getArtifact(), projectGroupName, projectName, projectVersion);
         
         config.setJaguarVersion(iniDependency.getCoordinate().getVersion());

         jaguarFacet.installIni(config);
         
         installReleasesRepository(project);

         return Results.success("Jaguar Project Created in: " + project.getRoot().getFullyQualifiedName());
      }
      else
      {
         return Results.fail("Can't retrieve INI dependency: " + iniVersion.getValue());
      }
   }

   private void installReleasesRepository(Project project)
   {
      MavenFacet mavenFacet = project.getFacet(MavenFacet.class);
      
      Model pom = mavenFacet.getModel();
      pom.addRepository(JaguarDependencyUtil.getReleasesRepository());
      pom.addPluginRepository(JaguarDependencyUtil.getReleasesRepository());
      
      // persist pom
      mavenFacet.setModel(pom);
   }

   private void updateVersions(String iniType)
   {
      List<Coordinate> versions = listIniCoordinates(iniType);

      iniVersion.setValueChoices(versions);
      iniVersion.setEnabled(!versions.isEmpty());
   }

   private List<Coordinate> listIniCoordinates(String iniType)
   {
      DependencyQueryBuilder iniQuery = DependencyQueryBuilder
               .create(JaguarDependencyUtil.getIniCoordinate(iniType))
               .setRepositories(getRepository());

      return dependencyResolver.resolveVersions(iniQuery);
   }

   private Dependency getIniDependency(Coordinate iniCoordinate)
   {
      DependencyQueryBuilder iniQuery = DependencyQueryBuilder
               .create(iniCoordinate)
               .setRepositories(getRepository());

      return dependencyResolver.resolveArtifact(iniQuery);
   }

   private DependencyRepository getRepository()
   {
      return JaguarDependencyUtil.getReleasesDependencyRepository();
   }
}
