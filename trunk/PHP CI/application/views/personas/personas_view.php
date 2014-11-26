<!--Start Content-->
<div id="content" class="col-xs-12 col-sm-10">

	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<a href="#" class="show-sidebar"><i class="fa fa-bars"></i></a>
			<ol class="breadcrumb">
				<li><a href="#" onclick="window.location.href = '../home'">Inicio</a></li>
				<li>Personas</li>
			</ol>
		</div>
	</div>
	
	<?php $attributes = array('id' => 'formPersonas');?>
	<?php echo form_open('personas/search',$attributes); ?>
	
	<div class="row">
		<div class="col-xs-12">
			<h3 class="page-header">N&oacute;mina de personas</h3>
			<br>
			<?php if ( ! empty($msg)){ echo "<p class='bg-success'>".$msg."</p>"; }?>
			<?php if ( ! empty($error)){ echo "<p class='bg-success'>".$error."</p>"; }?>
			<br>

			<?php $this->load->view('templates/search', array("table"=>'personas')); ?>
		</div>
	</div>

	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-folder-open-o"></i>
						<span>Registro de Personas (<?php echo $total_rows?>)</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						<a class="expand-link"><i class="fa fa-expand"></i></a>
						<a class="close-link"><i class="fa fa-times"></i></a>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content no-padding">
					<table class="table table-bordered table-striped table-hover table-heading table-datatable" id="datatable-3">
						<thead>
							<tr>
								<th id="sort_nombre" class="sorting" onclick="ordenar('nombre')">Nombre</th>
								<th>Mail</th>
								<th>Tel&eacute;fono</th>
								<th>IMEI</th>
								<th>Fecha Alta</th>
								<th>Acciones</th>
							</tr>	
						</thead>
						<tbody>
							<?php 
							foreach($personas as $persona) 
							{
								echo "<tr>";
								    echo "<td>".strtoupper($persona->nombre)."</td>";
								    echo "<td>".$persona->mail."</td>";
								    echo "<td>".$persona->telefono."</td>";
								    echo "<td>".$persona->imei."</td>";
								    echo "<td>".$persona->fecha."</td>";
								    echo "<td>";
							    		
									    if (!empty($permisos['personas_suspender'])){
									    	echo "<a href='".site_url('/personas/suspender/'.$persona->id)."'>";
									    	echo "<i class='fa fa-lg  fa-times-circle'></i></span>";
									    	echo "<a/>&nbsp;&nbsp;";
									    }
								    
									    if (!empty($permisos['personas_update'])){
									    	echo "<a href='".site_url('/personas/update/'.$persona->id)."'>";
												echo "<i class='fa fa-lg  fa-pencil-square-o'></i></span>";
											echo "<a/>&nbsp;&nbsp;";
								    	}
								    	
										if (!empty($permisos['personas_delete'])){
											echo "<a href='".site_url('/personas/delete/'.$persona->id)."'>";
											echo "<i class='fa fa-lg fa-trash-o'></i></span>";
											echo "<a/>&nbsp;&nbsp;";
										}
										
									echo "</td>";
							    echo "</tr>";
							}
							?>
						</tbody>
					</table>	
				    <ul class="pagination"><?php echo $this->pagination->create_links(); ?></ul>
				 </div>
			</div>
		</div>
	</div>
	
	<?php echo form_close(); ?>   
	
</div><!--End Content-->