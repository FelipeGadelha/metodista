package br.com.felipe.gadelha.metodista.api.v1.controller

import br.com.felipe.gadelha.metodista.api.dto.request.StateRq
import br.com.felipe.gadelha.metodista.api.dto.response.StateRs
import br.com.felipe.gadelha.metodista.domain.model.State
import br.com.felipe.gadelha.metodista.domain.service.StateService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/states")
class StateController(
    private val stateService: StateService
) {
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll() = ResponseEntity
        .ok(stateService.findAll().map { s -> StateRs(s) })

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = ResponseEntity
        .ok(StateRs(stateService.findById(id)))

    @PostMapping
    fun save(@RequestBody @Valid stateRq: StateRq): ResponseEntity<StateRs> {
        val saved: State = stateService.save(stateRq.toModel())
        return ResponseEntity(StateRs(saved), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody @Valid stateRq: StateRq): ResponseEntity<StateRs> {
        val state: State = stateService.update(id, stateRq.toModel())
        return ResponseEntity.ok(StateRs(state))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: Long) { stateService.deleteById(id) }
}