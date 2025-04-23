package com.marketing.Repository.DBMailMarketing;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.DBMailMarketing.TblEstilosSite;

@Repository
public interface TblEstilosSiteRepo extends JpaRepository<TblEstilosSite, Integer> {
	
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'navBar' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String Navbar_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'LetraNavbar' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String LetraNavbar_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'LetraNavbar' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String LetraNavbar_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'botonCarrusel' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String botonCarrusel_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgCarrusel_1' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgCarrusel_1_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgCarrusel_2' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgCarrusel_2_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgCarrusel_3' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgCarrusel_3_imagen(int IDLOCAL);
	
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'textCarrusel_1' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String textCarrusel_1_color(int IDLOCAL); 
	
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'textCarrusel_1' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String textCarrusel_1_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'textCarrusel_2' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String textCarrusel_2_color(int IDLOCAL); 
	
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'textCarrusel_2' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String textCarrusel_2_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'textCarrusel_1' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String textCarrusel_1_contenido(int IDLOCAL); 
	
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'textCarrusel_2' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  textCarrusel_2_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'textCarrusel_3' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  textCarrusel_3_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'titulo' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  titulo_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'titulo' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String  titulo_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'nosotrosResumen' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  nosotrosResumen_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'nosotrosResumen' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String  nosotrosResumen_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'nosotrosResumen' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  nosotrosResumen_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'nosotrosTexto' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  nosotrosTexto_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'nosotrosTexto' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String  nosotrosTexto_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'nosotrosTexto' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  nosotrosTexto_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'tituloNombreEmpresa' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  tituloNombreEmpresa_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'tituloNombreEmpresa' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String  tituloNombreEmpresa_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'tituloNombreEmpresa' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  tituloNombreEmpresa_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'checkList' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  checkList_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'checkList_texto' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  checkList_texto_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'checkList_texto' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String  checkList_texto_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'checkList_texto_1' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  checkList_texto_1_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'checkList_texto_2' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  checkList_texto_2_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'checkList_texto_3' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  checkList_texto_3_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'subtitulo' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  subtitulo_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'subtitulo' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String  subtitulo_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'linkTexto' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  linkTexto_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'linkTexto' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String  linkTexto_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'boton' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  boton_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'footer' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  footer_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'LetraFooter' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  LetraFooter_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'LetraFooter' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String  LetraFooter_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'subtituloFooter' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String  subtituloFooter_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'subtituloFooter' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  subtituloFooter_color(int IDLOCAL); 
	
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgNotrosResumen_1' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgNotrosResumen_1_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgNotrosResumen_2' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgNotrosResumen_2_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgNotrosResumen_3' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgNotrosResumen_3_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgNoticia_1' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgNoticia_1_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgNoticia_2' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgNoticia_2_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgNoticia_3' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgNoticia_3_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'quienesSomosTexto' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  quienesSomosTexto_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'quienesSomosTexto' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String  quienesSomosTexto_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'quienesSomosTexto' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  quienesSomosTexto_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'misionTexto' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  misionTexto_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'misionTexto' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String  misionTexto_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'misionTexto' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  misionTexto_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'visionTexto' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  visionTexto_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'visionTexto' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String visionTexto_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'visionTexto' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  visionTexto_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'historiaTexto' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  historiaTexto_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'historiaTexto' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String historiaTexto_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'historiaTexto' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  historiaTexto_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'objetivoTexto' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  objetivoTexto_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'objetivoTexto' " +
			  "AND tblEstilosSite.tipo = 'fuente' ", nativeQuery = true)
	    String objetivoTexto_fuente(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'objetivoTexto' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  objetivoTexto_contenido(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgQuienesSomos_1' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgQuienesSomos_1_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgQuienesSomos_2' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgQuienesSomos_2_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgQuienesSomos_3' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgQuienesSomos_3_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgMision_1' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgMision_1_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgMision_2' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgMision_2_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgMision_3' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgMision_3_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgVision_1' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgVision_1_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgVision_2' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgVision_2_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgVision_3' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgVision_3_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'direccion' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  direccion_contenido(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'telefono' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  telefono_contenido(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'correo' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  correo_contenido(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'icono' " +
			  "AND tblEstilosSite.tipo = 'color' ", nativeQuery = true)
	    String  icono_color(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'videoQuienesSomos' " +
			  "AND tblEstilosSite.tipo = 'video' ", nativeQuery = true)
	    String  videoQuienesSomos_video(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'videoMision' " +
			  "AND tblEstilosSite.tipo = 'video' ", nativeQuery = true)
	    String  videoMision_video(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'videoVision' " +
			  "AND tblEstilosSite.tipo = 'video' ", nativeQuery = true)
	    String  videoVision_video(int IDLOCAL); 
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgHistoria_1' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgHistoria_1_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgHistoria_2' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgHistoria_2_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgHistoria_3' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgHistoria_3_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'imgObjetivo' " +
			  "AND tblEstilosSite.tipo = 'imagen' ", nativeQuery = true)
	    String imgObjetivo_imagen(int IDLOCAL);
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'puntoDePago_1' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  puntoDePago_1_contenido(int IDLOCAL);
	
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'puntoDePago_2' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  puntoDePago_2_contenido(int IDLOCAL);
	
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'puntoDePago_3' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  puntoDePago_3_contenido(int IDLOCAL);
	
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'puntoDePago_4' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  puntoDePago_4_contenido(int IDLOCAL);
	
	
	@Query(value = "SELECT * " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND campo IN ('puntoDePago_1', 'puntoDePago_2', 'puntoDePago_3') ",
			  nativeQuery = true)
	    List<TblEstilosSite> listaPuntosDePago (int IDLOCAL);
	
	
	@Query(value = "SELECT tblEstilosSite.valor " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.campo = 'GooGleMaps' " +
			  "AND tblEstilosSite.tipo = 'contenido' ", nativeQuery = true)
	    String  GoogleMaps(int IDLOCAL);
	
	
	
	@Query(value = "SELECT * " +
	  		 "FROM BDMailMarketing.dbo.tblEstilosSite " +
			  "WHERE tblEstilosSite.IDLOCAL = ?1 " +
			  "AND tblEstilosSite.tipo = 'documento' " +
			  "order by idStyle desc ", nativeQuery = true)
	    List<TblEstilosSite> Documentos(int IDLOCAL);
	

}
