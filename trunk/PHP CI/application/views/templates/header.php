<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Boton Alerta Admin</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="<?php echo base_url();?>assets/plugins/bootstrap/bootstrap.css" rel="stylesheet">
		<link href="<?php echo base_url();?>assets/plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet">
		<link href="<?php echo base_url();?>assets/css/font-awesome.css" rel="stylesheet">
		<link href='http://fonts.googleapis.com/css?family=Righteous' rel='stylesheet' type='text/css'>
		<link href="<?php echo base_url();?>assets/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
		<link href="<?php echo base_url();?>assets/plugins/fullcalendar/fullcalendar.css" rel="stylesheet">
		<link href="<?php echo base_url();?>assets/plugins/xcharts/xcharts.min.css" rel="stylesheet">
		<link href="<?php echo base_url();?>assets/plugins/select2/select2.css" rel="stylesheet">
		<link href="<?php echo base_url();?>assets/css/style.css" rel="stylesheet">
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
				<script src="http://getbootstrap.com/docs-assets/js/html5shiv.js"></script>
				<script src="http://getbootstrap.com/docs-assets/js/respond.min.js"></script>
		<![endif]-->
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<!--<script src="http://code.jquery.com/jquery.js"></script>-->
		<script src="<?php echo base_url();?>assets/plugins/jquery/jquery-2.1.0.min.js"></script>
		<script src="<?php echo base_url();?>assets/plugins/jquery-ui/jquery-ui.min.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="<?php echo base_url();?>assets/plugins/bootstrap/bootstrap.min.js"></script>
		<script src="<?php echo base_url();?>assets/plugins/justified-gallery/jquery.justifiedgallery.min.js"></script>
		<script src="<?php echo base_url();?>assets/plugins/tinymce/tinymce.min.js"></script>
		<script src="<?php echo base_url();?>assets/plugins/tinymce/jquery.tinymce.min.js"></script>
		<!-- All functions for this theme + document.ready processing -->
		<script src="<?php echo base_url();?>assets/js/devoops.js"></script>
	</head>
<body>
	<!--Start Header-->
	<div id="screensaver">
		<canvas id="canvas"></canvas>
		<i class="fa fa-lock" id="screen_unlock"></i>
	</div>
	
	<div id="modalbox">
		<div class="devoops-modal">
			<div class="devoops-modal-header">
				<div class="modal-header-name">
					<span>Basic table</span>
				</div>
				<div class="box-icons">
					<a class="close-link">
						<i class="fa fa-times"></i>
					</a>
				</div>
			</div>
			<div class="devoops-modal-inner">
			</div>
			<div class="devoops-modal-bottom">
			</div>
		</div>
	</div>
	
	<header class="navbar">
		<div class="container-fluid expanded-panel">
			<div class="row">
			
				<div id="top-panel" class="col-xs-12 col-sm-10" style="width:100%">
					<div class="row" style="padding-top:5px" >
						<div class="col-xs-8 col-sm-4">
							<div id="search">
								<img src="<?php echo base_url();?>assets/img/logo.png" width="265px" >
								<!-- <input type="text" placeholder="search"/>
								<i class="fa fa-search"></i> -->
							</div>
						</div>
						<div class="col-xs-4 col-sm-8 top-panel-right">
							<ul class="nav navbar-nav pull-right panel-menu">
								<li class="hidden-xs">
									<a href="index.html" class="modal-link">
										<i class="fa fa-bell"></i>
										<span class="badge">7</span>
									</a>
								</li>
								<li class="hidden-xs">
									<a class="ajax-link" href="ajax/calendar.html">
										<i class="fa fa-calendar"></i>
										<span class="badge">3</span>
									</a>
								</li>
								<li class="dropdown">
									<a href="#" class="dropdown-toggle account" data-toggle="dropdown">
										<div class="avatar">
											<img src="<?php echo base_url();?>assets/img/avatar.png" class="img-rounded" alt="avatar" />
										</div>
										<i class="fa fa-angle-down pull-right"></i>
										<div class="user-mini pull-right">
											<span class="welcome">Bienvenido,</span>
											<span>
												<?php 
												 	$user = $this->session->userdata('logged_user'); 
												 	echo $user['username']. "&nbsp;|&nbsp";
												 ?>
											</span>
										</div>
									</a>
									<ul class="dropdown-menu">
										<li>
											<span class="hidden-sm text"><i class="fa fa-cog"></i>&nbsp;<?php echo anchor('/users/account', 'Mi cuenta', 'title="Mi cuenta"');?></span>
										</li>
										<li>
											<span class="hidden-sm text"><i class="fa fa-power-off"></i>&nbsp;<?php echo anchor('/login/logout', 'Logout', 'title="Logout"');?></span>
										</li>
				 					</ul>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	
	<!--Start Container-->
	<div id="main" class="container-fluid">
		<div class="row">
			<?php $this->load->view('templates/menu'); ?>