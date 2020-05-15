
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>



<link rel="stylesheet" type="text/css" href="style.css">
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link 	href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css"	rel="stylesheet" id="bootstrap-css">
<link 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"	rel="stylesheet">


<link 	href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css"	rel="stylesheet" >
<link 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"	rel="stylesheet" >



<link rel="stylesheet"	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/css/bootstrap.min.css" />
<link rel="stylesheet"	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker.min.css" />
<script	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="last.css">
<link rel="stylesheet" 	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- link rel="stylesheet" href="/resources/demos/style.css" -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script	src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>



<!-- load jQuery 1.9.1 -->
<script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript">
var $jQuery_1_9_1 = $.noConflict(true);
</script>



<script>


//************************************************************************************************************
// CIERRE DE SESION
// 02/02/2019 BY JHUALC
//************************************************************************************************************

var timeoutId = window.setInterval("confirmarSesion()",<%= ((request.getSession().getMaxInactiveInterval())-60)*300 %>);
function confirmarSesion()
{
        var maxTimeWait = 60 * 300;
        var d = new Date();
        
        <% 
                HttpSession nsession = request.getSession(false);                       
        
                if(nsession == null) 
                {
                %>
                        alert("Su sesion ha finalizado por inactividad.");
                        parent.window.location.href="<%= request.getContextPath()%>/login.html"; 
                <%
                }
                else
                {
                %> 
                        if (confirm("Su sesion ha finalizado por inactividad.")) 
                        {
                                var timeDiff = (new Date() - d);
                                if(timeDiff > maxTimeWait)
                                {
                               // alert("Su sesión ha expirado.");
                                        parent.window.location.href="<%= request.getContextPath()%>/login.html";         
                            	}   
                                else
                                {                                               
                                        keepSessionAlive();
                                        window.clearInterval(timeoutId);        
                                        window.setInterval("confirmarSesion()",<%= ((request.getSession().getMaxInactiveInterval())-60)*300 %>);                                                       
                                }               
                        }
                        else
                        {               
                                parent.window.location.href="<%= request.getContextPath()%>/login.html";
                        }
                <%
            }
            %> 
}

</script>


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

<script>
//************************************************************************************************************
// carga informacion de tipo de producto filtrado por marca javascript
//************************************************************************************************************


$(document).ready(function(){
	$('select[name=marca]').on('change', function(){
		$.ajax({
			type: 'GET',
			url: 'TipoProductoServlet',
			data: 'marca='+$('select[name=marca]').val(),
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
				
					var idTipoProducto = separado[i].split("-")[0];
					var nombreTipoProducto = separado[i].split("-")[1];
					$('select[name=tipoProducto]').append('<option value = "'+idTipoProducto +'"> '+ nombreTipoProducto+ '</option>')
				}
				
			}
			
		});
		
	})
});

	
</script>	




<!-- script agregar productos -->		
<script>
		$(document).ready(function(){
			$('a[id=buscar]').on('click', function(){
				var marca = $('#marca option:selected').text();
				var idMarca = document.getElementById('marca').value;
				var tipo = $('#tipoProducto option:selected').text();
				var idTipo = document.getElementById('tipoProducto').value;
				//var descripcion = document.getElementById('descripcion').value;
				var referencia = document.getElementById('referencia').value;
						
				
				
				 var params = {
							       idMarca : idMarca,  idTipo  : idTipo ,  referencia : referencia
							    };
				
				$.ajax({
			type: 'POST',
			url: 'GetProductoValorServlet',
			dataType: 'json',
			data: params,
			statusCode: {
				404: function(){
					alert("pagina no encontrada AlorProductoServlet");
				},
				500: function(){
					alert("Error servidor ValorProductoServlet");
				}
			},
			success: function(response){
				
				
					llenar(response);
				/*
					 document.getElementById("table_productos").innerHTML="";
					 
					 document.getElementById("table_productos").innerHTML="<table class='table table-bordered' id='table_productos'><thead class='thead-dark'><tr><th width= '10%'>REFERENCIA</th><th width= '15%'>MARCA</th><th width= '15%'>TIPO</th><th width= '30%'>PRODUCTO</th><th width= '10%'>MEDIDA</th><th width= '15%'>VALOR</th> <th width= '15%'> EXISTENCIA </th><th width= '15%'>CANTIDAD</th><th width= '5%'>OPC</th></tr></thead></table>";
					
					for (var i = 0; i < response.length; i++) {
							    var counter = response[i];
							   // alert(counter.marDescripcion);
							    var fila = $("#table_productos tr").length;
							    $('table[id=table_productos]').append('<tr><td width= "10%" contenteditable="true"> '+counter.proReferencia+'</td><td width= "15%">'+counter.marDescripcion+'</td><td width= "15%">'+counter.tipDescripcion+'</td><td width= "30%" contenteditable="true">'+counter.proDescripcion+'</td><td width= "10%">'+counter.unmDescripcion+'</td><td width= "15%" contenteditable="true">'+counter.pvmValor+'</td><td width= "15%" contenteditable="false">'+counter.pvmCantidad+'</td><td width= "15%" contenteditable="true">0</td><td width= "5%"><span class="glyphicon glyphicon-remove" id="delete" onclick="eliminar('+counter.pvmId+','+ counter.proId+')" ></span></td><td style="display: none;" >'+counter.pvmId+'</td><td style="display: none;" >'+counter.proId+'</td></tr>')
				
							}
							*/
				
			}
		});
				
				
				
			})
});
		
