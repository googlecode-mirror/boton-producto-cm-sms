<?php

class Personas extends MY_Controller {

	function __construct()
	{
		parent::__construct();
		$this->load->model('personas_model');
		$this->load->library('form_validation');
		$this->load->library('pagination');
		$this->load->helper('security');
		$this->load->helper('form');
		$data['title'] = 'Personas';
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
		$config['first_url'] = base_url().'index.php/personas/index/'.$this->uri->assoc_to_uri($criteria).'/0';
		$config['base_url'] = base_url().'index.php/personas/index/'.$this->uri->assoc_to_uri($criteria);
		$config['total_rows'] = $this->personas_model->count($criteria);
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
		$data['personas'] = $this->personas_model->find($criteria, $itemsPerPage, $ultimo_elemento);
		$data['criteria'] = $criteria;
		$data['itemsPerPage'] = $itemsPerPage;
		$userLogged = $this->session->userdata('logged_user');
		$data['permisos'] = $userLogged['permisos'];
		$data['total_rows'] = $config['total_rows'];

		$data['msg'] = $this->session->flashdata('msg');
		$data['error'] = $this->session->flashdata('error');

		$this->load->template('personas/personas_view.php', $data);
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
	
		redirect('personas/index/'.$criteria);
	}
	
	/*
	 * Segun los parametros de busqueda y ordenamiento arma la URL y redicciona al index.
	*/
	public function getPersonaData()
	{
		$imei = $this->input->post('imei');
	
		$usuario = $this->personas_model->get_persona_by_imei($imei);
		$json = array(
				"nombre"=> $usuario["nombre"],
				"mail"=> $usuario["mail"],
				"telefono"=> $usuario["telefono"]
		);
		echo json_encode($json);
	}

	
	public function add()
	{
		parent::check_is_logged();
		if ($this->input->post('sendform') == 1)
			$this->save();
		else
		{
			$data['title'] = 'Alta de la persona';
			$this->load->template('personas/personas_form_view.php', $data);
		}
	}
	/**
	 * servicio utilizado por el móvil para el alta de personas.
	 */
	public function addService(){
		$criteria= $this->uri->uri_to_assoc(3);
		
		$nombre = $criteria["nombreUsuario"];
		$mail = $criteria["email"];
		$imei = $criteria["s"];
		$telefono = $criteria["t"];
		
		$criteriaInsert["nombre"] = urldecode($nombre);
		$criteriaInsert["mail"] = urldecode($mail);
		$criteriaInsert["imei"] = $imei;
		$criteriaInsert["telefono"] = $telefono;
		$fecha_hora_server = date('Y-m-d H:i:s');
		$criteriaInsert["fecha"] = $fecha_hora_server;
		
		/** Si existe el imei lo piso*/
		if ($this->personas_model->existeImei($imei)){
			$usuario = $this->personas_model->get_persona_by_imei($imei);
			$criteriaInsert["id"] = $usuario["id"];
			$this->personas_model->save($criteriaInsert);
			echo("OK");
		}else{
			echo("BAD");
		}
			
	}
	/**
	 * servicio utilizado por el móvil para el alta de personas.
	 */
	public function updateService(){
		$criteria= $this->uri->uri_to_assoc(3);
	
		$nombre = $criteria["nombreUsuario"];
		$mail = $criteria["email"];
		$imei = $criteria["s"];
		$telefono = $criteria["t"];
			
		$criteriaInsert["nombre"] = urldecode($nombre);
		$criteriaInsert["mail"] = urldecode($mail);
		$criteriaInsert["telefono"] = $telefono;
		
		
		if ($this->personas_model->existeImei($imei)){ 
			$usuario = $this->personas_model->get_persona_by_imei($imei);
			$criteriaInsert["id"] = $usuario["id"];
			$criteriaInsert["imei"] = $imei;				
			$this->personas_model->save($criteriaInsert);
			echo("OK");
		}else{
			echo("BAD");
		}
			
	}
	public function update($id)
	{
		parent::check_is_logged();
		$data['title'] = 'Edici&oacute;n de la persona';
		$data['persona'] = $this->personas_model->get($id);
		$this->load->template('/personas/personas_form_view.php', $data);
	}

	private function save()
	{
		$id = $this->input->post('id');
		if (empty($id))
			$id = null;
		
		$this->_validations_save();
		$this->_validations_messages();

		$userLogged = $this->session->userdata('logged_user');
		
		$persona= array(
				'id' => $id,
				'nombre' => $this->input->post('nombre'),
				'mail' => $this->input->post('mail'),
				'telefono' => $this->input->post('telefono'),
				'ciudad' => $this->input->post('ciudad'),
				'active' => $this->input->post('v'),
				'fecha' => $this->input->post('fecha'),
				'imei' => $this->input->post('imei'),
				'borrado' => $this->input->post('borrado')
		);
		$data['persona'] = $persona;

		if ($this->form_validation->run() == FALSE)
		{
			if (!empty($id))
				$data['title'] = 'Edici&oacute;n de la persona';
			$data['title'] = 'Alta de la persona';
			$this->load->template('personas/personas_form_view.php', $data);
		}
		else
		{
			$lastIdInserted = $this->personas_model->save($persona);
			if ($lastIdInserted >= 0)
			{
				if (empty($id))
					$this->session->set_flashdata('msg', 'La persona fue agregada correctamente');
				else
					$this->session->set_flashdata('msg', 'La persona fue editada correctamente');
				redirect('personas');
			}
			else
			{
				$data['error'] = "Error al grabar la informaci&oacute;n";
				$this->load->template('/personas/personas_form_view.php', $data);
			}
		}

	}

	public function delete($id){
		parent::check_is_logged();
		$this->personas_model->delete(array("id"=>$id));
		$this->session->set_flashdata('msg', 'La persona ha sido borrado.');
		redirect('personas');
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