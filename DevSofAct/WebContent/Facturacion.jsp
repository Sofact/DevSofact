<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	 	<link rel="stylesheet" type="text/css" href="style.css">
	 	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	 	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
		<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/css/bootstrap.min.css" />
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker.min.css" />
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
		<link rel="stylesheet" type="text/css" href="last.css">
		 
		
	</head>
	<body>
	
	    <header> SofAct
	    <p>Usuario: <%=request.getUserPrincipal().getName()%></p> 
	    </header>
	   
	
<nav class=" navbar navbar-default sidebar" role="navigation">
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
        <li class="active"><a href="#">Facturas<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a></li>        
        <li ><a href="#">Notas Credito<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-th-list"></span></a></li>
        <li ><a href="#">Notas Debito<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-tags"></span></a></li>
        <li ><a href="#">Reportes<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
        <li data-toggle="collapse" data-target="#service" class="collapsed" ><a >Administración<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-cog"></span></a></li>
      		 <ul class="sub-menu collapse nav navbar-nav" id="service">
                  <li ><a data-toggle="modal" data-target="#exampleModal" >Crear Cliente<span style="font-size:16px;"  class="pull-right hidden-xs showopacity glyphicon glyphicon-globe"></span></a></li>
                  <li ><a href="Facturacion.html">Crear Usuario<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span></a></li>
                  <li ><a href="Facturacion.html">Crear Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-shopping-cart"></span></a></li>
                  <li ><a href="Facturacion.html">Crear Tipo Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-barcode"></span></a></li>
                  <li ><a href="Facturacion.html">Crear Marca<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-eye-open"></span></a></li>
                  <li ><a href="Facturacion.html">Crear Medida<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-wrench"></span></a></li>
                  

                </ul>
      </ul>
    </div>
  </div>
