package io.swagger.model.dto;

import io.swagger.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Log
public class TransactionResponseDTO {

    private Integer transactionId;
    private Integer userPerforming;
    private String fromAccount;
    private String toAccount;
    private Double amount;
    private String transactionType;
    private LocalDate timestamp;
    private Double balanceAfterTransfer;

}
