package {{topLevelPackage}}.controller.jsf.{{lower name}};

import javax.annotation.PostConstruct;

import {{topLevelPackage}}.controller.jsf.AppMB;

import com.powerlogic.jcompany.commons.annotation.PlcUriIoC;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcMB;
import com.powerlogic.jcompany.config.collaboration.FormPattern;
import com.powerlogic.jcompany.config.collaboration.PlcConfigForm;
import com.powerlogic.jcompany.config.collaboration.PlcConfigFormLayout;
import com.powerlogic.jcompany.controller.jsf.annotations.PlcHandleException;

/* Jaguar Use Case Configuration */
@PlcConfigForm(
   /* Jaguar Use Case Stereotype */
   formPattern = FormPattern.Ctl,
   /* Jaguar Use Case pages location */
   formLayout = @PlcConfigFormLayout(dirBase = "/WEB-INF/fcls/{{lower name}}")
)
@SPlcMB
@PlcUriIoC("{{lower name}}")
@PlcHandleException
public class {{capitalize name}}MB extends AppMB {

   private static final long serialVersionUID = 1L;

   @PostConstruct
   public void onLoad() {
   }
}