</nav>
	
	     
	     <article>
	     <br><br>
	     <table id= "tabla_form" >
	     	<tr>
	     		<td><label for="sel1">Cliente: </label>
	     		</td>
	     		<td><select class="form-control" id="sel1">
										    <option>Pintu Matiz</option>
										    <option>Pintu Edwar</option>
										    <option>Ferre Pinturas</option>
										    <option>Pintu Rossi</option>
										  </select>
	     		</td>
	     		<td> <label for="usr">Identificacion:  </label>
	     		</td>
	     		<td><table><tr><td>
	     			<select class="form-control" id="sel1">
										    <option id="identifica">CC</option>
										    <option id="identifica">NIT</option>
										    <option id="identifica">CE</option>
										    <option id="identifica">TI</option>
					 </select></td>
					 <td>
					<input type="text" class="form-control" id="identificacion">
					</td></tr>
					</table>
	     		</td>
	     	</tr>
	     	<!-- inicia segunda linea del formulario -->
	     	<tr>
	     		<td><label for="sel1">Direccion: </label> </div>
	     		</td>
	     		<td> <input type="text" class="form-control" id="direccion">
	     		</td>
	     		<td> <label for="usr">Telefono:  </label>
	     		</td>
	     		<td><input type="text" class="form-control" id="telefono">
	     		</td>
	     	</tr>
	     	<!-- inicia tercera linea del formulario -->
	     	<tr>
	     		<td><label for="sel1">e-Mail: </label> </div>
	     		</td>
	     		<td><input type="text" class="form-control" id="email">
	     		</td>
	     		<td><label for="usr">Fecha de Pago:  </label>
	     		</td>
	     		<td><input type="text" class="form-control" id="fecha" name="fecha" />   </div>
	     		</td>
	     	</tr>
	     </table >
		
			
			<!-- inicia tercera linea del formulario -->
			<hr  />
			 <table id= "tabla_form" >
	     	<tr>
	     		<td><label for="sel1">Marca: </label>
	     		</td>
	     		<td><select class="form-control" id="sel1">
										    <option>Super</option>
										    <option>Tonner</option>
										    <option>Gricoat</option>
										    <option>Every</option>
										  </select>
	     		</td>
	     		<td> <label for="usr">Tipo producto:  </label>
	     		</td>
	     		
					<td><select class="form-control" id="sel1">
										    <option>Esmalte</option>
										    <option>Primer</option>
										    <option>Base Pur</option>
										    <option>Lacas</option>
										  </select>
	     		</td>
	     		
	     	</tr>
	     	<!-- inicia segunda linea del formulario -->
	     	<tr>
	     		<td><label for="sel1">Producto: </label> </div>
	     		</td>
	     		<td><select class="form-control" id="sel1">
										    <option>Blanco S</option>
										    <option>Amarillo S</option>
										    <option>Rojo Ferrari G</option>
										    <option>Gold Tonner T</option>
										  </select>
	     		</td>
	     		<td> <label for="usr">Referencia:  </label>
	     		</td>
	     		<td><input type="text" class="form-control" id="telefono">
	     		</td>
	     	</tr>
	     	<!-- inicia tercera linea del formulario -->
	     	<tr>
	     		<td><label for="sel1">Medida: </label> </div>
	     		</td>
	     		<td><select class="form-control" id="sel1">
										    <option>Galon</option>
										    <option>1/4 galon</option>
										    <option>1/8 galon</option>
										    <option>Cuñete</option>
										  </select>
	     		</td>
	     		<td><label for="usr">Cantidad:  </label>
	     		</td>
	     		<td><input type="text" class="form-control" id="telefono">
	     		</td>
	     	</tr>
	     </table >
	     <div onclick="agregar()" >
	     <a href="#" class="btn btn-info btn-sm" data-toggle="modal" data-target="#exampleModal">
          <span class="glyphicon glyphicon-plus"></span> Agregar 
        	</a>
			      
	     
	     </div>
		<hr  />
		<div class="table-responsive" id="div_tabla">
		<div class="col-xs-12">
		  <table class="table table-bordered" id="table_productos">
		   <thead class="thead-dark">
		    <tr>
			    <th width= "10%">
			    	CANT
			    </th>
			    <th width= "12%">
			    	REFERENCIA
			    </th>
			    <th width= "12%">
			    	MARCA
			    </th>
			    <th width= "25%">
			    	DESCRIPCION
			    </th>
			    <th width= "10%">
			    	MEDIDA
			    </th>
			    <th width= "14%">
			    	VALOR UNITARIO
			    </th>
			    <th width= "14%">
			    	VALOR TOTAL
			    </th>
			    <th width= "5%">
			    	OPT
			    </th>
		    </tr>
		    </thead>
		    <tr>
			    <td width= "10%">
			    	2
			    </td>
			    <td width= "12%">
			    	PTAU1071
			    </td>
			    <td width= "12%">
			    	GRICOAT
			    </td>
			    <td width= "30%">
			    	BASE PUR NEGRO MATE G
			    </td>
			    <td width= "10%">
			    	GALON
			    </td>
			    <td width= "10%">
			    	45000
			    </td>
			    <td width= "14%">
			    	90000
			    </td>
			    <td width= "7%">
			    	<span class="glyphicon glyphicon-pencil" onclick="agregar()"></span>
			    	<span class="glyphicon glyphicon-remove" onclick="borrar()"></span>
			    </td>
		    </tr>
		  </table>
		  </div>
		</div>
	     
	     </article>
	     <aside> 
	     </aside>
	     <footer> POWERED BY: <br>
	     		JULIAN JAIMES<br>
	     		JHUALC<br>
	     </footer> 
	     
	
	<!-- Modal -->

<div class="modal fade" id="exampleModal"  role="dialog" aria-labelledby="exampleModalLabel"  aria-hidden="false">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Crear o editar Clientes</h5>
       
      </div>
      <form method="post"  action="./ClientServlet">
      <div class="modal-body" >
        <table id= "tabla_form" >
	     	<tr>
	     		<td><label for="sel1">Cliente: </label>
	     		</td>
	     		<td><input type="text" class="form-control" id="nombre" name="nombre_cliente">
	     		</td>
	     		<td> <label for="usr">Identificacion:  </label>
	     		</td>
	     		<td><table><tr><td>
	     			<select class="form-control" id="sel1" name="tipo_doc">
										    <option id="identifica">CC</option>
										    <option id="identifica">NIT</option>
										    <option id="identifica">CE</option>
										    <option id="identifica">TI</option>
					 </select></td>
					 <td>
					<input type="text" class="form-control" id="identificacion" name= "identificacion">
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
	     		<td><label for="sel1">e-Mail: </label> 
	     		</td>
	     		<td><input type="email" class="form-control" id="email" name="email">
	     		</td>
	     		<td><label for="usr">Tipo de Cliente:  </label>
	     		</td>
	     		<td><input type="text" class="form-control" id="tipo_cliente" name= "tipo_cliente" />  
	     		</td>
	     	</tr>
	     </table >
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
        <button type="submit" class="btn btn-primary">Guardar</button>
      </div>
    </div>
  </div>
  </form>
</div>     
	</body>	
	
<script>
        $( document ).ready(function() {
            $('#fecha').datepicker();
        });
        
        function agregar(){
        
        	alert("fue agregado");
        }
        function borrar(){
        
        	alert("fue borrado");
        }
    </script>

	
</html>