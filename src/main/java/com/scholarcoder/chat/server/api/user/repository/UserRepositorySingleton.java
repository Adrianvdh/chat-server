package com.scholarcoder.chat.server.api.user.repository;

public class UserRepositorySingleton {
    private static UserRepositorySingleton holder;
    private UserRepository userRepository;

    private UserRepositorySingleton() {
    }

    public static UserRepository get() {
        if(holder== null) {
            holder = new UserRepositorySingleton();
            if(holder.userRepository == null) {
                holder.userRepository = new InMemoryUserRepository();
            }
        }
        return holder.userRepository;
    }
}