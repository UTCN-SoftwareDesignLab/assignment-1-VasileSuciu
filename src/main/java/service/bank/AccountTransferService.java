package service.bank;

import model.validation.Notification;

public interface AccountTransferService {

    Notification<Boolean> moneyTransfer(Long accountFromId, Long accountToId, double sum);

}
