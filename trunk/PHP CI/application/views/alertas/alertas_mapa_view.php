<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />
	<script type="text/javascript" charset="UTF-8" src="http://api.maps.nokia.com/2.2.0//jsl.js?with=all"></script>
       
    <script src="<?php echo base_url();?>assets/js/ion.sound.js"></script>
    
    <!-- Custom JS & CSS -->
    <script src="<?php echo base_url();?>assets/js/custom-functions.js"></script>
    <link href="<?php echo base_url();?>assets/css/custom-styles.css" rel="stylesheet">
        
	<script type="text/javascript">
		$(document).ready(function(){
			//silencio();			
			$('#lista_header').each(function(){
				  var $content = $(this).closest('div').find('.content-acc');
				  $(this).click(function(e){
					  e.preventDefault();
					  $content.not(':animated').slideToggle();
					  if ($('#user_data').css("display")=='block'){
						  $('#user_data').slideToggle();
					  }
				  });
			});
			$('#datos_header').each(function(){
				  var $content = $(this).closest('div').find('.content-acc');
				  $(this).click(function(e){
					  e.preventDefault();
					  $content.not(':animated').slideToggle();
					  if ($('#lista_alertas').css("display")=='block'){
						  $('#lista_alertas').slideToggle();
					  }
				  });
			});
		});	
		
		//agrego la funcionalidad para que con la tecla ESC maneje el fullscreen
		$(window).bind('keydown', function(e){			
			if (e.keyCode==27 && fullscreen){
				menos();
				fullscreen = !fullscreen;
			}else if (e.keyCode==27 && !fullscreen){
				mas();		
				fullscreen = !fullscreen;
			}	
			window.scrollTo(0, 0);
		});
	</script>

	
	<input type="hidden" id="alerta_activa" value="" />
	
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

	<div class="row">
	<div class="col-xs-12 col-sm-8">
		<div class="box ui-draggable ui-droppable">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-map-marker"></i>
					<span>Mapa</span>
				</div>
				<div class="box-icons">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
					<a class="expand-link">
						<i class="fa fa-expand"></i>
					</a>
					<a class="close-link">
						<i class="fa fa-times"></i>
					</a>
				</div>
				<div class="no-move"></div>
			</div>
			<div id="map-1" class="box-content olMap" style="height: 450px;">
				<div id="map" style="width: 100%; height: 100%"></div>
			</div>
	
		</div>
	</div>
	<div class="col-xs-12 col-sm-4">
		<div class="box ui-draggable ui-droppable">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-warning"></i>
					<span>Alertas + Datos</span>
				</div>
				<div class="box-icons">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
					<a class="expand-link">
						<i class="fa fa-expand"></i>
					</a>
					<a class="close-link">
						<i class="fa fa-times"></i>
					</a>
				</div>
				<div class="no-move"></div>
			</div>
			<div id="map-2" class="box-content olMap" style="height: 450px;">
				<div id='improved'>
				    <div>
				        <span id="lista_header" class='head-acc'>Lista de Alertas</span>
				        <div id="lista_alertas" class="content-acc">
						 	<div style="text-align: center"> No hay alertas sin atender actualmente... </div>
					    </div>
				    </div>
				    <div>
				        <span id="datos_header" class='head-acc'>Datos del Vecino</span>
				        <div id="user_data" class="content-acc" style="height:390px;">
							<div class="pr" style="padding-top:10px;">
								<div id="circle_photo" class="circle_photo" 
									 style="background-image: url('../../assets/img/no_profile_pic.png');" >
								</div>
							</div>
							<div id="alerta_Policia" class="alerta_texto color_blue" style="display:none;">Alerta de Policia!!! (Tel: 101)</div>
							<div id="alerta_Bomberos" class="alerta_texto color_red" style="display:none;">Alerta de Bomberos!!! (Tel: 100)</div>
							<div id="alerta_Medico" class="alerta_texto color_green" style="display:none;">Alerta de Emergencia M&eacute;dica!!! (Tel: 120)</div>
							<div style="height:160px;">
								<div class="col-xs-12 col-sm-12">
				                    <div class="form-group">
									    <div class="input-group input-group">
										<span class="input-group-addon">
										    <span class="fui-user"></span>
										</span>
										<input class="form-control" id="nomyape" type="text" name="nomyape">
									    </div>
									</div>
									<div class="form-group">
									    <div class="input-group input-group">
										<span class="input-group-addon">
										    <span class="fui-mail"></span>
										</span>
										<input class="form-control" id="email" type="text" name="email">
									    </div>
									</div>
									<div class="form-group">
									    <div class="input-group input-group">
										<span class="input-group-addon">
										    <span class="fui-clip"></span>
										</span>
										<input class="form-control" id="tel" type="text" name="tel">
									    </div>
									</div>                             
				               </div>
							</div>
							<div>
								<div class="atendido" onclick="atenderAlerta()">
									Atender Alerta!
								</div>
							</div>
						</div>
				    </div>
				</div>
			</div>
		</div>
	</div>
</div>
	
</div><!--End Content-->