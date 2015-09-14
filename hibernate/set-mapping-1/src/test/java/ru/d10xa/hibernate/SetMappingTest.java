package ru.d10xa.hibernate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static ru.d10xa.hibernate.RowName.A;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SetMappingTest.class)
@EnableTransactionManagement
@Transactional
@SpringBootApplication
public class SetMappingTest {

   @Autowired
   ReportRepository repo;

   Report report;

   @Before
   public void initTestData() {
      report = new Report();
      Set<RowEntry> table = new HashSet<RowEntry>();

      table.add(new RowEntry(null, A, "a", "aa"));
      table.add(new RowEntry(null, A, "aaa", "aaaa"));

      report.setTable(table);
      repo.save(report);
      this.report = repo.findAll().iterator().next();
   }

   @Test
   public void expect_A_exists() {
      Collection<RowEntry> table = report.getTable();
      RowEntry a = null;
      for (RowEntry rowEntry : table) {
         if ("a".equals(rowEntry.getP1())) {
            a = rowEntry;
         }
      }
      Assert.assertNotNull(a);
   }

}