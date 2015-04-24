package org.cubekode.jaguar.forge.addon.commands;

import java.util.List;

import javax.inject.Inject;

import org.cubekode.jaguar.forge.addon.project.api.JaguarDependencyUtil;
import org.jboss.forge.addon.dependencies.Coordinate;
import org.jboss.forge.addon.dependencies.DependencyResolver;
import org.jboss.forge.addon.dependencies.builder.DependencyQueryBuilder;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.output.UIOutput;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;

public class JaguarIniListCommand extends AbstractUICommand
{
   @Inject
   private DependencyResolver dependencyResolver;

   @Override
   public UICommandMetadata getMetadata(UIContext context)
   {
      return Metadata
               .forCommand(getClass())
               .category(Categories.create("Jaguar"))
               .name("jaguar-ini-list");
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
   }

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      try
      {
         String iniType = JaguarDependencyUtil.getIniTypes().get(0);
         
         DependencyQueryBuilder jaguarQuery = DependencyQueryBuilder
                  .create(JaguarDependencyUtil.getIniCoordinate(iniType))
                  .setRepositories(JaguarDependencyUtil.getReleasesDependencyRepository());

         List<Coordinate> iniCoordinates = dependencyResolver.resolveVersions(jaguarQuery);

         UIOutput output = context.getUIContext().getProvider().getOutput();

         if (iniCoordinates.isEmpty())
         {
            output.out().println("No versions found");
         }
         else
         {
            for (Coordinate coordinate : iniCoordinates)
            {
               output.out().println(coordinate.getVersion());
            }
         }
         return Results.success();
      }
      catch (Exception e)
      {
         return Results.fail("Can't list versions", e);
      }
   }
}