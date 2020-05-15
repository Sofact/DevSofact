/**
 * 
 */


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
//carga informacion de  producto filtrado por marca y tipo de producto javascript
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

