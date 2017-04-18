package atos.esp.repository;

import atos.esp.domain.Banque;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Banque entity.
 */
@SuppressWarnings("unused")
public interface BanqueRepository extends JpaRepository<Banque,Long> {

}
