<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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

</head>

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
<body>

	


	<nav class=" navbar navbar-default sidebar" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-sidebar-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-sidebar-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="home.jsp">Home<span style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-home"></span></a></li>
				<li ><a href=""./FacturaServlet"">Facturas<span
						style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a></li>
				<li ><a href="./FacturaPosServlet">Facturas POS<span	style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a></li>
				<li><a href="NotaCredito.jsp">Notas Credito<span
						style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-th-list"></span></a></li>
				<li><a href="./NotaDebitoServlet">Notas Debito<span style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-tags"></span></a></li>
				<li><a href="Reportes.jsp">Reportes<span style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
				<li class="active"><a href="CopiaFactura.jsp">Generar Copia<span style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
				<li data-toggle="collapse" data-target="#service" class="collapsed"><a>Administración<span 	style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-cog"></span></a></li>
				<ul class="sub-menu collapse nav navbar-nav" id="service"> <li><a href="NuevoCliente.jsp">Crear Cliente<span style="font-size: 16px;" 	class="pull-right hidden-xs showopacity glyphicon glyphicon-globe"></span></a></li>
					<li><a href="CrearUsuario.jsp">Crear Usuario<span style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span></a></li>
					<li><a href="crearProducto.jsp">Crear Producto<span style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-shopping-cart"></span></a></li>
					<li><a href="UpdateProducto.jsp">Actualizar Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
					
					<li><a href="CrearTipoProducto.jsp">Crear Tipo Producto<span style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-barcode"></span></a></li>
					<li><a href="CrearMarca.jsp">Crear Marca<span style="font-size: 16px;"	class="pull-right hidden-xs showopacity glyphicon glyphicon-eye-open"></span></a></li>
					<li><a href="CrearMedida.jsp">Crear Medida<span style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-wrench"></span></a></li>

				</ul>
			</ul>
		</div>
	</div>
	</nav>

	<article style="height:1170px"> <br>
	
	<table id="tabla_form">
		<tr>
		<td>
			<label>Factura numero</label>
		</td>
			<td><input type="text" class="form-control" id="numeroFactura" name="numeroFactura" /></td>
				
			
		</tr>
		
	</table>
	
	<div id="Buscar">
		<a id="botonBuscar" name="botonBuscar"  class="btn btn-info btn-sm"> <span
			class="glyphicon glyphicon-search"></span> Buscar
		</a>
	
	<table id="example" class="table table-striped table-bordered" style="width:100%">
        <thead>
            <tr>
                <th>Factura No</th>
                <th>Fecha Factura</th>
                <th>Cliente</th>
                <th>Identificacion</th>
                <th>Direccion</th>
                <th>Telefono</th>
                <th>SubTotal</th>
                <th>IVA</th>
                <th>Total</th>
            </tr>
        </thead>
        
    </table>

	</article>
	<aside> </aside>
	<footer> POWERED BY: <br>
	JULIAN JAIMES<br>
	JHUALC<br>
	</footer>


  
  <script>
  
  //******************************************************
  //FUNCION BUSCAR
  // 14/02/19
  //******************************************************
  
$(document).ready(function(){
	$('a[id=botonBuscar]').on('click', function(){
	var facNumero =  document.getElementById("numeroFactura").value;
	
	alert ("La Factura recibida por parametro"+ facNumero);
 		var params = {facNumero: facNumero };
				
	
		$.ajax({
			type: 'POST',
			url: 'CopiaFacturaServlet',
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
            {"data":"facNumOriginal"},
            {"data":"facFecaPago"},
            {"data":"nombre"},
            {"data":"cliNumIdent"},
            {"data":"cliDireccion"},
            {"data":"cliTelefono"},
            {"data":"facSubTotal"},
            {"data":"facIva"},
            {"data":"facTotal"}
           
        ]
    });
}
  </script>
<script>
//**************************************
//el date picker
//**************************************
$( document ).ready(function() {
     $('#fecha').datepicker({ dateFormat: 'dd/mm/yy' });
        });
        
$( document ).ready(function() {
     $('#fechaFin').datepicker({ dateFormat: 'dd/mm/yy' });
        });
</script>

</html>