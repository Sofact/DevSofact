/**
 * Aqui se estan almacenando los Javascript de la pagina Facturacion.JSP
 */


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
// carga informacion de  producto filtrado por marca y tipo de producto javascript
//************************************************************************************************************

$(document).ready(function(){
	$('select[name=tipoProd]').on('change', function(){
		$.ajax({
			type: 'GET',
			url: 'ProductoServlet',
			data: 'tipo_prod='+$('select[name=tipoProd]').val(),
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
// carga informacion de medida  filtrado por producto javascript
//************************************************************************************************************
$(document).ready(function(){
	$('select[name=producto]').on('change', function(){
		$.ajax({
			type: 'GET',
			url: 'MedidaServlet',
			data: 'medida='+$('select[name=producto]').val(),
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
 
        // Remove invalid value
        /*
        this.input
          .val( "" )
          .attr( "title", value + " didn't match any item" )
          .tooltip( "open" );
        this.element.val( "" );
        this._delay(function() {
          this.input.tooltip( "close" ).attr( "title", "" );
        }, 2500 );
        this.input.autocomplete( "instance" ).term = "";*/
        alert ("valor texto "+ this.input.val());
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
                        		 var select_identificacion = tipo_ident[0].getAttribute("id");
                                
                                // document.getElementById("identificacion").value = tipo_ident[0].getAttribute("id");
                                 document.getElementById("identificacion").value = identifica[0].getAttribute("id");
                                 document.getElementById("direccion").value = direcc[0].getAttribute("id");
                                 document.getElementById("telefono").value = telefono[0].getAttribute("id");
                                 document.getElementById("email").value = email[0].getAttribute("id");
                                
                                
                                
                                
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
                                alert("ESto es mejor "+message);
                        }
                };
                request.send("userdata="+clienteSeleccionado);
        
  }