package org.cubekode.jaguar.forge.addon.project.api;

import java.util.Arrays;
import java.util.List;

import org.apache.maven.model.Repository;
import org.apache.maven.model.RepositoryPolicy;
import org.jboss.forge.addon.dependencies.Coordinate;
import org.jboss.forge.addon.dependencies.DependencyRepository;
import org.jboss.forge.addon.dependencies.builder.CoordinateBuilder;

public class JaguarDependencyUtil
{
   private static List<String> iniTypes = Arrays.asList(
            "jcompany_ini_extension",
            "jcompany_ini_facelets_bridge",
            "jcompany_ini_facelets_cdi",
            "jcompany_ini_facelets_cdi_bridge",
            "jcompany_ini_facelets_cdi_bridge_simple",
            "jcompany_ini_facelets_cdi_jboss",
            "jcompany_ini_facelets_cdi_modulo",
            "jcompany_ini_facelets_cdi_modulo_simple",
            "jcompany_ini_facelets_cdi_prime",
            "jcompany_ini_facelets_cdi_rich",
            "jcompany_ini_facelets_cdi_simple",
            "jcompany_ini_facelets_modulo_cdi",
            "jcompany_ini_facelets_modulo_cdi_simple",
            "jcompany_ini_jaguar_archetype",
            "jcompany_ini_modulo_negocio",
            "jcompany_ini_modulo_servico"
            );

   public static List<String> getIniTypes()
   {
      return iniTypes;
   }

   public static Coordinate getJaguarCoordinate()
   {
      return CoordinateBuilder.create()
               .setGroupId("powerlogic.jaguar")
               .setArtifactId("jcompany");
   }

   public static Coordinate getIniCoordinate(String iniType)
   {
      return CoordinateBuilder.create()
               .setGroupId("powerlogic.jaguar.jcompany.ini")
               .setArtifactId(iniType)
               .setPackaging("zip");
   }

   public static DependencyRepository getSnapshotsDependencyRepository()
   {
      return new DependencyRepository("powersnapshots", "http://archiva.powerlogic.com.br:8080/archiva/repository/powersnapshots");
   }

   public static DependencyRepository getReleasesDependencyRepository()
   {
      return new DependencyRepository("powerreleases", "http://archiva.powerlogic.com.br:8080/archiva/repository/powerreleases");
   }
   
   public static Repository getReleasesRepository()
   {
      Repository rep = create(getReleasesDependencyRepository());
      rep.getReleases().setEnabled(true);
      return rep;
   }
   
   public static Repository getSnapshotsRepository()
   {
      Repository rep = create(getSnapshotsDependencyRepository());
      rep.getReleases().setEnabled(true);
      return rep;
   }

   private static Repository create(DependencyRepository dep)
   {
      Repository rep = new Repository();
      rep.setId(dep.getId());
      rep.setUrl(dep.getUrl());
      rep.setReleases(new RepositoryPolicy());
      return rep;
   }
}
