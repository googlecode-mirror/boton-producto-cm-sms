<?php

class Users extends MY_Controller  {
	
	function __construct()
	{
		parent::__construct();
		$this->check_is_logged();
		$this->load->model('users_model');
		$this->load->library('form_validation');
		$this->load->library('pagination');
		$this->load->helper('security');
		$this->load->helper('form');
		$data['title'] = 'Usuarios';
		
	}
	
	public function index($offset = 0 )
	{
		// Paginado
		$config['base_url'] = base_url().'index.php/users/index';
		$config['total_rows'] = $this->users_model->count();
		$config['per_page'] = 20;
		$config['num_links'] = 5;
		$this->pagination->initialize($config);
		
		// Obtengo los registros para la pagina actual
		$data['users'] = $this->users_model->get_users($config['per_page'], $this->uri->segment(3));
				
		$userLogged = $this->session->userdata('logged_user');
		$data['permisos'] = $userLogged['permisos'];
		
		$data['msg'] = $this->session->flashdata('msg');
		$data['error'] = $this->session->flashdata('error');
		
		$this->load->template('users/users_view.php', $data);
	}
	
	public function add()
	{
		if ($this->input->post('sendform') == 1)
			$this->save();
		else
		{
			$data['title'] = 'Alta de Usuario';
			$data['roles'] = $this->users_model->get_roles();
			$this->load->template('users/users_form_view.php', $data);
		}
	}
	
	public function update($id)
	{
		
		$data['title'] = 'Edici&oacute;n de Usuario';
		$data['user'] = $this->users_model->get_user($id);
		$data['roles'] = $this->users_model->get_roles();
		$this->load->template('/users/users_form_view.php', $data);
	}
	
	private function save()
	{
		$userId = $this->input->post('id');
		if (empty($id))
			$id = null;
		
		$this->_validations_save();
		$this->_validations_messages();
		
		$user = array(
			'id' => $id,
			'username' => $this->input->post('username'),
			'password' => MD5($this->input->post('password')),
			'last_login' => $this->input->post('last_login'),
			'mail' => $this->input->post('mail')
		);
		$data['user'] = $user;
		
		
							
		if ($this->form_validation->run() == FALSE)
		{
			if (!empty($userId))
				$data['title'] = 'Edici&oacute;n de Usuario';
			$data['title'] = 'Alta de Usuario';
			$data['roles'] = $this->users_model->get_roles();
			$this->load->template('users/users_form_view.php', $data);
		}
		else
		{
			$lastIdInserted = $this->users_model->save('users', $user);
			if ($lastIdInserted >= 0)
			{
				if (!empty($userId))
					$this->users_model->delete('user_roles', array("userID"=>$userId));
				else
					$userId = $lastIdInserted;
				$user_roles = array();
				foreach ($this->input->post('roles') as $rol)
				{
					$user_roles[] = array(
							'userID' => $userId,
							'roleID' => $rol,
							'addDate' => date('Y-m-d H:i:s')
					);
				}
				$this->users_model->insertUserRoles($user_roles, 'user_roles');
				if (empty($userId))
					$this->session->set_flashdata('msg', 'El usuario fue agregado correctamente');
				else 
					$this->session->set_flashdata('msg', 'El usuario fue editado correctamente');
				redirect('users');
			}
			else 
			{
				$data['error'] = "Error al grabar la informaci&oacute;n";
				$this->load->template('/users/users_form_view.php', $data);
			}
		}
		
	}
	
	public function delete($id){
		$this->users_model->delete('users', array("id"=>$id));
		$this->users_model->delete('user_roles', array("userID"=>$id));
		$this->users_model->delete('user_perms', array("userID"=>$id));
		
		$this->session->set_flashdata('msg', 'El usuario ha sido borrado.');
		redirect('users');
	}
	
	public function permisos($id)
	{
		$todosLosPermisos = $this->users_model->get_permisos();
		$data['permisos'] = $todosLosPermisos; 
		if ($this->input->post('sendform') == 1)
		{
			$this->savePermisos($id, $todosLosPermisos);
		}
		$permisos_personalizados = $this->users_model->get_permisos_user($id);
		$permisos_roles = $this->users_model->get_permisos_roles_activos_user($id);
		$data['permisos_activos'] = array_merge($permisos_roles, $permisos_personalizados);
		$data['user'] = $this->users_model->get_user($id);
		$this->load->template('users/users_permisos_view.php', $data);
		
	}
	
