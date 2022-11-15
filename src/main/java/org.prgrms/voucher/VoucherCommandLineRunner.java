package org.prgrms.voucher;

import static org.prgrms.voucher.MenuType.EXIT;
import static org.prgrms.voucher.VoucherFactory.createVoucher;

import java.util.List;
import org.prgrms.console.Console;
import org.prgrms.exception.NoSuchMenuTypeException;
import org.prgrms.memory.CustomerBlackListFileMemory;
import org.prgrms.memory.Memory;

import org.prgrms.voucher.voucherType.Voucher;
import org.prgrms.voucher.voucherType.VoucherType;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherCommandLineRunner implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(VoucherCommandLineRunner.class);

  private final Console console;

  private final Memory voucherMemory;

  private final CustomerBlackListFileMemory blackListFileMemory;

  public VoucherCommandLineRunner(Console console, Memory voucherMemory,
      CustomerBlackListFileMemory blackListFileMemory) {
    this.console = console;
    this.voucherMemory = voucherMemory;
    this.blackListFileMemory = blackListFileMemory;
  }

  @Override
  public void run(String... args) {
    VoucherProgramStatus voucherProgramStatus = new VoucherProgramStatus();

    while (voucherProgramStatus.isRunnable()) {
      try {

        if (isExit(execute())) {
          voucherProgramStatus.stop();
        }

      } catch (RuntimeException e) {
        console.printErrorMsg(e.getMessage());
        logger.error("class: {}, message: {}", e.getClass().getName(), e.getMessage());
      }
    }
  }

  private MenuType execute() {
    MenuType menu = MenuType.of(console.chooseMenu());
    logger.info("Menu : {}", menu);

    switch (menu) {
      case EXIT -> {
        return EXIT;
      }

      case CREATE -> {
        Voucher savedVoucher = saveVoucher();
        console.printSavedVoucher(savedVoucher);
        logger.info("saved_voucher: {}", savedVoucher);
        return MenuType.CREATE;
      }

      case LIST -> {
        List<Voucher> voucherList = voucherMemory.findAll();

        console.printVoucherList(voucherList);
        logger.info("voucherList: {}", voucherList);
        return MenuType.LIST;
      }
      case BLACK -> {
        console.printBlackList(showCustomerBlackList());
        return MenuType.BLACK;
      }

      default ->
          throw new NoSuchMenuTypeException("The command could not be found. Please re-enter");
    }
  }

  public Voucher saveVoucher() {

    while (true) {
      try {
        VoucherType voucherType = enteredVoucherType();
        String inputAmount = console.enteredAmount(voucherType);
        logger.info("input_amount: {}", inputAmount);

        return voucherMemory.save(createVoucher(voucherType, inputAmount));
      } catch (RuntimeException e) {
        console.printErrorMsg(e.getMessage());
        logger.warn("class: {}, message: {}", e.getClass().getName(), e.getMessage());
      }
    }
  }


  private VoucherType enteredVoucherType() {
    String inputType = console.chooseVoucherType();
    return VoucherType.of(inputType);
  }

  private boolean isExit(MenuType menuType) {
    return menuType == EXIT;
  }

  private List<String> showCustomerBlackList() {
    List<String> blacklist = blackListFileMemory.findAll();

    logger.info("customer_blacklist: {}", blacklist);
    return blacklist;
  }

}