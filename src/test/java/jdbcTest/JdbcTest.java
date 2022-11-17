package jdbcTest;

import static org.junit.jupiter.api.Assertions.*;

import com.zaxxer.hikari.HikariDataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.prgrms.memory.VoucherDBMemory;
import org.prgrms.voucher.discountType.DiscountAmount;
import org.prgrms.voucher.voucherType.FixedAmountVoucher;
import org.prgrms.voucher.voucherType.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbcTest {

  @Configuration
  @ComponentScan(basePackages = {"org.prgrms.memory"})
  static class Config {
    @Bean
    public DataSource dataSource() {
      HikariDataSource dataSource = DataSourceBuilder.create()
          .url("jdbc:mysql://localhost/voucher_app")
          .username("root")
          .password("root1234!")
          .type(HikariDataSource.class)
          .build();
      return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
      return new JdbcTemplate(dataSource);
    }

  }

  @Autowired
  VoucherDBMemory memory;

  @Autowired
  DataSource dataSource;


  @DisplayName("바우처 save")
  @Test
  void test1() {
    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(300L));
    int save = memory.save(voucher);
    assertEquals(1, save);
  }

  @DisplayName("바우처 findAll")
  @Test
  void test2() {
    List<Voucher> voucherList = memory.findAll();
    assertFalse(voucherList.isEmpty());
  }

  @DisplayName("바우처 findById")
  @Test
  void test3() {
    Voucher savedVoucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(600L));
    Optional<Voucher> voucher = memory.findById(savedVoucher.getVoucherId());
    assertFalse(voucher.isEmpty());
  }



}
