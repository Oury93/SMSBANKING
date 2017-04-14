package atos.esp.web.rest;

import com.codahale.metrics.annotation.Timed;
import atos.esp.domain.GroupeBancaire;

import atos.esp.repository.GroupeBancaireRepository;
import atos.esp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GroupeBancaire.
 */
@RestController
@RequestMapping("/api")
public class GroupeBancaireResource {

    private final Logger log = LoggerFactory.getLogger(GroupeBancaireResource.class);

    private static final String ENTITY_NAME = "groupeBancaire";
        
    private final GroupeBancaireRepository groupeBancaireRepository;

    public GroupeBancaireResource(GroupeBancaireRepository groupeBancaireRepository) {
        this.groupeBancaireRepository = groupeBancaireRepository;
    }

    /**
     * POST  /groupe-bancaires : Create a new groupeBancaire.
     *
     * @param groupeBancaire the groupeBancaire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupeBancaire, or with status 400 (Bad Request) if the groupeBancaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/groupe-bancaires")
    @Timed
    public ResponseEntity<GroupeBancaire> createGroupeBancaire(@Valid @RequestBody GroupeBancaire groupeBancaire) throws URISyntaxException {
        log.debug("REST request to save GroupeBancaire : {}", groupeBancaire);
        if (groupeBancaire.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new groupeBancaire cannot already have an ID")).body(null);
        }
        GroupeBancaire result = groupeBancaireRepository.save(groupeBancaire);
        return ResponseEntity.created(new URI("/api/groupe-bancaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /groupe-bancaires : Updates an existing groupeBancaire.
     *
     * @param groupeBancaire the groupeBancaire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupeBancaire,
     * or with status 400 (Bad Request) if the groupeBancaire is not valid,
     * or with status 500 (Internal Server Error) if the groupeBancaire couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/groupe-bancaires")
    @Timed
    public ResponseEntity<GroupeBancaire> updateGroupeBancaire(@Valid @RequestBody GroupeBancaire groupeBancaire) throws URISyntaxException {
        log.debug("REST request to update GroupeBancaire : {}", groupeBancaire);
        if (groupeBancaire.getId() == null) {
            return createGroupeBancaire(groupeBancaire);
        }
        GroupeBancaire result = groupeBancaireRepository.save(groupeBancaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, groupeBancaire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /groupe-bancaires : get all the groupeBancaires.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of groupeBancaires in body
     */
    @GetMapping("/groupe-bancaires")
    @Timed
    public List<GroupeBancaire> getAllGroupeBancaires() {
        log.debug("REST request to get all GroupeBancaires");
        List<GroupeBancaire> groupeBancaires = groupeBancaireRepository.findAll();
        return groupeBancaires;
    }

    /**
     * GET  /groupe-bancaires/:id : get the "id" groupeBancaire.
     *
     * @param id the id of the groupeBancaire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupeBancaire, or with status 404 (Not Found)
     */
    @GetMapping("/groupe-bancaires/{id}")
    @Timed
    public ResponseEntity<GroupeBancaire> getGroupeBancaire(@PathVariable Long id) {
        log.debug("REST request to get GroupeBancaire : {}", id);
        GroupeBancaire groupeBancaire = groupeBancaireRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(groupeBancaire));
    }

    /**
     * DELETE  /groupe-bancaires/:id : delete the "id" groupeBancaire.
     *
     * @param id the id of the groupeBancaire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/groupe-bancaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupeBancaire(@PathVariable Long id) {
        log.debug("REST request to delete GroupeBancaire : {}", id);
        groupeBancaireRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
