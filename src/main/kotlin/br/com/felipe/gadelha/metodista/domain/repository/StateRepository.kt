package br.com.felipe.gadelha.metodista.domain.repository

import br.com.felipe.gadelha.metodista.domain.model.State
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StateRepository: JpaRepository<State, Long> {

}
