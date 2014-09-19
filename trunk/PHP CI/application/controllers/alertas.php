<?php

class Alertas extends MY_Controller {

	function __construct()
	{
		parent::__construct();
		$this->load->model('alertas_model');
		$this->load->model('personas_model');
		$this->load->library('form_validation');
		$this->load->library('pagination');
		$this->load->helper('security');
		$this->load->helper('form');
		$data['title'] = 'Alertas';
	}

	public function index($offset = 0 )
	{
		parent::check_is_logged();
		// Paginado - Inicializacion de parametros
		$criteria= $this->uri->uri_to_assoc(3); // pasa del segmento 3 en adelante a un array.
		array_pop($criteria); // Saca el ultimo segmento que es el paginado.
		$last = $this->uri->total_segments(); // Obtengo la pocision del ultimo segmento.
		$ultimo_elemento = $this->uri->segment($last);
		
		$itemsPerPage = $this->config->item('per_page');
		if (!empty($criteria['itemsPerPage']))
			$itemsPerPage = $criteria['itemsPerPage'];
		
		// Paginado - Configuracion		
		$config['first_url'] = base_url().'index.php/alertas/index/'.$this->uri->assoc_to_uri($criteria).'/0';
		$config['base_url'] = base_url().'index.php/alertas/index/'.$this->uri->assoc_to_uri($criteria);
		$config['total_rows'] = $this->alertas_model->count($criteria);
		$config['cur_page'] = $this->uri->segment($last);//CURRENT PAGE NUMBER -> fundamental para q funque sino se manbeaba con el criteria
		$config['per_page'] = $itemsPerPage;
		$config['num_links'] = 5;
		$config['last_link'] = '&Uacute;ltima';
		$config['first_link'] = 'Primera';
		$config['first_tag_open'] = $config['last_tag_open']= $config['next_tag_open']= $config['prev_tag_open'] = $config['num_tag_open'] = '<li>';
		$config['first_tag_close'] = $config['last_tag_close']= $config['next_tag_close']= $config['prev_tag_close'] = $config['num_tag_close'] = '</li>';
		$config['cur_tag_open'] = "<li><span><b>";
		$config['cur_tag_close'] = "</b></span></li>";
		
		$this->pagination->initialize($config);

		// Obtengo los registros para la pagina actual
		$data['alertas'] = $this->alertas_model->find($criteria, $itemsPerPage, $ultimo_elemento);
		$data['criteria'] = $criteria;
		$data['itemsPerPage'] = $itemsPerPage;
		$userLogged = $this->session->userdata('logged_user');
		$data['permisos'] = $userLogged['permisos'];
		$data['total_rows'] = $config['total_rows'];

		$data['msg'] = $this->session->flashdata('msg');
		$data['error'] = $this->session->flashdata('error');

		$this->load->template('alertas/alertas_view.php', $data);
	}
	
	/*
	 * Segun los parametros de busqueda y ordenamiento arma la URL y redicciona al index.
	*/
	public function search()
	{
		$orderby = $this->input->post('orderby');
		$order = $this->input->post('order');
		$searchtext = $this->input->post('searchtext');
		$itemsPerPage = $this->input->post('itemsPerPage');
	
		$criteria = "";
		if (!empty($orderby))
			$criteria .= "orderby/$orderby/";
		if (!empty($order))
			$criteria .= "order/$order/";
		if (!empty($searchtext))
			$criteria .= "searchtext/$searchtext/";
		if (!empty($itemsPerPage))
			$criteria .= "itemsPerPage/$itemsPerPage/";
		$criteria .= "0/";
	
		redirect('alertas/index/'.$criteria);
	}
	
	/*
	 * Segun los parametros de busqueda y ordenamiento arma la URL y redicciona al index.
	*/
	public function findUnattended()
	{
		$criteria["atendido"] = 0;
		$criteria["orderby"] = "fecha_hora";
		$criteria["itemsPerPage"] = "1000";
		$criteria["order"] = "DESC";	
	
		$alertas = $this->alertas_model->find($criteria);
		$json = array(); //para mostrar el json
		
		foreach($alertas as $alerta){
			$tipo_alerta = "";
			if ($alerta->boton_id == 1){
				$tipo_alerta = "Bomberos";
			}else if ($alerta->boton_id ==2){
				$tipo_alerta = "Medico";
			}else{
				$tipo_alerta = "Policia";
			}
		
			$json["loc".$alerta->usuario_id]= array(
					"info"=>"Alerta de $tipo_alerta",
					"id_boton"=>$alerta->boton_id,
					"tipo_alerta"=>$tipo_alerta,
					"user_id"=>$alerta->usuario_id,
					"fechahora"=>$alerta->fecha_hora,
					"fechahora_server"=>$alerta->fecha_hora_server,
					"provider"=>$alerta->locationProvider,
					"presicion"=>$alerta->accuracy,
					"alerta_id"=>$alerta->idAlerta,
					"lat"=>floatval(str_replace(',','.',$alerta->lat)),
					"lng"=>floatval(str_replace(',','.',$alerta->lng)));
		}		
		echo json_encode($json);
	}

