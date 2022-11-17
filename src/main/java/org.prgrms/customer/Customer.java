package org.prgrms.customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Customer {

  private final Long id;
  private final String name;
  private final LocalDateTime registeredDate;
  private final List<UUID> voucherId;

  public Customer(Long id, String name, LocalDateTime registeredDate, List<UUID> voucherId) {
    this.id = id;
    this.name = name;
    this.registeredDate = registeredDate;
    this.voucherId = voucherId;
  }
}