function borrar(valor, pvm_id){
        
        alert (pvm_id)
        
        $.ajax({
			type: 'POST',
			url: 'DeleteProductoValorServlet',
			dataType: 'json',
			data: 'pvm_id='+ pvm_id , 
			statusCode: {
				404: function(){
					alert("pagina no encontrada AlorProductoServlet");
				},
				500: function(){
					alert("Error servidor ValorProductoServlet");
				}
			},
			success: function(response){
				
				
				
				
			}
		});
        	
        	document.getElementById("table_productos").deleteRow(valor);
        	
        	
        	
        	}
</script>	


<script>

        	
        	
//************************************************************************************************************
// ELIMINA REGISTROS DE LA TABLA
//  IVA  SUBTOTAL Y TOTAL
// 05/10/2018 BY JHUALC
//************************************************************************************************************

$(document).on('click', '#delete', function (event) {
    event.preventDefault();
    $(this).closest('tr').remove();
 
});


function eliminar(pvmId, proId) {

  $.ajax({
			type: 'POST',
			url: 'DeleteUpdateProductoServlet',
			data: {pvmId : pvmId, proId : proId},
			statusCode: {
				404: function(){
					alert("pagina no encontrada AlorProductoServlet");
				},
				500: function(){
					alert("Error servidor ValorProductoServlet");
				}
			},
			success: function(response){
				
								
			}
		});
}

</script>

