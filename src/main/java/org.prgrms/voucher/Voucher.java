package org.prgrms.voucher;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public interface Voucher {
  long discount(long beforeDiscount);

  UUID getVoucherId();
}
