
//variables globales
var map;
var dataInfo = {};
var locations = {};
var circles = {};
var hackLat = 0;
var fullscreen = false; //flag de pantalla completa
var customIcons = {
	  blue: { //policia
		icon: '../../assets/img/markers/policia_icon_map.png'
	  },
	  red: { //bomberos
		icon: '../../assets/img/markers/bomberos_icon_map.png',
	  },
	  green: { //medico
		icon: '../../assets/img/markers/medico_icon_map.png',
	  }
};


function mas(){			
	$("#map-container").toggleClass("map-container")
					   .toggleClass("fullscreen-map")
					   .removeClass("normalscreen-map");	
	fullscreen = true;
}
function menos(){
	$("#map-container").toggleClass("map-container")							   
					   .toggleClass("normalscreen-map")
					   .removeClass("fullscreen-map").scrollTop();
	fullscreen = false;
}

//preparo el plugin de sonido
$.ionSound({
    sounds: ["../../1"],
    path: "",
    multiPlay: true,
    volume: "1.0"
});

function reproducirAudioAlarma(){	
	$.ionSound.play("1");
}
 
function silenciarAudioAlarma(){
	$.ionSound.stop("1");
}
		


function fillUserDataInfo(user_id, flag_show_data){
	var userData = dataInfo[user_id];	
	var locationData = locations["loc"+user_id];
	jQuery("#user_data").css("display","");	
	jQuery(".alerta_texto").css("display","none");
	jQuery("#alerta_"+locationData.tipo_alerta).css("display","");
	
	jQuery("#nomyape").val(userData.nombre);
	jQuery("#email").val(userData.mail);
	jQuery("#tel").val(userData.telefono);
	
	if (flag_show_data){
		$("#alerta_activa").val(locationData.alerta_id);
		if ($('#user_data').css("display")=='none'){
		    $('#user_data').slideToggle();
		}
		if ($('#').css("display")=='block'){
		    $('#lista_alertas').slideToggle();
		}
	}
}

function atenderAlerta(){	
	var alerta_id = $("#alerta_activa").val();
	if (alerta_id != ''){
		silenciarAudioAlarma();
		var parameters = "idAlerta="+alerta_id+"&valor=1";
		jQuery.ajax({
			type: "POST",
			url: "../alertas/atender",
			data: 	parameters,
			success: function(html){
				if (html.indexOf(":ok")> 0){
					$("#alerta_activa").val("");
					alert("El alerta fue atendida, se ha enviado un SMS al vecino.");
				}else{
					alert("Hay un problema con el servidor, intentelo nuevamente.");
				}
				
			}
		});
	}else{
		alert("Seleccione una alarma para poder atender.");
	}
}


