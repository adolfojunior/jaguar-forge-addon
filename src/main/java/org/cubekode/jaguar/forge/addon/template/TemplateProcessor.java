package org.cubekode.jaguar.forge.addon.template;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateProcessor
{
   private static final int OUT_INITIAL_SIZE = 4096;

   private static final String RESOURCE_PREFIX = "/jaguar-resources/";

   private Configuration freemarker;

   public TemplateProcessor()
   {
      freemarker = new Configuration();
      freemarker.setClassForTemplateLoading(getClass(), RESOURCE_PREFIX);
   }

   public String process(String resource, Object scope)
   {
      CharArrayWriter out = createCharWriter();
      process(resource, scope, out);
      return out.toString();
   }

   protected Template getTemplate(String name) throws IOException
   {
      return freemarker.getTemplate(name);
   }

   protected void process(String resource, Object scope, Writer out)
   {
      try
      {
         getTemplate(resource).process(scope, out);
         out.flush();
      }
      catch (TemplateException templateException)
      {
         throw new RuntimeException(templateException);
      }
      catch (IOException ioException)
      {
         throw new RuntimeException(ioException);
      }
   }

   private CharArrayWriter createCharWriter()
   {
      return new CharArrayWriter(OUT_INITIAL_SIZE);
   }

   public static void main(String[] args)
   {
      Map<String, String> scope = new HashMap<>();

      scope.put("ucDir", "controle");
      scope.put("ucName", "controle");
      scope.put("ucClassName", "ControleMB");
      scope.put("topLevelPackage", "org.teste");

      TemplateProcessor templateProcessor = new TemplateProcessor();
      
      System.out.println(templateProcessor.process("uc/controller/MB.java", scope));
   }
}
