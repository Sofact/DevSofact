<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker.min.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="last.css">
<link rel="stylesheet" 	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- link rel="stylesheet" href="/resources/demos/style.css" -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Cliente"%>
<%@ page import="model.Marca"%>
<%@ page import="model.Departamento"%>
<%@ page import="model.Municipio"%>
<%@ page import="model.TipoProducto"%>
<%@ page import="java.util.List"%>

<!-- scripts autocompletar y carga de selects -->

<script>

//************************************************************************************************************
// carga valores para session script
//************************************************************************************************************

$(document).ready(function(){
	
		$.ajax({
			type: 'GET',
			url: 'PropertiesServlet',
			data: 'i= ',
			statusCode: {
				404: function(){
					alert("Pagina no encontrada");
				},
				500: function(){
					alert("Error en servidor");
				}
			},
			success: function(res){
				
				
				sessionStorage.setItem('iva', res);
			}
			
		});
		
});

$(document).on('click', '#delete', function (event) {
    event.preventDefault();
    $(this).closest('tr').remove();
    var total= 0;
    $('#table_productos tr').each(function (index){
						
						
						$(this).children('td').each(function (index2){
							
							if(index2 == 6){
								total = total + parseFloat($(this).text());
								
							}
						})
					})
					
					
					document.getElementById('total').value = total;
					document.getElementById('iva').value = total * sessionStorage.getItem('iva');
					document.getElementById('subtotal').value = total - (total * sessionStorage.getItem('iva'));
});



//************************************************************************************************************
// carga informacion de municipio filtrado por Departamento javascript
//************************************************************************************************************


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
					alert("Debe seleccionar un departamento");
				}
			},
			success: function(res){
				var separado = res.split(":");
				
				document.getElementById("municipio").length=0;
				$('select[name=municipio]').append (' <option>Seleccione...</option>')
				for(var i = 0; i< separado.length -1; i++){
				
					var idTipoProducto = separado[i].split("-")[0];
					var nombreTipoProducto = separado[i].split("-")[1];
					$('select[name=municipio]').append('<option value = "'+idTipoProducto +'"> '+ nombreTipoProducto+ '</option>')
				}
				
			}
			
		});
		
	})
});




