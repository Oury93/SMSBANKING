package atos.esp.repository;

import atos.esp.domain.GroupeBancaire;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GroupeBancaire entity.
 */
@SuppressWarnings("unused")
public interface GroupeBancaireRepository extends JpaRepository<GroupeBancaire,Long> {

}
