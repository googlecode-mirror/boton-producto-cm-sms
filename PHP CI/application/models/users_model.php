<?php

if ( ! defined('BASEPATH'))
	exit('No direct script access allowed');


class Users_model extends MY_Model {

	public function __construct()
	{
		parent::__construct();
		//$this->output->enable_profiler(TRUE);
	}
	
	// LOGIN 
	public function login($user, $pass){
		$this->db->select('id, username, password, last_login, mail');
		$this->db->from('users');
		$this->db->where('username', $user);
		$this->db->where('password', MD5($pass));
		$this->db->limit(1);
		
		$query = $this->db->get();
		
		if($query -> num_rows() == 1)
			return $query->row(0);
		else
			return false;
	}
	
	public function set_last_login($id){
		$this->db->set("last_login", date('Y-m-d H:i:s'));
		$this->db->where("id", $id);
		$this->db->update("users");
	}
	
	public function validate_password($id, $pass)
	{
		$this->db->select('password');
		$this->db->from('users');
		$this->db->where('id', $id);
		$query = $this->db->get();
	
		$user = $query->row(0);
		if ($user != NULL && $user->password === $pass)
			return TRUE;
		else
			return FALSE;
	}
	
	// USER
	/**
	 * Obtine un usuario por ID con sus roles asignados
	 * @param $id del usuario
	 * @return Un array con el registro del usuario
	 */
	public function get_user($id){
		$this->db->select('id, username, password, last_login, mail');
		$this->db->from('users');
		$this->db->where('id', $id);
		$this->db->limit(1);
	
		$query = $this->db->get();
	
		if($query->num_rows() == 1)
		{
			$result_array = $query->result_array(); 
			$result_array[0]['roles'] = $this->get_user_roles($result_array[0]['id']);
			return $result_array[0];
		}
		return null;
	}
	
	/**
	 * Deveulve todos los usuarios con sus roles
	 * @param unknown_type $n
	 * @param unknown_type $offset
	 * @return multitype:Ambigous <Un, boolean, unknown>
	 */
	public function get_users(){
		$this->db->select('*');
		$query = $this->db->get('users');
		$users = $query->result_array();
		$result = array();
		foreach ($users as $user){
			$roles = $this->get_user_roles($user['id']);
			$user['roles'] = $roles; 
			$result[] = $user;
		}
		return $result;
	}
	
	public function insert($user){
		if (! empty($user['id'])){
			$this->db->set("username", $user['username']);
			$this->db->set("password", $user['password']);
			$this->db->set("mail", $user['mail']);
			$this->db->where("id", $user['id']);
			return $this->db->update("users");
		}
		else
			return $this->db->insert('users',$user);
	}
	
	public function count($criteria = array())
	{
		$where = $this->_get_where($criteria);
		return parent::count('users', $where);
	}
	
	// PERMISOS
	/**
	 * Retorna la lista completa de permisos
	 * @return una lista de objetos permisos
	 */
	public function get_permisos(){
		$this->db->select('*');
		$this->db->from('perm_data as PD');
		$this->db->where('PD.visible', 1);
		$this->db->order_by('nroSeccion', 'ASC');

		$query = $this->db->get();
	
		if($query -> num_rows() > 0)
			return $query->result();
		return null;
	}
	
	/**
	 * Obtiene los permisos individuales del usuario "user_perms"
	 * @param  $id usuario
	 * @return 
	 */
	public function get_permisos_user($id){
		$this->db->select('PD.*, UP.userID, UP.value');
		$this->db->from('perm_data as PD');
		$this->db->join('user_perms as UP', 'PD.ID = UP.permID', 'left');
		$this->db->where('UP.userID', $id);
		//$this->db->where('UP.value', 1);
	
		$query = $this->db->get();
	
		$result = array();
		foreach ($query->result_array() as $row)
		{
		   if ($row['value'] == 1)
		   	$result[$row['permKey']] = 1;
		   else
		   	$result[$row['permKey']] = 0;
		}
		return $result;
	}
	
