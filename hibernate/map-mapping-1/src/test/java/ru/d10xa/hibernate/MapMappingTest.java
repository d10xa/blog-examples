package ru.d10xa.hibernate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;

import static ru.d10xa.hibernate.RowName.A;
import static ru.d10xa.hibernate.RowName.B;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MapMappingTest.class)
@EnableTransactionManagement
@Transactional
@SpringBootApplication
public class MapMappingTest {

   @Autowired
   ReportRepository repo;

   Report report;

   @Before
   public void initTestData() {
      report = new Report();
      LinkedHashMap<RowName, RowEntry> table = new LinkedHashMap();

      table.put(A, new RowEntry("a", "aa"));
      table.put(A, new RowEntry("aaa", "aaaa"));
      table.put(B, new RowEntry("b", "bb"));
      table.put(B, new RowEntry("bbb", "bbbb"));

      report.setTable(table);
      repo.save(report);
      this.report = repo.findAll().iterator().next();
   }

   @Test
   public void expect_A_exists() {
      Assert.assertEquals("aaa", report.getTable().get(A).getP1());
   }

   @Test
   public void expect_B_exists() {
      Assert.assertEquals("bbb", report.getTable().get(B).getP1());
   }

}