-- phpMyAdmin SQL Dump
-- version 4.0.10.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 09, 2014 at 05:13 PM
-- Server version: 5.1.73-community
-- PHP Version: 5.3.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `botonalerta`
--

-- --------------------------------------------------------

--
-- Table structure for table `alertas`
--

CREATE TABLE IF NOT EXISTS `alertas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario_id` varchar(60) NOT NULL,
  `boton_id` varchar(80) NOT NULL,
  `lat` varchar(60) NOT NULL,
  `lng` varchar(60) NOT NULL,
  `fecha_hora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_hora_server` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `locationProvider` varchar(20) DEFAULT NULL,
  `accuracy` varchar(20) DEFAULT NULL,
  `atendido` tinyint(1) NOT NULL DEFAULT '0',
  `borrado` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=49 ;

--
-- Dumping data for table `alertas`
--

INSERT INTO `alertas` (`id`, `usuario_id`, `boton_id`, `lat`, `lng`, `fecha_hora`, `fecha_hora_server`, `locationProvider`, `accuracy`, `atendido`, `borrado`) VALUES
(21, '357143051186879', '3', '-37.544851', '-60.800241', '2014-06-17 15:00:48', '2014-06-17 14:57:08', 'network', '25.696', 1, 0),
(13, '353699043622689', '2', '-34.918564', '-57.924002', '2014-06-10 14:15:08', '2014-06-10 14:11:43', 'network', '45.0', 1, 0),
(22, '353989052498505', '1', '-37,543564', '-60,801552', '2014-06-20 22:05:31', '2014-06-20 22:05:01', 'network', '35.326', 1, 0),
(38, '000000000000000', '1', '-34,593227', '-58,399135', '2014-09-02 03:00:15', '2014-09-02 21:56:27', 'gps', '0.0', 1, 0),
(19, '356459041135037', '3', '-37.547157', '-60.800231', '2014-06-24 15:44:34', '2014-06-24 15:38:22', 'network', '2706.0', 1, 0),
(23, '355259051235391', '1', '-37,549133', '-60,804663', '2014-06-17 15:52:01', '2014-06-17 15:48:21', 'network', '2786.0', 1, 0),
(20, '356792040008536', '1', '-34.592402', '-58.398896', '2014-06-17 14:35:36', '2014-06-17 14:31:31', 'network', '758.0', 1, 0),
(24, '356459041135037', '3', '-37.549094', '-60.804672', '2014-06-24 15:44:34', '2014-06-24 15:38:22', 'network', '2706.0', 1, 0),
(42, '358306049856486', '2', '-34,59295', '-58,399587', '2014-09-02 21:39:37', '2014-09-02 21:48:22', 'gps', '35.0', 1, 0),
(48, '355694040219082', '3', '-34.626231', '-58.426084', '2014-09-04 01:15:41', '2014-09-04 01:11:39', 'network', '45.0', 1, 0),
(28, '353989050145330', '2', '-37,539568', '-60,797242', '2014-06-20 22:03:55', '2014-06-20 22:00:50', 'network', '55.5', 1, 0),
(29, '353989052498505', '1', '-38,718739', '-62,305347', '2014-06-20 22:05:31', '2014-06-20 22:05:01', 'network', '35.326', 1, 0),
(30, '356792040008536', '3', '-37.54522', '-60.795789', '2014-06-21 12:15:33', '2014-06-21 12:11:35', 'network', '2178.0', 1, 0),
(31, '356459041135037', '3', '-37.548911', '-60.804546', '2014-06-24 15:44:34', '2014-06-24 15:38:22', 'network', '2706.0', 1, 0),
(32, '352738060626198', '2', '-37,544983', '-60,800127', '2014-06-24 22:45:10', '2014-06-24 22:41:32', 'network', '48.0', 1, 0),
(33, '358922053054170', '3', '-37,545054', '-60,800196', '2014-06-24 22:46:59', '2014-06-24 22:42:41', 'network', '32.0', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `ci_sessions`
--

CREATE TABLE IF NOT EXISTS `ci_sessions` (
  `session_id` varchar(40) NOT NULL DEFAULT '0',
  `ip_address` varchar(45) NOT NULL DEFAULT '0',
  `user_agent` varchar(120) NOT NULL,
  `last_activity` int(10) unsigned NOT NULL DEFAULT '0',
  `user_data` text NOT NULL,
  PRIMARY KEY (`session_id`),
  KEY `last_activity_idx` (`last_activity`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ci_sessions`
--

INSERT INTO `ci_sessions` (`session_id`, `ip_address`, `user_agent`, `last_activity`, `user_data`) VALUES
('3da3d967620eecb0967759807b2c882b', '::1', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0', 1410293349, 'a:2:{s:9:"user_data";s:0:"";s:11:"logged_user";a:5:{s:2:"id";s:2:"23";s:8:"username";s:7:"pilcrow";s:4:"mail";s:19:"info@pilcrow.com.ar";s:8:"permisos";a:16:{s:9:"users_add";i:1;s:13:"users_account";i:1;s:12:"users_update";i:1;s:11:"users_roles";i:1;s:11:"users_index";i:1;s:12:"users_delete";i:1;s:14:"users_permisos";i:1;s:15:"personas_update";i:1;s:12:"personas_add";i:1;s:14:"personas_index";i:1;s:18:"personas_suspender";i:1;s:15:"personas_delete";i:1;s:12:"alertas_mapa";i:1;s:14:"alertas_update";i:1;s:13:"alertas_index";i:1;s:14:"alertas_delete";i:1;}s:5:"perms";a:16:{s:9:"users_add";a:5:{s:4:"perm";s:9:"users_add";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:16:"Alta de usuarios";s:2:"id";s:2:"39";}s:13:"users_account";a:5:{s:4:"perm";s:13:"users_account";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:17:"Cuenta de usuario";s:2:"id";s:2:"45";}s:12:"users_update";a:5:{s:4:"perm";s:12:"users_update";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:20:"Edición de usuarios";s:2:"id";s:2:"44";}s:11:"users_roles";a:5:{s:4:"perm";s:11:"users_roles";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:17:"Roles de permisos";s:2:"id";s:2:"43";}s:11:"users_index";a:5:{s:4:"perm";s:11:"users_index";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:19:"Listado de usuarios";s:2:"id";s:2:"42";}s:12:"users_delete";a:5:{s:4:"perm";s:12:"users_delete";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:19:"Borrado de usuarios";s:2:"id";s:2:"38";}s:14:"users_permisos";a:5:{s:4:"perm";s:14:"users_permisos";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:20:"Permisos de usuarios";s:2:"id";s:2:"41";}s:15:"personas_update";a:5:{s:4:"perm";s:15:"personas_update";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:20:"Edición de personas";s:2:"id";s:2:"80";}s:12:"personas_add";a:5:{s:4:"perm";s:12:"personas_add";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:16:"Alta de personas";s:2:"id";s:2:"79";}s:14:"personas_index";a:5:{s:4:"perm";s:14:"personas_index";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:19:"Listado de personas";s:2:"id";s:2:"78";}s:18:"personas_suspender";a:5:{s:4:"perm";s:18:"personas_suspender";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:18:"Suspender Personas";s:2:"id";s:3:"129";}s:15:"personas_delete";a:5:{s:4:"perm";s:15:"personas_delete";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:15:"Borrar personas";s:2:"id";s:2:"81";}s:12:"alertas_mapa";a:5:{s:4:"perm";s:12:"alertas_mapa";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:15:"Mapa de Alertas";s:2:"id";s:3:"130";}s:14:"alertas_update";a:5:{s:4:"perm";s:14:"alertas_update";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:19:"Edición de Alertas";s:2:"id";s:3:"128";}s:13:"alertas_index";a:5:{s:4:"perm";s:13:"alertas_index";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:18:"Listado de Alertas";s:2:"id";s:3:"126";}s:14:"alertas_delete";a:5:{s:4:"perm";s:14:"alertas_delete";s:10:"inheritted";b:1;s:5:"value";b:1;s:4:"name";s:18:"Borrado de Alertas";s:2:"id";s:3:"127";}}}}');

-- --------------------------------------------------------

--
-- Table structure for table `config`
--

CREATE TABLE IF NOT EXISTS `config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `empresa` varchar(100) DEFAULT NULL,
  `nombre` varchar(60) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `telefono` varchar(30) DEFAULT NULL,
  `celular` varchar(30) DEFAULT NULL,
  `mail` varchar(80) DEFAULT NULL,
  `web` varchar(80) DEFAULT NULL,
  `borrado` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `config`
--

INSERT INTO `config` (`id`, `empresa`, `nombre`, `direccion`, `telefono`, `celular`, `mail`, `web`, `borrado`) VALUES
(1, 'Laprida', 'BpaniK', 'Santamarina 1947', '011-1559799048', '11-1559799048', 'diegop@gmail.com', 'www.paraguil.com.ar', 0);

-- --------------------------------------------------------

--
-- Table structure for table `perm_data`
--

CREATE TABLE IF NOT EXISTS `perm_data` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `permKey` varchar(30) NOT NULL,
  `permName` varchar(45) NOT NULL,
  `seccion` varchar(40) DEFAULT NULL,
  `nroSeccion` int(11) NOT NULL DEFAULT '1',
  `visible` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `permKey` (`permKey`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=131 ;

--
-- Dumping data for table `perm_data`
--

INSERT INTO `perm_data` (`ID`, `permKey`, `permName`, `seccion`, `nroSeccion`, `visible`) VALUES
(39, 'users_add', 'Alta de usuarios', 'Usuarios y Permisos', 1, 1),
(41, 'users_permisos', 'Permisos de usuarios', 'Usuarios y Permisos', 1, 1),
(38, 'users_delete', 'Borrado de usuarios', 'Usuarios y Permisos', 1, 1),
(42, 'users_index', 'Listado de usuarios', 'Usuarios y Permisos', 1, 1),
(43, 'users_roles', 'Roles de permisos', 'Usuarios y Permisos', 1, 1),
(44, 'users_update', 'Edición de usuarios', 'Usuarios y Permisos', 1, 1),
(45, 'users_account', 'Cuenta de usuario', 'Usuarios y Permisos', 1, 1),
(126, 'alertas_index', 'Listado de Alertas', 'Alertas', 10, 1),
(127, 'alertas_delete', 'Borrado de Alertas', 'Alertas', 10, 1),
(128, 'alertas_update', 'Edición de Alertas', 'Alertas', 10, 1),
(129, 'personas_suspender', 'Suspender Personas', 'Personas', 9, 1),
(130, 'alertas_mapa', 'Mapa de Alertas', 'Alertas', 10, 1),
(78, 'personas_index', 'Listado de personas', 'Personas', 9, 1),
(79, 'personas_add', 'Alta de personas', 'Personas', 9, 1),
(80, 'personas_update', 'Edición de personas', 'Personas', 9, 1),
(81, 'personas_delete', 'Borrar personas', 'Personas', 9, 1);

-- --------------------------------------------------------

--
-- Table structure for table `personas`
--

CREATE TABLE IF NOT EXISTS `personas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(40) NOT NULL,
  `mail` varchar(80) DEFAULT NULL,
  `telefono` varchar(40) NOT NULL DEFAULT '0',
  `ciudad` varchar(60) DEFAULT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '0',
  `fecha` date NOT NULL,
  `imei` varchar(20) DEFAULT NULL,
  `borrado` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `imei` (`imei`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=49 ;

--
-- Dumping data for table `personas`
--

INSERT INTO `personas` (`id`, `nombre`, `mail`, `telefono`, `ciudad`, `active`, `fecha`, `imei`, `borrado`) VALUES
(31, 'Pablo torres', 'pablo.torreslaprida@gmail.com', '2284-569566', 'Laprida', 1, '2014-06-17', '356459041135037', 0),
(33, 'Ignacio', 'ignaciodipratula@gmail.com', '2284-469800', 'Laprida', 1, '2014-06-17', '357143051186879', 0),
(34, 'armando', 'armando-hector@hotail.com', '2932-459008', 'Laprida', 1, '2014-06-17', '353989052498505', 0),
(35, 'urbano', 'urbanomazzey@hotmail.com', '2284-465968', 'Laprida', 1, '2014-06-17', '355259051235391', 0),
(37, 'gontatest papapapapaa', 'gontattoo2@gmail.com', '11-63058144', 'Laprida', 1, '2014-09-02', '358306049856486', 0),
(38, 'Eugenia ', 'maruduhau@hotmail.com', '2284-671328', 'Laprida', 1, '2014-06-20', '353989050145330', 0),
(39, 'Ruben', 'turko85@gmail.com', '2286-401736', 'Laprida', 1, '2014-06-24', '358922050897829', 0),
(40, 'julia barraza', 'mariajulia_laprida@hotmail.com', '2284-694158', 'Laprida', 1, '2014-06-24', '352738060626198', 0),
(41, 'faure adriana', 'katata87@hotmail.com', '2284-202784', 'Laprida', 1, '2014-06-24', '358922053054170', 0),
(44, 'asdasdasd', 'sdsadasd', 'asdasdasd', 'asdasdasdasd', 1, '0000-00-00', '', 0),
(47, 'test ape', 'mail@mail.com', '2284-15151515', 'Laprida', 1, '2014-09-02', '000000000000000', 0),
(48, 'gabriel test', 'mail@mail.com', '11-59582856', 'Laprida', 1, '2014-09-02', '355694040219082', 0);

-- --------------------------------------------------------

--
-- Table structure for table `role_data`
--

CREATE TABLE IF NOT EXISTS `role_data` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `roleName` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `roleName` (`roleName`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `role_data`
--

INSERT INTO `role_data` (`ID`, `roleName`) VALUES
(5, 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `role_perms`
--

CREATE TABLE IF NOT EXISTS `role_perms` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `roleID` bigint(20) NOT NULL,
  `permID` bigint(20) NOT NULL,
  `value` tinyint(1) NOT NULL DEFAULT '0',
  `addDate` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `roleID_2` (`roleID`,`permID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `role_perms`
--

INSERT INTO `role_perms` (`ID`, `roleID`, `permID`, `value`, `addDate`) VALUES
(1, 5, 39, 1, '2014-09-09 17:02:58'),
(2, 5, 45, 1, '2014-09-09 17:02:58'),
(3, 5, 44, 1, '2014-09-09 17:02:58'),
(4, 5, 43, 1, '2014-09-09 17:02:58'),
(5, 5, 42, 1, '2014-09-09 17:02:58'),
(6, 5, 38, 1, '2014-09-09 17:02:58'),
(7, 5, 41, 1, '2014-09-09 17:02:58'),
(8, 5, 80, 1, '2014-09-09 17:02:58'),
(9, 5, 79, 1, '2014-09-09 17:02:58'),
(10, 5, 78, 1, '2014-09-09 17:02:58'),
(11, 5, 129, 1, '2014-09-09 17:02:58'),
(12, 5, 81, 1, '2014-09-09 17:02:58'),
(13, 5, 130, 1, '2014-09-09 17:02:58'),
(14, 5, 128, 1, '2014-09-09 17:02:58'),
(15, 5, 126, 1, '2014-09-09 17:02:58'),
(16, 5, 127, 1, '2014-09-09 17:02:58');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `password` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `last_login` datetime NOT NULL,
  `mail` varchar(60) COLLATE utf8_spanish_ci NOT NULL,
  `nombre` varchar(40) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=40 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `last_login`, `mail`, `nombre`) VALUES
(23, 'pilcrow', '154d28bc32e95b266847d9bdd0427b62', '2014-09-09 17:09:26', 'info@pilcrow.com.ar', 'Administrador');

-- --------------------------------------------------------

--
-- Table structure for table `user_perms`
--

CREATE TABLE IF NOT EXISTS `user_perms` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userID` bigint(20) NOT NULL,
  `permID` bigint(20) NOT NULL,
  `value` tinyint(1) NOT NULL DEFAULT '0',
  `addDate` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `userID` (`userID`,`permID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE IF NOT EXISTS `user_roles` (
  `userID` bigint(20) NOT NULL,
  `roleID` bigint(20) NOT NULL,
  `addDate` datetime NOT NULL,
  PRIMARY KEY (`userID`,`roleID`),
  UNIQUE KEY `userID` (`userID`,`roleID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`userID`, `roleID`, `addDate`) VALUES
(23, 5, '2014-09-09 17:00:32');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
