package ru.d10xa.hibernate;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Report extends GenericEntity {

   @OneToMany(cascade = CascadeType.ALL)
   @JoinColumn(name = "report_id")
   @MapKeyEnumerated(EnumType.STRING)
   private Map<RowName, RowEntry> table = new LinkedHashMap();

}