	private function savePermisos($userId, $todosLosPermisos)
	{
		$permisosArray = $this->input->post('permisosArray');
		$permisosArrayIndex = array();
		foreach ($permisosArray as $permiso){
			$permisosArrayIndex[$permiso] = 1;
		}
		//TODO: transaccion.
		$this->users_model->delete('user_perms', array("id"=>$userId));
		foreach ($todosLosPermisos as $permiso){
			if (isset($permisosArrayIndex[$permiso->ID]))
				$this->users_model->insertPermiso($userId, $permiso->ID, 1);
			else 
				$this->users_model->insertPermiso($userId, $permiso->ID, 0);
		}
		$this->session->set_flashdata('msg', 'Los permisos fueron modificados.');
		redirect('users');
	}
	
	public function roles()
	{
		if ($this->input->post('sendform') == 1)
		{
			$this->saveRoles();
		}
		// Obtengo los registros para la pagina actual
		$data['permisos'] = $this->users_model->get_permisos();
		$data['roles_permisos'] = $this->users_model->get_roles_permisos_activos();
		$data['roles'] = $this->users_model->get_roles();
		$this->load->template('users/users_roles_view.php', $data);
	}
	
	private function saveRoles()
	{
		$rolesPermisosArray = $this->input->post('rolesPermisosArray');
		//TODO: transaccion.
		$this->users_model->truncate('role_perms');
		foreach ($rolesPermisosArray as $rolPermiso){
			$rolPermisoArray = explode('-',$rolPermiso);
			$this->users_model->insertRolesPermisos($rolPermisoArray[0], $rolPermisoArray[1]);
		}
		$this->session->set_flashdata('msg', 'Los roles fueron modificados.');
		redirect('users');
	}
	
	public function account()
	{
		if ($this->input->post('sendform') == 1)
		{
			$this->_validations_account();
			$this->_validations_messages();
			//TODO: validar el pass anterior correcto con callback
			
			$user = array(
					'id' => $this->input->post('id'),
					'username' => $this->input->post('username'),
					'password' => $this->input->post('password'),
					'mail' => $this->input->post('mail')
			);
			
			$data['user'] = $user;
			
			if ($this->form_validation->run() == FALSE)
			{
				$data['error'] = "Error, verifique los datos";
				$this->load->template('/users/users_account_view.php', $data);
			}
			else
			{
				if ($this->_validate_password($this->input->post('password_old')))
				{
					if ($this->users_model->insert($user))
					{
						$this->session->set_flashdata('msg', 'Los datos de la cuenta fueron editados correctamente.');
						redirect('users'); 
					}
					else
					{
						$data['error'] = "Error, verifique los datos";
						$this->load->template('/users/users_account_view.php', $data);
					}
				}
				else
				{
					$data['error'] = "Error, la contraseña actual es incorrecta.";
					$this->load->template('/users/users_account_view.php', $data);
				}
					
			}
		}
		else 
		{
			$data['user'] = $this->session->userdata('logged_user');
			$data['title'] = 'Mi cuenta';
			$this->load->template('/users/users_account_view', $data);
		}
	}
	
	function reenviar_password()
	{
		//TODO: Ver como es mejor hacer esto, mandarla por mail o mandar un link para cambiarla directamente.	
	}
	
	function _validations_save()
	{
		//$this->form_validation->set_error_delimiters('<div class="bg-danger">', '</div>');
		$this->form_validation->set_rules('username', 'Usuario',  'trim|required|min_length[5]|max_length[12]|xss_clean');
		$this->form_validation->set_rules('mail', 'E-Mail', 'trim|required|valid_email');
		$this->form_validation->set_rules('password', 'Contraseña', 'trim|required|matches[passconf]');
		$this->form_validation->set_rules('passconf', 'Confirmación de contraseña', 'trim|required');

	}
	
	function _validations_account()
	{
		$this->form_validation->set_rules('username', 'Usuario',  'trim|required|min_length[5]|max_length[12]|xss_clean');
		$this->form_validation->set_rules('mail', 'E-Mail', 'trim|required|valid_email');
		$this->form_validation->set_rules('password_old', 'Contraseña anterior', 'trim|required|md5');
		$this->form_validation->set_rules('password', 'Contraseña', 'trim|required|matches[passconf]|md5');
		$this->form_validation->set_rules('passconf', 'Confirmación de contraseña', 'trim|required');
	}
	
	function _validations_messages()
	{
		$this->form_validation->set_message('required', 'El campo "%s" es obligatorio');
		$this->form_validation->set_message('min_length', 'El Campo "%s" debe tener un M&iacute;nimo de %d Caracteres');
		$this->form_validation->set_message('max_length', 'El Campo "%s" debe tener un M&aacute;ximo de %d Caracteres');
		$this->form_validation->set_message('matches', 'El confirmaci&oacute;n de la Contraseña no es correcta');
		$this->form_validation->set_message('valid_email', 'El E-Mail no tiene un formato v&aacute;lido');
	}
	
	function _validate_password($pass)
	{
		$id = $this->input->post('id');
		return $this->users_model->validate_password($id, $pass);
	}
	
		
}