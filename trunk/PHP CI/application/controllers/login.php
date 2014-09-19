<?php

class Login extends CI_Controller {
	
	function __construct()
	{
		parent::__construct();
		$this->load->model('users_model');
		$this->load->helper('form');
		$this->load->library('form_validation');
		$data['title'] = 'Login';
		date_default_timezone_set('Etc/GMT+3');
	}
	
	public function index()
	{
		$this->load->view('login/login_view.php');
	}
	
	public function verify()
	{
		$username = $this->input->post('username');
		$password = $this->input->post('password');
		
		$this->_validations();
		
		if($this->form_validation->run() == TRUE)
		{		
			$user = $this->users_model->login($username, $password);
		
			if($user)
			{
				//Obtengo los permisos del usuario logueado
				// Load the ACL library and pas it the config array
				$config = array('userID'=>$user->id);
				$this->load->library('acl',$config);
				$data['permisos'] = $this->acl->get_permisos_activos();
				$data['perms'] = $this->acl->buildACL();
				$sess_array = array(
					'id' => $user->id,
					'username' => $user->username,
					'mail' => $user->mail,
					'permisos' => $data['permisos'],
					'perms' => $data['perms']
						
				);
				$this->session->set_userdata('logged_user', $sess_array);
				$this->users_model->set_last_login($user->id);
				redirect('personas');
			}
			else
			{
				log_message('error', 'Login invalido.');
				$data["error"]="Usuario o Contrase&ntilde;a inv&aacutelida";
				$this->load->view('login/login_view.php',$data);
			}
		}
		else 
		{
			$this->load->view('login/login_view.php');
		}
	}
	
	function _validations()
	{
		// Añadimos las reglas necesarias.
		$this->form_validation->set_rules('username', 'Username', 'required');
		$this->form_validation->set_rules('password', 'Password', 'required');
		
		// Generamos el mensaje de error personalizado para la accion 'required'
		$this->form_validation->set_message('required', 'El campo %s es requerido.');
	}
	
	public function logout()
	{
		$this->session->sess_destroy();
		redirect('login');
	}
	
}