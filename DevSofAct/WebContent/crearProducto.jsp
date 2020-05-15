
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	 	<link rel="stylesheet" type="text/css" href="style.css">
		<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
		<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
		<link rel="stylesheet" type="text/css" href="last.css">
		 
<!-- script carga Marcas -->		
<script>
		
$(document).ready(function(){
		$.ajax({
			type: 'GET',
			url: 'MarcaServlet',
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
				
				document.getElementById("marca").length=0;
				$('select[name=marca]').append (' <option>Seleccione...</option>')
				for(var i = 0; i< separado.length -1; i++){
				
					var idMarca = separado[i].split("-")[0];
					var nombreMarca = separado[i].split("-")[1];
					$('select[name=marca]').append('<option value = "'+idMarca +'"> '+ nombreMarca+ '</option>')
				}
				
			}
			
		});

});
		
</script>	

<!-- script carga tipos de producto -->		
<script>
		$(document).ready(function(){
		$.ajax({
			type: 'GET',
			url: 'TipoProductoParametroServlet',
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
				
				document.getElementById("tipoProducto").length=0;
				$('select[name=tipoProducto]').append (' <option>Seleccione...</option>')
				for(var i = 0; i< separado.length -1; i++){
				
					var idTipoProd = separado[i].split("-")[0];
					var nombreTipoProd = separado[i].split("-")[1];
					$('select[name=tipoProducto]').append('<option value = "'+idTipoProd +'"> '+ nombreTipoProd+ '</option>')
				}
				
			}
			
		});

});
		
</script>	


<!-- script carga medidas -->		
<script>
		$(document).ready(function(){
		$.ajax({
			type: 'GET',
			url: 'MedidaParametroServlet',
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
				
				document.getElementById("medida").length=0;
				$('select[name=medida]').append (' <option>Seleccione...</option>')
				for(var i = 0; i< separado.length -1; i++){
				
					var idTMedida = separado[i].split("-")[0];
					var nombreMedida = separado[i].split("-")[1];
					$('select[name=medida]').append('<option value = "'+idTMedida +'"> '+ nombreMedida + '</option>')
				}
				
			}
			
		});

});
		
</script>	


<!-- script agregar productos -->		
<script>
		$(document).ready(function(){
			$('a[id=agregar]').on('click', function(){
				var marca = $('#marca option:selected').text();
				var idMarca = document.getElementById('marca').value;
				var tipo = $('#tipoProducto option:selected').text();
				var idTipo = document.getElementById('tipoProducto').value;
				var descripcion = document.getElementById('descripcion').value;
				var referencia = document.getElementById('referencia').value;
				var medida = $('#medida option:selected').text();
				var idMedida = document.getElementById('medida').value;
				var valor = document.getElementById('valor').value;				
				var fila = $("#table_productos tr").length;
				var cantidad= document.getElementById('cantidad').value;	
				
				
				$('table[id=table_productos]').append('<tr><td width= "10%"> '+referencia+'</td><td width= "15%">'+marca+'</td><td width= "15%">'+tipo+'</td><td width= "30%">'+descripcion+'</td><td width= "10%">'+medida+'</td><td width= "15%">'+valor+'</td><td width= "15%">'+cantidad+'</td><td width= "5%"><span class="glyphicon glyphicon-remove" id="delete" ></span></td><td style="display: none;" width= "0%">'+idMarca+'</td><td style="display: none;" width= "0%">'+idTipo+'</td><td style="display: none;" width= "0%">'+idMedida+'</td></tr>')
				
			})
});
		
function borrar(valor){
        
        	
        	document.getElementById("table_productos").deleteRow(valor);
        	}


//************************************************************************************************************
// ELIMINA REGISTROS DE LA TABLA
//  IVA  SUBTOTAL Y TOTAL
// 05/10/2018 BY JHUALC
//************************************************************************************************************

$(document).on('click', '#delete', function (event) {
    event.preventDefault();
    $(this).closest('tr').remove();
 
});


</script>	

