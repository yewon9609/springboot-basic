package org.prgrms.memory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.prgrms.voucher.discountType.DiscountAmount;
import org.prgrms.voucher.discountType.DiscountRate;
import org.prgrms.voucher.voucherType.FixedAmountVoucher;
import org.prgrms.voucher.voucherType.PercentDiscountVoucher;
import org.prgrms.voucher.voucherType.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class VoucherFileMemory implements Memory {

  private final File file;

  public VoucherFileMemory(@Value("${file.voucher.file-path}") String filePath) {
    this.file = new File(filePath);
  }

  public Voucher save(Voucher voucher) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

      writer.write(voucher.getClass().getSimpleName() + "," + voucher.getVoucherId() + ","
          + voucher.getVoucherAmount());
    }
    return voucher;
  }

//  public Optional<Voucher> findById(UUID id) throws IOException {
//
//    Optional<Voucher> voucher = Optional.empty();
//
//    if (file.exists()) {
//      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//
//        String line;
//
//        while ((line = reader.readLine()) != null) {
//
//          voucher = getVoucher(line.split(","), id);
//
//        }
//        return voucher;
//      }
//    } else {
//      throw new NoSuchFileException(file.getName());
//    }
//
//  }


  public List<Voucher> findAll() throws IOException {
    List<Voucher> voucherList = new ArrayList<>();

    if (file.exists()) {
      BufferedReader reader = new BufferedReader(new FileReader(file));

      String line;

      while ((line = reader.readLine()) != null) {
        voucherList.add(getVoucher(line.split(",")));
      }
      reader.close();
    }
    return voucherList;
  }

  private Voucher getVoucher(String[] lineArray) {

    String voucherClassName = lineArray[0];
    UUID voucherId = UUID.fromString(lineArray[1]);
    String voucherAmount = lineArray[2];
    Voucher voucher = null;

    if (voucherClassName.contains("Percent")) {

      voucher = new PercentDiscountVoucher(voucherId, new DiscountRate(voucherAmount));
    } else if (voucherClassName.contains("Fixed")) {
      voucher = new FixedAmountVoucher(voucherId, new DiscountAmount(voucherAmount));
    }

    return voucher;

  }

}
