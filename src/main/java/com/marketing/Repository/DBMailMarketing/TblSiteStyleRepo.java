package com.marketing.Repository.DBMailMarketing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.DBMailMarketing.TblSiteStyle;

@Repository
public interface TblSiteStyleRepo extends JpaRepository<TblSiteStyle, Integer>{

	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'navBar' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String Navbar_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'LetraNavbar' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String LetraNavbar_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'LetraNavbar' " +
			  "AND tblSiteStyle.tipo = 'fuente' ", nativeQuery = true)
	    String LetraNavbar_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'botonCarrusel' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String botonCarrusel_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgCarrusel_1' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgCarrusel_1_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgCarrusel_1' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgCarrusel_2_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'textCarrusel_1' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String textCarrusel_1_color(int IDLOCAL); 
	
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'textCarrusel_1' " +
			  "AND tblSiteStyle.tipo = 'fuente' ", nativeQuery = true)
	    String textCarrusel_1_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'textCarrusel_2' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String textCarrusel_2_color(int IDLOCAL); 
	
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'textCarrusel_2' " +
			  "AND tblSiteStyle.tipo = 'fuente' ", nativeQuery = true)
	    String textCarrusel_2_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'textCarrusel_1' " +
			  "AND tblSiteStyle.tipo = 'contenido' ", nativeQuery = true)
	    String textCarrusel_1_contenido(int IDLOCAL); 
	
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'textCarrusel_2' " +
			  "AND tblSiteStyle.tipo = 'contenido' ", nativeQuery = true)
	    String  textCarrusel_2_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'textCarrusel_3' " +
			  "AND tblSiteStyle.tipo = 'contenido' ", nativeQuery = true)
	    String  textCarrusel_3_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'titulo' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String  titulo_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'titulo' " +
			  "AND tblSiteStyle.tipo = 'fuente' ", nativeQuery = true)
	    String  titulo_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'nosotrosResumen' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String  nosotrosResumen_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'nosotrosResumen' " +
			  "AND tblSiteStyle.tipo = 'fuente' ", nativeQuery = true)
	    String  nosotrosResumen_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'nosotrosResumen' " +
			  "AND tblSiteStyle.tipo = 'contenido' ", nativeQuery = true)
	    String  nosotrosResumen_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'nosotrosTexto' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String  nosotrosTexto_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'nosotrosTexto' " +
			  "AND tblSiteStyle.tipo = 'fuente' ", nativeQuery = true)
	    String  nosotrosTexto_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'nosotrosTexto' " +
			  "AND tblSiteStyle.tipo = 'contenido' ", nativeQuery = true)
	    String  nosotrosTexto_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'tituloNombreEmpresa' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String  tituloNombreEmpresa_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'tituloNombreEmpresa' " +
			  "AND tblSiteStyle.tipo = 'fuente' ", nativeQuery = true)
	    String  tituloNombreEmpresa_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'tituloNombreEmpresa' " +
			  "AND tblSiteStyle.tipo = 'contenido' ", nativeQuery = true)
	    String  tituloNombreEmpresa_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'checkList' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String  checkList_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'checkList_texto' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String  checkList_texto_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'checkList_texto' " +
			  "AND tblSiteStyle.tipo = 'fuente' ", nativeQuery = true)
	    String  checkList_texto_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'checkList_texto_1' " +
			  "AND tblSiteStyle.tipo = 'contenido' ", nativeQuery = true)
	    String  checkList_texto_1_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'checkList_texto_2' " +
			  "AND tblSiteStyle.tipo = 'contenido' ", nativeQuery = true)
	    String  checkList_texto_2_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'checkList_texto_3' " +
			  "AND tblSiteStyle.tipo = 'contenido' ", nativeQuery = true)
	    String  checkList_texto_3_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'subtitulo' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String  subtitulo_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'subtitulo' " +
			  "AND tblSiteStyle.tipo = 'fuente' ", nativeQuery = true)
	    String  subtitulo_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'linkTexto' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String  linkTexto_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'linkTexto' " +
			  "AND tblSiteStyle.tipo = 'fuente' ", nativeQuery = true)
	    String  linkTexto_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'boton' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String  boton_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'footer' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String  footer_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'LetraFooter' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String  LetraFooter_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'LetraFooter' " +
			  "AND tblSiteStyle.tipo = 'fuente' ", nativeQuery = true)
	    String  LetraFooter_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'subtituloFooter' " +
			  "AND tblSiteStyle.tipo = 'fuente' ", nativeQuery = true)
	    String  subtituloFooter_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'subtituloFooter' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String  subtituloFooter_color(int IDLOCAL); 
	
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgNotrosResumen_1' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgNotrosResumen_1_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgNotrosResumen_2' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgNotrosResumen_2_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgNotrosResumen_3' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgNotrosResumen_3_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgNoticia_1' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgNoticia_1_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgNoticia_2' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgNoticia_2_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgNoticia_3' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgNoticia_3_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'quienesSomosTexto' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String  quienesSomosTexto_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'quienesSomosTexto' " +
			  "AND tblSiteStyle.tipo = 'fuente' ", nativeQuery = true)
	    String  quienesSomosTexto_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'quienesSomosTexto' " +
			  "AND tblSiteStyle.tipo = 'contenido' ", nativeQuery = true)
	    String  quienesSomosTexto_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'misionTexto' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String  misionTexto_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'misionTexto' " +
			  "AND tblSiteStyle.tipo = 'fuente' ", nativeQuery = true)
	    String  misionTexto_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'misionTexto' " +
			  "AND tblSiteStyle.tipo = 'contenido' ", nativeQuery = true)
	    String  misionTexto_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'visionTexto' " +
			  "AND tblSiteStyle.tipo = 'color' ", nativeQuery = true)
	    String  visionTexto_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'visionTexto' " +
			  "AND tblSiteStyle.tipo = 'fuente' ", nativeQuery = true)
	    String visionTexto_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'visionTexto' " +
			  "AND tblSiteStyle.tipo = 'contenido' ", nativeQuery = true)
	    String  visionTexto_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgQuienesSomos_1' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgQuienesSomos_1_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgQuienesSomos_2' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgQuienesSomos_2_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgQuienesSomos_3' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgQuienesSomos_3_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgMision_1' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgMision_1_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgMision_2' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgMision_2_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgMision_3' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgMision_3_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgVision_1' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgVision_1_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgVision_2' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgVision_2_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'imgVision_3' " +
			  "AND tblSiteStyle.tipo = 'imagen' ", nativeQuery = true)
	    String imgVision_3_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'direccion' " +
			  "AND tblSiteStyle.tipo = 'contenido' ", nativeQuery = true)
	    String  direccion_contenido(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'telefono' " +
			  "AND tblSiteStyle.tipo = 'contenido' ", nativeQuery = true)
	    String  telefono_contenido(int IDLOCAL);
	
	@Query(value = "SELECT tblSiteStyle.valor " +
	  		 "FROM BDMailMarketing.dbo.tblSiteStyle " +
			  "WHERE tblSiteStyle.IDLOCAL = ?1 " +
			  "AND tblSiteStyle.campo = 'correo' " +
			  "AND tblSiteStyle.tipo = 'contenido' ", nativeQuery = true)
	    String  correo_contenido(int IDLOCAL);
}