//************************************************************************************************************
// autocompletar cliente inicia en este punto javascript
//************************************************************************************************************

  $( function() {
    $.widget( "custom.combobox", {
      _create: function() {
        this.wrapper = $( "<span>" )
          .addClass( "custom-combobox" )
          .insertAfter( this.element );
 
        this.element.hide();
        this._createAutocomplete();
      
      },
 
      _createAutocomplete: function() {
        var selected = this.element.children( ":selected" ),
          value = selected.val() ? selected.text() : "";
 
        this.input = $( "<input>" )
          .appendTo( this.wrapper )
          .val( value )
          .attr( "title", "" )
          .attr( "id", "combito" )
          .addClass( "ui-widget ui-widget-content ui-state-default ui-corner-left form-control" )
          .autocomplete({
            delay: 0,
            minLength: 0,
            source: $.proxy( this, "_source" )
          })
          .tooltip({
            classes: {
              "ui-tooltip": "ui-state-highlight"
            }
          });
 
        this._on( this.input, {
          autocompleteselect: function( event, ui ) {
            ui.item.option.selected = true;
            this._trigger( "select", event, {
              item: ui.item.option
             
            });
          },
 
          autocompletechange: "_removeIfInvalid"
        });
      },
 
     
 
      _source: function( request, response ) {
        var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
        response( this.element.children( "option" ).map(function() {
          var text = $( this ).text();
          if ( this.value && ( !request.term || matcher.test(text) ) )
            return {
              label: text,
              value: text,
              option: this
              
            };
            
        }) );
      },
 
      _removeIfInvalid: function( event, ui ) {
 
        // Selected an item, nothing to do
        if ( ui.item ) {
       		
  			cargaDatosCliente();
  		//	document.getElementById("nuevoCliente").innerHTML= "";
          return;
        }
 
        // Search for a match (case-insensitive)
        var value = this.input.val(),
          valueLowerCase = value.toLowerCase(),
          valid = false;
        this.element.children( "option" ).each(function() {
          if ( $( this ).text().toLowerCase() === valueLowerCase ) {
            this.selected = valid = true;
            return false;
          }
        });
 
        // Found a match, nothing to do
        if ( valid ) {
          return;
        }
 
       
        document.getElementById("combito").value = this.input.val();
       
      },
 
      _destroy: function() {
        this.wrapper.remove();
        this.element.show();
      }
    });
 
    $( "#combobox" ).combobox();
    $( "#toggle" ).on( "click", function() {
      $( "#combobox" ).toggle();
    });
  } );
  
  
  function cargaDatosCliente(){
  
  	var myselect = document.getElementById("combobox");
    var clienteSeleccionado = myselect.options[myselect.selectedIndex].value;
   
   var request = new XMLHttpRequest();
                request.open("POST", "./DatosClienteServlet", true);
                request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                request.onreadystatechange = function() {
                        // si la respuesta fue exitosa
                        if(request.readyState == 4 && request.status == 200) {
                        		 var response = request.responseXML.documentElement;
                        		            		 
                        		 
                        		 var identifica = response.getElementsByTagName("cliente_identifica");
                        		 var direcc = response.getElementsByTagName("cliente_direccion");
                        		 var telefono = response.getElementsByTagName("cliente_telefono");
                        		 var email = response.getElementsByTagName("cliente_email");
                        		 var tipo_ident = response.getElementsByTagName("tipo_ident");
                        		 var cli_id = response.getElementsByTagName("cli_id");
                        		 var segundoNom = response.getElementsByTagName("segundo_nombre");
                        		 var apellidos = response.getElementsByTagName("apellidos");
                        		 var select_identificacion = tipo_ident[0].getAttribute("id");
                        		 var select_departamento =  response.getElementsByTagName("departamento");
                        		 var select_municipio =  response.getElementsByTagName("municipio");
                        		
                                
                                // document.getElementById("identificacion").value = tipo_ident[0].getAttribute("id");
                                 document.getElementById("identificacion").value = identifica[0].getAttribute("id");
                                 document.getElementById("direccion").value = direcc[0].getAttribute("id");
                                 document.getElementById("telefono").value = telefono[0].getAttribute("id");
                                 document.getElementById("email").value = email[0].getAttribute("id");
                                 document.getElementById("cli_id").value = cli_id[0].getAttribute("id");
                                 if (segundoNom[0].getAttribute("id") != 'null' )
                                 	document.getElementById("SegundoNombre").value = segundoNom[0].getAttribute("id");
                                 if (apellidos[0].getAttribute("id") != 'null' )
                                 	document.getElementById("apellidos").value = apellidos[0].getAttribute("id");
                                 	document.getElementById("departamento").innerHTML = "<option>"+select_departamento[0].getAttribute("id")+"</option>";
                                 	document.getElementById("municipio").innerHTML = "<option>"+select_municipio[0].getAttribute("id")+"</option>";
                                
                                
                                
                               if(select_identificacion == "CC "){
                               		
                               		document.getElementById("ced").selected = true;
                               }
                               if(select_identificacion == "NIT"){
                               		
                               		document.getElementById("nit").selected = true;
                               }
                               if(select_identificacion == "CE"){
                               		
                               		document.getElementById("ce").selected = true;
                               }
                               if(select_identificacion == "TI"){
                               		
                               		document.getElementById("ti").selected = true;
                               }
                            
                                
                        } 
                        // si la respuesta trajo error
                        else if(request.readyState == 4 && request.status != 200){
                                var message = request.responseText;
                                message = request.responseText;
                              
                        }
                };
                request.send("userdata="+clienteSeleccionado);
        
  }
  </script>

<!-- script ventanas modales -->
<script>

// ventanas modales 

        $( document ).ready(function() {
            $('#fecha').datepicker();
        });
        
        function agregar(){
        
        
        	var nombre = document.getElementById("combito").value;
        	var identifica = document.getElementById("identificacion").value;
        	var tipo_ident = document.getElementById("tipoIdent").value;
        	var direccion = document.getElementById("direccion").value;
        	var telefono = document.getElementById("telefono").value;
        	var email = document.getElementById("email").value;
        	
        	if(nombre == "" || identifica == "" || tipo_ident == "" || direccion == "" || telefono == "" || email == ""){
        		alert("Debes tener llenos los campos antes de continuar " );
        		
        	}else{
        		
        	$("#modalNuevoCliente").modal('toggle');}
        	
        }
        function borrar(valor){
        
        	
        	document.getElementById("table_productos").deleteRow(valor);
        	var total= 0;
        	$('#table_productos tr').each(function (index){
						
						
						$(this).children('td').each(function (index2){
							
							if(index2 == 6){
								total = total + parseFloat($(this).text());
								
							}
						})
					})
					document.getElementById('total').value = total;
					document.getElementById('iva').value = total * sessionStorage.getItem('iva');
					document.getElementById('subtotal').value = total - (total * sessionStorage.getItem('iva'));
        	$('#agregar_registro').show(3000);        }
    </script>

