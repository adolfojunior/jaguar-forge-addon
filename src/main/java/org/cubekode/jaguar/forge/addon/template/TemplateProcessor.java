package org.cubekode.jaguar.forge.addon.template;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

public class TemplateProcessor
{
   private static final String RESOURCE_PREFIX = "/jaguar-resources/";

   public static class HelperSource
   {
      public static String upper(String str)
      {
         if (str != null && !str.isEmpty())
         {
            return str.toUpperCase();
         }
         return str;
      }

      public static String lower(String str)
      {
         if (str != null && !str.isEmpty())
         {
            return str.toLowerCase();
         }
         return str;
      }

      public static String capitalize(String str)
      {
         if (str != null && !str.isEmpty())
         {
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
         }
         return str;
      }
   }

   private Handlebars handlebars;

   public TemplateProcessor()
   {
      TemplateLoader templateLoader = new ClassPathTemplateLoader();
      templateLoader.setPrefix(RESOURCE_PREFIX);
      templateLoader.setSuffix(null);

      handlebars = new Handlebars(templateLoader);
      handlebars.registerHelpers(HelperSource.class);
   }

   protected Handlebars getHandlebars()
   {
      return handlebars;
   }

   public String process(String resource, Object scope)
   {
      try
      {
         return getHandlebars().compile(resource).apply(scope);
      }
      catch (IOException e)
      {
         return null;
      }
   }

   public static void main(String[] args)
   {
      Map<String, String> scope = new HashMap<>();

      scope.put("name", "Controle");
      scope.put("topLevelPackage", "org.teste");

      TemplateProcessor templateProcessor = new TemplateProcessor();
      System.out.println(templateProcessor.process("uc/controller/MB.java", scope));
   }
}
