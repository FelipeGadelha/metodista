package br.com.felipe.gadelha.metodista.domain.exception

class BusinessException(message: String?, cause: Throwable? = null):
    RuntimeException(message, cause)