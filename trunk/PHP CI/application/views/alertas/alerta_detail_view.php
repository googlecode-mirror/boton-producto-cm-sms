<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />
	<script type="text/javascript" charset="UTF-8" src="http://api.maps.nokia.com/2.2.0//jsl.js?with=all"></script>
    
    <!-- Custom JS & CSS -->
    <link href="<?php echo base_url();?>assets/css/custom-styles.css" rel="stylesheet">
        
	<script type="text/javascript">

		function getFormatedDayTime(value){
			return value.substring(8,10) +"/"+ value.substring(5,7) +"/"+ value.substring(0,4) + " " +value.substring(11) + " hs";
		}

		$(function() {	
			nokia.Settings.set("appId", "URXZdgxbeucuoNFFV0Yk"); 
			nokia.Settings.set("authenticationToken", "Vu6n1j_yDQ2XLQaXU3RKjw");

			// We create a new instance of InfoBubbles bound to a variable so we can call it later on
			var infoBubbles = new nokia.maps.map.component.InfoBubbles();

			//Obtenemos la posicion Central para el MAPA, hoy Buenos Aires, ma�ana Laprida
			latc=  <?php echo(str_replace(",", ".", $alerta["lat"]));?>;	
			lngc=  <?php echo(str_replace(",", ".", $alerta["lng"]));?>;			
			
			var mapContainer = document.getElementById("map");
			map = new nokia.maps.map.Display(mapContainer, {
				center: [latc,lngc],
				zoomLevel: 15,
				components:[new nokia.maps.map.component.Behavior(), infoBubbles]
			});
			
			
			<?php 
				if ($alerta["boton_id"]==1){?>
					var icon = "<?php echo base_url();?>/assets/img/markers/bomberos_icon_map.png";
			<?php
				}else if ($alerta["boton_id"]==2){?>
					var icon = "<?php echo base_url();?>/assets/img/markers/medico_icon_map.png";
			<?php
				}else{?>
					var icon = "<?php echo base_url();?>/assets/img/markers/policia_icon_map.png";
			<?php
				}
			?>
				
			var icon_marker = new nokia.maps.gfx.BitmapImage(icon, null, 32, 37, 0, 0);
			
			//Create marker						
			var marker = new nokia.maps.map.Marker([parseFloat(latc),parseFloat(lngc)],{
					icon: icon_marker,
					anchor: new nokia.maps.util.Point(18, 32)
			});						
			
			// Agrego el marker al mapa
			map.objects.add(marker);
			
			// La presicion nunca es mayor a 150 metros
			var presicion = <?php echo($alerta["accuracy"]);?>;
			if (parseFloat(presicion) > 150){
				presicion = "150";
			}
			var circle = new nokia.maps.map.Circle([latc, lngc],							
					presicion,{
						  pen: {
							  strokeColor: "#A4A4A4", 
							  lineWidth: 2
						  },
						  brush: {
							  color: "#C22A"
						  }
					  });
			map.objects.add(circle);
			
			
		});
		
	</script>

	
<!--Start Content-->
<div id="content" class="col-xs-12 col-sm-10">

	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<a href="#" class="show-sidebar"><i class="fa fa-bars"></i></a>
			<ol class="breadcrumb">
				<li><a href="#" onclick="window.location.href = '../home'">Inicio</a></li>
				<li>Detalle de Alerta</li>
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
					<span>Datos</span>
				</div>				
				<div class="no-move"></div>
			</div>
			<div id="map-2" class="box-content olMap" style="height: 450px;">
				<div id='improved'>
				    <div>
				        <span id="datos_header" class='head-acc'>Datos del Vecino</span>
				        <div class="content-acc" style="height:390px; display:block">
							<div class="pr" style="padding-top:10px;">
								<div id="circle_photo" class="circle_photo" 
									 style="background-image: url('<?php echo base_url();?>/assets/img/no_profile_pic.png');" >
								</div>
							</div>
							
							<?php 
								if ($alerta["boton_id"]==1){?>
									<div id="alerta_Bomberos" class="alerta_texto color_red" >Alerta de Bomberos!!! (Tel: 100)</div>
							<?php
								}else if ($alerta["boton_id"]==2){?>
									<div id="alerta_Medico" class="alerta_texto color_green" >Alerta de Emergencia M&eacute;dica!!! (Tel: 120)</div>
							<?php
								}else{?>
									<div id="alerta_Policia" class="alerta_texto color_blue" >Alerta de Policia!!! (Tel: 101)</div>
							<?php
								}
							?>
			
							
							
							
							
							<div style="height:160px;">
								<div class="col-xs-12 col-sm-12">
				                    <div class="form-group">
									    <div class="input-group input-group">
										<span class="input-group-addon">
										    <span class="fui-user"></span>
										</span>
										<input class="form-control" id="nomyape" type="text" name="nomyape" value="<?php echo($alerta["nombre"]);?>"/>
									    </div>
									</div>
									<div class="form-group">
									    <div class="input-group input-group">
										<span class="input-group-addon">
										    <span class="fui-mail"></span>
										</span>
										<input class="form-control" id="email" type="text" name="email" value="<?php echo($alerta["mail"]);?>"/>
									    </div>
									</div>
									<div class="form-group">
									    <div class="input-group input-group">
										<span class="input-group-addon">
										    <span class="fui-clip"></span>
										</span>
										<input class="form-control" id="tel" type="text" name="tel" value="<?php echo($alerta["telefono"]);?>"/>
									    </div>
									</div>                             
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