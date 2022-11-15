package org.prgrms.voucher.discountType;

import static org.junit.jupiter.api.Assertions.*;
import static org.prgrms.voucher.VoucherFactory.createVoucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucher.voucherType.VoucherType;

class DiscountRateTest {

  @DisplayName("입력받은 할인율이 적거나 많은 경우에 IllegalStateException을 던진다")
  @Test
  void incorrectlyEnteredDiscountRate() {
    //given
    String minusValue = "-1";
    String overHundred = "101";
    //when&then
    assertAll(
        () -> assertThrows(IllegalStateException.class, () -> createVoucher(VoucherType.PERCENT, minusValue)),
        () -> assertThrows(IllegalStateException.class, () -> createVoucher(VoucherType.PERCENT, overHundred))
    );
  }

  @DisplayName("입력받은 할인율이 숫자가 아닐 때 NumberFormatException을 던진다")
  @Test
  void whenDiscountRateNotANumber() {
    //given
    String value = "가";
    //when&then
    assertThrows(NumberFormatException.class, () ->  createVoucher(VoucherType.PERCENT, value));
  }

}