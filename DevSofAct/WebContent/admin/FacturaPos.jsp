<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link 	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"	rel="stylesheet" id="bootstrap-css">

<link rel="stylesheet"	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/css/bootstrap.min.css" />
<link rel="stylesheet"	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker.min.css" />
<script	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="last.css">
<link rel="stylesheet" 	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- link rel="stylesheet" href="/resources/demos/style.css" -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

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
//************************************************************************************************************
// carga valores para session script
//  IVA
// 05/10/2018 BY JHUALC
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
//************************************************************************************************************
// RECALCULA VALORES
//  IVA  SUBTOTAL Y TOTAL
// 05/10/2018 BY JHUALC
//************************************************************************************************************
$(document).on('click', '#delete', function (event) {
    event.preventDefault();
    $(this).closest('tr').remove();
    var total1= 0;
    $('#table_productos tr').each(function (index){
						
						
						$(this).children('td').each(function (index2){
							
							if(index2 == 7){
								total1 = total1 + parseFloat($(this).text().replace(".","").replace(".",""));
								
							}
						})
					})
					
					
					var total = formatear(total1);
					var subtotal =  parseFloat(total/ (parseFloat(sessionStorage.getItem('iva'))+1.0)).toFixed(3);
					var iva = parseFloat(total - total/ (parseFloat(sessionStorage.getItem('iva'))+1.0)).toFixed(3) ;
					
					document.getElementById('total').value =  total;
					document.getElementById('iva').value =   iva;
					document.getElementById('subtotal').value = subtotal;
});
//************************************************************************************************************
// CARGA TIPO PRODUCTO 
// FILTROS: MARCA 
// 05/10/2018 BY JHUALC
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
				
				document.getElementById("tipoProd").length=0;
				$('select[name=tipoProd]').append (' <option>Seleccione...</option>')
				for(var i = 0; i< separado.length -1; i++){
					var idTipoProducto = separado[i].split("-")[0];
					var nombreTipoProducto = separado[i].split("-")[1];
					$('select[name=tipoProd]').append('<option value = "'+idTipoProducto +'"> '+ nombreTipoProducto+ '</option>')
				}	
			}			
		});		
	})
});
//************************************************************************************************************
// CARGA PRODUCTO 
// FILTROS: MARCA, TIPO_PRODUCTO
// 05/10/2018 BY JHUALC
//************************************************************************************************************
$(document).ready(function(){
						    
	$('select[name=tipoProd]').on('change', function(){	
	var tip_id = $('select[name=tipoProd]').val();
		var params = {
			tipo_prod : $('select[name=tipoProd]').val(),  marca  : $('select[name=marca]').val()
		};
							    
		$.ajax({
			type: 'GET',
			url: 'ProductoServlet',
			data: params,
			statusCode: {
				404: function(){
					alert("Pagina no encontrada");
				},
				500: function(){
					alert("Error en servidor")
				}	
			},
			success: function(resp){
				console.log(resp);
				var separado = resp.split(":"); 
				document.getElementById("producto").length=0;
				$('select[name=producto]').append('<option>Seleccione...</option>')
				for(var i =0; i<separado.length -1; i++){
					var idProducto = separado[i].split("-")[0];
					var nombreProducto = separado[i].split("-")[1];
					$('select[name=producto]').append('<option value= "'+idProducto+'">'+nombreProducto+'</option>')
				}
			}
		});
	})
});
//************************************************************************************************************
// CARGA MEDIDA 
// FILTROS: PRODUCTO
// 05/10/2018 BY JHUALC
//************************************************************************************************************
$(document).ready(function(){
	$('select[name=producto]').on('change', function(){
	
		var params = {
						medida : $('select[name=producto]').val(),  tipo  : 'M'
						};
	
		$.ajax({
			type: 'GET',
			url: 'MedidaServlet',
			data: params,
			statusCode: {
				404: function(){
					alert("Pagina no encontrada"); 
				},
				500: function(){
					alert("Error en el servidor" + 'medida='+$('select[name=producto]').val());
				}
			},
			success: function(res){
				var separado = res.split(":");
				
				document.getElementById("medida").length = 0;
				$('select[name=medida]').append('<option>Seleccione...</option>')
				for(var i=0; i< separado.length -1; i++){
					var idMedida= separado[i].split("-")[0];
					var nombreMedida = separado[i].split("-")[1];
					$('select[name=medida]').append('<option value= "'+idMedida+'">'+nombreMedida+'</option>')
				}
			}
		});
	})	
});
//************************************************************************************************************
// CARGA EXISTENCIAS 
// FILTROS: PRODUCTO
// 05/10/2018 BY JHUALC
//************************************************************************************************************
$(document).ready(function(){
	$('select[name=medida]').on('change', function(){
	
	var params = {
			medida : $('select[name=medida]').val(),  producto  : $('select[name=producto]').val()
		};
	
		$.ajax({
			type: 'POST',
			url: 'ExistenciasServlet',
			data: params,
			statusCode: {
				404: function(){
					alert("Pagina no encontrada");
				},
				500: function(){
					alert("Error en el servidor" + 'medida='+$('select[name=producto]').val());
				}
			},
			success: function(res){
				
				document.getElementById("existencia").value = res;
			}
		});
	})	
});
//************************************************************************************************************
// CARGA MUNICIPIO 
// FILTROS: DEPARTAMENTO
// 05/10/2018 BY JHUALC
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
// MOSTRAR OBSERVACIONES
// 05/10/2018 BY JHUALC
//************************************************************************************************************
$(document).ready(function(){
	$('a[name=agregarObs]').on('click', function(){
		
		$('#divObservaciones').show();
	})
});
//************************************************************************************************************
// AUTOCOMPLETAR CLIENTE 
// CARGA DE TODOS LOS ATRIBUTOS DEL CLIENTE
// 05/10/2018 BY JHUALC
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
  			document.getElementById("nuevoCliente").innerHTML= "";
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
                request.open("POST", "DatosClienteServlet", true);
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
//************************************************************************************************************
// VENTANA MODAL 
// 05/10/2018 BY JHUALC
//************************************************************************************************************
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
        	var total1= 0;
        	$('#table_productos tr').each(function (index){
						
						
						$(this).children('td').each(function (index2){
							
							if(index2 == 6){
								total1 = total1 + parseFloat($(this).text());
								
							}
						})
					})
					
					var total = formatear(total1);
					var subtotal =  formatear(total/ (parseFloat(sessionStorage.getItem('iva'))+1.0));
					var iva = formatear(total - total/ (parseFloat(sessionStorage.getItem('iva'))+1.0) )
					
					document.getElementById('total').value =  total;
					document.getElementById('iva').value =   iva;
					document.getElementById('subtotal').value = subtotal;
        	$('#agregar_registro').show(3000);        }
   
  
