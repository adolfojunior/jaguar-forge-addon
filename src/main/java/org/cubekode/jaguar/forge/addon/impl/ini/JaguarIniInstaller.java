package org.cubekode.jaguar.forge.addon.impl.ini;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.cubekode.jaguar.forge.addon.api.ini.JaguarIniConfig;
import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.furnace.util.Streams;

public class JaguarIniInstaller
{
   /**
    * Yep, another token pattern.
    */
   private static final Pattern JAGUAR_TOKEN_PATTERN = Pattern.compile("###(\\w+)###");

   private static final String JAGUAR_TK_SIGLA_PROJETO = "SIGLA_PROJETO";
   private static final String JAGUAR_TK_NOME_PROJETO = "NOME_PROJETO";
   private static final String JAGUAR_TK_NOME_PROJETO_PRINCIPAL = "NOME_PROJETO_PRINCIPAL";
   private static final String JAGUAR_TK_NOME_PACOTE = "NOME_PACOTE";
   private static final String JAGUAR_TK_USUARIO = "USUARIO";
   private static final String JAGUAR_TK_SENHA = "SENHA";
   private static final String JAGUAR_TK_SID = "SID";
   private static final String JAGUAR_TK_NOME_DO_ESQUEMA = "NOME DO ESQUEMA";
   private static final String JAGUAR_TK_VERSAO_JCOMPANY = "VERSAO_JCOMPANY";

   private static final Set<String> PARSEABLE_EXTENSIONS = new HashSet<>(Arrays.asList(
            // commom project config files
            "xml",
            "properties",
            // java of course
            "java",
            // TODO Eclipse things, should i exclude then?!
            // this project can be imported directly in IntelliJ or NetBeans now.
            "project",
            "classpath",
            "prefs",
            // jCompany QA, i think that was forgotten there...
            "jcqa"
            ));
   
   private static final String PROJECT_PARENT = "projetoplc_parent";

   private JaguarIniConfig config;

   public JaguarIniInstaller(JaguarIniConfig config)
   {
      this.config = config;
   }

   public void install(DirectoryResource outputDir) throws IOException
   {
      // overlay config
      config = new JaguarIniConfig(config);
      // main tokens
      config.token(JAGUAR_TK_SIGLA_PROJETO, config.getProjectAcronymn());
      config.token(JAGUAR_TK_NOME_PROJETO, config.getArtifactId());
      config.token(JAGUAR_TK_NOME_PROJETO_PRINCIPAL, config.getArtifactId());
      config.token(JAGUAR_TK_NOME_PACOTE, config.getPackageName());
      // embedded database tokens?
      config.token(JAGUAR_TK_USUARIO, config.getProjectAcronymn().toLowerCase());
      config.token(JAGUAR_TK_SENHA, config.getProjectAcronymn().toLowerCase());
      config.token(JAGUAR_TK_SID, config.getProjectAcronymn().toUpperCase());
      config.token(JAGUAR_TK_NOME_DO_ESQUEMA, config.getProjectAcronymn().toUpperCase());
      // TODO The framework version MUST be in INI, but ok.
      config.token(JAGUAR_TK_VERSAO_JCOMPANY, config.getJaguarVersion());
      // process
      installRoot(outputDir);
   }

   protected void installRoot(DirectoryResource outputDir) throws IOException
   {
      // TODO Is necessary remove all resources created by MavenBuildSystems
      outputDir.delete(true);
      outputDir.mkdirs();
      
      info("Creating Jaguar Project(%s) groupId: %s, artifactId: %s, version: %s in directory: %s", config.getJaguarVersion(), config.getGroupId(), config.getArtifactId(), config.getVersion(), outputDir);

      try (ZipInputStream zip = new ZipInputStream(config.getIniFile().getResourceInputStream()))
      {
         while (true)
         {
            ZipEntry entry = zip.getNextEntry();
            // no more entries
            if (entry == null)
            {
               break;
            }
            if (!entry.isDirectory())
            {
               installFile(outputDir, entry.getName(), zip);
            }
            else
            {
               // TODO should create empty dirs?
            }
            zip.closeEntry();
         }
      }
      catch (Exception e)
      {
         // remove all files
         outputDir.delete(true);

         throw new IOException(e.getMessage(), e);
      }
      
      // TODO move context.xml
      
      // TODO rename to parent.
      if (isProjectParent(outputDir))
      {
         outputDir.renameTo(config.getArtifactId() + "_parent");
         moveTomcatContext(outputDir.getChildDirectory(config.getArtifactId()));
      }
      else
      {
         moveTomcatContext(outputDir);
      }
   }

   private void moveTomcatContext(DirectoryResource childDirectory)
   {
      // TODO Auto-generated method stub
      
   }

   private boolean isProjectParent(DirectoryResource outputDir)
   {
      return outputDir.getChildDirectory(config.getArtifactId()) != null && outputDir.getChildDirectory(config.getArtifactId() + "_commons") != null;
   }

   protected void installFile(DirectoryResource outputDir, String filePath, InputStream fileIn) throws UnsupportedEncodingException, IOException, FileNotFoundException
   {
      String path = parsePath(filePath);

      debug("Processing resource: %s", path);

      FileResource<?> file = outputDir.getChild(path).reify(FileResource.class);

      // create file structure
      file.getParent().mkdirs();

      // fill file content
      try (OutputStream out = file.getResourceOutputStream())
      {
         if (isParseableFile(file.getName()))
         {
            // parse
            String fileContent = parseText(Streams.toString(fileIn));
            //
            Streams.write(new ByteArrayInputStream(fileContent.getBytes()), out);
         }
         else
         {
            Streams.write(fileIn, out);
         }
      }
   }

   private boolean isParseableFile(String name)
   {
      int dot = name.lastIndexOf(".");
      if (dot != -1)
      {
         return PARSEABLE_EXTENSIONS.contains(name.substring(dot + 1));
      }
      return false;
   }

   private String parsePath(String path)
   {
      String p = parseText(path);
      // removo parent
      if (p.startsWith(PROJECT_PARENT))
      {
         p = p.substring(PROJECT_PARENT.length() + 1);
      }
      // TODO Why dont use __token__ pattern in Archtype?
      p = p.replace("projetoplc", config.getArtifactId());
      p = p.replace("/com/empresa/app/", "/" + config.getPackageName().replace('.', '/') + "/");
      return p;
   }

   private String parseText(String text)
   {
      final Matcher matcher = JAGUAR_TOKEN_PATTERN.matcher(text);
      final StringBuilder result = new StringBuilder(text.length());

      boolean replaced = false;

      int pos = 0;

      while (matcher.find())
      {
         result.append(text.substring(pos, matcher.start()));

         String value = config.token(matcher.group(1));

         if (value != null)
         {
            replaced = true;
            result.append(value);
         }
         else
         {
            result.append(matcher.group(0));
         }
         pos = matcher.end();
      }
      // add chars that left over
      if (pos < text.length())
      {
         result.append(text.substring(pos));
      }
      return replaced ? result.toString() : text;
   }

   private void info(String s, Object... args)
   {
      if (args != null && args.length > 0)
      {
         System.out.println(String.format(s, args));
      }
      else
      {
         System.out.println(s);
      }
   }

   private void debug(String s, Object... args)
   {
      info(s, args);
   }
}
