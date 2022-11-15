package org.prgrms.voucher.voucherType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.exception.NoSuchVoucherTypeException;

public class VoucherTypeTest {

  @DisplayName("콘솔에서 1번을 입력받았을 때 고정 타입을 리턴한다.")
  @Test
  void isFixedType() {
    //given
    String fixed = "1";
    //when
    VoucherType voucherType = VoucherType.of(fixed);
    //then
    assertEquals(VoucherType.FIXED, voucherType);
  }

  @DisplayName("콘솔에서 2번을 입력받았을 때 고정 타입을 리턴한다.")
  @Test
  void isPercentType() {
    //given
    String percent = "2";
    //when
    VoucherType voucherType = VoucherType.of(percent);
    //then
    assertEquals(VoucherType.PERCENT, voucherType);
  }

  @DisplayName("선택지에 없는 번호를 입력받으면 NoSuchVoucherTypeException을 던진다")
  @Test
  void isNoMatchingType() {
    //given
    String noType = "3";
    //when&then
    assertThrows(NoSuchVoucherTypeException.class, () -> VoucherType.of(noType),
        noType + "은 존재하지 않습니다. 다시입력해주세요");

  }

  @DisplayName("Voucher 선택시 숫자가 아닌 값을 입력받으면 NoSuchVoucherTypeException을 던진다")
  @Test
  void nonNumericType() {
    //given
    String nonNumeric = "가abc*";
    //when&then
    assertThrows(NoSuchVoucherTypeException.class, () -> VoucherType.of(nonNumeric),
        nonNumeric + "은 존재하지 않습니다. 다시입력해주세요");

  }


}
