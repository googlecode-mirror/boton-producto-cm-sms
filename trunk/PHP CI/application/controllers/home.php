<?php

class Home extends CI_Controller {
	
	function __construct()
	{
		parent::__construct();
		$this->check_is_logged();
	}
	
	private function check_is_logged(){
		if(! $this->session->userdata('logged_user')){
			redirect('login');
		}
	}
	
	public function index()
	{
		$data['title'] = 'Administrador';
		$user = $this->session->userdata('logged_user');
		$data['username'] = $user['username'];
		$this->load->template('home_view.php', $data);
	}
	
	
	
}