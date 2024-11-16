package br.com.felipe.gadelha.metodista.api.v1.controller

import br.com.felipe.gadelha.metodista.domain.service.MemberService
import br.com.felipe.gadelha.metodista.domain.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userService: UserService
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UserController::class.java)
    }

    @PutMapping("/active/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun activeUser(@PathVariable id: UUID) {
        logger.info("activeUser - id = $id")
        userService.activeUser(id)
    }
}