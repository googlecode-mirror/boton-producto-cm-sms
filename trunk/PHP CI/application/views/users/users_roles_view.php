<script>
	function selectall(elem, grupo){
		if (elem.checked){
			$("."+grupo).attr('checked', 'true');
			$("."+grupo).prop('checked', true);
		}
		else
			$("."+grupo).removeAttr('checked');
	}
</script>

<!--Start Content-->
<div id="content" class="col-xs-12 col-sm-10">

	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<li><a href="#" onclick="window.location.href = '<?php echo base_url('index.php');?>/home'">Inicio</a></li>
				<li><a href="#" onclick="window.location.href = '<?php echo base_url('index.php');?>/users/index'">Usuaios</a></li>
				<li><a href="#">Roles y Permisos</a></li>
			</ol>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="page-header">Roles y Permisos</h3>
			<BR>
			<?php if ( ! empty($msg)){ echo "<div class='msg'>".$msg."</div>"; }?>
			<?php if ( ! empty($error)){ echo "<div class='error'>".$error."</div>"; }?>
			<br>
		</div>	
	</div>
	<div class="row">	
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-folder-open-o"></i>
						<span>Asignaci&oacute;n de permisos</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						<a class="expand-link"><i class="fa fa-expand"></i></a>
						<a class="close-link"><i class="fa fa-times"></i></a>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content no-padding">
					<?php echo form_open('users/roles', array('id' => 'rolesForm')); ?>
					<input type="hidden" value="1" name="sendform">
					<table class="table table-bordered table-striped table-hover table-heading table-datatable" id="datatable-3">
						<thead>
							<tr>
								<th>M&oacute;dulo</th>
								<th>Nombre</th>
								<?php 
									foreach($roles as $rol){
							    		echo "<th>$rol->roleName</th>";
							    	} 
							    ?>
							</tr>	
						</thead>
						<tbody>
						
						<?php 
						$anterior = 0;
						$i = 0;
						while ($i < count($permisos)) 
						{
							$permiso = $permisos[$i];
							if ($permiso->nroSeccion != $anterior)
							{
								echo "<tr >";
									echo "<td colspan='2' style='background-color:#D4E8F7'>".$permiso->seccion."</td>";
									foreach($roles as $rol)
									{
										echo "<td style='background-color:#D4E8F7'><input onchange='selectall(this, \"".$rol->ID."_".$permiso->nroSeccion."\");' type='checkbox' "; if (isset($roles_permisos[$rol->ID."-".$permiso->ID]) && $roles_permisos[$rol->ID."-".$permiso->ID] == 1){ echo "checked=checked";} echo " value='".$rol->ID."-".$permiso->ID ."' ></td>";

									}
								echo "</tr>";
								$anterior= $permiso->nroSeccion;
							}
							else
							{
								echo "<tr>";
								    echo "<td>".$permiso->seccion."</td>";
								    echo "<td>".$permiso->permName."</td>";
								    foreach($roles as $rol)
									{
								    	echo "<td><input type='checkbox' class='".$rol->ID."_".$permiso->nroSeccion."' name='rolesPermisosArray[]'"; if (isset($roles_permisos[$rol->ID."-".$permiso->ID]) && $roles_permisos[$rol->ID."-".$permiso->ID] == 1){ echo "checked=checked";} echo " value='".$rol->ID."-".$permiso->ID ."' ></td>";
								    }
								echo "</tr>";
								$i++;
							}
						}
						?>
						</tbody>
					</table>
					<div style="width:100%;text-align:center;padding:10px">
						<a href="#" onclick="$('#rolesForm').submit()">
							<button class="btn btn-primary btn-label-left" type="button">
								Grabar permisos
							</button>
						</a>
					</div>
					<?php echo form_close(); ?>
				</div>
			</div>
		</div>
	</div>

</div><!--End Content-->