package atos.esp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A GroupeBancaire.
 */
@Entity
@Table(name = "groupe_bancaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GroupeBancaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle_g", nullable = false)
    private String libelleG;

    @NotNull
    @Max(value = 5)
    @Column(name = "code_g", nullable = false)
    private Integer codeG;

    @OneToMany(mappedBy = "groupeBancaire")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Banque> banques = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleG() {
        return libelleG;
    }

    public GroupeBancaire libelleG(String libelleG) {
        this.libelleG = libelleG;
        return this;
    }

    public void setLibelleG(String libelleG) {
        this.libelleG = libelleG;
    }

    public Integer getCodeG() {
        return codeG;
    }

    public GroupeBancaire codeG(Integer codeG) {
        this.codeG = codeG;
        return this;
    }

    public void setCodeG(Integer codeG) {
        this.codeG = codeG;
    }

    public Set<Banque> getBanques() {
        return banques;
    }

    public GroupeBancaire banques(Set<Banque> banques) {
        this.banques = banques;
        return this;
    }

    public GroupeBancaire addBanque(Banque banque) {
        this.banques.add(banque);
        banque.setGroupeBancaire(this);
        return this;
    }

    public GroupeBancaire removeBanque(Banque banque) {
        this.banques.remove(banque);
        banque.setGroupeBancaire(null);
        return this;
    }

    public void setBanques(Set<Banque> banques) {
        this.banques = banques;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupeBancaire groupeBancaire = (GroupeBancaire) o;
        if (groupeBancaire.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, groupeBancaire.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GroupeBancaire{" +
            "id=" + id +
            ", libelleG='" + libelleG + "'" +
            ", codeG='" + codeG + "'" +
            '}';
    }
}
