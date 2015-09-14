package ru.d10xa.hibernate;

import org.springframework.data.repository.CrudRepository;

public interface ReportRepository
      extends CrudRepository<Report, Long> {
}