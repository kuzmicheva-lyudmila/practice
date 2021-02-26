package ru.kl.threads.accountservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Account {

    private final long personId;

    private long balance;
}
