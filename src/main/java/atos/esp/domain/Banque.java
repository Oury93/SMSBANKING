package atos.esp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Banque.
 */
@Entity
@Table(name = "banque")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Banque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "libelle_b", length = 50, nullable = false)
    private String libelleB;

    @NotNull
    @Size(max = 5)
    @Column(name = "code_b", length = 5, nullable = false)
    private String codeB;

    @NotNull
    @Size(max = 2)
    @Column(name = "code_p", length = 2, nullable = false)
    private String codeP;

    @ManyToOne(optional = false)
    @NotNull
    private GroupeBancaire groupeBancaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleB() {
        return libelleB;
    }

    public Banque libelleB(String libelleB) {
        this.libelleB = libelleB;
        return this;
    }

    public void setLibelleB(String libelleB) {
        this.libelleB = libelleB;
    }

    public String getCodeB() {
        return codeB;
    }

    public Banque codeB(String codeB) {
        this.codeB = codeB;
        return this;
    }

    public void setCodeB(String codeB) {
        this.codeB = codeB;
    }

    public String getCodeP() {
        return codeP;
    }

    public Banque codeP(String codeP) {
        this.codeP = codeP;
        return this;
    }

    public void setCodeP(String codeP) {
        this.codeP = codeP;
    }

    public GroupeBancaire getGroupeBancaire() {
        return groupeBancaire;
    }

    public Banque groupeBancaire(GroupeBancaire groupeBancaire) {
        this.groupeBancaire = groupeBancaire;
        return this;
    }

    public void setGroupeBancaire(GroupeBancaire groupeBancaire) {
        this.groupeBancaire = groupeBancaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Banque banque = (Banque) o;
        if (banque.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, banque.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Banque{" +
            "id=" + id +
            ", libelleB='" + libelleB + "'" +
            ", codeB='" + codeB + "'" +
            ", codeP='" + codeP + "'" +
            '}';
    }
}
