package org.prgrms.voucher.voucherType;

import java.util.stream.Stream;
import org.prgrms.exception.NoSuchVoucherTypeException;

public enum VoucherType {

  FIXED("1"),
  PERCENT("2");

  private final String type;

  VoucherType(String type) {
    this.type = type;

  }

  public static VoucherType of(String choice) {
    return Stream.of(VoucherType.values())
        .filter(voucher -> voucher.type.equals(choice))
        .findAny()
        .orElseThrow(() -> new NoSuchVoucherTypeException(choice));
  }

  public static VoucherType findByVoucherClassName(String className) {
    return Stream.of(VoucherType.values())
        .filter(voucher -> className.toUpperCase().contains(voucher.name()))
        .findAny()
        .orElseThrow(() -> new NoSuchVoucherTypeException(className));
  }

  public String getType() {
    return type;
  }
}