//************************************************************************************************************
// AGREGAR NUEVO CLIENTE
// 22/01/2019 BY JHUALC
//************************************************************************************************************
$(document).ready(function(){
	$('a[id=nuevoCliente]').on('click', function(){
	var primerNombre= document.getElementById("combito").value;
	var segundoNombre= document.getElementById("SegundoNombre").value;
	var apellido = document.getElementById("apellidos").value;
	var tipoIdent = document.getElementById("tipoIdent").value;
	var identificacion = document.getElementById("identificacion").value;
	var direccion = document.getElementById("direccion").value;
	var telefono = document.getElementById("telefono").value;
	var departamento = document.getElementById("departamento").value;
	var municipio = document.getElementById("municipio").value;
	var email = document.getElementById("email").value;
	var dispatcher = "0";
	
	
		$.ajax({
			type: 'POST',
			url: 'SaveClientServlet',
			data: {primerNombre: primerNombre , segundoNombre: segundoNombre, apellido: apellido, tipoIdent: tipoIdent, identificacion: identificacion, direccion: direccion, telefono: telefono, departamento: departamento, municipio: municipio, email: email, dispatcher: dispatcher} ,
			statusCode: {
				404: function(){
					alert("pagina no encontrada ValorProductoServlet contacte con soporte si el problema persiste");
				},
				500: function(){
					alert("Debe seleccionar todos los items Obligatorios");
				}
			},
			success: function(response){
			
			alert("EL CLIENTE FUE CREADO SATISFACTORIAMENTE ");
			document.getElementById("cli_id").value = response;
			
			/*
				var separado = response.split(":");
				
				for(var i = 0; i< separado.length -1; i++){
					
					var valor = (separado[i].split("-")[0]) ;
					var producto = separado[i].split("-")[1];
					var referencia = separado[i].split("-")[2];
					var pvmId = separado[i].split("-")[3];
					
					
					var valorSinIva = parseFloat (valor / (parseFloat(sessionStorage.getItem('iva'))+1.0)).toFixed(0);
					var valorDescuento = parseFloat(valorSinIva - (valorSinIva * (descuento /100))).toFixed(0);
					var total =parseFloat(Math.round(valorDescuento*cantidad).toFixed(0));
					
					var fila = $("#table_productos tr").length;
					$('table[id=table_productos]').append('<tr ><td width= "10%" id=>'+cantidad+'</td><td width= "12%">'+referencia+' </td><td width= "12%">'+marca+'</td><td width= "30%">'+tipo_p + " "+producto+ " "+ observacion+' </td><td width= "10%">'+medi+'</td><td width= "10%">'+descuento+'</td> <td width= "10%">'+formatear(valorDescuento)+'</td><td width= "14%">'+formatear(total)+'</td><td width= "7%"><span class="glyphicon glyphicon-pencil" onclick="agregar()"></span><span class="glyphicon glyphicon-remove" id="delete" ></span></td><td style="display: none;">'+pvmId+'</td><td style="display: none;">'+descuento+'</td></tr>')
				
					
				}
				var total= 0;
				$('#table_productos tr').each(function (index){
						
						
						$(this).children('td').each(function (index2){
							
							if(index2 == 7){
								reemplazado = $(this).text().replace(".", "");
								reemplazado = reemplazado.replace(".", "");
								total = total + parseFloat(reemplazado);
																
							}
						})
					})
					
					
					document.getElementById('total').value =  formatear(parseFloat(Math.round(total + (total * sessionStorage.getItem('iva')))).toFixed(0));
					document.getElementById('iva').value =  formatear(parseFloat(Math.round(total * sessionStorage.getItem('iva'))).toFixed(0));
					document.getElementById('subtotal').value = formatear(parseFloat(Math.round(total)).toFixed(0));
				var nFilas = $("#table_productos tr").length;
				if(nFilas > 20){
					//document.getElementById("agregar_registro").innerHTML= "";
					$('#agregar_registro').hide(3000);
					alert("Registraste la cantidad maxima de items para esta factura")
				}
				
				$('#divObservaciones').hide();
				
				*/
			}
		});
	})
});
   
   
   
    </script>

