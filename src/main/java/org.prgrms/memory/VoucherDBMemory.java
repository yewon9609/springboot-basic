package org.prgrms.memory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.voucher.discountType.Amount;
import org.prgrms.voucher.voucherType.Voucher;
import org.prgrms.voucher.voucherType.VoucherType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class VoucherDBMemory {


  private final JdbcTemplate jdbcTemplate;


  public VoucherDBMemory(JdbcTemplate jdbcTemplate) {

    this.jdbcTemplate = jdbcTemplate;
  }

  private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
    UUID id  = UUID.fromString(resultSet.getString("id"));
    VoucherType type = VoucherType.of(resultSet.getString("type"));
    Amount amount = type.generateAmount(resultSet.getLong("amount"));

    return type.generateVoucherWithId(id, amount);
  };


  public int save(Voucher voucher) {
    String insert = "INSERT INTO voucher(id, type, amount) VALUES (?,?,?)";
    int update = jdbcTemplate.update(insert, String.valueOf(voucher.getVoucherId()),
        String.valueOf(voucher.getVoucherType()),
        voucher.getVoucherAmount().getValue());
    return update;
  }

  public List<Voucher> findAll() {
    String findAll = "SELECT * FROM voucher";
    return jdbcTemplate.query(findAll, voucherRowMapper);
  }

  public Optional<Voucher> findById(UUID id) {
    String findById = "SELECT * FROM voucher WHERE id = ?";
    return Optional.ofNullable(jdbcTemplate.queryForObject(findById, voucherRowMapper, id));
  }
}
