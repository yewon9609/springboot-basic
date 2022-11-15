package org.prgrms.memory;

import static org.prgrms.voucher.VoucherFactory.createByFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.prgrms.exception.FileNotFoundException;
import org.prgrms.voucher.voucherType.Voucher;
import org.prgrms.voucher.voucherType.VoucherType;
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

  public Voucher save(Voucher voucher) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {

      writer.write(voucher.getClass().getSimpleName() + "," + voucher.getVoucherId() + ","
          + voucher.getVoucherAmount().getValue() + System.lineSeparator());
    } catch (IOException e) {
      throw new FileNotFoundException(file.getName());
    }
    return voucher;
  }

  public List<Voucher> findAll() {
    List<Voucher> voucherList = new ArrayList<>();

    if (file.exists()) {

      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

        String line;

        while ((line = reader.readLine()) != null) {
          voucherList.add(getVoucher(line.split(",")));
        }
      } catch (IOException e) {
        throw new FileNotFoundException(file.getName());
      }
    }
    return voucherList;
  }

  private Voucher getVoucher(String[] lineArray) {

    String voucherClassName = lineArray[0];
    UUID voucherId = UUID.fromString(lineArray[1]);
    String voucherAmount = lineArray[2];

    return createByFile(VoucherType.findByVoucherClassName(voucherClassName), voucherId,
        voucherAmount);

  }

}