<!-- script guardar productos y valores-->		
<script>
$(document).ready(function(){
	$('a[id=generar]').on('click', function(){
	
	
	var detalles = [];
	var contador =0;
	
	$('#table_productos tr').each(function (index){
		//var referencia, marca, tipo, descripcion, medida, valor, idMarca, idTipo, idMedida;
		
		$(this).children("td").each(function(index2){
		
		
			switch(index2){
			
				case 0:
					referencia= $(this).text();					
					break;
					
				case 3:
					producto= $(this).text();
					break;
				case 5:
					valor= $(this).text();
				
				break;
				
				case 6:
					existencia= $(this).text();
					break;
					
				case 7:
					cantidad= $(this).text();
					break;
						
					break;
				case 9:
					pvmId= $(this).text();
					break;
					
				case 10:
					proId= $(this).text();
					break;
				
				
			}
			
	
		})
		if (contador == 0){
			contador++;
		}else{
		
		 resultado = parseInt(cantidad) + parseInt(existencia);
		 
			detalles.push ({
				"referencia": referencia,
				"producto"	: producto,
				"valor" 	: valor,
				"pvmId"		: pvmId,
				"proId"		: proId,
				"cantidad"	: resultado
			});
			}
			
		// jsonFactura.detalles.push({pvid: pvmid ,cantidad: cant, tot: total});
		})
		
		
		
		var jsonProducto = JSON.stringify(eval({
						detallePVMU : detalles
						}))
						
						
		
		
		$.ajax({
			type: 'POST',
			url: 'UpdateProductoValorServlet',
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
				
				
				
				
			}
		});
		
		alert("El valor fue actualizado correctamente");
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
            <li ><a href="Reportes.jsp">Reportes<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
            <li><a href="CopiaFactura.jsp">Generar Copia<span style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
				<li data-toggle="collapse" data-target="#service" class="collapsed" ><a id="colapsador">Administración<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-cog"></span></a></li>
            <ul class="sub-menu collapse nav navbar-nav" id="service">
               <li ><a href="NuevoCliente.jsp">Crear Cliente<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-globe"></span></a></li>
               <li ><a href="CrearUsuario.jsp">Crear Usuario<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span></a></li>
               <li ><a href="crearProducto.jsp">Crear Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-shopping-cart"></span></a></li>
               <li class="active"><a href="UpdateProducto.jsp">Actualizar Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
               <li ><a href="CrearTipoProducto.jsp">Crear Tipo Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-barcode"></span></a></li>
               <li ><a href="CrearMarca.jsp">Crear Marca<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-eye-open"></span></a></li>
               <li ><a href="CrearMedida.jsp">Crear Medida<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-wrench"></span></a></li>
            </ul>
         </div>
      </div>
   </nav>
   <article id="articulo" style= "height: auto">
      <br>
      <h3>ACTUALIZAR VALORES</h3>
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
               <td>
               </td>
               <td>
                  
               </td>
               <td> <label for="usr">Referencia:  </label>
               </td>
               <td><input type="text" class="form-control" id="referencia" name="referencia">
               </td>
            </tr>
         </table >
         
        
          <div id="Buscar_regsitro">
		     <a id="buscar" name="buscar"   class="btn btn-info btn-sm" >
	          <div class="glyphicon glyphicon-search"  ></div> Buscar 
	         </a>
	      </div>   
	      <hr />
         <br>
         <br>
         <div class="table-responsive" id="tableProducto" name="tableProducto">
            <div class="col-xs-12">
               <table id="example" class="table table-striped table-bordered" style="width:100%"  >
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
                         <th width= "15%">
                           EXISTENCIA
                        </th>
                         <th width= "15%">
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
          <div class="glyphicon glyphicon-ok"  ></div> Actualizar
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


  
  <script>
  
  //******************************************************
  //FUNCION BUSCAR
  // 14/02/19
  //******************************************************
  
$(document).ready(function(){
	$('a[id=botonBuscar]').on('click', function(){
	
	var fechaIni = "";
	var fechaFin = "";

	
	//alert ("la fecha de inicio"+ fechaIni);

 		var params = {fechaIni: fechaIni , fechaFin: fechaFin};
				
	
		$.ajax({
			type: 'POST',
			url: 'BuscadorFacturaServlet',
			dataType: 'json',
			data: params,
			statusCode: {
				404: function(){
					alert("pagina no encontrada ValorProductoServlet contacte con soporte si el problema persiste");
				},
				500: function(){
					alert("Debe seleccionar todos los items Obligatorios");
				}
			},
			success: function(response){
			
			
			
			llenar(response);
			
			//alert ("Response"+ response.length + response)
			/*
			
			 document.getElementById("example").innerHTML="";
					 
			 document.getElementById("example").innerHTML="<table id='example' class='table table-striped table-bordered' style='width:100%'><thead><tr><th>Factura No</th><th>Fecha Factura</th><th>Cliente</th><th>Identificacion</th><th>Direccion</th><th>Telefono</th><th>SubTotal</th><th>IVA</th><th>Total</th></tr></thead></table>";
					
			
			
			 $('table[id=example]').append('<tbody>');
           
            
       
			
			for (var i = 0; i < response.length; i++) {
							    var counter = response[i];
							 //   alert(counter.facNumOriginal);
							    var fila = $("#example tr").length;
							     $('table[id=example]').append('<tr><td>'+counter.facNumOriginal+'</td><td>'+counter.facFecaPago+'</td><td>'+counter.nombre+'</td><td>'+counter.cliNumIdent+'</td><td>'+counter.cliDireccion+'</td><td>'+counter.cliTelefono+'</td><td>'+counter.facSubTotal+'</td><td>'+counter.facIva+'</td><td>'+counter.facTotal+'</td></tr>');
							     
			}
			
			 $('table[id=example]').append('</tbody><tfooter></tfooter>');
			 
			 */
			
			}
		});
	})
});


$jQuery_1_9_1(document).ready(function() {
    $jQuery_1_9_1('#example').DataTable();
} );


function llenar(response)
{



    $jQuery_1_9_1('#example').DataTable({
        "destroy": true,
        "data": response,
        "columns":[
            {"data":"proReferencia"},
            {"data":"marDescripcion"},
            {"data":"tipDescripcion"},
            {"data":"proDescripcion"},
            {"data":"unmDescripcion"},
            {"data":"pvmValor"},
            {"data":"pvmValor"},
            {"data":"pvmCantidad"},
            {"defaultContent":"<span class='glyphicon glyphicon-remove' id='delete' onclick='eliminar(1,"+ 1 + ")' ></span>"}
           
        ]
    });
}

/*
+'</td><td width= "15%" contenteditable="false">'+counter.pvmCantidad+'</td><td width= "15%" contenteditable="true">0</td><td width= "5%"><span class="glyphicon glyphicon-remove" id="delete" onclick="eliminar('+counter.pvmId+','+ counter.proId+')" ></span></td><td style="display: none;" >'+counter.pvmId+'</td><td style="display: none;" >'+counter.proId+'</td></tr>')
				
*/


  </script>

</html>