<!-- script guardar productos y valores-->		
<script>
$(document).ready(function(){
	$('a[id=generar]').on('click', function(){
	
	
	var detalles = [];
	var contador =0;
	
	$('#table_productos tr').each(function (index){
		var referencia, marca, tipo, descripcion, medida, valor, idMarca, idTipo, idMedida;
		
		$(this).children("td").each(function(index2){
		
		
			switch(index2){
				case 0:
					referencia= $(this).text();
					
					break;
				case 1:
					marca= $(this).text();
					break;
				case 2:
					tipo= $(this).text();
					break;
				case 3:
					descripcion= $(this).text();
					break;	
				case 4:
					medida= $(this).text();
					break;	
				case 5:
					valor= $(this).text();
					break;
				case 6:
					cantidad= $(this).text();
					break;
				case 8:
					idMarca= $(this).text();
					break;
				case 9:
					idTipo= $(this).text();
					break;
				case 10:
					idMedida= $(this).text();
					break;
			}
			
	
		})
		if (contador == 0){
			contador++;
		}else{
			detalles.push ({
				"referencia" 	: referencia,
				"descripcion"		: descripcion,
				"valor"		: valor,
				"idMarca" 	: idMarca,
				"idTipo"	: idTipo,
				"idMedida"	: idMedida,
				"cantidad"	: cantidad
			});
			}
			
		// jsonFactura.detalles.push({pvid: pvmid ,cantidad: cant, tot: total});
		})
		
		
		
		var jsonProducto = JSON.stringify(eval({
						detallePVM : detalles
						}))
						
						
		
		
		$.ajax({
			type: 'POST',
			url: 'SaveProductoValorMedidaServlet',
			dataType: 'json',
			data: jsonProducto , 
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
		
		alert("El producto fue creado correctamente");
		location.reload(true);
	})
});

</script>	

<script>
	

	$(document).ready(function(){
		$('a[id=colapsador]').on('click', function(){
		
			document.getElementById("articulo").style.height = "750px";
			
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
            <li data-toggle="collapse" data-target="#service" class="collapsed" ><a id="colapsador">Administración<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-cog"></span></a></li>
            <ul class="sub-menu collapse nav navbar-nav" id="service">
               <li ><a href="NuevoCliente.jsp">Crear Cliente<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-globe"></span></a></li>
               <li ><a href="CrearUsuario.jsp">Crear Usuario<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span></a></li>
               <li class="active"><a href="crearProducto.jsp">Crear Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-shopping-cart"></span></a></li>
               <li><a href="UpdateProducto.jsp">Actualizar Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
               <li ><a href="CrearUsuario.jsp">Crear Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-shopping-cart"></span></a></li>
               <li ><a href="CrearTipoProducto.jsp">Crear Tipo Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-barcode"></span></a></li>
               <li ><a href="CrearMarca.jsp">Crear Marca<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-eye-open"></span></a></li>
               <li ><a href="CrearMedida.jsp">Crear Medida<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-wrench"></span></a></li>
            </ul>
         </div>
      </div>
   </nav>
   <article id="articulo"  style= "height: AUTO">
      <br>
      <h3>ADMINISTRAR PRODUCTOS</h3>
      <HR />
      <form class="form-horizontal" method="POST" action="./SaveClientServlet">
         <br>
         <table id= "tabla_form" >
            <tr>
               <td><label for="sel1">Marca: </label>
               </td>
               <td>
                  <select class="form-control" id="marca" name="marca">	
                  </select>			
               </td>
               <td> <label for="usr">Tipo Producto:  </label>
               </td>
               <td><select class="form-control" id="tipoProducto" name="tipoProducto">
                  </select>
               </td>
            </tr>
            <tr>
               <td><label for="sel1">Descripcion: </label>
               </td>
               <td>
                  <input type="text" class="form-control" id="descripcion" name="descripcion">				
               </td>
               <td> <label for="usr">Referencia:  </label>
               </td>
               <td><input type="text" class="form-control" id="referencia" name="referencia">
               </td>
            </tr>
         </table >
         <hr />
         <table id= "tabla_form" >
            <tr>
               <td><label for="sel1">Medida: </label>
               </td>
               <td>
                  <select class="form-control" id="medida" name="medida">	
                  </select>			
               </td>
               <td> <label for="usr">Valor:  </label>
               </td>
               <td><input type="text" class="form-control" id="valor" name="valor">
               </td>
               <tr>
               	<td>
               		<label for="cantidad">Cantidad:  </label>
               	</td>
               	 <td><input type="text" class="form-control" id="cantidad" name="cantidad" value="0">
               </td>
               </tr>
            </tr>
         </table >
          <div id="agregar_registro">
		     <a id="agregar" name="agregar"   class="btn btn-info btn-sm" >
	          <div class="glyphicon glyphicon-plus"  ></div> Agregar 
	         </a>
	      </div>   
         <br>
         <br>
         <div class="table-responsive" id="tableProducto" name="tableProducto">
            <div class="col-xs-12">
               <table class="table table-bordered" id="table_productos"  >
                  <thead class="thead-dark">
                     <tr>
                        <th width= "10%">
                           REFERENCIA
                        </th>
                         <th width= "15%">
                           MARCA
                        </th>
                         <th width= "15%">
                           TIPO
                        </th>
                        <th width= "30%">
                           PRODUCTO
                        </th>
                        <th width= "10%">
                           MEDIDA
                        </th>
                        <th width= "15%">
                           VALOR
                        </th>
                        <th width= "10%">
                           CANTIDAD
                        </th>
                        <th width= "5%">
                           OPC
                        </th>
                       
                     </tr>
                  </thead>
               </table>
            </div>
         </div>
         <br>
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