<!-- script carga Nota debito generada -->
<script>
$(document).ready(function(){
	$('a[id=generar]').on('click', function(){
	var fecha = "";
	fecha = $('#fecha').datepicker({ dateFormat: 'dd,MM,yyyy' }).val();
	
	if(fecha == ""){
		 $('#modalResumenFactura').modal('hide');
		alert ("LA FECHA DE VENCIMIENTO ESTA VACIA");
		
		}
	else{
			
		if ($('#total').val() > 0){
	
	 $('#modalResumenFactura').modal('show');

	var cliente= $('#combito').val();
	var identificacion= $('#identificacion').val();
	var direccion= $('#direccion').val();
	var telefono= $('#telefono').val();
	
	var email = $('#email').val();
	var observacion = $('#observacion').val();
	var cont = 0;
	
	
	
	document.getElementById("clientefac").innerHTML= cliente;
	document.getElementById("identifac").innerHTML= identificacion;
	document.getElementById("dirfac").innerHTML= direccion;
	document.getElementById("telfac").innerHTML= telefono;
	document.getElementById("fechafac").innerHTML= fecha;
	document.getElementById("emailfac").innerHTML= email;
	document.getElementById("observacionf").innerHTML = observacion;
	
	document.getElementById("table_productosf").innerHTML="<thead class='thead-dark'><tr><th width= '7%'> CANT </th><th width= '12%'>	REFERENCIA </th> <th width= '10%'>	MARCA  </th> <th width= '29%'>	DESCRIPCION </th><th width= '10%'>	MEDIDA </th><th width= '14%'>VALOR UNITARIO </th><th width= '14%'>VALOR TOTAL </th></tr></thead>";
	//$('#table_productos')
	$('#table_productos tr').each(function (index){
		var cant, ref, marca, descrip, medida, valor, total;
		$(this).children("td").each(function(index2){
			switch(index2){
				case 0:
					cant= $(this).text();
					break;
				case 1:
					ref= $(this).text();
					break;
				case 2:
					marca= $(this).text();
					break;
				case 3:
					descrip= $(this).text();
					break;	
				case 4:
					medida= $(this).text();
					break;	
				case 5:
					valor= $(this).text();
					break;
				case 6:
					total= $(this).text();
					break;
				case 8:
					pvmId= $(this).text();
					break;
				
				
			}
		})
		if(cont > 0){
			$('table[id=table_productosf]').append('<tr ><td width= "10%" id=>'+cant+'</td><td width= "12%">'+ref+' </td><td width= "12%">'+marca+'</td><td width= "30%">'+descrip+'</td><td width= "10%">'+medida+'</td> <td width= "10%">'+valor+'</td><td width= "14%">'+total+'</td><td style="display: none;">'+pvmId+'</td></tr>')
			
		}cont++;
	})
	document.getElementById("subtotalf").value = document.getElementById("subtotal").value;
	document.getElementById("ivaf").value = document.getElementById("iva").value;
	document.getElementById("totalf").value = document.getElementById("total").value;
	}else {alert ("Debe dar un valor de nota debito");}
	}
	})
});
</script>

<script>
$(document).ready(function(){
	$('a[id=addValor]').on('click', function(){
	
	var tipo_p = "NOTA";
	var medi = "UNIDAD";
	var cantidad = "1";
	var marca = "N/A";

	
	var producto = "DEBITO";
	var referencia = "N/DEBITO";
	var pvmId = 2;
	var valorDescuento = document.getElementById("totalNota").value;
	var total = document.getElementById("totalNota").value;
			
	
			
	var fila = $("#table_productos tr").length;
	$('table[id=table_productos]').append('<tr ><td width= "10%" id=>'+cantidad+'</td><td width= "12%">'+referencia+' </td><td width= "12%">'+marca+'</td><td width= "30%">'+tipo_p + " "+producto+ ' </td><td width= "10%">'+medi+'</td> <td width= "10%">'+valorDescuento+'</td><td width= "14%">'+total+'</td><td width= "7%"><span class="glyphicon glyphicon-remove" id="delete" ></span></td><td style="display: none;">'+pvmId+'</td></tr>')
				
	var total= 0;
				$('#table_productos tr').each(function (index){
						
						
						$(this).children('td').each(function (index2){
							
							if(index2 == 6){
								total = total + parseFloat($(this).text());
								
							}
						})
					})
					document.getElementById('total').value = total;
					document.getElementById('iva').value = total * sessionStorage.getItem('iva');
					document.getElementById('subtotal').value = total - (total * sessionStorage.getItem('iva'));
					document.getElementById('totalNota').value = "";
		
	})
});

