package org.prgrms.voucher.discountType;

import static org.junit.jupiter.api.Assertions.*;
import static org.prgrms.voucher.VoucherFactory.createVoucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucher.voucherType.VoucherType;

class DiscountAmountTest {
  @DisplayName("입력받은 할인금액이 1보다 작을 때 IllegalStateException을 던진다")
  @Test
  void incorrectlyEnteredDiscountRate2() {
    //given
    String value = "-1";
    //when&then
    assertThrows(IllegalStateException.class, () -> createVoucher(VoucherType.FIXED, value));
  }

  @DisplayName("입력받은 할인금액이 숫자가 아닐 때 NumberFormatException을 던진다")
  @Test
  void whenDiscountRateNotANumber() {
    //given
    String value = "가";
    //when&then
    assertThrows(NumberFormatException.class, () -> createVoucher(VoucherType.FIXED, value));
  }
}