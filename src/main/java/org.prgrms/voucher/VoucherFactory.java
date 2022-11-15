package org.prgrms.voucher;

import java.util.UUID;
import org.prgrms.exception.NoSuchVoucherTypeException;
import org.prgrms.voucher.discountType.DiscountAmount;
import org.prgrms.voucher.discountType.DiscountRate;
import org.prgrms.voucher.voucherType.FixedAmountVoucher;
import org.prgrms.voucher.voucherType.PercentDiscountVoucher;
import org.prgrms.voucher.voucherType.Voucher;
import org.prgrms.voucher.voucherType.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class VoucherFactory {

  private VoucherFactory() {}

  public static Voucher createVoucher(VoucherType voucherType, String amount) {
    switch (voucherType) {
      case PERCENT -> {
        return new PercentDiscountVoucher(UUID.randomUUID(),new DiscountRate(amount));
      }
      case FIXED -> {
        return new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(amount));
      }
      default -> throw new NoSuchVoucherTypeException(voucherType.getType());
    }
  }

  public static Voucher createByFile(VoucherType voucherType, UUID voucherId, String amount) {
    switch (voucherType) {
      case PERCENT -> {
        return new PercentDiscountVoucher(voucherId,new DiscountRate(amount));
      }
      case FIXED -> {
        return new FixedAmountVoucher(voucherId, new DiscountAmount(amount));
      }
      default -> throw new NoSuchVoucherTypeException(voucherType.getType());
    }
  }

}
