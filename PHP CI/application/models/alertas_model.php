<?php

if ( ! defined('BASEPATH'))
	exit('No direct script access allowed');


class Alertas_model extends MY_Model {

	public function __construct()
	{
		parent::__construct();
		//$this->output->enable_profiler(TRUE);
	}

	public function get($id)
	{
		return parent::get('alertas', $id);
	}
	
	public function find($criteria = array(), $n = NULL, $offset = NULL)
	{
		$this->db->select('*, A.id as idAlerta, P.id as idPersona');
    	$this->db->from('alertas as A');
    	$this->db->join('personas as P', 'A.usuario_id = P.imei');
    	
    	$where = $this->_get_where($criteria);
    	
    	if ( ! empty($where))
    		$this->db->where($where, NULL, FALSE);
    	if ( ! empty($n)  )
    		$this->db->limit($n, $offset);
    	if ( ! empty($orderby)) {
    		$this->db->order_by($orderby, $order);
    	}else{
    		//default
    		$this->db->order_by("A.id", "DESC");
    	}
    
    	$query = $this->db->get();
    
    	if($query -> num_rows() > 0)
    		return $query->result();
    	else
    		return array();
	}
	
	
	public function findDetail($idAlerta)
	{
		$this->db->select('A.boton_id, A.lat, A.lng, A.fecha_hora, A.locationProvider, A.accuracy, A.atendido, A.id as idAlerta, P.id as idPersona, P.nombre, P.mail, P.telefono');
		$this->db->from('alertas as A');
		$this->db->join('personas as P', 'A.usuario_id = P.imei');
		$this->db->where('A.id', $idAlerta);
		
		$query = $this->db->get();
	
		if($query -> num_rows() > 0){
    		$result_array = $query->result_array();
			return $result_array[0];
    	}
    	else
    		return array();
	}
	
	
	public function count($criteria = array())
	{
		$where = $this->_get_where($criteria);
		return parent::count('alertas', $where);
	}
	
	function delete($conditions)
	{
		return parent::delete('alertas', $conditions);
	}
	
	function save($datos)
	{
		return parent::save('alertas', $datos);
	}
	
	function _get_where($criteria)
	{
		$where = '1 = 1';
		if ( isset($criteria['searchtext']) ){
			$where .= " AND id LIKE '%".$criteria['searchtext']."%' ";
		}
		if ( isset($criteria['atendido']) ){
			$where .= " AND atendido = ".$criteria['atendido']." ";
		}
		if ( isset($criteria['borrado']) ){
			$where .= " AND borrado = ".$criteria['borrado']." ";
		}
		return $where;
	}
	
	
}