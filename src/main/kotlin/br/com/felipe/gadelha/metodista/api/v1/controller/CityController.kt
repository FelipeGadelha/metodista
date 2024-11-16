package br.com.felipe.gadelha.metodista.api.v1.controller

import br.com.felipe.gadelha.metodista.api.dto.View
import br.com.felipe.gadelha.metodista.api.dto.request.CityRq
import br.com.felipe.gadelha.metodista.api.dto.response.CityRs
import br.com.felipe.gadelha.metodista.domain.service.CityService
import com.fasterxml.jackson.annotation.JsonView
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
@RequestMapping(value = ["/v1/cities"])
class CityController(
    private val cityService: CityService
) {
    @JsonView(View.Basic::class)
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll() = ResponseEntity
        .ok(cityService.findAll().map {c -> CityRs(c) })

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = ResponseEntity
        .ok(CityRs(cityService.findById(id)))

    @PostMapping
    fun save(@RequestBody @Valid cityRq: CityRq): ResponseEntity<CityRs> {
        val saved = cityService.save(cityRq.toModel())
        return ResponseEntity<CityRs>(CityRs(saved), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody @Valid cityRq: CityRq): ResponseEntity<CityRs> {
        val city = cityService.update(id, cityRq.toModel())
        return ResponseEntity.ok(CityRs(city))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: Long) { cityService.deleteById(id) }
}