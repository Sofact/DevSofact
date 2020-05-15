
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	 	<link rel="stylesheet" type="text/css" href="style.css">
		<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
		<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
		<link rel="stylesheet" type="text/css" href="last.css">
		 

<!-- script guardar productos y valores-->		
<script>
$(document).ready(function(){
	$('a[id=generar]').on('click', function(){
		
		var nombre = $('#nombre').val();
		var pass = $('#pass').val();
		
		$.ajax({
			type: 'POST',
			url: 'SaveUsuarioServlet',
			data:  {nombre: nombre , pass: pass} ,
			statusCode: {
				404: function(){
					alert("pagina no encontrada AlorProductoServlet");
				},
				500: function(){
					alert("Error servidor ValorProductoServlet");
				}
			},
			success: function(response){
				var separado = response.split(":");
				
				
			}
		});
		alert("El usuario fue creado correctamente");
		location.reload(true);
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
            <li data-toggle="collapse" data-target="#service" class="collapsed" ><a id="colapsador"  >Administración<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-cog"></span></a></li>
            <ul class="sub-menu collapse nav navbar-nav" id="service">
               <li ><a href="NuevoCliente.jsp">Crear Cliente<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-globe"></span></a></li>
               <li class="active"><a href="CrearUsuario.jsp">Crear Usuario<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span></a></li>
               <li ><a href="crearProducto.jsp">Crear Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-shopping-cart"></span></a></li>
               <li><a href="UpdateProducto.jsp">Actualizar Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
               <li ><a href="CrearTipoProducto.jsp">Crear Tipo Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-barcode"></span></a></li>
               <li ><a href="CrearMarca.jsp">Crear Marca<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-eye-open"></span></a></li>
               <li ><a href="CrearMedida.jsp">Crear Medida<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-wrench"></span></a></li>
            </ul>
         </div>
      </div>
   </nav>
   <article id="articulo" style= "height: 700px">
      <br>
      <h3>ADMINISTRAR USUARIOS</h3>
      <HR />
      <form class="form-horizontal" >
         <br>
         <table id= "tabla_form" >
            <tr>
               <td><label for="sel1">Nombre Usuario: </label>
               </td>
               <td>
                  <input type="text" class="form-control" id="nombre" name="nombre">	
                 
               </td>
               
               
            </tr>
             <tr>
               <td><label for="sel1">Contraseña: </label>
               </td>
               <td>
                  <input type="password" class="form-control" id="pass" name="pass">	
                 
               </td>
               
               
            </tr>
            
         </table >
         <hr />
        
          

        <div >
	     <a id="generar" name="generar"   class="btn btn-info btn-sm" >
          <div class="glyphicon glyphicon-list-alt"  ></div> Guardar
        </a>
		</div>	 
      </form>
      <br><br>
   </article>
   <aside> 
   </aside>
   <footer> POWERED BY: <br>
      JULIAN JAIMES<br>
      JHUALC<br>
   </footer>
</body>	
</html>