<!-- scripts llenado tabla de factura -->
<script>
//************************************************************************************************************
// LLENAR TABLA FACTURA
// 05/10/2018 BY JHUALC
//************************************************************************************************************
$(document).ready(function(){
	$('a[id=agregar]').on('click', function(){
	
	var prod= document.getElementById("producto").value;
	var med= document.getElementById("medida").value;
	var tipo_p = $('#tipoProd option:selected').text();
	var medi = $('#medida option:selected').text();
	var cantidad = document.getElementById("cantidad").value;
	var marca = $('#marca option:selected').text();
	var descuento = document.getElementById("descuento").value;
	var observacion = document.getElementById("textObservacion").value;
	
		$.ajax({
			type: 'GET',
			url: 'ValorProductoServlet',
			data: {produ: prod , medi: med} ,
			statusCode: {
				404: function(){
					alert("pagina no encontrada ValorProductoServlet contacte con soporte si el problema persiste");
				},
				500: function(){
					alert("Debe seleccionar todos los items Obligatorios");
				}
			},
			success: function(response){
				var separado = response.split(":");
				
				for(var i = 0; i< separado.length -1; i++){
					
					var valor = (separado[i].split("-")[0]) ;
					var producto = separado[i].split("-")[1];
					var referencia = separado[i].split("-")[2];
					var pvmId = separado[i].split("-")[3];
					
					
					//var valorSinIva = parseFloat (valor / (parseFloat(sessionStorage.getItem('iva'))+1.0)).toFixed(0);
					var valorSinIva = parseFloat (valor).toFixed(0);
					var valorDescuento = parseFloat(valorSinIva - (valorSinIva * (descuento /100))).toFixed(0);
					var total =parseFloat(Math.round(valorDescuento*cantidad).toFixed(0));
					
					var fila = $("#table_productos tr").length;
					$('table[id=table_productos]').append('<tr ><td width= "10%" id=>'+cantidad+'</td><td width= "12%">'+referencia+' </td><td width= "12%">'+marca+'</td><td width= "30%">'+tipo_p + " "+producto+ " "+ observacion+' </td><td width= "10%">'+medi+'</td><td width= "10%">'+descuento+'</td> <td width= "10%">'+formatear(valorDescuento)+'</td><td width= "14%">'+formatear(total)+'</td><td width= "7%"><span class="glyphicon glyphicon-pencil" onclick="agregar()"></span><span class="glyphicon glyphicon-remove" id="delete" ></span></td><td style="display: none;">'+pvmId+'</td><td style="display: none;">'+descuento+'</td></tr>')
				
					
				}
				var total= 0;
				$('#table_productos tr').each(function (index){
						
						
						$(this).children('td').each(function (index2){
							
							if(index2 == 7){
								reemplazado = $(this).text().replace(".", "");
								reemplazado = reemplazado.replace(".", "");
								total = total + parseFloat(reemplazado);
																
							}
						})
					})
					
					var total = formatear(total);
					var subtotal =  parseFloat(total/ (parseFloat(sessionStorage.getItem('iva'))+1.0)).toFixed(3);
					var iva = parseFloat(total - total/ (parseFloat(sessionStorage.getItem('iva'))+1.0)).toFixed(3) ;
					
					document.getElementById('total').value =  total;
					document.getElementById('iva').value =   iva;
					document.getElementById('subtotal').value = subtotal;
					
				var nFilas = $("#table_productos tr").length;
				if(nFilas > 20){
					//document.getElementById("agregar_registro").innerHTML= "";
					$('#agregar_registro').hide(3000);
					alert("Registraste la cantidad maxima de items para esta factura")
				}
				
				$('#divObservaciones').hide();
			}
		});
	})
});
//****************************************
//FUNCION SEPARADOR DE MILES
//****************************************
	 function formatear (num){
	 
	 	 separador= ".", // separador para los miles
 		 sepDecimal= ',', // separador para los decimales
		 num +='';
		 var splitStr = num.split('.');
		 var splitLeft = splitStr[0];
		 var splitRight = splitStr.length > 1 ? this.sepDecimal + splitStr[1] : '';
		 var regx = /(\d+)(\d{3})/;
		 while (regx.test(splitLeft)) {
			 splitLeft = splitLeft.replace(regx, '$1' + this.separador + '$2');
			 }
		 return splitLeft +splitRight;
		 }
