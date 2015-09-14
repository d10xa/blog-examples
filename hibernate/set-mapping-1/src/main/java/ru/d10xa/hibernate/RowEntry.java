package ru.d10xa.hibernate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(of = {"rowName"}, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"row_name", "report_id"})})
public class RowEntry extends GenericEntity implements Serializable {

   @ManyToOne
   @JoinColumn(name = "report_id", insertable = false, updatable = false)
   private Report report;

   @Enumerated(EnumType.STRING)
   @Column(name = "row_name")
   private RowName rowName;

   private String p1, p2;

}