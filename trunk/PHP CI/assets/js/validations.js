function FormValidator(){

// USER ---------------------------------------------------------------------------------			
	$('#usersForm').bootstrapValidator({
		message: 'This value is not valid',
		fields: {
			username: {
				validators: {
					notEmpty: {
						message: 'El nombre de usuario es un dato obligatorio.'
					},
					stringLength: {
						min: 3,
						max: 20,
						message: 'El nombre de usuario debe tener entre 3 y 20 caracteres.'
					}
				}
			},
			mail: {
				validators: {
					emailAddress: {
						message: 'El formato del e-mail no es correcto'
					}
				}
			},
			password: {
				validators: {
					notEmpty: {
						message: 'El password es un campo obligatorio.'
					},
					identical: {
						field: 'passconf',
						message: 'La confirmaci&oacute;n del password no es correcta.'
					}
				}
			},
			passconf: {
				validators: {
					notEmpty: {
						message: 'La confirmaci&oacute;m del password no es correcta.'
					},
					identical: {
						field: 'password',
						message: 'La confirmaci&oacute;n del password no es correcta.'
					}
				}
			}
		}
	});

// CLIENTES ---------------------------------------------------------------------------------	
	$('#clientesForm').bootstrapValidator({
		message: 'This value is not valid',
		fields: {
			razonsocial: {
				validators: {
					notEmpty: {
						message: 'La Raz&oacute;n Social es un dato obligatorio.'
					},
					stringLength: {
						min: 3,
						max: 50,
						message: 'La Raz&oacute;n Social debe tener entre 3 y 50 caracteres.'
					},
					//regexp: {
					//	regexp: /^[a-zA-Z0-9_\.]+$/,
					//	message: 'The username can only consist of alphabetical, number, dot and underscore'
					//}
				}
			},
			cuit: {
				validators: {
					digits: {
						message: 'S&oacute;lo se pueden ingresar n&uacute;meros.'
					}
				}
			},
			mail: {
				validators: {
					emailAddress: {
						message: 'El formato del e-mail no es correcto'
					}
				}
			}
		}
	});
	
// MEDICOS ---------------------------------------------------------------------------------	
	$('#medicosForm').bootstrapValidator({
		message: 'This value is not valid',
		fields: {
			nombre: {
				validators: {
					notEmpty: {
						message: 'El nombre es un dato obligatorio.'
					},
					stringLength: {
						min: 3,
						max: 50,
						message: 'El nombre debe tener entre 3 y 50 caracteres.'
					},
					//regexp: {
					//	regexp: /^[a-zA-Z0-9_\.]+$/,
					//	message: 'The username can only consist of alphabetical, number, dot and underscore'
					//}
				}
			},
			mail: {
				validators: {
					emailAddress: {
						message: 'El formato del e-mail no es correcto'
					}
				}
			}
		}
	});

// CLINICAS ---------------------------------------------------------------------------------
	$('#clinicasForm').bootstrapValidator({
		message: 'This value is not valid',
		fields: {
			nombre: {
				validators: {
					notEmpty: {
						message: 'El nombre es un dato obligatorio.'
					},
					stringLength: {
						min: 3,
						max: 50,
						message: 'El nombre debe tener entre 3 y 50 caracteres.'
					},

				}
			},
			mail: {
				validators: {
					emailAddress: {
						message: 'El formato del e-mail no es correcto'
					}
				}
			}
		}
	});
	
// PRESUPUESTOS ---------------------------------------------------------------------------------
	$('#presupuestosForm').bootstrapValidator({
		message: 'This value is not valid',
		fields: {
			fecha_presupuesto: {
				validators: {
					notEmpty: {
						message: 'El nombre es un dato obligatorio.'
					},
				}
			}
		}
	});
	
// HONORARIOS MEDICOS ---------------------------------------------------------------------------------
	$('#hmForm').bootstrapValidator({
		message: 'This value is not valid',
		fields: {
			hm: {
				validators: {
					notEmpty: {
						message: 'El honorario es un dato obligatorio.'
					},
					regexp: {
						regexp: /^[0-9_\.]+$/,
						message: 'S&oacute;lo est&aacute;n permitidos caracteres num&eacute;ricos.'
					}
				}
			},
			numero_cheque: {
				validators: {	
					regexp: {
						regexp: /^[0-9_\.]+$/,
						message: 'S&oacute;lo est&aacute;n permitidos caracteres num&eacute;ricos.'
					}
				}
			},
			quien: {
				validators: {	
					regexp: {
						regexp: /^[a-zA-Z0-9_\.]+$/,
						message: 'S&oacute;lo est&aacute;n permitidos caracteres alfanum&eacute;ricos'
					}
				}
			}
			
		}
	});

// HONORARIOS INSTRUMENTADOR ---------------------------------------------------------------------------------
	$('#hiForm').bootstrapValidator({
		message: 'This value is not valid',
		fields: {
			fecha_hi: {
				validators: {
					notEmpty: {
						message: 'La fecha es un dato obligatorio.'
					}
				}
			},
			importe: {
				validators: {
					notEmpty: {
						message: 'El importe es un dato obligatorio.'
					},
					regexp: {
						regexp: /^[0-9_\.]+$/,
						message: 'S&oacute;lo est&aacute;n permitidos caracteres num&eacute;ricos.'
					}
				}
			}
			
		}
	});
	
// COBRANZA ---------------------------------------------------------------------------------
	$('#cobranzaForm').bootstrapValidator({
		message: 'This value is not valid',
		fields: {
			fecha_cobro: {
				validators: {
					notEmpty: {
						message: 'La fecha de cobro es un dato obligatorio.'
					}
				}
			},
			recibo_numero: {
				validators: {
					notEmpty: {
						message: 'El n&uacute;mero de recibo es un dato obligatorio.'
					},
					regexp: {
						regexp: /^[0-9_\.]+$/,
						message: 'S&oacute;lo est&aacute;n permitidos caracteres num&eacute;ricos.'
					}
				}
			},
			cobrado: {
				validators: {
					notEmpty: {
						message: 'El importe es un dato obligatorio.'
					},
					regexp: {
						regexp: /^[0-9_\.]+$/,
						message: 'S&oacute;lo est&aacute;n permitidos caracteres num&eacute;ricos.'
					}
				}
			}
			
		}
	});
	
// FACTURACION ---------------------------------------------------------------------------------
	$('#facturacionForm').bootstrapValidator({
		message: 'This value is not valid',
		fields: {
			fecha_factura: {
				validators: {
					notEmpty: {
						message: 'La fecha de la factura es un dato obligatorio.'
					}
				}
			},
			numero: {
				validators: {
					notEmpty: {
						message: 'El n&uacute;mero de factura es un dato obligatorio.'
					},
					regexp: {
						regexp: /^[0-9_\.]+$/,
						message: 'S&oacute;lo est&aacute;n permitidos caracteres num&eacute;ricos.'
					}
				}
			},
			monto: {
				validators: {
					notEmpty: {
						message: 'El monto es un dato obligatorio.'
					},
					regexp: {
						regexp: /^[0-9_\.]+$/,
						message: 'S&oacute;lo est&aacute;n permitidos caracteres num&eacute;ricos.'
					}
				}
			}
			
		}
	});
	
	// PRODUCCION ---------------------------------------------------------------------------------	
	$('#produccionForm').bootstrapValidator({
		message: 'No es un valor v&aacute;lido',
		fields: {
			codigo_familia: {
				validators: {
					digits: {
						message: 'S&oacute;lo se pueden ingresar n&uacute;meros.'
					}
				}
			}
		}
	});
	
	// PRODUCCION - FABRICACION ---------------------------------------------------------------------------------	
	$('#fabricacionForm').bootstrapValidator({
		message: 'No es un valor v&aacute;lido',
		fields: {
			tercerista: {
				validators: {	
					notEmpty: {
						message: 'El Tercerista es un dato obligatorio.'
					},
					regexp: {
						regexp: /^[a-zA-Z0-9_\.]+$/,
						message: 'S&oacute;lo est&aacute;n permitidos caracteres alfanum&eacute;ricos'
					}
				}
			},
			fecha_entregada:{
				validators: {	
					notEmpty: {
						message: 'La Fecha Entregada es un dato obligatorio.'
					}
				}
			}
		}
	});
	
	// PRODUCCION - LAVADO ---------------------------------------------------------------------------------	
	$('#lavadoForm').bootstrapValidator({
		message: 'No es un valor v&aacute;lido',
		fields: {
			desengrasante_lote: {
				validators: {	
					notEmpty: {
						message: 'Es un dato obligatorio.'
					},
					regexp: {
						regexp: /^[a-zA-Z0-9_\.]+$/,
						message: 'S&oacute;lo est&aacute;n permitidos caracteres alfanum&eacute;ricos'
					}
				}
			},
			tiempo_aplicado:{
				validators: {	
					notEmpty: {
						message: 'Es un dato obligatorio.'
					},
					regexp: {
						regexp: /^[a-zA-Z0-9_\.]+$/,
						message: 'S&oacute;lo est&aacute;n permitidos caracteres alfanum&eacute;ricos'
					}
				}
			}
		}
	});
}