function atenderAlertaAjax(alerta_id){	
	if (alerta_id != ''){
		jQuery.ajax({
			type: "POST",
			url: "atender",
			data: 	"idAlerta="+alerta_id,
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

function addMarkerLinkToSidebar(loc){	
	
	jQuery.ajax({
		type: "POST",
		url: "../personas/getPersonaData/imei/"+loc.user_id,
		data: 	"",
		success: function(html){
			result = jQuery.parseJSON(html); //array con los datos del vecino
			
			//guardo los datos del vecino para mostrar cuando sean requeridos
			dataInfo[loc.user_id] = result;
			
			//armo el div con los datos del alerta para agregar a la lista
			var color = (loc.tipo_alerta=="Bomberos")?"red":(loc.tipo_alerta=="Policia")?"blue":"green";	
			var icon = (loc.tipo_alerta=="Bomberos")?customIcons[color]:(loc.tipo_alerta=="Policia")?customIcons[color]:customIcons[color];
			var icon = icon.icon;
			var fechahora = getFormatedDayTime(loc.fechahora);
			var div_str = "<div class='alerta_item' id='mark_"+loc.user_id+"' onclick='mapGoTo(\""+loc.user_id+"\")'><img src='"+icon+"'/><div>"+result.nombre+" - "+fechahora+"</div></div>";
			$("#lista_alertas").append(div_str);
			if ($("#relleno")){
				$("#relleno").remove();
			}
		}
	});
}

function mapGoTo(user_id){
	var locationData = locations["loc"+user_id];
	var lat = locationData.lat;
	var lon = locationData.lng;
	var coord = new nokia.maps.geo.Coordinate(lat, lon);
	map.set("center", coord);
	fillUserDataInfo(user_id, true);
}

function getFormatedDayTime(value){
	return value.substring(8,10) +"/"+ value.substring(5,7) +"/"+ value.substring(0,4) + " " +value.substring(11) + " hs";
}

$(function() {	
	nokia.Settings.set("appId", "URXZdgxbeucuoNFFV0Yk"); 
	nokia.Settings.set("authenticationToken", "Vu6n1j_yDQ2XLQaXU3RKjw");

	// We create a new instance of InfoBubbles bound to a variable so we can call it later on
	var infoBubbles = new nokia.maps.map.component.InfoBubbles();

	//Obtenemos la posicion Central para el MAPA, hoy Buenos Aires, ma�ana Laprida
	latc=  -37.546135;	
	lngc=  -60.799315;			
	
	var mapContainer = document.getElementById("map");
	map = new nokia.maps.map.Display(mapContainer, {
		center: [latc,lngc],//centrado en Laprida
		zoomLevel: 14,
		components:[new nokia.maps.map.component.Behavior(), infoBubbles]
	});

	
	function setMarkers(locObj) {
		
		//primero itero los markers para saber si vino uno nuevo a la vuelta anterior
		$.each(locObj, function(key, loc) {
			if(!locations[key]){
				reproducirAudioAlarma(); //como hay uno nuevo, reproduzco el sonido de alarma
			}
		});
		
		//ahora saco del mapa las alertas anteriores y limpio mi array de locations
		$.each(locations, function(key) {
			locations[key].marker.destroy();
			delete locations[key];
		});
		
		//ahora saco del mapa los circulos de presicion que haya dibujado antes
		$.each(circles, function(key) {
			circles[key].destroy();
			delete circles[key];
		});
		
		//ahora limpio la lista de alertas para crear una lista con las nuevas que vinieron
		var divHeight = $("#lista_alertas").css("height") - 10;
		$("#lista_alertas").html("<div id='relleno' style='height:"+divHeight+"'></div>");
		
		//por cada alerta que vino, dibujo un marker
		$.each(locObj, function(key, loc) {
									
			//segun el tipo de alerta dibujamos un icono distinto
			var color = (loc.tipo_alerta=="Bomberos")?"red":(loc.tipo_alerta=="Policia")?"blue":"green";	
			var icon = (loc.tipo_alerta=="Bomberos")?customIcons[color]:(loc.tipo_alerta=="Policia")?customIcons[color]:customIcons[color];						
			var icon_marker = new nokia.maps.gfx.BitmapImage(icon.icon, null, 32, 37, 0, 0);
			
			//Create marker						
			var marker = new nokia.maps.map.Marker([parseFloat(loc.lat),parseFloat(loc.lng)],{
					icon: icon_marker,
					anchor: new nokia.maps.util.Point(18, 32)
			});						

			//ac� agregamos la funcion de mostrar los datos del usuario cuando clickea sobre el marker
			var TOUCH = nokia.maps.dom.Page.browser.touch,
			CLICK = TOUCH ? "tap" : "click";
			marker.addListener(
				CLICK, 
				function (evt) {								
					fillUserDataInfo(loc.user_id, true);
				}
			);
			
			// Agrego el marker al mapa
			map.objects.add(marker);
			
			// La presicion nunca es mayor a 150 metros
			var presicion = loc.presicion;
			if (parseFloat(loc.presicion) > 150){
				presicion = "150";
			}
			var circle = new nokia.maps.map.Circle([loc.lat, loc.lng],							
					presicion,{
						  pen: {
							  strokeColor: "#A4A4A4", 
							  lineWidth: 2
						  },
						  brush: {
							  color: "#C22A"
						  }
					  });
			circles[key] = circle;
			map.objects.add(circle);
			

			//agregamos el marker al loc y lo metemos en locations
			loc["marker"] = marker;
			locations[key] = loc;
			
			//agregamos el marker a la lista de alertas
			addMarkerLinkToSidebar(loc);	
			reproducirAudioAlarma();
		});
	}

	var ajaxObj = {//Object to save cluttering the namespace.
		options: {
			url: "findUnattended",//The resource that delivers loc data.
			dataType: "json"//The type of data tp be returned by the server.
		},
		delay: 4000,//(milliseconds) the interval between successive gets.
		errorCount: 0,//running total of ajax errors.
		errorThreshold: 20,//the number of ajax errors beyond which the get cycle should cease.
		ticker: null,//setTimeout reference - allows the get cycle to be cancelled with clearTimeout(ajaxObj.ticker);
		get: function() { //a function which initiates 
			ajaxObj.ticker = setTimeout(getMarkerData, ajaxObj.delay);
		},
		fail: function(jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
			ajaxObj.errorCount++;
		}
	};

	//Ajax master routine
	function getMarkerData() {
		$.ajax(ajaxObj.options)
		  .done(setMarkers) //fires when ajax returns successfully
		  .fail(ajaxObj.fail) //fires when an ajax error occurs
		  .always(ajaxObj.get); //fires after ajax success or ajax error
	}

	//Create markers from the initial dataset served with the document.
	ajaxObj.get(); //Start the get cycle. 
	
	
});