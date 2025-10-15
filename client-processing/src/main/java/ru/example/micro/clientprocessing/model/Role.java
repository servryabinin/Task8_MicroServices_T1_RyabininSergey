package ru.example.micro.clientprocessing.model;

public enum Role {
    MASTER,
    GRAND_EMPLOYEE,
    CLIENT,           // прежний "обычный"
    CURRENT_CLIENT,   // роль выданная при создании клиента
    BLOCKED_CLIENT    // роль при блокировке клиента
}
