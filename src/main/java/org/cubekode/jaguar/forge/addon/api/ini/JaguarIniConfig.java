package org.cubekode.jaguar.forge.addon.api.ini;

import java.util.HashMap;
import java.util.Map;

import org.jboss.forge.addon.resource.FileResource;

public class JaguarIniConfig
{
   private static final String DEFAULT_JAGUAR_VERSION = "6.1.5";
   private static final String DEFAULT_ACRONYMN = "App";
   private static final String DEFAULT_PROJECT_VERSION = "1.0.0-SNAPSHOT";

   /* Value properties - initialized in constructor */
   private FileResource<?> iniFile;
   private String groupId;
   private String artifactId;
   private String version;

   private String jaguarVersion;
   private String projectAcronymn;
   private String packageName;

   private Map<String, String> tokens = new HashMap<String, String>();

   public JaguarIniConfig(FileResource<?> iniFile, String groupId, String artifactId, String version)
   {
      this.iniFile = iniFile;
      this.groupId = groupId;
      this.artifactId = artifactId;
      this.version = version;
      fillDefaults();
   }

   public JaguarIniConfig(JaguarIniConfig config)
   {
      this.iniFile = config.iniFile;
      this.groupId = config.groupId;
      this.artifactId = config.artifactId;
      this.version = config.version;
      this.jaguarVersion = config.jaguarVersion;
      this.projectAcronymn = config.projectAcronymn;
      this.packageName = config.packageName;
      this.tokens = new HashMap<>(config.tokens);
      fillDefaults();
   }

   public JaguarIniConfig fillDefaults()
   {
      if (this.version == null || this.version.isEmpty())
      {
         this.version = DEFAULT_PROJECT_VERSION;
      }
      if (this.packageName == null || this.packageName.isEmpty())
      {
         this.packageName = groupId + "." + artifactId;
      }
      if (this.jaguarVersion == null || this.jaguarVersion.isEmpty())
      {
         this.jaguarVersion = DEFAULT_JAGUAR_VERSION;
      }
      if (this.projectAcronymn == null || this.projectAcronymn.isEmpty())
      {
         this.projectAcronymn = DEFAULT_ACRONYMN;
      }
      return this;
   }

   public String token(String key)
   {
      return tokens.get(key);
   }

   /**
    * Replace tokens in this weird format (###TOKEN_KEY###) on ini template files.
    */
   public JaguarIniConfig token(String key, String value)
   {
      this.tokens.put(key, value);
      return this;
   }

   public JaguarIniConfig groupId(String groupId)
   {
      this.groupId = groupId;
      return this;
   }

   public JaguarIniConfig artifactId(String artifactId)
   {
      this.artifactId = artifactId;
      return this;
   }

   public JaguarIniConfig version(String version)
   {
      this.version = version;
      return this;
   }

   public JaguarIniConfig jaguarVersion(String jaguarVersion)
   {
      this.jaguarVersion = jaguarVersion;
      return this;
   }

   public JaguarIniConfig projectAcronymn(String projectAcronymn)
   {
      this.projectAcronymn = projectAcronymn;
      return this;
   }

   public JaguarIniConfig packageName(String packageName)
   {
      this.packageName = packageName;
      return this;
   }

   public JaguarIniConfig tokens(Map<String, String> tokens)
   {
      this.tokens = tokens;
      return this;
   }

   public FileResource<?> getIniFile()
   {
      return iniFile;
   }

   public String getGroupId()
   {
      return groupId;
   }

   public String getArtifactId()
   {
      return artifactId;
   }

   public String getVersion()
   {
      return version;
   }

   public String getJaguarVersion()
   {
      return jaguarVersion;
   }

   public String getProjectAcronymn()
   {
      return projectAcronymn;
   }

   public String getPackageName()
   {
      return packageName;
   }

   public Map<String, String> getTokens()
   {
      return tokens;
   }
}
