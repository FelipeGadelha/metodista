package br.com.felipe.gadelha.metodista.api.v1.controller

import br.com.felipe.gadelha.metodista.api.dto.View
import br.com.felipe.gadelha.metodista.api.dto.request.BasicBasketRq
import br.com.felipe.gadelha.metodista.api.dto.response.BasicBasketRs
import br.com.felipe.gadelha.metodista.domain.service.BasicBasketService
import com.fasterxml.jackson.annotation.JsonView
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/v1/basic-baskets"])
class BasicBasketController(
    private val basicBasketService: BasicBasketService
) {
    @JsonView(View.Basic::class)
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll() = ResponseEntity
        .ok(basicBasketService.findAll().map {b -> BasicBasketRs(b) })

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID) = ResponseEntity
        .ok(BasicBasketRs(basicBasketService.findById(id)))

    @PostMapping
    fun save(@RequestBody @Valid basicRq: BasicBasketRq): ResponseEntity<BasicBasketRs> {
        val saved = basicBasketService.save(basicRq.toModel())
        return ResponseEntity<BasicBasketRs>(BasicBasketRs(saved), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody @Valid basicRq: BasicBasketRq): ResponseEntity<BasicBasketRs> {
        val basic = basicBasketService.update(id, basicRq.toModel())
        return ResponseEntity.ok(BasicBasketRs(basic))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: Long) { basicBasketService.deleteById(id) }
}