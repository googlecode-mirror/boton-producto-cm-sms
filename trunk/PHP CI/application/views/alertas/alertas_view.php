<script type="text/javascript">
function atenderAlertaAjax(alerta_id, valor){	
	if (alerta_id != ''){
		jQuery.ajax({
			type: "POST",
			url: "alertas/atender",
			data: 	"idAlerta="+alerta_id+"&valor="+valor,
			success: function(html){
				if (html.indexOf(":ok")> 0){
					location.reload();
				}					
			}
		});
	}else{
		alert("Seleccione una alarma para poder atender.");
	}
}

</script>
<!--Start Content-->
<div id="content" class="col-xs-12 col-sm-10">

	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<a href="#" class="show-sidebar"><i class="fa fa-bars"></i></a>
			<ol class="breadcrumb">
				<li><a href="#" onclick="window.location.href = '<?php echo base_url('index.php');?>/home'">Inicio</a></li>
				<li>Alertas</li>
			</ol>
		</div>
	</div>
	
	<?php $attributes = array('id' => 'formAlertas');?>
	<?php echo form_open('alertas/search',$attributes); ?>
	
	<div class="row">
		<div class="col-xs-12">
			<h3 class="page-header">N&oacute;mina de alertas</h3>
			<br>
			<?php if ( ! empty($msg)){ echo "<p class='textcenter fontbold fontsize-M bg-success'>".$msg."</p>"; }?>
			<?php if ( ! empty($error)){ echo "<p class='textcenter fontbold fontsize-M bg-success'>".$error."</p>"; }?>
			<br>

			<?php $this->load->view('templates/search', array("table"=>'alertas')); ?>
		</div>
	</div>

	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-folder-open-o"></i>
						<span>Registro de Alertas (<?php echo $total_rows?>)</span>
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
								<th>Persona</th>
								<th>Tipo de Alerta</th>
								<th>Lat-Lng</th>
								<th>Fecha Hora</th>
								<th>Proveedor</th>
								<th>Presicion</th>
								<th>Atendido</th>
								<th>Acciones</th>
							</tr>	
						</thead>
						<tbody>
							<?php 
							foreach($alertas as $alerta) 
							{
								echo "<tr>";
								    echo "<td>".$alerta->nombre."</td>";
								    echo "<td>";
								    		if ($alerta->boton_id == '1') echo "Bomberos";
								    		else if ($alerta->boton_id == '2') echo "Ambulancia";
								    		else if ($alerta->boton_id == '3') echo "Policia";
								    echo "</td>";
								    echo "<td>".$alerta->lat.'-'.$alerta->lng."</td>";
								    echo "<td>".$alerta->fecha_hora."</td>";
								    echo "<td>".$alerta->locationProvider."</td>";
								    echo "<td>".$alerta->accuracy."</td>";
								    echo "<td align='center'>";								    	
								    	if (!empty($permisos['alertas_update'])){
											if($alerta->atendido == 0){
									    		echo "<a title='Atender' href='javascript:atenderAlertaAjax(".$alerta->idAlerta.", 1)'>";
									    		echo "<i class='fa fa-lg fa-square-o'></i></span>";
									    		echo "<a/>&nbsp;&nbsp;";
									    	}else{
									    		echo "<a title='Desatender' href='javascript:atenderAlertaAjax(".$alerta->idAlerta.", 0)'>";
												echo "<i class='fa fa-lg fa-check-square-o'></i></span>";
												echo "<a/>&nbsp;&nbsp;";
									    	}
								    	}else{
								    		if ($alerta->atendido == 0) echo "NO";
								    		else echo "SI";
								    	}
								    	
								    echo"</td>";
								    echo "<td align='center'>";
									    if (!empty($permisos['alertas_update'])){
									    	echo "<a title='Ver Detalle' href='".site_url('/alertas/viewDetail/'.$alerta->idAlerta)."'>";
									    	echo "<i class='fa fa-lg fa-map-marker'></i></span>";
									    	echo "<a/>&nbsp;&nbsp;&nbsp;&nbsp;";
									    }
										if (!empty($permisos['alertas_delete'])){
											echo "<a title='Eliminar' href='".site_url('/alertas/delete/'.$alerta->idAlerta)."'>";
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