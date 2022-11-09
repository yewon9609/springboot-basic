package org.prgrms.voucher.voucherType;

import java.util.UUID;
import org.prgrms.voucher.discountType.Amount;

public class PercentDiscountVoucher implements Voucher {
  private final UUID voucherId;
  private final Amount discountPercent;


  public PercentDiscountVoucher(UUID voucherId, Amount discountPercent) {
    this.voucherId = voucherId;
    this.discountPercent = discountPercent;
  }

  @Override
  public long discount(long beforeDiscount) {
    return (long)(beforeDiscount * (1 - (discountPercent.getValue() / 100.0)));
  }

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  @Override
  public String toString() {
    return "*** PercentDiscountVoucher ***"
        + System.lineSeparator()
        + "voucherId : " + voucherId
        + System.lineSeparator()
        + "discountRate: " + discountPercent.getValue() + "%";
  }
}