	public function insertPermiso($userId, $permisoId, $value = 1){
		$permiso = array(
				'userID' => $userId,
				'permID' => $permisoId,
				'value' => $value,
				'addDate' => date('Y-m-d H:i:s')
		);
		
		$this->db->insert('user_perms', $permiso);
	}
	
	// ROLES
	/**
	 * Obtiene una lista completa de los roles.
	 * @return boolean
	 */
	public function get_roles(){
		$this->db->select('*');
		$this->db->from('role_data as RD');
		
		$query = $this->db->get();
	
		if($query -> num_rows() > 0)
			return $query->result();
		else
			return false;
	}
	
	/**
	 * Obtiene los permisos de los roles activos del usuario "user_roles"
	 * @param  $id usuario
	 * @return un array con el permKey como clave y valor 1 para todos.(no importa el valor)
	 */
	public function get_permisos_roles_activos_user($id){
		//SELECT * FROM role_perms as RP inner join user_roles as UR on RP.roleID=UR.roleID   WHERE UR.userID = '34'
		$this->db->select('*');
		$this->db->from('role_perms as RP');
		$this->db->join('user_roles as UR', 'RP.roleID=UR.roleID');
		$this->db->join('perm_data as PD', 'PD.ID=RP.permID');
		$this->db->where('UR.userID', $id);
		$this->db->where('RP.value', 1);
	
		$query = $this->db->get();
	
		$result = array();
		foreach ($query->result_array() as $row)
		{
			if ($row['value'] == 1)
				$result[$row['permKey']] = 1;
		}
		return $result;
	}
	
	public function get_roles_permisos_activos(){
		
		$this->db->select('*');
		$this->db->from('role_perms as RP');
		$this->db->join('role_data as RD', 'RD.ID=RP.roleID');
		$this->db->join('perm_data as PD', 'PD.ID=RP.permID');
		$this->db->where('RP.value', 1);

		$query = $this->db->get();
	
		$result = array();
		foreach ($query->result_array() as $row)
		{
		   	$result[$row['roleID'].'-'.$row['permID']] = 1;
		}
		return $result;
	}
	
	/**
	 * Obtiene los roles que tiene un usuario en particular, 
	 * es privada pq se usar solo en el modelo para completar la estructura devuelta por el get y el find.
	 * @param $idUser
	 * @return Un array con los NOMBRES de los roles.
	 */
	private function get_user_roles($idUser){
	
		$this->db->select('RD.roleName');
		$this->db->from('users as U');
		$this->db->join('user_roles as UR', 'U.ID=UR.userID');
		$this->db->join('role_data as RD', 'RD.ID=UR.roleID');
		
		$this->db->where('U.ID', $idUser);
	
		$query = $this->db->get();
	
		if($query -> num_rows() > 0){
			foreach ($query->result_array() as $rol){
				$result[] = $rol['roleName'];			
			}
			return $result;
		}
		else
			return false;
	}
	
	/**
	 * Esta se usa para grabar los permisos por roles desde la pantalla "Administrar Roles"
	 */
	public function insertRolesPermisos($rolId, $permisoId){
		$rolPermiso = array(
				'roleID' => $rolId,
				'permID' => $permisoId,
				'value' => '1',
				'addDate' => date('Y-m-d H:i:s')
		);
		$this->db->insert('role_perms', $rolPermiso);
	}
	
	/**
	 * Inserta los registros que asocian un rol con un usuario. 
	 * @param array de array los datos del registro "user_roles"
	 */
	public function insertUserRoles($user_roles){
		foreach ($user_roles as $user_rol)
			$this->db->insert('user_roles', $user_rol);
	}
	
	
	function _get_where($criteria)
	{
		$where = '1 = 1';
		if ( isset($criteria['searchtext']) )
		{
			$where .= " AND nombre LIKE '%".$criteria['searchtext']."%' ";
		}
		return $where;
	}
	
	
}