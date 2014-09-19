<!--Start Content-->
<div id="content" class="col-xs-12 col-sm-10">

	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<a href="#" class="show-sidebar"><i class="fa fa-bars"></i></a>
			<ol class="breadcrumb">
				<li><a href="#" onclick="window.location.href = '<?php echo base_url('index.php');?>/home'">Inicio</a></li>
				<li><a href="#" onclick="window.location.href = '<?php echo base_url('index.php');?>/empleados/index'">Usuarios</a></li>
				<li>Datos del Usuario</li>
			</ol>
		</div>
	</div>
	
	<div class="row">
		<div class="col-xs-12">
			<h3 class="page-header">Datos del usuario</h3>
			<BR>
			<?php  if (validation_errors()) echo "<div class='bg-danger'>".utf8_encode(validation_errors())."</div>"; ?>
			<br>
		</div>	
	</div>
	
	<div class="row">	
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-folder-open-o"></i>
						<span>Formulario de datos</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						<a class="expand-link"><i class="fa fa-expand"></i></a>
						<a class="close-link"><i class="fa fa-times"></i></a>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content no-padding">
					<?php echo form_open('empleados/add', array('id' => 'empleadosForm', 'class'=> 'form-horizontal bootstrap-validator-form')); ?>
					<input type="hidden" value="1" name="sendform">
					<input type="hidden" value="<?php if (isset($usuario)){echo $usuario['id'];} ?>" name="id" id="id">
					<br>
					<fieldset>
						<div class="form-group">
							<label class="col-sm-3 control-label">Nombre</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="nombre" name="nombre" value="<?php if (isset($usuario)){echo $usuario['nombre'];} ?>" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Ciudad</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="ciudad" name="ciudad"  value="<?php if (isset($usuario)){echo $usuario['ciudad'];} ?>" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Barrio</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="barrio" name="barrio"  value="<?php if (isset($usuario)){echo $usuario['barrio'];} ?>" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Direcci&oacute;n</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" id="domicilio" name="domicilio" value="<?php if (isset($usuario)){echo utf8_decode($usuario['domicilio']);} ?>" />
							</div>
						</div>
						<div class="form-group has-feedback">
							<label class="col-sm-3 control-label">Fecha de Nacimiento</label>
							<div class="col-sm-2">
								<input readonly="readonly" type="text" id="fecha_nacimiento" class="form-control" name="fecha_nacimiento" value="<?php if (isset($usuario)) echo $usuario['fecha_nacimiento'];?>" >
								<span class="fa fa-calendar txt-danger form-control-feedback"></span>
							</div>
						</div>
						<div class="form-group has-feedback">
							<label class="col-sm-3 control-label">Fecha de Alta</label>
							<div class="col-sm-2">
								<input readonly="readonly" type="text" id="fecha_alta" class="form-control" name="fecha_alta" value="<?php if (isset($usuario)) echo $usuario['fecha_alta'];?>" >
								<span class="fa fa-calendar txt-danger form-control-feedback"></span>
							</div>
						</div>
						<div class="form-group has-feedback">
							<label class="col-sm-3 control-label">Fecha de Baja</label>
							<div class="col-sm-2">
								<input readonly="readonly" type="text" id="fecha_baja" class="form-control" name="fecha_baja" value="<?php if (isset($usuario)) echo $usuario['fecha_baja'];?>" >
								<span class="fa fa-calendar txt-danger form-control-feedback"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Tel&eacute;fono</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="celular" name="celular"  value="<?php if (isset($usuario)){echo $usuario['celular'];} ?>" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">DNI</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="dni" name="dni"  value="<?php if (isset($usuario)){echo $usuario['dni'];} ?>" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Convenio</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="convenio" name="convenio"  value="<?php if (isset($usuario)){echo $usuario['convenio'];} ?>" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">Sueldo</label>
							<div class="col-sm-2">
								<input type="text" class="form-control" id="sueldo" name="sueldo"  value="<?php if (isset($usuario)){echo $usuario['sueldo'];} ?>" />
							</div>
						</div>
					</fieldset>
					<div style="width:100%;text-align:center;padding:10px">
						<a href="#" onclick="$('#empleadosForm').submit()">
							<button class="btn btn-primary btn-label-left" type="button">
								Guardar
							</button>
						</a>
						<a href="#" onclick="window.location.href = '../'">
							<button class="btn btn-primary btn-label-left" type="button">
								Cancelar
							</button>
						</a>
					</div>
					<?php echo form_close(); ?>
				</div>
			</div>
		</div>
	</div>

</div><!--End Content-->

<script src="<?php echo base_url();?>assets/plugins/select2/select2.js"></script>
<script src="<?php echo base_url();?>assets/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
<script src="<?php echo base_url();?>assets/js/validations.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		// Initialize datepicker
		$('#fecha_nacimiento').datepicker({ changeYear: true,yearRange: "-80:+0", dateFormat: "dd/mm/yy", altFormat: "yy-mm-dd",altField: "#fecha_nacimiento" });
		$('#fecha_alta').datepicker({ changeYear: true,yearRange: "-20:+0", dateFormat: "dd/mm/yy", altFormat: "yy-mm-dd",altField: "#fecha_alta" });
		$('#fecha_baja').datepicker({ changeYear: true,yearRange: "-20:+0", dateFormat: "dd/mm/yy", altFormat: "yy-mm-dd",altField: "#fecha_baja" });
		// Load script of Select2 and run this
		LoadSelect2Script(Select2Test);
		// Load example of form validation
		LoadBootstrapValidatorScript(FormValidator);
		WinMove();
	});
</script>
