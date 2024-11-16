package br.com.felipe.gadelha.metodista.domain.exception

import org.springframework.validation.BindingResult

class ValidationException(val bindingResult: BindingResult):
    RuntimeException()