package ru.d10xa.hibernate;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Report extends GenericEntity {

   @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
   @JoinColumn(name = "report_id")
   private Collection<RowEntry> table;

}