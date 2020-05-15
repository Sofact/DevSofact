
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	 	<link rel="stylesheet" type="text/css" href="style.css">
		<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
		<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
		<link rel="stylesheet" type="text/css" href="last.css">
		 
<!-- script carga Departamentos -->		
<script>
		
$(document).ready(function(){
		$.ajax({
			type: 'GET',
			url: 'DepartamentoServlet',
			data: 'depto=1',
			statusCode: {
				404: function(){
					alert("Pagina no encontrada");
				},
				500: function(){
					alert("Error en servidor");
				}
			},
			success: function(res){
				var separado = res.split(":");
				
				document.getElementById("departamento").length=0;
				$('select[name=departamento]').append (' <option>Seleccione...</option>')
				for(var i = 0; i< separado.length -1; i++){
				
					var idDepto = separado[i].split("-")[0];
					var nombreDepto = separado[i].split("-")[1];
					$('select[name=departamento]').append('<option value = "'+idDepto +'"> '+ nombreDepto+ '</option>')
				}
				
			}
			
		});

});
		
</script>	
<!-- script carga Municipios filtrados por departamento -->		
<script>
		
$(document).ready(function(){
	$('select[name=departamento]').on('change', function(){
		$.ajax({
			type: 'GET',
			url: 'MunicipioServlet',
			data: 'depto='+$('select[name=departamento]').val(),
			statusCode: {
				404: function(){
					alert("Pagina no encontrada");
				},
				500: function(){
					alert("Error en servidor " +'depto='+$('select [name=departamento]').val());
				}
			},
			success: function(res){
				var separado = res.split(":");
				
				document.getElementById("municipio").length=0;
				$('select[name=municipio]').append (' <option>Seleccione...</option>')
				for(var i = 0; i< separado.length -1; i++){
				
					var idMuni = separado[i].split("-")[0];
					var nombreMuni = separado[i].split("-")[1];
					$('select[name=municipio]').append('<option value = "'+idMuni +'"> '+ nombreMuni+ '</option>')
				}
				
			}
			
		});
	})
});
		
</script>	
		
<script>
	

	$(document).ready(function(){
		$('a[id=colapsador]').on('click', function(){
		
			document.getElementById("articulo").style.height = "700px";
			
		})
	});
	
	</script>			
		
	</head>
	<body>
	
	   <header> 
	<br>
	<div class="col-xs-12" >
	<table>
		<tr>
		<td width= "50%">
		</td>
			<td width= "50%">
	    		 <img src="./admin/SofActBlue.png" width="170px" height="50px">
	   		 
	    <td width= "50%">
	     <div><h3><%=request.getUserPrincipal().getName()%></h3></div>
	  	</td>
	  	</tr>
	</table>
	</div>
	</header>
	   
	
<nav class="navbar navbar-default sidebar" role="navigation">
    <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-sidebar-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>      
    </div>
    <div class="collapse navbar-collapse" id="bs-sidebar-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li ><a href="home.jsp">Home<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-home"></span></a></li>  
        <li ><a  href="./FacturaServlet">Facturas<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a></li>        
        <li ><a href="FacturaPos.jsp">Facturas POS<span	style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a></li>
        <li ><a href="NotaCredito.jsp">Notas Credito<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-th-list"></span></a></li>
        <li ><a href="./NotaDebitoServlet">Notas Debito<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-tags"></span></a></li>
        <li ><a href="#">Reportes<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
        <li data-toggle="collapse" data-target="#service" class="collapsed" ><a id="colapsador" >Administración<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-cog"></span></a></li>
      		 <ul class="sub-menu collapse nav navbar-nav" id="service"> 
                  <li class="active"><a href="./FacturaServlet">Crear Cliente<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-globe"></span></a></li>
                  <li ><a href="crearProducto.jsp">Crear Usuario<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span></a></li>
                  <li><a href="UpdateProducto.jsp">Actualizar Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
                  <li ><a href="CrearUsuario.jsp">Crear Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-shopping-cart"></span></a></li>
                  <li ><a href="CrearTipoProducto.jsp">Crear Tipo Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-barcode"></span></a></li>
                  <li ><a href="CrearMarca.jsp">Crear Marca<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-eye-open"></span></a></li>
                  <li ><a href="CrearMedida.jsp">Crear Medida<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-wrench"></span></a></li>
                  

      </ul>
    </div>
  </div>
</nav>
	    
	     
	     <article id="articulo" style= "height: 450px">
	     <br>
	     <h3>NUEVO CLIENTE</h3>
	   	 <HR />
	     <form class="form-horizontal" method="POST" action="./SaveClientServlet">
	      <br>
	     
	     <table id= "tabla_form" >
	     	<tr>
	     		<td><label for="sel1">Primer Nombre: </label>
	     		</td>
	     		<td>
					<input type="text" class="form-control" id="primerNombre" name="primerNombre">				
	     		</td>
	     		<td> <label for="usr">Segundo Nombre:  </label>
	     		</td>
	     		<td><input type="text" class="form-control" id="segundoNombre" name="segundoNombre">
	     		</td>
	     	</tr>
	     	<tr>
	     		<td><label for="sel1">Apellidos: </label>
	     		</td>
	     		<td>
					<input type="text" class="form-control" id="apellidos" name="apellidos">				
	     		</td>
	     		<td> <label for="usr">Identificacion:  </label>
	     		</td>
	     		<td><table><tr><td>
	     			<select class="form-control" id="tipoIdent" name="tipoIdent">
										    <option id="ced">CC</option>
										    <option id="nit">NIT</option>
										    <option id="ce">CE</option>
										    <option id="ti">TI</option>
					 </select></td>
					 <td>
					<input type="text" class="form-control" id="identificacion" name="identificacion" >
					<input type="hidden" id="cli_id"	name="cli_id">		   
					</td></tr>
					</table>
	     		</td>
	     	</tr>
	     	<!-- inicia segunda linea del formulario -->
	     	<tr>
	     		<td><label for="sel1">Direccion: </label> </div>
	     		</td>
	     		<td> <input type="text" class="form-control" id="direccion" name="direccion">
	     		</td>
	     		<td> <label for="usr">Telefono:  </label>
	     		</td>
	     		<td><input type="text" class="form-control" id="telefono" name="telefono">
	     		</td>
	     	</tr>
	     	<!-- inicia tercera linea del formulario -->
	     	<tr>
	     		<td><label for="sel1">e-Mail: </label> </div>
	     		</td>
	     		<td><input type="email" class="form-control" id="email" name="email">
	     		</td>
	     		<td><label for="usr">Departamento:  </label>
	     		</td>
	     		<td><select id='departamento' name='departamento' class="form-control" >
	     		</select>   
	     		</td>
	     	</tr>
	     	<tr>
	     		<td><label for="usr">Municipio:  </label>
	     		</td>
	     		<td><select id='municipio' name='municipio' class="form-control" >
	     		</select>   
	     		</td>
	     	</tr>
	     </table >
	     <hr />
	     <input type="submit" value="GUARDAR" />
		</form>
			     
	     </article>
	     <aside> 
	     </aside>
	     <footer> POWERED BY: <br>
	     		JULIAN JAIMES<br>
	     		JHUALC<br>
	     </footer> 
	</body>	
</html>