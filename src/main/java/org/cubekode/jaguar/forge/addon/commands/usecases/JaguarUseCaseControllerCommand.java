package org.cubekode.jaguar.forge.addon.commands.usecases;

import javax.inject.Inject;

import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.Result;

public class JaguarUseCaseControllerCommand extends AbstractJaguarCommand
{
   static final String COMMAND_NAME = "jaguar-uc-controller";

   @Inject
   @WithAttributes(label = "Name", description = "Name used in URI (/f/n/...)", required = true)
   protected UIInput<String> named;

   @Inject
   @WithAttributes(label = "Generate Facade?", description = "Should generate Facade implementation?", required = true, defaultValue = "false")
   protected UIInput<Boolean> facade;

   @Inject
   @WithAttributes(label = "Generate Repository?", description = "Should generate Repository implementation?", required = true, defaultValue = "false")
   protected UIInput<Boolean> repository;

   @Override
   protected String getCommandName()
   {
      return COMMAND_NAME;
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
      builder.add(named);
   }

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      return null;
   }
}