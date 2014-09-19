<?php

/**
 * /application/core/MY_Loader.php
 *
 */
class MY_Loader extends CI_Loader {
    public function template($template_name, $vars = array(), $return = FALSE)
    {
        $content  = $this->view('templates/header', $vars, $return);
        //$this->load->library('Menu.php');
        //$vars['menu'] =  $this->menu->render('items');
        //$content  = $this->view('templates/menu', $vars, $return);
        $content .= $this->view($template_name, $vars, $return);
        $content .= $this->view('templates/footer', $vars, $return);

        if ($return)
        {
            return $content;
        }
    }
}