//FIN FUNCION
</script>
<!-- script carga factura generada -->
<script>
//************************************************************************************************************
// CARGA FACTURA GENERADA 
// 05/10/2018 BY JHUALC
//************************************************************************************************************
$(document).ready(function(){
	$('a[id=generar]').on('click', function(){
	var fecha = "";
	fecha = $('#fecha').datepicker({ dateFormat: 'dd,MM,yyyy' }).val();
	
	//if(fecha == ""){
	//	 $('#modalResumenFactura').modal('hide');
	//	alert ("LA FECHA DE VENCIMIENTO ESTA VACIA POR FAVOR SELECCIONE UNA FECHA");
		
	//	}
	//else{
	 $('#modalResumenFactura').modal('show');
	var cliente= $('#combito').val();
	var identificacion= $('#identificacion').val();
	var direccion= $('#direccion').val();
	var telefono= $('#telefono').val();
	
	var email = $('#email').val();
	var cont = 0;
	document.getElementById("clientefac").innerHTML= cliente;
	document.getElementById("identifac").innerHTML= identificacion;
	document.getElementById("dirfac").innerHTML= direccion;
	document.getElementById("telfac").innerHTML= telefono;
	document.getElementById("fechafac").innerHTML= fecha;
	document.getElementById("emailfac").innerHTML= email;
	
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
				case 6:
					valor= $(this).text();
					break;
				case 7:
					total= $(this).text();
					break;	
			}
		})
		if(cont > 0){
			$('table[id=table_productosf]').append('<tr ><td width= "10%" id=>'+cant+'</td><td width= "12%">'+ref+' </td><td width= "12%">'+marca+'</td><td width= "30%">'+descrip+'</td><td width= "10%">'+medida+'</td> <td width= "10%">'+valor+'</td><td width= "14%">'+total+'</td></tr>')
			
		}cont++;
	})
	document.getElementById("subtotalf").value = document.getElementById("subtotal").value;
	document.getElementById("ivaf").value = document.getElementById("iva").value;
	document.getElementById("totalf").value = document.getElementById("total").value;
	//}
	})
});
</script>

