<?php

if ( ! defined('BASEPATH'))
	exit('No direct script access allowed');


class Personas_model extends MY_Model {

	public function __construct()
	{
		parent::__construct();
		//$this->output->enable_profiler(TRUE);
	}

	public function get($id)
	{
		return parent::get('personas', $id);
	}
	
	/**
	 * Obtine una persona por IMEI
	 * @param $imei de la persona
	 * @return Un array con el registro de la persona
	 */
	public function get_persona_by_imei($imei){
		$this->db->select('nombre, mail, telefono, id');
		$this->db->from('personas');
		$this->db->where('imei', $imei);
		$this->db->limit(1);
	
		$query = $this->db->get();
	
		if($query->num_rows() == 1)
		{
			$result_array = $query->result_array();
			return $result_array[0];
		}
		return null;
	}
	public function existeImei($imei)
	{
		$usuario = $this->get_persona_by_imei($imei);
		return  (!empty($usuario));
	}
	
	public function find($criteria = array(), $n = NULL, $offset = NULL)
	{
		$where = $this->_get_where($criteria);
		$orderby = parent::_get_orderby($criteria, 'nombre');
		$order = parent::_get_order($criteria, 'ASC');
		return parent::find('personas', $where, $orderby, $order, $n, $offset);
	}
	
	public function count($criteria = array())
	{
		$where = $this->_get_where($criteria);
		return parent::count('personas', $where);
	}
	
	function delete($conditions)
	{
		return parent::delete('personas', $conditions);
	}
	
	function save($datos)
	{
		return parent::save('personas', $datos);
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