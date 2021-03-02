package ru.netology.web.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DataForRegistration {
    private final String login;
    private final String password;
    private final String status;
    public static final String active = "active";
    public static final String blocked = "blocked";
}