<!-- scripts Save factura -->
<script>
//************************************************************************************************************
// GUARDAR FACTURAS
// 05/10/2018 BY JHUALC
//************************************************************************************************************
$(document).ready(function(){
	$('button[id=enviar]').on('click', function(){
	
	fecha   = $('#fecha').datepicker({ dateFormat: 'dd,MM,yyyy' }).val();
	
	//if(fecha == ""){
	//	alert("LA FECHA DE VENCIMIENTO SE ENCUENTRA VACIA");
	//}else {
	var detalles = [];
	
	var contador =0;
	
	$('#table_productos tr').each(function (index){
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
				case 6:
					valor= $(this).text().replace(".","").replace(".","");
					break;
				case 7:
					total= $(this).text().replace(".","").replace(".","");
					break;
				case 9:
					pvmid= $(this).text();
					break;
				case 10:
					descuento= $(this).text();
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
				"descuento" : descuento,
				"subtotal"	: valor,
				"observacion" : descrip
			});
			}
		})
		var d = new Date();
		var n = d.getDate();
		var jsonFactura = JSON.stringify(eval({
						cliente: $('#cli_id').val(),
						subtotal: $('#subtotal').val().replace(".","").replace(".",""),
						total: $('#total').val().replace(".","").replace(".",""),
						iva: $('#iva').val().replace(".","").replace(".",""),
						fecha: n ,
						detalleFactura : detalles
						}))
		$.ajax({
			type: 'POST',
			url: 'SaveFacturaposServlet',
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
				//alert (response);
				
			}
			
		});
		//}
	})
	
});
//********************************************************
//CALCULAR VALOR CON DESCUENTO ONLINE
//********************************************************
$(document).ready(function(){
$( "#descuento" ).keyup(function() {
  
	var prod= document.getElementById("producto").value;
	var med= document.getElementById("medida").value;
	var descuento = document.getElementById("descuento").value;
	
	
	
	$.ajax({
			type: 'POST',
			url: 'CalculaDescuento',
			data: {produ: prod , medi: med} ,
			statusCode: {
				404: function(){
					alert("pagina no encontrada ValorProductoServlet contacte con soporte si el problema persiste");
				},
				500: function(){
					alert("Debe seleccionar todos los items Obligatorios");
				}
			},
			success: function(response){
				
				document.getElementById("calculado").value = response - (response * (descuento/100));
				document.getElementById("original").value = response;
			
			}
		});
});
});
//********************************************************
//CALCULAR DESCUENTO A PARTIR DEL VALOR
//********************************************************
$(document).ready(function(){
$( "#calculado" ).keyup(function() {
  
	var prod= document.getElementById("producto").value;
	var med= document.getElementById("medida").value;
	var calculado = document.getElementById("calculado").value;
	
	
	
	$.ajax({
			type: 'POST',
			url: 'CalculaDescuento',
			data: {produ: prod , medi: med} ,
			statusCode: {
				404: function(){
					alert("pagina no encontrada ValorProductoServlet contacte con soporte si el problema persiste");
				},
				500: function(){
					alert("Debe seleccionar todos los items Obligatorios");
				}
			},
			success: function(response){
				
				document.getElementById("descuento").value = (1- (calculado/response))*100;
				document.getElementById("original").value = response;
			
			}
		});
});
});
//*******************************************************
//CAPTURA EVENTO CHECKBOX CLIENTE MOSTRADOR
//*******************************************************
$(document).ready(function(){
 $('#mostrador').change(function() {
   
  if(this.checked == true){
       document.getElementById("identificacion").value ="CM000000000";
       document.getElementById("combito").value ="CLIENTE MOSTRADOR";
       document.getElementById("cli_id").value=1;
       $('#identificacion').prop("disabled", true);
       $('#combito').prop("disabled", true);
       $('#SegundoNombre').prop("disabled", true);
       $('#apellidos').prop("disabled", true);
        $('#direccion').prop("disabled", true);
         $('#telefono').prop("disabled", true);
          $('#municipio').prop("disabled", true);
           $('#email').prop("disabled", true);
     }
     
    if(this.checked == false){
       document.getElementById("identificacion").value ="";
       document.getElementById("combito").value ="";
       $('#identificacion').prop("disabled", false);
       $('#combito').prop("disabled", false);
       $('#SegundoNombre').prop("disabled", false);
       $('#apellidos').prop("disabled", false);
        $('#direccion').prop("disabled", false);
         $('#telefono').prop("disabled", false);
          $('#municipio').prop("disabled", false);
           $('#email').prop("disabled", false);
     }
});
});
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
				<li ><a href="./FacturaServlet">Facturas<span
						style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a></li>
				<li class="active"><a href="./FacturaPosServlet">Facturas POS<span	style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a></li>
				<li><a href="NotaCredito.jsp">Notas Credito<span
						style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-th-list"></span></a></li>
				<li><a href="./NotaDebitoServlet">Notas Debito<span style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-tags"></span></a></li>
				<li><a href="Reportes.jsp">Reportes<span style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
				<li><a href="CopiaFactura.jsp">Generar Copia<span style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
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
	<br>

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
			</td>
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
						<td><input type="text" class="form-control"
							id="identificacion"> <input type="hidden" id="cli_id"
							name="cli_id"></td>
					</tr>
				</table></td>
		</tr>
		<!-- inicia segunda linea del formulario -->
		<tr>
			<td><label for="sel1">Direccion: </label>
				</div></td>
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
				</div></td>
			<td><input type="email" class="form-control" id="email">
			</td>
			<td>
			</td>
			<td>
				 <div class="form-check">
				    <input type="checkbox" class="form-check-input" id="mostrador" name="mostrador">
				    <label class="form-check-label" for="exampleCheck1">Cliente Mostrador</label>
				  </div>
			</td>
		</tr>
	</table>

	<c:set var="completo" scope="session" value="1" />
	<div id="nuevoClienteDiv">
		<a id="nuevoCliente" name="nuevoCliente"  class="btn btn-info btn-sm"> <span
			class="glyphicon glyphicon-user"></span> NUEVO CLIENTE
		</a>


	</div>

	<!-- inicia tercera linea del formulario -->
	<hr />
	<table id="tabla_form">
		<tr>
			<td><label for="sel1"> <span class="red"> *</span>Marca:
			</label></td>
			<td><select class="form-control" id="marca" name="marca">
					<option value="">Seleccione ...</option>

					<c:forEach items="${marca}" var="objeto">

						<option value=<c:out value="${objeto.marId}"/>><c:out
								value="${objeto.marDescripcion}" /></option>
					</c:forEach>


			</select></td>
			<td><label for="usr"><span class="red"> *</span>Tipo
					producto: </label></td>

			<td><select class="form-control" id="tipoProd" name="tipoProd">
					<option>Seleccione...</option>

			</select></td>

		</tr>
		<!-- inicia segunda linea del formulario -->
		<tr>
			<td><label for="sel1"><span class="red"> *</span>Producto:
			</label>
				</div></td>
			<td><select class="form-control" id="producto" name="producto">
					<option>Seleccione ...</option>
			</select></td>
			<td><label for="usr">Referencia: </label></td>
			<td><input type="text" class="form-control" id="referencia">
			</td>
		</tr>
		<!-- inicia tercera linea del formulario -->
		<tr>
			<td><label for="sel1"><span class="red"> *</span>Medida:
			</label>
				</div></td>
			<td><select class="form-control" id="medida" name="medida">
					<option>Seleccione ...</option>
			</select></td>
			<td><label for="usr"><span class="red"> *</span>Cantidad:
			</label></td>
			<td><input type="text" class="form-control" id="cantidad"
				required></td>
		</tr>
		<tr>
			<td><label for="usr"><span class="red"> *</span>Descuento (%):
			</label></td>
			<td><input type="number" class="form-control" id="descuento" value = "0">
			</td>
			<td><label for="usr"><span class="red"> *</span>Valor Calculado:
			</label></td>
			<td><input type="number" class="form-control" id="calculado" >
			</td>
		</tr>
		<tr>
			<td><label for="usr"><span class="red"> *</span>Valor Original:
			</label></td>
			<td><input type="number" class="form-control" id="original" value = "0">
			</td>
			<td><label for="usr"><span class="red"> *</span>Existencias:</label>
			</td>
			<td><input type="text" class="form-control" id="existencia" disabled="true" >
			</td>
		</tr>
			<tr>
			<td>
				<div id="agregar_registro">
					<a id="agregarObs" name="agregarObs" class="btn btn-info btn-sm">
						<div class="glyphicon glyphicon-plus"></div> Agregar Observación
					</a>
				</div>
			</td>
		</tr>

	</table>
	<div class="col-xs-12" style="display: none;" id="divObservaciones">
		<table align="center">
			<tr>
				<td>
					<label>Observaciones:</label>
				</td>
				<td>
					<textarea rows="4" cols="100"  id="textObservacion" ></textarea>
				</td>
			</tr>
		</table>
	</div>

	<div id="agregar_registro">
		<a id="agregar" name="agregar" class="btn btn-info btn-sm">
			<div class="glyphicon glyphicon-plus"></div> Agregar
		</a>
	</div>
	<hr />
	<div class="table-responsive" id="div_tabla">
		<div class="col-xs-12">
			<table class="table table-bordered" id="table_productos">
				<thead class="thead-dark">
					<tr>
						<th width="7%">CANT</th>
						<th width="12%">REFERENCIA</th>
						<th width="10%">MARCA</th>
						<th width="30%">DESCRIPCION</th>
						<th width="10%">MEDIDA</th>
						<th width="5%">DTO %</th>
						<th width="14%">VALOR UNITARIO</th>
						<th width="14%">VALOR TOTAL</th>
						<th width="5%">OPT</th>
					</tr>
				</thead>

			</table>
		</div>
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