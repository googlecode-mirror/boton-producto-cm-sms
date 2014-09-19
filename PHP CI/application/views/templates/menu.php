<?php 
$userLogged = $this->session->userdata('logged_user');
$permisos = $userLogged['permisos'];
?>
<div id="sidebar-left" class="col-xs-2 col-sm-2">
	<ul class="nav main-menu">
		<li>
			<a href="<?php echo base_url('index.php');?>/home" class="">
				<i class="fa fa-dashboard"></i>
				<span class="hidden-xs">Inicio</span>
			</a>
		</li>
		<li class="dropdown">
			<?php  if (!empty($permisos['users_index'])){ ?>
			<a href="#" class="dropdown-toggle">
				<i class="fa fa-group"></i>
				<span class="hidden-xs">Usuarios y Permisos</span>
			</a>
			<?php }?>
			<ul class="dropdown-menu">
				<li><?php if (!empty($permisos['users_index'])){echo anchor('/users', 'Usuarios', 'title="Usuarios"');}?></li>
				<li><?php if (!empty($permisos['users_roles'])){echo anchor('/users/roles', 'Roles', 'title="Roles"');}?></li>
			</ul>
		</li>
		
		<?php if (!empty($permisos['alertas_mapa'])){ ?>
		<li>
			<a title="Mapa" href="<?php echo base_url('index.php');?>/alertas/mapa" class="">
				<i class="fa fa-warning"></i>
				<span class="hidden-xs">Mapa</span>
			</a>
		</li>
		<?php }?>
				
		<?php if (!empty($permisos['personas_index'])){ ?>
		<li>
			<a title="Personas" href="<?php echo base_url('index.php');?>/personas" class="">
				<i class="fa fa-table"></i>
				<span class="hidden-xs">Personas</span>
			</a>
		</li>
		<?php }?>
		
		<?php if (!empty($permisos['alertas_index'])){ ?>
		<li>
			<a title="Alertas" href="<?php echo base_url('index.php');?>/alertas" class="">
				<i class="fa fa-map-marker"></i>
				<span class="hidden-xs">Alertas</span>
			</a>
		</li>
		<?php }?>
				
	</ul>
</div>