</script>


<!-- scripts Save Nota Debito -->
<script>
$(document).ready(function(){
	$('button[id=enviar]').on('click', function(){
	
	

	var detalles = [];
	

	var contador =0;
	
	$('#table_productosf tr').each(function (index){
		var cant, ref, marca, descrip, medida, valor, total, pvmid;
		
		$(this).children("td").each(function(index2){
		
		
			switch(index2){
				case 0:
					cant= $(this).text();
					
					break;
				case 1:
					ref= $(this).text();
					break;
				case 2:
					marca= $(this).text();
					break;
				case 3:
					descrip= $(this).text();
					break;	
				case 4:
					medida= $(this).text();
					break;	
				case 5:
					valor= $(this).text();
					break;
				case 6:
					total= $(this).text();
					break;
				case 7:
					pvmid= $(this).text();
					break;
					
					
				
			}
		
			
		})
		
		if (contador == 0){
			contador++;
		}else{
			
			
			detalles.push ({
				"cantidad" 	: cant,
				"total"		: total,
				"pvmid"		: pvmid,
			//	"descuento" : descuento,
				"subtotal"	: valor
			});
			
		
			}
			
		
		})
		
		
		var jsonFactura = JSON.stringify(eval({
						
						subtotal: $('#subtotal').val(),
						facturaNum: "",
						cliente: $('#cli_id').val(),
						total: $('#total').val(),
						iva: $('#iva').val(),
						fecha: $('#fecha').datepicker('getDate') ,
						observacion: $('#observacion').val(),
						detalleFactura : detalles
						}))
						
						
		
		
		$.ajax({
			type: 'POST',
			url: 'SaveNotaDebitoServlet',
			dataType: 'json',
			data: jsonFactura , 
			statusCode: {
				404: function(){
					alert("pagina no encontrada AlorProductoServlet");
				},
				500: function(){
					alert("Error servidor ValorProductoServlet");
				}
			},
			success: function(response){
				alert (response);
				
				 
			}
			
		});
		//}
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
				<li ><a href="./FacturaServlet">Facturas<span
						style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a></li>
				<li ><a href="FacturaPos.jsp">Facturas POS<span	style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a></li>
				<li><a href="NotaCredito.jsp">Notas Credito<span
						style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-th-list"></span></a></li>
				<li class="active"><a href="./NotaDebitoServlet">Notas Debito<span style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-tags"></span></a></li>
				<li><a href="#">Reportes<span style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
				<li data-toggle="collapse" data-target="#service" class="collapsed"><a>Administración<span
						style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-cog"></span></a></li>
				<ul class="sub-menu collapse nav navbar-nav" id="service">
					<li><a href="NuevoCliente.jsp">Crear Cliente<span
							style="font-size: 16px;"
							class="pull-right hidden-xs showopacity glyphicon glyphicon-globe"></span></a></li>
					<li><a href="CrearUsuario.jsp">Crear Usuario<span
							style="font-size: 16px;"
							class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span></a></li>
					<li><a href="crearProducto.jsp">Crear Producto<span
							style="font-size: 16px;"
							class="pull-right hidden-xs showopacity glyphicon glyphicon-shopping-cart"></span></a></li>
					<li><a href="UpdateProducto.jsp">Actualizar Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
					<li><a href="CrearTipoProducto.jsp">Crear Tipo Producto<span
							style="font-size: 16px;"
							class="pull-right hidden-xs showopacity glyphicon glyphicon-barcode"></span></a></li>
					<li><a href="CrearMarca.jsp">Crear Marca<span
							style="font-size: 16px;"
							class="pull-right hidden-xs showopacity glyphicon glyphicon-eye-open"></span></a></li>
					<li><a href="CrearMedida.jsp">Crear Medida<span
							style="font-size: 16px;"
							class="pull-right hidden-xs showopacity glyphicon glyphicon-wrench"></span></a></li>


				</ul>
			</ul>
		</div>
	</div>
	</nav>


	<article style="height:1170px"> <br>
	<br>
	<div>
	<table id="tabla_form">
		<tr>
			<td><label for="sel1">Nombre: </label></td>
			<td><select class="form-control" id="combobox">
					<option value="">Select one...</option>

					<c:forEach items="${clientes}" var="objeto">

						<option value=<c:out value="${objeto.cliId}"/>><c:out
								value="${objeto.cliNombre}" /></option>
					</c:forEach>


			</select>
			<td><label for="usr">Segundo Nombre: </label></td>
			
			<td><input type="text" class="form-control" id="SegundoNombre">
			</td>
		</tr>
		<tr>
			<td><label for="usr">Apellidos: </label></td>
			<td><input type="text" class="form-control" id="apellidos">
			</td>
			<td><label for="usr">Identificacion: </label></td>
			<td><table>
					<tr>
						<td><select class="form-control" id="tipoIdent">
								<option id="ced">CC</option>
								<option id="nit">NIT</option>
								<option id="ce">CE</option>
								<option id="ti">TI</option>
						</select></td>
						<td><input type="text" class="form-control" id="identificacion"> </td>
						<td style="display: none;" ><label id="cli_id" ></label><td>
					</tr>
				</table></td>
		</tr>
		<!-- inicia segunda linea del formulario -->
		<tr>
			<td><label for="sel1">Direccion: </label>
			</td>
			<td><input type="text" class="form-control" id="direccion">
			</td>
			<td><label for="usr">Telefono: </label></td>
			<td><input type="text" class="form-control" id="telefono">
			</td>
		</tr>
		<!-- inicia tercera linea del formulario -->
		<tr>
			<td><label for="usr">Departamento: </label></td>
			<td><select id='departamento' name='departamento' class="form-control">
			<option value="">Seleccione ...</option>
			<c:forEach items="${departamento}" var="objeto">

						<option value=<c:out value="${objeto.depId}"/>><c:out
								value="${objeto.depDescripcion}" /></option>
					</c:forEach>
			</select>
			<td><label for="usr">Municipio: </label></td>
			<td><select id='municipio' name='municipio' class="form-control">
			</select></td>
		</tr>
		<!-- inicia cuarta linea del formulario -->
		<tr>
			<td><label for="sel1">e-Mail: </label>
			</td>
			<td><input type="email" class="form-control" id="email">
			</td>
			<td><label for="usr">Fecha de Vencimiento: </label></td>
			<td><input type="text" class="form-control" id="fecha"
				name="fecha" /></td>
		</tr>
	</table>
	</div>

	<!-- inicia tercera linea del formulario -->
	<hr />
	
	
	<div class="table-responsive" id="div_tabla">
		<div class="col-xs-14">

								<table class="table table-bordered" id="table_productos">
								
										<tr>
											<th width="7%">CANT</th>
											<th width="12%">REFERENCIA</th>
											<th width="10%">MARCA</th>
											<th width="29%">DESCRIPCION</th>
											<th width="10%">MEDIDA</th>
											<th width="14%">VALOR UNITARIO</th>
											<th width="14%">VALOR TOTAL</th>
											<th width="0%"> </th>
										</tr>
									

								</table>
		</div>
	</div>
	
	<div class="col-xs-12">
		<table align="center">
			<tr>
				<td>
					<h3>Descripcion:</h3>
				</td>
			</tr>
			<tr>
				<td>
					<textarea rows="8" cols="100" id="observacion"></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td>
								<h4>Valor Adicional Nota Debito:</h4>
							</td>
							<td>
								<input type="text" class="form-control" id="totalNota">
							</td>
							<td>
								<a id="addValor" name="addValor" class="btn btn-info btn-sm">
									<div class="glyphicon glyphicon-ok"></div> Agregar Valor
								</a>
							</td>
						</tr>	
					</table>
				</td>
				
			</tr>
		</table>
	</div>

	
	
	
	<div class="col-xs-12">
		<table>
			<tr>
				<td width="70%"></td>
				<td width="15%"><h5>SUBTOTAL $</h5></td>
				<td width="15%"><input type="text" class="form-control"
					align="right" id="subtotal" readonly></td>
			</tr>
			<tr>
				<td width="70%"></td>
				<td width="15%"><h5>IVA $</h5></td>
				<td width="15%"><input type="text" class="form-control"
					align="right" id="iva" style="align: right" readonly></td>
			</tr>
			<tr>
				<td width="70%"></td>
				<td width="15%"><h5>TOTAL $</h5></td>
				<td width="15%"><input type="text" class="form-control"
					align="right" id="total" style="align: right" readonly></td>
			</tr>
		</table>
	</div>
	<div>
		<a id="generar" name="generar" class="btn btn-info btn-sm"
			>
			<div class="glyphicon glyphicon-list-alt"></div> Generar Factura
		</a>
	</div>


	</article>
	<aside> </aside>
	<footer> POWERED BY: <br>
	JULIAN JAIMES<br>
	JHUALC<br>
	</footer>



	<!-- Modal Resume factura antes de imprimir-->


	<div class="modal fade" id="modalResumenFactura" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="false">
		<div class="modal-dialog" role="document">
			<div class="modal-content">

				<div>
					<img src="./img/encabezado.png" width="80%"></img>
				</div>
				<form>
					<div class="modal-body">
						<table id="tabla_form">
							<tr>
								<td><label for="sel1">Cliente: </label></td>
								<td><label id="clientefac" name="clientefac"></label></td>
								<td><label for="usr">Identificacion: </label></td>
								<td><label id="identifac"> </label></td>

							</tr>
							<!-- inicia segunda linea del formulario -->
							<tr>
								<td><label id="sel1">Direccion: </label></td>
								<td><label id="dirfac"></label></td>
								<td><label id="usr">Telefono: </label></td>
								<td><label id="telfac"></label></td>
							</tr>
							<tr>
								<td><label id="sel1">Email: </label></td>
								<td><label id="emailfac"></label></td>
								<td><label id="usr">Fecha Vencimiento: </label></td>
								<td><label id="fechafac"></label></td>
							</tr>
						</table>
						<div class="table-responsive" id="div_tablaf">
							<div class="col-xs-12">
								<table class="table table-bordered" id="table_productosf">
									<thead class="thead-dark">
										<tr>
											<th width="7%">CANT</th>
											<th width="12%">REFERENCIA</th>
											<th width="10%">MARCA</th>
											<th width="29%">DESCRIPCION</th>
											<th width="10%">MEDIDA</th>
											<th width="14%">VALOR UNITARIO</th>
											<th width="14%">VALOR TOTAL</th>
											<th width="0%"> </th>
										</tr>
									</thead>

								</table>
							</div>
						</div>
						
						<div class="col-xs-12">
							<table align="center">
								<tr>
									<td>
										<h3>Descripcion:</h3>
									</td>
								</tr>
								<tr>
									<td>
										<label  id="observacionf"></label>
									</td>
								</tr>
								
							</table>
						</div>
						
						<div class="col-xs-12">
							<table>
								<tr>
									<td width="70%"></td>
									<td width="15%"><h6>SUBTOTAL $</h6></td>
									<td width="15%"><input type="text" class="form-control"
										align="right" id="subtotalf" value="200.000" readonly>
									</td>
								</tr>
								<tr>
									<td width="70%"></td>
									<td width="15%"><h6>IVA $</h6></td>
									<td width="15%"><input type="text" class="form-control"
										align="right" id="ivaf" style="align: right" value="20.000"
										readonly></td>
								</tr>
								<tr>
									<td width="70%"></td>
									<td width="15%"><h6>TOTAL $</h6></td>
									<td width="15%"><input type="text" class="form-control"
										align="right" id="totalf" style="align: right" value="220.000"
										readonly></td>
								</tr>
							</table>
						</div>


						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Regresar</button>
							<button class="btn btn-primary" id="enviar" name="enviar">Enviar</button>
						</div>

					</div>
				</form>
			</div>

		</div>
</body>

<script>
 var colArray= new Array();
 var marcaArray = new Array();
 

<% 

ArrayList<Cliente> clientes = (ArrayList<Cliente>) request.getAttribute("clientes");

for(Cliente clien : clientes){
%>
colArray.push("<%= clien.getCliNombre()%>"); 
<% } %>





$( "#direccion" ).autocomplete({
  source:  colArray
});
</script>
<script>
$( "#referencia" ).autocomplete({
  source: []
});


</script>




</html>