	public function add()
	{		
		$criteria= $this->uri->uri_to_assoc(3);
		
		$imei = $criteria["usuario_id"];
		if ($this->personas_model->existeImei($imei)){
			
		}else{
			echo("BAD");
		}		
	}

	
	public function atender()
	{
		parent::check_is_logged();
		$idAlerta = $this->input->post('idAlerta');
		$valorAtendido = $this->input->post('valor');
				
		$result = $this->modificarAtendido($valorAtendido, $idAlerta);
		if ($result==1){
			echo("atendido::ok");
		}else{
			echo("atendido::bad");
		}
		exit;
	}
	
	private function modificarAtendido($valorAtendido, $idAlerta)
	{
		parent::check_is_logged();
		$alerta = array(
				"id"=>$idAlerta, 
				"atendido"=>$valorAtendido
		);
		$result = $this->alertas_model->save($alerta);
		return $result;
	}
	
	public function update($id)
	{
		parent::check_is_logged();
		$data['title'] = 'Edici&oacute;n de la alerta';
		$data['alerta'] = $this->alertas_model->get($id);
		$this->load->template('/alertas/alertas_form_view.php', $data);
	}

	private function save()
	{
		$id = $this->input->post('id');
		if (empty($id))
			$id = null;
		
		$this->_validations_save();
		$this->_validations_messages();

		$userLogged = $this->session->userdata('logged_user');
		
		$alerta= array(
				'id' => $id,
				'usuario_id' => $this->input->post('usuario_id'),
				'boton_id' => $this->input->post('boton_id'),
				'lat' => parent::getValue($this->input->post('lat')),
				'lng' => parent::getValue($this->input->post('lng')),
				'fecha_hora' => parent::getValue($this->input->post('fecha_hora')),
				'fecha_hora_server' => parent::getValue($this->input->post('fecha_hora_server')),
				'locationProvider' => parent::getValue($this->input->post('locationProvider')),
				'accuracy' => parent::getValue($this->input->post('accuracy')),
				'atendido' => $this->input->post('atendido'),
				'borrado' => $this->input->post('borrado')
		);
		$data['alerta'] = $alerta;

		if ($this->form_validation->run() == FALSE)
		{
			if (!empty($id))
				$data['title'] = 'Edici&oacute;n de la alerta';
			$data['title'] = 'Alta de la alerta';
			$this->load->template('alertas/alertas_form_view.php', $data);
		}
		else
		{
			$lastIdInserted = $this->alertas_model->save($alerta);
			if ($lastIdInserted >= 0)
			{
				if (empty($id))
					$this->session->set_flashdata('msg', 'El alerta fue agregada correctamente');
				else
					$this->session->set_flashdata('msg', 'El alerta fue editada correctamente');
				redirect('alertas');
			}
			else
			{
				$data['error'] = "Error al grabar la informaci&oacute;n";
				$this->load->template('/alertas/alertas_form_view.php', $data);
			}
		}
	}

	public function delete($id){
		parent::check_is_logged();
		$this->alertas_model->delete(array("id"=>$id));
		$this->session->set_flashdata('msg', 'El alerta ha sido borrada.');
		redirect('alertas');
	}

	public function mapa()
	{
		parent::check_is_logged();
		$data['title'] = 'Mapa de Alertas';
		$this->load->template('alertas/alertas_mapa_view.php', $data);
	}
	
	function _validations_save()
	{
		$this->form_validation->set_rules('nombre', 'Nombre',  'trim|required|min_length[4]|xss_clean');

	}

	function _validations_messages()
	{
		$this->form_validation->set_message('required', 'El campo "%s" es obligatorio');
		$this->form_validation->set_message('min_length', 'El Campo "%s" debe tener un M&iacute;nimo de %d Caracteres');
		$this->form_validation->set_message('max_length', 'El Campo "%s" debe tener un M&aacute;ximo de %d Caracteres');
		$this->form_validation->set_message('matches', 'El confirmaci&oacute;n de la Contrase&ntilde;a no es correcta');
		$this->form_validation->set_message('valid_email', 'El E-Mail no tiene un formato v&aacute;lido');
	}

	
}