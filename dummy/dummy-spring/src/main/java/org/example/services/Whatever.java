package org.example.services;

@ExistingEntityValidation(groups = {ExistingEntity.class})
@NewEntityValidation(groups = {NewEntity.class})
public interface Whatever {

    String getSomething();

    record WhateverImpl(String name, String value) implements Whatever {
        @Override
        public String getSomething() {
            return name + value;
        }
    }
}
