package ru.d10xa.hibernate;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Data
@EqualsAndHashCode(of = {"id"})
public abstract class GenericEntity implements Serializable {
   @Id @GeneratedValue
   protected Long id;
}