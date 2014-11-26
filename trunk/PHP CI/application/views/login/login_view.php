<html>

<head>
	<title><?php echo(APP_NAME);?></title>
	<link rel="stylesheet" href="<?php echo base_url();?>assets/css/login.css" type="text/css" media="screen"/>
	<link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet' type='text/css'>
</head>

<body>
	
	
	<div class="container" style="text-align:center">
		<div>
			<img width="100" src="<?php echo base_url();?>assets/img/alertar_logo_ch.png">
			<div style="font-family: 'Varela Round', sans-serif; font-size: 60px; color: #646364;">Alert-AR</div>
		</div>
		
		
		<br><br>
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
					<a href="javascript:alert('pilcrow / pilcrow');">Olvido su contrase&ntilde;a?</a>
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
				M&aacute;s info: <a href="http://alertar.pilcrow.com.ar" target="_blank">alertar.pilcrow.com.ar</a>
			</div><!-- button -->
			
		</section><!-- content -->
		
	</div><!-- container -->
	
</body>
</html>