<html>

<head>
	<title>Osteo Implants</title>
	<link rel="stylesheet" href="<?php echo base_url();?>assets/css/login.css" type="text/css" media="screen"/>
</head>

<body>
	
	
	<div class="container" style="text-align:center">
		<img width="320px" src="<?php echo base_url();?>assets/img/logo.png"><br><br>
		<section id="content">
		
			<?php echo form_open('login/verify'); ?>
				<h1>Iniciar Sesi&oacute;n</h1>
				<div>
					<input type="text" placeholder="Usuario" required="" id="username" name="username"  />
				</div>
				<div>
					<input type="password" placeholder="Contase&ntilde;a" required="" id="password" name="password" />
				</div>
				<div>
					<input type="submit" value="Ingresar" />
					<a href="#">Olvido su contrase&ntilde;a?</a>
				</div>
			<?php echo form_close(); ?>   
			
			<div style="height:35px">
				<?php 
					if (validation_errors()){
						echo "<div class='error'>".validation_errors()."</div>"; 
					}
					if (isset($error)){
					    echo "<div class='error'>$error</div>";
					}
				?>
				<br>
			</div>
			
			<div class="button">
				<a href="#">Pilcrow Admin</a>
			</div><!-- button -->
			
		</section><!-- content -->
		
	</div><!-- container -->
	
</body>
</html>